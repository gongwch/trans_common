 SELECT T.KNR_CODE_5,
       T.KNR_NAME_S_5,
       T.KNR_NAME_K_5,
       T.STR_DATE,
       T.END_DATE
  FROM KNR5_MST T
 WHERE T.KAI_CODE = /*kaiCode*/
/*IF knrCode != null && knrCode != "" */AND KNR_CODE_5 = /*knrCode*/ /*END*/
/*IF beginCode != null && beginCode != "" */ AND KNR_CODE_5 >= /*beginCode*/ /*END*/
/*IF endCode != null && endCode != "" */ AND KNR_CODE_5 <= /*endCode*/ /*END*/
 ORDER BY T.KNR_CODE_5