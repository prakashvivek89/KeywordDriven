select top 1 S_POLMAI.POL_NUM, S_POLMAI.COMP_CODE, S_POLMAI.CONT_NUM, S_polmai.ProductCode, S_POLMAI.Description PolDes,InvestInvestment.IncomeTypeID,
convert(varchar,cast(InvestInvestment.WithdrawalPercent as money),1) WithdrawalPercent, 
convert(varchar,cast(InvestInvestment.WithdrawalAmount as money),1) WithdrawalAmount, 
convert(varchar(10), InvestInvestment.WithdrawalDate, 103)WithdrawalDate, WithdrawalFrequency,
convert(varchar(10), S_PolMAI.START_DATE, 103) policyStartDate, convert(varchar(10), S_PolMAI.STOP_DATE, 103)policyStopDate, 
convert(varchar(10), PlanSIPP.AutoEnrolmentOptOutDate, 103) autoEnrollmentOPDate, convert(varchar(10), PlanSIPP.ProjectEstimateDate, 103) EstimateDate,
PlanSIPP.AccrualRate, PlanSIPP.WaiverOfPremium, convert(varchar,cast(PlanSIPP.ProjectFundValueHigh as money),1) FundValueHigh,
convert(varchar,cast(PlanSIPP.ProjectFundValueMedium as money),1) FundValueMed, convert(varchar,cast(PlanSIPP.ProjectFundValueLow as money),1) FundValueLow,
convert(varchar,cast(PlanSIPP.ProjectTaxFreeCashHigh as money),1) TaxFreeCashHigh, convert(varchar,cast(PlanSIPP.ProjectTaxFreeCashMedium as money),1) TaxFreeCashMed,
convert(varchar,cast(PlanSIPP.ProjectTaxFreeCashLow as money),1) TaxFreeCashLow, convert(varchar,cast(PlanSIPP.ProjectPensionHigh as money),1) PensionHigh,
convert(varchar,cast(PlanSIPP.ProjectPensionLow as money),1) PensionLow, convert(varchar,cast(PlanSIPP.ProjectPensionMedium as money),1) PensionMed,
convert(varchar,cast(PlanSIPP.ProjectReducedPensionHigh as money),1) ReducedPensionHigh, convert(varchar,cast(PlanSIPP.ProjectReducedPensionMedium as money),1) ReducedPensionMed,
convert(varchar,cast(PlanSIPP.ProjectReducedPensionLow as money),1) ReducedPensionLow, convert(int,cast(PlanSIPP.ProjectGrowthRateMedium as money),1) ProjectGrowthRateMedium, 
convert(int,cast(PlanSIPP.ProjectGrowthRateHigh as money),1) ProjectGrowthRateHigh, convert(int,cast(PlanSIPP.ProjectGrowthRateLow as money),1) ProjectGrowthRateLow, 
PlanSIPP.WaiverDeferredPeriod, PlanSIPP.DeathBenefit, PlanSIPP.AutoIncreaseIndexation, convert(varchar,cast(PlanSIPP.AutoIncreasePercent as money),1) autoIncreasePrcent, convert(varchar,cast(S_ANNUIT.TAX_FREE as money),1) taxFreeAnnuity, convert(varchar,cast(S_ANNUIT.ANNUITY as money),1) ANNUITY_ann, 
S_ANNUIT.ADV_ARR ADV_ARRAnnuity, S_ANNUIT.GUAR_PERD GUAR_PERDAnnuity, convert(varchar,cast(S_ANNUIT.ESCALATION as money),1) EsclationAnnuity, convert(varchar,cast(S_ANNUIT.RED_TO_DED as money),1) RED_TO_DEDAnnuity, S_ANNUIT.ContractTypeID contractTypeIDAnn,
S_ANNUIT.EscRatePreDef ADV_EscRateAnnuity, S_ANNUIT.PAY_FREQ PAY_FREQAnnuity, convert(varchar,cast(S_ANNUIT.SpousePercent as money),1) SpousePercentAnnuity,S_ANNUIT.CapitalProtected CapitalProtectedAnn,
S_POLMAI.PLAN_CODE, S_POLMAI.TERM_Y, S_POLMAI.TERM_M, PolicyTrust.TrustName, PolicyTrust.TrustType, AssetBankAccount.SortCode, AssetBankAccount.BankName, 
convert(varchar,cast(AssetBankAccount.CurInterestRate as money),1) CurInterestRate, AssetBankAccount.BankType, PolicyGI.CoverDetails CoverDetails, convert(varchar,cast(PolicyGI.Excess as money),1) GIExcess,
S_PHI.EMP_CLASS EMP_CLASS_HI, convert(varchar,cast(S_PHI.BENEFIT as money),1) benefitAmout_HI, S_PHI.ExpiryAge expAge_HI, 
S_PHI.BEN_FREQ benFREQ_HI, S_PHI.GUAR_REV guarRev_HI, convert(varchar,cast(S_PHI.AUTO_INCR as money),1) autoIncr_HI, S_PHI.WEEKS_DEF weeksDef_HI,S_PHI.RateOfCoverIncreaseID RateOfCoverIncreaseID_HI,
S_PHI.PremiumIncreaseIndexation PremiumIncreaseIndexation_HI, S_PHI.WaiverOfPremium WaiverOfPremium_HI, convert(varchar,cast(S_PHI.BenefitIncreaseRate as money),1)BenefitIncreaseRate_HI,
PolicyMortgage.MortgageType, PolicyMortgage.OutstandingTermY OutstandingTermY_Mort, PolicyFactFind.MortgageRateType,
PolicyMortgage.OutstandingTermM OutstandingTermM_Mort,PolicyMortgage.MortgageReasonID, 
convert(varchar,cast(PolicyMortgage.OriginalLoan as money),1)OriginalLoan_Mort, 
convert(varchar,cast(PolicyMortgage.PropertyPrice as money),1)PropertyPrice_Mort,
convert(varchar,cast(PolicyMortgage.AmountOutstanding as money),1)AmountOutstanding_Mort,
convert(varchar(10), PolicyMortgage.OutstandingLoanDate, 103) OutstandingLoanDate_Mort, 
convert(varchar(10), PolicyMortgage.SpecialDealEndDate, 103) SpecialDealEndDate_Mort,
convert(varchar(10), PolicyFactFind.RedemptionPenaltyEndDate, 103) RedemptionPenaltyEndDate_Mort,
convert(varchar,cast(PolicyFactFind.RedemptionPenaltyAmount as money),1)RedemptionPenaltyAmount_Mort,  
convert(varchar,cast(PolicyFactFind.MortgageInterestRatePercent as money),1)MortgageInterestRatePercent_Mort,
S_LIFE.WaiverExpiryAge WaiverExpiryAge_LI, convert(varchar,cast(S_LIFE.SUM_ASS as money),1)SUM_ASS_LI,S_LIFE.GUAR_REV GUAR_REV_LI,
convert(varchar,cast(S_LIFE.AUTO_INCR as money),1)AUTO_INCR_LI, 
convert(varchar,cast(S_LIFE.CRIT_SUMAS as money),1)CRIT_SUMAS_LI , 
convert(varchar,cast(S_LIFE.PolicyInterestRate as money),1)
PolicyInterestRate_LI, S_LIFE.LifeCoverDeferredForMonths LifeCoverDeferredForMonths_LI, 
S_LIFE.PremiumCeaseAtAge PremiumCeaseAtAge_LI, S_LIFE.WaiverExpiryAge, S_LIFE.CRIT_BASIS CRIT_BASIS_LI,
S_LIFE.WaiverOfPremium WaiverOfPremium_LI, PlanUnderwriting.UnderwritingTerms UnderwritingTerms_LI, 
PlanUnderwriting.ReasonForUnderwriting ReasonForUnderwriting_LI, S_LIFE.PremiumIncreaseIndexation PremiumIncreaseIndexation_LI, 
CodePolicyFamilyBasis.Code familyBasis, CodePolicyCoverCategory.Code coverCategory,
CodePolicyCoverScale.Code coverScale, CodePolicyUnderwritingTerm.Code underwritingterms_med,
S_PMI.AgeFrom, S_PMI.AgeTo, S_PMI.OVERSEAS_T, convert(varchar,cast(S_PMI.EXCESS as money),1)excess_MedInsure,
S_PMI.NamedHospital, S_PMI.QualifyingPeriod ,  S_PMI.QualifyingPeriodPolicyPeriodBasisID,  S_PMI.MaxChildAge,
CodePolicyBenefitType.Description benefitType, CodePolicyBenefit.Description benefit, CodePolicyBenefitBasis.Description BenefitBasis,
convert(varchar,cast(PolicyBenefit.BenefitAmount as money),1) BenefitAmount, 
convert(varchar,cast(PolicyBenefit.BenefitMax as money),1)BenefitMax,
convert(varchar,cast(PolicyBenefit.Excess as money),1) excess_addBenefit, S_PMI.EXCLUSN_1, S_PMI.MEMBR_EXCL, S_PMI.DEPEN_EXCL,
convert(varchar(10), S_POLDEP.BIRTHDATE, 103) pensionCoveredDOB, S_POLDEP.NAME pensionCoveredName,
S_PMI.SPOUSE pensionCoveredSpouseName, convert(varchar(10), S_PMI.SPSE_DOB, 103)pensionCoveredSpouseDOB, 
CodePolicyEmployeeCategory.Description Category_GroupHI, SchemeCategories.QualifyingPeriod QualifyingPeriod_GropuHI, 
SchemeCategories.AgeFemaleFrom, SchemeCategories.AgeFemaleTo,
SchemeCategories.AgeMaleFrom, SchemeCategories.AgeMaleTo,
convert(varchar,cast(SchemeCategories.EmployeeAmount as money),1) EmployeeAmount_GroupHI,
convert(varchar,cast(SchemeCategories.EmployerAmount as money),1) EmployerAmount_GroupHI,
convert(varchar,cast(S_GPMI.TotalClaimsPaid  as money),1) TotalClaimsPaid_S_GPMI, 
CodePolicyUnderwritingTerm_SGPMI.Code UnderwritingTerm_S_GPMI,
CodePolicyCoverScale_SGPMI.Code CoverScale_S_GPMI, S_GPMI.MaxChildAge MaxChildAge_S_GPMI, 
convert(varchar,cast(S_GPMI.DependentClaimPercent  as money),1) DependentClaimPercent_S_GPMI, S_GPMI.NamedHospital NamedHospital_S_GPMI,
convert(varchar,cast(S_GPMI.Excess as money),1) Excess_S_GPMI,
convert(varchar,cast(SchemePension.NISavingRebate as money),1) NISavingRebate,
convert(varchar,cast(SchemePension.ActiveMemberDiscountRegular as money),1) ActiveMemberDiscountRegular_GPension,
convert(varchar,cast(SchemePension.ActiveMemberDiscountSingle as money),1) ActiveMemberDiscountSingle_GPension,
convert(varchar,cast(SchemePension.AnnualManagementCharge as money),1) AnnualManagementCharge_GPension,
convert(varchar,cast(SchemePension.ActiveMemberDiscountTransfer as money),1) ActiveMemberDiscountTransfer_GPension,
convert(varchar,cast(SchemePension.ContributionLevel as money),1) ContributionLevel_GPension,
SchemePension.AccrualRate AccrualRate_GPension, SchemePension.AnnualWindow AnnualWindow_GPension, PolicyScheme.TotalMembers TotalMembers_GPension
from S_POLMAI
left outer join dbo.Policy1 on S_POLMAI.POL_NUM = Policy1.PolicyID
left outer join dbo.PolicyTrust on S_POLMAI.POL_NUM = PolicyTrust.PolicyID
left outer join dbo.IntPolicyType on S_POLMAI.POL_TYPE = IntPolicyType.ID
left outer join InvestInvestment on  S_POLMAI.POL_NUM = InvestInvestment.PolicyID
left outer join PolicyClientRole on  S_POLMAI.POL_NUM = PolicyClientRole.PolicyID
left outer join dbo.PlanSIPP on S_POLMAI.POL_NUM = PlanSIPP.PolicyID
left outer join dbo.S_ANNUIT on S_POLMAI.POL_NUM = S_ANNUIT.POL_NUM
left outer join dbo.AssetBankAccount on S_POLMAI.POL_NUM = AssetBankAccount.AssetID
left outer join dbo.PolicyGI on S_POLMAI.POL_NUM = PolicyGI.PolicyID
left outer join dbo.S_PHI on S_POLMAI.POL_NUM = S_PHI.POL_NUM
left outer join PolicyMortgage on S_POLMAI.POL_NUM = PolicyMortgage.PolicyID
left outer join PolicyFactFind on S_POLMAI.POL_NUM = PolicyFactFind.PolicyID
left outer join S_LIFE on S_POLMAI.POL_NUM = S_LIFE.POL_NUM
left outer join PlanUnderwriting on S_POLMAI.POL_NUM = PlanUnderwriting.PolicyID
left outer join S_PMI on S_POLMAI.POL_NUM = S_PMI.POL_NUM
left outer join dbo.PolicyBenefit on S_PMI.POL_NUM = dbo.PolicyBenefit.PolicyID
left outer join dbo.CodePolicyFamilyBasis on S_PMI.PolicyFamilyBasisID = dbo.CodePolicyFamilyBasis.ID
left outer join dbo.CodePolicyCoverCategory on S_PMI.PolicyCoverCategoryID = dbo.CodePolicyCoverCategory.ID
left outer join dbo.CodePolicyCoverScale on S_PMI.PolicyCoverScaleID = dbo.CodePolicyCoverScale.ID
left outer join dbo.CodePolicyUnderwritingTerm on S_PMI.PolicyUnderwritingTermsID = dbo.CodePolicyUnderwritingTerm.ID
left outer join dbo.CodePolicyBenefit on PolicyBenefit.PolicyBenefitID = dbo.CodePolicyBenefit.ID
left outer join dbo.CodePolicyBenefitBasis on PolicyBenefit.PolicyBenefitBasisID = dbo.CodePolicyBenefitBasis.ID
left outer join dbo.CodePolicyBenefitType on PolicyBenefit.PolicyBenefitTypeID = dbo.CodePolicyBenefitType.ID
left outer join dbo.S_POLDEP on S_POLMAI.POL_NUM = dbo.S_POLDEP.POL_NUM
left outer join dbo.SchemeCategories on S_POLMAI.POL_NUM = dbo.SchemeCategories.SchemeID
left outer join dbo.CodePolicyEmployeeCategory on SchemeCategories.EmployeeCategoryID = dbo.CodePolicyEmployeeCategory.ID
left outer join dbo.S_GPMI on S_POLMAI.POL_NUM = dbo.S_GPMI.POL_NUM
left outer join CodePolicyCoverScale CodePolicyCoverScale_SGPMI on CodePolicyCoverScale_SGPMI.ID = S_GPMI.PolicyCoverScaleID 
left outer join CodePolicyUnderwritingTerm CodePolicyUnderwritingTerm_SGPMI on CodePolicyUnderwritingTerm_SGPMI.ID = S_GPMI.PolicyUnderwritingTermsID
left outer join dbo.SchemePension on S_POLMAI.POL_NUM = dbo.SchemePension.PolicyID
left outer join dbo.PolicyScheme on S_POLMAI.POL_NUM = dbo.PolicyScheme.PolicyID
where S_POLMAI.CLIENT_NUM in('{argument}') order by S_POLMAI.POL_NUM desc