package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.model.ui.slip.*;

/**
 * 伝票日付と決算段階コンポーネント
 * 
 * @author AIS
 */
public class TSlipDateSettlementStage extends TPanel {

	/** 伝票日付 */
	public TSlipDate slipDate;

	/** 伝票日付表示設定 */
	public int dateType = TSlipDate.TYPE_YMD;

	/** 決算伝票か */
	public TClosingEntryCheck chkSettlementStage;

	/** コントローラ */
	protected TSlipDateSettlementStageController controller = null;

	/**
	 * コンストラクタ
	 */
	public TSlipDateSettlementStage() {
		initComponents();
		allocateComponents();
		controller = new TSlipDateSettlementStageController(this);
	}

	/**
	 * 日付Type設定ありの場合 コンストラクタ
	 * 
	 * @param type
	 */
	public TSlipDateSettlementStage(int type) {
		dateType = type;
		initComponents();
		allocateComponents();
		controller = new TSlipDateSettlementStageController(this);
	}

	/**
	 * コンポーネント初期化
	 */
	public void initComponents() {
		slipDate = new TSlipDate(dateType);
		chkSettlementStage = new TClosingEntryCheck(slipDate);
	}

	/**
	 * コンポーネント配置
	 */
	public void allocateComponents() {

		setLayout(null);

		// 伝票日付
		slipDate.setLocation(0, 0);
		add(slipDate);

		// 決算伝票か
		chkSettlementStage.setSize(120, 20);
		chkSettlementStage.setLocation(slipDate.getWidth() + 20, 0);
		chkSettlementStage.setOpaque(false);
		add(chkSettlementStage);

		resize();

	}

	/**
	 * サイズ変更
	 */
	public void resize() {
		setSize(slipDate.getWidth() + chkSettlementStage.getWidth() + 20, slipDate.getHeight());
	}

	/**
	 * tab順指定
	 * 
	 * @param index tab順
	 */
	public void setTabControlNo(int index) {
		slipDate.setTabControlNo(index);
		chkSettlementStage.setTabControlNo(index);
	}

	/**
	 * 決算チェック
	 * 
	 * @return true：正常、false：異常
	 */
	public boolean canCreateSlip() {
		return controller.canCreateSlip();
	}

	/**
	 * 決算段階を返す
	 * 
	 * @return 決算段階
	 */
	public int getSettlementStage() {
		return controller.getSettlementStage();
	}

}
