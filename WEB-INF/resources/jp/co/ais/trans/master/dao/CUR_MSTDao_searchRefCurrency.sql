SELECT 
 CUR_CODE,
 CUR_NAME,
 CUR_NAME_S,
 CUR_NAME_K
  
FROM 
 CUR_MST
   
WHERE 
 KAI_CODE = /*kaiCode*/ 

  /*IF curCode != "" */ AND CUR_CODE  /*$curCode*/ /*END*/ -- LIKE
  /*IF sName != "" */ AND CUR_NAME_S  /*$sName*/ /*END*/ -- LIKE
  /*IF kName != "" */ AND CUR_NAME_K  /*$kName*/ /*END*/ -- LIKE
 
 /*IF termBasisDate != null */ 
  AND STR_DATE <= /*termBasisDate*/ 
  AND END_DATE >= /*termBasisDate*/ 
 /*END*/
 
 /*IF !"".equals(beginCode) */ AND CUR_CODE >= /*beginCode*/ /*END*/
 /*IF !"".equals(endCode) */ AND CUR_CODE <= /*endCode*/ /*END*/

ORDER BY 
 CUR_CODE