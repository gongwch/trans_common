package jp.co.ais.trans2.model.calendar;

import java.io.*;
import java.math.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.common.exception.*;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;

/**
 * �J�����_�[�Ǘ��̎����N���X�ł��B
 * 
 * @author AIS
 */
public class CalendarManagerImpl extends TModel implements CalendarManager {

	/**
	 * �w������ɊY������J�����_�[����Ԃ��B
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������J�����_�[���<���t�A�i�\:true�A�s��:false�A���݂��Ȃ�:null�j>
	 * @throws TException
	 */
	public Map<String, Boolean> get(CalendarSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		Map<String, Boolean> map = new TreeMap<String, Boolean>();

		try {

			String column = condition.getDivisionColumn();
			if (Util.isNullOrEmpty(column)) {
				column = CalendarSearchCondition.BANK;
			}

			// �J�����_�[�}�X�^�𒊏o
			SQLCreator sql = new SQLCreator();

			sql.add("");
			sql.add(" SELECT cal.CAL_DATE, ");
			sql.add("        cal." + column + " AS KBN ");
			sql.add(" FROM   AP_CAL_MST cal ");
			sql.add(" WHERE  cal.KAI_CODE = ? ", condition.getCompanyCode()); // ��ЃR�[�h

			// �J�n���t
			if (!Util.isNullOrEmpty(condition.getDateFrom())) {
				sql.add(" AND CAL_DATE >= ? ", condition.getDateFrom());
			}

			// �I�����t
			if (!Util.isNullOrEmpty(condition.getDateTo())) {
				sql.add(" AND CAL_DATE <= ? ", condition.getDateTo());
			}

			sql.add(" ORDER  BY cal.CAL_DATE ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				Date date = rs.getDate("CAL_DATE");
				String kbn = rs.getString("KBN");

				String dt = DateUtil.toYMDString(date);

				if (Util.isNullOrEmpty(kbn)) {
					map.put(dt, null); // ���ݒ肠��
				} else {
					if (CalendarSearchCondition.BANK.equals(column)) {
						map.put(dt, "0".equals(kbn)); // 0:�c�Ɠ�
					} else {
						map.put(dt, "1".equals(kbn)); // 1:�\���i�Վ��x�����A�Ј��x�����j
					}
				}
			}

			DBUtil.close(rs);
			DBUtil.close(statement);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return map;
	}

	/**
	 * @param condition
	 * @return �J�����_�[�}�X�^
	 * @throws TException
	 */
	public List<CalendarEntity> getCalendar(CalendarSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<CalendarEntity> list = new ArrayList<CalendarEntity>();

		try {
			// �J�����_�[�}�X�^�𒊏o
			SQLCreator sql = new SQLCreator();
			sql.add(" SELECT ");
			sql.add("  cal.KAI_CODE ");
			sql.add(" ,cal.CAL_DATE ");
			sql.add(" ,cal.CAL_HARAI ");
			sql.add(" ,cal.CAL_BNK_KBN ");
			sql.add(" ,cal.CAL_SHA ");
			sql.add(" ,cal.CAL_INP_DATE ");
			sql.add(" ,cal.UPD_DATE ");
			sql.add(" ,cal.PRG_ID ");
			sql.add(" ,cal.USR_ID ");
			sql.add(" FROM AP_CAL_MST cal ");
			sql.add(" WHERE 1 = 1 ");

			// ��ЃR�[�h
			if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
				sql.add("AND cal.KAI_CODE = ? ", condition.getCompanyCode());
			}

			// �J�n���t
			if (!Util.isNullOrEmpty(condition.getDateFrom())) {
				sql.add(" AND cal.CAL_DATE >= ? ", condition.getDateFrom());
			}

			// �I�����t
			if (!Util.isNullOrEmpty(condition.getDateTo())) {
				sql.add(" AND cal.CAL_DATE <= ? ", condition.getDateTo());
			}
			sql.add(" ORDER BY cal.CAL_DATE ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mapping(rs));
			}
			DBUtil.close(rs);
			DBUtil.close(statement);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
		return list;
	}

	/**
	 * O/R�}�b�s���O
	 * 
	 * @param rs
	 * @return �G���e�B�e�B
	 * @throws Exception
	 */
	protected CalendarEntity mapping(ResultSet rs) throws Exception {

		CalendarEntity bean = new CalendarEntity();

		// ��{���}�b�s���O
		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setCalDate(rs.getDate("CAL_DATE"));
		bean.setCalSha(rs.getString("CAL_HARAI"));
		bean.setCalBank(rs.getString("CAL_BNK_KBN"));
		bean.setCalRinji(rs.getString("CAL_SHA"));
		return bean;
	}

	/**
	 * �J�����_�[�}�X�^�ɓo�^
	 * 
	 * @param list
	 * @throws TException
	 */
	public void entry(List<CalendarEntity> list, CalendarSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			delete(conn, condition);

			for (CalendarEntity bean : list) {
				SQLCreator sql = new SQLCreator();
				sql.add(" INSERT INTO AP_CAL_MST ");
				sql.add(" ( ");
				sql.add(" KAI_CODE ");
				sql.add(" ,CAL_DATE ");
				sql.add(" ,CAL_HARAI ");
				sql.add(" ,CAL_BNK_KBN ");
				sql.add(" ,CAL_SHA ");
				sql.add(" ,CAL_INP_DATE ");
				sql.add(" ,PRG_ID ");
				sql.add(" ,USR_ID ");
				sql.add(" ) VALUES ( ");
				sql.add("  ?", bean.getCompanyCode());
				sql.add(", ?", bean.getCalDate());
				sql.add(", ?", bean.getCalSha());
				sql.add(", ?", bean.getCalBank());
				sql.add(", ?", bean.getCalRinji());
				sql.adt(", ?", getNow());
				sql.add(", ?", getProgramCode());
				sql.add(", ?", getUserCode());
				sql.add(" ) ");

				DBUtil.execute(conn, sql);
			}
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * �J�����_�[�}�X�^����폜
	 * 
	 * @param conn
	 * @param condition
	 * @throws TException
	 */
	public void delete(Connection conn, CalendarSearchCondition condition) throws TException {

		SQLCreator sql = new SQLCreator();
		sql.add(" DELETE FROM AP_CAL_MST ");
		sql.add("WHERE KAI_CODE = ? ", condition.getCompanyCode());
		sql.add("AND CAL_DATE >= ? ", condition.getDateFrom());
		sql.add("AND CAL_DATE <= ? ", condition.getDateTo());

		DBUtil.execute(conn, sql);
	}

	/**
	 * �G�N�X�Z���捞�̏���
	 * 
	 * @param file
	 * @throws TException
	 */
	public void importExcel(File file) throws TException {

		// �t�@�C������I/F�f�[�^�̒��o
		List<CalendarEntity> list = convertExcel(file);

		CalendarSearchCondition condition = new CalendarSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setDateFrom(list.get(0).getCalDate());
		condition.setDateTo(list.get(list.size() - 1).getCalDate());

		entry(list, condition);
	}

	/**
	 * �V�[�g���݃`�F�b�N
	 * 
	 * @param excel
	 * @return true: ���Ȃ�
	 */
	protected boolean isExistsSheet(TExcel excel) {

		if (excel.getSheetCount() == 0) {
			return false;
		}
		return true;
	}

	/**
	 * �V�[�g�s���݃`�F�b�N
	 * 
	 * @param sheet
	 * @return true:���Ȃ�
	 */
	protected boolean isExistsRow(TExcelSheet sheet) {

		if (sheet.getRowCount() == 1) {
			return false;
		}
		return true;
	}

	/**
	 * �e���v���[�g�̐������`�F�b�N
	 * 
	 * @param file
	 * @return �e���v���[�g�̐�����
	 * @throws TException
	 */
	protected List<CalendarEntity> convertExcel(File file) throws TException {

		List<CalendarEntity> list = new ArrayList<CalendarEntity>();

		try {
			// excel�t�@�C����ǂݎ��
			TExcel excel = new TExcel(file);
			// �V�[�g���݃`�F�b�N
			if (!isExistsSheet(excel)) {
				// �捞�ΏۊO�̃t�@�C���ł��B�V�X�e������o�͂����t�@�C���𗘗p���Ă��������B
				throw new TWarningException(getMessage("I00775"));
			}
			TExcelSheet sheet = excel.getSheet(0);
			// �s���݃`�F�b�N
			if (!isExistsRow(sheet)) {
				// �t�@�C���ɗL���ȍs������܂���B
				throw new TWarningException(getMessage("I00296"));
			}
			// �w�b�_�`�F�b�N(�J�����ύX���Ȃ����e���v���[�g�̐��������Ƃ�)
			if (sheet.sheet.getRow(0).getLastCellNum() != 5) {
				// �捞�ΏۊO�̃t�@�C���ł��B�V�X�e������o�͂����t�@�C���𗘗p���Ă��������B
				throw new TWarningException(getMessage("I00775"));
			}

			Date preDate = null;

			for (int row = 1; row < sheet.getRowCount(); row++) {
				String rowMsg = (row + 1) + getWord("C04288") + " �F ";

				String tgtDate = sheet.getString(row, 0);

				// �K�{�`�F�b�N
				if (Util.isNullOrEmpty(tgtDate)) {
					throw new TWarningException(rowMsg + getMessage("I00037", "C00446"));
				}

				// ���t�`�F�b�N
				try {
					TExcel.getExcelDate(new BigDecimal(tgtDate));
				} catch (Exception e) {
					// ���t�`���ł͂���܂���B�������`���œ��͂��s���Ă��������B
					throw new TWarningException(rowMsg + getMessage("I00803"));
				}

				CalendarEntity bean = mapping(sheet, row);

				if (preDate != null && !DateUtil.equals(DateUtil.addDay(preDate, 1), bean.getCalDate())) {
					throw new TWarningException(rowMsg + getMessage("I01061", DateUtil.toYMDString(bean.getCalDate())));
				}
				list.add(bean);
				preDate = bean.getCalDate();
			}
		} catch (TWarningException e) {
			throw e;
		} catch (TException e) {
			throw e;
		} catch (Exception e) {
			ServerLogger.error("error", e);
			// �t�@�C���̓ǂݍ��݂Ɏ��s���܂����B
			throw new TException("E00021");
		}
		return list;
	}

	/**
	 * �}�b�s���O
	 * 
	 * @param sheet
	 * @param row
	 * @return CalendarEntity
	 */
	public CalendarEntity mapping(TExcelSheet sheet, int row) {

		CalendarEntity bean = new CalendarEntity();
		bean.setCompanyCode(getCompanyCode());
		bean.setCalDate(TExcel.getExcelDate(new BigDecimal((sheet.getString(row, 0)))));
		bean.setCalSha(geteCheckFlg(sheet.getString(row, 2)));
		bean.setCalBank(geteCheckFlg(sheet.getString(row, 3)));
		bean.setCalRinji(geteCheckFlg(sheet.getString(row, 4)));

		return bean;
	}

	/**
	 * @param data
	 * @return flg
	 */
	public String geteCheckFlg(String data) {
		return Util.isNullOrEmpty(data) ? "0" : "1";
	}

	/**
	 * �p�r�ꗗ���G�N�Z���`���ŕԂ�
	 * 
	 * @param condition ��������
	 * @return �G�N�Z���`���̗p�r�ꗗ
	 * @throws TException
	 */
	public byte[] exportExcel(CalendarSearchCondition condition) throws TException {

		List<CalendarEntity> list = getCalendar(condition);

		if (list == null || list.isEmpty()) {
			return null;
		}

		CalenderExcel excel = new CalenderExcel(getUser().getLanguage());
		byte[] data = excel.getExcel(list);

		return data;
	}
}
