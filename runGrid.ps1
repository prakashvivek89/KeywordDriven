$computerName = "IN00302"
$batchFileName = "C:\Vivek\WorkSpace\AdviserKeywordDriven_Parallel\Grid\hub.bat"

Write-Host "Test scripts executing on $computerName"

$scriptBlock = $ExecutionContext.InvokeCommand.NewScriptBlock("& cmd /c '$batchFileName'")
Invoke-Command -ComputerName $computerName -ErrorAction SilentlyContinue -ScriptBlock $scriptBlock



$computerName = "IN00302"
$batchFileName = "C:\Vivek\WorkSpace\AdviserKeywordDriven_Parallel\Grid\IENode.bat"

Write-Host "Test scripts executing on $computerName"

$scriptBlock = $ExecutionContext.InvokeCommand.NewScriptBlock("& cmd /c '$batchFileName'")
Invoke-Command -ComputerName $computerName -ErrorAction SilentlyContinue -ScriptBlock $scriptBlock




$computerName = "AdvAutomation"
$batchFileName = "E:\IE11\IENode.bat"

Write-Host "Test scripts executing on $computerName"

$scriptBlock = $ExecutionContext.InvokeCommand.NewScriptBlock("& cmd /c '$batchFileName'")
Invoke-Command -ComputerName $computerName -ErrorAction SilentlyContinue -ScriptBlock $scriptBlock