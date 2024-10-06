SELECT T.EMP_NAME
  FROM EMP_MST T
 WHERE T.KAI_CODE = /*kaiCode*/
 AND T.EMP_CODE = /*empCode*/
/*IF slipDate != null && slipDate != "" */ AND STR_DATE <= /*slipDate*/ /*END*/
/*IF slipDate != null && slipDate != "" */ AND END_DATE >= /*slipDate*/ /*END*/