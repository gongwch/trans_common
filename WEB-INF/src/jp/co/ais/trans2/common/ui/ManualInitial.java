package jp.co.ais.trans2.common.ui;

import java.awt.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * ダッシュボード画面レイアウト
 * 
 * @author AIS
 */
public class ManualInitial extends TDialog {

	/** マニュアル添付ボタン */
	public TImageButton btnManual;

	/**
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public ManualInitial(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TDialog#initComponents()
	 */
	@Override
	public void initComponents() {
		btnManual = new TImageButton(IconType.HELP);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TDialog#allocateComponents()
	 */
	@Override
	public void allocateComponents() {
		TGuiUtil.setComponentSize(btnManual, 30, 30);
		btnManual.setFocusable(false);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TDialog#setTabIndex()
	 */
	@Override
	public void setTabIndex() {
		//
	}

}
