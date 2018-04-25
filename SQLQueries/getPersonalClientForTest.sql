select Top 1* from S_CLIENT 
left outer join Client1 on Client1.ClientID = S_CLIENT.CLIENT_NUM
where CLIENT_TYP = 1
and REFER is not null
and STATUS = 1
and client1.ProcessingConsent = 1
and CreateDate < DATEADD(MINUTE, -30, GETDATE())
and S_CLIENT.CLIENT_NUM not in (select LockRowID from IntLocks)
order by CreateDate desc