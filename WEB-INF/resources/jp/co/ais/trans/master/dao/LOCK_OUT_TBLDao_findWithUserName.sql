SELECT 
 LO.USR_CODE,   --  ���[�U�R�[�h
 LO.FAIL_DATE,  --  ���s����
 US.USR_NAME 	--  ���[�U����

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
 


