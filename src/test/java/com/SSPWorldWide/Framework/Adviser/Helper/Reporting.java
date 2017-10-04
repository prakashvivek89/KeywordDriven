package com.SSPWorldWide.Framework.Adviser.Helper;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Reporting extends WebdriverHelper{

	public static ExtentReports report;
	public static ExtentTest test; 
	private static ExtentHtmlReporter htmlReporter ;
	
	public static void createReport(String suiteName) {
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/Report/"+suiteName+".html");
		htmlReporter.setAppendExisting(true);
		report =  new ExtentReports();
		report.attachReporter(htmlReporter);
	}
	
	public static void flushReport() {
		report.flush();
	}
	
	public static void createExtentTest(String testName) {
		test = report.createTest(testName);
	}
	
	public static void endTest(ExtentTest test1) {
		report.removeTest(test1);
	}
}
