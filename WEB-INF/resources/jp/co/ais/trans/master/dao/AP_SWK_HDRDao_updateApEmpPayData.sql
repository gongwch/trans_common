 UPDATE AP_SWK_HDR 
  SET       
        --  �X�V��
  		/*IF bean.SWK_UPD_CNT !=null && !"".equals(bean.SWK_UPD_CNT)*/
			SWK_UPD_CNT   = /*bean.SWK_UPD_CNT*/,
		/*END*/	
		
		--  �x����
		/*IF bean.SWK_SIHA_DATE !=null && !"".equals(bean.SWK_SIHA_DATE)*/
			SWK_SIHA_DATE = /*bean.SWK_SIHA_DATE*/,
		/*END*/	
		
		--  �x�����@
		/*IF bean.SWK_HOH_CODE !=null && !"".equals(bean.SWK_HOH_CODE)*/
			SWK_HOH_CODE  = /*bean.SWK_HOH_CODE*/,
		/*END*/	
		
		--  ��s����
		/*IF bean.SWK_CBK_CODE !=null && !"".equals(bean.SWK_CBK_CODE)*/
			SWK_CBK_CODE  = /*bean.SWK_CBK_CODE*/,
		/*END*/		
		
		
		-- ��t�ԍ�
		/*IF bean.SWK_UTK_NO !=null && !"".equals(bean.SWK_UTK_NO)*/
			SWK_UTK_NO      = /*bean.SWK_UTK_NO*/,
		/*END*/
		
		-- �������Z�`�[�ԍ�
		/*IF bean.SWK_KARI_CR_DEN_NO !=null && !"".equals(bean.SWK_KARI_CR_DEN_NO)*/
			SWK_KARI_CR_DEN_NO  = /*bean.SWK_KARI_CR_DEN_NO*/,
		/*END*/
		
		--  �X�V���t
		UPD_DATE      = /*bean.UPD_DATE*/,

		--  �v���O����ID
		PRG_ID        = /*bean.PRG_ID*/,
		
		--  ���[�U�[�R�[�h
		USR_ID        = /*bean.USR_ID*/
		/*IF bean.kbn==1*/
			--  �x�����ϋ敪
			 ,SWK_KESAI_KBN = /*bean.SWK_KESAI_KBN*/			
		/*END*/
		
 WHERE 
		--  ��ЃR�[�h
		KAI_CODE         = /*bean.KAI_CODE*/
		/*IF bean.kbn==0*/
			--  �`�[���t
			AND SWK_DEN_DATE = /*bean.SWK_DEN_DATE*/			
			--  �`�[�ԍ� 
			AND SWK_DEN_NO   = /*bean.SWK_DEN_NO*/
		/*END*/
		/*IF bean.kbn==1*/
			--  ��tNO
			AND SWK_UTK_NO = /*bean.SWK_UTK_NO*/			
		/*END*/
