package jp.co.ais.trans2.common.ui;

import java.awt.*;
import java.awt.event.*;
import java.net.*;

import jp.co.ais.trans2.common.config.*;

/**
 * BIボタン追加のメイン画面Ctrl
 */
public class TBIMainCtrl extends TMainCtrl {

	/**
	 * メイン画面のファクトリ
	 * 
	 * @return メイン画面
	 */
	@Override
	protected TMain createMainView() {
		return new TBIMain();
	}

	@Override
	public TBIMain getView() {
		return (TBIMain) mainView;
	}

	/**
	 * メイン画面のイベント定義
	 */
	@Override
	protected void initMainViewEvent() {
		super.initMainViewEvent();

		// Dr.Sumボタン押下
		getView().btnDrSum.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				btnDrSum_Click();
			}
		});
	}

	/**
	 * Dr.Sumボタン押下
	 */
	protected void btnDrSum_Click() {
		try {
			Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
			if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
				URI url = new URI(getInitUrl());
				desktop.browse(url);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return 初期URL
	 */
	protected String getInitUrl() {
		String url = null;

		try {
			url = ClientConfig.getProperty(getUrlKey());
		} catch (Exception e) {
			url = "http://www.a-i-s.co.jp";
		}
		return url;
	}

	/**
	 * @return URL保持プロパティのキー
	 */
	protected String getUrlKey() {
		return "trans.bi.dr.sum.login.page";
	}

}
