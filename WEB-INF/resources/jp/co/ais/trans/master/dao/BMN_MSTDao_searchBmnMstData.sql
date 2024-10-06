SELECT BMN.DEP_CODE,
       BMN.DEP_NAME_S,
       BMN.DEP_NAME_K
  FROM BMN_MST BMN
 WHERE BMN.KAI_CODE = /*kaiCode*/
   AND BMN.DEP_KBN = 0
/*IF depCode != "" */AND DEP_CODE  /*$depCode*/  /*END*/ -- LIKE
/*IF depName != "" */AND DEP_NAME_S  /*depName*/  /*END*/ -- LIKE
/*IF depName_K != "" */AND DEP_NAME_K  /*depName_K*/  /*END*/ -- LIKE
/*IF slipDate != null */ AND STR_DATE <= /*slipDate*/ /*END*/
/*IF slipDate != null */ AND END_DATE >= /*slipDate*/ /*END*/
 ORDER BY BMN.DEP_CODE