package com.xlscoder.xls;

import com.xlscoder.coder.HashHelper;
import com.xlscoder.coder.PGPUtility;
import com.xlscoder.model.Key;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

import static com.xlscoder.lang.Functional.le;

public class XLFile {
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
