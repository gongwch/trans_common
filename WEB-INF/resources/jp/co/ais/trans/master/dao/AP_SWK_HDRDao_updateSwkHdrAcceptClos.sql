 UPDATE AP_SWK_HDR 
  SET       
  		--  �X�V��
  		/*IF bean.SWK_UPD_CNT !=null && !"".equals(bean.SWK_UPD_CNT)*/
			SWK_UPD_CNT   = /*bean.SWK_UPD_CNT*/,
		/*END*/
		
		-- �x�����ϋ敪
		/*IF bean.SWK_KESAI_KBN !=null && !"".equals(bean.SWK_KESAI_KBN)*/
			SWK_KESAI_KBN      = /*bean.SWK_KESAI_KBN*/,
		/*END*/
		
		-- ��t�ԍ�
		/*IF bean.SWK_UTK_NO !=null && !"".equals(bean.SWK_UTK_NO)*/
			SWK_UTK_NO      = /*bean.SWK_UTK_NO*/,
		/*END*/
		
		--  �X�V���t
		UPD_DATE      = /*bean.UPD_DATE*/,

		--  �v���O����ID
		PRG_ID        = /*bean.PRG_ID*/,
		
		--  ���[�U�[�R�[�h
		USR_ID        = /*bean.USR_ID*/
		
 WHERE 
		--  ��ЃR�[�h
		KAI_CODE         = /*bean.KAI_CODE*/
		--  �v���
		AND SWK_DEN_DATE = /*bean.SWK_DEN_DATE*/
		--  �`�[�ԍ� 
		AND SWK_DEN_NO   = /*bean.SWK_DEN_NO*/
			
			