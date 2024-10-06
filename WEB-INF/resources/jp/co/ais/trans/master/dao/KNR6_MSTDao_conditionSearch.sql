SELECT KNR_CODE_6,KNR_NAME_S_6,KNR_NAME_K_6 
FROM KNR6_MST 
WHERE KAI_CODE = /*KAI_CODE*/
/*BEGIN*/AND
/*IF KNR_CODE_6 != "" */ KNR_CODE_6  /*$KNR_CODE_6*/ /*END*/ -- LIKE
/*IF KNR_NAME_S_6 != "" */ AND KNR_NAME_S_6  /*$KNR_NAME_S_6*/ /*END*/ -- LIKE
/*IF KNR_NAME_K_6 != "" */ AND KNR_NAME_K_6  /*$KNR_NAME_K_6*/ /*END*/ -- LIKE
/*IF beginCode != null && beginCode != "" */ AND KNR_CODE_6 >= /*beginCode*/ /*END*/
/*IF endCode != null && endCode != "" */ AND KNR_CODE_6 <= /*endCode*/ /*END*/
/*END*/
ORDER BY KNR_CODE_6