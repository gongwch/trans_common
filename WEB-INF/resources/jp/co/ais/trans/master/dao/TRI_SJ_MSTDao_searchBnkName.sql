SELECT
  TJK_CODE,
  STR_DATE,
  END_DATE,
  BNK_CODE,
  BNK_STN_CODE,
  YKN_NO  
FROM
 TRI_SJ_MST 
 
 WHERE 
	KAI_CODE=/*kaiCode*/

 AND TRI_CODE = /*triCode*/
 AND TJK_CODE = /*tjkCode*/
	