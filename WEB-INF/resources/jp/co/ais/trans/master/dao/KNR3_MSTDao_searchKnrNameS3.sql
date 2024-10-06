SELECT T.KNR_CODE_3,
       T.KNR_NAME_S_3,
       T.KNR_NAME_K_3,
       T.STR_DATE,
       T.END_DATE
  FROM KNR3_MST T
 WHERE T.KAI_CODE = /*kaiCode*/
/*IF knrCode != null && knrCode != "" */AND KNR_CODE_3 = /*knrCode*/ /*END*/
/*IF beginCode != null && beginCode != "" */ AND KNR_CODE_3 >= /*beginCode*/ /*END*/
/*IF endCode != null && endCode != "" */ AND KNR_CODE_3 <= /*endCode*/ /*END*/
 ORDER BY T.KNR_CODE_3