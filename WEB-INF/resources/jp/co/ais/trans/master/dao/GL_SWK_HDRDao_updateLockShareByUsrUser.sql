UPDATE GL_SWK_HDR
SET
    SWK_SHR_KBN = 1,        -- �r���t���O 1:�X�V��
    PRG_ID   =/*prgId*/,    -- �X�V�v���O����
    USR_ID   =/*usrId*/      -- ���[�UID
WHERE
    KAI_CODE =/*kaiCode*/    -- ��ЃR�[�h
AND SWK_DEN_NO =/*denNo*/    -- �`�[�ԍ�
AND SWK_SHR_KBN = 0           -- �r���t���O 
