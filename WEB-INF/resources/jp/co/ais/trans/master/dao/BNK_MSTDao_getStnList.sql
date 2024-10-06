SELECT 
	BNK_STN_CODE, -- Žx“XƒR[ƒh
	BNK_STN_NAME_S, -- Žx“X–¼Ì
	BNK_STN_NAME_K,  -- Žx“XŒŸõ—ªÌ
	STR_DATE, -- ŠúŒÀŠJŽn“ú
	END_DATE  -- ŠúŒÀI—¹“ú
FROM BNK_MST 
WHERE BNK_CODE = /*param.bnkCode*/

/*IF param.bnkStnCode != "" */ AND BNK_STN_CODE = /*param.bnkStnCode*/ /*END*/ 
/*IF param.likeBnkStnCode != "" */ AND BNK_STN_CODE  /*$param.likeBnkStnCode*/ /*END*/ -- LIKE
/*IF param.likeBnkStnName != "" */ AND BNK_STN_NAME_S  /*$param.likeBnkStnName*/ /*END*/ -- LIKE
/*IF param.likeBnkStnNameK != "" */ AND BNK_STN_NAME_K  /*$param.likeBnkStnNameK*/ /*END*/ -- LIKE
/*IF param.bnkStnCodeBegin != null && param.bnkStnCodeBegin !="" */ AND BNK_STN_CODE >= /*param.bnkStnCodeBegin*/ /*END*/
/*IF param.bnkStnCodeEnd != null && param.bnkStnCodeEnd !="" */ AND BNK_STN_CODE <= /*param.bnkStnCodeEnd*/ /*END*/
/*IF param.termBasisDate != null */ 
  AND STR_DATE <= /*param.termBasisDate*/ 
  AND END_DATE >= /*param.termBasisDate*/ 
/*END*/

ORDER BY BNK_STN_CODE