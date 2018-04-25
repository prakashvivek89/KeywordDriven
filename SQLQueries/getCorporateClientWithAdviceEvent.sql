select top 1 S_CLIENT.REFER, S_CLIENT.NAME, S_CLIENT.FIRSTNAMES from S_CLIENT
where CLIENT_NUM in (select clientID from AdviceEvent
where ID not in (select LockRowID from IntLocks)
GROUP BY clientID
having COUNT(clientID)=1)
and CLIENT_TYP = 2
and CreateDate < DATEADD(MINUTE, -30, GETDATE())
and S_CLIENT.STATUS = 1
and REFER is not null
and S_CLIENT.CLIENT_NUM not in (select LockRowID from IntLocks)
order by CreateDate desc