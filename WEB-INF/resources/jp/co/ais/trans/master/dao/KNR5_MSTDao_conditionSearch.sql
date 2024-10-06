SELECT KNR_CODE_5,KNR_NAME_S_5,KNR_NAME_K_5 
FROM KNR5_MST 
WHERE KAI_CODE = /*KAI_CODE*/
/*BEGIN*/AND
/*IF KNR_CODE_5 != "" */ KNR_CODE_5  /*$KNR_CODE_5*/ /*END*/ -- LIKE
/*IF KNR_NAME_S_5 != "" */ AND KNR_NAME_S_5  /*$KNR_NAME_S_5*/ /*END*/ -- LIKE
/*IF KNR_NAME_K_5 != "" */ AND KNR_NAME_K_5  /*$KNR_NAME_K_5*/ /*END*/ -- LIKE
/*IF beginCode != null && beginCode != "" */ AND KNR_CODE_5 >= /*beginCode*/ /*END*/
/*IF endCode != null && endCode != "" */ AND KNR_CODE_5 <= /*endCode*/ /*END*/
/*END*/
ORDER BY KNR_CODE_5