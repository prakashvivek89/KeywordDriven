package com.SSPWorldWide.Framework.Adviser.ReadExcel;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.SSPWorldWide.Framework.Adviser.Helper.WebdriverHelper;
import com.aventstack.extentreports.Status;
import com.SSPWorldWide.Framework.Adviser.Helper.Constants;
import com.SSPWorldWide.Framework.Adviser.Helper.Reporting;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

public class ReadTestcaseFile extends ExcelReusables {
	public static ListMultimap<String, String> testcases_persuite = ArrayListMultimap.create();
	public static Map<String, String> scenarioNames = new HashMap<String, String>();
	public static Map<String, String> smokeTestCases = new HashMap<String, String>();
	private static final Logger LOG = LoggerFactory.getLogger(ReadTestcaseFile.class);

	private static ReadTestcaseFile readTC;

	private ReadTestcaseFile() {
	}

	public static ReadTestcaseFile getInstance() {
		if (readTC == null) {
			readTC = new ReadTestcaseFile();
		}
		return readTC;
	}
	
	public static List<String> getTestCaseSteps(String testcaseID) throws Exception {
		if (!(testcases_persuite.get(testcaseID).isEmpty())) {
			return testcases_persuite.get(testcaseID);
		} else {
			throw new Exception("Testcase ID '" + testcaseID + "' does not exist");
		}
	}
	
	/* Reads the excel file */

	private void readTestcaseFile(String suiteName) {
		try {
			Workbook workbook = readFile("testcases", suiteName);
			Sheet sheet = workbook.getSheetAt(0);
			int totalnumberrow = sheet.getLastRowNum();
			for (int i = 1; i <= totalnumberrow; i++) {
				try {
					if (!(isRowEmpty(sheet.getRow(i)))) {
						if (!(getCellValueString(sheet.getRow(i).getCell(1)).isEmpty())) {
							if (testcases_persuite.containsKey(sheet.getRow(i).getCell(1).toString())) {
								throw (new Exception("Testestcase '" + sheet.getRow(i).getCell(1).toString()
										+ "' already exist in the module '" + suiteName + "'"));
							} else {
								for (int j = i; j <= totalnumberrow; j++) {
									if (!(isRowEmpty(sheet.getRow(j)))) {
										scenarioNames.put(getCellValueString(sheet.getRow(i).getCell(1)),
												getCellValueString(sheet.getRow(i).getCell(2)));
										if (WebdriverHelper.runSmoke.equalsIgnoreCase("yes")
												&& (getCellValueString(sheet.getRow(j).getCell(0))
														.equalsIgnoreCase("smoke"))) {
											smokeTestCases.put(getCellValueString(sheet.getRow(j).getCell(1)),
													getCellValueString(sheet.getRow(j).getCell(0)));
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
				} catch (Exception e) {
					Reporting.createReport(suiteName,"");
					Reporting.createExtentTest(sheet.getRow(i).getCell(1).toString() + "&ensp;:&ensp;"
							+ sheet.getRow(i).getCell(2).toString());
					Reporting.extentTestMap.get((int) (long) (Thread.currentThread().getId())).log(Status.FAIL,
							suiteName + "<br />" + "<b>Failure reason :&emsp;</b>" + e.getMessage());
					Reporting.flushReport();
				}
			}
			workbook.close();
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}

	public void readAllFiles() {
		File excelFileToRead = new File(Constants.COPIEDFILESDIR.toString()+File.separator+"testcases");
		File[] files = excelFileToRead.listFiles();
		for (File f : files) {
			readTestcaseFile(FilenameUtils.removeExtension(f.getName()));
		}
	}
}
