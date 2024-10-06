 UPDATE AP_SWK_HDR 
  SET       
  		--  更新回数
		SWK_UPD_CNT   = /*bean.SWK_UPD_CNT*/,
		
		-- 支払決済区分
		SWK_KESAI_KBN      = /*bean.SWK_KESAI_KBN*/,
		
		-- 受付番号
		SWK_UTK_NO      = /*bean.SWK_UTK_NO*/,
		
		--  更新日付
		UPD_DATE      = /*bean.UPD_DATE*/,

		--  プログラムID
		PRG_ID        = /*bean.PRG_ID*/,
		
		--  ユーザーコード
		USR_ID        = /*bean.USR_ID*/
		
 WHERE 
		--  会社コード
		KAI_CODE         = /*bean.KAI_CODE*/
		--  計上日
		AND SWK_DEN_DATE = /*bean.SWK_DEN_DATE*/
		--  伝票番号 
		AND SWK_DEN_NO   = /*bean.SWK_DEN_NO*/
			
			