UPDATE OBJ_SAVE o
SET    SEQ = (SELECT SEQ
              FROM   (SELECT KAI_CODE
                            ,USR_ID
                            ,PRG_ID
                            ,SEQ AS OLD_SEQ
                            ,KEY
                            ,ROWNUM AS SEQ
                      FROM   OBJ_SAVE
                      WHERE KAI_CODE = /*kAI_CODE*/
                      AND   USR_ID = /*uSR_ID*/
                      AND   PRG_ID = /*pRG_ID*/
                      ORDER  BY SEQ) p
              WHERE  p.KAI_CODE = o.KAI_CODE
              AND    p.USR_ID = o.USR_ID
              AND    p.PRG_ID = o.PRG_ID
              AND    p.OLD_SEQ = o.SEQ
              AND    p.KEY = o.KEY)
WHERE KAI_CODE = /*kAI_CODE*/
AND   USR_ID = /*uSR_ID*/
AND   PRG_ID = /*pRG_ID*/
