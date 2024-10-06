package jp.co.ais.trans2.op.model.ui;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.customer.*;

/**
 * ����挟���R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class OPCustomerReferenceController extends TCustomerReferenceController {

	/** ��� */
	public Date baseDate;

	/** �f�t�H���g���X�g */
	public ArrayList defaultList;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �t�B�[���h
	 */
	public OPCustomerReferenceController(TReference field) {
		super(field);
	}

	/**
	 * @return �_�C�A���O��ʂł̌����������擾
	 */
	@Override
	protected CustomerSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition_
	 * @return �����Entity
	 */
	@Override
	protected List<Customer> getList(CustomerSearchCondition condition_) {
		try {
			List<Customer> list = OPLoginUtil.getCustomerList(condition_);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * ����ݒ�
	 * 
	 * @param termDate
	 */
	public void setTermDate(Date termDate) {
		if (field.name.getAutoComplete() == null) {
			return;
		}

		Date dt = termDate;
		if (dt == null) {
			// ������w��̏ꍇ�̓V�X�e�����t
			dt = Util.getCurrentDate();
		}

		boolean isChanged = !equals(this.baseDate, dt);

		this.baseDate = dt;

		// �f�t�H���g���X�g�̕ێ�
		if (defaultList == null) {
			if (field.name.getAutoComplete().getMatchDataList() != null) {
				defaultList = new ArrayList(field.name.getAutoComplete().getMatchDataList());
			}
		}

		if (isChanged) {

			// 1. �C���N�������g�T�[�`�̃��X�g�X�V

			List<Customer> list = null;
			CustomerSearchCondition param = condition.clone();
			param.setValidTerm(this.baseDate);

			if (defaultList != null) {
				list = OPLoginUtil.getFilterList(defaultList, param);
			} else {
				list = getList(param);
			}

			field.name.getAutoComplete().setMatchData(list);

			boolean hasData = false;
			if (list != null && !list.isEmpty()) {
				for (Customer bean : list) {
					if (equals(bean.getCode(), field.getCode())) {
						hasData = true;
						break;
					}
				}
			}

			if (!hasData && field.name.isEditable()) {
				// ���Ԑ؂ꂽ�ꍇ�ɑD�ύX�\�ł���΃N���A����
				clear();
			}
		}
	}

}
