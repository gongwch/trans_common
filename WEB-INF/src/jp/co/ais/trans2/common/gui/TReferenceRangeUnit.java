package jp.co.ais.trans2.common.gui;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.model.ui.*;

/**
 * 範囲選択フィールドと個別選択フィールドのセット
 */
public abstract class TReferenceRangeUnit extends TTitlePanel {

	/** ボーダーを表示するか */
	protected boolean border = false;

	/** 会社出力単位 */
	protected TCompanyOrganizationUnit companyOrgUnit = null;

	/** */
	public TReferenceRangeUnit() {
		this(false);
	}

	/**
	 * @param border
	 */
	public TReferenceRangeUnit(boolean border) {

		this(border, false);

	}

	/**
	 * @param border
	 * @param title タイトル表示かどうか
	 */
	public TReferenceRangeUnit(boolean border, boolean title) {

		this.border = border;

		initComponents();

		allocateComponents(title);

	}

	/**
	 * コンポーネントの初期化
	 */
	public abstract void initComponents();

	/**
	 * コンポーネントの配置
	 * 
	 * @param title タイトル表示かどうか
	 */
	public void allocateComponents(boolean title) {

		setOpaque(false);

		TReferenceRange referenceRange = getReferenceRange();
		referenceRange.setLocation(0, 0);
		add(referenceRange);

		TOptionalSelector optionalSelector = getOptionalSelector();
		optionalSelector.setLocation(0, referenceRange.getHeight());
		add(optionalSelector);

		setSize(optionalSelector.getWidth() + 5, referenceRange.getHeight() + optionalSelector.getHeight() + 5);

		if (border) {
			setSize(365, 95);
			if (title) {
				setLangMessageID(getBorderTitle());
				referenceRange.setLocation(15, 5);
			} else {
				setBorder(TBorderFactory.createTitledBorder(getBorderTitle()));
				this.titlePanel.setVisible(false);
				referenceRange.setLocation(15, 0);
			}
			optionalSelector.setLocation(15, referenceRange.getHeight() + referenceRange.getY());
		} else {
			this.titlePanel.setVisible(false);
			this.setBorder(null);
		}

	}

	/**
	 * 範囲指定フィールドのgetter
	 * 
	 * @return 範囲指定フィールド
	 */
	public abstract TReferenceRange getReferenceRange();

	/**
	 * 任意選択(個別指定)コンポーネントのgetter
	 * 
	 * @return 任意選択(個別指定)コンポーネント
	 */
	public abstract TOptionalSelector getOptionalSelector();

	/**
	 * ボーダーのタイトルを返す
	 * 
	 * @return ボーターのタイトル
	 */
	public abstract String getBorderTitle();

	/**
	 * コードと略称を初期化する
	 */
	public void clear() {
		getReferenceRange().getFieldFrom().clear();
		getReferenceRange().getFieldTo().clear();
		// TODO
		// getOptionalSelector().clear;
	}

	/**
	 * 編集可・不可制御
	 * 
	 * @param edit
	 */
	public void setEditable(boolean edit) {
		getReferenceRange().getFieldFrom().setEditable(edit);
		getReferenceRange().getFieldTo().setEditable(edit);
		// TODO
		// getOptionalSelector().setEditable(edit);
	}

	/**
	 * Tab順設定
	 * 
	 * @param no タブ順
	 */
	public void setTabControlNo(int no) {
		getOptionalSelector().setTabControlNo(no);
		getReferenceRange().getFieldFrom().setTabControlNo(no);
		getReferenceRange().getFieldTo().setTabControlNo(no);
	}

	/**
	 * 大小チェック
	 * 
	 * @return true(問題無し) / false(エラーあり)
	 */
	public boolean isSmallerFrom() {
		return (Util.isSmallerThen(getReferenceRange().getFieldFrom().getCode(), getReferenceRange().getFieldTo()
			.getCode()));
	}

	/**
	 * 会社出力単位の取得
	 * 
	 * @return companyOrgUnit 会社出力単位
	 */
	public TCompanyOrganizationUnit getCompanyOrgUnit() {
		return companyOrgUnit;
	}

	/**
	 * 会社出力単位の設定
	 * 
	 * @param companyOrgUnit 会社出力単位
	 */
	public void setCompanyOrgUnit(TCompanyOrganizationUnit companyOrgUnit) {
		this.companyOrgUnit = companyOrgUnit;

		if (getReferenceRange() != null) {
			if (getReferenceRange().getFieldFrom() != null) {
				getReferenceRange().getFieldFrom().setCompanyOrgUnit(companyOrgUnit);
			}
			if (getReferenceRange().getFieldTo() != null) {
				getReferenceRange().getFieldTo().setCompanyOrgUnit(companyOrgUnit);
			}
		}
		if (getOptionalSelector() != null) {
			getOptionalSelector().setCompanyOrgUnit(companyOrgUnit);
		}
	}

}
