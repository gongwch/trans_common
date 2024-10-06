package jp.co.ais.trans2.common.model.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jp.co.ais.trans.common.gui.TPanelBusiness;
import jp.co.ais.trans.common.util.Util;
import jp.co.ais.trans2.common.client.TController;

/**
 * 決算段階選択コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TSettlementStageSelectorController extends TController {

	/** フィールド */
	protected TSettlementStageSelector field;

	/**
	 * コンストラクタ
	 * 
	 * @param field フィールド
	 */
	public TSettlementStageSelectorController(TSettlementStageSelector field) {
		this.field = field;
		init();
	}

	@Override
	public void start() {
		//
	}

	/**
	 * 初期化
	 */
	protected void init() {
		addEvent();

		// 決算しない場合
		if (getCompany().getFiscalPeriod().getMaxSettlementStage() == 0) {
			field.rdoNormal.setSelected(true);

			// 決算する場合
		} else {
			field.rdoSettlement.setSelected(true);
			field.nmSettlementLevel.setNumber(getCompany().getFiscalPeriod().getMaxSettlementStage());
		}

	}

	/**
	 * インベト定義
	 */
	protected void addEvent() {

		/**
		 * 通常押下
		 */
		field.rdoNormal.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				rdoNormal_Click();
			}
		});

		/**
		 * 決算押下
		 */
		field.rdoSettlement.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				rdoSettlement_Click();
			}
		});

	}

	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * 決算段階を返す
	 * 
	 * @return 決算段階
	 */
	public int getSettlementStage() {
		if (field.rdoNormal.isSelected()) {
			return 0;
		}
		return field.nmSettlementLevel.getInt();
	}

	/**
	 * 決算段階を返す
	 * 
	 * @param settlementLevel 決算段階
	 */
	public void setSettlementStage(int settlementLevel) {
		if (settlementLevel == 0) {
			rdoNormal_Click();
			field.rdoNormal.setSelected(true);
		} else {
			rdoSettlement_Click();
			field.rdoSettlement.setSelected(true);
			field.nmSettlementLevel.setNumber(settlementLevel);
		}
	}

	/**
	 * [通常]押下
	 */
	protected void rdoNormal_Click() {
		field.nmSettlementLevel.setEnabled(false);
		field.nmSettlementLevel.setText(null);
	}

	/**
	 * [決算]押下
	 */
	protected void rdoSettlement_Click() {
		field.nmSettlementLevel.setEnabled(true);
	}

	/**
	 * 入力が正しいかを返す
	 * 
	 * @return true(正常) / false(エラー)
	 */
	public boolean isCorrect() {

		// 決算が選択されている場合
		if (field.rdoSettlement.isSelected()) {

			// 決算段階が未入力ならエラー
			if (Util.isNullOrEmpty(field.nmSettlementLevel.getText())) {
				return false;
			}

			// 決算段階が1～9でなければエラー
			if (field.nmSettlementLevel.getInt() < 1 || field.nmSettlementLevel.getInt() > 9) {
				return false;
			}
		}

		return true;
	}

}
