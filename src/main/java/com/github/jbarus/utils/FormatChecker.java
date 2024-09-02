package com.github.jbarus.utils;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.List;

public class FormatChecker {
    public static boolean checkFormat(XSSFSheet sheet, List<String> columns) {
        XSSFRow row = sheet.getRow(0);
        for(int i = 0; i < columns.size(); i++) {
            if(!row.getCell(i).getStringCellValue().equals(columns.get(i))) {
                return false;
            }
        }
        return true;
    }
}
