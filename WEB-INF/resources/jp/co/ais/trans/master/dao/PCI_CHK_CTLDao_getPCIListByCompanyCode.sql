SELECT
 pci.USR_ID,   -- ���[�U�R�[�h
 pci.PCI_CHECK_IN_TIME,  -- ���O�C������
 usr.USR_NAME  -- ���[�U����

FROM PCI_CHK_CTL pci LEFT OUTER JOIN USR_MST usr 
                     ON pci.KAI_CODE = usr.KAI_CODE
                     AND pci.USR_ID = usr.USR_CODE

WHERE 
 pci.KAI_CODE = /*companyCode*/