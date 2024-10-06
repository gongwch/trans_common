SELECT EMP_CODE,EMP_NAME_S,EMP_NAME_K 
FROM EMP_MST 
WHERE KAI_CODE = /*kaiCode*/
/*BEGIN*/AND
/*IF empCode != "" */ EMP_CODE  /*$empCode*/ /*END*/ -- LIKE
/*IF empName_S != "" */ AND EMP_NAME_S  /*$empName_S*/ /*END*/ -- LIKE
/*IF empName_K != "" */ AND EMP_NAME_K  /*$empName_K*/ /*END*/ -- LIKE
/*IF beginCode != null  &&  beginCode != "" */ AND EMP_CODE >= /*beginCode*/ /*END*/
/*IF endCode != null  &&  endCode != "" */ AND EMP_CODE <= /*endCode*/ /*END*/
/*END*/
ORDER BY EMP_CODE