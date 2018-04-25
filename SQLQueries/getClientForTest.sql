select Top 1* from S_CLIENT
left outer join Client1 on Client1.ClientID = S_CLIENT.CLIENT_NUM
where CreateDate < DATEADD(MINUTE, -30, GETDATE())
and REFER is not null
and Client1.ProcessingConsent = 1
and CLIENT_NUM not in (select LockRowID from IntLocks)
order by CreateDate desc