UPDATE TRI_MST
SET IRAI_NAME = /*dto.iRAI_NAME*/,
    UPD_DATE = SYSDATE,
    PRG_ID = /*dto.pRG_ID*/,
    USR_ID = /*dto.uSR_ID*/
WHERE KAI_CODE = /*dto.kAI_CODE*/
AND TRI_CODE = /*dto.tRI_CODE*/