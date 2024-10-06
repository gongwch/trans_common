package jp.co.ais.trans.common.bizui;

import java.util.*;

import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.master.entity.*;

/**
 * �`�[��ރt�B�[���h
 * 
 * @author roh
 */
public class TSlipKindListTable extends TTable {

	/** ��ЃR�[�h */
	private String companyCode;

	/** �V�X�e���敪 true �ݒ肵���ꍇ�����V�X�e���Q�Ƃɕς��B */
	private boolean includeSystemElse = true;

	/** �f�[�^�敪���X�g */
	private List<String> kbnCodeList;
	
	/** �R���g���[�� */
	private TSlipKindListTableCtrl ctrl;

	/**
	 * �R���X�g���N�^
	 */
	public TSlipKindListTable() {
		this(TClientLoginInfo.getInstance().getCompanyCode());

	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param companyCode ��ЃR�[�h
	 */
	public TSlipKindListTable(String companyCode) {
		this(companyCode, true, null);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param includeSystemElse true:���V�X�e�������荞�񂾓`�[��ʂ�ΏۂƂ���
	 */
	public TSlipKindListTable(boolean includeSystemElse) {
		this(TClientLoginInfo.getInstance().getCompanyCode(), includeSystemElse, null);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param companyCode
	 * @param includeSystemElse
	 */
	public TSlipKindListTable(String companyCode, boolean includeSystemElse) {
		this(companyCode, includeSystemElse, null);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param KbnCodeList �f�[�^�敪���X�g
	 */
	public TSlipKindListTable(String companyCode, List<String> KbnCodeList) {
		this(companyCode, true, KbnCodeList);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param includeSystemElse true:���V�X�e�������荞�񂾓`�[��ʂ�ΏۂƂ���
	 * @param KbnCodeList �f�[�^�敪���X�g
	 */
	public TSlipKindListTable(boolean includeSystemElse, List<String> KbnCodeList) {
		this(TClientLoginInfo.getInstance().getCompanyCode(), includeSystemElse, KbnCodeList);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param KbnCodeList �f�[�^�敪���X�g
	 */
	public TSlipKindListTable(List<String> KbnCodeList) {
		this(TClientLoginInfo.getInstance().getCompanyCode(), true, KbnCodeList);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param includeSystemElse true:���V�X�e�������荞�񂾓`�[��ʂ�ΏۂƂ���
	 * @param KbnCodeList �f�[�^�敪���X�g
	 */
	public TSlipKindListTable(String companyCode, boolean includeSystemElse, List<String> KbnCodeList) {
		super();

		this.kbnCodeList = KbnCodeList;
		this.companyCode = companyCode;
		this.includeSystemElse = includeSystemElse;

		ctrl = new TSlipKindListTableCtrl(this);

		// �Z���̕�
		setCharWidth(0, 2);
		setCharWidth(1, 13);

		// ���x���ݒ�Ȃ�
		setRowLabelDisplay(false);
	}

	/**
	 * ��ЃR�[�h�擾
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * ���V�X�e�������荞�񂾓`�[��ʂ�ΏۂƂ��邩
	 * 
	 * @return true:���V�X�e�������荞�񂾓`�[��ʂ�ΏۂƂ���
	 */
	public boolean isIncludeSystemElse() {
		return includeSystemElse;
	}
	
	/**
	 * �^�C�g���ݒ�
	 * 
	 * @param title �^�C�g��
	 */
	public void setTableTitle(String[] title) {
		ctrl.cslipTypelabel = title;
		ctrl.setSpreadSheet();
	}

	/**
	 * �I������Ă���`�[��ʃf�[�^���X�g���擾����
	 * 
	 * @return �`�[��ʃf�[�^���X�g
	 */
	public List<DEN_SYU_MST> getSelectedDataList() {
		List<DEN_SYU_MST> list = new LinkedList<DEN_SYU_MST>();

		JCVectorDataSource ds = (JCVectorDataSource) this.getDataSource();

		for (int row = 0; row < this.getNumRows(); row++) {

			boolean isCheck = ((TCheckBox) this.getComponent(row, 0)).isSelected();

			if (isCheck) {
				Vector rows = (Vector) ds.getCells().get(row);
				list.add((DEN_SYU_MST) rows.get(2));
			}
		}

		return list;
	}

	/**
	 * �I������Ă���`�[��ʃR�[�h���擾����
	 * 
	 * @return �R�[�h���X�g
	 */
	public String[] getSelectedCodes() {
		List<DEN_SYU_MST> list = getSelectedDataList();

		String[] codes = new String[list.size()];

		int i = 0;
		for (DEN_SYU_MST bean : list) {
			codes[i] = bean.getDEN_SYU_CODE();
			i++;
		}

		return codes;
	}

	/**
	 * �I������Ă���X�V�敪�R�[�h�����邩
	 * 
	 * @return �I������Ă����true��Ԃ�
	 */
	public boolean isSelected() {
		for (int row = 0; row < this.getNumRows(); row++) {
			boolean isCheck = ((TCheckBox) this.getComponent(row, 0)).isSelected();
			if (isCheck) {
				return true;
			}
		}
		return false;
	}

	/**
	 * �f�[�^�敪���X�g�擾
	 * 
	 * @return �f�[�^�敪���X�g
	 */
	public List<String> getKbnCodeList() {
		return kbnCodeList;
	}

	/**
	 * �f�[�^�敪���X�g�ݒ�
	 * 
	 * @param kbnCodeList
	 */
	public void setKbnCodeList(List<String> kbnCodeList) {
		this.kbnCodeList = kbnCodeList;
	}

}
