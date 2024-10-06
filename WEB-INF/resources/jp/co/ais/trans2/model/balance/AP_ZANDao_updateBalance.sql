UPDATE AP_ZAN
SET
  ZAN_JSK_DATE    = /*param.eraseSlipDate*/,
  ZAN_KESAI_KBN   = 3,
  ZAN_SIHA_DEN_NO = /*param.eraseSlipNo*/,
  ZAN_SIHA_GYO_NO = /*param.eraseSlipLineNo*/,
  UPD_DATE = /*param.updateDate.*/,
  PRG_ID   = /*param.programCode*/,
  USR_ID   = /*param.userCode*/
  
WHERE KAI_CODE     = /*param.companyCode*/
  AND ZAN_DEN_NO   = /*param.slipNo*/
  AND ZAN_DEN_DATE = /*param.slipDate*/
  AND ZAN_GYO_NO   = /*param.slipLineNo*/