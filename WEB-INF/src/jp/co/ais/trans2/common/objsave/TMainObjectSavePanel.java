package jp.co.ais.trans2.common.objsave;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.dnd.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 状態保存Mainパネル
 * 
 * @author AIS
 */
public abstract class TMainObjectSavePanel extends TMainPanel {

	/** true:一時保存機能有効 */
	public static boolean isUseObjectSave = ClientConfig.isFlagOn("trans.use.temp.save");

	/** メインパネル */
	public TDnDPanel pnlDnDMain;

	/** 保存用リスト */
	public TDnDList lstDnDSave;

	/**
	 * コンストラクタ.
	 */
	public TMainObjectSavePanel() {
		super();
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param company
	 */
	public TMainObjectSavePanel(Company company) {
		super(company);
	}

	/**
	 * 初期化
	 */
	@Override
	protected void init() {
		super.init();

		if (!isUseObjectSave) {
			// 無効の場合、処理不要
			return;
		}

		removeAll();

		// 保存用パネル
		this.pnlDnDMain = createDnDPanel();
		this.lstDnDSave = createDnDList();

		pnlDnDMain.setMinimumSize(new Dimension(0, 0));
		lstDnDSave.setMinimumSize(new Dimension(0, 0));

		lstDnDSave.setPreferredSize(new Dimension(50, 800));

		JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlDnDMain, lstDnDSave);
		jSplitPane.setContinuousLayout(true);
		jSplitPane.setOneTouchExpandable(true);
		jSplitPane.setDividerSize(6);
		jSplitPane.setDividerLocation(2000);

		gc = new GridBagConstraints();

		pnlDnDMain.setLayout(new GridBagLayout());

		// ヘッダー領域
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlDnDMain.add(pnlHeader, gc);

		// 境界線
		gc.gridy = 1;
		pnlDnDMain.add(sep, gc);

		// ボディ領域
		gc.gridy = 2;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		pnlDnDMain.add(pnlBody, gc);

		setLayout(new BorderLayout());
		this.add(jSplitPane, BorderLayout.CENTER);
	}

	/**
	 * @see javax.swing.JComponent#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean isVisible) {
		super.setVisible(isVisible);
	}

	/**
	 * DnDList生成
	 * 
	 * @return DnDList
	 */
	protected TDnDList createDnDList() {
		return new TDnDList();
	}

	/**
	 * DnDPanel生成
	 * 
	 * @return DnDPanel
	 */
	protected TDnDPanel createDnDPanel() {
		return new TDnDPanel();
	}

	/**
	 * 保存用リスナー設定
	 * 
	 * @param listener リスナー
	 */
	public void addSaveListener(TObjectSaveListener listener) {
		if (!isUseObjectSave) {
			// 無効の場合、処理不要
			return;
		}

		listener.setPanel(this);
		listener.readObjectList();
	}
}
