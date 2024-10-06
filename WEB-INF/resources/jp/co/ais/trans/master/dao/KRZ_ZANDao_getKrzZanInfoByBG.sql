SELECT
	zan.KAI_CODE
	,zan.KRZ_NENDO
	,zan.KRZ_DEP_CODE
	,zan.KRZ_KMK_CODE
	,zan.KRZ_HKM_CODE
	,zan.KRZ_UKM_CODE
	,zan.KRZ_TRI_CODE
	,zan.KRZ_EMP_CODE
	,zan.KRZ_KNR_CODE_1
	,zan.KRZ_KNR_CODE_2
	,zan.KRZ_KNR_CODE_3
	,zan.KRZ_KNR_CODE_4
	,zan.KRZ_KNR_CODE_5
	,zan.KRZ_KNR_CODE_6
	,zan.KRZ_CUR_CODE
	,SUM(zan.KRZ_ZAN_1 - zan.KRZ_STR_SUM) AS KRZ_ZAN_1
	,SUM(zan.KRZ_ZAN_2 - zan.KRZ_ZAN_1) AS KRZ_ZAN_2
	,SUM(zan.KRZ_ZAN_3 - zan.KRZ_ZAN_2) AS KRZ_ZAN_3
	,SUM(zan.KRZ_ZAN_4 - zan.KRZ_ZAN_3) AS KRZ_ZAN_4
	,SUM(zan.KRZ_ZAN_5 - zan.KRZ_ZAN_4) AS KRZ_ZAN_5
	,SUM(zan.KRZ_ZAN_6 - zan.KRZ_ZAN_5) AS KRZ_ZAN_6
	,SUM(zan.KRZ_ZAN_7 - zan.KRZ_ZAN_6) AS KRZ_ZAN_7
	,SUM(zan.KRZ_ZAN_8 - zan.KRZ_ZAN_7) AS KRZ_ZAN_8
	,SUM(zan.KRZ_ZAN_9 - zan.KRZ_ZAN_8) AS KRZ_ZAN_9
	,SUM(zan.KRZ_ZAN_10 - zan.KRZ_ZAN_9) AS KRZ_ZAN_10
	,SUM(zan.KRZ_ZAN_11 - zan.KRZ_ZAN_10) AS KRZ_ZAN_11
	,SUM(zan.KRZ_ZAN_12 - zan.KRZ_ZAN_11) AS KRZ_ZAN_12
FROM
	KRZ_ZAN zan,KMK_MST kmk
WHERE zan.KAI_CODE = /*kaiCode*/
AND   zan.KRZ_NENDO = /*nenDo*/
AND   zan.KAI_CODE = kmk.KAI_CODE
AND   zan.KRZ_KMK_CODE = kmk.KMK_CODE
AND   kmk.SUM_KBN = 0
AND   kmk.KMK_SHU = '1'
/*IF !"".equals(strDepCode)*/
	AND   zan.KRZ_DEP_CODE >= /*strDepCode*/
/*END*/
/*IF !"".equals(endDepCode)*/
	AND   zan.KRZ_DEP_CODE <= /*endDepCode*/
/*END*/
/*IF !"".equals(strKmkCode)*/
	AND   zan.KRZ_KMK_CODE>= /*strKmkCode*/
/*END*/
/*IF !"".equals(endKmkCode)*/
	AND   zan.KRZ_KMK_CODE <= /*endKmkCode*/
/*END*/
GROUP BY
	 zan.KAI_CODE
	,zan.KRZ_NENDO
	,zan.KRZ_DEP_CODE
	,zan.KRZ_KMK_CODE
	,zan.KRZ_HKM_CODE
	,zan.KRZ_UKM_CODE
	,zan.KRZ_TRI_CODE
	,zan.KRZ_EMP_CODE
	,zan.KRZ_KNR_CODE_1
	,zan.KRZ_KNR_CODE_2
	,zan.KRZ_KNR_CODE_3
	,zan.KRZ_KNR_CODE_4
	,zan.KRZ_KNR_CODE_5
	,zan.KRZ_KNR_CODE_6
	,zan.KRZ_CUR_CODE
ORDER BY
	 zan.KRZ_DEP_CODE
	,zan.KRZ_KMK_CODE
	,zan.KRZ_HKM_CODE
	,zan.KRZ_UKM_CODE
	,zan.KRZ_TRI_CODE
	,zan.KRZ_EMP_CODE
	,zan.KRZ_KNR_CODE_1
	,zan.KRZ_KNR_CODE_2
	,zan.KRZ_KNR_CODE_3
	,zan.KRZ_KNR_CODE_4
	,zan.KRZ_KNR_CODE_5
	,zan.KRZ_KNR_CODE_6
	,zan.KRZ_CUR_CODE
	