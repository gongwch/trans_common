 SELECT T.KNR_CODE_2,
       T.KNR_NAME_S_2,
       T.KNR_NAME_K_2,
       T.STR_DATE,
       T.END_DATE
  FROM KNR2_MST T
 WHERE T.KAI_CODE = /*kaiCode*/
/*IF knrCode != null && knrCode != "" */AND KNR_CODE_2 = /*knrCode*/ /*END*/
/*IF beginCode != null && beginCode != "" */ AND KNR_CODE_2 >= /*beginCode*/ /*END*/
/*IF endCode != null && endCode != "" */ AND KNR_CODE_2 <= /*endCode*/ /*END*/
 ORDER BY T.KNR_CODE_2