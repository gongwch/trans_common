package jp.co.ais.trans.master.ui;

import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.entity.*;

/**
 * �r���Ǘ��}�X�^�R���g���[��
 */
public class MG0360UserUnLockPanelCtrl extends TPanelCtrlBase {

	/** �����T�[�u���b�g */
	private static final String TARGET_SERVLET = "MG0360UserUnLockServlet";

	/** �p�l�� */
	private MG0360UserUnLockPanel panel;

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
	public MG0360UserUnLockPanelCtrl() {
		panel = new MG0360UserUnLockPanel(this);
		try {
			setSpreadData(new ArrayList<PCI_CHK_CTL>(0));

			// ��ʍX�V
			reflesh();
		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00010");
		}
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
		List<PCI_CHK_CTL> list = (List<PCI_CHK_CTL>) super.getResultObject();

		// �w���Ђ̃��O�C����񂪂Ȃ��ƃ��X�g���\�����Ȃ�
		if (list.size() == 0) {
			// �{�^���C�x���g������
			panel.btnSettle.setEnabled(false);
			setSpreadData(new ArrayList<PCI_CHK_CTL>(0));
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
	private void setSpreadData(List<PCI_CHK_CTL> list) {
		// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
		Vector<Vector> cells = new Vector<Vector>(list.size());

		for (PCI_CHK_CTL record : list) {
			Vector<String> colum = new Vector<String>(4);

			// VECTOR�ɃC���f�b�N�X���g�����߁A�l��ݒ�
			colum.add(0, "");

			// ���[�U�R�[�h
			colum.add(1, record.getUSR_ID());

			// ���[�U��
			colum.add(2, record.getUSR_NAME());

			// ���O�C������
			colum.add(3, DateUtil.toYMDHMSString(record.getPCI_CHECK_IN_TIME()));

			cells.add(colum);
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
	 * �I�����ꂽ�r�����X�g����
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

			// ��ȏ�I��
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

			// ��ʍX�V
			reflesh();

			showMessage(panel, "I00008");// �o�^����

		} catch (Exception a) {
			errorHandler(panel.getParentFrame(), a, "E00009");
		}
	}
}
