package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.util.*;

import com.klg.jclass.table.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.util.*;

/**
 * ��ЊK�w�}�X�^ Ref�_�C�A���O �R���g���[��
 * 
 * @author liuguozheng
 */
public class OW0140REFDisplayDialogCtrl extends TAppletClientBase {

	/** �_�C�A���O */
	private OW0140REFDisplayDialog dialog;

	/** �����T�[�u���b�g */

	protected Vector<Vector> cells = new Vector<Vector>();

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent �e�t���[��
	 * @param cells
	 */
	OW0140REFDisplayDialogCtrl(Dialog parent, Vector<Vector> cells) {
		dialog = new OW0140REFDisplayDialog(parent, true, this);
		dialog.setSize(650, 500);
		dialog.txtCode.setMaxLength(10);
		dialog.txtNameForSearch.setMaxLength(20);

		this.cells = cells;

		lockBtn(cells.size() != 0);
	}

	/**
	 * �\��
	 */
	void show() {
		dialog.setVisible(true);

	}

	/**
	 * �m��{�^���������ꂽ���ǂ���
	 * 
	 * @return �m��̏ꍇtrue
	 */
	boolean isSettle() {
		return dialog.isSettle;
	}

	/**
	 * ���ݑI������Ă���Z���̉�ЃR�[�h���擾
	 * 
	 * @return String[]
	 */
	String[] getCurrencyInfo() {

		// �I������Ă���s��1�ڂ̃J�������擾
		int numRow = dialog.ssJournal.getCurrentRow();
		TableDataModel model = dialog.ssJournal.getDataView();

		String curCode = (String) model.getTableDataItem(numRow, 0); // ��ЃR�[�h
		String curName = (String) model.getTableDataItem(numRow, 1); // ��Ж���

		return new String[] { curCode, curName };
	}

	/**
	 * �m��{�^������
	 * 
	 * @param bol
	 */
	void lockBtn(boolean bol) {
		dialog.btnSettle.setEnabled(bol);

	}

	/**
	 * ����
	 */
	void disposeDialog() {
		dialog.setVisible(false);
	}

	/**
	 * ��������
	 */
	void condition() {

		condition(true);
	}

	/**
	 * ��������
	 * 
	 * @param msgFlg ���b�Z�[�W�\���t���O true�F�\�� false:�\�����Ȃ�
	 */
	void condition(boolean msgFlg) {

		this.reflesh(msgFlg);
	}

	/**
	 * ��ʃ��t���b�V��
	 * 
	 * @param msgFlg ���b�Z�[�W�\���t���O true�F�\�� false:�\�����Ȃ�
	 */
	@SuppressWarnings("deprecation")
	private void reflesh(boolean msgFlg) {

		String code = Util.avoidNull(dialog.txtCode.getValue());
		String name = Util.avoidNull(dialog.txtNameForSearch.getValue());

		Vector<Vector> sarchCells = new Vector<Vector>();

		boolean checkCode = !Util.isNullOrEmpty(code);
		boolean checkName = !Util.isNullOrEmpty(name);

		for (int i = 0; i < cells.size(); i++) {
			boolean existCode = true;
			boolean existName = true;

			if (checkCode) {
				existCode = ((String) cells.get(i).get(0)).indexOf(code) != -1;
			}
			if (checkName) {
				existName = ((String) cells.get(i).get(1)).indexOf(name) != -1;
			}

			if (existCode && existName) {
				Vector<String> colum = new Vector<String>();
				colum.add(0, (String) cells.get(i).get(0));
				colum.add(1, (String) cells.get(i).get(1));
				sarchCells.add(colum);
			}
		}

		this.setDataList(sarchCells);

		if (sarchCells.size() == 0) {
			if (msgFlg) {
				super.showMessage(dialog, "W00100");
			}
			dialog.txtCode.requestFocus();
			lockBtn(false);
		} else {
			dialog.ssJournal.requestFocus();
			lockBtn(true);
		}
	}

	/**
	 * �X�v���b�h�f�[�^�̐ݒ�
	 * 
	 * @param cells �X�v���b�h�f�[�^
	 */
	void setDataList(Vector cells) {

		dialog.ds.setCells(cells);

		// Set the number of rows in the data source.
		dialog.ds.setNumRows(cells.size());

		dialog.ssJournal.setDataSource(dialog.ds);

		if (cells.size() > 0) {
			// �w��row�Ƀt�H�[�J�X�𓖂Ă�
			dialog.ssJournal.clearSelectedCells();
			dialog.ssJournal.setRowSelection(0, 0);
			dialog.ssJournal.setCurrentCell(0, 0);
		}
	}

	/**
	 * REF��ʂ̺��ނ�ݒ肷��
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		dialog.txtCode.setValue(code);
	}

}
