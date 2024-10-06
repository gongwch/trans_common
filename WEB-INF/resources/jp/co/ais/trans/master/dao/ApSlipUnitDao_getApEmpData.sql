SELECT 
  hdr.KAI_CODE                    KAI_CODE,
  hdr.SWK_DEN_DATE                DEN_DATE,
  hdr.SWK_DEN_NO                  DEN_NO  ,
  hdr.SWK_KMK_CODE                HDR_KMK_CODE,
  hdr.SWK_HKM_CODE                HDR_HKM_CODE,
  hdr.SWK_UKM_CODE                HDR_UKM_CODE,
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
  env.KAI_NAME                    KAI_NAME,
  env.KAI_NAME_S                  KAI_NAME_S,
  cmp.CUR_CODE                    BASE_CUR_CODE,
  cur.DEC_KETA                    BASE_CUR_DEC_KETA,
  emp.EMP_NAME                    HDR_EMP_NAME,
  emp.EMP_NAME_S                  HDR_EMP_NAME_S,
  dep.DEP_CODE                    HDR_DEP_CODE,
  dep.DEP_NAME                    HDR_DEP_NAME,
  dep.DEP_NAME_S                  HDR_DEP_NAME_S,
  hdrcur.DEC_KETA                 HDR_CUR_DEC_KETA

  FROM (SELECT * 
          FROM AP_SWK_HDR 
         WHERE KAI_CODE = /*param.kaiCode*/
         /*IF param.dataKbn != null */           AND SWK_DATA_KBN       IN /*param.dataKbn*/()      /*END*/
         /*IF param.sihaDateBegin  != null */    AND SWK_SIHA_DATE      >= /*param.sihaDateBegin*/  /*END*/
         /*IF param.sihaDateEnd != null */       AND SWK_SIHA_DATE      <= /*param.sihaDateEnd*/    /*END*/
         /*IF !"".equals(param.empCode) && param.empCode != null */  AND SWK_EMP_CODE = /*param.empCode*/  /*END*/
         /*IF !"".equals(param.depCode) && param.depCode != null */ 
            AND SWK_EMP_CODE IN (SELECT EMP_CODE FROM USR_MST WHERE DEP_CODE = /*param.depCode*/)
         /*END*/
       ) hdr 

  LEFT OUTER JOIN ENV_MST env        ON hdr.KAI_CODE     = env.KAI_CODE
  LEFT OUTER JOIN CMP_MST cmp        ON hdr.KAI_CODE     = cmp.KAI_CODE
  INNER JOIN      CUR_MST cur        ON cmp.KAI_CODE     = cur.KAI_CODE
                                    AND cmp.CUR_CODE     = cur.CUR_CODE
  LEFT OUTER JOIN EMP_MST emp        ON hdr.KAI_CODE     = emp.KAI_CODE
                                    AND hdr.SWK_EMP_CODE = emp.EMP_CODE
  INNER JOIN      CUR_MST hdrcur     ON hdr.KAI_CODE     = hdrcur.KAI_CODE
                                    AND hdr.SWK_CUR_CODE = hdrcur.CUR_CODE
  LEFT OUTER JOIN USR_MST usr        ON hdr.KAI_CODE     = usr.KAI_CODE
                                    AND hdr.SWK_EMP_CODE = usr.EMP_CODE
  LEFT OUTER JOIN BMN_MST dep        ON usr.KAI_CODE     = dep.KAI_CODE
                                    AND usr.DEP_CODE     = dep.DEP_CODE
  WHERE '1' = '1'
  /*IF !"".equals(param.orderBy) && param.orderBy != null */                 ORDER BY  /*$param.orderBy*/                         /*END*/
 