package jp.co.ais.trans2.common.gui.ac;

import jp.co.ais.trans.common.dt.*;

/**
 * �C���N�������g�T�[�`���ʕ\��
 */
public class AutoCompleteValue extends TransferBase {

	/** �l */
	public Object value;

	/** �\���� */
	public String text;

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param value
	 */
	public AutoCompleteValue(Object value) {
		if (value != null) {
			if (value instanceof AutoCompletable) {
				// �\�������ďo��
				this.value = value;
				this.text = ((AutoCompletable) value).getDisplayText();
			} else {
				// toString�B��ďo��
				this.value = value;
				this.text = value.toString();
			}
		}
	}

	@Override
	public String toString() {
		return text;
	}
}
