package jp.co.ais.trans.common.bizui.ctrl;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.master.entity.CMP_MST;

/**
 * 内訳科目 出力する/出力しない の選択コンポーネントのコントローラクラスです。
 * 
 * @author AIS
 */
public class TBreakDownItemOutputSelectFieldCtrl extends TAppletClientBase {

	/** 
	 * 会社コントロールマスタEntityです。<br>
	 * 当該Entityにしたがってフィールドが初期化されます。
	 */
	protected CMP_MST cmpMst = null;

	/** 内訳科目 出力する/出力しない の選択コンポーネント */
	protected TBreakDownItemOutputSelectField field;

	/**
	 * コンストラクタ
	 * 
	 * @param itemField
	 */
	public TBreakDownItemOutputSelectFieldCtrl(TBreakDownItemOutputSelectField field) {
		this.field = field;
		setCmpMst(getDefaultCmpMst());
	}

	/**
	 * デフォルトの会社コントロールマスタEntityを返します。<br>
	 * セットされる値はログイン情報から取得した<br>
	 * 「内訳科目名称」、「内訳科目を使用するか<br>
	 * のみですので注意して下さい。
	 * @return デフォルトの会社コントロールマスタEntity 
	 */
	public CMP_MST getDefaultCmpMst() {

		CMP_MST cmpMst = new CMP_MST();
		if (TClientLoginInfo.getInstance().getCompanyInfo().isUseBreakDownItem()) {
			cmpMst.setCMP_UKM_KBN(1);
		} else {
			cmpMst.setCMP_UKM_KBN(0);
		}
		cmpMst.setCMP_UKM_NAME(TClientLoginInfo.getInstance().getCompanyInfo().getBreakDownItemName());
		return cmpMst;
	}

	/**
	 * 初期化
	 *
	 */
	public void init() {
		field.initComponent();
		field.rdoNotOutput.setSelected(true);
		field.setVisible(getCmpMst().getCMP_UKM_KBN() == 1);
	}

	/**
	 * @return 会社コントロールマスタEntityを戻します。
	 */
	public CMP_MST getCmpMst() {
		return cmpMst;
	}

	/**
	 * @param cmpMst 会社コントロールマスタEntityを設定します。
	 */
	public void setCmpMst(CMP_MST cmpMst) {
		this.cmpMst = cmpMst;
	}

	/**
	 * 内訳科目を出力するかを返します。
	 * @return trueなら返す、falseなら返さない
	 */
	public boolean isOutput() {
		return field.rdoOutput.isSelected();
	}

}
