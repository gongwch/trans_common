DELETE FROM 
  SWK_DTL 
WHERE 
  KAI_CODE = /*kaiCode*/
   AND SWK_DEN_NO = /*swkDenNo*/
   AND (KAI_CODE, 
        SWK_DEN_NO,
        SWK_DEP_CODE,
        SWK_KMK_CODE, 
        NVL(SWK_HKM_CODE, ' '),
        NVL(SWK_UKM_CODE, ' '), 
        SWK_CUR_CODE, 
        NVL(SWK_TRI_CODE, ' '),
        NVL(SWK_EMP_CODE, ' '), 
        NVL(SWK_KNR_CODE_1, ' '),
        NVL(SWK_KNR_CODE_2, ' '), 
        NVL(SWK_KNR_CODE_3, ' '),
        NVL(SWK_KNR_CODE_4, ' '), 
        NVL(SWK_KNR_CODE_5, ' '),
        NVL(SWK_KNR_CODE_6, ' ')) IN
       (SELECT dat.KAI_CODE,
               dat.SWK_DEN_NO,
               dat.SWK_DEP_CODE,
               dat.SWK_KMK_CODE,
               NVL(dat.SWK_HKM_CODE, ' '),
               NVL(dat.SWK_UKM_CODE, ' '),
               dat.SWK_CUR_CODE,
               NVL(dat.SWK_TRI_CODE, ' '),
               NVL(dat.SWK_EMP_CODE, ' '),
               NVL(dat.SWK_KNR_CODE_1, ' '),
               NVL(dat.SWK_KNR_CODE_2, ' '),
               NVL(dat.SWK_KNR_CODE_3, ' '),
               NVL(dat.SWK_KNR_CODE_4, ' '),
               NVL(dat.SWK_KNR_CODE_5, ' '),
               NVL(dat.SWK_KNR_CODE_6, ' ')
          FROM (SELECT KAI_CODE,
                       SWK_DEN_NO,
                       SWK_DEP_CODE,
                       SWK_KMK_CODE,
                       SWK_HKM_CODE,
                       SWK_UKM_CODE,
                       SWK_CUR_CODE,
                       SWK_TRI_CODE,
                       SWK_EMP_CODE,
                       SWK_KNR_CODE_1,
                       SWK_KNR_CODE_2,
                       SWK_KNR_CODE_3,
                       SWK_KNR_CODE_4,
                       SWK_KNR_CODE_5,
                       SWK_KNR_CODE_6,
                       SUM(DECODE(SWK_DC_KBN,
                                  0,
                                  SWK_IN_KIN,
                                  1,
                                  SWK_IN_KIN * -1))
                 FROM SWK_DTL
                 WHERE 
                   KAI_CODE = /*kaiCode*/
                   AND SWK_DEN_NO = /*swkDenNo*/
                 GROUP BY KAI_CODE,
                          SWK_DEN_NO,
                          SWK_DEP_CODE,
                          SWK_KMK_CODE,
                          SWK_HKM_CODE,
                          SWK_UKM_CODE,
                          SWK_CUR_CODE,
                          SWK_TRI_CODE,
                          SWK_EMP_CODE,
                          SWK_KNR_CODE_1,
                          SWK_KNR_CODE_2,
                          SWK_KNR_CODE_3,
                          SWK_KNR_CODE_4,
                          SWK_KNR_CODE_5,
                          SWK_KNR_CODE_6
                HAVING SUM(DECODE(SWK_DC_KBN, 0, SWK_IN_KIN, 1, SWK_IN_KIN * -1)) = 0) tmp,
                SWK_DTL dat
         WHERE dat.KAI_CODE = tmp.KAI_CODE
           AND dat.SWK_DEN_NO = tmp.SWK_DEN_NO
           AND dat.SWK_DEP_CODE= tmp.SWK_DEP_CODE
           AND dat.SWK_KMK_CODE = tmp.SWK_KMK_CODE
           AND NVL(dat.SWK_HKM_CODE, ' ') = NVL(tmp.SWK_HKM_CODE, ' ')
           AND NVL(dat.SWK_UKM_CODE, ' ') = NVL(tmp.SWK_UKM_CODE, ' ')
           AND dat.SWK_CUR_CODE = tmp.SWK_CUR_CODE
           AND NVL(dat.SWK_TRI_CODE, ' ') = NVL(tmp.SWK_TRI_CODE, ' ')
           AND NVL(dat.SWK_EMP_CODE, ' ') = NVL(tmp.SWK_EMP_CODE, ' ')
           AND NVL(dat.SWK_KNR_CODE_1, ' ') = NVL(tmp.SWK_KNR_CODE_1, ' ')
           AND NVL(dat.SWK_KNR_CODE_2, ' ') = NVL(tmp.SWK_KNR_CODE_2, ' ')
           AND NVL(dat.SWK_KNR_CODE_3, ' ') = NVL(tmp.SWK_KNR_CODE_3, ' ')
           AND NVL(dat.SWK_KNR_CODE_4, ' ') = NVL(tmp.SWK_KNR_CODE_4, ' ')
           AND NVL(dat.SWK_KNR_CODE_5, ' ') = NVL(tmp.SWK_KNR_CODE_5, ' ')
           AND NVL(dat.SWK_KNR_CODE_6, ' ') = NVL(tmp.SWK_KNR_CODE_6, ' '))
           
           
           