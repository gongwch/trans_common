package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.bank.*;

/**
 * 支店検索コンポーネント
 * 
 * @author AIS
 */
public class TBranchReference extends TReference {

	/** serialVersionUID */
	private static final long serialVersionUID = 5845285285425873551L;

	/** コントローラ */
	protected TBranchReferenceController controller;
	
	/**
	 * コンストラクタ.
	 * 
	 */
	public TBranchReference() {
		controller = new TBranchReferenceController(this);

		this.resize();
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TReference#getController()
	 */
	@Override
	protected TReferenceController getController() {
		return controller;
	}

	/**
	 * コンポーネントを初期化する<BR>
	 */
	@Override
	protected void initComponents() {
		super.initComponents();
	}

	/**
	 * コンポーネントを配置する
	 */
	@Override
	protected void allocateComponents() {
		super.allocateComponents();
	}

	/**
	 * サイズの再反映
	 */
	@Override
	public void resize() {

		int width = (int) (btn.getPreferredSize().getWidth() + code.getPreferredSize().getWidth());
		if (name.isVisible()) {
			width += (int) name.getPreferredSize().getWidth();
		}

		setSize(width, 20);
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	public BranchSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	public Bank getEntity() {
		return controller.getEntity();
	}

	/**
	 * Entityをセットする
	 * 
	 * @param bank 支店
	 */
	public void setEntity(Bank bank) {
		controller.setEntity(bank);
	}

}
