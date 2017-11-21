package com.VP.Framework.Adviser.ReadExcel;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.VP.Framework.Adviser.Helper.WebdriverHelper;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

public class Read_Testcase_File extends ExcelReusables {
	public static ListMultimap<String, String> testcases_persuite = ArrayListMultimap.create();
	public static Map<String, String> scenarioNames = new HashMap<String, String>();
	public static Map<String, String> smokeTestCases = new HashMap<String, String>();

	public static List<String> getTestCaseSteps(String testcaseID) throws Exception {
		if (!(testcases_persuite.get(testcaseID).isEmpty())) {
			return testcases_persuite.get(testcaseID);
		} else {
			throw new Exception("Testcase ID '" + testcaseID + "' does not exist");
		}
	}

	/* Reads the excel file*/
	
	public static void readTestcaseFile(String suiteName) throws Exception {
		Workbook workbook = null;
		String FilePath = System.getProperty("user.dir") + "/src/test/resources/CopiedFiles/testcases";
		File ExcelFileToRead = new File(FilePath);
		File[] files = ExcelFileToRead.listFiles();
		if (!(suiteName.toString().contains("~"))) {
			if ((new File(FilePath, (suiteName.trim() + ".xlsx")).exists())
					|| (new File(FilePath, (suiteName.trim() + ".xls")).exists())) {
				for (File f : files) {
					if ((suiteName.trim() + ".xlsx").equalsIgnoreCase(f.getName())) {
						workbook = new XSSFWorkbook(f);
					} else if (f.getName().equalsIgnoreCase(suiteName + ".xls")) {
						workbook = new HSSFWorkbook(new POIFSFileSystem(ExcelFileToRead));
					}
				}
			} else {
				throw new Exception(
						"Entered Module : " + suiteName + " does not exist, please check the name of Module");
			}
			Sheet sheet = workbook.getSheetAt(0);
			int totalNumber_Row = sheet.getLastRowNum();
			for (int i = 1; i <= totalNumber_Row; i++) {
				if (!(isRowEmpty(sheet.getRow(i)))) {
					if (!(getCellValueString(sheet.getRow(i).getCell(1)).isEmpty())) {
						if (testcases_persuite.containsKey(sheet.getRow(i).getCell(1).toString())) {
							throw new Exception("Testestcase '" + sheet.getRow(i).getCell(1).toString()
									+ "' already exist in the module '" + suiteName + "'");
						} else {
							for (int j = i; j <= totalNumber_Row; j++) {
								if (!(isRowEmpty(sheet.getRow(j)))) {
									scenarioNames.put(getCellValueString(sheet.getRow(i).getCell(1)),
											getCellValueString(sheet.getRow(i).getCell(2)));									
									if (WebdriverHelper.runSmoke.equalsIgnoreCase("yes")) {
										if (getCellValueString(sheet.getRow(j).getCell(0)).equalsIgnoreCase("smoke")) {
											smokeTestCases.put(getCellValueString(sheet.getRow(j).getCell(1)),
													getCellValueString(sheet.getRow(j).getCell(0)));
										}
									}
									if ((getCellValueString(sheet.getRow(j).getCell(5)).isEmpty())) {
										testcases_persuite.put(getCellValueString(sheet.getRow(i).getCell(1)),
												getCellValueString(sheet.getRow(j).getCell(4)));
									} else {
										testcases_persuite.put(getCellValueString(sheet.getRow(i).getCell(1)),
												getCellValueString(sheet.getRow(j).getCell(4)) + "##"
														+ getCellValueString(sheet.getRow(j).getCell(5)));
									}
								} else {
									break;
								}
							}
						}
					}
				}
			}
			workbook.close();
		}
	}
}
