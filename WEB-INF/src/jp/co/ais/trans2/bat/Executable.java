package jp.co.ais.trans2.bat;

import java.sql.*;
import java.util.*;

import javax.sql.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans2.common.db.*;

/**
 * �o�b�`�����x�[�X
 */
public abstract class Executable {

	/** �R���e�i */
	protected S2Container container;

	/** TRANS DB�ڑ��ݒ�t�@�C�� */
	protected String transDBj2ee = "j2ee.dicon";

	/**
	 * �J�n
	 */
	public void start() {
		// ��������
		if (!init()) {
			return;
		}

		// �������s
		execute();
	}

	/**
	 * ���s����<br>
	 * �ڑ��m�F�A�ݒ�t�@�C���Ǎ����������`�F�b�N
	 * 
	 * @return false:���s
	 */
	protected boolean init() {

		try {
			container = S2ContainerFactory.create(getContainerName());
			SingletonS2ContainerFactory.setContainer(container);

			// �ݒ�t�@�C���̓Ǎ�
			if (!readConfig()) {
				return false;
			}

			// DB�ڑ��m�F
			if (!canConnectDB()) {
				writeError("�f�[�^�x�[�X�ւ̐ڑ��Ɏ��s���܂����B");
				return false;
			}

			// �ݒ茟��
			return verifyConfig();

		} catch (Exception ex) {
			writeError(ex);
			return false;
		}
	}

	/**
	 * �R���e�i��`��
	 * 
	 * @return �R���e�i��`��
	 */
	protected abstract String getContainerName();

	/**
	 * DB�ڑ��m�F
	 * 
	 * @return true:�ڑ���ԁAfalse:��ڑ����
	 */
	protected boolean canConnectDB() {
		Connection con = null;

		try {
			for (String name : getDBConfigNames()) {
				S2Container dbContainer = S2ContainerFactory.create(name);
				DataSource dataSource = (DataSource) dbContainer.getComponent(DataSource.class);
				con = dataSource.getConnection();

				if (con == null) {
					writeError("DB�ڑ��Ɏ��s���܂����B");
					return false;
				}

				DBUtil.close(con);
			}

			return true;

		} catch (Exception ex) {
			writeError(ex, "DB�ڑ��Ɏ��s���܂����B");
			return false;

		} finally {
			try {
				DBUtil.close(con);
			} catch (Throwable e) {
				writeError(e, "DB�ڑ��Ɏ��s���܂����B");

			}
		}
	}

	/**
	 * DB�ڑ��ݒ薼
	 * 
	 * @return DB�ڑ��ݒ薼
	 */
	protected String[] getDBConfigNames() {
		return new String[] { getTransDBConfigName() };
	}

	/**
	 * DB�ڑ��ݒ薼
	 * 
	 * @return DB�ڑ��ݒ薼
	 */
	protected String getTransDBConfigName() {
		return transDBj2ee;
	}

	/**
	 * �ݒ�t�@�C���Ǎ�
	 * 
	 * @return false:���s
	 */
	protected abstract boolean readConfig();

	/**
	 * �ݒ茟��
	 * 
	 * @return false:NG
	 */
	protected abstract boolean verifyConfig();

	/**
	 * ���s
	 */
	protected abstract void execute();

	/**
	 * �ݒ�Ǎ�.
	 * 
	 * @param key �L�[
	 * @return �ݒ�l
	 * @throws TException �ݒ肪����
	 */
	protected String getProperty(String key) throws TException {
		try {
			return ServerConfig.getProperty(key);

		} catch (MissingResourceException e) {
			throw new TException("�ݒ肪������܂���B{0}", key);
		}
	}

	/**
	 * �ݒ�Ǎ�.
	 * 
	 * @param key �L�[
	 * @return �ݒ�l�̃��X�g
	 * @throws TException �ݒ肪����
	 */
	protected String[] getProperties(String key) throws TException {
		try {
			return ServerConfig.getProperties(key);

		} catch (MissingResourceException e) {
			throw new TException("�ݒ肪������܂���B{0}", key);
		}
	}

	/**
	 * ���b�Z�[�W�擾
	 * 
	 * @param id ���b�Z�[�WID
	 * @param words �o�C���h�P��
	 * @return ���b�Z�[�W
	 */
	protected String getMessage(String id, Object... words) {
		return MessageUtil.convertMessage("ja", id, words);
	}

	/**
	 * �P��擾
	 * 
	 * @param word �P��ID
	 * @return �P��
	 */
	protected String getWord(String word) {
		return MessageUtil.getWord("ja", word);
	}

	/**
	 * �f�o�b�O�p���O
	 * 
	 * @param msg ���b�Z�[�W
	 */
	protected void writeDebug(String msg) {
		ServerLogger.debug(msg);
	}

	/**
	 * �x�����O�o��
	 * 
	 * @param id ���b�Z�[�WID
	 * @param word �o�C���h����
	 */
	protected void writeWarning(String id, Object... word) {
		ServerLogger.warning(getMessage(id, word));
	}

	/**
	 * �G���[���O�o��(��O�x�[�X)
	 * 
	 * @param ex ������O
	 * @param id ���b�Z�[�WID
	 * @param word �o�C���h����
	 */
	protected void writeError(Throwable ex, String id, Object... word) {
		writeError(id, word);
		writeError(ex);
	}

	/**
	 * �G���[���O�o��
	 * 
	 * @param id ���b�Z�[�WID
	 * @param word �o�C���h����
	 */
	protected void writeError(String id, Object... word) {
		ServerLogger.error(getMessage(id, word));
	}

	/**
	 * �G���[���O�o��(��O�x�[�X)
	 * 
	 * @param ex ������O
	 */
	protected void writeError(Throwable ex) {
		Throwable e = ex;

		while (e != null) {
			String message;
			if (e instanceof TException) {
				String id = ((TException) e).getMessageID();
				Object[] words = ((TException) e).getMessageArgs();
				message = getMessage(id, words);

			} else {
				message = e.getMessage();
			}

			ServerLogger.error(message);

			StringBuilder buff = new StringBuilder("\n" + e.getClass().getName());

			StackTraceElement[] els = e.getStackTrace();
			for (StackTraceElement el : els) {
				buff.append("\n").append("  at ").append(el.getClassName());
				buff.append(".").append(el.getMethodName());
				buff.append("(").append(el.getFileName()).append(":").append(el.getLineNumber()).append(")");
			}

			ServerLogger.error(buff.toString());

			e = e.getCause();
		}
	}

}
