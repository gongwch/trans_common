package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * �����[�X�t�@�C���ꗗ�o�̓p�l��
 * 
 * @author roh
 */
public class MG0029ReleasedFilePanel extends TPanelBusiness {

	/** UID */
	private static final long serialVersionUID = 6864139718117925182L;

	/** �R���g���[�� */
	private MG0029ReleasedFilePanelCtrl ctrl;

	/** �{�^���p�l�� */
	public TMainHeaderPanel pnlButton;

	/** �G�N�Z���o�̓{�^�� */
	public TImageButton btnExcel;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param ctrl ��ʃR���g���[��
	 */
	public MG0029ReleasedFilePanel(MG0029ReleasedFilePanelCtrl ctrl) {
		this.ctrl = ctrl;
		initComponents();
		super.initPanel();
	}

	/**
	 * ��ʍ\�z
	 */
	private void initComponents() {
		pnlButton = new TMainHeaderPanel();
		btnExcel = new TImageButton(IconType.EXCEL);

		// ��{���C�A�E�g
		setLayout(new BorderLayout());
		setMaximumSize(new Dimension(800, 600));
		setMinimumSize(new Dimension(800, 600));
		setPreferredSize(new Dimension(800, 600));

		// �{�^���p�l�����C�A�E�g
		pnlButton.setLayout(null);
		pnlButton.setMaximumSize(new Dimension(800, 45));
		pnlButton.setMinimumSize(new Dimension(800, 45));
		pnlButton.setPreferredSize(new Dimension(800, 45));

		// �G�N�Z���{�^��
		btnExcel.setLangMessageID("C01545");
		btnExcel.setShortcutKey(KeyEvent.VK_F6);
		btnExcel.setSize(25, 110);
		btnExcel.setLocation(5, 10);

		btnExcel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				ctrl.exportToExcel();
			}

		});

		pnlButton.add(btnExcel);

		add(pnlButton, BorderLayout.NORTH);
	}

}
