package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;
import java.util.*;

import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * �X�V�敪�ꗗ�R���|�[�l���g�R���g���[��
 */
public class TSlipStateListTableCtrl extends TAppletClientBase {

	/** �����T�[�u���b�g */
	private static final String TARGET_SERVLET = "InformationServlet";

	/** �t�B�[���h */
	private TSlipStateListTable field;

	/** �X�V�R�[�h���X�g */
	private List<Integer> updateCodeList;

	/** �X�V�敪�w�b�_ */
	public String[] cupdateDivisionlabel;

	/** �\�����x���}�b�v */
	private Map<Integer, String> LabelMap;

	/**
	 * ���x���}�b�v���\��
	 */
	private void initMap() {
		LabelMap = new HashMap<Integer, String>();
		LabelMap.put(TSlipStateListTable.INSERT, getWord("C01258"));
		LabelMap.put(TSlipStateListTable.SPOT_ADMIT, getWord("C00157"));
		LabelMap.put(TSlipStateListTable.SPOT_DENY, getWord("C01617"));
		LabelMap.put(TSlipStateListTable.ACCOUNT_ADMIT, getWord("C01616"));
		LabelMap.put(TSlipStateListTable.ACCOUNT_DENY, getWord("C01615"));
		LabelMap.put(TSlipStateListTable.UPDATE, getWord("C00169"));
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field
	 */
	public TSlipStateListTableCtrl(TSlipStateListTable field) {
		this.field = field;
		initMap();
	}

	/**
	 * �X�V�敪�ꗗ������
	 */
	public void setTypeUpdateInit() {
		try {
			String kaiCode = field.getCompanyCode();
			boolean isUpdateKbn = field.getIncludeUpdate();

			// �X�V�敪�w�b�_
			cupdateDivisionlabel = new String[] { "", getWord("C01069") };

			// ��ЃR���g���[���}�X�^�̎擾
			Map map = getCmpInfo(kaiCode);

			boolean bol = BooleanUtil.toBoolean(Util.avoidNull(map.get("FLAG")));
			if (!bol) {
				errorHandler(field, "W01002");
				return;
			}

			// ���ꏳ�F�t���O�̎擾
			String g_shonin = Util.avoidNull(map.get("G_SHONIN_FLG"));
			// �o�����F�t���O�̎擾
			String k_shonin = Util.avoidNull(map.get("K_SHONIN_FLG"));

			int[] updateCode;

			// ���ꏳ�F�t���O�P�F�g�p���� �o�����F�K�{
			if ("1".equals(g_shonin)) {
				// �X�V�\��
				if (isUpdateKbn) {
					updateCode = new int[] { 1, 2, 3, 11, 12, 4 };
				}
				// �X�V��\��
				else {
					updateCode = new int[] { 1, 2, 3, 11, 12 };
				}
			}
			// ���ꏳ�F�t���O�O�F�g�p���Ȃ�
			else {
				// �o�����F�t���O�P�F�g�p����
				if ("1".equals(k_shonin)) {
					// �X�V�\��
					if (isUpdateKbn) {
						updateCode = new int[] { 1, 3, 12, 4 };
					}
					// �X�V��\��
					else {
						// �X�V�敪��
						updateCode = new int[] { 1, 3, 12 };
					}
				}
				// �o�����F�t���O�O�F�g�p���Ȃ�
				else {
					// �X�V�\��
					if (isUpdateKbn) {
						// �X�V�敪��
						updateCode = new int[] { 1, 4 };
					}
					// �X�V��\��
					else {
						// �X�V�敪��
						updateCode = new int[] { 1 };
					}
				}
			}

			updateCodeList = new LinkedList<Integer>();
			// �R�[�h�z������X�g�ɒu��������
			for (int code : updateCode) {
				updateCodeList.add(code);
			}

			setSpreadInfo();

		} catch (TRequestException e) {
			errorHandler(field);
		} catch (Exception e) {
			errorHandler(field, e, "E00009");
		}
	}

	/**
	 * �X�v���b�h�V�[�g���č\������
	 * 
	 * @param isDelete true : �폜 false = �ǉ�
	 */
	public void resetSpread(boolean isDelete) {
		// �폜����
		if (isDelete) {
			if (field.getInVisibleList() != null) {
				List<Integer> list = new LinkedList<Integer>();
				for (int code : updateCodeList) {
					if (!isSelected(code, field.getInVisibleList())) {
						list.add(code);
					}
				}
				updateCodeList = list;
				setSpreadInfo();
			}
		}

		// �ǉ�����
		else {
			if (field.getVisibleList() != null) {
				for (int code : field.getVisibleList()) {
					if (!isSelected(code, updateCodeList)) {
						updateCodeList.add(code);
					}
				}
				setSpreadInfo();
			}
		}
	}

	/**
	 * �O������̐ݒ�Ɠn���ꂽ�R�[�h�Ƃ̔�r���s��
	 * 
	 * @param code
	 * @param list
	 * @return ��v����R�[�h�����݂����true��Ԃ�
	 */
	private boolean isSelected(int code, List<Integer> list) {
		for (int setCode : list) {
			if (setCode == code) {
				return true;
			}
		}
		return false;
	}

	/**
	 * �f�[�^�ݒ�
	 */
	private void setSpreadInfo() {
		// �X�v���b�h�V�[�gDataSource
		JCVectorDataSource ds = new JCVectorDataSource();
		Vector<Vector> updateCells = new Vector<Vector>();

		for (int i = 0; i < updateCodeList.size(); i++) {
			Vector<Object> updateColum = new Vector<Object>(3);
			updateColum.add(0, "");
			updateColum.add(1, LabelMap.get(updateCodeList.get(i)));
			updateColum.add(2, updateCodeList.get(i));

			updateCells.add(i, updateColum);
		}

		ds.setColumnLabels(cupdateDivisionlabel); // �^�C�g��
		ds.setNumRows(updateCodeList.size()); // �s��
		ds.setNumColumns(2);
		ds.setCells(updateCells);

		// �f�[�^�𔽉f
		field.setDataSource(ds);

		// �`�F�b�N�{�b�N�X��0�J�����ɔz�u
		field.setCheckBoxComponents();
	}

	/**
	 * ��ЃR���g���[���}�X�^�擾
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @return ����
	 * @throws IOException
	 * @throws TRequestException
	 */
	private Map getCmpInfo(String kaiCode) throws IOException, TRequestException {

		// ���M����p�����[�^��ݒ�
		addSendValues("FLAG", "CmpInfo");
		// ��ЃR�[�h
		addSendValues("KAI_CODE", kaiCode);

		if (!request(TARGET_SERVLET)) {
			throw new TRequestException();
		}

		return super.getResult();
	}
}
