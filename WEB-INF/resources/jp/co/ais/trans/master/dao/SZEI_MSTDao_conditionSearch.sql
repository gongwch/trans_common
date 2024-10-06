SELECT ZEI_CODE,ZEI_NAME_S,ZEI_NAME_K 
FROM SZEI_MST 
WHERE KAI_CODE = /*kaiCode*/
/*BEGIN*/AND
/*IF zeiCode != "" */ ZEI_CODE  /*$zeiCode*/ /*END*/ -- LIKE
/*IF zeiName_S != "" */ AND ZEI_NAME_S  /*$zeiName_S*/ /*END*/ -- LIKE
/*IF zeiName_K != "" */ AND ZEI_NAME_K  /*$zeiname_K*/ /*END*/ -- LIKE
/*IF beginCode != null && beginCode != ""  */ AND ZEI_CODE >= /*beginCode*/ /*END*/
/*IF endCode != null && endCode != ""  */ AND ZEI_CODE <= /*endCode*/ /*END*/
/*IF kind == "PurchaseConsumptionTax" */ AND US_KBN = 2 /*END*/
/*END*/
ORDER BY ZEI_CODE