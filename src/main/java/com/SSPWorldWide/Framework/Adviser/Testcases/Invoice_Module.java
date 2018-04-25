package com.SSPWorldWide.Framework.Adviser.Testcases;

import org.testng.annotations.Test;
import com.SSPWorldWide.Framework.Adviser.ReadExcel.*;
public class Invoice_Module extends TestcaseFlow{
 @Test(description = "Personal Client - add Invoice")
 public void Invoice_Module_001() throws Exception{
runSingleTest("Invoice_Module.001");
}
 @Test(description = "Corporate Client - add Invoice")
 public void Invoice_Module_002() throws Exception{
runSingleTest("Invoice_Module.002");
}
 @Test(description = "Corporate Client - edit/delete Invoice")
 public void Invoice_Module_003() throws Exception{
runSingleTest("Invoice_Module.003");
}
 @Test(description = "Personal Client - edit/delete Invoice")
 public void Invoice_Module_004() throws Exception{
runSingleTest("Invoice_Module.004");
}
 @Test(description = "Personal Client - Issue Invoice")
 public void Invoice_Module_005() throws Exception{
runSingleTest("Invoice_Module.005");
}
 @Test(description = "Corporate Client - Issue Invoice")
 public void Invoice_Module_006() throws Exception{
runSingleTest("Invoice_Module.006");
}
}
