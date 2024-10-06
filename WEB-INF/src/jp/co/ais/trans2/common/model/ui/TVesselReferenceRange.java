package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;

/**
 * VESSEL範囲検索フィールド
 * 
 * @author AIS
 */
public class TVesselReferenceRange extends TReferenceRange {

	/** 開始フィールド */
	public TVesselReference ctrlVesselReferenceFrom;

	/** 終了フィールド */
	public TVesselReference ctrlVesselReferenceTo;

	/**
	 * コンストラクタ
	 */
	public TVesselReferenceRange() {
		super();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param isShipBuildEntry 造船契約登録用か<br/>
	 *            true：造船契約情報に存在するVesselのみ検索の対象となる
	 */
	public TVesselReferenceRange(boolean isShipBuildEntry) {
		this();
		ctrlVesselReferenceFrom.setShipBuildEntry(isShipBuildEntry);
		ctrlVesselReferenceTo.setShipBuildEntry(isShipBuildEntry);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param companyCode 会社コード
	 * @param isShipBuildEntry 造船契約登録用か<br/>
	 *            true：造船契約情報に存在するVesselのみ検索の対象となる
	 */
	public TVesselReferenceRange(String companyCode, boolean isShipBuildEntry) {
		this();
		ctrlVesselReferenceFrom.setShipBuildEntry(isShipBuildEntry);
		ctrlVesselReferenceFrom.setCompanyCode(companyCode);
		ctrlVesselReferenceTo.setShipBuildEntry(isShipBuildEntry);
		ctrlVesselReferenceTo.setCompanyCode(companyCode);

	}

	@Override
	public void initComponents() {
		ctrlVesselReferenceFrom = new TVesselReference();
		ctrlVesselReferenceTo = new TVesselReference();
	}

	/**
	 * 初期化
	 */
	@Override
	protected void init() {

		ctrlVesselReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlVesselReferenceTo.getSearchCondition().setCodeFrom(ctrlVesselReferenceFrom.getCode());
			}
		});

		ctrlVesselReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlVesselReferenceFrom.getSearchCondition().setCodeTo(ctrlVesselReferenceTo.getCode());
			}
		});

	}

	@Override
	public TReference getFieldFrom() {
		return ctrlVesselReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlVesselReferenceTo;
	}

	/**
	 * コード幅設定
	 * 
	 * @param width
	 */
	public void setCodeSize(int width) {
		ctrlVesselReferenceFrom.setCodeSize(width);
		ctrlVesselReferenceTo.setCodeSize(width);
	}

	/**
	 * 名称テキスト幅設定
	 * 
	 * @param width
	 */
	public void setNameSize(int width) {
		ctrlVesselReferenceFrom.setNameSize(width);
		ctrlVesselReferenceTo.setNameSize(width);
	}

	/**
	 * ボタン幅設定
	 * 
	 * @param width
	 */
	public void setButtonSize(int width) {
		ctrlVesselReferenceFrom.setButtonSize(width);
		ctrlVesselReferenceTo.setButtonSize(width);
	}

	/**
	 * 会社コードを設定する
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		ctrlVesselReferenceFrom.setCompanyCode(companyCode);
		ctrlVesselReferenceTo.setCompanyCode(companyCode);
	}

}
