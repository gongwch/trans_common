package jp.co.ais.trans2.model.slip.panel;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * �`�[���Class�擾����
 */
public abstract class SlipPanelLogic extends TModel {

	/** �`�[��� */
	protected String slipType;

	/** �f�[�^�敪 */
	protected String dataType;

	/**
	 * �`�[���
	 * 
	 * @param slipType �`�[���
	 */
	public void setSlipType(String slipType) {
		this.slipType = slipType;
	}

	/**
	 * �`�[��ʎ擾
	 * 
	 * @return �`�[���
	 * @throws TException
	 */
	public SlipType getSlipType() throws TException {
		return getSlipType(this.slipType);
	}

	/**
	 * �`�[���
	 * 
	 * @param typeNo �`�[��ʔԍ�
	 * @return �`�[���
	 * @throws TException
	 */
	public SlipType getSlipType(String typeNo) throws TException {
		SlipTypeManager slipTypeManager = (SlipTypeManager) getComponent(SlipTypeManager.class);
		SlipType type = slipTypeManager.get(getCompanyCode(), typeNo);

		if (type == null) {
			// �`�[���[{0}]���ݒ肳��Ă��܂���B
			throw new TException("I00128", typeNo);
		}

		return type;
	}

	/**
	 * �f�[�^�敪
	 * 
	 * @param dataKind �f�[�^�敪
	 */
	public void setDataType(String dataKind) {
		this.dataType = dataKind;
	}

	/**
	 * @param prgCode �v���O�����R�[�h
	 * @return Class
	 */
	public abstract Class getSlipPanelClass(String prgCode);

}
