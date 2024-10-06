SELECT TEK_CODE,TEK_NAME,TEK_NAME_K 
FROM TEK_MST 
WHERE KAI_CODE = /*kaiCode*/
/*BEGIN*/AND
/*IF tekCode != "" */ TEK_CODE  /*$tekCode*/ /*END*/ -- LIKE
/*IF tekName != "" */ AND TEK_NAME  /*$tekName*/ /*END*/ -- LIKE
/*IF tekName_K != "" */ AND TEK_NAME_K  /*$tekName_K*/ /*END*/ -- LIKE
/*IF beginCode != null && beginCode != "" */ AND TEK_CODE >= /*beginCode*/ /*END*/
/*IF endCode != null && endCode != "" */ AND TEK_CODE <= /*endCode*/ /*END*/
/*END*/
ORDER BY TEK_CODE