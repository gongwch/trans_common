SELECT *
FROM AP_HOH_MST 
WHERE KAI_CODE = /*param.kaiCode*/
/*IF !"".equals(param.hohSihKbn) && param.hohSihKbn != null */         AND HOH_SIH_KBN   = /*param.hohSihKbn*/        /*END*/
/*IF !"".equals(param.notHohNaiCode) && param.notHohNaiCode != null */ AND HOH_NAI_CODE <> /*param.notHohNaiCode*/    /*END*/
/*IF param.hohNaiCode != null */                                       AND HOH_NAI_CODE IN /*param.hohNaiCode*/()     /*END*/            
/*IF !"".equals(param.hohCode) && param.hohCode != null */             AND HOH_HOH_CODE  = /*param.hohCode*/          /*END*/
/*IF !"".equals(param.likeHohCode) && param.likeHohCode != null */     AND HOH_HOH_CODE  /*$param.likeHohCode*/    /*END*/ -- LIKE
/*IF !"".equals(param.likeHohName) && param.likeHohName != null */     AND HOH_HOH_NAME  /*$param.likeHohName*/    /*END*/ -- LIKE
/*IF !"".equals(param.likeHohNameK) && param.likeHohNameK != null */   AND HOH_HOH_NAME_K  /*$param.likeHohNameK*/ /*END*/ -- LIKE
/*IF param.termBasisDate != null */
   AND STR_DATE <= /*param.termBasisDate*/
   AND END_DATE >= /*param.termBasisDate*/
/*END*/
/*IF param.hohCodes != null*/
  AND HOH_HOH_CODE IN /*param.hohCodes*/() 
/*END*/
ORDER BY HOH_HOH_CODE