package jp.co.ais.trans2.master.ui;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.master.model.ui.*;
import jp.co.ais.trans2.model.item.*;

/**
 * 科目マスタコントローラ<br>
 * 航海収支計算フラグ追加版
 */
public class MG0081ItemMasterPanelCtrl extends MG0080ItemMasterPanelCtrl {

	/**
	 * 指示画面のファクトリ。新規に指示画面を生成し、イベントを定義する。
	 */
	@Override
	protected void createMainView() {
		mainView = new MG0081ItemMasterPanel(getCompany());
		addMainViewEvent();
	}

	/**
	 * 編集画面のファクトリ。新規に編集画面を生成し、イベントを定義する。
	 */
	@Override
	protected void createEditView() {

		// 編集画面を生成
		editView = new MG0081ItemMasterDialog(getCompany(), mainView.getParentFrame(), true);

		// 編集画面のイベント定義
		addEditViewEvent();
		addSubViewEvent();
	}

	/**
	 * 編集画面を初期化する
	 * 
	 * @param mode_ 操作モード。
	 * @param bean 科目。修正、複写の場合は当該科目情報を編集画面にセットする。
	 */
	@Override
	protected void initEditView(Mode mode_, Item bean) {
		super.initEditView(mode_, bean);

		switch (mode_) {
			case COPY:
			case MODIFY:
				// 編集

				if (bean.getItemSumType() == ItemSumType.INPUT) {
					((TItemStatusVoyageUnit) editView.chk).chkVoyage.setSelected(bean.isUseVoyageCalculation());
				}
		}
	}

	/**
	 * 編集画面で入力された科目を返す
	 * 
	 * @return 編集画面で入力された科目
	 */
	@Override
	protected Item getInputedItem() {
		Item item = super.getInputedItem();

		if (item.getItemSumType() == ItemSumType.INPUT) {
			item.setUseVoyageCalculation(((TItemStatusVoyageUnit) editView.chk).chkVoyage.isSelected());
		}

		return item;
	}

	/**
	 * 編集画面[集計][見出]ボタン押下
	 */
	@Override
	protected void btnSum_Click() {
		super.btnSum_Click();

		((TItemStatusVoyageUnit) editView.chk).chkVoyage.setEnabled(false);
		((TItemStatusVoyageUnit) editView.chk).chkVoyage.setSelected(false);
	}

	/**
	 * 科目情報を一覧に表示する形式に変換し返す
	 * 
	 * @param item 科目情報
	 * @return 一覧に表示する形式に変換された科目情報
	 */
	@Override
	protected List<Object> getRowData(Item item) {
		List<Object> list = new ArrayList<Object>();

		list.add(item.getCode()); // 科目コード
		list.add(item.getName()); // 科目名称
		list.add(item.getNames()); // 科目略称
		list.add(item.getNamek()); // 科目検索名称
		list.add(getWord(ItemSumType.getName(item.getItemSumType()))); // 集計区分
		list.add(getWord(ItemType.getName(item.getItemType()))); // 科目種別
		list.add(getWord(Dc.getName(item.getDc()))); // 貸借区分

		if (item.getItemSumType() == ItemSumType.INPUT) {
			list.add(item.hasSubItem() ? getWord("C00006") : getWord("C00412")); // 補助区分
			list.add(item.getFixedDepartmentCode()); // 固定部門ｺｰﾄﾞ
			list.add(item.getConsumptionTax().getCode());// 消費税コード
			list.add(getWord(GLType.getName(item.getGlType()))); // GL科目制御区分
			list.add(getWord(APType.getName(item.getApType()))); // AP科目制御区分
			list.add(getWord(ARType.getName(item.getArType()))); // AR科目制御区分
			list.add(getWord(BGType.getName(item.getBgType()))); // BG科目制御区分
			list.add(getWord((CustomerType.getName(item.getClientType())))); // 取引先入力フラグ
			list.add(getWord(getDivisionName(item.isDoesOffsetItem()))); // 相殺科目制御区分
			list.add(getWord(getDivisionName(item.isDoesBsOffset()))); // BS勘定消込区分
			list.add(getWord(EvaluationMethod.getName(item.getEvaluationMethod()))); // 評価替対象フラグ
			list.add(getWord(getBoo(item.isUseInputCashFlowSlip()))); // 入金伝票入力フラグ
			list.add(getWord(getBoo(item.isUseOutputCashFlowSlip()))); // 出金伝票入力フラグ
			list.add(getWord(getBoo(item.isUseTransferSlip()))); // 振替伝票入力フラグ
			list.add(getWord(getBoo(item.isUseExpenseSettlementSlip()))); // 経費精算伝票入力フラグ
			list.add(getWord(getBoo(item.isUsePaymentAppropriateSlip()))); // 債務計上伝票入力フラグ
			list.add(getWord(getBoo(item.isUseReceivableAppropriateSlip()))); // 債権計上伝票入力フラグ
			list.add(getWord(getBoo(item.isUseReceivableErasingSlip()))); // 債権消込伝票入力フラグ
			list.add(getWord(getBoo(item.isUseAssetsEntrySlip()))); // 資産計上伝票入力フラグ
			list.add(getWord(getBoo(item.isUsePaymentRequestSlip()))); // 支払依頼伝票入力フラグ
			list.add(getWord(getBoo(item.isUseForeignCurrency()))); // 多通貨入力フラグ
			list.add(getWord(getBoo1(item.isUseEmployee()))); // 社員入力フラグ
			list.add(getWord(getBoo1(item.isUseManagement1()))); // 管理１入力フラグ
			list.add(getWord(getBoo1(item.isUseManagement2()))); // 管理2入力フラグ
			list.add(getWord(getBoo1(item.isUseManagement3()))); // 管理3入力フラグ
			list.add(getWord(getBoo1(item.isUseManagement4()))); // 管理4入力フラグ
			list.add(getWord(getBoo1(item.isUseManagement5()))); // 管理5入力フラグ
			list.add(getWord(getBoo1(item.isUseManagement6()))); // 管理6入力フラグ
			list.add(getWord(getBoo(item.isUseNonAccount1()))); // 非会計１入力フラグ
			list.add(getWord(getBoo(item.isUseNonAccount2()))); // 非会計2入力フラグ
			list.add(getWord(getBoo(item.isUseNonAccount3()))); // 非会計3入力フラグ
			list.add(getWord(getBoo(item.isUseSalesTaxation()))); // 売上課税入力フラグ
			list.add(getWord(getBoo(item.isUsePurchaseTaxation()))); // 仕入課税入力フラグ
			list.add(getWord(getDivisionName(item.isUseVoyageCalculation()))); // 航海収支計算フラグ
			list.add(getWord(getBoo(item.isUseOccurDate()))); // 発生日フラグ
			list.add(DateUtil.toYMDString(item.getDateFrom())); // 開始年月日
			list.add(DateUtil.toYMDString(item.getDateTo())); // 終了年月日
		} else {
			list.add(""); // 補助区分
			list.add(""); // 固定部門ｺｰﾄﾞ
			list.add(""); // 消費税コード
			list.add(""); // GL科目制御区分
			list.add(""); // AP科目制御区分
			list.add(""); // AR科目制御区分
			list.add(""); // BG科目制御区分
			list.add(""); // 取引先入力フラグ
			list.add(""); // 相殺科目制御区分
			list.add(""); // BS勘定消込区分
			list.add(""); // 評価替対象フラグ
			list.add(""); // 入金伝票入力フラグ
			list.add(""); // 出金伝票入力フラグ
			list.add(""); // 振替伝票入力フラグ
			list.add(""); // 経費精算伝票入力フラグ
			list.add(""); // 債務計上伝票入力フラグ
			list.add(""); // 債権計上伝票入力フラグ
			list.add(""); // 債権消込伝票入力フラグ
			list.add(""); // 資産計上伝票入力フラグ
			list.add(""); // 支払依頼伝票入力フラグ
			list.add(""); // 多通貨入力フラグ
			list.add(""); // 社員入力フラグ
			list.add(""); // 管理１入力フラグ
			list.add(""); // 管理2入力フラグ
			list.add(""); // 管理3入力フラグ
			list.add(""); // 管理4入力フラグ
			list.add(""); // 管理5入力フラグ
			list.add(""); // 管理6入力フラグ
			list.add(""); // 非会計１入力フラグ
			list.add(""); // 非会計2入力フラグ
			list.add(""); // 非会計3入力フラグ
			list.add(""); // 売上課税入力フラグ
			list.add(""); // 仕入課税入力フラグ
			list.add(""); // 航海収支計算フラグ
			list.add(""); // 発生日フラグ
			list.add(DateUtil.toYMDString(item.getDateFrom())); // 開始年月日
			list.add(DateUtil.toYMDString(item.getDateTo())); // 終了年月日

		}

		return list;
	}
}
