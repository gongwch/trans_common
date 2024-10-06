SELECT BMN_MST.DEP_NAME_S
FROM DPK_MST LEFT OUTER JOIN BMN_MST
ON BMN_MST.KAI_CODE = DPK_MST.KAI_CODE
AND BMN_MST.DEP_CODE = DPK_MST.DPK_DEP_CODE
WHERE DPK_MST.KAI_CODE = /*kaiCode*/
AND DPK_MST.DPK_SSK = /*dpkSsk*/
AND DPK_MST.DPK_DEP_CODE = /*depCode*/
/*BEGIN*/AND
/*IF level==0 */ AND DPK_LVL = 0 /*END*/
/*IF level==1 */ AND DPK_LVL = 1 /*END*/
/*IF level==2 */ AND DPK_MST.DPK_LVL_1 = /*parentDepCode*/ AND DPK_LVL = 2 /*END*/
/*IF level==3 */ AND DPK_MST.DPK_LVL_2 = /*parentDepCode*/ AND DPK_LVL = 3 /*END*/
/*IF level==4 */ AND DPK_MST.DPK_LVL_3 = /*parentDepCode*/ AND DPK_LVL = 4 /*END*/
/*IF level==5 */ AND DPK_MST.DPK_LVL_4 = /*parentDepCode*/ AND DPK_LVL = 5 /*END*/
/*IF level==6 */ AND DPK_MST.DPK_LVL_5 = /*parentDepCode*/ AND DPK_LVL = 6 /*END*/
/*IF level==7 */ AND DPK_MST.DPK_LVL_6 = /*parentDepCode*/ AND DPK_LVL = 7 /*END*/
/*IF level==8 */ AND DPK_MST.DPK_LVL_7 = /*parentDepCode*/ AND DPK_LVL = 8 /*END*/
/*IF level==9 */ AND DPK_MST.DPK_LVL_8 = /*parentDepCode*/ AND DPK_LVL = 9 /*END*/
/*END*/
ORDER BY DPK_MST.DPK_DEP_CODE