SELECT KAI_CODE,
	  EVKMST.KAI_NAME_S
  FROM
	(
	SELECT 
		ENV.KAI_CODE KAI_CODE,
       	ENV.KAI_NAME_S,
       	T.KAI_CODE KAICODE,
       	T.DPK_SSK,
       	T.DPK_LVL
  	FROM 
  		EVK_MST T
  	LEFT JOIN ENV_MST ENV 
  		ON ENV.KAI_CODE = T.DPK_DEP_CODE
  	WHERE T.KAI_CODE = /*loginKaiCode*/
 	AND   T.DPK_SSK = /*dpkSsk*/
 	AND	 ENV.KAI_CODE IS NOT NULL
	/*IF cmpKbn == "0" */AND T.DPK_LVL >= STR_TO_INT(/*dpkLvl*/) /*END*/
	/*IF cmpKbn == "1" */AND T.DPK_LVL = STR_TO_INT(/*dpkLvl*/) /*END*/
	/*IF cmpKbn == "2" */AND (T.DPK_LVL + 1) = STR_TO_INT(/*dpkLvl*/) /*END*/
	/*IF baseDpkLvl != "" */AND T.DPK_LVL >= /*baseDpkLvl*/ /*END*/
	/*IF baseDpkLvl == "0" && baseCmpCode != "" */AND T.DPK_LVL_0 = /*baseCmpCode*/ /*END*/
	/*IF baseDpkLvl == "1" && baseCmpCode != "" */AND T.DPK_LVL_1 = /*baseCmpCode*/ /*END*/
	/*IF baseDpkLvl == "2" && baseCmpCode != "" */AND T.DPK_LVL_2 = /*baseCmpCode*/ /*END*/
	/*IF baseDpkLvl == "3" && baseCmpCode != "" */AND T.DPK_LVL_3 = /*baseCmpCode*/ /*END*/
	/*IF baseDpkLvl == "4" && baseCmpCode != "" */AND T.DPK_LVL_4 = /*baseCmpCode*/ /*END*/
	/*IF baseDpkLvl == "5" && baseCmpCode != "" */AND T.DPK_LVL_5 = /*baseCmpCode*/ /*END*/
	/*IF baseDpkLvl == "6" && baseCmpCode != "" */AND T.DPK_LVL_6 = /*baseCmpCode*/ /*END*/
	/*IF baseDpkLvl == "7" && baseCmpCode != "" */AND T.DPK_LVL_7 = /*baseCmpCode*/ /*END*/
	/*IF baseDpkLvl == "8" && baseCmpCode != "" */AND T.DPK_LVL_8 = /*baseCmpCode*/ /*END*/
	/*IF baseDpkLvl == "9" && baseCmpCode != "" */AND T.DPK_LVL_9 = /*baseCmpCode*/ /*END*/
	/*IF cmpKbn != "2" && dpkLvl == "1" */AND T.DPK_LVL_0 = /*upCmpCode*/ /*END*/
	/*IF cmpKbn != "2" && dpkLvl == "2" */AND T.DPK_LVL_1 = /*upCmpCode*/ /*END*/
	/*IF cmpKbn != "2" && dpkLvl == "3" */AND T.DPK_LVL_2 = /*upCmpCode*/ /*END*/
	/*IF cmpKbn != "2" && dpkLvl == "4" */AND T.DPK_LVL_3 = /*upCmpCode*/ /*END*/
	/*IF cmpKbn != "2" && dpkLvl == "5" */AND T.DPK_LVL_4 = /*upCmpCode*/ /*END*/
	/*IF cmpKbn != "2" && dpkLvl == "6" */AND T.DPK_LVL_5 = /*upCmpCode*/ /*END*/
	/*IF cmpKbn != "2" && dpkLvl == "7" */AND T.DPK_LVL_6 = /*upCmpCode*/ /*END*/
	/*IF cmpKbn != "2" && dpkLvl == "8" */AND T.DPK_LVL_7 = /*upCmpCode*/ /*END*/
	/*IF cmpKbn != "2" && dpkLvl == "9" */AND T.DPK_LVL_8 = /*upCmpCode*/ /*END*/
	) EVKMST
/*BEGIN*/
  WHERE
	/*IF kaiCode != "" */ KAI_CODE  /*$kaiCode*/ /*END*/ --  LIKE
	/*IF kaiName_S != "" */ AND KAI_NAME_S  /*$kaiName_S*/ /*END*/ --  LIKE
/*END*/
ORDER BY 
	KAI_CODE