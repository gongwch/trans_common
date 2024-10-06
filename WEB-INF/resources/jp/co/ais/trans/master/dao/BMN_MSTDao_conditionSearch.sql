SELECT 
	DEP_CODE,
	DEP_NAME_S,
	DEP_NAME_K
FROM 
	BMN_MST 
WHERE 
	KAI_CODE = /*kaiCode*/
	/*IF depCode != "" */ AND DEP_CODE  /*$depCode*/ /*END*/ -- LIKE
	/*IF depName_S != "" */ AND DEP_NAME_S  /*$depName_S*/ /*END*/ -- LIKE
	/*IF depName_K != "" */ AND DEP_NAME_K  /*$depname_K*/ /*END*/ -- LIKE
	/*IF beginCode != null &&  beginCode != "" */ AND DEP_CODE >= /*beginCode*/ /*END*/
	/*IF endCode != null  &&  endCode != "" */ AND DEP_CODE <= /*endCode*/ /*END*/
	/*IF kind == "InputDepartment" */ AND DEP_KBN = 0 /*END*/
	/*IF kind == "Department" */ AND DEP_KBN = 0 /*END*/
	/*IF kind == "DepartmentAll" */ AND DEP_KBN < 2 /*END*/
ORDER BY DEP_CODE