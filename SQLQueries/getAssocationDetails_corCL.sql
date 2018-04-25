select Refer, NAME, IntClientLinkType.FromDescription fromDes, IntClientStatus.Description status  from S_CLIENT 
    left join ClientLink on ClientLink.ToID =  S_CLIENT.CLIENT_NUM
    left join IntClientLinkType on IntClientLinkType.ID = ClientLink.LinkTypeID
    left join IntClientStatus on IntClientStatus.ID = S_CLIENT.STATUS
     where ClientLink.ToID in (select CLIENT_NUM from S_CLIENT where REFER ='{argument}')