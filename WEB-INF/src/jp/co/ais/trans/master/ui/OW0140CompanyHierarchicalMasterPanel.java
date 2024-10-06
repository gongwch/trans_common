package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TTable;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * 会社階層マスタパネル
 * 
 * @author chchcj
 */
public class OW0140CompanyHierarchicalMasterPanel extends jp.co.ais.trans.common.gui.TPanelBusiness {

	/** UID */
	private static final long serialVersionUID = 5179986878512874397L;

	/** SSヘッダ */
	private String[] clabel1;

	private String[] clabel2;

	/** コントロールクラス */
	private OW0140CompanyHierarchicalMasterPanelCtrl ctrl;

	/** スプレッドシートDataSource1 */
	JCVectorDataSource ds1;

	/** スプレッドシートDataSource2 */
	JCVectorDataSource ds2;

	protected boolean isItemSetFlag;

	/**
	 * コンストラクタ
	 * 
	 * @param ctrl コントロール
	 */
	public OW0140CompanyHierarchicalMasterPanel(OW0140CompanyHierarchicalMasterPanelCtrl ctrl) {
		this.ctrl = ctrl;

		initComponents();

		super.initPanel();
	}

	/**
	 * 画面構築
	 */
	private void initComponents() {
		GridBagConstraints gridBagConstraints;

		pnlButton = new TMainHeaderPanel();
		btnNew = new jp.co.ais.trans2.common.gui.TImageButton(IconType.NEW);
		btnDelete = new jp.co.ais.trans2.common.gui.TImageButton(IconType.DELETE);
		btnExcel = new jp.co.ais.trans2.common.gui.TImageButton(IconType.EXCEL);
		btnSettle = new jp.co.ais.trans2.common.gui.TImageButton(IconType.SETTLE);
		pnlHeader = new TPanel();
		ctrlOrganizationCode = new TLabelComboBox();
		ctrlLevel0 = new TLabelField();
		txtCompanyName = new TTextField();
		btnUpperCompany = new TButton();
		btnLowerCompany = new TButton();
		pnlDetial = new TPanel();
		pnlJournal1 = new TPanel();
		ssJournal1 = new TTable();
		pnlJournal2 = new TPanel();
		ssJournal2 = new TTable();
		pnlJournal3 = new TPanel();
		btnCompanyAdd = new TButton();
		btnCompanyCancellation = new TButton();

		setLayout(new GridBagLayout());
		setMaximumSize(new Dimension(800, 600));
		setMinimumSize(new Dimension(800, 600));
		setPreferredSize(new Dimension(800, 600));

		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(800, 40));
		pnlButton.setMinimumSize(new Dimension(800, 40));
		pnlButton.setPreferredSize(new Dimension(800, 40));

		btnNew.setLangMessageID("C00303");
		btnNew.setShortcutKey(KeyEvent.VK_F1);
		btnNew.setMaximumSize(new Dimension(110, 25));
		btnNew.setMinimumSize(new Dimension(110, 25));
		btnNew.setPreferredSize(new Dimension(110, 25));
		btnNew.setTabControlNo(6);
		btnNew.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				btnNewActionPerformed();
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 0, 5, 0);
		pnlButton.add(btnNew, gridBagConstraints);

		btnDelete.setLangMessageID("C01544");
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
		btnDelete.setMaximumSize(new Dimension(110, 25));
		btnDelete.setMinimumSize(new Dimension(110, 25));
		btnDelete.setPreferredSize(new Dimension(110, 25));
		btnDelete.setTabControlNo(7);
		btnDelete.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				btnDeleteActionPerformed();
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnDelete, gridBagConstraints);

		btnExcel.setLangMessageID("C01545");
		btnExcel.setShortcutKey(KeyEvent.VK_F6);
		btnExcel.setMaximumSize(new Dimension(120, 25));
		btnExcel.setMinimumSize(new Dimension(120, 25));
		btnExcel.setPreferredSize(new Dimension(120, 25));
		btnExcel.setTabControlNo(8);
		btnExcel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				btnListOutputActionPerformed();
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnExcel, gridBagConstraints);

		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setMaximumSize(new Dimension(110, 25));
		btnSettle.setMinimumSize(new Dimension(110, 25));
		btnSettle.setPreferredSize(new Dimension(110, 25));
		btnSettle.setTabControlNo(9);
		btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				btnSettleActionPerformed();
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 300, 5, 0);
		pnlButton.add(btnSettle, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		add(pnlButton, gridBagConstraints);

		// 境界線
		JSeparator sep = new JSeparator();
		TGuiUtil.setComponentSize(sep, 0, 3);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridy = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		add(sep, gridBagConstraints);

		pnlHeader.setLayout(new GridBagLayout());

		pnlHeader.setMaximumSize(new Dimension(800, 60));
		pnlHeader.setMinimumSize(new Dimension(800, 60));
		pnlHeader.setPreferredSize(new Dimension(800, 60));
		ctrlOrganizationCode.setComboSize(80);
		ctrlOrganizationCode.setLabelSize(60);
		ctrlOrganizationCode.setLangMessageID("C00335");
		ctrlOrganizationCode.setTabControlNo(1);

		(ctrlOrganizationCode.getComboBox()).addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent evt) {
				ctrlOrganizationCodeItemListener(evt);
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.anchor = GridBagConstraints.SOUTHWEST;
		gridBagConstraints.insets = new Insets(5, 1, 5, 0);
		pnlHeader.add(ctrlOrganizationCode, gridBagConstraints);

		ctrlLevel0.setEditable(false);
		ctrlLevel0.setEnabled(true);
		ctrlLevel0.setFieldSize(120);
		ctrlLevel0.setLabelSize(50);
		ctrlLevel0.setLangMessageID("C00722");
		ctrlLevel0.setName("C00722");
		ctrlLevel0.setMaxLength(10);
		ctrlLevel0.setTabControlNo(2);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 11, 10, 0);
		pnlHeader.add(ctrlLevel0, gridBagConstraints);

		txtCompanyName.setEnabled(true);
		txtCompanyName.setEditable(false);
		txtCompanyName.setMaxLength(40);
		txtCompanyName.setMaximumSize(new Dimension(410, 20));
		txtCompanyName.setMinimumSize(new Dimension(410, 20));
		txtCompanyName.setPreferredSize(new Dimension(410, 20));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 10, 0);
		pnlHeader.add(txtCompanyName, gridBagConstraints);

		btnUpperCompany.setLangMessageID("C01487");
		btnUpperCompany.setShortcutKey(KeyEvent.VK_F7);
		btnUpperCompany.setMaximumSize(new Dimension(110, 25));
		btnUpperCompany.setMinimumSize(new Dimension(110, 25));
		btnUpperCompany.setPreferredSize(new Dimension(110, 25));
		btnUpperCompany.setTabControlNo(10);
		btnUpperCompany.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				btnUpperCompanyActionPerformed();
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 350, 5, 0);
		pnlHeader.add(btnUpperCompany, gridBagConstraints);

		btnLowerCompany.setLangMessageID("C01488");
		btnLowerCompany.setShortcutKey(KeyEvent.VK_F8);
		btnLowerCompany.setMaximumSize(new Dimension(110, 25));
		btnLowerCompany.setMinimumSize(new Dimension(110, 25));
		btnLowerCompany.setPreferredSize(new Dimension(110, 25));
		btnLowerCompany.setTabControlNo(11);
		btnLowerCompany.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				btnLowerCompanyActionPerformed();
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 10, 5, 0);
		pnlHeader.add(btnLowerCompany, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		add(pnlHeader, gridBagConstraints);

		pnlDetial.setLayout(new GridBagLayout());

		pnlDetial.setMaximumSize(new Dimension(780, 500));
		pnlDetial.setMinimumSize(new Dimension(780, 500));
		pnlDetial.setPreferredSize(new Dimension(780, 500));
		pnlJournal1.setLayout(new javax.swing.BoxLayout(pnlJournal1, javax.swing.BoxLayout.X_AXIS));

		pnlJournal1.setMaximumSize(new Dimension(280, 460));
		pnlJournal1.setMinimumSize(new Dimension(280, 460));
		pnlJournal1.setPreferredSize(new Dimension(280, 460));
		ssJournal1.setTabControlNo(14);
		pnlJournal1.add(ssJournal1);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 0, 10, 0);
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		pnlDetial.add(pnlJournal1, gridBagConstraints);

		pnlJournal2.setLayout(new javax.swing.BoxLayout(pnlJournal2, javax.swing.BoxLayout.X_AXIS));

		pnlJournal2.setMaximumSize(new Dimension(365, 460));
		pnlJournal2.setMinimumSize(new Dimension(365, 460));
		pnlJournal2.setPreferredSize(new Dimension(365, 460));
		ssJournal2.setTabControlNo(15);
		pnlJournal2.add(ssJournal2);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 0, 10, 5);
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		pnlDetial.add(pnlJournal2, gridBagConstraints);

		pnlJournal3.setLayout(new GridBagLayout());

		pnlJournal3.setMaximumSize(new Dimension(115, 460));
		pnlJournal3.setMinimumSize(new Dimension(115, 460));
		pnlJournal3.setPreferredSize(new Dimension(115, 460));

		btnCompanyAdd.setLangMessageID("C10543");
		btnCompanyAdd.setShortcutKey(KeyEvent.VK_F2);
		btnCompanyAdd.setMaximumSize(new Dimension(115, 25));
		btnCompanyAdd.setMinimumSize(new Dimension(115, 25));
		btnCompanyAdd.setPreferredSize(new Dimension(115, 25));
		btnCompanyAdd.setTabControlNo(12);
		btnCompanyAdd.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				btnCompanyAddActionPerformed();
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 0, 10, 0);
		pnlJournal3.add(btnCompanyAdd, gridBagConstraints);

		btnCompanyCancellation.setLangMessageID("C10544");
		btnCompanyCancellation.setShortcutKey(KeyEvent.VK_F3);
		btnCompanyCancellation.setMaximumSize(new Dimension(115, 25));
		btnCompanyCancellation.setMinimumSize(new Dimension(115, 25));
		btnCompanyCancellation.setPreferredSize(new Dimension(115, 25));
		btnCompanyCancellation.setTabControlNo(13);
		btnCompanyCancellation.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				btnCompanyCancellationActionPerformed();
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		pnlJournal3.add(btnCompanyCancellation, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 10, 0, 10);
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 1;
		pnlDetial.add(pnlJournal3, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		add(pnlDetial, gridBagConstraints);

		clabel2 = new String[] { "C00596", "C01739", "C01751", "C01752", "C01753", "C01754", "C01755", "C01756",
				"C01757", "C01758", "C01759" };
		clabel1 = new String[] { "C00596", "C00055", "C00261", "C00686" };

		// 列非表示
		ssJournal1.setColumnHidden(1, true);
		ssJournal1.setColumnHidden(2, true);

		// セル幅
		int[] columnWidths1 = new int[] { 7, 10, 10, 15 };
		// 列、行表題のスタイル設定
		ssJournal1.initSpreadSheet(clabel1, columnWidths1);
		// セル幅
		int[] columnWidths2 = new int[] { 7, 5, 20, 20, 20, 20, 20, 20, 20, 20, 20 };
		// 列、行表題のスタイル設定
		ssJournal2.initSpreadSheet(clabel2, columnWidths2);

		// スプレッドイベントの初期化
		ssJournal1.addSpreadSheetSelectChange(btnCompanyAdd);

		// Scroll位置設定
		ssJournal1.setHorizSBPosition(JCTableEnum.POSITION_AT_SIDE);
		ssJournal1.setVertSBPosition(JCTableEnum.POSITION_AT_SIDE);

		// スプレッドイベントの初期化
		ssJournal2.addSpreadSheetSelectChange(btnCompanyCancellation);

		// Scroll位置設定
		ssJournal2.setHorizSBPosition(JCTableEnum.POSITION_AT_SIDE);
		ssJournal2.setVertSBPosition(JCTableEnum.POSITION_AT_SIDE);

		// SSデータの構築
		ds1 = new JCVectorDataSource();
		ds1.setNumColumns(4);
		ds1.setNumRows(0);

		ssJournal1.setDataSource(ds1);

		// SSデータの構築
		ds2 = new JCVectorDataSource();
		ds2.setNumColumns(11);
		ds2.setNumRows(0);

		ssJournal2.setDataSource(ds2);

	}

	private void btnLowerCompanyActionPerformed() {
		ctrl.doLowerCompany();
	}

	private void btnUpperCompanyActionPerformed() {
		ctrl.doUpperCompany();
	}

	private void btnSettleActionPerformed() {
		ctrl.settle();
	}

	private void btnCompanyCancellationActionPerformed() {
		ctrl.doCompanyCancel();
	}

	private void btnCompanyAddActionPerformed() {
		ctrl.doCompanyAdd();
	}

	private void btnListOutputActionPerformed() {
		ctrl.listOutput();
	}

	private void btnDeleteActionPerformed() {
		ctrl.delete();
	}

	private void btnNewActionPerformed() {
		ctrl.doNewOrg();
	}

	public Frame getParentFrame() {
		return super.getParentFrame();
	}

	private int ctrlOrganizationCodeItemListener(ItemEvent evt) {

		if (evt.getStateChange() == 1) {
			if (!isItemSetFlag) {
				ctrl.find();
			}
		}
		return evt.getStateChange();
	}

	protected TButton btnCompanyAdd;

	protected TButton btnCompanyCancellation;

	protected TImageButton btnDelete;

	protected TImageButton btnExcel;

	protected TButton btnLowerCompany;

	protected TImageButton btnNew;

	protected TImageButton btnSettle;

	protected TButton btnUpperCompany;

	protected TLabelField ctrlLevel0;

	protected TLabelComboBox ctrlOrganizationCode;

	protected TMainHeaderPanel pnlButton;

	protected TPanel pnlDetial;

	protected TPanel pnlHeader;

	protected TPanel pnlJournal1;

	protected TPanel pnlJournal2;

	protected TPanel pnlJournal3;

	protected TTable ssJournal1;

	protected TTable ssJournal2;

	protected TTextField txtCompanyName;
}
