UPDATE 
	USR_MST			-- ���[�U�[�}�X�^
	
 SET 
	KAI_CODE = /*entity.kAI_CODE*/,					-- ��ЃR�[�h
	USR_CODE = /*entity.uSR_CODE*/,					-- ���[�U�[�R�[�h
	PASSWORD = /*entity.pASSWORD*/,					-- �p�X���[�h
	LNG_CODE = /*entity.lNG_CODE*/,					-- ����R�[�h
	USR_NAME = /*entity.uSR_NAME*/,					-- ���[�U�[����
	USR_NAME_S = /*entity.uSR_NAME_S*/,				-- ���[�U�[����
	USR_NAME_K = /*entity.uSR_NAME_K*/,				-- ���[�U�[��������
	SYS_KBN = /*entity.sYS_KBN*/,					-- �V�X�e���敪
	PRC_KEN = /*entity.pRC_KEN*/,					-- �����������x��
	UPD_KEN = /*entity.uPD_KEN*/,					-- �X�V�������x��
	EMP_CODE = /*entity.eMP_CODE*/,					-- �Ј��R�[�h
	DEP_CODE = /*entity.dEP_CODE*/,					-- ��������R�[�h
	KEIRI_KBN = /*entity.kEIRI_KBN*/,				-- �o���S���ҋ敪
	STR_DATE = /*entity.sTR_DATE*/,					-- �J�n�N����
	END_DATE = /*entity.eND_DATE*/,					-- �I���N����
	INP_DATE = (SELECT INP_DATE FROM USR_MST WHERE KAI_CODE = /*oldKaiCode*/ AND USR_CODE = /*entity.uSR_CODE*/),-- �o�^���t
	UPD_DATE = /*entity.uPD_DATE*/,					-- �X�V���t
	PRG_ID = /*entity.pRG_ID*/,						-- �v���O�����h�c
	USR_ID = /*entity.uSR_ID*/,						-- ���[�U�[�h�c
	PWD_UPD_DATE = /*entity.pWD_UPD_DATE*/			-- �p�X���[�h�X�V����

 WHERE
 	KAI_CODE = /*oldKaiCode*/				-- ��ЃR�[�h
 	AND USR_CODE = /*entity.uSR_CODE*/				-- ���[�U�[�R�[�h