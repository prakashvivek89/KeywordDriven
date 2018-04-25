package com.SSPWorldWide.Framework.Adviser.Testcases;

import org.testng.annotations.Test;
import com.SSPWorldWide.Framework.Adviser.ReadExcel.*;
public class ClientSearch_Module extends TestcaseFlow{
 @Test(description = "Client Search_Personal Client_Advanced search")
 public void ClientSearch_Module_001() throws Exception{
runSingleTest("ClientSearch_Module.001");
}
 @Test(description = "Client Search_Personal Client_Quick search")
 public void ClientSearch_Module_004() throws Exception{
runSingleTest("ClientSearch_Module.004");
}
 @Test(description = "Client Search_Corporate Client_Advanced search")
 public void ClientSearch_Module_002() throws Exception{
runSingleTest("ClientSearch_Module.002");
}
 @Test(description = "Client Search_Corporate Client_Quick search")
 public void ClientSearch_Module_003() throws Exception{
runSingleTest("ClientSearch_Module.003");
}
}
