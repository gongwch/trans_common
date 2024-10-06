package jp.co.ais.trans2.common.model.ui;

import java.awt.event.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * �`�[��ʑI��(�e�[�u���`�F�b�N�`��)�R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TSlipTypeListSelectorController extends TController {

	/** �t�B�[���h */
	protected TSlipTypeListSelector field;

	/** �������� */
	protected SlipTypeSearchCondition condition;

	/** �`�[��ʃX�v���b�h�S�f�[�^���X�g */
	protected List<SlipType> slipList;

	/** �`�[��ʃX�v���b�h�Fmap */
	protected Map<String, Boolean> map = new HashMap<String, Boolean>();

	/**
	 * �R���X�g���N�^(�ꗗ�f�[�^�������\������)
	 * 
	 * @param field �t�B�[���h
	 */
	public TSlipTypeListSelectorController(TSlipTypeListSelector field) {
		this(field, true);
	}

	/**
	 * �R���X�g���N�^(�ꗗ�f�[�^�������\�����Ȃ�)
	 * 
	 * @param field
	 * @param isRefresh
	 */
	public TSlipTypeListSelectorController(TSlipTypeListSelector field, boolean isRefresh) {

		try {

			this.field = field;

			initSearchCondition();

			if (isRefresh) {
				init(condition);
			}

		} catch (TException e) {
			errorHandler(e);
		}
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
	 * ��������������
	 */
	protected void initSearchCondition() {
		condition = new SlipTypeSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setReferOtherSystemDivision(true);
	}

	/**
	 * �ݒ肳�ꂽ�������������ɓ`�[��ʈꗗ���ēǍ�����B
	 */
	protected void refresh() {
		try {
			init(condition);

		} catch (TException e) {
			errorHandler(e);
		}
	}

	/**
	 * ������
	 * 
	 * @param condition1
	 * @throws TException
	 */
	protected void init(SlipTypeSearchCondition condition1) throws TException {

		field.removeRow();

		slipList = getSlipType(condition1);

		boolean isChecked = ClientConfig.isFlagOn("trans.sliptype.init.checkall");

		for (SlipType slipType : slipList) {
			field
				.addRow(new Object[] { isChecked, slipType.getCode() + " : " + slipType.getNames(), slipType.getCode() });
			map.put(slipType.getCode(), isChecked);

		}
		filterEvent();

	}

	/**
	 * �t�B���^�[���̓C�x���g��`
	 */
	protected void filterEvent() {
		field.ctrlFilter.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				filter_Input(e);

			}

		});
	}

	/**
	 * �`�[��ʈꗗ[�t�B���^�[����]
	 * 
	 * @param e
	 */
	protected void filter_Input(KeyEvent e) {

		for (int i = 0; i < field.getRowCount(); i++) {

			// �`�F�b�N�l�̎擾
			Boolean isChecked = (Boolean) field.getRowValueAt(i, TSlipTypeListSelector.SC.CHECK);

			// �`�F�b�N����Ă���ꍇ�A���X�g�ɑΏۍX�V��ǉ�
			String key = (String) field.getRowValueAt(i, TSlipTypeListSelector.SC.SLIPTYPE_VALUE);
			map.put(key, isChecked);

		}

		field.removeRow();

		for (SlipType slipType : slipList) {
			String name = slipType.getCode() + ":" + slipType.getNames();

			if (name.contains(field.ctrlFilter.getInputText())) {

				field.addRow(new Object[] { map.get(slipType.getCode()),
						slipType.getCode() + " : " + slipType.getNames(), slipType.getCode() });
			}

		}
	}

	/**
	 * �`�[��ʂ��擾����
	 * 
	 * @param searchCondition
	 * @return List<�`�[���>
	 * @throws TException
	 */
	@SuppressWarnings("unchecked")
	protected List<SlipType> getSlipType(SlipTypeSearchCondition searchCondition) throws TException {

		List<SlipType> list = (List<SlipType>) request(getSlipTypeManagerClass(), "get", searchCondition);

		return list;
	}

	/**
	 * �`�[��ʒ��o�N���X��Ԃ�
	 * 
	 * @return �N���X
	 */
	protected Class getSlipTypeManagerClass() {
		return SlipTypeManager.class;
	}

	/**
	 * �`�F�b�N�����s����Ԃ�
	 * 
	 * @return �`�F�b�N�s��
	 */
	public int getCheckedRowCount() {

		// �`�F�b�N�s�̎擾
		int checkedCount = 0;

		for (int i = 0; i < field.getRowCount(); i++) {

			// �`�F�b�N�l�̎擾�y��map�X�V
			Boolean isChecked = (Boolean) field.getRowValueAt(i, TSlipTypeListSelector.SC.CHECK);
			String value = (String) field.getRowValueAt(i, TSlipTypeListSelector.SC.SLIPTYPE_VALUE);
			map.put(value, isChecked);

		}
		for (Map.Entry<String, Boolean> entry : map.entrySet()) {
			if (entry.getValue() != null && entry.getValue()) {
				checkedCount++;
			}
		}

		return checkedCount;

	}

	/**
	 * �`�F�b�N�����`�[��ʂ�Ԃ�
	 * 
	 * @return List<�`�[���>
	 */
	public List<String> getCheckedSlipType() {

		List<String> list = new LinkedList<String>();
		for (int i = 0; i < field.getRowCount(); i++) {

			// �`�F�b�N�l�̎擾�y��map�X�V
			Boolean isChecked = (Boolean) field.getRowValueAt(i, TSlipTypeListSelector.SC.CHECK);
			String value = (String) field.getRowValueAt(i, TSlipTypeListSelector.SC.SLIPTYPE_VALUE);
			map.put(value, isChecked);

		}
		for (Map.Entry<String, Boolean> entry : map.entrySet()) {
			if (entry.getValue() != null && entry.getValue()) {
				list.add(entry.getKey());
			}
		}

		return list;
	}

	/**
	 * @return ��������
	 */
	public SlipTypeSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition ��������
	 */
	public void setCondition(SlipTypeSearchCondition condition) {
		this.condition = condition;
	}

}
