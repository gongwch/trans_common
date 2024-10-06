SELECT *
FROM KNR3_MST
WHERE 3 = 3
AND KAI_CODE = /*param.kaiCode*/
/*IF param.knrCode != "" && param.knrCode != null */
	AND KNR_CODE_3 = /*param.knrCode*/ 
/*END*/

/*IF param.knrCodeLike != NULL && param.knrCodeLike != ""*/
	AND KNR_CODE_3 /*$param.knrCodeLike*/ -- LIKE
/*END*/

/*IF param.knrCodeBegin != null && param.knrCodeBegin != "" */
	AND KNR_CODE_3 >= /*param.knrCodeBegin*/ 
/*END*/

/*IF param.knrCodeEnd != null && param.knrCodeEnd != "" */
	AND KNR_CODE_3 <= /*param.knrCodeEnd*/
/*END*/

/*IF param.knrName != NULL && param.knrName != ""*/
	AND KNR_NAME_3 = /*param.knrName*/  
/*END*/

/*IF param.knrNameS != NULL && param.knrNameS != ""*/
	AND KNR_NAME_S_3 = /*param.knrNameS*/  
/*END*/

/*IF param.knrNameK != NULL && param.knrNameK != ""*/
	AND KNR_NAME_K_3 = /*param.knrNameK*/  
/*END*/

/*IF param.knrNameLike != NULL && param.knrNameLike != ""*/
	AND KNR_NAME_3 /*$param.knrNameLike*/ -- LIKE
/*END*/

/*IF param.knrNameSLike != NULL && param.knrNameSLike != ""*/
	AND KNR_NAME_S_3 /*$param.knrNameSLike*/ -- LIKE
/*END*/

/*IF param.knrNameKLike != NULL && param.knrNameKLike != ""*/
	AND KNR_NAME_K_3 /*$param.knrNameKLike*/ -- LIKE
/*END*/

/*IF param.strDate != null && param.strDate != "" */
	AND STR_DATE >= /*param.strDate*/ 
/*END*/

/*IF param.endDate != null && param.endDate != "" */
	AND STR_DATE <= /*param.endDate*/
/*END*/


ORDER BY KNR_CODE_3