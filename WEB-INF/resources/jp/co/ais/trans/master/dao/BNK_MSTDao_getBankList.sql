SELECT 
	BNK_CODE, -- 銀行コード
	BNK_NAME_S, -- 名称
	BNK_NAME_K, -- 検索略称
	MIN(STR_DATE) STR_DATE, -- 期限開始日
    MAX(END_DATE) END_DATE  -- 期限終了日
FROM BNK_MST
 
WHERE 1 = 1
/*IF param.bnkCode != "" */ AND BNK_CODE = /*param.bnkCode*/ /*END*/
/*IF param.likeBnkCode != "" */ AND BNK_CODE  /*$param.likeBnkCode*/ /*END*/ -- LIKE
/*IF param.likeBnkName != "" */AND BNK_NAME_S  /*$param.likeBnkName*/ /*END*/ -- LIKE
/*IF param.likeBnkNameK != "" */AND BNK_NAME_K  /*$param.likeBnkNameK*/ /*END*/ -- LIKE

/*IF param.bnkCodeBegin != null && param.bnkCodeBegin != "" */ AND BNK_CODE >= /*param.bnkCodeBegin*/ /*END*/
/*IF param.bnkCodeEnd != null && param.bnkCodeEnd != "" */ AND BNK_CODE <= /*param.bnkCodeEnd*/ /*END*/

/*IF param.termBasisDate != null */ 
  AND STR_DATE <= /*param.termBasisDate*/ 
  AND END_DATE >= /*param.termBasisDate*/ 
/*END*/

GROUP BY BNK_CODE,BNK_NAME_S,BNK_NAME_K

ORDER BY BNK_CODE