UPDATE AP_SWK_HDR
SET
    SWK_SHR_KBN =0,        -- �r���t���O 0:�ʏ�
    PRG_ID   =/*prgId*/     -- �X�V�v���O����
WHERE
    KAI_CODE =/*kaiCode*/    -- ��ЃR�[�h
AND USR_ID   =/*usrId*/      -- ���[�UID
AND SWK_SHR_KBN =1           -- �r���t���O 1:�X�V��
