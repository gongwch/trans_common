SELECT ENV_MST.KAI_CODE, GL_CTL_MST.PRG_ID, GL_CTL_MST.USR_ID, GL_CTL_MST.INP_DATE, GL_CTL_MST.UPD_DATE, GL_CTL_MST.KSD_KBN, GL_CTL_MST.KSN_NYU_KBN, GL_CTL_MST.MT_ZAN_HYJ_KBN, GL_CTL_MST.EXC_RATE_KBN 
FROM ENV_MST LEFT OUTER JOIN GL_CTL_MST
ON GL_CTL_MST.KAI_CODE = ENV_MST.KAI_CODE  

 WHERE 
 	1 = 1
 	
 	/*IF kaiCode != null && kaiCode !="" */
 		AND ENV_MST.KAI_CODE =  /*kaiCode*/ 
 	/*END*/ 

 ORDER BY ENV_MST.KAI_CODE