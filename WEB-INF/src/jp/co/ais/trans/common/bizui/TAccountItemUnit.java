package jp.co.ais.trans.common.bizui;

import java.awt.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.info.*;

/**
 * 科目コンポーネント
 * 
 * @author ookawara
 */
public class TAccountItemUnit extends TPanel implements TInterfaceLangMessageID {

	/** 補助科目未使用モード(表示はする) */
	protected boolean subItemEnableMode = true;

	/** 内訳科目未使用モード(表示はする) */
	protected boolean breakDownItemEnableMode = true;

	/** ベースパネル */
	protected TPanel basePanel;

	/** 科目フィールド */
	protected TItemField ctrlItem;

	/** 補助科目フィールド */
	protected TSubItemField ctrlSubItem;

	/** 内訳科目フィールド */
	protected TBreakDownItemField ctrlBreakDownItem;

	/** 統合条件セット */
	private UnitInputParameter inputParameter;

	/** 統合結果セット */
	protected AccountItemOutputParameter outputParameter;

	/** 個別InputParameterリスト */
	private List<AccountItemInputParameter> paramList;

	/**
	 * コンストラクタ
	 */
	public TAccountItemUnit() {
		super();

		paramList = Collections.synchronizedList(new LinkedList<AccountItemInputParameter>());

		initComponents();

		// 初期制御
		setEnableSubItem(false);

		// 会社コントロール情報
		TCompanyInfo compInfo = TClientLoginInfo.getInstance().getCompanyInfo();

		// 科目範囲内訳科目非表示
		getTBreakDownItemField().setVisible(compInfo.isUseBreakDownItem());
	}

	/**
	 * 画面構築
	 */
	protected void initComponents() {
		GridBagConstraints gridBagConstraints;

		basePanel = new TPanel();
		ctrlItem = new TItemField();
		ctrlSubItem = new TSubItemField();
		ctrlBreakDownItem = new TBreakDownItemField();

		// 条件用Beanのセット
		paramList.add(ctrlItem.getInputParameter());
		paramList.add(ctrlSubItem.getInputParameter());
		paramList.add(ctrlBreakDownItem.getInputParameter());
		inputParameter = new UnitInputParameter(paramList);

		// 値取得用Beanの生成
		outputParameter = new AccountItemOutputParameter();

		ctrlItem.addCallControl(new CallBackListener() {

			public void after() {
				if (!subItemEnableMode) {
					return;
				}

				if (ctrlItem.getOutputParameter().isIncludeSubItem()) {
					setEnableSubItem(true);
					inputParameter.setItemCode(getItemCode());
				} else {
					setEnableSubItem(false);
				}
			}
		});

		ctrlSubItem.addCallControl(new CallBackListener() {

			public void after() {
				if (!breakDownItemEnableMode) {
					return;
				}

				// 空文字指定の場合、科目コードの再設定
				if (ctrlSubItem.isEmpty() && ctrlSubItem.isValueChanged()) {
					ctrlItem.resetValue();
					setEnableBreakDownItem(false);
					return;
				}

				if (ctrlSubItem.getOutputParameter().isIncludeBreakDownItem()) {
					setEnableBreakDownItem(true);
					inputParameter.setItemCode(getItemCode());
					inputParameter.setSubItemCode(getSubItemCode());
				} else {
					setEnableBreakDownItem(false);
				}
			}
		});

		ctrlBreakDownItem.addCallControl(new CallBackListener() {

			public void after() {

				// 空文字指定の場合、補助科目コードの再設定
				if (ctrlBreakDownItem.isEmpty() && ctrlBreakDownItem.isValueChanged()) {
					ctrlSubItem.resetValue();
				}
			}
		});

		// 値取得用BEANを設定
		ctrlItem.setOutputParameter(outputParameter);
		ctrlSubItem.setOutputParameter(outputParameter);
		ctrlBreakDownItem.setOutputParameter(outputParameter);

		setLayout(new GridBagLayout());

		basePanel.setLayout(new GridBagLayout());
		gridBagConstraints = new GridBagConstraints();
		add(basePanel, new GridBagConstraints());

		ctrlItem.setButtonSize(85);
		ctrlItem.setFieldSize(75);
		ctrlItem.setMaxLength(10);
		ctrlItem.setImeMode(false);
		ctrlItem.setNoticeSize(135);
		ctrlItem.setTabEnabled(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		basePanel.add(ctrlItem, gridBagConstraints);

		ctrlSubItem.setButtonSize(85);
		ctrlSubItem.setFieldSize(75);
		ctrlSubItem.setMaxLength(10);
		ctrlSubItem.setImeMode(false);
		ctrlSubItem.setNoticeSize(135);
		ctrlSubItem.setTabEnabled(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		basePanel.add(ctrlSubItem, gridBagConstraints);

		ctrlBreakDownItem.setButtonSize(85);
		ctrlBreakDownItem.setFieldSize(75);
		ctrlBreakDownItem.setMaxLength(10);
		ctrlBreakDownItem.setImeMode(false);
		ctrlBreakDownItem.setNoticeSize(135);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		basePanel.add(ctrlBreakDownItem, gridBagConstraints);
	}

	/**
	 * サイズをセット(Small:伝票サイズ)
	 */
	public void setSizeSmall() {
		this.setMaximumSize(new Dimension(310, 60));
		this.setMinimumSize(new Dimension(310, 60));
		this.setPreferredSize(new Dimension(310, 60));

		GridBagConstraints gridBagConstraints;

		basePanel.setLayout(new GridBagLayout());

		basePanel.setMaximumSize(new Dimension(310, 60));
		basePanel.setMinimumSize(new Dimension(310, 60));
		basePanel.setPreferredSize(new Dimension(310, 60));
		ctrlItem.setButtonSize(85);
		ctrlItem.setFieldSize(75);
		ctrlItem.setNoticeSize(135);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(0, 10, 0, 20);
		basePanel.add(ctrlItem, gridBagConstraints);

		ctrlSubItem.setButtonSize(85);
		ctrlSubItem.setFieldSize(75);
		ctrlSubItem.setNoticeSize(135);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(0, 10, 0, 20);
		basePanel.add(ctrlSubItem, gridBagConstraints);

		ctrlBreakDownItem.setButtonSize(85);
		ctrlBreakDownItem.setFieldSize(75);
		ctrlBreakDownItem.setNoticeSize(135);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(0, 10, 0, 20);
		basePanel.add(ctrlBreakDownItem, gridBagConstraints);
	}

	/**
	 * パネルサイズ設定
	 * 
	 * @param size サイズ
	 */
	public void setPanelSize(Dimension size) {

		TGuiUtil.setComponentSize(basePanel, size);
	}

	/**
	 * 一括ボタンサイズ設定
	 * 
	 * @param size サイズ
	 */
	public void setButtonSize(int size) {
		ctrlItem.setButtonSize(size);
		ctrlSubItem.setButtonSize(size);
		ctrlBreakDownItem.setButtonSize(size);
	}

	/**
	 * 一括入力フィールドサイズ設定
	 * 
	 * @param size サイズ
	 */
	public void setFieldSize(int size) {
		ctrlItem.setFieldSize(size);
		ctrlSubItem.setFieldSize(size);
		ctrlBreakDownItem.setFieldSize(size);
	}

	/**
	 * 一括名称フィールドサイズ設定
	 * 
	 * @param size サイズ
	 */
	public void setNoticeSize(int size) {
		ctrlItem.setNoticeSize(size);
		ctrlSubItem.setNoticeSize(size);
		ctrlBreakDownItem.setNoticeSize(size);
	}

	/**
	 * 「科目」コンポーネントを返す
	 * 
	 * @return 科目フィールド
	 */
	public TItemField getTItemField() {
		return this.ctrlItem;
	}

	/**
	 * 「補助科目」コンポーネントを返す
	 * 
	 * @return 補助科目フィールド
	 */
	public TSubItemField getTSubItemField() {
		return this.ctrlSubItem;
	}

	/**
	 * 「内訳科目」コンポーネントを返す
	 * 
	 * @return 内訳科目フィールド
	 */
	public TBreakDownItemField getTBreakDownItemField() {
		return this.ctrlBreakDownItem;
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
	 * 条件用BEANコンポーネントを返す
	 * 
	 * @return inputParameter 条件用BEAN
	 */
	public AccountItemInputParameter getInputParameter() {
		return this.inputParameter;
	}

	/**
	 * 値取得用BEANコンポーネントを返す
	 * 
	 * @return outputParameter 値取得用BEAN
	 */
	public AccountItemOutputParameter getOutputParameter() {
		return this.outputParameter;
	}

	/**
	 * 科目フィールドから値を取得
	 * 
	 * @return 科目コード
	 */
	public String getItemCode() {
		return ctrlItem.getField().getText();
	}

	/**
	 * 科目フィールドへ値セット.<br>
	 * 科目の有無で補助科目の入力制御を行う.
	 * 
	 * @param code 科目コード
	 */
	public void setItemCode(String code) {
		ctrlItem.setValue(code);

		ctrlSubItem.getButton().setEnabled(this.outputParameter.isIncludeSubItem());
		ctrlSubItem.getField().setEditable(this.outputParameter.isIncludeSubItem());
	}

	/**
	 * 補助科目フィールドから値を取得
	 * 
	 * @return 補助科目コード
	 */
	public String getSubItemCode() {
		return ctrlSubItem.getField().getText();
	}

	/**
	 * 補助科目フィールドへ値セット.<br>
	 * 補助科目の有無で内訳科目の入力制御を行う.
	 * 
	 * @param code 補助科目コード
	 */
	public void setSubItemCode(String code) {
		ctrlSubItem.setValue(code);

		ctrlBreakDownItem.getButton().setEnabled(this.outputParameter.isIncludeBreakDownItem());
		ctrlBreakDownItem.getField().setEditable(this.outputParameter.isIncludeBreakDownItem());
	}

	/**
	 * 内訳科目フィールドから値を取得
	 * 
	 * @return 内訳科目コード
	 */
	public String getBreakDownItemCode() {
		return ctrlBreakDownItem.getField().getText();
	}

	/**
	 * 内訳科目フィールドへ値セット.
	 * 
	 * @param code 内訳科目コード
	 */
	public void setBreakDownItemCode(String code) {

		ctrlBreakDownItem.setValue(code);
	}

	/**
	 * 値をクリアする.<br>
	 * InputParameterは、そのまま
	 */
	public void clearCode() {
		ctrlItem.clear();
	}

	/**
	 * パネル全体 タブ移動順番号を設定する.
	 * 
	 * @param no タブ順
	 * @see TInterfaceTabControl#setTabControlNo(int)
	 */
	public void setTabControlNo(int no) {
		ctrlItem.setTabControlNo(no);
		ctrlSubItem.setTabControlNo(no);
		ctrlBreakDownItem.setTabControlNo(no);
	}

	/**
	 * 科目選択時の画面制御
	 * 
	 * @param enableSubItem true:補助科目あり, false:補助科目無し
	 */
	protected void setEnableSubItem(boolean enableSubItem) {
		ctrlSubItem.getField().setText("");
		ctrlSubItem.getField().setEditable(enableSubItem);
		ctrlSubItem.getNotice().setText("");
		ctrlSubItem.getButton().setEnabled(enableSubItem);

		setEnableBreakDownItem(false);
	}

	/**
	 * 補助科目選択時の画面制御
	 * 
	 * @param enableBDItem true:内訳科目あり, false:内訳科目無し
	 */
	protected void setEnableBreakDownItem(boolean enableBDItem) {
		ctrlBreakDownItem.getField().setText("");
		ctrlBreakDownItem.getField().setEditable(enableBDItem);
		ctrlBreakDownItem.getNotice().setText("");
		ctrlBreakDownItem.getButton().setEnabled(enableBDItem);
	}

	/**
	 * コードの存在のチェックを行わないモードへ変更
	 */
	public void setNonCheckMode() {
		ctrlItem.setCheckMode(false);
		ctrlSubItem.setCheckMode(false);
		ctrlBreakDownItem.setChekcMode(false);
	}

	/**
	 * 補助科目未使用モード
	 * 
	 * @return true:使用
	 */
	public boolean isSubItemEnableMode() {
		return subItemEnableMode;
	}

	/**
	 * 補助科目未使用モード
	 * 
	 * @param subItemEnableMode true:使用
	 */
	public void setSubItemEnableMode(boolean subItemEnableMode) {
		this.subItemEnableMode = subItemEnableMode;
	}

	/**
	 * 内訳科目未使用モード
	 * 
	 * @return true:使用
	 */
	public boolean isBreakDownItemEnableMode() {
		return breakDownItemEnableMode;
	}

	/**
	 * 内訳科目未使用モード
	 * 
	 * @param breakDownItemEnableMode true:使用
	 */
	public void setBreakDownItemEnableMode(boolean breakDownItemEnableMode) {
		this.breakDownItemEnableMode = breakDownItemEnableMode;
	}

	/**
	 * 部門コードを設定する
	 * 
	 * @param depCode 部門コード
	 */
	public void setDepartmentCode(String depCode) {
		this.ctrlItem.setDepartmentCode(depCode);
	}

	/**
	 * @param isControll true:操作可能
	 * @see jp.co.ais.trans.common.gui.TButtonField#setEditMode(boolean)
	 */
	public void setEditMode(boolean isControll) {
		this.ctrlItem.setEditMode(isControll);
	}

	/**
	 * 入力可能な状態かどうか
	 * 
	 * @return true:入力可能
	 */
	public boolean isEditable() {
		return this.ctrlItem.isEditable();
	}

}
