package jp.co.ais.trans2.common.model.ui;

import javax.swing.*;
import jp.co.ais.trans2.common.gui.*;

/**
 * �����R���|�[�l���g(�u�����N)�̃R���g���[��
 * 
 * @author AIS
 */
public class TNoneReferenceController extends TReferenceController {

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �t�B�[���h
	 */
	public TNoneReferenceController(TReference field) {
		super(field);
	}

	@Override
	public void init() {
		super.init();
		setEditable(false);
	}

	@Override
	public boolean code_Verify(@SuppressWarnings("unused") JComponent comp) {
		return false;
	}

	@Override
	public void btnSearch_Click() {
		//		
	}

	@Override
	public void btnSettle_Click() {
		//
	}

	@Override
	public String getDialogCaption() {
		return null;
	}

	@Override
	public String getButtonCaption() {
		return null;
	}

	@Override
	public String getTableKeyName() {
		return null;
	}

	/**
	 * ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity��Ԃ�
	 * @return ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity
	 */
	public Object getUnspecifiedEntity() {
		return null;
	}
}
