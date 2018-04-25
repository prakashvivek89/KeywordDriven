select TOP 1 REFER, NAME from S_CLIENT
where CLIENT_NUM not in (Select CLIENT_NUM from S_POLMAI)
and REFER is not null
and CLIENT_TYP = 2
and CreateDate < DATEADD(MINUTE, -30, GETDATE())
and CLIENT_NUM not in (select LockRowID from IntLocks)
order by CreateDate desc