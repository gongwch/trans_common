package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;

/**
 * ���s���O�Q�ƃR���g���[��
 * 
 * @author roh
 */
public class MG0028ExecutedLogPanelCtrl extends TPanelCtrlBase {

	/** ���t��\�[�g */
	private final int SORT_DATE = 1;

	/** ���[�U�R�[�h��\�[�h */
	private final int SORT_USR_CODE = 2;

	/** �v���O������\�[�h */
	private final int SORT_PRG_CODE = 3;

	/** �����T�[�u���b�g(�v���O�����j */
	protected static final String TARGET_SERVLET = "MG0028ExecutedLogServlet";

	/** �p�l�� */
	protected MG0028ExecutedLogPanel panel;

	/**
	 * �p�l���擾
	 * 
	 * @return �p�l��
	 */
	public TPanelBusiness getPanel() {
		return this.panel;
	}

	/**
	 * �R���X�g���N�^
	 */
	public MG0028ExecutedLogPanelCtrl() {
		panel = new MG0028ExecutedLogPanel(this);
		panel.fedStartDate.getCalendar().requestFocus();

		// ���������C�x���g�o�^
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				initEvent();
			}
		});

		// ���b�Z�[�W�o�^
		initMessage();

		// �e�[�u�������\��
		setTableDs(new ArrayList(0));
	}

	/**
	 * �����t�B�[���h�̃C�x���g��o�^
	 */
	protected void initEvent() {

		// �J�n���[�U�R�[�h�̐ݒ�
		final REFDialogCtrl startUserDialog = new REFDialogCtrl(panel.fedStartUser, panel.getParentFrame());
		startUserDialog.setTargetServlet("MG0020UserMasterServlet");
		startUserDialog.setTitleID(getWord("C02355"));
		startUserDialog.setColumnLabelIDs("C00589", "C00692", "C00693");
		startUserDialog.setShowsMsgOnError(false);
		startUserDialog.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("kind", "User");
				keys.put("endCode", panel.fedEndUser.getField().getText());
				return keys;
			}

		});

		// �I�����[�U�R�[�h�̐ݒ�
		final REFDialogCtrl endUserDialog = new REFDialogCtrl(panel.fedEndUser, panel.getParentFrame());
		endUserDialog.setTargetServlet("MG0020UserMasterServlet");
		endUserDialog.setTitleID(getWord("C02355"));
		endUserDialog.setColumnLabelIDs("C00589", "C00692", "C00693");
		endUserDialog.setShowsMsgOnError(false);
		endUserDialog.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("kind", "User");
				keys.put("beginCode", panel.fedStartUser.getField().getText());
				return keys;
			}

		});

		// �J�n���[�U�R�[�h�̃��[�X�g�t�H�[�J�X�ݒ�
		panel.fedStartUser.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				startUserDialog.refreshName();
				if (!startUserDialog.isCodeValid()) {
					showMessage(panel, "W00081");
					return false;
				}
				return true;
			}

		});

		// �I�����[�U�R�[�h�̃��[�X�g�t�H�[�J�X�ݒ�
		panel.fedEndUser.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				endUserDialog.refreshName();
				if (!endUserDialog.isCodeValid()) {
					showMessage(panel, "W00081");
					return false;
				}
				return true;
			}

		});

		// �J�n�v���O�����R�[�h�̐ݒ�
		final REFDialogCtrl startProgramDialog = new REFDialogCtrl(panel.fedStartProgram, panel.getParentFrame());
		startProgramDialog.setColumnLabels("C00818", "C00820", "C00821");
		startProgramDialog.setTargetServlet("MG0240ProgramMasterServlet");
		startProgramDialog.setTitleID(getWord("C02147"));
		startProgramDialog.setShowsMsgOnError(false);
		startProgramDialog.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("endCode", panel.fedEndProgram.getField().getText());
				keys.put("kind", "Program");
				return keys;
			}

		});

		// �J�n�v���O�����R�[�h�̃��[�X�g�t�H�[�J�X�ݒ�
		panel.fedStartProgram.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				startProgramDialog.refreshName();
				if (!startProgramDialog.isCodeValid()) {
					showMessage(panel, "W00081");
					return false;
				}
				return true;
			}

		});

		// �I���v���O�����R�[�h�̐ݒ�
		final REFDialogCtrl endProgramDialog = new REFDialogCtrl(panel.fedEndProgram, panel.getParentFrame());
		endProgramDialog.setColumnLabels("C00818", "C00820", "C00821");
		endProgramDialog.setTargetServlet("MG0240ProgramMasterServlet");
		endProgramDialog.setTitleID(getWord("C02147"));
		endProgramDialog.setShowsMsgOnError(false);
		endProgramDialog.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("beginCode", panel.fedStartProgram.getField().getText());
				keys.put("kind", "Program");
				return keys;
			}

		});

		// �I���v���O�����R�[�h�̃��[�X�g�t�H�[�J�X�ݒ�
		panel.fedEndProgram.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				endProgramDialog.refreshName();
				if (!endProgramDialog.isCodeValid()) {
					showMessage(panel, "W00081");
					return false;
				}
				return true;
			}

		});
	}

	/**
	 * ���b�Z�[�W�ݒ�
	 */
	protected void initMessage() {
		// ���x�����ݒ�
		panel.fedStartDate.getLabel().setText(getWord("C00347") + getWord("C02909"));
		panel.pnlUser.setLangMessageID(getWord("C00347") + getWord("C00528"));
		panel.pnlProgram.setLangMessageID(getWord("C00347") + getWord("C00477"));

		// �R���{�{�b�N�X�̍\��
		panel.sortCombo.getComboBox().addTextValueItem(SORT_DATE, getWord("C00218") + getWord("C02906"));
		panel.sortCombo.getComboBox().addTextValueItem(SORT_USR_CODE, getWord("C00528"));
		panel.sortCombo.getComboBox().addTextValueItem(SORT_PRG_CODE, getWord("C00477"));
	}

	/**
	 * �G�N�Z���o��
	 */
	protected void exportToExcel() {
		panel.fedStartDate.requestFocus();
		// ���������̌���
		if (!checkPanel()) {
			return;
		}

		// �T�u���b�g�ɓ`������p�����[�^
		Map<String, String> map = new HashMap<String, String>();
		map.put("flag", "report");
		map.put("startDate", panel.fedStartDate.getCalendar().getText());
		map.put("endDate", panel.fedEndDate.getCalendar().getText());
		map.put("startUser", panel.fedStartUser.getValue());
		map.put("endUser", panel.fedEndUser.getValue());
		map.put("startPrg", panel.fedStartProgram.getValue());
		map.put("endPrg", panel.fedEndProgram.getValue());
		map.put("sort", String.valueOf(panel.sortCombo.getComboBox().getSelectedItemValue()));
		map.put("loginFlag", BooleanUtil.toString(panel.checkProgram.isSelected()));

		// �G�N�Z���ŕ\������
		this.download(panel, TARGET_SERVLET, map);
	}

	/**
	 * ���������ɂ�郍�O�����X�v���b�h�V�[�g��\��
	 */
	protected void searchLog() {

		try {
			// ���������̌���
			if (!checkPanel()) {
				return;
			}

			// �T�u���b�g�ɓ`������p�����[�^
			addSendValues("flag", "findLog");
			addSendValues("startDate", panel.fedStartDate.getCalendar().getText());
			addSendValues("endDate", panel.fedEndDate.getCalendar().getText());
			addSendValues("startUser", panel.fedStartUser.getValue());
			addSendValues("endUser", panel.fedEndUser.getValue());
			addSendValues("startPrg", panel.fedStartProgram.getValue());
			addSendValues("endPrg", panel.fedEndProgram.getValue());
			addSendValues("sort", String.valueOf(panel.sortCombo.getComboBox().getSelectedItemValue()));
			addSendValues("loginFlag", BooleanUtil.toString(panel.checkProgram.isSelected()));

			if (!request(TARGET_SERVLET)) {
				setTableDs(new ArrayList(0));
				errorHandler(panel);
				return;
			}

			// ���O�ꗗ�K��
			List<ExecutedLog> dtoList = getResultDtoList(ExecutedLog.class);

			// �f�[�^�\�[�X�ݒ�
			setTableDs(dtoList);

			// �f�[�^�Ȃ��̏ꍇ�A���b�Z�[�W��\������
			if (dtoList.isEmpty()) {
				panel.fedStartDate.requestFocus();
				showMessage(panel, "W00100");
			}

		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * �e�[�u���Ƀf�[�^�\�[�X�\��
	 * 
	 * @param dtoList ���O���X�g
	 */
	protected void setTableDs(List<ExecutedLog> dtoList) {
		// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
		Vector<Vector> cells = new Vector<Vector>(dtoList.size());

		for (ExecutedLog record : dtoList) {
			Vector<String> colum = new Vector<String>(7);

			colum.add(0, DateUtil.toYMDHMSString(record.getEXCUTED_DATE())); // ���s���t
			colum.add(1, record.getUSR_CODE()); // ���[�U�R�[�h
			colum.add(2, record.getUSR_NAME()); // ���[�U��
			colum.add(3, record.getIP_ADDRESS()); // IP�A�h���X
			colum.add(4, record.getPROGRAM_CODE()); // �v���O�����R�[�h
			colum.add(5, record.getPROGRAM_NAME()); // �v���O��������
			colum.add(6, record.getSTATE()); // ���

			cells.add(colum);
		}

		// SS�f�[�^�̍\�z
		JCVectorDataSource ds = new JCVectorDataSource();

		// �Z���̑}��
		ds.setCells(cells);

		// �J�����̐��̎w��
		ds.setNumColumns(7);
		ds.setNumRows(cells.size());

		// �f�[�^�Z�b�g
		panel.tblLog.setDataSource(ds);
	}

	/**
	 * ���͏��������؂���B
	 * 
	 * @return boolean ���͗��`�F�b�N
	 */
	protected boolean checkPanel() {

		Date startDate = panel.fedStartDate.getValue();
		Date endDate = panel.fedEndDate.getValue();

		// �����J�n���ԂƏI�����Ԃ̔�r
		if (!Util.isSmallerThenByYMD(startDate, endDate)) {
			showMessage(panel.getParentFrame(), "W00035");
			panel.fedStartDate.requestTextFocus();
			return false;
		}

		// �������[�U�R�[�h�̔�r(�������͂��ꂽ�ꍇ�̂݁j
		if (!panel.fedStartUser.getValue().equals("") && !panel.fedEndUser.getValue().equals("")) {
			if (panel.fedStartUser.getValue().compareTo(panel.fedEndUser.getValue()) > 0) {
				showMessage(panel.getParentFrame(), "W00036");
				panel.fedStartUser.requestTextFocus();
				return false;
			}
		}

		// �����v���O�����R�[�h�̔�r(�������͂��ꂽ�ꍇ�̂݁j
		if (!panel.fedStartProgram.getValue().equals("") && !panel.fedEndProgram.getValue().equals("")) {
			if (panel.fedStartProgram.getValue().compareTo(panel.fedEndProgram.getValue()) > 0) {
				showMessage(panel.getParentFrame(), "W00036");
				panel.fedStartProgram.requestTextFocus();
				return false;
			}
		}

		// �����̑��s��₤
		if (!showConfirmMessage(panel.getParentFrame(), "Q00011")) {
			return false;
		}

		return true;
	}
}
