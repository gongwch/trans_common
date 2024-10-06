package jp.co.ais.trans2.common.model.ui;

import java.awt.*;
import java.util.List;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.code.*;

/**
 * コードRadioのコントローラ
 * 
 * @author AIS
 */
public class TCodeRadioController extends TController {

	/** Radio */
	protected TCodeRadio panel;

	/**
	 * @param panel フィールド
	 */
	public TCodeRadioController(TCodeRadio panel) {
		this.panel = panel;

		init();

	}

	/**
	 * 初期化
	 */
	protected void init() {

		// Radio生成
		getList();

	}

	/**
	 * Radioのリストを返す
	 */
	protected void getList() {

		try {

			panel.clear();
			int totalWidth = 0;

			List<OP_CODE_MST> list = OPLoginUtil.getCodeMstList(panel.isLocal(), panel.getCodeDivision(),
				panel.getCodes());

			if (TUIManager.defaultFont != null) {
				panel.setFont(TUIManager.defaultFont);
			}

			FontMetrics fm = panel.getFontMetrics(panel.getFont());

			if (list != null) {
				for (OP_CODE_MST bean : list) {
					String title = bean.getCODE_NAME();

					if (panel.isAutoAdjustment()) {
						int width = fm.stringWidth(title) + 30;
						totalWidth += width;
						panel.addRadio(bean, title, width);
					} else {
						panel.addRadio(bean, title);
					}
				}
			}

			if (panel.isAutoAdjustment()) {
				panel.resizePanel(totalWidth);
			} else {
				panel.resizePanel();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * @see jp.co.ais.trans.common.client.TPanelCtrlBase#getPanel()
	 */
	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

}
