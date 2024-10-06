SELECT HOH_HOH_CODE,HOH_HOH_NAME,HOH_HOH_NAME_K 
FROM AP_HOH_MST 
WHERE KAI_CODE = /*kaiCode*/
/*BEGIN*/AND
/*IF hohHohCode != "" */ HOH_HOH_CODE  /*$hohHohCode*/ /*END*/ -- LIKE
/*IF hohHohName != "" */AND HOH_HOH_NAME  /*$hohHohName*/ /*END*/ -- LIKE
/*IF hohHohName_K != "" */AND HOH_HOH_NAME_K  /*$hohHohName_K*/ /*END*/ -- LIKE
/*IF beginCode != null &&  beginCode != "" */ AND HOH_HOH_CODE >= /*beginCode*/ /*END*/
/*IF endCode != null &&  endCode != "" */ AND HOH_HOH_CODE <= /*endCode*/ /*END*/
/*IF kind == "SelectPaymentMethod" */ AND HOH_SIH_KBN = 1 /*END*/
/*END*/
ORDER BY HOH_HOH_CODE