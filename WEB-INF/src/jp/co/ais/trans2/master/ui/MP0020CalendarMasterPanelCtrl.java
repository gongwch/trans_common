package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.calendar.*;

/**
 * �J�����_�[�}�X�^
 */
public class MP0020CalendarMasterPanelCtrl extends TController {

	/** �Y�����̓��� */
	protected int intLastDay = 0;

	/** ���j���i�Ώ۔N���̏����̗j���j */
	protected int intDayOfWeek = 0;

	/** �m��{�^���������ǂ����`�F�b�N�敪 */
	protected boolean settleFlg = true;

	/** �w����� */
	protected MP0020CalendarMasterPanel mainView = null;

	/** ���t̫�ϯ� */
	public static final DateFormat ddFormat = new SimpleDateFormat("dd");

	@Override
	public void start() {

		try {
			// �w����ʐ���
			createMainView();

			// �w����ʂ�����������
			initMainView();

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �w����ʂ�����������
	 */
	protected void initMainView() {

		// �Y�����̏�����
		Date dtDate = Util.getCurrentYMDDate();

		// �N�̐ݒ�
		int year = DateUtil.getYear(dtDate);
		// ���̐ݒ�
		int month = DateUtil.getMonth(dtDate);
		// ���t�̐ݒ�
		mainView.txtObjectYears.setValue(dtDate);

		// �f�[�^�̎擾
		fillData(year, month);
	}

	/**
	 * �w����ʂ̃t�@�N�g���B�V�K�Ɏw����ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createMainView() {
		mainView = new MP0020CalendarMasterPanel(this);

		addMainViewEvent();
	}

	/**
	 * �w����ʂ̃C�x���g��`
	 */
	protected void addMainViewEvent() {

		// [����]�{�^������
		mainView.btnSearch.addActionListener(new ActionListener() {

			// @Override
			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSearch_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}

		});
		// �u�G�N�Z���捞�v�{�^������
		mainView.btnImportExcel.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnImportExcel_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// �u�G�N�Z���o�́v�{�^������
		mainView.btnExportExcel.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnExportExcel_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [�m��]�{�^������
		mainView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSettle_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// �o�E���h�v���p�e�B�[�̕ύX��
		mainView.txtObjectYears.addPropertyChangeListener(new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent ev) {

				if (ev.getPropertyName().equals("value")) {
					mainView.btnSettle.setEnabled(false);
				}
			}
		});

		// �N���e�L�X�g�̃��X�ƃt�H�[�J�X�ړ���
		mainView.txtObjectYears.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				// �`�F�b�N���s����
				if (settleFlg) {
					// �N�����ύX�̏ꍇ
					if (mainView.txtObjectYears.isValueChanged()) {
						mainView.btnSettle.setEnabled(false);
						settleFlg = false;
					} else {
						mainView.btnSettle.setEnabled(true);
					}
				}
				return true;
			}
		});
	}

	/**
	 * �p�l���擾
	 * 
	 * @return �J�����_�[�}�X�^�p�l��
	 */
	@Override
	public TPanelBusiness getPanel() {
		// �p�l����Ԃ�
		return mainView;
	}

	/**
	 * @return boolean
	 */
	public boolean disposeDialog() {
		boolean isSettle = false;
		// �m��{�^������ �`�F�b�NOK�Ȃ����
		if (this.showConfirmMessage(mainView.getParentFrame(), "Q00005", "")) {
			isSettle = true;
		}
		return isSettle;
	}

	/**
	 * �w�����[����]�{�^������
	 */
	protected void btnSearch_Click() {
		try {
			// �m��{�^���������s�\�ɂ���
			mainView.btnSettle.setEnabled(false);

			// �Y�����̏�����
			Date dtDate = DateUtil.toYMDDate(mainView.txtObjectYears.getValue());

			// �N�̐ݒ�
			int year = DateUtil.getYear(dtDate);
			// ���̐ݒ�
			int month = DateUtil.getMonth(dtDate);
			// �f�[�^�̎擾
			fillData(year, month);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * ���������ɊY�������Ђ̃��X�g��Ԃ�
	 * 
	 * @param condition ��������
	 * @return ���������ɊY�������Ђ̃��X�g
	 * @throws Exception
	 */
	protected List<CalendarEntity> getCalendar(CalendarSearchCondition condition) throws Exception {

		List<CalendarEntity> list = (List<CalendarEntity>) request(getModelClass(), "getCalendar", condition);
		return list;
	}

	/**
	 * �u�G�N�Z���捞�v�{�^���������̏���
	 */
	protected void btnImportExcel_Click() {
		try {
			// �t�@�C���I���_�C�A���O���J��
			TFileChooser fc = new TFileChooser();

			File dir = getPreviousFolder(".chooser");

			// �O��̃t�H���_�𕜌�
			if (dir != null) {
				fc.setCurrentDirectory(dir);
			}

			// �e�L�X�g�t�@�C��(CSV�`��)�t�B���^�[
			TFileFilter filter = new TFileFilter(getWord("C00085") + getWord("C00500"), ExtensionType.XLS,
				ExtensionType.XLSX);

			fc.setFileFilter(filter);
			if (TFileChooser.FileStatus.Selected != fc.show(mainView)) {
				return;
			}

			// ����t�@�C�����ߋ��Ɏ�荞�܂�Ă���ꍇ�x�����b�Z�[�W��\������B
			TFile file = fc.getSelectedTFile();
			file.setKey("MP0020");

			// �Ō�̎捞�t�H���_�ۑ�
			saveFolder(fc.getCurrentDirectory(), ".chooser");

			// �G�N�Z���捞���s��
			request(getModelClass(), "importExcel", file.getFile());

			// �������b�Z�[�W
			showMessage(mainView, "I00013");

			// ��ʂ��Č�������
			btnSearch_Click();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �O��ۑ������t�H���_����Ԃ��B
	 * 
	 * @param type ���
	 * @return �O��ۑ������t�H���_���
	 */
	protected File getPreviousFolder(String type) {

		String path = SystemUtil.getAisSettingDir();
		File dir = (File) FileUtil.getObject(path + File.separator + getFolderKeyName() + type);

		return dir;
	}

	/**
	 * �w��̃t�H���_����ۑ�����
	 * 
	 * @param type ���
	 * @param dir �t�H���_���
	 */
	protected void saveFolder(File dir, String type) {
		String path = SystemUtil.getAisSettingDir();
		FileUtil.saveObject(dir, path + File.separator + getFolderKeyName() + type);
	}

	/**
	 * @return FolderKeyName��߂��܂��B
	 */
	protected String getFolderKeyName() {
		return "MP0020";
	}

	/**
	 * �u�G�N�Z���o�́v�{�^���������̏���
	 */
	protected void btnExportExcel_Click() {

		try {
			CalendarSearchCondition condition = getCalendarSearchCondition();
			Date calDate = DateUtil.toYMDDate(mainView.txtObjectYears.getValue());

			condition.setCompanyCode(getCompanyCode());
			condition.setDateFrom(DateUtil.getFirstDate(calDate));
			condition.setDateTo(DateUtil.getLastDate(calDate));

			// �G�N�Z���o�͂��s��
			byte[] data = (byte[]) request(getModelClass(), "exportExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// �v���r���[
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C00085") + getWord("C00500") + ".xls");

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * ���f���C���^�[�t�F�[�X��Ԃ�
	 * 
	 * @return ���f���C���^�[�t�F�[�X
	 */
	protected Class getModelClass() {
		return CalendarManager.class;
	}

	/**
	 * ���������̎擾
	 * 
	 * @return ��������
	 */
	protected CalendarSearchCondition getCalendarSearchCondition() {
		return new CalendarSearchCondition();
	}

	/**
	 * �\���f�[�^�̎擾
	 * 
	 * @param dteFrom �J�n���t, dteTo �I�����t
	 * @param dteTo
	 */
	public void reflesh(Date dteFrom, Date dteTo) {
		try {
			// ��ЃR�[�h�̎擾
			String kaiCode = getCompanyCode();
			// �J�����_�[�����擾
			CalendarSearchCondition condition = getCalendarSearchCondition();
			condition.setCompanyCode(kaiCode);
			condition.setDateFrom(dteFrom);
			condition.setDateTo(dteTo);

			List<CalendarEntity> list = getCalendar(condition);

			// ���ʂ̐ݒ�
			setData(list);

			// �m��{�^�������\
			mainView.btnSettle.setEnabled(true);
		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(e);
		}
	}

	/**
	 * �V�K����
	 * 
	 * @param list Calendar���X�g
	 * @param condition
	 */
	public void insert(List<CalendarEntity> list, CalendarSearchCondition condition) {
		try {
			// �o�^
			request(getModelClass(), "entry", list, condition);

		} catch (Exception e) {
			// ����ɏ�������܂���ł���
			errorHandler(e);
		}
	}

	/**
	 * ��ʍ��ڂ̃N���A
	 */
	public void clearMainView() {
		for (int i = 0; i < 37; i++) {
			// ���t���x���̕\��
			mainView.lbl[i].setVisible(true);
			// ���t���x���̃e�L�X�g�N���A
			mainView.lbl[i].setText("");
			// �Ј��x���Ώۓ������ޯ���̕\��
			mainView.chkEmp[i].setVisible(true);
			// ��s�x�Ɠ������ޯ���̕\��
			mainView.chkBak[i].setVisible(true);
			// �Վ��x���Ώۓ������ޯ���̕\��
			mainView.chkTep[i].setVisible(true);
			// �y���𔻒�
			boolean isHoliday = i == 0 || i == 6 || i == 7 || i == 13 || i == 14 || i == 20 || i == 21 || i == 27
				|| i == 28 || i == 34 || i == 35 ? true : false;

			// �Ј��x���Ώۓ��̑S�N���A
			mainView.chkEmp[i].setSelected(false);
			// ��s�x�Ɠ��̃Z�b�g
			mainView.chkBak[i].setSelected(isHoliday);
			// �Վ��x���Ώۓ��̑S�Z�b�g
			mainView.chkTep[i].setSelected(!isHoliday);
		}
		// ��ʍ��ڂ̔�\���i�O�������j
		for (int i = 0; i < intDayOfWeek; i++) {
			// ���t���x���̔�\��
			mainView.lbl[i].setVisible(false);
			// �Ј��x���Ώۓ������ޯ���̔�\��
			mainView.chkEmp[i].setVisible(false);
			// ��s�x�Ɠ������ޯ���̔�\��
			mainView.chkBak[i].setVisible(false);
			// �Վ��x���Ώۓ������ޯ���̔�\��
			mainView.chkTep[i].setVisible(false);
		}
		// ��ʍ��ڂ̔�\���i���������j
		for (int i = intLastDay + intDayOfWeek; i < 37; i++) {
			// ���t���x���̔�\��
			mainView.lbl[i].setVisible(false);
			// �Ј��x���Ώۓ������ޯ���̔�\��
			mainView.chkEmp[i].setVisible(false);
			// ��s�x�Ɠ������ޯ���̔�\��
			mainView.chkBak[i].setVisible(false);
			// �Վ��x���Ώۓ������ޯ���̔�\��
			mainView.chkTep[i].setVisible(false);
		}

	}

	/**
	 * �m��{�^���̏���
	 */
	protected void btnSettle_Click() {
		try {
			// �m�F���b�Z�[�W�\��
			if (!disposeDialog()) {
				return;
			}
			// ��ЃR�[�h�̎擾
			String kaiCode = getCompanyCode();
			// �N���̎擾
			Date dtDate = DateUtil.toYMDDate(mainView.txtObjectYears.getValue());

			// ���t�̏�����
			Date calDate;
			// �Ј��x���Ώۓ��̏�����
			String calSha = "";
			// ��s�x�Ɠ��̏�����
			String calBank = "";
			// �Վ��x���Ώۓ��̏�����
			String calRinji = "";

			// �J�n���t�̎擾
			Date dteFrom = DateUtil.getFirstDate(dtDate);
			// �I�����t�̎擾
			Date dteTo = DateUtil.getLastDate(dtDate);

			CalendarSearchCondition condition = new CalendarSearchCondition();
			condition.setCompanyCode(kaiCode);
			condition.setDateFrom(dteFrom);
			condition.setDateTo(dteTo);

			List<CalendarEntity> list = new ArrayList<CalendarEntity>();
			for (int i = 0; i < intLastDay; i++) {
				// ���t�̐ݒ�
				calDate = DateUtil.addDay(dteFrom, i);
				// �Ј��x���Ώۓ��̐ݒ�
				calSha = String.valueOf(BooleanUtil.toInt(mainView.chkEmp[i + intDayOfWeek].isSelected()));
				// ��s�x�Ɠ��̐ݒ�
				calBank = String.valueOf(BooleanUtil.toInt(mainView.chkBak[i + intDayOfWeek].isSelected()));
				// �Վ��x���Ώۓ��̐ݒ�
				calRinji = String.valueOf(BooleanUtil.toInt(mainView.chkTep[i + intDayOfWeek].isSelected()));

				// Calendar���X�g�쐬
				CalendarEntity rs = new CalendarEntity();
				rs.setCompanyCode(kaiCode);
				rs.setCalDate(calDate);
				rs.setCalSha(calSha);
				rs.setCalBank(calBank);
				rs.setCalRinji(calRinji);
				list.add(rs);

			}
			// �o�^����
			insert(list, condition);
			
			// �\���X�V
			switchModify();
			
			// �������b�Z�[�W�\��
			showMessage(mainView, "I00008");

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �f�[�^�̎擾
	 * 
	 * @param year
	 * @param month
	 */
	public void fillData(int year, int month) {
		try {
			// ���t�̏�����
			Calendar cal = Calendar.getInstance();
			// �N�̐ݒ�
			cal.set(Calendar.YEAR, year);
			// ���̐ݒ�
			cal.set(Calendar.MONTH, month - 1);
			// ���̐ݒ�
			cal.set(Calendar.DATE, 1);
			// �����̐ݒ�
			intLastDay = cal.getActualMaximum(Calendar.DATE);
			// �j���̐ݒ�
			intDayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
			// ���j���̐ݒ�
			if (intDayOfWeek < 0) {
				intDayOfWeek = 0;
			}

			// ��ʍ��ڂ̃N���A
			clearMainView();

			// ���̕\��
			for (int i = intDayOfWeek; i < intLastDay + intDayOfWeek; i++) {
				// ���t���x���̕\��
				mainView.lbl[i].setText(String.valueOf(i + 1 - intDayOfWeek));
			}
			Date calDate = cal.getTime();
			// �J�n���t�̎擾
			Date dateFrom = DateUtil.getFirstDate(calDate);
			// �I�����t�̎擾
			Date dateTo = DateUtil.getLastDate(calDate);

			// �\���f�[�^�̎擾
			reflesh(dateFrom, dateTo);

			// �m��{�^���̗L����
			settleFlg = true;
			mainView.btnSettle.setEnabled(true);

		} catch (Exception ex) {
			// ����ɏ�������܂���ł���
			errorHandler(ex);
		}
	}

	/**
	 * �f�[�^�̐ݒ�
	 * 
	 * @param lisRt
	 */
	public void setData(List<CalendarEntity> lisRt) {

		boolean isExists = false;

		// ��ʍ��ڂ̐ݒ�
		for (int i = 0; i < lisRt.size(); i++) {
			if (!isExists) isExists = true;
			
			// �Y���f�[�^�̎擾
			CalendarEntity lisRow = lisRt.get(i);

			String strDate = ddFormat.format(lisRow.getCalDate());

			// ���̎擾
			int intCel = Integer.parseInt(strDate);
			// �Ј��x���Ώۓ������ޯ���̐ݒ�
			mainView.chkEmp[intCel + intDayOfWeek - 1].setSelected("1".equals(lisRow.getCalSha()));
			// ��s�x�Ɠ������ޯ���̐ݒ�
			mainView.chkBak[intCel + intDayOfWeek - 1].setSelected("1".equals(lisRow.getCalBank()));
			// �Վ��x���Ώۓ������ޯ���̐ݒ�
			mainView.chkTep[intCel + intDayOfWeek - 1].setSelected("1".equals(lisRow.getCalRinji()));

		}
		
		// �\���X�V
		if (isExists) {
			switchModify();
		} else {
			switchNew();
		}
			
	}

	/**
	 * �V�K���[�h�ؑ�
	 */
	public void switchNew() {
		mainView.lblState.setBackground(new Color(0, 240, 255));
		mainView.lblState.setText(getShortWord("C00303"));
	}

	/**
	 * �ҏW���[�h�ؑ�
	 */
	public void switchModify() {
		mainView.lblState.setBackground(new Color(255, 255, 50));
		mainView.lblState.setText(getShortWord("C00169"));
	}
}
