SELECT BNK_STN_CODE,BNK_STN_NAME_S,BNK_STN_NAME_K 
FROM BNK_MST 
WHERE BNK_CODE = /*bnkCode*/
/*BEGIN*/AND
/*IF stnCode != "" */ BNK_STN_CODE  /*$stnCode*/ /*END*/ -- LIKE
/*IF stnName_S != "" */ AND BNK_STN_NAME_S  /*$stnName_S*/ /*END*/ -- LIKE
/*IF stnName_K != "" */ AND BNK_STN_NAME_K  /*$stnname_K*/ /*END*/ -- LIKE
/*IF beginCode != null && beginCode !="" */ AND BNK_STN_CODE >= /*beginCode*/ /*END*/
/*IF endCode != null && endCode !="" */ AND BNK_STN_CODE <= /*endCode*/ /*END*/
/*END*/
ORDER BY BNK_STN_CODE