package jp.co.ais.trans2.op.model.ui;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.currency.Currency;

/**
 * �ʉ݌����R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class OPCurrencyReferenceController extends TCurrencyReferenceController {

	/** ��� */
	public Date baseDate;

	/** �f�t�H���g���X�g */
	public ArrayList defaultList;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �t�B�[���h
	 */
	public OPCurrencyReferenceController(TReference field) {
		super(field);
	}

	/**
	 * @return �_�C�A���O��ʂł̌����������擾
	 */
	@Override
	protected CurrencySearchCondition getCondition() {
		return condition;
	}

	@Override
	public void init() {

		super.init();

		field.code.setRegex(null); // ���������͉\
	}

	/**
	 * �t�B�[���h����R�[�h�𒼐ړ��͂����ꍇ�̌���
	 * 
	 * @return Entity
	 */
	@Override
	protected Currency getInputtedEntity() {

		field.code.setText(field.code.getText().toUpperCase()); // �啶�������ϊ�

		return super.getInputtedEntity();
	}

	/**
	 * @param condition_
	 * @return �ʉݏ��
	 */
	@Override
	protected List<Currency> getList(CurrencySearchCondition condition_) {

		try {
			List<Currency> list = OPLoginUtil.getCurrencyList(condition_);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	@Override
	public Object getUnspecifiedEntity() {
		Currency entity1 = new Currency();
		entity1.setCode(field.code.getText().toUpperCase()); // �啶�������ϊ�
		entity1.setNames(field.name.getText());
		return entity1;
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

			List<Currency> list = null;
			CurrencySearchCondition param = condition.clone();
			param.setValidTerm(this.baseDate);

			if (defaultList != null) {
				list = OPLoginUtil.getFilterList(defaultList, param);
			} else {
				list = getList(param);
			}

			field.name.getAutoComplete().setMatchData(list);

			boolean hasData = false;
			if (list != null && !list.isEmpty()) {
				for (Currency bean : list) {
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
