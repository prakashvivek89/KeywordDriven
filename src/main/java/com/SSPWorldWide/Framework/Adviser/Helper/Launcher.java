package com.SSPWorldWide.Framework.Adviser.Helper;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import org.apache.commons.collections4.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.SSPWorldWide.Framework.Adviser.ReadExcel.ReadObjectRepo;
import com.SSPWorldWide.Framework.Adviser.ReadExcel.ReadProjectReusables;
import com.SSPWorldWide.Framework.Adviser.ReadExcel.ReadTestcaseFile;
import com.SSPWorldWide.Framework.Adviser.ReadExcel.TestDataReader;
import com.aventstack.extentreports.Status;

/**
 * This class is a start point of the automation execution.
 */

public class Launcher {
	static String currDir = null;
	public static String startTime;
	public static String endTime;
	public static long totalTime;
	private static final Logger LOG = LoggerFactory
			.getLogger(Launcher.class);

	@Test
	public void launchAutomation() {
		try{
			WebdriverHelper.launch();
		}
		catch (Exception e){
			LOG.error(e.getMessage());
		}
	}
		/*  In this method, report folder is created and all the excel files are copied to a temp folder*/	
	
	@BeforeSuite
	public static void beforeSuite(ITestContext ctx) {
		try {
		DatabaseConnection.getInstance().createDBConnection(WebdriverHelper.jdbcUrl, WebdriverHelper.jdbcDriver, WebdriverHelper.DBUser, WebdriverHelper.DBPassword);
		createReportFolder();
		Readgridfile.getInstance().getbrowser();
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
		ReadObjectRepo.getInstance().getORData();
		ReadTestcaseFile.getInstance().readAllFiles(); 
		ReadProjectReusables.getInstance().getMethodActions();
		TestDataReader.getInstance().readAllFiles();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		startTime = sdf.format(cal.getTime());
		}
		catch (Exception e) {
			Reporting.createReport("Configuaration Error","");
			Reporting.createExtentTest("config Error");
			Reporting.extentTestMap.get((int) (long) (Thread.currentThread().getId())).log(Status.FAIL,"<b>Failure reason(Launcher):&emsp;</b>" + e.getMessage());
			Reporting.flushReport();
			LOG.error(e.getMessage());
		}
	}

	/*  In this method, final excel report is generated and the temp folder containing the copied excel file is deleted*/
	
	@AfterSuite
	public static void afterSuite() throws Exception {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		endTime = sdf.format(cal.getTime());
		Date date1 = sdf.parse(startTime);
		Date date2 = sdf.parse(endTime);
		totalTime = date2.getTime() - date1.getTime();
		ExcelReport.generateReport();
		CopyFiles.deleteFlder();
		DatabaseConnection.getInstance().closeDBConnection();
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

}