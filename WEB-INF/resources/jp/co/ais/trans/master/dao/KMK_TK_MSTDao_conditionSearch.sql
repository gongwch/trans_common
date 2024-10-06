SELECT KMT_CODE,KMT_NAME_S,KMT_NAME_K 
FROM KMK_TK_MST 
WHERE KAI_CODE = /*kaiCode*/
/*BEGIN*/AND
/*IF kmtCode != "" */ KMT_CODE  /*$kmtCode*/ /*END*/ -- LIKE
/*IF kmtName_S != "" */ AND KMT_NAME_S  /*$kmtName_S*/ /*END*/ -- LIKE
/*IF katName_K != "" */ AND KMT_NAME_K  /*$katname_K*/ /*END*/ -- LIKE
/*END*/
ORDER BY KMT_CODE