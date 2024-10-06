SELECT
  zan.KAI_CODE,
  zan.ZAN_DEN_DATE,
  zan.ZAN_DEN_NO,
  zan.ZAN_GYO_NO,
  zan.ZAN_DC_KBN,
  zan.ZAN_KMK_CODE,
  zan.ZAN_HKM_CODE,
  zan.ZAN_UKM_CODE,
  zan.ZAN_DEP_CODE,
  zan.ZAN_UKE_DEP_CODE,
  zan.ZAN_TEK_CODE,
  zan.ZAN_TEK,
  zan.ZAN_SIHA_CODE,
  zan.ZAN_KIN,
  zan.ZAN_SIHA_DATE,
  zan.ZAN_JSK_DATE,
  zan.ZAN_FURI_DATE,
  zan.ZAN_SIHA_KBN,
  zan.ZAN_HOH_CODE,
  zan.ZAN_HORYU_KBN,
  zan.ZAN_KESAI_KBN,
  zan.ZAN_SEI_NO,
  zan.ZAN_DATA_KBN,
  zan.ZAN_NAI_CODE,
  zan.ZAN_FURI_TESU_KBN,
  zan.ZAN_YUSO_KBN,
  zan.ZAN_YUSO_TESU_KBN,
  zan.ZAN_TEG_SAITO,
  zan.ZAN_TEG_SIHA_KIJITU,
  zan.ZAN_FURI_CBK_CODE,
  zan.ZAN_FURI_BNK_CODE,
  zan.ZAN_FURI_STN_CODE,
  zan.ZAN_TEG_CBK_CODE,
  zan.ZAN_TEG_BNK_CODE,
  zan.ZAN_TEG_STN_CODE,
  zan.ZAN_LIST_KBN,
  zan.ZAN_SIHA_DEN_NO,
  zan.ZAN_SIHA_KAGEN_KBN,
  zan.ZAN_TEG_KAGEN_KBN,
  zan.ZAN_FURI_KAGEN_KBN,
  zan.ZAN_INP_DATE,
  zan.UPD_DATE,
  zan.PRG_ID,
  zan.USR_ID,
  zan.ZAN_TJK_CODE,
  zan.ZAN_CUR_CODE,
  zan.ZAN_CUR_RATE,
  zan.ZAN_IN_SIHA_KIN,
  zan.ZAN_SYS_KBN,
  zan.ZAN_DEN_SYU,
  zan.ZAN_UTK_NO,
  zan.ZAN_SIHA_GYO_NO,
  zan.ZAN_SWK_GYO_NO
FROM AP_ZAN zan

WHERE zan.KAI_CODE = /*param.companyCode*/
  AND zan.ZAN_NAI_CODE = '16' -- ì‡ïîÉRÅ[Éh
  AND zan.ZAN_HORYU_KBN = 0   -- ï€óØãÊï™
  AND zan.ZAN_DEN_NO IN /*param.slipNoList*/() -- ì`ï[î‘çÜ
  
  AND (
    (zan.ZAN_SIHA_DEN_NO IS null AND zan.ZAN_KESAI_KBN = 0)
  /*IF param.notIncledEraseSlipNo != null && !"".equals(param.notIncledEraseSlipNo) */
    OR zan.ZAN_SIHA_DEN_NO = /*param.notIncledEraseSlipNo*/
  /*END*/
  ) -- éxï•ì`ï[î‘çÜÅAéxï•åàçœãÊï™
