select Refer, FullName, IntClientLinkType.ToDescription ToDes, IntClientStatus.Description status from S_CLIENT 
    inner join ClientLink on ClientLink.FromID =  S_CLIENT.CLIENT_NUM
    inner join IntClientLinkType on IntClientLinkType.ID = ClientLink.LinkTypeID
    left join IntClientStatus on IntClientStatus.ID = S_CLIENT.STATUS
     where ClientLink.FromID in (select CLIENT_NUM from S_CLIENT where REFER ='{argument}')