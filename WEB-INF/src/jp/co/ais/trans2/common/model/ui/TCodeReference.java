package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.code.*;

/**
 * Codeの検索コンポーネント
 * 
 * @author AIS
 */
public class TCodeReference extends TReference {

	/** コントローラ */
	protected TCodeReferenceController controller;

	/** true:内航モード、false:外航モード */
	protected boolean local = false;

	/** 区分 */
	protected OPCodeDivision div = null;

	/** コード指定 */
	protected String[] codes = null;

	/**
	 * コンストラクター
	 * 
	 * @param div 区分
	 * @param codes コード指定
	 */
	public TCodeReference(OPCodeDivision div, String... codes) {
		this(false, div, codes);
	}

	/**
	 * コンストラクター
	 * 
	 * @param local true:内航モード、false:外航モード
	 * @param div 区分
	 * @param codes コード指定
	 */
	public TCodeReference(boolean local, OPCodeDivision div, String... codes) {
		super();

		this.local = local;
		this.div = div;
		this.codes = codes;

		initController();
	}

	/**
	 * true:内航モード、false:外航モードの取得
	 * 
	 * @return local true:内航モード、false:外航モード
	 */
	public boolean isLocal() {
		return local;
	}

	/**
	 * true:内航モード、false:外航モードの設定
	 * 
	 * @param local true:内航モード、false:外航モード
	 */
	public void setLocal(boolean local) {
		this.local = local;
	}

	/**
	 * @return コード区分
	 */
	public OPCodeDivision getCodeDivision() {
		return div;
	}

	/**
	 * コード区分指定
	 * 
	 * @param codeDiv 区分
	 */
	public void setCodeDivision(OPCodeDivision codeDiv) {
		this.div = codeDiv;
	}

	/**
	 * @return 指定コード
	 */
	public String[] getCodes() {
		return codes;
	}

	/**
	 * コード指定
	 * 
	 * @param arr 指定コード
	 */
	public void setCodes(String... arr) {
		this.codes = arr;
	}

	/**
	 * コントローラ生成
	 */
	protected void initController() {
		// コントローラ生成
		if (controller == null) {
			controller = new TCodeReferenceController(this);
		}
	}

	/**
	 * コンポーネントを初期化する<BR>
	 */
	@Override
	protected void initComponents() {

		// トラオペ共通対応
		if (isLabelMode()) {
			// 強制的にラベルにする
			this.type = TYPE.LABEL;
		}

		super.initComponents();
	}

	/**
	 * コンポーネントを配置する
	 */
	@Override
	protected void allocateComponents() {

		// トラオペ共通対応
		if (isLabelMode()) {
			OPGuiUtil.allocateComponents(this);
		} else {
			super.allocateComponents();
		}
	}

	/**
	 * @return true:ラベルモード
	 */
	protected boolean isLabelMode() {
		return OPGuiUtil.isLabelMode();
	}

	@Override
	public TReferenceController getController() {
		return controller;
	}

	/**
	 * 選択されているエンティティ
	 * 
	 * @return エンティティ
	 */
	public OP_CODE_MST getEntity() {
		return controller.getEntity();
	}

	/**
	 * エンティティをセット
	 * 
	 * @param bean エンティティ
	 */
	public void setEntity(OP_CODE_MST bean) {
		controller.setEntity(bean);
	}

}
