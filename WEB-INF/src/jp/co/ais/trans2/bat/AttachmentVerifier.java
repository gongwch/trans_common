package jp.co.ais.trans2.bat;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.model.attach.verify.*;

/**
 * 
 */
public class AttachmentVerifier extends Executable {

	/**
	 * ���C�����\�b�h
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new AttachmentVerifier().start();
	}

	/**
	 * �R���e�i��`��
	 * 
	 * @return �R���e�i��`��
	 */
	@Override
	protected String getContainerName() {
		return "verify.dicon";
	}

	/**
	 * �ݒ�t�@�C���Ǎ�
	 * 
	 * @return false:���s
	 */
	@Override
	protected boolean readConfig() {
		return true;
	}

	/**
	 * @see jp.co.ais.trans2.bat.Executable#verifyConfig()
	 */
	@Override
	protected boolean verifyConfig() {
		return true;
	}

	/**
	 * �捞����
	 */
	@Override
	protected void execute() {
		writeDebug("�Y�t�t�@�C������ �J�n");

		try {
			// ���s���W���[��
			AttachmentVerifyManager logic = (AttachmentVerifyManager) container
				.getComponent(AttachmentVerifyManager.class);

			try {
				List<AttachmentVerifyResult> result = logic.get();
				writeLog("AttachmentVerifyLog", result);
			} catch (Throwable ex) {
				writeError(ex);
			}

		} catch (Throwable ex) {
			writeError(ex);

		} finally {
			writeDebug("�Y�t�t�@�C������ �I��");
		}
	}

	/**
	 * �G���[���O�L�q
	 * 
	 * @param fileName �G���[�t�@�C����
	 * @param logList ���O���X�g
	 * @throws TException
	 */
	protected void writeLog(String fileName, List<AttachmentVerifyResult> logList) throws TException {

		FileWriter writer = null;
		try {
			String logName = FileUtil.removeExtension(fileName);
			logName += "_" + DateUtil.toYMDHMSPlainString(new Date()) + ".log";
			writer = new FileWriter(new File(getLogPath() + logName));

			for (AttachmentVerifyResult result : logList) {
				List<String> list = new LinkedList<String>();
				list.add(result.getKAI_CODE());
				list.add(result.getTYPE().toString());
				list.add(result.getKEY1());
				list.add(result.getKEY2());
				list.add(result.getKEY3());
				list.add(result.getKEY4());
				list.add(result.getFILE_NAME());
				list.add(result.getSRV_FILE_NAME());
				list.add(result.getMESSAGE());

				String line = StringUtil.toCommaString(list);
				writer.write(line + SystemUtil.LINE_SEP);
			}

			writer.flush();

		} catch (IOException e) {
			throw new TException(e, "�G���[���O�o�͂Ɏ��s���܂����B");

		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				ServerLogger.debug(e);
			}
		}
	}

	/**
	 * ���O�t�@�C���o�͐�p�X
	 * 
	 * @return ���O�t�@�C���o�͐�p�X
	 */
	protected String getLogPath() {
		try {
			return ServerConfig.getProperty("trans.attachment.verify.log.dir");
		} catch (Exception e) {
			return "C:\\temp\\";
		}
	}

}
