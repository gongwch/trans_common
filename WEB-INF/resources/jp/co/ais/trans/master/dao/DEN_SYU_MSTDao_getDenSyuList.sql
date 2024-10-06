SELECT 
 *
FROM 
  DEN_SYU_MST 
WHERE 
  KAI_CODE = /*kaiCode*/
  
  /*IF !isIncludeSystemEls */ 
    AND ((TA_SYS_KBN = 1 AND SWK_IN_KBN = 0) or TA_SYS_KBN = 0)
  /*END*/
  
ORDER BY 
  DEN_SYU_CODE