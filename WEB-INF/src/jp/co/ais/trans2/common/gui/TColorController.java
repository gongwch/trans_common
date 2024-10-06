package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;

/**
 * 色選択コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TColorController extends TController {

	/** 色選択コンポーネント */
	protected TColor field;

	/** 色選択ダイアログ */
	protected TColorDialog dialog = null;

	/** ボタン、色選択ダイアログのキャプション */
	protected String caption = "";

	/** 選択中の色 */
	protected Color color = null;

	/**
	 * コンストラクタ
	 * 
	 * @param field 色選択コンポーネント
	 */
	public TColorController(TColor field) {

		this.field = field;

		// 初期化
		init();

	}

	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * 初期処理
	 */
	protected void init() {

		// イベントを追加する
		addEvent();

		field.ctrlColor.setEditable(false);

	}

	/**
	 * イベント定義
	 */
	protected void addEvent() {

		// ボタン押下
		field.btn.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				btn_Click();
			}
		});

	}

	/**
	 * ダイアログの初期処理
	 */
	protected void initDialog() {

		dialog = new TColorDialog(field.getParentFrame(), true);
		dialog.setTitle(caption);

		// イベント定義
		addDialogEvent();

	}

	/**
	 * 個別選択ダイアログのイベント定義
	 */
	protected void addDialogEvent() {

		// 確定ボタン押下
		dialog.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				btnSettle_Click();
			}
		});

		// 戻るボタン押下
		dialog.btnBack.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				btnBack_Click();
			}
		});

	}

	/**
	 * ボタン押下
	 */
	protected void btn_Click() {

		// 個別選択ダイアログ取得
		if (dialog == null) {
			initDialog();
		}

		// 色をセット
		dialog.colorChooser.setColor(color);

		// 表示
		dialog.setVisible(true);

	}

	/**
	 * [確定]ボタン押下
	 */
	protected void btnSettle_Click() {
		color = dialog.colorChooser.getColor();
		setColor(dialog.colorChooser.getColor());
		dialog.setVisible(false);
	}

	/**
	 * [戻る]ボタン押下
	 */
	protected void btnBack_Click() {
		dialog.setVisible(false);
	}

	@Override
	public void start() {
		//
	}

	/**
	 * caption設定
	 * 
	 * @param caption caption
	 */
	public void setCaption(String caption) {
		this.caption = caption;
		if (dialog != null) {
			dialog.setTitle(caption);
		}
		if (field.btn != null) {
			field.btn.setLangMessageID(caption);
		}
	}

	/**
	 * 色をセットする
	 * 
	 * @param color 色
	 */
	public void setColor(Color color) {
		this.color = color;
		field.ctrlColor.setBackground(color);
	}

	/**
	 * 選択された色を返す
	 * 
	 * @return 選択された色
	 */
	public Color getColor() {
		return color;
	}

}
