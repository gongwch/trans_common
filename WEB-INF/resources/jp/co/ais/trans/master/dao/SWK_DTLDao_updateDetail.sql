UPDATE SWK_DTL
SET 
	UPD_DATE = /*bean.UPD_DATE*/,-- �V�X�e�����t
	PRG_ID   = /*bean.PRG_ID*/,-- �v���O����ID
	USR_ID   = /*bean.USR_ID*/-- ���[�U�[�R�[�h

WHERE 
	KAI_CODE         = /*bean.KAI_CODE*/-- ��ЃR�[�h
	AND SWK_DEN_DATE = /*bean.SWK_DEN_DATE*/-- �`�[���t
	AND SWK_DEN_NO   = /*bean.SWK_DEN_NO*/-- �`�[�ԍ�