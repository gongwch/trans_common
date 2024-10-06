SELECT 
	MIN(USR_NAME_S) AS USR_NAME_S
 FROM 
 	USR_MST
 WHERE 
 	1 = 1 
 	/*IF kaiCode != null && kaiCode != "" */ 
 		 AND KAI_CODE = /*kaiCode*/
 	/*END*/ 

	/*IF usrCode != null && usrCode != "" */ 
		AND USR_CODE = /*usrCode*/ 
	/*END*/