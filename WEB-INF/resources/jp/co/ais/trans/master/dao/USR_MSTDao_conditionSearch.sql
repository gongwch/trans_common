SELECT 
 DISTINCT USR_CODE,
 USR_NAME_S,
 USR_NAME_K
 
FROM 
 USR_MST
  
WHERE 
 	1 = 1
	
/*IF KAI_CODE != null && KAI_CODE != "" */ AND  KAI_CODE = /*KAI_CODE*/ /*END*/
/*IF USR_CODE != "" */ AND USR_CODE  /*$USR_CODE*/ /*END*/ -- LIKE
/*IF USR_NAME_S != "" */ AND USR_NAME_S  /*$USR_NAME_S*/ /*END*/ -- LIKE
/*IF USR_NAME_K != "" */ AND USR_NAME_K  /*$USR_NAME_K*/ /*END*/ -- LIKE
/*IF beginCode != null && beginCode != "" */ AND USR_CODE >= /*beginCode*/ /*END*/
/*IF endCode != null && endCode != "" */ AND USR_CODE <= /*endCode*/ /*END*/

ORDER BY 
 USR_CODE