SELECT 
 	*
 FROM 
 	USR_MST  
 WHERE 
 	1 = 1
 	/*IF kaiCode != null && kaiCode != "" */ 
 		 AND KAI_CODE = /*kaiCode*/
 	/*END*/ 

	/*IF beginUsrCode != null && beginUsrCode != "" */ 
		AND USR_CODE >= /*beginUsrCode*/ 
	/*END*/
	
	/*IF endUsrCode != null && endUsrCode != "" */ 
		AND USR_CODE <= /*endUsrCode*/ 
	/*END*/

ORDER BY 
 KAI_CODE,
 USR_CODE