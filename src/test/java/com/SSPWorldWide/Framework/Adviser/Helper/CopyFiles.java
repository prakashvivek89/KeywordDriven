package com.SSPWorldWide.Framework.Adviser.Helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;

public class CopyFiles {

	public static void copyFileForRegression() throws Exception {
		copyProjectReusable();
		copyObjectRepo();
		copyTestData();
		copyTestsuiteFolder();
	}

	public static void copySingleFile(String suiteName) throws Exception {
		copyProjectReusable();
		copyObjectRepo();
		copyTestData();
		copySinlgeTestcase(suiteName);
	}

	public static void deleteFlder() throws IOException {
		FileUtils.cleanDirectory(new File(System.getProperty("user.dir") + "/src/test/resources/CopiedFiles"));
	}

	private static void copyProjectReusable() throws Exception {
		File srcFolder = new File(System.getProperty("user.dir") + "/ProjectAutomationFiles/projectResuables");
		File destFolder = new File(System.getProperty("user.dir") + "/src/test/resources/CopiedFiles/projectResuables");
		copyFolder(srcFolder, destFolder);
	}

	private static void copyObjectRepo() throws Exception {
		File srcFolder = new File(System.getProperty("user.dir") + "/ProjectAutomationFiles/objectRepo");
		File destFolder = new File(System.getProperty("user.dir") + "/src/test/resources/CopiedFiles/objectRepo");
		copyFolder(srcFolder, destFolder);
	}

	private static void copyTestData() throws Exception {
		File srcFolder = new File(System.getProperty("user.dir") + "/ProjectAutomationFiles/testData");
		File destFolder = new File(System.getProperty("user.dir") + "/src/test/resources/CopiedFiles/testData");
		copyFolder(srcFolder, destFolder);
	}

	private static void copyTestsuiteFolder() throws Exception {
		File srcFolder = new File(System.getProperty("user.dir") + "/ProjectAutomationFiles/testcases");
		File destFolder = new File(System.getProperty("user.dir") + "/src/test/resources/CopiedFiles/testcases");
		copyFolder(srcFolder, destFolder);
	}

	private static void copySinlgeTestcase(String suiteName) throws Exception {
		if(suiteName.contains(",")) {
			for(String suite:suiteName.split(",")) {
				suite = suite.trim();
				File srcFolder = new File(System.getProperty("user.dir") + "/ProjectAutomationFiles/testcases");
				File destFolder = new File(System.getProperty("user.dir") + "/src/test/resources/CopiedFiles/testcases");
				copyFolder(srcFolder, destFolder, suite);
			}
		}
		else {
			File srcFolder = new File(System.getProperty("user.dir") + "/ProjectAutomationFiles/testcases");
			File destFolder = new File(System.getProperty("user.dir") + "/src/test/resources/CopiedFiles/testcases");
			copyFolder(srcFolder, destFolder, suiteName);
		}
	}

	private static void copyFolder(File src, File dest, String suiteName) throws Exception {
		if (!dest.exists()) {
			dest.mkdir();
		}
		if ((new File(src, suiteName.trim() + ".xlsx")).exists()&&!(suiteName.contains("~"))) {
			File srcf = new File(src, suiteName + ".xlsx");
			FileUtils.copyFile(srcf, new File(dest + File.separator + suiteName + ".xlsx"));
		} else if (new File(src, suiteName.trim() + ".xls").exists()&&!(suiteName.contains("~"))) {
			File srcf = new File(src, suiteName + ".xls");
			FileUtils.copyFile(srcf, new File(dest + File.separator + suiteName + ".xls"));
		}
	}

	private static void copyFolder(File src, File dest) throws Exception {

		if (src.isDirectory()) {
			if (!dest.exists()) {
				dest.mkdir();
			}
			String files[] = src.list();
			for (String file : files) {
				if (!(file.toString().contains("~"))) {
					File srcFile = new File(src, file);
					File destFile = new File(dest, file);
					copyFolder(srcFile, destFile);
				}
			}
		} else {
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int length;
			// copy the file content in bytes
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			in.close();
			out.close();
		}
	}
}
