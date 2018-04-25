select top 1 * from S_CLIENT
where CLIENT_NUM not in (select clientID from AdviceEvent)
and CLIENT_TYP = 2
and CreateDate < DATEADD(MINUTE, -30, GETDATE())
and REFER is not null
and S_CLIENT.CLIENT_NUM not in (select LockRowID from IntLocks)
and S_CLIENT.STATUS = 1
order by CreateDate desc