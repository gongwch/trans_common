package jp.co.ais.trans2.common.db;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.department.*;

/**
 * Trans�̋Ɩ�SQL���[�e�B���e�B
 * 
 * @author AIS
 */
public class TransSQLUtil {

	/**
	 * �X�V�敪��IN���Ԃ�
	 * 
	 * @param isEntry �o�^���܂ނ�
	 * @param isApprove ���F���܂ނ�
	 * @param isSettle �m����܂ނ�
	 * @return �X�V�敪��IN��
	 */
	public static String getSlipStateInStatement(boolean isEntry, boolean isApprove, boolean isSettle) {

		List<Integer> list = new ArrayList<Integer>();

		if (isEntry) {
			list.add(SlipState.ENTRY.value);
			list.add(SlipState.FIELD_DENY.value);
			list.add(SlipState.DENY.value);
		}

		if (isApprove) {
			list.add(SlipState.FIELD_APPROVE.value);
			list.add(SlipState.APPROVE.value);
		}

		if (isSettle) {
			list.add(SlipState.UPDATE.value);
		}

		String sql = SQLUtil.getIntInStatement(list);

		return sql;

	}

	/**
	 * ����o�͏����ɑ΂����������Ԃ��B
	 * 
	 * @param depc ����o�͏���
	 * @param tableName ����K�w�}�X�^�̃e�[�u����
	 * @return ����o�͏����ɑ΂��������
	 */
	public static String getDepartmentOutputConditionStatement(DepartmentOutputCondition depc, String tableName) {
		return getOutputConditionStatement(depc, tableName);
	}

	/**
	 * ����o�͏����ɑ΂����������Ԃ��B����K�w�}�X�^�̃e�[�u�����͒Z�k��dpk�Ƃ���B
	 * 
	 * @param depc ����o�͏���
	 * @return ����o�͏����ɑ΂��������
	 */
	public static String getDepartmentOutputConditionStatement(DepartmentOutputCondition depc) {
		return getDepartmentOutputConditionStatement(depc, "dpk");
	}

	/**
	 * ��Џo�͏����ɑ΂����������Ԃ��B
	 * 
	 * @param depc ��Џo�͏���
	 * @param tableName ��ЊK�w�}�X�^�̃e�[�u����
	 * @return ��Џo�͏����ɑ΂��������
	 */
	public static String getCompanyOutputConditionStatement(CompanyOutputCondition depc, String tableName) {
		return getOutputConditionStatement(depc, tableName);
	}

	/**
	 * ��Џo�͏����ɑ΂����������Ԃ��B��ЊK�w�}�X�^�̃e�[�u�����͒Z�k��dpk�Ƃ���B
	 * 
	 * @param depc ��Џo�͏���
	 * @return ��Џo�͏����ɑ΂��������
	 */
	public static String getCompanyOutputConditionStatement(CompanyOutputCondition depc) {
		return getCompanyOutputConditionStatement(depc, "dpk");
	}

	/**
	 * �o�͏����ɑ΂����������Ԃ��B�K�w�}�X�^�̃e�[�u�����͒Z�k��dpk�Ƃ���B
	 * 
	 * @param depc �o�͏���
	 * @return �o�͏����ɑ΂��������
	 */
	public static String getOutputConditionStatement(BasicOutputCondition depc) {
		return getOutputConditionStatement(depc, "dpk");
	}

	/**
	 * �o�͏����ɑ΂����������Ԃ��B
	 * 
	 * @param depc �o�͏���
	 * @param tableName �K�w�}�X�^�̃e�[�u����
	 * @return �o�͏����ɑ΂��������
	 */
	public static String getOutputConditionStatement(BasicOutputCondition depc, String tableName) {

		String sql = " AND " + tableName + ".DPK_SSK = " + SQLUtil.getParam(depc.getOrganizationCode());

		// �z�����܂ނ�
		if (depc.isIncludeUnder()) {
			sql = sql + " AND " + tableName + ".DPK_LVL >= " + Integer.toString(depc.getLevel());
		} else {
			sql = sql + " AND " + tableName + ".DPK_LVL = " + Integer.toString(depc.getLevel());
		}

		// ���
		if (!Util.isNullOrEmpty(depc.getSuperior())) {
			sql = sql + " AND " + tableName + ".DPK_LVL_" + Integer.toString(depc.getSuperiorLevel()) + " = "
				+ SQLUtil.getParam(depc.getSuperior().getCode());
		}

		// �J�n
		if (!Util.isNullOrEmpty(depc.getFrom())) {
			sql = sql + " AND " + tableName + ".DPK_LVL_" + Integer.toString(depc.getLevel()) + " >= "
				+ SQLUtil.getParam(depc.getFrom().getCode());
		}

		// �I��
		if (!Util.isNullOrEmpty(depc.getTo())) {
			sql = sql + " AND " + tableName + ".DPK_LVL_" + Integer.toString(depc.getLevel()) + " <= "
				+ SQLUtil.getParam(depc.getTo().getCode());
		}

		// �ʑI��
		List<String> codeList = depc.getOptionalEntitiesCode();
		if (codeList != null && !codeList.isEmpty()) {
			sql = sql + " AND " + tableName + ".DPK_LVL_" + Integer.toString(depc.getLevel()) + " IN "
				+ SQLUtil.getInStatement(codeList);
		}

		return sql;

	}

}
