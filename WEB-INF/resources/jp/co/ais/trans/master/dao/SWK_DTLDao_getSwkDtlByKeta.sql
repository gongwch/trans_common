SELECT COUNT(1)
FROM SWK_DTL
WHERE KAI_CODE = /*KAI_CODE*/
AND SWK_DEN_NO = /*SWK_DEN_NO*/ 
/*IF SWK_DC_KBN == 0 */ AND SWK_DC_KBN = 0 /*END*/
/*IF SWK_DC_KBN == 1 */ AND SWK_DC_KBN = 1 /*END*/
  