package jp.co.ais.trans.common.bizui;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;

/**
 * 更新区分フィールド
 * 
 * @author AIS
 */
public class TUpdateDivisionField extends TPanel {

	/** UID */
	private static final long serialVersionUID = -9181170060878639499L;

	/** ボタンレイアウト */
	private int layoutType = VERTICAL;

	/** 横 */
	public static final int HORIZONTAL = 1;

	/** 縦 */
	public static final int VERTICAL = 2;

	/** 登録 */
	protected TCheckBox chkEntry;

	/** 承認 */
	protected TCheckBox chkApprove;

	/** ベースパネル */
	protected TPanel pnlBase;

	/**
	 * @return pnlBaseを戻します。
	 */
	public TPanel getPnlBase() {
		return pnlBase;
	}

	/**
	 * @param pnlBase pnlBaseを設定します。
	 */
	public void setPnlBase(TPanel pnlBase) {
		this.pnlBase = pnlBase;
	}

	/**
	 * コンストラクタ<br>
	 * ログイン会社コード、縦で構築.
	 */
	public TUpdateDivisionField() {
		this(VERTICAL);
	}

	/**
	 * コンストラクタ<br>
	 * 
	 * @param buttonLayout ボタンレイアウト <br>
	 *            TItemLevelField.HORIZONTAL 横並び <br>
	 *            TItemLevelField.VERTICAL 縦並び<br>
	 */
	public TUpdateDivisionField(int buttonLayout) {
		super();

		this.layoutType = buttonLayout;

		initComponents();

	}

	/**
	 * レイアウト構成
	 */
	private void initComponents() {

		chkEntry = new TCheckBox();
		chkEntry.setLangMessageID("C01258");

		chkApprove = new TCheckBox();
		chkApprove.setLangMessageID("C01168");

		pnlBase = new TPanel();
		pnlBase.setLayout(new GridBagLayout());
		pnlBase.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ""));
		pnlBase.setLangMessageID("C01069");

		GridBagConstraints gridBagConstraints;
		switch (layoutType) {
			case HORIZONTAL:
				// 横並び
				pnlBase.setMaximumSize(new Dimension(130, 45));
				pnlBase.setMinimumSize(new Dimension(130, 45));
				pnlBase.setPreferredSize(new Dimension(130, 45));

				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.insets = new Insets(0, 5, 5, 0);
				pnlBase.add(chkEntry, gridBagConstraints);

				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.gridx = 1;
				gridBagConstraints.gridy = 0;
				gridBagConstraints.insets = new Insets(0, 10, 5, 0);
				pnlBase.add(chkApprove, gridBagConstraints);

				gridBagConstraints = new GridBagConstraints();
				add(pnlBase, gridBagConstraints);

				break;

			case VERTICAL:
				// 縦並び
				pnlBase.setMaximumSize(new Dimension(100, 80));
				pnlBase.setMinimumSize(new Dimension(100, 80));
				pnlBase.setPreferredSize(new Dimension(100, 80));

				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.insets = new Insets(0, 10, 5, 30);
				pnlBase.add(chkEntry, gridBagConstraints);

				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.gridx = 0;
				gridBagConstraints.gridy = 1;
				gridBagConstraints.insets = new Insets(0, 10, 5, 30);
				pnlBase.add(chkApprove, gridBagConstraints);

				gridBagConstraints = new GridBagConstraints();
				add(pnlBase, gridBagConstraints);

				break;
		}
	}

	/**
	 * 登録が選択されているかどうか
	 * 
	 * @return true:選択状態
	 */
	public boolean isChkEntrySelected() {
		return chkEntry.isSelected();
	}

	/**
	 * 承認が選択されているかどうか
	 * 
	 * @return true:選択状態
	 */
	public boolean isChkApproveSelected() {
		return chkApprove.isSelected();
	}

	/**
	 * 登録の設定
	 * 
	 * @param bol
	 */
	public void setChkApprove(boolean bol) {
		chkApprove.setSelected(bol);
	}

	/**
	 * 承認の設定
	 * 
	 * @param bol
	 */
	public void setChkEntry(boolean bol) {
		chkEntry.setSelected(bol);
	}

	/**
	 * タブ移動順番号を設定
	 * 
	 * @param no タブ順番号
	 */
	public void setTabControlNo(int no) {
		chkEntry.setTabControlNo(no);
		chkApprove.setTabControlNo(no);
	}

	/**
	 * @return chkApproveを戻します。
	 */
	public TCheckBox getChkApprove() {
		return chkApprove;
	}

	/**
	 * @param chkApprove chkApproveを設定します。
	 */
	public void setChkApprove(TCheckBox chkApprove) {
		this.chkApprove = chkApprove;
	}

	/**
	 * @return chkEntryを戻します。
	 */
	public TCheckBox getChkEntry() {
		return chkEntry;
	}

	/**
	 * @param chkEntry chkEntryを設定します。
	 */
	public void setChkEntry(TCheckBox chkEntry) {
		this.chkEntry = chkEntry;
	}

}
