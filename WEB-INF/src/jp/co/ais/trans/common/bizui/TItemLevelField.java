package jp.co.ais.trans.common.bizui;

import java.awt.*;
import javax.swing.*;
import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.TransUtil;

/**
 * �Ȗڃ��x���t�B�[���h
 * 
 * @author roh
 */
public class TItemLevelField extends TPanel {

	/** UID */
	private static final long serialVersionUID = -9181170060878639499L;

	/** �{�^�����C�A�E�g */
	private int layoutType = VERTICAL;

	/** ��ЃR�[�h */
	private String companyCode;

	/** ���s */
	public static final int HORIZONTAL = 1;

	/** �c */
	public static final int VERTICAL = 2;

	/** �{�^���O���[�v */
	private ButtonGroup bGroup;

	/** �Ȗ� */
	private TRadioButton rdo;

	/** �⏕�Ȗ� */
	private TRadioButton rdoSub;

	/** �Ȗڏڍׁi����j */
	private TRadioButton rdoDown;

	/** �R���g���[�� */
	private TItemLevelFieldCtrl ctrl;

	/**
	 * �R���X�g���N�^<br>
	 * ���O�C����ЃR�[�h�A�c�ō\�z.
	 */
	public TItemLevelField() {
		this(VERTICAL);
	}

	/**
	 * �R���X�g���N�^.<br>
	 * ���O�C����ЃR�[�h�ō\�z.
	 * 
	 * @param buttonLayout <br>
	 *            TItemLevelField.HORIZONTAL ������ <br>
	 *            TItemLevelField.VERTICAL �c����<br>
	 */
	public TItemLevelField(int buttonLayout) {
		this(TClientLoginInfo.getInstance().getCompanyCode(), buttonLayout);
	}

	/**
	 * �R���X�g���N�^<br>
	 * �c�ō\�z.
	 * 
	 * @param companyCode ��ЃR�[�h
	 */
	public TItemLevelField(String companyCode) {
		this(companyCode, VERTICAL);
	}

	/**
	 * �R���X�g���N�^<br>
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param buttonLayout �{�^�����C�A�E�g <br>
	 *            TItemLevelField.HORIZONTAL ������ <br>
	 *            TItemLevelField.VERTICAL �c����<br>
	 */
	public TItemLevelField(String companyCode, int buttonLayout) {
		super();

		this.companyCode = companyCode;
		this.layoutType = buttonLayout;

		initComponents();

		this.ctrl = new TItemLevelFieldCtrl(this);
	}

	/**
	 * ���C�A�E�g�\��
	 */
	private void initComponents() {
		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ""));
		setLangMessageID("C00906");

		bGroup = new ButtonGroup();

		rdo = new TRadioButton();
		rdo.setSelected(true);
		rdo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		bGroup.add(rdo);

		rdoSub = new TRadioButton();
		rdoSub.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		bGroup.add(rdoSub);

		rdoDown = new TRadioButton();
		rdoDown.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		bGroup.add(rdoDown);

		GridBagConstraints gridBagConstraints;
		switch (layoutType) {
			case HORIZONTAL:
				// �����т̏ꍇ�B
				setPreferredSize(new Dimension(240, 55));

				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.insets = new Insets(0, 0, 0, 15);
				gridBagConstraints.anchor = GridBagConstraints.WEST;
				gridBagConstraints.gridx = 0;
				gridBagConstraints.gridy = 0;
				add(rdo, gridBagConstraints);

				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.insets = new Insets(0, 0, 0, 15);
				gridBagConstraints.anchor = GridBagConstraints.WEST;
				gridBagConstraints.gridx = 1;
				gridBagConstraints.gridy = 0;
				add(rdoSub, gridBagConstraints);

				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.insets = new Insets(0, 0, 0, 0);
				gridBagConstraints.anchor = GridBagConstraints.WEST;
				gridBagConstraints.gridx = 2;
				gridBagConstraints.gridy = 0;
				add(rdoDown, gridBagConstraints);
				break;

			case VERTICAL:
				// �c���т̏ꍇ�B
				setPreferredSize(new Dimension(120, 80));

				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.insets = new Insets(0, 0, 4, 0);
				gridBagConstraints.anchor = GridBagConstraints.WEST;
				gridBagConstraints.gridx = 0;
				gridBagConstraints.gridy = 0;
				add(rdo, gridBagConstraints);

				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.insets = new Insets(0, 0, 4, 0);
				gridBagConstraints.anchor = GridBagConstraints.WEST;
				gridBagConstraints.gridx = 0;
				gridBagConstraints.gridy = 1;
				add(rdoSub, gridBagConstraints);

				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.insets = new Insets(0, 0, 4, 0);
				gridBagConstraints.anchor = GridBagConstraints.WEST;
				gridBagConstraints.gridx = 0;
				gridBagConstraints.gridy = 2;
				add(rdoDown, gridBagConstraints);
				break;
		}
	}

	/**
	 * ��ЃR�[�h�K��
	 * 
	 * @return companyCode ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * ��ЃR�[�h�ݒ�
	 * 
	 * @param companyCode ��ЃR�[�h
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
		this.ctrl.initControl();
	}

	/**
	 * �Ȗڃ{�^���擾
	 * 
	 * @return �Ȗڃ{�^��
	 */
	public TRadioButton getItemButton() {
		return rdo;
	}

	/**
	 * �⏕�Ȗڃ{�^���擾
	 * 
	 * @return �⏕�Ȗڃ{�^��
	 */
	public TRadioButton getSubItemButton() {
		return rdoSub;
	}

	/**
	 * ����Ȗڃ{�^���擾
	 * 
	 * @return ����Ȗڃ{�^��
	 */
	public TRadioButton getBreakDownItemButton() {
		return rdoDown;
	}

	/**
	 * �Ȗڂ��I������Ă��邩�ǂ���
	 * 
	 * @return true:�I�����
	 */
	public boolean isItemSelected() {
		return rdo.isSelected();
	}

	/**
	 * �⏕�Ȗڂ��I������Ă��邩�ǂ���
	 * 
	 * @return true:�I�����
	 */
	public boolean isSubItemSelected() {
		return rdoSub.isSelected();
	}

	/**
	 * ����Ȗڂ��I������Ă��邩�ǂ���
	 * 
	 * @return true:�I�����
	 */
	public boolean isBreakDownItemSelected() {
		return rdoDown.isSelected();
	}

	/**
	 * �^�u�ړ����ԍ���ݒ�
	 * 
	 * @param no �^�u���ԍ�
	 */
	public void setTabControlNo(int no) {
		rdo.setTabControlNo(no);
		rdoSub.setTabControlNo(no);
		rdoDown.setTabControlNo(no);
	}

	/**
	 * �I�����ꂽ�Ȗڃ��x����Ԃ��B
	 * @return �I�����ꂽ�Ȗڃ��x��
	 */
	public TransUtil.ItemLevel getSelectedItemLevel() {
		if (isItemSelected()) {
			return TransUtil.ItemLevel.Item;
		} else if (isSubItemSelected()) {
			return TransUtil.ItemLevel.SubItem;
		} else if (isBreakDownItemSelected()) {
			return TransUtil.ItemLevel.DetailItem;	
		}
		return null;
	}

}
