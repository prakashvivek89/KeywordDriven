package com.SSPWorldWide.Framework.Adviser.Helper;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class TakeScreenshotUtility{
	public static String captureScreenshot(WebDriver driver, String screenshotName) {
		try {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(source, new File(Launcher.currDir + File.separator + "Screenshots" + File.separator + screenshotName + ".png"));
			
		} catch (Exception e) {
			System.out.println("Exception while taking screenshot " + e.getMessage());
		}
		return new File(System.getProperty("user.dir")+"/Report/" + screenshotName + ".png").getAbsolutePath();
	}
}
