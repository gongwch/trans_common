package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.vessel.*;

/**
 * 会社検索、船検索のグループコンポーネントコントローラ
 * 
 * @author AIS
 */
public class TCompanyVesselGroupController extends TController {

	/** フィールド */
	protected TCompanyVesselGroup field;

	/**
	 * @param field フィールド
	 */
	public TCompanyVesselGroupController(TCompanyVesselGroup field) {
		this.field = field;
		init();
	}

	/**
	 * 初期化
	 */
	protected void init() {

		// イベント定義
		addEvent();

		clear();
	}

	/**
	 * CompanyVesselのイベント定義
	 */
	protected void addEvent() {

		// 会社選択
		field.ctrlCompany.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean isValid) {
				// エラーがある場合は船をクリアする
				if (!isValid) {
					field.ctrlVessel.clear();
					field.ctrlVessel.setEditable(false);
					field.ctrlVessel.setCompanyCode(null);
					return;
				}
				// 値が変更されていない場合は何もしない
				if (!field.ctrlCompany.isValueChanged()) {
					return;
				}
				if (Util.isNullOrEmpty(field.ctrlCompany.getCode())) {
					field.ctrlCompany.clear();
				}
				ctrlCompany_after();
			}
		});

	}

	@Override
	public void start() {
		//
	}

	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * フィールドをクリアする
	 */
	public void clear() {
		field.ctrlCompany.clear();
		ctrlCompany_after();
	}

	/**
	 * [会社選択]時の処理
	 */
	protected void ctrlCompany_after() {
		Company entity = field.ctrlCompany.getEntity();

		// 会社を取得した場合、船フィールドを入力可能にする。
		if (entity != null) {
			field.ctrlVessel.clear();
			field.ctrlVessel.setEditable(true);
			field.ctrlVessel.getSearchCondition().setCompanyCode(entity.getCode());
			field.ctrlVessel.setCompanyCode(entity.getCode());
		} else {
			field.ctrlVessel.clear();
			field.ctrlVessel.setEditable(false);
			field.ctrlVessel.setCompanyCode(null);
		}
	}

	/**
	 * 選択された船を返す
	 * 
	 * @return 選択された船
	 */
	public Vessel getVesselEntity() {

		// 選択された船を取得
		return field.ctrlVessel.getEntity();
	}

	/**
	 * 船を設定する
	 * 
	 * @param bean 船情報
	 */
	public void setVesselEntity(Vessel bean) {
		field.ctrlVessel.setEntity(bean);
	}

	/**
	 * 選択された会社を返す
	 * 
	 * @return 選択された会社
	 */
	public Company getCompanyEntity() {
		return field.ctrlCompany.getEntity();
	}

	/**
	 * 会社を設定する
	 * 
	 * @param bean 会社
	 */
	public void setCompanyEntity(Company bean) {
		field.ctrlCompany.setEntity(bean);
		ctrlCompany_after();
	}

	/**
	 * Vessel検索条件のgetter
	 * 
	 * @return Vessel検索条件
	 */
	public VesselSearchCondition getVesselSearchCondition() {
		return field.ctrlVessel.getSearchCondition();
	}

	/**
	 * 会社の検索条件getter
	 * 
	 * @return 会社の検索条件
	 */
	public CompanySearchCondition getCompanySearchCondition() {
		return field.ctrlCompany.getSearchCondition();
	}

}
