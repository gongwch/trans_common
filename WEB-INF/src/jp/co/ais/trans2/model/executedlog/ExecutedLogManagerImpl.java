package jp.co.ais.trans2.model.executedlog;

import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.log.ExecutedLogger.ExecutedFileLogger;
import jp.co.ais.trans.common.server.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.program.*;

/**
 * ���s���O�Q��ManagerImple
 */
public class ExecutedLogManagerImpl extends TModel implements ExecutedLogManager {

	/**
	 * ��񌟍� (SELECT)
	 * 
	 * @param condition ��������
	 * @return ���s���O�Q�Ə��
	 * @throws TException
	 */
	public List<ExecutedLog> get(ExecutedLogSearchCondition condition) throws TException {
		if (ServerConfig.isExeLogDBMode()) {
			return getDb(condition);
		} else {
			return getFile(condition);
		}

	}

	/**
	 * ��񌟍�-Excel�o�� (SELECT)
	 * 
	 * @param condition
	 * @return ���s���O�Q�Ə��
	 * @throws TException
	 */
	public byte[] getExcel(ExecutedLogSearchCondition condition) throws TException {

		List<ExecutedLog> dbList = get(condition);

		try {

			if (Util.isNullOrEmpty(dbList)) {
				return null;
			}
			ExecutedLogExcel exl = new ExecutedLogExcel(getUser().getLanguage());

			return exl.getExcel(dbList);

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * ���s���O�ꗗ���擾(�t�@�C������)
	 * 
	 * @param condition
	 * @return ���s���O�ꗗ
	 */
	public List<ExecutedLog> getFile(ExecutedLogSearchCondition condition) {
		// �t�@�C�����[�h
		BufferedReader br = null;

		try {
			List<ExecutedLog> logList = new ArrayList<ExecutedLog>();
			String fileName = getCompanyCode() + "." + "log";
			File file = new File(TServerEnv.USER_LOG_DIR.toString() + fileName);
			if (file.isFile()) {
				br = mappingFile(logList, file, condition);
				br.close();
			}

			int months = DateUtil.getMonthCount(condition.dateFrom, condition.dateTo);
			Date from = DateUtil.getFirstDate(condition.dateFrom);

			for (int i = 0; i <= months; i++) {

				String ext = "";

				SimpleDateFormat dateFormat = new SimpleDateFormat(ExecutedFileLogger.datePattern);
				ext = dateFormat.format(DateUtil.addMonth(from, i));

				fileName = getCompanyCode() + "." + "log" + ext;
				file = new File(TServerEnv.USER_LOG_DIR.toString() + fileName);

				if (file.isFile()) {
					br = mappingFile(logList, file, condition);
					br.close();
				} else {
					// �w���PATH�����݂��Ȃ��Ə����s�v
					continue;
				}
			}

			// ���̂�ݒ肷��B
			List<ExecutedLog> dtoList = setProgramName(logList);

			if (condition.sort == 1) {
				Collections.sort(dtoList, new DateComparator());
			} else if (condition.sort == 2) {
				Collections.sort(dtoList, new UsrCodeComparator());
			} else {
				Collections.sort(dtoList, new PrgCodeComparator());
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
	 * @param condition
	 * @return BufferedReader
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	protected BufferedReader mappingFile(List<ExecutedLog> logList, File file, ExecutedLogSearchCondition condition)
		throws FileNotFoundException, IOException, ParseException {
		BufferedReader br;

		br = new BufferedReader(new FileReader(file));

		String line;
		while ((line = br.readLine()) != null) {
			String[] logString = StringUtil.toArrayFromDelimitString(line);

			String excutedDate = logString[0]; // ���s���t
			String usrCode = logString[1]; // ���[�U�R�[�h
			String usrName = logString[2]; // ���[�U����
			String ip = logString[3]; // IP�A�h���X
			String prgCode = logString[4]; // �v���O�����R�[�h
			String prgName = logString[5]; // �v���O��������
			String state = logString[6]; // �J�n�E�I��

			// �w��̊��ԏ����𖞑����邩
			if (!isSearchDate(excutedDate, condition)) {
				continue;
			}

			// ���[�U���������ɓ����邩�ۂ��B
			if (!isSearchUsrCode(usrCode, condition)) {
				continue;
			}

			// ���O�C�����O�A�E�g��\�����Ȃ��ƁA���ʂɃv���O�����R�[�h������r���s��
			if (condition.isLogin == false) {
				if (prgCode.equals(ExecutedLogger.LOGIN) || prgCode.equals(ExecutedLogger.LOGOUT)) {
					continue;
				}

				// �v���O�������������𖞑����邩�B
				if (!isSearchPrgCode(prgCode, condition)) {
					continue;
				}

				// ���O�C�����O�A�E�g��\������ƁA���ʂɃv���O�����R�[�h������r�Ń��O�C���͎c��������ǉ�
			} else {
				// �v���O�������������𖞑����邩�B
				if (!isSearchPrgCode(prgCode, condition)) {
					// �����𖞑����Ȃ��ꍇ���A���O�C���͗�O�Ƃ���B
					if (!(prgCode.equals(ExecutedLogger.LOGIN) || prgCode.equals(ExecutedLogger.LOGOUT))) {
						continue;
					}
				}
			}

			// ���O�l�N���X�̏��\�z
			ExecutedLog exeFile = new ExecutedLog();
			exeFile.setExcDate(DateUtil.toYMDHMSDate(excutedDate));
			exeFile.setExcCode(usrCode);
			exeFile.setExcNames(usrName);
			exeFile.setIpAddress(ip);
			exeFile.setProCode(prgCode);
			exeFile.setProName(prgName);
			exeFile.setStste(state);

			// ���O���X�g�ɒǉ�
			logList.add(exeFile);

		}

		return br;
	}

	/**
	 * ���O���X�g�Ƀv���O�������̂�ݒ�
	 * 
	 * @param logList
	 * @return ���O���X�g
	 * @throws TException
	 */
	protected List<ExecutedLog> setProgramName(List<ExecutedLog> logList) throws TException {

		List<ExecutedLog> list = logList;

		// �v���O�������������Ă���
		List prgList = getProgramMstList();

		// ���ꂼ��̃��O���ɖ��̂��Z�b�g
		for (ExecutedLog log : list) {
			String prgName = "";
			for (int i = 0; i < prgList.size(); i++) {
				Program prg = (Program) prgList.get(i);
				if (log.getProCode().equals(prg.getCode())) {
					prgName = prg.getName();
					i = prgList.size();
				}
			}
			log.setProName(prgName);
		}

		return list;
	}

	/**
	 * �v���O�������X�g�擾
	 * 
	 * @return �v���O�������X�g
	 * @throws TException
	 */
	protected List getProgramMstList() throws TException {

		ProgramSearchCondition condition = new ProgramSearchCondition();
		condition.setCompanyCode(getCompanyCode());

		ProgramManager programManager = (ProgramManager) getComponent(ProgramManager.class);
		List<Program> prgList = programManager.get(condition);

		// ���O�C������ݒ�
		Program login = new Program();
		login.setCompanyCode(getCompanyCode());
		login.setCode(ExecutedLogger.LOGIN);
		login.setName(getWord("C03187"));// ���O�C��
		prgList.add(login);

		// ���O�C������ݒ�
		Program logout = new Program();
		logout.setCompanyCode(getCompanyCode());
		logout.setCode(ExecutedLogger.LOGOUT);
		logout.setName(getWord("C03188"));// ���O�A�E�g
		prgList.add(logout);

		return prgList;

	}

	/**
	 * ���t���������ɂ��t�@�C������
	 * 
	 * @param excutedDate �������ꂽ���t
	 * @param condition
	 * @return boolean ��r����
	 * @throws ParseException
	 */
	protected boolean isSearchDate(String excutedDate, ExecutedLogSearchCondition condition) throws ParseException {
		Date logDate = DateUtil.toYMDDate(excutedDate);

		// ���O���t�������͈͓���������True��Ԃ��B
		if (Util.isSmallerThenByYMD(condition.getDateFrom(), logDate)
			&& Util.isSmallerThenByYMD(logDate, condition.getDateTo())) {
			return true;
		}
		return false;
	}

	/**
	 * ���[�U�R�[�h���������ɂ��t�@�C������
	 * 
	 * @param usrCode �������ꂽ���[�U�R�[�h
	 * @param condition
	 * @return boolean ��r����
	 */
	protected boolean isSearchUsrCode(String usrCode, ExecutedLogSearchCondition condition) {
		if (!Util.isNullOrEmpty(condition.getUserFrom())) {
			if (condition.getUserFrom().compareTo(usrCode) > 0) {
				return false;
			}
		}

		if (!Util.isNullOrEmpty(condition.getUserTo())) {
			if (condition.getUserTo().compareTo(usrCode) < 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * �v���O�����R�[�h���������ɂ��t�@�C������
	 * 
	 * @param prgCode �������ꂽ�v���O�����R�[�h
	 * @param condition
	 * @return boolean ��r����
	 */
	protected boolean isSearchPrgCode(String prgCode, ExecutedLogSearchCondition condition) {
		if (!Util.isNullOrEmpty(condition.getProgramFrom())) {
			if (condition.getProgramFrom().compareTo(prgCode) > 0) {
				return false;
			}
		}

		if (!Util.isNullOrEmpty(condition.getProgramTo())) {
			if (condition.getProgramTo().compareTo(prgCode) < 0) {
				return false;
			}
		}
		return true;
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
		 * @param first ��r�Ώۂ̍ŏ��̃I�u�W�F�N�g
		 * @param second ��r�Ώۂ� 2 �Ԗڂ̃I�u�W�F�N�g
		 * @return boolean ��r����
		 */
		public int compare(Object first, Object second) {

			Date date1 = ((ExecutedLog) first).getExcDate();
			Date date2 = ((ExecutedLog) second).getExcDate();

			return date1.compareTo(date2);
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
		 * @param first ��r�Ώۂ̍ŏ��̃I�u�W�F�N�g
		 * @param second ��r�Ώۂ� 2 �Ԗڂ̃I�u�W�F�N�g
		 * @return boolean ��r����
		 */
		public int compare(Object first, Object second) {
			String user1 = ((ExecutedLog) first).getExcCode().toUpperCase();
			String user2 = ((ExecutedLog) second).getExcCode().toUpperCase();

			return user1.compareTo(user2);
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
		 * @param first ��r�Ώۂ̍ŏ��̃I�u�W�F�N�g
		 * @param second ��r�Ώۂ� 2 �Ԗڂ̃I�u�W�F�N�g
		 * @return boolean ��r����
		 */
		public int compare(Object first, Object second) {
			String prg1 = ((ExecutedLog) first).getProCode().toUpperCase();
			String prg2 = ((ExecutedLog) second).getProCode().toUpperCase();

			return prg1.compareTo(prg2);
		}
	}

	/**
	 * ��񌟍� (SELECT)
	 * 
	 * @param condition ��������
	 * @return List ��������
	 * @throws TException
	 */
	public List<ExecutedLog> getDb(ExecutedLogSearchCondition condition) throws TException {

		// DB Connection ����
		Connection conn = DBUtil.getConnection();

		// �������ʊi�[�p�z�񐶐�
		List<ExecutedLog> list = new ArrayList<ExecutedLog>();
		SQLCreator sql = new SQLCreator();
		try {
			sql.add("");
			sql.add("SELECT");
			sql.add("    log.EXCUTED_DATE");
			sql.add("    ,log.KAI_CODE");
			sql.add("    ,log.USR_CODE");
			sql.add("    ,log.USR_NAME");
			sql.add("    ,usr.USR_NAME_S");
			sql.add("    ,log.IP_ADDRESS");
			sql.add("    ,log.PROGRAM_CODE");
			sql.add("    ,prg.PRG_NAME");
			sql.add("    ,log.STATE");
			sql.add("FROM EXE_LOG_TBL log");
			sql.add("    LEFT OUTER JOIN USR_MST usr");
			sql.add("      ON log.KAI_CODE = usr.KAI_CODE");
			sql.add("      AND log.USR_CODE = usr.USR_CODE");
			sql.add("    LEFT OUTER JOIN PRG_MST prg");
			sql.add("      ON log.KAI_CODE = prg.KAI_CODE");
			sql.add("      AND log.PROGRAM_CODE = prg.PRG_CODE");
			sql.add("WHERE 1 = 1");

			// ���������F��ЃR�[�h
			if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
				sql.add("AND");
				sql.add("    log.KAI_CODE = ?", condition.getCompanyCode());
			}

			// ���������F�Ώ۔N����
			sql.add("AND");
			sql.adt("    log.EXCUTED_DATE >= ?", condition.getDateFrom());
			sql.add("AND");
			sql.adt("    log.EXCUTED_DATE <= ?", condition.getDateTo());

			// ���������F���[�U�[�J�n�R�[�h
			if (!Util.isNullOrEmpty(condition.getUserFrom())) {
				sql.add("AND");
				sql.add("    log.USR_CODE >= ?", condition.getUserFrom());
			}
			// ���������F���[�U�[�I���R�[�h
			if (!Util.isNullOrEmpty(condition.getUserTo())) {
				sql.add("AND");
				sql.add("    log.USR_CODE <= ?", condition.getUserTo());
			}
			// ���������F�v���O�����J�n�R�[�h
			if (!Util.isNullOrEmpty(condition.getProgramFrom())) {
				sql.add("AND");
				sql.add("    log.PROGRAM_CODE >= ?", condition.getProgramFrom());
			}
			// ���������F�v���O�����I���R�[�h
			if (!Util.isNullOrEmpty(condition.getProgramTo())) {
				sql.add("AND");
				sql.add("    log.PROGRAM_CODE <= ?", condition.getProgramTo());
			}

			if (condition.isLogin == false) {
				sql.add("AND");
				sql.add("    log.PROGRAM_CODE NOT LIKE 'LOGIN'");
				sql.add("AND");
				sql.add("    log.PROGRAM_CODE NOT LIKE 'LOGOUT'");
			}

			sql.add("ORDER BY");
			if (condition.getSort() == 1) {
				sql.add(" log.EXCUTED_DATE ");
			} else if (condition.getSort() == 2) {
				sql.add(" log.USR_CODE ");
				sql.add(" ,log.EXCUTED_DATE ");
			} else {
				sql.add(" log.PROGRAM_CODE");
				sql.add(" ,log.EXCUTED_DATE");
			}

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mapping(rs));
			}

			DBUtil.close(statement);
			DBUtil.close(rs);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return list;
	}

	/**
	 * �������ʒl��Bean�ɃZ�b�g
	 * 
	 * @param rs ResultSet ��������
	 * @return Employee �������ʒl���Z�b�g���ꂽBean
	 * @throws Exception
	 */
	protected ExecutedLog mapping(ResultSet rs) throws Exception {

		ExecutedLog bean = new ExecutedLog();

		bean.setExcDate(rs.getTimestamp("EXCUTED_DATE"));
		bean.setKaiCode(rs.getString("KAI_CODE"));
		bean.setExcCode(rs.getString("USR_CODE"));
		bean.setExcName(rs.getString("USR_NAME"));
		bean.setExcNames(rs.getString("USR_NAME_S"));
		bean.setIpAddress(rs.getString("IP_ADDRESS"));
		bean.setProCode(rs.getString("PROGRAM_CODE"));
		bean.setProName(rs.getString("PRG_NAME"));
		bean.setStste(rs.getString("STATE"));

		return bean;
	}
}
