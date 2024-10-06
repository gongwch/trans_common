SELECT
  zan.KAI_CODE,
  env.KAI_NAME_S KAI_NAME_S,
  zan.ZAN_TRI_CODE,
  tri.TRI_NAME_S ZAN_TRI_NAME_S,
  zan.ZAN_SEI_DEN_DATE,
  zan.ZAN_SEI_DEN_NO,
  zan.ZAN_SEI_GYO_NO,
  zan.ZAN_SEI_NO,
  zan.ZAN_KMK_CODE,
  kmk.KMK_NAME_S ZAN_KMK_NAME_S,
  zan.ZAN_HKM_CODE,
  hkm.HKM_NAME_S ZAN_HKM_NAME_S,
  zan.ZAN_UKM_CODE,
  ukm.UKM_NAME_S ZAN_UKM_NAME_S,
  zan.ZAN_AR_DATE,
  zan.ZAN_CUR_CODE,
  zan.ZAN_CUR_RATE,
  cur.DEC_KETA ZAN_CUR_DEC_KETA,
  zan.NON_KESI_IN_KIN,
  zan.NON_KESI_KIN,
  zan.SEI_IN_KIN,
  zan.SEI_KIN,
  zan.KESI_IN_KIN,
  zan.KESI_KIN,
  zan.ZAN_DEP_CODE,
  bmn.DEP_NAME_S ZAN_DEP_NAME_S,
  swkhdr.SWK_TEK ZAN_TEK,
  cur.RATE_POW ZAN_CUR_RATE_POW

FROM 
(
   SELECT
      KAI_CODE,
      ZAN_TRI_CODE,
      ZAN_SEI_DEN_DATE,
      ZAN_SEI_DEN_NO,
      ZAN_SEI_GYO_NO,
      ZAN_SEI_NO,
      ZAN_KMK_CODE,
      ZAN_HKM_CODE,
      ZAN_UKM_CODE,
      ZAN_AR_DATE,
      ZAN_CUR_CODE,
      ZAN_CUR_RATE,
      ZAN_DEP_CODE,
      SUM(CASE WHEN ZAN_SEI_IN_KIN IS NULL THEN 0 ELSE ZAN_SEI_IN_KIN END 
          - CASE WHEN ZAN_KESI_IN_KIN IS NULL THEN 0 ELSE ZAN_KESI_IN_KIN END) NON_KESI_IN_KIN,
      SUM(CASE WHEN ZAN_SEI_KIN IS NULL THEN 0 ELSE ZAN_SEI_KIN END 
          - CASE WHEN ZAN_KESI_KIN IS NULL THEN 0 ELSE ZAN_KESI_KIN END) NON_KESI_KIN,
      SUM(CASE WHEN ZAN_SEI_IN_KIN IS NULL THEN 0 ELSE ZAN_SEI_IN_KIN END ) SEI_IN_KIN,
      SUM(CASE WHEN ZAN_SEI_KIN IS NULL THEN 0 ELSE ZAN_SEI_KIN END ) SEI_KIN,
      SUM(CASE WHEN ZAN_KESI_IN_KIN IS NULL THEN 0 ELSE ZAN_KESI_IN_KIN END) KESI_IN_KIN,
      SUM(CASE WHEN ZAN_KESI_KIN IS NULL THEN 0 ELSE ZAN_KESI_KIN END) KESI_KIN
    FROM
      AR_ZAN
    WHERE KAI_CODE = /*param.companyCode*/
      AND CASE WHEN ZAN_SOUSAI_FLG IS null THEN 0
               WHEN ZAN_DATA_KBN = '31' THEN ZAN_SOUSAI_FLG
               ELSE ZAN_SOUSAI_FLG
          END <> 1
      AND ZAN_NYU_UTK_NO IS null

      /*IF param.clientCodeFrom != null && !"".equals(param.clientCodeFrom) */
        AND ZAN_TRI_CODE >= /*param.clientCodeFrom*/
      /*END*/

      /*IF param.clientCodeTo != null && !"".equals(param.clientCodeTo) */
        AND ZAN_TRI_CODE <= /*param.clientCodeTo*/
      /*END*/

      /*IF param.departmentCodeFrom != null && !"".equals(param.departmentCodeFrom) */
        AND ZAN_DEP_CODE >= /*param.departmentCodeFrom*/
      /*END*/

      /*IF param.departmentCodeTo != null && !"".equals(param.departmentCodeTo) */
        AND ZAN_DEP_CODE <= /*param.departmentCodeTo*/
      /*END*/

      /*IF param.billNoLike != null && !"".equals(param.billNoLike) */
        AND ZAN_SEI_NO  /*$param.billNoLike*/ -- LIKE
      /*END*/

      /*IF param.slipNoLike != null && !"".equals(param.slipNoLike) */
        AND ZAN_SEI_DEN_NO  /*$param.slipNoLike*/ -- LIKE
      /*END*/

      /*IF param.slipDate != null */
        AND ZAN_SEI_DEN_DATE = /*param.slipDate*/
      /*END*/

      /*IF param.expectedDay != null */
        AND ZAN_AR_DATE = /*param.expectedDay*/
      /*END*/
      
      /*IF param.currencyCode != null && !"".equals(param.currencyCode) */
        AND ZAN_CUR_CODE = /*param.currencyCode*/
      /*END*/

      /*IF param.notIncledEraseSlipNo != null && !"".equals(param.notIncledEraseSlipNo) */
        AND (ZAN_KESI_DEN_NO IS null OR ZAN_KESI_DEN_NO <> /*param.notIncledEraseSlipNo*/)
      /*END*/

    GROUP BY
      KAI_CODE,
      ZAN_TRI_CODE,
      ZAN_SEI_DEN_DATE,
      ZAN_SEI_DEN_NO,
      ZAN_SEI_GYO_NO,
      ZAN_SEI_NO,
      ZAN_KMK_CODE,
      ZAN_HKM_CODE,
      ZAN_UKM_CODE,
      ZAN_AR_DATE,
      ZAN_CUR_CODE,
      ZAN_CUR_RATE,
      ZAN_DEP_CODE
    HAVING
      SUM(ZAN_SEI_KIN) <> SUM(CASE WHEN ZAN_KESI_KIN IS NULL THEN 0 ELSE ZAN_KESI_KIN END)
) zan

 LEFT OUTER JOIN ENV_MST env
   ON zan.KAI_CODE = env.KAI_CODE
 
 INNER JOIN TRI_MST tri
   ON zan.KAI_CODE = tri.KAI_CODE
  AND zan.ZAN_TRI_CODE = tri.TRI_CODE
  /*IF param.customerNamesLike != null && !"".equals(param.customerNamesLike) */
    AND tri.TRI_NAME_S  /*$param.customerNamesLike*/ -- LIKE
  /*END*/

 LEFT OUTER JOIN BMN_MST bmn
   ON zan.KAI_CODE = bmn.KAI_CODE
  AND zan.ZAN_DEP_CODE = bmn.DEP_CODE

 INNER JOIN AR_SWK_HDR swkhdr
   ON zan.KAI_CODE = swkhdr.KAI_CODE
  AND zan.ZAN_SEI_DEN_DATE = swkhdr.SWK_DEN_DATE
  AND zan.ZAN_SEI_DEN_NO = swkhdr.SWK_DEN_NO

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

 WHERE 1 = 1
 /*IF param.departmentNameLike != null && !"".equals(param.departmentNameLike) */
   AND bmn.DEP_NAME_S  /*$param.departmentNameLike*/ -- LIKE
 /*END*/

 /*IF param.slipTekLike != null && !"".equals(param.slipTekLike) */
   AND swkhdr.SWK_TEK  /*$param.slipTekLike*/ -- LIKE
 /*END*/

ORDER BY
  ZAN_TRI_CODE,
  ZAN_SEI_DEN_DATE,
  ZAN_SEI_NO,
  ZAN_SEI_DEN_NO,
  ZAN_SEI_GYO_NO

