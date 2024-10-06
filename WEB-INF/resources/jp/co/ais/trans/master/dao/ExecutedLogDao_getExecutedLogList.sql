SELECT 
   log.EXCUTED_DATE,                  
   log.KAI_CODE,     
   log.USR_CODE,     
   log.USR_NAME,      
   log.IP_ADDRESS,     
   log.PROGRAM_CODE,  
   log.STATE,
   prg.PRG_NAME PROGRAM_NAME                
 
FROM 
EXE_LOG_TBL log
LEFT OUTER JOIN PRG_MST prg
ON  log.KAI_CODE = prg.KAI_CODE
AND log.PROGRAM_CODE = prg.PRG_CODE


WHERE log.KAI_CODE = /*param.companyCode*/
AND   TODATESTRING(log.EXCUTED_DATE,'yyyy/MM/dd') >= /*param.startDate*/
AND   TODATESTRING(log.EXCUTED_DATE,'yyyy/MM/dd') <= /*param.endDate*/

/*IF param.startUser != null && !"".equals(param.startUser)*/
AND   log.USR_CODE >= /*param.startUser*/
/*END*/

/*IF param.endUser != null && !"".equals(param.endUser)*/
AND   log.USR_CODE <= /*param.endUser*/
/*END*/

/*IF param.startPrg != null && !"".equals(param.startPrg)*/
AND   log.PROGRAM_CODE >= /*param.startPrg*/
/*END*/

/*IF param.endPrg != null && !"".equals(param.endPrg)*/
AND   log.PROGRAM_CODE <= /*param.endPrg*/
/*END*/

/*IF param.isIncludeLogin == 0 */
AND  (log.STATE = 'IN' OR log.STATE ='OUT')
/*END*/

ORDER BY /*$param.orderBy*/
