SELECT USR.EMP_CODE,
       EMP.EMP_NAME_S,
       EMP.EMP_NAME_K
  FROM USR_MST USR
  LEFT JOIN EMP_MST EMP ON USR.KAI_CODE = EMP.KAI_CODE
                       AND USR.EMP_CODE = EMP.EMP_CODE
 WHERE USR.KAI_CODE = /*kaiCode*/
/*IF depCode != null && depCode != "" */AND USR.DEP_CODE = /*depCode*/ /*END*/
/*IF empCode != null && empCode != "" */AND USR.EMP_CODE = /*empCode*/ /*END*/
 ORDER BY USR.EMP_CODE