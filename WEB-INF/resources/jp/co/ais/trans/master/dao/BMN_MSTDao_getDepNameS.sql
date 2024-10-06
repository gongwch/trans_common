SELECT BMN_MST.DEP_NAME_S
 FROM BMN_MST, DPK_MST
 WHERE BMN_MST.KAI_CODE = /*KaiCode*/
 AND DPK_MST.KAI_CODE = /*KaiCode*/
 AND BMN_MST.DEP_CODE = DPK_MST.DPK_DEP_CODE
 AND DPK_MST.DPK_SSK = /*DpkSsk*/
 AND DPK_MST.DPK_DEP_CODE = /*DepCode*/
 AND DPK_MST.DPK_LVL = /*panelLevel*/
/*IF kjlLvl == "0" */AND DPK_MST.DPK_LVL_0 = /*kjlDepCode*/ /*END*/
/*IF kjlLvl == "1" */AND DPK_MST.DPK_LVL_1 = /*kjlDepCode*/ /*END*/
/*IF kjlLvl == "2" */AND DPK_MST.DPK_LVL_2 = /*kjlDepCode*/ /*END*/
/*IF kjlLvl == "3" */AND DPK_MST.DPK_LVL_3 = /*kjlDepCode*/ /*END*/
/*IF kjlLvl == "4" */AND DPK_MST.DPK_LVL_4 = /*kjlDepCode*/ /*END*/
/*IF kjlLvl == "5" */AND DPK_MST.DPK_LVL_5 = /*kjlDepCode*/ /*END*/
/*IF kjlLvl == "6" */AND DPK_MST.DPK_LVL_6 = /*kjlDepCode*/ /*END*/
/*IF kjlLvl == "7" */AND DPK_MST.DPK_LVL_7 = /*kjlDepCode*/ /*END*/
/*IF kjlLvl == "8" */AND DPK_MST.DPK_LVL_8 = /*kjlDepCode*/ /*END*/
/*IF kjlLvl == "9" */AND DPK_MST.DPK_LVL_9 = /*kjlDepCode*/ /*END*/