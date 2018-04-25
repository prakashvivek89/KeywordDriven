package com.SSPWorldWide.Framework.Adviser.ReadExcel;

import com.SSPWorldWide.Framework.Adviser.Helper.WebdriverHelper;

public class TestcaseFlow extends WebdriverHelper{
	
	public void runSingleTest(String testcaseID) throws Exception {
		ReadTestcaseFile.testcases_persuite.get(testcaseID).size();
		for (String testSteps : ReadTestcaseFile.testcases_persuite.get(testcaseID)) {
			String testdata[] = new String[0];
			for (String steps : testSteps.split(",")) {
				/* if test data is present in the testcase */
				if (steps.contains("##")) {
					testdata = steps.split("##")[1].split("\\|");

					/* if test data contains the range */

					if (testdata[2].contains("-")) {
						int startRange = Integer.parseInt(testdata[2].split("-")[0]);
						int endRange = Integer.parseInt(testdata[2].split("-")[1]);
						for (int i = startRange; i <= endRange; i++) {
							testdata[2] = String.valueOf(i);
							ReadProjectReusables.getInstance().getActionSteps(steps.split("##")[0], testdata, testcaseID);
						}
					}
					/* if test data does not contains the range */
					else {
						ReadProjectReusables.getInstance().getActionSteps(steps.split("##")[0], testdata, testcaseID);
					}
				} else {
					ReadProjectReusables.getInstance().getActionSteps(steps.trim(), testdata, testcaseID.replace(".", "_"));
				}
			}
		}
	}
}