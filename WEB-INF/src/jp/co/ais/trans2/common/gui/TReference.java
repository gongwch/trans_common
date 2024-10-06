package jp.co.ais.trans2.common.gui;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * 参照フィールドの基底クラス
 * 
 * @author AIS
 */
public abstract class TReference extends TPanel {

	/** ボタン */
	public TButton btn;

	/** コード */
	public TTextField code;

	/** 略称 */
	public TTextField name;

	/** ラベル */
	public TLabel lbl;

	/** タイトル */
	public String title;

	/** タイプ */
	public TYPE type;

	/** 会社出力単位 */
	protected TCompanyOrganizationUnit companyOrgUnit = null;

	/**
	 * タイプ
	 */
	public enum TYPE {
		/** 通常タイプ */
		BUTTON,
		/** ラベルタイプ */
		LABEL
	}

	/**
	 * コンストラクタ
	 */
	public TReference() {
		this(TYPE.BUTTON, "");
	}

	/**
	 * コンストラクタ
	 * 
	 * @param title
	 */
	public TReference(String title) {
		this(TYPE.BUTTON, title);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type
	 */
	public TReference(TYPE type) {
		this(type, "");
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type
	 * @param title 外から設定したい場合のキャプション
	 */
	public TReference(TYPE type, String title) {

		this.type = type;

		this.title = title;

		// コンポーネントを初期化する
		initComponents();

		// コンポーネントを配置する
		allocateComponents();

	}

	/**
	 * コントローラのファクトリ
	 * 
	 * @return コントローラ
	 */
	protected abstract TReferenceController getController();

	/**
	 * コンポーネントを初期化する<BR>
	 */
	protected void initComponents() {
		btn = new TButton();
		code = new TTextField();
		name = new TTextField();
		lbl = new TLabel();
	}

	/**
	 * コンポーネントを配置する
	 */
	protected void allocateComponents() {

		GridBagConstraints gc = new GridBagConstraints();

		setLayout(new GridBagLayout());
		setOpaque(false);

		// ボタン
		TGuiUtil.setComponentSize(btn, new Dimension(80, 20));
		btn.setOpaque(false);

		// ラベル
		TGuiUtil.setComponentSize(lbl, new Dimension(80, 20));
		lbl.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl.setOpaque(false);

		if (this.type == TYPE.BUTTON) {
			add(btn, gc);
			lbl.setVisible(false);
		} else {
			gc.insets = new Insets(0, 0, 0, 5);
			add(lbl, gc);
			btn.setVisible(false);
		}

		// コード
		TGuiUtil.setComponentSize(code, new Dimension(80, 20));
		code.setMaxLength(getCodeLength());
		gc.insets = new Insets(0, 0, 0, 0);
		add(code, gc);
		code.setAllowedSpace(false);
		code.setImeMode(false);

		// 名称
		name.setEditable(false);
		TGuiUtil.setComponentSize(name, new Dimension(150, 20));
		gc.insets = new Insets(0, 0, 0, 0);
		add(name, gc);

		resize();
	}

	/**
	 * コードと略称を初期化する
	 */
	public void clear() {
		getController().clear();
	}

	/**
	 * 値が変わったかを返す
	 * 
	 * @return 値が変わったか
	 */
	public boolean isValueChanged() {
		return getController().isValueChanged();
	}

	/**
	 * Tab順の設定
	 * 
	 * @param tabControlNo タブ順
	 */
	public void setTabControlNo(int tabControlNo) {
		btn.setTabControlNo(tabControlNo);
		code.setTabControlNo(tabControlNo);
		name.setTabControlNo(tabControlNo);
	}

	/**
	 * コード入力判定
	 * 
	 * @return true:未入力
	 */
	public boolean isEmpty() {
		return code.isEmpty();
	}

	/**
	 * コード入力可/不可判定
	 * 
	 * @return true:入力可
	 */
	public boolean isEditable() {
		return code.isEditable();
	}

	/**
	 * 入力されたコード値を返す
	 * 
	 * @return 入力されたコード値
	 */
	public String getCode() {
		return code.getText();
	}

	/**
	 * 表示/入力された略称を返す.
	 * 
	 * @return 略称
	 */
	public String getNames() {
		return name.getText();
	}

	/**
	 * 入力されたコード値をセットする.
	 * 
	 * @param value 入力されたコード値
	 */
	public void setCode(String value) {
		code.setText(value);
	}

	/**
	 * 表示/入力された略称をセットする.
	 * 
	 * @param names 略
	 */
	public void setNames(String names) {
		name.setText(names);
	}

	/**
	 * @param callBackListener callBackListenerを設定する。
	 */
	public void addCallBackListener(TCallBackListener callBackListener) {
		getController().addCallBackListener(callBackListener);
	}

	/**
	 * コードの存在チェックをするかを返します
	 * 
	 * @return true する
	 */
	public boolean isCheckExist() {
		return getController().isCheckExist();
	}

	/**
	 * コードの存在チェックをするか設定します
	 * 
	 * @param checkExist
	 */
	public void setCheckExist(boolean checkExist) {
		getController().setCheckExist(checkExist);
	}

	/**
	 * サイズの再反映
	 */
	public void resize() {

		int width = (int) code.getPreferredSize().getWidth();
		if (btn.isVisible()) {
			width += (int) btn.getPreferredSize().getWidth();
		}
		if (name.isVisible()) {
			width += (int) name.getPreferredSize().getWidth();
		}
		if (lbl.isVisible()) {
			width += (int) lbl.getPreferredSize().getWidth() + 5; // 右余白が計算するべき
		}

		setSize(width, 20);
	}

	/**
	 * 編集可・不可制御
	 * 
	 * @param edit
	 */
	public void setEditable(boolean edit) {
		getController().setEditable(edit);
	}

	/**
	 * コードにフォーカスを合わせる。
	 */
	public void requestTextFocus() {
		this.code.requestFocus();
	}

	/**
	 * @see javax.swing.JComponent#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean bln) {
		btn.setVisible(bln);
		code.setVisible(bln);
		name.setVisible(bln);
		lbl.setVisible(bln);
		super.setVisible(bln);
	}

	/**
	 * コードの長さを返す。デフォルト長と異なる検索フィールドは<br>
	 * 当該メソッドをOverrideする。
	 * 
	 * @return コード長
	 */
	protected int getCodeLength() {
		return TransUtil.DEFAULT_CODE_LENGTH;
	}

	/**
	 * 固定ボタンの名を取得します
	 * 
	 * @return String
	 */
	public String getFixedButtonCaption() {
		return getController().fixedButtonCaption;
	}

	/**
	 * 固定ボタンの名を設定します
	 * 
	 * @param fixedButtonCaption
	 */
	public void setFixedButtonCaption(String fixedButtonCaption) {
		getController().fixedButtonCaption = fixedButtonCaption;
	}

	/**
	 * 固定検索ダイアログを取得します
	 * 
	 * @return String
	 */
	public String getFixedDialogCaption() {
		return getController().fixedDialogCaption;
	}

	/**
	 * 固定検索ダイアログを設定します
	 * 
	 * @param fixedDialogCaption
	 */
	public void setFixedDialogCaption(String fixedDialogCaption) {
		getController().fixedDialogCaption = fixedDialogCaption;
	}

	/**
	 * ボタン幅を変更する
	 * 
	 * @param width 幅
	 */
	public void setButtonSize(int width) {
		Dimension size = new Dimension(width, 20);
		btn.setSize(size);
		btn.setPreferredSize(size);
		btn.setMinimumSize(size);
		btn.setMaximumSize(size);
		resize();
	}

	/**
	 * ボタン幅を変更する
	 * 
	 * @param width 幅
	 */
	public void setLabelSize(int width) {
		Dimension size = new Dimension(width, 20);
		lbl.setSize(size);
		lbl.setPreferredSize(size);
		lbl.setMinimumSize(size);
		lbl.setMaximumSize(size);
		resize();
	}

	/**
	 * コード幅を変更する
	 * 
	 * @param width 幅
	 */
	public void setCodeSize(int width) {
		Dimension size = new Dimension(width, 20);
		code.setSize(size);
		code.setPreferredSize(size);
		code.setMinimumSize(size);
		code.setMaximumSize(size);
		resize();
	}

	/**
	 * 名称幅を変更する
	 * 
	 * @param width 幅
	 */
	public void setNameSize(int width) {
		Dimension size = new Dimension(width, 20);
		name.setSize(size);
		name.setPreferredSize(size);
		name.setMinimumSize(size);
		name.setMaximumSize(size);
		resize();
	}

	/**
	 * ダイアログのコードの初期値表示するかの取得
	 * 
	 * @return showDefaultCode ダイアログのコードの初期値表示するか
	 */
	public boolean isShowDefaultCode() {
		return getController().isShowDefaultCode();
	}

	/**
	 * ダイアログのコードの初期値表示するかの設定
	 * 
	 * @param showDefaultCode ダイアログのコードの初期値表示するか
	 */
	public void setShowDefaultCode(boolean showDefaultCode) {
		getController().setShowDefaultCode(showDefaultCode);
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.<br>
	 * 表示を更新する
	 */
	public void refleshAndShowEntity() {
		// override 用
	}

	/**
	 * ボタン及びラベルの単語ID設定
	 * 
	 * @param langMessageID メッセージID
	 */
	@Override
	public void setLangMessageID(String langMessageID) {
		btn.setLangMessageID(langMessageID);
		lbl.setLangMessageID(langMessageID);
	}

	/**
	 * スーパーパネルの単語ID設定
	 * 
	 * @param langMessageID メッセージID
	 */
	public void setPanelLangMessageID(String langMessageID) {
		super.setLangMessageID(langMessageID);
	}

	/**
	 * 会社出力単位の取得
	 * 
	 * @return companyOrgUnit 会社出力単位
	 */
	public TCompanyOrganizationUnit getCompanyOrgUnit() {
		return companyOrgUnit;
	}

	/**
	 * 会社出力単位の設定
	 * 
	 * @param companyOrgUnit 会社出力単位
	 */
	public void setCompanyOrgUnit(TCompanyOrganizationUnit companyOrgUnit) {
		this.companyOrgUnit = companyOrgUnit;
	}

}
