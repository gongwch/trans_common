UPDATE SWK_DTL 
SET    SWK_SOUSAI_DATE = /*denDate*/,
       SWK_SOUSAI_DEN_NO = /*seiDenNo*/,
       PRG_ID = /*prgId*/,
       USR_ID = /*usrId*/,
       UPD_DATE = /*updDate*/
WHERE  KAI_CODE = /*kaiCode*/
AND    SWK_DEN_NO = /*seiDenNo*/
AND    SWK_GYO_NO = /*gyoNo*/