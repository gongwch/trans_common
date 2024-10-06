package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.entity.*;

/**
 * ���b�N�A�E�g��ʃR���g���[��
 */
public class MG0027LockOutMasterPanelCtrl extends TPanelCtrlBase {

	/** �����T�[�u���b�g */
	private static final String TARGET_SERVLET = "MG0027LockOutMasterServlet";

	/** �����T�[�u���b�g(���[�U�F�؁j */
	private static final String TARGET_SERVLET_AUTH = "MG0026UserAuthUpdateServlet";

	/** �p�l�� */
	public MG0027LockOutMasterPanel panel;

	/** �X�v���b�hCheckBox�I���C�x���g */
	private ItemListener ssCbxListener = new ItemListener() {

		public void itemStateChanged(ItemEvent e) {
			int focusRow = panel.lockTable.getCurrentRow();
			panel.lockTable.setRowSelection(focusRow, focusRow);
		}
	};

	private JCVectorDataSource ds;

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
	public MG0027LockOutMasterPanelCtrl() {
		panel = new MG0027LockOutMasterPanel(this);
		try {
			setSpreadData(new ArrayList(0), 0);

			reflesh();
		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00010");
		}
	}

	/**
	 * �e�[�u���X�V �i���b�N�A�E�g���X�g�K���j
	 * 
	 * @throws IOException
	 */
	protected void reflesh() throws IOException {
		addSendValues("flag", "find");

		// ���M
		if (!request(TARGET_SERVLET)) {
			errorHandler(panel);
			return;
		}

		// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
		List<LOCK_OUT_TBL> list = super.getResultDtoList(LOCK_OUT_TBL.class);

		// �w���Ђ̃��b�N�A�E�g��񂪂Ȃ��ƃ��X�g�\���Ȃ�
		if (list.size() == 0) {
			// �{�^���̃C�x���g������
			panel.btnSettle.setEnabled(false);
			setSpreadData(new ArrayList(0), 0);
			return;
		}

		// ���[�U�F�؃}�X�^���烍�b�N�A�E�g�J���������K��
		addSendValues("flag", "findByKaiCode");

		if (!request(TARGET_SERVLET_AUTH)) {
			errorHandler(panel);
			return;
		}

		USR_AUTH_MST usrAuthDto = (USR_AUTH_MST) getResultDto(USR_AUTH_MST.class);
		int lockoutTerm;

		// �F�؏�񂪂Ȃ��ƃ��b�N�A�E�g���X�g�Ȃ�
		if (usrAuthDto == null) {
			return;
		}
		lockoutTerm = usrAuthDto.getLOCK_OUT_RELEASE_TIME();
		setSpreadData(list, lockoutTerm);
	}

	/**
	 * �f�[�^���e�[�u���ɐݒ肷��B
	 * 
	 * @param list ���b�N�A�E�g���X�g
	 * @param lockoutTerm �J������
	 */
	public void setSpreadData(List<LOCK_OUT_TBL> list, int lockoutTerm) {

		// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
		Vector<Vector> cells = new Vector<Vector>(list.size());

		long sysDate = (Util.getCurrentDate().getTime()) / 60000;

		CellStyleModel defaultStyle = panel.lockTable.getDefaultCellStyle();

		// cell�X�^�C�������p�C���f�b�N�X
		int rowIndex = 0;

		for (LOCK_OUT_TBL record : list) {
			Vector<String> colum = new Vector<String>(4);

			// VECTOR�ɃC���f�b�N�X���g�����߁A�l��ݒ�
			colum.add(0, "");

			// ���[�U�R�[�h
			colum.add(1, record.getUSR_CODE());

			// ���[�U��
			colum.add(2, record.getUSR_NAME());

			// ���s���t
			colum.add(3, DateUtil.toYMDHMSString(record.getFAIL_DATE()));

			// �O�ݒ�A���Ȃ��͏������Ȃ�
			if (lockoutTerm != 0.0) {
				long failDate = (record.getFAIL_DATE().getTime()) / 60000;

				// ���b�N�A�E�g�J���������o�߂����ꍇ�B�Ԃ������ŕ\��
				if (lockoutTerm < (sysDate - failDate)) {
					JCCellStyle centerStyle = new JCCellStyle(defaultStyle);
					centerStyle.setForeground(Color.RED);
					centerStyle.setHorizontalAlignment(SwingConstants.CENTER);
					panel.lockTable.setCellStyle(new JCCellRange(rowIndex, 3, rowIndex, 3), centerStyle);

					JCCellStyle leftStyle = new JCCellStyle(defaultStyle);
					leftStyle.setForeground(Color.RED);
					leftStyle.setHorizontalAlignment(SwingConstants.LEFT);
					panel.lockTable.setCellStyle(new JCCellRange(rowIndex, 1, rowIndex, 2), leftStyle);
				}
			}
			cells.add(colum);
			rowIndex = rowIndex + 1;
		}

		// SS�f�[�^�̍\�z
		ds = new JCVectorDataSource();

		// �Z���̑}��
		ds.setCells(cells);

		// �J�����̐��̎w��
		ds.setNumColumns(4);
		ds.setNumRows(cells.size());

		// �Ώ������ޯ���̍\�z
		for (int i = 0; i < cells.size(); i++) {
			TCheckBox tCheckBox = new TCheckBox();
			tCheckBox.setOpaque(false);
			tCheckBox.setHorizontalAlignment(JCTableEnum.CENTER);
			tCheckBox.addItemListener(ssCbxListener);

			panel.lockTable.setComponent(i, 0, tCheckBox);
		}

		// �f�[�^�Z�b�g
		panel.lockTable.setDataSource(ds);
	}

	/**
	 * ���b�N�A�E�g����
	 */
	protected void deleteDto() {
		// ���M
		try {
			List<String> list = new LinkedList<String>();

			// ��ʂɑI�����ꂽ�f�[�^
			for (int nomRow = 0; nomRow < ds.getNumRows(); nomRow++) {
				boolean status = ((TCheckBox) this.panel.lockTable.getComponent(nomRow, 0)).isSelected();

				if (status) {
					// �`�F�b�N�{�b�N�X���I������Ă���ꍇ
					list.add((String) ds.getTableDataItem(nomRow, 1));
				}
			}

			if (list.size() == 0) {
				showMessage(panel, "W00195");
				return;
			}

			addSendValues("flag", "delete");
			addSendList(list);

			if (!request(TARGET_SERVLET)) {
				errorHandler(panel);
				return;
			}

			reflesh();

			showMessage(panel, "I00008");

		} catch (Exception a) {
			errorHandler(panel.getParentFrame(), a, "E00009");
		}
	}
}
