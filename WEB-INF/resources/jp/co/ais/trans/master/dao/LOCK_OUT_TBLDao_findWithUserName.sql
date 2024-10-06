SELECT 
 LO.USR_CODE,   --  ユーザコード
 LO.FAIL_DATE,  --  失敗日時
 US.USR_NAME 	--  ユーザ名称

FROM 
 LOCK_OUT_TBL LO 
  LEFT OUTER JOIN USR_MST US ON LO.KAI_CODE = US.KAI_CODE
                             AND LO.USR_CODE = US.USR_CODE

WHERE 
 LO.KAI_CODE = /*kaiCode*/ 

  /*IF arrCount == 0 */
     AND LO.FAIL_COUNT = /*arrCount*/
 
  -- ELSE
  
     AND LO.FAIL_COUNT >= /*arrCount*/
  /*END*/
 


