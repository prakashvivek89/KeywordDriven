select convert(varchar,cast(SUM(AdviceCharge.AmountDue) as money),1) amountDue, IntAdviceEventStatus.Description statusDes, CodeAdviceEventType.Description eventDes
 from AdviceEvent 
inner join AdviceCharge on AdviceCharge.AdviceEventID = AdviceEvent.ID 
inner join IntAdviceEventStatus on AdviceEvent.StatusID = IntAdviceEventStatus.ID 
inner join CodeAdviceEventType on AdviceEvent.TypeID = CodeAdviceEventType.ID
where AdviceEvent.ClientID = '{argument}'
group by AdviceCharge.AdviceEventID, IntAdviceEventStatus.Description , CodeAdviceEventType.Description 