UPDATE SWK_DTL
   SET SWK_KESI_KBN      = 1,    
       SWK_KESI_DEN_NO   = /*entity.sWK_KESI_DEN_NO*/,
       SWK_KESI_DATE     = /*entity.sWK_KESI_DATE*/,
       SWK_SOUSAI_DATE   = /*entity.sWK_SOUSAI_DATE*/,
       SWK_SOUSAI_DEN_NO = /*entity.sWK_SOUSAI_DEN_NO*/,
       SWK_SOUSAI_GYO_NO = /*entity.sWK_SOUSAI_GYO_NO*/,
       PRG_ID            = /*entity.pRG_ID*/,
       USR_ID            = /*entity.uSR_ID*/,
       UPD_DATE          = /*entity.uPD_DATE*/
 WHERE KAI_CODE          = /*entity.kAI_CODE*/
   AND SWK_DEN_NO        = /*entity.sWK_DEN_NO*/
   AND SWK_GYO_NO        = /*entity.sWK_GYO_NO*/
   AND SWK_KESI_KBN      = 0
   AND SWK_ADJ_KBN       <> 2 