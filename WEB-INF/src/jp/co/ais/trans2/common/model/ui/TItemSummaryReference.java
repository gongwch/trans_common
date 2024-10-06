package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.item.summary.*;

/**
 * �ȖڏW�v�����R���|�[�l���g
 */
public class TItemSummaryReference extends TReference {

	/** serialVersionUID */
	private static final long serialVersionUID = 5845285285425873551L;

	/** �R���g���[�� */
	protected TItemSummaryReferenceController controller;
	
	/**
	 * �R���X�g���N�^.
	 * 
	 */
	public TItemSummaryReference() {
		controller = new TItemSummaryReferenceController(this);

		this.resize();
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TReference#getController()
	 */
	@Override
	protected TReferenceController getController() {
		return controller;
	}

	/**
	 * �R���|�[�l���g������������<BR>
	 */
	@Override
	protected void initComponents() {
		super.initComponents();
	}

	/**
	 * �R���|�[�l���g��z�u����
	 */
	@Override
	protected void allocateComponents() {
		super.allocateComponents();
	}

	/**
	 * �T�C�Y�̍Ĕ��f
	 */
	@Override
	public void resize() {

		int width = (int) (btn.getPreferredSize().getWidth() + code.getPreferredSize().getWidth());
		if (name.isVisible()) {
			width += (int) name.getPreferredSize().getWidth();
		}

		setSize(width, 20);
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	public ItemSummarySearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	public ItemSummary getEntity() {
		return controller.getEntity();
	}

	/**
	 * Entity���Z�b�g����
	 * 
	 * @param Item �ȖڏW�v
	 */
	public void setEntity(ItemSummary Item) {
		controller.setEntity(Item);
	}
}
