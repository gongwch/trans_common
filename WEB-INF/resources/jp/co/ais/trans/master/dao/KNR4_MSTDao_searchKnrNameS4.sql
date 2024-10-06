SELECT T.KNR_CODE_4,
       T.KNR_NAME_S_4,
       T.KNR_NAME_K_4,
       T.STR_DATE,
       T.END_DATE
  FROM KNR4_MST T
 WHERE T.KAI_CODE = /*kaiCode*/
/*IF knrCode != null && knrCode != "" */AND KNR_CODE_4 = /*knrCode*/ /*END*/
/*IF beginCode != null && beginCode != "" */ AND KNR_CODE_4 >= /*beginCode*/ /*END*/
/*IF endCode != null && endCode != "" */ AND KNR_CODE_4 <= /*endCode*/ /*END*/
 ORDER BY T.KNR_CODE_4