package com.xlscoder.xls;

import com.xlscoder.coder.HashHelper;
import com.xlscoder.coder.PGPUtility;
import com.xlscoder.model.Key;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.xlscoder.lang.Functional.le;

public class XLFile {
    public static final Logger logger = LoggerFactory.getLogger(XLFile.class);
    /**
     * https://stackoverflow.com/questions/5673260/downloading-a-file-from-spring-controllers
     */
    public static boolean sendAndProcess(ProcessingType processingType, boolean verification, Key key, MultipartFile src, HttpServletResponse response, List<String> desiredColumnNames) throws IOException {
        boolean result = false;
        try (InputStream inputStream = src.getInputStream()) {
            Workbook wb = WorkbookFactory.create(inputStream);
            Sheet sheet = wb.getSheetAt(0);

            String filenamePrefix = "";
            if (processingType == ProcessingType.ENCRYPT) {
                encryptColumns(verification, key, sheet, desiredColumnNames);
                filenamePrefix = "encrypted-";
            } else if (processingType == ProcessingType.DECRYPT) {
                decryptColumns(verification, key, sheet, desiredColumnNames);
                filenamePrefix = "decrypted-";
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            wb.write(baos);

            response.setContentType("mimetype:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setContentLength(baos.size());
            response.setHeader("Content-Disposition", String.format("inline; filename=\"" + filenamePrefix + src.getOriginalFilename() +"\""));

            baos.writeTo(response.getOutputStream());
            response.flushBuffer();

            return true;
        }
    }

    public static void encryptColumns(boolean verification, Key key, Sheet sheet, String... desiredColumnNames) {
        for (String col: desiredColumnNames) {
            encryptColumn(verification, key, sheet, col);
        }
    }

    public static void encryptColumns(boolean verification, Key key, Sheet sheet, List<String> desiredColumnNames ) {
        for (String col: desiredColumnNames) {
            encryptColumn(verification, key, sheet, col);
        }
    }

    public static void encryptColumn(boolean verification, Key key, Sheet sheet, String desiredColumnName) {
        XLSet cellSet = XLSet.extractColumn(sheet, desiredColumnName);
        if (null == cellSet) {
            logger.error(String.format("Column \"%s\" was not found", desiredColumnName));
            return;
        }

        logger.info("Hashing");

        cellSet.replaceOrAppend(verification,
                false,
                false,
                header -> header + ":sha512",
                cell -> HashHelper.stdSha(XLSHelper.getUniversalValue(cell).orElse(""), key.getShaSalt()));

        logger.info("Encrypting");

        cellSet.replaceOrAppend(false,
                true,
                false,
                String::valueOf,
                le(cell -> PGPUtility.encryptString(XLSHelper.getUniversalValue(cell).orElse(""), key)));
    }


    public static void decryptColumns(boolean verification, Key key, Sheet sheet, String... desiredColumnNames) {
        for (String col: desiredColumnNames) {
            decryptColumn(verification, key, sheet, col);
        }
    }

    public static void decryptColumns(boolean verification, Key key, Sheet sheet, List<String> desiredColumnNames ) {
        for (String col: desiredColumnNames) {
            decryptColumn(verification, key, sheet, col);
        }
    }

    public static void decryptColumn(boolean verification, Key key, Sheet sheet, String desiredColumnName) {
        XLSet cellSet = XLSet.extractColumn(sheet, desiredColumnName);
        if (null == cellSet) {
            logger.error(String.format("Column \"%s\" was not found", desiredColumnName));
            return;
        }

        logger.info("Decrypting");

        cellSet.replaceOrAppend(false,
                true,
                false,
                String::valueOf,
                le(cell -> PGPUtility.decryptString(XLSHelper.getUniversalValue(cell).orElse(""), key)));

        if (verification) {
            logger.info("Encrypting for verification");
            encryptColumn(verification, key, sheet, desiredColumnName);

            logger.info("Decrypting for verification again");
            cellSet.replaceOrAppend(false,
                    true,
                    false,
                    String::valueOf,
                    le(cell -> PGPUtility.decryptString(XLSHelper.getUniversalValue(cell).orElse(""), key)));
        }
    }
}
