
/*IF user != "true" */

SELECT 
 EMP_CODE,
 EMP_NAME_S,
 EMP_NAME_K
  
FROM 
 EMP_MST 
WHERE 
 KAI_CODE = /*kaiCode*/

/*IF empCode != "" */ AND EMP_CODE  /*$empCode*/ /*END*/ -- LIKE
/*IF sName != "" */ AND EMP_NAME_S  /*$sName*/ /*END*/ -- LIKE
/*IF kName != "" */ AND EMP_NAME_K  /*$kName*/ /*END*/ -- LIKE

/*IF termBasisDate != null */ 
 AND STR_DATE <= /*termBasisDate*/ 
 AND END_DATE >= /*termBasisDate*/ /*END*/
/*IF beginCode != null && beginCode != "" */ AND EMP_CODE >= /*beginCode*/ /*END*/
/*IF endCode != null && endCode != "" */ AND EMP_CODE <= /*endCode*/ /*END*/

ORDER BY 
 EMP_CODE

-- ELSE

SELECT
 E.EMP_CODE,
 E.EMP_NAME_S,
 E.EMP_NAME_K
 
FROM 
 EMP_MST E
 INNER JOIN USR_MST U
  ON E.KAI_CODE = U.KAI_CODE
  AND E.EMP_CODE = U.EMP_CODE
  
WHERE
 E.KAI_CODE = /*kaiCode*/
 /*IF depCode != "" */ 
  AND U.DEP_CODE = /*depCode*/
 /*END*/
 
 /*IF empCode != "" */ AND E.EMP_CODE  /*$empCode*/ /*END*/ -- LIKE
 /*IF sName != "" */ AND E.EMP_NAME_S  /*$sName*/ /*END*/ -- LIKE
 /*IF kName != "" */ AND E.EMP_NAME_K  /*$kName*/ /*END*/ -- LIKE
 /*IF termBasisDate != null */ 
 AND E.STR_DATE <= /*termBasisDate*/ 
 AND E.END_DATE >= /*termBasisDate*/ 
 /*END*/
 
 
/*IF beginCode != null && beginCode != "" */ AND E.EMP_CODE >= /*beginCode*/ /*END*/
/*IF endCode != null && endCode != "" */ AND E.EMP_CODE <= /*endCode*/ /*END*/
 
ORDER BY
 E.EMP_CODE

/*END*/