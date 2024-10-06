package jp.co.ais.trans2.op.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.country.*;

/**
 * �������R���|�[�l���g�̃R���g���[��
 */
public class OPCountryReferenceController extends TCountryReferenceController {

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �t�B�[���h
	 */
	public OPCountryReferenceController(TReference field) {
		super(field);
	}

	/**
	 * �����������擾����
	 * 
	 * @return ��������
	 */
	@Override
	protected CountrySearchCondition getCondition() {
		return condition;
	}

	/**
	 * Country���擾����
	 * 
	 * @param condition_ ��������
	 * @return Country���
	 */
	@Override
	protected List<Country> getList(CountrySearchCondition condition_) {
		try {
			List<Country> list = OPLoginUtil.getCountryList(condition_);
			return list;

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

}
