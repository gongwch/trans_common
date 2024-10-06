UPDATE KMK_TK_MST SET
KAI_CODE  = /*dto.KAI_CODE */,
KMT_CODE = /*dto.KMT_CODE*/,
KMT_NAME = /*dto.KMT_NAME*/,
KMT_NAME_S = /*dto.KMT_NAME_S*/,
KMT_NAME_K = /*dto.KMT_NAME_K*/,
INP_DATE = /*dto.INP_DATE*/,
UPD_DATE = /*dto.UPD_DATE*/,
PRG_ID = /*dto.PRG_ID*/,
USR_ID = /*dto.USR_ID*/
WHERE KAI_CODE  = /*dto.KAI_CODE */
AND KMT_CODE = /*dto.KMT_CODE*/
