package jp.co.ais.trans.common.client;

import java.awt.*;

import jp.co.ais.trans.common.gui.*;

/**
 * ���C���p�l���R���g���[�������p
 */
public abstract class TPanelCtrlBase extends TAppletClientBase {

	/** �v���O������� */
	protected TClientProgramInfo prgInfo;

	/**
	 * �p�l����Ԃ�.
	 * 
	 * @see TAppletClientBase#getView()
	 */
	@Override
	public Container getView() {
		return getPanel();
	}

	/**
	 * �p�l���擾
	 * 
	 * @return �p�l��
	 */
	@SuppressWarnings("deprecation")
	public abstract TPanelBusiness getPanel();

	/**
	 * �v���O��������ݒ�
	 * 
	 * @param prgInfo �v���O�������
	 */
	public void setProgramInfo(TClientProgramInfo prgInfo) {
		this.prgInfo = prgInfo;
	}

	/**
	 * �v���O�������̎擾
	 * 
	 * @return �v���O�������
	 */
	@SuppressWarnings("deprecation")
	public TClientProgramInfo getProgramInfo() {

		if (this.prgInfo == null) {
			return TClientProgramInfo.getInstance();
		}

		return this.prgInfo;
	}

	/**
	 * �v���O����ID�擾
	 * 
	 * @return �v���O����ID
	 */
	public String getProgramCode() {

		return this.getProgramInfo().getProgramCode();
	}

	/**
	 * �v���O�������̎擾
	 * 
	 * @return �v���O��������
	 */
	public String getProgramName() {

		return this.getProgramInfo().getProgramName();
	}

	/**
	 * ��ʎ��ʎq�擾
	 * 
	 * @return ��ʎ��ʎq
	 */
	public String getRealUID() {
		return "";
	}

	/**
	 * ���O�p�ǉ����̎擾
	 * 
	 * @return ���O�p�ǉ����
	 */
	public String getRealInfo() {
		return "";
	}

}
