package jp.co.ais.trans2.model.slip;

/**
 * �폜�`�[����
 */
public class DeleteSlipListCondition extends SlipCondition {

	/** �폜���[�U�[�̕\���敪 */
	protected boolean showUserInfo = true;

	/**
	 * �N���[��
	 */
	@Override
	public DeleteSlipListCondition clone() {
		return (DeleteSlipListCondition) super.clone();
	}

	/**
	 * �폜���[�U�[�̕\���敪���擾�B
	 * 
	 * @return showUserInfo �\���敪
	 */
	public boolean isShowUserInfo() {
		return showUserInfo;
	}

	/**
	 * �폜���[�U�[�̕\���敪��ݒ肷��B
	 * 
	 * @param showUserInfo �\���敪
	 */
	public void setShowUserInfo(boolean showUserInfo) {
		this.showUserInfo = showUserInfo;
	}

}
