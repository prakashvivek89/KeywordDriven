select top 1 S_CLIENT.REFER, S_CLIENT.NAME, S_CLIENT.FIRSTNAMES, COUNT(invoice.AdviceEventID) from Invoice
 inner join AdviceEvent on AdviceEvent.ID = Invoice.AdviceEventID
 inner join S_CLIENT on S_CLIENT.CLIENT_NUM = AdviceEvent.ClientID
 left outer join Client1 on Client1.ClientID = S_CLIENT.CLIENT_NUM
 where S_CLIENT.CLIENT_TYP = 2
 and S_CLIENT.STATUS = 1
 and S_CLIENT.CLIENT_NUM not in (select LockRowID from IntLocks)
 and Invoice.StatusID = 1
 and AdviceEvent.ID not in (select LockRowID from IntLocks)
 GROUP BY S_CLIENT.REFER,S_CLIENT.NAME, S_CLIENT.FIRSTNAMES
 having COUNT(invoice.AdviceEventID)=1
