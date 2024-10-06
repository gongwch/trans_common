SELECT T.EMP_CODE,
       T.EMP_NAME_S,
       T.EMP_NAME_K
  FROM EMP_MST T
 WHERE T.KAI_CODE = /*kaiCode*/
/*IF empCode != "" */AND EMP_CODE  /*$empCode*/  /*END*/ -- LIKE
/*IF empName != "" */AND EMP_NAME_S  /*$empName*/  /*END*/ -- LIKE
/*IF empName_K != "" */AND EMP_NAME_K  /*$empName_K*/  /*END*/ -- LIKE
/*IF slipDate != null */ AND STR_DATE <= /*slipDate*/ /*END*/
/*IF slipDate != null */ AND END_DATE >= /*slipDate*/ /*END*/
/*IF startCode != null && startCode != "" */AND EMP_CODE >= /*startCode*/ /*END*/
/*IF endCode != null && endCode != "" */AND EMP_CODE <= /*endCode*/ /*END*/
 ORDER BY T.EMP_CODE