DELETE FROM AP_ZAN
WHERE KAI_CODE     = /*entity.kAI_CODE*/
  AND ZAN_DEN_NO   = /*entity.zAN_DEN_NO*/
  AND ZAN_DEN_DATE = /*entity.zAN_DEN_DATE*/
  AND ZAN_GYO_NO   = /*entity.zAN_GYO_NO*/
  /*IF entity.uPD_DATE != null */
  AND UPD_DATE     = /*entity.uPD_DATE*/
  -- ELSE
  AND UPD_DATE IS NULL
  /*END*/