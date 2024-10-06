SELECT UKM_CODE,UKM_NAME_S,UKM_NAME_K 
FROM UKM_MST 
WHERE KAI_CODE = /*kaiCode*/
AND KMK_CODE = /*kmkCode*/
AND HKM_CODE = /*hkmCode*/
/*BEGIN*/AND
/*IF ukmCode != "" */ UKM_CODE  /*$ukmCode*/ /*END*/ -- LIKE
/*IF ukmName_S != "" */ AND UKM_NAME_S  /*$ukmName_S*/ /*END*/ -- LIKE
/*IF ukmName_K != "" */ AND UKM_NAME_K  /*$ukmname_K*/ /*END*/ -- LIKE
/*IF beginCode != null && beginCode != "" */ AND UKM_CODE >= /*beginCode*/ /*END*/
/*IF endCode != null && endCode != "" */ AND UKM_CODE <= /*endCode*/ /*END*/
/*END*/
ORDER BY UKM_CODE