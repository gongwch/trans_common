SELECT KMT.KMT_CODE,
       KMT.KMT_NAME_S,
       KMT.KMT_NAME_K
  FROM KMK_TK_MST KMT
 WHERE KMT.KAI_CODE = /*kaiCode*/
/*IF kmtCode != "" */AND KMT_CODE  /*$kmtCode*/  /*END*/ -- LIKE
/*IF kmtName != "" */AND KMT_NAME_S  /*$kmtName*/  /*END*/ -- LIKE
/*IF kmtName_K != "" */AND KMT_NAME_K  /*$kmtName_K*/  /*END*/ -- LIKE
/*IF startCode != null && startCode != "" */AND KMT_CODE >= /*startCode*/ /*END*/
/*IF endCode != null && endCode != "" */AND KMT_CODE <= /*endCode*/ /*END*/
 ORDER BY KMT.KMT_CODE