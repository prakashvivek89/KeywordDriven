package com.SSPWorldWide.Framework.Adviser.Helper;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class CommonKeywords extends WebdriverHelper {
	private static final long DRIVER_WAIT_TIME = 10;
	protected static WebDriverWait wait = null;

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

		case "validatelementnotpresent":
			elementIsNotPresent(by);
			break;

		case "currenturlcontains":
			currentURLContains(testdata);
			break;

		case "gettextandmatch":
			getTextAndMatch(by, testdata);
			break;

		case "waitforelementtodisappear":
			waitforelementtodisappear(by);
			break;

		case "":
			throw new Exception("Please enter an action");

		default:
			throw new Exception("Action is not defined");
		}
	}

	private static void waitforelementtodisappear(By by) {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
	}

	private static void getTextAndMatch(By by, String text) {
			Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(by)).getText(), text);
	}

	private static void currentURLContains(String URL) {
		Assert.assertTrue(getWebDriver().getCurrentUrl().contains(URL));
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
			if (text.contains("\\-")) {
				int range = Integer.parseInt(text.split("")[1]);
				text = RandomStringUtils.randomNumeric(range);
			} else {
				throw new Exception("Please enter a range");
			}
		}
		if (text.isEmpty()) {
			throw new Exception("Please enter the test data");
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(by)).clear();
		wait.until(ExpectedConditions.visibilityOfElementLocated(by)).sendKeys(text);
	}

	private static void waitForClick(By by) {
		wait.until(ExpectedConditions.elementToBeClickable(by)).click();
	}

	public static void getBrowserAction(String action) throws Exception {
		switch (action.toLowerCase()) {

		case "navigateto":
			navigateToURL("http://adviser11test/Portal/");
			break;

		case "closedriver":
			closeCurrentBrowser();
			break;

		case "quitdriver":
			closeBrowser();
			break;

		default:
			throw new Exception("Please enter an action");
		}
	}
}
