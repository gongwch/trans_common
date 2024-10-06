SELECT
 pci.USR_ID,   -- ユーザコード
 pci.PCI_CHECK_IN_TIME,  -- ログイン日時
 usr.USR_NAME  -- ユーザ名称

FROM PCI_CHK_CTL pci LEFT OUTER JOIN USR_MST usr 
                     ON pci.KAI_CODE = usr.KAI_CODE
                     AND pci.USR_ID = usr.USR_CODE

WHERE 
 pci.KAI_CODE = /*companyCode*/