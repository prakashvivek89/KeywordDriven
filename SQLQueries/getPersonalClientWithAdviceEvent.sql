select top 1 REFER, NAME, FIRSTNAMES from S_CLIENT
left outer join Client1 on Client1.ClientID = S_CLIENT.CLIENT_NUM
where CLIENT_NUM in (select clientID from AdviceEvent
where ID not in (select LockRowID from IntLocks)
GROUP BY clientID
having COUNT(clientID)=1)
and CLIENT_TYP = 1
and CreateDate < DATEADD(MINUTE, -30, GETDATE())
and S_CLIENT.CLIENT_NUM not in (select LockRowID from IntLocks)
and S_CLIENT.STATUS = 1
and REFER is not null
and FIRSTNAMES is not null
and client1.ProcessingConsent = 1
order by CreateDate desc