 UPDATE AP_SWK_HDR 
  SET       
  		--  �X�V��
		SWK_UPD_CNT   = /*bean.SWK_UPD_CNT*/,
		
		-- �x�����ϋ敪
		SWK_KESAI_KBN      = /*bean.SWK_KESAI_KBN*/,
		
		-- ��t�ԍ�
		SWK_UTK_NO      = /*bean.SWK_UTK_NO*/,
		
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
			
			