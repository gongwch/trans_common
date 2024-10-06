SELECT T.HOH_HOH_CODE AS HOH_HOH_CODE,         
 T.HOH_HOH_NAME AS HOH_HOH_NAME,   
 T.HOH_HOH_NAME_K AS HOH_HOH_NAME_K 
 FROM AP_HOH_MST T 
WHERE T.HOH_SIH_KBN = 1
AND T.KAI_CODE = /*kaiCode*/

 /*IF hohCode != null  &&  hohCode != ""*/
 	AND T.HOH_HOH_CODE  /*$hohCode*/ -- LIKE
 /*END*/
  
 /*IF hohName != null  &&  hohName != ""*/
 	AND T.HOH_HOH_NAME  /*$hohName*/ -- LIKE
 /*END*/
 
 /*IF hohNameK != null  &&  hohNameK != ""*/
 	AND T.HOH_HOH_NAME_K  /*$hohNameK*/ -- LIKE
 /*END*/
