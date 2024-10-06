package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * �Ȗڃ}�X�^�̎w�����
 */
public class MG0080ItemMasterPanel extends TMainPanel {

	/** �V�K�{�^�� */
	public TImageButton btnNew;

	/** �����{�^�� */
	public TImageButton btnSearch;

	/** �ҏW�{�^�� */
	public TImageButton btnModify;

	/** ���ʃ{�^�� */
	public TImageButton btnCopy;

	/** �폜�{�^�� */
	public TImageButton btnDelete;

	/** �G�N�Z���{�^�� */
	public TImageButton btnExcel;

	/** ���������p�l�� */
	public TPanel pnlSearchCondition;

	/** �Ȗڔ͈͌��� */
	public TItemReferenceRange itemRange;

	/** �L�������؂�`�F�b�N�{�b�N�X */
	public TCheckBox chkOutputTermEnd;

	/** �Ȗڃ}�X�^�ꗗ */
	public TTable tbl;

	/**
	 * �ꗗ�̃J������`
	 */
	public enum SC {
		/** �ȖڃR�[�h */
		code,
		/** �Ȗږ��� */
		name,
		/** �Ȗڗ��� */
		names,
		/** �Ȗڌ������� */
		namek,
		/** �W�v�敪 */
		sumkbn,
		/** �Ȗڎ�� */
		kmkshu,
		/** �ݎ؋敪 */
		dckbn,
		/** �⏕�敪 */
		hkmkbn,
		/** �Œ蕔�庰�� */
		koteidepcode,
		/** ����ŃR�[�h */
		zeicode,
		/** GL�Ȗڐ���敪 */
		cntgl,
		/** AP�Ȗڐ���敪 */
		cntap,
		/** AR�Ȗڐ���敪 */
		cntar,
		/** BG�Ȗڐ���敪 */
		cntbg,
		/** �������̓t���O */
		tricodeflg,
		/** ���E�Ȗڐ���敪 */
		cntsousai,
		/** BS��������敪 */
		kasikbn,
		/** �]���֑Ώۃt���O */
		excflg,
		/** �����`�[���̓t���O */
		glflg1,
		/** �o���`�[���̓t���O */
		glflg2,
		/** �U�֓`�[���̓t���O */
		glflg3,
		/** �o��Z�`�[���̓t���O */
		apflg1,
		/** ���v��`�[���̓t���O */
		apflg2,
		/** ���v��`�[���̓t���O */
		arflg1,
		/** �������`�[���̓t���O */
		arflg2,
		/** ���Y�v��`�[���̓t���O */
		faflg1,
		/** �x���˗��`�[���̓t���O */
		faflg2,
		/** ���ʉݓ��̓t���O */
		mcrflg,
		/** �Ј����̓t���O */
		empcodeflg,
		/** �Ǘ��P���̓t���O */
		knrflg1,
		/** �Ǘ��Q���̓t���O */
		knrflg2,
		/** �Ǘ��R���̓t���O */
		knrflg3,
		/** �Ǘ��S���̓t���O */
		knrflg4,
		/** �Ǘ��T���̓t���O */
		knrflg5,
		/** �Ǘ��U���̓t���O */
		knrflg6,
		/** ���v�P���̓t���O */
		hmflg1,
		/** ���v�Q���̓t���O */
		hmflg2,
		/** ���v�R���̓t���O */
		hmflg3,
		/** ����ېœ��̓t���O */
		urizeiflg,
		/** �d���ېœ��̓t���O */
		sirzeiflg,
		/** �������t���O */
		occurDateflg,
		/** �J�n�N���� */
		dateFrom,
		/** �I���N���� */
		dateTo
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param company
	 */
	public MG0080ItemMasterPanel(Company company) {
		super(company);
	}

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainPanel#allocateComponents()
	 */
	@Override
	public void allocateComponents() {

		// �V�K�{�^��
		int x = HEADER_LEFT_X;
		btnNew.setLangMessageID("C00303");
		btnNew.setShortcutKey(KeyEvent.VK_F1);
		btnNew.setSize(25, 110);
		btnNew.setLocation(x, HEADER_Y);
		pnlHeader.add(btnNew);

		// �����{�^��
		x = x + btnNew.getWidth() + HEADER_MARGIN_X;
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setSize(25, 110);
		btnSearch.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSearch);

		// �ҏW�{�^��
		x = x + btnSearch.getWidth() + HEADER_MARGIN_X;
		btnModify.setLangMessageID("C00481");
		btnModify.setShortcutKey(KeyEvent.VK_F3);
		btnModify.setSize(25, 110);
		btnModify.setLocation(x, HEADER_Y);
		pnlHeader.add(btnModify);
		tbl.addSpreadSheetSelectChange(btnModify);

		// ���ʃ{�^��
		x = x + btnModify.getWidth() + HEADER_MARGIN_X;
		btnCopy.setLangMessageID("C00459");
		btnCopy.setShortcutKey(KeyEvent.VK_F4);
		btnCopy.setSize(25, 110);
		btnCopy.setLocation(x, HEADER_Y);
		pnlHeader.add(btnCopy);

		// �폜�{�^��
		x = x + btnCopy.getWidth() + HEADER_MARGIN_X;
		btnDelete.setLangMessageID("C01544");
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
		btnDelete.setSize(25, 110);
		btnDelete.setLocation(x, HEADER_Y);
		pnlHeader.add(btnDelete);

		// �G�N�Z���{�^��
		x = x + btnDelete.getWidth() + HEADER_MARGIN_X;
		btnExcel.setLangMessageID("C01545");
		btnExcel.setShortcutKey(KeyEvent.VK_F6);
		btnExcel.setSize(25, 130);
		btnExcel.setLocation(x, HEADER_Y);
		pnlHeader.add(btnExcel);

		// �㕔
		TPanel pnlBodyTop = new TPanel();
		pnlBodyTop.setLayout(null);
		pnlBodyTop.setMaximumSize(new Dimension(0, 85));
		pnlBodyTop.setMinimumSize(new Dimension(0, 85));
		pnlBodyTop.setPreferredSize(new Dimension(0, 85));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(pnlBodyTop, gc);

		// ���������p�l��
		pnlSearchCondition.setLayout(null);
		pnlSearchCondition.setLangMessageID("C01060"); // ��������
		pnlSearchCondition.setLocation(30, 10);
		pnlSearchCondition.setSize(600, 75);
		pnlBodyTop.add(pnlSearchCondition);

		// ���匟���͈�
		itemRange.setLocation(20, 20);
		pnlSearchCondition.add(itemRange);

		// �L�����Ԑ؂�\��
		chkOutputTermEnd.setLangMessageID("C11089"); // �L�����Ԑ؂�\��
		chkOutputTermEnd.setLocation(360, 40);
		chkOutputTermEnd.setSize(140, 20);
		pnlSearchCondition.add(chkOutputTermEnd);

		// ����
		TPanel pnlBodyButtom = new TPanel();
		pnlBodyButtom.setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		pnlBody.add(pnlBodyButtom, gc);

		// �ꗗ
		gc = new GridBagConstraints();
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, 30, 10, 30);
		pnlBodyButtom.add(tbl, gc);
	}

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainPanel#initComponents()
	 */
	@Override
	public void initComponents() {
		btnNew = new TImageButton(IconType.NEW);
		btnSearch = new TImageButton(IconType.SEARCH);
		btnModify = new TImageButton(IconType.EDIT);
		btnCopy = new TImageButton(IconType.COPY);
		btnDelete = new TImageButton(IconType.DELETE);
		btnExcel = new TImageButton(IconType.EXCEL);
		pnlSearchCondition = new TPanel();
		chkOutputTermEnd = new TCheckBox();
		itemRange = new TItemReferenceRange();

		tbl = new TTable();
		tbl.addColumn(SC.code, "C00572", 100, SwingConstants.LEFT);
		tbl.addColumn(SC.name, "C00700", 200, SwingConstants.LEFT);
		tbl.addColumn(SC.names, "C00730", 130, SwingConstants.LEFT);
		tbl.addColumn(SC.namek, "C00731", 200, SwingConstants.LEFT);
		tbl.addColumn(SC.sumkbn, "C01148", 80, SwingConstants.CENTER);
		tbl.addColumn(SC.kmkshu, "C01007", 130, SwingConstants.CENTER);
		tbl.addColumn(SC.dckbn, "C01226", 80, SwingConstants.CENTER);
		tbl.addColumn(SC.hkmkbn, "C01314", 80, SwingConstants.CENTER);
		tbl.addColumn(SC.koteidepcode, "C02066", 100, SwingConstants.LEFT);
		tbl.addColumn(SC.zeicode, "C00573", 100, SwingConstants.LEFT);
		tbl.addColumn(SC.cntgl, "C00968", 100, SwingConstants.CENTER);
		tbl.addColumn(SC.cntap, "C00959", 100, SwingConstants.CENTER);
		tbl.addColumn(SC.cntar, "C00960", 100, SwingConstants.CENTER);
		tbl.addColumn(SC.cntbg, "C00962", 100, SwingConstants.CENTER);
		tbl.addColumn(SC.tricodeflg, "C01134", 100, SwingConstants.CENTER);
		tbl.addColumn(SC.cntsousai, "C01217", 100, SwingConstants.CENTER);
		tbl.addColumn(SC.kasikbn, "C02078", 100, SwingConstants.CENTER);
		tbl.addColumn(SC.excflg, "C00453", 100, SwingConstants.CENTER);
		tbl.addColumn(SC.glflg1, "C02067", 100, SwingConstants.CENTER);
		tbl.addColumn(SC.glflg2, "C02068", 100, SwingConstants.CENTER);
		tbl.addColumn(SC.glflg3, "C02321", 100, SwingConstants.CENTER);
		tbl.addColumn(SC.apflg1, "C01049", 100, SwingConstants.CENTER);
		tbl.addColumn(SC.apflg2, "C01083", 100, SwingConstants.CENTER);
		tbl.addColumn(SC.arflg1, "C02071", 100, SwingConstants.CENTER);
		tbl.addColumn(SC.arflg2, "C02072", 100, SwingConstants.CENTER);
		tbl.addColumn(SC.faflg1, "C02073", 100, SwingConstants.CENTER);
		tbl.addColumn(SC.faflg2, "C02074", 100, SwingConstants.CENTER);
		tbl.addColumn(SC.mcrflg, "C01223", 100, SwingConstants.CENTER);
		tbl.addColumn(SC.empcodeflg, "C01120", 100, SwingConstants.CENTER);

		if (company.getAccountConfig().getManagement1Name() != null) {
			tbl.addColumn(SC.knrflg1, company.getAccountConfig().getManagement1Name() + getWord("C02386"), 100,
				SwingConstants.CENTER);
		} else {
			tbl.addColumn(SC.knrflg1, "C01026", 100, SwingConstants.CENTER);
		}

		if (company.getAccountConfig().getManagement2Name() != null) {
			tbl.addColumn(SC.knrflg2, company.getAccountConfig().getManagement2Name() + getWord("C02386"), 100,
				SwingConstants.CENTER);
		} else {
			tbl.addColumn(SC.knrflg2, "C01028", 100, SwingConstants.CENTER);
		}
		if (company.getAccountConfig().getManagement3Name() != null) {
			tbl.addColumn(SC.knrflg3, company.getAccountConfig().getManagement3Name() + getWord("C02386"), 100,
				SwingConstants.CENTER);
		} else {
			tbl.addColumn(SC.knrflg3, "C01030", 100, SwingConstants.CENTER);
		}
		if (company.getAccountConfig().getManagement4Name() != null) {
			tbl.addColumn(SC.knrflg4, company.getAccountConfig().getManagement4Name() + getWord("C02386"), 100,
				SwingConstants.CENTER);
		} else {
			tbl.addColumn(SC.knrflg4, "C01032", 100, SwingConstants.CENTER);
		}
		if (company.getAccountConfig().getManagement5Name() != null) {
			tbl.addColumn(SC.knrflg5, company.getAccountConfig().getManagement5Name() + getWord("C02386"), 100,
				SwingConstants.CENTER);
		} else {
			tbl.addColumn(SC.knrflg5, "C01034", 100, SwingConstants.CENTER);
		}
		if (company.getAccountConfig().getManagement6Name() != null) {
			tbl.addColumn(SC.knrflg6, company.getAccountConfig().getManagement6Name() + getWord("C02386"), 100,
				SwingConstants.CENTER);
		} else {
			tbl.addColumn(SC.knrflg6, "C01036", 100, SwingConstants.CENTER);
		}
		if (company.getAccountConfig().getNonAccounting1Name() != null) {
			tbl.addColumn(SC.hmflg1, company.getAccountConfig().getNonAccounting1Name() + getWord("C02386"), 100,
				SwingConstants.CENTER);
		} else {
			tbl.addColumn(SC.hmflg1, "C01288", 100, SwingConstants.CENTER);
		}
		if (company.getAccountConfig().getNonAccounting2Name() != null) {
			tbl.addColumn(SC.hmflg2, company.getAccountConfig().getNonAccounting2Name() + getWord("C02386"), 100,
				SwingConstants.CENTER);
		} else {
			tbl.addColumn(SC.hmflg2, "C01289", 100, SwingConstants.CENTER);
		}
		if (company.getAccountConfig().getNonAccounting3Name() != null) {
			tbl.addColumn(SC.hmflg3, company.getAccountConfig().getNonAccounting3Name() + getWord("C02386"), 100,
				SwingConstants.CENTER);
		} else {
			tbl.addColumn(SC.hmflg3, "C01290", 100, SwingConstants.CENTER);
		}
		tbl.addColumn(SC.urizeiflg, "C01282", 100, SwingConstants.CENTER);
		tbl.addColumn(SC.sirzeiflg, "C01088", 100, SwingConstants.CENTER);

		if (ClientConfig.isFlagOn("trans.MG0080.use.occurdate")) {
			tbl.addColumn(SC.occurDateflg, "C11619", 100, SwingConstants.CENTER);
		} else {
			tbl.addColumn(SC.occurDateflg, "C11619", -1, SwingConstants.CENTER);
		}

		tbl.addColumn(SC.dateFrom, "C00055", 100, SwingConstants.CENTER);
		tbl.addColumn(SC.dateTo, "C00261", 100, SwingConstants.CENTER);
	}

	/**
	 * �^�u���̐ݒ�
	 * 
	 * @see jp.co.ais.trans2.common.ui.TMainPanel#setTabIndex()
	 */
	@Override
	public void setTabIndex() {

		int i = 0;
		itemRange.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
	}

}