SELECT KNR_CODE_2,KNR_NAME_S_2,KNR_NAME_K_2 
FROM KNR2_MST 
WHERE KAI_CODE = /*KAI_CODE*/
/*BEGIN*/AND
/*IF KNR_CODE_2 != "" */ KNR_CODE_2  /*$KNR_CODE_2*/ /*END*/ -- LIKE
/*IF KNR_NAME_S_2 != "" */ AND KNR_NAME_S_2  /*$KNR_NAME_S_2*/ /*END*/ -- LIKE
/*IF KNR_NAME_K_2 != "" */ AND KNR_NAME_K_2  /*$KNR_NAME_K_2*/ /*END*/ -- LIKE
/*IF beginCode != null && beginCode != "" */ AND KNR_CODE_2 >= /*beginCode*/ /*END*/
/*IF endCode != null && endCode != "" */ AND KNR_CODE_2 <= /*endCode*/ /*END*/
/*END*/
ORDER BY KNR_CODE_2