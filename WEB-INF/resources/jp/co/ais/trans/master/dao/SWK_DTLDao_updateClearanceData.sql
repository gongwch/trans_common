UPDATE SWK_DTL 
SET    SWK_SOUSAI_DATE = NULL,
       SWK_SOUSAI_DEN_NO = NULL,
       PRG_ID = /*prgId*/,
       USR_ID = /*usrId*/,
       UPD_DATE = /*updDate*/
WHERE  KAI_CODE = /*kaiCode*/
AND    SWK_SOUSAI_DEN_NO = /*denNo*/