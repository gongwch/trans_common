package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.payment.*;

/**
 * 銀行口座検索コンポーネント
 * 
 * @author AIS
 */
public class TBankAccountReference extends TReference {

	/** コントローラ */
	protected TBankAccountReferenceController controller;

	/** 略称 */
	public TTextField accountNo;

	/** 部門コード */
	protected String deptCode;

	/** true:全SPCモード */
	protected boolean allCompanyMode = false;

	/**
	 * コンストラクタ.
	 */
	public TBankAccountReference() {
		this(false);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type タイプ
	 */
	public TBankAccountReference(TYPE type) {
		super(type);
		createController();
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param isVisibleAccountNo 口座番号を表示するかどうか
	 */
	public TBankAccountReference(boolean isVisibleAccountNo) {
		createController();
		accountNo.setVisible(isVisibleAccountNo);

		if (isVisibleAccountNo) {
			name.setMaximumSize(new Dimension(120, 20));
			name.setMinimumSize(new Dimension(120, 20));
			name.setPreferredSize(new Dimension(120, 20));
		}

		this.resize();
	}

	/**
	 * コントローラの作成
	 */
	protected void createController() {
		controller = new TBankAccountReferenceController(this);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TReference#getController()
	 */
	@Override
	protected TReferenceController getController() {
		return controller;
	}

	/**
	 * コンポーネントを初期化する<BR>
	 */
	@Override
	protected void initComponents() {

		super.initComponents();
		accountNo = new TTextField();
	}

	/**
	 * コンポーネントを配置する
	 */
	@Override
	protected void allocateComponents() {

		super.allocateComponents();

		GridBagConstraints gc = new GridBagConstraints();

		// 口座番号
		accountNo.setEditable(false);
		accountNo.setMaximumSize(new Dimension(100, 20));
		accountNo.setMinimumSize(new Dimension(100, 20));
		accountNo.setPreferredSize(new Dimension(100, 20));
		gc.insets = new Insets(0, 0, 0, 0);
		add(accountNo, gc);

		resize();
	}

	/**
	 * サイズの再反映
	 */
	@Override
	public void resize() {

		int width = (int) (btn.getPreferredSize().getWidth() + code.getPreferredSize().getWidth());
		if (name.isVisible()) {
			width += (int) name.getPreferredSize().getWidth();
		}
		if (accountNo.isVisible()) {
			width += (int) accountNo.getPreferredSize().getWidth();
		}

		setSize(width, 20);
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	public BankAccountSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	public BankAccount getEntity() {
		return controller.getEntity();
	}

	/**
	 * Entityをセットする
	 * 
	 * @param BankAccount 銀行口座
	 */
	public void setEntity(BankAccount BankAccount) {
		controller.setEntity(BankAccount);
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.
	 */
	public void refleshEntity() {
		controller.refleshEntity();
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.<br>
	 * 表示を更新する
	 */
	@Override
	public void refleshAndShowEntity() {
		controller.refleshEntity();
		controller.setEntity(getEntity());
	}

	/**
	 * 計上部門コードの条件セット アンカーカスタマイズ
	 * 
	 * @param code 銀行口座
	 */
	public void setDepCode(String code) {
		if (controller == null) {
			return;
		}
		controller.setDepCode(code);
	}

	/**
	 * true:全SPCモードの取得
	 * 
	 * @return allCompanyMode true:全SPCモード
	 */
	public boolean isAllCompanyMode() {
		return allCompanyMode;
	}

	/**
	 * true:全SPCモードの設定
	 * 
	 * @param allCompanyMode true:全SPCモード
	 */
	public void setAllCompanyMode(boolean allCompanyMode) {
		this.allCompanyMode = allCompanyMode;

		if (allCompanyMode) {
			getSearchCondition().setCompanyCode(null);
		} else {
			getSearchCondition().setCompanyCode(TLoginInfo.getCompany().getCode());
		}
	}

	/**
	 * 口座番号の幅変更
	 * 
	 * @param width 幅
	 */
	public void setAccountNoSize(int width) {

		// 口座番号
		Dimension size = new Dimension(width, 20);
		accountNo.setSize(size);
		accountNo.setPreferredSize(size);
		accountNo.setMinimumSize(size);
		accountNo.setMaximumSize(size);
		resize();
	}

}
