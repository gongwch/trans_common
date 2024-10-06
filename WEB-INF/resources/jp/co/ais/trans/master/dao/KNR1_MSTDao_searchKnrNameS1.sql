 SELECT T.KNR_CODE_1,
       T.KNR_NAME_S_1,
       T.KNR_NAME_K_1,
       T.STR_DATE,
       T.END_DATE
  FROM KNR1_MST T
 WHERE T.KAI_CODE = /*kaiCode*/
/*IF knrCode != null && knrCode != "" */AND KNR_CODE_1 = /*knrCode*/ /*END*/
/*IF beginCode != null && beginCode != "" */ AND KNR_CODE_1 >= /*beginCode*/ /*END*/
/*IF endCode != null && endCode != "" */ AND KNR_CODE_1 <= /*endCode*/ /*END*/
 ORDER BY T.KNR_CODE_1