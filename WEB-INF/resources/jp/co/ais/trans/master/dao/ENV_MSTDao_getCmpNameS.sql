SELECT ENV_MST.KAI_NAME_S
 FROM ENV_MST, EVK_MST
 WHERE EVK_MST.KAI_CODE = /*KaiCode*/
 AND ENV_MST.KAI_CODE = EVK_MST.DPK_DEP_CODE
 AND EVK_MST.DPK_SSK = /*DpkSsk*/
 AND EVK_MST.DPK_DEP_CODE = /*DepCode*/
 AND EVK_MST.DPK_LVL = /*panelLevel*/
/*IF kjlLvl == "0" */AND EVK_MST.DPK_LVL_0 = /*kjlDepCode*/ /*END*/
/*IF kjlLvl == "1" */AND EVK_MST.DPK_LVL_1 = /*kjlDepCode*/ /*END*/
/*IF kjlLvl == "2" */AND EVK_MST.DPK_LVL_2 = /*kjlDepCode*/ /*END*/
/*IF kjlLvl == "3" */AND EVK_MST.DPK_LVL_3 = /*kjlDepCode*/ /*END*/
/*IF kjlLvl == "4" */AND EVK_MST.DPK_LVL_4 = /*kjlDepCode*/ /*END*/
/*IF kjlLvl == "5" */AND EVK_MST.DPK_LVL_5 = /*kjlDepCode*/ /*END*/
/*IF kjlLvl == "6" */AND EVK_MST.DPK_LVL_6 = /*kjlDepCode*/ /*END*/
/*IF kjlLvl == "7" */AND EVK_MST.DPK_LVL_7 = /*kjlDepCode*/ /*END*/
/*IF kjlLvl == "8" */AND EVK_MST.DPK_LVL_8 = /*kjlDepCode*/ /*END*/
/*IF kjlLvl == "9" */AND EVK_MST.DPK_LVL_9 = /*kjlDepCode*/ /*END*/
