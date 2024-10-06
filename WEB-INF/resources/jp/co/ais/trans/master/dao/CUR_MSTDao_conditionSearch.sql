SELECT CUR_CODE,CUR_NAME_S,CUR_NAME_K 
FROM CUR_MST 
WHERE KAI_CODE = /*kaiCode*/
/*BEGIN*/AND
/*IF curCode != "" */ CUR_CODE  /*$curCode*/ /*END*/ -- LIKE
/*IF curName != "" */ AND CUR_NAME_S  /*$curName*/ /*END*/ -- LIKE
/*IF curName_K != "" */ AND CUR_NAME_K  /*$curname_K*/ /*END*/ -- LIKE
/*IF beginCode != null && beginCode != "" */ AND CUR_CODE >= /*beginCode*/ /*END*/
/*IF endCode != null && endCode != "" */ AND CUR_CODE <= /*endCode*/ /*END*/
/*END*/
ORDER BY CUR_CODE