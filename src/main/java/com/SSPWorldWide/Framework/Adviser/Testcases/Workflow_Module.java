package com.SSPWorldWide.Framework.Adviser.Testcases;

import org.testng.annotations.Test;
import com.SSPWorldWide.Framework.Adviser.ReadExcel.*;
public class Workflow_Module extends TestcaseFlow{
 @Test(description = "Personal Client - Add/Edit/delete Group")
 public void Workflow_Module_001() throws Exception{
runSingleTest("Workflow_Module.001");
}
 @Test(description = "Personal Client - Abondon An Item")
 public void Workflow_Module_004() throws Exception{
runSingleTest("Workflow_Module.004");
}
 @Test(description = "Personal Client - Attach and view document")
 public void Workflow_Module_005() throws Exception{
runSingleTest("Workflow_Module.005");
}
 @Test(description = "Corporate Client - Add/Edit/delete Item")
 public void Workflow_Module_002() throws Exception{
runSingleTest("Workflow_Module.002");
}
 @Test(description = "Corporate Client - Complete An Item")
 public void Workflow_Module_003() throws Exception{
runSingleTest("Workflow_Module.003");
}
}
