SELECT BMN.DEP_NAME
  FROM BMN_MST BMN
 WHERE BMN.KAI_CODE = /*kaiCode*/
 AND DEP_CODE = /*depCode*/
/*IF slipDate != null && slipDate != "" */ AND STR_DATE <= /*slipDate*/ /*END*/
/*IF slipDate != null && slipDate != "" */ AND END_DATE >= /*slipDate*/ /*END*/