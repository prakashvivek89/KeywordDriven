select distinct top 1 S_client.CLIENT_NUM, REFER, FIRSTNAMES, NAME from s_client 
inner join S_POLMAI on s_client.CLIENT_NUM = S_POLMAI.CLIENT_NUM
inner join PolicyTransactions on S_POLMAI.POL_NUM = PolicyTransactions.PolicyID 
inner join IntPolicyTransactionType on PolicyTransactions.TransType = IntPolicyTransactionType.ID
left outer join Client1 on Client1.ClientID = S_CLIENT.CLIENT_NUM
where S_POLMAI.STATUS = 20 
and IntPolicyTransactionType.MoneyDirection = 1
and S_CLIENT.CLIENT_TYP = 1
and S_CLIENT.STATUS = 1
and S_CLIENT.CLIENT_NUM not in (select clientID from AdviceEvent )
and S_CLIENT.CLIENT_NUM not in (select LockRowID from IntLocks)
and S_POLMAI.POL_NUM not in (select LockRowID from IntLocks)
and client1.ProcessingConsent = 1
order by S_CLIENT.CLIENT_NUM desc