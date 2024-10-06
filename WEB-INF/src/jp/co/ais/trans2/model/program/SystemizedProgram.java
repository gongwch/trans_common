package jp.co.ais.trans2.model.program;

import java.awt.*;
import java.util.List;

import jp.co.ais.trans.common.dt.*;

/**
 * �̌n�����ꂽ�v���O�����Q<br>
 * ��ʏ�̕��ѓ��Ɏg�p����
 * 
 * @author AIS
 */
public class SystemizedProgram extends TransferBase {

	/** serialVersionUID */
	private static final long serialVersionUID = 5153074987035799051L;

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �v���O�����O���[�v�R�[�h */
	protected String programGroupCode = null;

	/** �v���O�����O���[�v���� */
	protected String programGroupName = null;

	/** �v���O�����O���[�v�ɑ�����v���O�����Q */
	protected List<MenuDisp> menuDisp = null;

	// TODO:�v���O�����}�X�^�ɂ�郁�j���[�\�z�ˍ폜�\��
	/** �v���O�����O���[�v�ɑ�����v���O�����Q */
	protected List<Program> programs = null;

	/** �v���O�����O���[�v�̃J���[ */
	protected Color menuColor = null;

	/** �\������ */
	protected int dispIndex = 0;

	/**
	 * @return menuColor �v���O�����O���[�v�̃J���[��ݒ肵�܂��B
	 */
	public Color getMenuColor() {
		return menuColor;
	}

	/**
	 * @param menuColor �v���O�����O���[�v�̃J���[��߂��܂��B
	 */
	public void setMenuColor(Color menuColor) {
		this.menuColor = menuColor;
	}

	/**
	 * @return companyCode��߂��܂��B
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode companyCode��ݒ肵�܂��B
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return programGroupCode��߂��܂��B
	 */
	public String getProgramGroupCode() {
		return programGroupCode;
	}

	/**
	 * @param programGroupCode programGroupCode��ݒ肵�܂��B
	 */
	public void setProgramGroupCode(String programGroupCode) {
		this.programGroupCode = programGroupCode;
	}

	/**
	 * @return programGroupName��߂��܂��B
	 */
	public String getProgramGroupName() {
		return programGroupName;
	}

	/**
	 * @param programGroupName programGroupName��ݒ肵�܂��B
	 */
	public void setProgramGroupName(String programGroupName) {
		this.programGroupName = programGroupName;
	}

	/**
	 * @return programs��߂��܂��B
	 */
	public List<MenuDisp> getMenuDisp() {
		return menuDisp;
	}

	/**
	 * @param menuDisp menuDisp��ݒ肵�܂��B
	 */
	public void setMenuDisp(List<MenuDisp> menuDisp) {
		this.menuDisp = menuDisp;
	}

	/**
	 * �\���������擾����B
	 * 
	 * @return �\������
	 */
	public int getDispIndex() {
		return dispIndex;
	}

	/**
	 * �\��������ݒ肷��B
	 * 
	 * @param dispIndex
	 */
	public void setDispIndex(int dispIndex) {
		this.dispIndex = dispIndex;
	}

	/**
	 * @return programs��߂��܂��B
	 */
	public List<Program> getPrograms() {
		// TODO:�v���O�����}�X�^�ɂ�郁�j���[�\�z�ˍ폜�\��
		return programs;
	}

	/**
	 * @param programs programs��ݒ肵�܂��B
	 */
	public void setPrograms(List<Program> programs) {
		// TODO:�v���O�����}�X�^�ɂ�郁�j���[�\�z�ˍ폜�\��
		this.programs = programs;
	}
}
