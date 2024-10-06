package jp.co.ais.trans.common.client;

import java.io.*;
import java.net.*;
import java.util.*;

import jp.co.ais.trans.common.client.http.*;
import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.file.*;

/**
 * ���N�G�X�g�N���X���
 */
public class TRequestBase {

	/** response ��؂蕶��START */
	public static final String TOKEN_RESPONSE = "<trans.response>";

	/** response ��؂蕶��END */
	public static final String TOKEN_RESPONSE_END = "</trans.response>";

	/** KEY-VALUE�`��START */
	public static final String TOKEN_KEY_VALUE_LIST = "<trans.keyValueList>";

	/** KEY-VALUE�`��END */
	public static final String TOKEN_KEY_VALUE_LIST_END = "</trans.keyValueList>";

	/** List�`��START */
	public static final String TOKEN_RECORD_LIST = "<trans.recordList>";

	/** List�`��END */
	public static final String TOKEN_RECORD_LIST_END = "</trans.recordList>";

	/** �o�C�i���`��START */
	public static final String TOKEN_BINARY = "<trans.binary>";

	/** �o�C�i���`��END */
	public static final String TOKEN_BINARY_END = "</trans.binary>";

	/** �I�u�W�F�N�g�`��START */
	public static final String TOKEN_OBJECT = "<trans.object>";

	/** �I�u�W�F�N�g�`��END */
	public static final String TOKEN_OBJECT_END = "</trans.object>";

	/** �I�u�W�F�N�gEX�`��START */
	public static final String TOKEN_OBJECT_EX = "<trans.object.ex>";

	/** �I�u�W�F�N�gEX�`��END */
	public static final String TOKEN_OBJECT_EX_END = "</trans.object.ex>";

	/** �G���[�`��START */
	public static final String TOKEN_ERROR = "<trans.error>";

	/** �G���[�`��END */
	public static final String TOKEN_ERROR_END = "</trans.error>";

	/** SUCCESS�ʒm */
	public static final String TOKEN_SUCCESS = "<trans.success/>";

	/** ���X�|���X�^�C�v:�f�[�^�Ȃ� */
	public static final int RESP_NO_DATA = 0;

	/** ���X�|���X�^�C�v:Map */
	public static final int RESP_MAP = 1;

	/** ���X�|���X�^�C�v:List */
	public static final int RESP_LIST = 2;

	/** ���X�|���X�^�C�v:�o�C�i�� */
	public static final int RESP_BINARY = 3;

	/** ���X�|���X�^�C�v:�I�u�W�F�N�g */
	public static final int RESP_OBJECT = 4;

	/** ���X�|���X�^�C�v:�G���[ */
	public static final int RESP_ERROR = 16;

	/** ���X�|���X�^�C�v:�\���G���[ */
	public static final int RESP_ERROR_SYNTAX = 32;

	/** �G���[�R�[�h�擾���̃L�[ */
	public static final String ERR_CODE_KEY = "code";

	/** �G���[���b�Z�[�W�擾���̃L�[ */
	public static final String ERR_MSG_KEY = "notice";

	/** Dto�����𑗐M����ۂ̃L�[ */
	public static final String SEND_DTO_KEY = "trans.dtoString";

	/** LIST�𑗐M����ۂ̃L�[ */
	public static final String SEND_LIST_KEY = "trans.listString";

	/** Object�����𑗐M����ۂ̃L�[ */
	public static final String SEND_OBJ_KEY = "trans.objString";

	/** Boolean���ʂ̃L�[ */
	public static final String RESULT_BOOLEAN = "trans.BooleanResult";

	/** �o�C�i���f�[�^���ʂ̃f�[�^���L�[ */
	public static final String RESULT_BYTE_DATA = "trans.BinaryData";

	/** �o�C�i���f�[�^���ʂ̃t�@�C�����L�[ */
	public static final String RESULT_BYTE_FILENAME = "trans.FileName";

	/** �o�C�i���f�[�^���ʂ̃t�@�C���g���q�L�[ */
	public static final String RESULT_BYTE_EXTENSION = "trans.FileExtension";

	/** �ڑ���A�h���X */
	private static String urlAddless;

	static {
		// �O���ݒ肩��ڑ���擾
		urlAddless = ClientConfig.getBaseAddress();

		ClientLogger.info(urlAddless);
	}

	/** HTTP���N�G�X�g�p */
	public TAccessHTTP tAccessHTTP;

	/** ���M�l���X�g */
	private List<String[]> sendValues;

	/** ���M�lDto���X�g */
	private Map<String, List<TransferBase>> sendDtoValues;

	/** ���ʒl���X�g(���X�g�`��) */
	private List<List<String>> resultList;

	/** ���ʒl���X�g(key/value�`��) */
	private Map<String, String> resultMap;

	/** �G���[���� */
	private Map<String, String> errorMap;

	/** �o�C�i���f�[�^���� */
	private Map<String, Object> resultBinaryDataSet;

	/** Object���� */
	private Object resultObject;

	/** respMode */
	private int respMode = 0;

	/**
	 * �R���X�g���N�^
	 */
	public TRequestBase() {
		tAccessHTTP = new TAccessHTTP(urlAddless);

		sendValues = new LinkedList<String[]>();
		sendDtoValues = new TreeMap<String, List<TransferBase>>();
		resultList = new LinkedList<List<String>>();
		resultMap = new TreeMap<String, String>();
		errorMap = new TreeMap<String, String>();
		resultBinaryDataSet = new TreeMap<String, Object>();
		resultObject = null;
		respMode = 0;
	}

	/**
	 * ���M
	 * 
	 * @param path ���s�p�X
	 * @return true:success false:error
	 * @throws IOException ���M�G���[
	 */
	public boolean request(String path) throws IOException {
		return request(path, new ArrayList<File>(0));
	}

	/**
	 * ���M
	 * 
	 * @param path ���s�p�X
	 * @param file �t�@�C��
	 * @return true:success false:error
	 * @throws IOException ���M�G���[
	 */
	public boolean request(String path, File file) throws IOException {
		List<File> list = new ArrayList<File>(1);
		list.add(file);
		return request(path, list);
	}

	/**
	 * ���M
	 * 
	 * @param path ���s�p�X
	 * @param files �t�@�C�����X�g
	 * @return true:success false:error
	 * @throws IOException ���M�G���[
	 */
	public boolean request(String path, List<File> files) throws IOException {

		if (ClientConfig.isFlagOn("system.type.trans1")) {
			// TRANS1�o�[�W�����̃��[�J���e�X�g�p
			addSendValues("T_CC", TClientLoginInfo.getInstance().getCompanyCode());
			addSendValues("T_UC", TClientLoginInfo.getInstance().getUserCode());
		}

		respMode = 0;
		resultList = new LinkedList<List<String>>();
		resultMap = new TreeMap<String, String>();
		errorMap = new TreeMap<String, String>();
		resultBinaryDataSet = new TreeMap<String, Object>();
		resultObject = null;

		InputStream stream = null;
		try {

			if (!jp.co.ais.trans2.common.config.ClientConfig.isWeb()) {
				// WEB���[�h�ȊO�A�����Ȃ�
				throw new TRuntimeException("E00001", "Request need web mode ON.");
			}

			// Dto���X�g��String�y�A��sendValues�֔z�u
			for (String key : sendDtoValues.keySet()) {
				List<TransferBase> sendDtoList = sendDtoValues.get(key);
				if (sendDtoList == null) {
					continue;
				}

				StringBuilder dtoStr = new StringBuilder();
				for (TransferBase row : sendDtoList) {
					if (row == null) {
						continue;
					}

					if (dtoStr.length() != 0) {
						dtoStr.append(StringUtil.LINESEPARATOR);
					}

					List<String> list = row.toStringArray();
					dtoStr.append(StringUtil.toHTMLEscapeString(list));
				}

				addSendValues(key, dtoStr.toString());
			}

			// ���M.
			if (files.isEmpty()) {
				stream = tAccessHTTP.send(path, sendValues, "");
			} else {
				stream = tAccessHTTP.sendFile(path, sendValues, "", files);
			}

			// �ԐM���.
			boolean stat = this.parseResponse(stream);

			if (!stat) {
				return false;
			}

			return respMode != RESP_ERROR;

		} finally {
			// ���M�l�N���A
			sendValues.clear();
			sendDtoValues.clear();

			ResourceUtil.closeInputStream(stream);
		}
	}

	/**
	 * �ԐM���.
	 * 
	 * @param stream
	 * @return false:�`���G���[
	 * @throws IOException
	 */
	private boolean parseResponse(InputStream stream) throws IOException {

		// �f�[�^�̎擾�E���ʃ��X�g�ێ�
		InputStreamReader rd = new InputStreamReader(stream);
		BufferedReader rb = new BufferedReader(rd);

		// ��M���.
		String line;

		/* �ŏ��̋�ؕ�����T��. */
		while ((line = rb.readLine()) != null) {
			if (line.compareTo(TOKEN_RESPONSE) == 0) {
				break;
			}
		}

		/* �擪�̋�ؕ����Ȃ� */
		if (line == null) {
			respMode = RESP_ERROR_SYNTAX;
			return false;
		}

		/* �f�[�^��̋�ؕ������擾 */
		line = rb.readLine();
		if (line == null) {
			respMode = RESP_ERROR_SYNTAX;
			return false;
		}

		boolean result = false;
		if (line.compareTo(TOKEN_KEY_VALUE_LIST) == 0) {
			// keyValue �`��
			result = makeMapFromStream(rb, TOKEN_KEY_VALUE_LIST_END, resultMap);
			respMode = RESP_MAP;

		} else if (line.compareTo(TOKEN_RECORD_LIST) == 0) {
			// list �`��.
			result = makeListFromStream(rb);
			respMode = RESP_LIST;

		} else if (line.compareTo(TOKEN_BINARY) == 0) {
			// �o�C�i���`��
			result = makeBinaryFromStream(rb);
			respMode = RESP_BINARY;

		} else if (line.compareTo(TOKEN_OBJECT) == 0) {
			// �o�C�i���`��
			result = makeObjectFromStream(rb);
			respMode = RESP_OBJECT;

		} else if (line.compareTo(TOKEN_OBJECT_EX) == 0) {
			// �o�C�i��EX�`��
			result = makeObjectExFromStream(rb);
			respMode = RESP_OBJECT;

		} else if (line.compareTo(TOKEN_SUCCESS) == 0) {
			// ���s�݂̂̃��N�G�X�g.(insert,update�Ȃ�modify�n)
			result = true;
			respMode = RESP_NO_DATA;

		} else if (line.compareTo(TOKEN_ERROR) == 0) {
			// server�����ł̃G���[.
			result = makeMapFromStream(rb, TOKEN_ERROR_END, errorMap);
			respMode = RESP_ERROR;
		}

		// �Ō�̋�ؕ����܂œǔ�.
		while ((line = rb.readLine()) != null) {
			if (line.compareTo(TOKEN_RESPONSE_END) == 0) {
				break;
			}
		}

		if (!result || line == null) {
			respMode = RESP_ERROR_SYNTAX;
			return false;
		}

		// �Ō�܂œǔ�.
		while (rb.readLine() != null) {
			/* Nothing */
		}

		return true;
	}

	/**
	 * response��stream����A�ʐM�K���list�`���œǍ��݁AList���쐬����.
	 * 
	 * @param rb ��Buffer
	 * @return false:�������s
	 * @throws IOException
	 */
	private boolean makeListFromStream(BufferedReader rb) throws IOException {

		String line;
		String[] columns;

		if (rb == null) {
			return false;
		}

		while ((line = rb.readLine()) != null) {
			if (line.compareTo(TOKEN_RECORD_LIST_END) == 0) {
				break;
			}
			columns = StringUtil.toArrayFromDelimitString(line);
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < columns.length; i++) {
				// escapeHTML�̃f�R�[�h.
				String strDec = StringUtil.unescapeHtml(columns[i]);
				list.add(strDec);
			}
			resultList.add(list);
		}

		if (line == null) {
			return false;
		}
		return true;
	}

	/**
	 * response��stream����A�ʐM�K���key:value�`���œǍ��݁AMap���쐬����.
	 * 
	 * @param rb ��Buffer
	 * @param tokenEnd END����Token
	 * @param map ���ꍞ��Map
	 * @return false:�������s
	 * @throws IOException
	 */
	private boolean makeMapFromStream(BufferedReader rb, String tokenEnd, Map<String, String> map) throws IOException {

		String line;
		String[] keyValue;

		if (rb == null) {
			return false;
		}

		while ((line = rb.readLine()) != null) {
			if (line.compareTo(tokenEnd) == 0) {
				break;
			}

			keyValue = StringUtil.toArrayFromDelimitString(line);

			if (keyValue.length == 1) {
				map.put(keyValue[0], "");

			} else if (2 <= keyValue.length) {

				// escapeHTML�̃f�R�[�h.
				String strDec = StringUtil.unescapeHtml(keyValue[1]);
				map.put(keyValue[0], strDec);
			} else {
				throw new TRuntimeException("E00009", "C02768");
			}
		}

		if (line == null) {
			return false;
		}

		return true;
	}

	/**
	 * response��stream����A�o�C�i���`����Ǎ���.
	 * 
	 * @param rb ��Buffer
	 * @return false:�������s
	 * @throws IOException
	 */
	private boolean makeBinaryFromStream(BufferedReader rb) throws IOException {

		String line;

		if (rb == null) {
			return false;
		}

		while ((line = rb.readLine()) != null) {
			if (line.compareTo(TOKEN_BINARY_END) == 0) {
				break;
			}

			String[] splits = StringUtil.toArrayFromDelimitString(line);
			if (splits.length != 3) {
				throw new TRuntimeException("E00009", "C02768");
			}

			resultBinaryDataSet.put(RESULT_BYTE_FILENAME, StringUtil.unescapeHtml(splits[0]));
			resultBinaryDataSet.put(RESULT_BYTE_EXTENSION, splits[1]);

			// �f�R�[�h.
			resultBinaryDataSet.put(RESULT_BYTE_DATA, StringUtil.toURLDecodeBytes(splits[2]));
		}

		if (line == null) {
			return false;
		}

		return true;
	}

	/**
	 * response��stream����A�o�C�i���`����Ǎ���.
	 * 
	 * @param rb ��Buffer
	 * @return false:�������s
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	private boolean makeObjectFromStream(BufferedReader rb) throws UnsupportedEncodingException, IOException {

		ByteArrayOutputStream output = null;
		ByteArrayInputStream input = null;
		ObjectInputStream ois = null;

		try {
			String line;
			if (rb == null) {
				return false;
			}

			boolean hasData = false;
			output = new ByteArrayOutputStream();

			while ((line = rb.readLine()) != null) {
				if (line.compareTo(TOKEN_OBJECT_END) == 0) {
					break;
				}

				if (Util.NULL_STRING1.equals(line)) {
					// null�̏ꍇ
					resultObject = null;

				} else {
					// �f�R�[�h.
					byte[] binarry = StringUtil.toURLDecodeBytes(line);
					output.write(binarry);
					output.flush();

					hasData = true;
				}
			}

			if (hasData) {
				input = new ByteArrayInputStream(output.toByteArray());
				ois = new ObjectInputStream(input);
				resultObject = ois.readObject();
			}

			if (line == null) {
				return false;
			}

			return true;

		} catch (ClassNotFoundException ex) {
			throw new IOException(ex.getMessage());

		} finally {
			ResourceUtil.closeOutputStream(output);
			ResourceUtil.closeInputStream(ois);
			ResourceUtil.closeInputStream(input);
		}
	}

	/**
	 * response��stream����A�o�C�i���`����Ǎ���.
	 * 
	 * @param rb ��Buffer
	 * @return false:�������s
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	private boolean makeObjectExFromStream(BufferedReader rb) throws UnsupportedEncodingException, IOException {

		ByteArrayOutputStream output = null;
		String fileToken = "";

		try {
			String line;
			if (rb == null) {
				return false;
			}

			output = new ByteArrayOutputStream();

			while ((line = rb.readLine()) != null) {
				if (line.compareTo(TOKEN_OBJECT_EX_END) == 0) {
					break;
				}

				if (Util.NULL_STRING1.equals(line)) {
					// null�̏ꍇ
					fileToken = null;
					break;

				} else {
					// �f�R�[�h.
					fileToken = line;
					break;
				}
			}

			if (fileToken == null) {
				return false;
			}

		} finally {
			ResourceUtil.closeOutputStream(output);
		}

		if (Util.isNullOrEmpty(fileToken)) {
			return false;
		} else {

			// �t�@�C������
			// �_�E�����[�h����
			URL url = new URL(urlAddless + "/TDownloadServlet?token=" + fileToken);
			String fileName = getTempFileName("client_" + fileToken);
			File file = createTempFile(fileName);
			FileUtil.copyURLToFile(url, file);

			try {
				resultObject = FileUtil.getObject(fileName);

				FileUtil.removeFile(fileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return true;
	}

	/**
	 * �ꎞ�t�@�C�����̍쐬
	 * 
	 * @param fileToken
	 * @return �ꎞ�t�@�C����
	 */
	public static String getTempFileName(String fileToken) {
		return SystemUtil.getTemporaryDir() + File.separator + fileToken + ".obj";
	}

	/**
	 * �ꎞ�t�@�C���̍쐬
	 * 
	 * @param filename
	 * @return �ꎞ�t�@�C��
	 */
	public static File createTempFile(String filename) {
		return new File(filename);
	}

	/**
	 * ���M����p�����[�^�̒ǉ�
	 * 
	 * @param name �p�����[�^��
	 * @param param �p�����[�^
	 */
	public void addSendValues(String name, String param) {
		String[] pair = { name, param };
		sendValues.add(pair);
	}

	/**
	 * ���M����p�����[�^�̒ǉ�
	 * 
	 * @param paramMap �p�����[�^
	 */
	public void addSendValues(Map<String, Object> paramMap) {
		for (Iterator ite = paramMap.keySet().iterator(); ite.hasNext();) {
			String key = (String) ite.next();
			addSendValues(key, Util.avoidNullNT(paramMap.get(key)));
		}
	}

	/**
	 * ���M����Object�p�����[�^�̒ǉ�.<br>
	 * �V�X�e���̃f�t�H���g�L�[�Őݒ�.<br>
	 * Object�p�����[�^��p���\�b�h�Ŏ󂯎��K�v������.
	 * 
	 * @param obj Object�p�����[�^
	 */
	public void addSendObject(Object obj) {
		addSendObject(SEND_OBJ_KEY, obj);
	}

	/**
	 * ���M����Object�p�����[�^�̒ǉ�.<br>
	 * Object�p�����[�^��p���\�b�h�Ŏ󂯎��K�v������.
	 * 
	 * @param key �L�[
	 * @param obj Object�p�����[�^
	 */
	public void addSendObject(String key, Object obj) {

		ByteArrayOutputStream out = null;
		ObjectOutputStream oos = null;

		try {

			String value;

			if (obj == null) {
				value = Util.NULL_STRING1;

			} else {

				double startMemory = 0d;
				double endMemory = 0d;
				double minMemory = 1d; // ���b�Z�[�W�\���ŏ��l
				if (ClientConfig.isFlagOn("trans.show.used.request.memory")) {
					startMemory = Runtime.getRuntime().freeMemory() / 1024d / 1024d;
					try {
						minMemory = Double.parseDouble(ClientConfig
							.getProperty("trans.show.used.request.memory.min.MB"));
					} catch (Throwable e) {
						// �����Ȃ�
					}
				}

				out = new ByteArrayOutputStream();
				oos = new ObjectOutputStream(out);
				oos.writeObject(obj);
				oos.flush();

				byte[] image = out.toByteArray();

				// �I�u�W�F�N�g���o�C�i������������
				value = StringUtil.toURLEncodeString(image);

				if (ClientConfig.isFlagOn("trans.show.used.request.memory")) {
					endMemory = Runtime.getRuntime().freeMemory() / 1024d / 1024d;
				}

				if (startMemory - endMemory > minMemory) {
					// �������g�p�ʁ��PMB(�w��MIN�l)�̏ꍇ
					String num = NumberFormatUtil.formatNumber(startMemory - endMemory, 2) + "MB";
					System.out.println("More than " + num + " of request memory used.");
				}

			}

			String[] pair = { key, value };
			sendValues.add(pair);

		} catch (IOException ex) {
			throw new TRuntimeException("E00023", ex);

		} finally {
			ResourceUtil.closeOutputStream(out);
			ResourceUtil.closeOutputStream(oos);
		}
	}

	/**
	 * �������X�g�p�����[�^�̐ݒ�
	 * 
	 * @param list
	 */
	public void addSendList(List<String> list) {
		addSendValues(SEND_LIST_KEY, StringUtil.toHTMLEscapeString(list));
	}

	/**
	 * ���M����Dto�p�����[�^�̒ǉ�.<br>
	 * �V�X�e���̃f�t�H���g�L�[�Őݒ�.<br>
	 * Dto�p�����[�^��p���\�b�h�Ŏ󂯎��K�v������.
	 * 
	 * @param dto Dto�p�����[�^
	 */
	public void addSendDto(TransferBase dto) {
		List<TransferBase> tbList = sendDtoValues.get(SEND_DTO_KEY);
		if (tbList == null) {
			tbList = new LinkedList<TransferBase>();
			sendDtoValues.put(SEND_DTO_KEY, tbList);
		}

		tbList.add(dto);
	}

	/**
	 * ���M����Dto�p�����[�^�̒ǉ�.<br>
	 * Dto�p�����[�^��p���\�b�h�Ŏ󂯎��K�v������.
	 * 
	 * @param key �L�[
	 * @param dto Dto�p�����[�^
	 */
	public void addSendDto(String key, TransferBase dto) {
		List<TransferBase> tbList = sendDtoValues.get(key);
		if (tbList == null) {
			tbList = new LinkedList<TransferBase>();
			sendDtoValues.put(key, tbList);
		}

		tbList.add(dto);
	}

	/**
	 * ���M����Dto�p�����[�^���X�g�̒ǉ�.<br>
	 * �V�X�e���̃f�t�H���g�L�[�Őݒ�.<br>
	 * Dto�p�����[�^��p���\�b�h�Ŏ󂯎��K�v������.
	 * 
	 * @param list Dto�p�����[�^���X�g
	 */
	public void addSendDtoList(List<? extends TransferBase> list) {
		List<TransferBase> tbList = sendDtoValues.get(SEND_DTO_KEY);
		if (tbList == null) {
			tbList = new LinkedList<TransferBase>();
			sendDtoValues.put(SEND_DTO_KEY, tbList);
		}

		tbList.addAll(list);
	}

	/**
	 * ���M����Dto�p�����[�^���X�g�̒ǉ�.<br>
	 * Dto�p�����[�^��p���\�b�h�Ŏ󂯎��K�v������.
	 * 
	 * @param key �L�[
	 * @param list Dto�p�����[�^���X�g
	 */
	public void addSendDtoList(String key, List<? extends TransferBase> list) {
		List<TransferBase> tbList = sendDtoValues.get(key);
		if (tbList == null) {
			tbList = new LinkedList<TransferBase>();
			sendDtoValues.put(key, tbList);
		}

		tbList.addAll(list);
	}

	/**
	 * ���M����p�����[�^�̃N���A
	 */
	public void clearSendValues() {
		sendValues.clear();
		sendDtoValues.clear();
	}

	/**
	 * ����(���X�|���X)�̏��
	 * 
	 * @return ���
	 */
	public int getRespMode() {
		return this.respMode;
	}

	/**
	 * ����(���X�|���X)���V�X�e���G���[�ł��邩�ǂ���
	 * 
	 * @return true: �V�X�e���G���[
	 */
	public boolean isErrorResponse() {

		if (this.respMode == RESP_ERROR) {
			if (this.errorMap == null) {
				return false;
			}
			if (Util.isNullOrEmpty(this.errorMap.get("code"))) {
				return false;
			}

			if (this.errorMap.get("code").equals("systemerror")) {
				return true;
			}
		}

		return false;
	}

	/**
	 * ����(���X�|���X)��Map�`���ł��邩�ǂ���
	 * 
	 * @return true: Map�`��
	 */
	public boolean isMapResponse() {
		return this.respMode == RESP_MAP;
	}

	/**
	 * ����(���X�|���X)��List�`���ł��邩�ǂ���
	 * 
	 * @return true: List�`��
	 */
	public boolean isListResponse() {
		return this.respMode == RESP_LIST;
	}

	/**
	 * ����(���X�|���X)���o�C�i���`���ł��邩�ǂ���
	 * 
	 * @return true: �o�C�i���`��
	 */
	public boolean isBinaryResponse() {
		return this.respMode == RESP_BINARY;
	}

	/**
	 * ����(���X�|���X)���I�u�W�F�N�g�`���ł��邩�ǂ���
	 * 
	 * @return true: Object�`��
	 */
	public boolean isObjectResponse() {
		return this.respMode == RESP_OBJECT;
	}

	/**
	 * ����(���X�|���X)���f�[�^������Ԃł��邩�ǂ���
	 * 
	 * @return true: �f�[�^�������
	 */
	public boolean isNoDataResponse() {
		return this.respMode == RESP_NO_DATA;
	}

	/**
	 * ����(���X�|���X)�̎擾
	 * 
	 * @param key �L�[
	 * @return ����(���X�|���X)�l
	 */
	public String getResultValue(String key) {
		return resultMap.get(key);
	}

	/**
	 * key/value�`������(���X�|���X)�̎擾
	 * 
	 * @return ����(���X�|���X)�l
	 */
	public Map<String, String> getResult() {
		return resultMap;
	}

	/**
	 * boolean�`������(���X�|���X)�̎擾
	 * 
	 * @return ����(���X�|���X)boolean�l
	 */
	public boolean getResultBoolean() {
		String res = resultMap.get(RESULT_BOOLEAN);

		if (res == null) {
			throw new TRuntimeException("E00003", "Coding Miss");
		}

		return BooleanUtil.toBoolean(res);
	}

	/**
	 * ���X�g�`������(���X�|���X)�̎擾
	 * 
	 * @return ����(���X�|���X)�l
	 */
	public List<List<String>> getResultList() {
		return resultList;
	}

	/**
	 * �o�C�i���f�[�^���ʁi�V�X�e���p�j
	 * 
	 * @return �o�C�i���f�[�^�Z�b�g
	 */
	public Map getResultBinaryDataSet() {
		return resultBinaryDataSet;
	}

	/**
	 * �I�u�W�F�N�g���ʂ̎擾
	 * 
	 * @return �I�u�W�F�N�g
	 */
	public Object getResultObject() {
		return resultObject;
	}

	/**
	 * �G���[���ʂ�����ꍇ�A�G���[�R�[�h��Ԃ�.<br>
	 * �ɗ�TappletClientBase.errorHandler()�𗘗p���邱��.
	 * 
	 * @return �G���[�R�[�h
	 */
	public String getErrorCode() {
		return errorMap.get(ERR_CODE_KEY);
	}

	/**
	 * �G���[���ʂ�����ꍇ�A�G���[���b�Z�[�W��Ԃ�. <br>
	 * �ɗ�TappletClientBase.errorHandler()�𗘗p���邱��.
	 * 
	 * @return �G���[���b�Z�[�W
	 */
	public String getErrorMessage() {
		return errorMap.get(ERR_MSG_KEY);
	}

	/**
	 * �T�[�o�ʐM�̃��X�g���ʂ�Entity�̌`�Ŏ擾����.
	 * 
	 * @param clazz TransferBase���p������Entity�̃N���X��`
	 * @return ���ʂ�Entity�N���X.���ʂ������ꍇ�́Anull���Ԃ�.
	 */
	public TransferBase getResultDto(Class clazz) {
		try {
			if (getRespMode() != RESP_LIST) {
				throw new TRuntimeException("E00003", "Coding Miss");
			}

			List<List<String>> resultList_ = getResultList();

			if (resultList_.isEmpty()) {
				return null;
			}

			TransferBase base = (TransferBase) clazz.newInstance();
			base.reflectArray(resultList_.get(0));

			return base;

		} catch (InstantiationException ex) {
			throw new TRuntimeException("E00003", "Coding Miss");
		} catch (IllegalAccessException ex) {
			throw new TRuntimeException("E00003", "Coding Miss");
		}
	}

	/**
	 * �T�[�o�ʐM�̃��X�g���ʂ�Entity���X�g�̌`�Ŏ擾����.
	 * 
	 * @param clazz TransferBase���p������Entity�̃N���X��`
	 * @return ���ʂ�Entity�N���X���X�g.���ʂ������ꍇ�́A�󃊃X�g���Ԃ�.
	 */
	public List getResultDtoList(Class clazz) {
		try {
			if (getRespMode() != RESP_LIST) {
				throw new TRuntimeException("E00003", "Coding Miss");
			}

			List<List<String>> resultList_ = getResultList();
			List<TransferBase> returnList = new ArrayList<TransferBase>(resultList_.size());

			for (List<String> list : resultList_) {
				TransferBase base = (TransferBase) clazz.newInstance();
				base.reflectArray(list);

				returnList.add(base);
			}

			return returnList;

		} catch (InstantiationException ex) {
			throw new TRuntimeException("E00003", "Coding Miss");
		} catch (IllegalAccessException ex) {
			throw new TRuntimeException("E00003", "Coding Miss");
		}
	}

	/**
	 * HTTP�|�[�g�����
	 */
	@Override
	public void finalize() throws Throwable {
		super.finalize();
		tAccessHTTP.releasePort();
	}

	/**
	 * ���M
	 * 
	 * @param path ���s�p�X
	 * @throws TException ���M�G���[
	 */
	public void send(String path) throws TException {
		send(path, new ArrayList<File>(0));
	}

	/**
	 * ���M
	 * 
	 * @param path ���s�p�X
	 * @param files �t�@�C�����X�g
	 * @return true:success false:error
	 * @throws TException ���M�G���[
	 */
	public boolean send(String path, List<File> files) throws TException {

		if (Util.isNullOrEmpty(TClientLoginInfo.getSessionID())) {
			addSendValues("T_CC", TClientLoginInfo.getInstance().getCompanyCode());
			addSendValues("T_UC", TClientLoginInfo.getInstance().getUserCode());
		}

		respMode = 0;
		resultList = new LinkedList<List<String>>();
		resultMap = new TreeMap<String, String>();
		errorMap = new TreeMap<String, String>();
		resultBinaryDataSet = new TreeMap<String, Object>();
		resultObject = null;

		InputStream stream = null;

		try {

			// Dto���X�g��String�y�A��sendValues�֔z�u
			for (String key : sendDtoValues.keySet()) {

				List<TransferBase> sendDtoList = sendDtoValues.get(key);
				if (sendDtoList == null) {
					continue;
				}

				StringBuilder dtoStr = new StringBuilder();
				for (TransferBase row : sendDtoList) {
					if (row == null) {
						continue;
					}

					if (dtoStr.length() != 0) {
						dtoStr.append(StringUtil.LINESEPARATOR);
					}

					List<String> list = row.toStringArray();
					dtoStr.append(StringUtil.toHTMLEscapeString(list));
				}

				addSendValues(key, dtoStr.toString());
			}

			// ���M.
			if (files.isEmpty()) {
				stream = tAccessHTTP.send(path, sendValues, "");
			} else {
				stream = tAccessHTTP.sendFile(path, sendValues, "", files);
			}

			// �ԐM���.
			boolean stat = this.parseResponse(stream);

			if (!stat) {
				return false;
			}

			return respMode != RESP_ERROR;

		} catch (Exception e) {
			throw new TException(e, "E00037");
		} finally {
			// ���M�l�N���A
			sendValues.clear();
			sendDtoValues.clear();

			ResourceUtil.closeInputStream(stream);
		}
	}

}