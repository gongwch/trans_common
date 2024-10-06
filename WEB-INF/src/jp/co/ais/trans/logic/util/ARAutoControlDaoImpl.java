package jp.co.ais.trans.logic.util;

import java.sql.*;
import java.text.*;
import java.util.Date;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;

/**
 * AR�������ԍ��p�����̔�Dao����
 */
public class ARAutoControlDaoImpl extends TModel implements ARAutoControlDao {

	/**
	 * �v���t�B�b�N�X�̎擾<br>
	 * �J�X�^�}�C�Y�p
	 * 
	 * @param division �����ݒ�̋敪
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�[�R�[�h
	 * @param departmentCode ���͕���
	 * @param slipDate �`�[���t(yyyy/mm/dd�`��)
	 * @return �v���t�B�b�N�X
	 */
	public String getPrefix(String division, String companyCode, String userCode, String departmentCode, String slipDate) {
		return "";
	}

	/**
	 * �����ݒ荀�ڂ̎擾
	 * 
	 * @param jid �����ݒ�̋敪
	 * @param jidName �����ݒ�̌ŗL����
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�[�R�[�h
	 * @param depCode ���͕���
	 * @param slipDate �`�[���t(yyyy/mm/dd�`��)
	 * @return �����ݒ荀��
	 */
	public String getAutoSetting(InvoiceNoAdopt jid, String jidName, String companyCode, String userCode,
		String depCode, String slipDate) {
		try {

			switch (jid) {
				case NONE: // �Ȃ�
					return "";

				case FIXED_CHAR: // �ŗL����
					return jidName;

				case YY_DATE: // �N�x(YY)
					String yy = Util.avoidNull(BizUtil.getFiscalYear(slipDate, companyCode));
					return StringUtil.rightBX(yy, 2);

				case YYYY_DATE:// �N�x(YYYY)
					return Util.avoidNull(BizUtil.getFiscalYear(slipDate, companyCode));

				case MM_DATE: // ���x
					String mm = Util.avoidNull(BizUtil.getFiscalMonth(slipDate, companyCode));
					return StringUtil.rightBX(("00" + mm), 2);

				case YYMM_DATE: // �N���iYYMM�j
					return slipDate.replaceAll("/", "").substring(3, 6);

				case YYYYMM_DATE: // �N���iYYYYMM�j
					return StringUtil.leftBX(slipDate.replaceAll("/", ""), 6);

				case DEPARTMENT: // ����
					return depCode;

				case USER: // ���[�U�[
					return userCode;

				case CODE: // ���
					return companyCode;

				default:
					return "";
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * �����̔Ԃ��ꂽ�ԍ����擾<br>
	 * �����̔ԃR���g���[���̍X�V���s��
	 * 
	 * @param companyCode
	 * @param userCode
	 * @param prifix
	 * @param increase
	 * @return �����̔Ԕԍ�
	 */
	public int getAutoNumber(String companyCode, String userCode, String prifix, int increase) {

		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			// �r���R���g���[���}�X�^�����b�N
			try {
				DBUtil.execute(conn, "LOCK TABLE AUTO_CTL IN SHARE ROW EXCLUSIVE MODE NOWAIT");
			} catch (TException e) {
				throw new TRuntimeException("W01133");// �������ݍ����Ă���܂��B���΂炭���҂����������B
			}
		} catch (TException ex) {
			throw new TRuntimeException(ex);
		} finally {
			DBUtil.close(conn);
		}

		// �����̔ԃR���g���[�����擾(�ŏI�ԍ�)
		AUTO_CTLDao autodao = (AUTO_CTLDao) getComponent(AUTO_CTLDao.class);
		AUTO_CTL auto = autodao.getAUTO_CTLByKaicodePrifix(companyCode, prifix);

		int lastno = increase;
		AUTO_CTL autoCTL = (AUTO_CTL) getComponent(AUTO_CTL.class);

		Date sysDate = Util.getCurrentDate();

		autoCTL.setKAI_CODE(companyCode);
		autoCTL.setPRIFIX(prifix);
		autoCTL.setUPD_DATE(sysDate);
		autoCTL.setUSR_ID(userCode);

		if (auto != null) {
			// �ŏI�ԍ��擾
			lastno = auto.getLAST_NO() + increase;
			autoCTL.setLAST_NO(lastno);
			// �X�V����
			autodao.update(autoCTL);
		} else {
			// �V�K�o�^
			autoCTL.setLAST_NO(lastno);
			autoCTL.setINP_DATE(sysDate);
			autodao.insert(autoCTL);
		}

		return lastno;
	}

	/**
	 * �T�t�B�b�N�X�̎擾<br>
	 * �J�X�^�}�C�Y�p
	 * 
	 * @param division �����ݒ�̋敪
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�[�R�[�h
	 * @param departmentCode ���͕���
	 * @param slipDate �`�[���t(yyyy/mm/dd�`��)
	 * @return �T�t�B�b�N�X
	 */
	public String getSuffix(String division, String companyCode, String userCode, String departmentCode, String slipDate) {
		return "";
	}

}