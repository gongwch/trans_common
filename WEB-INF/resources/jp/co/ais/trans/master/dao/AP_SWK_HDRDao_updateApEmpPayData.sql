 UPDATE AP_SWK_HDR 
  SET       
        --  XVñ
  		/*IF bean.SWK_UPD_CNT !=null && !"".equals(bean.SWK_UPD_CNT)*/
			SWK_UPD_CNT   = /*bean.SWK_UPD_CNT*/,
		/*END*/	
		
		--  x¥ú
		/*IF bean.SWK_SIHA_DATE !=null && !"".equals(bean.SWK_SIHA_DATE)*/
			SWK_SIHA_DATE = /*bean.SWK_SIHA_DATE*/,
		/*END*/	
		
		--  x¥û@
		/*IF bean.SWK_HOH_CODE !=null && !"".equals(bean.SWK_HOH_CODE)*/
			SWK_HOH_CODE  = /*bean.SWK_HOH_CODE*/,
		/*END*/	
		
		--  âsûÀ
		/*IF bean.SWK_CBK_CODE !=null && !"".equals(bean.SWK_CBK_CODE)*/
			SWK_CBK_CODE  = /*bean.SWK_CBK_CODE*/,
		/*END*/		
		
		
		-- ótÔ
		/*IF bean.SWK_UTK_NO !=null && !"".equals(bean.SWK_UTK_NO)*/
			SWK_UTK_NO      = /*bean.SWK_UTK_NO*/,
		/*END*/
		
		-- ¼¥¸Z`[Ô
		/*IF bean.SWK_KARI_CR_DEN_NO !=null && !"".equals(bean.SWK_KARI_CR_DEN_NO)*/
			SWK_KARI_CR_DEN_NO  = /*bean.SWK_KARI_CR_DEN_NO*/,
		/*END*/
		
		--  XVút
		UPD_DATE      = /*bean.UPD_DATE*/,

		--  vOID
		PRG_ID        = /*bean.PRG_ID*/,
		
		--  [U[R[h
		USR_ID        = /*bean.USR_ID*/
		/*IF bean.kbn==1*/
			--  x¥Ïæª
			 ,SWK_KESAI_KBN = /*bean.SWK_KESAI_KBN*/			
		/*END*/
		
 WHERE 
		--  ïÐR[h
		KAI_CODE         = /*bean.KAI_CODE*/
		/*IF bean.kbn==0*/
			--  `[út
			AND SWK_DEN_DATE = /*bean.SWK_DEN_DATE*/			
			--  `[Ô 
			AND SWK_DEN_NO   = /*bean.SWK_DEN_NO*/
		/*END*/
		/*IF bean.kbn==1*/
			--  ótNO
			AND SWK_UTK_NO = /*bean.SWK_UTK_NO*/			
		/*END*/
