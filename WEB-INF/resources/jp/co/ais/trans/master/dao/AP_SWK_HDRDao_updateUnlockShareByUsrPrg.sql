UPDATE AP_SWK_HDR
SET
    SWK_SHR_KBN =0           -- �r���t���O 0:�ʏ�
WHERE
    KAI_CODE =/*kaiCode*/    -- ��ЃR�[�h
AND USR_ID   =/*usrId*/      -- ���[�UID
AND PRG_ID   =/*prgId*/      -- �v���O����ID
AND SWK_SHR_KBN =1           -- �r���t���O 1:�X�V��
