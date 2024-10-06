package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.model.*;

/**
 * 参照フィールドのコントローラ基底クラス。
 * 
 * @author AIS
 */
public abstract class TReferenceController extends TController {

	/** 検索ダイアログ */
	protected TReferenceDialog dialog;

	/** 検索フィールド */
	protected TReference field;

	/** ロストフォーカス時のコード存在チェック true;チェックする */
	protected boolean checkExist = true;

	/** TCallBackListener */
	protected List<TCallBackListener> callBackListenerList = null;

	/** 選択中のEntity */
	protected Object entity;

	/** 一個前のEntity */
	protected TReferable oldEntity = null;

	/** 3カラムの表示 */
	protected boolean show3rdColumn = true;

	/** 固定ボタンの名を取得します */
	protected String fixedButtonCaption = null;

	/** 固定検索ダイアログを取得します */
	protected String fixedDialogCaption = null;

	/** ダイアログのコードの初期値表示するか */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/**
	 * 固定検索ダイアログを取得します
	 * 
	 * @return fixedButtonCaption
	 */
	public String getFixedDialogCaption() {
		return fixedDialogCaption;
	}

	/**
	 * 固定検索ダイアログを設定します
	 * 
	 * @param fixedDialogCaption
	 */
	public void setFixedDialogCaption(String fixedDialogCaption) {
		this.fixedDialogCaption = fixedDialogCaption;
	}

	/**
	 * 3カラムの表示
	 * 
	 * @return boolean
	 */
	public boolean isShow3rdColumn() {
		return show3rdColumn;
	}

	/**
	 * 3カラムの表示
	 * 
	 * @param show3rdColumn
	 */
	public void setShow3rdColumn(boolean show3rdColumn) {
		this.show3rdColumn = show3rdColumn;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param field 検索フィールド
	 */
	public TReferenceController(TReference field) {
		this.field = field;
		init();
	}

	/**
	 * 初期処理
	 */
	public void init() {

		// イベントを追加する
		addEvent();

		// コードと名称をクリアする
		clear();

		// ボタンのキャプション
		setFixedButtonCaption(field.title);
		if (Util.isNullOrEmpty(getFixedButtonCaption())) {
			field.btn.setLangMessageID(getButtonCaption());
			field.lbl.setLangMessageID(getButtonCaption());
		} else {
			field.btn.setLangMessageID(getFixedButtonCaption());
			field.lbl.setLangMessageID(getFixedButtonCaption());
		}

	}

	/**
	 * 検索フィールドで発生するイベントを追加する
	 */
	protected void addEvent() {

		// ボタン押下
		field.btn.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				field.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btn_Click();
				field.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}

		});

		// コードフィールドでスペース押下
		field.code.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				code_KeyPressed(e);
			}

		});

		// コードフィールドのverifyイベント
		field.code.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				try {

					// 値の変更がない
					if (!field.code.isValueChanged2()) {
						return true;
					}

					// 旧Entityを退避
					oldEntity = createEntity();
					if (entity != null && entity instanceof TReferable) {
						oldEntity.setCode(((TReferable) entity).getCode());
					}

					field.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					if (callBackListenerList != null && !callBackListenerList.isEmpty()) {
						for (TCallBackListener listener : callBackListenerList) {
							listener.before();
						}
					}

					boolean rt = code_Verify(comp);

					if (callBackListenerList != null && !callBackListenerList.isEmpty()) {
						for (TCallBackListener listener : callBackListenerList) {
							listener.after();
							listener.after(rt);

							if (!listener.afterVerify(rt)) {
								return false;
							}
						}
					}

					return rt;

				} finally {
					field.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
			}

		});

	}

	/**
	 * コードと略称を初期化する
	 */
	public void clear() {
		field.code.setText(null);
		field.name.setText(null);
		entity = null;
	}

	/**
	 * フィールド[ボタン]押下
	 */
	public void btn_Click() {

		try {
			// ロストフォーカス時チェックするかの情報を退避
			boolean tempCheckExist = isCheckExist();

			// 検索ダイアログを生成
			dialog = createDialog();
			dialog.setShow3rdColumn(this.show3rdColumn);
			// 検索ダイアログにイベント追加
			addDialogEvent();

			dialog.btnSettle.setEnabled(false);

			if (!field.code.isEmpty()) {
				// コードが入力された状態でダイアログが表示された場合、エラーメッセージを表示しないようにする。
				setCheckExist(false);
				if (isShowDefaultCode()) {
					dialog.code.setText(field.code.getText());
					dialog.controller.btnSearch_Click();
					dialog.btnSettle.setEnabled(true);
				}
			}

			// 検索した結果、データが存在しない場合、エラーメッセージを表示するようにする。
			setCheckExist(true);
			// mordalで開く
			dialog.setModal(true);
			dialog.setVisible(true);

			// ダイアログ処理が終了したため、チェックフラグを元に戻す。
			setCheckExist(tempCheckExist);
		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * フィールド[コード]でスペース押下で検索ダイアログを開く
	 * 
	 * @param e
	 */
	public void code_KeyPressed(KeyEvent e) {

		// スペース押下で検索ダイアログを開く
		if (KeyEvent.VK_SPACE == e.getKeyCode()) {

			// 編集不可の場合は何もしない
			if (!field.code.isEditable() || !field.code.isEnabled()) {
				return;
			}

			// コード値が消えないようにする
			field.code.select(0, 0);

			// 検索ダイアログを開く
			btn_Click();
		}

	}

	/**
	 * フィールド[コード]のverify
	 * 
	 * @param comp コンポーネント
	 * @return verify結果
	 */
	public abstract boolean code_Verify(JComponent comp);

	/**
	 * 検索ダイアログで発生するイベントを追加する
	 */
	protected void addDialogEvent() {

		// 検索ボタン押下
		dialog.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				btnSearch_Click();
				after_btnSearch_Click();
			}
		});

		// 確定ボタン押下
		dialog.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				btnSettle_Click(e);
			}
		});

		// 戻るボタン押下
		dialog.btnBack.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				btnBack_Click();
			}
		});

		// コード条件でEnterで検索実行
		dialog.code.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				dialog_code_keyPressed(e);
			}
		});

		// 略称条件でEnterで検索実行
		dialog.names.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				dialog_code_keyPressed(e);
			}
		});

		// 検索名称でEnterで検索実行
		dialog.namek.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				dialog_code_keyPressed(e);
			}
		});
	}

	/**
	 * 検索ダイアログ[検索]ボタン押下
	 */
	public abstract void btnSearch_Click();

	/**
	 * 検索ダイアログ[確定]ボタン押下
	 * 
	 * @param e
	 */
	public void btnSettle_Click(@SuppressWarnings("unused") ActionEvent e) {

		// 旧Entityを退避
		oldEntity = createEntity();
		if (oldEntity != null) {
			oldEntity.setCode(field.code.getText());
		}

		btnSettle_Click();
	}

	/**
	 * 検索ダイアログ[確定]ボタン押下
	 */
	public abstract void btnSettle_Click();

	/**
	 * 検索ダイアログ[戻る]ボタン押下
	 */
	public void btnBack_Click() {
		// 検索ダイアログを閉じる
		dialog.setVisible(false);
		dialog.dispose();
	}

	/**
	 * 検索ダイアログの検索キーフィールドでEnterが押されたら検索する。
	 * 
	 * @param e
	 */
	public void dialog_code_keyPressed(KeyEvent e) {
		if (KeyEvent.VK_ENTER == e.getKeyCode()) {
			btnSearch_Click();
			after_btnSearch_Click();
		}
	}

	/**
	 * 検索ダイアログの検索キーフィールドでEnterが押されたら検索した処理。
	 */
	public void after_btnSearch_Click() {
		if (ClientConfig.isFlagOn("trans.ref.table.focusable") && dialog.tbl.getRowCount() > 0) {
			EventQueue.invokeLater(new Runnable() {

				public void run() {
					dialog.tbl.requestFocus();
				}
			});
		}
	}

	/**
	 * 検索ダイアログのインスタンスを生成し返す
	 * 
	 * @return 検索ダイアログ
	 */
	protected TReferenceDialog createDialog() {
		return new TReferenceDialog(this);
	}

	/**
	 * コードの存在チェックをするかを返します
	 * 
	 * @return true する
	 */
	public boolean isCheckExist() {
		return checkExist;
	}

	/**
	 * コードの存在チェックをするか設定します
	 * 
	 * @param checkExist
	 */
	public void setCheckExist(boolean checkExist) {
		this.checkExist = checkExist;
	}

	/**
	 * 検索ダイアログのキャプションを返す
	 * 
	 * @return 検索ダイアログのキャプション
	 */
	public abstract String getDialogCaption();

	/**
	 * ボタンのキャプションを取得します
	 * 
	 * @return ボタンのキャプション
	 */
	public abstract String getButtonCaption();

	/**
	 * 固定ボタンの名を取得します
	 * 
	 * @return fixedButtonCaption
	 */
	public String getFixedButtonCaption() {
		return fixedButtonCaption;
	}

	/**
	 * 固定ボタンの名を設定します
	 * 
	 * @param fixedButtonCaption
	 */
	public void setFixedButtonCaption(String fixedButtonCaption) {
		this.fixedButtonCaption = fixedButtonCaption;
	}

	/**
	 * 検索ダイアログのタイトルを返す
	 * 
	 * @return タイトル
	 */
	public String getDialogTitle() {
		if (Util.isNullOrEmpty(getFixedDialogCaption())) {
			return getWord(getDialogCaption()) + getWord("C00010"); // XX一覧
		} else {
			return getWord(getFixedDialogCaption()) + getWord("C00010");
		}
	}

	/**
	 * コードのキャプションを取得します
	 * 
	 * @return コードのキャプション
	 */
	public String getCodeCaption() {
		if (Util.isNullOrEmpty(getFixedDialogCaption())) {
			return getWord(getDialogCaption()) + getWord("C00174"); // XXコード
		} else {
			return getWord(getFixedDialogCaption()) + getWord("C00174");
		}
	}

	/**
	 * 略称のキャプションを取得します
	 * 
	 * @return 略称のキャプション
	 */
	public String getNamesCaption() {
		if (Util.isNullOrEmpty(getFixedDialogCaption())) {
			return getWord(getDialogCaption()) + getWord("C00548"); // XX略称
		} else {
			return getWord(getFixedDialogCaption()) + getWord("C00548");
		}
	}

	/**
	 * 名称のキャプションを取得します
	 * 
	 * @return 名称のキャプション
	 */
	public String getNameCaption() {
		if (Util.isNullOrEmpty(getFixedDialogCaption())) {
			return getWord(getDialogCaption()) + getWord("C00518"); // 名称
		} else {
			return getWord(getFixedDialogCaption()) + getWord("C00518");
		}
	}

	/**
	 * 検索名称のキャプションを取得します
	 * 
	 * @return 検索名称のキャプション
	 */
	public String getNamekCaption() {
		if (Util.isNullOrEmpty(getFixedDialogCaption())) {
			return getWord(getDialogCaption()) + getWord("C00160"); // 検索名称
		} else {
			return getWord(getFixedDialogCaption()) + getWord("C00160");
		}
	}

	/**
	 * コードのカラムサイズを取得します
	 * 
	 * @return コードのカラムサイズ
	 */
	public int getCodeColumnSize() {
		return 100;
	}

	/**
	 * 略称のカラムサイズを取得します
	 * 
	 * @return 略称のカラムサイズ
	 */
	public int getNamesColumnSize() {
		return 160;
	}

	/**
	 * 検索名称のカラムサイズを取得します
	 * 
	 * @return 検索名称のカラムサイズ
	 */
	public int getNamekColumnSize() {
		return 160;
	}

	/**
	 * 略称の寄せを取得します
	 * 
	 * @return 略称の寄せ
	 */
	public int getNamesColumnAlignment() {
		return SwingConstants.LEFT;
	}

	/**
	 * 検索名称の寄せを取得します
	 * 
	 * @return 検索名称の寄せ
	 */
	public int getNamekColumnAlignment() {
		return SwingConstants.LEFT;
	}

	/**
	 * 検索ダイアログのテーブル保存キーを返す
	 * 
	 * @return 検索ダイアログのテーブル保存キー
	 */
	public abstract String getTableKeyName();

	/**
	 * @return callBackListenerを戻します。
	 */
	public List<TCallBackListener> getCallBackListenerList() {
		return callBackListenerList;
	}

	/**
	 * @param callBackListenerList callBackListenerを設定します。
	 */
	public void setCallBackListenerList(List<TCallBackListener> callBackListenerList) {
		this.callBackListenerList = callBackListenerList;
	}

	/**
	 * @param callBackListener callBackListenerを追加する
	 */
	public void addCallBackListener(TCallBackListener callBackListener) {
		if (callBackListenerList == null) {
			callBackListenerList = new ArrayList<TCallBackListener>();
		}
		callBackListenerList.add(callBackListener);
	}

	/**
	 * 編集可・不可制御
	 * 
	 * @param edit
	 */
	public void setEditable(boolean edit) {
		field.btn.setEnabled(edit);
		field.code.setEditable(edit);
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	public Object getEntity() {
		// 存在チェックをする場合、またはEntityが存在した場合、存在チェックを受けたEntityを返す
		if (checkExist || entity != null) {
			return entity;
		}
		// 存在チェックをしない場合、入力途中の未確定Entityでも返す。ただしコードが未入力の場合はnull
		if (Util.isNullOrEmpty(field.code.getText())) {
			return null;
		}
		return getUnspecifiedEntity();

	}

	/**
	 * 存在チェックをしない場合の入力途中未確定Entityを返す
	 * 
	 * @return 存在チェックをしない場合の入力途中未確定Entity
	 */
	public abstract Object getUnspecifiedEntity();

	/**
	 * Entityのインスタンスファクトリ
	 * 
	 * @return 新規TReferable
	 */
	public TReferable createEntity() {
		// TODO 後にabstractに変更
		return null;
	}

	/**
	 * 値が変わったかを返す。<br>
	 * ここで、「値が変わった」= 最後にコンポーネントが退避したentityと<br>
	 * 直近のverify後(CallbackListenerのafterより前)のentityの比較である。
	 * 
	 * @return AIS
	 */
	public boolean isValueChanged() {

		if ((oldEntity != null && entity == null) || (oldEntity == null && entity != null)) {
			return true;
		}

		// 旧コード
		String oldCode = "";
		if (oldEntity != null) {
			oldCode = Util.avoidNull(oldEntity.getCode());
		}

		String newCode = "";
		if (entity != null) {
			newCode = Util.avoidNull(((TReferable) entity).getCode());
		}

		return !oldCode.equals(newCode);
	}

	/**
	 * ダイアログのコードの初期値表示するかの取得
	 * 
	 * @return showDefaultCode ダイアログのコードの初期値表示するか
	 */
	public boolean isShowDefaultCode() {
		return showDefaultCode;
	}

	/**
	 * ダイアログのコードの初期値表示するかの設定
	 * 
	 * @param showDefaultCode ダイアログのコードの初期値表示するか
	 */
	public void setShowDefaultCode(boolean showDefaultCode) {
		this.showDefaultCode = showDefaultCode;
	}

	/**
	 * 日付同一比較
	 * 
	 * @param a
	 * @param b
	 * @return true:同じ
	 */
	public static boolean equals(Date a, Date b) {
		if (a == null && b != null) {
			return false;
		}
		if (a != null && b == null) {
			return false;
		}
		if (a == null && b == null) {
			return true;
		}

		return DateUtil.equals(a, b);
	}

	/**
	 * 文字列同一比較
	 * 
	 * @param a
	 * @param b
	 * @return true:同じ
	 */
	public static boolean equals(String a, String b) {
		return Util.avoidNullNT(a).equals(Util.avoidNullNT(b));
	}
}
