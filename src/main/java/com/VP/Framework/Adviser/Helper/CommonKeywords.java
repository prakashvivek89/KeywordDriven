package com.VP.Framework.Adviser.Helper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.VP.Framework.Adviser.Helper.WebdriverHelper;
import com.jacob.com.LibraryLoader;
import autoitx4java.AutoItX;

/**
 * This class contains all the Selenium methods
 * @author vivek.prakash
 */

public class CommonKeywords extends WebdriverHelper {
	private static final long DRIVER_WAIT_TIME = 10;
	protected static WebDriverWait wait = null;
	public static Map<String, String> mapToStoreVariable = new HashMap<String, String>();
	private static String parentWindowHandle = null;

	public static void getWebelementAction(String action, By by, String testdata) throws Exception {
		switch (action.toLowerCase()) {
		case "entertext":
			waitForExpectedAndEnterText(by, testdata);
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

		case "clear":
			clear(by);
			break;

		case "selectitem":
			selectItem(by, testdata);
			break;

		case "executescript":
			executeScript(by, testdata);
			break;

		case "fileupload":
			fileUpload(testdata);
			break;

		case "wait":
			wait(testdata);
			break;
			
		case "verifytextcontainedinlist":
			verifyTextContainedInList(by, testdata);
			
		case "verifytextpresentinlist":
			verifyTextPresentInList(by, testdata);

		case "":
			throw new Exception("Please enter an action");

		default:
			throw new Exception("Action : '" + action + "' is not defined");
		}
	}

	public static void getBrowserAction(String action) throws Exception {
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

		default:
			throw new Exception("Please enter an action");
		}
	}

	private static void switchToNextTab() {
			parentWindowHandle = getWebDriver().getWindowHandle();
			getWebDriver().getWindowHandles();
			Set<String> windowHandles = getWebDriver().getWindowHandles();
			for (String window : windowHandles) {
				if (!(window.equals(parentWindowHandle))) {
					getWebDriver().switchTo().window(window);
					getWebDriver().switchTo().defaultContent();
				}
			}
	}

	private static void switchToPreviousTab() {
		getWebDriver().findElement(By.tagName("html")).sendKeys(Keys.CONTROL, Keys.PAGE_UP);
		getWebDriver().switchTo().defaultContent();
	}

	private static void selectItem(By by, String testdata) {
		Select sel = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(by)));
		sel.selectByVisibleText(testdata);
	}

	private static void clear(By by) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(by)).clear();
	}

	private static void waitforelementtodisappear(By by) {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
	}

	private static void getTextAndMatch(By by, String text) {
		Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(by)).getText(), text);
	}

	private static void verifyTextNotContained(By by, String testdata) throws Exception {
		if (testdata.contains(wait.until(ExpectedConditions.visibilityOfElementLocated(by)).getText()))
			;
		else {
			throw new Exception("'" + testdata + "' contains '"
					+ wait.until(ExpectedConditions.visibilityOfElementLocated(by)).getText() + "'");
		}
	}
	
	private static void verifyTextPresentInList(By by, String data) throws Exception {
		List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));	
		List<String> text = new ArrayList<>();
		for(int i = 0; i<elements.size();i++) {
			text.add(elements.get(i).getText());
		}
		if(text.contains(data));
		else {
			throw new Exception(data + " is not present");
		}
	}
	
	private static void verifyTextContainedInList(By by, String data) throws Exception {
		List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
		for(int i = 0; i<elements.size();i++) {
			if(elements.get(i).getText().contains(data));
			else {
				throw new Exception(data + " is not present");
			}
		}
	}

	private static void noTextPresent(By by) throws Exception {
		if (wait.until(ExpectedConditions.visibilityOfElementLocated(by)).getAttribute("value").equalsIgnoreCase(""))
			;
		else if (wait.until(ExpectedConditions.visibilityOfElementLocated(by)).getText().equalsIgnoreCase(""))
			;
		else {
			throw new Exception(
					"Text '" + wait.until(ExpectedConditions.visibilityOfElementLocated(by)).getAttribute("value")
							+ "' is present");
		}
	}

	private static void textNotMatch(String testdata) {
		if ((mapToStoreVariable.get(testdata.split(",")[0]) != null)
				&& (mapToStoreVariable.get(testdata.split(",")[1]) != null)) {
			Assert.assertNotEquals(mapToStoreVariable.get(testdata.split(",")[0]),
					mapToStoreVariable.get(testdata.split(",")[1]));
		} else if ((mapToStoreVariable.get(testdata.split(",")[0]) == null)
				&& (mapToStoreVariable.get(testdata.split(",")[1]) != null)) {
			Assert.assertNotEquals(testdata.split(",")[0], mapToStoreVariable.get(testdata.split(",")[1]));
		} else if ((mapToStoreVariable.get(testdata.split(",")[0]) != null)
				&& (mapToStoreVariable.get(testdata.split(",")[1]) == null)) {
			Assert.assertNotEquals(mapToStoreVariable.get(testdata.split(",")[0]), testdata.split(",")[1]);
		} else if ((mapToStoreVariable.get(testdata.split(",")[0]) == null)
				&& (mapToStoreVariable.get(testdata.split(",")[1]) == null)) {
			Assert.assertNotEquals(testdata.split(",")[0], (testdata.split(",")[1]));
		}
	}

	private static void getText(By by, String testdata) {
		mapToStoreVariable.put(testdata, wait.until(ExpectedConditions.visibilityOfElementLocated(by)).getText());
	}

	private static void getAttribute(By by, String testdata) throws Exception {
		if (testdata.contains(",")) {
			if (wait.until(ExpectedConditions.visibilityOfElementLocated(by))
					.getAttribute(testdata.split(",")[1]) != null) {
				mapToStoreVariable.put(testdata.split(",")[0],
						wait.until(ExpectedConditions.visibilityOfElementLocated(by))
								.getAttribute(testdata.split(",")[1]).trim());
			} else if (testdata.split(",")[1].equalsIgnoreCase("text")) {
				mapToStoreVariable.put(testdata.split(",")[0],
						wait.until(ExpectedConditions.visibilityOfElementLocated(by)).getText());
			} else {
				throw new Exception("Attribute '" + testdata.split(",")[1] + "' is not valid for the element");
			}
		}
	}

	private static void textMatch(String testdata) {
		if ((mapToStoreVariable.get(testdata.split(",")[0]) != null)
				&& (mapToStoreVariable.get(testdata.split(",")[1]) != null)) {
			Assert.assertEquals(mapToStoreVariable.get(testdata.split(",")[0]),
					mapToStoreVariable.get(testdata.split(",")[1]));
		} else if ((mapToStoreVariable.get(testdata.split(",")[0]) == null)
				&& (mapToStoreVariable.get(testdata.split(",")[1]) != null)) {
			Assert.assertEquals(testdata.split(",")[0], mapToStoreVariable.get(testdata.split(",")[1]));
		}

		else if ((mapToStoreVariable.get(testdata.split(",")[0]) != null)
				&& (mapToStoreVariable.get(testdata.split(",")[1]) == null)) {
			Assert.assertEquals(mapToStoreVariable.get(testdata.split(",")[0]), testdata.split(",")[1]);
		}

		else if ((mapToStoreVariable.get(testdata.split(",")[0]) == null)
				&& (mapToStoreVariable.get(testdata.split(",")[1]) == null)) {
			Assert.assertEquals(testdata.split(",")[0], (testdata.split(",")[1]));
		}
	}

	private static void textPartialMatch(String testdata) throws Exception {
		if ((mapToStoreVariable.get(testdata.split(",")[0]) != null)
				&& (mapToStoreVariable.get(testdata.split(",")[1]) != null)) {
			if (mapToStoreVariable.get(testdata.split(",")[0])
					.contains(mapToStoreVariable.get(testdata.split(",")[1]))) {
			} else {
				throw new Exception("Text '" + mapToStoreVariable.get(testdata.split(",")[0])
						+ "' does not contains the text'" + mapToStoreVariable.get(testdata.split(",")[1]) + "'");
			}
		}

		else if ((mapToStoreVariable.get(testdata.split(",")[0]) == null)
				&& (mapToStoreVariable.get(testdata.split(",")[1]) != null)) {
			if (testdata.split(",")[0].contains(mapToStoreVariable.get(testdata.split(",")[1]))) {

			} else {
				throw new Exception("Text '" + testdata.split(",")[0] + "' does not contains the text'"
						+ mapToStoreVariable.get(testdata.split(",")[1]) + "'");
			}
		}

		else if ((mapToStoreVariable.get(testdata.split(",")[0]) != null)
				&& (mapToStoreVariable.get(testdata.split(",")[1]) == null)) {
			if (mapToStoreVariable.get(testdata.split(",")[0]).contains(testdata.split(",")[1])) {

			} else {
				throw new Exception("Text '" + mapToStoreVariable.get(testdata.split(",")[0])
						+ "' does not contains the text'" + testdata.split(",")[1] + "'");
			}
		}

		else if ((mapToStoreVariable.get(testdata.split(",")[0]) == null)
				&& (mapToStoreVariable.get(testdata.split(",")[1]) == null)) {
			if (testdata.split(",")[0].contains(testdata.split(",")[1])) {

			} else {
				throw new Exception("Text '" + testdata.split(",")[0] + "' does not contains the text'"
						+ testdata.split(",")[1] + "'");
			}
		}
	}

	private static void currentURLContains(String URL) throws Exception {
		if (getWebDriver().getCurrentUrl().contains(URL)) {

		} else if (getWebDriver().getCurrentUrl().contains(mapToStoreVariable.get(URL))) {
		} else {
			throw new Exception(URL + " is not present in the current URL '" + getWebDriver().getCurrentUrl() + "'");
		}
	}

	private static void elementIsNotPresent(By by) {
		Assert.assertTrue(wait.until(ExpectedConditions.invisibilityOfElementLocated(by)));
	}

	private static void elementIsPresent(By by) {
		Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(by)).isDisplayed());
	}

	private static void closeCurrentBrowser() {
		getWebDriver().close();
	}

	private static void navigateToURL(String URL) {
		getWebDriver().navigate().to(URL);
		getWebDriver().manage().window().maximize();
		wait = new WebDriverWait(getWebDriver(), DRIVER_WAIT_TIME);
	}

	private static void waitForExpectedAndEnterText(By by, String text) throws Exception {
		if (text.equalsIgnoreCase("randstring")) {
			text = RandomStringUtils.randomAlphabetic(6);
		} else if (text.toLowerCase().contains("randint")) {
			if (text.contains("-")) {
				int range = Integer.parseInt(text.split("-")[1]);
				text = String.valueOf(RandomStringUtils.randomNumeric(range));
			} else {
				throw new Exception("Please enter a range");
			}
		}
		else if(text.toLowerCase().contains("randemail")) {
			text = RandomStringUtils.randomAlphabetic(6) + "@mailinator.com";
		}
		if (mapToStoreVariable.containsKey(text)) {
			text = mapToStoreVariable.get(text);
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(by)).clear();
		wait.until(ExpectedConditions.visibilityOfElementLocated(by)).sendKeys(text);
	}

	private static void wait(String testdata) throws Exception {
		if(testdata.contains(".")) {
			testdata = testdata.split("\\.")[0];
		}
		int miliSeconds = Integer.parseInt(testdata) * 1000;
		Thread.sleep(miliSeconds);
	}

	private static void waitForClick(By by) throws Exception {
		wait.until(ExpectedConditions.elementToBeClickable(by)).click();
	}

	private static void goBack() {
		getWebDriver().navigate().back();
	}

	private static void goForward() {
		getWebDriver().navigate().forward();
	}

	private static void refreshBrowser() {
		getWebDriver().navigate().refresh();
	}

	private static void executeScript(By by, String query) {
		JavascriptExecutor js = (JavascriptExecutor) getWebDriver();
		js.executeScript(query, getWebDriver().findElement(by));
	}

	private static void fileUpload(String fileName) {
		String workingDir = System.getProperty("user.dir");
		final String jacobdllarch = System.getProperty("sun.arch.data.model").contains("32") ? "jacob-1.18-x86.dll"
				: "jacob-1.18-x64.dll";
		String jacobdllpath = workingDir + "\\lib" + "\\" + jacobdllarch;

		File filejacob = new File(jacobdllpath);
		System.setProperty(LibraryLoader.JACOB_DLL_PATH, filejacob.getAbsolutePath());
		AutoItX uploadWindow = new AutoItX();
		String filePath = workingDir + "\\FileUpload" + "\\" + fileName;
		if (uploadWindow.winWaitActive("Choose File to Upload", "", 5)) {
			if (uploadWindow.winExists("Choose File to Upload")) {
				uploadWindow.sleep(100);
				uploadWindow.send(filePath);
				uploadWindow.controlClick("Choose File to Upload", "&Open", "1");
			}
		}
	}

}
