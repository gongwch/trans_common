 
 SELECT
  T1.CBK_CBK_CODE, 
  T2.BNK_NAME_S CBK_BNK_CODE, 
  T2.BNK_STN_NAME_S CBK_STN_CODE,
  T1.CBK_YKN_KBN,
  T1.CBK_YKN_NO
   
FROM 
 AP_CBK_MST T1,BNK_MST T2
 
WHERE 
 T1.KAI_CODE = /*kaiCode*/
 AND  T1.CBK_BNK_CODE = T2.BNK_CODE
 AND T1.CBK_STN_CODE = T2.BNK_STN_CODE 

 /*IF code != null  &&  code != ""*/
  	AND T1.CBK_CBK_CODE  /*$code*/ -- LIKE
 /*END*/
 /*IF nameS != null  &&  nameS != ""*/
  	AND (T2.BNK_NAME_S  /*$nameS*/  OR  T2.BNK_STN_NAME_S  /*$nameS*/) -- LIKE
 /*END*/
 /*IF nameK != null  &&  nameK != ""*/
  	AND (T1.CBK_YKN_KBN  /*$nameK*/ OR T1.CBK_YKN_NO  /*$nameK*/) -- LIKE
 /*END*/
 
 /*IF outKbn == 1 || outKbn == 0*/
  AND T1.CBK_OUT_FB_KBN = /*outKbn*/
 /*END*/
