SELECT T.KNR_CODE_6,
       T.KNR_NAME_S_6,
       T.KNR_NAME_K_6
  FROM KNR6_MST T
 WHERE T.KAI_CODE = /*kaiCode*/
/*IF knrCode != "" */AND KNR_CODE_6  /*$knrCode*/  /*END*/ -- LIKE
/*IF knrName != "" */AND KNR_NAME_S_6  /*$knrName*/  /*END*/ -- LIKE
/*IF knrName_K != "" */AND KNR_NAME_K_6  /*$knrName_K*/  /*END*/ -- LIKE
/*IF slipDate != null */ AND STR_DATE <= /*slipDate*/ /*END*/
/*IF slipDate != null */ AND END_DATE >= /*slipDate*/ /*END*/
/*IF startCode != null && startCode != "" */AND KNR_CODE_6 >= /*startCode*/ /*END*/
/*IF endCode != null && endCode != "" */AND KNR_CODE_6 <= /*endCode*/ /*END*/
 ORDER BY T.KNR_CODE_6