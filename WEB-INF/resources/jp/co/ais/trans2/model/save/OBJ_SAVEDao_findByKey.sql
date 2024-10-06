SELECT *
FROM   OBJ_SAVE
WHERE  KAI_CODE = /*kAI_CODE*/
AND     USR_ID = /*uSR_ID*/
AND     PRG_ID = /*pRG_ID*/
/*IF sEQ != null */
AND     SEQ = /*sEQ*/
/*END*/
