package com.SSPWorldWide.Framework.Adviser.Testcases;

import org.testng.annotations.Test;
import com.SSPWorldWide.Framework.Adviser.ReadExcel.*;
public class Transactions_Module extends TestcaseFlow{
 @Test(description = "Personal Client - Edit/delete Regular Transactions - Pension")
 public void Transactions_Module_008() throws Exception{
runSingleTest("Transactions_Module.008");
}
 @Test(description = "Personal Client - Add Regular Transactions - Investment")
 public void Transactions_Module_007() throws Exception{
runSingleTest("Transactions_Module.007");
}
 @Test(description = "Corporate Client - Edit/delete Regular Transactions - Pension")
 public void Transactions_Module_006() throws Exception{
runSingleTest("Transactions_Module.006");
}
 @Test(description = "Corporate Client - Add Regular Transactions - Investment")
 public void Transactions_Module_005() throws Exception{
runSingleTest("Transactions_Module.005");
}
 @Test(description = "Corporate Client - Edit/delete Single Transactions - Pension")
 public void Transactions_Module_004() throws Exception{
runSingleTest("Transactions_Module.004");
}
 @Test(description = "Corporate Client - Add Single Transactions - Investment")
 public void Transactions_Module_003() throws Exception{
runSingleTest("Transactions_Module.003");
}
 @Test(description = "Personal Client - Edit/delete Single Transactions - Pension")
 public void Transactions_Module_002() throws Exception{
runSingleTest("Transactions_Module.002");
}
 @Test(description = "Personal Client - Add Single Transactions - Investment")
 public void Transactions_Module_001() throws Exception{
runSingleTest("Transactions_Module.001");
}
}
