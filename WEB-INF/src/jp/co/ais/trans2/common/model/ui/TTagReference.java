package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.tag.*;

/**
 * 部門検索コンポーネント
 * 
 * @author AIS
 */
public class TTagReference extends TPanel {

	/** コントローラ */
	protected TTagReferenceController controller;

	/** ボタン */
	public TButton btn;

	/** コード */
	public TTextField code;

	/** 付箋色 */
	public TTextField color;

	/** 付箋タイトル */
	public TTextField name;

	/** タイトル */
	public String title;

	/**
	 * コンストラクタ
	 */
	public TTagReference() {

		this.title = "";

		// コンポーネントを初期化する
		initComponents();

		// コンポーネントを配置する
		allocateComponents();

		controller = createController();
	}

	/**
	 * コンポーネントを初期化する<BR>
	 */
	protected void initComponents() {
		btn = new TButton();
		code = new TTextField();
		color = new TTextField();
		name = new TTextField();
	}

	/**
	 * コンポーネントを配置する
	 */
	// 小窓のリファレンス画面
	protected void allocateComponents() {

		setLayout(new GridBagLayout());
		setOpaque(false);

		// ボタン
		TGuiUtil.setComponentSize(btn, new Dimension(80, 20));
		btn.setOpaque(false);

		add(btn);

		// 付箋コード
		TGuiUtil.setComponentSize(code, new Dimension(80, 20));
		code.setMaxLength(getCodeLength());
		add(code);
		code.setImeMode(false);

		// 付箋色
		TGuiUtil.setComponentSize(color, new Dimension(40, 20));
		color.setEditable(false);// 編集可・不可制御
		add(color);

		// 付箋タイトル
		TGuiUtil.setComponentSize(name, new Dimension(240, 20));
		name.setMaxLength(80);
		name.setEditable(false);
		add(name);

		resize();
	}

	/**
	 * コントローラ生成
	 * 
	 * @return コントローラ
	 */
	protected TTagReferenceController createController() {
		return new TTagReferenceController(this);
	}

	/**
	 * @return controller
	 */
	protected TTagReferenceController getController() {
		return controller;
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	public TagSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * コントローラーを初期化する
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
		btn.setTabControlNo(tabControlNo++);
		code.setTabControlNo(tabControlNo++);
		name.setTabControlNo(tabControlNo++);
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
	 * 表示/入力された付箋タイトルを返す.
	 * 
	 * @return 付箋タイトル
	 */
	@Override
	public String getName() {
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
		if (color.isVisible()) {
			width += (int) color.getPreferredSize().getWidth();
		}
		if (name.isVisible()) {
			width += (int) name.getPreferredSize().getWidth();
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
		color.setVisible(bln);
		name.setVisible(bln);
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
	 * 付箋色幅を変更する
	 * 
	 * @param width 幅
	 */
	public void setColorSize(int width) {
		Dimension size = new Dimension(width, 20);
		color.setSize(size);
		color.setPreferredSize(size);
		color.setMinimumSize(size);
		color.setMaximumSize(size);
		resize();
	}

	/**
	 * タイトル幅を変更する
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
		controller.refleshEntity();
		controller.setEntity(getEntity());
	}

	/**
	 * ボタン及びラベルの単語ID設定
	 * 
	 * @param langMessageID メッセージID
	 */
	@Override
	public void setLangMessageID(String langMessageID) {
		btn.setLangMessageID(langMessageID);
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
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */

	public Tag getEntity() {
		return controller.getEntity();
	}

	/**
	 * Entityをセットする
	 * 
	 * @param tag
	 */
	public void setEntity(Tag tag) {
		controller.setEntity(tag);
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.
	 */
	public void refleshEntity() {
		controller.refleshEntity();
	}

}
