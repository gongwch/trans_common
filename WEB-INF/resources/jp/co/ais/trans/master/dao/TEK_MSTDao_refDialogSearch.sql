SELECT 
 TEK_CODE,
 TEK_NAME,
 TEK_NAME_K 
 
FROM 
 TEK_MST
  
WHERE 
 KAI_CODE = /*kaiCode*/
 
 /*IF memoType == 0 */
 AND TEK_KBN = 0
 /*END*/
 /*IF memoType == 1 */
 AND TEK_KBN = 1
 /*END*/

 /*IF slipType != "" */
 AND DATA_KBN = /*slipType*/
 /*END*/

 /*IF tekCode != "" */ AND TEK_CODE  /*$tekCode*/ /*END*/ -- LIKE
 /*IF kName != "" */ AND TEK_NAME  /*$kName*/ /*END*/ -- LIKE
 /*IF sName != "" */ AND TEK_NAME_K  /*$sName*/ /*END*/ -- LIKE

 /*IF termBasisDate !=null */ 
	AND STR_DATE <= /*termBasisDate*/
 	AND END_DATE >= /*termBasisDate*/
 /*END*/

ORDER BY 
 TEK_CODE