select  * from S_CLIENT
inner join AdviceEvent on AdviceEvent.ClientID = S_CLIENT.CLIENT_NUM
left outer join Client1 on Client1.ClientID = S_CLIENT.CLIENT_NUM
where S_CLIENT.CLIENT_TYP = 1
and CLIENT_NUM in (select clientID from AdviceEvent 
inner join AdviceCharge on AdviceCharge.AdviceEventID = AdviceEvent.ID 
where AdviceEvent.ID not in (select AdviceEventID from Invoice ))
and S_CLIENT.CLIENT_NUM not in (select LockRowID from IntLocks)
and S_CLIENT.STATUS = 1
and CreateDate < DATEADD(MINUTE, -30, GETDATE())
and REFER is not null
and client1.ProcessingConsent = 1
and FIRSTNAMES is not null
order by CreateDate desc
