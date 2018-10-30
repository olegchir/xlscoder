package com.xlscoder.xls;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;

import java.util.ArrayList;
import java.util.List;

import static com.xlscoder.xls.XLSHelper.getCellAt;

public class XLSet {
    private List<Cell> items = new ArrayList<>();

    private XLSet() {
    }

    public static XLSet extractColumn(Sheet sheet, String rowName) {
        XLSet that = new XLSet();

        Row row = sheet.getRow(0);
        short lastCellNum = row.getLastCellNum();

        int foundRowIndex = -1;
        for (int i = 0; i < lastCellNum; i++) {
            Cell currCell = getCellAt(row, i);

            String stringCellValue = currCell.getStringCellValue();
            if (!StringUtils.isBlank(stringCellValue) && stringCellValue.equals(rowName)) {
                foundRowIndex = i;
            }
        }

        if (-1 == foundRowIndex) {
            return null;
        }
        int lastRowNum = sheet.getLastRowNum();
        for (int rowNum = 1; rowNum <= lastRowNum; rowNum++) {
            Cell currCell = getCellAt(sheet, rowNum, foundRowIndex);
            that.getItems().add(currCell);
        }

        return that;
    }

    public List<Cell> getItems() {
        return items;
    }

    public void setItems(List<Cell> items) {
        this.items = items;
    }
}
