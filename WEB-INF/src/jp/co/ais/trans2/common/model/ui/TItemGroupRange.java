package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.model.item.*;

/**
 * 科目・補助・内訳のユニットの範囲検索コンポーネント
 * 
 * @author AIS
 */
public class TItemGroupRange extends TPanel {

	/** serialVersionUID */
	private static final long serialVersionUID = 3051866409639326455L;

	/** 開始フィールド */
	public TItemGroup ctrlItemGroupFrom;

	/** 終了フィールド */
	public TItemGroup ctrlItemGroupTo;

	/**
	 * 
	 *
	 */
	public TItemGroupRange() {

		// コンポーネントを初期化する
		initComponents();

		// コンポーネントを配置する
		allocateComponents();

		// 初期化
		init();

	}

	/**
	 * コンポーネントを初期化する<BR>
	 */
	protected void initComponents() {
		ctrlItemGroupFrom = new TItemGroup();
		ctrlItemGroupTo = new TItemGroup();
	}

	/**
	 * コンポーネントを配置する
	 */
	protected void allocateComponents() {

		setSize(ctrlItemGroupFrom.getWidth(), ctrlItemGroupFrom.getHeight() + ctrlItemGroupTo.getHeight());

		setLayout(null);

		// 開始
		ctrlItemGroupFrom.setLocation(0, 0);
		add(ctrlItemGroupFrom);

		// 終了
		ctrlItemGroupTo.setLocation(0, ctrlItemGroupFrom.getHeight());
		add(ctrlItemGroupTo);

	}

	/**
	 * Tab順の設定
	 * 
	 * @param tabControlNo タブ順
	 */
	public void setTabControlNo(int tabControlNo) {
		ctrlItemGroupFrom.setTabControlNo(tabControlNo);
		ctrlItemGroupTo.setTabControlNo(tabControlNo);
	}

	/**
	 * 初期化
	 */
	public void init() {

		// 科目の範囲指定
		ctrlItemGroupFrom.ctrlItemReference.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				field_verify_after();
			}
		});

		ctrlItemGroupTo.ctrlItemReference.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				field_verify_after();
			}
		});

		// 補助科目の範囲指定
		ctrlItemGroupFrom.ctrlSubItemReference.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				field_verify_after();
			}
		});

		ctrlItemGroupTo.ctrlSubItemReference.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				field_verify_after();
			}
		});

		// 内訳科目の範囲指定
		ctrlItemGroupFrom.ctrlDetailItemReference.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				field_verify_after();
			}
		});

		ctrlItemGroupTo.ctrlDetailItemReference.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				field_verify_after();
			}
		});

	}

	/**
	 * 開始フィールドのverify追加処理
	 */
	protected void field_verify_after() {

		// 科目の範囲指定
		ctrlItemGroupTo.ctrlItemReference.getSearchCondition().setCodeFrom(
			ctrlItemGroupFrom.ctrlItemReference.getCode());

		// 科目がともに入力されていて、かつ科目コードが一致する場合、補助科目の範囲指定
		if (ctrlItemGroupFrom.ctrlItemReference.getCode() != null
			&& ctrlItemGroupTo.ctrlItemReference.getCode() != null
			&& ctrlItemGroupFrom.ctrlItemReference.getCode().equals(ctrlItemGroupTo.ctrlItemReference.getCode())) {

			ctrlItemGroupTo.ctrlSubItemReference.getSearchCondition().setCodeFrom(
				ctrlItemGroupFrom.ctrlSubItemReference.getCode());

			// 科目が未入力、または科目が異なる場合は範囲指定無し。
		} else {
			ctrlItemGroupTo.ctrlSubItemReference.getSearchCondition().setCodeFrom(null);
			ctrlItemGroupTo.ctrlDetailItemReference.getSearchCondition().setCodeFrom(null);
			ctrlItemGroupTo.ctrlDetailItemReference.getSearchCondition().setCodeTo(null);
		}

		if (ctrlItemGroupFrom.ctrlSubItemReference.getCode() != null
			&& ctrlItemGroupTo.ctrlSubItemReference.getCode() != null
			&& ctrlItemGroupFrom.ctrlItemReference.getCode().equals(ctrlItemGroupTo.ctrlItemReference.getCode())
			&& ctrlItemGroupFrom.ctrlSubItemReference.getCode().equals(ctrlItemGroupTo.ctrlSubItemReference.getCode())) {

			ctrlItemGroupTo.ctrlDetailItemReference.getSearchCondition().setCodeFrom(
				ctrlItemGroupFrom.ctrlDetailItemReference.getCode());
		} else {

			ctrlItemGroupTo.ctrlDetailItemReference.getSearchCondition().setCodeFrom(null);

		}

		// 科目の範囲指定
		ctrlItemGroupFrom.ctrlItemReference.getSearchCondition().setCodeTo(ctrlItemGroupTo.ctrlItemReference.getCode());

		// 科目がともに入力されていて、かつ科目コードが一致する場合、補助科目の範囲指定
		if (ctrlItemGroupFrom.ctrlItemReference.getCode() != null
			&& ctrlItemGroupTo.ctrlItemReference.getCode() != null
			&& ctrlItemGroupFrom.ctrlItemReference.getCode().equals(ctrlItemGroupTo.ctrlItemReference.getCode())) {

			ctrlItemGroupFrom.ctrlSubItemReference.getSearchCondition().setCodeTo(
				ctrlItemGroupTo.ctrlSubItemReference.getCode());

			// 科目が未入力、または科目が異なる場合は範囲指定無し。
		} else {
			ctrlItemGroupFrom.ctrlSubItemReference.getSearchCondition().setCodeTo(null);
			ctrlItemGroupTo.ctrlDetailItemReference.getSearchCondition().setCodeFrom(null);
			ctrlItemGroupTo.ctrlDetailItemReference.getSearchCondition().setCodeTo(null);
		}

		if (ctrlItemGroupFrom.ctrlSubItemReference.getCode() != null
			&& ctrlItemGroupTo.ctrlSubItemReference.getCode() != null
			&& ctrlItemGroupFrom.ctrlItemReference.getCode().equals(ctrlItemGroupTo.ctrlItemReference.getCode())
			&& ctrlItemGroupFrom.ctrlSubItemReference.getCode().equals(ctrlItemGroupTo.ctrlSubItemReference.getCode())) {

			ctrlItemGroupFrom.ctrlDetailItemReference.getSearchCondition().setCodeTo(
				ctrlItemGroupTo.ctrlDetailItemReference.getCode());
		} else {

			ctrlItemGroupFrom.ctrlDetailItemReference.getSearchCondition().setCodeTo(null);

		}

	}

	/**
	 * 大小チェック
	 * 
	 * @return true(正常) / false(エラー)
	 */
	public boolean isSmallerFrom() {

		// 科目の開始、終了のいずれかがブランクの場合true
		if (Util.isNullOrEmpty(ctrlItemGroupFrom.ctrlItemReference.getCode())
			|| Util.isNullOrEmpty(ctrlItemGroupTo.ctrlItemReference.getCode())) {
			return true;
		}

		// 科目の開始、終了がともに入力されていて、かつ開始 > 終了の場合エラー
		if (!Util.isSmallerThen(ctrlItemGroupFrom.ctrlItemReference.getCode(),
			ctrlItemGroupTo.ctrlItemReference.getCode())) {
			return false;
		}

		// 補助科目の開始、終了のいずれかがブランクの場合true
		if (Util.isNullOrEmpty(ctrlItemGroupFrom.ctrlSubItemReference.getCode())
			|| Util.isNullOrEmpty(ctrlItemGroupTo.ctrlSubItemReference.getCode())) {
			return true;
		}

		// 科目が同じで、かつ開始 > 終了の場合エラー
		if (ctrlItemGroupFrom.ctrlItemReference.getCode().equals(ctrlItemGroupTo.ctrlItemReference.getCode())
			&& !Util.isSmallerThen(ctrlItemGroupFrom.ctrlSubItemReference.getCode(),
				ctrlItemGroupTo.ctrlSubItemReference.getCode())) {
			return false;
		}

		// 内訳科目の開始、終了のいずれかがブランクの場合true
		if (Util.isNullOrEmpty(ctrlItemGroupFrom.ctrlDetailItemReference.getCode())
			|| Util.isNullOrEmpty(ctrlItemGroupTo.ctrlDetailItemReference.getCode())) {
			return true;
		}

		// 内訳科目の開始、終了がともに入力されていて、かつ開始 > 終了の場合エラー
		if (ctrlItemGroupFrom.ctrlItemReference.getCode().equals(ctrlItemGroupTo.ctrlItemReference.getCode())
			&& ctrlItemGroupFrom.ctrlSubItemReference.getCode().equals(ctrlItemGroupTo.ctrlSubItemReference.getCode())
			&& !Util.isSmallerThen(ctrlItemGroupFrom.ctrlDetailItemReference.getCode(),
				ctrlItemGroupTo.ctrlDetailItemReference.getCode())) {
			return false;
		}

		return true;

	}

	/**
	 * 開始フィールドで選択された科目・補助・内訳を返す
	 * 
	 * @return 選択された科目・補助・内訳<br>
	 *         (Itemの中に階層的に科目・補助・内訳を内包して返す)
	 */
	public Item getFromEntity() {
		return ctrlItemGroupFrom.getEntity();
	}

	/**
	 * 終了フィールドで選択された科目・補助・内訳を返す
	 * 
	 * @return 選択された科目・補助・内訳<br>
	 *         (Itemの中に階層的に科目・補助・内訳を内包して返す)
	 */
	public Item getToEntity() {
		return ctrlItemGroupTo.getEntity();
	}

	/**
	 * クリアする
	 */
	public void clear() {
		ctrlItemGroupFrom.clear();
		ctrlItemGroupTo.clear();
	}

}
