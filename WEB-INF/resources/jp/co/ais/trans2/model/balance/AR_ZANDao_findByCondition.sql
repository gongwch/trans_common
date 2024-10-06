SELECT
  zan.*,
  env.KAI_NAME_S KAI_NAME_S,
  tri.TRI_NAME_S ZAN_TRI_NAME_S,
  kmk.KMK_NAME_S ZAN_KMK_NAME_S,
  hkm.HKM_NAME_S ZAN_HKM_NAME_S,
  ukm.UKM_NAME_S ZAN_UKM_NAME_S,
  cur.DEC_KETA ZAN_CUR_DEC_KETA,
  bmn.DEP_NAME_S ZAN_DEP_NAME_S,
  swkhdr.SWK_TEK ZAN_TEK,
  cur.RATE_POW ZAN_CUR_RATE_POW

FROM AR_ZAN zan
 LEFT OUTER JOIN ENV_MST env
   ON zan.KAI_CODE = env.KAI_CODE
 
 LEFT OUTER JOIN TRI_MST tri
   ON zan.KAI_CODE = tri.KAI_CODE
  AND zan.ZAN_TRI_CODE = tri.TRI_CODE
  
 LEFT OUTER JOIN BMN_MST bmn
   ON zan.KAI_CODE = bmn.KAI_CODE
  AND zan.ZAN_DEP_CODE = bmn.DEP_CODE

 LEFT OUTER  JOIN AR_SWK_HDR swkhdr
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

WHERE zan.KAI_CODE = /*param.companyCode*/

  /*IF param.eraseSlipNo != null && !"".equals(param.eraseSlipNo) */
    AND zan.ZAN_KESI_DEN_NO = /*param.eraseSlipNo*/
  /*END*/

  /*IF param.slipTekLike != null && !"".equals(param.slipTekLike) */
    AND swkhdr.SWK_TEK  /*$param.slipTekLike*/ -- LIKE
  /*END*/

  /*IF param.departmentNameLike != null && !"".equals(param.departmentNameLike) */
    AND bmn.DEP_NAME_S  /*$param.departmentNameLike*/ -- LIKE
  /*END*/
