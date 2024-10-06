UPDATE SWK_DTL
SET 
	UPD_DATE = /*bean.UPD_DATE*/,-- システム日付
	PRG_ID   = /*bean.PRG_ID*/,-- プログラムID
	USR_ID   = /*bean.USR_ID*/-- ユーザーコード

WHERE 
	KAI_CODE         = /*bean.KAI_CODE*/-- 会社コード
	AND SWK_DEN_DATE = /*bean.SWK_DEN_DATE*/-- 伝票日付
	AND SWK_DEN_NO   = /*bean.SWK_DEN_NO*/-- 伝票番号