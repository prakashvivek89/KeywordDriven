package com.SSPWorldWide.Framework.Adviser.Helper;

import org.openqa.selenium.By;

import com.SSPWorldWide.Framework.Adviser.ReadExcel.ReadObjectRepo;

public class FindWebELement {

	public static By getWebElement(String ele, String testdata) throws Exception {
		By by = null;
		if (ReadObjectRepo.data.containsKey(ele.toLowerCase())) {
			if (ReadObjectRepo.data.get(ele.toLowerCase()) != null) {
				String[] type_value = ReadObjectRepo.data.get(ele.toLowerCase()).split("\\|");
				String type = type_value[0].toLowerCase();
				String value = type_value[1];
				
				/*  Code for dynamic locator  */
				if (value.contains("{$argument}")) {
					String[] first = value.split("\\{");
					String[] second = first[1].split("\\}");
					if (second.length > 1) {
						value = first[0] + testdata + second[1];
					} else {
						value = first[0] + testdata;
					}
				}
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
			} else {
				throw new Exception("Please check type '" + ReadObjectRepo.data.get(ele.toLowerCase()).split("\\|")[0]
						+ "' and value '" + ReadObjectRepo.data.get(ele.toLowerCase()).split("\\|")[1]
						+ "' in the object repository");
			}
		} else {
			throw new Exception("Webelement '" + ele.toLowerCase() + "' does not exist in the object repository");
		}
	}
}
