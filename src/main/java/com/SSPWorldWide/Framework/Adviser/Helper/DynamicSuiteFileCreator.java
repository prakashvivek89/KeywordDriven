package com.SSPWorldWide.Framework.Adviser.Helper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.io.FilenameUtils;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import com.SSPWorldWide.Framework.Adviser.ReadExcel.ReadTestcaseFile;
import com.SSPWorldWide.Framework.Adviser.Helper.Constants;
import com.SSPWorldWide.Framework.Adviser.Helper.DynamicClassCreator;

/**
 * This class creates the dynamic testng XMLs for each excel test suite. Once
 * dynamic testng XMLs are created it executes them.
 */

public class DynamicSuiteFileCreator {

	public static XmlSuite createXMLSuite(String suiteName, String tcID) throws Exception {
		Map<String, String> browser = new HashedMap<>();
		browser.put("browser", WebdriverHelper.browserConfig);
		XmlSuite suite = new XmlSuite();
		suite.setName(suiteName);
		String className = suiteName;
		suite.setParameters(browser);
		XmlClass packageName = null;
		if (tcID.contains(",")) {
			for (String id : tcID.split(",")) {
				id = id.replace(".", "_").trim();
				writeXML(suite, id, packageName, className);
			}
		} else {
			tcID = tcID.replace(".", "_").trim();
			writeXML(suite, tcID, packageName, className);
		}
		return suite;
	}

	public static Map<String, XmlSuite> createRegressionXML() throws Exception {
		File ExcelFileToRead = new File(Constants.EXCELTESTCASEPATH.toString());
		File[] files = ExcelFileToRead.listFiles();
		Map<String, XmlSuite> regressionSuites = new HashMap<String, XmlSuite>();
		for (File f : files) {
			Map<String, String> browser = new HashedMap<>();
			String className = FilenameUtils.removeExtension(f.getName());
			if(Readgridfile.gridvalues.containsKey(className)) {
				browser.put("browser", Readgridfile.gridvalues.get(className));
			}
			XmlSuite suite = new XmlSuite();
			suite.setName(className);
			suite.setParameters(browser);
			XmlClass packageName = null;
			for (String TCid : ReadTestcaseFile.testcases_persuite.keySet()) {
				if(className.equalsIgnoreCase(TCid.split("\\.")[0])) {
					writeXML(suite, className, TCid, packageName, regressionSuites);
				}
			}
			browser.clear();
		}
		return regressionSuites;
	}
	
	public static XmlSuite createFinalRegressionxml() throws IOException {
		File singleXML = new File(Constants.DYNAMICXMLDIR.toString());
		File[] files = singleXML.listFiles();
		List<String> path = new ArrayList<>();
		XmlSuite suite = new XmlSuite();
		for (File f : files) {
			path.add(f.getAbsolutePath());		
		}
		suite.setName("All Suites");
		suite.setSuiteFiles(path);
		FileWriter writer = new FileWriter(new File(System.getProperty("user.dir") + "\\src\\main\\resources\\dynamicXML", "regression" + ".xml"));
		writer.write(suite.toXml());
		writer.flush();
		writer.close();
		return suite;
	}
	
	public static Map<String, XmlSuite> createSmokeXML() throws Exception {
		File ExcelFileToRead = new File(Constants.EXCELTESTCASEPATH.toString());
		File[] files = ExcelFileToRead.listFiles();
		Map<String, XmlSuite> regressionSuites = new HashMap<String, XmlSuite>();
		for (File f : files) {
			Map<String, String> browser = new HashedMap<>();
			String className = FilenameUtils.removeExtension(f.getName());
			if(Readgridfile.gridvalues.containsKey(className)) {
				browser.put("browser", Readgridfile.gridvalues.get(className));
			}
			XmlSuite suite = new XmlSuite();
			suite.setName(className);
			suite.setParameters(browser);
			XmlClass packageName = null;
			for (String TCid : ReadTestcaseFile.testcases_persuite.keySet()) {
				if (ReadTestcaseFile.smokeTestCases.get(TCid) != null
						&& ReadTestcaseFile.smokeTestCases.get(TCid).equalsIgnoreCase("smoke")&&(className.equalsIgnoreCase(TCid.split("\\.")[0]))) {
					writeXML(suite, className, TCid, packageName, regressionSuites);
				}
			}
		}
		return regressionSuites;
	}

	public static Map<String, XmlSuite> createSingleTestSuiteRegressionXML(String testSuiteName) throws Exception {
		Map<String, XmlSuite> regressionSuites = new HashMap<String, XmlSuite>();
		Map<String, String> browser = new HashedMap<>();
		browser.put("browser", WebdriverHelper.browserConfig);
		if (testSuiteName.contains(",")) {
			for (String suiteName : testSuiteName.split(",")) {
				XmlSuite suite = new XmlSuite();
				suiteName = suiteName.trim();
				String className = suiteName;
				suite.setName(className);
				suite.setParameters(browser);
				XmlClass packageName = null;
				for (String TCid : ReadTestcaseFile.testcases_persuite.keySet()) {
					if(className.equalsIgnoreCase(TCid.split("\\.")[0])){
						writeXML(suite, className, TCid, packageName, regressionSuites);
					}
				}
			}
		} else {
			XmlSuite suite = new XmlSuite();
			String className = testSuiteName;
			suite.setName(className);
			suite.setParameters(browser);
			XmlClass packageName = null;
			
			
			for (String TCid : ReadTestcaseFile.testcases_persuite.keySet()) {
				if(className.equalsIgnoreCase(TCid.split("\\.")[0])){
					writeXML(suite, className, TCid, packageName, regressionSuites);
				}
			}
		}
		return regressionSuites;
	}

	private static void writeXML(XmlSuite suite, String className, String TCid, XmlClass packageName,
			Map<String, XmlSuite> regressionSuites) throws IOException {
		XmlTest test = new XmlTest(suite);
		TCid = TCid.replace(".", "_").trim();
		test.setName(TCid);
		
		List<XmlClass> classes = new ArrayList<XmlClass>();
		packageName = new XmlClass(Constants.PACKAGENAME.toString() + className);
		XmlInclude method = null;
		List<XmlInclude> inmethods = new ArrayList<XmlInclude>();
		method = new XmlInclude(TCid);
		inmethods.add(method);
		packageName.setIncludedMethods(inmethods);
		classes.add(packageName);
		test.setXmlClasses(classes);
		regressionSuites.put(className, suite);
		FileWriter writer = new FileWriter(new File(Constants.DYNAMICXMLDIR.toString(), className + ".xml"));
		writer.write(suite.toXml());
		writer.flush();
		writer.close();
	}

	private static void writeXML(XmlSuite suite, String TCid, XmlClass packageName, String className)
			throws IOException {
		XmlTest test = new XmlTest(suite);
		TCid = TCid.replace(".", "_").trim();
		test.setName(TCid);
		List<XmlClass> classes = new ArrayList<XmlClass>();
		packageName = new XmlClass(Constants.PACKAGENAME.toString() + className);
		XmlInclude method = null;
		List<XmlInclude> inmethods = new ArrayList<XmlInclude>();
		method = new XmlInclude(TCid);
		inmethods.add(method);
		packageName.setIncludedMethods(inmethods);
		classes.add(packageName);
		test.setXmlClasses(classes);
		FileWriter writer = new FileWriter(new File(Constants.DYNAMICXMLDIR.toString(), className + ".xml"));
		writer.write(suite.toXml());
		writer.flush();
		writer.close();
	}

	public static void runTestNG(XmlSuite suite) {
		TestNG tng = new TestNG();
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		suites.add(suite);
		tng.setXmlSuites(suites);
		TestListenerAdapter adapter = new TestListenerAdapter();
		tng.addListener(adapter);
	    tng.setParallel("parallel");
	    tng.setSuiteThreadPoolSize(2);
		tng.run();
	}

	public static void runSingleTestCase(String testSuiteName, String testcaseID) throws Exception {
		DynamicClassCreator.createSingleTestCaseClass(testSuiteName, testcaseID);
		DynamicClassCreator.makeDirectory(Constants.DYNAMICXMLDIR.toString());
		XmlSuite suite = createXMLSuite(testSuiteName, testcaseID);
		runTestNG(suite);
	}

	public static void runSingleSuite(String testSuiteName) throws Exception {
		DynamicClassCreator.createSingleSuiteClasse(testSuiteName);
		DynamicClassCreator.makeDirectory(Constants.DYNAMICXMLDIR.toString());
		createSingleTestSuiteRegressionXML(testSuiteName);
		XmlSuite suite = createFinalRegressionxml();
		runTestNG(suite);
	}

	public static void runRegressionSuite() throws Exception {
		DynamicClassCreator.createRegressionSuiteClasses();
		DynamicClassCreator.makeDirectory(Constants.DYNAMICXMLDIR.toString());
		createRegressionXML();
		XmlSuite suite = createFinalRegressionxml();
		runTestNG(suite);
	}

	public static void runSmokeSuite() throws Exception {
		DynamicClassCreator.createSmokeSuiteClasses();
		DynamicClassCreator.makeDirectory(Constants.DYNAMICXMLDIR.toString());
		createSmokeXML();
		XmlSuite suite = createFinalRegressionxml();
		runTestNG(suite);
	}

	public static void main(String[] args) throws Exception {
		runRegressionSuite();
	}
}