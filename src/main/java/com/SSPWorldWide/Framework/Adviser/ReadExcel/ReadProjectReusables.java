package com.SSPWorldWide.Framework.Adviser.ReadExcel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.xmlbeans.impl.piccolo.util.DuplicateKeyException;
import com.SSPWorldWide.Framework.Adviser.Exceptions.FrameworkExceptions;
import com.SSPWorldWide.Framework.Adviser.Exceptions.MissingFileException;
import com.SSPWorldWide.Framework.Adviser.Helper.CommonKeywords;
import com.SSPWorldWide.Framework.Adviser.Helper.FindWebELement;
import com.SSPWorldWide.Framework.Adviser.Helper.Reporting;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

/**
 * This class reads all the reusable methods and performs action as mentioned in
 * methods.
 */

public class ReadProjectReusables extends ExcelReusables {

	public static ListMultimap<String, String> actionsPerMethod = ArrayListMultimap.create();
	public static ArrayList<String> sheetsInWorkBook = new ArrayList<String>();
	public static Map<Integer, String> failuremethodName = new HashMap<Integer, String>();
	private static ReadProjectReusables readPR;

	private ReadProjectReusables() {
	}

	public static ReadProjectReusables getInstance() {
		if (readPR == null) {
			readPR = new ReadProjectReusables();
		}
		return readPR;
	}

	public void getMethodActions() throws Exception {
		Workbook workbook = ExcelReusables.readFile("projectResuables", "ProjectReusables");
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			sheetsInWorkBook.add(workbook.getSheetAt(i).getSheetName().toString());
		}
		for (String sheetName : sheetsInWorkBook) {
			Sheet sheet = workbook.getSheet(sheetName);
			int totalnumberow = sheet.getLastRowNum();
			for (int i = 1; i <= totalnumberow; i++) {
				if (!(isRowEmpty(sheet.getRow(i)))
						&& !(getCellValueString(sheet.getRow(i).getCell(0)).isEmpty())) {
					if (actionsPerMethod.containsKey(
							sheetName.toLowerCase() + "." + sheet.getRow(i).getCell(0).toString().toLowerCase())) {
						throw new DuplicateKeyException(
								"duplicate methodName  : " + sheet.getRow(i).getCell(0).toString());
					} else {
						for (int j = i; j <= totalnumberow; j++) {
							if (!(isRowEmpty(sheet.getRow(j)))) {
								String actionWithElement = getCellValueString(sheet.getRow(j).getCell(1))
										+ "##" + getCellValueString(sheet.getRow(j).getCell(2)) + "##"
										+ getCellValueString(sheet.getRow(j).getCell(3));
								actionsPerMethod.put(
										sheetName.toLowerCase() + "." + getCellValueString(sheet.getRow(i).getCell(0)).toLowerCase(),
										actionWithElement);
							} else {
								break;
							}
						}
					}
				}
			}

		}
		workbook.close();
		for(int i=0,l=sheetsInWorkBook.size();i<l;++i)
		{
			sheetsInWorkBook.set(i, sheetsInWorkBook.get(i).toLowerCase());
		}
	}

	public void getActionSteps(String actionStepsWithSheetName, String[] testdata, String testcaseID) throws Exception {
		FindWebELement findWE = new FindWebELement();
		CommonKeywords comKey = new CommonKeywords();
		String sheetName = actionStepsWithSheetName.split("\\.")[0].toLowerCase();
		String	methodName = actionStepsWithSheetName.split("\\.")[1];
		actionStepsWithSheetName = actionStepsWithSheetName.toLowerCase();
		failuremethodName.put((int) (long) (Thread.currentThread().getId()), methodName);
		comKey.mapToStoreVariable.clear();
		String data = "";
		if (sheetsInWorkBook.contains(sheetName)) {
			if (actionsPerMethod.containsKey(actionStepsWithSheetName)) {
				for (String step : actionsPerMethod.get(actionStepsWithSheetName)) {
					/* Testdata is present */
					if (step.split("##").length > 2) {
						if (step.split("##")[2].contains("|")) {
							/* store the value in variable */
							data = step.split("##")[2].replace("|", "``");
						}

						/* Test data is passed from test case sheet */

						else if (step.split("##")[2].contains("{$[td]") && !(step.split("##")[2].contains("|"))) {
							String key = step.split("##")[2];
							key = key.substring(6, key.length() - 1).trim();
							if (testdata.length > 0) {
								if (TestDataReader.testdataMap.get(testdata[0].toLowerCase()+"|"+ testdata[1].toLowerCase()+"|"+Integer.parseInt(testdata[2]))
										.containsKey(key)) {
									data = TestDataReader.testdataMap.get(testdata[0].toLowerCase()+"|"+ testdata[1].toLowerCase()+"|"+Integer.parseInt(testdata[2])).get(key);
								} else {
									throw new FrameworkExceptions("Key '" + key
											+ "'is not present in the testdata sheet'" + testdata[1] + "'");
								}
							} else if (testdata.length == 0) {
								throw new FrameworkExceptions("Please enter the data in testcase sheet");
							}
						} else {
							data = step.split("##")[2].trim();
						}

						if (!(step.split("##")[1].trim().isEmpty())) {
							comKey.getWebelementAction(step.split("##")[0].trim(),
									findWE.getWebElement(step.split("##")[1].trim(), data), data);
						} else {
							comKey.getWebelementAction(step.split("##")[0].trim(), null, data);
						}
					} else if (step.split("##").length > 1) {
						comKey.getWebelementAction(step.split("##")[0].trim(),
								findWE.getWebElement(step.split("##")[1].trim(), data), data);
					} else {	
						comKey.getBrowserAction(step.split("##")[0].trim());
					}
				}
//				if(Reporting.extentTestMap.get((int) (long) (Thread.currentThread().getId())).getModel().getName().contains(testcaseID)) {
					Reporting.extentTestMap.get((int) (long) (Thread.currentThread().getId())).log(Status.PASS, methodName);
//				}
			} else {
				throw new FrameworkExceptions("sheet '" + sheetName + "' does not contains the method '" + methodName + "'.");
			}
		} else {
			throw new MissingFileException("Sheet : '" + sheetName + "' does not exist in project reusable workbook.");
		}
	}
}
