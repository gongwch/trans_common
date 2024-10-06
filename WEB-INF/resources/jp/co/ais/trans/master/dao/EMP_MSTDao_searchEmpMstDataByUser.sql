SELECT DISTINCT USR.EMP_CODE,
                EMP.EMP_NAME_S,
                EMP.EMP_NAME_K
  FROM USR_MST USR
  LEFT JOIN EMP_MST EMP ON USR.KAI_CODE = EMP.KAI_CODE
                       AND USR.EMP_CODE = EMP.EMP_CODE
 WHERE USR.KAI_CODE = /*kaiCode*/
/*IF depCode != null && depCode != "" */AND USR.DEP_CODE = /*depCode*/ /*END*/
/*IF empCode != "" */AND USR.EMP_CODE  /*$empCode*/  /*END*/ -- LIKE
/*IF empName != "" */AND EMP.EMP_NAME_S  /*$empName*/  /*END*/ -- LIKE
/*IF empName_K != "" */AND EMP.EMP_NAME_K  /*$empName_K*/  /*END*/ -- LIKE
 ORDER BY EMP_CODE
