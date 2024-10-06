package jp.co.ais.trans.common.bizui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans2.common.gui.*;

/**
 * 組織コンポーネント
 */
public class TOrganizationUnit extends TTitlePanel {

	/** シリアルUID */
	private static final long serialVersionUID = -3240004347898566637L;

	/** 会社タイプ */
	public static final int TYPE_COMPANY = 1;

	/** 部門タイプ */
	public static final int TYPE_DEPARTMENT = 2;

	/** 連結部門タイプ */
	public static final int TYPE_CONSOLIDATED_DEPARTMENT = 2;

	/** コントロールクラス */
	private TOrganizationUnitCtrl ctrl;

	/** 出力単位パネル */
	protected TPanel pnlOutputUnit;

	/** 組織コード */
	protected TLabelComboBox ctrlOrganizationCode;

	/** 階層レベル */
	protected TLabelComboBox ctrlHierarchicalLevel;

	/** 上位コード */
	protected TButtonField ctrlUpperCode;

	/** コード */
	protected TButtonField ctrlCode;

	/** 配下パネルラジオボタン（含む） */
	protected TRadioButton rdoInclude;

	/** 配下パネルラジオボタン（含まない） */
	protected TRadioButton rdoExclude;

	/** 配下パネルラジオボタングループ */
	protected ButtonGroup btngrpSubordinateSection;

	/** 配下パネル */
	protected TPanel pnlSubordinate;

	/**
	 * コンストラクタ
	 */
	public TOrganizationUnit() {
		this(TYPE_DEPARTMENT);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type コントロールタイプ
	 */
	public TOrganizationUnit(int type) {
		super();

		initComponents();

		// TODO 会社、連結部門も作る？
		switch (type) {
			// 部門タイプ
			case TYPE_DEPARTMENT:

				this.ctrl = new DepartmentUnitCtrl(this);
				this.ctrl.initLangMessageID();
				this.ctrl.initPanel();

				break;
			// 会社タイプ
			case TYPE_COMPANY:

				this.ctrl = new CompanyUnitCtrl(this);
				this.ctrl.initLangMessageID();
				this.ctrl.initPanel();

				break;

			default:
				return;
		}
	}

	/**
	 * 画面構築
	 */
	private void initComponents() {
		GridBagConstraints gc;

		pnlOutputUnit = new TPanel();
		ctrlOrganizationCode = new TLabelComboBox();
		ctrlHierarchicalLevel = new TLabelComboBox();
		ctrlUpperCode = new TButtonField();
		ctrlCode = new TButtonField();
		rdoInclude = new TRadioButton();
		rdoExclude = new TRadioButton();
		btngrpSubordinateSection = new ButtonGroup();
		pnlSubordinate = new TPanel();

		// 出力単位
		pnlOutputUnit.setLayout(new GridBagLayout());
		TGuiUtil.setComponentSize(pnlOutputUnit, new Dimension(440, 125));
		pnlOutputUnit.setLocation(0, 0);
		add(pnlOutputUnit);

		// 組織コード
		ctrlOrganizationCode.setComboSize(90);
		ctrlOrganizationCode.setLabelSize(60);
		ctrlOrganizationCode.getComboBox().addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent evt) {
				if (ItemEvent.SELECTED == evt.getStateChange()) {
					ctrl.changeOrgCode();
				}
			}
		});

		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = new Insets(2, 6, 2, 0);// 52-0
		pnlOutputUnit.add(ctrlOrganizationCode, gc);

		// 階層ﾚﾍﾞﾙ
		ctrlHierarchicalLevel.setComboSize(75);
		ctrlHierarchicalLevel.setLabelSize(60);
		ctrlHierarchicalLevel.getComboBox().addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent evt) {
				if (ItemEvent.SELECTED == evt.getStateChange()) {
					ctrl.changeHierarchicalLevelItem();
				}
			}
		});

		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 1;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = new Insets(0, 6, 3, 0);// 60-0
		pnlOutputUnit.add(ctrlHierarchicalLevel, gc);

		// ラジオボタン(含む)
		btngrpSubordinateSection.add(rdoInclude);
		rdoInclude.setSelected(true);
		rdoInclude.setSize(65, 20);
		rdoInclude.setLocation(10, 20);
		rdoInclude.setOpaque(false);
		pnlSubordinate.add(rdoInclude, gc);

		// ラジオボタン（含まない）
		btngrpSubordinateSection.add(rdoExclude);
		rdoExclude.setSize(80, 20);
		rdoExclude.setLocation(75, 20);
		rdoExclude.setOpaque(false);
		pnlSubordinate.add(rdoExclude, gc);

		// 配下パネル
		pnlSubordinate.setLayout(null);
		TGuiUtil.setComponentSize(pnlSubordinate, new Dimension(200, 50));
		gc = new GridBagConstraints();
		gc.gridx = 1;
		gc.gridy = 0;
		gc.gridheight = 2;
		gc.insets = new Insets(0, 50, 3, 0);
		pnlOutputUnit.add(pnlSubordinate, gc);

		// 上位コード
		ctrlUpperCode.setButtonSize(85);
		ctrlUpperCode.setFieldSize(120);
		ctrlUpperCode.setMaxLength(10);
		ctrlUpperCode.setNoticeSize(200);
		ctrlUpperCode.setImeMode(false);
		ctrlUpperCode.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				if (!ctrlUpperCode.isValueChanged()) {
					return true;
				}
				if (!ctrlUpperCode.getField().isEditable()) {
					return true;
				}

				boolean b = ctrl.setupName(ctrlUpperCode, ctrl.getUpperOrgFlag());

				ctrl.changeUpper();

				return b;
			}
		});

		ctrlUpperCode.addButtonActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				ctrl.showRefDialog(ctrlUpperCode, ctrl.getUpperOrgFlag());

				if (ctrlUpperCode.getField().isValueChanged()) {
					ctrl.changeUpper();
				}
			}
		});

		// デフォルト設定
		ctrlUpperCode.getButton().setEnabled(false);
		ctrlUpperCode.getField().setEditable(false);

		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 2;
		gc.gridwidth = 3;
		gc.insets = new Insets(0, 0, 5, 0);
		pnlOutputUnit.add(ctrlUpperCode, gc);

		// コード
		ctrlCode.setButtonSize(85);
		ctrlCode.setFieldSize(120);
		ctrlCode.setMaxLength(10);
		ctrlCode.setNoticeSize(200);
		ctrlCode.setImeMode(false);
		ctrlCode.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				if (!ctrlCode.isValueChanged()) {
					return true;
				}
				if (!ctrlCode.getField().isEditable()) {
					return true;
				}

				return ctrl.setupName(ctrlCode, ctrl.getOrgFlag());
			}
		});

		ctrlCode.getButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				ctrl.showRefDialog(ctrlCode, ctrl.getOrgFlag());
			}
		});
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 3;
		gc.gridwidth = 2;
		gc.insets = new Insets(0, 0, 16, 0);
		pnlOutputUnit.add(ctrlCode, gc);
	}

	/**
	 * サイズをセット(Sサイズ)
	 */
	public void setSizeSmall() {
		this.setMaximumSize(new Dimension(350, 130));
		this.setMinimumSize(new Dimension(350, 130));
		this.setPreferredSize(new Dimension(350, 130));

		GridBagConstraints gridBagConstraints;

		pnlOutputUnit.setLayout(new GridBagLayout());
		TGuiUtil.setComponentSize(pnlOutputUnit, new Dimension(350, 125));
		pnlOutputUnit.setLocation(0, 0);

		// 組織コード
		ctrlOrganizationCode.setComboSize(73);
		ctrlOrganizationCode.setLabelSize(65);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(6, 2, 2, 0);
		pnlOutputUnit.add(ctrlOrganizationCode, gridBagConstraints);

		// 階層レベル
		ctrlHierarchicalLevel.setComboSize(73);
		ctrlHierarchicalLevel.setLabelSize(65);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(0, 2, 0, 0);
		pnlOutputUnit.add(ctrlHierarchicalLevel, gridBagConstraints);

		// 配下部門選択
		TGuiUtil.setComponentSize(pnlSubordinate, new Dimension(170, 50));

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.insets = new Insets(2, 2, 3, 0);
		pnlOutputUnit.add(pnlSubordinate, gridBagConstraints);

		// 上位部門
		ctrlUpperCode.setButtonSize(85);
		ctrlUpperCode.setFieldSize(85);
		ctrlUpperCode.setNoticeSize(125);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(2, 0, 5, 0);
		pnlOutputUnit.add(ctrlUpperCode, gridBagConstraints);

		// 部門
		ctrlCode.setButtonSize(85);
		ctrlCode.setFieldSize(85);
		ctrlCode.setNoticeSize(125);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(0, 0, 16, 0);
		pnlOutputUnit.add(ctrlCode, gridBagConstraints);
	}

	/**
	 * サイズをセット(Mサイズ)
	 */
	public void setSizeMiddle() {
		this.setMaximumSize(new Dimension(385, 130));
		this.setMinimumSize(new Dimension(385, 130));
		this.setPreferredSize(new Dimension(385, 130));

		GridBagConstraints gridBagConstraints;

		pnlOutputUnit.setLayout(new GridBagLayout());
		TGuiUtil.setComponentSize(pnlOutputUnit, new Dimension(385, 125));
		pnlOutputUnit.setLocation(0, 0);

		// 組織コード
		ctrlOrganizationCode.setComboSize(90);
		ctrlOrganizationCode.setLabelSize(60);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(4, 27, 1, 0);
		pnlOutputUnit.add(ctrlOrganizationCode, gridBagConstraints);

		// 階層コード
		ctrlHierarchicalLevel.setComboSize(75);
		ctrlHierarchicalLevel.setLabelSize(60);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(3, 27, 4, 10);
		pnlOutputUnit.add(ctrlHierarchicalLevel, gridBagConstraints);

		// 配下部門選択
		TGuiUtil.setComponentSize(pnlSubordinate, new Dimension(170, 50));

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.insets = new Insets(0, 6, 3, 0);
		pnlOutputUnit.add(pnlSubordinate, gridBagConstraints);

		// 上位部門
		ctrlUpperCode.setButtonSize(85);
		ctrlUpperCode.setFieldSize(85);
		ctrlUpperCode.setMaxLength(10);
		ctrlUpperCode.setNoticeSize(180);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(0, 0, 5, 0);
		pnlOutputUnit.add(ctrlUpperCode, gridBagConstraints);

		// 部門
		ctrlCode.setButtonSize(85);
		ctrlCode.setFieldSize(85);
		ctrlCode.setMaxLength(10);
		ctrlCode.setNoticeSize(180);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(0, 0, 16, 0);
		pnlOutputUnit.add(ctrlCode, gridBagConstraints);

	}

	/**
	 * サイズをセット(Lサイズ)
	 */
	public void setSizeLarge() {
		this.setMaximumSize(new Dimension(440, 125));
		this.setMinimumSize(new Dimension(440, 125));
		this.setPreferredSize(new Dimension(440, 125));

		GridBagConstraints gridBagConstraints;

		pnlOutputUnit.setLayout(new GridBagLayout());
		TGuiUtil.setComponentSize(pnlOutputUnit, new Dimension(440, 120));
		pnlOutputUnit.setLocation(0, 0);

		// 組織コード
		ctrlOrganizationCode.setComboSize(75);
		ctrlOrganizationCode.setLabelSize(85);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(6, 0, 2, 0);
		pnlOutputUnit.add(ctrlOrganizationCode, gridBagConstraints);

		// 階層コード
		ctrlHierarchicalLevel.setComboSize(75);
		ctrlHierarchicalLevel.setLabelSize(85);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		pnlOutputUnit.add(ctrlHierarchicalLevel, gridBagConstraints);

		// 配下部門選択
		TGuiUtil.setComponentSize(pnlSubordinate, new Dimension(170, 50));

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.insets = new Insets(2, 10, 3, 0);
		pnlOutputUnit.add(pnlSubordinate, gridBagConstraints);

		// 上位部門
		ctrlUpperCode.setButtonSize(85);
		ctrlUpperCode.setFieldSize(120);
		ctrlUpperCode.setMaxLength(10);
		ctrlUpperCode.setNoticeSize(200);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 5, 0);
		pnlOutputUnit.add(ctrlUpperCode, gridBagConstraints);

		// 部門
		ctrlCode.setButtonSize(85);
		ctrlCode.setFieldSize(120);
		ctrlCode.setMaxLength(10);
		ctrlCode.setNoticeSize(200);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 16, 0);
		pnlOutputUnit.add(ctrlCode, gridBagConstraints);
	}

	/**
	 * サイズをセット(会社用)
	 */
	public void setSizeCompany() {
		this.setMaximumSize(new Dimension(455, 135));
		this.setMinimumSize(new Dimension(455, 135));
		this.setPreferredSize(new Dimension(455, 135));

		GridBagConstraints gridBagConstraints;

		pnlOutputUnit.setMaximumSize(new Dimension(455, 130));
		pnlOutputUnit.setMinimumSize(new Dimension(455, 130));
		pnlOutputUnit.setPreferredSize(new Dimension(455, 130));

		// 組織コード
		ctrlOrganizationCode.setComboSize(75);
		ctrlOrganizationCode.setLabelSize(85);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(12, 25, 3, 0);
		pnlOutputUnit.add(ctrlOrganizationCode, gridBagConstraints);

		// 階層コード
		ctrlHierarchicalLevel.setComboSize(75);
		ctrlHierarchicalLevel.setLabelSize(85);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(0, 25, 0, 0);
		pnlOutputUnit.add(ctrlHierarchicalLevel, gridBagConstraints);

		// 配下会社選択
		TGuiUtil.setComponentSize(pnlSubordinate, new Dimension(160, 50));

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.insets = new Insets(2, 10, 3, 0);
		pnlOutputUnit.add(pnlSubordinate, gridBagConstraints);

		// 上位会社
		ctrlUpperCode.setButtonSize(110);
		ctrlUpperCode.setFieldSize(120);
		ctrlUpperCode.setMaxLength(10);
		ctrlUpperCode.setNoticeSize(190);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(2, 0, 3, 0);
		pnlOutputUnit.add(ctrlUpperCode, gridBagConstraints);

		// 会社
		ctrlCode.setButtonSize(110);
		ctrlCode.setFieldSize(120);
		ctrlCode.setMaxLength(10);
		ctrlCode.setNoticeSize(190);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 16, 0);
		pnlOutputUnit.add(ctrlCode, gridBagConstraints);
	}

	/**
	 * タブ移動順番号を組織コンポーネント全体に設定する.
	 * 
	 * @param no
	 * @see TInterfaceTabControl#setTabControlNo(int)
	 */
	public void setTabControlNo(int no) {
		ctrlOrganizationCode.setTabControlNo(no);
		ctrlHierarchicalLevel.setTabControlNo(no);
		rdoInclude.setTabControlNo(no);
		rdoExclude.setTabControlNo(no);
		ctrlUpperCode.setTabControlNo(no);
		ctrlCode.setTabControlNo(no);
	}

	/**
	 * 科目体系コードのセット
	 * 
	 * @param code 科目体系コード
	 */
	public void setItemSystemCode(String code) {
		ctrl.setItemSystemCode(code);
	}

	/**
	 * 出力単位パネル取得
	 * 
	 * @return 出力単位パネル
	 */
	public TPanel getOutputUnitPanel() {
		return this;
	}

	/**
	 * 組織コード取得
	 * 
	 * @return 組織コード
	 */
	public TLabelComboBox getOrganizationComboBox() {
		return ctrlOrganizationCode;
	}

	/**
	 * 階層レベル取得
	 * 
	 * @return 階層レベル
	 */
	public TLabelComboBox getHierarchicalLevelComboBox() {
		return ctrlHierarchicalLevel;
	}

	/**
	 * 上位コード取得
	 * 
	 * @return 上位コード
	 */
	public TButtonField getUpperCodeField() {
		return ctrlUpperCode;
	}

	/**
	 * コード取得
	 * 
	 * @return コード
	 */
	public TButtonField getCodeField() {
		return ctrlCode;
	}

	/**
	 * 配下パネル取得
	 * 
	 * @return 部門パネル
	 */
	public TPanel getSubordinate() {
		return pnlSubordinate;
	}

	/**
	 * 配下ラジオボタン（含む）取得
	 * 
	 * @return ラジオボタン（含む）
	 */
	public TRadioButton getRdoInclude() {
		return rdoInclude;
	}

	/**
	 * 配下ラジオボタン（含まない)取得
	 * 
	 * @return ラジオボタン（含まない）
	 */
	public TRadioButton getRdoExclude() {
		return rdoExclude;
	}

	/**
	 * 組織コード取得
	 * 
	 * @return 組織コード
	 */
	public String getOrganizationCode() {
		return (String) ctrlOrganizationCode.getComboBox().getSelectedItemValue();
	}

	/**
	 * 階層レベル取得
	 * 
	 * @return 階層レベル
	 */
	public int getHierarchicalLevel() {
		return ctrlHierarchicalLevel.getComboBox().getSelectedIndex();
	}

	/**
	 * 上位コード取得
	 * 
	 * @return 上位コード
	 */
	public String getUpperCode() {
		return ctrlUpperCode.getValue();
	}

	/**
	 * コード取得
	 * 
	 * @return コード
	 */
	public String getCode() {
		return ctrlCode.getValue();
	}

	/**
	 * 配下ラジオボタン「含む」を選択しているかどうか
	 * 
	 * @return true:「含む」を選択、false:「含まない」を選択
	 */
	public boolean isIncludeSelected() {
		return rdoInclude.isSelected();
	}

	/**
	 * 財務諸表に対する開示制限を返します。<br>
	 * 組織、上位部門、部門、配下部門を含む/含まない、階層レベルをまとめて返します。
	 * 
	 * @return 財務諸表に対する開示制限
	 */
	public DisclosureRegulationOfFinancialStatement getDisclosureRegulationOfFinancialStatement() {

		DisclosureRegulationOfFinancialStatement drfs = new DisclosureRegulationOfFinancialStatement();
		drfs.setOrganizationCode(getOrganizationCode());
		drfs.setDepartmentLevel(getHierarchicalLevel());
		drfs.setUpperDepartmentCode(getUpperCode());
		drfs.setDepartmentCode(getCode());
		drfs.setIncludeLowerDepartment(isIncludeSelected());

		return drfs;
	}

}
