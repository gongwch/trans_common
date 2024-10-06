package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.util.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * ��ЊK�w�}�X�^ EDIT�_�C�A���O �R���g���[��
 * 
 * @author ookawara
 */
public class OW0140EditCompanySelectionDialogCtrl extends TAppletClientBase {

	/** DIVESTMEENT�_�C�A���O */
	private OW0140EditCompanySelectionDialog dialog;

	protected Vector<ArrayList<String>> cells = new Vector<ArrayList<String>>();

	protected Vector<Vector> cells1 = new Vector<Vector>();

	/**
	 * �R���X�g���N�^ �e�t���[��
	 * 
	 * @param parent
	 * @param cells
	 * @param titleid
	 */
	OW0140EditCompanySelectionDialogCtrl(Frame parent, Vector<ArrayList<String>> cells, String titleid) {
		dialog = new OW0140EditCompanySelectionDialog(parent, true, this, titleid);
		dialog.setSize(585, 180);
		this.cells = cells;

	}

	/**
	 * �\��
	 * 
	 * @param isEnabledCurCode
	 */
	void show(boolean isEnabledCurCode) {
		dialog.setVisible(true);
		dialog.ctrlLowerCompany.setEnabled(isEnabledCurCode);
		dialog.ctrlUpperCompany.setEnabled(!isEnabledCurCode);
		dialog.btnReturn.setEnabled(true);
		dialog.btnSettle.setEnabled(true);
	}

	/**
	 * �l�̃Z�b�g
	 * 
	 * @param map
	 */
	void setValues(Map map) {

		// �ҏW���[�h�̂Ƃ���DIVESTMEENT���ҏW�s�ɂȂ�
		if (map.get("flag") == "lowerCom") {
			dialog.ctrlLowerCompany.setEnabled(false);
			dialog.ctrlUpperCompany.setEnabled(true);

			dialog.ctrlLowerCompany.setValue((String) map.get("kaiCode"));
			dialog.ctrlLowerCompany.setNoticeValue((String) map.get("lvlName"));

		} else {
			dialog.ctrlLowerCompany.setEnabled(true);
			dialog.ctrlUpperCompany.setEnabled(false);
			dialog.ctrlLowerCompany.requestTextFocus();

			dialog.ctrlUpperCompany.setValue((String) map.get("kaiCode"));
			dialog.ctrlUpperCompany.setNoticeValue((String) map.get("lvlName"));

		}
		for (int i = 0; i < cells.size(); i++) {
			String kaicodeTemp = (cells.get(i)).get(2);
			if (kaicodeTemp.compareToIgnoreCase((String) map.get("kaiCode")) != 0) {
				Vector<String> colum = new Vector<String>();

				colum.add(0, cells.get(i).get(2));
				colum.add(1, cells.get(i).get(16));

				cells1.add(colum);
			}
		}
	}

	/**
	 * ����
	 */
	void disposeDialog() {
		// �G���[������ꍇ�ɂ̓_�C�A���O����Ȃ�
		if (dialog.isSettle) {
			if (checkDialog()) {
				dialog.setVisible(false);
			} else {
				dialog.isSettle = false;
			}
		} else {
			dialog.setVisible(false);
		}
	}

	/**
	 * �m��{�^���������ꂽ���ǂ���
	 * 
	 * @return �m��̏ꍇtrue
	 */
	boolean isSettle() {
		return dialog.isSettle;
	}

	boolean checkDialog() {
		// ��ʉ�ЃR�[�h�`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlUpperCompany.getValue())) {
			// �G���[���b�Z�[�W�o��
			super.showMessage(dialog, "I00002", "C00745");
			dialog.ctrlUpperCompany.requestTextFocus();
			return false;
		}
		// ���ʉ�ЃR�[�h�`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlLowerCompany.getValue())) {
			// �G���[���b�Z�[�W�o��
			super.showMessage(dialog, "I00002", "C00746");
			dialog.ctrlLowerCompany.requestTextFocus();
			return false;
		}
		return true;
	}

	/**
	 * ��ʏ�\���f�[�^�̎擾
	 * 
	 * @return �f�[�^
	 */
	Map getDataList() {
		Map<String, Object> map = new TreeMap<String, Object>();

		map.put("lowerkaiCode", dialog.ctrlLowerCompany.getValue());
		map.put("upperkaiCode", dialog.ctrlUpperCompany.getValue());
		map.put("lowerkaiName", dialog.ctrlLowerCompany.getNoticeValue());
		map.put("upperkaiName", dialog.ctrlUpperCompany.getNoticeValue());

		return map;
	}

	void doOwnerCompanyClick(TButtonField bfield) {
		try {
			OW0140REFDisplayDialogCtrl dialogRef = new OW0140REFDisplayDialogCtrl(this.dialog, cells1);

			// ���ސݒ�A��������
			if (!Util.isNullOrEmpty(bfield.getValue())) {
				dialogRef.setCode(bfield.getValue());
				dialogRef.condition(false);
			}

			dialogRef.show();

			if (dialogRef.isSettle()) {
				String[] info = dialogRef.getCurrencyInfo();
				bfield.setValue(info[0]);
				bfield.setNoticeValue(info[1]);
				this.dialog.btnSettle.transferFocus();

			}
		} catch (Exception e) {
			errorHandler(this.dialog, e, "E00009");
		}
	}

	boolean doOwnerCompanyLostFocus(TButtonField bfield) {

		try {
			if (!bfield.isValueChanged()) {
				return true;
			}

			// �e�L�X�g�t�B�[���h�ɕ����񂪓��͂���Ă����Ƃ��̂ݗL��
			String code = bfield.getValue();

			if (Util.isNullOrEmpty(code)) {
				bfield.setNoticeValue(""); // �󕶎��Z�b�g
				return true;
			}

			int codeIndex = getKatCodeIndex(cells1, code);

			if (codeIndex >= 0) {

				// ���̂��Z�b�g
				bfield.setNoticeValue(Util.avoidNull(cells1.get(codeIndex).get(1)));

			} else {
				// �x�����b�Z�[�W�\���i�Y���R�[�h�͑��݂��܂���j
				showMessage(dialog, "W00081", code);

				bfield.setNoticeValue("");
				bfield.clearOldText();
				bfield.requestTextFocus();

				return false;
			}

		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(dialog, e, "E00009");

			bfield.setNoticeValue("");
			bfield.clearOldText();
		}

		return true;
	}

	int getKatCodeIndex(Vector<Vector> cellsTemp, String kaiCode) {

		int kaicodenum = cellsTemp.size();
		for (int i = 0; i < kaicodenum; i++) {
			String kaicodeTemp = (String) (cellsTemp.get(i)).get(0);
			if (kaicodeTemp.compareToIgnoreCase(kaiCode) == 0) {
				return i;
			}
		}
		return -1;
	}

}
