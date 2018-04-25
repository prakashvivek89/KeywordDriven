package com.VP.Framework.Adviser.ReadExcel;

import java.io.File;
import java.util.ArrayList;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.VP.Framework.Adviser.Helper.CommonKeywords;
import com.VP.Framework.Adviser.Helper.Constants;
import com.VP.Framework.Adviser.Helper.FindWebELement;
import com.VP.Framework.Adviser.Helper.Reporting;
import com.aventstack.extentreports.Status;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

public class ReadProjectReusables extends ExcelReusables {
	static ListMultimap<String, String> ActionsPerMethod = ArrayListMultimap.create();
	public static String methodName;
	
	public static ListMultimap<String, String> getMethodActions(String sheetName) throws Exception {
		Workbook workbook = null;
		String FilePath =Constants.COPIEDFILESDIR.toString() + "\\projectResuables";
		String excelName = "ProjectReusables";
		File ExcelFileToRead = new File(FilePath);
		File[] files = ExcelFileToRead.listFiles();
		if ((new File(FilePath, (excelName.trim() + ".xlsx")).exists())
				|| (new File(FilePath, (excelName.trim() + ".xls")).exists())) {
			for (File f : files) {
				if ((excelName.trim() + ".xlsx").equalsIgnoreCase(f.getName())) {
					workbook = new XSSFWorkbook(f);
				} else if (f.getName().equalsIgnoreCase(excelName + ".xls")) {
					workbook = new HSSFWorkbook(new POIFSFileSystem(ExcelFileToRead));
				}
			}
		} else {
			throw new Exception("Project Reusable workbook : " + excelName + " does not exist, please check!!");
		}
		ActionsPerMethod.clear();
		ArrayList<String> sheetsInWorkBook = new ArrayList<String>();
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			sheetsInWorkBook.add(workbook.getSheetAt(i).getSheetName().toString().toLowerCase());
		}
		if (sheetsInWorkBook.contains(sheetName.toLowerCase())) {
			Sheet sheet = workbook.getSheet(sheetName);
			int totalNumber_Row = sheet.getLastRowNum();
			for (int i = 1; i <= totalNumber_Row; i++) {
				if (!(isRowEmpty(sheet.getRow(i)))) {
					if (!(getCellValueString(sheet.getRow(i).getCell(0)).isEmpty())) {
						if (ActionsPerMethod.containsKey(sheet.getRow(i).getCell(0).toString())) {
							throw new Exception("duplicate methodName  : " + sheet.getRow(i).getCell(0).toString());
						} else {
							for (int j = i; j <= totalNumber_Row; j++) {
								if (!(isRowEmpty(sheet.getRow(j)))) {
									String actionWithElement = getCellValueString(sheet.getRow(j).getCell(1)) + "##"
											+ getCellValueString(sheet.getRow(j).getCell(2)) + "##"
											+ getCellValueString(sheet.getRow(j).getCell(3));
									ActionsPerMethod.put(getCellValueString(sheet.getRow(i).getCell(0)),
											actionWithElement);
								} else {
									break;
								}
							}
						}
					}
				}
			}
		} else {
			throw new Exception("Sheet : '" + sheetName + "' does not exist in project reusable workbook.");
		}
		workbook.close();
		return ActionsPerMethod;
	}
	
	public static void getActionSteps1(String actionStepsWithSheetName, String[] testdata) throws Exception {
		String sheetName = actionStepsWithSheetName.split("\\.")[0];
		methodName = actionStepsWithSheetName.split("\\.")[1];
		String data = "";
		for (String step : getMethodActions(sheetName).get(methodName)) {
			
			/*       Testdata is present       */
			
			if (step.split("##").length > 2) {
				if (step.split("##")[2].contains("|")) {
					/*       store the value in variable    */
					
					data = step.split("##")[2].replace("|", ",");
				}
				
				/*       Test data is passed from test case sheet  */
				
				else if (step.split("##")[2].contains("{$[td]")&&!(step.split("##")[2].contains("|"))) {
					String key = step.split("##")[2];
					key = key.substring(6, key.length() - 1);
					if(testdata.length>0) {
						data = TestDataReader.getTData(testdata[0], testdata[1], Integer.parseInt(testdata[2])).get(key);
					}
					else {
						data = key;
					}
				}
				else {
					data = step.split("##")[2].trim();
				}

				if (!(step.split("##")[1].trim().isEmpty())) {
					CommonKeywords.getWebelementAction(step.split("##")[0].trim(), FindWebELement.getWebElement(step.split("##")[1].trim(), data), data);
				} else {
					CommonKeywords.getWebelementAction(step.split("##")[0].trim(), null, data);
				}
			} else if (step.split("##").length > 1) {
				CommonKeywords.getWebelementAction(step.split("##")[0].trim(), FindWebELement.getWebElement(step.split("##")[1].trim(), data), data);
			} else {
				CommonKeywords.getBrowserAction(step.split("##")[0].trim());
			}
		}
		Reporting.test.log(Status.PASS, methodName);
	}
	
	

	public static void getActionSteps(String actionStepsWithSheetName, String[] testdata) throws Exception {
		String sheetName = actionStepsWithSheetName.split("\\.")[0];
		methodName = actionStepsWithSheetName.split("\\.")[1];
		for (String s : getMethodActions(sheetName).get(methodName)) {
			String data = "";
//			if testdata is present
			if (s.split("##").length > 2) {
				if (s.split("##")[2].contains("|")) {
					data = s.split("##")[2].replace("|", ",");
				}
				else if (s.split("##")[2].contains("{$[td]")&&!(s.split("##")[2].contains("|"))) {
					String key = s.split("##")[2];
					key = key.substring(6, key.length() - 1);
					if(testdata.length>0) {
						if(TestDataReader.getTData(testdata[0], testdata[1], Integer.parseInt(testdata[2])).containsKey(key)) {
							data = TestDataReader.getTData(testdata[0], testdata[1], Integer.parseInt(testdata[2])).get(key);
						}
						else {
							throw new Exception("Coulumn '"+key+"' does not exist in "+testdata[0]);
						}
					}
					else {
						data = key;
					}
				}
				else {
					data = s.split("##")[2].trim();
				}

				if (!(s.split("##")[1].trim().isEmpty())) {
					CommonKeywords.getWebelementAction(s.split("##")[0].trim(), FindWebELement.getWebElement(s.split("##")[1].trim(), data), data);
				} else {
					CommonKeywords.getWebelementAction(s.split("##")[0].trim(), null, data);
				}
			} else if (s.split("##").length > 1) {
				CommonKeywords.getWebelementAction(s.split("##")[0].trim(), FindWebELement.getWebElement(s.split("##")[1].trim(), data), data);
			} else {
				CommonKeywords.getBrowserAction(s.split("##")[0].trim());
			}
		}
		Reporting.test.log(Status.PASS, methodName);
	}

}
