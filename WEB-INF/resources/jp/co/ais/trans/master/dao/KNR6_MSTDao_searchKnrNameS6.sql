SELECT T.KNR_CODE_6,
       T.KNR_NAME_S_6,
       T.KNR_NAME_K_6,
       T.STR_DATE,
       T.END_DATE
  FROM KNR6_MST T
 WHERE T.KAI_CODE = /*kaiCode*/
/*IF knrCode != null && knrCode != "" */AND KNR_CODE_6 = /*knrCode*/ /*END*/
/*IF beginCode != null && beginCode != "" */ AND KNR_CODE_6 >= /*beginCode*/ /*END*/
/*IF endCode != null && endCode != "" */ AND KNR_CODE_6 <= /*endCode*/ /*END*/
 ORDER BY T.KNR_CODE_6