SELECT DISTINCT BNK_CODE,BNK_NAME_S,BNK_NAME_K 
FROM BNK_MST 
/*BEGIN*/WHERE
/*IF bnkCode != "" */ BNK_CODE  /*$bnkCode*/ /*END*/ -- LIKE
/*IF bnkName_S != "" */AND BNK_NAME_S  /*$bnkName_S*/ /*END*/ -- LIKE
/*IF bnkName_K != "" */AND BNK_NAME_K  /*$bnkname_K*/ /*END*/ -- LIKE
/*END*/
ORDER BY BNK_CODE