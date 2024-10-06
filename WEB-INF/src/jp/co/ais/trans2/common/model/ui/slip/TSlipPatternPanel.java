package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.event.*;

import javax.swing.*;

/**
 * パターン入力パネル
 */
public abstract class TSlipPatternPanel extends TSlipPanel {

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainPanel#initComponents()
	 */
	@Override
	public void initComponents() {
		super.initComponents();

		// ヘッダ共通

		// 伝票日付
		ctrlSlipDate.setAllowableBlank(true);
		ctrlSlipDate.setEditable(false);
		ctrlSlipDate.setValue(null); // 日付なし

		// 決算段階
		ctrlCloseEntry.setNotVisibleStage(); // チェックボックスのみ

		// パターン番号
		ctrlSlipNo.setLangMessageID("C00987");
		ctrlSlipNo.setLabelSize(75);
		ctrlSlipNo.setEditable(true); // 入力可

		// 明細
		ctrlDetail = createPatternDetailPanel();
	}

	/**
	 * 明細パネル作成
	 * 
	 * @return 明細パネル
	 */
	public TSlipDetailPanel createPatternDetailPanel() {

		if (isTFormMode()) {
			return new TFormSlipPatternDetailPanel(this);
		}
		return new TSlipPatternDetailPanel();
	}

	/**
	 * ボタン部の配置<br>
	 * 仕訳辞書ボタンなし
	 */
	@Override
	public void allocateButtons() {

		int x = X_POINT;

		// switchNew();
		lblState.setSize(30, 25);
		lblState.setHorizontalAlignment(SwingConstants.CENTER);
		lblState.setOpaque(true);
		lblState.setLocation(x, HEADER_Y);
		pnlHeader.add(lblState);

		// 新規ボタン
		x = x + lblState.getWidth() + HEADER_MARGIN_X;
		btnNew.setLangMessageID("C00303");
		btnNew.setShortcutKey(KeyEvent.VK_F2);
		btnNew.setSize(25, 110);
		btnNew.setLocation(x, HEADER_Y);
		pnlHeader.add(btnNew);

		// 修正
		x = x + btnNew.getWidth() + HEADER_MARGIN_X;
		btnModify.setLangMessageID("C01760");
		btnModify.setShortcutKey(KeyEvent.VK_F3);
		btnModify.setSize(25, 110);
		btnModify.setLocation(x, HEADER_Y);
		pnlHeader.add(btnModify);

		// 複写
		x = x + btnModify.getWidth() + HEADER_MARGIN_X;
		btnCopy.setLangMessageID("C00459");
		btnCopy.setShortcutKey(KeyEvent.VK_F4);
		btnCopy.setSize(25, 110);
		btnCopy.setLocation(x, HEADER_Y);
		pnlHeader.add(btnCopy);

		// 削除
		x = x + btnCopy.getWidth() + HEADER_MARGIN_X;
		btnDelete.setLangMessageID("C01544");
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
		btnDelete.setSize(25, 110);
		btnDelete.setLocation(x, HEADER_Y);
		pnlHeader.add(btnDelete);

		// 確定
		x = x + btnDelete.getWidth() + HEADER_MARGIN_X;
		btnEntry.setLangMessageID("C01019");
		btnEntry.setShortcutKey(KeyEvent.VK_F8);
		btnEntry.setSize(25, 110);
		btnEntry.setLocation(x, HEADER_Y);
		pnlHeader.add(btnEntry);
	}
}
