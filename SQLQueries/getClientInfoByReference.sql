 select *, convert(varchar(10), S_CLIENT.BIRTHDATE, 103) DOB , CodeClientSources.Code as sourceCode,
  CodeClientSegmentation.Code as segmenttationCode, convert(varchar(10), S_CLIENT.TERMS_OB, 103) TERMS_OBDATE,
   convert(varchar(10), S_CLIENT.REVIEW, 103) REVIEWDATE,
   convert(varchar(10), S_CLIENT.AgeEvidence1Seen, 103) AgeEvidence1Date,
   convert(varchar(10), S_CLIENT.AgeEvidence3Seen, 103) AgeEvidence3Date,
   convert(varchar(10), S_CLIENT.AgeEvidence2Seen, 103) AgeEvidence2Date
  from S_CLIENT
    left join ClientPersonal on ClientPersonal.ClientID = S_CLIENT.CLIENT_NUM
    left join ClientCorporate on ClientCorporate.ClientID = S_CLIENT.CLIENT_NUM
    left join IntCompanySize on IntCompanySize.ID = ClientCorporate.CompanySizeID
    inner join Client1 on Client1.ClientID = S_CLIENT.CLIENT_NUM
    left join CodeClientSources on CodeClientSources.ID = Client1.SourceOfBusinessID
    left join CodeClientSegmentation on CodeClientSegmentation.ID = Client1.SegmentationID
    inner join ClientUserDefinedDataFixed on ClientUserDefinedDataFixed.ClientID = S_CLIENT.CLIENT_NUM
    where S_Client.Refer = '{argument}'