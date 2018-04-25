select IntAdviceEventStatus.Description status, CodeAdviceEventType.Description eventDes, convert(varchar(10), AdviceEvent.StartDate, 103) sDate from AdviceEvent 
inner join IntAdviceEventStatus on AdviceEvent.StatusID = IntAdviceEventStatus.ID 
inner join CodeAdviceEventType on AdviceEvent.TypeID = CodeAdviceEventType.ID
where ClientID = '{argument}'