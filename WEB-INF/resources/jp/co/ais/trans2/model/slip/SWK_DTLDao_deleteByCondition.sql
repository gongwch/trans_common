DELETE FROM SWK_DTL
WHERE SWK_DEN_NO = /*param.slipNo*/
  /*IF param.companyCode != null && !"".equals(param.companyCode) */
    AND KAI_CODE = /*param.companyCode*/
  /*END*/