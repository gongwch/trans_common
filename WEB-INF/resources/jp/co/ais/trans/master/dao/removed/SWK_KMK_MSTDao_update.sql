UPDATE SWK_KMK_MST SET
KAI_CODE = /*dto.KAI_CODE*/,
KMK_CNT = /*dto.KMK_CNT*/,
KMK_CNT_NAME = /*dto.KMK_CNT_NAME*/,
KMK_CODE = /*dto.KMK_CODE*/,
HKM_CODE = /*dto.HKM_CODE*/,
UKM_CODE = /*dto.UKM_CODE*/,
DEP_CODE = /*dto.DEP_CODE*/
WHERE KAI_CODE = /*dto.KAI_CODE*/
AND KMK_CNT = /*dto.KMK_CNT*/