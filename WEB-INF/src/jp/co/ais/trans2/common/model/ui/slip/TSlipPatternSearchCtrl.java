package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * �p�^�[�������R���g���[��
 */
public class TSlipPatternSearchCtrl extends TController {

	/** ��� */
	public static int CANCEL_OPTION = 0;

	/** �m�� */
	public static int OK_OPTION = 1;

	/** �X�e�[�g */
	protected int option = CANCEL_OPTION;

	/** �p�^�[���ꗗ�_�C�A���O */
	protected TSlipPatternSearchDialog dialog;

	/** �`�[��� */
	protected String[] slipTypes = new String[0];

	/** �f�[�^�敪 */
	protected String[] dataKinds = new String[0];

	/** true:���A���[�U�̂݃t���O���� */
	protected static boolean notUseSelfOnly = ClientConfig.isFlagOn("trans.slip.pattern.notuse.selfonly");

	/** true:���A�Q�ƌ������� */
	protected static boolean notUsePermission = ClientConfig.isFlagOn("trans.slip.pattern.notuse.permission");

	/** �����[�U�݂̂��ǂ��� */
	protected boolean selfOnly = false;

	/** �r�����f�[�^���܂ނ��ǂ��� */
	protected boolean includeLocked = false;

	/** �����e�L�X�g���X�i */
	protected KeyListener txtListener = new KeyAdapter() {

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				e.consume();

				dialog.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				searchList();
				dialog.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}
	};

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param mainView
	 */
	public TSlipPatternSearchCtrl(TPanel mainView) {
		dialog = createDialog(mainView);

		// �C�x���g�o�^
		addEvent();
	}

	/**
	 * �ꗗ�_�C�A���O����
	 * 
	 * @param mainView ���R���|�[�l���g
	 * @return �ꗗ�_�C�A���O
	 */
	protected TSlipPatternSearchDialog createDialog(TPanel mainView) {
		dialog = new TSlipPatternSearchDialog(mainView);
		return dialog;
	}

	/**
	 * ���g�̃f�[�^�݂̂�����
	 */
	public void switchSelfOnly() {
		this.selfOnly = true;
	}

	/**
	 * �r���f�[�^���܂ނ��ǂ���
	 * 
	 * @param includeLocked true:�܂�
	 */
	public void setIncludeLocked(boolean includeLocked) {
		this.includeLocked = includeLocked;
	}

	/**
	 * �C�x���g�o�^
	 */
	protected void addEvent() {

		// �e����(�e�L�X�g)
		dialog.txtSlipNo.addKeyListener(txtListener);
		dialog.txtEntryDate.addKeyListener(txtListener);
		dialog.txtDepCode.addKeyListener(txtListener);
		dialog.txtDepNames.addKeyListener(txtListener);
		dialog.txtSlipRemarks.addKeyListener(txtListener);

		// �����{�^��
		dialog.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dialog.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				searchList();
				dialog.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// �m��{�^��
		dialog.btnEnter.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				option = OK_OPTION;

				dialog.setVisible(false);
			}
		});

		// ����{�^��
		dialog.btnCancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
			}
		});
	}

	/**
	 * �`�[��ʂ̐ݒ�
	 * 
	 * @param slipType �`�[���
	 */
	public void setSlipType(String... slipType) {
		this.slipTypes = slipType;
	}

	/**
	 * �f�[�^�敪�̐ݒ�
	 * 
	 * @param dataType �f�[�^�敪
	 */
	public void setDataKind(String... dataType) {
		this.dataKinds = dataType;
	}

	/**
	 * �\��
	 * 
	 * @return �I������(XX_OPTION)
	 */
	public int show() {
		dialog.txtSlipNo.requestFocus();
		dialog.setVisible(true);

		return option;
	}

	/**
	 * �p�^�[������
	 */
	protected void searchList() {
		try {

			// ���̓`�F�b�N
			if (!checkInput()) {
				return;
			}

			dialog.tbl.removeRow();

			// �`�[����
			List<SWK_HDR> list = (List<SWK_HDR>) request(SlipManager.class, "getPatternHeader", getCondition());

			// ���ʂ��ꗗ�ɕ\��
			for (SWK_HDR hdr : list) {
				dialog.tbl.addRow(getRow(hdr));
			}

			dialog.btnEnter.setEnabled(!list.isEmpty());

			if (list.isEmpty()) {
				showMessage(dialog, "I00022");
				dialog.txtSlipNo.requestFocus();

			} else {
				dialog.tbl.setRowSelection(0);
				dialog.tbl.requestFocus();
			}

		} catch (Exception ex) {
			errorHandler(ex);
		}
	}

	/**
	 * ���̓`�F�b�N
	 * 
	 * @return false:NG
	 */
	protected boolean checkInput() {
		if (!dialog.txtEntryDate.isEmpty()) {
			String txtDate = dialog.txtEntryDate.getText().toString();
			int length = txtDate.length();

			switch (length) {
				case 6:
					String txtYMDate = txtDate.substring(0, 4) + "/" + txtDate.substring(4, 6);
					if (DateUtil.isYMDate(txtYMDate)) {
						dialog.txtEntryDate.setText(txtYMDate);
					}
					break;
				case 8:
					String txtYMDDate = txtDate.substring(0, 4) + "/" + txtDate.substring(4, 6) + "/" + txtDate.substring(6, 8);
					if (DateUtil.isYMDDate(txtYMDDate)) {
						dialog.txtEntryDate.setText(txtYMDDate);
					}
					break;
			}
		}

		if (!dialog.txtEntryDate.isEmpty() && !dialog.txtEntryDate.isDateFormat()) {
			showMessage(dialog, "I00113");// ���������t�K�i����͂��Ă��������B
			dialog.txtEntryDate.requestFocus();
			return false;
		}

		return true;
	}

	/**
	 * ��������
	 * 
	 * @return ��������
	 */
	protected SlipCondition getCondition() {
		SlipCondition condition = new SlipCondition();
		condition.setCompanyCode(getCompanyCode()); // ��ЃR�[�h
		condition.setSlipNoLike(dialog.txtSlipNo.getText()); // �`�[�ԍ�
		condition.setEntryDateFrom(dialog.txtEntryDate.getStartDate()); // �o�^���tFrom
		condition.setEntryDateTo(dialog.txtEntryDate.getEndDate()); // �o�^���tTo
		condition.setDepartmentCodeLike(dialog.txtDepCode.getText()); // ����R�[�h
		condition.setDepartmentNamesLike(dialog.txtDepNames.getText()); // ���嗪��
		condition.setRemarksLike(dialog.txtSlipRemarks.getText()); // �E�v
		condition.addSlipType(slipTypes); // �`�[���
		condition.addDataKind(dataKinds); // �f�[�^�敪
		condition.setOrder(" SWK_DEN_NO "); // �p�^�[���ԍ��̏���

		if (!notUsePermission) {
			// �Q�ƌ������L���̏ꍇ�̂݁A����

			// �d�󎫏��̏ꍇ�A�˗��҂̂�(NULL�\)
			if (selfOnly && !notUseSelfOnly) {
				condition.setRequestEmpCode(getUser().getEmployee().getCode());
				condition.setRequestEmpCodeIncledNull(true);
			}

			// �˗��ҁA�˗�����Ő���
			if (getUser().getSlipRole() == SlipRole.SELF) {
				condition.setRequestEmpCode(getUser().getEmployee().getCode());
				condition.setRequestEmpCodeIncledNull(false);

			} else if (getUser().getSlipRole() == SlipRole.DEPARTMENT) {
				condition.setRequestDeptCode(getUser().getDepartment().getCode());
				condition.setRequestDeptCodeIncledNull(false);
			}
		}

		if (!includeLocked) {
			// �r������ĂȂ��f�[�^���Ώ�
			condition.setNotLocked();
		}

		return condition;
	}

	/**
	 * �ꗗ�\������
	 * 
	 * @param hdr �w�b�_
	 * @return �ꗗ�\�����ڃf�[�^
	 */
	protected List<Object> getRow(SWK_HDR hdr) {
		List<Object> ssList = new ArrayList<Object>(4);
		ssList.add(hdr);
		ssList.add(hdr.getSWK_DEN_NO());

		if (hdr.getSWK_INP_DATE() != null) {
			ssList.add(DateUtil.toYMDString(hdr.getSWK_INP_DATE()));
		} else {
			ssList.add(DateUtil.toYMDString(hdr.getINP_DATE()));
		}
		ssList.add(hdr.getSWK_DEP_CODE());
		ssList.add(hdr.getSWK_DEP_NAME_S());
		ssList.add(hdr.getSWK_TEK());

		return ssList;
	}

	/**
	 * �I���f�[�^�̎擾
	 * 
	 * @return �w�b�_
	 */
	public SWK_HDR getSelectedRow() {

		return (SWK_HDR) dialog.tbl.getSelectedRowValueAt(TSlipPatternSearchDialog.SC.bean);
	}
}
