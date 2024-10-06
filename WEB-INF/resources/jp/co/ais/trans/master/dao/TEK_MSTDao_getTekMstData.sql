SELECT TEK_NAME
FROM TEK_MST 
WHERE KAI_CODE = /*kaiCode*/
AND TEK_CODE = /*tekCode*/
AND TEK_KBN = /*tekKbn*/
AND DATA_KBN = /*dataKbn*/
/*IF slipDate != null && slipDate != "" */ AND STR_DATE <= /*slipDate*/ /*END*/
/*IF slipDate != null && slipDate != "" */ AND END_DATE >= /*slipDate*/ /*END*/