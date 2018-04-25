package com.SSPWorldWide.Framework.Adviser.Testcases;

import org.testng.annotations.Test;
import com.SSPWorldWide.Framework.Adviser.ReadExcel.*;
public class Activities_Module extends TestcaseFlow{
 @Test(description = "Personal Client - add/edit/delete Contact")
 public void Activities_Module_001() throws Exception{
runSingleTest("Activities_Module.001");
}
 @Test(description = "Personal Client - Contact Tracking - Add/Edit/delete Group")
 public void Activities_Module_003() throws Exception{
runSingleTest("Activities_Module.003");
}
 @Test(description = "Corporate Client - add/edit/delete Contact")
 public void Activities_Module_002() throws Exception{
runSingleTest("Activities_Module.002");
}
 @Test(description = "Corporate Client - Advice Event - Complete An Item")
 public void Activities_Module_005() throws Exception{
runSingleTest("Activities_Module.005");
}
 @Test(description = "Corporate Client - Contact Tracking - Add/Edit/delete Item")
 public void Activities_Module_004() throws Exception{
runSingleTest("Activities_Module.004");
}
 @Test(description = "Personal Client - Advice Event - attach an document")
 public void Activities_Module_007() throws Exception{
runSingleTest("Activities_Module.007");
}
 @Test(description = "Personal Client - Advice Event - Abandone An Item")
 public void Activities_Module_006() throws Exception{
runSingleTest("Activities_Module.006");
}
}
