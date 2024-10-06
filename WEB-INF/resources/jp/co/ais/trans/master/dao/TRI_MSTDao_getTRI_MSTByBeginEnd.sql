SELECT
 * 
FROM TRI_MST
WHERE
  KAI_CODE = /*kaiCode*/
    AND TRI_CODE = /*triCode*/
    
  /*IF beginCode != null && !"".equals(beginCode) */
    AND TRI_CODE >= /*beginCode*/
  /*END*/
  
  /*IF endCode != null && !"".equals(endCode) */
    AND TRI_CODE <= /*endCode*/
  /*END*/