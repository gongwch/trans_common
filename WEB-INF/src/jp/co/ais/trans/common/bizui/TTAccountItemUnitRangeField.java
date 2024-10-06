package jp.co.ais.trans.common.bizui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.Util;

/**
 * �Ȗڃ��j�b�g�͈͎w��t�B�[���h�ł��B
 * @author AIS
 *
 */
public class TTAccountItemUnitRangeField extends TPanel {
	
	/** �V���A��ID */
	private static final long serialVersionUID = 915374992893282388L;

	/** �Ȗڑ̌n�R�[�h */
	protected String itemSystem = "";

	/** �Ȗڃ��j�b�g�J�n */
	public TAccountItemUnit beginField;

	/** �Ȗڃ��j�b�g�I�� */
	public TAccountItemUnit endField;

	public TTAccountItemUnitRangeField() {
		initComponents();
	}

	/**
	 * �R���|�[�l���g�̏�������
	 *
	 */
	protected void initComponents() {
		
		GridBagConstraints gridBagConstraints;

		setLayout(new GridBagLayout());

		beginField = new TAccountItemUnit();
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		add(beginField, gridBagConstraints);

		endField = new TAccountItemUnit();
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		add(endField, gridBagConstraints);

	}

	/**
	 * �J�n <= �I���̏ꍇtrue�A����ȊO��false��Ԃ��܂��B
	 * @return
	 */
	public boolean isEndValueLargerThanBeginValue() {

		// �J�n�ȖځA�I���Ȗڂ̂����ꂩ�������͂̏ꍇ�̓`�F�b�N�Ȃ�
		if (Util.isNullOrEmpty(beginField.getItemCode()) || Util.isNullOrEmpty(endField.getItemCode())) {
			return true;

		// �J�n�Ȗ� < �I���Ȗڂ̏ꍇOK
		} else if (endField.getItemCode().compareTo(beginField.getItemCode()) >= 0) {
			return true;

		// �J�n�Ȗ� = �I���Ȗڂ̏ꍇ�A�⏕�Ȗڂ��r����
		} else if (endField.getItemCode().compareTo(beginField.getItemCode()) == 0) {

			// �J�n�⏕�ȖځA�I���⏕�Ȗڂ̂����ꂩ�������͂̏ꍇ�̓`�F�b�N�Ȃ�
			if (Util.isNullOrEmpty(beginField.getSubItemCode()) || Util.isNullOrEmpty(endField.getSubItemCode())) {
				return true;

			// �J�n�⏕�Ȗ� < �I���⏕�Ȗڂ̏ꍇOK
			} else if (endField.getSubItemCode().compareTo(beginField.getSubItemCode()) >= 0) {
				return true;

			// �J�n�⏕�Ȗ� = �I���⏕�Ȗڂ̏ꍇ�A����Ȗڂ��r����
			} else if (endField.getSubItemCode().compareTo(beginField.getSubItemCode()) == 0) {

				// �J�n����ȖځA�I������Ȗڂ̂����ꂩ�������͂̏ꍇ�̓`�F�b�N�Ȃ�
				if (Util.isNullOrEmpty(beginField.getBreakDownItemCode()) ||
						Util.isNullOrEmpty(endField.getBreakDownItemCode())) {
					return true;

				// �J�n����Ȗ� < �I������Ȗڂ̏ꍇOK
				} else if (endField.getBreakDownItemCode().compareTo(beginField.getBreakDownItemCode()) >= 0) {
					return true;
				}

			}

		}

		return false;
	}

}
