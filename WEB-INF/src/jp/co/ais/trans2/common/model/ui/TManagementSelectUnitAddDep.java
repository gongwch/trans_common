package jp.co.ais.trans2.common.model.ui;


/**
 * �Ǘ��I���F�Ǘ��I��+�͈͌���+�C�ӑI���F���C�� �i����ǉ��Łj
 * 
 * @author AIS
 */
public class TManagementSelectUnitAddDep extends TManagementSelectUnit {

	/**
	 * �R���X�g���N�^
	 */
	public TManagementSelectUnitAddDep() {
		super(false);
	}

	/**
	 * �R���g���[������
	 * 
	 * @return controller
	 */
	@Override
	public TManagementSelectUnitAddDepController createController() {
		return new TManagementSelectUnitAddDepController(this);
	}

}