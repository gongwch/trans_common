package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans.common.gui.TCheckBox;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.define.*;

/**
 * �`�[�̃X�e�[�^�X�I��(�e�[�u���`�F�b�N�`��)�R���|�[�l���g
 * 
 * @author AIS
 */
public class TSlipStateListSelector extends TTable {

	/** serialVersionUID */
	private static final long serialVersionUID = 5229926607981299214L;

	/** �R���g���[�� */
	protected TSlipStateListSelectorController controller;

	/**
	 * �e�[�u���񖼗񋓑�
	 */
	public enum SC {
		/** �I�� */
		CHECK,
		/** �X�V�敪���� */
		SLIPSTATE,
		/** �X�V�敪 */
		SLIPSTATE_VALUE
	}

	/**
	 * �R���X�g���N�^.
	 */
	public TSlipStateListSelector() {

		this(new SlipState[] {});
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param nonDisplayList ��\���敪
	 */
	public TSlipStateListSelector(SlipState... nonDisplayList) {

		initComponents();

		allocateComponents();

		// �R���g���[������
		controller = new TSlipStateListSelectorController(this, nonDisplayList);
	}

	/**
	 * �R���|�[�l���g�̏�����
	 */
	public void initComponents() {
		addColumn(SC.CHECK, "", 30, TCheckBox.class);
		addColumn(SC.SLIPSTATE, "C01069", 100);
		addColumn(SC.SLIPSTATE_VALUE, "", -1);
		setRowLabelNumber(false);
		getTableHeader().setReorderingAllowed(false);
	}

	/**
	 * �R���|�[�l���g�̔z�u
	 */
	public void allocateComponents() {
		setSize(135, 140);
	}

	/**
	 * �`�F�b�N�����s����Ԃ�
	 * 
	 * @return �`�F�b�N�s��
	 */
	public int getCheckedRowCount() {
		return controller.getCheckedRowCount();
	}

	/**
	 * �`�F�b�N�����X�V�敪��Ԃ�
	 * 
	 * @return List<�X�V�敪>
	 */
	public List<SlipState> getCheckedSlipState() {
		return controller.getCheckedSlipState();
	}

}
