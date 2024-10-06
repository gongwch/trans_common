package jp.co.ais.trans2.master.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.model.ui.TModelUIUtil;
import jp.co.ais.trans2.model.company.*;

/**
 * �Ȗڃ}�X�^�`�F�b�N�{�b�N�X�̃R���|�[�l���g
 */
public class TItemStatusUnit extends TPanel {

	/** �����`�[���̓t���O */
	public TCheckBox chkglflg1;

	/** �o���`�[���̓t���O */
	public TCheckBox chkglflg2;

	/** �U�֓`�[���̓t���O */
	public TCheckBox chkglflg3;

	/** �o��Z�`�[���̓t���O */
	public TCheckBox chkapflg1;

	/** ���v��`�[���̓t���O */
	public TCheckBox chkapflg2;

	/** ���v��`�[���̓t���O */
	public TCheckBox chkarflg1;

	/** �������`�[���̓t���O */
	public TCheckBox chkarflg2;

	/** ���Y�v��`�[���̓t���O */
	public TCheckBox chkfaflg1;

	/** �x���˗��`�[���̓t���O */
	public TCheckBox chkfaflg2;

	/** ���ʉݓ��̓t���O */
	public TCheckBox chkmcrflg;

	/** �Ј����̓t���O */
	public TCheckBox chkempcodeflg;

	/** �Ǘ��P���̓t���O(�D����) */
	public TCheckBox chkknrflg1;

	/** �Ǘ��Q���̓t���O(��������) */
	public TCheckBox chkknrflg2;

	/** �Ǘ��R���̓t���O(�`����) */
	public TCheckBox chkknrflg3;

	/** �Ǘ��S���̓t���O(�ςݒn����) */
	public TCheckBox chkknrflg4;

	/** �Ǘ��T���̓t���O(�g���n����) */
	public TCheckBox chkknrflg5;

	/** �Ǘ��U���̓t���O(�����n�敪) */
	public TCheckBox chkknrflg6;

	/** ���v�P���̓t���O(���l����) */
	public TCheckBox chkhmflg1;

	/** ���v�Q���̓t���O(��������) */
	public TCheckBox chkhmflg2;

	/** ���v�R���̓t���O(��������) */
	public TCheckBox chkhmflg3;

	/** ����ېœ��̓t���O */
	public TCheckBox chkurizeiflg;

	/** �d���ېœ��̓t���O */
	public TCheckBox chksirzeiflg;

	/** �������t���O */
	public TCheckBox chkOccurDate;

	/** ��Џ�� */
	protected Company company = null;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param company
	 */
	public TItemStatusUnit(Company company) {
		this.company = company;
		initComponents();
		allocateComponents();
	}

	/**
	 * �R���|�[�l���g�̏������B��ɃC���X�^���X�̐������s���܂��B
	 */
	public void initComponents() {

		chkglflg1 = new TCheckBox();
		chkglflg2 = new TCheckBox();
		chkglflg3 = new TCheckBox();
		chkapflg1 = new TCheckBox();
		chkapflg2 = new TCheckBox();
		chkarflg1 = new TCheckBox();
		chkarflg2 = new TCheckBox();
		chkfaflg1 = new TCheckBox();
		chkfaflg2 = new TCheckBox();
		chkmcrflg = new TCheckBox();
		chkempcodeflg = new TCheckBox();
		chkknrflg1 = new TCheckBox();
		chkknrflg2 = new TCheckBox();
		chkknrflg3 = new TCheckBox();
		chkknrflg4 = new TCheckBox();
		chkknrflg5 = new TCheckBox();
		chkknrflg6 = new TCheckBox();
		chkhmflg1 = new TCheckBox();
		chkhmflg2 = new TCheckBox();
		chkhmflg3 = new TCheckBox();
		chkurizeiflg = new TCheckBox();
		chksirzeiflg = new TCheckBox();
		chkOccurDate = new TCheckBox();
	}

	/**
	 * �R���|�[�l���g�̔z�u���s���܂��B
	 */
	public void allocateComponents() {

		setLayout(null);
		setSize(700, 520);

		// �����`�[���̓t���O
		chkglflg1.setLangMessageID("C01272");
		chkglflg1.setSize(180, 20);
		chkglflg1.setLocation(0, 0);
		add(chkglflg1);

		// �o���`�[���̓t���O
		chkglflg2.setLangMessageID("C01155");
		chkglflg2.setSize(180, 20);
		chkglflg2.setLocation(0, 20);
		add(chkglflg2);

		// �U�֓`�[���̓t���O
		chkglflg3.setLangMessageID("C01188");
		chkglflg3.setSize(180, 20);
		chkglflg3.setLocation(0, 40);
		add(chkglflg3);

		// �o��Z�`�[���̓t���O
		chkapflg1.setLangMessageID("C01049");
		chkapflg1.setSize(180, 20);
		chkapflg1.setLocation(0, 60);
		add(chkapflg1);

		// ���v��`�[���̓t���O
		chkapflg2.setLangMessageID("C01083");
		chkapflg2.setSize(180, 20);
		chkapflg2.setLocation(0, 80);
		add(chkapflg2);

		// ���v��`�[���̓t���O
		chkarflg1.setLangMessageID("C01079");
		chkarflg1.setSize(180, 20);
		chkarflg1.setLocation(0, 100);
		add(chkarflg1);

		// �������`�[���̓t���O
		chkarflg2.setLangMessageID("C01081");
		chkarflg2.setSize(180, 20);
		chkarflg2.setLocation(0, 120);
		add(chkarflg2);

		// ���Y�v��`�[���̓t���O
		chkfaflg1.setLangMessageID("C01102");
		chkfaflg1.setSize(180, 20);
		chkfaflg1.setLocation(0, 140);
		add(chkfaflg1);

		// �x���˗��`�[���̓t���O
		chkfaflg2.setLangMessageID("C01094");
		chkfaflg2.setSize(180, 20);
		chkfaflg2.setLocation(0, 160);
		add(chkfaflg2);

		// ���ʉݓ��̓t���O
		chkmcrflg.setLangMessageID("C01223");
		chkmcrflg.setSize(180, 20);
		chkmcrflg.setLocation(0, 180);
		add(chkmcrflg);

		// �Ј����̓t���O
		chkempcodeflg.setLangMessageID("C01120");
		chkempcodeflg.setSize(180, 20);
		chkempcodeflg.setLocation(0, 200);
		add(chkempcodeflg);

		// �Ǘ��P���̓t���O

		if (company.getAccountConfig().getManagement1Name() != null) {
			chkknrflg1.setLangMessageID(company.getAccountConfig().getManagement1Name()
				+ TModelUIUtil.getWord("C02386"));
		} else {
			chkknrflg1.setLangMessageID("C01026");
		}
		chkknrflg1.setSize(180, 20);
		chkknrflg1.setLocation(0, 220);
		add(chkknrflg1);

		// �Ǘ��Q���̓t���O
		if (company.getAccountConfig().getManagement2Name() != null) {
			chkknrflg2.setLangMessageID(company.getAccountConfig().getManagement2Name()
				+ TModelUIUtil.getWord("C02386"));
		} else {
			chkknrflg2.setLangMessageID("C01028");
		}
		chkknrflg2.setSize(180, 20);
		chkknrflg2.setLocation(0, 240);
		add(chkknrflg2);

		// �Ǘ��R���̓t���O
		if (company.getAccountConfig().getManagement3Name() != null) {
			chkknrflg3.setLangMessageID(company.getAccountConfig().getManagement3Name()
				+ TModelUIUtil.getWord("C02386"));
		} else {
			chkknrflg3.setLangMessageID("C01030");
		}
		chkknrflg3.setSize(180, 20);
		chkknrflg3.setLocation(0, 260);
		add(chkknrflg3);

		// �Ǘ��S���̓t���O
		if (company.getAccountConfig().getManagement4Name() != null) {
			chkknrflg4.setLangMessageID(company.getAccountConfig().getManagement4Name()
				+ TModelUIUtil.getWord("C02386"));
		} else {
			chkknrflg4.setLangMessageID("C01032");
		}
		chkknrflg4.setSize(180, 20);
		chkknrflg4.setLocation(0, 280);
		add(chkknrflg4);

		// �Ǘ��T���̓t���O
		if (company.getAccountConfig().getManagement5Name() != null) {
			chkknrflg5.setLangMessageID(company.getAccountConfig().getManagement5Name()
				+ TModelUIUtil.getWord("C02386"));
		} else {
			chkknrflg5.setLangMessageID("C01034");
		}
		chkknrflg5.setSize(180, 20);
		chkknrflg5.setLocation(0, 300);
		add(chkknrflg5);

		// �Ǘ��U���̓t���O
		if (company.getAccountConfig().getManagement6Name() != null) {
			chkknrflg6.setLangMessageID(company.getAccountConfig().getManagement6Name()
				+ TModelUIUtil.getWord("C02386"));
		} else {
			chkknrflg6.setLangMessageID("C01036");
		}
		chkknrflg6.setSize(180, 20);
		chkknrflg6.setLocation(0, 320);
		add(chkknrflg6);

		// ���v�P���̓t���O
		if (company.getAccountConfig().getNonAccounting1Name() != null) {
			chkhmflg1.setLangMessageID(company.getAccountConfig().getNonAccounting1Name()
				+ TModelUIUtil.getWord("C02386"));
		} else {
			chkhmflg1.setLangMessageID("C01288");
		}
		chkhmflg1.setSize(180, 20);
		chkhmflg1.setLocation(0, 340);
		add(chkhmflg1);

		// ���v�Q���̓t���O
		if (company.getAccountConfig().getNonAccounting2Name() != null) {
			chkhmflg2.setLangMessageID(company.getAccountConfig().getNonAccounting2Name()
				+ TModelUIUtil.getWord("C02386"));
		} else {
			chkhmflg2.setLangMessageID("C01289");
		}
		chkhmflg2.setSize(180, 20);
		chkhmflg2.setLocation(0, 360);
		add(chkhmflg2);

		// ���v�R���̓t���O
		if (company.getAccountConfig().getNonAccounting3Name() != null) {
			chkhmflg3.setLangMessageID(company.getAccountConfig().getNonAccounting3Name()
				+ TModelUIUtil.getWord("C02386"));
		} else {
			chkhmflg3.setLangMessageID("C01290");
		}
		chkhmflg3.setSize(180, 20);
		chkhmflg3.setLocation(0, 380);
		add(chkhmflg3);

		// ����ېœ��̓t���O
		chkurizeiflg.setLangMessageID("C01282");
		chkurizeiflg.setSize(180, 20);
		chkurizeiflg.setLocation(0, 400);
		add(chkurizeiflg);

		// �d���ېœ��̓t���O
		chksirzeiflg.setLangMessageID("C01088");
		chksirzeiflg.setSize(180, 20);
		chksirzeiflg.setLocation(0, 420);
		add(chksirzeiflg);

		if (ClientConfig.isFlagOn("trans.MG0080.use.occurdate")) {
			// �������t���O
			chkOccurDate.setLangMessageID("C11619");
			chkOccurDate.setSize(180, 20);
			chkOccurDate.setLocation(0, 440);
			add(chkOccurDate);
		} else {
			chkOccurDate.setVisible(false);
		}
	}

	/**
	 * �R���|�[�l���g�̃^�u���̐ݒ���s���܂��B
	 * 
	 * @param tabControlNo
	 */
	public void setTabControlNo(int tabControlNo) {
		chkglflg1.setTabControlNo(tabControlNo);
		chkglflg2.setTabControlNo(tabControlNo);
		chkglflg3.setTabControlNo(tabControlNo);
		chkapflg1.setTabControlNo(tabControlNo);
		chkapflg2.setTabControlNo(tabControlNo);
		chkarflg1.setTabControlNo(tabControlNo);
		chkarflg2.setTabControlNo(tabControlNo);
		chkfaflg1.setTabControlNo(tabControlNo);
		chkfaflg2.setTabControlNo(tabControlNo);
		chkmcrflg.setTabControlNo(tabControlNo);
		chkempcodeflg.setTabControlNo(tabControlNo);
		chkknrflg1.setTabControlNo(tabControlNo);
		chkknrflg2.setTabControlNo(tabControlNo);
		chkknrflg3.setTabControlNo(tabControlNo);
		chkknrflg4.setTabControlNo(tabControlNo);
		chkknrflg5.setTabControlNo(tabControlNo);
		chkknrflg6.setTabControlNo(tabControlNo);
		chkhmflg1.setTabControlNo(tabControlNo);
		chkhmflg2.setTabControlNo(tabControlNo);
		chkhmflg3.setTabControlNo(tabControlNo);
		chkurizeiflg.setTabControlNo(tabControlNo);
		chksirzeiflg.setTabControlNo(tabControlNo);
	}
}
