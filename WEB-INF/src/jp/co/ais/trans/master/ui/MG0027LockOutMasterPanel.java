package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.klg.jclass.table.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TTable;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * ���[�U�F�؊Ǘ��}�X�^�p�l��
 * 
 * @author roh
 */
public class MG0027LockOutMasterPanel extends TPanelBusiness {

	/** UID */
	private static final long serialVersionUID = 1285281438447797312L;

	/** �R���g���[�� */
	private MG0027LockOutMasterPanelCtrl ctrl;

	/** �폜�{�^�� */
	public TImageButton btnSettle;

	/** ��ʃ{�^���z�u�p�l�� */
	public TMainHeaderPanel pnlButton;

	/** �t�b�^�p�l�� */
	public TPanel pnlFooter;

	/** �\���e�[�u�� */
	public TTable lockTable;

	/** �ڍ׃t�B�[���h�z�u�p�l�� */
	public TPanel pnlJournal;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param ctrl ��ʃR���g���[��
	 */
	public MG0027LockOutMasterPanel(MG0027LockOutMasterPanelCtrl ctrl) {
		this.ctrl = ctrl;

		initComponents();
		initSpreadSheet();

		super.initPanel();
	}

	/** ��� �\�z */
	public void initComponents() {
		GridBagConstraints gridBagConstraints;

		pnlButton = new TMainHeaderPanel();
		btnSettle = new TImageButton(IconType.DELETE);
		pnlFooter = new TPanel();
		lockTable = new TTable();
		pnlJournal = new TPanel();

		setLayout(new GridBagLayout());
		setMaximumSize(new Dimension(800, 600));
		setMinimumSize(new Dimension(800, 600));
		setPreferredSize(new Dimension(800, 600));

		pnlButton.setLayout(new GridBagLayout());
		pnlButton.setMaximumSize(new Dimension(800, 40));
		pnlButton.setMinimumSize(new Dimension(800, 40));
		pnlButton.setPreferredSize(new Dimension(800, 40));

		btnSettle.setLangMessageID("C01544");
		btnSettle.setShortcutKey(KeyEvent.VK_F7);
		btnSettle.setMaximumSize(new Dimension(110, 25));
		btnSettle.setMinimumSize(new Dimension(110, 25));
		btnSettle.setPreferredSize(new Dimension(110, 25));
		btnSettle.setTabControlNo(2);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 320, 5, 0);
		pnlButton.add(btnSettle, gridBagConstraints);
		btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				ctrl.deleteDto();
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		add(pnlButton, gridBagConstraints);

		// �e�[�u���ݒ�
		lockTable.setTabControlNo(1);
		// Scroll�ʒu�ݒ�
		lockTable.setHorizSBPosition(JCTableEnum.POSITION_AT_SIDE);
		lockTable.setVertSBPosition(JCTableEnum.POSITION_AT_SIDE);

		pnlJournal.setLayout(new BoxLayout(pnlJournal, BoxLayout.X_AXIS));

		pnlJournal.setMaximumSize(new Dimension(780, 460));
		pnlJournal.setMinimumSize(new Dimension(780, 460));
		pnlJournal.setPreferredSize(new Dimension(780, 460));
		pnlJournal.add(lockTable);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(10, 20, 10, 5);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		add(pnlJournal, gridBagConstraints);

		// �t�b�^�p�l���ݒ�
		pnlFooter.setLayout(new GridBagLayout());

		pnlFooter.setMaximumSize(new Dimension(800, 50));
		pnlFooter.setMinimumSize(new Dimension(800, 50));
		pnlFooter.setPreferredSize(new Dimension(800, 50));

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		add(pnlFooter, gridBagConstraints);
	}

	/** �X�v���b�h�\�� */
	private void initSpreadSheet() {

		String[] slipTypelabel = new String[] { "", "C00589", "C00691", "C02781" };
		int[] columnWidths = new int[] { 3, 10, 20, 14 };

		// ��A�s�\��̃X�^�C���ݒ�
		lockTable.initSpreadSheet(slipTypelabel, columnWidths);

		CellStyleModel defaultStyle = lockTable.getDefaultCellStyle();

		JCCellStyle leftStyle = new JCCellStyle(defaultStyle);
		leftStyle.setHorizontalAlignment(CellStyleModel.LEFT);

		lockTable.setCellStyle(JCTableEnum.ALLCELLS, 1, leftStyle);
		lockTable.setCellStyle(JCTableEnum.ALLCELLS, 2, leftStyle);

		JCCellStyle centerStyle = new JCCellStyle(defaultStyle);
		centerStyle.setHorizontalAlignment(CellStyleModel.CENTER);

		lockTable.setCellStyle(JCTableEnum.ALLCELLS, 3, centerStyle);
	}
}