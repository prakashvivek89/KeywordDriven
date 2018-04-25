select Top 1* from S_CLIENT
where CLIENT_TYP = 2
and REFER is not null
and CreateDate < DATEADD(MINUTE, -30, GETDATE())
and STATUS = 1
and CLIENT_NUM not in (select LockRowID from IntLocks)
order by CreateDate desc