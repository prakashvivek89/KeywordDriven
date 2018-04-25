    select PolicyValuationNumber, convert(varchar,cast(TransValuations.TotalAmountsInvested as money),1) grossContri, 
    convert(varchar,cast(TransValuations.CurrentValue as money),1)CurrentValue,
    convert(varchar(10), TransValuations.ValuationDate, 103) valuationDate,
    convert(varchar(10), TransValuations.ContractedOutRetirement, 103) ContractedOutRetirement,
    ManualOrElectronic , convert(varchar,cast(TransValuationFunds.CurrentValue as money),1) currentValue_Funds, 
    TransValuationFunds.FundCode, TransValuationFunds.FundCodeType,
    TransValuationFunds.BidPrice, convert(varchar,cast(TransValuationFunds.OfferPrice as money),1) OfferPrice,
    TransValuationFunds.ProtectedRightsUnitsHeldPost ProtectedRightsUnitsHeldPost,
    convert(varchar,cast(TransValuations.SumAssured as money),1)bsicSumAssured,
    convert(varchar,cast(TransValuations.ReversionaryBonus as money),1)ReversionaryBonus,
    convert(varchar(10), TransValuations.LastBonusDate, 103) LastBonusDate,
    convert(varchar,cast(TransValuations.TotalBonus as money),1)TotalBonus,
    convert(varchar,cast(TransValuations.ExitCharge as money),1)ExitCharge,
    convert(varchar,cast(TransValuations.MarketReductionValue as money),1)MarketReductionValue,
    convert(varchar,cast(TransValuations.ProtectedRightsTransferPre as money),1)ProtectedRightsTransferPre,
    convert(varchar,cast(TransValuations.ProtectedRightsTransferPost as money),1)ProtectedRightsTransferPost,
    convert(varchar,cast(TransValuations.NonProtectedRightsTransfer as money),1)NonProtectedRightsTransfer,
    convert(varchar,cast(TransValuations.TransferValue as money),1)TransferValue,
    convert(varchar,cast(TransValuations.TerminalBonus as money),1)TerminalBonus 
    from TransValuations
    inner join dbo.PolicyTransactions on TransValuations.TransactionID = PolicyTransactions.ID
	inner join dbo.S_POLMAI on PolicyTransactions.PolicyID = S_POLMAI.POL_NUM
    left outer join TransValuationFunds on TransValuationFunds.ValuationID = TransValuations.ID
    where S_POLMAI.POL_NUM in ('{argument}') order by PolicyTransactions.ID desc