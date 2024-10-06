SELECT T.KNR_CODE_5,
       T.KNR_NAME_S_5,
       T.KNR_NAME_K_5
  FROM KNR5_MST T
 WHERE T.KAI_CODE = /*kaiCode*/
/*IF knrCode != "" */AND KNR_CODE_5  /*$knrCode*/  /*END*/ -- LIKE
/*IF knrName != "" */AND KNR_NAME_S_5  /*$knrName*/  /*END*/ -- LIKE
/*IF knrName_K != "" */AND KNR_NAME_K_5  /*$knrName_K*/  /*END*/ -- LIKE
/*IF slipDate != null */ AND STR_DATE <= /*slipDate*/ /*END*/
/*IF slipDate != null */ AND END_DATE >= /*slipDate*/ /*END*/
/*IF startCode != null && startCode != "" */AND KNR_CODE_5 >= /*startCode*/ /*END*/
/*IF endCode != null && endCode != "" */AND KNR_CODE_5 <= /*endCode*/ /*END*/
 ORDER BY T.KNR_CODE_5