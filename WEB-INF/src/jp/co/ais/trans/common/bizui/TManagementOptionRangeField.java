package jp.co.ais.trans.common.bizui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * 管理コンポーネント
 * 
 * @author AIS
 */
public class TManagementOptionRangeField extends TPanel implements TInterfaceLangMessageID {

	/** シリアルUID */
	private static final long serialVersionUID = 6975993877695662983L;

	/** 条件なし */
	public static final int VALUE_NONE = 0;

	/** 取引先コード */
	public static final int VALUE_CUSTOMER = 1;

	/** 社員コード */
	public static final int VALUE_EMP = 2;

	/** 管理１コード */
	public static final int VALUE_MANAGEMENT1 = 3;

	/** 管理２コード */
	public static final int VALUE_MANAGEMENT2 = 4;

	/** 管理３コード */
	public static final int VALUE_MANAGEMENT3 = 5;

	/** 管理４コード */
	public static final int VALUE_MANAGEMENT4 = 6;

	/** 管理５コード */
	public static final int VALUE_MANAGEMENT5 = 7;

	/** 管理６コード */
	public static final int VALUE_MANAGEMENT6 = 8;

	/**
	 * コンストラクタ
	 */
	public TManagementOptionRangeField() {

		super();

		initComponents();

		new TManagementOptionRangeFieldCtrl(this);
	}

	/**
	 * 画面構築
	 */
	protected void initComponents() {

		GridBagConstraints gridBagConstraints;

		basePanel = new TPanel();
		cmbManagement = new TComboBox();
		ctrlBeginManagement = new TManagementEnhancingField();
		ctrlEndManagement = new TManagementEnhancingField();

		// 開始コード
		ctrlBeginManagement.setLangMessageID("C01012");
		// 終了コード
		ctrlEndManagement.setLangMessageID("C01143");

		ctrlBeginManagement.addCallControl(new CallBackListener() {

			public void before() {
				// 条件なし ボタンロック
				if ((cmbManagement.getSelectedItemValue()).equals(VALUE_NONE)) {
					setBtnLock();
				}
				// 取引先
				else if ((cmbManagement.getSelectedItemValue()).equals(VALUE_CUSTOMER)) {
					ctrlBeginManagement.setManagementType(TManagementEnhancingField.TYPE_CUSTOMER);
				}
				// 社員
				else if ((cmbManagement.getSelectedItemValue()).equals(VALUE_EMP)) {
					ctrlBeginManagement.setManagementType(TManagementEnhancingField.TYPE_EMP);
				}
				// 管理コード１～６選択時
				else {
					switch (Util.avoidNullAsInt(cmbManagement.getSelectedItemValue())) {
						case VALUE_MANAGEMENT1:
							ctrlBeginManagement.setManagementType(TManagementField.TYPE_MANAGEMENT1);
							break;
						case VALUE_MANAGEMENT2:
							ctrlBeginManagement.setManagementType(TManagementField.TYPE_MANAGEMENT2);
							break;
						case VALUE_MANAGEMENT3:
							ctrlBeginManagement.setManagementType(TManagementField.TYPE_MANAGEMENT3);
							break;
						case VALUE_MANAGEMENT4:
							ctrlBeginManagement.setManagementType(TManagementField.TYPE_MANAGEMENT4);
							break;
						case VALUE_MANAGEMENT5:
							ctrlBeginManagement.setManagementType(TManagementField.TYPE_MANAGEMENT5);
							break;
						case VALUE_MANAGEMENT6:
							ctrlBeginManagement.setManagementType(TManagementField.TYPE_MANAGEMENT6);
							break;
					}
				}
			}

			public void after() {
				ctrlBeginManagement.setBeginCode("");
				ctrlBeginManagement.setEndCode(ctrlEndManagement.getValue());
				ctrlEndManagement.setBeginCode(ctrlBeginManagement.getValue());
				ctrlEndManagement.setEndCode("");
			}

		});

		ctrlEndManagement.addCallControl(new CallBackListener() {

			public void before() {
				if ((cmbManagement.getSelectedItemValue()).equals(VALUE_NONE)) {
					setBtnLock();
				}
				// 取引先
				else if ((cmbManagement.getSelectedItemValue()).equals(VALUE_CUSTOMER)) {
					ctrlEndManagement.setManagementType(TManagementEnhancingField.TYPE_CUSTOMER);
				}
				// 社員
				else if ((cmbManagement.getSelectedItemValue()).equals(VALUE_EMP)) {
					ctrlEndManagement.setManagementType(TManagementEnhancingField.TYPE_EMP);
				}
				// 管理コード１～６選択時
				else {
					switch (Util.avoidNullAsInt(cmbManagement.getSelectedItemValue())) {
						case VALUE_MANAGEMENT1:
							ctrlEndManagement.setManagementType(TManagementField.TYPE_MANAGEMENT1);
							break;
						case VALUE_MANAGEMENT2:
							ctrlEndManagement.setManagementType(TManagementField.TYPE_MANAGEMENT2);
							break;
						case VALUE_MANAGEMENT3:
							ctrlEndManagement.setManagementType(TManagementField.TYPE_MANAGEMENT3);
							break;
						case VALUE_MANAGEMENT4:
							ctrlEndManagement.setManagementType(TManagementField.TYPE_MANAGEMENT4);
							break;
						case VALUE_MANAGEMENT5:
							ctrlEndManagement.setManagementType(TManagementField.TYPE_MANAGEMENT5);
							break;
						case VALUE_MANAGEMENT6:
							ctrlEndManagement.setManagementType(TManagementField.TYPE_MANAGEMENT6);
							break;
					}
				}
			}

			public void after() {
				ctrlBeginManagement.setBeginCode("");
				ctrlBeginManagement.setEndCode(ctrlEndManagement.getValue());
				ctrlEndManagement.setBeginCode(ctrlBeginManagement.getValue());
				ctrlEndManagement.setEndCode("");
			}
		});

		setLayout(new GridBagLayout());

		basePanel.setLayout(new GridBagLayout());
		gridBagConstraints = new GridBagConstraints();
		add(basePanel, new GridBagConstraints());

		// 初期値設定
		setBtnLock();
		cmbManagement.setModel(new DefaultComboBoxModel(new String[] { " " }));
		cmbManagement.setMaximumSize(new Dimension(110, 20));
		cmbManagement.setMinimumSize(new Dimension(110, 20));
		cmbManagement.setPreferredSize(new Dimension(110, 20));
		// 管理を変更する
		cmbManagement.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1) {
					if (cmbManagement.getSelectedIndex() == 0) {
						ctrlBeginManagement.setEditMode(false);
						ctrlEndManagement.setEditMode(false);
					} else {
						ctrlBeginManagement.setEditMode(true);
						ctrlEndManagement.setEditMode(true);
					}
				}
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		basePanel.add(cmbManagement, gridBagConstraints);

		ctrlBeginManagement.setButtonSize(85);
		ctrlBeginManagement.setFieldSize(75);
		ctrlBeginManagement.setMaxLength(10);
		ctrlBeginManagement.setImeMode(false);
		ctrlBeginManagement.setNoticeSize(135);
		ctrlBeginManagement.setTabEnabled(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		basePanel.add(ctrlBeginManagement, gridBagConstraints);

		ctrlEndManagement.setButtonSize(85);
		ctrlEndManagement.setFieldSize(75);
		ctrlEndManagement.setMaxLength(10);
		ctrlEndManagement.setImeMode(false);
		ctrlEndManagement.setNoticeSize(135);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		basePanel.add(ctrlEndManagement, gridBagConstraints);

	}

	/**
	 * 一括入力フィールドサイズ設定
	 * 
	 * @param size サイズ
	 */
	public void setFieldSize(int size) {
		ctrlBeginManagement.setFieldSize(size);
		ctrlEndManagement.setFieldSize(size);
	}

	/**
	 * 一括名称フィールドサイズ設定
	 * 
	 * @param size サイズ
	 */
	public void setNoticeSize(int size) {
		ctrlBeginManagement.setNoticeSize(size);
		ctrlEndManagement.setNoticeSize(size);
	}

	/**
	 * 「管理コンボボックス」コンポーネントを返す
	 * 
	 * @return 管理コンボボックス
	 */
	public TComboBox getCmbManagement() {
		return this.cmbManagement;
	}

	/**
	 * 「開始管理フィールド」コンポーネントを返す
	 * 
	 * @return 開始管理フィールド
	 */
	public TManagementField getBeginTManagementField() {
		return this.ctrlBeginManagement;
	}

	/**
	 * 「終了管理フィールド」コンポーネントを返す
	 * 
	 * @return 終了管理フィールド
	 */
	public TManagementField getEndTManagementField() {
		return this.ctrlEndManagement;
	}

	/**
	 * 「パネル」コンポーネントを返す
	 * 
	 * @return パネル
	 */
	public TPanel getTBasePanel() {
		return this.basePanel;
	}

	/**
	 * 管理コンボボックスからインデックス値を取得
	 * 
	 * @return 選択行のインデックス
	 */
	public int getSelectIndex() {
		return cmbManagement.getSelectedIndex();
	}

	/**
	 * 開始管理フィールドから値を取得
	 * 
	 * @return 開始管理コード
	 */
	public String getBeginManagementCode() {
		return ctrlBeginManagement.getField().getText();
	}

	/**
	 * 終了管理コードフィールドから値を取得
	 * 
	 * @return 終了管理コード
	 */
	public String getEndManagementCode() {
		return ctrlEndManagement.getField().getText();
	}

	/**
	 * パネル全体 タブ移動順番号を設定する.
	 * 
	 * @param no
	 * @see TInterfaceTabControl#setTabControlNo(int)
	 */
	public void setTabControlNo(int no) {
		cmbManagement.setTabControlNo(no);
		ctrlBeginManagement.setTabControlNo(no);
		ctrlEndManagement.setTabControlNo(no);
	}

	/**
	 * コードの存在のチェックを行わないモードへ変更
	 */
	public void setNonCheckMode() {
		ctrlBeginManagement.setChekcMode(false);
		ctrlEndManagement.setChekcMode(false);
	}

	/**
	 * ボタン 押下不可
	 */
	public void setBtnLock() {
		ctrlBeginManagement.getButton().setEnabled(false);
		ctrlBeginManagement.getField().setEditable(false);
		ctrlEndManagement.getButton().setEnabled(false);
		ctrlEndManagement.getField().setEditable(false);
	}

	/**
	 * ボタン 押下可
	 */
	public void setBtnUnLock() {
		ctrlBeginManagement.getButton().setEnabled(true);
		ctrlBeginManagement.getField().setEditable(true);
		ctrlEndManagement.getButton().setEnabled(true);
		ctrlEndManagement.getField().setEditable(true);
	}

	/**
	 * パネル全体 押下不可
	 */
	public void setLock() {
		cmbManagement.setEnabled(false);
		ctrlBeginManagement.getButton().setEnabled(false);
		ctrlBeginManagement.getField().setEditable(false);
		ctrlEndManagement.getButton().setEnabled(false);
		ctrlEndManagement.getField().setEditable(false);
	}

	/**
	 * パネル全体 押下可
	 */
	public void setUnLock() {
		cmbManagement.setEnabled(true);
		ctrlBeginManagement.getButton().setEnabled(true);
		ctrlBeginManagement.getField().setEditable(true);
		ctrlEndManagement.getButton().setEnabled(true);
		ctrlEndManagement.getField().setEditable(true);
	}

	protected TComboBox cmbManagement;

	protected TManagementEnhancingField ctrlBeginManagement;

	protected TManagementEnhancingField ctrlEndManagement;

	protected TPanel basePanel;

	/**
	 * 選択された集計単位を返す。
	 * return 選択された集計単位
	 */
	public TransUtil.SumGroup getSelectedSumGroup() {

		int managementType = Integer.parseInt(cmbManagement.getSelectedItemValue().toString());
		// 条件無し
		if (VALUE_NONE == managementType) {
			return TransUtil.SumGroup.None;
		}
		// 取引先コード
		else if (VALUE_CUSTOMER == managementType) {
			return TransUtil.SumGroup.Tri;
		}
		// 社員コード
		else if (VALUE_EMP == managementType) {
			return TransUtil.SumGroup.Emp;
		}
		// 管理1コード
		else if (VALUE_MANAGEMENT1 == managementType) {
			return TransUtil.SumGroup.Knr1;
		}
		// 管理2コード
		else if (VALUE_MANAGEMENT2 == managementType) {
			return TransUtil.SumGroup.Knr2;
		}
		// 管理3コード
		else if (VALUE_MANAGEMENT3 == managementType) {
			return TransUtil.SumGroup.Knr3;
		}
		// 管理4コード
		else if (VALUE_MANAGEMENT4 == managementType) {
			return TransUtil.SumGroup.Knr4;
		}
		// 管理5コード
		else if (VALUE_MANAGEMENT5 == managementType) {
			return TransUtil.SumGroup.Knr5;
		}
		// 管理6コード
		else if (VALUE_MANAGEMENT6 == managementType) {
			return TransUtil.SumGroup.Knr6;
		}

		return null;
		
	}

}
