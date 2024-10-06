UPDATE SWK_DTL
   SET SWK_KESI_KBN      = 0,
       SWK_KESI_DEN_NO   = NULL,
       SWK_KESI_DATE     = NULL,
       SWK_SOUSAI_DATE   = NULL,
       SWK_SOUSAI_DEN_NO = NULL,
       PRG_ID            = /*param.programCode*/,
       USR_ID            = /*param.userCode*/,
       UPD_DATE          = /*param.updateDateFrom*/
 WHERE KAI_CODE          = /*param.companyCode*/
   AND SWK_KESI_KBN      = 1

 /*IF param.eraseNo != null && !"".equals(param.eraseNo)*/
   AND SWK_KESI_DEN_NO   = /*param.eraseNo*/
 /*END*/

 /*IF param.slipNo != null && !"".equals(param.slipNo)*/
   AND SWK_SOUSAI_DEN_NO = /*param.slipNo*/
 /*END*/
