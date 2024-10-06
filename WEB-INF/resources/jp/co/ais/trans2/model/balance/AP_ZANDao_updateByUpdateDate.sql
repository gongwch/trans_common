UPDATE AP_ZAN
SET
 USR_ID=             /*entity.uSR_ID*/,
 PRG_ID=             /*entity.pRG_ID*/,
 UPD_DATE=           /*sysDate*/,
 ZAN_SIHA_GYO_NO=    /*entity.zAN_SIHA_GYO_NO*/,
 ZAN_CUR_CODE=       /*entity.zAN_CUR_CODE*/,
 ZAN_CUR_RATE=       /*entity.zAN_CUR_RATE*/,
 ZAN_DATA_KBN=       /*entity.zAN_DATA_KBN*/,
 ZAN_DC_KBN=         /*entity.zAN_DC_KBN*/,
 ZAN_DEN_SYU=        /*entity.zAN_DEN_SYU*/,
 ZAN_DEP_CODE=       /*entity.zAN_DEP_CODE*/,
 ZAN_FURI_BNK_CODE=  /*entity.zAN_FURI_BNK_CODE*/,
 ZAN_FURI_CBK_CODE=  /*entity.zAN_FURI_CBK_CODE*/,
 ZAN_FURI_KAGEN_KBN=  /*entity.zAN_FURI_KAGEN_KBN*/,
 ZAN_FURI_STN_CODE=   /*entity.zAN_FURI_STN_CODE*/,
 ZAN_FURI_TESU_KBN=   /*entity.zAN_FURI_TESU_KBN*/,
 ZAN_HKM_CODE=        /*entity.zAN_HKM_CODE*/,
 ZAN_HOH_CODE=        /*entity.zAN_HOH_CODE*/,
 ZAN_HORYU_KBN=       /*entity.zAN_HORYU_KBN*/,
 ZAN_IN_SIHA_KIN=     /*entity.zAN_IN_SIHA_KIN*/,
 ZAN_INP_DATE=        /*entity.zAN_INP_DATE*/,
 ZAN_KESAI_KBN=       /*entity.zAN_KESAI_KBN*/,
 ZAN_KIN=             /*entity.zAN_KIN*/,
 ZAN_KMK_CODE=        /*entity.zAN_KMK_CODE*/,
 ZAN_LIST_KBN=        /*entity.zAN_LIST_KBN*/,
 ZAN_NAI_CODE=        /*entity.zAN_NAI_CODE*/,
 ZAN_SEI_NO=          /*entity.zAN_SEI_NO*/,
 ZAN_SIHA_CODE=       /*entity.zAN_SIHA_CODE*/,
 ZAN_SIHA_DATE=       /*entity.zAN_SIHA_DATE*/,
 ZAN_SIHA_DEN_NO=     /*entity.zAN_SIHA_DEN_NO*/,
 ZAN_SIHA_KAGEN_KBN=  /*entity.zAN_SIHA_KAGEN_KBN*/,
 ZAN_SIHA_KBN=        /*entity.zAN_SIHA_KBN*/,
 ZAN_SYS_KBN=         /*entity.zAN_SYS_KBN*/,
 ZAN_TEG_BNK_CODE=    /*entity.zAN_TEG_BNK_CODE*/,
 ZAN_TEG_CBK_CODE=    /*entity.zAN_TEG_CBK_CODE*/,
 ZAN_TEG_KAGEN_KBN=   /*entity.zAN_TEG_KAGEN_KBN*/,
 ZAN_TEG_SAITO=       /*entity.zAN_TEG_SAITO*/,
 ZAN_TEG_STN_CODE=    /*entity.zAN_TEG_STN_CODE*/,
 ZAN_TEK=             /*entity.zAN_TEK*/,
 ZAN_TEK_CODE=        /*entity.zAN_TEK_CODE*/,
 ZAN_TJK_CODE=        /*entity.zAN_TJK_CODE*/,
 ZAN_UKE_DEP_CODE=    /*entity.zAN_UKE_DEP_CODE*/,
 ZAN_UKM_CODE=        /*entity.zAN_UKM_CODE*/,
 ZAN_UTK_NO=          /*entity.zAN_UTK_NO*/,
 ZAN_YUSO_KBN=        /*entity.zAN_YUSO_KBN*/,
 ZAN_YUSO_TESU_KBN=   /*entity.zAN_YUSO_TESU_KBN*/
WHERE KAI_CODE     = /*entity.kAI_CODE*/
  AND ZAN_DEN_NO   = /*entity.zAN_DEN_NO*/
  AND ZAN_DEN_DATE = /*entity.zAN_DEN_DATE*/
  AND ZAN_GYO_NO   = /*entity.zAN_GYO_NO*/
  /*IF entity.uPD_DATE != null */
  AND UPD_DATE     = /*entity.uPD_DATE*/
  -- ELSE
  AND UPD_DATE IS NULL
  /*END*/
