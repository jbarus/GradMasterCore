package com.github.jbarus.utils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DataLoader {

    public static List<List<String>> loadData(String pathToFile, List<String> columnList, List<String> columnsName, HashMap<String, String> rowRange) {
        XSSFSheet sheet = getSheet(pathToFile);
        if(FormatChecker.checkFormat(sheet,columnList)){
            return loadFromXlsx(sheet, columnList, columnsName, rowRange);
        }else{
            return new ArrayList<>();
        }
    }

    private static List<List<String>> loadFromXlsx(XSSFSheet sheet, List<String> columnList, List<String> columnNames, HashMap<String, String> rowRange) {
        //Get column indexes in wanted order
        List<Integer> columnIndexes = getColumnIndexes(sheet.getRow(0), columnList, columnNames);
        //Get row indexes
        List<Integer> rowIndexes = getRowIndexes(sheet, columnList, rowRange);
        //Load data
        List<List<String>> data = new ArrayList<>();

        DataFormatter formatter = new DataFormatter();

        for(int rowIndex : rowIndexes){
            List<String> rowData = new ArrayList<>();
            for(int columnIndex : columnIndexes){
                rowData.add(formatter.formatCellValue(sheet.getRow(rowIndex).getCell(columnIndex)));
            }
            data.add(rowData);
        }
        return data;
    }

    private static List<Integer> getRowIndexes(XSSFSheet sheet, List<String> columnList, HashMap<String, String> rowRange) {
        List<Integer> columnIndexes = getColumnIndexes(sheet.getRow(0), columnList,rowRange.keySet().stream().toList());
        List<Integer> rowIndexes = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();
        for (Row row : sheet){
            for (int index : columnIndexes){
                if(!row.getCell(index).toString().isBlank() && rowRange.containsValue(formatter.formatCellValue(row.getCell(index)))){
                    rowIndexes.add(row.getRowNum());
                }
            }
        }
        return rowIndexes.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() == rowRange.keySet().size())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private static List<Integer> getColumnIndexes(XSSFRow row, List<String> columnList, List<String> columnNames) {
        List<Integer> columnIndexes = new ArrayList<>();
        for (int i = 0; i < columnList.size(); i++) {
            if(columnNames.contains(columnList.get(i))){
                columnIndexes.add(i);
            }
            if(columnIndexes.size() == columnNames.size()){
                break;
            }
        }
        return  columnIndexes;
    }

    private static XSSFSheet getSheet(String pathToFile) {
        FileInputStream fis = null;
        XSSFWorkbook workbook = null;
        String[] columnsToFind = {"NAZWISKO", "IMIE", "DATA_OBRONY", "RECENZENT"};
        try {
            fis = new FileInputStream(new File(pathToFile));
            workbook = new XSSFWorkbook(fis);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + pathToFile);
        } catch (IOException e) {
            throw new RuntimeException("Could not read file: " + pathToFile);
        }

        XSSFSheet sheet = workbook.getSheetAt(0);
        return sheet;
    }
}
