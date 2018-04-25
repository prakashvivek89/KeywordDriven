select Invoice.Description invoiceDes, CodeAdviceEventType.Description eventDes,AdviceCharge.AmountDue,AdviceCharge.id,
Invoice.Number num, convert(varchar(10), Invoice.InvoiceDate, 103) invDate, IntInvoiceStatus.Description invoiceStatus 
from Invoice 
left join AdviceEvent on AdviceEvent.ID = Invoice.AdviceEventID
left join CodeAdviceEventType on AdviceEvent.TypeID = CodeAdviceEventType.ID
left join IntInvoiceStatus on IntInvoiceStatus.ID= Invoice.StatusID
left join AdviceCharge on AdviceCharge.AdviceEventID = AdviceEvent.ID
where Invoice.Number ='{argument}'

