package com.SSPWorldWide.Framework.Adviser.Helper;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.ITestResult;

public class ExcelReport {
	private static FileOutputStream fos = null;
	private static XSSFWorkbook workbook = null;
	private static XSSFSheet sheet = null;
	private static XSSFRow row = null;
	private static XSSFCell cell = null;
	private static XSSFFont font = null;
	private static XSSFFont headerFont = null;
	private static XSSFCellStyle style = null;
	private static XSSFCellStyle headerStyle = null;
	private static String summarySheetName = "Summary";
	private static int colCount = 0;
	private static List<ITestResult> allResults;

	private static boolean setCellData(String sheetName, int colNumber, int rowNum, String value, Short index) {
		try {
			sheet = workbook.getSheet(sheetName);
			row = sheet.getRow(rowNum);
			if (row == null) {
				row = sheet.createRow(rowNum);
			}

			cell = row.getCell(colNumber);
			if (cell == null) {
				cell = row.createCell(colNumber);
			}

			applyCellStyle(index.shortValue());
			cell.setCellValue(value);
			return true;
		} catch (Exception arg5) {
			arg5.printStackTrace();
			return false;
		}
	}

	private static boolean setCellHeaderData(String sheetName, int colNumber, int rowNum, String value, Short index) {
		try {
			sheet = workbook.getSheet(sheetName);
			row = sheet.getRow(rowNum);
			if (row == null) {
				row = sheet.createRow(rowNum);
			}

			cell = row.getCell(colNumber);
			if (cell == null) {
				cell = row.createCell(colNumber);
			}

			applyCellHeaderStyle(index.shortValue());
			cell.setCellValue(value);
			return true;
		} catch (Exception arg5) {
			arg5.printStackTrace();
			return false;
		}
	}

	private static String addSheet(String sheetname) {
		sheet = workbook.createSheet(sheetname);
		sheet.setDisplayGridlines(true);
		sheet.setPrintGridlines(true);
		return sheetname;
	}

	private static void applyCellStyle(short index) {
		style = workbook.createCellStyle();
		font.setFontName("Arial");
		font.setFontHeight(12.0D);
		style.setFont(font);
		style.setFillForegroundColor(index);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setBorderBottom(BorderStyle.MEDIUM);
		style.setBorderTop(BorderStyle.MEDIUM);
		style.setBorderRight(BorderStyle.MEDIUM);
		style.setBorderLeft(BorderStyle.MEDIUM);
		cell.setCellStyle(style);
	}

	private static void applyCellHeaderStyle(short index) {
		headerStyle = workbook.createCellStyle();
		font.setFontName("Arial");
		headerFont.setFontHeight(14.0D);
		headerFont.setBold(true);
		headerStyle.setFont(headerFont);
		headerStyle.setFillForegroundColor(index);
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerStyle.setBorderBottom(BorderStyle.THICK);
		headerStyle.setBorderTop(BorderStyle.THICK);
		headerStyle.setBorderRight(BorderStyle.THICK);
		headerStyle.setBorderLeft(BorderStyle.THICK);
		cell.setCellStyle(headerStyle);
	}

	private static int getColumnCount(String sheetName) {
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(0);
		short colCount = row.getLastCellNum();
		return colCount;
	}
	
	private static String getDateWithDay () {
		final DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		((SimpleDateFormat) sdf).applyPattern("EEEE d MMM yyyy");
		String currentDate = sdf.format(cal.getTime());
		System.out.println(currentDate);
		return currentDate;
	}

	public static void generateFinalReportExcelSheet() throws Exception {
		String total = String.valueOf(WebdriverHelper.totalCount);
		String passed = String.valueOf(WebdriverHelper.totalPassCount);
		String failed = String.valueOf(WebdriverHelper.totalFailCount);
		String skipped = String.valueOf(WebdriverHelper.totalSkipCount);
		String test_suite_startTime = String.valueOf(WebdriverHelper.totalSkipCount);
		String test_suite_endTime = String.valueOf(WebdriverHelper.totalSkipCount);
		workbook.getSheet(summarySheetName);
		setCellData("Summary", 2, 5, "Adviser11", Short.valueOf((short) 9));
		setCellData("Summary", 2, 6, getDateWithDay(), Short.valueOf((short) 9));
		setCellData("Summary", 5, 6,passed , Short.valueOf((short) 9));
		setCellData("Summary", 5, 7,failed, Short.valueOf((short) 9));
		setCellData("Summary", 5, 8,skipped, Short.valueOf((short) 9));
		setCellData("Summary", 5, 9,total, Short.valueOf((short) 9));
	}

	public static void generateReport(String folderLocation, String xlFileName) throws Exception {
		workbook = new XSSFWorkbook();
		font = workbook.createFont();
		font.setFontName("Arial");
		headerFont = workbook.createFont();
		style = workbook.createCellStyle();
		headerStyle = workbook.createCellStyle();
		addSheet(summarySheetName);
		for (String moduleName : WebdriverHelper.finalReportingMap.keySet()) {
			generateFinalReportExcelSheet();
			allResults = WebdriverHelper.finalReportingMap.get(moduleName);
			generateModuleWiseReportExcelSheet(moduleName, allResults);
		}
		fos = new FileOutputStream(folderLocation + File.separator + xlFileName);
		workbook.write(fos);
		workbook.close();
		fos.close();
		System.out.println("Excel Report Generated");
	}

	public static void generateModuleWiseReportExcelSheet(String moduleName, List<ITestResult> moduleResults) {
		addSheet(moduleName);
		setCellHeaderData(moduleName, 0, 0, "TestcaseID", Short.valueOf((short) 13));
		setCellHeaderData(moduleName, 1, 0, "TestCaseName", Short.valueOf((short) 13));
		setCellHeaderData(moduleName, 2, 0, "Status", Short.valueOf((short) 13));
		setCellHeaderData(moduleName, 3, 0, "Exception", Short.valueOf((short) 13));
		setCellHeaderData(moduleName, 4, 0, "StartTime", Short.valueOf((short) 13));
		setCellHeaderData(moduleName, 5, 0, "EndTime", Short.valueOf((short) 13));
		setCellHeaderData(moduleName, 6, 0, "Duration", Short.valueOf((short) 13));
		int r = 1;
		for (ITestResult result : moduleResults) {
			String testcaseID = result.getMethod().getMethodName();
			String testCaseName = result.getMethod().getDescription();
			String exception = result.getThrowable().getMessage();
			String startTime = String.valueOf(result.getStartMillis());
			String endTime = String.valueOf(result.getEndMillis());
			setCellData(moduleName, 0, r, testcaseID, Short.valueOf((short) 9));
			setCellData(moduleName, 1, r, testCaseName, Short.valueOf((short) 9));
			setCellData(moduleName, 4, r, startTime, Short.valueOf((short) 9));
			setCellData(moduleName, 5, r, endTime, Short.valueOf((short) 9));
			if (result.getStatus() == ITestResult.SUCCESS) {
				setCellData(moduleName, 2, r, "PASS", Short.valueOf((short) 9));
				setCellData(moduleName, 3, r, "", Short.valueOf((short) 11));
			}
			if (result.getStatus() == ITestResult.FAILURE) {
				setCellData(moduleName, 2, r, "FAIL", Short.valueOf((short) 10));
				setCellData(moduleName, 3, r, exception, Short.valueOf((short) 10));
			}
			r++;
		}
		colCount = getColumnCount(moduleName);

		for (int colPosition = 1; colPosition < colCount; ++colPosition) {
			sheet.autoSizeColumn(colPosition);
		}
	}

}