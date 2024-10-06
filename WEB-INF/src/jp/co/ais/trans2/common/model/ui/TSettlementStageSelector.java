package jp.co.ais.trans2.common.model.ui;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;

/**
 * 決算段階選択コンポーネント
 * 
 * @author AIS
 */
public class TSettlementStageSelector extends TTitlePanel {

	/** serialVersionUID */
	private static final long serialVersionUID = 5437867894826776661L;

	/** コントローラ */
	protected TSettlementStageSelectorController controller;

	/** 通常 */
	public TRadioButton rdoNormal;

	/** 決算 */
	public TRadioButton rdoSettlement;

	/** 決算段階 */
	public TNumericField nmSettlementLevel;

	/** 決算段階 */
	public TLabel lblSettlementLevel;

	/**
	 * 
	 *
	 */
	public TSettlementStageSelector() {

		initComponents();

		allocateComponents();

		// コントローラ生成
		controller = new TSettlementStageSelectorController(this);

	}

	/**
	 * コンポーネントの初期化
	 */
	public void initComponents() {
		rdoNormal = new TRadioButton();
		rdoSettlement = new TRadioButton();
		nmSettlementLevel = new TNumericField();
		lblSettlementLevel = new TLabel();
	}

	/**
	 * コンポーネントの配置
	 */
	public void allocateComponents() {

		setLangMessageID("C00610"); // 決算区分
		setSize(150, 75);

		// 通常
		rdoNormal.setLangMessageID("C00372"); // 通常
		rdoNormal.setSize(100, 20);
		rdoNormal.setLocation(15, 5);
		add(rdoNormal);

		// 決算
		rdoSettlement.setLangMessageID("C00142"); // 決算
		rdoSettlement.setSize(50, 20);
		rdoSettlement.setLocation(15, 30);
		add(rdoSettlement);

		// 決算段階
		nmSettlementLevel.setSize(20, 20);
		nmSettlementLevel.setLocation(70, 30);
		nmSettlementLevel.setMaxLength(1);
		nmSettlementLevel.setPositiveOnly(true);
		add(nmSettlementLevel);

		ButtonGroup bg = new ButtonGroup();
		bg.add(rdoNormal);
		bg.add(rdoSettlement);

		lblSettlementLevel.setLangMessageID("C00374"); // 次迄
		lblSettlementLevel.setSize(50, 20);
		lblSettlementLevel.setLocation(95, 30);
		add(lblSettlementLevel);

	}

	/**
	 * 決算段階を返す
	 * 
	 * @return 決算段階
	 */
	public int getSettlementStage() {
		return controller.getSettlementStage();
	}

	/**
	 * 決算段階を返す
	 * 
	 * @param settlementLevel 決算段階
	 */
	public void setSettlementStage(int settlementLevel) {
		controller.setSettlementStage(settlementLevel);
	}

	/**
	 * Tab順の設定
	 * 
	 * @param tabControlNo タブ順
	 */
	public void setTabControlNo(int tabControlNo) {
		rdoNormal.setTabControlNo(tabControlNo);
		rdoSettlement.setTabControlNo(tabControlNo);
		nmSettlementLevel.setTabControlNo(tabControlNo);
	}

	/**
	 * 入力が正しいかを返す
	 * 
	 * @return true(正常) / false(エラー)
	 */
	public boolean isCorrect() {
		return controller.isCorrect();
	}

}