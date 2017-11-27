package com.VP.Framework.Adviser.ReadExcel;

import com.VP.Framework.Adviser.Helper.CommonKeywords;

public class TestcaseFlow {

	public static void runSingleTest(String suiteName, String testcaseID) throws Exception {
//		ReadTestcaseFile.testcases_persuite.clear();		
		CommonKeywords.mapToStoreVariable.clear();
//		ReadTestcaseFile.readTestcaseFile(suiteName);
		for (String testSteps : ReadTestcaseFile.getTestCaseSteps(testcaseID)) {
			String testdata[] = null;
			for (String steps : testSteps.split(",")) {
				
				/* if test data is present in the testcase*/
				
				if (steps.contains("##")) {
					testdata = steps.split("##")[1].split("\\|");
				
					/* if test data contains the range */
					
					if (testdata[2].contains("-")) {
						int startRange = Integer.parseInt(testdata[2].split("-")[0]);
						int endRange = Integer.parseInt(testdata[2].split("-")[1]);
						for (int i = startRange; i <= endRange; i++) {
							testdata[2] = String.valueOf(i);
							ReadProjectReusables.getActionSteps1(steps.split("##")[0], testdata);
						}
					} 
					/* if test data does not contains the range */
					else {
						ReadProjectReusables.getActionSteps1(steps.split("##")[0], testdata);
					}
				} else {
					ReadProjectReusables.getActionSteps1(steps.trim(), testdata);
				}
			}
		}
	}
}
