package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.event.*;

import javax.swing.*;

/**
 * �p�^�[�����̓p�l��
 */
public abstract class TSlipPatternPanel extends TSlipPanel {

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainPanel#initComponents()
	 */
	@Override
	public void initComponents() {
		super.initComponents();

		// �w�b�_����

		// �`�[���t
		ctrlSlipDate.setAllowableBlank(true);
		ctrlSlipDate.setEditable(false);
		ctrlSlipDate.setValue(null); // ���t�Ȃ�

		// ���Z�i�K
		ctrlCloseEntry.setNotVisibleStage(); // �`�F�b�N�{�b�N�X�̂�

		// �p�^�[���ԍ�
		ctrlSlipNo.setLangMessageID("C00987");
		ctrlSlipNo.setLabelSize(75);
		ctrlSlipNo.setEditable(true); // ���͉�

		// ����
		ctrlDetail = createPatternDetailPanel();
	}

	/**
	 * ���׃p�l���쐬
	 * 
	 * @return ���׃p�l��
	 */
	public TSlipDetailPanel createPatternDetailPanel() {

		if (isTFormMode()) {
			return new TFormSlipPatternDetailPanel(this);
		}
		return new TSlipPatternDetailPanel();
	}

	/**
	 * �{�^�����̔z�u<br>
	 * �d�󎫏��{�^���Ȃ�
	 */
	@Override
	public void allocateButtons() {

		int x = X_POINT;

		// switchNew();
		lblState.setSize(30, 25);
		lblState.setHorizontalAlignment(SwingConstants.CENTER);
		lblState.setOpaque(true);
		lblState.setLocation(x, HEADER_Y);
		pnlHeader.add(lblState);

		// �V�K�{�^��
		x = x + lblState.getWidth() + HEADER_MARGIN_X;
		btnNew.setLangMessageID("C00303");
		btnNew.setShortcutKey(KeyEvent.VK_F2);
		btnNew.setSize(25, 110);
		btnNew.setLocation(x, HEADER_Y);
		pnlHeader.add(btnNew);

		// �C��
		x = x + btnNew.getWidth() + HEADER_MARGIN_X;
		btnModify.setLangMessageID("C01760");
		btnModify.setShortcutKey(KeyEvent.VK_F3);
		btnModify.setSize(25, 110);
		btnModify.setLocation(x, HEADER_Y);
		pnlHeader.add(btnModify);

		// ����
		x = x + btnModify.getWidth() + HEADER_MARGIN_X;
		btnCopy.setLangMessageID("C00459");
		btnCopy.setShortcutKey(KeyEvent.VK_F4);
		btnCopy.setSize(25, 110);
		btnCopy.setLocation(x, HEADER_Y);
		pnlHeader.add(btnCopy);

		// �폜
		x = x + btnCopy.getWidth() + HEADER_MARGIN_X;
		btnDelete.setLangMessageID("C01544");
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
		btnDelete.setSize(25, 110);
		btnDelete.setLocation(x, HEADER_Y);
		pnlHeader.add(btnDelete);

		// �m��
		x = x + btnDelete.getWidth() + HEADER_MARGIN_X;
		btnEntry.setLangMessageID("C01019");
		btnEntry.setShortcutKey(KeyEvent.VK_F8);
		btnEntry.setSize(25, 110);
		btnEntry.setLocation(x, HEADER_Y);
		pnlHeader.add(btnEntry);
	}
}
