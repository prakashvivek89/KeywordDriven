package com.SSPWorldWide.Framework.Adviser.ReadExcel;

import java.io.File;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import org.apache.commons.io.FilenameUtils;

public class Read_Testcase_File extends ExcelReusables {
	public static ListMultimap<String, String> testcases_persuite = ArrayListMultimap.create();

	public static List<String> getTestCaseSteps(String testcaseID) throws Exception {
		if (!(testcases_persuite.get(testcaseID).isEmpty())) {
			return testcases_persuite.get(testcaseID);
		} else {
			throw new Exception("Testcase ID '"+testcaseID+"' does not exist");
		}
	}

	public static void readTestcaseFile(String suiteName) throws Exception {
		Workbook workbook = null;
		String FilePath = System.getProperty("user.dir") + "/src/test/resources/testcases";
		File ExcelFileToRead = new File(FilePath);
		File[] files = ExcelFileToRead.listFiles();
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
					"Entered Testsuite : " + suiteName + " does not exist, please check the name of testsuite");
		}
		Sheet sheet = workbook.getSheetAt(0);
		int totalNumber_Row = sheet.getLastRowNum();
		for (int i = 1; i <= totalNumber_Row; i++) {
			if (!(isRowEmpty(sheet.getRow(i)))) {
				if (!(getCellValueString(sheet.getRow(i).getCell(1)).isEmpty())) {
					if (testcases_persuite.containsKey(sheet.getRow(i).getCell(1).toString())) {
						throw new Exception("Testestcase '" + sheet.getRow(i).getCell(1).toString()
								+ "' already exist in the suite '" + suiteName + "'");
					} else {
						for (int j = i; j <= totalNumber_Row; j++) {
							if (!(isRowEmpty(sheet.getRow(j)))) {
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

	public static void main(String[] args) throws Exception {
//		executeAllTestSuites();
//		String testSuiteName = "Login_TestSuite";
//		String testCaseID = "Login_001";
//		readTestcaseFile(testSuiteName);
//		for (String s : getTestCaseSteps(testCaseID)) {
//			String testdata[] = null;
//			for (String ss : s.split(",")) {
//				if (ss.contains("##")) {
//					testdata = ss.split("##")[1].split("\\|");
//					System.out.println(ss.split("##")[0]);
//					Read_ProjectReusables.getActionSteps(ss.split("##")[0], testdata);
//				} else {
//					System.out.println(ss.trim());
//					Read_ProjectReusables.getActionSteps(ss.trim(), testdata);
//				}
//			}
//		}
	}

	public static void executeAllTestSuites() throws Exception {
		String FilePath = System.getProperty("user.dir") + "/src/test/resources/testcases";
		File ExcelFileToRead = new File(FilePath);
		File[] files = ExcelFileToRead.listFiles();
		for (File f : files) {
			testcases_persuite.clear();
//			System.out.println(FilenameUtils.removeExtension(f.getName()));
			readTestcaseFile(FilenameUtils.removeExtension(f.getName()));
			for (String TCid : testcases_persuite.keySet()) {
//				System.out.println("testcase ID :" + TCid);
				for (String ss : getTestCaseSteps(TCid)) {
					String testdata[] = null;
					for (String sss : ss.split(",")) {
						if (sss.contains("##")) {
							testdata = sss.split("##")[1].split("\\|");
							Read_ProjectReusables.getActionSteps1(sss.split("##")[0], testdata);
						} else {
							Read_ProjectReusables.getActionSteps1(sss.trim(), testdata);
						}
					}
				}
			}
		}
	}
}
