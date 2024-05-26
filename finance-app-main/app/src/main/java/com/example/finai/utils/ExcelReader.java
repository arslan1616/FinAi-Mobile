package com.example.finai.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.finai.model.ExcelData;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelReader {

    public static List<ExcelData> readExcelFile(Context context, String fileName) {
        List<ExcelData> dataList = new ArrayList<>();

        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(fileName);
            Workbook workbook = new XSSFWorkbook(inputStream);

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            // İlk satırı atla (başlık satırı)
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                String hisse = getCellValueAsString(row.getCell(0));
                String netKar = getCellValueAsString(row.getCell(1));
                String ozkaynakDegisim = getCellValueAsString(row.getCell(2));
                String ortalamaDegisim = getCellValueAsString(row.getCell(3));
                String durum = getCellValueAsString(row.getCell(4));

                ExcelData data = new ExcelData(hisse, netKar, ozkaynakDegisim, ortalamaDegisim, durum);
                dataList.add(data);
            }

            workbook.close();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataList;
    }

    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }
}