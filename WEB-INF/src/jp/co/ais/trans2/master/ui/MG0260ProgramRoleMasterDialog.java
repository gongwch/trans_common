package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.tree.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * �v���O�������[���}�X�^�̕ҏW���
 * 
 * @author AIS
 */
public class MG0260ProgramRoleMasterDialog extends TDialog {

	/** serialVersionUID */
	private static final long serialVersionUID = 8557714357271090392L;

	/** �m��{�^�� */
	public TImageButton btnSettle;

	/** �߂�{�^�� */
	public TButton btnClose;

	/** �R�[�h */
	public TLabelField ctrlProgramCode;

	/** ���� */
	public TLabelField ctrlProgramName;

	/** ���� */
	public TLabelField ctrlProgramNames;

	/** �������� */
	public TLabelField ctrlProgramNamek;

	/** �J�n�N���� */
	public TLabelPopupCalendar dtBeginDate;

	/** �I���N���� */
	public TLabelPopupCalendar dtEndDate;

	/** ���j���[�^�u�ƃv���O�����ꗗ�̋��E */
	public JSplitPane splitPane;

	/** ���j���[�^�u */
	public TProgramPanel prgPnl;

	/** �E�p�l�� */
	public TPanel pnlCenter;

	/** �E�p�l�� */
	public TPanel pnlRight;

	/** �v���O�����ǉ��{�^�� */
	public TButton btnAddProgram;

	/** �v���O�����폜�{�^�� */
	public TButton btnDeleteProgram;

	/** �v���O�����ꗗ */
	public TTable tbl;

	/**
	 * �e�[�u���񖼗񋓑�
	 */
	public enum SC {
		/** �V�X�e���R�[�h */
		systemCode,
		/** �v���O�����R�[�h */
		programCode,
		/** �v���O�������� */
		programNames
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public MG0260ProgramRoleMasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	/**
	 * �R���|�[�l���g������
	 */
	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlProgramCode = new TLabelField();
		ctrlProgramName = new TLabelField();
		ctrlProgramNames = new TLabelField();
		ctrlProgramNamek = new TLabelField();
		dtBeginDate = new TLabelPopupCalendar();
		dtEndDate = new TLabelPopupCalendar();
		splitPane = new JSplitPane();
		prgPnl = new TProgramPanel();
		pnlCenter = new TPanel();
		pnlRight = new TPanel();
		btnAddProgram = new TButton();
		btnDeleteProgram = new TButton();
		tbl = new TTable();
		tbl.addColumn(SC.systemCode, getWord("C00217"), 100);// �V�X�e���敪
		tbl.addColumn(SC.programCode, getWord("C00818"), 100);// �v���O�����R�[�h
		tbl.addColumn(SC.programNames, getWord("C00820"), 200);// �v���O��������
	}

	/**
	 * �R���|�[�l���g�z�u
	 */
	@Override
	public void allocateComponents() {

		setSize(850, 600);

		// �m��{�^��
		int x = 620 - 110 - HEADER_MARGIN_X;
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(x, HEADER_Y);
		btnSettle.setEnterFocusable(true);
		pnlHeader.add(btnSettle);

		// �߂�{�^��
		x = 620;
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(x, HEADER_Y);
		pnlHeader.add(btnClose);

		TPanel pnlBodyTop = new TPanel();
		pnlBodyTop.setLayout(null);
		pnlBodyTop.setMaximumSize(new Dimension(480, 155));
		pnlBodyTop.setMinimumSize(new Dimension(480, 155));
		pnlBodyTop.setPreferredSize(new Dimension(480, 155));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(pnlBodyTop, gc);

		// �V�X�e���敪
		x = 10;
		int y = 10;

		// �R�[�h
		ctrlProgramCode.setLabelSize(120);
		ctrlProgramCode.setFieldSize(75);
		ctrlProgramCode.setSize(200, 20);
		ctrlProgramCode.setLocation(x, y);
		ctrlProgramCode.setLabelText(getWord("C11154"));
		ctrlProgramCode.setMaxLength(TransUtil.PROGRAM_CODE_LENGTH);
		ctrlProgramCode.setImeMode(false);
		ctrlProgramCode.setAllowedSpace(false);
		pnlBodyTop.add(ctrlProgramCode);

		// ����
		ctrlProgramName.setLabelSize(120);
		ctrlProgramName.setFieldSize(300);
		ctrlProgramName.setSize(425, 20);
		ctrlProgramName.setLocation(x, y += 25);
		ctrlProgramName.setLabelText(getWord("C11155"));
		ctrlProgramName.setMaxLength(TransUtil.PROGRAM_NAME_LENGTH);
		pnlBodyTop.add(ctrlProgramName);

		// ����
		ctrlProgramNames.setLabelSize(120);
		ctrlProgramNames.setFieldSize(150);
		ctrlProgramNames.setSize(275, 20);
		ctrlProgramNames.setLocation(x, y += 25);
		ctrlProgramNames.setLabelText(getWord("C11156"));
		ctrlProgramNames.setMaxLength(TransUtil.PROGRAM_NAMES_LENGTH);
		pnlBodyTop.add(ctrlProgramNames);

		// ��������
		ctrlProgramNamek.setLabelSize(120);
		ctrlProgramNamek.setFieldSize(300);
		ctrlProgramNamek.setSize(425, 20);
		ctrlProgramNamek.setLocation(x, y += 25);
		// ���[����������
		ctrlProgramNamek.setLangMessageID("C11157");
		ctrlProgramNamek.setMaxLength(TransUtil.PROGRAM_NAMEK_LENGTH);
		pnlBodyTop.add(ctrlProgramNamek);

		// �J�n�N����
		dtBeginDate.setLabelSize(120);
		dtBeginDate.setSize(120 + dtBeginDate.getCalendarSize() + 5, 20);
		dtBeginDate.setLocation(x, y += 25);
		dtBeginDate.setLangMessageID("C00055");
		pnlBodyTop.add(dtBeginDate);

		// �I���N����
		dtEndDate.setLabelSize(120);
		dtEndDate.setSize(120 + dtBeginDate.getCalendarSize() + 5, 20);
		dtEndDate.setLocation(x, y += 25);
		dtEndDate.setLangMessageID("C00261");
		pnlBodyTop.add(dtEndDate);

		// splitPane
		splitPane.setContinuousLayout(true);
		splitPane.setDividerLocation(300);
		splitPane.setDividerSize(2);
		splitPane.setBorder(null);

		gc = new GridBagConstraints();
		gc.insets = new Insets(10, 10, 10, 10);
		gc.fill = GridBagConstraints.BOTH;
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		pnlBody.add(splitPane, gc);

		// ���j���[�ꗗ
		JScrollPane sp = new JScrollPane(prgPnl);
		splitPane.setLeftComponent(sp);

		// �E��
		pnlRight.setLayout(new GridBagLayout());
		splitPane.setRightComponent(pnlRight);

		pnlCenter.setLayout(new GridBagLayout());
		pnlCenter.setMaximumSize(new Dimension(60, 600));
		pnlCenter.setMinimumSize(new Dimension(60, 600));
		pnlCenter.setPreferredSize(new Dimension(60, 600));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.VERTICAL;
		pnlRight.add(pnlCenter, gc);

		// �v���O�����ǉ��{�^��
		btnAddProgram.setLangMessageID("C10140");// ��
		btnAddProgram.setSize(20, 20);
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 0, 20, 0);
		pnlCenter.add(btnAddProgram, gc);

		// �v���O�����폜�{�^��
		btnDeleteProgram.setLangMessageID("C10139");// ��
		btnDeleteProgram.setSize(20, 20);
		gc = new GridBagConstraints();
		gc.gridy = 1;
		gc.insets = new Insets(20, 0, 0, 0);
		pnlCenter.add(btnDeleteProgram, gc);

		// �v���O�����ꗗ
		tbl.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 0, 0, 0);
		gc.gridx = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		pnlRight.add(tbl, gc);
	}

	/**
	 * Tab Index Setting
	 */
	@Override
	public void setTabIndex() {
		int i = 0;
		ctrlProgramCode.setTabControlNo(i++);
		ctrlProgramName.setTabControlNo(i++);
		ctrlProgramNames.setTabControlNo(i++);
		ctrlProgramNamek.setTabControlNo(i++);
		dtBeginDate.setTabControlNo(i++);
		dtEndDate.setTabControlNo(i++);
		btnAddProgram.setTabControlNo(i++);
		btnDeleteProgram.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}

	/**
	 * �I���v���O�����ꗗ
	 * 
	 * @author AIS
	 */
	public class TProgramPanel extends TPanel {

		/** serialVersionUID */
		private static final long serialVersionUID = -8692998405247935610L;

		/** �c���[ */
		public TTree tree;

		/** �^�u�̃^�C�g�� */
		protected String title;

		/** ���[�g */
		protected DefaultMutableTreeNode root;

		/**
		 * 
		 */
		public TProgramPanel() {
			initComponents();
			allocateComponents();
		}

		/**
		 * �R���X�g���N�^
		 */
		public void initComponents() {
			root = new DefaultMutableTreeNode();
			tree = new TTree(root);
			tree.setRootVisible(false);
		}

		/**
		 * ����������
		 */
		public void allocateComponents() {

			setLayout(new GridBagLayout());
			setBackground(Color.white);

			GridBagConstraints gc1 = new GridBagConstraints();
			gc1.weightx = 1.0d;
			gc1.weighty = 1.0d;
			gc1.fill = GridBagConstraints.BOTH;
			add(tree, gc1);
		}

		/**
		 * �^�C�g�����擾����
		 * 
		 * @return �^�C�g��
		 */
		public String getTitle() {
			return title;
		}

		/**
		 * �^�C�g����ݒ肷��
		 * 
		 * @param title
		 */
		public void setTitle(String title) {
			this.title = title;
			setName(getTabChar(title));
		}

		/**
		 * ���j���[�^�u�ɃZ�b�g���镶�����Ԃ�
		 * 
		 * @param str �^�C�g��
		 * @return ���j���[�^�u�ɃZ�b�g���镶����
		 */
		protected String getTabChar(String str) {
			String rt = "<html>�@";

			for (int i = 0; i < str.length(); i++) {
				rt = rt + "<br>" + str.charAt(i);
			}
			rt = rt + "<br>�@";

			return rt;
		}

		/**
		 * ���[�g���擾����
		 * 
		 * @return root
		 */
		public DefaultMutableTreeNode getRoot() {
			return root;
		}

		/**
		 * ���[�g��ݒ肷��
		 * 
		 * @param root
		 */
		public void setRoot(DefaultMutableTreeNode root) {
			this.root = root;
		}
	}
}