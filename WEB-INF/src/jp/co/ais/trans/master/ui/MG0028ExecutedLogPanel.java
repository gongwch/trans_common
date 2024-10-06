package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.plaf.basic.*;

import com.klg.jclass.table.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TTable;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * ���s���O�Q�ƃp�l��
 * 
 * @author roh
 */
public class MG0028ExecutedLogPanel extends TPanelBusiness {

	/** UID */
	private static final long serialVersionUID = 6864139718117925182L;

	/** �R���g���[�� */
	private MG0028ExecutedLogPanelCtrl ctrl;

	/** �{�^���p�l�� */
	public TMainHeaderPanel pnlButton;

	/** �ڍ׃p�l�� */
	public TPanel pnlDetail;

	/** ���[�U�����p�l�� */
	public TPanel pnlUser;

	/** �v���O���������p�l�� */
	public TPanel pnlProgram;

	/** �����p�l�� */
	public TPanel pnlDate;

	/** �e�[�u���p�p�l�� */
	public TPanel pnlTabel;

	/** �����{�^�� */
	public TImageButton btnSearch;

	/** �G�N�Z���o�̓{�^�� */
	public TImageButton btnExcel;

	/** ���[�U�p���x�� */
	public TLabel lblStart;

	/** ���[�U�p���x�� */
	public TLabel lblEnd;

	/** �v���O�����p���x�� */
	public TLabel lblProStart;

	/** �v���O�����p���x�� */
	public TLabel lblProEnd;

	/** ���O�C���Q�ƃ`�F�b�N�{�b�N�X */
	public TCheckBox checkProgram;

	/** �����J�n���t�B�[���h */
	public TLabelPopupCalendar fedStartDate;

	/** �����I�����t�B�[���h */
	public TLabelPopupCalendar fedEndDate;

	/** �J�n���[�U�����t�B�[���h */
	public TButtonField fedStartUser;

	/** �I�����[�U�����t�B�[���h */
	public TButtonField fedEndUser;

	/** �J�n�v���O���������t�B�[���h */
	public TButtonField fedStartProgram;

	/** �I���v���O���������t�B�[���h */
	public TButtonField fedEndProgram;

	/** ���я��R���{�{�b�N�X */
	public TLabelComboBox sortCombo;

	/** ���s���O�e�[�u�� */
	public TTable tblLog;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param ctrl ��ʃR���g���[��
	 */
	public MG0028ExecutedLogPanel(MG0028ExecutedLogPanelCtrl ctrl) {
		this.ctrl = ctrl;
		initComponents();
		super.initPanel();
	}

	/**
	 * ��ʍ\�z
	 */
	protected void initComponents() {
		GridBagConstraints gridBagConstraints;
		pnlButton = new TMainHeaderPanel();
		pnlDetail = new TPanel();
		pnlUser = new TPanel();
		pnlProgram = new TPanel();
		pnlDate = new TPanel();
		pnlTabel = new TPanel();
		btnSearch = new TImageButton(IconType.SEARCH);
		btnExcel = new TImageButton(IconType.EXCEL);
		checkProgram = new TCheckBox();
		fedStartDate = new TLabelPopupCalendar();
		fedEndDate = new TLabelPopupCalendar();
		fedStartUser = new TButtonField();
		fedEndUser = new TButtonField();
		fedStartProgram = new TButtonField();
		fedEndProgram = new TButtonField();
		tblLog = new TTable();
		lblEnd = new TLabel();
		sortCombo = new TLabelComboBox();
		lblStart = new TLabel();
		lblProEnd = new TLabel();
		lblProStart = new TLabel();

		// ��{���C�A�E�g
		setLayout(new GridBagLayout());
		setMaximumSize(new Dimension(800, 600));
		setMinimumSize(new Dimension(800, 600));
		setPreferredSize(new Dimension(800, 600));

		// �{�^���p�l�����C�A�E�g
		pnlButton.setLayout(new GridBagLayout());
		pnlButton.setMaximumSize(new Dimension(800, 45));
		pnlButton.setMinimumSize(new Dimension(800, 45));
		pnlButton.setPreferredSize(new Dimension(800, 45));

		// �����{�^��
		btnSearch.setLangMessageID("C00155");
		btnSearch.setTabControlNo(9);
		btnSearch.setShortcutKey(KeyEvent.VK_F1);
		btnSearch.setMaximumSize(new Dimension(110, 25));
		btnSearch.setMinimumSize(new Dimension(110, 25));
		btnSearch.setPreferredSize(new Dimension(110, 25));

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 5, 0, 410);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		pnlButton.add(btnSearch, gridBagConstraints);
		btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				ctrl.searchLog();
			}
		});

		// �G�N�Z���{�^��
		btnExcel.setLangMessageID("C01545");
		btnExcel.setTabControlNo(10);
		btnExcel.setShortcutKey(KeyEvent.VK_F9);
		btnExcel.setMaximumSize(new Dimension(130, 25));
		btnExcel.setMinimumSize(new Dimension(130, 25));
		btnExcel.setPreferredSize(new Dimension(130, 25));

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagConstraints.insets = new Insets(0, 92, 0, 0);
		pnlButton.add(btnExcel, gridBagConstraints);
		btnExcel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				ctrl.exportToExcel();
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		add(pnlButton, gridBagConstraints);

		// ���t�p�l�����C�A�E�g
		pnlDate.setLayout(new GridBagLayout());
		pnlDate.setMaximumSize(new Dimension(800, 22));
		pnlDate.setMinimumSize(new Dimension(800, 22));
		pnlDate.setPreferredSize(new Dimension(800, 22));

		// �����J�n�t�B�[���h
		fedStartDate.setLabelSize(61);
		fedStartDate.setPreferredSize(new Dimension(160, 20));
		fedStartDate.setTabControlNo(1);
		fedStartDate.setAllowableBlank(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 19, 0, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		pnlDate.add(fedStartDate, gridBagConstraints);

		// �����I���t�B�[���h
		fedEndDate.setLangMessageID("C01333");
		fedEndDate.setLabelSize(20);
		fedEndDate.setPreferredSize(new Dimension(160, 20));
		fedEndDate.setTabControlNo(2);
		fedEndDate.setAllowableBlank(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 7, 0, 460);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		pnlDate.add(fedEndDate, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(10, 5, 0, 0);
		add(pnlDate, gridBagConstraints);

		// �ڍ׃p�l�����C�A�E�g
		pnlDetail.setLayout(new GridBagLayout());

		pnlDetail.setMaximumSize(new Dimension(780, 120));
		pnlDetail.setMinimumSize(new Dimension(780, 120));
		pnlDetail.setPreferredSize(new Dimension(780, 120));

		// ���[�U�p�l��
		pnlUser.setLayout(new GridBagLayout());

		pnlUser.setMaximumSize(new Dimension(350, 80));
		pnlUser.setMinimumSize(new Dimension(350, 80));
		pnlUser.setPreferredSize(new Dimension(350, 80));

		// ���[�U�����J�n���x��
		lblStart.setLangMessageID("C01012");
		lblStart.setMaximumSize(new Dimension(60, 20));
		lblStart.setPreferredSize(new Dimension(60, 20));
		lblStart.setMinimumSize(new Dimension(60, 20));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		pnlUser.add(lblStart, gridBagConstraints);

		// ���[�U�����J�n�t�B�[���h
		fedStartUser.setLangMessageID("C00528");
		fedStartUser.setButtonSize(70);
		fedStartUser.setFieldSize(60);
		fedStartUser.setNoticeSize(110);
		fedStartUser.setMaxLength(20);
		fedStartUser.setTabControlNo(3);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		pnlUser.add(fedStartUser, gridBagConstraints);

		// ���[�U�����I�����x��
		lblEnd.setLangMessageID("C01143");
		lblEnd.setMaximumSize(new Dimension(60, 20));
		lblEnd.setPreferredSize(new Dimension(60, 20));
		lblEnd.setMinimumSize(new Dimension(60, 20));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlUser.add(lblEnd, gridBagConstraints);

		// ���[�U�����I���t�B�[���h
		fedEndUser.setLangMessageID("C00528");
		fedEndUser.setButtonSize(70);
		fedEndUser.setFieldSize(60);
		fedEndUser.setNoticeSize(110);
		fedEndUser.setMaxLength(20);
		fedEndUser.setTabControlNo(4);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 5, 0, 0);
		pnlUser.add(fedEndUser, gridBagConstraints);

		pnlUser.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ""));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 15, 5, 0);
		pnlDetail.add(pnlUser, gridBagConstraints);

		// �v���O�����p�l��
		pnlProgram.setLayout(new GridBagLayout());
		pnlProgram.setMaximumSize(new Dimension(360, 110));
		pnlProgram.setMinimumSize(new Dimension(360, 110));
		pnlProgram.setPreferredSize(new Dimension(360, 110));

		// ���O�C���v���O�����`�F�b�N�{�b�N�X
		checkProgram.setLangMessageID("C02910");
		checkProgram.setMaximumSize(new Dimension(200, 20));
		checkProgram.setPreferredSize(new Dimension(200, 20));
		checkProgram.setMinimumSize(new Dimension(200, 20));
		checkProgram.setTabControlNo(5);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		pnlProgram.add(checkProgram, gridBagConstraints);

		// �v���O�����J�n���x��
		lblProStart.setLangMessageID("C01012");
		lblProStart.setMaximumSize(new Dimension(60, 20));
		lblProStart.setPreferredSize(new Dimension(60, 20));
		lblProStart.setMinimumSize(new Dimension(60, 20));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlProgram.add(lblProStart, gridBagConstraints);

		// �v���O�����J�n�t�B�[���h
		fedStartProgram.setLangMessageID("C00477");
		fedStartProgram.setButtonSize(80);
		fedStartProgram.setFieldSize(60);
		fedStartProgram.setNoticeSize(120);
		fedStartProgram.setMaxLength(10);
		fedStartProgram.setTabControlNo(6);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 5, 0, 0);
		pnlProgram.add(fedStartProgram, gridBagConstraints);

		// �v���O�����I�����x��
		lblProEnd.setLangMessageID("C01143");
		lblProEnd.setMaximumSize(new Dimension(60, 20));
		lblProEnd.setPreferredSize(new Dimension(60, 20));
		lblProEnd.setMinimumSize(new Dimension(60, 20));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlProgram.add(lblProEnd, gridBagConstraints);

		// �v���O�����I���t�B�[���h
		fedEndProgram.setLangMessageID("C00477");
		fedEndProgram.setButtonSize(80);
		fedEndProgram.setFieldSize(60);
		fedEndProgram.setNoticeSize(120);
		fedEndProgram.setMaxLength(10);
		fedEndProgram.setTabControlNo(7);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 5, 0, 0);
		pnlProgram.add(fedEndProgram, gridBagConstraints);

		pnlProgram.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ""));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 0, 20);
		pnlDetail.add(pnlProgram, gridBagConstraints);

		// sort�R���{�{�b�N�X
		sortCombo.setLangMessageID("C02839");
		sortCombo.setLabelSize(100);
		sortCombo.setComboSize(110);
		sortCombo.setPreferredSize(new Dimension(210, 20));
		sortCombo.setMaximumSize(new Dimension(220, 20));
		sortCombo.setMinimumSize(new Dimension(220, 20));
		sortCombo.setTabControlNo(8);
		sortCombo.getLabel().setHorizontalTextPosition(SwingConstants.CENTER);
		((BasicComboBoxRenderer) sortCombo.getComboBox().getRenderer()).setHorizontalAlignment(SwingConstants.LEFT);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new Insets(3, 0, 8, 170);
		pnlDetail.add(sortCombo, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 20, 5, 5);
		add(pnlDetail, gridBagConstraints);

		// �X�v���b�h
		String logdate = getWord("C00218") + getWord("C02906"); // ���s����
		String userCode = getWord("C00218") + getWord("C00589");
		String userName = getWord("C00218") + getWord("C00691");

		// SpreadSheet �� init ����
		String[] columnIDs = new String[] { logdate, userCode, userName, "C02907", "C00818", "C00819", "C02908" };
		int[] columnWidths = new int[] { 12, 10, 10, 8, 9, 10, 8 };

		// ��\��
		tblLog.initSpreadSheet(columnIDs, columnWidths);

		// Scroll�ʒu�ݒ�
		tblLog.setHorizSBPosition(JCTableEnum.POSITION_AT_SIDE);
		tblLog.setVertSBPosition(JCTableEnum.POSITION_AT_SIDE);

		// �X�^�C��
		CellStyleModel defaultStyle = tblLog.getDefaultCellStyle();
		JCCellStyle centerStyle = new JCCellStyle(defaultStyle);
		centerStyle.setHorizontalAlignment(CellStyleModel.CENTER);
		tblLog.setCellStyle(JCTableEnum.ALLCELLS, 0, centerStyle);

		// �e�[�u���p�l��
		pnlTabel.setLayout(new BoxLayout(pnlTabel, BoxLayout.X_AXIS));
		pnlTabel.setMaximumSize(new Dimension(800, 435));
		pnlTabel.setMinimumSize(new Dimension(800, 435));
		pnlTabel.setPreferredSize(new Dimension(800, 435));
		pnlTabel.add(tblLog);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = GridBagConstraints.PAGE_START;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		add(pnlTabel, gridBagConstraints);
	}
}
