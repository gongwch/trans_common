package jp.co.ais.trans2.model.slip.parts;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * �`�[�`�F�b�J�[
 */
public class SlipChecker extends TModel {

	/** �w�b�_�`�F�b�J�[ */
	protected SlipHeaderChecker hchecker;

	/** ���׃`�F�b�J�[ */
	protected SlipDetailChecker dchecker;

	/**
	 * �`�[�`�F�b�N
	 * 
	 * @param slip �`�[
	 * @return ���b�Z�[�W
	 * @throws TException
	 */
	public boolean check(Slip slip) throws TException {
		List<Slip> list = new ArrayList<Slip>(1);
		list.add(slip);
		return check(list);
	}

	/**
	 * �`�[�`�F�b�N
	 * 
	 * @param slipList �`�[
	 * @return ���b�Z�[�W
	 * @throws TException
	 */
	public boolean check(List<Slip> slipList) throws TException {
		hchecker = getHeaderChecker();
		dchecker = getDetailChecker();

		hchecker.check(slipList);
		dchecker.check(slipList);

		return hchecker.getErrorList().isEmpty() && dchecker.getErrorList().isEmpty();
	}

	/**
	 * �w�b�_�`�F�b�J�[
	 * 
	 * @return �w�b�_�`�F�b�J�[
	 */
	protected SlipHeaderChecker getHeaderChecker() {
		return (SlipHeaderChecker) getComponent(SlipHeaderChecker.class);
	}

	/**
	 * ���׃`�F�b�J�[
	 * 
	 * @return ���׃`�F�b�J�[
	 */
	protected SlipDetailChecker getDetailChecker() {
		return (SlipDetailChecker) getComponent(SlipDetailChecker.class);
	}

	/**
	 * �`�[�G���[�����b�Z�[�W�ɕϊ�����
	 * 
	 * @return ���b�Z�[�W
	 */
	public List<Message> getMessages() {
		List<SlipHeaderError> hdrList = hchecker.getErrorList();
		List<SlipDetailError> dtlList = dchecker.getErrorList();

		List<Message> list = new ArrayList<Message>(hdrList.size() + dtlList.size());

		// �w�b�_
		for (SlipHeaderError err : hdrList) {
			Message msg = convertHeader(err);
			list.add(msg);
		}

		// ����
		for (SlipDetailError err : dtlList) {
			Message msg = convertDetail(err);
			msg.setSubMessageID(err.getRowNo() + getWord("C04288")); // �s��

			list.add(msg);
		}

		return list;
	}

	/**
	 * �w�b�_�G���[�����b�Z�[�W�ɕϊ�����
	 * 
	 * @param err �w�b�_�G���[
	 * @return ���b�Z�[�W
	 */
	protected Message convertHeader(SlipHeaderError err) {

		SWK_HDR hdr = err.getHeader();

		String id = "";
		List<Object> binds = new LinkedList<Object>();

		String company = hdr.getKAI_CODE();
		String word = err.getDataWord();
		String value = err.getValue();

		switch (err.getErrorType()) {
			case CLOSED_SLIP_DATE:
				// �w��̓��t[{0}] �͌v���А�[{1}]�Ŋ��ɒ��߂��Ă��܂��B
				id = "I00348";
				binds.add(value);
				binds.add(company);
				break;

			case NULL:
				// ���[{0}] {1}���ݒ肳��Ă��܂���B
				id = "I00349";
				binds.add(company);
				binds.add(word);
				break;

			case NULL_ON_ITEM:
				// �ȖڂɕK�v��{0}���s�����Ă��܂��B���[{0}] �Ȗ�[{1}]
				id = "I00350";
				binds.add(word);
				binds.add(company);
				binds.add(hdr.getSWK_KMK_CODE());

				if (!Util.isNullOrEmpty(hdr.getSWK_HKM_CODE())) {
					// �ȖڂɕK�v��{0}���s�����Ă��܂��B���[{0}] �Ȗ�[{1}] �⏕[{2}]
					id = "I00351";
					binds.add(hdr.getSWK_HKM_CODE());

					if (!Util.isNullOrEmpty(hdr.getSWK_UKM_CODE())) {
						// �ȖڂɕK�v��{0}���s�����Ă��܂��B���[{0}] �Ȗ�[{1}] �⏕[{2}] ����[{3}]
						id = "I00352";
						binds.add(hdr.getSWK_UKM_CODE());
					}
				}

				break;

			case NOT_FOUND:

				switch (err.getDataType()) {
					case PAY_SETTING: // �����x������
						// ���[XX] �����[XX] �x������[XX]��������܂���ł����B
						id = "I00354";
						binds.add(company);
						binds.add("C00408"); // �����
						binds.add(hdr.getSWK_TRI_CODE());
						binds.add("C00238"); // �x������
						binds.add(hdr.getSWK_TJK_CODE());

						break;

					case SUB_ITEM: // �⏕
						// ���[XX] �Ȗ�[XX] �⏕[XX]��������܂���ł����B
						id = "I00354";
						binds.add(company);
						binds.add("C00077"); // �Ȗ�
						binds.add(hdr.getSWK_KMK_CODE());
						binds.add("C00488"); // �⏕
						binds.add(hdr.getSWK_HKM_CODE());

						break;

					case DETAIL_ITEM: // ����
						// ���[XX] �Ȗ�[XX] �⏕[XX] ����[XX]��������܂���ł����B
						id = "I00355";
						binds.add(company);
						binds.add("C00077"); // �Ȗ�
						binds.add(hdr.getSWK_KMK_CODE());
						binds.add("C00488"); // �⏕
						binds.add(hdr.getSWK_HKM_CODE());
						binds.add("C00025"); // ����
						binds.add(hdr.getSWK_UKM_CODE());

						break;

					default:
						// ���[{0}] {1}[{2}]��������܂���ł����B
						id = "I00353";
						binds.add(company);
						binds.add(word);
						binds.add(value);

						break;
				}

				break;

			case ITEM_FIXED_OUT:
				// ���[XX] �Ȗ�[XX]�͕���XX]�ŗ��p�ł��܂���B
				id = "I00356";
				binds.add(company);
				binds.add("C00077"); // �Ȗ�
				binds.add(hdr.getSWK_KMK_CODE());
				binds.add("C00467"); // ����
				binds.add(hdr.getSWK_DEP_CODE());

				break;

			case TERM_OUT:
				// ���[{0}] {1}[{2}]�͗L�����Ԃ���O��Ă��܂��B
				id = "I00357";
				binds.add(company);
				binds.add(word);
				binds.add(value);

				break;

			case NOT_KEY_CURRENCY:
				// ���ʉ݋֎~�̉ȖڂɊ�ʉ݈ȊO���w�肵�Ă���܂��B���[{0}] �ʉ�[{1}] �Ȗ�[{2}]
				id = "I00360";
				binds.add(company);
				binds.add(hdr.getSWK_CUR_CODE());
				binds.add(hdr.getSWK_KMK_CODE());

				if (!Util.isNullOrEmpty(hdr.getSWK_HKM_CODE())) {
					// ���ʉ݋֎~�̉ȖڂɊ�ʉ݈ȊO���w�肵�Ă���܂��B���[{0}] �ʉ�[{1}] �Ȗ�[{2}] �⏕[{3}]
					id = "I00361";
					binds.add(hdr.getSWK_HKM_CODE());

					if (!Util.isNullOrEmpty(hdr.getSWK_UKM_CODE())) {
						// ���ʉ݋֎~�̉ȖڂɊ�ʉ݈ȊO���w�肵�Ă���܂��B���[{0}] �ʉ�[{1}] �Ȗ�[{2}] �⏕[{3}] ����[{4}]
						id = "I00362";
						binds.add(hdr.getSWK_UKM_CODE());
					}
				}

				break;

			case EMPTY_DETAIL:
				// ���׍s������܂���B
				id = "I00363";

				break;

			case NONE_OWN_DETAIL:
				// ���ׂɎ��Ђ̌v���Ђ��w�肳��Ă��܂���B
				id = "I00364";

				break;

			case UNBALANCE_AMOUNT:
				// �ݎ؂��o�����X���Ă��܂���B
				id = "I00136";

				break;
		}

		Message error = new Message(id, binds.toArray(new Object[binds.size()]));
		error.setErrorType(err.getErrorType());
		error.setDataType(err.getDataType());
		return error;
	}

	/**
	 * ���׃G���[�����b�Z�[�W�ɕϊ�����
	 * 
	 * @param err ���׃G���[
	 * @return ���b�Z�[�W
	 */
	protected Message convertDetail(SlipDetailError err) {
		SWK_DTL dtl = err.getDetail();

		String id = "";
		List<Object> binds = new LinkedList<Object>();

		String company = dtl != null ? dtl.getSWK_K_KAI_CODE() : "";
		String word = err.getDataWord();
		String value = err.getValue();

		switch (err.getErrorType()) {
			case CLOSED_SLIP_DATE:
				// �w��̓��t[{0}]�͌v���А�[{1}]�Ŋ��ɒ��߂��Ă��܂��B
				id = "I00348";
				binds.add(value);
				binds.add(company);
				break;

			case NULL:
				// ���[{0}] {1}���ݒ肳��Ă��܂���B
				id = "I00349";
				binds.add(company);
				binds.add(word);
				binds.add(value);
				break;

			case NULL_ON_ITEM:
				// �ȖڂɕK�v�ȍ���[{0}]���s�����Ă��܂��B���[{1}] �Ȗ�[{2}]
				id = "I00350";
				binds.add(word);
				binds.add(company);
				binds.add(dtl.getSWK_KMK_CODE());

				if (!Util.isNullOrEmpty(dtl.getSWK_HKM_CODE())) {
					// �ȖڂɕK�v�ȍ���[{0}]���s�����Ă��܂��B���[{1}] �Ȗ�[{2}] �⏕[{3}]
					id = "I00351";
					binds.add(dtl.getSWK_HKM_CODE());

					if (!Util.isNullOrEmpty(dtl.getSWK_UKM_CODE())) {
						// �ȖڂɕK�v�ȍ���[{0}]���s�����Ă��܂��B���[{1}] �Ȗ�[{2}] �⏕[{3}] ����[{4}]
						id = "I00352";
						binds.add(dtl.getSWK_UKM_CODE());
					}
				}

				break;

			case NOT_FOUND:
				switch (err.getDataType()) {
					case SUB_ITEM: // �⏕
						// ���[{0}] �Ȗ�[{1}] �⏕[{2}]��������܂���ł����B
						id = "I00354";
						binds.add(company);
						binds.add("C00077"); // �Ȗ�
						binds.add(dtl.getSWK_KMK_CODE());
						binds.add("C00488"); // �⏕
						binds.add(dtl.getSWK_HKM_CODE());

						break;

					case DETAIL_ITEM: // ����
						// ���[{0}] �Ȗ�[{1}] �⏕[{2}] ����[{3}]��������܂���ł����B
						id = "I00355";
						binds.add(company);
						binds.add("C00077"); // �Ȗ�
						binds.add(dtl.getSWK_KMK_CODE());
						binds.add("C00488"); // �⏕
						binds.add(dtl.getSWK_HKM_CODE());
						binds.add("C00025"); // ����
						binds.add(dtl.getSWK_UKM_CODE());

						break;

					default:
						// ���[{0}] {1}[{2}]��������܂���ł����B
						id = "I00353";
						binds.add(company);
						binds.add(word);
						binds.add(value);

						break;
				}

				break;

			case ITEM_FIXED_OUT:
				// ���[XX] �Ȗ�[XX]�͕���XX]�ŗ��p�ł��܂���B
				id = "I00356";
				binds.add(company);
				binds.add("C00077"); // �Ȗ�
				binds.add(dtl.getSWK_KMK_CODE());
				binds.add("C00467"); // ����
				binds.add(dtl.getSWK_DEP_CODE());

				break;

			case TERM_OUT:
				switch (err.getDataType()) {
					case SUB_ITEM: // �⏕
						// ���[XX] �Ȗ�[XX] �⏕[XX]�͗L�����Ԃ���O��Ă��܂��B
						id = "I00358";
						binds.add(company);
						binds.add("C00077"); // �Ȗ�
						binds.add(dtl.getSWK_KMK_CODE());
						binds.add("C00488"); // �⏕
						binds.add(dtl.getSWK_HKM_CODE());

						break;

					case DETAIL_ITEM: // ����
						// ���[XX] �Ȗ�[XX] �⏕[XX] ����[XX]�͗L�����Ԃ���O��Ă��܂��B
						id = "I00359";
						binds.add(company);
						binds.add("C00077"); // �Ȗ�
						binds.add(dtl.getSWK_KMK_CODE());
						binds.add("C00488"); // �⏕
						binds.add(dtl.getSWK_HKM_CODE());
						binds.add("C00025"); // ����
						binds.add(dtl.getSWK_UKM_CODE());

						break;

					default:
						// ���[{0}] {1}[{2}]�͗L�����Ԃ���O��Ă��܂��B
						id = "I00357";
						binds.add(company);
						binds.add(word);
						binds.add(value);
						break;
				}

				break;

			case NOT_KEY_CURRENCY:
				// ���ʉ݋֎~�̉ȖڂɊ�ʉ݈ȊO���w�肵�Ă���܂��B���[{0}] �ʉ�[{1}] �Ȗ�[{2}]
				id = "I00360";
				binds.add(company);
				binds.add(dtl.getSWK_CUR_CODE());
				binds.add(dtl.getSWK_KMK_CODE());

				if (!Util.isNullOrEmpty(dtl.getSWK_HKM_CODE())) {
					// ���ʉ݋֎~�̉ȖڂɊ�ʉ݈ȊO���w�肵�Ă���܂��B���[{0}] �ʉ�[{1}] �Ȗ�[{2}] �⏕[{3}]
					id = "I00361";
					binds.add(dtl.getSWK_HKM_CODE());

					if (!Util.isNullOrEmpty(dtl.getSWK_UKM_CODE())) {
						// ���ʉ݋֎~�̉ȖڂɊ�ʉ݈ȊO���w�肵�Ă���܂��B���[{0}] �ʉ�[{1}] �Ȗ�[{2}] �⏕[{3}] ����[{4}]
						id = "I00362";
						binds.add(dtl.getSWK_UKM_CODE());
					}
				}

				break;
		}

		Message error = new Message(id, binds.toArray(new Object[binds.size()]));
		error.setErrorType(err.getErrorType());
		error.setDataType(err.getDataType());
		return error;
	}
}
