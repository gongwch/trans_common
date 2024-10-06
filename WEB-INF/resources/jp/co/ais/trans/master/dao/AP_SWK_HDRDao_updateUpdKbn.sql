UPDATE
  AP_SWK_HDR
SET
  SWK_UPD_KBN =/*dto.SWK_UPD_KBN*/,  -- 更新区分
  SWK_SYO_EMP_CODE = /*dto.SWK_SYO_EMP_CODE*/, -- 承認者
  SWK_SYO_DATE = /*dto.SWK_SYO_DATE*/,  -- 承認更新日付
  UPD_DATE = /*dto.UPD_DATE*/, -- 更新日付
  PRG_ID   = /*dto.PRG_ID*/,   -- 更新プログラム
  USR_ID   = /*dto.USR_ID*/    -- ユーザコード
WHERE
  SWK_DEN_NO =/*dto.SWK_DEN_NO*/  -- 伝票番号
  AND SWK_UPD_KBN <> '4'