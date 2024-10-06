 
 SELECT
  cbk.KAI_CODE, 
  cbk.CBK_CBK_CODE, 
  cbk.CBK_NAME,
  cbk.CBK_YKN_KBN,
  cbk.CBK_YKN_NO,
  cbk.CBK_DEP_CODE,
  cbk.CBK_KMK_CODE,
  cbk.CBK_HKM_CODE,
  cbk.CBK_UKM_CODE,
  cbk.CBK_UKM_CODE,
  cbk.CUR_CODE,
  cbk.CBK_EMP_FB_KBN,
  cbk.CBK_OUT_FB_KBN,
  cbk.STR_DATE,
  cbk.END_DATE,
  bnk.BNK_NAME_S,
  bnk.BNK_STN_NAME_S
  
FROM 
  AP_CBK_MST cbk
  LEFT OUTER JOIN BNK_MST  bnk  ON cbk.CBK_BNK_CODE = bnk.BNK_CODE  
                               AND cbk.CBK_STN_CODE = bnk.BNK_STN_CODE
 
WHERE 
  cbk.KAI_CODE = /*param.kaiCode*/
/*IF !"".equals(param.cbkCode) && param.cbkCode != null */
     AND cbk.CBK_CBK_CODE = /*param.cbkCode*/
/*END*/
/*IF !"".equals(param.likeCbkCode) && param.likeCbkCode != null */
     AND cbk.CBK_CBK_CODE  /*$param.likeCbkCode*/ -- LIKE
/*END*/

/*IF !"".equals(param.likeNameS) && param.likeNameS != null */
    AND CONCAT(NVL(bnk.BNK_NAME_S,''), CONCAT(' ', NVL(bnk.BNK_STN_NAME_S,'')))  /*$param.likeNameS*/ -- LIKE
/*END*/

/*IF !"".equals(param.likeCbkYknNo) && param.likeCbkYknNo != null */
    AND cbk.CBK_YKN_NO  /*$param.likeCbkYknNo*/ -- LIKE
/*END*/

/*IF param.outFbKbn == true */
 AND cbk.CBK_OUT_FB_KBN = 1
/*END*/
 
/*IF param.empFbKbn == true */
 AND cbk.CBK_EMP_FB_KBN = 1
/*END*/
 
/*IF param.termBasisDate != null */
  AND cbk.STR_DATE <= /*param.termBasisDate*/
  AND cbk.END_DATE >= /*param.termBasisDate*/
/*END*/

/*IF param.cbkCodes != null*/
  AND cbk.CBK_CBK_CODE IN /*param.cbkCodes*/() 
/*END*/
