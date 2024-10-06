UPDATE AP_SWK_HDR
SET
    SWK_SHR_KBN =0,        -- 排他フラグ 0:通常
    PRG_ID   =/*prgId*/     -- 更新プログラム
WHERE
    KAI_CODE =/*kaiCode*/    -- 会社コード
AND USR_ID   =/*usrId*/      -- ユーザID
AND SWK_SHR_KBN =1           -- 排他フラグ 1:更新中
