package jp.co.ais.trans2.common.gui.table;

import java.util.*;

/**
 * �`���[�g�̃o�[(���X�g�f�[�^)�\��I/F
 */
public interface TChartBarListInterface {

	/**
	 * ���׃��X�g�̎擾
	 * 
	 * @return list ���׃��X�g
	 */
	public List getDetailList();

	/**
	 * DETAIL���[�h�̏ꍇ�Ƀo�[�̊e���׃f�[�^
	 * 
	 * @return list �o�[�̖��׃��X�g
	 */
	public List getBarDetailList();

	/**
	 * DETAIL���[�h�̏ꍇ��VCC���׃f�[�^
	 * 
	 * @return list VCC���׃��X�g
	 */
	public List getVCCDetailList();
}
