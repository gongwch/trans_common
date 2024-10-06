SELECT DPK_MST.DPK_DEP_CODE DEP_CODE, BMN_MST.DEP_NAME_S, BMN_MST.DEP_NAME_K
  FROM DPK_MST LEFT OUTER JOIN BMN_MST
 ON BMN_MST.KAI_CODE = DPK_MST.KAI_CODE
AND BMN_MST.DEP_CODE = DPK_MST.DPK_DEP_CODE
WHERE DPK_MST.KAI_CODE = /*kaiCode*/
   AND DPK_MST.DPK_SSK = /*dpkSsk*/
   AND DPK_MST.DPK_LVL = /*level*/
/*BEGIN*/AND
/*IF depCode != "" */ DPK_MST.DPK_DEP_CODE  /*$depCode*/ /*END*/ -- LIKE
/*IF depName_S != "" */ AND BMN_MST.DEP_NAME_S  /*$depName_S*/ /*END*/ -- LIKE
/*IF depName_K != "" */ AND BMN_MST.DEP_NAME_K  /*$depName_K*/ /*END*/ -- LIKE
/*END*/
ORDER BY DPK_MST.DPK_DEP_CODE