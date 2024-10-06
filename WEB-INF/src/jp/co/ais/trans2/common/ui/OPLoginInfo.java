package jp.co.ais.trans2.common.ui;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * OP�L���b�V�����(�ꊇ�擾�y�у`�F�b�N�p)
 */
public class OPLoginInfo extends TransferBase implements Cloneable {

	/** �N���[�� */
	@Override
	public OPLoginInfo clone() {
		try {
			return (OPLoginInfo) super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/** ���ʃf�[�^:�}�b�v */
	public Map<OPLoginDataType, OPLoginData> dataMap = new HashMap<OPLoginDataType, OPLoginData>();

	/** ���ʃf�[�^�^�C�v:�}�b�v */
	public Map<OPLoginDataType, OPLoginResultType> resultMap = new HashMap<OPLoginDataType, OPLoginResultType>();

	/** �^�C���X�^���v:�}�b�v */
	public Map<OPLoginDataType, Date> timestampMap = new HashMap<OPLoginDataType, Date>();

	/** ��������:�}�b�v */
	public Map<OPLoginDataType, OPLoginCondition> conditionMap = new HashMap<OPLoginDataType, OPLoginCondition>();

	/** ����p�R�[�h���X�g */
	public Map<OPLoginDataType, List<String>> codeListMap = new HashMap<OPLoginDataType, List<String>>();

	/**
	 * @param type
	 * @return ���ʃf�[�^
	 */
	public OPLoginData getData(OPLoginDataType type) {
		if (dataMap == null || type == null) {
			return null;
		}

		return dataMap.get(type);
	}

	/**
	 * ���ʃf�[�^�ݒ�
	 * 
	 * @param type
	 * @param data
	 */
	public void setData(OPLoginDataType type, OPLoginData data) {
		if (type == null) {
			return;
		}

		dataMap.put(type, data);
	}

	/**
	 * @param type
	 * @return ���ʃf�[�^�^�C�v
	 */
	public OPLoginResultType getResultType(OPLoginDataType type) {
		if (resultMap == null || type == null) {
			return null;
		}

		return resultMap.get(type);
	}

	/**
	 * ���ʃf�[�^�^�C�v�ݒ�
	 * 
	 * @param type
	 * @param t
	 */
	public void setResultType(OPLoginDataType type, OPLoginResultType t) {
		if (type == null || t == null) {
			return;
		}

		resultMap.put(type, t);
	}

	/**
	 * @param type
	 * @return �^�C���X�^���v
	 */
	public Date getTimestamp(OPLoginDataType type) {
		if (timestampMap == null || type == null) {
			return null;
		}

		return timestampMap.get(type);
	}

	/**
	 * �^�C���X�^���v�ݒ�
	 * 
	 * @param type
	 * @param ts
	 */
	public void setTimestamp(OPLoginDataType type, Date ts) {
		if (type == null) {
			return;
		}

		timestampMap.put(type, ts);
	}

	/**
	 * @param type
	 * @return ��������
	 */
	public OPLoginCondition getCondition(OPLoginDataType type) {
		if (conditionMap == null || type == null) {
			return null;
		}

		return conditionMap.get(type);
	}

	/**
	 * ���������ݒ�
	 * 
	 * @param type
	 * @param param
	 */
	public void setCondition(OPLoginDataType type, OPLoginCondition param) {
		if (type == null || param == null) {
			return;
		}

		conditionMap.put(type, param);
	}

	/**
	 * @param type
	 * @return �e�픻��p�R�[�h(PK)���X�g
	 */
	public List<String> getCodeList(OPLoginDataType type) {
		if (codeListMap == null || type == null) {
			return null;
		}

		return codeListMap.get(type);
	}

	/**
	 * �e�픻��p�R�[�h(PK)���X�g�ݒ�
	 * 
	 * @param type
	 * @param codeList
	 */
	public void setCodeList(OPLoginDataType type, List<String> codeList) {
		if (type == null || codeList == null) {
			return;
		}

		codeListMap.put(type, codeList);
	}
}
