package com.SSPWorldWide.Framework.Adviser.Testcases;

import org.testng.annotations.Test;
import com.SSPWorldWide.Framework.Adviser.ReadExcel.*;
public class PolicyValuation_Module extends TestcaseFlow{
 @Test(description = "Personal Client - Add Valuation")
 public void PolicyValuation_Module_001() throws Exception{
runSingleTest("PolicyValuation_Module.001");
}
 @Test(description = "Corporate Client - Group Policy - Add Valuation")
 public void PolicyValuation_Module_003() throws Exception{
runSingleTest("PolicyValuation_Module.003");
}
 @Test(description = "Corporate Client - Add Valuation")
 public void PolicyValuation_Module_002() throws Exception{
runSingleTest("PolicyValuation_Module.002");
}
}
