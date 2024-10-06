package jp.co.ais.trans.common.bizui;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.define.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.master.entity.*;

/**
 * �g���X�v���b�h
 * 
 * @author Yanwei
 */
public class TEnhanceTable extends TTable {

	/** �v���O����ID */
	private String programId = "COM16";

	/** �g���X�v���b�h��CTRL */
	private TEnhanceTableCtrl ctrl;

	/**
	 * �R���X�g���N�^
	 */
	public TEnhanceTable() {
		super();
		ctrl = new TEnhanceTableCtrl(this);
	}

	/**
	 * �R���X�g���N�^ �v���O����ID��ݒ肷��B
	 * 
	 * @param prgId �v���O����ID
	 */
	public TEnhanceTable(String prgId) {
		this.programId = prgId;
		ctrl = new TEnhanceTableCtrl(this);
	}

	/**
	 * �R���X�g���N�^ �v���O����ID���}�ԕt�Őݒ肷��B
	 * 
	 * @param prgId �v���O����ID
	 * @param subNo �x���m��N����
	 */
	public TEnhanceTable(String prgId, int subNo) {
		this.programId = prgId + "-" + subNo;
	}

	/**
	 * �����̔z��ŃJ�����̕���ݒ肷��iDB�f�[�^�̏�ԂɈˑ����Ȃ��j
	 * 
	 * @param widths �����X�g
	 */
	@Override
	public void setColumnWidths(int[] widths) {
		// �@���[�U�[�ݒ蕝�����擾����B
		COL_SLT_MST bean = ctrl.getWidths(programId);

		// �f�[�^�����݂��Ȃ��ꍇ
		if (bean == null) {
			this.setCustomColumnWidths(widths);// ���\�b�h.����.�f�t�H���g�J�������ŕ��̐ݒ���s�Ȃ��B
		}
		// �f�[�^�����݂���ꍇ
		else {
			int[] colWidths = new int[48];

			colWidths[ColEvn.COL0.ordinal()] = bean.getWIDTH_00() == null ? 0 : bean.getWIDTH_00();// �v���ЃR�[�h
			colWidths[ColEvn.COL1.ordinal()] = bean.getWIDTH_01() == null ? 0 : bean.getWIDTH_01();// �v����
			colWidths[ColEvn.COL2.ordinal()] = bean.getWIDTH_02() == null ? 0 : bean.getWIDTH_02();// ����R�[�h
			colWidths[ColEvn.COL3.ordinal()] = bean.getWIDTH_03() == null ? 0 : bean.getWIDTH_03();// ����
			colWidths[ColEvn.COL4.ordinal()] = bean.getWIDTH_04() == null ? 0 : bean.getWIDTH_04();// �ȖڃR�[�h
			colWidths[ColEvn.COL5.ordinal()] = bean.getWIDTH_05() == null ? 0 : bean.getWIDTH_05();// �Ȗ�
			colWidths[ColEvn.COL6.ordinal()] = bean.getWIDTH_06() == null ? 0 : bean.getWIDTH_06();// �⏕�ȖڃR�[�h
			colWidths[ColEvn.COL7.ordinal()] = bean.getWIDTH_07() == null ? 0 : bean.getWIDTH_07();// �⏕�Ȗ�
			colWidths[ColEvn.COL8.ordinal()] = bean.getWIDTH_08() == null ? 0 : bean.getWIDTH_08();// ����ȖڃR�[�h
			colWidths[ColEvn.COL9.ordinal()] = bean.getWIDTH_09() == null ? 0 : bean.getWIDTH_09();// ����Ȗ�
			colWidths[ColEvn.COL10.ordinal()] = bean.getWIDTH_10() == null ? 0 : bean.getWIDTH_10();// ��
			colWidths[ColEvn.COL11.ordinal()] = bean.getWIDTH_11() == null ? 0 : bean.getWIDTH_11();// ����ŃR�[�h
			colWidths[ColEvn.COL12.ordinal()] = bean.getWIDTH_12() == null ? 0 : bean.getWIDTH_12();// ����Ŗ���
			colWidths[ColEvn.COL13.ordinal()] = bean.getWIDTH_13() == null ? 0 : bean.getWIDTH_13();// �ŗ�
			colWidths[ColEvn.COL14.ordinal()] = bean.getWIDTH_14() == null ? 0 : bean.getWIDTH_14();// �ؕ����z
			colWidths[ColEvn.COL15.ordinal()] = bean.getWIDTH_15() == null ? 0 : bean.getWIDTH_15();// ����Ŋz
			colWidths[ColEvn.COL16.ordinal()] = bean.getWIDTH_16() == null ? 0 : bean.getWIDTH_16();// �ݕ����z
			colWidths[ColEvn.COL17.ordinal()] = bean.getWIDTH_17() == null ? 0 : bean.getWIDTH_17();// �s�E�v�R�[�h
			colWidths[ColEvn.COL18.ordinal()] = bean.getWIDTH_18() == null ? 0 : bean.getWIDTH_18();// �s�E�v
			colWidths[ColEvn.COL19.ordinal()] = bean.getWIDTH_19() == null ? 0 : bean.getWIDTH_19();// �����R�[�h
			colWidths[ColEvn.COL20.ordinal()] = bean.getWIDTH_20() == null ? 0 : bean.getWIDTH_20();// �����
			colWidths[ColEvn.COL21.ordinal()] = bean.getWIDTH_21() == null ? 0 : bean.getWIDTH_21();// �Ј��R�[�h
			colWidths[ColEvn.COL22.ordinal()] = bean.getWIDTH_22() == null ? 0 : bean.getWIDTH_22();// �Ј�
			colWidths[ColEvn.COL23.ordinal()] = bean.getWIDTH_23() == null ? 0 : bean.getWIDTH_23();// �Ǘ�1�R�[�h
			colWidths[ColEvn.COL24.ordinal()] = bean.getWIDTH_24() == null ? 0 : bean.getWIDTH_24();// �Ǘ�1
			colWidths[ColEvn.COL25.ordinal()] = bean.getWIDTH_25() == null ? 0 : bean.getWIDTH_25();// �Ǘ�2�R�[�h
			colWidths[ColEvn.COL26.ordinal()] = bean.getWIDTH_26() == null ? 0 : bean.getWIDTH_26();// �Ǘ�2
			colWidths[ColEvn.COL27.ordinal()] = bean.getWIDTH_27() == null ? 0 : bean.getWIDTH_27();// �Ǘ�3�R�[�h
			colWidths[ColEvn.COL28.ordinal()] = bean.getWIDTH_28() == null ? 0 : bean.getWIDTH_28();// �Ǘ�3
			colWidths[ColEvn.COL29.ordinal()] = bean.getWIDTH_29() == null ? 0 : bean.getWIDTH_29();// �Ǘ�4�R�[�h
			colWidths[ColEvn.COL30.ordinal()] = bean.getWIDTH_30() == null ? 0 : bean.getWIDTH_30();// �Ǘ�4
			colWidths[ColEvn.COL31.ordinal()] = bean.getWIDTH_31() == null ? 0 : bean.getWIDTH_31();// �Ǘ�5�R�[�h
			colWidths[ColEvn.COL32.ordinal()] = bean.getWIDTH_32() == null ? 0 : bean.getWIDTH_32();// �Ǘ�5
			colWidths[ColEvn.COL33.ordinal()] = bean.getWIDTH_33() == null ? 0 : bean.getWIDTH_33();// �Ǘ�6�R�[�h
			colWidths[ColEvn.COL34.ordinal()] = bean.getWIDTH_34() == null ? 0 : bean.getWIDTH_34();// �Ǘ�6
			colWidths[ColEvn.COL35.ordinal()] = bean.getWIDTH_35() == null ? 0 : bean.getWIDTH_35();// ���v����1
			colWidths[ColEvn.COL36.ordinal()] = bean.getWIDTH_36() == null ? 0 : bean.getWIDTH_36();// ���v����2
			colWidths[ColEvn.COL37.ordinal()] = bean.getWIDTH_37() == null ? 0 : bean.getWIDTH_37();// ���v����3
			colWidths[ColEvn.COL38.ordinal()] = bean.getWIDTH_38() == null ? 0 : bean.getWIDTH_38();// �ʉ݃R�[�h
			colWidths[ColEvn.COL39.ordinal()] = bean.getWIDTH_39() == null ? 0 : bean.getWIDTH_39();// �ʉ݃��[�g
			colWidths[ColEvn.COL40.ordinal()] = bean.getWIDTH_40() == null ? 0 : bean.getWIDTH_40();// ���͋��z
			colWidths[ColEvn.COL41.ordinal()] = bean.getWIDTH_41() == null ? 0 : bean.getWIDTH_41();// ���z
			colWidths[ColEvn.COL42.ordinal()] = bean.getWIDTH_42() == null ? 0 : bean.getWIDTH_42();// �ʉ݃R�[�h�\��
			colWidths[ColEvn.COL43.ordinal()] = bean.getWIDTH_43() == null ? 0 : bean.getWIDTH_43();// �ʉ݃��[�g�\��
			colWidths[ColEvn.COL44.ordinal()] = bean.getWIDTH_44() == null ? 0 : bean.getWIDTH_44();// �ؕ����z�O��
			colWidths[ColEvn.COL45.ordinal()] = bean.getWIDTH_45() == null ? 0 : bean.getWIDTH_45();// �ݕ����z�O��
			colWidths[ColEvn.COL46.ordinal()] = bean.getWIDTH_46() == null ? 0 : bean.getWIDTH_46();// ������

			colWidths[ColEvn.COL47.ordinal()] = 0;// Bean

			// �Z�����̃Z�b�g
			this.setCustomColumnWidths(colWidths);
		}
	}

	/**
	 * �Z�����̃Z�b�g
	 * 
	 * @param widths �����X�g
	 */
	public void setCustomColumnWidths(int[] widths) {
		for (ColEvn col : ColEvn.values()) {
			if (widths[col.ordinal()] <= 0) {
				this.setColumnHidden(col.ordinal(), true);
			} else {
				this.setPixelWidth(col.ordinal(), widths[col.ordinal()]);
				this.setColumnHidden(col.ordinal(), false);
			}
		}

	}

	/**
	 * �J��������ۑ�����
	 */
	public void saveColumnWidths() {
		int[] widths = new int[48];

		for (ColEvn col : ColEvn.values()) {
			if (this.getPixelWidth(col.ordinal()) < 0 || isColumnHidden(col.ordinal())) {
				widths[col.ordinal()] = 0;
			} else {
				widths[col.ordinal()] = this.getPixelWidth(col.ordinal());
			}
		}

		// �J��������ۑ�����
		ctrl.saveColumnWidths(widths, programId);
	}

	/**
	 * �v���O����ID�̎擾
	 * 
	 * @return �v���O����ID
	 */
	public String getProgramId() {
		return programId;
	}

	/**
	 * �v���O����ID�̃Z�b�g
	 * 
	 * @param programId �v���O����ID
	 */
	public void setProgramId(String programId) {
		this.programId = programId;
	}

}
