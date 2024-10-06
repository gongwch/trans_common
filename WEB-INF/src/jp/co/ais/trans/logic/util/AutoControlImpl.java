package jp.co.ais.trans.logic.util;

import java.sql.*;
import java.util.Date;

import org.seasar.framework.container.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans2.common.db.DBUtil;

/**
 * �����̔ԃR���g���[���N���X����
 * 
 * @author nagahashi
 */
public class AutoControlImpl implements AutoControl {

	/** �R���e�i */
	private S2Container container;

	/** �v�㕔��R�[�h */
	protected String depCode;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param container �R���e�i
	 */
	public AutoControlImpl(S2Container container) {
		super();
		this.container = container;
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
		AUTO_CTLDao autodao = (AUTO_CTLDao) container.getComponent(AUTO_CTLDao.class);
		AUTO_CTL auto = autodao.getAUTO_CTLByKaicodePrifix(companyCode, prifix);
		int lastno = increase;
		AUTO_CTL autoCTL = (AUTO_CTL) container.getComponent(AUTO_CTL.class);

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
	 * �����ݒ荀�ڂ̎擾(�`�[��ʒǉ�)
	 * 
	 * @param division �����ݒ�̋敪
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�[�R�[�h
	 * @param loginDepCode ���͕���
	 * @param systemDivision �V�X�e���敪
	 * @param slipDate �`�[���t(yyyy/mm/dd�`��)
	 * @param slipType �`�[���
	 * @param kisyu
	 * @return �����ݒ荀��
	 */
	public String getAutoSetting(String division, String companyCode, String userCode, String loginDepCode,
		String systemDivision, String slipDate, String slipType, Integer kisyu) {

		if (division.equals("1")) {
			if (Integer.parseInt(slipDate.substring(5, 7)) < kisyu) {
				String resNendo = String.valueOf(Integer.parseInt(slipDate.substring(2, 4)) - 1);
				if (1 == resNendo.length()) {
					resNendo = "0" + resNendo;
				}
				return resNendo;
			} else {
				return slipDate.substring(2, 4);
			}
		} else if (division.equals("2")) {
			if (Integer.parseInt(slipDate.substring(5, 7)) < kisyu) {
				return String.valueOf(Integer.parseInt(slipDate.substring(0, 4)) - 1);
			} else {
				return slipDate.substring(0, 4);
			}
		} else if (division.equals("3")) {
			return slipDate.substring(2, 4) + slipDate.substring(5, 7);
		} else if (division.equals("4")) {
			return slipDate.substring(0, 4) + slipDate.substring(5, 7);
		} else if (division.equals("5")) {
			return userCode;
		} else if (division.equals("6")) {
			return Util.isNullOrEmpty(this.depCode) ? loginDepCode : this.depCode;
		} else if (division.equals("7")) {
			return systemDivision;
		} else if (division.equals("8")) {
			return companyCode;
		} else if (division.equals("9")) {
			return slipType;
		}

		return "";
	}

	/**
	 * �v�㕔��R�[�h��ݒ肷��B
	 * 
	 * @param depCode
	 */
	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}

	/**
	 * �v���t�B�b�N�X�̎擾<br>
	 * �J�X�^�}�C�Y�p
	 * 
	 * @param division �����ݒ�̋敪
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�[�R�[�h
	 * @param departmentCode ���͕���
	 * @param systemDivision �V�X�e���敪
	 * @param slipDate �`�[���t(yyyy/mm/dd�`��)
	 * @param slipType �`�[���
	 * @param kisyu
	 * @return �v���t�B�b�N�X
	 */
	public String getPrefix(String division, String companyCode, String userCode, String departmentCode,
		String systemDivision, String slipDate, String slipType, Integer kisyu) {
		return "";
	}

	/**
	 * �T�t�B�b�N�X�̎擾<br>
	 * �J�X�^�}�C�Y�p
	 * 
	 * @param division �����ݒ�̋敪
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�[�R�[�h
	 * @param departmentCode ���͕���
	 * @param systemDivision �V�X�e���敪
	 * @param slipDate �`�[���t(yyyy/mm/dd�`��)
	 * @param slipType �`�[���
	 * @param kisyu
	 * @return �T�t�B�b�N�X
	 */
	public String getSuffix(String division, String companyCode, String userCode, String departmentCode,
		String systemDivision, String slipDate, String slipType, Integer kisyu) {
		return "";
	}

}
