package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.item.*;

/**
 * 科目体系検索コンポーネント
 * 
 * @author AIS
 */
public class TItemOrganizationReference extends TReference {

	/** serialVersionUID */
	private static final long serialVersionUID = 7346976337342780514L;

	/** コントローラ */
	protected TItemOrganizationReferenceController controller;

	/**
	 * 
	 *
	 */
	public TItemOrganizationReference() {
		controller = createController();
	}

	/**
	 * @return コントローラー
	 */
	protected TItemOrganizationReferenceController createController() {
		return new TItemOrganizationReferenceController(this);
	}

	@Override
	protected TReferenceController getController() {
		return controller;
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	public ItemOrganizationSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	public ItemOrganization getEntity() {
		return controller.getEntity();
	}

	/**
	 * 基本科目体系をセットする
	 */
	public void setBaseItemOrganization() {
		controller.setBaseItemOrganization();
	}

	/**
	 * Entityをセットする
	 * 
	 * @param itemOrganization 科目体系
	 */
	public void setEntity(ItemOrganization itemOrganization) {
		controller.setEntity(itemOrganization);
	}

	@Override
	protected int getCodeLength() {
		return TransUtil.ITEM_ORGANIZATION_CODE_LENGTH;
	}

	@Override
	protected void allocateComponents() {
		super.allocateComponents();
		code.setMaximumSize(new Dimension(30, 20));
		code.setMinimumSize(new Dimension(30, 20));
		code.setPreferredSize(new Dimension(30, 20));
		resize();
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.
	 */
	@Override
	public void refleshAndShowEntity() {
		controller.refleshEntity();
		controller.setEntity(getEntity());
	}
}
