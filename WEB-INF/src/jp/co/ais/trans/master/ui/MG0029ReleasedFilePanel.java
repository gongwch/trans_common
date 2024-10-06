package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * リリースファイル一覧出力パネル
 * 
 * @author roh
 */
public class MG0029ReleasedFilePanel extends TPanelBusiness {

	/** UID */
	private static final long serialVersionUID = 6864139718117925182L;

	/** コントロール */
	private MG0029ReleasedFilePanelCtrl ctrl;

	/** ボタンパネル */
	public TMainHeaderPanel pnlButton;

	/** エクセル出力ボタン */
	public TImageButton btnExcel;

	/**
	 * コンストラクタ
	 * 
	 * @param ctrl 画面コントロール
	 */
	public MG0029ReleasedFilePanel(MG0029ReleasedFilePanelCtrl ctrl) {
		this.ctrl = ctrl;
		initComponents();
		super.initPanel();
	}

	/**
	 * 画面構築
	 */
	private void initComponents() {
		pnlButton = new TMainHeaderPanel();
		btnExcel = new TImageButton(IconType.EXCEL);

		// 基本レイアウト
		setLayout(new BorderLayout());
		setMaximumSize(new Dimension(800, 600));
		setMinimumSize(new Dimension(800, 600));
		setPreferredSize(new Dimension(800, 600));

		// ボタンパネルレイアウト
		pnlButton.setLayout(null);
		pnlButton.setMaximumSize(new Dimension(800, 45));
		pnlButton.setMinimumSize(new Dimension(800, 45));
		pnlButton.setPreferredSize(new Dimension(800, 45));

		// エクセルボタン
		btnExcel.setLangMessageID("C01545");
		btnExcel.setShortcutKey(KeyEvent.VK_F6);
		btnExcel.setSize(25, 110);
		btnExcel.setLocation(5, 10);

		btnExcel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				ctrl.exportToExcel();
			}

		});

		pnlButton.add(btnExcel);

		add(pnlButton, BorderLayout.NORTH);
	}

}
