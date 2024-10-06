SELECT 
	PRG_CODE,
	PRG_NAME_S,
	PRG_NAME_K 
FROM
	PRG_MST 
WHERE 
	KAI_CODE = /*KAI_CODE*/
	/*IF PRG_CODE != "" */ AND PRG_CODE  /*$PRG_CODE*/ /*END*/ -- LIKE
	/*IF PRG_NAME_S != "" */ AND PRG_NAME_S  /*$PRG_NAME_S*/ /*END*/ -- LIKE
	/*IF PRG_NAME_K != "" */ AND PRG_NAME_K  /*$PRG_NAME_K*/ /*END*/ -- LIKE
	/*IF beginCode != null && beginCode != "" */ AND PRG_CODE >= /*beginCode*/ /*END*/
	/*IF endCode != null && endCode != "" */ AND PRG_CODE <= /*endCode*/ /*END*/
ORDER BY 
	PRG_CODE
	