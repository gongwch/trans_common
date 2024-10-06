package jp.co.ais.trans.master.ui;

import java.awt.event.*;
import java.io.*;
import java.util.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.entity.*;

/**
 * �r���Ǘ��}�X�^�R���g���[��
 */
public class MG0380BatchUnLockPanelCtrl extends TPanelCtrlBase {

	/** �����T�[�u���b�g */
	private static final String TARGET_SERVLET = "MG0380BatchUnLockServlet";

	/** �v���O�������X�g */
	private List<PRG_MST> programList;

	/** ���[�U���X�g */
	private List<USR_MST> userList;

	/** �p�l�� */
	private MG0380BatchUnLockPanel panel;

	/** �X�v���b�hCheckBox�I���C�x���g */
	private ItemListener ssCbxListener = new ItemListener() {

		public void itemStateChanged(ItemEvent e) {
			int focusRow = panel.lockTable.getCurrentRow();
			panel.lockTable.setRowSelection(focusRow, focusRow);
		}
	};

	/** �f�[�^�\�[�X */
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
	public MG0380BatchUnLockPanelCtrl() {
		panel = new MG0380BatchUnLockPanel(this);

		try {
			initList(); // �v���O�������́A�r�����[�U���̂̎擾
			setSpreadData(new ArrayList<BAT_CTL>(0));

			// ��ʍX�V
			reflesh();
		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00010");
		}
	}

	/**
	 * �v���O�������́A���[�U���̂̎擾
	 * 
	 * @throws IOException
	 */
	private void initList() throws IOException {

		// �v���O��������
		addSendValues("flag", "getProgram");
		if (!request(TARGET_SERVLET)) {
			errorHandler(panel);
			return;
		}
		programList = (List<PRG_MST>) super.getResultObject();

		// ���[�U����
		addSendValues("flag", "getUser");
		if (!request(TARGET_SERVLET)) {
			errorHandler(panel);
			return;
		}
		userList = (List<USR_MST>) super.getResultObject();
	}

	/**
	 * �e�[�u���X�V �i�r���o�b�`���X�g�K���j
	 * 
	 * @throws IOException
	 */
	private void reflesh() throws IOException {
		addSendValues("flag", "find");

		// ���M
		if (!request(TARGET_SERVLET)) {
			errorHandler(panel);
			return;
		}

		// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
		List<BAT_CTL> list = (List<BAT_CTL>) super.getResultObject();

		// �w���Ђ̃��O�C����񂪂Ȃ��ƃ��X�g���\�����Ȃ�
		if (list.size() == 0) {
			// �{�^���C�x���g������
			panel.btnSettle.setEnabled(false);
			setSpreadData(new ArrayList<BAT_CTL>(0));
			return;
		}

		// �X�v���b�h�V�[�g�\��
		setSpreadData(list);
	}

	/**
	 * �f�[�^���e�[�u���ɐݒ肷��B
	 * 
	 * @param list �r���e�[�^���X�g
	 */
	private void setSpreadData(List<BAT_CTL> list) {
		// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
		Vector<Vector> cells = new Vector<Vector>(list.size());

		final int ColNoChkBox = 0; // �`�F�b�N�{�b�N�X
		final int ColNoPrgID = 1; // �v���O�����R�[�h
		final int ColNoPrgName = 2; // �v���O��������
		final int ColNoUsrID = 3; // �r�����[�U�R�[�h
		final int ColNoUsrName = 4; // �r�����[�U����
		final int ColNoBatDate = 5; // �r�����s��
		final int ColNoRecord = 6; // ���R�[�h

		for (BAT_CTL record : list) {
			Vector<Object> colum = new Vector<Object>(9);

			// VECTOR�ɃC���f�b�N�X���g�����߁A�l��ݒ�
			colum.add(ColNoChkBox, "");

			// �v���O�����R�[�h
			colum.add(ColNoPrgID, record.getBAT_ID());

			// �v���O��������
			colum.add(ColNoPrgName, getProgramName(record.getBAT_ID()));

			// �r�����[�U�R�[�h
			colum.add(ColNoUsrID, record.getUSR_ID());

			// �r�����[�U����
			colum.add(ColNoUsrName, getUserName(record.getUSR_ID()));

			// �r�����s��
			colum.add(ColNoBatDate, DateUtil.toYMDHMSString (record.getBAT_STR_DATE()));

			// �f�[�^
			colum.add(ColNoRecord, record);

			cells.add(colum);
		}

		// SS�f�[�^�̍\�z
		ds = new JCVectorDataSource();

		// �Z���̑}��
		ds.setCells(cells);

		// �J�����̐��̎w��
		ds.setNumColumns(6);
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
	 * �v���O�������̎擾
	 * 
	 * @param kbn �v���O�����R�[�h
	 * @return �v���O��������
	 */
	private String getProgramName(String kbn) {
		for (PRG_MST program : programList) {
			if (program.getPRG_CODE().equals(kbn)) {
				return program.getPRG_NAME();
			}
		}
		return "";
	}

	/**
	 * ���[�U���̎擾
	 * 
	 * @param kbn
	 * @return ���[�U����
	 */
	private String getUserName(String kbn) {
		for (USR_MST user : userList) {
			if (user.getUSR_CODE().equals(kbn)) {
				return user.getUSR_NAME();
			}
		}
		return "";
	}

	/**
	 * �I�����ꂽ�r�����X�g����
	 */
	protected void deleteDto() {
		// ���M
		try {
			List<BAT_CTL> list = new LinkedList<BAT_CTL>();

			// ��ʂɑI�����ꂽ�f�[�^
			for (int nomRow = 0; nomRow < ds.getNumRows(); nomRow++) {
				boolean status = ((TCheckBox) this.panel.lockTable.getComponent(nomRow, 0)).isSelected();

				if (status) {
					// �`�F�b�N�{�b�N�X���I������Ă���ꍇ
					list.add((BAT_CTL) ds.getTableDataItem(nomRow, 6));
				}
			}

			// ��ȏ�I��
			if (list.size() == 0) {
				showMessage(panel, "W00195");
				return;
			}
			
			//	���s�m�Fү����
			if (!showConfirmMessage(panel, "Q00016")) {
				return;
			}

			addSendValues("flag", "delete");
			addSendObject(list);

			if (!request(TARGET_SERVLET)) {
				errorHandler(panel);
				return;
			}

			// ��ʍX�V
			reflesh();

			showMessage(panel, "I00008");// �o�^����

		} catch (Exception a) {
			errorHandler(panel.getParentFrame(), a, "E00009");
		}
	}
}
