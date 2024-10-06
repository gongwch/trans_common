package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.ac.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.code.*;

/**
 * コードコンボボックス
 */
public class TCodeComboBox extends TLabelComboBox {

	/** コントローラ */
	protected TCodeComboBoxController controller;

	/** true:内航モード、false:外航モード */
	protected boolean local = false;

	/** 先頭ブランク追加 */
	protected boolean addBlank = false;

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
	public TCodeComboBox(OPCodeDivision div, String... codes) {
		this(false, div, codes);
	}

	/**
	 * コンストラクター
	 * 
	 * @param addBlank true:先頭ブランクアイテム追加
	 * @param div 区分
	 * @param codes コード指定
	 */
	public TCodeComboBox(boolean addBlank, OPCodeDivision div, String... codes) {
		this(false, addBlank, div, codes);
	}

	/**
	 * コンストラクター
	 * 
	 * @param local true:内航モード、false:外航モード
	 * @param addBlank true:先頭ブランクアイテム追加
	 * @param div 区分
	 * @param codes コード指定
	 */
	public TCodeComboBox(boolean local, boolean addBlank, OPCodeDivision div, String... codes) {

		this.local = local;
		this.addBlank = addBlank;
		this.div = div;
		this.codes = codes;

		// 初期化
		init();
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
	 * @return true:先頭ブランクアイテム追加
	 */
	public boolean isAddBlank() {
		return addBlank;
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

		boolean isChanged = codeDiv != this.div;

		this.div = codeDiv;

		if (isChanged) {
			// 変更あり→再度初期化
			getController().init();
		}
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

		// 再度初期化
		getController().init();
	}

	/**
	 * 初期化
	 */
	protected void init() {

		// コンポーネントを初期化する
		initComponents();

		// コンポーネントを配置する
		allocateComponents();

		controller = createController();

	}

	/**
	 * @return コントローラ
	 */
	protected TCodeComboBoxController createController() {
		return new TCodeComboBoxController(this);
	}

	/**
	 *
	 */
	protected void initComponents() {
		//
	}

	/**
	 *
	 */
	protected void allocateComponents() {
		// コード
		setLabelSize(85);
		setComboSize(100);
		setSize(190, 20);
	}

	/**
	 * @return controller
	 */
	public TCodeComboBoxController getController() {
		return controller;
	}

	/**
	 * 選択されている値を取得
	 * 
	 * @return 値
	 */
	@Override
	public String getSelectedItemValue() {
		Object value = this.combo.getSelectedItemValue();

		if (value instanceof OP_CODE_MST) {
			return ((OP_CODE_MST) value).getCode();
		} else if (value instanceof AutoCompletable) {
			return ((AutoCompletable) value).getCode();
		} else if (value instanceof TReferable) {
			return ((TReferable) value).getCode();
		}

		return Util.avoidNull(value);
	}

	/**
	 * 選択されている値を取得
	 * 
	 * @return 値
	 */
	public OP_CODE_MST getSelectedEntity() {
		Object value = this.combo.getSelectedItemValue();

		if (value instanceof OP_CODE_MST) {
			return (OP_CODE_MST) value;
		}
		String code = null;
		if (value instanceof AutoCompletable) {
			code = ((AutoCompletable) value).getCode();
		} else if (value instanceof TReferable) {
			code = ((TReferable) value).getCode();
		}
		return controller.getCodeMst(code);
	}

	/**
	 * 選択中値の戻す
	 * 
	 * @return 選択中値(null可能)
	 */
	public String getCode() {
		return getSelectedItemValue();
	}

	/**
	 * 選択中値の名称を戻す
	 * 
	 * @return 選択中値(null可能)
	 */
	public String getCodeName() {
		OP_CODE_MST code = getSelectedEntity();
		if (code == null) {
			return null;
		}
		return code.getCODE_NAME();
	}

	/**
	 * 選択中値の戻す<br>
	 * 有効な数値の場合、数値を返す
	 * 
	 * @return 選択中値(nullの場合は-1)
	 */
	public int getInt() {
		String code = getCode();
		if (!Util.isNullOrEmpty(code) && Util.isNumber(code)) {
			return DecimalUtil.toInt(code);
		}
		return -1;
	}

	/**
	 * 値を指定して選択肢を変更する
	 * 
	 * @param code OP_CODE_MST.CODE
	 */
	public void setCode(String code) {
		setSelectedValue(code);
	}

	/**
	 * 値を指定して選択肢を変更する
	 * 
	 * @param no
	 */
	public void setInt(int no) {
		setSelectedValue(Integer.toString(no));
	}

	/**
	 * 値を指定して選択肢を変更する
	 * 
	 * @param code OP_CODE_MST.CODE
	 */
	public void setSelectedValue(String code) {
		OP_CODE_MST bean = controller.getCodeMst(code);
		this.setSelectedItemValue(bean);
	}

	/**
	 * 値を指定して選択肢を変更する
	 * 
	 * @param bean OP_CODE_MST
	 */
	public void setSelectedItemValue(OP_CODE_MST bean) {
		super.setSelectedItemValue(bean);
	}

	/**
	 * 値を指定して選択肢を変更する
	 * 
	 * @param obj OP_CODE_MST
	 * @deprecated 非推奨 明確に引数にOP_CODE_MSTを指定することを推奨
	 */
	@Override
	public void setSelectedItemValue(Object obj) {
		super.setSelectedItemValue(obj);
	}

	/**
	 * 値(キー)・表示文字を追加
	 * 
	 * @param text テキスト
	 * @param value 値
	 * @deprecated 非推奨 AddItemを推奨
	 */
	@Override
	public void addTextValueItem(Object value, String text) {
		super.addTextValueItem(value, text);
	}

	/**
	 * コンボボックス選択肢を追加する
	 * 
	 * @param bean
	 */
	public void addItem(OP_CODE_MST bean) {
		controller.addItem(bean);
	}

	/**
	 * コンボボックス選択肢を追加する
	 * 
	 * @param code
	 */
	public void addItem(String code) {
		controller.addItem(code);
	}

}
