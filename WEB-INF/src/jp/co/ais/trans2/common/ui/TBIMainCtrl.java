package jp.co.ais.trans2.common.ui;

import java.awt.*;
import java.awt.event.*;
import java.net.*;

import jp.co.ais.trans2.common.config.*;

/**
 * BI�{�^���ǉ��̃��C�����Ctrl
 */
public class TBIMainCtrl extends TMainCtrl {

	/**
	 * ���C����ʂ̃t�@�N�g��
	 * 
	 * @return ���C�����
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
	 * ���C����ʂ̃C�x���g��`
	 */
	@Override
	protected void initMainViewEvent() {
		super.initMainViewEvent();

		// Dr.Sum�{�^������
		getView().btnDrSum.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				btnDrSum_Click();
			}
		});
	}

	/**
	 * Dr.Sum�{�^������
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
	 * @return ����URL
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
	 * @return URL�ێ��v���p�e�B�̃L�[
	 */
	protected String getUrlKey() {
		return "trans.bi.dr.sum.login.page";
	}

}
