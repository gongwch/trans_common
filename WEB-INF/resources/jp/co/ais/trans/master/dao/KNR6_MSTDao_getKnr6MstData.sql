SELECT KNR_NAME_6
FROM KNR6_MST
WHERE 
 KAI_CODE = /*param.kaiCode*/
/*IF param.knrCode != "" && param.knrCode != null */
	AND KNR_CODE_6 = /*param.knrCode*/ 
/*END*/

/*IF param.strDate != null && param.strDate != "" */
	AND STR_DATE <= /*param.strDate*/ 
/*END*/

/*IF param.endDate != null && param.endDate != "" */
	AND END_DATE >= /*param.endDate*/
/*END*/


ORDER BY KNR_CODE_6