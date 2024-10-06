SELECT KNR_CODE_4,KNR_NAME_S_4,KNR_NAME_K_4 
FROM KNR4_MST 
WHERE KAI_CODE = /*KAI_CODE*/
/*BEGIN*/AND
/*IF KNR_CODE_4 != "" */ KNR_CODE_4  /*$KNR_CODE_4*/ /*END*/ -- LIKE
/*IF KNR_NAME_S_4 != "" */ AND KNR_NAME_S_4  /*$KNR_NAME_S_4*/ /*END*/ -- LIKE
/*IF KNR_NAME_K_4 != "" */ AND KNR_NAME_K_4  /*$KNR_NAME_K_4*/ /*END*/ -- LIKE
/*IF beginCode != null && beginCode != "" */ AND KNR_CODE_4 >= /*beginCode*/ /*END*/
/*IF endCode != null && endCode != "" */ AND KNR_CODE_4 <= /*endCode*/ /*END*/
/*END*/
ORDER BY KNR_CODE_4