package com.SSPWorldWide.Framework.Adviser.ReadExcel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestDataReader extends ExcelReusables{
	static List<String> key = new ArrayList<String>();
	static List<String> value = new ArrayList<String>();
	static Map<String, String> testdata = new HashedMap<String, String>();

	public static Map<String, String> getTData(String excelName, String sheetName, int rownum) throws Exception {
		Workbook workbook = null;
		String FilePath = System.getProperty("user.dir") + "/ProjectAutomationFiles/testdata/";
		File ExcelFileToRead = new File(FilePath);
		File[] files = ExcelFileToRead.listFiles();
		DataFormatter formatter = new DataFormatter();
		if ((new File(FilePath, (excelName.trim() + ".xlsx")).exists())
				|| (new File(FilePath, (excelName.trim() + ".xls")).exists())) {
			for (File f : files) {
				if ((excelName.trim() + ".xlsx").equalsIgnoreCase(f.getName())) {
					workbook = new XSSFWorkbook(f);
				} else if (f.getName().equalsIgnoreCase(excelName + ".xls")) {
					workbook = new HSSFWorkbook(new POIFSFileSystem(ExcelFileToRead));
				}
			}
		} else {
			throw new Exception("Entered Testdata " +excelName+ " does not exist, please check the name of excel workbook");
		}
		Sheet sheet = workbook.getSheet(sheetName);
		Row valueRow;
		if (rownum <= 0) {
			throw new Exception("Please enter the row num greater than 0");
		} else {
			valueRow = sheet.getRow(rownum);
		}

		Row keyRow = sheet.getRow(0);
		
		for (int i = 2; i < keyRow.getLastCellNum(); i++) {
			if (keyRow.getCell(i).getCellType() == XSSFCell.CELL_TYPE_STRING) {
				key.add(keyRow.getCell(i).getStringCellValue());
			} else if (keyRow.getCell(i).getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
				key.add(keyRow.getCell(i).getStringCellValue());
			} else if (keyRow.getCell(i).getCellType() == XSSFCell.CELL_TYPE_BLANK || keyRow.getCell(i) == null) {
				key.add(keyRow.getCell(i).getStringCellValue());
			} else if (keyRow.getCell(i).getStringCellValue().isEmpty()) {
				key.add(keyRow.getCell(i).getStringCellValue());
			}
		}
		if (!(isRowEmpty(valueRow))) {
			for (int i = 2; i < keyRow.getLastCellNum(); i++) {
				if (valueRow != null) {
					if (valueRow.getCell(i).getCellType() == XSSFCell.CELL_TYPE_STRING) {
						value.add(formatter.formatCellValue(valueRow.getCell(i)));
					} else if (valueRow.getCell(i).getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
						value.add(formatter.formatCellValue(valueRow.getCell(i)));
					} else if (valueRow.getCell(i).getCellType() == XSSFCell.CELL_TYPE_BLANK
							|| valueRow.getCell(i) == null) {
						value.add(formatter.formatCellValue(valueRow.getCell(i)));
					} else if (valueRow.getCell(i).getStringCellValue().isEmpty()) {
						value.add(formatter.formatCellValue(valueRow.getCell(i)));
					}
				}
			}
		} else {
			throw new Exception("Row entered is empty");
		}
		for (int i = 0; i < key.size(); i++) {
			testdata.put(key.get(i), value.get(i));
		}
		workbook.close();
		return testdata;
	}
}
