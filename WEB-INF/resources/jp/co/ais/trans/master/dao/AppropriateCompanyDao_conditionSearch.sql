SELECT 
  env.KAI_CODE,
  env.KAI_NAME_S,
  cmp.CUR_CODE,
  env.STR_DATE,
  env.END_DATE
FROM
  ENV_MST env LEFT JOIN CMP_MST cmp ON env.KAI_CODE = cmp.KAI_CODE
WHERE 1=1
/*IF param.curKbn*/
  AND cmp.CUR_CODE = /*param.CUR_CODE*/
/*END*/  
/*IF param.blnOptKbn*/
	/*IF param.KAI_CODE != null  &&  param.KAI_CODE != ""*/
  		AND env.KAI_CODE  /*$param.KAI_CODE*/ -- LIKE
  	/*END*/
  	/*IF env.KAI_NAME_S != null  &&  env.KAI_NAME_S != ""*/	
  		AND env.KAI_NAME_S  /*$param.KAI_NAME_S*/ -- LIKE
  	/*END*/
/*END*/ 	
/*IF !param.blnOptKbn*/
  	AND env.KAI_CODE = /*param.KAI_CODE*/
/*END*/

/*IF !"".equals(param.strCompanyCode) && param.strCompanyCode != null */ 
    AND env.KAI_CODE  >= /*param.strCompanyCode*/  
/*END*/

/*IF !"".equals(param.endCompanyCode) && param.endCompanyCode != null */ 
    AND env.KAI_CODE  <= /*param.endCompanyCode*/  
/*END*/

ORDER BY env.KAI_CODE