SELECT KNR_CODE_3,KNR_NAME_S_3,KNR_NAME_K_3 
FROM KNR3_MST 
WHERE KAI_CODE = /*KAI_CODE*/
/*BEGIN*/AND
/*IF KNR_CODE_3 != "" */ KNR_CODE_3  /*$KNR_CODE_3*/ /*END*/ -- LIKE
/*IF KNR_NAME_S_3 != "" */ AND KNR_NAME_S_3  /*$KNR_NAME_S_3*/ /*END*/ -- LIKE
/*IF KNR_NAME_K_3 != "" */ AND KNR_NAME_K_3  /*$KNR_NAME_K_3*/ /*END*/ -- LIKE
/*IF beginCode != null && beginCode != "" */ AND KNR_CODE_3 >= /*beginCode*/ /*END*/
/*IF endCode != null && endCode != "" */ AND KNR_CODE_3 <= /*endCode*/ /*END*/
/*END*/
ORDER BY KNR_CODE_3