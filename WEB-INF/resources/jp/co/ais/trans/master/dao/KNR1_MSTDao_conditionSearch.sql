SELECT KNR_CODE_1,KNR_NAME_S_1,KNR_NAME_K_1 
FROM KNR1_MST 
WHERE KAI_CODE = /*KAI_CODE*/
/*BEGIN*/AND
/*IF KNR_CODE_1 != "" */ KNR_CODE_1  /*$KNR_CODE_1*/ /*END*/ -- LIKE
/*IF KNR_NAME_S_1 != "" */ AND KNR_NAME_S_1  /*$KNR_NAME_S_1*/ /*END*/ -- LIKE
/*IF KNR_NAME_K_1 != "" */ AND KNR_NAME_K_1  /*$KNR_NAME_K_1*/ /*END*/ -- LIKE
/*IF beginCode != null && beginCode != "" */ AND KNR_CODE_1 >= /*beginCode*/ /*END*/
/*IF endCode != null && endCode != "" */ AND KNR_CODE_1 <= /*endCode*/ /*END*/
/*END*/
ORDER BY KNR_CODE_1