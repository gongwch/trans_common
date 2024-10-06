UPDATE 
	USR_MST			-- ユーザーマスタ
	
 SET 
	KAI_CODE = /*entity.kAI_CODE*/,					-- 会社コード
	USR_CODE = /*entity.uSR_CODE*/,					-- ユーザーコード
	PASSWORD = /*entity.pASSWORD*/,					-- パスワード
	LNG_CODE = /*entity.lNG_CODE*/,					-- 言語コード
	USR_NAME = /*entity.uSR_NAME*/,					-- ユーザー名称
	USR_NAME_S = /*entity.uSR_NAME_S*/,				-- ユーザー略称
	USR_NAME_K = /*entity.uSR_NAME_K*/,				-- ユーザー検索名称
	SYS_KBN = /*entity.sYS_KBN*/,					-- システム区分
	PRC_KEN = /*entity.pRC_KEN*/,					-- 処理権限レベル
	UPD_KEN = /*entity.uPD_KEN*/,					-- 更新権限レベル
	EMP_CODE = /*entity.eMP_CODE*/,					-- 社員コード
	DEP_CODE = /*entity.dEP_CODE*/,					-- 所属部門コード
	KEIRI_KBN = /*entity.kEIRI_KBN*/,				-- 経理担当者区分
	STR_DATE = /*entity.sTR_DATE*/,					-- 開始年月日
	END_DATE = /*entity.eND_DATE*/,					-- 終了年月日
	INP_DATE = (SELECT INP_DATE FROM USR_MST WHERE KAI_CODE = /*oldKaiCode*/ AND USR_CODE = /*entity.uSR_CODE*/),-- 登録日付
	UPD_DATE = /*entity.uPD_DATE*/,					-- 更新日付
	PRG_ID = /*entity.pRG_ID*/,						-- プログラムＩＤ
	USR_ID = /*entity.uSR_ID*/,						-- ユーザーＩＤ
	PWD_UPD_DATE = /*entity.pWD_UPD_DATE*/			-- パスワード更新日時

 WHERE
 	KAI_CODE = /*oldKaiCode*/				-- 会社コード
 	AND USR_CODE = /*entity.uSR_CODE*/				-- ユーザーコード