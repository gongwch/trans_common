package jp.co.ais.trans.common.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans.common.util.*;

/**
 * TPanelに、タブ順、メッセージIDインターフェイスを追加した複合item. <br>
 * 子item.
 * <ol>
 * <li>button TButton
 * <li>field TTextField
 * <li>notice TTextField
 * </ol>
 */
public class TButtonField extends TPanel implements TInterfaceLangMessageID, TInterfaceTabControl {

	/** シリアルUID */
	private static final long serialVersionUID = 6680537868864513388L;

	/** ボタン */
	protected TButton button;

	/** テキスト */
	protected TTextField field;

	/** ラベルテキスト */
	protected TTextField notice;

	/** ボタンサイズ */
	private Dimension buttonSize = null;

	/** テキストサイズ */
	private Dimension fieldSize = null;

	/** ラベルテキストサイズ */
	private Dimension noticeSize = null;

	/** Noticeフィールドでダイアログを開くか */
	private boolean isOpenDialogNotice = false;

	/** フォーカスがNoticeフィールドにあったかどうか */
	private boolean isFocusNotice = false;

	/**
	 * Constructor.
	 */
	public TButtonField() {
		super();

		initComponents();
		initControl();
	}

	/**
	 * init
	 */
	protected void initControl() {
		field.setEditable(true);
		field.setValue("");
		notice.setEditable(false);
		notice.setValue("");
	}

	/**
	 * コンポーネント構築
	 */
	private void initComponents() {
		GridBagConstraints gridBagConstraints;

		button = new TButton();
		field = new TTextField();
		notice = new TTextField();

		setLayout(new GridBagLayout());
		setPreferredSize(TGuiUtil.correctSize(TGuiUtil.TYPE_BUTTON_FIELD, new Dimension(300, 20)));

		button.setText("TButtonField");
		button.setActionCommand("button");
		button.setMargin(new Insets(2, 2, 2, 2));
		TGuiUtil.setComponentSize(button, new Dimension(100, 20));

		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				fireButtonAction(evt);
			}
		});
		add(button, new GridBagConstraints());

		TGuiUtil.setComponentSize(field, new Dimension(140, 20));

		field.setAllowedSpace(false);
		field.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		add(field, gridBagConstraints);

		TGuiUtil.setComponentSize(notice, new Dimension(180, 20));

		add(notice, new GridBagConstraints());

		buttonSize = this.button.getPreferredSize();
		fieldSize = this.field.getPreferredSize();
		noticeSize = this.notice.getPreferredSize();

		setPanelSize();

		// キー用リスナ
		this.getField().addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				paneKeyReleased(e);
			}
		});

		// フォーカス用リスナ
		this.getField().addFocusListener(new FocusAdapter() {

			public void focusLost(FocusEvent e) {
				focusLostActionPerformed(e);
			}
		});

		// キー用リスナ
		this.getNotice().addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				paneKeyReleasedNotice(e);
			}
		});
	}

	/**
	 * 再度、イベントを起し、lostFocus時に、移動先のenabledが変わる場合に対応する。
	 * 
	 * @param e イベント
	 */
	protected void focusLostActionPerformed(FocusEvent e) {
		// フォーカス受け取ったコンポーネント
		Component comp = e.getOppositeComponent();

		if (comp != null
			&& (TAppletMainCtrl.MenuTreeNode.class.equals(comp.getClass()) || "jp.co.ais.trans.common.ui.TAppletMainCtrl$2"
				.equals(comp.getClass().getName()))) {
			// メニュー押したら強制的に値をクリアする。ただし値が変更されているときのみ
			// ※不正値確定防止のため
			if (this.isValueChanged()) {
				this.field.setValue("");
				this.notice.setValue("");
			}
		}
	}

	/**
	 * スペースキーの処理. <br>
	 * スペースキー押下でボタンをクリックしたものと同等の処理を行う。
	 * 
	 * @param e キーイベント
	 */
	protected void paneKeyReleased(KeyEvent e) {
		if (TGuiUtil.isActive(field) && e.getKeyCode() == KeyEvent.VK_SPACE) {
			// スペース押下時に文字列が消えないように全選択しない
			field.select(0, 0);
			// フォーカスはフィールドにある
			isFocusNotice = false;
			// 該当ボタンをクリック
			button.doClick();
		}
	}

	/**
	 * スペースキーの処理. <br>
	 * スペースキー押下でボタンをクリックしたものと同等の処理を行う。
	 * 
	 * @param e キーイベント
	 */
	protected void paneKeyReleasedNotice(KeyEvent e) {
		if (TGuiUtil.isActive(notice) && isOpenDialogNotice && e.getKeyCode() == KeyEvent.VK_SPACE) {
			// スペース押下時に文字列が消えないように全選択しない
			notice.select(0, 0);
			// フォーカスはNoticeフィールドにある
			isFocusNotice = true;
			// 該当ボタンをクリック
			button.doClick();
		}
	}

	// for MouseListener ********************************

	/**
	 * パネルにフォーカスを合わせる.
	 */
	public void panelRequestFoucus() {
		this.requestFocus(); // キーボードフォーカスの設定
	}

	// アイテムの幅 ******************************************

	/**
	 * panelサイズを、並んだアイテムの横幅の合計にする
	 */
	private void setPanelSize() {
		TGuiUtil.setComponentSize(this, new Dimension(
			(int) (this.buttonSize.getWidth() + this.fieldSize.getWidth() + this.noticeSize.getWidth()) + 5, this
				.getPreferredSize().height));
	}

	/**
	 * button 幅の設定.
	 * 
	 * @param size 幅
	 */
	public void setButtonSize(int size) {
		this.buttonSize.setSize(size, this.buttonSize.getHeight());
		TGuiUtil.setComponentSize(this.button, this.buttonSize);
		this.setPanelSize();
	}

	/**
	 * button 幅の取得
	 * 
	 * @return 幅
	 */
	public int getButtonSize() {
		return (int) this.buttonSize.getWidth();
	}

	/**
	 * field 幅の設定
	 * 
	 * @param size 幅
	 */
	public void setFieldSize(int size) {
		this.fieldSize.setSize(size, this.fieldSize.getHeight());
		TGuiUtil.setComponentSize(this.field, this.fieldSize);
		this.setPanelSize();
	}

	/**
	 * field 幅の取得
	 * 
	 * @return 幅
	 */
	public int getFieldSize() {
		return (int) this.fieldSize.getWidth();
	}

	/**
	 * notice 幅の設定
	 * 
	 * @param size 幅
	 */
	public void setNoticeSize(int size) {
		this.noticeSize.setSize(size, this.noticeSize.getHeight());
		TGuiUtil.setComponentSize(this.notice, this.noticeSize);
		this.setPanelSize();
	}

	/**
	 * notice 幅の取得
	 * 
	 * @return 幅
	 */
	public int getNoticeSize() {
		return (int) this.noticeSize.getWidth();
	}

	// アイテムの寄せ ******************************************

	/**
	 * button の寄せ
	 * 
	 * @deprecated
	 * @param align 0:左 1:中央 2:右
	 */
	public void setButtonHAlignment(int align) {
		TGuiUtil.setButtonHorizonalAlignment(this.button, align);
	}

	/**
	 * field の寄せ
	 * 
	 * @deprecated
	 * @param align 0:左 1:中央 2:右
	 */
	public void setFieldHAlignment(int align) {
		TGuiUtil.setTextFieldHorizonalAlignment(this.field, align);
	}

	/**
	 * notice の寄せ
	 * 
	 * @deprecated
	 * @param align 0:左 1:中央 2:右
	 */
	public void setNoticeHAlignment(int align) {
		TGuiUtil.setTextFieldHorizonalAlignment(this.notice, align);
	}

	// getter. *****************************************

	/**
	 * パネル上のTButtonインスタンスを返す.
	 * 
	 * @return button
	 */
	public TButton getButton() {
		return this.button;
	}

	/**
	 * パネル上のTTextField fieldインスタンスを返す.
	 * 
	 * @return field
	 */
	public TTextField getField() {
		return this.field;
	}

	/**
	 * パネル上のTTextField noticeインスタンスを返す.
	 * 
	 * @return notice
	 */
	public TTextField getNotice() {
		return this.notice;
	}

	// 表示文字 ***************************************************

	/**
	 * ボタン表示文字を設定する.
	 * 
	 * @param text
	 */
	public void setButtonText(String text) {
		this.button.setText(text);
	}

	/**
	 * ボタン表示文字を取得する.
	 * 
	 * @return ボタン表示文字
	 */
	public String getButtonText() {
		return this.button.getText();
	}

	/**
	 * ボタンテキスト最後に三点リーダー(...)を付与するかどうか
	 * 
	 * @return true:付与する
	 */
	public boolean isButtonAddThreeDots() {
		return this.button.isAddThreeDots();
	}

	/**
	 * ボタンテキスト最後に三点リーダー(...)を付与するかどうか
	 * 
	 * @param isAddThreeDots true:付与する
	 */
	public void setButtonAddThreeDots(boolean isAddThreeDots) {
		this.button.setAddThreeDots(isAddThreeDots);
	}

	/**
	 * field テキストフィールドに文字列を設定する.
	 * 
	 * @param value 設定文字列
	 */
	public void setValue(String value) {
		this.field.setValue(value);
	}

	/**
	 * field テキストフィールドから文字列を取得する.
	 * 
	 * @return 入力文字列
	 */
	public String getValue() {
		return this.field.getText();
	}

	/**
	 * テキストフィールドに入力値が有るかどうか
	 * 
	 * @return true:入力値有り
	 */
	public boolean isEmpty() {
		return Util.isNullOrEmpty(getValue());
	}

	/**
	 * @return OldValue
	 * @deprecated 使用しない(isValueChanged()を利用)
	 */
	public String getOldValue() {
		return this.field.getOldText();
	}

	/**
	 * 変更前テキストをクリア
	 */
	public void clearOldText() {
		this.field.clearOldText();
	}

	/**
	 * notice テキストフィールドから文字列を取得する.
	 * 
	 * @return notice文字列
	 */
	public String getNoticeValue() {
		return this.notice.getText();
	}

	/**
	 * notice テキストフィールドに文字列を設定する.
	 * 
	 * @param value 設定文字列
	 */
	public void setNoticeValue(String value) {
		boolean en = notice.isEnabled();
		boolean ed = notice.isEditable();

		notice.setEnabled(true);
		notice.setEditable(true);

		this.notice.setText(value);

		notice.setEditable(ed);
		notice.setEnabled(en);
	}

	// interface 実装 ************************************************

	/**
	 * ラベルの表示文字のメッセージIDを設定する.
	 * 
	 * @see TInterfaceLangMessageID#setLangMessageID(java.lang.String)
	 */
	public void setLangMessageID(String messageID) {
		this.button.setLangMessageID(messageID);
	}

	/**
	 * ラベルの表示文字のメッセージIDを取得する.
	 * 
	 * @see TInterfaceLangMessageID#getLangMessageID()
	 */
	public String getLangMessageID() {
		return this.button.getLangMessageID();
	}

	/**
	 * タブ移動順番号をテキストフィールドに設定する.
	 * 
	 * @see TInterfaceTabControl#setTabControlNo(int)
	 */
	public void setTabControlNo(int no) {
		this.button.setTabControlNo(no);
		this.field.setTabControlNo(no);
		this.notice.setTabControlNo(no);
	}

	/**
	 * タブ移動順番号をテキストフィールドから取得する.
	 * 
	 * @see TInterfaceTabControl#getTabControlNo()
	 */
	public int getTabControlNo() {
		int no = this.button.getTabControlNo();
		if (no != -1) {
			return no;
		} else {
			return this.field.getTabControlNo();

		}
	}

	/**
	 * タブ移動順実装.
	 * 
	 * @see TInterfaceTabControl#setTabEnabled(boolean)
	 */
	public void setTabEnabled(boolean bool) {
		this.button.setTabEnabled(bool);
		this.field.setTabEnabled(bool);
	}

	/**
	 * タブ移動順実装.
	 */
	public boolean isTabEnabled() {
		return this.button.isTabEnabled() || this.field.isTabEnabled();
	}

	// 最大桁. ******************************************************

	/**
	 * 最大桁数
	 * 
	 * @return 最大桁数
	 */
	public int getMaxLength() {
		return this.field.getMaxLength();
	}

	/**
	 * 最大桁数
	 * 
	 * @param maxLength 最大桁数
	 */
	public void setMaxLength(int maxLength) {
		this.field.setMaxLength(maxLength);
	}

	/**
	 * 最大桁数
	 * 
	 * @return 最大桁数
	 */
	public int getNoticeMaxLength() {
		return this.notice.getMaxLength();
	}

	/**
	 * 最大桁数
	 * 
	 * @param maxLength 最大桁数
	 */
	public void setNoticeMaxLength(int maxLength) {
		this.notice.setMaxLength(maxLength);
	}

	/**
	 * IMEモードフラグ
	 * 
	 * @param flag true: IMEモード
	 */
	public void setImeMode(boolean flag) {
		this.field.setImeMode(flag);
	}

	/**
	 * IMEモードフラグ
	 * 
	 * @return true: IMEモード
	 */
	public boolean isImeMode() {
		return this.field.isImeMode();
	}

	/**
	 * ボタンのアクションリスナー登録
	 * 
	 * @param listener アクションリスナー
	 */
	public void addButtonActionListener(ActionListener listener) {
		button.addActionListener(listener);
	}

	/**
	 * ボタンのアクションの際のcallback method(for override).
	 * 
	 * @param evt
	 */
	public void fireButtonAction(@SuppressWarnings("unused")
	ActionEvent evt) {
		// 処理無し
	}

	/**
	 * テキストフィールドのフォーカスリスナー登録
	 * 
	 * @param listener フォーカスリスナー
	 */
	public void addTextFocusListener(FocusListener listener) {
		field.addFocusListener(listener);
	}

	// enable 有効／無効操作制御 *************************************************

	/**
	 * Button enabled property
	 * 
	 * @param bool false:操作不可
	 */
	public void setButtonEnabled(boolean bool) {
		this.button.setEnabled(bool);
	}

	/**
	 * Button enabled property
	 * 
	 * @return false:操作不可
	 */
	public boolean isButtonEnabled() {
		return this.button.isEnabled();
	}

	/**
	 * TextField enabled property
	 * 
	 * @param bool false:操作不可
	 */
	public void setFieldEnabled(boolean bool) {
		this.field.setEnabled(bool);
	}

	/**
	 * TextField enabled property
	 * 
	 * @return false:操作不可
	 */
	public boolean isFieldEnabled() {
		return this.field.isEnabled();
	}

	/**
	 * notice enabled property
	 * 
	 * @param bool false:操作不可
	 */
	public void setNoticeEnabled(boolean bool) {
		this.notice.setEnabled(bool);
	}

	/**
	 * notice enabled property
	 * 
	 * @return false:操作不可
	 */
	public boolean isNoticeEnabled() {
		return this.notice.isEnabled();
	}

	/**
	 * panel enabled property
	 * 
	 * @param bool false:操作不可
	 */
	public void setEnabled(boolean bool) {
		this.button.setEnabled(bool);
		this.field.setEnabled(bool);
		this.notice.setEnabled(bool);
		super.setEnabled(bool);
	}

	/**
	 * ボタンとコードフィールドのEnabled制御
	 * 
	 * @param bool false:操作不可
	 */
	public void setEnabledButtonField(boolean bool) {
		this.button.setEnabled(bool);
		this.field.setEnabled(bool);
	}

	/**
	 * ボタンとコードフィールドのEnabled制御
	 * 
	 * @return false:操作不可
	 */
	public boolean isEnabledButtonField() {
		return this.button.isEnabled() && this.field.isEnabled();
	}

	// editable 編集可／不可操作制御 ************************************************

	/**
	 * field editable property.
	 * 
	 * @param bool false:編集不可
	 */
	public void setEditable(boolean bool) {
		this.field.setEditable(bool);
	}

	/**
	 * field editable property.
	 * 
	 * @return false:編集不可
	 */
	public boolean isEditable() {
		return this.field.isEditable();
	}

	/**
	 * notice editable property.
	 * 
	 * @param bool false:編集不可
	 */
	public void setNoticeEditable(boolean bool) {
		this.notice.setEditable(bool);
	}

	/**
	 * notice editable property.
	 * 
	 * @return false:編集不可
	 */
	public boolean isNoticeEditable() {
		return this.notice.isEditable();
	}

	/**
	 * ボタンはEnabled、フィールドはEditableを制御.<br>
	 * また、値のクリアを行う. noticeは値のクリアのみ
	 * 
	 * @param isControll true:操作可能
	 */
	public void setEditMode(boolean isControll) {
		this.button.setEnabled(isControll);
		this.field.setEditable(isControll);

		if (!isControll) {
			this.clear();
		}
	}

	/**
	 * テキストへのフォーカス移動
	 */
	public void requestTextFocus() {
		this.field.requestFocus();
	}

	// notice-field visible ************************************************

	/**
	 * notice visible property.
	 * 
	 * @param bool false:不可視
	 */
	public void setNoticeVisible(boolean bool) {
		this.notice.setVisible(bool);
	}

	/**
	 * notice visible property.
	 * 
	 * @return false:不可視
	 */
	public boolean isNoticeVisible() {
		return this.notice.isVisible();
	}

	/**
	 * button visible property.
	 * 
	 * @param bool false:不可視
	 */
	public void setButtonVisible(boolean bool) {
		this.button.setVisible(bool);
	}

	/**
	 * button visible property.
	 * 
	 * @return false:不可視
	 */
	public boolean isButtonVisible() {
		return this.button.isVisible();
	}

	/**
	 * コード以外を不可視にする.
	 * 
	 * @param bool true:コード以外は不可視
	 */
	public void setCodeOnlyVisible(boolean bool) {
		setNoticeVisible(!bool);
		setButtonVisible(!bool);
	}

	/**
	 * button visible property.
	 * 
	 * @return true:コード以外は不可視
	 */
	public boolean isCodeOnlyVisible() {
		return !isNoticeVisible() && !isButtonVisible();
	}

	/**
	 * 入力チェッククラスを設定する
	 * 
	 * @param iv Verifier
	 */
	@Override
	public void setInputVerifier(InputVerifier iv) {

		this.field.setInputVerifier(iv);
	}

	/**
	 * 入力チェッククラスを取得する
	 * 
	 * @return Verifier
	 */
	@Override
	public InputVerifier getInputVerifier() {

		return this.field.getInputVerifier();
	}

	/**
	 * 変更されたかチェックする
	 * 
	 * @return true:変更された
	 */
	public boolean isValueChanged() {

		return this.field.isValueChanged();
	}

	/**
	 * 変更されたかチェックする.null、ブランクも対象
	 * 
	 * @return true:変更された
	 */
	public boolean isValueChanged2() {

		return this.field.isValueChanged2();
	}

	/**
	 * 変更されたかチェックする(Notice用)
	 * 
	 * @return true:変更された
	 */
	public boolean isNoticeValueChanged() {

		return this.notice.isValueChanged();
	}

	/**
	 * Noticeフィールドでダイアログを開くか
	 * 
	 * @param bol
	 */
	public void setIsOpenDialogNotice(boolean bol) {
		isOpenDialogNotice = bol;
	}

	/**
	 * Noticeフィールドでダイアログを開くか
	 * 
	 * @return true:開く
	 */
	public boolean isOpenDialogNotice() {
		return this.isOpenDialogNotice;
	}

	/**
	 * Noticeにフォーカスがあったか
	 * 
	 * @return true:Noticeにフォーカスあり
	 */
	public boolean isFocusNotice() {
		return this.isFocusNotice;
	}

	/**
	 * 禁則文字(入力させない文字)の設定
	 * 
	 * @param words 禁則文字リスト
	 * @see TTextField#setProhibitionWords(String[])
	 */
	public void setProhibitionWords(String... words) {
		this.field.setProhibitionWords(words);
	}

	/**
	 * 入力可能文字を正規表現で指定する.
	 * 
	 * @param regex 正規表現文字
	 * @see TTextField#setRegex(String)
	 */
	public void setRegex(String regex) {
		this.field.setRegex(regex);
	}

	/**
	 * 値をクリアする.履歴もクリア
	 */
	public void clear() {
		this.setValue("");
		this.field.clearOldText();
		this.setNoticeValue("");
		this.notice.clearOldText();
	}
}
