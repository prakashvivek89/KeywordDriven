package com.SSPWorldWide.Framework.Adviser.Helper;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.SSPWorldWide.Framework.Adviser.Helper.PropReader;
import com.SSPWorldWide.Framework.Adviser.ReadExcel.ReadObjectRepo;
import com.SSPWorldWide.Framework.Adviser.ReadExcel.Read_ProjectReusables;
import com.aventstack.extentreports.Status;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.SSPWorldWide.Framework.Adviser.Helper.TakeScreenshotUtility;

public class WebdriverHelper {
	public static RemoteWebDriver driver;
	public static DesiredCapabilities caps;
	public static String platformName;
	public static String browserName;
	public static String browserDriverPath;
	public static String testcaseID;
	public static String testSuiteName;
	public static String runRegression;
	public static ListMultimap<String, ITestResult> finalReportingMap = ArrayListMultimap.create();
	public static Map<String, String> getObjectRepo = new HashMap<String, String>();
	public static int totalPassCount = 0;
	public static int totalFailCount = 0;
	public static int totalCount = 0;
	public static int totalSkipCount = 0;
	static {
		platformName = PropReader.readWebdriverConfig("platform");
		browserName = PropReader.readWebdriverConfig("browser.name");
		runRegression = PropReader.readWebdriverConfig("runRegressionSuite");
		testSuiteName = PropReader.readWebdriverConfig("suiteName");
		testcaseID = PropReader.readWebdriverConfig("testcaseID");
		try {
			getObjectRepo = ReadObjectRepo.getORData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void launchDriver() {
		try {
			if (platformName.equalsIgnoreCase("windows") && browserName.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "/drivers/Firefox/geckodriver.exe");
				launchFirefoxDriver();
			} else if (platformName.equalsIgnoreCase("windows") && browserName.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "/drivers/Chrome/chromedriver.exe");
				launchChromeDriver();
			} else if (platformName.equalsIgnoreCase("windows") && browserName.equalsIgnoreCase("IE")) {
				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir") + "/drivers/IE11/IEDriverServer.exe");
				launchIEDriver();
			} else {
				throw new IllegalArgumentException(
						"Please check Platform : " + platformName + " and browser : " + browserName);
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}

	private static void launchIEDriver() {
		driver = new InternetExplorerDriver(internetExplorercapabilities());
	}

	private static void launchChromeDriver() {
		driver = new ChromeDriver(chromecapabilities());
	}

	private static void launchFirefoxDriver() {
		driver = new FirefoxDriver(firefoxDesiredCapabilities());
	}

	private static DesiredCapabilities chromecapabilities() {
		caps = DesiredCapabilities.chrome();
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--disable-extensions");
		chromeOptions.addArguments("--disable-web-security");
		chromeOptions.addArguments("--test-type");
		caps.setCapability("chrome.verbose", false);
		caps.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
		caps.setBrowserName("Chrome");
		caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		return caps;
	}

	private static DesiredCapabilities internetExplorercapabilities() {
		caps = DesiredCapabilities.internetExplorer();
		caps.setBrowserName("internet explorer");
		caps.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
		caps.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
		caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		caps.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
		caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		return caps;
	}

	private static DesiredCapabilities firefoxDesiredCapabilities() {
		caps = DesiredCapabilities.firefox();
		caps.setCapability("disable-restore-session-state", true);
		caps.setCapability("marionette", true);
		caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		caps.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		return caps;
	}

	public static RemoteWebDriver getWebDriver() {
		return driver;
	}

	public static void closeBrowser() {
		driver.quit();
	}

	@BeforeSuite
	public void beforeSuite(ITestContext ctx) {
		Reporting.createReport(ctx.getCurrentXmlTest().getSuite().getName());
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(Method method) {
		Test test = method.getAnnotation(Test.class);
		Reporting.createExtentTest(method.getName() + "&ensp;:&ensp;" + test.description());
		launchDriver();
		try {
			Runtime.getRuntime().exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 255");
			Thread.sleep(5000);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult result) throws IOException {
		if (result.getStatus() == ITestResult.FAILURE) {
			String screenshot_path = TakeScreenshotUtility.captureScreenshot(driver, result.getName());
			Reporting.test.addScreenCaptureFromPath(screenshot_path);
			Reporting.test.log(Status.FAIL, Read_ProjectReusables.methodName + "<br />"
					+ "<b>Failure reason :&emsp;</b>" + result.getThrowable());
		}
		if (driver != null) {
			closeBrowser();
		}
		finalReportingMap.put(result.getTestContext().getCurrentXmlTest().getSuite().getName(), result);
	}

	@AfterSuite
	public void afterSuite(ITestContext ctx) {
		totalCount = totalCount + ctx.getAllTestMethods().length;
		totalPassCount = totalPassCount + ctx.getFailedTests().getAllResults().size();
		totalFailCount = totalFailCount + ctx.getPassedTests().getAllResults().size();
		Reporting.flushReport();
		if (driver != null) {
			closeBrowser();
		}
	}

	public static void launch() {
		if (runRegression.equalsIgnoreCase("yes")) {
			System.out.println("running whole suite");
			try {
				DynamicSuiteFileCreator.runRegressionSuite();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("running single testcase");
			try {
				System.out.println("testsuiteName is  : "+testSuiteName);
				System.out.println("testcaseID is  : "+testcaseID);
				DynamicSuiteFileCreator.runSingleSuite(testSuiteName, testcaseID);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
