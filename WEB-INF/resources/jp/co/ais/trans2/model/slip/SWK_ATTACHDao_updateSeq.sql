UPDATE SWK_ATTACH o
SET    SEQ = (SELECT SEQ
              FROM   (SELECT KAI_CODE
                            ,SWK_DEN_NO
                            ,SEQ AS OLD_SEQ
                            ,ROWNUM AS SEQ
                      FROM   SWK_ATTACH
                      WHERE  KAI_CODE = /*kAI_CODE*/
                      AND    SWK_DEN_NO = /*sWK_DEN_NO*/
                      ORDER  BY SEQ, INP_DATE) p
              WHERE  p.KAI_CODE = o.KAI_CODE
              AND    p.SWK_DEN_NO = o.SWK_DEN_NO
              AND    p.OLD_SEQ = o.SEQ
                     )
WHERE  KAI_CODE = /*kAI_CODE*/
AND    SWK_DEN_NO = /*sWK_DEN_NO*/
