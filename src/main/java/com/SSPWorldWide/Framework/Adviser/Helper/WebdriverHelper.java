package com.SSPWorldWide.Framework.Adviser.Helper;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.SSPWorldWide.Framework.Adviser.Helper.PropReader;
import com.SSPWorldWide.Framework.Adviser.Helper.TakeScreenshotUtility;
import com.SSPWorldWide.Framework.Adviser.ReadExcel.ReadProjectReusables;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;

public class WebdriverHelper {
	private static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();
	public static final String huburl = "http://192.168.74.75:4444/wd/hub";
	private DesiredCapabilities caps;
	public static final String jdbcUrl;
	public static final String jdbcDriver;
	public static final String platformName;
	public static String browserDriverPath;
	public static String testcaseID;
	public static String testSuiteName;
	public static final String runRegression;
	public static final String runSmoke;
	public static final String siteURL;
	public static final String DBUser;
	public static final String DBPassword;
	public static ListMultimap<String, ITestResult> finalReportingMap =  Multimaps.synchronizedListMultimap(ArrayListMultimap.create());
	public static ListMultimap<String, ExtentTest> testcasewithsteps =  Multimaps.synchronizedListMultimap(ArrayListMultimap.create());
	public static Map<String, String> getObjectRepo = new HashMap<String, String>();
	public static int totalPassCount = 0;
	public static int totalFailCount = 0;
	public static int totalCount = 0;
	public static int totalSkipCount = 0;
	public static final String browserConfig;
	private static final Logger LOG = LoggerFactory
			.getLogger(WebdriverHelper.class);
	static {
		platformName = PropReader.readWebdriverConfig("platform");
		runRegression = PropReader.readWebdriverConfig("runRegressionSuite");
		testSuiteName = PropReader.readWebdriverConfig("suiteName");
		testcaseID = PropReader.readWebdriverConfig("testcaseID");
		runSmoke = PropReader.readWebdriverConfig("runSmokeSuite");
		siteURL = PropReader.readWebdriverConfig("site.url");
		jdbcUrl = PropReader.readWebdriverConfig("jdbcUrl");
		jdbcDriver = PropReader.readWebdriverConfig("jdbcDriver");
		DBUser = PropReader.readWebdriverConfig("DBUser");
		DBPassword = PropReader.readWebdriverConfig("DBPassword");
		browserConfig = PropReader.readWebdriverConfig("browser.name");
	}

	public void launchDriver(String browserName) {
		try {
			if (platformName.equalsIgnoreCase("windows") && browserName.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver",
						Constants.FIREFOXDRIVER.toString());
				launchFirefoxDriver();
			} else if (platformName.equalsIgnoreCase("windows") && browserName.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver",
						Constants.CHROMEDRIVER.toString());
				launchChromeDriver();
			} else if (platformName.equalsIgnoreCase("windows") && browserName.equalsIgnoreCase("IE")) {
				System.setProperty("webdriver.ie.driver",
						Constants.INTERNETEXPLORERDRIVER.toString());
				launchIEDriver();
			} 
			else if (platformName.equalsIgnoreCase("windows") && browserName.equalsIgnoreCase("phantomjs")) {
				launchPhantomJSDriver();
			}
			else {
				throw new IllegalArgumentException(
						"Please check Platform : " + platformName + " and browser : " + browserName);
			}
		} catch (IllegalStateException e) {
			LOG.error(e.getMessage());
		}
	}

	private void launchIEDriver() {
		try{
			LOG.info("launching IE driver");
			driver.set(new RemoteWebDriver(new URL(huburl), internetExplorercapabilities()));
		}
		catch (Exception e){
			Reporting.createReport("Configuaration Error","IE");
			Reporting.createExtentTest("Error");
			Reporting.extentTestMap.get((int) (long) (Thread.currentThread().getId())).log(Status.FAIL,"<b>Failure reason :&emsp;</b>" + e.getMessage());
			Reporting.flushReport();
		}
	}

	private void launchChromeDriver() {
		try{
			LOG.info("launching Chrome driver");
			driver.set(new RemoteWebDriver(new URL(huburl), chromecapabilities()));	
		}
		catch (Exception e){
			Reporting.createReport("Configuaration Error","Chrome");
			Reporting.createExtentTest("Error");
			Reporting.extentTestMap.get((int) (long) (Thread.currentThread().getId())).log(Status.FAIL,"<b>Failure reason :&emsp;</b>" + e.getMessage());
			Reporting.flushReport();
		}
	}

	private void launchFirefoxDriver() {
		try{
			LOG.info("launching Firefox driver");
			driver.set(new RemoteWebDriver(new URL(huburl), firefoxDesiredCapabilities()));
		}
		catch (Exception e){
			Reporting.createReport("Configuaration Error","FF");
			Reporting.createExtentTest("Error");
			Reporting.extentTestMap.get((int) (long) (Thread.currentThread().getId())).log(Status.FAIL,"<b>Failure reason :&emsp;</b>" + e.getMessage());
			Reporting.flushReport();
		}
	}
	
	private void launchPhantomJSDriver(){
		try{
		driver.set(new RemoteWebDriver(new URL(huburl), phantomJSDesiredCapabilities()));
		}
		catch (Exception e){
			Reporting.createReport("Configuaration Error","Phantom JS");
			Reporting.createExtentTest("Error");
			Reporting.extentTestMap.get((int) (long) (Thread.currentThread().getId())).log(Status.FAIL,"<b>Failure reason :&emsp;</b>" + e.getMessage());
			Reporting.flushReport();
		}
	}

	private DesiredCapabilities chromecapabilities() {
		caps = DesiredCapabilities.chrome();
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--disable-extensions");
		chromeOptions.addArguments("--disable-web-security");
		chromeOptions.addArguments("--test-type");
		caps.setCapability("chrome.verbose", false);
		caps.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
		caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		return caps;
	}

	protected DesiredCapabilities internetExplorercapabilities() {
		caps = DesiredCapabilities.internetExplorer();
		caps.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
		caps.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
		caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		caps.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
		caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		caps.setCapability("ignoreProtectedModeSettings", true);
		caps.setCapability("ignoreZoomSetting", true);
		caps.setCapability("disable-popup-blocking", true);
		caps.setCapability("enablePersistentHover", true);
		caps.setCapability("requireWindowFocus", true);
		return caps;
	}

	private DesiredCapabilities firefoxDesiredCapabilities() {
		caps = DesiredCapabilities.firefox();
		caps.setCapability("disable-restore-session-state", true);
		caps.setCapability("marionette", true);
		caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		caps.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		return caps;
	}
	
	private DesiredCapabilities phantomJSDesiredCapabilities() {
		caps = new DesiredCapabilities();
		caps.setJavascriptEnabled(true);
		caps.setCapability("takesScreenshot", true);
		String[] phantomargs = new  String[] {"--webdriver-loglevel=none" }; 
		caps.setCapability("cookiesEnabled",false);
		caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, phantomargs);
		caps.setCapability(PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_CLI_ARGS, "--logLevel=NONE");
		caps.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX,"y");
		caps.setCapability("phantomjs.page.settings.useragent", "mozilla/5.0 (windows nt 6.1; win64; x64; rv:16.0) gecko/20121026 firefox/16.0");
		caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
		System.getProperty("user.dir") + "\\drivers\\phantomJS\\phantomjs.exe");
		return caps;
	}

	public static synchronized WebDriver getWebDriver() {
		return driver.get();
	}

	public void closeBrowser() {
		getWebDriver().quit();
	}
	
	@BeforeSuite @Parameters("browser") 
	public void beforeSuite(ITestContext ctx, String browser){
		try {
			String suitename = ctx.getSuite().getName();
			LOG.info("Suite name : " + suitename + "           browser name : " + browser);			
			Reporting.createReport(suitename, browser);
		} catch (Exception e) {
			Reporting.extentTestMap.get((int) (long) (Thread.currentThread().getId())).log(Status.FAIL, ctx.getSuite().getName() + "<br />"
					+ "<b>Failure reason :&emsp;</b>" + e.toString());
		}
	}

	@Parameters("browser") 	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(Method method, String browser) {
		Test test = method.getAnnotation(Test.class);
		Reporting.createExtentTest(method.getName() + "&ensp;:&ensp;" + test.description());
		launchDriver(browser);
	}
	
	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult result) throws IOException {
		if (result.getStatus() == ITestResult.FAILURE) {
			totalFailCount = totalFailCount + 1;
			String screenshot_path = TakeScreenshotUtility.captureScreenshot(getWebDriver(), result.getName());
			Reporting.extentTestMap.get((int) (long) (Thread.currentThread().getId())).addScreenCaptureFromPath(screenshot_path);
			Reporting.extentTestMap.get((int) (long) (Thread.currentThread().getId())).log(Status.FAIL, ReadProjectReusables.failuremethodName.get((int) (long) (Thread.currentThread().getId())) + "<br />"
					+ "<b>Failure reason :&emsp;</b>" + result.getThrowable());
			closeBrowser();
		}

		else {
			totalPassCount = totalPassCount + 1;
		}
		if (result.getStatus() == ITestResult.SKIP) {
			totalSkipCount = totalSkipCount + 1;
		}
		finalReportingMap.put(result.getTestContext().getCurrentXmlTest().getSuite().getName(), result);
	}

	@AfterSuite
	public void afterSuite(ITestContext ctx) {
		totalCount = totalCount + ctx.getSuite().getAllMethods().size();
		Reporting.flushReport();
	}

	public static void launch() throws Exception {
		if (runRegression.equalsIgnoreCase("yes")) {
			DynamicSuiteFileCreator.runRegressionSuite();
		}

		else if (runSmoke.equalsIgnoreCase("yes")) {
			DynamicSuiteFileCreator.runSmokeSuite();
		}

		else if (!(testSuiteName.isEmpty()) && !(testcaseID.isEmpty())) {
				DynamicSuiteFileCreator.runSingleTestCase(testSuiteName, testcaseID);
		}

		else if (!(testSuiteName.isEmpty()) && (testcaseID.isEmpty())) {
				DynamicSuiteFileCreator.runSingleSuite(testSuiteName);
		}
	}

}
