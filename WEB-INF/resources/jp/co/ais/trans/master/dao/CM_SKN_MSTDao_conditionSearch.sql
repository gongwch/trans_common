SELECT SKN_CODE,SKN_NAME_S,SKN_NAME_K 
FROM CM_SKN_MST 
WHERE SKN_KAI_CODE = /*sknKaiCode*/
/*BEGIN*/AND
/*IF sknCode != "" */ SKN_CODE  /*$sknCode*/ /*END*/ -- LIKE
/*IF sknName_S != "" */ AND SKN_NAME_S  /*$sknName_S*/ /*END*/ -- LIKE
/*IF sknName_K != "" */ AND SKN_NAME_K  /*$sknname_K*/ /*END*/ -- LIKE
/*IF kind == "InputFund" */ AND SKN_SUM_KBN = 0 /*END*/
/*END*/
ORDER BY SKN_CODE