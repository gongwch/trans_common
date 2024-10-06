package jp.co.ais.trans2.common.print;

import java.io.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.common.ui.*;

/**
 * �v�����^�[
 * 
 * @author AIS
 */
public class TPrinter {

	/** �`�[�ԍ����� */
	public static final int SLIP_NO_LENGTH = 37;

	/** PDF */
	public static final String PDF = "PDF";

	/** XLS(Excel 2003) �w�b�_�[�o�C�g��` */
	public static final byte[] XLS_HEADER = new byte[] { -48, -49, 17, -32, -95, -79, 26, -31 };

	/** PDF���ڈ���t���O */
	public static boolean directPrint = ClientConfig.isFlagOn("trans.pdf.direct.print");

	/** true:�o�C�i����ZIP�o�C�i�� */
	public boolean zipBinary = false;

	/**
	 * �R���X�g���N�^�[
	 */
	public TPrinter() {
		//
	}

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param zipBinary true:�o�C�i����ZIP�o�C�i��
	 */
	public TPrinter(boolean zipBinary) {
		this.zipBinary = zipBinary;
	}

	/**
	 * �v���r���[����
	 * 
	 * @param data �f�[�^
	 * @param fileName �t�@�C����
	 * @throws TException
	 */
	public void preview(byte[] data, String fileName) throws TException {

		if (hasSlipNo(data)) {
			// �`�[�ԍ��܂�
			dispatchWithSlipNo(data, fileName);
		} else {
			createAndExecute(data, fileName);
		}

	}

	/**
	 * �v���r���[����
	 * 
	 * @param data �f�[�^
	 * @param fileName �t�@�C����
	 * @throws TException
	 */
	public void previewAttach(byte[] data, String fileName) throws TException {
		createAndExecute(data, fileName, true);
	}

	/**
	 * PDF�o�͔���
	 * 
	 * @param data
	 * @param fileName �t�@�C����
	 * @throws TException
	 */
	public void createAndExecute(byte[] data, String fileName) throws TException {
		createAndExecute(data, fileName, false);
	}

	/**
	 * PDF�o�͔���
	 * 
	 * @param data
	 * @param fileName �t�@�C����
	 * @param useOriginal
	 * @throws TException
	 */
	public void createAndExecute(byte[] data, String fileName, boolean useOriginal) throws TException {

		// �t�@�C��������p�X�ɕۑ�
		String dir = createResultFile(fileName, data, useOriginal);
		String exts = StringUtil.rightBX(fileName, 3).toUpperCase();

		String printerName = null;
		if (TLoginInfo.getUser() != null) {
			printerName = TLoginInfo.getUser().getPrinterName();
		}

		// ���ڈ���̔���
		if (isDirectPrint() && PDF.equals(exts) && !Util.isNullOrEmpty(printerName)) {
			// ���ڈ��
			SystemUtil.printPDF(dir, printerName);

		} else {
			// �t�@�C���N��
			SystemUtil.executeFile(dir);
		}

	}

	/**
	 * �`�[�ԍ��܂ނ��ǂ���
	 * 
	 * @param data
	 * @return true:�`�[�ԍ��܂�
	 */
	public boolean hasSlipNo(byte[] data) {
		if (data == null || data.length < SLIP_NO_LENGTH) {
			return false;
		}

		byte[] sign = new byte[SLIP_NO_LENGTH];
		StringUtil.arraycopy(data, data.length - SLIP_NO_LENGTH, sign, 0, SLIP_NO_LENGTH);

		String signature = Util.avoidNull(new String(sign));
		if (signature.startsWith("<SlipNo>")) {
			return true;
		}

		return false;
	}

	/**
	 * �w��`�[�ԍ����t�@�C�����擪�ɕt����PDF�t�@�C���쐬
	 * 
	 * @param data
	 * @param fileName
	 * @throws TException
	 */
	public void dispatchWithSlipNo(byte[] data, String fileName) throws TException {

		byte[] sign = new byte[20];
		StringUtil.arraycopy(data, data.length - SLIP_NO_LENGTH + 8, sign, 0, 20);

		String signature = Util.avoidNull(new String(sign));
		fileName = signature + "_" + Util.avoidNull(fileName);

		createAndExecute(data, fileName);
	}

	/**
	 * �������
	 * 
	 * @param data �f�[�^
	 * @param fileName �t�@�C����
	 * @throws TException
	 */
	public void print(byte[] data, String fileName) throws TException {

		// �t�@�C��������p�X�ɕۑ�
		String dir = createResultFile(fileName, data);

		// �t�@�C���N��
		SystemUtil.printPDF(dir);

	}

	/**
	 * �t�@�C���_�E�����[�h���ʂ̏o��(TEMP�t�H���_�ۑ�)
	 * 
	 * @param fileName �t�@�C����
	 * @param binary
	 * @return �o�̓t�@�C����
	 * @throws TException
	 */
	public String createResultFile(String fileName, byte[] binary) throws TException {
		return createResultFile(fileName, binary, false);
	}

	/**
	 * �t�@�C���_�E�����[�h���ʂ̏o��(TEMP�t�H���_�ۑ�)
	 * 
	 * @param fileName �t�@�C����
	 * @param binary
	 * @param useOriginal
	 * @return �o�̓t�@�C����
	 * @throws TException
	 */
	public String createResultFile(String fileName, byte[] binary, boolean useOriginal) throws TException {

		// �ꎞTemp�t�@�C���ꏊ
		String dirPath = SystemUtil.getTemporaryDir();

		// �t�@�C���W�J.�W�J���ꂽ�t�@�C�������߂�
		return createResultFile(dirPath, fileName, binary, useOriginal);

	}

	/**
	 * �t�@�C���_�E�����[�h���ʂ̏o��(�w��t�H���_�ۑ�)
	 * 
	 * @param dirPath �t�@�C���o�͏ꏊ
	 * @param fileName �t�@�C����
	 * @param binary
	 * @return �o�̓t�@�C����
	 * @throws TException
	 */
	public String createResultFile(String dirPath, String fileName, byte[] binary) throws TException {
		return createResultFile(dirPath, fileName, binary, false);
	}

	/**
	 * �t�@�C���_�E�����[�h���ʂ̏o��(�w��t�H���_�ۑ�)
	 * 
	 * @param dirPath �t�@�C���o�͏ꏊ
	 * @param fileName �t�@�C����
	 * @param binary
	 * @param useOriginal
	 * @return �o�̓t�@�C����
	 * @throws TException
	 */
	public String createResultFile(String dirPath, String fileName, byte[] binary, boolean useOriginal) throws TException {

		// �o�̓t�@�C�����uXLSX�v�̏ꍇ�A�t�@�C���g���q������uxls�v�ˁuxlsx�v
		if (!useOriginal && ExcelType.XLS.toString().equals(StringUtil.rightBX(fileName, 3).toUpperCase())) {

			// OLE���ʎq����
			boolean isXLS = true;

			if (isZipBinary()) {
				// ZIP �o�C�i���̏ꍇ�A�𓀂��Ă��画�肷��
				try {
					binary = ResourceUtil.toBinaryInZip(binary);
					zipBinary = false;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			ClientLogger.info("check xls binary header");

			if (binary.length > 0) {
				for (int i = 0; i < XLS_HEADER.length; i++) {
					byte b1 = XLS_HEADER[i];
					byte b2 = binary[i];

					if (b1 != b2) {
						isXLS = false;
						break;
					}
				}
			}

			if (!isXLS) {
				ClientLogger.info("binary header is xlsx");
				fileName += "x";
			} else {
				ClientLogger.info("binary header is xls");
			}
		}

		// �p�X�������폜
		fileName = FileUtil.toNonProhibitedName(fileName);

		String tmpFileName = fileName;

		// �t�@�C�����J����Ă���ꍇ�A�ʖ���t���ĕۑ�����
		for (int i = 1; !ResourceUtil.canWrite(dirPath + tmpFileName); i++) {

			if (i == 100) {
				// �p�X�w��~�X���̖������[�v���
				throw new TRuntimeException("File count over100." + fileName);
			}

			tmpFileName = "(" + i + ")" + fileName;

		}

		return createResultFileNative(dirPath, tmpFileName, binary);
	}

	/**
	 * �t�@�C���_�E�����[�h���ʂ̏o��(�w��t�H���_�A�t�@�C�����ۑ�).<br>
	 * ���ʏ���
	 * 
	 * @param dirPath �t�@�C���o�͏ꏊ
	 * @param fileName �t�@�C����(�g���q�t��)
	 * @param binary
	 * @return �o�̓t�@�C����
	 * @throws TException
	 */
	public String createResultFileNative(String dirPath, String fileName, byte[] binary) throws TException {

		try {

			String fullPath = dirPath + fileName;

			ClientLogger.info("zip file binary=" + binary.length);
			ClientLogger.memory("write binay start");

			if (isZipBinary()) {
				ClientLogger.info("write zip binary");
				ResourceUtil.writeBinaryInZip(fullPath, binary);
			} else {
				ClientLogger.info("write binary");
				ResourceUtil.writeBinary(fullPath, binary);
			}

			ClientLogger.memory("write binary end");
			ClientLogger.debug("create fileName=" + fullPath);

			return fullPath;

		} catch (Exception e) {
			throw new TException(e);
		}

	}

	/**
	 * @return true:���ڈ��
	 */
	public boolean isDirectPrint() {
		return directPrint;
	}

	/**
	 * @return zipBinary true:�o�C�i����ZIP�o�C�i��
	 */
	public boolean isZipBinary() {
		return zipBinary;
	}

	/**
	 * @param zipBinary true:�o�C�i����ZIP�o�C�i��
	 */
	public void setZipBinary(boolean zipBinary) {
		this.zipBinary = zipBinary;
	}

}
