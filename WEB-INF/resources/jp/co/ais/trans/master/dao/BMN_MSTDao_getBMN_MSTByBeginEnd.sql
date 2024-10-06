SELECT
 * 
FROM BMN_MST
WHERE
  KAI_CODE = /*kaiCode*/
    AND DEP_CODE = /*deptCode*/
    
  /*IF beginCode != null && !"".equals(beginCode) */
    AND DEP_CODE >= /*beginCode*/
  /*END*/
  
  /*IF endCode != null && !"".equals(endCode) */
    AND DEP_CODE <= /*endCode*/
  /*END*/
