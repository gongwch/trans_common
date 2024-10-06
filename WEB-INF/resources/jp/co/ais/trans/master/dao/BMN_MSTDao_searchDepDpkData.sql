 /*IF organization != "" */
SELECT 
	B.DEP_CODE,
	B.DEP_NAME_S,
	B.DEP_NAME_K
	
FROM 
	BMN_MST B, DPK_MST D
	
WHERE  
    B.KAI_CODE = D.KAI_CODE
 	AND  B.DEP_CODE = D.DPK_DEP_CODE
 	AND B.KAI_CODE = /*kaiCode*/
 	AND  D.DPK_SSK = /*organization*/
 	
 	/*IF level != 10 */ 
    AND  D.DPK_LVL = /*level*/
 	/*IF upperDepart != "" */
     /*IF level == 0 */ AND D.DPK_LVL_0 = /*upperDepart*/ /*END*/
     /*IF level == 1 */ AND D.DPK_LVL_1 = /*upperDepart*/ /*END*/
     /*IF level == 2 */ AND D.DPK_LVL_2 = /*upperDepart*/ /*END*/
     /*IF level == 3 */ AND D.DPK_LVL_3 = /*upperDepart*/ /*END*/
     /*IF level == 4 */ AND D.DPK_LVL_4 = /*upperDepart*/ /*END*/
     /*IF level == 5 */ AND D.DPK_LVL_5 = /*upperDepart*/ /*END*/
     /*IF level == 6 */ AND D.DPK_LVL_6 = /*upperDepart*/ /*END*/
     /*IF level == 7 */ AND D.DPK_LVL_7 = /*upperDepart*/ /*END*/
     /*IF level == 8 */ AND D.DPK_LVL_8 = /*upperDepart*/ /*END*/
     /*IF level == 9 */ AND D.DPK_LVL_9 = /*upperDepart*/ /*END*/
   	/*END*/
 	/*END*/

	/*IF (sumDepart == true && inputDepart == false) */
 	 AND  B.DEP_KBN = '1'
 	/*END*/
 	
 	/*IF (sumDepart == false && inputDepart == true) */
  	 AND  B.DEP_KBN = '0'
	/*END*/
	
	/*IF depCode != "" */ AND B.DEP_CODE  /*$depCode*/ /*END*/
	/*IF sName != "" */ AND B.DEP_NAME_S  /*$sName*/ /*END*/
	/*IF kName != "" */ AND B.DEP_NAME_K  /*$kName*/ /*END*/
	/*IF termBasisDate !=null */ 
	 AND B.STR_DATE <= /*termBasisDate*/
 	 AND B.END_DATE >= /*termBasisDate*/
 	/*END*/
 	
 	/*IF beginCode != "" */ AND B.DEP_CODE >= /*beginCode*/ /*END*/
 	/*IF endCode != "" */ AND B.DEP_CODE <= /*endCode*/ /*END*/
	
	ORDER BY 
	 B.DEP_CODE 
	
 -- ELSE

SELECT 
	DEP_CODE,
	DEP_NAME_S,
	DEP_NAME_K
	
FROM 
	BMN_MST
	  
WHERE  
   KAI_CODE = /*kaiCode*/
	/*IF (sumDepart == true && inputDepart == false) */
 	 AND  DEP_KBN = '1'
 	/*END*/
 	
 	/*IF (sumDepart == false && inputDepart == true) */
 	 AND  DEP_KBN = '0'
	/*END*/
	
	/*IF depCode != "" */ AND DEP_CODE  /*$depCode*/ /*END*/
	/*IF sName != "" */ AND DEP_NAME_S  /*$sName*/ /*END*/
	/*IF kName != "" */ AND DEP_NAME_K  /*$kName*/ /*END*/
	/*IF termBasisDate !=null */ 
	 AND STR_DATE <= /*termBasisDate*/
 	 AND END_DATE >= /*termBasisDate*/
 	/*END*/
 	
 	/*IF beginCode != "" */ AND DEP_CODE >= /*beginCode*/ /*END*/
 	/*IF endCode != "" */ AND DEP_CODE <= /*endCode*/ /*END*/
	
ORDER BY 
 DEP_CODE

/*END*/