package jp.co.ais.trans.master.common;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.klg.jclass.table.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * REFDialogのCtrlクラス。<br>
 * このクラスは jp.co.ais.trans.master.common.REFDisplayDialogCtrl から改善される。 <br>
 * プログラム特有の「REFKind」等のものを回避して、システム全般の汎用を目指す。
 * 
 * @author ISFnet China: lipu
 */
public class REFDialogCtrl extends TAppletClientBase {

	/** 検索ダイアログ * */
	private REFDialog dialog;

	/** TButtonField コントロール * */
	private TButtonField buttonField;

	/** TButtonField コントロール * */
	private TButtonField passivityButtonField;

	/** 画面 * */
	private Object parent;

	/** プログラム特有の機能にサポート為のリスナー * */
	private REFListener listener;

	/** 送信先のServlet * */
	private String targetServlet = "";

	/** 表題ID * */
	private String titleID = "";

	/** スプレッドテーブルの第一列の表題 * */
	String columnLabel1 = "C00174";

	/** スプレッドテーブルの第二列の表題 * */
	String columnLabel2 = "C00548";

	/** スプレッドテーブルの第三列の表題 * */
	String columnLabel3 = "C00160";

	/** エラー時、メッセージを表示するか？default = true * */
	private boolean showsMsgOnError = true;

	/** the last user inputted code. * */
	private String lastCode;

	/** lastCode is whether or not valid * */
	private boolean isLastCodeValid = true;

	/**
	 * whether or not the focusLost event is fired by clicking an ignored button<br>
	 * (例えれば、「戻る」ボタンを押下時)
	 */
	private boolean isClickingIgnoredButton = false;

	/**
	 * コンストラクタ
	 * 
	 * @param buttonField TButtonFieldコントロール
	 * @param parent 画面
	 */
	public REFDialogCtrl(TButtonField buttonField, Object parent) {
		this.buttonField = buttonField;

		this.parent = parent;

		showsMsgOnError = true;

		// add listeners for the button
		REFButtonWatcher buttonWatcher = new REFButtonWatcher(this);
		this.buttonField.getButton().addActionListener(buttonWatcher);

	}

	/**
	 * コンストラクタ
	 * 
	 * @param buttonField TButtonFieldコントロール
	 * @param passivityButtonField TButtonFieldコントロール
	 * @param parent 画面
	 */
	public REFDialogCtrl(TButtonField buttonField, TButtonField passivityButtonField, Object parent) {
		this.buttonField = buttonField;
		this.passivityButtonField = passivityButtonField;

		this.parent = parent;

		showsMsgOnError = true;

		// add listeners for the button
		REFButtonWatcher buttonWatcher = new REFButtonWatcher(this);
		this.buttonField.getButton().addActionListener(buttonWatcher);

	}

	/**
	 * プログラム特有の機能にサポート為のリスナー
	 * 
	 * @param listener
	 */
	public void setREFListener(REFListener listener) {
		this.listener = listener;
	}

	/**
	 * 送信先のServlet
	 * 
	 * @param targetServlet
	 */
	public void setTargetServlet(String targetServlet) {
		this.targetServlet = targetServlet;
	}

	/**
	 * 送信先のServletの取得
	 * 
	 * @return 送信先のServlet
	 */
	public String getTargetServlet() {
		return targetServlet;
	}

	/**
	 * 表題ID
	 * 
	 * @param titleID
	 */
	public void setTitleID(String titleID) {
		this.titleID = titleID;
	}

	/**
	 * スプレッドテーブルの各列の表題
	 * 
	 * @param columnLabel1 第一列の表題
	 * @param columnLabel2 第二列の表題、無の場合「null」をください
	 * @param columnLabel3 第三列の表題、無の場合「null」をください
	 */
	public void setColumnLabels(String columnLabel1, String columnLabel2, String columnLabel3) {
		this.columnLabel1 = columnLabel1;
		this.columnLabel2 = columnLabel2;
		this.columnLabel3 = columnLabel3;
	}

	/**
	 * スプレッドテーブルの各列の表題の単語ID
	 * 
	 * @param columnLabelID1 第一列の表題の単語ID
	 * @param columnLabelID2 第二列の表題の単語ID、無の場合「null」をください
	 * @param columnLabelID3 第三列の表題の単語ID、無の場合「null」をください
	 */
	public void setColumnLabelIDs(String columnLabelID1, String columnLabelID2, String columnLabelID3) {
		this.columnLabel1 = getWord(columnLabelID1);
		this.columnLabel2 = getWord(columnLabelID2);
		this.columnLabel3 = getWord(columnLabelID3);
	}

	/**
	 * エラー時、メッセージを表示するか？
	 * 
	 * @param showsMsgOnError
	 */
	public void setShowsMsgOnError(boolean showsMsgOnError) {
		this.showsMsgOnError = showsMsgOnError;
	}

	/**
	 * 「戻る」等のボタンを押下時、REFのfocusLostを無視するため
	 * 
	 * @param button 「戻る」等のボタン
	 * @deprecated 利用するな
	 */
	public void addIgnoredButton(JButton button) {
		//
	}

	/**
	 * 名称表示の強制されるリフレッシュする
	 */
	public void refreshName() {
		try {
			refreshName(false);

		} catch (IOException ex) {
			errorHandler(dialog, ex);
		}
	}

	/**
	 * 入力コード値の検索結果
	 * 
	 * @return 入力コードの存在可否
	 */
	public boolean isCodeValid() {
		return isLastCodeValid;
	}

	// ********** 左のButtonの処理 **********

	/**
	 * This class is used for watching the button of the TButtonField.<brs> It listens both mouse and action events.
	 */
	private class REFButtonWatcher implements ActionListener {

		REFDialogCtrl dialogCtrl;

		REFButtonWatcher(REFDialogCtrl dialogCtrl) {
			this.dialogCtrl = dialogCtrl;
		}

		// when the button has been clicked
		public void actionPerformed(ActionEvent ev) {

			// if no dialog instance has been created, create one.
			dialog = null;
			String dialogTitleID = null;
			dialogTitleID = dialogCtrl.getWord(dialogCtrl.titleID) + dialogCtrl.getWord("C00010");

			if (parent instanceof Dialog) {
				dialog = new REFDialog((Dialog) parent, true, dialogCtrl, dialogTitleID);
			} else {
				dialog = new REFDialog((Frame) parent, true, dialogCtrl, dialogTitleID);
			}

			dialog.setSize(700, 430);
			initREFDialog();

			// clear the previous data
			Vector cells = new Vector();
			dialogSetDataList(cells);

			dialog.txtCode.setValue("");
			dialog.txtAbbreviationName.setValue("");
			dialog.txtNameForSearch.setValue("");

			dialog.txtCode.requestFocus();

			// ｺｰﾄﾞ設定、自動検索
			if (!Util.isNullOrEmpty(buttonField.getValue())) {
				dialog.txtCode.setValue(buttonField.getValue());
				dialogFinds(false);
			}

			dialog.setVisible(true);
		}
	}

	/**
	 * refresh the Name field (the TTextField on the right side)
	 * 
	 * @param requestsFocusOnError whether or not re-focus the field when error occurs
	 * @throws IOException
	 */
	protected void refreshName(boolean requestsFocusOnError) throws IOException {
		// if 「戻る」-like buttons clicked, ignore it
		if (isClickingIgnoredButton) return;

		if (this.buttonField.getOldValue() != null) {
			lastCode = this.buttonField.getOldValue().trim();
		} else {
			lastCode = "";
		}

		this.buttonField.setValue(this.buttonField.getField().getText().trim());

		// first assuming code not changed
		boolean codeChanged = false;

		// if the input isn't changed, don't check DB.
		if (lastCode != null && lastCode.equals(this.buttonField.getField().getText())) {

			// if lastCode is empty, clear the name field ...
			if (Util.isNullOrEmpty(lastCode)) {
				isLastCodeValid = true;
				this.buttonField.getNotice().setEditable(true);
				this.buttonField.getNotice().setText("");
				this.buttonField.getNotice().setEditable(false);

				// fire the textCleared event of REFListener
				if (listener != null) {
					listener.textCleared();
				}

			} else if (isLastCodeValid) {

				// if lastCode is valid and is not empty, return
				if (!"".equals(this.buttonField.getNotice().getText())) {
					return;
				}
			} else if (!Util.isNullOrEmpty(lastCode)) {

				// if lastCode is not valid and is not empty, display an error and return
				codeNotExists(requestsFocusOnError);
				return;
			}
		} else {

			codeChanged = (lastCode != null);

			// record the current code
			lastCode = this.buttonField.getField().getText();
		}

		if (Util.isNullOrEmpty(lastCode)) {

			// code is empty, clear the name field ...
			isLastCodeValid = true;
			this.buttonField.getNotice().setEditable(true);
			this.buttonField.getNotice().setText("");
			this.buttonField.getNotice().setEditable(false);

			// 上位会社は押下可且つ入力済みの場合、会社押下可、他の場合逆
			boolean isEdit = !Util.isNullOrEmpty(buttonField.getNotice().getText());

			if (null != passivityButtonField) {

				this.passivityButtonField.setValue("");
				this.passivityButtonField.setNoticeValue("");

				this.passivityButtonField.getButton().setEnabled(isEdit);
				this.passivityButtonField.getField().setEditable(isEdit);
			}

			// fire the textCleared event of REFListener
			if (listener != null) {
				listener.textCleared();
			}

		} else {

			// DBへ検索して、該当コードが存在するどうか確認させる
			// 送信するパラメータを設定
			addSendValues("flag", "findname");

			// langCode: some programs need to support multi-languages,
			// for example, 「銀行口座マスタ」REFのデータは多言語表示を対応する。
			addSendValues("langCode", super.getLoginLanguage());

			// code: to query for the name, which is matching this code in DB.
			addSendValues("code", lastCode);

			// append additional keys for querying ...
			if (listener != null) {
				Map keys = listener.primaryKeysAppending();
				if (keys != null) {
					Iterator iterator = keys.entrySet().iterator();
					while (iterator.hasNext()) {
						Map.Entry entry = (Map.Entry) iterator.next();
						addSendValues(entry.getKey().toString(), entry.getValue().toString());
					}
				}
			}

			if (!request(targetServlet)) {
				errorHandler(dialog);
				return;
			}

			List result = super.getResultList();

			if (result.size() == 0) {

				// 該当コードは存在しません。
				// mark isLastCodeValid as false
				codeNotExists(requestsFocusOnError);
				isLastCodeValid = false;

			} else {

				// mark isLastCodeValid as true
				isLastCodeValid = true;

				// read the first record from the result.
				List inner = (List) result.get(0);

				// read the first item in the inner list and display it
				this.buttonField.getNotice().setEditable(true);
				this.buttonField.getNotice().setText(inner.get(0).toString());
				this.buttonField.getNotice().setEditable(false);

				if (null != passivityButtonField && !"".equals(this.buttonField.getField().getText())) {
					// 上位会社は押下可且つ入力済みの場合、会社押下可、他の場合逆
					boolean isEdit = !Util.isNullOrEmpty(buttonField.getNotice().getText());

					this.passivityButtonField.getButton().setEnabled(isEdit);
					this.passivityButtonField.getField().setEditable(isEdit);

					this.passivityButtonField.setValue("");
					this.passivityButtonField.setNoticeValue("");
				}

				// fire the codeChanged event
				if (requestsFocusOnError && codeChanged) {
					if (listener != null) {
						listener.codeChanged();
					}
				}

				// fire the goodCodeInputted event
				if (listener != null) {
					if (!buttonField.getField().isEditable() || !buttonField.getField().isEnabled()) {
						return;
					}

					listener.goodCodeInputted();
				}
			}
		}
	}

	/**
	 * @param prefixID
	 */
	public void setPrefixID(String prefixID) {

		if (prefixID != null) {
			this.columnLabel1 = prefixID + getWord("C00174");
			this.columnLabel2 = prefixID + getWord("C00548");
			this.columnLabel3 = prefixID + getWord("C00160");
		}
	}

	/**
	 * when name refreshing fails, run into this method
	 * 
	 * @param requestsFocusOnError whether or not re-focus the field when error occurs
	 */
	protected void codeNotExists(boolean requestsFocusOnError) {

		// clear the name field first
		this.buttonField.getNotice().setEditable(true);
		this.buttonField.getNotice().setText("");
		this.buttonField.getNotice().setEditable(false);

		if (showsMsgOnError) {

			// show error message
			showMessage(dialog, "W00081", lastCode);

			if (requestsFocusOnError) {
				// requestFocus need to wait for this method finishing running.
				buttonField.getField().requestFocus();
			}
		}

		// fire badCodeInputted event
		if (listener != null) {
			listener.badCodeInputted();
		}
	}

	// ********** REFDialogの処理 **********

	/**
	 * 画面初期化
	 */
	protected void initREFDialog() {

		// ***** １．必要なイベントリスナーの登記 *****

		// 「画面デザイン規約」の「画面項目設計」の「5． 検索画面の動き」により、
		// Enterキーを押した場合、コードやNo.を、検索画面の呼び出し元画面に返す。
		dialog.ssJournal.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent ev) {
				// ENTER 押下時、btnSettleを押下する
				if (ev.getKeyCode() == KeyEvent.VK_ENTER) {
					dialog.ssJournal.dispatchEvent(new KeyEvent(dialog.ssJournal, KeyEvent.KEY_PRESSED, 0, 0,
						KeyEvent.VK_UP, KeyEvent.CHAR_UNDEFINED));
					dialog.btnSettle.doClick();
				}
			}
		});

		// ***** ４．その他 *****

		// ダイアログを閉じ時、「戻り」ボタンの押下処理と同じ
		dialog.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent ev) {
				dialog.btnCancel.doClick();
			}
		});

		// 画面サイズ固定
		dialog.setResizable(false);
	}

	/**
	 * 検索処理
	 */
	protected void dialogFinds() {
		dialogFinds(true);
	}

	/**
	 * 検索処理
	 * 
	 * @param msgFlg メッセージ表示フラグ true：表示 false:表示しない
	 */
	protected void dialogFinds(boolean msgFlg) {
		try {
			this.lastCode = buttonField.getValue();
			String code = dialog.txtCode.getText();
			String name_S = dialog.txtAbbreviationName.getText();
			String name_K = dialog.txtNameForSearch.getText();

			dialog.btnSettle.setEnabled(false);

			// DBへ検索
			// 送信するパラメータを設定
			addSendValues("flag", "findref");
			addSendValues("langCode", super.getLoginLanguage());

			addSendValues("code", StringUtil.convertPrm(code));
			addSendValues("name_S", StringUtil.convertPrm(name_S));
			addSendValues("name_K", StringUtil.convertPrm(name_K));

			// append additional keys for querying ...
			if (listener != null) {
				Map keys = listener.primaryKeysAppending();
				if (keys != null) {
					Iterator iterator = keys.entrySet().iterator();
					while (iterator.hasNext()) {
						Map.Entry entry = (Map.Entry) iterator.next();
						addSendValues(entry.getKey().toString(), entry.getValue().toString());
					}
				}
			}

			if (!request(targetServlet)) {
				errorHandler(dialog);
				return;
			}

			List result = super.getResultList();
			// サーブレットから送られてきた結果を配列にセット
			Vector<Vector> cells = new Vector<Vector>();

			Iterator recordIte = result.iterator();
			for (int row = 0; recordIte.hasNext(); row++) {
				Iterator dataIte = ((List) recordIte.next()).iterator();

				Vector colum = new Vector();

				for (int column = 0; dataIte.hasNext(); column++) {
					colum.add(column, dataIte.next());
				}
				cells.add(row, colum);
			}
			dialogSetDataList(cells);

			if (result.size() == 0) {
				if (msgFlg) {
					super.showMessage(dialog, "W00100");
				}
				dialog.txtCode.requestFocus();
			} else {
				dialog.ssJournal.requestFocus();
			}

		} catch (IOException e) {
			errorHandler(dialog, e, "E00009");
		}
	}

	/*
	 * スプレッドデータの設定
	 */
	protected void dialogSetDataList(Vector cells) {
		dialog.ds.setCells(cells);
		dialog.ds.setNumRows(cells.size());

		dialog.ssJournal.setDataSource(dialog.ds);
		dialog.btnSettle.setEnabled(cells.size() > 0);
	}

	/**
	 * 「確定」ボタンを押下
	 */
	protected void dialogSettle() {
		int nomRow = dialog.ssJournal.getCurrentRow();
		TableDataModel model = dialog.ssJournal.getDataView();

		dialog.selectedCode = (String) model.getTableDataItem(nomRow, 0); // コード
		dialog.selectedName_S = (String) model.getTableDataItem(nomRow, 1); // 略称
		dialogClosed();
	}

	/**
	 * REFDialogを閉じる
	 */
	protected void dialogClosed() {

		dialog.setVisible(false);

		if (dialog.selectedCode != null) {
			this.buttonField.setValue(dialog.selectedCode);
			this.buttonField.getNotice().setEditable(true);
			this.buttonField.getNotice().setText(dialog.selectedName_S);
			this.buttonField.getNotice().setEditable(false);
			this.isLastCodeValid = true;

			buttonField.getField().requestFocus();

			// fire goodCodeInputted event
			if (listener != null) {
				listener.goodCodeInputted();
			}

			if (!this.lastCode.equals(dialog.selectedCode)) {
				listener.codeChanged();
			}

		} else {
			// re-focus the code field
			buttonField.getField().requestFocus();
		}

		if (lastCode != null && !lastCode.equals(this.buttonField.getField().getText())
			&& !"".equals(this.buttonField.getField().getText())) {
			if (null != passivityButtonField) {
				// 上位会社は押下可且つ入力済みの場合、会社押下可、他の場合逆
				boolean isEdit = !Util.isNullOrEmpty(buttonField.getNotice().getText());

				this.passivityButtonField.getButton().setEnabled(isEdit);
				this.passivityButtonField.getField().setEditable(isEdit);

				this.passivityButtonField.setValue("");
				this.passivityButtonField.setNoticeValue("");
			}
		}
	}

	/**
	 * REF画面のｺｰﾄﾞを設定する
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		dialog.txtCode.setValue(code);
	}
}
