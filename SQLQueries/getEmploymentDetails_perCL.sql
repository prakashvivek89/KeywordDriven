Select S_CLIENT.OCC_CODE, S_CLIENT.ST_EMPLOY, convert(varchar(10), S_CLIENT.SERV_FROM, 103) startDate,
	S_CLIENT.RETIRE_AGE, ClientPersonal.EmploymentStaffReference, convert(varchar(10), ClientPersonal.EmploymentEndDate, 103) endDate, 
	S_CLIENT.EMP_NAME, S_CLIENT.EMP_CONTAC, S_CLIENT.EMP_POST, S_CLIENT.EMP_ADD3, S_CLIENT.EMP_COUNTRY, S_CLIENT.EMP_BRANCH, 
	S_CLIENT.EMP_COUNTY, S_CLIENT.EMP_PHONE, convert(varchar(10), S_CLIENT.PENS_FROM, 103) pensionFrom ,
	ClientSalaryHistory.TaxYearStart, convert(varchar,cast(ClientSalaryHistoryDetails.Amount as money),1) amount, ClientSalaryHistoryDetails.Type, 
	convert(varchar,cast(ClientSalaryHistory.TaxRate as money),1) taxRate, ClientSalaryHistoryDetails.TaxRegimeID,
	convert(varchar(10), ClientSalaryHistoryDetails.SalaryDate, 103) salaryDate
	from S_CLIENT
    left outer join ClientSalaryHistory on ClientSalaryHistory.ClientID = S_CLIENT.CLIENT_NUM
    left outer join ClientSalaryHistoryDetails on ClientSalaryHistoryDetails.SalaryHistoryID = ClientSalaryHistory.ID
    left outer join ClientPersonal on ClientPersonal.ClientID = S_CLIENT.CLIENT_NUM
    where CLIENT_NUM =  '{argument}'