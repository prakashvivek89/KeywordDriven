package com.SSPWorldWide.Framework.Adviser.Helper;

import java.io.File;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.IReport;
import com.aventstack.extentreports.RunResult;
import com.aventstack.extentreports.TestListener;
import com.aventstack.extentreports.model.BasicReportElement;
import com.aventstack.extentreports.model.Test;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.*;

public class Reporting extends WebdriverHelper {

	public static ExtentReports report;
	public static ExtentTest test;
	private static ExtentHtmlReporter htmlReporter;

	public static void createReport(String suiteName) {
		htmlReporter = new ExtentHtmlReporter(Launcher.currDir + File.separator + suiteName + ".html");
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setDocumentTitle("Adviser Automation Report");
		htmlReporter.config().setReportName("Regression cycle");
		htmlReporter.getSystemAttributeContext();
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.STANDARD);
		report = new ExtentReports();
		report.attachReporter(htmlReporter);
		report.setReportUsesManualConfiguration(true);
		report.setSystemInfo("Browser : ", browserName);
		report.setSystemInfo("Environment", "QA");
		report.setAnalysisStrategy(AnalysisStrategy.TEST);
	}

	public static void flushReport() {
		report.flush();
	}

	public static void createExtentTest(String testName) {
		test = report.createTest(testName);
		test.assignAuthor("Vivek");
	}

	public static void endTest(ExtentTest test1) {
		report.removeTest(test1);
	}
}
