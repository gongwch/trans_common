UPDATE GL_SWK_HDR
SET
    SWK_SHR_KBN = 1,        -- 排他フラグ 1:更新中
    PRG_ID   =/*prgId*/,    -- 更新プログラム
    USR_ID   =/*usrId*/      -- ユーザID
WHERE
    KAI_CODE =/*kaiCode*/    -- 会社コード
AND SWK_DEN_NO =/*denNo*/    -- 伝票番号
AND SWK_SHR_KBN = 0           -- 排他フラグ 
