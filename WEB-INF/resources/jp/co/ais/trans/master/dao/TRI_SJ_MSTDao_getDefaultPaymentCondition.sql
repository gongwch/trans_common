SELECT
 trisj.*
FROM
 TRI_SJ_MST trisj
 INNER JOIN
 (
  SELECT
   tri.KAI_CODE,
   tri.TRI_CODE,
   MIN(tri.TJK_CODE) TJK_CODE
  FROM
   TRI_SJ_MST tri
   LEFT OUTER JOIN AP_CBK_MST cbk
   ON tri.KAI_CODE = cbk.KAI_CODE
   AND tri.FURI_CBK_CODE = cbk.CBK_CBK_CODE
   AND cbk.CUR_CODE = /*curCode*/
  WHERE  tri.KAI_CODE = /*kaiCode*/
  AND  tri.TRI_CODE = /*triCode*/
  GROUP BY
   tri.KAI_CODE,
   tri.TRI_CODE,
   tri.TJK_CODE
 ) tmp
ON trisj.KAI_CODE = tmp.KAI_CODE
AND trisj.TRI_CODE = tmp.TRI_CODE
AND trisj.TJK_CODE = tmp.TJK_CODE