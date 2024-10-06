package jp.co.ais.trans2.op.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.payment.*;
import jp.co.ais.trans2.model.payment.*;

/**
 * 取引先条件検索コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class OPPaymentSettingReferenceController extends TPaymentSettingReferenceController {

	/**
	 * コンストラクタ
	 * 
	 * @param field フィールド
	 */
	public OPPaymentSettingReferenceController(TReference field) {
		super(field);
	}

	/**
	 * @return ダイアログ画面での検索条件を取得
	 */
	@Override
	public PaymentSettingSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition_
	 * @return list
	 */
	@Override
	protected List<PaymentSetting> getList(PaymentSettingSearchCondition condition_) {
		return super.getList(condition_);
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする
	 */
	public void refleshEntity() {
		PaymentSetting bean = getInputtedEntity();
		setEntity(bean);
	}

}
