SELECT 
 TRI_CODE,   -- éÊà¯êÊÉRÅ[Éh
 TRI_NAME,   -- ñºèÃ
 TRI_NAME_S, -- ó™ñºèÃ
 TRI_NAME_K  -- åüçıñºèÃ
  
FROM 
 TRI_MST
  
WHERE 
 KAI_CODE = /*kaiCode*/ 

 /*IF triCode != "" */ AND TRI_CODE  /*$triCode*/ /*END*/ -- LIKE
 /*IF sName != "" */ AND TRI_NAME_S  /*$sName*/ /*END*/ -- LIKE
 /*IF kName != "" */ AND TRI_NAME_K  /*$kName*/ /*END*/ -- LIKE
 
 /*IF termBasisDate != null */ 
  AND STR_DATE <= /*termBasisDate*/ 
  AND END_DATE >= /*termBasisDate*/ 
 /*END*/
 
 /*IF siire == true && tokui == false*/
  AND SIIRE_KBN = 1
 /*END*/
 
 /*IF tokui == true && siire == false*/
  AND TOKUI_KBN = 1
 /*END*/
 
 /*IF tokui == true && siire == true*/
  AND(SIIRE_KBN = 1 OR TOKUI_KBN = 1)
 /*END*/
 
 /*IF beginCode != null && beginCode != "" */ AND TRI_CODE >= /*beginCode*/ /*END*/
 /*IF endCode != null && endCode != "" */ AND TRI_CODE <= /*endCode*/ /*END*/
ORDER BY TRI_CODE