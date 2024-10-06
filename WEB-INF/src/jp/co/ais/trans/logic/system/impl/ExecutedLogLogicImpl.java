package jp.co.ais.trans.logic.system.impl;

import java.io.*;
import java.text.*;
import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.log.ExecutedLogger.*;
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans.common.server.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.logic.system.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * ���s���O�Q�ƃ��W�b�N
 */
public class ExecutedLogLogicImpl implements ExecutedLogLogic {

	/** �v���O�����}�X�^Dao */
	protected PRG_MSTDao prgDao;

	/** ���s���ODao */
	protected ExecutedLogDao exeLogDao;

	/** �g���q */
	protected final String FILE_EXTENSION = "log";

	/** ���t��\�[�g */
	protected final int SORT_DATE = 1;

	/** ���[�U�R�[�h��\�[�h */
	protected final int SORT_USR_CODE = 2;

	/** �v���O������\�[�h */
	protected final int SORT_PRG_CODE = 3;

	/** �����J�n���t */
	protected Date startDate;

	/** �����I�����t */
	protected Date endDate;

	/** �����J�n���[�U */
	protected String startUser;

	/** �����I�����[�U */
	protected String endUser;

	/** �����J�n�v���O���� */
	protected String startPrg;

	/** �����I���v���O���� */
	protected String endPrg;

	/** ��ЃR�[�h */
	protected String companyCode;

	/** ���O�C���A�E�g�敪 */
	protected boolean isIncludeLogin = false;

	/** �\�[�g�L�[ */
	protected int sort;

	/** ����R�[�h */
	protected String langCode;

	/**
	 * ���O���s
	 * 
	 * @param userCode ���[�U�R�[�h
	 * @param userName ���[�U����
	 * @param ipAddress IP�A�h���X
	 * @param prgCode �v���O����ID
	 * @param state ���
	 */
	public void log(String userCode, String userName, String ipAddress, String prgCode, String state) {

		ExecutedLog logData = new ExecutedLog();
		logData.setEXCUTED_DATE(Util.getCurrentDate());
		logData.setIP_ADDRESS(ipAddress);
		logData.setPROGRAM_CODE(prgCode);
		logData.setSTATE(state);
		logData.setUSR_CODE(userCode);
		logData.setUSR_NAME(userName);
		logData.setKAI_CODE(companyCode);

		exeLogDao.insertLog(logData);
	}

	/**
	 * ���O�C�����O���s
	 * 
	 * @param userCode ���[�U�R�[�h
	 * @param userName ���[�U����
	 * @param ipAddress IP�A�h���X
	 */
	public void logLogin(String userCode, String userName, String ipAddress) {

		ExecutedLog logData = new ExecutedLog();
		logData.setEXCUTED_DATE(Util.getCurrentDate());
		logData.setIP_ADDRESS(ipAddress);
		logData.setPROGRAM_CODE(ExecutedLogger.LOGIN);
		logData.setSTATE("");
		logData.setUSR_CODE(userCode);
		logData.setUSR_NAME(userName);
		logData.setKAI_CODE(companyCode);

		exeLogDao.insertLog(logData);
	}

	/**
	 * ���O�A�E�g���O���s
	 * 
	 * @param userCode ���[�U�R�[�h
	 * @param userName ���[�U����
	 * @param ipAddress IP�A�h���X
	 */
	public void logLogout(String userCode, String userName, String ipAddress) {

		ExecutedLog logData = new ExecutedLog();
		logData.setEXCUTED_DATE(Util.getCurrentDate());
		logData.setIP_ADDRESS(ipAddress);
		logData.setPROGRAM_CODE(ExecutedLogger.LOGOUT);
		logData.setSTATE("");
		logData.setUSR_CODE(userCode);
		logData.setUSR_NAME(userName);
		logData.setKAI_CODE(companyCode);

		exeLogDao.insertLog(logData);
	}

	/**
	 * ���s���O�ꗗ���擾
	 * 
	 * @see jp.co.ais.trans.logic.system.ExecutedLogLogic#getExecutedLogList()
	 */

	public List<ExecutedLog> getExecutedLogList() {

		// DB���[�h�̏ꍇ
		if (ServerConfig.isExeLogDBMode()) {
			return getExecutedLogDBList();
		}

		// �t�@�C�����[�h�̏ꍇ
		return getExecutedFileLogList();
	}

	/**
	 * ���s���O�ꗗ���擾(DB����)
	 * 
	 * @return ���s���O�ꗗ
	 */
	protected List<ExecutedLog> getExecutedLogDBList() {

		ExecutedLogSearchParam param = new ExecutedLogSearchParam();

		param.setCompanyCode(companyCode);
		param.setEndDate(endDate);
		param.setEndPrg(endPrg);
		param.setEndUser(endUser);
		param.setIsIncludeLogin(BooleanUtil.toInt(isIncludeLogin));
		param.setStartDate(startDate);
		param.setStartPrg(startPrg);
		param.setStartUser(startUser);

		String strSort = "";

		// ���я�����
		switch (sort) {

		// ���t��
			case SORT_DATE:
				strSort = "log.EXCUTED_DATE";
				break;

			// ���[�U�R�[�h��
			case SORT_USR_CODE:
				strSort = "log.USR_CODE,log.EXCUTED_DATE";
				break;

			// �v���O�����R�[�h��
			case SORT_PRG_CODE:
				strSort = "log.PROGRAM_CODE,log.EXCUTED_DATE";
				break;
		}

		param.setOrderBy(strSort);

		List<ExecutedLog> list = exeLogDao.getExecutedLogList(param);

		for (ExecutedLog log : list) {
			if (ExecutedLogger.LOGIN.equals(log.getPROGRAM_CODE())) {
				log.setPROGRAM_NAME(MessageUtil.getWord(langCode, "C03187")); // ���O�C��

			} else if (ExecutedLogger.LOGOUT.equals(log.getPROGRAM_CODE())) {

				log.setPROGRAM_NAME(MessageUtil.getWord(langCode, "C03188")); // ���O�A�E�g
			}
		}

		return list;
	}

	/**
	 * ���s���O�ꗗ���擾(�t�@�C������)
	 * 
	 * @return ���s���O�ꗗ
	 */
	protected List<ExecutedLog> getExecutedFileLogList() {
		// �t�@�C�����[�h
		BufferedReader br = null;

		try {
			List<ExecutedLog> logList = new ArrayList<ExecutedLog>();

			String filename = companyCode + "." + FILE_EXTENSION;
			File file = new File(TServerEnv.USER_LOG_DIR.toString() + filename);
			if (file.isFile()) {
				br = mappingFileLog(logList, file);
				br.close();
			}

			int months = DateUtil.getMonthCount(startDate, endDate);
			Date from = DateUtil.getFirstDate(startDate);

			for (int i = 0; i <= months; i++) {

				String ext = "";

				SimpleDateFormat DATE_FORMAT_YM = new SimpleDateFormat(ExecutedFileLogger.datePattern);
				ext = DATE_FORMAT_YM.format(DateUtil.addMonth(from, i));

				filename = companyCode + "." + FILE_EXTENSION + ext;
				file = new File(TServerEnv.USER_LOG_DIR.toString() + filename);

				if (file.isFile()) {
					br = mappingFileLog(logList, file);
					br.close();
				} else {
					// �w���PATH�����݂��Ȃ��Ə����s�v
					continue;
				}
			}

			// ���̓X�g���[�������
			// ���̂�ݒ肷��B
			List<ExecutedLog> dtoList = setProgramName(logList);

			// ���я�����
			switch (sort) {

			// ���t��
				case SORT_DATE:
					sortByDate(dtoList);
					break;

				// ���[�U�R�[�h��
				case SORT_USR_CODE:
					sortByUserCode(dtoList);
					break;

				// �v���O�����R�[�h��
				case SORT_PRG_CODE:
					sortByProgramCode(dtoList);
					break;
			}

			return dtoList;

		} catch (Exception e) {
			throw new TRuntimeException(e);

		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException ex) {
					ServerErrorHandler.handledException(ex);
				}
			}
		}
	}

	/**
	 * @param logList
	 * @param file
	 * @return BufferedReader
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	protected BufferedReader mappingFileLog(List<ExecutedLog> logList, File file) throws FileNotFoundException,
		IOException, ParseException {
		BufferedReader br;
		br = new BufferedReader(new FileReader(file));

		String pLine;
		while ((pLine = br.readLine()) != null) {

			String[] logString = StringUtil.toArrayFromDelimitString(pLine);

			String excutedDate = logString[0]; // ���s���t
			String usrCode = logString[1]; // ���[�U�R�[�h
			String usrName = logString[2]; // ���[�U����
			String ip = logString[3]; // IP�A�h���X
			String prgCode = logString[4]; // �v���O�����R�[�h
			String prgName = logString[5]; // �v���O��������
			String state = logString[6]; // �J�n�E�I��

			// �w��̊��ԏ����𖞑����邩
			if (!isSearchDate(excutedDate)) {
				continue;
			}

			// ���[�U���������ɓ����邩�ۂ��B
			if (!isSearchUsrCode(usrCode)) {
				continue;
			}

			// ���O�C�����O�A�E�g��\�����Ȃ��ƁA���ʂɃv���O�����R�[�h������r���s��
			if (!isIncludeLogin) {
				if (prgCode.equals(ExecutedLogger.LOGIN) || prgCode.equals(ExecutedLogger.LOGOUT)) {
					continue;
				}

				// �v���O�������������𖞑����邩�B
				if (!isSearchPrgCode(prgCode)) {
					continue;
				}

				// ���O�C�����O�A�E�g��\������ƁA���ʂɃv���O�����R�[�h������r�Ń��O�C���͎c��������ǉ�
			} else {
				// �v���O�������������𖞑����邩�B
				if (!isSearchPrgCode(prgCode)) {
					// �����𖞑����Ȃ��ꍇ���A���O�C���͗�O�Ƃ���B
					if (!(prgCode.equals(ExecutedLogger.LOGIN) || prgCode.equals(ExecutedLogger.LOGOUT))) {
						continue;
					}
				}
			}

			// ���O�l�N���X�̏��\�z
			ExecutedLog logObj = new ExecutedLog();
			logObj.setEXCUTED_DATE(DateUtil.toYMDHMSDate(excutedDate));
			logObj.setUSR_CODE(usrCode);
			logObj.setUSR_NAME(usrName);
			logObj.setIP_ADDRESS(ip);
			logObj.setPROGRAM_CODE(prgCode);
			logObj.setPROGRAM_NAME(prgName);
			logObj.setSTATE(state);

			// ���O���X�g�ɒǉ�
			logList.add(logObj);
		}
		return br;
	}

	/**
	 * ���t���������ɂ��t�@�C����������
	 * 
	 * @param excutedDate �������ꂽ���t
	 * @return boolean ��r����
	 * @throws ParseException
	 */
	protected boolean isSearchDate(String excutedDate) throws ParseException {
		Date logDate = DateUtil.toYMDDate(excutedDate);

		// ���O���t�������͈͓���������True��Ԃ��B
		if (Util.isSmallerThenByYMD(startDate, logDate) && Util.isSmallerThenByYMD(logDate, endDate)) {
			return true;
		}
		return false;
	}

	/**
	 * ���[�U�R�[�h���������ɂ�郊�X�g�ǉ�����
	 * 
	 * @param usrCode �������ꂽ���[�U�R�[�h
	 * @return boolean ��r����
	 */
	protected boolean isSearchUsrCode(String usrCode) {
		if (!Util.isNullOrEmpty(startUser)) {
			if (startUser.compareTo(usrCode) > 0) {
				return false;
			}
		}

		if (!Util.isNullOrEmpty(endUser)) {
			if (endUser.compareTo(usrCode) < 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * �v���O�����R�[�h���������ɂ�郊�X�g�ǉ�����
	 * 
	 * @param prgCode �������ꂽ�v���O�����R�[�h
	 * @return boolean ��r����
	 */
	protected boolean isSearchPrgCode(String prgCode) {
		if (!Util.isNullOrEmpty(startPrg)) {
			if (startPrg.compareTo(prgCode) > 0) {
				return false;
			}
		}

		if (!Util.isNullOrEmpty(endPrg)) {
			if (endPrg.compareTo(prgCode) < 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * ���t�Ń\�[�g���܂�
	 * 
	 * @param list �Ώۃ��X�g
	 */
	public void sortByDate(List<ExecutedLog> list) {

		Collections.sort(list, new DateComparator());
	}

	/**
	 * ���[�U�R�[�h�Ń\�[�g���܂�
	 * 
	 * @param list �Ώۃ��X�g
	 */
	public void sortByUserCode(List<ExecutedLog> list) {

		Collections.sort(list, new UsrCodeComparator());
	}

	/**
	 * �v���O�����R�[�h�Ń\�[�g���܂�
	 * 
	 * @param list �Ώۃ��X�g
	 */
	public void sortByProgramCode(List<ExecutedLog> list) {

		Collections.sort(list, new PrgCodeComparator());
	}

	/**
	 * @see jp.co.ais.trans.logic.system.ExecutedLogLogic#setCompanyCode(String)
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.ExecutedLogLogic#setStartDate(Date)
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.ExecutedLogLogic#setEndDate(Date)
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.ExecutedLogLogic#setStartProgramCode(String)
	 */
	public void setStartProgramCode(String prgCode) {
		this.startPrg = prgCode;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.ExecutedLogLogic#setEndProgramCode(String)
	 */
	public void setEndProgramCode(String prgCode) {
		this.endPrg = prgCode;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.ExecutedLogLogic#setStartUserCode(String)
	 */
	public void setStartUserCode(String userCode) {
		this.startUser = userCode;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.ExecutedLogLogic#setEndUserCode(String)
	 */
	public void setEndUserCode(String userCode) {
		this.endUser = userCode;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.ExecutedLogLogic#onLogin()
	 */
	public void onLogin() {
		this.isIncludeLogin = true;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.ExecutedLogLogic#offLogin()
	 */
	public void offLogin() {
		this.isIncludeLogin = false;
	}

	/**
	 * ����R�[�h�ݒ�
	 * 
	 * @param langCode
	 */
	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	/**
	 * @param prgDao
	 */
	public void setprgDao(PRG_MSTDao prgDao) {
		this.prgDao = prgDao;
	}

	/**
	 * ���O���X�g�Ƀv���O�������̂�ݒ�
	 * 
	 * @param logList
	 * @return ���O���X�g
	 */
	protected List<ExecutedLog> setProgramName(List<ExecutedLog> logList) {
		List<ExecutedLog> list = logList;

		// �v���O�����ꗗ�������Ă���
		List prgList = getProgramMstList();

		// ���ꂼ��̃��O���ɖ��̂��Z�b�g
		for (ExecutedLog log : list) {
			String prgName = "";
			for (int i = 0; i < prgList.size(); i++) {
				PRG_MST prgMst = (PRG_MST) prgList.get(i);
				if (log.getPROGRAM_CODE().equals(prgMst.getPRG_CODE())) {
					prgName = prgMst.getPRG_NAME();
					i = prgList.size();
				}
			}
			log.setPROGRAM_NAME(prgName);
		}

		return list;
	}

	/**
	 * �v���O�������X�g�擾
	 * 
	 * @return �v���O�������X�g
	 */
	protected List getProgramMstList() {
		// �S�v���O�������X�g�擾
		List list = prgDao.getAllPRG_MST2(companyCode);

		// ���O�C������ݒ�
		PRG_MST loginLogObj = new PRG_MST();

		loginLogObj.setPRG_CODE(ExecutedLogger.LOGIN);
		loginLogObj.setPRG_NAME(MessageUtil.getWord(langCode, "C03187"));
		list.add(loginLogObj);

		// ���O�C������ݒ�
		PRG_MST logoutLogObj = new PRG_MST();

		logoutLogObj.setPRG_CODE(ExecutedLogger.LOGOUT);
		logoutLogObj.setPRG_NAME(MessageUtil.getWord(langCode, "C03188"));
		list.add(logoutLogObj);

		list.add(logoutLogObj);

		return list;

	}

	/**
	 * ���t���я��̔�r�N���X
	 * 
	 * @author roh
	 */
	protected class DateComparator implements Comparator {

		/**
		 * ���t��r����
		 * 
		 * @param o1 ��r�Ώۂ̍ŏ��̃I�u�W�F�N�g
		 * @param o2 ��r�Ώۂ� 2 �Ԗڂ̃I�u�W�F�N�g
		 * @return boolean ��r����
		 */
		public int compare(Object o1, Object o2) {

			Date o1Date = ((ExecutedLog) o1).getEXCUTED_DATE();
			Date o2Date = ((ExecutedLog) o2).getEXCUTED_DATE();

			return o1Date.compareTo(o2Date);
		}
	}

	/**
	 * ���[�U���я��̔�r�N���X
	 * 
	 * @author roh
	 */
	protected class UsrCodeComparator implements Comparator {

		/**
		 * ���[�U�R�[�h��r����
		 * 
		 * @param o1 ��r�Ώۂ̍ŏ��̃I�u�W�F�N�g
		 * @param o2 ��r�Ώۂ� 2 �Ԗڂ̃I�u�W�F�N�g
		 * @return boolean ��r����
		 */
		public int compare(Object o1, Object o2) {
			String o1Code = ((ExecutedLog) o1).getUSR_CODE().toUpperCase();
			String o2Code = ((ExecutedLog) o2).getUSR_CODE().toUpperCase();

			return o1Code.compareTo(o2Code);
		}
	}

	/**
	 * �v���O�����R�[�h���я��̔�r�N���X
	 * 
	 * @author roh
	 */
	protected class PrgCodeComparator implements Comparator {

		/**
		 * �v���O�����R�[�h��r����
		 * 
		 * @param o1 ��r�Ώۂ̍ŏ��̃I�u�W�F�N�g
		 * @param o2 ��r�Ώۂ� 2 �Ԗڂ̃I�u�W�F�N�g
		 * @return boolean ��r����
		 */
		public int compare(Object o1, Object o2) {
			String o1Code = ((ExecutedLog) o1).getPROGRAM_CODE().toUpperCase();
			String o2Code = ((ExecutedLog) o2).getPROGRAM_CODE().toUpperCase();

			return o1Code.compareTo(o2Code);
		}
	}

	/**
	 * exeLogDao�ݒ�
	 * 
	 * @param exeLogDao
	 */
	public void setExeLogDao(ExecutedLogDao exeLogDao) {
		this.exeLogDao = exeLogDao;
	}

	/**
	 * sort�ݒ�
	 * 
	 * @param sort
	 */
	public void setSort(int sort) {
		this.sort = sort;
	}

}
