UPDATE
  AP_SWK_HDR
SET
  SWK_UPD_KBN =/*dto.SWK_UPD_KBN*/,  -- �X�V�敪
  SWK_SYO_EMP_CODE = /*dto.SWK_SYO_EMP_CODE*/, -- ���F��
  SWK_SYO_DATE = /*dto.SWK_SYO_DATE*/,  -- ���F�X�V���t
  UPD_DATE = /*dto.UPD_DATE*/, -- �X�V���t
  PRG_ID   = /*dto.PRG_ID*/,   -- �X�V�v���O����
  USR_ID   = /*dto.USR_ID*/    -- ���[�U�R�[�h
WHERE
  SWK_DEN_NO =/*dto.SWK_DEN_NO*/  -- �`�[�ԍ�
  AND SWK_UPD_KBN <> '4'