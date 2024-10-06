package jp.co.ais.trans.master.logic.impl;

import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.logic.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * �����x�������}�X�^����
 */
public class CustomerConditionLogicImpl extends CommonLogicBaseImpl implements CustomerConditionLogic {

	/** �����x�������}�X�^Dao */
	private TRI_SJ_MSTDao dao;

	/** �����x�������}�X�^���� */
	private TRI_SJ_MST entity;

	/** ��s�}�X�^Dao */
	private BNK_MSTDao bnkDao;

	/** �x�����@�}�X�^DAO */
	private AP_HOH_MSTDao aphohMstDao;

	/** ��s�����}�X�^DAO */
	private AP_CBK_MSTDao apcbkMstDao;

	/**
	 * �f�t�H���g�R���X�g���N�^.
	 * 
	 * @param dao TRI_SJ_MSTDao
	 */
	public CustomerConditionLogicImpl(TRI_SJ_MSTDao dao) {
		// �����x�������}�X�^Dao��Ԃ�
		this.dao = dao;
	}

	/**
	 * TRI_SJ_MST�C���X�^���X�̐ݒ�.
	 * 
	 * @param entity TRI_SJ_MST
	 */
	public void setEntity(TRI_SJ_MST entity) {
		// �����x�������}�X�^���̂�Ԃ�
		this.entity = entity;
	}

	/**
	 * ��s�}�X�^�ꗗ�ݒ肵�܂��B
	 * 
	 * @param dao ��s�}�X�^�ꗗ
	 */
	public void setBNK_MSTDao(BNK_MSTDao dao) {
		// ��s�}�X�^Dao��Ԃ�
		this.bnkDao = dao;
	}

	/**
	 * �x�����@�}�X�^�ꗗ�ݒ肵�܂��B
	 * 
	 * @param aphohMstDao �x�����@�}�X�^�ꗗ
	 */
	public void setAphohMstDao(AP_HOH_MSTDao aphohMstDao) {
		this.aphohMstDao = aphohMstDao;
	}

	/**
	 * ��s�����}�X�^�ꗗ�ݒ肵�܂��B
	 * 
	 * @param apcbkMstDao ��s�����}�X�^�ꗗ
	 */
	public void setApcbkMstDao(AP_CBK_MSTDao apcbkMstDao) {
		this.apcbkMstDao = apcbkMstDao;
	}

	/**
	 * ���ʂ���������
	 * 
	 * @param conditions Map
	 */
	public List find(Map conditions) {
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) conditions.get("kaiCode");
		// �J�n�R�[�h�̎擾
		String beginTriSjCode = (String) conditions.get("beginTriSjCode");
		// �I���R�[�h�̎擾
		String endTriSjCode = (String) conditions.get("endTriSjCode");
		// ���ʂ�Ԃ�
		return dao.getAllTRI_SJ_MST2(kaiCode, beginTriSjCode, endTriSjCode);

	}

	/**
	 * ���ʂ���������
	 * 
	 * @param keys Map
	 */
	public Object findOne(Map keys) {
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) keys.get("kaiCode");
		// �����R�[�h�̐ݒ�
		String triCode = (String) keys.get("triCode");
		// ���������R�[�h�̐ݒ�
		String tjkCode = (String) keys.get("tjkCode");
		// ���ʂ�Ԃ�
		return dao.getTRI_SJ_MSTByKaicodeTricodeTjkcode(kaiCode, triCode, tjkCode);
	}

	/**
	 * �f�[�^���폜����
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) {
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) conditions.get("kaiCode");
		// �����R�[�h�̎擾
		String triCode = (String) conditions.get("triCode");
		// ���������R�[�h�̎擾
		String tjkCode = (String) conditions.get("tjkCode");
		// ��ЃR�[�h�̐ݒ�
		entity.setKAI_CODE(kaiCode);
		// �����R�[�h�̐ݒ�
		entity.setTRI_CODE(triCode);
		// ���������R�[�h�̐ݒ�
		entity.setTJK_CODE(tjkCode);
		// �f�[�^���폜����
		dao.delete(entity);
	}

	/**
	 * �f�[�^��o�^����
	 * 
	 * @param dto Object
	 */
	protected void insertImpl(Object dto) {
		// ���̂̏�����
		TRI_SJ_MST entity1 = (TRI_SJ_MST) dto;

		// �f�[�^��o�^����
		dao.insert(entity1);
	}

	/**
	 * �f�[�^���X�V����
	 * 
	 * @param dto Object
	 */
	protected void updateImpl(Object dto, Object oldDto) {
		// ���̂̏�����
		TRI_SJ_MST entity1 = (TRI_SJ_MST) dto;

		// �f�[�^���X�V����
		dao.update(entity1);
	}

	/**
	 * �ҏW���̃��R�[�h�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A dao�̃��\�b�h���ďo���ADB����ҏW���̃��R�[�h���擾����
	 * 
	 * @param dto
	 * @return Object
	 */
	protected Object getOldEntity(Object dto) {
		TRI_SJ_MST entity1 = (TRI_SJ_MST) dto;
		String kaiCode = entity1.getKAI_CODE();
		String triCode = entity1.getTRI_CODE();
		String tjkCode = entity1.getTJK_CODE();

		return dao.getTRI_SJ_MSTByKaicodeTricodeTjkcode(kaiCode, triCode, tjkCode);
	}

	/**
	 * �ҏW���̃��R�[�h�̎�L�[�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A �ʏ�ꍇ�́A��ЃR�[�h�ȊO�̎�L�[��߂�B
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		TRI_SJ_MST entity1 = (TRI_SJ_MST) dto;
		return entity1.getTJK_CODE();
	}

	/**
	 * �_�C�A���O�ɕ\����������惊�X�g�擾
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param triCode �����R�[�h
	 * @param tjkCode ���������R�[�h
	 * @param termBasisDate �L������
	 * @return ����惊�X�g
	 */
	public List<Object> refDailog(String kaiCode, String triCode, String tjkCode, Timestamp termBasisDate) {
		// �߂�l�̃��X�g
		List<Object> addedList = new ArrayList<Object>();
		// �����ɓ�����x�������R�[�h�B���K��
		List<Object> tjkList = dao.refDialogSearch(kaiCode, triCode, tjkCode, termBasisDate);
		// �R�[�h�����݂����
		if (!tjkList.isEmpty()) {
			Iterator i = tjkList.iterator();
			// �����R�[�h�ʂɎx����s�����K������B
			while (i.hasNext()) {
				TRI_SJ_MST triSj = (TRI_SJ_MST) i.next();
				String tjkCodes = triSj.getTJK_CODE();
				String bankAccout = "";
				String yknNo = triSj.getYKN_NO();
				StringBuilder fieldName = new StringBuilder();
				String bankName = triSj.getYKN_NAME();
				String bankStnName = triSj.getYKN_KANA();
				fieldName.append(bankName);
				fieldName.append(" ");
				fieldName.append(Util.avoidNull(bankStnName));
				fieldName.append(" ");
				fieldName.append(Util.avoidNull(yknNo));
				bankAccout = fieldName.toString();
				List<String> added = new ArrayList<String>();
				added.add(tjkCodes);
				added.add(bankAccout);
				// �����e�[�u���̎x����s�R�[�h����s�e�[�u���ɑ��݂���ꍇ�A
				// �������[�h�Ƌ�s�������X�g�ɒǉ��B
				addedList.add(added);

			}
		}
		return addedList;
	}

	/**
	 * ���������R�[�h�R�[�h�ɂ��A�������擾
	 * 
	 * @param kaiCode
	 * @param triCode
	 * @param tjkCode
	 * @return �������
	 */
	public List<Object> searchName(String kaiCode, String triCode, String tjkCode) {
		// �߂�l�̃��X�g
		List<Object> addedList = new ArrayList<Object>();
		List<Object> tjkRows = dao.searchBnkName(kaiCode, triCode, tjkCode);

		if (!tjkRows.isEmpty()) {
			TRI_SJ_MST tjkRow = (TRI_SJ_MST) tjkRows.get(0);
			String tjkCodes = tjkRow.getTJK_CODE();
			Date strDate = tjkRow.getSTR_DATE();
			Date endDate = tjkRow.getEND_DATE();
			String yknNo = tjkRow.getYKN_NO();
			// ��s�R�[�h
			String bankAccout = "";
			// �x����s�R�[�h
			String bnkStnCode = tjkRow.getBNK_STN_CODE();
			// �����e�[�u���̋�s�R�[�h
			String bnkCode = tjkRow.getBNK_CODE();
			// ��s�R�[�h�Ǝx����s�R�[�h�ŋ�s�����K��
			BNK_MST bnk = bnkDao.getBNK_MSTByBnkcodeBnkStncode(bnkCode, bnkStnCode);
			StringBuilder fieldName = new StringBuilder();
			String bankName = bnk.getBNK_NAME_S();
			String bankStnName = bnk.getBNK_STN_NAME_S();
			fieldName.append(bankName);
			fieldName.append(" ");
			fieldName.append(bankStnName);
			fieldName.append(" ");
			fieldName.append(yknNo);
			bankAccout = fieldName.toString();

			// �����Ƌ�s���𒲍������f�[�^���\��
			List<Object> added = new ArrayList<Object>();
			added.add(tjkCodes);
			added.add(bankAccout);
			added.add(strDate);
			added.add(endDate);

			// �K��������s�����X�g��߂�l���X�g�ɍڂ���B
			addedList.add(added);
		}
		return addedList;
	}

	/**
	 * �f�t�H���g�̌��������擾
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param triCode �����R�[�h
	 * @param curCode �ʉ݃R�[�h
	 * @return map ���������R�[�h�E�x����s��
	 */
	public Map<String, Object> getDefaultPaymentCondition(String kaiCode, String triCode, String curCode) {

		TRI_SJ_MST tjkMst = dao.getDefaultPaymentCondition(kaiCode, triCode, curCode);

		// ���������R�[�h
		String tjkCode = "";
		// ��s�R�[�h
		String bnkcode = "";
		// ��s�x�X��
		String stncode = "";
		// �����ԍ�
		String yknNo = "";

		if (!Util.isNullOrEmpty(tjkMst)) {
			tjkCode = Util.avoidNull(tjkMst.getTJK_CODE());
			bnkcode = Util.avoidNull(tjkMst.getBNK_CODE());
			stncode = Util.avoidNull(tjkMst.getBNK_STN_CODE());
			yknNo = Util.avoidNull(tjkMst.getYKN_NO());
		}

		// �x����s��
		String bnkname = "";

		// �x����s���A�����ԍ����擾
		BNK_MST bnk = bnkDao.getBNK_MSTByBnkcodeBnkStncode(bnkcode, stncode);
		if (!Util.isNullOrEmpty(bnk)) {
			bnkname = bnk.getBNK_NAME_S() + ' ' + bnk.getBNK_STN_NAME_S() + ' ' + yknNo;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tjkCode", Util.avoidNull(tjkCode));
		map.put("bnkname", Util.avoidNull(bnkname));

		return map;
	}

	/**
	 * �x����������͎��� �x���擝�������擾
	 * 
	 * @param param
	 * @return map ����擝�����
	 * @throws ParseException
	 */
	public Map<String, Object> getPaymentConditionInfo(Map<String, String> param) throws ParseException {
		String kaiCode = param.get("kaiCode");
		String triCode = param.get("triCode");
		String tjkCode = param.get("tjkCode");
		String slipDate = param.get("slipDate");
		TRI_SJ_MST tjkMst = dao.getTRI_SJ_MSTByKaicodeTricodeTjkcode(kaiCode, triCode, tjkCode);
		// �x����R�[�h
		String tjkcode = "";
		// �x����s��
		String bnkname = "";
		// ��s�R�[�h
		String bnkcode = "";
		// ��s�x�X��
		String stncode = "";
		// �x���敪
		String sihakbn = "";
		// �x�������x����
		String pDate = "";
		// �x���������ߓ�
		String cDate = "";
		// �x���������ߌ㌎
		String mon = "";
		// �U�o��s�R�[�h
		String huricode = "";
		// �J�n��
		String strDate = "";
		// �I����
		String endDate = "";
		// �x�����@�R�[�h
		String sihahouCode = "";
		// �x�����@����
		String sihahouName = "";
		// �x�������R�[�h
		String naicode = "";
		// �U�o��s����
		String huriname = "";
		// �t���O 0:�f�[�^�Ȃ� 1:�f�[�^�L��
		String flag = "0";
		// �x����
		String sihaDate = "";

		if (tjkMst != null) {
			flag = "1";
			// ���������R�[�h
			tjkcode = Util.avoidNull(tjkMst.getTJK_CODE());
			// ��s�R�[�h
			bnkcode = Util.avoidNull(tjkMst.getBNK_CODE());
			// ��s�x�X��
			stncode = Util.avoidNull(tjkMst.getBNK_STN_CODE());
			// �x���敪
			sihakbn = Util.avoidNull(tjkMst.getSIHA_KBN());
			// �x�������x����
			pDate = Util.avoidNull(tjkMst.getSJP_DATE());
			// �x���������ߓ�
			cDate = Util.avoidNull(tjkMst.getSJC_DATE());
			// �x���������ߌ㌎
			mon = Util.avoidNull(tjkMst.getSJR_MON());
			// �U�o��s�R�[�h
			huricode = Util.avoidNull(tjkMst.getFURI_CBK_CODE());
			// �J�n��
			strDate = DateUtil.toYMDString(tjkMst.getSTR_DATE());
			// �I����
			endDate = DateUtil.toYMDString(tjkMst.getEND_DATE());
			// �����ԍ�
			String yknNo = Util.avoidNull(tjkMst.getYKN_NO());

			// �x����s���A�����ԍ����擾
			BNK_MST bnk = bnkDao.getBNK_MSTByBnkcodeBnkStncode(bnkcode, stncode);
			if (!Util.isNullOrEmpty(bnk)) {
				bnkname = Util.avoidNull(bnk.getBNK_NAME_S()) + ' '
					+ Util.avoidNull(bnk.getBNK_STN_NAME_S() + ' ' + yknNo);
			}

			// �x�����@�R�[�h�̎擾
			TRI_SJ_MST tjkMst2 = dao.getSihhoh(kaiCode, triCode, tjkCode);
			if (!Util.isNullOrEmpty(tjkMst2)) {
				sihahouCode = Util.avoidNull(tjkMst2.getSIHA_HOU_CODE());
			}

			// �x�����@���̎擾
			AP_HOH_MST aphoh = aphohMstDao.getAP_HOH_MSTByKaicodehohhohcode(kaiCode, sihahouCode);
			if (!Util.isNullOrEmpty(aphoh)) {
				sihahouName = Util.avoidNull(aphoh.getHOH_HOH_NAME());
				naicode = Util.avoidNull(aphoh.getHOH_NAI_CODE());
			}

			// �U�o��s���̎擾
			AP_CBK_MST apcbk = apcbkMstDao.getAP_CBK_MSTByKaicodecbkcbkcodeoutfbkbn(kaiCode, huricode);
			String huribnkcode = "";
			String huristncode = "";
			if (!Util.isNullOrEmpty(apcbk)) {
				huribnkcode = Util.avoidNull(apcbk.getCBK_BNK_CODE());
				huristncode = Util.avoidNull(apcbk.getCBK_STN_CODE());

			}

			// �U�o��s���̎擾
			BNK_MST huribnk = bnkDao.getBNK_MSTByBnkcodeBnkStncode(huribnkcode, huristncode);
			if (!Util.isNullOrEmpty(huribnk)) {
				huriname = Util.avoidNull(huribnk.getBNK_NAME_S()) + ' ' + Util.avoidNull(huribnk.getBNK_STN_NAME_S());
			}

			// ���t��ݒ肵�Ă��Ȃ��ꍇ�A�x���\��������߂Ȃ�
			if (!Util.isNullOrEmpty(slipDate)) {
				// �x����
				boolean bol = !Util.isNullOrEmpty(cDate) && !Util.isNullOrEmpty(mon) && !Util.isNullOrEmpty(pDate);

				if (bol) {
					sihaDate = BizUtil.getInitialBalanceDate(kaiCode, triCode, tjkCode, slipDate, 0);
				} else {
					sihaDate = slipDate;
				}
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tjkcode", tjkcode); // ���������R�[�h
		map.put("bnkcode", bnkcode); // �x����s�R�[�h
		map.put("stncode", stncode); // �x�X�R�[�h
		map.put("bnkname", bnkname); // �x����s��
		map.put("sihahouCode", sihahouCode); // �x�����@�R�[�h
		map.put("sihahouName", sihahouName); // �x�����@����
		map.put("huricode", huricode); // �U�o��s�R�[�h
		map.put("huriname", huriname); // �U�o��s����
		map.put("sihakbn", sihakbn); // �x���敪
		map.put("naicode", naicode); // �x�������R�[�h
		map.put("strDate", strDate); // �J�n���t
		map.put("endDate", endDate); // �I�����t
		map.put("flag", flag); // �t���O
		map.put("sihaDate", sihaDate); // �x���\���

		return map;
	}

	/**
	 * @see jp.co.ais.trans.master.logic.CustomerConditionLogic#findOneInfo(java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	public Object findOneInfo(String kaiCode, String triCode, String triSjCode) {
		return dao.getOneTRI_SJ_MST2(kaiCode, triCode, triSjCode);
	}
}
