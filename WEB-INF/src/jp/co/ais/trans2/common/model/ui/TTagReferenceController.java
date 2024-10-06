package jp.co.ais.trans2.common.model.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.tag.*;

/**
 * 参照フィールドのコントローラ基底クラス。
 * 
 * @author AIS
 */
public class TTagReferenceController extends TController {

	/** 検索条件 */
	protected TagSearchCondition condition;

	/** 検索ダイアログ */
	protected TTagReferenceDialog dialog;

	/** 検索フィールド */
	protected TTagReference field;

	/** ロストフォーカス時のコード存在チェック true;チェックする */
	protected boolean checkExist = true;

	/** TCallBackListener */
	protected List<TCallBackListener> callBackListenerList = null;

	/** 選択中のEntity */
	protected Tag entity;

	/** 一個前のEntity */
	protected TReferable oldEntity = null;

	/** 固定ボタンの名を取得します */
	protected String fixedButtonCaption = null;

	/** 固定検索ダイアログを取得します */
	protected String fixedDialogCaption = null;

	/** ダイアログのコードの初期値表示するか */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/** 背景カラー */
	protected Color colorBackColor = null;

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
	 * コンストラクタ
	 * 
	 * @param field 検索フィールド
	 */
	public TTagReferenceController(TTagReference field) {
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

		field.btn.setLangMessageID(getButtonCaption1());
		field.btn.setLangMessageID(getButtonCaption2());

		initSearchCondition();

		colorBackColor = field.color.getBackground();
	}

	/**
	 * 検索フィールドで発生するイベントを追加する
	 */
	protected void addEvent() {

		// ボタン押下
		field.btn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
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
	 * 表示内容を初期化する
	 */

	public void clear() {
		field.code.setText(null);
		field.color.setBackground(colorBackColor);
		field.name.setText(null);
		field.name.setEditable(false);
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
	 * 検索条件のインスタンスを生成
	 * 
	 * @return 新規検索条件のインスタンス
	 */
	protected TagSearchCondition createSearchCondition() {
		return new TagSearchCondition();
	}

	/**
	 * 検索条件の初期化
	 */

	protected void initSearchCondition() {
		condition = createSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
	}

	/**
	 * フィールド[コード]のverify
	 * 
	 * @param comp コンポーネント
	 * @return verify結果
	 */
	public boolean code_Verify(@SuppressWarnings("unused") JComponent comp) {

		// 値がブランクの場合、名称を初期化
		if (Util.isNullOrEmpty(field.getCode())) {
			entity = null;
			field.color.setBackground(colorBackColor);
			field.name.setText(null);
			field.name.setEditable(false);
			return true;
		}
		// 値の変更がない時はデータを取得しない
		if (!field.code.isValueChanged()) {
			return true;
		}

		// 入力されたコードに対し、紐付くデータを取得する
		entity = getInputtedEntity();

		// 値があればセット

		if (entity != null) {

			field.code.setText(getEntity().getCode());
			field.color.setBackground(getEntity().getColor());
			field.name.setText(getEntity().getTitle());
			field.name.setEditable(true);

			return true;

		}
		field.name.setText(null);
		if (checkExist) {
			showMessage(field, "I00123");// 該当コードは存在しません
			field.code.requestFocus();
			field.code.clearOldText();
			return false;
		}
		return true;

	}

	/**
	 * 検索ダイアログで発生するイベントを追加する
	 */
	protected void addDialogEvent() {

		// 検索ボタン押下
		dialog.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnSearch_Click();
				after_btnSearch_Click();
			}
		});

		// 確定ボタン押下
		dialog.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnSettle_Click(e);
			}
		});

		// 戻るボタン押下
		dialog.btnBack.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
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

		// 検索名称でEnterで検索実行
		dialog.title.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				dialog_code_keyPressed(e);
			}
		});
	}

	/**
	 * 検索ダイアログ[検索]ボタン押下
	 */
	public void btnSearch_Click() {

		try {

			// 確定ボタンを押下不可にする
			dialog.btnSettle.setEnabled(false);

			// 一覧をクリアする
			dialog.tbl.removeRow();

			// データを抽出する
			TagSearchCondition condition_ = getCondition().clone();
			// コード曖昧検索
			condition_.setCodeLike(dialog.code.getText());

			// 検索名称曖昧検索
			condition_.setTitleLike(dialog.title.getText());

			List<Tag> list = getList(condition_);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");// データが見つかりません。
				}
				return;
			}

			// 一覧にセット
			for (Tag bean : list) {
				dialog.tbl.addRow(new Object[] { bean, bean.getCode(), " ", bean.getTitle() });
			}

			// 確定ボタンを押下可能にする
			dialog.btnSettle.setEnabled(true);

			// 1行目を選択
			dialog.tbl.setRowSelectionInterval(0, 0);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 検索ダイアログ[確定]ボタン押下
	 * 
	 * @param e
	 */

	// 選択データを一覧に反映
	public void btnSettle_Click(@SuppressWarnings("unused") ActionEvent e) {

		// 旧Entityを退避
		oldEntity = createEntity();
		if (oldEntity != null) {
			oldEntity.setCode(field.code.getText());
		}
		btnSettle_Click();
	}

	/**
	 * Classを返す
	 * 
	 * @return Class
	 */
	// インターフェイスとの繋がり
	protected Class getModelClass() {
		return TagManager.class;
	}

	/**
	 * 入力された付箋情報を返す
	 * 
	 * @return Entity
	 */

	protected Tag getInputtedEntity() {

		try {

			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			TagSearchCondition condition_ = this.condition.clone();
			condition_.setTagCode(field.getCode());
			condition_.setTagTitle(field.getName());

			List<Tag> list = getList(condition_);

			if (list == null || list.isEmpty()) {
				return null;
			}
			return list.get(0);

		} catch (Exception e) {
			errorHandler(e);
		}

		return null;
	}

	/**
	 * 検索条件に該当する付箋リストを返す
	 * 
	 * @param condition_ 検索条件
	 * @return 検索条件に該当する付箋リスト
	 */

	protected List<Tag> getList(TagSearchCondition condition_) {

		try {

			List<Tag> list = (List<Tag>) request(getModelClass(), "get", condition_);

			return list;

		} catch (Exception e) {
			errorHandler(e);
		}

		return null;
	}

	/**
	 * @return 検索条件を取得
	 */
	public TagSearchCondition getCondition() {
		return condition;
	}

	/**
	 * 選択された付箋を返す
	 * 
	 * @return 選択された付箋
	 */
	protected Tag getSelectedEntity() {
		return (Tag) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	/**
	 * 検索ダイアログ[確定]ボタン押下
	 */
	public void btnSettle_Click() {
		try {

			// 一覧で選択されたEntityを取得する
			entity = getSelectedEntity();

			// Entityが存在すれば、検索ダイアログ呼び出し元にセット

			if (entity != null) {
				field.code.setText(getEntity().getCode());
				field.color.setBackground(getEntity().getColor());
				field.name.setText(getEntity().getTitle());
				field.name.setEditable(true);
				
				field.code.pushOldText();// 他のコントローラーでもcodeのみ

			}

			// ダイアログを閉じる
			dialog.setVisible(false);
			dialog.dispose();

			// 呼び出し元のコードフィールドにフォーカス
			field.code.requestFocus();

			if (callBackListenerList != null && !callBackListenerList.isEmpty()) {
				for (TCallBackListener listener : callBackListenerList) {
					listener.after();
					listener.after(entity != null);
					listener.afterVerify(entity != null);
				}
			}

		} catch (Exception e) {
			errorHandler(e);
		}
	}

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
	protected TTagReferenceDialog createDialog() {
		return new TTagReferenceDialog(this);
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
	public String getDialogCaption() {
		return "C12054";
	}

	/**
	 * ボタンのキャプションを取得します
	 * 
	 * @return ボタンのキャプション
	 */
	public String getButtonCaption1() {
		return "C12055";
	}

	/**
	 * ボタンのキャプションを取得します
	 * 
	 * @return ボタンのキャプション
	 */
	public String getButtonCaption2() {
		return "C12056";
	}

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
	 * 付箋コードのキャプションを取得します
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
	 * 付箋色のキャプションを取得します
	 * 
	 * @return 付箋色のキャプション
	 */
	public String getColorCaption() {
		if (Util.isNullOrEmpty(getFixedDialogCaption())) {
			return getWord(getDialogCaption()) + getWord("C12053"); // 付箋色
		} else {
			return getWord(getFixedDialogCaption()) + getWord("C12053");
		}
	}

	/**
	 * 付箋タイトルのキャプションを取得します
	 * 
	 * @return 付箋タイトルのキャプション
	 */
	public String getTitleCaption() {
		if (Util.isNullOrEmpty(getFixedDialogCaption())) {
			return getWord(getDialogCaption()) + getWord("COP1299"); // 付箋タイトル
		} else {
			return getWord(getFixedDialogCaption()) + getWord("COP1299");
		}
	}

	/**
	 * 付箋コードのカラムサイズを取得します
	 * 
	 * @return 付箋コードのカラムサイズ
	 */
	public int getCodeColumnSize() {
		return 100;
	}

	/**
	 * 付箋色のカラムサイズを取得します
	 * 
	 * @return 付箋色のカラムサイズ
	 */
	public int getColorColumnSize() {
		return 100;
	}

	/**
	 * 付箋タイトルのカラムサイズを取得します
	 * 
	 * @return 付箋タイトルのカラムサイズ
	 */
	public int getTitleColumnSize() {
		return 250;
	}

	/**
	 * 付箋コードの寄せを取得します
	 * 
	 * @return 付箋コードの寄せ
	 */
	public int getCodeColumnAlignment() {
		return SwingConstants.LEFT;
	}

	/**
	 * 付箋色の寄せを取得します
	 * 
	 * @return 付箋色の寄せ
	 */
	public int getColorColumnAlignment() {
		return SwingConstants.LEFT;
	}

	/**
	 * 付箋タイトルの寄せを取得します
	 * 
	 * @return 付箋タイトルの寄せ
	 */
	public int getTitleColumnAlignment() {
		return SwingConstants.LEFT;
	}

	/**
	 * 検索ダイアログのテーブル保存キーを返す
	 * 
	 * @return 検索ダイアログのテーブル保存キー
	 */
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

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
	public Tag getEntity() {
		// 存在チェックをする場合、またはEntityが存在した場合、存在チェックを受けたEntityを返す
		if (checkExist || entity != null) {
			return entity;
		}
		// 存在チェックをしない場合、入力途中の未確定Entityでも返す。ただしコードが未入力の場合はnull
		if (Util.isNullOrEmpty(field.code.getText())) {
			return null;
		}
		return (Tag) getUnspecifiedEntity();

	}

	/**
	 * 存在チェックをしない場合の入力途中未確定Entityを返す
	 * 
	 * @return 存在チェックをしない場合の入力途中未確定Entity
	 */

	public Object getUnspecifiedEntity() {
		Tag entity_ = new Tag();
		entity_.setCode(field.code.getText());
		return entity_;
	}

	/**
	 * Entityをセットする
	 * 
	 * @param tag
	 */
	// 選択した情報を一覧画面にセット
	public void setEntity(Tag tag) {
		if (tag == null) {
			clear();
		} else {
			field.code.setText(tag.getCode());
			field.color.setBackground(tag.getColor());
			field.name.setText(tag.getTitle());
			field.name.setEditable(true);
			entity = tag;
		}

		field.code.pushOldText();
		field.color.clear();
		field.name.pushOldText();
	}

	/**
	 * Entityのインスタンスファクトリ
	 * 
	 * @return 新規TReferable
	 */
	public TReferable createEntity() {
		return null; // return先の確認
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.
	 */
	public void refleshEntity() {
		Tag bean = getInputtedEntity();
		setEntity(bean);
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

}
