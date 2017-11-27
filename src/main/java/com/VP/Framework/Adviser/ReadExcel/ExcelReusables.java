package com.VP.Framework.Adviser.ReadExcel;

import java.math.BigDecimal;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ExcelReusables {
	protected static String getCellValueString(Cell cell) {
		String value = "";
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_BOOLEAN:
				value = String.valueOf(cell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_NUMERIC:
				value = BigDecimal.valueOf(cell.getNumericCellValue()).toPlainString();
				break;
			case Cell.CELL_TYPE_STRING:
				value = String.valueOf(cell.getStringCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:
				value = String.valueOf(cell.getCellFormula());
				break;
			case Cell.CELL_TYPE_BLANK:
				value = "";
				break;
			}
		}
		return value.trim();
	}

	protected static boolean isRowEmpty(Row row) {
		if (row == null) {
			return true;
		}

		int cellCount = row.getLastCellNum() + 1;
		for (int i = 1; i < cellCount; i++) {
			String cellValue = getCellValue(row, i);
			if (cellValue != null && cellValue.length() > 0) {
				return false;
			}
		}
		return true;
	}

	protected static String getCellValue(Row row, int columnIndex) {
		String cellValue;
		Cell cell = row.getCell(columnIndex);
		if (cell == null) {
			cellValue = null;
		} else {
			if (cell.getCellType() != Cell.CELL_TYPE_FORMULA) {
				cellValue = cell.getStringCellValue();
			} else {
				cellValue = cell.getStringCellValue();
			}
		}
		return cellValue;
	}
}
