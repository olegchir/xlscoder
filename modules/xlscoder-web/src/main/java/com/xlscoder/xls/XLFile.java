package com.xlscoder.xls;

import com.xlscoder.coder.HashHelper;
import com.xlscoder.coder.PGPUtility;
import com.xlscoder.model.Key;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import static com.xlscoder.lang.Functional.le;

public class XLFile {
    /**
     * https://stackoverflow.com/questions/5673260/downloading-a-file-from-spring-controllers
     */
    public static void sendAndEncode(Key key, MultipartFile src, HttpServletResponse dst, List<String> desiredColumnNames) throws IOException {
        try (InputStream inputStream = src.getInputStream()) {
            Workbook wb = WorkbookFactory.create(inputStream);
            Sheet sheet = wb.getSheetAt(0);
            encodeColumns(key, sheet, desiredColumnNames);
            wb.write(dst.getOutputStream());
            dst.flushBuffer();
        }
    }

    public static void encodeColumns(Key key, Sheet sheet, String... desiredColumnNames) {
        for (String col: desiredColumnNames) {
            encodeColumn(key, sheet, col);
        }
    }

    public static void encodeColumns(Key key, Sheet sheet, List<String> desiredColumnNames ) {
        for (String col: desiredColumnNames) {
            encodeColumn(key, sheet, col);
        }
    }
    public static void encodeColumn(Key key, Sheet sheet, String desiredColumnName) {
        XLSet cellSet = XLSet.extractColumn(sheet, desiredColumnName);
        cellSet.replaceOrAppend(false,
                false,
                header -> header + ":sha512",
                cell -> HashHelper.stdSha(XLSHelper.getUniversalValue(cell).orElse(""), key.getShaSalt()));
        cellSet.replaceOrAppend(true,
                false,
                String::valueOf,
                le(cell -> PGPUtility.encryptString(XLSHelper.getUniversalValue(cell).orElse(""), key)));
    }
}
