SELECT CUR_MST.CUR_CODE,
       CUR_MST.CUR_NAME_S,
       CUR_MST.CUR_NAME_K,
       CUR_MST.DEC_KETA,
       CUR_MST.RATE_POW,
       CUR_MST.CONV_KBN
  FROM CUR_MST
 WHERE CUR_MST.KAI_CODE = /*kaiCode*/
/*IF curCode != "" */ AND CUR_MST.CUR_CODE = /*curCode*/ /*END*/
/*IF slipDate != null */ AND CUR_MST.STR_DATE <= /*slipDate*/ /*END*/
/*IF slipDate != null */ AND CUR_MST.END_DATE >= /*slipDate*/ /*END*/
 ORDER BY CUR_MST.CUR_CODE