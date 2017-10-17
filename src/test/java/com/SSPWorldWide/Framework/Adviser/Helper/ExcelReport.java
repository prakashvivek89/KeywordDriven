package com.SSPWorldWide.Framework.Adviser.Helper;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.List;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
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

	public static void generateFinalReportExcelSheet() throws Exception {
		String total = String.valueOf(WebdriverHelper.totalCount);
		String passed = String.valueOf(WebdriverHelper.totalPassCount);
		String failed = String.valueOf(WebdriverHelper.totalFailCount);
		String skipped = String.valueOf(WebdriverHelper.totalSkipCount);
		String test_suite_startTime = String.valueOf(WebdriverHelper.totalSkipCount);
		String test_suite_endTime = String.valueOf(WebdriverHelper.totalSkipCount);
		workbook.getSheet(summarySheetName);
		setCellHeaderData(summarySheetName, 0, 0, "Category", Short.valueOf((short) 13));
		setCellHeaderData(summarySheetName, 1, 0, "Count", Short.valueOf((short) 13));
		setCellData(summarySheetName, 0, 1, "Passed", Short.valueOf((short) 9));
		setCellData(summarySheetName, 1, 1, passed, Short.valueOf((short) 9));
		setCellData(summarySheetName, 0, 2, "Failed", Short.valueOf((short) 9));
		setCellData(summarySheetName, 1, 2, failed, Short.valueOf((short) 9));
		setCellData(summarySheetName, 0, 3, "Skipped", Short.valueOf((short) 9));
		setCellData(summarySheetName, 1, 3, skipped, Short.valueOf((short) 9));
		setCellHeaderData(summarySheetName, 0, 4, "Total", Short.valueOf((short) 49));
		setCellHeaderData(summarySheetName, 1, 4, total, Short.valueOf((short) 49));
		setCellData(summarySheetName, 0, 5, "Start Time", Short.valueOf((short) 9));
		setCellData(summarySheetName, 1, 5, test_suite_startTime, Short.valueOf((short) 9));
		setCellData(summarySheetName, 0, 6, "End Time", Short.valueOf((short) 9));
		setCellData(summarySheetName, 1, 6, test_suite_endTime, Short.valueOf((short) 9));
		setCellData(summarySheetName, 0, 7, "Duration", Short.valueOf((short) 9));
		colCount = getColumnCount(summarySheetName);

		 for (int my_pie_chart_data = 0; my_pie_chart_data < colCount; ++my_pie_chart_data) {
		 sheet.autoSizeColumn((short) my_pie_chart_data);
		 }

		DefaultPieDataset arg53 = new DefaultPieDataset();
		arg53.setValue("Passed", (double) Integer.parseInt(passed));
		arg53.setValue("Failed", (double) Integer.parseInt(failed));
		arg53.setValue("Skipped", (double) Integer.parseInt(skipped));
		JFreeChart myPieChart = ChartFactory.createPieChart("Execution Status in PIE Chart", arg53, true, true, false);
		PiePlot plot = (PiePlot) myPieChart.getPlot();
		plot.setSectionPaint(1, new Color(255, 0, 0));
		plot.setSectionPaint(0, new Color(0, 128, 0));
		plot.setSectionPaint(2, new Color(255, 255, 0));
		short width = 500;
		short height = 500;
		float quality = 5.0F;
		ByteArrayOutputStream chart_out = new ByteArrayOutputStream();
		ChartUtilities.writeChartAsJPEG(chart_out, quality, myPieChart, width, height);
		int my_picture_id = workbook.addPicture(chart_out.toByteArray(), 5);
		chart_out.close();
		XSSFDrawing drawing = sheet.createDrawingPatriarch();
		XSSFClientAnchor my_anchor = new XSSFClientAnchor();
		my_anchor.setCol1(4);
		my_anchor.setRow1(5);
		XSSFPicture my_picture = drawing.createPicture(my_anchor, my_picture_id);
		my_picture.resize();
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
		fos = new FileOutputStream(folderLocation + "/" + xlFileName);
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