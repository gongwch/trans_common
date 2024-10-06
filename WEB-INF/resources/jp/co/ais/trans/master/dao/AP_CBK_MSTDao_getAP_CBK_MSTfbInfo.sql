SELECT *
FROM AP_CBK_MST 
WHERE KAI_CODE = /*kaiCode*/
AND CBK_CBK_CODE = /*cbkcbkCode*/
/*IF fbKbn !=null && fbKbn != "" */ AND CBK_OUT_FB_KBN = /*fbKbn*/ /*END*/
