SELECT DEN_SYU_CODE,DEN_SYU_NAME_S,DEN_SYU_NAME_K 
FROM DEN_SYU_MST 
WHERE KAI_CODE = /*KAI_CODE*/
/*BEGIN*/AND
/*IF DEN_SYU_CODE != "" */ DEN_SYU_CODE  /*$DEN_SYU_CODE*/ /*END*/ -- LIKE
/*IF DEN_SYU_NAME_S != "" */ AND DEN_SYU_NAME_S  /*$DEN_SYU_NAME_S*/ /*END*/ -- LIKE
/*IF DEN_SYU_NAME_K != "" */ AND DEN_SYU_NAME_K  /*$DEN_SYU_NAME_K*/ /*END*/ -- LIKE
/*IF beginCode != null  &&  beginCode != "" */ AND DEN_SYU_CODE >= /*beginCode*/ /*END*/
/*IF endCode != null  &&  endCode != "" */ AND DEN_SYU_CODE <= /*endCode*/ /*END*/
/*END*/
ORDER BY DEN_SYU_CODE