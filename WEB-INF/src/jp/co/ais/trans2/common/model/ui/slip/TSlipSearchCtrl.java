package jp.co.ais.trans2.common.model.ui.slip;

import static jp.co.ais.trans2.define.SlipState.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.exception.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * �`�[�����R���g���[��
 */
public class TSlipSearchCtrl extends TController {

	/** ��� */
	public static int CANCEL_OPTION = 0;

	/** �m�� */
	public static int OK_OPTION = 1;

	/** �X�e�[�g */
	protected int option = CANCEL_OPTION;

	/** �ꗗ�_�C�A���O */
	protected TSlipSearchDialog dialog;

	/** �`�[��� */
	protected String[] slipTypes = new String[0];

	/** �f�[�^�敪 */
	protected String[] dataKinds = new String[0];

	/** true:���A���[�U�̂݃t���O���� */
	protected static boolean notUseSelfOnly = ClientConfig.isFlagOn("trans.slip.copy.notuse.selfonly");

	/** true:���A�Q�ƌ������� */
	protected static boolean notUsePermission = ClientConfig.isFlagOn("trans.slip.copy.notuse.permission");

	/** �ő啡�ʌ����Ώی��� */
	protected static int maxHeaderCount = Util.avoidNullAsInt(ClientConfig.getProperty("trans.slip.copy.max.header.count"));

	/** �ҏW�����ʂ�(true:�ҏW) */
	protected boolean isModify = true;

	/** ���V�X���܂߂邩�ǂ��� */
	protected boolean includeOtherSystem = false;

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
	 * @param mainView ���R���|�[�l���g
	 */
	public TSlipSearchCtrl(TPanel mainView) {
		dialog = createDialog(mainView);

		// �C�x���g�o�^
		addEvent();

		// �_�C�A���O������
		initDialogView();
	}

	/**
	 * �ꗗ�_�C�A���O����
	 * 
	 * @param mainView ���R���|�[�l���g
	 * @return �ꗗ�_�C�A���O
	 */
	protected TSlipSearchDialog createDialog(TPanel mainView) {
		return new TSlipSearchDialog(mainView);
	}

	/**
	 * ���ʂɐؑ�
	 */
	public void switchCopyMode() {

		this.isModify = false;

		// ���ʂ̂�
		dialog.cmbUpdDivision.addTextValueItem(APPROVE, getWord(APPROVE.getName()));
		dialog.cmbUpdDivision.addTextValueItem(UPDATE, getWord(UPDATE.getName()));
		dialog.ctrlCopyMode.setVisible(true);
	}

	/**
	 * ���ʃ��[�h�I���p�l�����\��
	 */
	public void switchNonVisibleCopyMode() {
		dialog.ctrlCopyMode.setVisible(false);
	}

	/**
	 * �����_�C�A���O��\��
	 */
	protected void addEvent() {
		// �e����(�e�L�X�g)
		dialog.txtSlipNo.addKeyListener(txtListener);
		dialog.txtSlipDate.addKeyListener(txtListener);
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
	 * �_�C�A���O�̏����ݒ�
	 */
	protected void initDialogView() {

		// �X�V�敪�_�C�A���O

		// ���ꏳ�F���g����
		boolean useField = getCompany().getAccountConfig().isUseFieldApprove();

		// �X�V�敪���X�g
		List stateList = new LinkedList<TComboBox.TTextValue>();

		// �I���Ȃ�
		stateList.add(new TComboBox.TTextValue("", null));

		// �o�^
		stateList.add(new TComboBox.TTextValue(getWord(ENTRY.getName()), ENTRY));

		// ����۔F
		if (useField) {
			stateList.add(new TComboBox.TTextValue(getWord(FIELD_DENY.getName()), FIELD_DENY));
		}

		// �۔F
		stateList.add(new TComboBox.TTextValue(getWord(DENY.getName()), DENY));

		// ���ꏳ�F
		if (useField) {
			stateList.add(new TComboBox.TTextValue(getWord(FIELD_APPROVE.getName()), FIELD_APPROVE));
		}

		dialog.cmbUpdDivision.addItemList(stateList);
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
	 * �`�[����
	 */
	protected void searchList() {
		try {

			// ���̓`�F�b�N
			if (!checkInput()) {
				return;
			}

			dialog.tbl.removeRow();

			// �`�[����
			List<SWK_HDR> list = (List<SWK_HDR>) request(SlipManager.class, "getHeader", getCondition());

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

		} catch (TWarningException ex) {
			showMessage(ex.getMessage());

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
		if (!dialog.txtSlipDate.isEmpty()) {
			String txtDate = dialog.txtSlipDate.getText().toString();
			int length = txtDate.length();

			switch (length) {
				case 6:
					String txtYMDate = txtDate.substring(0, 4) + "/" + txtDate.substring(4, 6);
					if (DateUtil.isYMDate(txtYMDate)) {
						dialog.txtSlipDate.setText(txtYMDate);
					}
					break;
				case 8:
					String txtYMDDate = txtDate.substring(0, 4) + "/" + txtDate.substring(4, 6) + "/"
						+ txtDate.substring(6, 8);
					if (DateUtil.isYMDDate(txtYMDDate)) {
						dialog.txtSlipDate.setText(txtYMDDate);
					}
					break;
			}
		}

		if (!dialog.txtSlipDate.isEmpty() && !dialog.txtSlipDate.isDateFormat()) {
			showMessage(dialog, "I00113");// ���������t�K�i����͂��Ă��������B
			dialog.txtSlipDate.requestFocus();
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
		condition.setSlipDateFrom(dialog.txtSlipDate.getStartDate()); // �`�[���tFrom
		condition.setSlipDateTo(dialog.txtSlipDate.getEndDate()); // �`�[���tTo
		condition.setDepartmentCodeLike(dialog.txtDepCode.getText()); // ����R�[�h
		condition.setDepartmentNamesLike(dialog.txtDepNames.getText()); // ���嗪��
		condition.setRemarksLike(dialog.txtSlipRemarks.getText()); // �E�v
		condition.addSlipType(slipTypes); // �`�[���
		condition.addDataKind(dataKinds); // �f�[�^�敪
		condition.setIncludeOtherSystem(includeOtherSystem); // ���V�X�܂߂�

		condition.setGroupAccountOff(); // �t�֓`�[�͏���
		condition.setIfType(JornalIfType.ENTRY); // �d��C���^�[�t�F�[�X�敪 0:�o�^

		condition.setOrder(" SWK_DEN_NO DESC "); // �`�[�ԍ��~��

		if (!notUsePermission) {
			// �Q�ƌ������L���̏ꍇ�̂݁A����

			// ���ʂ̏ꍇ�A�˗��҂̂�(NULL�\)
			if (!isModify && !notUseSelfOnly) {
				condition.setRequestEmpCode(getUser().getEmployee().getCode());
				condition.setRequestEmpCodeIncledNull(true);
			}

			if (!isModify) {
				// ���ʎ��ɍő�w�b�_�[�擾��������
				condition.setMaxHeaderCount(maxHeaderCount);
			}

			// �ȊO�A�˗��ҁA�˗�����Ő���
			if (getUser().getSlipRole() == SlipRole.SELF) {
				condition.setRequestEmpCode(getUser().getEmployee().getCode());
				condition.setRequestEmpCodeIncledNull(false);

			} else if (getUser().getSlipRole() == SlipRole.DEPARTMENT) {
				condition.setRequestDeptCode(getUser().getDepartment().getCode());
				condition.setRequestDeptCodeIncledNull(false);
			}
		}

		// �C���̏ꍇ�́A�r������ĂȂ��f�[�^���Ώ�
		if (isModify) {
			condition.setNotLocked();
		}

		// �X�V�敪
		SlipState state = (SlipState) dialog.cmbUpdDivision.getSelectedItemValue();
		if (state != null) {
			condition.addSlipState(state);

		} else if (isModify) {
			condition.addSlipState(ENTRY, FIELD_DENY, DENY);
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
		List<Object> ssList = new ArrayList<Object>(5);
		ssList.add(hdr);
		ssList.add(hdr.getSWK_DEN_NO());
		ssList.add(DateUtil.toYMDString(hdr.getSWK_DEN_DATE()));
		ssList.add(hdr.getSWK_DEP_CODE());
		ssList.add(hdr.getSWK_DEP_NAME_S());
		ssList.add(hdr.getSWK_TEK());
		ssList.add(getWord(getSlipStateName(hdr.getSWK_UPD_KBN())));

		return ssList;
	}

	/**
	 * �I���f�[�^�̎擾
	 * 
	 * @return �w�b�_
	 */
	public SWK_HDR getSelectedRow() {

		return (SWK_HDR) dialog.tbl.getSelectedRowValueAt(TSlipSearchDialog.SC.bean);
	}

	/**
	 * �ԓ`���[�h���I�����ꂽ���ǂ���
	 * 
	 * @return true:�I�����ꂽ
	 */
	public boolean isSelectedCancel() {
		return dialog.ctrlCopyMode.isSelected(1);
	}

	/**
	 * �t�`���[�h���I�����ꂽ���ǂ���
	 * 
	 * @return true:�I�����ꂽ
	 */
	public boolean isSelectedReverse() {
		return dialog.ctrlCopyMode.isSelected(2);
	}

	/**
	 * ���V�X���܂߂邩�ǂ���
	 * 
	 * @param includeOtherSystem true:�܂߂�
	 */
	public void setIncludeOtherSystem(boolean includeOtherSystem) {
		this.includeOtherSystem = includeOtherSystem;
	}
}
