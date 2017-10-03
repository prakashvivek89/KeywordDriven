package com.SSPWorldWide.Framework.Adviser.Helper;

import org.openqa.selenium.By;

import com.SSPWorldWide.Framework.Adviser.ReadExcel.ReadObjectRepo;

public class FindWebELement {

	public static By getWebElement(String ele) throws Exception {
		By by = null;
		String[] type_value = ReadObjectRepo.data.get(ele.toLowerCase()).split("\\|");
		String type = type_value[0].toLowerCase();
		String value = type_value[1];
		switch (type) {
		case "id":
			by = By.id(value);
			break;
		case "classname":
			by = By.className(value);
			break;
		case "name":
			by = By.name(value);
			break;
		case "linktext":
			by = By.linkText(value);
			break;
		case "xpath":
			by = By.xpath(value);
			break;
		case "css":
			by = By.cssSelector(value);
			break;
		default:
			throw new Exception("Please enter a type");
		}
		return by;
	}
	

}
