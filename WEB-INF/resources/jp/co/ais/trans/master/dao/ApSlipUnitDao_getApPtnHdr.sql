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
  -- ‚±‚±‚©‚çƒwƒbƒ_ ŠO•”Œ‹‡
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
  densyu.DEN_SYU_NAME_K           DEN_SYU_NAME_K

  FROM (SELECT * 
          FROM AP_SWK_PTN_HDR 
         WHERE KAI_CODE = /*param.kaiCode*/
         /*IF param.denDate  != null */                                       AND SWK_DEN_DATE = /*param.denDate*/         /*END*/
         /*IF param.sihaDate != null */                                       AND SWK_SIHA_DATE = /*param.sihaDate*/        /*END*/
         /*IF !"".equals(param.denNo) && param.denNo != null */               AND SWK_DEN_NO   = /*param.denNo*/           /*END*/
         /*IF !"".equals(param.empCode) && param.empCode != null */           AND SWK_EMP_CODE = /*param.empCode*/         /*END*/
         /*IF !"".equals(param.iraiEmpCodeAndNull) && param.iraiEmpCodeAndNull != null */    
             AND (SWK_IRAI_EMP_CODE = /*param.iraiEmpCodeAndNull*/ OR SWK_IRAI_EMP_CODE IS NULL )  
         /*END*/
         /*IF !"".equals(param.iraiEmpCode) && param.iraiEmpCode != null */   AND SWK_IRAI_EMP_CODE = /*param.iraiEmpCode*/ /*END*/
         /*IF !"".equals(param.iraiDepCode) && param.iraiDepCode != null */   AND SWK_IRAI_DEP_CODE = /*param.iraiDepCode*/      /*END*/
         /*IF !"".equals(param.likeDenNo) && param.likeDenNo != null */       AND SWK_DEN_NO   /*$param.likeDenNo*/    /*END*/ -- LIKE
         /*IF !"".equals(param.likeDenTek) && param.likeDenTek != null */     AND SWK_TEK      /*$param.likeDenTek*/   /*END*/ -- LIKE
         /*IF !"".equals(param.likeSihaKin) && param.likeSihaKin != null */   AND SWK_SIHA_KIN LIKE /*param.likeSihaKin*/  /*END*/
         /*IF param.dataKbn != null */                                        AND SWK_DATA_KBN   IN /*param.dataKbn*/()    /*END*/
         /*IF param.updKbn != null */                                         AND SWK_UPD_KBN    IN /*param.updKbn*/()     /*END*/
         /*IF param.kesaiKbn != null */                                       AND SWK_KESAI_KBN  IN /*param.kesaiKbn*/()     /*END*/
         /*IF !"".equals(param.shrKbn) && param.shrKbn != null */
            AND SWK_SHR_KBN = /*param.shrKbn*/  
         /*END*/
         /*IF param.denDateBegin  != null */                                  AND SWK_DEN_DATE       >= /*param.denDateBegin*/   /*END*/
         /*IF param.denDateEnd != null */                                     AND SWK_DEN_DATE       <= /*param.denDateEnd*/     /*END*/
         /*IF param.sihaDateBegin  != null */                                 AND SWK_SIHA_DATE      >= /*param.sihaDateBegin*/  /*END*/
         /*IF param.sihaDateEnd != null */                                    AND SWK_SIHA_DATE      <= /*param.sihaDateEnd*/    /*END*/
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
  WHERE '1' = '1'
  /*IF !"".equals(param.empCodeLike) && param.empCodeLike != null */         AND  iraiEmp.EMP_NAME   /*$param.empCodeLike*/   /*END*/ -- LIKE
  /*IF !"".equals(param.depCodeLike) && param.depCodeLike != null */         AND  bmn.DEP_NAME_S   /*$param.depCodeLike*/   /*END*/ -- LIKE
  /*IF !"".equals(param.hohNaiCode) && param.hohNaiCode != null */           AND  hoh.HOH_NAI_CODE = /*param.hohNaiCode*/         /*END*/
  /*IF !"".equals(param.likeEmpName) && param.likeEmpName != null */         AND  emp.EMP_NAME   /*$param.likeEmpName*/   /*END*/ -- LIKE
  /*IF !"".equals(param.orderBy) && param.orderBy != null */                 ORDER BY  /*$param.orderBy*/                          /*END*/