DELETE FROM SWK_ATTACH
WHERE  SWK_DEN_NO = /*sWK_DEN_NO*/
/*IF kAI_CODE !=null && !kAI_CODE.equals("")*/
AND    KAI_CODE = /*kAI_CODE*/
/*END*/
/*IF sEQ !=null */
AND    SEQ = /*sEQ*/
/*END*/
