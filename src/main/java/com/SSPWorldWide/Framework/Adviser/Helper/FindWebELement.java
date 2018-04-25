package com.SSPWorldWide.Framework.Adviser.Helper;

import org.openqa.selenium.By;

import com.SSPWorldWide.Framework.Adviser.Exceptions.FrameworkExceptions;
import com.SSPWorldWide.Framework.Adviser.ReadExcel.ReadObjectRepo;
import com.SSPWorldWide.Framework.Adviser.ReadExcel.TestcaseFlow;

/**
 * This class locates all the web element in the web page.
 */

public class FindWebELement extends TestcaseFlow{

	public By getWebElement(String ele, String testdata) throws Exception {
		By by = null;
		if (ReadObjectRepo.objectrepo.containsKey(ele.toLowerCase())) {
			if (ReadObjectRepo.objectrepo.get(ele.toLowerCase()) != null) {
				String[] typeandvalue = ReadObjectRepo.objectrepo.get(ele.toLowerCase()).split("\\|");
				String type = typeandvalue[0].toLowerCase();
				String value = typeandvalue[1];
				
				/*  Code for dynamic locator  */
				
				if (value.contains("{$argument}")) {
					String[] first = value.split("\\{");
					String[] second = first[1].split("\\}");
					if (second.length > 1) {
//						if(comKey.mapToStoreVariable.containsKey(testdata)){
//							testdata = comKey.mapToStoreVariable.get(testdata);
//						}
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
					throw new FrameworkExceptions("Please enter a type");
				}
				return by;
			} else {
				throw new FrameworkExceptions("Please check type '" + ReadObjectRepo.objectrepo.get(ele.toLowerCase()).split("\\|")[0]
						+ "' and value '" + ReadObjectRepo.objectrepo.get(ele.toLowerCase()).split("\\|")[1]
						+ "' in the object repository");
			}
		} else {
			throw new FrameworkExceptions("Webelement '" + ele.toLowerCase() + "' does not exist in the object repository");
		}
	}
}
