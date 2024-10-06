package jp.co.ais.trans.common.bizui;

import java.awt.*;
import javax.swing.*;
import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.TransUtil;

/**
 * 科目レベルフィールド
 * 
 * @author roh
 */
public class TItemLevelField extends TPanel {

	/** UID */
	private static final long serialVersionUID = -9181170060878639499L;

	/** ボタンレイアウト */
	private int layoutType = VERTICAL;

	/** 会社コード */
	private String companyCode;

	/** 平行 */
	public static final int HORIZONTAL = 1;

	/** 縦 */
	public static final int VERTICAL = 2;

	/** ボタングループ */
	private ButtonGroup bGroup;

	/** 科目 */
	private TRadioButton rdo;

	/** 補助科目 */
	private TRadioButton rdoSub;

	/** 科目詳細（内訳） */
	private TRadioButton rdoDown;

	/** コントロール */
	private TItemLevelFieldCtrl ctrl;

	/**
	 * コンストラクタ<br>
	 * ログイン会社コード、縦で構築.
	 */
	public TItemLevelField() {
		this(VERTICAL);
	}

	/**
	 * コンストラクタ.<br>
	 * ログイン会社コードで構築.
	 * 
	 * @param buttonLayout <br>
	 *            TItemLevelField.HORIZONTAL 横並び <br>
	 *            TItemLevelField.VERTICAL 縦並び<br>
	 */
	public TItemLevelField(int buttonLayout) {
		this(TClientLoginInfo.getInstance().getCompanyCode(), buttonLayout);
	}

	/**
	 * コンストラクタ<br>
	 * 縦で構築.
	 * 
	 * @param companyCode 会社コード
	 */
	public TItemLevelField(String companyCode) {
		this(companyCode, VERTICAL);
	}

	/**
	 * コンストラクタ<br>
	 * 
	 * @param companyCode 会社コード
	 * @param buttonLayout ボタンレイアウト <br>
	 *            TItemLevelField.HORIZONTAL 横並び <br>
	 *            TItemLevelField.VERTICAL 縦並び<br>
	 */
	public TItemLevelField(String companyCode, int buttonLayout) {
		super();

		this.companyCode = companyCode;
		this.layoutType = buttonLayout;

		initComponents();

		this.ctrl = new TItemLevelFieldCtrl(this);
	}

	/**
	 * レイアウト構成
	 */
	private void initComponents() {
		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ""));
		setLangMessageID("C00906");

		bGroup = new ButtonGroup();

		rdo = new TRadioButton();
		rdo.setSelected(true);
		rdo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		bGroup.add(rdo);

		rdoSub = new TRadioButton();
		rdoSub.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		bGroup.add(rdoSub);

		rdoDown = new TRadioButton();
		rdoDown.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		bGroup.add(rdoDown);

		GridBagConstraints gridBagConstraints;
		switch (layoutType) {
			case HORIZONTAL:
				// 横並びの場合。
				setPreferredSize(new Dimension(240, 55));

				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.insets = new Insets(0, 0, 0, 15);
				gridBagConstraints.anchor = GridBagConstraints.WEST;
				gridBagConstraints.gridx = 0;
				gridBagConstraints.gridy = 0;
				add(rdo, gridBagConstraints);

				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.insets = new Insets(0, 0, 0, 15);
				gridBagConstraints.anchor = GridBagConstraints.WEST;
				gridBagConstraints.gridx = 1;
				gridBagConstraints.gridy = 0;
				add(rdoSub, gridBagConstraints);

				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.insets = new Insets(0, 0, 0, 0);
				gridBagConstraints.anchor = GridBagConstraints.WEST;
				gridBagConstraints.gridx = 2;
				gridBagConstraints.gridy = 0;
				add(rdoDown, gridBagConstraints);
				break;

			case VERTICAL:
				// 縦並びの場合。
				setPreferredSize(new Dimension(120, 80));

				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.insets = new Insets(0, 0, 4, 0);
				gridBagConstraints.anchor = GridBagConstraints.WEST;
				gridBagConstraints.gridx = 0;
				gridBagConstraints.gridy = 0;
				add(rdo, gridBagConstraints);

				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.insets = new Insets(0, 0, 4, 0);
				gridBagConstraints.anchor = GridBagConstraints.WEST;
				gridBagConstraints.gridx = 0;
				gridBagConstraints.gridy = 1;
				add(rdoSub, gridBagConstraints);

				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.insets = new Insets(0, 0, 4, 0);
				gridBagConstraints.anchor = GridBagConstraints.WEST;
				gridBagConstraints.gridx = 0;
				gridBagConstraints.gridy = 2;
				add(rdoDown, gridBagConstraints);
				break;
		}
	}

	/**
	 * 会社コード習得
	 * 
	 * @return companyCode 会社コード
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * 会社コード設定
	 * 
	 * @param companyCode 会社コード
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
		this.ctrl.initControl();
	}

	/**
	 * 科目ボタン取得
	 * 
	 * @return 科目ボタン
	 */
	public TRadioButton getItemButton() {
		return rdo;
	}

	/**
	 * 補助科目ボタン取得
	 * 
	 * @return 補助科目ボタン
	 */
	public TRadioButton getSubItemButton() {
		return rdoSub;
	}

	/**
	 * 内訳科目ボタン取得
	 * 
	 * @return 内訳科目ボタン
	 */
	public TRadioButton getBreakDownItemButton() {
		return rdoDown;
	}

	/**
	 * 科目が選択されているかどうか
	 * 
	 * @return true:選択状態
	 */
	public boolean isItemSelected() {
		return rdo.isSelected();
	}

	/**
	 * 補助科目が選択されているかどうか
	 * 
	 * @return true:選択状態
	 */
	public boolean isSubItemSelected() {
		return rdoSub.isSelected();
	}

	/**
	 * 内訳科目が選択されているかどうか
	 * 
	 * @return true:選択状態
	 */
	public boolean isBreakDownItemSelected() {
		return rdoDown.isSelected();
	}

	/**
	 * タブ移動順番号を設定
	 * 
	 * @param no タブ順番号
	 */
	public void setTabControlNo(int no) {
		rdo.setTabControlNo(no);
		rdoSub.setTabControlNo(no);
		rdoDown.setTabControlNo(no);
	}

	/**
	 * 選択された科目レベルを返す。
	 * @return 選択された科目レベル
	 */
	public TransUtil.ItemLevel getSelectedItemLevel() {
		if (isItemSelected()) {
			return TransUtil.ItemLevel.Item;
		} else if (isSubItemSelected()) {
			return TransUtil.ItemLevel.SubItem;
		} else if (isBreakDownItemSelected()) {
			return TransUtil.ItemLevel.DetailItem;	
		}
		return null;
	}

}
