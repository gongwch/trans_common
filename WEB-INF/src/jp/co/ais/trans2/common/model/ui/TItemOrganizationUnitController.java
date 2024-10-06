package jp.co.ais.trans2.common.model.ui;

import java.awt.event.*;

import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.item.*;

/**
 * 科目組織レベルの範囲ユニット<br>
 * (表示レベル+科目体系+科目範囲+科目任意指定)
 * 
 * @author AIS
 */
public class TItemOrganizationUnitController extends TController {

	/**
	 * 表示レベル選択種類
	 */
	protected enum MODE {
		/** 科目 */
		Item,

		/** 補助科目 */
		SubItem,

		/** 内訳科目 */
		DetailItem;
	}

	/** 選択表示レベル */
	protected MODE Mode = MODE.Item;

	/** フィールド */
	protected TItemOrganizationUnit field;

	/** 会社Entity */
	protected Company company;

	/** 表示レベルによってページを分けない true：分けない */
	protected boolean isItemLevelNoSep = ClientConfig.isFlagOn("trans.item.level.no.separate");

	/**
	 * コンストラクタ
	 * 
	 * @param field
	 */
	public TItemOrganizationUnitController(TItemOrganizationUnit field) {
		this.field = field;
		init();
	}

	/**
	 * 初期化
	 */
	protected void init() {
		// 会社初期化
		company = getCompany();

		// イベント設定
		addEvent();
	}

	/**
	 * イベント定義
	 */
	protected void addEvent() {

		// [科目体系]変更時
		field.ctrlItemOrganization.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlItemOrganization_after();
			}
		});

		// [表示レベル]変更時
		field.ctrlItemLevelChooser.rdoItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Mode = MODE.Item;
				ctrlItemReference_after(field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupFrom, true);
				ctrlItemReference_after(field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupTo, true);
			}
		});
		field.ctrlItemLevelChooser.rdoSubItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MODE preMode = Mode;
				Mode = MODE.SubItem;
				field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupFrom.refleshGroupEntity();
				field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupTo.refleshGroupEntity();

				if (preMode == MODE.Item) {
					ctrlItemReference_after(field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupFrom, true);
					ctrlItemReference_after(field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupTo, true);
				} else {
					ctrlSubItemReference_after(field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupFrom, true);
					ctrlSubItemReference_after(field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupTo, true);
				}
			}
		});
		field.ctrlItemLevelChooser.rdoDetailItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MODE preMode = Mode;
				Mode = MODE.DetailItem;
				field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupFrom.refleshGroupEntity();
				field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupTo.refleshGroupEntity();

				if (preMode == MODE.Item) {
					ctrlItemReference_after(field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupFrom, true);
					ctrlItemReference_after(field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupTo, true);
				} else {
					ctrlSubItemReference_after(field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupFrom, true);
					ctrlSubItemReference_after(field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupTo, true);
				}
			}
		});

		// [科目範囲：科目]変更時
		field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupFrom.ctrlItemReference
			.addCallBackListener(new TCallBackListener() {

				@Override
				public void after(boolean flag) {
					if (!flag
						|| !field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupFrom.ctrlItemReference.isValueChanged()) {
						return;
					}
					ctrlItemReference_after(field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupFrom, false);
				}
			});
		field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupTo.ctrlItemReference
			.addCallBackListener(new TCallBackListener() {

				@Override
				public void after(boolean flag) {
					if (!flag
						|| !field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupTo.ctrlItemReference.isValueChanged()) {
						return;
					}
					ctrlItemReference_after(field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupTo, false);
				}
			});

		// [科目範囲：補助科目]変更時
		field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupFrom.ctrlSubItemReference
			.addCallBackListener(new TCallBackListener() {

				@Override
				public void after(boolean flag) {
					if (!flag
						|| !field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupFrom.ctrlSubItemReference
							.isValueChanged()) {
						return;
					}
					ctrlSubItemReference_after(field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupFrom, false);
				}
			});
		field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupTo.ctrlSubItemReference
			.addCallBackListener(new TCallBackListener() {

				@Override
				public void after(boolean flag) {
					if (!flag
						|| !field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupTo.ctrlSubItemReference
							.isValueChanged()) {
						return;
					}
					ctrlSubItemReference_after(field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupTo, false);
				}
			});
	}

	/**
	 * [科目選択]時の処理
	 * 
	 * @param fieldGroup 科目グループ
	 * @param isLevel 表示レベル操作かどうか
	 */
	protected void ctrlItemReference_after(TItemGroup fieldGroup, boolean isLevel) {
		Item entity = fieldGroup.ctrlItemReference.getEntity();

		if (!isLevel || (isLevel && !isItemLevelNoSep)) {
			fieldGroup.ctrlSubItemReference.clear();
		}

		// 補助科目を持つ場合、補助フィールドを入力可能にする
		// 但し、表示レベルが「科目」の場合は入力不可にする
		if (entity != null) {
			boolean isItemLevelEditable = isItemLevelNoSep || (!isItemLevelNoSep && this.Mode != MODE.Item);

			fieldGroup.ctrlSubItemReference.setEditable(isItemLevelEditable && entity.hasSubItem());
			fieldGroup.ctrlSubItemReference.getSearchCondition().setItemCode(entity.getCode());

			if (!isLevel || (isLevel && !isItemLevelNoSep)) {
				fieldGroup.ctrlDetailItemReference.clear();
				fieldGroup.ctrlDetailItemReference.setEditable(false);
			}
			fieldGroup.ctrlDetailItemReference.getSearchCondition().setItemCode(entity.getCode());
		} else {
			fieldGroup.ctrlSubItemReference.clear();
			fieldGroup.ctrlSubItemReference.setEditable(false);

			fieldGroup.ctrlDetailItemReference.clear();
			fieldGroup.ctrlDetailItemReference.setEditable(false);
		}
	}

	/**
	 * [補助科目選択]時の処理
	 * 
	 * @param fieldGroup 科目グループ
	 * @param isLevel 表示レベル操作かどうか
	 */
	protected void ctrlSubItemReference_after(TItemGroup fieldGroup, boolean isLevel) {
		Item entity = fieldGroup.ctrlSubItemReference.getEntity();

		if (!isLevel || (isLevel && !isItemLevelNoSep)) {
			fieldGroup.ctrlDetailItemReference.clear();
		}

		// 内訳科目を持つ場合、内訳フィールドを入力可能にする
		// 但し、表示レベルが「内訳科目」でない場合は入力不可にする
		if (entity != null && entity.getSubItem() != null) {
			boolean isItemLevelEditable = isItemLevelNoSep || (!isItemLevelNoSep && this.Mode == MODE.DetailItem);

			fieldGroup.ctrlDetailItemReference.setEditable(isItemLevelEditable && entity.getSubItem().hasDetailItem());
			fieldGroup.ctrlDetailItemReference.getSearchCondition().setItemCode(entity.getCode());
			fieldGroup.ctrlDetailItemReference.getSearchCondition().setSubItemCode(entity.getSubItem().getCode());
		} else {
			fieldGroup.ctrlDetailItemReference.setEditable(false);
		}
	}

	/**
	 * [科目体系]変更を、科目範囲コンポーネントへ反映する
	 */
	protected void ctrlItemOrganization_after() {

		// 科目コンポーネントに科目体系の条件セット
		String ioCode = field.ctrlItemOrganization.getCode();
		field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupFrom.getSearchCondition().setItemOrganizationCode(ioCode);
		field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupTo.getSearchCondition().setItemOrganizationCode(ioCode);
		field.ctrlItemRange.ctrlItemGroupOptionalSelector.getSearchCondition().setItemOrganizationCode(ioCode);
		field.ctrlItemRange.ctrlItemGroupOptionalSelector.getSubItemSearchCondition().setItemOrganizationCode(ioCode);
		field.ctrlItemRange.ctrlItemGroupOptionalSelector.getDetailItemSearchCondition()
			.setItemOrganizationCode(ioCode);

		// 科目体系が変更になったら科目範囲をクリアする。
		System.out.println(field.ctrlItemOrganization.isValueChanged());
		if (field.ctrlItemOrganization.getEntity() != null && field.ctrlItemOrganization.isValueChanged()) {
			field.ctrlItemRange.clear();
		}
	}

}
