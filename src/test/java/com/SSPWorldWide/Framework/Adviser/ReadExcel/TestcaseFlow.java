package com.SSPWorldWide.Framework.Adviser.ReadExcel;

public class TestcaseFlow {
	
	public static void runSingleTest(String suiteName, String testcaseID) throws Exception {
		Read_Testcase_File.testcases_persuite.clear();
		Read_Testcase_File.readTestcaseFile(suiteName);
		for (String s : Read_Testcase_File.getTestCaseSteps(testcaseID)) {
			String testdata [] = null;
			for (String ss : s.split(",")) {
				if(ss.contains("##")) {
					testdata = ss.split("##")[1].split("\\|");
					Read_ProjectReusables.getActionSteps1(ss.split("##")[0], testdata);
				}
				else{
					Read_ProjectReusables.getActionSteps1(ss.trim(), testdata);
				}
			}
		}
	}
}
