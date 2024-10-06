SELECT TRI_CODE,TRI_NAME_S,TRI_NAME_K 
FROM TRI_MST 
WHERE KAI_CODE = /*kaiCode*/
/*BEGIN*/AND
/*IF triCode != "" */ TRI_CODE  /*$triCode*/ /*END*/ -- LIKE
/*IF triName_S != "" */ AND TRI_NAME_S  /*$triName_S*/ /*END*/ -- LIKE
/*IF triName_K != "" */ AND TRI_NAME_K  /*$triname_K*/ /*END*/ -- LIKE
/*IF beginCode != null && beginCode != "" */ AND TRI_CODE >= /*beginCode*/ /*END*/
/*IF endCode != null && endCode != "" */ AND TRI_CODE <= /*endCode*/ /*END*/
/*IF kind == "CustomerWithoutSumCode" */ AND SUM_CODE IS NULL /*END*/
/*END*/
ORDER BY TRI_CODE