SELECT A.*
FROM (
SELECT AP.CBK_CBK_CODE,
 /*$tablespace*/ CONCAT( NVL(BNK.BNK_NAME_S,'') ,/*$tablespace*/ CONCAT( ' ' , NVL(BNK.BNK_STN_NAME_S,''))) AS CBK_NAME,
 /*$tablespace*/ CONCAT(CASE AP.CBK_YKN_KBN
    WHEN 1 THEN /*word1*/
    WHEN 2 THEN /*word2*/
    WHEN 3 THEN /*word3*/
    WHEN 4 THEN /*word4*/
    ELSE /*wordUnkown*/
  END ,/*$tablespace*/ CONCAT(' ', NVL(AP.CBK_YKN_NO,'') ))AS CBK_YKN_NO
FROM AP_CBK_MST AP
LEFT OUTER JOIN BNK_MST BNK
ON BNK.BNK_CODE = AP.CBK_BNK_CODE
AND BNK.BNK_STN_CODE = AP.CBK_STN_CODE
WHERE AP.KAI_CODE = /*kaiCode*/
/*BEGIN*/AND
/*IF cbkCode != "" */ AP.CBK_CBK_CODE  /*$cbkCode*/ /*END*/ -- LIKE
/*IF beginCode != null  &&  beginCode != "" */ AND AP.CBK_CBK_CODE >= /*beginCode*/ /*END*/
/*IF endCode != null &&  endCode != "" */ AND AP.CBK_CBK_CODE <= /*endCode*/ /*END*/
/*END*/
) A
/*BEGIN*/WHERE
/*IF bnkName != "" */ A.CBK_NAME  /*$bnkName*/ /*END*/ -- LIKE
/*IF cbkYknNo != "" */ AND A.CBK_YKN_NO  /*$cbkYknNo*/ /*END*/ -- LIKE
/*END*/
ORDER BY CBK_CBK_CODE