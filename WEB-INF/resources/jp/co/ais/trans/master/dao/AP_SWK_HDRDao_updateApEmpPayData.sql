 UPDATE AP_SWK_HDR 
  SET       
        --  更新回数
  		/*IF bean.SWK_UPD_CNT !=null && !"".equals(bean.SWK_UPD_CNT)*/
			SWK_UPD_CNT   = /*bean.SWK_UPD_CNT*/,
		/*END*/	
		
		--  支払日
		/*IF bean.SWK_SIHA_DATE !=null && !"".equals(bean.SWK_SIHA_DATE)*/
			SWK_SIHA_DATE = /*bean.SWK_SIHA_DATE*/,
		/*END*/	
		
		--  支払方法
		/*IF bean.SWK_HOH_CODE !=null && !"".equals(bean.SWK_HOH_CODE)*/
			SWK_HOH_CODE  = /*bean.SWK_HOH_CODE*/,
		/*END*/	
		
		--  銀行口座
		/*IF bean.SWK_CBK_CODE !=null && !"".equals(bean.SWK_CBK_CODE)*/
			SWK_CBK_CODE  = /*bean.SWK_CBK_CODE*/,
		/*END*/		
		
		
		-- 受付番号
		/*IF bean.SWK_UTK_NO !=null && !"".equals(bean.SWK_UTK_NO)*/
			SWK_UTK_NO      = /*bean.SWK_UTK_NO*/,
		/*END*/
		
		-- 仮払精算伝票番号
		/*IF bean.SWK_KARI_CR_DEN_NO !=null && !"".equals(bean.SWK_KARI_CR_DEN_NO)*/
			SWK_KARI_CR_DEN_NO  = /*bean.SWK_KARI_CR_DEN_NO*/,
		/*END*/
		
		--  更新日付
		UPD_DATE      = /*bean.UPD_DATE*/,

		--  プログラムID
		PRG_ID        = /*bean.PRG_ID*/,
		
		--  ユーザーコード
		USR_ID        = /*bean.USR_ID*/
		/*IF bean.kbn==1*/
			--  支払決済区分
			 ,SWK_KESAI_KBN = /*bean.SWK_KESAI_KBN*/			
		/*END*/
		
 WHERE 
		--  会社コード
		KAI_CODE         = /*bean.KAI_CODE*/
		/*IF bean.kbn==0*/
			--  伝票日付
			AND SWK_DEN_DATE = /*bean.SWK_DEN_DATE*/			
			--  伝票番号 
			AND SWK_DEN_NO   = /*bean.SWK_DEN_NO*/
		/*END*/
		/*IF bean.kbn==1*/
			--  受付NO
			AND SWK_UTK_NO = /*bean.SWK_UTK_NO*/			
		/*END*/
