SELECT BMNMST.DEP_CODE,
	   BMNMST.DEP_NAME_S,
	   BMNMST.DEP_NAME_K
  FROM
(SELECT T.DPK_DEP_CODE DEP_CODE,
       BMN.DEP_NAME_S,
       BMN.DEP_NAME_K,
       T.KAI_CODE,
       T.DPK_SSK,
       T.DPK_LVL
  FROM DPK_MST T
  LEFT JOIN BMN_MST BMN ON BMN.KAI_CODE = T.KAI_CODE
                       AND BMN.DEP_CODE = T.DPK_DEP_CODE
 WHERE T.KAI_CODE = /*kaiCode*/
   AND T.DPK_SSK = /*dpkSsk*/
/*IF bmnKbn == "0" */AND T.DPK_LVL >= STR_TO_INT(/*dpkLvl*/) /*END*/
/*IF bmnKbn == "1" */AND T.DPK_LVL = STR_TO_INT(/*dpkLvl*/) /*END*/
/*IF bmnKbn == "2" */AND (T.DPK_LVL + 1) = STR_TO_INT(/*dpkLvl*/) /*END*/
/*IF baseDpkLvl != "" */AND T.DPK_LVL >= /*baseDpkLvl*/ /*END*/
/*IF baseDpkLvl == "0" && baseBmnCode != "" */AND T.DPK_LVL_0 = /*baseBmnCode*/ /*END*/
/*IF baseDpkLvl == "1" && baseBmnCode != "" */AND T.DPK_LVL_1 = /*baseBmnCode*/ /*END*/
/*IF baseDpkLvl == "2" && baseBmnCode != "" */AND T.DPK_LVL_2 = /*baseBmnCode*/ /*END*/
/*IF baseDpkLvl == "3" && baseBmnCode != "" */AND T.DPK_LVL_3 = /*baseBmnCode*/ /*END*/
/*IF baseDpkLvl == "4" && baseBmnCode != "" */AND T.DPK_LVL_4 = /*baseBmnCode*/ /*END*/
/*IF baseDpkLvl == "5" && baseBmnCode != "" */AND T.DPK_LVL_5 = /*baseBmnCode*/ /*END*/
/*IF baseDpkLvl == "6" && baseBmnCode != "" */AND T.DPK_LVL_6 = /*baseBmnCode*/ /*END*/
/*IF baseDpkLvl == "7" && baseBmnCode != "" */AND T.DPK_LVL_7 = /*baseBmnCode*/ /*END*/
/*IF baseDpkLvl == "8" && baseBmnCode != "" */AND T.DPK_LVL_8 = /*baseBmnCode*/ /*END*/
/*IF baseDpkLvl == "9" && baseBmnCode != "" */AND T.DPK_LVL_9 = /*baseBmnCode*/ /*END*/
/*IF bmnKbn != "2" && dpkLvl == "1" */AND T.DPK_LVL_0 = /*upBmnCode*/ /*END*/
/*IF bmnKbn != "2" && dpkLvl == "2" */AND T.DPK_LVL_1 = /*upBmnCode*/ /*END*/
/*IF bmnKbn != "2" && dpkLvl == "3" */AND T.DPK_LVL_2 = /*upBmnCode*/ /*END*/
/*IF bmnKbn != "2" && dpkLvl == "4" */AND T.DPK_LVL_3 = /*upBmnCode*/ /*END*/
/*IF bmnKbn != "2" && dpkLvl == "5" */AND T.DPK_LVL_4 = /*upBmnCode*/ /*END*/
/*IF bmnKbn != "2" && dpkLvl == "6" */AND T.DPK_LVL_5 = /*upBmnCode*/ /*END*/
/*IF bmnKbn != "2" && dpkLvl == "7" */AND T.DPK_LVL_6 = /*upBmnCode*/ /*END*/
/*IF bmnKbn != "2" && dpkLvl == "8" */AND T.DPK_LVL_7 = /*upBmnCode*/ /*END*/
/*IF bmnKbn != "2" && dpkLvl == "9" */AND T.DPK_LVL_8 = /*upBmnCode*/ /*END*/
) BMNMST
/*BEGIN*/WHERE 
/*IF depCode != "" */AND BMNMST.DEP_CODE  /*$depCode*/  /*END*/ -- LIKE
/*IF depName != "" */AND BMNMST.DEP_NAME_S  /*$depName*/  /*END*/ -- LIKE
/*IF depName_K != "" */AND BMNMST.DEP_NAME_K  /*$depName_K*/  /*END*/ -- LIKE
/*END*/
 ORDER BY BMNMST.DEP_CODE