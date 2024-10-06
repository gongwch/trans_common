SELECT T.KNR_CODE_4,
       T.KNR_NAME_S_4,
       T.KNR_NAME_K_4
  FROM KNR4_MST T
 WHERE T.KAI_CODE = /*kaiCode*/
/*IF knrCode != "" */AND KNR_CODE_4  /*$knrCode*/  /*END*/ -- LIKE
/*IF knrName != "" */AND KNR_NAME_S_4  /*$knrName*/  /*END*/ -- LIKE
/*IF knrName_K != "" */AND KNR_NAME_K_4  /*$knrName_K*/  /*END*/ -- LIKE
/*IF slipDate != null */ AND STR_DATE <= /*slipDate*/ /*END*/
/*IF slipDate != null */ AND END_DATE >= /*slipDate*/ /*END*/
/*IF startCode != null && startCode != "" */AND KNR_CODE_4 >= /*startCode*/ /*END*/
/*IF endCode != null && endCode != "" */AND KNR_CODE_4 <= /*endCode*/ /*END*/
 ORDER BY T.KNR_CODE_4