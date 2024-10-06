package jp.co.ais.trans.common.bizui;

import jp.co.ais.trans.common.gui.*;

/**
 * 科目範囲指定フィールド
 */
public class TItemRangeField extends TRangeField {
	
	/** 科目体系コード */
	protected String itemSystem = "";

	/**
	 * コンストラクタ
	 */
	public TItemRangeField() {
		super.panelMsgId = "C01009";

		super.init();
	}

	/**
	 * 開始フィールドを生成する(Orverride用)
	 * 
	 * @return 開始フィールド
	 */
	protected TButtonField createBeginField() {
		TItemField field = new TItemField();

		// 終了科目に開始コードを設定
		field.addCallControl(new CallBackListener() {

			public void after() {
				String code = TItemRangeField.super.getBeginCode();
				((TItemField) TItemRangeField.super.getEndField()).getInputParameter().setBeginCode(code);
				((TItemField) TItemRangeField.super.getEndField()).getInputParameter().setItemSystemCode(
					getItemSystem());
			}
		});

		return field;
	}

	/**
	 * 終了フィールドを生成する(Orverride用)
	 * 
	 * @return 終了フィールド
	 */
	protected TButtonField createEndField() {
		TItemField field = new TItemField();

		// 終了科目に開始コードを設定
		field.addCallControl(new CallBackListener() {

			public void after() {
				String code = TItemRangeField.super.getEndCode();
				((TItemField) TItemRangeField.super.getBeginField()).getInputParameter().setEndCode(code);
				((TItemField) TItemRangeField.super.getBeginField()).getInputParameter().setItemSystemCode(
					getItemSystem());
			}
		});

		return field;
	}

	/**
	 * 科目体系コードを取得する
	 * 
	 * @return itemSystem
	 */
	public String getItemSystem() {
		return itemSystem;
	}

	/**
	 * 科目体系コードを設定する
	 * 
	 * @param itemSystem 科目体系コード
	 */
	public void setItemSystem(String itemSystem) {
		this.itemSystem = itemSystem;
		((TItemField) TItemRangeField.super.getBeginField()).getInputParameter().setItemSystemCode(
			getItemSystem());
		((TItemField) TItemRangeField.super.getEndField()).getInputParameter().setItemSystemCode(
			getItemSystem());
	}
	
}
