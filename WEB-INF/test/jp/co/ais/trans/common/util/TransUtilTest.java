package jp.co.ais.trans.common.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import jp.co.ais.trans.common.util.TransUtil.ZeiKeiKbn;
import jp.co.ais.trans2.master.ui.MG0010CompanyMasterPanelCtrl;
import jp.co.ais.trans2.model.TModel;
import jp.co.ais.trans2.model.user.User;

class TransUtilTest {

	@Test
	void testGetUpdKbnChar() {
		// Array of test cases
		Object[][] testCases = {
				{ TransUtil.UPD_KBN_ENTRY, TransUtil.UPD_KBN_ENTRY_CHAR },
				{ TransUtil.UPD_KBN_GENBA_APPROVE, TransUtil.UPD_KBN_GENBA_APPROVE_CHAR },
				{ TransUtil.UPD_KBN_APPROVE, TransUtil.UPD_KBN_APPROVE_CHAR },
				{ TransUtil.UPD_KBN_UPDATE, TransUtil.UPD_KBN_UPDATE_CHAR },
				{ TransUtil.UPD_KBN_GENBA_DENY, TransUtil.UPD_KBN_GENBA_DENY_CHAR },
				{ TransUtil.UPD_KBN_DENY, TransUtil.UPD_KBN_DENY_CHAR },
				{ -1, null } // Test with an invalid `updKbn` value
		};

		for (Object[] testCase : testCases) {
			int updKbn = (int) testCase[0];
			String expectedChar = (String) testCase[1];
			assertEquals(expectedChar, TransUtil.getUpdKbnChar(updKbn),
					"Failed for updKbn value: " + updKbn);
		}
	}

	@Test
	void testGetZeiKeiKbn() {
		// Test cases for valid inputs
		assertEquals(ZeiKeiKbn.UriKazei, TransUtil.getZeiKeiKbn("1"), "Failed for zeiKeiKbn value: 1");
		assertEquals(ZeiKeiKbn.UriMenzei, TransUtil.getZeiKeiKbn("2"), "Failed for zeiKeiKbn value: 2");
		assertEquals(ZeiKeiKbn.UriHikazei, TransUtil.getZeiKeiKbn("3"), "Failed for zeiKeiKbn value: 3");
		assertEquals(ZeiKeiKbn.SirKazei, TransUtil.getZeiKeiKbn("4"), "Failed for zeiKeiKbn value: 4");
		assertEquals(ZeiKeiKbn.SirHikazei, TransUtil.getZeiKeiKbn("5"), "Failed for zeiKeiKbn value: 5");

		// Test case for invalid value
		assertNull(TransUtil.getZeiKeiKbn("invalid"), "Failed for invalid zeiKeiKbn value");

		// Test case for empty string
		assertNull(TransUtil.getZeiKeiKbn(""), "Failed for empty zeiKeiKbn value");
	}

	@Test
	void testGetTableNameTModelString() throws IOException {
		// Test case 1: Valid language and table
		TModel model1 = new TModel();
		User user1 = new User();
		user1.setLanguage("EN");
		model1.setUser(user1);
		String result1 = TransUtil.getTableName(model1, "kmk_mst");
		assertEquals("KMK_MST_EN", result1);

		// Test case 2: Language not in list
		TModel model2 = new TModel();
		User user2 = new User();
		user2.setLanguage("ES");
		model2.setUser(user2);
		String result2 = TransUtil.getTableName(model2, "my_table");
		assertEquals("my_table", result2);

		// Test case 3: Table not in list
		TModel model3 = new TModel();
		User user3 = new User();
		user3.setLanguage("EN");
		model3.setUser(user3);
		String result3 = TransUtil.getTableName(model3, "other_table");
		assertEquals("other_table", result3);

		// Test case 4: No language set
		TModel model4 = new TModel();
		String result4 = TransUtil.getTableName(model4, "my_table");
		assertEquals("my_table", result4);

		// Test case 5: Multi-lang flag turned off
		TModel model5 = new TModel();
		User user5 = new User();
		user5.setLanguage("EN");
		model5.setUser(user5);
		String result5 = TransUtil.getTableName(model5, "my_table");
		assertEquals("my_table", result5);
	}

	@Test
	void testGetTableNameTControllerString() {
		//T
		MG0010CompanyMasterPanelCtrl ctrl = new MG0010CompanyMasterPanelCtrl();
		User user1 = new User();
		user1.setLanguage("en");
		String result1 = TransUtil.getTableName(ctrl, "kmk_mst");
		assertEquals("kmk_mst", result1);

	}

}
