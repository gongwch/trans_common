package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;

/**
 * ����K�w�}�X�^��ʃR���g���[��
 */
public class MG0050DepartmentHierarchyMasterPanelCtrl extends PanelAndDialogCtrlBase {

	/** �v���O����ID */
	protected static final String PROGRAM_ID = "MG0050";

	protected MG0050DepartmentHierarchyMasterPanel panel;

	protected static final String TARGET_SERVLET = "MG0050DepartmentHierarchyMasterServlet";

	protected Map sskMap;

	protected Map depMap;

	protected Map depMap3Column;

	protected List records;

	private String level0DepCode;

	/** �X�v���b�h�V�[�gDataSource */
	JCVectorDataSource ds1 = new JCVectorDataSource();

	JCVectorDataSource ds2 = new JCVectorDataSource();

	/**
	 * �p�l���擾
	 * 
	 * @return ��ЃR���g���[���}�X�^�p�l��
	 */
	public TPanelBusiness getPanel() {
		return this.panel;
	}

	/**
	 * 
	 */
	public MG0050DepartmentHierarchyMasterPanelCtrl() {
		try {
			panel = new MG0050DepartmentHierarchyMasterPanel(this);

			// �S�Ă̑g�D��ǂݍ���
			loadSskMap();
			// �S�Ă̕����ǂݍ���
			loadDepMap();

			/* �{�^������ݒ� */
			setBtnLock(false);

			this.fillItemsToComboBox(panel.ctrlOrganizationCode.getComboBox(), sskMap);
		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00010");
		}
	}

	/**
	 * �{�^�����b�N�̐���
	 * 
	 * @param boo
	 */
	void setBtnLock(boolean boo) {
		panel.btnDelete.setEnabled(boo);
		panel.btnSettle.setEnabled(boo);
		panel.btnDepartmentAdd.setEnabled(boo);
		panel.btnDepartmentCancellation.setEnabled(boo);
		panel.btnLowerDepartment.setEnabled(boo);
		panel.btnUpperDepartment.setEnabled(boo);
	}

	void loadSsk() {
		// �I�����ꂽ�g�D�����[�h����
		String ssk = this.getComboBoxSelectedValue(panel.ctrlOrganizationCode.getComboBox());

		if (Util.isNullOrEmpty(ssk)) {
			panel.ctrlLevel0.getField().setText(null);
			panel.ctrlLevel0.getField().setEditable(false);
			panel.txtDepartmentName.setText(null);

			setBtnLock(false);
			// C.ERROR
			panel.btnListOutput.setEnabled(false);

			// panel.ssDepartmentList.setVisible(true);
			// panel.ssHierarchyList.setVisible(true);

			panel.ctrlLevel0.getField().setEditable(true);
			panel.ctrlLevel0.getField().setText("");
			panel.ctrlLevel0.setEnabled(true); // 2006/12/29 Yanwei
			panel.ctrlLevel0.getField().setEditable(false);

			panel.txtDepartmentName.setEnabled(true); // 2006/12/29 Yanwei
			panel.txtDepartmentName.setEditable(true);
			panel.txtDepartmentName.setText("");
			panel.txtDepartmentName.setEditable(false);
		} else {
			addSendValues("flag", "find");
			addSendValues("kaiCode", getLoginUserCompanyCode());
			addSendValues("dpkSsk", ssk);

			try {
				request(TARGET_SERVLET);
			} catch (IOException ex) {
				super.errorHandler(panel.getParentFrame(), ex, "E00009");
			}

			records = super.getResultList();

			refresh();

			panel.ssDepartmentList.setVisible(true);
			panel.ssHierarchyList.setVisible(true);
			panel.btnDelete.setEnabled(true);
			panel.btnSettle.setEnabled(true);

			// C.ERROR
			panel.btnListOutput.setEnabled(true);
		}
	}

	void createSsk() {
		MG0050NewOrganizationDialogCtrl dialog = new MG0050NewOrganizationDialogCtrl(panel.getParentFrame());
		dialog.show();

		if (dialog.isSettle()) {
			loadSskMap();
			this.fillItemsToComboBox(panel.ctrlOrganizationCode.getComboBox(), sskMap);
			loadSsk();
		}
	}

	void deleteSsk() {
		try {
			String ssk = this.getComboBoxSelectedValue(panel.ctrlOrganizationCode.getComboBox());

			if (super.showConfirmMessage(panel, FocusButton.NO, "Q00007", ssk)) {
				addSendValues("flag", "deleteorganization");
				addSendValues("kaiCode", super.getLoginUserCompanyCode());
				addSendValues("dpkSsk", ssk);

				if (!request(TARGET_SERVLET)) {
					errorHandler(panel);
					return;
				}

				loadSskMap();
				this.fillItemsToComboBox(panel.ctrlOrganizationCode.getComboBox(), sskMap);
				loadSsk();
				panel.txtDepartmentName.setEditable(false);
			}
		} catch (IOException e) {
			errorHandler(panel, e, "E00009");
		}
	}

	// �G�N�Z���o��
	void excelOutput() {
		try {
			if (!showConfirmMessage(panel.getParentFrame(), "Q00011")) {
				return;
			}

			String ssk = this.getComboBoxSelectedValue(panel.ctrlOrganizationCode.getComboBox());

			Map conds = new HashMap();
			conds.put("flag", "report");
			conds.put("kaiCode", getLoginUserCompanyCode());
			conds.put("dpkSsk", ssk);
			conds.put("langCode", getLoginLanguage());

			download(panel, TARGET_SERVLET, conds);

		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	void addDepartment() {
		int nowRow = panel.ssDepartmentList.getCurrentRow();

		if (nowRow != -1) {
			TableDataModel model = panel.ssDepartmentList.getDataSource();
			String depCode = (String) model.getTableDataItem(nowRow, 0);

			List inner = new ArrayList();
			// ��ЃR�[�h
			inner.add(super.getLoginUserCompanyCode());
			// �g�D�R�[�h
			inner.add(this.getComboBoxSelectedValue(panel.ctrlOrganizationCode.getComboBox()));
			// ����R�[�h
			inner.add(depCode);
			// ���x��
			inner.add("1");
			// ���x���O
			inner.add(level0DepCode);
			// ���x���P
			inner.add(depCode);
			// ���x���Q�`���x���X
			for (int i = 2; i <= 9; i++) {
				inner.add("");
			}
			// �J�n�N����
			inner.add("");
			// �I���N����
			inner.add("");
			// �o�^���t
			inner.add("");
			// �X�V���t
			inner.add("");
			// �v���O�����h�c
			inner.add("");
			// ���[�U�[�h�c
			inner.add("");

			records.add(inner);

			refresh();
			panel.btnDepartmentCancellation.setEnabled(true);
			panel.btnLowerDepartment.setEnabled(true);
			panel.btnUpperDepartment.setEnabled(true);
		}
	}

	void removeDepartment() {
		int nowRow = panel.ssHierarchyList.getCurrentRow();

		if (nowRow != -1) {
			List inner = (List) records.get(nowRow + 1);
			String depCode = (String) inner.get(2);
			int level = Integer.parseInt((String) inner.get(3));

			Iterator ite = records.iterator();

			boolean start = false;

			while (ite.hasNext()) {
				List inner1 = (List) ite.next();

				String depCode1 = (String) inner1.get(2);

				if (depCode.equals(depCode1)) {
					start = true;
				}

				if (!start) {
					continue;
				}

				String parentAtLevelChild = (String) inner1.get(level + 4);

				if (depCode.equals(parentAtLevelChild)) {
					ite.remove();
				} else {
					// ���̕���͌��ݑI�����ꂽ����̉��ʂł͂Ȃ�
					break;
				}
			}

			refresh();
		}
	}

	void saveSsk() {
		try {
			if (this.showConfirmMessage(panel, "Q00005", "")) {
				addSendValues("flag", "savessk");

				for (int i = 0; i < records.size(); i++) {
					List inner = (List) records.get(i);
					String[] array = new String[20];

					for (int j = 0; j < 14; j++) {
						array[j] = (String) inner.get(j);
					}
					// �v���O�����h�c
					array[18] = PROGRAM_ID;
					// ���[�U�[�h�c
					array[19] = super.getLoginUserID();

					addSendValues("data", StringUtil.toHTMLEscapeString(array));
				}

				if (!request(TARGET_SERVLET)) {
					errorHandler(panel);
					return;
				}

				super.showMessage(panel.getParentFrame(), "I00013");
				loadSsk();
			}
		} catch (IOException e) {
			errorHandler(panel, e, "E00009");
		}
	}

	/**
	 * @param frame
	 * @param messageID
	 * @param params
	 */
	public void showMessagePub(Frame frame, String messageID, String... params) {
		super.showMessage(frame, messageID, (Object[]) params);
	}

	void chooseChild() {
		int nowRow = panel.ssHierarchyList.getCurrentRow();

		if (nowRow != -1) {
			List inner = (List) records.get(nowRow + 1);
			String depCode = (String) inner.get(2);
			String depName = (String) depMap.get(depCode);
			int level = Integer.parseInt((String) inner.get(3));

			// 2007/02/11: �I�����ꂽ����̒��ړI�����͊ԐړI��ʕ���
			// ����т��̕���̎��Ȃ�����
			List excludes = new ArrayList();

			for (int i = 0; i <= level; i++) {
				String parentDepCode = (String) inner.get(4 + i);
				excludes.add(parentDepCode);
			}

			Map deps = new HashMap();

			Iterator ite = depMap3Column.entrySet().iterator();

			while (ite.hasNext()) {
				Map.Entry entry = (Map.Entry) ite.next();
				String depCode1 = (String) entry.getKey();

				Iterator ite2 = records.iterator();
				boolean exists = false;

				while (ite2.hasNext()) {
					List inner2 = (List) ite2.next();
					String depCode2 = (String) inner2.get(2);

					if (depCode1.equals(depCode2)) {
						exists = true;
						break;
					}
				}

				// ���x���O����͌��ݑI�����ꂽ����̉��ʂɂȂ��Ăł��Ȃ�
				// if (exists && !depCode.endsWith(depCode1)
				// && !level0DepCode.equals(depCode1)) {

				// 2007/02/11: �I�����ꂽ����̒��ړI�����͊ԐړI��ʕ��������
				if (exists && !excludes.contains(depCode1)) {
					TCodeNameSNameK cnn = (TCodeNameSNameK) entry.getValue();
					deps.put(depCode1, cnn);
				}
			}

			MG0050DepartmentSelectionDialog dialog = new MG0050DepartmentSelectionDialog(panel.getParentFrame(), true,
				this, true, deps, depCode, depName, "C02405");
			// WordID��n���i���ʕ���j
			// dialog.setWordID("C02405");
			dialog.setVisible(true);

			if (dialog.isSettle()) {
				move(depCode, dialog.getSelectedDepartmentCode());
			}
		}
	}

	void chooseParent() {
		int nowRow = panel.ssHierarchyList.getCurrentRow();

		if (nowRow != -1) {
			List inner = (List) records.get(nowRow + 1);
			String depCode = (String) inner.get(2);
			String depName = (String) depMap.get(depCode);
			int level = Integer.parseInt((String) inner.get(3));

			// 2007/02/11: �I�����ꂽ����̒��ړI�����͊ԐړI���ʕ���
			// ����т��̕���̎��Ȃ�����
			List excludes = new ArrayList();
			excludes.add(depCode);

			for (int i = nowRow + 2; i < records.size(); i++) {
				List inner2 = (List) records.get(i);
				String depCode2 = (String) inner2.get(2);
				String parentAtLevel = (String) inner2.get(level + 4);

				if (depCode.equals(parentAtLevel)) {
					excludes.add(depCode2);
				} else {
					break;
				}
			}

			Map deps = new HashMap();

			Iterator ite = depMap3Column.entrySet().iterator();

			while (ite.hasNext()) {
				Map.Entry entry = (Map.Entry) ite.next();
				String depCode1 = (String) entry.getKey();

				Iterator ite2 = records.iterator();
				boolean exists = false;

				while (ite2.hasNext()) {
					List inner2 = (List) ite2.next();
					String depCode2 = (String) inner2.get(2);

					if (depCode1.equals(depCode2)) {
						exists = true;
						break;
					}
				}

				// 2007/02/11: �I�����ꂽ����̒��ړI�����͊ԐړI���ʕ���
				// ����т��̕���̎��Ȃ�����
				if (exists && !excludes.contains(depCode1)) {
					TCodeNameSNameK cnn = (TCodeNameSNameK) entry.getValue();
					deps.put(depCode1, cnn);
				}
			}

			MG0050DepartmentSelectionDialog dialog = new MG0050DepartmentSelectionDialog(panel.getParentFrame(), true,
				this, false, deps, depCode, depName, "C02404");
			dialog.setVisible(true);

			if (dialog.isSettle()) {
				move(dialog.getSelectedDepartmentCode(), depCode);
			}
		}
	}

	protected void move(String parentDepCode, String childDepCode) {
		Iterator ite = records.iterator();
		List movingDeps = new ArrayList(); // �ړ����������僌�R�[�h
		int childLevel = -1;
		int parentIndex = -1;
		int childIndex = -1;
		int parentLevel = -1;
		List parentDep = null;
		int i = 0;
		// List downDep=null;
		// List oldRecords = records;

		while (ite.hasNext()) {
			List inner = (List) ite.next();
			String depCode = (String) inner.get(2);

			if (depCode.equals(parentDepCode)) {
				parentIndex = i;
				parentLevel = Integer.parseInt((String) inner.get(3));
				parentDep = inner;
			}
			if (depCode.equals(childDepCode)) {
				childIndex = i;
				childLevel = Integer.parseInt((String) inner.get(3));
				// downDep=inner;
				movingDeps.add(inner);
				ite.remove();
			} else if (childLevel != -1) {
				String parentAtChildLevel = (String) inner.get(childLevel + 4);

				if (childDepCode.equals(parentAtChildLevel)) {
					movingDeps.add(inner);
					ite.remove();
				}
			}
			i++;
		}

		if (parentIndex > childIndex) {
			parentIndex -= movingDeps.size();
		}

		for (int j = 0; j < movingDeps.size(); j++) {
			List inner = (List) movingDeps.get(j);
			int level = Integer.parseInt((String) inner.get(3));

			String[] depCodes = new String[10];

			for (int k = 0; k <= parentLevel; k++) {
				depCodes[k] = (String) parentDep.get(4 + k);
			}

			if (parentLevel < childLevel) {
				for (int index = childLevel, cnt = 1; index <= 9; index++, cnt++) {
					depCodes[parentLevel + cnt] = (String) inner.get(4 + index);
				}
			} else {
				for (int index = childLevel, cnt = 1; (parentLevel + cnt) <= 9; index++, cnt++) {
					depCodes[parentLevel + cnt] = (String) inner.get(4 + index);
				}
			}

			for (int k = 0; k <= 9; k++) {
				inner.set(4 + k, depCodes[k] == null ? "" : depCodes[k]);
			}

			// ���x��
			inner.set(3, "" + (level - childLevel + parentLevel + 1));

			records.add(parentIndex + j + 1, inner);
		}

		refresh();
	}

	boolean checkLevelOverflow(String parentDepCode, String childDepCode) {
		int parentLevel = -1; // ��ʕ���̃��x��
		int childLevel = -1; // ���ʕ���̃��x��
		int childChildMaxLevel = 0; // ���ʕ���̉��ʕ��咆�A�ő�̃��x��

		Iterator ite = records.iterator();
		while (ite.hasNext()) {
			List inner = (List) ite.next();

			String depCode = (String) inner.get(2);
			if (depCode.equals(parentDepCode)) {
				parentLevel = Integer.parseInt((String) inner.get(3));
			}
			if (depCode.equals(childDepCode)) {
				childLevel = Integer.parseInt((String) inner.get(3));
			}
			if (childLevel != -1) {
				String parentAtChildLevel = (String) inner.get(childLevel + 4);

				if (childDepCode.equals(parentAtChildLevel)) {
					int levelChildChild = Integer.parseInt((String) inner.get(3));
					if (levelChildChild > childChildMaxLevel) {
						childChildMaxLevel = levelChildChild;
					}
				}
			}
		}

		// �ω���̍ő�̃��x��
		int levelToBe = childChildMaxLevel - childLevel + parentLevel + 1;

		return (levelToBe > 9);
	}

	// 12/25 fix: ����o �͕���b �̏�ʕ���̏ꍇ�́A����o �͕���b �̉��ʕ���ɂȂ��ďo���܂���
	boolean isParentChild(String parentDepCode, String childDepCode) {
		boolean isChild = false;
		String upCode = null;
		String downCode = null;
		int upLevel = 0;
		int downLevel = 0;

		Iterator ite = records.iterator();
		while (ite.hasNext()) {
			List inner = (List) ite.next();

			String depCode = (String) inner.get(2);

			if (depCode.equals(childDepCode)) {
				downCode = (String) inner.get(5);
				downLevel = Integer.parseInt((String) inner.get(3));
			}
			if (depCode.equals(parentDepCode)) {
				upCode = (String) inner.get(5);
				upLevel = Integer.parseInt((String) inner.get(3));
			}
		}

		if (downLevel < upLevel && upCode.equals(downCode)) {
			isChild = true;
		}
		return isChild;
	}

	protected void loadSskMap() {
		try {
			addSendValues("flag", "getorganizations");
			addSendValues("kaiCode", getLoginUserCompanyCode());

			if (!request(TARGET_SERVLET)) {
				errorHandler(panel);
				return;
			}

			List result = super.getResultList();
			sskMap = new LinkedHashMap();
			Iterator ite = result.iterator();
			while (ite.hasNext()) {
				List inner = (List) ite.next();

				// �g�D�R�[�h
				String text = (String) inner.get(1);
				sskMap.put(text, text);
			}
		} catch (IOException e) {
			errorHandler(panel, e, "E00009");
		}
	}

	protected void loadDepMap() {
		addSendValues("flag", "find");
		addSendValues("kaiCode", getLoginUserCompanyCode());
		addSendValues("beginDepCode", "");
		addSendValues("endDepCode", "");

		try {
			if (!request("MG0060DepartmentMasterServlet")) {
				errorHandler(panel);
				return;
			}

			List result = super.getResultList();
			depMap = new LinkedHashMap();
			depMap3Column = new HashMap();

			Iterator ite = result.iterator();
			while (ite.hasNext()) {
				List inner = (List) ite.next();

				// ����R�[�h
				String code = (String) inner.get(1);
				// ���喼��
				String text = (String) inner.get(2);
				depMap.put(code, text);

				TCodeNameSNameK cnn = new TCodeNameSNameK();
				cnn.setCode(code);
				cnn.setName_S(text);
				cnn.setName_K((String) inner.get(4));
				depMap3Column.put(code, cnn);
			}
		} catch (IOException e) {
			errorHandler(panel, e, "E00009");
		}
	}

	/**
	 * ��ʂ̃f�[�^���Ď擾����
	 */
	protected void refresh() {
		resortRecords();

		List depCodesAdded = new ArrayList();
		Vector vectorForHierarchyList = new Vector();

		Iterator ite1 = records.iterator();

		while (ite1.hasNext()) {
			List inner = (List) ite1.next();
			Vector line = new Vector();

			// ��ЃR�[�h
			line.add(inner.get(0));
			// �g�D�R�[�h
			line.add(inner.get(1));
			// ����R�[�h
			String depCode = (String) inner.get(2);
			line.add(depCode);
			// ���x��
			int level = Integer.parseInt((String) inner.get(3));
			line.add(inner.get(3));
			depCodesAdded.add(depCode);

			if (level == 0) {
				level0DepCode = depCode;
				// ite1.remove();
				panel.ctrlLevel0.getField().setEditable(true);
				panel.ctrlLevel0.getField().setText(depCode);
				panel.ctrlLevel0.setEnabled(true); // 2006/12/29 Yanwei
				panel.ctrlLevel0.getField().setEditable(false);

				panel.txtDepartmentName.setEnabled(true); // 2006/12/29 Yanwei
				panel.txtDepartmentName.setEditable(true);
				panel.txtDepartmentName.setText((String) depMap.get(depCode));
				panel.txtDepartmentName.setEditable(false);
				continue;
			}

			// ����R�[�h���x���O
			line.add("");

			for (int i = 1; i <= 9; i++) {
				if (i == level) {
					// ����R�[�h
					String depName = (String) depMap.get(depCode);
					line.add(depName);
				} else {
					line.add("");
				}
			}
			vectorForHierarchyList.add(line);
		}
		depCodesAdded.add(level0DepCode);

		// vectorForHierarchyList�̃f�[�^��ݒ肷��
		panel.setHierarchyList(vectorForHierarchyList);

		Vector vectorForDepartmentList = new Vector();
		Iterator ite2 = depMap.entrySet().iterator();

		while (ite2.hasNext()) {
			Map.Entry entry = (Map.Entry) ite2.next();

			String depCode = (String) entry.getKey();

			if (!depCodesAdded.contains(depCode)) {
				Vector line = new Vector();
				// ����R�[�h
				line.add(depCode);
				// ���嗪��
				line.add(entry.getValue());

				vectorForDepartmentList.add(line);
			}
		}
		// vectorForDepartmentList�̃f�[�^��ݒ肷��
		panel.setDepartmentList(vectorForDepartmentList);

	}

	void resortRecords() {
		for (int i = 0; i < records.size(); i++) {
			for (int j = i; j < records.size(); j++) {
				List inner1 = (List) records.get(i);
				List inner2 = (List) records.get(j);

				int compare = 0;

				for (int p = 0; p <= 9; p++) {
					String d1 = (String) inner1.get(4 + p);
					String d2 = (String) inner2.get(4 + p);

					if (Util.isNullOrEmpty(d1)) {
						compare = -1;
					} else if (Util.isNullOrEmpty(d2)) {
						compare = 1;
					} else {
						compare = d1.compareTo(d2);
					}

					if (compare != 0) {
						break;
					}
				}

				if (compare > 0) {
					records.set(i, inner2);
					records.set(j, inner1);
				}
			}
		}
	}

	/**
	 * 
	 */
	public class TCodeNameSNameK {

		private String code;

		private String name_S;

		private String name_K;

		/**
		 * @param code
		 */
		public void setCode(String code) {
			this.code = code;
		}

		/**
		 * @return String
		 */
		public String getCode() {
			return code;
		}

		/**
		 * @param name_S
		 */
		public void setName_S(String name_S) {
			this.name_S = name_S;
		}

		/**
		 * @return String
		 */
		public String getName_S() {
			return name_S;
		}

		/**
		 * @param name_K
		 */
		public void setName_K(String name_K) {
			this.name_K = name_K;
		}

		/**
		 * @return String
		 */
		public String getName_K() {
			return name_K;
		}
	}
}
