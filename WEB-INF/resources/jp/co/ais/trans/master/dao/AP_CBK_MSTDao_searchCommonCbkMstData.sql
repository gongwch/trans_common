 
 SELECT 
  T1.CBK_CBK_CODE, 
  T1.CBK_NAME,
  T1.CBK_YKN_KBN,
  T1.CBK_YKN_NO
  
FROM 
 AP_CBK_MST T1
 
WHERE 
  T1.KAI_CODE = /*kaiCode*/  

 /*IF code != null  &&  code != ""*/
   AND T1.CBK_CBK_CODE  /*$code*/ -- LIKE
 /*END*/
 
 /*IF nameS != null  &&  nameS != ""*/
   AND T1.CBK_NAME  /*$nameS*/ -- LIKE
 /*END*/

 /*IF nameK != null  &&  nameK != ""*/
   AND (T1.CBK_YKN_KBN  /*$nameK*/ OR T1.CBK_YKN_NO  /*$nameK*/) -- LIKE
 /*END*/

 /*IF outKbn == true */
 AND T1.CBK_OUT_FB_KBN = 1
 /*END*/
 
 /*IF empKbn == true */
 AND T1.CBK_EMP_FB_KBN = 1
 /*END*/
 
 /*IF termBasisDate !=null && termBasisDate != "" */ 
  AND T1.STR_DATE <= /*termBasisDate*/
  AND T1.END_DATE >= /*termBasisDate*/
 /*END*/
