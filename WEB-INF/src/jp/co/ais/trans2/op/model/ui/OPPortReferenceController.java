package jp.co.ais.trans2.op.model.ui;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.port.*;

/**
 * Port�̌����R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class OPPortReferenceController extends TPortReferenceController {

	/** ��� */
	public Date baseDate;

	/** �f�t�H���g���X�g */
	public ArrayList defaultList;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �t�B�[���h
	 */
	public OPPortReferenceController(TReference field) {
		super(field);
	}

	/**
	 * @return ����������Ԃ�
	 */
	@Override
	protected PortSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition_
	 * @return Port���
	 */
	@Override
	protected List<Port> getList(PortSearchCondition condition_) {
		try {
			List<Port> list = OPLoginUtil.getPortList(condition_);
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

			List<Port> list = null;
			PortSearchCondition param = condition.clone();
			param.setValidTerm(this.baseDate);

			if (defaultList != null) {
				list = OPLoginUtil.getFilterList(defaultList, param);
			} else {
				list = getList(param);
			}

			field.name.getAutoComplete().setMatchData(list);

			boolean hasData = false;
			if (list != null && !list.isEmpty()) {
				for (Port bean : list) {
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
