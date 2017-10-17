package com.SSPWorldWide.Framework.Adviser.Helper;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.map.HashedMap;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;

public class Launcher {
	public static String currDir = null;
	private static List<ITestResult> allResults;

	@Test
	public void launchAutomation() throws Exception {
		WebdriverHelper.launch();
	}

	@BeforeSuite
	public void beforeSuite(ITestContext ctx) {
		ctx.getStartDate().getTime();
		
		createReportFolder();
	}

	@AfterSuite
	public void afterSuite() throws Exception {
//		Xl.generateReport(currDir + File.separator, "Final.xlsx");
//		ExcelReportGenerator.generateExcelReport("Final.xlsx", currDir + File.separator);
		ctx.getEndDate().getTime();
		ExcelReport.generateReport(currDir , "Final.xlsx");
	}

	private static void createReportFolder() {
		currDir = System.getProperty("user.dir") + "/Report/" + getCurrentDate();
		DynamicClassCreator.makeDirectory(currDir);
		DynamicClassCreator.makeDirectory(currDir + File.separator + "Screenshots");
	}

	public static String getCurrentDate() {
		final DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String currentDate = sdf.format(cal.getTime());
		currentDate = currentDate.replace("/", "-").replace(":", "-");
		currentDate = currentDate.split("-")[0] + getMonth(currentDate.split("-")[1]) + currentDate.split("-")[2] + "-"
				+ currentDate.split("-")[3] + "-" + currentDate.split("-")[4];
		return currentDate;
	}

	public static String getMonth(String mnth) {
		Map<String, String> monthMap = new HashedMap<>();
		monthMap.put("01", "Jan");
		monthMap.put("02", "Feb");
		monthMap.put("03", "Mar");
		monthMap.put("04", "Apr");
		monthMap.put("05", "May");
		monthMap.put("06", "Jun");
		monthMap.put("07", "Jul");
		monthMap.put("08", "Aug");
		monthMap.put("09", "Sep");
		monthMap.put("10", "Oct");
		monthMap.put("11", "Nov");
		monthMap.put("12", "Dec");
		return monthMap.get(mnth);
	}
	
	public void generateFinalReport() {
		Reporting.createReport("Final report");
		for (String suiteName : WebdriverHelper.finalReportingMap.keySet()) {
			allResults = WebdriverHelper.finalReportingMap.get(suiteName);
			Reporting.createExtentTest(suiteName);
			System.out.println(allResults.size());
			for (ITestResult result : allResults) {
				if (result.getStatus() == ITestResult.FAILURE) {
					Reporting.test.log(Status.FAIL,
							result.getMethod().getMethodName() + "&ensp;:&ensp;" + result.getMethod().getDescription());
				} else if (result.getStatus() == ITestResult.SUCCESS) {
					Reporting.test.log(Status.PASS,
							result.getMethod().getMethodName() + "&ensp;:&ensp;" + result.getMethod().getDescription());
				}
			}
		}
		Reporting.flushReport();
	}
}