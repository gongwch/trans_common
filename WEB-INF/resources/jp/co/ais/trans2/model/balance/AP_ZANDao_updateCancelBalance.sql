UPDATE AP_ZAN
SET
  ZAN_JSK_DATE    = NULL,
  ZAN_KESAI_KBN   = 0,
  ZAN_SIHA_DEN_NO = NULL,
  ZAN_SIHA_GYO_NO = NULL,
  UPD_DATE = /*param.updateDate.*/,
  PRG_ID   = /*param.programCode*/,
  USR_ID   = /*param.userCode*/
  
WHERE KAI_CODE = /*param.companyCode*/
  AND ZAN_SIHA_DEN_NO = /*param.eraseSlipNo*/