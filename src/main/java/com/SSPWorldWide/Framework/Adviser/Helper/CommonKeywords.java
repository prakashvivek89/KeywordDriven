package com.SSPWorldWide.Framework.Adviser.Helper;

import java.awt.AWTException;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import com.SSPWorldWide.Framework.Adviser.Exceptions.DatabaseExceptions;
import com.SSPWorldWide.Framework.Adviser.Exceptions.SeleniumExceptions;

/**
 * This class contains all the Selenium methods
 */

public class CommonKeywords extends WebdriverHelper{
	private final long DRIVER_WAIT_TIME = 30;
	public Map<String, String> mapToStoreVariable = new ConcurrentHashMap<>();
	protected  Map<String, String> listToStoreDBValidation = new ConcurrentHashMap<>();
	public Map<String, String> val = new ConcurrentHashMap<>();
	 String parentWindowHandle = null;
	private  final Logger LOG = LoggerFactory.getLogger(CommonKeywords.class);
	WebDriver driver = WebdriverHelper.getWebDriver();
	private WebDriverWait wait = new WebDriverWait(driver, DRIVER_WAIT_TIME);

	public  void getWebelementAction(String action, By by, String testdata) throws SeleniumExceptions, IOException, AWTException {
		switch (action.toLowerCase()) {
		case "entertext":
				waitForExpectedAndEnterText(by, testdata);
			break;

		case "entertextbyscript":
			enterTextByScript(by, testdata);
			break;

		case "click":
			waitForClick(by);
			break;

		case "validateelementpresent":
			elementIsPresent(by);
			break;

		case "validateelementnotpresent":
			elementIsNotPresent(by);
			break;

		case "currenturlcontains":
			currentURLContains(testdata);
			break;

		case "gettextandmatch":
			getTextAndMatch(by, testdata);
			break;

		case "gettext":
			getText(by, testdata);
			break;

		case "textmatch":
			textMatch(testdata);
			break;

		case "textpartialmatch":
			textPartialMatch(testdata);
			break;

		case "verifytextnotcontained":
			verifyTextNotContained(by, testdata);
			break;

		case "textnotmatch":
			textNotMatch(testdata);
			break;

		case "notextpresent":
			noTextPresent(by);
			break;

		case "getattribute":
			getAttribute(by, testdata);
			break;

		case "waitforelementtodisappear":
			waitforelementtodisappear(by);
			break;

		case "waitforelementtovisible":
			waitforelementtovisible(by);
			break;

		case "clear":
			clear(by);
			break;

		case "selectitembytext":
			selectItemByText(by, testdata);
			break;

		case "selectitembyvalue":
			selectItemByValue(by, testdata);
			break;

		case "selectitembyindex":
			selectItemByIndex(by, testdata);
			break;

		case "executescript":
			executeScript(by, testdata);
			break;

		case "fileupload":
			fileUpload(by, testdata);
			break;

		case "wait":
			wait(testdata);
			break;

		case "verifytextcontainedinlist":
			verifyTextContainedInList(by, testdata);
			break;

		case "verifytextpresentinlist":
			verifyTextPresentInList(by, testdata);
			break;

		case "getdatafromdatabase":
			getDataFromDataBase(testdata);
			break;

		case "getdata":
			getData(testdata);
			break;

		case "getdatafordbvalidation":
			getDataForDBValidation(by, testdata);
			break;

		case "clickwithoption":
			clickWithOption(by, testdata);
			break;
			
		case "clickbyjs":
			clickByJS(by);
			break;
			
		case "readfile":
			readfile(testdata);
			break;
		case "":
			throw new SeleniumExceptions("Please enter an action");

		default:
			throw new SeleniumExceptions("Action : '" + action + "' is not defined");
		}
	}

	private  void clickByJS(By by) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement ele = wait.until(ExpectedConditions.elementToBeClickable(by));
		js.executeScript("arguments[0].click();", ele);
	}

	public  void getBrowserAction(String action) throws SeleniumExceptions {
		switch (action.toLowerCase()) {

		case "navigateto":
			navigateToURL(WebdriverHelper.siteURL);
			break;

		case "closebrowser":
			closeCurrentBrowser();
			break;

		case "quitbrowser":
			closeBrowser();
			break;

		case "refreshbrowser":
			refreshBrowser();
			break;

		case "goforward":
			goForward();
			break;

		case "goback":
			goBack();
			break;

		case "switchtonexttab":
			switchToNextTab();
			break;

		case "switchtoprevioustab":
			switchToPreviousTab();
			break;

		case "comparewithdb":
			compareWithDB();
			break;

		default:
			throw new SeleniumExceptions("Please enter an action");
		}
	}

	private  void switchToNextTab() {
		parentWindowHandle = driver.getWindowHandle();
		driver.getWindowHandles();
		 ArrayList<String> windowHandles = new ArrayList<String>(driver.getWindowHandles());
		for (String window : windowHandles) {
			if (!(window.equals(parentWindowHandle))) {
				driver.switchTo().window(window);
				driver.switchTo().defaultContent();
		if(driver.toString().contains("IE")) {
					((JavascriptExecutor) getWebDriver()).executeScript("window.focus();");
				}
			}
		}
	}

	private  void switchToPreviousTab() {
		driver.findElement(By.tagName("html")).sendKeys(Keys.CONTROL, Keys.PAGE_UP);
		driver.switchTo().defaultContent();
	}

	private  void selectItemByText(By by, String testdata) throws SeleniumExceptions {
		Select sel = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(by)));
		try {
			if (!(testdata.isEmpty())) {
				sel.selectByVisibleText(testdata);
			} else {
				sel.selectByIndex(0);
			}
		} catch (Exception e) {
			throw new SeleniumExceptions(
					"Dropdown with locator '" + by + "' does not contain the visible text '" + testdata + "'");
		}
	}

	private  void selectItemByValue(By by, String testdata) throws SeleniumExceptions {
		Select sel = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(by)));
		try {
			if (!(testdata.isEmpty())) {
				sel.selectByValue(testdata);
			} else {
				sel.selectByIndex(0);
			}
		} catch (Exception e) {
			throw new SeleniumExceptions(
					"Dropdown with locator '" + by + "' does not contain the value '" + testdata + "'");
		}
	}

	private  void selectItemByIndex(By by, String testdata) throws SeleniumExceptions {
		Select sel = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(by)));
		try {
			if (!(testdata.isEmpty())) {
				sel.selectByIndex(Integer.parseInt(testdata));
			} else {
				sel.selectByIndex(0);
			}
		} catch (Exception e) {
			throw new SeleniumExceptions(
					"Dropdown with locator '" + by + "' does not contain the index '" + testdata + "'");
		}
	}

	private  void clear(By by) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(by)).clear();
	}

	private  void waitforelementtodisappear(By by) {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
	}

	private  void waitforelementtovisible(By by) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	private  void getTextAndMatch(By by, String text) {
		if (mapToStoreVariable.containsKey(text)) {
			Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(by)).getText(),
					mapToStoreVariable.get(text));
		} else {
			Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(by)).getText(), text);
		}
	}

	private  void verifyTextNotContained(By by, String testdata) throws SeleniumExceptions {
		if (testdata.contains(wait.until(ExpectedConditions.visibilityOfElementLocated(by)).getText()))
			;
		else {
			throw new SeleniumExceptions("'" + testdata + "' contains '"
					+ wait.until(ExpectedConditions.visibilityOfElementLocated(by)).getText() + "'");
		}
	}

	private  void clickWithOption(By by, String text) throws SeleniumExceptions {
		if (text.equalsIgnoreCase("click")) {
			if (wait.until(ExpectedConditions.elementToBeClickable(by)).isDisplayed()) {
				wait.until(ExpectedConditions.elementToBeClickable(by)).click();
			} else {
				throw new SeleniumExceptions("element with locator '" + by + "' is not present");
			}
		}
		
		if (text.equalsIgnoreCase("noclick")) {
			if (wait.until(ExpectedConditions.elementToBeClickable(by)).isDisplayed()) {
			} else {
				throw new SeleniumExceptions("element with locator '" + by + "' is not present");
			}
		}
	}

	private  void verifyTextPresentInList(By by, String data) throws SeleniumExceptions {
		List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
		List<String> text = new ArrayList<>();
		for (int i = 0; i < elements.size(); i++) {
			text.add(elements.get(i).getText());
		}
		if (text.contains(data))
			;
		else {
			throw new SeleniumExceptions(data + " is not present");
		}
	}

	private  void verifyTextContainedInList(By by, String data) throws SeleniumExceptions {
		List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
		for (int i = 0; i < elements.size(); i++) {
			if (elements.get(i).getText().contains(data)) {

			} else {
				throw new SeleniumExceptions(data + " is not present");
			}
		}
	}

	private  void noTextPresent(By by) throws SeleniumExceptions {
		if (wait.until(ExpectedConditions.visibilityOfElementLocated(by)).getAttribute("value").equalsIgnoreCase(""))
			;
		else if (wait.until(ExpectedConditions.visibilityOfElementLocated(by)).getText().equalsIgnoreCase(""))
			;
		else {
			throw new SeleniumExceptions(
					"Text '" + wait.until(ExpectedConditions.visibilityOfElementLocated(by)).getAttribute("value")
							+ "' is present");
		}
	}

	private  void textNotMatch(String testdata) {
		if ((mapToStoreVariable.get(testdata.split("``")[0]) != null)
				&& (mapToStoreVariable.get(testdata.split("``")[1]) != null)) {
			Assert.assertNotEquals(mapToStoreVariable.get(testdata.split("``")[0]),
					mapToStoreVariable.get(testdata.split("``")[1]));
		} else if ((mapToStoreVariable.get(testdata.split("``")[0]) == null)
				&& (mapToStoreVariable.get(testdata.split("``")[1]) != null)) {
			Assert.assertNotEquals(testdata.split("``")[0], mapToStoreVariable.get(testdata.split("``")[1]));
		} else if ((mapToStoreVariable.get(testdata.split("``")[0]) != null)
				&& (mapToStoreVariable.get(testdata.split("``")[1]) == null)) {
			Assert.assertNotEquals(mapToStoreVariable.get(testdata.split("``")[0]), testdata.split("``")[1]);
		} else if ((mapToStoreVariable.get(testdata.split("``")[0]) == null)
				&& (mapToStoreVariable.get(testdata.split("``")[1]) == null)) {
			Assert.assertNotEquals(testdata.split("``")[0], (testdata.split("``")[1]));
		}
	}

	private  void getText(By by, String testdata) {
		mapToStoreVariable.put(testdata, wait.until(ExpectedConditions.visibilityOfElementLocated(by)).getText());
	}

	private void getAttribute(By by, String testdata) throws SeleniumExceptions {
		if (testdata.contains("``")) {
			if (wait.until(ExpectedConditions.visibilityOfElementLocated(by))
					.getAttribute(testdata.split("``")[1]) != null) {
				mapToStoreVariable.put(testdata.split("``")[0],
						wait.until(ExpectedConditions.visibilityOfElementLocated(by))
								.getAttribute(testdata.split("``")[1]).trim());
			} else if (testdata.split("``")[1].equalsIgnoreCase("text")) {
				mapToStoreVariable.put(testdata.split("``")[0],
						wait.until(ExpectedConditions.visibilityOfElementLocated(by)).getText());
			} else if (testdata.split("``")[1].equalsIgnoreCase("dropdownvalue")) {
				mapToStoreVariable.put(testdata.split("``")[0],
						new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(by)))
								.getFirstSelectedOption().getAttribute("value"));
			} else if (testdata.split("``")[1].equalsIgnoreCase("dropdowntext")) {
				mapToStoreVariable.put(testdata.split("``")[0],
						new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(by)))
								.getFirstSelectedOption().getText());
			} 
			
			else if (testdata.split("``")[1].equalsIgnoreCase("dropdownindex")) {
				List<WebElement> list = new Select(waitForExpectedElement(by)).getOptions();
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getText()
							.equals(new Select(waitForExpectedElement(by)).getFirstSelectedOption().getText())) {
						mapToStoreVariable.put(testdata.split("``")[0], String.valueOf(i));
						break;
					}
				}
			}
			
			else {
				throw new SeleniumExceptions("Attribute '" + testdata.split("``")[1] + "' is not valid for the element");
			}
		}
	}

	private  void textMatch(String testdata) {
		if ((mapToStoreVariable.get(testdata.split("``")[0]) != null)
				&& (mapToStoreVariable.get(testdata.split("``")[1]) != null)) {
			Assert.assertEquals(mapToStoreVariable.get(testdata.split("``")[0]).trim(),
					mapToStoreVariable.get(testdata.split("``")[1]).trim());
		} else if ((mapToStoreVariable.get(testdata.split("``")[0]) == null)
				&& (mapToStoreVariable.get(testdata.split("``")[1]) != null)) {
			Assert.assertEquals(testdata.split("``")[0].trim(), mapToStoreVariable.get(testdata.split("``")[1]).trim());
		}

		else if ((mapToStoreVariable.get(testdata.split("``")[0]) != null)
				&& (mapToStoreVariable.get(testdata.split("``")[1]) == null)) {
			Assert.assertEquals(mapToStoreVariable.get(testdata.split("``")[0].trim()), testdata.split("``")[1].trim());
		}

		else if ((mapToStoreVariable.get(testdata.split("``")[0]) == null)
				&& (mapToStoreVariable.get(testdata.split("``")[1]) == null)) {
			Assert.assertEquals(testdata.split("``")[0].trim(), (testdata.split("``")[1]).trim());
		}
	}

	private  void textPartialMatch(String testdata) throws SeleniumExceptions {
		if ((mapToStoreVariable.get(testdata.split("``")[0]) != null)
				&& (mapToStoreVariable.get(testdata.split("``")[1]) != null)) {
			if (mapToStoreVariable.get(testdata.split("``")[0])
					.contains(mapToStoreVariable.get(testdata.split("``")[1]))) {
			} else {
				throw new SeleniumExceptions("Text '" + mapToStoreVariable.get(testdata.split("``")[0])
						+ "' does not contains the text'" + mapToStoreVariable.get(testdata.split("``")[1]) + "'");
			}
		}

		else if ((mapToStoreVariable.get(testdata.split("``")[0]) == null)
				&& (mapToStoreVariable.get(testdata.split("``")[1]) != null)) {
			if (testdata.split("``")[0].contains(mapToStoreVariable.get(testdata.split("``")[1]))) {

			} else {
				throw new SeleniumExceptions("Text '" + testdata.split("``")[0] + "' does not contains the text'"
						+ mapToStoreVariable.get(testdata.split("``")[1]) + "'");
			}
		}

		else if ((mapToStoreVariable.get(testdata.split("``")[0]) != null)
				&& (mapToStoreVariable.get(testdata.split("``")[1]) == null)) {
			if (mapToStoreVariable.get(testdata.split("``")[0]).contains(testdata.split("``")[1])) {

			} else {
				throw new SeleniumExceptions("Text '" + mapToStoreVariable.get(testdata.split("``")[0])
						+ "' does not contains the text'" + testdata.split("``")[1] + "'");
			}
		}

		else if ((mapToStoreVariable.get(testdata.split("``")[0]) == null)
				&& (mapToStoreVariable.get(testdata.split("``")[1]) == null)) {
			if (testdata.split("``")[0].contains(testdata.split("``")[1])) {

			} else {
				throw new SeleniumExceptions("Text '" + testdata.split("``")[0] + "' does not contains the text'"
						+ testdata.split("``")[1] + "'");
			}
		}
	}

	private  void currentURLContains(String URL) throws SeleniumExceptions {
		if (mapToStoreVariable.get(URL) == null) {
			wait.until(ExpectedConditions.urlContains(URL));
		} else if (mapToStoreVariable.get(URL) != null) {
			wait.until(ExpectedConditions.urlContains(mapToStoreVariable.get(URL)));
		}

		else {
			throw new SeleniumExceptions(
					URL + " is not present in the current URL '" + driver.getCurrentUrl() + "'");
		}
	}

	private  void elementIsNotPresent(By by) {
		Assert.assertTrue(wait.until(ExpectedConditions.invisibilityOfElementLocated(by)));
	}

	private  void elementIsPresent(By by) {
		Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(by)).isDisplayed());
	}

	private  void closeCurrentBrowser() {
		driver.close();
		driver.switchTo().defaultContent();
	}

	private  void navigateToURL(String URL) {
//		LOG.info(Thread.currentThread().getId() + "    BROWSER LAUNCH with driver instance : " + driver); 
		driver.navigate().to(URL);
		driver.manage().window().maximize();
	}

	private  void waitForExpectedAndEnterText(By by, String text) throws SeleniumExceptions {
		if (text.equalsIgnoreCase("randstring")) {
			text = RandomStringUtils.randomAlphabetic(6);
		} else if (text.toLowerCase().contains("randint")) {
			if (text.contains("-")) {
				int range = Integer.parseInt(text.split("-")[1]);
				text = String.valueOf(RandomStringUtils.randomNumeric(range));
			} else {
				throw new SeleniumExceptions("Please enter a range");
			}
		} else if (text.toLowerCase().contains("randemail")) {
			text = RandomStringUtils.randomAlphabetic(6) + "@mailinator.com";
		} else if (text.toLowerCase().contains("startdate")) {
			text = randBetween(2018, 2019);
		} else if (text.toLowerCase().contains("enddate")) {
			text = randBetween(2020, 2022);
		} else if (text.toLowerCase().contains("pastdate")) {
			text = randBetween(1970, 2000);
		}
		if (mapToStoreVariable.containsKey(text)) {
			text = mapToStoreVariable.get(text);
		}
		try {
			if (wait.until(ExpectedConditions.visibilityOfElementLocated(by)).isDisplayed()) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(by)).clear();
				wait.until(ExpectedConditions.visibilityOfElementLocated(by)).sendKeys(text);
			}
		} catch (Exception e) {
			throw new SeleniumExceptions("element with locator : '" + by + "' is not a textbox");
		}
		
	}

	private  void enterTextByScript(By by, String text) throws SeleniumExceptions {
		if (text.equalsIgnoreCase("randstring")) {
			text = RandomStringUtils.randomAlphabetic(6);
		} else if (text.toLowerCase().contains("randint")) {
			if (text.contains("-")) {
				int range = Integer.parseInt(text.split("-")[1]);
				text = String.valueOf(RandomStringUtils.randomNumeric(range));
			} else {
				throw new SeleniumExceptions("Please enter a range");
			}
		} else if (text.toLowerCase().contains("randemail")) {
			text = RandomStringUtils.randomAlphabetic(6) + "@mailinator.com";
		} else if (text.toLowerCase().contains("startdate")) {
			text = randBetween(2018, 2019);
		} else if (text.toLowerCase().contains("enddate")) {
			text = randBetween(2020, 2022);
		} else if (text.toLowerCase().contains("pastdate")) {
			text = randBetween(1970, 2000);
		}
		if (mapToStoreVariable.containsKey(text)) {
			text = mapToStoreVariable.get(text);
		}
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].value='" + text + "';", driver.findElement(by));
	}

	public  String randBetween(int start, int end) {
		String date = "";
		GregorianCalendar gc = new GregorianCalendar();
		int year = start + (int) Math.round(Math.random() * (end - start));
		gc.set(GregorianCalendar.YEAR, year);
		int dayOfYear = 1 + (int) Math.round(Math.random() * (gc.getActualMaximum(GregorianCalendar.DAY_OF_YEAR) - 1));
		gc.set(GregorianCalendar.DAY_OF_YEAR, dayOfYear);
		if (gc.get(GregorianCalendar.DAY_OF_MONTH) < 10 && (gc.get(GregorianCalendar.MONTH) + 1) < 10) {
			date = "0" + gc.get(GregorianCalendar.DAY_OF_MONTH) + "/0" + (gc.get(GregorianCalendar.MONTH) + 1) + "/"
					+ gc.get(GregorianCalendar.YEAR);
		} else if (gc.get(GregorianCalendar.DAY_OF_MONTH) < 10 && (gc.get(GregorianCalendar.MONTH) + 1) > 10) {
			date = "0" + gc.get(GregorianCalendar.DAY_OF_MONTH) + "/" + (gc.get(GregorianCalendar.MONTH) + 1) + "/"
					+ gc.get(GregorianCalendar.YEAR);
		} else if (gc.get(GregorianCalendar.DAY_OF_MONTH) > 10 && (gc.get(GregorianCalendar.MONTH) + 1) < 10) {
			date = gc.get(GregorianCalendar.DAY_OF_MONTH) + "/0" + (gc.get(GregorianCalendar.MONTH) + 1) + "/"
					+ gc.get(GregorianCalendar.YEAR);
		} else if (gc.get(GregorianCalendar.DAY_OF_MONTH) > 10 && (gc.get(GregorianCalendar.MONTH) + 1) > 10) {
			date = gc.get(GregorianCalendar.DAY_OF_MONTH) + "/" + (gc.get(GregorianCalendar.MONTH) + 1) + "/"
					+ gc.get(GregorianCalendar.YEAR);
		}
		return date;
	}

	private void wait(String testdata) {
		if (testdata.contains(".")) {
			testdata = testdata.split("\\.")[0];
		}
		int miliSeconds = Integer.parseInt(testdata) * 1000;
		try {
			Thread.currentThread().sleep(miliSeconds);
		} catch (InterruptedException e) {
			LOG.error(e.getMessage());
			Thread.currentThread().interrupt();
		}
	}

	private  void waitForClick(By by) throws SeleniumExceptions {
		try {
			if(driver.toString().contains("Chrome")) {
				WebElement ele = wait.until(ExpectedConditions.elementToBeClickable(by));
				JavascriptExecutor js = (JavascriptExecutor) getWebDriver();
				js.executeScript("arguments[0].click();", ele);
			}
			else{
				wait.until(ExpectedConditions.elementToBeClickable(by)).click();
			}
		} catch (Exception e) {
			throw new SeleniumExceptions("element with value : '" + by + "' is not clicked" + e.getMessage());
		}
	}

	private  void goBack() {
		driver.navigate().back();
	}

	private  void goForward() {
		driver.navigate().forward();
	}

	private  void refreshBrowser() {
		driver.navigate().refresh();
	}

	private  void executeScript(By by, String query) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(query, driver.findElement(by));
	}
	
	private  void getDataFromDataBase(String query) {
		listToStoreDBValidation.clear();
		val.clear();
		Statement stmt;
		String data = null;
		DataInputStream in;
		ResultSet rs = null;
		BufferedReader br;
		try {
			if (DatabaseConnection.getCon() != null) {
				stmt = DatabaseConnection.getCon().createStatement();
				if (query.contains("``") && !(query.isEmpty())) {
					data = query.split("``")[1];
					if (mapToStoreVariable.containsKey(data)) {
						data = mapToStoreVariable.get(data);
					}
					query = query.split("``")[0];
				}
				if (query.contains(".sql") || query.contains(".SQL")) {
					in = new DataInputStream(
							new FileInputStream(System.getProperty("user.dir") + "\\SQLQueries" + "\\" + query));
					br = new BufferedReader(new InputStreamReader(in));
					String strLine;
					StringBuilder sb = new StringBuilder();
					while ((strLine = br.readLine()) != null) {
						if (strLine.contains("{argument}")) {
							strLine = strLine.replaceAll("\\{argument}", data);
						}
						sb.append(strLine + "\n ");
					}
					br.close();
					rs = stmt.executeQuery(sb.toString());
				}
				if (query.contains("query{")) {
					query = query.replace("query{", "").replace("}", "").replace("{", "");
					if (query.contains("argument")) {
						query = query.replaceAll("argument", data);
						rs = stmt.executeQuery(query);
						
					}
				}
				if (rs != null) {
					ResultSetMetaData metadata = rs.getMetaData();
					int columncount = metadata.getColumnCount();
					while (rs.next()) {
						for (int i = 1; i <= columncount; i++) {
							String columnName = metadata.getColumnName(i);
							int type = metadata.getColumnType(i);
							if (type == Types.VARCHAR || type == Types.CHAR||type==Types.LONGVARCHAR) {
								if(rs.getString(columnName)!=null) {
									val.put(columnName, rs.getString(columnName));
								}
								else {
									val.put(columnName, "");
								}
								
							} 
							else if (type == Types.INTEGER || type == Types.TINYINT || type == Types.SMALLINT) {
								if(String.valueOf(rs.getInt(columnName))!=null) {
									val.put(columnName, String.valueOf(rs.getInt(columnName)));
								}
								else {
									val.put(columnName, "");
								}
							}

							else if (type == Types.BOOLEAN) {
								val.put(columnName, String.valueOf(rs.getBoolean(columnName)));
							}

							else if (type == Types.DOUBLE || type == Types.FLOAT) {
								if(String.valueOf(rs.getDouble(columnName))!=null) {
									val.put(columnName, String.valueOf(rs.getDouble(columnName)));
								}
								else {
									val.put(columnName, "");
								}
							}
							
							else if (type == Types.DECIMAL) {
								if(String.valueOf(rs.getBigDecimal(columnName))!=null) {
									val.put(columnName, String.valueOf(rs.getBigDecimal(columnName)));
								}
								else {
									val.put(columnName, "");
								}
							}
							
							else if (type == Types.NULL) {
								val.put(columnName, String.valueOf(rs.getString(columnName)));
							}
							
							else if (type == Types.DATE||type == Types.TIMESTAMP) {
								Timestamp timestamp = rs.getTimestamp(columnName);
								if (timestamp != null) {
									val.put(columnName, String.valueOf(timestamp.getDate()));
								}
								
								if (timestamp == null) {
									val.put(columnName, String.valueOf(""));
								} 
								
								else {
									val.put(columnName, String.valueOf(rs.getDate(columnName).toString()));
								}
								    
							}
							
						}
					}
				} else {
					throw new DatabaseExceptions("Query return empty result");
				}
			} else {
				throw new DatabaseExceptions("Database connection not created");
			}
		} catch (IOException | SQLException | DatabaseExceptions e) {
			LOG.error(e.getMessage());
		}
	}

	private  void getData(String testdata) {
		mapToStoreVariable.put(testdata.split("``")[0], val.get(testdata.split("``")[1]));
	}

	private  void getDataForDBValidation(By by, String testdata) throws SeleniumExceptions {
		if (testdata.contains("``")) {
			if (wait.until(ExpectedConditions.visibilityOfElementLocated(by))
					.getAttribute(testdata.split("``")[1]) != null) {
				listToStoreDBValidation.put(testdata.split("``")[0],
						wait.until(ExpectedConditions.visibilityOfElementLocated(by))
								.getAttribute(testdata.split("``")[1]).trim());				
			} else if (testdata.split("``")[1].equalsIgnoreCase("text")) {
				listToStoreDBValidation.put(testdata.split("``")[0],
						wait.until(ExpectedConditions.visibilityOfElementLocated(by)).getText());
			} else if (testdata.split("``")[1].equalsIgnoreCase("dropdownvalue")) {
				listToStoreDBValidation.put(testdata.split("``")[0],
						new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(by)))
								.getFirstSelectedOption().getAttribute("value"));
			} else if (testdata.split("``")[1].equalsIgnoreCase("dropdowntext")) {
				listToStoreDBValidation.put(testdata.split("``")[0],
						new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(by)))
								.getFirstSelectedOption().getText());
			}

			else if (testdata.split("``")[1].equalsIgnoreCase("dropdownindex")) {

				List<WebElement> list = new Select(waitForExpectedElement(by)).getOptions();
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getText()
							.equals(new Select(waitForExpectedElement(by)).getFirstSelectedOption().getText())) {
						listToStoreDBValidation.put(testdata.split("``")[0], String.valueOf(i));
						break;
					}
				}
			}
			
			else if (testdata.split("``")[1].equalsIgnoreCase("click")) {
				listToStoreDBValidation.put(testdata.split("``")[0],"1");
			}
			
			else if (testdata.split("``")[1].equalsIgnoreCase("noclick")) {
				listToStoreDBValidation.put(testdata.split("``")[0],"0");
			}
			
			else {
				throw new SeleniumExceptions("Attribute '" + testdata.split("``")[1] + "' is not valid for the element");
			}
		}
		
		else {
			throw new SeleniumExceptions("Please enter the Database column to be matched or the attribute to be matched");
		}
	}

	private  WebElement waitForExpectedElement(By by) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	private  void compareWithDB() throws SeleniumExceptions {	
		if(!(val.isEmpty())) {
			for (String key : listToStoreDBValidation.keySet()) {
				if (val.containsKey(key)
						&& val.get(key).equalsIgnoreCase(listToStoreDBValidation.get(key))) {

				} else if (val.containsKey(key)
						&& !(val.get(key).equalsIgnoreCase(listToStoreDBValidation.get(key)))) {
					throw new SeleniumExceptions("Database column '"+key+"' with value '" + val.get(key)
							+ " does not matches with portal value '" + listToStoreDBValidation.get(key)+"'");
				}

				else if (!(val.containsKey(key))) {
					throw new SeleniumExceptions("Field '"+key+"' does not exist in DB query");
				}

			}
		}
		else {
			throw new SeleniumExceptions(Thread.currentThread().getId() + "empty map from DB");
		}
		
	}
	
	private  void fileUpload(By by,String fileName) throws AWTException {
		String workingDir = System.getProperty("user.dir");
		String filePath = workingDir + "\\FileUpload" + File.separator + fileName;
		if(driver.toString().contains("phantom")) {
			filePath= filePath.replace("\\", "/");
			((PhantomJSDriver)getWebDriver()).executePhantomJS("var page = this; page.uploadFile('input[type=file]','"+filePath+"');");
		}
		else{
//			filePath= filePath.replace("\\", "/");
//			((InternetExplorerDriver)getWebDriver()).executeScript("var page = this; page.uploadFile('input[type=file]','"+filePath+"');");
//			final String jacobdllarch = System.getProperty("sun.arch.data.model").contains("32") ? "jacob-1.18-x86.dll"
//					: "jacob-1.18-x64.dll";
//			String jacobdllpath = workingDir + "\\lib" + File.separator + jacobdllarch;
//			File filejacob = new File(jacobdllpath);
//			System.setProperty(LibraryLoader.JACOB_DLL_PATH, filejacob.getAbsolutePath());
//			AutoItX uploadWindow = new AutoItX();
//			System.out.println(filePath);
//			if (uploadWindow.winWaitActive("Choose File to Upload", "", 5)
//					&& uploadWindow.winExists("Choose File to Upload")) {
//				uploadWindow.sleep(100);
//				uploadWindow.send(filePath);
//				uploadWindow.controlClick("Choose File to Upload", "&Open", "1");
//			}
//			StringSelection ss = new StringSelection(filePath);
//			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
//			Robot robot = new Robot();
//			robot.keyPress(KeyEvent.VK_CONTROL);
//			robot.keyPress(KeyEvent.VK_V);
//			robot.keyRelease(KeyEvent.VK_V);
//			robot.keyRelease(KeyEvent.VK_CONTROL);
//			robot.keyPress(KeyEvent.VK_ENTER);
//			robot.keyRelease(KeyEvent.VK_ENTER);
			driver.findElement(by).sendKeys(new File(filePath).getAbsolutePath());
		}
		
	}
	
	private  void readfile(String fileName) throws IOException {
		BufferedReader br;
		DataInputStream in;
		String workingDir = System.getProperty("user.dir");
		String filePath = workingDir + "\\FileUpload" + File.separator + fileName;
		in = new DataInputStream(new FileInputStream(filePath));
		br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		StringBuilder sb = new StringBuilder();
		while ((strLine = br.readLine()) != null) {
			sb.append(strLine + "\n");
		}
		mapToStoreVariable.put(fileName, sb.toString().trim());
		br.close();
	}
}
