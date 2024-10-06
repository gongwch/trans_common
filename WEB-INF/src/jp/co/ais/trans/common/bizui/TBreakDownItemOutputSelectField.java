package jp.co.ais.trans.common.bizui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;

import jp.co.ais.trans.common.bizui.ctrl.TBreakDownItemOutputSelectFieldCtrl;
import jp.co.ais.trans.common.gui.TPanel;
import jp.co.ais.trans.common.gui.TRadioButton;
import jp.co.ais.trans.master.entity.CMP_MST;

/**
 * ����Ȗ� �o�͂���/�o�͂��Ȃ� �̑I���R���|�[�l���g�ł��B
 * @author AIS
 *
 */
public class TBreakDownItemOutputSelectField extends TPanel {

	/** serialVersionUID */
	private static final long serialVersionUID = -4232877318775150603L;

	/** �R���g���[�� */
	protected TBreakDownItemOutputSelectFieldCtrl ctrl = null;

	/**
	 * 
	 * @param
	 */
	public TBreakDownItemOutputSelectField() {
		ctrl = getController();
		ctrl.init();
	}

	/**
	 * ���������܂��B<br>
	 * ����Ȗڂ��g��Ȃ��ݒ�̏ꍇ�A�t�B�[���h���̂���\���ƂȂ�܂��B
	 */
	public void initComponent() {

		setLayout(new GridBagLayout());

		CMP_MST cmpMst = ctrl.getCmpMst();

		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ""));
		setLangMessageID(cmpMst.getCMP_UKM_NAME());

		GridBagConstraints gridBagConstraints;

		setMaximumSize(new Dimension(180, 45));
		setMinimumSize(new Dimension(180, 45));
		setPreferredSize(new Dimension(180, 45));

		// �o�͂���
		rdoOutput = new TRadioButton();
		rdoOutput.setLangMessageID("C01158");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 5, 0);
		add(rdoOutput, gridBagConstraints);
		
		// �o�͂��Ȃ�
		rdoNotOutput = new TRadioButton();
		rdoNotOutput.setLangMessageID("C01157");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 5, 0);
		add(rdoNotOutput, gridBagConstraints);

		bg = new ButtonGroup();
		bg.add(rdoOutput);
		bg.add(rdoNotOutput);
	}

	/**
	 * �R���g���[���̃t�@�N�g�����\�b�h�ł��B
	 * @return �R���g���[��
	 */
	protected TBreakDownItemOutputSelectFieldCtrl getController() {
		return new TBreakDownItemOutputSelectFieldCtrl(this);
	}

	/**
	 * ����Ȗڂ��o�͂��邩��Ԃ��܂��B
	 * @return true�Ȃ�Ԃ��Afalse�Ȃ�Ԃ��Ȃ�
	 */
	public boolean isOutput() {
		return ctrl.isOutput();
	}

	/** �o�͂��� */
	public TRadioButton rdoOutput;

	/** �o�͂��Ȃ� */
	public TRadioButton rdoNotOutput;

	/** ButtonGroup */
	public ButtonGroup bg;

}
