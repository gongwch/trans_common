SELECT
 sj.TJK_CODE,
 sj.YKN_NO,
 bnk.BNK_NAME_S YKN_NAME, 
 bnk.BNK_STN_NAME_S YKN_KANA,
 sj.STR_DATE,
 sj.END_DATE
 
FROM
 TRI_SJ_MST sj
 LEFT OUTER JOIN AP_CBK_MST cbk
  ON   sj.KAI_CODE = cbk.KAI_CODE
  AND  sj.FURI_CBK_CODE = cbk.CBK_CBK_CODE
 LEFT OUTER JOIN BNK_MST bnk
  ON  cbk.CBK_BNK_CODE = bnk.BNK_CODE
  AND cbk.CBK_STN_CODE = bnk.BNK_STN_CODE
 WHERE sj.KAI_CODE = /*kaiCode*/
 AND  sj.TRI_CODE = /*triCode*/
 /*IF tjkCode != "" */ AND sj.TJK_CODE  /*$tjkCode*/ /*END*/ -- LIKE
 /*IF termBasisDate != null */ 
   AND sj.STR_DATE <= /*termBasisDate*/ 
   AND sj.END_DATE >= /*termBasisDate*/ 
 /*END*/
  
ORDER BY
   sj.TJK_CODE
	