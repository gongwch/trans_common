UPDATE AP_CAL_MST SET
KAI_CODE = /*dto.KAI_CODE*/,
CAL_DATE = /*dto.CAL_DATE*/,
CAL_HARAI = /*dto.CAL_HARAI*/,
CAL_BNK_KBN = /*dto.CAL_BNK_KBN*/,
CAL_SHA = /*dto.CAL_SHA*/,
CAL_INP_DATE = /*dto.CAL_INP_DATE*/,
UPD_DATE = /*dto.UPD_DATE*/,
PRG_ID = /*dto.PRG_ID*/,
USR_ID = /*dto.USR_ID*/
WHERE KAI_CODE = /*dto.KAI_CODE*/
AND CAL_DATE = /*dto.CAL_DATE*/
