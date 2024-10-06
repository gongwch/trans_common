package jp.co.ais.trans2.common.model.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TDialog;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.gui.table.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.master.ui.MG0460TagMasterPanel.SC;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.tag.*;

/**
 * �tⳗp�����_�C�A���O�̊��
 * 
 * @author AIS
 */
public class TTagReferenceDialog extends TDialog {

	/** �ꗗ */
	public TTable tbl;

	/** �tⳃR�[�h */
	public TTextField code;

	/** �tⳃ^�C�g�� */
	public TTextField title;

	/** �����{�^�� */
	public TButton btnSearch;

	/** �m��{�^�� */
	public TButton btnSettle;

	/** �߂�{�^�� */
	public TButton btnBack;

	/** ��Џ�� */
	public Company company;

	/** �����p�l�� */
	public TPanel pnlSearch;

	/** �{�^���p�l�� */
	public TPanel pnlButton;

	/** �R���g���[�� */
	protected TTagReferenceController controller = null;

	/**
	 * @param controller
	 */
	public TTagReferenceDialog(TTagReferenceController controller) {
		super(controller.field.getParentFrame(), true);
		this.controller = controller;

		initComponents();

		try {
			// �e�[�u���̂ݐ�ɕ���
			Class c = this.getClass();
			tbl.restoreComponent(new TDefaultStorableKey(c, c.getField("tbl")));

		} catch (Exception ex) {
			ClientErrorHandler.handledException(ex);
		}

		allocateComponents();

		setTabIndex();
		initDialog();
	}

	/**
	 * �R���|�[�l���g������
	 */
	protected void initComponents() {
		tbl = new TTable();
		tbl.getTableHeader().setReorderingAllowed(false);
		tbl.addColumn(SC.bean, "", -1);
		tbl.addColumn(SC.CODE, getWord(controller.getCodeCaption()), controller.getCodeColumnSize(),
			controller.getCodeColumnAlignment());
		tbl.addColumn(SC.COLOR, getWord(controller.getColorCaption()), controller.getColorColumnSize(),
			controller.getColorColumnAlignment());
		tbl.addColumn(SC.TITLE, getWord(controller.getTitleCaption()), controller.getTitleColumnSize(),
			controller.getTitleColumnAlignment());
		tbl.getColumnModel().getColumn(SC.COLOR.ordinal()).setCellRenderer(new TagRenderer(tbl));

		pnlSearch = new TPanel();
		pnlButton = new TPanel();
		code = new TTextField();
		title = new TTextField();
		btnSearch = new TButton();
		btnSettle = new TButton();
		btnBack = new TButton();

	}

	/**
	 * �R���|�[�l���g�z�u
	 */
	protected void allocateComponents() {

		setLayout(new GridBagLayout());
		setResizable(true);

		setPreferredSize(new Dimension(650, 520));

		setTitle(controller.getDialogTitle()); // XX�ꗗ

		// �ꗗ
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, 10, 0, 10);
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		add(tbl, gc);

		// ���������w��t�B�[���h
		pnlSearch.setLayout(null);
		pnlSearch.setMinimumSize(new Dimension(0, 20));
		pnlSearch.setPreferredSize(new Dimension(0, 20));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridy = 1;
		add(pnlSearch, gc);

		// �tⳃR�[�h����
		TTableInformation ti = tbl.getTableInformation();
		int codeLength = ti.getWidthList().get(SC.CODE.ordinal());
		code.setMinimumSize(new Dimension(codeLength, 20));
		code.setPreferredSize(new Dimension(codeLength, 20));
		code.setMaxLength(TransUtil.COMPANY_CODE_LENGTH);
		code.setImeMode(false);
		@SuppressWarnings("unused")
		int marginX = tbl.getRowColumnWidth() + 10;
		code.setSize(100, 20);
		code.setLocation(40, 0);

		pnlSearch.add(code);

		// �tⳃ^�C�g������
		int titleLength = ti.getWidthList().get(SC.TITLE.ordinal());
		title.setMinimumSize(new Dimension(titleLength, 20));
		title.setPreferredSize(new Dimension(titleLength, 20));
		title.setSize(250, 20);
		title.setLocation(250, 0);
		title.setMaxLength(80);

		pnlSearch.add(title);

		// �{�^���t�B�[���h
		pnlButton.setLayout(new GridBagLayout());
		pnlButton.setMinimumSize(new Dimension(0, 40));
		pnlButton.setPreferredSize(new Dimension(0, 40));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridy = 2;
		add(pnlButton, gc);

		// �����{�^��
		btnSearch.setPreferredSize(new Dimension(120, 25));
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		pnlButton.add(btnSearch);
		gc = new GridBagConstraints();
		pnlButton.add(btnSearch, gc);

		// �m��{�^��
		btnSettle.setPreferredSize(new Dimension(120, 25));
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		pnlButton.add(btnSettle, gc);
		tbl.addSpreadSheetSelectChange(btnSettle);

		// �߂�{�^��
		btnBack.setPreferredSize(new Dimension(120, 25));
		btnBack.setLangMessageID("C01747");
		btnBack.setShortcutKey(KeyEvent.VK_F12);
		gc = new GridBagConstraints();
		pnlButton.add(btnBack, gc);

		pack();

	}

	/**
	 * �Z�������_���i�X�V������̕tⳐF�ύX�Ή��j
	 */
	public class TagRenderer extends TBaseCellRenderer {

		/**
		 * �R���X�g���N�^
		 * 
		 * @param tbl
		 */
		public TagRenderer(TTable tbl) {
			super(tbl);
			this.setHorizontalAlignment(SwingConstants.CENTER);
		}

		@Override
		public Component getTableCellRendererComponent(JTable paramJTable, Object paramObject, boolean paramBoolean1,
			boolean paramBoolean2, int paramInt1, int paramInt2) {

			Tag hdr = getHeaderAt(paramInt1);
			Color foreC = hdr.getColor();

			Component comp = super.getTableCellRendererComponent(paramJTable, paramObject, paramBoolean1,
				paramBoolean2, paramInt1, paramInt2);
			comp.setBackground(foreC);

			return comp;
		}

		/**
		 * @param row
		 * @return compornent
		 */
		protected Tag getHeaderAt(int row) {
			return (Tag) tbl.getRowValueAt(row, SC.bean);
		}

	}

	/**
	 * Tab���Z�b�g
	 */
	protected void setTabIndex() {
		int i = 0;
		code.setTabControlNo(i++);
		title.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnBack.setTabControlNo(i++);
		tbl.setTabControlNo(i++);
	}

}
