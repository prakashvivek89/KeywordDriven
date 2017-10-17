package com.SSPWorldWide.Framework.Adviser.ReadExcel;

import java.io.File;
import java.util.Map;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadObjectRepo extends ExcelReusables {
	public static Map<String, String> data = new HashedMap<String, String>();

	public static Map<String, String> getORData() throws Exception {
		Workbook workbook = null;
		String FilePath = System.getProperty("user.dir") + "/ProjectAutomationFiles/objectRepo";
		String excelName = "ObjectRepo";
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
			throw new Exception("Object Repository does not exist");
		}
		Sheet sheet = workbook.getSheetAt(0);
		data.clear();
		int totalNumber_Row = sheet.getLastRowNum();
		for (int i = 1; i < totalNumber_Row; i++) {
			if (!(isRowEmpty(sheet.getRow(i)))) {
				if (!(data.containsKey(
						sheet.getRow(i).getCell(0).toString() + "|" + sheet.getRow(i).getCell(1).toString()))) {
					data.put(
							sheet.getRow(i).getCell(0).toString().toLowerCase() + "|"
									+ sheet.getRow(i).getCell(1).toString().toLowerCase(),
							sheet.getRow(i).getCell(2).toString().toLowerCase() + "|"
									+ sheet.getRow(i).getCell(3).toString());
				} else {
					throw new Exception("Duplicate combination of parent '" + sheet.getRow(i).getCell(0).toString()
							+ "' and object '" + sheet.getRow(i).getCell(1).toString() + "'");
				}
			}
		}
		workbook.close();
		return data;
	}
	public static String getTypeAndValue(String element) throws Exception {
		System.out.println("elemetnName :   " + element + "     value:  " + getORData().get(element.toLowerCase()));
		return data.get(element);
	}
}
