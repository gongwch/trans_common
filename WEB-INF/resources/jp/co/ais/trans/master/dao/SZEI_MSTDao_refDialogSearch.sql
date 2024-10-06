SELECT 
 ZEI_CODE,  -- è¡îÔê≈ÉRÅ[Éh
 ZEI_NAME_S,-- ó™ñºèÃ
 ZEI_NAME_K -- åüçıñºèÃ
 
FROM 
 SZEI_MST
  
WHERE 
 KAI_CODE = /*kaiCode*/
 
 AND US_KBN IN(
  /*IF sales.equals("1") */
  1
  /*END*/

 /*IF purchase.equals("1") */
  /*IF sales.equals("1") */
  ,2
  --  ELSE
  2
  /*END*/
 /*END*/

 /*IF elseTax.equals("1") */
  /*IF sales.equals("1") || purchase.equals("1") */
   ,0
  --  ELSE
  0
  /*END*/
 /*END*/

 /*IF sales.equals("0") && purchase.equals("0") && elseTax.equals("0") */
  1,2,0
 /*END*/
 )

 /*IF zeiCode != "" */ AND ZEI_CODE  /*$zeiCode*/ /*END*/ --  LIKE
 /*IF sName != "" */ AND ZEI_NAME_S  /*$sName*/ /*END*/ --  LIKE
 /*IF kName != "" */ AND ZEI_NAME_K  /*$kName*/ /*END*/ --  LIKE

 /*IF termBasisDate != null */ 
  AND STR_DATE <= /*termBasisDate*/ 
  AND END_DATE >= /*termBasisDate*/  
 /*END*/

ORDER BY ZEI_CODE