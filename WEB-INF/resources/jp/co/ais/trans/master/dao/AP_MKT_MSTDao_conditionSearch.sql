SELECT MKT_CODE,MKT_NAME
FROM AP_MKT_MST 
/*BEGIN*/WHERE
/*IF mktCode != "" */ MKT_CODE  /*$mktCode*/ /*END*/ -- LIKE
/*IF mktName != "" */AND MKT_NAME  /*$mktName*/ /*END*/ -- LIKE
/*END*/
ORDER BY MKT_CODE