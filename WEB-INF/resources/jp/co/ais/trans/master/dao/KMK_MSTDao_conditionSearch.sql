SELECT 
	KMK_CODE,
	KMK_NAME_S,
	KMK_NAME_K 
FROM 
	KMK_MST 
WHERE 
	KAI_CODE = /*kaiCode*/
/*IF kmkCode != "" */ AND KMK_CODE  /*$kmkCode*/ /*END*/ -- LIKE
/*IF kmkName_S != "" */ AND KMK_NAME_S  /*$kmkName_S*/ /*END*/ -- LIKE
/*IF kmkName_K != "" */ AND KMK_NAME_K  /*$kmkname_K*/ /*END*/ -- LIKE
/*IF beginCode != null && beginCode != "" */ AND KMK_CODE >= /*beginCode*/ /*END*/
/*IF endCode !=null && endCode != "" */ AND KMK_CODE <= /*endCode*/ /*END*/
/*IF kind == "Item" */ AND SUM_KBN = 0 /*END*/
/*IF kind == "ItemAll" */ AND SUM_KBN < 3 /*END*/
/*IF kind == "ItemWithChild" */ AND HKM_KBN = 1 /*END*/
ORDER BY KMK_CODE