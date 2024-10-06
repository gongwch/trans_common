SELECT 
  hdr.KAI_CODE                    KAI_CODE,
  hdr.SWK_DEN_DATE                DEN_DATE,
  hdr.SWK_DEN_NO                  DEN_NO  ,
  hdr.SWK_KMK_CODE                HDR_KMK_CODE,
  hdr.SWK_HKM_CODE                HDR_HKM_CODE,
  hdr.SWK_UKM_CODE                HDR_UKM_CODE,
  hdr.SWK_DEP_CODE                HDR_DEP_CODE,
  hdr.SWK_TRI_CODE                HDR_TRI_CODE,
  hdr.SWK_EMP_CODE                HDR_EMP_CODE,
  hdr.SWK_SIHA_KBN                HDR_SIHA_KBN,
  hdr.SWK_SIHA_DATE               HDR_SIHA_DATE,
  hdr.SWK_HOH_CODE                HDR_HOH_CODE,
  hdr.SWK_HORYU_KBN               HDR_HORYU_KBN,
  hdr.SWK_KARI_KIN                HDR_KARI_KIN,
  hdr.SWK_KEIHI_KIN               HDR_KEIHI_KIN,
  hdr.SWK_SIHA_KIN                HDR_SIHA_KIN,
  hdr.SWK_KARI_DR_DEN_NO          HDR_KARI_DR_DEN_NO,
  hdr.SWK_KARI_CR_DEN_NO          HDR_KARI_CR_DEN_NO,
  hdr.SWK_KESAI_KBN               HDR_KESAI_KBN,
  hdr.SWK_SEI_NO                  SEI_NO,
  hdr.SWK_DATA_KBN                DATA_KBN,
  hdr.SWK_UKE_DEP_CODE            HDR_UKE_DEP_CODE,
  hdr.SWK_TEK_CODE                HDR_TEK_CODE,
  hdr.SWK_TEK                     HDR_TEK,
  hdr.SWK_BEFORE_DEN_NO           HDR_BEFORE_DEN_NO,
  hdr.SWK_UPD_KBN                 UPD_KBN,
  hdr.SWK_SYO_EMP_CODE            HDR_SYO_EMP_CODE,
  hdr.SWK_SYO_DATE                HDR_SYO_DATE,
  hdr.SWK_IRAI_EMP_CODE           HDR_IRAI_EMP_CODE,
  hdr.SWK_IRAI_DEP_CODE           HDR_IRAI_DEP_CODE,
  hdr.SWK_IRAI_DATE               HDR_IRAI_DATE,
  hdr.SWK_KARI_DEP_CODE           HDR_KARI_DEP_CODE,
  hdr.SWK_SHR_KBN                 HDR_SHR_KBN,
  hdr.SWK_KSN_KBN                 KSN_KBN,
  hdr.PRG_ID                      PRG_ID,
  hdr.USR_ID                      USR_ID,
  hdr.SWK_TJK_CODE                HDR_TJK_CODE ,
  hdr.SWK_CUR_CODE                HDR_CUR_CODE ,
  hdr.SWK_CUR_RATE                HDR_CUR_RATE ,
  hdr.SWK_IN_KARI_KIN             HDR_IN_KARI_KIN ,
  hdr.SWK_IN_KEIHI_KIN            HDR_IN_KEIHI_KIN ,
  hdr.SWK_IN_SIHA_KIN             HDR_IN_SIHA_KIN ,
  hdr.SWK_SYS_KBN                 HDR_SYS_KBN ,
  hdr.SWK_DEN_SYU                 DEN_SYU ,
  hdr.SWK_TUKE_KBN                HDR_TUKE_KBN,
  hdr.SWK_UPD_CNT                 HDR_UPD_CNT,
  hdr.SWK_INV_CUR_CODE            HDR_INV_CUR_CODE ,
  hdr.SWK_INV_CUR_RATE            HDR_INV_CUR_RATE ,
  hdr.SWK_INV_KIN                 HDR_INV_KIN ,
  hdr.SWK_CBK_CODE                HDR_CBK_CODE,
  hdr.SWK_SSY_DATE                HDR_SSY_DATE,
  hdr.SWK_UTK_NO                  HDR_UTK_NO,
  hdr.SWK_KARI_CUR_CODE           HDR_KARI_CUR_CODE ,
  hdr.SWK_KARI_CUR_RATE           HDR_KARI_CUR_RATE,
  -- ここからヘッダ 外部結合
  kmk.KMK_NAME                    HDR_KMK_NAME,
  hkm.HKM_NAME                    HDR_HKM_NAME,
  ukm.UKM_NAME                    HDR_UKM_NAME,
  Kmk.KMK_NAME_S                  HDR_KMK_NAME_S,
  hkm.HKM_NAME_S                  HDR_HKM_NAME_S,
  ukm.UKM_NAME_S                  HDR_UKM_NAME_S,
  hoh.HOH_HOH_NAME                HDR_HOH_NAME,
  hoh.HOH_NAI_CODE                HDR_HOH_NAI_CODE,
  env.KAI_NAME                    KAI_NAME,
  env.KAI_NAME_S                  KAI_NAME_S,
  cmp.CUR_CODE                    BASE_CUR_CODE,
  cmp.CMP_KMK_NAME                CMP_KMK_NAME,
  cmp.CMP_HKM_NAME                CMP_HKM_NAME,
  cmp.CMP_UKM_NAME                CMP_UMK_NAME,
  cur.DEC_KETA                    BASE_CUR_DEC_KETA,
  bmn.DEP_NAME                    HDR_IRAI_DEP_NAME,
  bmn.DEP_NAME_S                  HDR_IRAI_DEP_NAME_S,
  iraiEmp.EMP_NAME                HDR_IRAI_EMP_NAME,
  iraiEmp.EMP_NAME_S              HDR_IRAI_EMP_NAME_S,
  emp.EMP_NAME                    HDR_EMP_NAME,
  emp.EMP_NAME_S                  HDR_EMP_NAME_S,
  cbk.CBK_YKN_KBN                 HDR_CBK_YKN_KBN,
  bnk.BNK_NAME_S                  HDR_CBK_BANK_NAME,
  bnk.BNK_STN_NAME_S              HDR_CBK_BANK_STN_NAME,
  densyu.DEN_SYU_NAME_K           DEN_SYU_NAME_K,
  -- ここから仕訳DTL
  dtl.SWK_GYO_NO,
  dtl.SWK_NENDO  ,
  dtl.SWK_TUKIDO  ,
  dtl.SWK_DC_KBN,
  dtl.SWK_KMK_CODE,
  dtl.SWK_HKM_CODE,
  dtl.SWK_UKM_CODE,
  dtl.SWK_DEP_CODE,
  dtl.SWK_ZEI_KBN,
  dtl.SWK_KIN,
  dtl.SWK_ZEI_KIN,
  dtl.SWK_ZEI_CODE,
  (SELECT ZEI_NAME_S FROM SZEI_MST WHERE KAI_CODE = dtl.KAI_CODE AND ZEI_CODE = dtl.SWK_ZEI_CODE) AS SWK_ZEI_NAME, 
  dtl.SWK_ZEI_RATE,
  dtl.SWK_GYO_TEK_CODE,
  dtl.SWK_GYO_TEK,
  dtl.SWK_TRI_CODE,
  dtl.SWK_EMP_CODE,
  dtl.SWK_KNR_CODE_1,
  dtl.SWK_KNR_CODE_2,
  dtl.SWK_KNR_CODE_3,
  dtl.SWK_KNR_CODE_4,
  dtl.SWK_KNR_CODE_5,
  dtl.SWK_KNR_CODE_6,
  dtl.SWK_HM_1,
  dtl.SWK_HM_2,
  dtl.SWK_HM_3,
  dtl.SWK_AUTO_KBN,
  dtl.SWK_AT_KMK_CODE,
  dtl.SWK_AT_HKM_CODE,
  dtl.SWK_AT_UKM_CODE,
  dtl.SWK_AT_DEP_CODE,
  dtl.SWK_K_KAI_CODE,
  (SELECT KAI_NAME_S FROM ENV_MST WHERE KAI_CODE = DTL.SWK_K_KAI_CODE) AS SWK_K_KAI_NAME,
  dtl.SWK_CUR_CODE,
  dtl.SWK_CUR_RATE,
  dtl.SWK_IN_KIN,
  dtl.SWK_TUKE_KBN,
  dtl.SWK_IN_ZEI_KIN,
  dtl.SWK_KESI_KBN,
  dtl.SWK_KESI_DATE,
  dtl.SWK_KESI_DEN_NO,
  dtl.SWK_SOUSAI_DATE,
  dtl.SWK_SOUSAI_DEN_NO,
  dtl.HAS_DATE  AS SWK_HAS_DATE,
  -- ここから仕訳DTL 外部結合
  dtlKmk.KMK_NAME                 SWK_KMK_NAME,
  dtlHkm.HKM_NAME                 SWK_HKM_NAME,
  dtlUkm.UKM_NAME                 SWK_UKM_NAME,
  dtlKmk.KMK_NAME_S               SWK_KMK_NAME_S,
  dtlHkm.HKM_NAME_S               SWK_HKM_NAME_S,
  dtlUkm.UKM_NAME_S               SWK_UKM_NAME_S,
  tri.TRI_NAME                    SWK_TRI_NAME,
  tri.TRI_NAME_S                  SWK_TRI_NAME_S,
  dtlEmp.EMP_NAME                 SWK_EMP_NAME,
  dtlEmp.EMP_NAME_S               SWK_EMP_NAME_S,
  knr1.KNR_NAME_1                 SWK_KNR_NAME_1,
  knr2.KNR_NAME_2                 SWK_KNR_NAME_2,
  knr3.KNR_NAME_3                 SWK_KNR_NAME_3,
  knr4.KNR_NAME_4                 SWK_KNR_NAME_4,
  knr5.KNR_NAME_5                 SWK_KNR_NAME_5,
  knr6.KNR_NAME_6                 SWK_KNR_NAME_6,
  knr1.KNR_NAME_S_1               SWK_KNR_NAME_S_1,
  knr2.KNR_NAME_S_2               SWK_KNR_NAME_S_2,
  knr3.KNR_NAME_S_3               SWK_KNR_NAME_S_3,
  knr4.KNR_NAME_S_4               SWK_KNR_NAME_S_4,
  knr5.KNR_NAME_S_5               SWK_KNR_NAME_S_5,
  knr6.KNR_NAME_S_6               SWK_KNR_NAME_S_6,
  dtlCur.DEC_KETA                 SWK_CUR_DEC_KETA, 
  bmn.DEP_NAME_S                  SWK_DEP_NAME_S
  FROM (SELECT * 
          FROM AP_SWK_PTN_HDR 
         WHERE KAI_CODE = /*param.kaiCode*/
         /*IF param.denDate  != null */                                       AND SWK_DEN_DATE = /*param.denDate*/         /*END*/
         /*IF param.sihaDate != null */                                       AND SWK_SIHA_DATE = /*param.sihaDate*/        /*END*/
         /*IF !"".equals(param.denNo) && param.denNo != null */               AND SWK_DEN_NO   = /*param.denNo*/           /*END*/
         /*IF !"".equals(param.empCode) && param.empCode != null */           AND SWK_EMP_CODE = /*param.empCode*/         /*END*/
         /*IF !"".equals(param.likeDenNo) && param.likeDenNo != null */       AND SWK_DEN_NO   /*$param.likeDenNo*/    /*END*/ -- LIKE
         /*IF !"".equals(param.likeDenTek) && param.likeDenTek != null */     AND SWK_TEK      /*$param.likeDenTek*/   /*END*/ -- LIKE
         /*IF !"".equals(param.likeSihaKin) && param.likeSihaKin != null */   AND SWK_SIHA_KIN LIKE /*param.likeSihaKin*/  /*END*/
         /*IF param.dataKbn != null */                                        AND SWK_DATA_KBN   IN /*param.dataKbn*/()    /*END*/
         /*IF param.updKbn != null */                                         AND SWK_UPD_KBN    IN /*param.updKbn*/()     /*END*/
         /*IF param.kesaiKbn != null */                                       AND SWK_KESAI_KBN  IN /*param.kesaiKbn*/()     /*END*/
         /*IF !"".equals(param.shrKbn) && param.shrKbn != null */
            AND SWK_SHR_KBN = /*param.shrKbn*/ 
         /*END*/
       ) hdr 

  LEFT OUTER JOIN DEN_SYU_MST densyu ON hdr.KAI_CODE     = densyu.KAI_CODE
                                    AND hdr.SWK_DEN_SYU  = densyu.DEN_SYU_CODE
  LEFT OUTER JOIN KMK_MST kmk        ON hdr.KAI_CODE     = kmk.KAI_CODE
                                    AND hdr.SWK_KMK_CODE = kmk.KMK_CODE
  LEFT OUTER JOIN HKM_MST hkm        ON hdr.KAI_CODE     = hkm.KAI_CODE
                                    AND hdr.SWK_KMK_CODE = hkm.KMK_CODE
                                    AND hdr.SWK_HKM_CODE = hkm.HKM_CODE
  LEFT OUTER JOIN UKM_MST ukm        ON hdr.KAI_CODE     = ukm.KAI_CODE
                                    AND hdr.SWK_KMK_CODE = ukm.KMK_CODE
                                    AND hdr.SWK_HKM_CODE = ukm.HKM_CODE
                                    AND hdr.SWK_UKM_CODE = ukm.UKM_CODE
  LEFT OUTER JOIN AP_HOH_MST hoh     ON hdr.KAI_CODE     = hoh.KAI_CODE
                                    AND hdr.SWK_HOH_CODE = hoh.HOH_HOH_CODE
  LEFT OUTER JOIN ENV_MST env        ON hdr.KAI_CODE     = env.KAI_CODE
  LEFT OUTER JOIN CMP_MST cmp        ON hdr.KAI_CODE     = cmp.KAI_CODE
  INNER JOIN      CUR_MST cur        ON cmp.KAI_CODE     = cur.KAI_CODE
                                    AND cmp.CUR_CODE     = cur.CUR_CODE
  LEFT OUTER JOIN BMN_MST bmn        ON hdr.KAI_CODE     = bmn.KAI_CODE
                                    AND hdr.SWK_IRAI_DEP_CODE = bmn.DEP_CODE
  LEFT OUTER JOIN EMP_MST iraiEmp    ON hdr.KAI_CODE     = iraiEmp.KAI_CODE
                                    AND hdr.SWK_IRAI_EMP_CODE = iraiEmp.EMP_CODE
  LEFT OUTER JOIN EMP_MST emp        ON hdr.KAI_CODE     = emp.KAI_CODE
                                    AND hdr.SWK_EMP_CODE = emp.EMP_CODE
  LEFT OUTER JOIN AP_CBK_MST cbk     ON hdr.KAI_CODE     = cbk.KAI_CODE
                                    AND hdr.SWK_CBK_CODE = cbk.CBK_CBK_CODE
  LEFT OUTER JOIN BNK_MST    bnk     ON cbk.CBK_BNK_CODE = bnk.BNK_CODE  
                                    AND cbk.CBK_STN_CODE = bnk.BNK_STN_CODE
  INNER JOIN  SWK_PTN_DTL    dtl     ON hdr.KAI_CODE     = dtl.KAI_CODE
                                    AND hdr.SWK_DEN_DATE = dtl.SWK_DEN_DATE
                                    AND hdr.SWK_DEN_NO   = dtl.SWK_DEN_NO
  LEFT OUTER JOIN KMK_MST dtlKmk     ON dtl.KAI_CODE     = dtlKmk.KAI_CODE
                                    AND dtl.SWK_KMK_CODE = dtlKmk.KMK_CODE
  LEFT OUTER JOIN HKM_MST dtlHkm     ON dtl.KAI_CODE     = dtlHkm.KAI_CODE
                                    AND dtl.SWK_KMK_CODE = dtlHkm.KMK_CODE
                                    AND dtl.SWK_HKM_CODE = dtlHkm.HKM_CODE
  LEFT OUTER JOIN UKM_MST dtlUkm     ON dtl.KAI_CODE     = dtlUkm.KAI_CODE
                                    AND dtl.SWK_KMK_CODE = dtlUkm.KMK_CODE
                                    AND dtl.SWK_HKM_CODE = dtlUkm.HKM_CODE
                                    AND dtl.SWK_UKM_CODE = dtlUkm.UKM_CODE
  LEFT OUTER JOIN TRI_MST tri        ON dtl.KAI_CODE     = tri.KAI_CODE
                                    AND dtl.SWK_TRI_CODE = tri.TRI_CODE
  LEFT OUTER JOIN EMP_MST dtlEmp     ON dtl.KAI_CODE     = dtlEmp.KAI_CODE
                                    AND dtl.SWK_EMP_CODE = dtlEmp.EMP_CODE
  LEFT OUTER JOIN KNR1_MST knr1      ON dtl.KAI_CODE       = knr1.KAI_CODE
                                    AND dtl.SWK_KNR_CODE_1 = knr1.KNR_CODE_1
  LEFT OUTER JOIN KNR2_MST knr2      ON dtl.KAI_CODE       = knr2.KAI_CODE
                                    AND dtl.SWK_KNR_CODE_2 = knr2.KNR_CODE_2
  LEFT OUTER JOIN KNR3_MST knr3      ON dtl.KAI_CODE       = knr3.KAI_CODE
                                    AND dtl.SWK_KNR_CODE_3 = knr3.KNR_CODE_3
  LEFT OUTER JOIN KNR4_MST knr4      ON dtl.KAI_CODE       = knr4.KAI_CODE
                                    AND dtl.SWK_KNR_CODE_4 = knr4.KNR_CODE_4
  LEFT OUTER JOIN KNR5_MST knr5      ON dtl.KAI_CODE       = knr5.KAI_CODE
                                    AND dtl.SWK_KNR_CODE_5 = knr5.KNR_CODE_5
  LEFT OUTER JOIN KNR6_MST knr6      ON dtl.KAI_CODE       = knr6.KAI_CODE
                                    AND dtl.SWK_KNR_CODE_6 = knr6.KNR_CODE_6
  INNER JOIN       CUR_MST dtlCur       ON dtl.KAI_CODE  = dtlCur.KAI_CODE
                                    AND dtl.SWK_CUR_CODE = dtlCur.CUR_CODE
  WHERE '1' = '1'
  /*IF !"".equals(param.empCodeLike) && param.empCodeLike != null */         AND  iraiEmp.EMP_NAME   /*$param.empCodeLike*/   /*END*/ -- LIKE
  /*IF !"".equals(param.depCodeLike) && param.depCodeLike != null */         AND  bmn.DEP_NAME_S   /*$param.depCodeLike*/   /*END*/ -- LIKE                                  
  /*IF !"".equals(param.hohNaiCode) && param.hohNaiCode != null */           AND  hoh.HOH_NAI_CODE = /*param.hohNaiCode*/         /*END*/
  /*IF !"".equals(param.likeEmpName) && param.likeEmpName != null */         AND  emp.EMP_NAME   /*$param.likeEmpName*/   /*END*/ -- LIKE
  /*IF !"".equals(param.orderBy) && param.orderBy != null */                 ORDER BY  /*$param.orderBy*/                          /*END*/