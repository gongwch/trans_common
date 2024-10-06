SELECT KNR_NAME_2
FROM KNR2_MST
WHERE KAI_CODE = /*param.kaiCode*/
/*IF param.knrCode != "" && param.knrCode != null */
	AND KNR_CODE_2 = /*param.knrCode*/ 
/*END*/

/*IF param.strDate != null && param.strDate != "" */
	AND STR_DATE <= /*param.strDate*/ 
/*END*/

/*IF param.endDate != null && param.endDate != "" */
	AND END_DATE >= /*param.endDate*/
/*END*/