SELECT KMK_NAME_S,
       HKM_KBN,
       URI_ZEI_FLG, 
       SIR_ZEI_FLG,
       ZEI_CODE,
       TRI_CODE_FLG,
       EMP_CODE_FLG,
       KNR_FLG_1,
       KNR_FLG_2,
       KNR_FLG_3,
       KNR_FLG_4,
       KNR_FLG_5,
       KNR_FLG_6,
       HM_FLG_1,
       HM_FLG_2,
       HM_FLG_3,
       HAS_FLG,
       MCR_FLG
FROM KMK_MST 
WHERE KAI_CODE = /*kaiCode*/
/*IF (!"".equals(kmkCode)) && kmkCode != null*/ AND KMK_CODE = /*kmkCode*/ /*END*/
/*IF (!"".equals(sumKbn)) && sumKbn != null*/ AND SUM_KBN = STR_TO_INT(/*sumKbn*/) /*END*/
/*IF (!"".equals(kesiKbn)) && kesiKbn != null*/ AND KESI_KBN = STR_TO_INT(/*kesiKbn*/) /*END*/
/*IF (!"".equals(bmnCode)) && bmnCode != null*/    AND (KOTEI_DEP_CODE IS null OR KOTEI_DEP_CODE = /*bmnCode*/) /*END*/
/*IF (!"".equals(slipDate)) && slipDate != null*/ AND STR_DATE <= /*slipDate*/ /*END*/
/*IF (!"".equals(slipDate)) && slipDate != null*/ AND END_DATE >= /*slipDate*/ /*END*/
/*IF ("0".equals(excFlg)) && excFlg != null*/ AND EXC_FLG = 0 /*END*/
/*IF (!"0".equals(excFlg)) && excFlg != null && (!"".equals(excFlg))*/ AND EXC_FLG <> 0 /*END*/
/*IF (!"".equals(nyuKin)) && nyuKin != null*/ AND GL_FLG_1 = STR_TO_INT(/*nyuKin*/) /*END*/
/*IF (!"".equals(shutsuKin)) && shutsuKin != null*/ AND GL_FLG_2 = STR_TO_INT(/*shutsuKin*/) /*END*/
/*IF (!"".equals(furikaeFlg)) && furikaeFlg != null*/AND GL_FLG_3 = STR_TO_INT(/*furikaeFlg*/)  /*END*/
/*IF (!"".equals(kmkCntGl)) && kmkCntGl != null*/ AND KMK_CNT_GL = /*kmkCntGl*/ /*END*/
/*IF (!"".equals(kmkCntAp)) && kmkCntAp != null*/ AND KMK_CNT_AP = /*kmkCntAp*/ /*END*/
/*IF (!"".equals(kmkCntAr)) && kmkCntAr != null*/ AND KMK_CNT_AR = /*kmkCntAr*/ /*END*/
/*IF (!"".equals(kmkCntUnAr)) && kmkCntUnAr != null*/ AND KMK_CNT_AR <> /*kmkCntUnAr*/ /*END*/
