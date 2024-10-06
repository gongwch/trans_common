SELECT HKM_CODE,HKM_NAME_S,HKM_NAME_K 
FROM HKM_MST 
WHERE KAI_CODE = /*kaiCode*/
AND KMK_CODE = /*kmkCode*/
/*BEGIN*/AND
/*IF hkmCode != "" */ HKM_CODE  /*$hkmCode*/ /*END*/ -- LIKE
/*IF hkmName_S != "" */ AND HKM_NAME_S  /*$hkmName_S*/ /*END*/ -- LIKE
/*IF hkmName_K != "" */ AND HKM_NAME_K  /*$hkmname_K*/ /*END*/ -- LIKE
/*IF beginCode != null  &&  beginCode != "" */ AND HKM_CODE >= /*beginCode*/ /*END*/
/*IF endCode != null  &&  endCode != "" */ AND HKM_CODE <= /*endCode*/ /*END*/
/*IF kind == "SubItemWithChild" */ AND UKM_KBN = 1 /*END*/
/*END*/
ORDER BY HKM_CODE