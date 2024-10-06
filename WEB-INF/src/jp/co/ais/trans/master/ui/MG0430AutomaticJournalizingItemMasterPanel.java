package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

import jp.co.ais.trans.common.bizui.*;

/**
 * 自動仕訳科目マスタ画面コントロール
 */
public class MG0430AutomaticJournalizingItemMasterPanel extends TPanelBusiness {

	/** シリアルUID */
	private static final long serialVersionUID = -6189932794522479190L;

	/** コントロールクラス */
	private MG0430AutomaticJournalizingItemMasterPanelCtrl ctrl;

	/**
	 * コンストラクタ.
	 * 
	 * @param ctrl コントロールクラス
	 */
	MG0430AutomaticJournalizingItemMasterPanel(MG0430AutomaticJournalizingItemMasterPanelCtrl ctrl) {
		this.ctrl = ctrl;
		// 画面の初期化
		initComponents();

		// ** messageID変換, tab順登録のため、initComponents()の後に必ず呼ぶこと */
		super.initPanel();
	}

	boolean isSettle = false;

	protected void initComponents() {
		GridBagConstraints gridBagConstraints;

		pnlBusiness = new TPanel();
		pnlButton = new TMainHeaderPanel();
		btnSettle = new TImageButton(IconType.SETTLE);
		btnCancellation = new TButton();
		pnlDetail = new TPanel();
		ctrlAppropriateDepartment = new TButtonField();
		ctrlCompanyCode = new TLabelField();
		ctrlItemControlDivision = new TLabelComboBox();
		txtCompanyName = new TTextField();
		ctrlItemUnit = new TAccountItemUnit();
		pnlFooter = new TPanel();

		setLayout(new GridBagLayout());
		setMaximumSize(new Dimension(800, 600));
		setMinimumSize(new Dimension(800, 600));
		setPreferredSize(new Dimension(800, 600));

		pnlBusiness.setLayout(new GridBagLayout());

		pnlBusiness.setMaximumSize(new Dimension(800, 600));
		pnlBusiness.setMinimumSize(new Dimension(800, 600));
		pnlBusiness.setPreferredSize(new Dimension(800, 600));

		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(800, 40));
		pnlButton.setMinimumSize(new Dimension(800, 40));
		pnlButton.setPreferredSize(new Dimension(800, 40));

		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setMaximumSize(new Dimension(110, 25));
		btnSettle.setMinimumSize(new Dimension(110, 25));
		btnSettle.setPreferredSize(new Dimension(110, 25));
		btnSettle.setTabControlNo(6);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 450, 5, 0);
		pnlButton.add(btnSettle, gridBagConstraints);
		btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				isSettle = true;
				ctrl.save();
			}
		});

		btnCancellation.setLangMessageID("C00405");
		btnCancellation.setShortcutKey(KeyEvent.VK_F12);
		btnCancellation.setMaximumSize(new Dimension(110, 25));
		btnCancellation.setMinimumSize(new Dimension(110, 25));
		btnCancellation.setPreferredSize(new Dimension(110, 25));
		btnCancellation.setTabControlNo(7);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 10);
		pnlButton.add(btnCancellation, gridBagConstraints);
		btnCancellation.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				ctrl.setCode("cancel");
				ctrl.loadData();
				ctrl.setCode("");
			}
		});
		btnCancellation.setForClose(true);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		pnlBusiness.add(pnlButton, gridBagConstraints);

		pnlDetail.setLayout(new GridBagLayout());

		pnlDetail.setMaximumSize(new Dimension(600, 160));
		pnlDetail.setMinimumSize(new Dimension(600, 160));
		pnlDetail.setPreferredSize(new Dimension(600, 160));

		ctrlItemUnit.setTabControlNo(3);
		ctrlItemUnit.getTItemField().getInputParameter().setSummaryDivision("0");
		ctrlItemUnit.getTItemField().setLangMessageID("C01006");
		ctrlItemUnit.getTSubItemField().setLangMessageID("C00488");
		ctrlItemUnit.getTBreakDownItemField().setLangMessageID("C00025");
		ctrlItemUnit.getTItemField().setImeMode(false);
		ctrlItemUnit.getTSubItemField().setImeMode(false);
		ctrlItemUnit.getTBreakDownItemField().setImeMode(false);
		ctrlItemUnit.getTItemField().setMaxLength(10);
		ctrlItemUnit.getTSubItemField().setMaxLength(10);
		ctrlItemUnit.getTBreakDownItemField().setMaxLength(10);
		ctrlItemUnit.getTItemField().setButtonSize(85);
		ctrlItemUnit.getTSubItemField().setButtonSize(85);
		ctrlItemUnit.getTBreakDownItemField().setButtonSize(85);
		ctrlItemUnit.getTItemField().setFieldSize(120);
		ctrlItemUnit.getTSubItemField().setFieldSize(120);
		ctrlItemUnit.getTBreakDownItemField().setFieldSize(120);
		ctrlItemUnit.getTItemField().setNoticeSize(255);
		ctrlItemUnit.getTSubItemField().setNoticeSize(255);
		ctrlItemUnit.getTBreakDownItemField().setNoticeSize(255);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, -10, 0, 0);
		pnlDetail.add(ctrlItemUnit, gridBagConstraints);

		ctrlAppropriateDepartment.setButtonSize(85);
		ctrlAppropriateDepartment.setFieldSize(120);
		ctrlAppropriateDepartment.setLangMessageID("C00863");
		ctrlAppropriateDepartment.setMaxLength(10);
		ctrlAppropriateDepartment.setMaximumSize(new Dimension(420, 20));
		ctrlAppropriateDepartment.setMinimumSize(new Dimension(420, 20));
		ctrlAppropriateDepartment.setNoticeSize(255);
		ctrlAppropriateDepartment.setTabControlNo(2);
		ctrlAppropriateDepartment.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlAppropriateDepartment, gridBagConstraints);

		ctrlCompanyCode.setEditable(false);
		ctrlCompanyCode.setEnabled(false);
		ctrlCompanyCode.setFieldSize(120);
		ctrlCompanyCode.setLabelSize(85);
		ctrlCompanyCode.setLangMessageID("C00596");
		ctrlCompanyCode.setMaxLength(10);
		ctrlCompanyCode.setTabControlNo(-1);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlCompanyCode, gridBagConstraints);

		ctrlItemControlDivision.setComboSize(150);
		ctrlItemControlDivision.setLabelSize(85);
		ctrlItemControlDivision.setLangMessageID("C01008");
		ctrlItemControlDivision.setTabControlNo(1);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlItemControlDivision, gridBagConstraints);

		txtCompanyName.setMaximumSize(new Dimension(255, 20));
		txtCompanyName.setMinimumSize(new Dimension(255, 20));
		txtCompanyName.setPreferredSize(new Dimension(255, 20));
		txtCompanyName.setTabControlNo(-1);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(txtCompanyName, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 90, 0, 0);
		pnlBusiness.add(pnlDetail, gridBagConstraints);

		pnlFooter.setLayout(new GridBagLayout());
		pnlFooter.setMaximumSize(new Dimension(800, 400));
		pnlFooter.setMinimumSize(new Dimension(800, 400));
		pnlFooter.setPreferredSize(new Dimension(800, 400));

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		pnlBusiness.add(pnlFooter, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		add(pnlBusiness, gridBagConstraints);
	}

	/** */
	public TButton btnCancellation;

	/** */
	public TImageButton btnSettle;

	/** */
	public TButtonField ctrlAppropriateDepartment;

	/** */
	public TLabelField ctrlCompanyCode;

	/** */
	public TLabelComboBox ctrlItemControlDivision;

	/** */
	public TMainHeaderPanel pnlButton;

	/** */
	public TPanel pnlDetail;

	/** */
	public TPanel pnlBusiness;

	/** */
	public TTextField txtCompanyName;

	TAccountItemUnit ctrlItemUnit;

	TPanel pnlFooter;

}
