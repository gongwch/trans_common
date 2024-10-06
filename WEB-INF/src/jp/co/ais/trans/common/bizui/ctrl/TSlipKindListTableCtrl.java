package jp.co.ais.trans.common.bizui.ctrl;

import java.util.*;

import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.entity.*;

/**
 * �`�[��ʃt�B�[���h�̃R���g���[��
 * 
 * @author roh
 */
public class TSlipKindListTableCtrl extends TAppletClientBase {

	/** �����T�[�u���b�g */
	private static final String TARGET_SERVLET = "MG0330SlipTypeMasterServlet";

	/** �t�B�[���h */
	private TSlipKindListTable field;

	/** �`�[��ʃw�b�_ */
	public String[] cslipTypelabel = new String[] { "", getWord("C00917") };

	/**
	 * �R���X�g���N�^
	 * 
	 * @param itemField �t�B�[���h
	 */
	public TSlipKindListTableCtrl(TSlipKindListTable itemField) {
		try {
			this.field = itemField;

			setSpreadSheet();
		} catch (Exception e) {
			errorHandler(field, e);
		}

	}

	/**
	 * �f�[�^�V�[�g�ݒ� �T�[�u���b�g����List���K��
	 */
	public void setSpreadSheet() {
		try {

			// ���M����p�����[�^��ݒ�
			addSendValues("flag", "refSlip");

			// ��ЃR�[�h
			addSendValues("kaiCode", field.getCompanyCode());
			addSendValues("isIncludeSystemEls", BooleanUtil.toString(field.isIncludeSystemElse()));

			if (!request(TARGET_SERVLET)) {
				// �N���C�A���g ��M��̓G���[�B
				errorHandler(field, "S00002");
			}

			// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
			List listDenSyu = super.getResultDtoList(DEN_SYU_MST.class);

			// �f�[�^�敪���ݒ肳��Ă����
			if (field.getKbnCodeList() != null) {
				List copyedList = new LinkedList();
				for (Object densyu : listDenSyu) {
					DEN_SYU_MST denMst = (DEN_SYU_MST) densyu;
					if (isDataKbnExist(denMst.getDATA_KBN())) {
						copyedList.add(densyu);
					}
				}
				listDenSyu = copyedList;
			}

			// �`�[��ʏ�����
			this.setDataSource(listDenSyu);
		} catch (Exception e) {
			errorHandler(field, e);
		}
	}

	/**
	 * �w��̃R�[�h�͐ݒ肵���f�[�^�敪�ɂ��邩�ǂ����𔻖�
	 * 
	 * @param dataKbn
	 * @return boolean
	 */
	private boolean isDataKbnExist(String dataKbn) {
		for (String setterCode : field.getKbnCodeList()) {
			if (dataKbn != null) {
				if (dataKbn.equals(setterCode)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * �`�[��ʈꗗ������
	 * 
	 * @param list ��ʃ��X�g
	 */
	private void setDataSource(List<DEN_SYU_MST> list) {

		// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
		Vector<Vector> cells = new Vector<Vector>();

		for (DEN_SYU_MST bean : list) {
			Vector<Object> colum = new Vector<Object>();

			colum.add(0, "");
			// ��ʃR�[�h�F��ʗ���
			colum.add(1, bean.getDEN_SYU_CODE() + ":" + bean.getDEN_SYU_NAME_S());
			// �f�[�^�敪
			colum.add(2, bean);

			cells.add(colum);
		}

		// SS�f�[�^�̍\�z
		JCVectorDataSource ds = new JCVectorDataSource();

		ds.setColumnLabels(cslipTypelabel); // �J�������x��
		ds.setNumColumns(2); // �J������
		ds.setNumRows(cells.size()); // �s��
		ds.setCells(cells);

		// �f�[�^�H��
		field.setDataSource(ds);

		// �`�F�b�N�{�b�N�X�R���|�[�l���g�̔z�u
		field.setCheckBoxComponents();
	}
}
