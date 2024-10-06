SELECT
  zan.KAI_CODE,
  env.KAI_NAME_S KAI_NAME_S,
  zan.ZAN_DEN_DATE,
  zan.ZAN_DEN_NO,
  zan.ZAN_GYO_NO,
  zan.ZAN_DC_KBN,
  zan.ZAN_KMK_CODE,
  kmk.KMK_NAME_S ZAN_KMK_NAME_S,
  zan.ZAN_HKM_CODE,
  hkm.HKM_NAME_S ZAN_HKM_NAME_S,
  zan.ZAN_UKM_CODE,
  ukm.UKM_NAME_S ZAN_UKM_NAME_S,
  zan.ZAN_DEP_CODE,
  dep.DEP_NAME_S ZAN_DEP_NAME_S,
  zan.ZAN_UKE_DEP_CODE,
  zan.ZAN_TEK_CODE,
  zan.ZAN_TEK,
  zan.ZAN_SIHA_CODE,
  tri.TRI_NAME_S ZAN_SIHA_NAME_S,
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
  cur.DEC_KETA ZAN_CUR_DEC_KETA,
  cur.RATE_POW ZAN_CUR_RATE_POW,
  zan.ZAN_CUR_RATE,
  zan.ZAN_IN_SIHA_KIN,
  zan.ZAN_SYS_KBN,
  zan.ZAN_DEN_SYU,
  zan.ZAN_UTK_NO,
  zan.ZAN_SIHA_GYO_NO,
  zan.ZAN_SWK_GYO_NO
FROM AP_ZAN zan
  LEFT OUTER JOIN ENV_MST env
     ON zan.KAI_CODE = env.KAI_CODE
  
  INNER JOIN TRI_MST tri
     ON zan.KAI_CODE = tri.KAI_CODE
    AND zan.ZAN_SIHA_CODE = tri.TRI_CODE
    /*IF param.customerNamesLike != null && !"".equals(param.customerNamesLike) */
      AND tri.TRI_NAME_S  /*$param.customerNamesLike*/ -- LIKE
    /*END*/
  
  LEFT OUTER JOIN BMN_MST dep
    ON zan.KAI_CODE = dep.KAI_CODE
   AND zan.ZAN_DEP_CODE = dep.DEP_CODE

  LEFT OUTER JOIN CUR_MST cur
    ON zan.KAI_CODE = cur.KAI_CODE
   AND zan.ZAN_CUR_CODE = cur.CUR_CODE

  LEFT OUTER JOIN KMK_MST kmk
    ON zan.KAI_CODE = kmk.KAI_CODE
   AND zan.ZAN_KMK_CODE = kmk.KMK_CODE
  
  LEFT OUTER JOIN HKM_MST hkm
    ON zan.KAI_CODE = hkm.KAI_CODE
   AND zan.ZAN_KMK_CODE = hkm.KMK_CODE
   AND zan.ZAN_HKM_CODE = hkm.HKM_CODE

  LEFT OUTER JOIN UKM_MST ukm
    ON zan.KAI_CODE = ukm.KAI_CODE
   AND zan.ZAN_KMK_CODE = ukm.KMK_CODE
   AND zan.ZAN_HKM_CODE = ukm.HKM_CODE
   AND zan.ZAN_UKM_CODE = ukm.UKM_CODE

WHERE zan.KAI_CODE = /*param.companyCode*/
  AND zan.ZAN_NAI_CODE = '16' --  “à•”ƒR[ƒh
  AND zan.ZAN_HORYU_KBN = 0   --  •Û—¯‹æ•ª

  /*IF param.eraseSlipNo != null && !"".equals(param.eraseSlipNo) */
    AND zan.ZAN_SIHA_DEN_NO = /*param.eraseSlipNo*/ --  Žx•¥“`•[”Ô†
  /*END*/
  
  /*IF param.clientCodeFrom != null && !"".equals(param.clientCodeFrom) */
    AND zan.ZAN_SIHA_CODE >= /*param.clientCodeFrom*/
  /*END*/

  /*IF param.clientCodeTo != null && !"".equals(param.clientCodeTo)  */
    AND zan.ZAN_SIHA_CODE <= /*param.clientCodeTo*/
  /*END*/

  /*IF param.departmentCodeFrom != null && !"".equals(param.departmentCodeFrom) */
    AND zan.ZAN_DEP_CODE >= /*param.departmentCodeFrom*/
  /*END*/

  /*IF param.departmentCodeTo != null && !"".equals(param.departmentCodeTo) */
    AND zan.ZAN_DEP_CODE <= /*param.departmentCodeTo*/
  /*END*/

  /*IF param.billNoLike != null && !"".equals(param.billNoLike) */
    AND zan.ZAN_SEI_NO  /*$param.billNoLike*/ -- LIKE
  /*END*/

  /*IF param.slipNoLike != null && !"".equals(param.slipNoLike) */
    AND zan.ZAN_DEN_NO  /*$param.slipNoLike*/ -- LIKE
  /*END*/

  /*IF param.slipDate != null */
    AND zan.ZAN_DEN_DATE = /*param.slipDate*/
  /*END*/

  /*IF param.expectedDay != null */
    AND zan.ZAN_SIHA_DATE = /*param.expectedDay*/
  /*END*/
  
  /*IF param.slipTekLike != null && !"".equals(param.slipTekLike) */
    AND zan.ZAN_TEK  /*$param.slipTekLike*/ -- LIKE
  /*END*/

  /*IF param.departmentNameLike != null && !"".equals(param.departmentNameLike) */
    AND dep.DEP_NAME_S  /*$param.departmentNameLike*/ -- LIKE
  /*END*/

  /*IF param.currencyCode != null && !"".equals(param.currencyCode) */
    AND zan.ZAN_CUR_CODE = /*param.currencyCode*/
  /*END*/

  /*IF param.eraseSlipNo == null || "".equals(param.eraseSlipNo) */
    --  Žx•¥“`•[”Ô†AŽx•¥ŒˆÏ‹æ•ª
    AND (
      (zan.ZAN_SIHA_DEN_NO IS null AND zan.ZAN_KESAI_KBN = 0)
    /*IF param.notIncledEraseSlipNo != null && !"".equals(param.notIncledEraseSlipNo) */
      OR zan.ZAN_SIHA_DEN_NO = /*param.notIncledEraseSlipNo*/
    /*END*/
    )
  /*END*/

ORDER BY
  zan.ZAN_SIHA_CODE,
  zan.ZAN_DEN_DATE,
  zan.ZAN_DEN_NO,
  zan.ZAN_GYO_NO,
  zan.ZAN_SEI_NO