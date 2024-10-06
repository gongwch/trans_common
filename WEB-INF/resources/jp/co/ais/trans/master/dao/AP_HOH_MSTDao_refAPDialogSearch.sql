SELECT 
 HOH_HOH_CODE,
 HOH_HOH_NAME,
 HOH_HOH_NAME_K
  
FROM 
 AP_HOH_MST
  
WHERE 
 KAI_CODE = /*kaiCode*/

 /*IF hohCode != "" */ AND HOH_HOH_CODE  /*$hohCode*/ /*END*/ -- LIKE
 /*IF sName != "" */AND HOH_HOH_NAME  /*$sName*/ /*END*/ -- LIKE
 /*IF kName != "" */AND HOH_HOH_NAME_K  /*$kName*/ /*END*/ -- LIKE

 /*IF termBasisDate != null */ 
  AND STR_DATE <= /*termBasisDate*/ 
  AND END_DATE >= /*termBasisDate*/ 
 /*END*/
 /*IF searchKbn */
	AND HOH_NAI_CODE <> '10'
	AND HOH_SIH_KBN = '0'
-- ELSE
  AND HOH_NAI_CODE = '03'
  AND HOH_SIH_KBN = 0
/*END*/
ORDER BY 
 HOH_HOH_CODE