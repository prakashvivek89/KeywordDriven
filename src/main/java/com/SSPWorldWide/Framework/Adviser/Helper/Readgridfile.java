package com.SSPWorldWide.Framework.Adviser.Helper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.SSPWorldWide.Framework.Adviser.ReadExcel.ExcelReusables;

public class Readgridfile extends ExcelReusables {
	protected static Map<String, String> gridvalues = new HashedMap<>();
	
	private static Readgridfile readGrid;
	
	private Readgridfile(){
	}
	
	public static Readgridfile getInstance() {
		if(readGrid==null) {
			readGrid= new Readgridfile();
		}
		return readGrid;
	}

	public void getbrowser() throws InvalidFormatException, IOException {
		String fileloc = System.getProperty("user.dir") + File.separator +"Grid" + File.separator + "Grid.xlsx";
		File excelFileToRead = new File(fileloc);
		Workbook grid = new XSSFWorkbook(excelFileToRead);
		Sheet sheet = grid.getSheetAt(0);
		int totalnumberrow = sheet.getLastRowNum();
		for (int i = 3; i <= totalnumberrow; i++) {
			try {
				if (!(isRowEmpty(sheet.getRow(i))) && !(getCellValueString(sheet.getRow(i).getCell(1)).isEmpty())) {
					gridvalues.put(sheet.getRow(i).getCell(1).toString(), sheet.getRow(i).getCell(2).toString());
				}
			} catch (Exception e) {

			}
		}
		grid.close();
	}
}
