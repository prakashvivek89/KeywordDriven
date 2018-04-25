select  convert(varchar(10), PolicyTransactions.TransactionDate, 103) TransactionDate, PolicyTransactions.TermYears, PolicyTransactions.TermMonths,
convert(varchar(10), PolicyTransactions.StartDate, 103) StartDate, convert(varchar(10), PolicyTransactions.FinishDate, 103) FinishDate,
IntFrequency.Description Frequency, IntAdminMethod.Description AdminMethod, IntRemunerationMethod.Description RemunerationMethod,
IntPaymentMethod.Description PaymentMethod, Agentcode, CodeTransactionCategory.Code transactionCategoryCode,
convert(varchar,cast(PolicyTransactions.Amount as money),1) Amount, PolicyTransactions.Notes, IntPolicyStatus.Description policyStatus, convert(varchar,cast(TransPlanSIPP.SalaryBasedOn as money),1) SalaryBasedOn,
convert(varchar,cast(TransPlanSIPP.EmployeeGrossPercent as money),1) EmployeeGrossPercent,
convert(varchar,cast(TransPlanSIPP.EmployeeNetThisYear as money),1) EmployeeNetThisYear,
convert(varchar,cast(TransPlanSIPP.EmployeeNetCarriedBack as money),1) EmployeeNetCarriedBack,
convert(varchar,cast(TransPlanSIPP.EmployeeNetTotal as money),1) EmployeeNetTotal,
convert(varchar,cast(TransPlanSIPP.EmployeeTaxThisYear as money),1) EmployeeTaxThisYear,
convert(varchar,cast(TransPlanSIPP.EmployeeGrossThisYear as money),1) EmployeeGrossThisYear,
convert(varchar,cast(TransPlanSIPP.EmployerGrossPercent as money),1) EmployerGrossPercent,
convert(varchar,cast(TransPlanSIPP.EmployerGrossTotal as money),1) EmployerGrossTotal,
case
when (PolicyTransactions.TransType not in (select TransactionTypeID from IntPolicyTransactionTypePerPolicyType))
then IntPolicyTransactionType.Description
when (S_POLMAI.POL_TYPE  = IntPolicyTransactionTypePerPolicyType.Policytypeid and PolicyTransactions.TransType = IntPolicyTransactionTypePerPolicyType.TransactionTypeID)
then IntPolicyTransactionTypePerPolicyType.Description end as PolicyTransactionType
from PolicyTransactions 
left outer join IntFrequency on IntFrequency.ID = PolicyTransactions.Frequency 
left outer join IntAdminMethod on IntAdminMethod.Code = PolicyTransactions.AdminMethod 
left outer join IntRemunerationMethod on IntRemunerationMethod.ID = PolicyTransactions.RemunerationMethodID 
left outer join IntPaymentMethod on IntPaymentMethod.ID = PolicyTransactions.PaymentMethod
left outer join CodeTransactionCategory on CodeTransactionCategory.ID = PolicyTransactions.TransactionCategoryCode
left outer join S_POLMAI on S_POLMAI.POL_NUM = PolicyTransactions.PolicyID
left outer join IntPolicyStatus on IntPolicyStatus.ID = S_POLMAI.STATUS
left outer Join TransPlanSIPP on TransPlanSIPP.TransactionID = PolicyTransactions.ID
left  Join IntPolicyTransactionType on IntPolicyTransactionType.ID = PolicyTransactions.TransType
left join IntPolicyTransactionTypePerPolicyType on IntPolicyTransactionTypePerPolicyType.TransactionTypeID = PolicyTransactions.TransType 
and IntPolicyTransactionTypePerPolicyType.PolicyTypeID = S_POLMAI.POL_TYPE
where S_POLMAI.POL_NUM in ('{argument}') order by PolicyTransactions.ID desc