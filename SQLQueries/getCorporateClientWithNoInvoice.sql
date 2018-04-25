select top 1 REFER, NAME, FIRSTNAMES from s_client
inner join AdviceEvent on AdviceEvent.ClientID = S_CLIENT.CLIENT_NUM
where S_CLIENT.CLIENT_TYP = 2
and CLIENT_NUM in (select clientID from AdviceEvent 
inner join AdviceCharge on AdviceCharge.AdviceEventID = AdviceEvent.ID 
where AdviceEvent.ID not in (select AdviceEventID from Invoice ))
and CreateDate < DATEADD(MINUTE, -30, GETDATE())
and S_CLIENT.STATUS = 1
and S_CLIENT.CLIENT_NUM not in (select LockRowID from IntLocks)
and REFER is not null
order by CreateDate desc
