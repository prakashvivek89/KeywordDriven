select top 1 REFER, NAME, FIRSTNAMES  from S_CLIENT
left outer join Client1 on Client1.ClientID = S_CLIENT.CLIENT_NUM
where CLIENT_NUM not in (select clientID from AdviceEvent)
and CLIENT_TYP = 1
and CreateDate < DATEADD(MINUTE, -30, GETDATE())
and REFER is not null
and client1.ProcessingConsent = 1
and S_CLIENT.CLIENT_NUM not in (select LockRowID from IntLocks)
and FIRSTNAMES is not null
order by CreateDate desc