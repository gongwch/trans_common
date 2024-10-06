SELECT cal.*
FROM AP_CAL_MST cal,
(
SELECT KAI_CODE, CAL_HARAI, MIN(CAL_DATE) CAL_DATE
FROM AP_CAL_MST
WHERE KAI_CODE = /*kaiCode*/
AND CAL_DATE >= /*calDate*/
AND CAL_HARAI = 1
GROUP BY KAI_CODE, CAL_HARAI
) cal2
WHERE cal.KAI_CODE = cal2.KAI_CODE
AND  cal.CAL_DATE = cal2.CAL_DATE
AND  cal.CAL_HARAI = cal2.CAL_HARAI