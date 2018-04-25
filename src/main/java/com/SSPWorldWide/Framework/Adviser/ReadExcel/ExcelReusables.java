package com.SSPWorldWide.Framework.Adviser.ReadExcel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.SSPWorldWide.Framework.Adviser.Helper.Constants;

public class ExcelReusables {
	@SuppressWarnings("deprecation")
	protected String getCellValueString(Cell cell) {
		String value = "";
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_BOOLEAN:
				value = String.valueOf(cell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if(DateUtil.isCellDateFormatted(cell)) {
					DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					value = df.format(cell.getDateCellValue());
				}
				else {
					value = String.valueOf(BigDecimal.valueOf(cell.getNumericCellValue()).intValue());
				}
				break;
			case Cell.CELL_TYPE_STRING:
				value = String.valueOf(cell.getStringCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:
				value = String.valueOf(cell.getCellFormula());
				break;
				
			case Cell.CELL_TYPE_ERROR:
				value = String.valueOf(cell.getCellFormula());
				break;
				
			case Cell.CELL_TYPE_BLANK:
				value = "";
				break;
			}
		}
		return value.trim();
	}

	protected boolean isRowEmpty(Row row) {
		if (row == null) {
			return true;
		}

		int cellCount = row.getLastCellNum() + 1;
		for (int i = 1; i < cellCount; i++) {
			Cell cell = row.getCell(i);
			String cellValue = getCellValueString(cell);
			if (cellValue != null && cellValue.length() > 0) {
				return false;
			}
		}
		return true;
	}
	
	protected static Workbook readFile(String filePath, String excelName) throws IOException, InvalidFormatException {
		Workbook workbook = null ;
		String fileloc = Constants.COPIEDFILESDIR.toString() +File.separator+ filePath;
		File excelFileToRead = new File(fileloc);
		File[] files = excelFileToRead.listFiles();
		if (!(excelFileToRead.toString().contains("~"))) {
		if ((new File(fileloc, (excelName.trim() + ".xlsx")).exists())
				|| (new File(fileloc, (excelName.trim() + ".xls")).exists())) {
			for (File f : files) {
				if ((excelName.trim() + ".xlsx").equalsIgnoreCase(f.getName())) {
					workbook = WorkbookFactory.create(f);
				} else if (f.getName().equalsIgnoreCase(excelName + ".xls")) {
					workbook = new HSSFWorkbook(new POIFSFileSystem(excelFileToRead));
				}
			}
		} else {
			throw new FileNotFoundException("Excel workbook : " + excelName + " does not exist, please check the name of excel workbook");
			} 
		}
		return workbook;
	}
}
