package jp.co.ais.trans2.model.aprvrole;

/**
 * ���F�O���[�v����<br>
 * ���[�����܂ł�ێ����A�Ԃ牺���鏊�����[�U�[�ɂ��Ă͈���Ȃ�
 */
public class AprvRoleGroupDetail extends AprvRole {

	/** ���F�������x�� */
	protected int APRV_LEVEL = -1;

	/**
	 * ���F�������x�����擾
	 * 
	 * @return ���F�������x��
	 */
	public int getAPRV_LEVEL() {
		return APRV_LEVEL;
	}

	/**
	 * ���F�������x�����Z�b�g����
	 * 
	 * @param aPRV_LEVEL ���F�������x��
	 */
	public void setAPRV_LEVEL(int aPRV_LEVEL) {
		APRV_LEVEL = aPRV_LEVEL;
	}

}
