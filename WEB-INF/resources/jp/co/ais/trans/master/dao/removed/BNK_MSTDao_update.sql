UPDATE BNK_MST SET
BNK_CODE = /*dto.BNK_CODE*/,
BNK_STN_CODE = /*dto.BNK_STN_CODE*/,
BNK_NAME_S = /*dto.BNK_NAME_S*/,
BNK_KANA = /*dto.BNK_KANA*/,
BNK_NAME_K = /*dto.BNK_NAME_K*/,
BNK_STN_NAME_S = /*dto.BNK_STN_NAME_S*/,
BNK_STN_KANA = /*dto.BNK_STN_KANA*/,
BNK_STN_NAME_K = /*dto.BNK_STN_NAME_K*/,
STR_DATE = /*dto.STR_DATE*/,
END_DATE = /*dto.END_DATE*/,
INP_DATE = /*dto.INP_DATE*/,
UPD_DATE = /*dto.UPD_DATE*/,
PRG_ID = /*dto.PRG_ID*/,
USR_ID = /*dto.USR_ID*/
WHERE BNK_CODE = /*dto.BNK_CODE*/
AND BNK_STN_CODE = /*dto.BNK_STN_CODE*/
