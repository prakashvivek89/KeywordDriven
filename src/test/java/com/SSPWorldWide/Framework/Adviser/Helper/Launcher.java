package com.SSPWorldWide.Framework.Adviser.Helper;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.map.HashedMap;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.SSPWorldWide.Framework.Adviser.ReadExcel.ReadObjectRepo;
import com.aventstack.extentreports.Status;

public class Launcher {
	public static String currDir = null;
	private static List<ITestResult> allResults;
	public static String startTime;
	public static String endTime;
	public static long totalTime;

	@Test
	public void launchAutomation() throws Exception {
		WebdriverHelper.launch();
	}

	@BeforeSuite
	public void beforeSuite(ITestContext ctx) throws Exception {
		createReportFolder();
		if (WebdriverHelper.runRegression.equalsIgnoreCase("yes")) {
			CopyFiles.copyFileForRegression();
		} 
		
		else if (WebdriverHelper.runSmoke.equalsIgnoreCase("yes")) {
			CopyFiles.copyFileForRegression();
		}
		
		else if (!(WebdriverHelper.testSuiteName.isEmpty())&&!(WebdriverHelper.testcaseID.isEmpty())) {
			CopyFiles.copySingleFile(WebdriverHelper.testSuiteName);
		} 
		else if (!(WebdriverHelper.testSuiteName.isEmpty())&&(WebdriverHelper.testcaseID.isEmpty())) {
			CopyFiles.copySingleFile(WebdriverHelper.testSuiteName);
		} 
		WebdriverHelper.getObjectRepo = ReadObjectRepo.getORData();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		startTime = sdf.format(cal.getTime());
	}

	@AfterSuite
	public void afterSuite() throws Exception {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		endTime = sdf.format(cal.getTime());
		Date date1 = sdf.parse(startTime);
		Date date2 = sdf.parse(endTime);
		totalTime = date2.getTime() - date1.getTime();
		ExcelReport.generateReport();
		CopyFiles.deleteFlder();
	}

	private static void createReportFolder() {
		currDir = System.getProperty("user.dir") + "/Report/" + getCurrentDate();
		DynamicClassCreator.makeDirectory(currDir);
		DynamicClassCreator.makeDirectory(currDir + File.separator + "Screenshots");
		DynamicClassCreator.makeDirectory(currDir + File.separator + "Detailed Report");
		DynamicClassCreator.makeDirectory(currDir + File.separator + "Summarized Report");
	}

	private static String getCurrentDate() {
		final DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String currentDate = sdf.format(cal.getTime());
		currentDate = currentDate.replace("/", "-").replace(":", "-");
		currentDate = currentDate.split("-")[0] +" " + getMonth(currentDate.split("-")[1]) +" " + currentDate.split("-")[2] + "-"
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