package jp.co.ais.trans2.common.ui;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.define.*;

/**
 * バージョン表示ダイアログ
 */
public class TVersionDialog extends TDialog {

	/** プロダクト名 */
	protected static String title;

	/** パネル */
	public TPanel pnlPackage;

	/** 上ラベル：システム名 */
	public TLabel lblUp;

	/** 下ラベル：Ver.2.2 */
	public TLabel lblDown;

	/** 更新日時 */
	public TLabel lblUpdateDate;

	/** ﾌｧｲﾙﾊﾞｰｼﾞｮﾝ */
	public TLabel lblVersion;

	/** パネル */
	public TPanel pnlFooter;

	/** OK */
	public TImageButton btnOK;

	/** CTRL */
	public TVersionDialogCtrl controller;

	/**
	 * コンストラクタ.
	 * 
	 * @param frame 親フレーム
	 */
	public TVersionDialog(Frame frame) {
		super(frame, true);

		controller = createController();
	}

	/**
	 * @return CTRL
	 */
	protected TVersionDialogCtrl createController() {
		return new TVersionDialogCtrl(this);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TDialog#initComponents()
	 */
	@Override
	public void initComponents() {

		pnlPackage = new TPanel();
		lblUp = new TLabel();
		lblDown = new TLabel();
		lblUpdateDate = new TLabel();
		lblVersion = new TLabel();
		pnlFooter = new TPanel();

		btnOK = new TImageButton(IconType.SETTLE);

		title = ClientConfig.getTitle();
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TDialog#allocateComponents()
	 */
	@Override
	public void allocateComponents() {

		setTitle(getWord("C11917")); // バージョン情報
		setSize(600, 270);
		setMinimumSize(new Dimension(600, 270));
		getLayeredPane().getComponent(1).setFont(new Font("Yu Gothic UI", Font.BOLD, 16));

		pnlHeader.setVisible(false);
		sep.setVisible(false);

		pnlBody.setLayout(new GridBagLayout());

		// パッケージ表示
		pnlPackage.setBorder(BorderFactory.createEtchedBorder());
		TGuiUtil.setComponentSize(lblUp, 390, 50);
		TGuiUtil.setComponentSize(lblDown, 390, 50);

		lblUp.setHorizontalAlignment(SwingConstants.CENTER);
		lblDown.setHorizontalAlignment(SwingConstants.CENTER);

		lblUp.setIcon(TMainCtrl.getInstance().getCompanyLogo());
		lblDown.setLangMessageID(title);

		TUIManager.addFontSize(lblDown, 24);

		pnlPackage.setLayout(new BorderLayout());
		pnlPackage.add(lblUp, BorderLayout.NORTH);
		pnlPackage.add(lblDown, BorderLayout.SOUTH);

		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.NORTHWEST;
		gc.insets = new Insets(5, 5, 5, 5);
		pnlBody.add(pnlPackage, gc);

		// バージョン表示ラベル
		lblUpdateDate.setLangMessageID(getWord("C11918") + TController.jarUpdateDate); // 更新日時:
		lblVersion.setLangMessageID(getWord("C11919") + TController.jarVersion); // ﾌｧｲﾙﾊﾞｰｼﾞｮﾝ:

		TGuiUtil.setComponentSize(lblUpdateDate, 0, 22);
		TGuiUtil.setComponentSize(lblVersion, 0, 22);

		TUIManager.addFontSize(lblUpdateDate, 3);
		TUIManager.addFontSize(lblVersion, 3);

		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = new Insets(10, 10, 0, 0);
		pnlBody.add(lblUpdateDate, gc);

		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 2;
		gc.weightx = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = new Insets(10, 10, 0, 0);
		pnlBody.add(lblVersion, gc);

		TGuiUtil.setComponentSize(pnlFooter, 0, 35);
		pnlFooter.setLayout(null);

		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 3;
		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.CENTER;
		pnlBody.add(pnlFooter, gc);

		// OKボタン
		btnOK.setLangMessageID("COP635"); // OK
		btnOK.setSize(30, 125);
		btnOK.setLocation(getWidth() - 140, 0);
		pnlFooter.add(btnOK);
	}

	@Override
	public void setTabIndex() {
		int i = 1;
		btnOK.setTabControlNo(i++);

	}
}
