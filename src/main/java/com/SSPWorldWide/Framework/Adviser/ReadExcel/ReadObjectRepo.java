package com.SSPWorldWide.Framework.Adviser.ReadExcel;

import java.io.IOException;
import java.util.Map;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import com.SSPWorldWide.Framework.Adviser.Exceptions.FrameworkExceptions;

/**
 * This class reads the object repository file.
 */

public class ReadObjectRepo extends ExcelReusables {
	public static Map<String, String> objectrepo = new HashedMap<>();

	private static ReadObjectRepo readOR;

	private ReadObjectRepo() {
	}

	public static ReadObjectRepo getInstance() {
		if (readOR == null) {
			readOR = new ReadObjectRepo();
		}
		return readOR;
	}
	
	public void getORData() throws FrameworkExceptions, InvalidFormatException, IOException {
		Workbook workbook = readFile("objectRepo", "ObjectRepo");
		Sheet sheet = workbook.getSheetAt(0);
		int totalnumberrow = sheet.getLastRowNum();
		for (int i = 1; i <= totalnumberrow; i++) {
			if (!(isRowEmpty(sheet.getRow(i)))) {
				if (!(objectrepo.containsKey(sheet.getRow(i).getCell(0).toString().toLowerCase() + "|" + sheet.getRow(i).getCell(1).toString().toLowerCase()))) {
					objectrepo.put(sheet.getRow(i).getCell(0).toString().toLowerCase() + "|" + sheet.getRow(i).getCell(1).toString().toLowerCase()
							, sheet.getRow(i).getCell(2).toString().toLowerCase() + "|"	+ sheet.getRow(i).getCell(3).toString());
				} else {
					throw new FrameworkExceptions("Duplicate combination of parent '" + sheet.getRow(i).getCell(0).toString()
							+ "' and object '" + sheet.getRow(i).getCell(1).toString() + "'");
				}
			}
		}
		workbook.close();
	}	
}
