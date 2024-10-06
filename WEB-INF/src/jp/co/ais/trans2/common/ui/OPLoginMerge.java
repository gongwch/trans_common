package jp.co.ais.trans2.common.ui;

import java.util.*;
import java.util.Map.Entry;

import jp.co.ais.trans.common.log.*;

/**
 * �}�[�W�N���X
 * 
 * @param <T>
 */
public class OPLoginMerge<T> {

	/** �f�[�^�敪 */
	protected OPLoginDataType dataType;

	/**
	 * �}�[�W����
	 * 
	 * @param kbn
	 * @param bean
	 * @param list
	 * @return �}�[�W��bean
	 */
	public OPLoginData merge(OPLoginDataType kbn, OPLoginData bean, Object list) {
		if (list == null) {
			return bean;
		}
		ClientLogger.debug("merge start:" + kbn);

		this.dataType = kbn;

		List<T> diffList = (List<T>) list;

		if (bean.getList() == null || bean.getList().isEmpty()) {
			// 1. bean�Ƀ��X�g�͎����Ă��Ȃ��ꍇ�ɁA�������X�g�͑S�Ď��悤�ɂ���
			bean.setList(diffList);
			return bean;
		}

		// 2. �����\�[�g�̃}�b�v��p�ӂ���
		Map<String, T> map = new TreeMap<String, T>();
		Map<String, T> diffMap = new TreeMap<String, T>();

		// 3. �����̃L�[�}�b�v
		for (int i = 0; i < bean.getList().size(); i++) {
			T e = (T) bean.getList().get(i);
			map.put(OPLoginDataType.getKey(dataType, e), e);
		}

		// 4.�����̃L�[�}�b�v
		for (T e : diffList) {
			diffMap.put(OPLoginDataType.getKey(dataType, e), e);
		}

		// 5. �L�[�P�ʂōŐV���v�b�g
		for (Entry<String, T> entry : diffMap.entrySet()) {
			map.put(entry.getKey(), entry.getValue());
		}

		// 6. Map to List
		List<T> newList = new ArrayList<T>(map.values());

		// 6.2
		// CODE�}�X�^�A����̏ꍇ�Ƀ\�[�g������
		if (bean.getDataTypeValue() == OPLoginDataType.OP_CODE_MST.value //
			|| bean.getDataTypeValue() == OPLoginDataType.CM_BNKR_TYPE_MST.value) {
			// OP_CODE_MST, CM_BNKR_TYPE_MST
			// �\�[�g����DISP_ODR
			Collections.sort(newList, new OPLoginMergeComparator<T>());
		}

		// 7. �ݒ�
		bean.setList(newList);

		ClientLogger.debug("merge finish:");

		return bean;

	}
}
