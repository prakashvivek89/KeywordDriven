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
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.xml.XmlSuite;
import com.SSPWorldWide.Framework.Adviser.Helper.PropReader;
import com.SSPWorldWide.Framework.Adviser.ReadExcel.ReadObjectRepo;
import com.aventstack.extentreports.Status;
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
	public static Map<String, String> getObjectRepo = new HashMap<String, String>();
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
	public void beforeSuite() {
		
	}


	@BeforeMethod
	public void beforeMethod(Method method) {
		launchDriver();
		Reporting.test.log(Status.PASS, "Browser launched");
		try {
			Runtime.getRuntime().exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 255");
			Thread.sleep(5000);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException
	{
	if(result.getStatus()==ITestResult.FAILURE)
	{
	String screenshot_path=TakeScreenshotUtility.captureScreenshot(driver, result.getName());
	Reporting.test.addScreenCaptureFromPath(screenshot_path);
	Reporting.test.log(Status.FAIL, result.getThrowable());
	}
	closeBrowser();
	}

	@AfterTest
	public void afterTest() {
		if (driver != null) {
			closeBrowser();
		}
	}

	@AfterSuite
	public void afterSuite() {
		Reporting.flushReport();
		if (driver != null) {
			closeBrowser();
		}
	}
	
	public static void main(String[] args) throws Exception {
		Reporting.createReport();
		if(runRegression.equalsIgnoreCase("yes")) {
			DynamicClassCreator.createRegressionSuiteClasses();
			for (String s : DynamicSuiteFileCreator.createWholeXML().keySet()) {
				XmlSuite suite = DynamicSuiteFileCreator.createWholeXML().get(s);
				DynamicSuiteFileCreator.runTestNG(suite);
			}
		}
		else {
			Reporting.createExtentTest(testcaseID);
			DynamicClassCreator.createSingleTestCaseClass(testSuiteName, testcaseID);
			DynamicSuiteFileCreator.runTestNG(DynamicSuiteFileCreator.createXMLSuite(testSuiteName, testcaseID));
		}
	}

}
