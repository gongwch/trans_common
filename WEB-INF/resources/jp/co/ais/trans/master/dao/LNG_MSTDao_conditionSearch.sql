SELECT LNG_CODE
FROM LNG_MST 
WHERE KAI_CODE = /*kaiCode*/
/*BEGIN*/AND
/*IF lngCode != "" */ LNG_CODE  /*$lngCode*/ /*END*/ -- LIKE
/*END*/
ORDER BY LNG_CODE