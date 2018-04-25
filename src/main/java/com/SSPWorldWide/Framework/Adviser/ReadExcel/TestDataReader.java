package com.SSPWorldWide.Framework.Adviser.ReadExcel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.SSPWorldWide.Framework.Adviser.Exceptions.FrameworkExceptions;
import com.SSPWorldWide.Framework.Adviser.Helper.Constants;
import com.SSPWorldWide.Framework.Adviser.Helper.CopyFiles;

public class TestDataReader extends ExcelReusables{
	
	private static TestDataReader tdreader;
	public static Map<String, Map<String, String>> testdataMap = new HashedMap<>();
	
	
	private TestDataReader(){
	}
	
	public static TestDataReader getInstance() {
		if(tdreader==null) {
			tdreader = new TestDataReader();
		}
		return tdreader;
	}
	
	public void getTData(String excelName) throws Exception {
		Workbook workbook = readFile("testData", excelName);
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
		}
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			try {
			Sheet sheet = workbook.getSheetAt(i);
			String sheetName = sheet.getSheetName();
			Row keyRow = sheet.getRow(0);
			List<String> key = new ArrayList<>();
			if (keyRow != null) {
				for (int j = 2; j < keyRow.getLastCellNum(); j++) {
					key.add(getCellValueString(keyRow.getCell(j)).trim());
				}
				Row valueRow;
				for (int k = 1; k <=sheet.getLastRowNum(); k++) {
					List<String> value = new ArrayList<>();
					valueRow = sheet.getRow(k);
					if (valueRow != null) {
					for (int j = 2; j <=keyRow.getLastCellNum(); j++) {
							value.add(getCellValueString(valueRow.getCell(j)).trim());
						}
					
					Map<String, String> testdata = new HashedMap<>();
					for (int l = 0; l < key.size(); l++) {
						testdata.put(key.get(l), value.get(l));
					}
					testdataMap.put(excelName.toLowerCase() + "|" + sheetName.toLowerCase() + "|" + k, testdata);
					}
				}
			}
		}
			catch (Exception e) {
				throw new Exception(workbook.getSheetAt(i).getSheetName()+"    failure     " + e.getMessage());
			}
		}
		
	}
	
	
	public void readAllFiles() throws Exception {
		File excelFileToRead = new File(Constants.COPIEDFILESDIR.toString()+File.separator+"testData");
		File[] files = excelFileToRead.listFiles();
		for (File f : files) {
			getTData(FilenameUtils.removeExtension(f.getName()));
		}
	}
//	
	public static void main(String[] args) throws Exception {
		CopyFiles.copyFileForRegression();
		getInstance().readAllFiles();
		String key = "Login_Testdata|Login_Credentials|1";
		key = key.split("\\|")[0] +"|"+ key.split("\\|")[1]+"|" + key.split("\\|")[2];
		System.out.println(key);
		if(testdataMap.get(key).keySet().contains("LoginName")) {System.out.println(testdataMap.get(key).get("LoginName"));
		}
		else {
			System.err.println("fail");
		}
	}
	
}
