SELECT SYS_KBN,SYS_KBN_NAME_S,SYS_KBN_NAME_K 
FROM SYS_MST  
WHERE KAI_CODE = /*KAI_CODE*/
/*BEGIN*/AND
/*IF SYS_KBN != "" */ SYS_KBN  /*$SYS_KBN*/ /*END*/ -- LIKE
/*IF SYS_NAME_S != "" */ AND SYS_KBN_NAME_S  /*$SYS_NAME_S*/ /*END*/ -- LIKE
/*IF SYS_NAME_K != "" */ AND SYS_KBN_NAME_K  /*$SYS_NAME_K*/ /*END*/ -- LIKE
/*IF beginCode != null && beginCode != "" */ AND SYS_KBN >= /*beginCode*/ /*END*/
/*IF endCode != null && endCode != "" */ AND SYS_KBN <= /*endCode*/ /*END*/
/*END*/
ORDER BY SYS_KBN