SELECT 
	HOH_HOH_NAME
FROM 
	AP_HOH_MST 
WHERE 
	KAI_CODE = /*kaiCode*/ AND
	HOH_HOH_CODE = /*hohhohCode*/
	/*BEGIN*/AND
	/*IF kind == "SelectPaymentMethod" */ AND HOH_SIH_KBN = 1 /*END*/
	/*END*/
