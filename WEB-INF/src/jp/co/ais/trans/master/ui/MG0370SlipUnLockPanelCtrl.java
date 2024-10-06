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
public class MG0370SlipUnLockPanelCtrl extends TPanelCtrlBase {

	/** �����T�[�u���b�g */
	private static final String TARGET_SERVLET = "MG0370SlipUnLockServlet";

	/** ���[�U���X�g */
	private List<USR_MST> userList;

	/** �V�X�e���敪���X�g */
	private List<SYS_MST> systemList;

	/** �p�l�� */
	private MG0370SlipUnLockPanel panel;

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
	@Override
	public TPanelBusiness getPanel() {
		return this.panel;
	}

	/**
	 * �R���X�g���N�^
	 */
	public MG0370SlipUnLockPanelCtrl() {
		panel = new MG0370SlipUnLockPanel(this);

		try {
			initList();
			setSpreadData(new ArrayList<SlipUnlockObject>(0));

			// ��ʍX�V
			reflesh();
		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00010");
		}
	}

	/**
	 * ���[�U���́A�V�X�e�����̂̎擾
	 * 
	 * @throws IOException
	 */
	private void initList() throws IOException {
		addSendValues("flag", "getUser");
		if (!request(TARGET_SERVLET)) {
			errorHandler(panel);
			return;
		}
		userList = (List<USR_MST>) super.getResultObject();

		addSendValues("flag", "getSystem");

		if (!request(TARGET_SERVLET)) {
			errorHandler(panel);
			return;
		}
		systemList = (List<SYS_MST>) super.getResultObject();
	}

	/**
	 * �e�[�u���X�V �i�r�����[�U���X�g�K���j
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
		List<SlipUnlockObject> list = (List<SlipUnlockObject>) super.getResultObject();

		// �w���Ђ̃��O�C����񂪂Ȃ��ƃ��X�g���\�����Ȃ�
		if (list == null || list.size() == 0) {
			// �{�^���C�x���g������
			panel.btnSettle.setEnabled(false);
			setSpreadData(new ArrayList<SlipUnlockObject>(0));
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
	private void setSpreadData(List<SlipUnlockObject> list) {
		// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
		Vector<Vector> cells = new Vector<Vector>(list.size());

		for (SlipUnlockObject record : list) {
			Vector<Object> colum = new Vector<Object>(9);

			// VECTOR�ɃC���f�b�N�X���g�����߁A�l��ݒ�
			colum.add(0, "");

			// �T�u�V�X�e����
			colum.add(1, getSystemName(record.getSubSystemCode()));

			// �`�[�ԍ�
			colum.add(2, record.getSilpNo());

			// �`�[�敪
			colum.add(3, record.getSilpName());

			// �`�[���t
			colum.add(4, DateUtil.toYMDHMString(record.getSilpDate()));

			// �`�[�E�v
			colum.add(5, record.getSilpTek());

			// �r�����[�U�R�[�h
			colum.add(6, record.getUserCode());

			// �r�����[�U����
			colum.add(7, getUserName(record.getUserCode()));

			// �r�����s��
			colum.add(8, DateUtil.toYMDHMString(record.getExclusiveDate()));

			// �f�[�^
			colum.add(9, record);

			cells.add(colum);
		}

		// SS�f�[�^�̍\�z
		ds = new JCVectorDataSource();

		// �Z���̑}��
		ds.setCells(cells);

		// �J�����̐��̎w��
		ds.setNumColumns(9);
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
	 * �I�����ꂽ�r�����X�g����
	 */
	protected void deleteDto() {
		// ���M
		try {
			List<SlipUnlockObject> list = new LinkedList<SlipUnlockObject>();

			// ��ʂɑI�����ꂽ�f�[�^
			for (int nomRow = 0; nomRow < ds.getNumRows(); nomRow++) {
				boolean status = ((TCheckBox) this.panel.lockTable.getComponent(nomRow, 0)).isSelected();

				if (status) {
					SlipUnlockObject slip = new SlipUnlockObject();
					slip = (SlipUnlockObject) ds.getTableDataItem(nomRow, 9);
					slip.setSilpDate(slip.getSilpDate());
					// �`�F�b�N�{�b�N�X���I������Ă���ꍇ
					list.add(slip);
				}
			}

			// ��ȏ�I��
			if (list.size() == 0) {
				showMessage(panel, "W00195");
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
	 * �T�u�V�X�e���敪�P��擾
	 * 
	 * @param kbn
	 * @return �T�u�V�X�e���敪�P��
	 */
	private String getSystemName(int kbn) {
		for (SYS_MST system : systemList) {
			String sysKbn = "";
			if (kbn == SYS_MST.AP_HDR || kbn == SYS_MST.AP_PTN) {
				sysKbn = SYS_MST.AP;
			}
			if (kbn == SYS_MST.AR_HDR || kbn == SYS_MST.AR_PTN) {
				sysKbn = SYS_MST.AR;
			}
			if (kbn == SYS_MST.GL_HDR || kbn == SYS_MST.GL_PTN) {
				sysKbn = SYS_MST.GL;
			}
			if (kbn == SYS_MST.LM_HDR) {
				sysKbn = SYS_MST.LM;
			}
			if (system.getSYS_KBN().equals(sysKbn)) {
				return system.getSYS_KBN_NAME();
			}
		}
		return "";
	}

	/**
	 * �J�����\���p���\�b�h
	 * 
	 * @param word1
	 * @param word2
	 * @return ���[�h
	 */
	protected String getLinkedWord(String word1, String word2) {
		String word = getWord(word1) + getWord(word2);
		return word;
	}
}
