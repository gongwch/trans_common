package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.define.*;
import jp.co.ais.trans.master.entity.*;

/**
 * �g���X�v���b�h��CTRL
 * 
 * @author Yanwei
 */
public class TEnhanceTableCtrl extends TAppletClientBase {

	/** �����T�[�u���b�g */
	private static final String TARGET_SERVLET = "TEnhanceTableServlet";

	/** �t�B�[���h */
	private TEnhanceTable field;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param tEnhanceTable �g���X�v���b�h
	 */
	public TEnhanceTableCtrl(TEnhanceTable tEnhanceTable) {
		try {
			this.field = tEnhanceTable;

		} catch (Exception e) {
			errorHandler(field, e);
		}
	}

	/**
	 * ���[�U�[�ݒ蕝�����擾����
	 * 
	 * @param prgId �v���O����ID
	 * @return �X�v���b�h�V�[�g����}�X�^
	 */
	public COL_SLT_MST getWidths(String prgId) {
		COL_SLT_MST bean = null;

		try {
			// ���M����p�����[�^��ݒ�
			addSendValues("flag", "getWidths");
			addSendValues("programId", prgId);

			if (!request(TARGET_SERVLET)) {
				// �N���C�A���g ��M��̓G���[�B
				errorHandler(field);
				return null;
			}
			// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
			bean = (COL_SLT_MST) super.getResultDto(COL_SLT_MST.class);

		} catch (IOException e) {
			errorHandler(field);
		}
		return bean;
	}

	/**
	 * �J��������ۑ�����
	 * 
	 * @param widths �J������
	 * @param programId �v���O����ID
	 */
	public void saveColumnWidths(int[] widths, String programId) {
		try {
			// �X�v���b�h�V�[�g����}�X�^
			COL_SLT_MST bean = new COL_SLT_MST();

			bean.setKAI_CODE(TClientLoginInfo.getInstance().getCompanyCode());// ��ЃR�[�h
			bean.setUSR_ID(TClientLoginInfo.getInstance().getUserCode()); // ���[�U�[�h�c
			bean.setPRG_ID(programId); // �v���O����ID

			bean.setWIDTH_00(widths[ColEvn.COL0.ordinal()] == 0 ? null : widths[ColEvn.COL0.ordinal()]); // �v���ЃR�[�h
			bean.setWIDTH_01(widths[ColEvn.COL1.ordinal()] == 0 ? null : widths[ColEvn.COL1.ordinal()]); // �v����
			bean.setWIDTH_02(widths[ColEvn.COL2.ordinal()] == 0 ? null : widths[ColEvn.COL2.ordinal()]); // ����R�[�h
			bean.setWIDTH_03(widths[ColEvn.COL3.ordinal()] == 0 ? null : widths[ColEvn.COL3.ordinal()]); // ����
			bean.setWIDTH_04(widths[ColEvn.COL4.ordinal()] == 0 ? null : widths[ColEvn.COL4.ordinal()]); // �ȖڃR�[�h
			bean.setWIDTH_05(widths[ColEvn.COL5.ordinal()] == 0 ? null : widths[ColEvn.COL5.ordinal()]); // �Ȗ�
			bean.setWIDTH_06(widths[ColEvn.COL6.ordinal()] == 0 ? null : widths[ColEvn.COL6.ordinal()]); // �⏕�ȖڃR�[�h
			bean.setWIDTH_07(widths[ColEvn.COL7.ordinal()] == 0 ? null : widths[ColEvn.COL7.ordinal()]); // �⏕�Ȗ�
			bean.setWIDTH_08(widths[ColEvn.COL8.ordinal()] == 0 ? null : widths[ColEvn.COL8.ordinal()]); // ����ȖڃR�[�h
			bean.setWIDTH_09(widths[ColEvn.COL9.ordinal()] == 0 ? null : widths[ColEvn.COL9.ordinal()]); // ����Ȗ�
			bean.setWIDTH_10(widths[ColEvn.COL10.ordinal()] == 0 ? null : widths[ColEvn.COL10.ordinal()]); // ��
			bean.setWIDTH_11(widths[ColEvn.COL11.ordinal()] == 0 ? null : widths[ColEvn.COL11.ordinal()]); // ����ŃR�[�h
			bean.setWIDTH_12(widths[ColEvn.COL12.ordinal()] == 0 ? null : widths[ColEvn.COL12.ordinal()]); // ����Ŗ���
			bean.setWIDTH_13(widths[ColEvn.COL13.ordinal()] == 0 ? null : widths[ColEvn.COL13.ordinal()]); // �ŗ�
			bean.setWIDTH_14(widths[ColEvn.COL14.ordinal()] == 0 ? null : widths[ColEvn.COL14.ordinal()]); // �ؕ����z
			bean.setWIDTH_15(widths[ColEvn.COL15.ordinal()] == 0 ? null : widths[ColEvn.COL15.ordinal()]); // ����Ŋz
			bean.setWIDTH_16(widths[ColEvn.COL16.ordinal()] == 0 ? null : widths[ColEvn.COL16.ordinal()]); // �ݕ����z
			bean.setWIDTH_17(widths[ColEvn.COL17.ordinal()] == 0 ? null : widths[ColEvn.COL17.ordinal()]); // �s�E�v�R�[�h
			bean.setWIDTH_18(widths[ColEvn.COL18.ordinal()] == 0 ? null : widths[ColEvn.COL18.ordinal()]); // �s�E�v
			bean.setWIDTH_19(widths[ColEvn.COL19.ordinal()] == 0 ? null : widths[ColEvn.COL19.ordinal()]); // �����R�[�h
			bean.setWIDTH_20(widths[ColEvn.COL20.ordinal()] == 0 ? null : widths[ColEvn.COL20.ordinal()]); // �����
			bean.setWIDTH_21(widths[ColEvn.COL21.ordinal()] == 0 ? null : widths[ColEvn.COL21.ordinal()]); // �Ј��R�[�h
			bean.setWIDTH_22(widths[ColEvn.COL22.ordinal()] == 0 ? null : widths[ColEvn.COL22.ordinal()]); // �Ј�
			bean.setWIDTH_23(widths[ColEvn.COL23.ordinal()] == 0 ? null : widths[ColEvn.COL23.ordinal()]); // �Ǘ�1�R�[�h
			bean.setWIDTH_24(widths[ColEvn.COL24.ordinal()] == 0 ? null : widths[ColEvn.COL24.ordinal()]); // �Ǘ�1
			bean.setWIDTH_25(widths[ColEvn.COL25.ordinal()] == 0 ? null : widths[ColEvn.COL25.ordinal()]); // �Ǘ�2�R�[�h
			bean.setWIDTH_26(widths[ColEvn.COL26.ordinal()] == 0 ? null : widths[ColEvn.COL26.ordinal()]); // �Ǘ�2
			bean.setWIDTH_27(widths[ColEvn.COL27.ordinal()] == 0 ? null : widths[ColEvn.COL27.ordinal()]); // �Ǘ�3�R�[�h
			bean.setWIDTH_28(widths[ColEvn.COL28.ordinal()] == 0 ? null : widths[ColEvn.COL28.ordinal()]); // �Ǘ�3
			bean.setWIDTH_29(widths[ColEvn.COL29.ordinal()] == 0 ? null : widths[ColEvn.COL29.ordinal()]); // �Ǘ�4�R�[�h
			bean.setWIDTH_30(widths[ColEvn.COL30.ordinal()] == 0 ? null : widths[ColEvn.COL30.ordinal()]); // �Ǘ�4
			bean.setWIDTH_31(widths[ColEvn.COL31.ordinal()] == 0 ? null : widths[ColEvn.COL31.ordinal()]); // �Ǘ�5�R�[�h
			bean.setWIDTH_32(widths[ColEvn.COL32.ordinal()] == 0 ? null : widths[ColEvn.COL32.ordinal()]); // �Ǘ�5
			bean.setWIDTH_33(widths[ColEvn.COL33.ordinal()] == 0 ? null : widths[ColEvn.COL33.ordinal()]); // �Ǘ�6�R�[�h
			bean.setWIDTH_34(widths[ColEvn.COL34.ordinal()] == 0 ? null : widths[ColEvn.COL34.ordinal()]); // �Ǘ�6
			bean.setWIDTH_35(widths[ColEvn.COL35.ordinal()] == 0 ? null : widths[ColEvn.COL35.ordinal()]); // ���v����1
			bean.setWIDTH_36(widths[ColEvn.COL36.ordinal()] == 0 ? null : widths[ColEvn.COL36.ordinal()]); // ���v����2
			bean.setWIDTH_37(widths[ColEvn.COL37.ordinal()] == 0 ? null : widths[ColEvn.COL37.ordinal()]); // ���v����3
			bean.setWIDTH_38(widths[ColEvn.COL38.ordinal()] == 0 ? null : widths[ColEvn.COL38.ordinal()]); // �ʉ݃R�[�h
			bean.setWIDTH_39(widths[ColEvn.COL39.ordinal()] == 0 ? null : widths[ColEvn.COL39.ordinal()]); // �ʉ݃��[�g
			bean.setWIDTH_40(widths[ColEvn.COL40.ordinal()] == 0 ? null : widths[ColEvn.COL40.ordinal()]); // ���͋��z
			bean.setWIDTH_41(widths[ColEvn.COL41.ordinal()] == 0 ? null : widths[ColEvn.COL41.ordinal()]); // ���z
			bean.setWIDTH_42(widths[ColEvn.COL42.ordinal()] == 0 ? null : widths[ColEvn.COL42.ordinal()]); // �ʉ݃R�[�h�\��
			bean.setWIDTH_43(widths[ColEvn.COL43.ordinal()] == 0 ? null : widths[ColEvn.COL43.ordinal()]); // �ʉ݃��[�g�\��
			bean.setWIDTH_44(widths[ColEvn.COL44.ordinal()] == 0 ? null : widths[ColEvn.COL44.ordinal()]); // �ؕ����z�O��
			bean.setWIDTH_45(widths[ColEvn.COL45.ordinal()] == 0 ? null : widths[ColEvn.COL45.ordinal()]); // �ݕ����z�O��
			bean.setWIDTH_46(widths[ColEvn.COL46.ordinal()] == 0 ? null : widths[ColEvn.COL46.ordinal()]); // ������

			bean.setSAVE_MODE(1); // �Z�[�u���[�h 0�F��Ԃ�ۑ����Ȃ� 1�F��Ԃ�ۑ�����

			// ���M����p�����[�^��ݒ�
			addSendValues("flag", "saveColumnWidths");
			this.addSendDto(bean);

			if (!request(TARGET_SERVLET)) {
				errorHandler(field);// �N���C�A���g ��M��̓G���[�B
				return;
			}

		} catch (IOException e) {
			errorHandler(field);
		}
	}
}
