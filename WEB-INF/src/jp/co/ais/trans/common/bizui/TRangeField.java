package jp.co.ais.trans.common.bizui;

import java.awt.*;
import javax.swing.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.message.*;

/**
 * 範囲指定フィールド(画面原本)
 */
public abstract class TRangeField extends TPanel {

	/** パネル */
	protected TPanel pnlRangeSpecification;

	/** 開始ラベル */
	protected TLabel lblBeginItem;

	/** 終了ラベル */
	protected TLabel lblEndItem;

	/** 開始フィールド */
	protected TButtonField ctrlBeginItem;

	/** 終了フィールド */
	protected TButtonField ctrlEndItem;

	/** パネル文字の単語ID */
	protected String panelMsgId = "C03186";

	/** 開始ラベル文字単語ID */
	protected String beginLblMsgId = "C01013";

	/** 終了ラベル文字単語ID */
	protected String endLblMsgId = "C00260";

	/**
	 * 画面構築
	 */
	protected void init() {

		GridBagConstraints gridBagConstraints;

		pnlRangeSpecification = new TPanel();
		lblBeginItem = new TLabel();
		lblEndItem = new TLabel();
		ctrlBeginItem = createBeginField();
		ctrlEndItem = createEndField();

		pnlRangeSpecification.setLayout(new GridBagLayout());
		pnlRangeSpecification.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ""));
		pnlRangeSpecification.setLangMessageID(panelMsgId);
		pnlRangeSpecification.setPreferredSize(new Dimension(480, 85));

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 10, 5, 5);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		pnlRangeSpecification.add(lblBeginItem, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(0, 10, 10, 5);
		pnlRangeSpecification.add(lblEndItem, gridBagConstraints);

		lblBeginItem.setLangMessageID(beginLblMsgId);
		ctrlBeginItem.setButtonSize(75);
		ctrlBeginItem.setFieldSize(95);
		ctrlBeginItem.setNoticeSize(220);
		ctrlBeginItem.setMaxLength(10);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.PAGE_START;
		gridBagConstraints.insets = new Insets(0, 0, 5, 10);
		pnlRangeSpecification.add(ctrlBeginItem, gridBagConstraints);

		lblEndItem.setLangMessageID(endLblMsgId);
		ctrlEndItem.setButtonSize(75);
		ctrlEndItem.setFieldSize(95);
		ctrlEndItem.setNoticeSize(220);
		ctrlEndItem.setMaxLength(10);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.PAGE_START;
		gridBagConstraints.insets = new Insets(0, 0, 10, 10);
		pnlRangeSpecification.add(ctrlEndItem, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		add(pnlRangeSpecification, gridBagConstraints);
	}

	/**
	 * 開始フィールドを生成する(Orverride用)
	 * 
	 * @return 開始フィールド
	 */
	protected abstract TButtonField createBeginField();

	/**
	 * 終了フィールドを生成する
	 * 
	 * @return 終了フィールド
	 */
	protected abstract TButtonField createEndField();

	/**
	 * パネルの取得
	 * 
	 * @return TPanel
	 */
	public TPanel getBasePanel() {
		return pnlRangeSpecification;
	}

	/**
	 * 開始フィールドの取得
	 * 
	 * @return TItemField
	 */
	public TButtonField getBeginField() {
		return ctrlBeginItem;
	}

	/**
	 * 終了フィールドの取得
	 * 
	 * @return TItemField
	 */
	public TButtonField getEndField() {
		return ctrlEndItem;
	}

	/**
	 * 開始ラベルの取得
	 * 
	 * @return TLabel
	 */
	public TLabel getBeginLabel() {
		return lblBeginItem;
	}

	/**
	 * 終了ラベルの取得
	 * 
	 * @return TLabel
	 */
	public TLabel getEndLabel() {
		return lblEndItem;
	}

	/**
	 * タブ移動順番号を範囲指定コンポーネント全体に設定する.
	 * 
	 * @param no タブ順
	 * @see TInterfaceTabControl#setTabControlNo(int)
	 */
	public void setTabControlNo(int no) {
		ctrlBeginItem.setTabControlNo(no);
		ctrlEndItem.setTabControlNo(no);
	}

	/**
	 * 開始コードを取得
	 * 
	 * @return 開始コード
	 */
	public String getBeginCode() {
		return ctrlBeginItem.getField().getText().trim();
	}

	/**
	 * 終了コードを取得
	 * 
	 * @return 終了コード
	 */
	public String getEndCode() {
		return ctrlEndItem.getField().getText().trim();
	}

	/**
	 * 単語取得
	 * 
	 * @param wordId 単語ID
	 * @return 単語
	 */
	public String getWord(String wordId) {
		String lang = TClientLoginInfo.getInstance().getUserLanguage();
		return MessageUtil.getWord(lang, wordId);
	}

}
