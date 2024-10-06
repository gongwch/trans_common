SELECT
  swk.KAI_CODE,
  swk.SWK_DEN_DATE,
  swk.SWK_DEN_NO  ,
  swk.SWK_GYO_NO,
  swk.SWK_NENDO  ,
  swk.SWK_TUKIDO  ,
  swk.SWK_SEI_NO ,
  swk.SWK_DC_KBN,
  swk.SWK_KMK_CODE,
  swk.SWK_HKM_CODE,
  swk.SWK_UKM_CODE,
  swk.SWK_DEP_CODE,
  swk.SWK_ZEI_KBN,
  swk.SWK_KIN,
  swk.SWK_ZEI_KIN,
  swk.SWK_ZEI_CODE,
  swk.SWK_ZEI_RATE,
  swk.SWK_GYO_TEK_CODE,
  swk.SWK_GYO_TEK,
  swk.SWK_TRI_CODE,
  swk.SWK_EMP_CODE,
  swk.SWK_KNR_CODE_1,
  swk.SWK_KNR_CODE_2,
  swk.SWK_KNR_CODE_3,
  swk.SWK_KNR_CODE_4,
  swk.SWK_KNR_CODE_5,
  swk.SWK_KNR_CODE_6,
  swk.SWK_HM_1,
  swk.SWK_HM_2,
  swk.SWK_HM_3,
  swk.SWK_AUTO_KBN,
  swk.SWK_DATA_KBN,
  swk.SWK_UPD_KBN,
  swk.SWK_KSN_KBN,
  swk.SWK_AT_KMK_CODE,
  swk.SWK_AT_HKM_CODE,
  swk.SWK_AT_UKM_CODE,
  swk.SWK_AT_DEP_CODE,
  swk.INP_DATE,
  swk.UPD_DATE,
  swk.PRG_ID,
  swk.USR_ID,
  swk.SWK_K_KAI_CODE,
  swk.SWK_CUR_CODE,
  swk.SWK_CUR_RATE,
  swk.SWK_IN_KIN,
  swk.SWK_TUKE_KBN,
  swk.SWK_IN_ZEI_KIN,
  swk.SWK_KESI_KBN,
  swk.SWK_KESI_DATE,
  swk.SWK_KESI_DEN_NO,
  swk.SWK_SOUSAI_DATE,
  swk.SWK_SOUSAI_DEN_NO,
  swk.HAS_DATE,
  swk.DEN_SYU_CODE,
  env.KAI_NAME      SWK_K_KAI_NAME,
  env.KAI_NAME_S    SWK_K_KAI_NAME_S,
  emp.EMP_NAME      SWK_EMP_NAME,
  emp.EMP_NAME_S    SWK_EMP_NAME_S,
  dep.DEP_NAME      SWK_DEP_NAME,
  dep.DEP_NAME_S    SWK_DEP_NAME_S,
  kmk.KMK_NAME      SWK_KMK_NAME,
  kmk.KMK_NAME_S    SWK_KMK_NAME_S,
  hkm.HKM_NAME      SWK_HKM_NAME,
  hkm.HKM_NAME_S    SWK_HKM_NAME_S,
  ukm.UKM_NAME      SWK_UKM_NAME,
  ukm.UKM_NAME_S    SWK_UKM_NAME_S,
  cur.DEC_KETA      CUR_DEC_KETA,
  zei.ZEI_NAME      SWK_ZEI_NAME,
  zei.ZEI_NAME_S    SWK_ZEI_NAME_S,
  tri.TRI_NAME      SWK_TRI_NAME,
  tri.TRI_NAME_S    SWK_TRI_NAME_S,
  knr1.KNR_NAME_1   SWK_KNR_NAME_1,
  knr1.KNR_NAME_S_1 SWK_KNR_NAME_S_1,
  knr2.KNR_NAME_2   SWK_KNR_NAME_2,
  knr2.KNR_NAME_S_2 SWK_KNR_NAME_S_2,
  knr3.KNR_NAME_3   SWK_KNR_NAME_3,
  knr3.KNR_NAME_S_3 SWK_KNR_NAME_S_3,
  knr4.KNR_NAME_4   SWK_KNR_NAME_4,
  knr4.KNR_NAME_S_4 SWK_KNR_NAME_S_4,
  knr5.KNR_NAME_5   SWK_KNR_NAME_5,
  knr5.KNR_NAME_S_5 SWK_KNR_NAME_S_5,
  knr6.KNR_NAME_6   SWK_KNR_NAME_6,
  knr6.KNR_NAME_S_6 SWK_KNR_NAME_S_6,
  keyCur.CUR_CODE   KEY_CUR_CODE,         --  ŠîŽ²’Ê‰Ý
  keyCur.DEC_KETA   KEY_CUR_DEC_KETA,     --  ŠîŽ²’Ê‰Ý¬”“_Œ…”
  NULL SWK_IRAI_DEP_CODE,
  NULL SWK_IRAI_EMP_CODE
FROM SWK_PTN_DTL swk
  LEFT OUTER JOIN EMP_MST  emp   ON  swk.KAI_CODE       = emp.KAI_CODE
                                AND  swk.SWK_EMP_CODE   = emp.EMP_CODE
  LEFT OUTER JOIN ENV_MST  env   ON  swk.SWK_K_KAI_CODE = env.KAI_CODE
  LEFT OUTER JOIN BMN_MST  dep   ON  swk.KAI_CODE       = dep.KAI_CODE
                                AND  swk.SWK_DEP_CODE   = dep.DEP_CODE
  LEFT OUTER JOIN KMK_MST  kmk   ON  swk.KAI_CODE       = kmk.KAI_CODE
                                AND  swk.SWK_KMK_CODE   = kmk.KMK_CODE
  LEFT OUTER JOIN HKM_MST  hkm   ON  swk.KAI_CODE       = hkm.KAI_CODE
                                AND  swk.SWK_KMK_CODE   = hkm.KMK_CODE
                                AND  swk.SWK_HKM_CODE   = hkm.HKM_CODE
  LEFT OUTER JOIN UKM_MST  ukm   ON  swk.KAI_CODE       = ukm.KAI_CODE
                                AND  swk.SWK_KMK_CODE   = ukm.KMK_CODE
                                AND  swk.SWK_HKM_CODE   = ukm.HKM_CODE
                                AND  swk.SWK_UKM_CODE   = ukm.UKM_CODE
  LEFT OUTER JOIN SZEI_MST zei   ON  swk.KAI_CODE       = zei.KAI_CODE
                                AND  swk.SWK_ZEI_CODE   = zei.ZEI_CODE
  LEFT OUTER JOIN CUR_MST  cur   ON  swk.KAI_CODE       = cur.KAI_CODE
                                AND  swk.SWK_CUR_CODE   = cur.CUR_CODE
  LEFT OUTER JOIN TRI_MST  tri   ON  swk.KAI_CODE       = tri.KAI_CODE
                                AND  swk.SWK_TRI_CODE   = tri.TRI_CODE
  LEFT OUTER JOIN KNR1_MST knr1  ON  swk.KAI_CODE       = knr1.KAI_CODE
                                AND  swk.SWK_KNR_CODE_1 = knr1.KNR_CODE_1
  LEFT OUTER JOIN KNR2_MST knr2  ON  swk.KAI_CODE       = knr2.KAI_CODE
                                AND  swk.SWK_KNR_CODE_2 = knr2.KNR_CODE_2
  LEFT OUTER JOIN KNR3_MST knr3  ON  swk.KAI_CODE       = knr3.KAI_CODE
                                AND  swk.SWK_KNR_CODE_3 = knr3.KNR_CODE_3
  LEFT OUTER JOIN KNR4_MST knr4  ON  swk.KAI_CODE       = knr4.KAI_CODE
                                AND  swk.SWK_KNR_CODE_4 = knr4.KNR_CODE_4
  LEFT OUTER JOIN KNR5_MST knr5  ON  swk.KAI_CODE       = knr5.KAI_CODE
                                AND  swk.SWK_KNR_CODE_5 = knr5.KNR_CODE_5
  LEFT OUTER JOIN KNR6_MST knr6  ON  swk.KAI_CODE       = knr6.KAI_CODE
                                AND  swk.SWK_KNR_CODE_6 = knr6.KNR_CODE_6

  LEFT OUTER JOIN CMP_MST cmp
          ON swk.KAI_CODE = cmp.KAI_CODE
  LEFT OUTER JOIN CUR_MST keyCur
          ON cmp.KAI_CODE = keyCur.KAI_CODE
         AND cmp.CUR_CODE = keyCur.CUR_CODE
         
WHERE 1 = 1
  /*IF param.companyCode != null && !"".equals(param.companyCode)*/
    AND swk.KAI_CODE = /*param.companyCode*/
  /*END*/

  /*IF param.slipNo != null && !"".equals(param.slipNo)*/
    AND swk.SWK_DEN_NO = /*param.slipNo*/
  /*END*/

  /*IF param.slipNoList != null */
    AND swk.SWK_DEN_NO IN /*param.slipNoList*/()
  /*END*/

  /*IF param.slipNoLike != null && !"".equals(param.slipNoLike) */
    AND swk.SWK_DEN_NO /*$param.slipNoLike*/
  /*END*/
  
  /*IF param.slipNoFrom != null && !"".equals(param.slipNoFrom) */
    AND swk.SWK_DEN_NO >= /*param.slipNoFrom*/
  /*END*/
  
  /*IF param.slipNoTo != null && !"".equals(param.slipNoTo) */
    AND swk.SWK_DEN_NO <= /*param.slipNoTo*/
  /*END*/

  /*IF param.dataKindList != null */
    AND swk.SWK_DATA_KBN IN /*param.dataKindList*/()
  /*END*/

  /*IF param.slipTypeList != null */
    AND swk.DEN_SYU_CODE IN /*param.slipTypeList*/()
  /*END*/

  /*IF param.slipStateList != null */
    AND swk.SWK_UPD_KBN IN /*param.slipStateList*/()
  /*END*/

  /*IF param.slipDateFrom != null */
    AND swk.SWK_DEN_DATE >= /*param.slipDateFrom*/
  /*END*/

  /*IF param.slipDateTo != null */
    AND swk.SWK_DEN_DATE <= /*param.slipDateTo*/
  /*END*/
  
  /*IF param.autoDivision != -1 */
    AND swk.SWK_AUTO_KBN = /*param.autoDivision*/
  /*END*/

/*IF  param.order != null && !"".equals(param.order) */
  ORDER BY /*$param.order*/

 -- ELSE

  ORDER BY
    swk.KAI_CODE,
    swk.SWK_DEN_NO,
    swk.SWK_GYO_NO
/*END*/
 