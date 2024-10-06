package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.define.*;

/**
 * �䒠�I���R���|�[�l���g(�`�F�b�N�{�b�N�X��)�̃R���g���[��
 * 
 * @author AIS
 */
public class TBookCheckBoxSelectorController extends TController {

	/** �䒠�I���R���|�[�l���g */
	protected TBookCheckBoxSelector field;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �t�B�[���h
	 */
	public TBookCheckBoxSelectorController(TBookCheckBoxSelector field) {
		this.field = field;
		init();
	}

	/**
	 * ������
	 */
	public void init() {
		field.chkOwn.setSelected(true);
		field.chkIfrs.setSelected(true);
	}

	@Override
	public void start() {
		//
	}

	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * �I�����ꂽ�����Ԃ�
	 * 
	 * @return �I�����ꂽ����
	 */
	public AccountBook getAccountBook() {
		if (field.chkOwn.isSelected() && !field.chkIfrs.isSelected()) {
			return AccountBook.OWN;
		} else if (!field.chkOwn.isSelected() && field.chkIfrs.isSelected()) {
			return AccountBook.IFRS;
		} else if (field.chkOwn.isSelected() && field.chkIfrs.isSelected()) {
			return AccountBook.BOTH;
		}
		return null;
	}

	/**
	 * �䒠���I������Ă��邩�ǂ���
	 * 
	 * @return false�F����I������Ă��Ȃ�
	 */
	public boolean isSelected() {

		if (!field.chkOwn.isSelected() && !field.chkIfrs.isSelected()) {
			return false;
		}
		return true;
	}

}
