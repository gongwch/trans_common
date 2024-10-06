package jp.co.ais.trans.common.server;

import java.io.*;
import java.text.*;
import java.util.*;
import java.util.Map.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;
import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.logic.system.*;

/**
 * Servlet�x�[�X�N���X.
 * 
 * @author ais-y inumazawa
 */
public abstract class TServletBase extends HttpServlet {

	/** �_�E�����[�h�t�@�C����� PDF */
	private static final int FILE_KIND_PDF = 1;

	/** �_�E�����[�h�t�@�C����� EXCEL */
	private static final int FILE_KIND_EXCEL = 2;

	/** �_�E�����[�h�t�@�C����� XML */
	private static final int FILE_KIND_XML = 3;

	/** �_�E�����[�h�t�@�C����� CSV */
	private static final int FILE_KIND_CSV = 4;

	/** �_�E�����[�h�t�@�C����� �e�L�X�g */
	private static final int FILE_KIND_TEXT = 5;

	/** �t�@�C�����[�h�Ŏ�M */
	protected static boolean RESPONSE_FILE_MODE = false;

	/** true:�A�b�v���[�h�����t�@�C���������������A�폜���s�Ȃ� */
	protected static boolean DELETE_FILE_WHEN_REQUEST_COMPLETED = false;

	/** �V�X�e���o�[�W���� */
	public static String version = "";

	static {
		try {
			RESPONSE_FILE_MODE = ServerConfig.isFlagOn("trans.response.file.mode");
			DELETE_FILE_WHEN_REQUEST_COMPLETED = ServerConfig
					.isFlagOn("trans.delete.upload.file.when.request.completed");
		} catch (Throwable e) {
			// �����Ȃ�
		}
	}

	/**
	 * ����HTTP�Z�b�V�����擾.<br>
	 * �Z�b�V�������J�n����Ă��Ȃ��ꍇ�Anull���Ԃ�.
	 * 
	 * @param req ���N�G�X�g
	 * @return �Z�b�V����
	 */
	protected HttpSession getSession(HttpServletRequest req) {
		return req.getSession(false);
	}

	/**
	 * Get method ����. <br>
	 * 
	 * @param req
	 * @param resp
	 */
	@Override
	protected final void doGet(HttpServletRequest req, HttpServletResponse resp) {
		this.doPost(req, resp);
	}

	/**
	 * Post method ����. <br>
	 * method=post�ő��M���ꂽ�f�[�^����������B
	 * 
	 * @param req
	 * @param resp
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

		this.doPost(req, resp, ServerConfig.isSessionMode());
	}

	/**
	 * Post method ����. <br>
	 * method=post�ő��M���ꂽ�f�[�^����������B
	 * 
	 * @param req
	 * @param resp
	 * @param isSessionAuth �Z�b�V�����F�؂��s�����ǂ���
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp, boolean isSessionAuth) {
		long stime = System.currentTimeMillis();
		ServerLogger.info("**doPost in** : " + this.getServletName());

		try {
			// �F�؏�Ԃ��m�F����.

			// �p�����Ă���Z�b�V�����擾.
			HttpSession httpSession = getSession(req);

			// log �o��
			ServerLogger.debug(req.getServletPath() + " " + (httpSession == null ? "null" : httpSession.getId()));

			// �Z�b�V�����F��
			if (isSessionAuth && !authenticateSession(httpSession)) {

				// �F�؎��s���̓G���[�o�͂ŏI��.�������󂯕t���Ȃ�
				this.dispatchSystemError(req, resp, getMessage(req, "W01001"));
				return;
			}

			// �A�b�v���[�h�t�@�C���̏���.
			if (ServletFileUpload.isMultipartContent(req)) {

				this.handleUploadFile(req);
				this.doMethodPostMultipart(req, resp);
			} else {

				// �������\�b�h���Ăяo��.
				this.doMethodPost(req, resp);
			}

		} catch (OutOfMemoryError e) {
			try {
				ServerErrorHandler.handledException(e);

				dispatchError(req, resp, "E00036");
			} catch (Exception e1) {
				ServerErrorHandler.handledException(e1);
			}

		} catch (Throwable e) {
			try {
				// �n���h���[�̌Ăяo��
				ServerErrorHandler.handledException(e);

				// ServletException�Ō��̃G���[��TException�������ꍇ
				if (e instanceof ServletException) {
					Throwable causeEx = e.getCause();
					if (causeEx != null && causeEx instanceof TException) {
						dispatchError(req, resp, (TException) causeEx);
						return;
					}
				}

				// �N���C�A���g�ɃG���[�𑗐M
				dispatchSystemError(req, resp, getMessage(req, "S00001"));

			} catch (Exception e1) {
				ServerErrorHandler.handledException(e);
			}

		} finally {

			if (ServletFileUpload.isMultipartContent(req)) {
				// �A�b�v���[�h�t�@�C��(�e���|����)���폜
				closeUploadFile(req);
			}

			long etime = System.currentTimeMillis();
			ServerLogger.info("**doPost out** : " + this.getServletName() + " - " + (etime - stime));
		}
	}

	/**
	 * �Z�b�V�����F�؂��s��.
	 * 
	 * @param httpSession �Z�b�V����
	 * @return �F�؂Ɏ��s�����ꍇfalse
	 */
	private boolean authenticateSession(HttpSession httpSession) {
		if (httpSession == null) {
			return false;
		}

		// �F�؏�Ԏ擾
		TServerCertification certification = (TServerCertification) httpSession
				.getAttribute(TServerEnv.SYS_PREFIX + "certificationification"); // �F�؃I�u�W�F�N�g���F�؏�ԂɂȂ�.

		boolean isAuth = certification != null && certification.isCertified();

		// ���[�U���̕ێ����
		TUserInfo info = (TUserInfo) httpSession.getAttribute(TServerEnv.SYS_PREFIX + "userinfo");

		isAuth &= info != null && !Util.isNullOrEmpty(info.getUserCode());

		return isAuth;
	}

	/**
	 * �I�[�o�[���C�h�̂��߂́APost����.
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 * @throws ServletException
	 * @throws ParseException
	 */
	protected abstract void doMethodPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, ParseException;

	/**
	 * �I�[�o�[���C�h�̂��߂́Amultipart Post����.
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 * @throws ServletException
	 */
	@SuppressWarnings("unused")
	protected void doMethodPostMultipart(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		// Orverride������ׁA������������
	}

	/**
	 * Exception�̎�ނ𔻒肵�ď������s��
	 * 
	 * @param ex �Ώ�Exception
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 * @throws ServletException
	 */
	protected final void handledException(Exception ex, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException {

		if (!(ex instanceof TException)) {
			ServerErrorHandler.handledException(ex);
		}

		if (ex instanceof ServletException) {
			throw (ServletException) ex;

		} else {
			dispatchError(req, resp, ex);
		}
	}

	/**
	 * �A�b�v���[�h�t�@�C������ (1�t�@�C���̂ݑΉ�)
	 * 
	 * @param req ���N�G�X�g
	 * @return ����
	 * @throws Exception
	 */
	private boolean handleUploadFile(HttpServletRequest req) throws Exception {

		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Set factory constraints
		factory.setSizeThreshold(1024 * 64); // ���̃T�C�Y�ȉ��Ȃ�on memory�Ńt�@�C��������.

		String uploadPath = getServletContext().getRealPath(TServerEnv.TMP_DIR.toString());
		ServerLogger.debug("upload path : " + uploadPath);

		factory.setRepository(new File(uploadPath));

		ServletFileUpload upload = new ServletFileUpload(factory);

		upload.setSizeMax(-1); // �ő�T�C�Y -1:��������
		// upload.setHeaderEncoding("UTF-8"); // �w�b�_�̕����G���R�[�f�B���O

		Map<String, List<FileItem>> map = upload.parseParameterMap(req);
		// System.out.println(map);

		Map<String, Object> para = new HashMap<String, Object>();
		List<String> fileNames = new LinkedList<String>();
		List<File> files = new LinkedList<File>();

		// // �A�b�v���[�h���ꂽ�t�@�C������FileItem�I�u�W�F�N�g�̃��X�g�Ƃ��Ď擾
		// List<FileItem> objLst = upload.parseRequest(req);

		for (Entry<String, List<FileItem>> entry : map.entrySet()) {
			if (entry.getValue() != null) {
				// �p�����[�^
				for (FileItem i : entry.getValue()) {
					para.put(i.getFieldName(), i.getString());
				}
			}
		}
		// // �p�����[�^
		// for (FileItem objFil : objLst) {
		//
		// if (objFil.isFormField()) {
		//
		// // parameter.
		// para.put(objFil.getFieldName(), objFil.getString());
		//
		// }
		// }
		req.setAttribute(TServerEnv.SYS_PREFIX + "upload.parameters", para); // �p�����[�^

		// ���X�g���珇�Ƀt�@�C���f�[�^�����o���A�u/WEB-INF/data/���̃t�@�C�����v�̌`���ŃA�b�v���[�h�t�@�C����ۑ�
		TUserInfo info = refTServerUserInfo(req);
		String preFix = "_" + info.getCompanyCode() + "-" + info.getUserCode();

		for (Entry<String, List<FileItem>> entry : map.entrySet()) {
			if (entry.getValue() != null) {
				// �p�����[�^
				for (FileItem i : entry.getValue()) {

					if (!i.isFormField()) {
						// �e���|�����t�@�C���쐬.
						String fileName = StringUtil.unescapeHtml(i.getFieldName());
						String ext = "." + ResourceUtil.getExtension(fileName); // �g���q

						File temp = File.createTempFile(fileName.replace(ext, "") + preFix, ext);
						temp.deleteOnExit();

						// �t�@�C�����o��.
						i.write(temp);

						fileNames.add(fileName); // �t�@�C����
						files.add(temp); // �t�@�C��
					}
				}
			}
		}
		req.setAttribute(TServerEnv.SYS_PREFIX + "upload.name", fileNames); // ���t�@�C����
		req.setAttribute(TServerEnv.SYS_PREFIX + "upload.file", files); // �A�b�v���[�h�t�@�C��

		return true;
	}

	/**
	 * upload ���t�@�C�����擾.
	 * 
	 * @param req ���N�G�X�g
	 * @return �t�@�C����
	 */
	protected final String getUploadFileName(HttpServletRequest req) {

		List<String> list = getUploadFileNames(req);

		return list.isEmpty() ? null : list.get(0);
	}

	/**
	 * upload ���t�@�C�������X�g�擾.
	 * 
	 * @param req ���N�G�X�g
	 * @return �t�@�C�������X�g
	 */
	protected final List<String> getUploadFileNames(HttpServletRequest req) {

		return (List<String>) req.getAttribute(TServerEnv.SYS_PREFIX + "upload.name");
	}

	/**
	 * upload�t�@�C���擾.
	 * 
	 * @param req ���N�G�X�g
	 * @return �t�@�C��
	 */
	protected final File getUploadFile(HttpServletRequest req) {

		List<File> list = getUploadFiles(req);

		return list.isEmpty() ? null : list.get(0);
	}

	/**
	 * upload�t�@�C���擾.
	 * 
	 * @param req ���N�G�X�g
	 * @return �t�@�C�����X�g
	 */
	protected final List<File> getUploadFiles(HttpServletRequest req) {

		return (List<File>) req.getAttribute(TServerEnv.SYS_PREFIX + "upload.file");
	}

	/**
	 * upload �p�����[�^�擾.
	 * 
	 * @param req ���N�G�X�g
	 * @param key �p�����[�^�L�[
	 * @return �p�����[�^
	 */
	protected final String getUploadParameter(HttpServletRequest req, String key) {

		Map para = (Map) req.getAttribute(TServerEnv.SYS_PREFIX + "upload.parameters");
		if (para == null) {
			return null;
		}

		return (String) para.get(key);
	}

	/**
	 * upload �p�����[�^�擾.
	 * 
	 * @param req ���N�G�X�g
	 * @param key �p�����[�^�L�[
	 * @return �p�����[�^
	 */
	protected final Object getUploadObject(HttpServletRequest req, String key) {
		String objString = getUploadParameter(req, key);
		return toObjectFromBinary(objString);
	}

	/**
	 * �I�u�W�F�N�g�o�C�i���������I�u�W�F�N�g�ɕϊ�
	 * 
	 * @param objString �I�u�W�F�N�g�o�C�i������
	 * @return �I�u�W�F�N�g
	 */
	protected Object toObjectFromBinary(String objString) {

		ByteArrayInputStream input = null;
		ObjectInputStream ois = null;

		try {
			if (objString == null || Util.isNullString(objString)) {
				return null;
			}

			// �f�R�[�h.
			byte[] binarry = StringUtil.toURLDecodeBytes(objString);

			input = new ByteArrayInputStream(binarry);
			ois = new ObjectInputStream(input);

			return ois.readObject();

		} catch (UnsupportedEncodingException ex) {
			throw new TRuntimeException("E00023", ex);

		} catch (IOException ex) {
			throw new TRuntimeException("E00023", ex);

		} catch (ClassNotFoundException ex) {
			throw new TRuntimeException("E00023", ex);

		} finally {
			ResourceUtil.closeInputStream(ois);
			ResourceUtil.closeInputStream(input);
		}
	}

	/**
	 * upload�t�@�C���j��.
	 * 
	 * @param req ���N�G�X�g
	 */
	protected final void closeUploadFile(HttpServletRequest req) {

		if (DELETE_FILE_WHEN_REQUEST_COMPLETED) {
			List<File> files = (List<File>) req.getAttribute(TServerEnv.SYS_PREFIX + "upload.file");

			if (files != null) {
				for (File fil : files) {
					if (fil != null) {
						try {
							fil.delete();
						} catch (Throwable ex) {
							ServerErrorHandler.handledException(ex);
						}
					}
				}
			}
		}

		req.removeAttribute(TServerEnv.SYS_PREFIX + "upload.file");
		req.removeAttribute(TServerEnv.SYS_PREFIX + "upload.name");
	}

	/**
	 * �����񃊃X�g�p�����[�^�擾.<br>
	 * �p�����[�^�������ꍇ�A��̃��X�g���Ԃ�.
	 * 
	 * @param req ���N�G�X�g
	 * @return �p�����[�^���X�g
	 */
	protected final List<String> getListParameter(HttpServletRequest req) {
		String listString = req.getParameter(TRequestBase.SEND_LIST_KEY);
		return Util.isNullOrEmpty(listString) ? new ArrayList<String>(0)
				: StringUtil.toListFromHTMLEscapeString(listString);
	}

	/**
	 * Dto�p�����[�^���X�g�擾. <br>
	 * �p�����[�^�������ꍇ�A��̃��X�g���Ԃ�.
	 * 
	 * @param req ���N�G�X�g
	 * @param clazz �Ώ�Dto�N���XClass
	 * @return Dto�p�����[�^���X�g
	 */
	protected final List<? extends TransferBase> getDtoListParameter(HttpServletRequest req, Class clazz) {
		return getDtoListParameter(req, clazz, TRequestBase.SEND_DTO_KEY);
	}

	/**
	 * �w��L�[�ɑΉ�����Dto�p�����[�^�擾.<br>
	 * �p�����[�^�������ꍇ�Anull���Ԃ�.
	 * 
	 * @param req ���N�G�X�g
	 * @param clazz �Ώ�Dto�N���XClass
	 * @param key �L�[
	 * @return Dto�p�����[�^
	 */
	protected final TransferBase getDtoParameter(HttpServletRequest req, Class clazz, String key) {
		List<TransferBase> dtoList = getDtoListParameter(req, clazz, key);
		return dtoList.isEmpty() ? null : dtoList.get(0);
	}

	/**
	 * Dto�p�����[�^�擾.<br>
	 * �p�����[�^�������ꍇ�Anull���Ԃ�.
	 * 
	 * @param req ���N�G�X�g
	 * @param clazz �Ώ�Dto�N���XClass
	 * @return Dto�p�����[�^
	 */
	protected final TransferBase getDtoParameter(HttpServletRequest req, Class clazz) {
		List<? extends TransferBase> dtoList = getDtoListParameter(req, clazz);
		return dtoList.isEmpty() ? null : dtoList.get(0);
	}

	/**
	 * �w��L�[�ɑΉ�����Dto�p�����[�^���X�g�擾. <br>
	 * �p�����[�^�������ꍇ�A��̃��X�g���Ԃ�.
	 * 
	 * @param req ���N�G�X�g
	 * @param clazz �Ώ�Dto�N���XClass
	 * @param key �L�[
	 * @return Dto�p�����[�^���X�g
	 */
	protected final List<TransferBase> getDtoListParameter(HttpServletRequest req, Class clazz, String key) {
		try {
			String dtoString = req.getParameter(key);

			if (Util.isNullOrEmpty(dtoString)) {
				return new ArrayList<TransferBase>(0);
			}

			String[] values = StringUtil.split(dtoString, StringUtil.LINESEPARATOR);

			List<TransferBase> list = new ArrayList<TransferBase>(values.length);

			for (String value : values) {
				List<String> dtoList = StringUtil.toListFromHTMLEscapeString(value);
				TransferBase dto = (TransferBase) clazz.newInstance();
				dto.reflectArray(dtoList);
				list.add(dto);
			}

			return list;

		} catch (InstantiationException ex) {
			throw new TRuntimeException("E00003", "Coding Miss");
		} catch (IllegalAccessException ex) {
			throw new TRuntimeException("E00003", "Coding Miss");
		}
	}

	/**
	 * �w��L�[�ɑΉ�����Object�p�����[�^�擾.<br>
	 * �p�����[�^�������ꍇ�Anull���Ԃ�.
	 * 
	 * @param req ���N�G�X�g
	 * @param key �L�[
	 * @return Object�p�����[�^
	 */
	protected final Object getObjectParameter(HttpServletRequest req, String key) {
		String objString = req.getParameter(key);
		return toObjectFromBinary(objString);

	}

	/**
	 * Object�p�����[�^�擾.<br>
	 * �p�����[�^�������ꍇ�Anull���Ԃ�.
	 * 
	 * @param req ���N�G�X�g
	 * @return Object�p�����[�^
	 */
	protected final Object getObjectParameter(HttpServletRequest req) {
		return getObjectParameter(req, TRequestBase.SEND_OBJ_KEY);
	}

	/**
	 * �G���[��ʒm����.
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 * @param ex �ΏۃG���[
	 */
	protected final void dispatchError(HttpServletRequest req, HttpServletResponse resp, Exception ex) {

		if (ex instanceof TException) {
			TException tex = (TException) ex;
			this.dispatchError(req, resp, tex.getMessageID(), tex.getMessageArgs());

		} else if (ex instanceof TRuntimeException) {
			TRuntimeException tex = (TRuntimeException) ex;
			this.dispatchError(req, resp, tex.getMessageID(), tex.getMessageArgs());

		} else {
			ServerErrorHandler.handledException(ex);

			this.dispatchError(req, resp, "E00009");
		}
	}

	/**
	 * �G���[��ʒm����.
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 * @param messageID ���b�Z�[�WID
	 * @param objects �P��ID
	 */
	protected final void dispatchError(HttpServletRequest req, HttpServletResponse resp, String messageID,
			Object... objects) {

		PrintWriter bw = null;
		try {
			// ���b�Z�[�W��ϊ�.
			String message = this.getMessage(req, messageID, objects);

			// error�̃��X�|���X.
			bw = resp.getWriter();

			bw.println(TRequestBase.TOKEN_RESPONSE);
			bw.println(TRequestBase.TOKEN_ERROR);

			bw.println(StringUtil
					.toDelimitString(new String[] { TRequestBase.ERR_CODE_KEY, StringUtil.escapeHtml(messageID) }));
			bw.println(StringUtil.toDelimitString(
					new String[] { TRequestBase.ERR_MSG_KEY, StringUtil.escapeHtml(Util.avoidNullNT(message)) }));

			bw.println(TRequestBase.TOKEN_ERROR_END);
			bw.println(TRequestBase.TOKEN_RESPONSE_END);
			bw.flush();
			bw.close();
		} catch (IOException ex) {
			throw new TRuntimeException("E00023", ex);
		} finally {
			if (bw != null) {
				bw.close();
			}
		}
	}

	/**
	 * �G���[��JSP�֑��M����.
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 * @param messageID ���b�Z�[�WID
	 * @param objects �P��ID
	 */
	protected final void dispatchJspErrorForLink(HttpServletRequest req, HttpServletResponse resp, String messageID,
			Object... objects) {
		try {
			req.setAttribute("error.id", messageID);
			req.setAttribute("error.message", this.getMessage(req, messageID, objects)); // ���b�Z�[�W��ϊ�.
			req.setAttribute("error.button", this.getWord(req, "C02374")); // close����.
			req.setAttribute("error.dispButton", Boolean.FALSE);
			req.setAttribute("error.loginLink", Boolean.TRUE);
			req.setAttribute("error.goBackMessage", "back to login"); // ���O�C����ʂ֖߂�

			String errorJSP = TServerEnv.JSP_PATH.toString() + TServerEnv.ERR_JSP.toString();
			super.getServletContext().getRequestDispatcher(errorJSP).forward(req, resp);
		} catch (Exception ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * �V�X�e���G���[��]������.
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 * @param message �G���[���b�Z�[�W
	 */
	@SuppressWarnings("unused")
	private void dispatchSystemError(HttpServletRequest req, HttpServletResponse resp, String message) {

		PrintWriter bw = null;
		try {
			// error�̃��X�|���X.
			bw = resp.getWriter();

			bw.println(TRequestBase.TOKEN_RESPONSE);
			bw.println(TRequestBase.TOKEN_ERROR);
			bw.println(StringUtil.toDelimitString(new String[] { TRequestBase.ERR_CODE_KEY, "systemerror" }));
			bw.println(StringUtil.toDelimitString(
					new String[] { TRequestBase.ERR_MSG_KEY, StringUtil.escapeHtml(Util.avoidNullNT(message)) }));
			bw.println(TRequestBase.TOKEN_ERROR_END);
			bw.println(TRequestBase.TOKEN_RESPONSE_END);
			bw.flush();
			bw.close();
		} catch (IOException ex) {
			throw new TRuntimeException("E00023", ex);
		} finally {
			if (bw != null) {
				bw.close();
			}
		}
	}

	/**
	 * Map�����ʂƂ��đ��M����.
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 * @param map ����Map
	 */
	@SuppressWarnings("unused")
	protected final void dispatchResultMap(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> map) {

		PrintWriter bw = null;
		try {
			// Map�`���̃��X�|���X.
			bw = resp.getWriter();

			bw.println(TRequestBase.TOKEN_RESPONSE);
			bw.println(TRequestBase.TOKEN_KEY_VALUE_LIST);

			for (Iterator i = map.keySet().iterator(); i.hasNext();) {

				String key = (String) i.next();
				if (key != null && map.get(key) != null) {
					String strEnc;
					if (map.get(key) instanceof Double) {
						strEnc = StringUtil.escapeHtml(DecimalUtil.doubleToString((Double) map.get(key)));
					} else {
						strEnc = StringUtil.escapeHtml(Util.avoidNullNT(map.get(key)));
					}
					bw.println(StringUtil.toDelimitString(new String[] { key, strEnc }));
				}
			}

			bw.println(TRequestBase.TOKEN_KEY_VALUE_LIST_END);
			bw.println(TRequestBase.TOKEN_RESPONSE_END);

			bw.flush();
			bw.close();
		} catch (IOException ex) {
			throw new TRuntimeException("E00023", ex);
		} finally {
			if (bw != null) {
				bw.close();
			}
		}
	}

	/**
	 * boolean�l�����ʂƂ��đ��M����.
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 * @param result boolean����
	 */
	protected final void dispatchResult(HttpServletRequest req, HttpServletResponse resp, boolean result) {

		this.dispatchResult(req, resp, TRequestBase.RESULT_BOOLEAN, BooleanUtil.toString(result));
	}

	/**
	 * �L�[�ƒl�����ʂƂ��đ��M����.
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 * @param key �L�[
	 * @param value �l
	 */
	protected final void dispatchResult(HttpServletRequest req, HttpServletResponse resp, String key, Object value) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put(key, value);
		this.dispatchResultMap(req, resp, map);
	}

	/**
	 * Entity�����ʂƂ��đ��M����
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 * @param dto �Ώ�Entity
	 */
	@SuppressWarnings("unused")
	protected final void dispatchResultDto(HttpServletRequest req, HttpServletResponse resp, TransferBase dto) {

		PrintWriter bw = null;
		try {
			// Map�`���̃��X�|���X.
			bw = resp.getWriter();

			bw.println(TRequestBase.TOKEN_RESPONSE);
			bw.println(TRequestBase.TOKEN_RECORD_LIST);

			if (dto != null) {
				List<String> columns = dto.toStringArray();
				for (Iterator j = columns.iterator(); j.hasNext();) {
					String strEnc = StringUtil.escapeHtml(Util.avoidNullNT(j.next()));
					bw.print(strEnc);
					if (j.hasNext()) {
						bw.print(StringUtil.DELIMITER);
					}
				}
				bw.println();
			}

			bw.println(TRequestBase.TOKEN_RECORD_LIST_END);
			bw.println(TRequestBase.TOKEN_RESPONSE_END);

			bw.flush();

		} catch (IOException ex) {
			throw new TRuntimeException("E00023", ex);
		} finally {
			ResourceUtil.closePrintWriter(bw);
		}
	}

	/**
	 * Entity�̃��X�g�����ʂƂ��đ��M����
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 * @param list Entity���X�g
	 */
	@SuppressWarnings("unused")
	protected final void dispatchResultDtoList(HttpServletRequest req, HttpServletResponse resp,
			List<TransferBase> list) {

		PrintWriter bw = null;
		try {
			// Map�`���̃��X�|���X.
			bw = resp.getWriter();

			bw.println(TRequestBase.TOKEN_RESPONSE);
			bw.println(TRequestBase.TOKEN_RECORD_LIST);

			for (TransferBase row : list) {

				if (row == null) {
					continue;
				}

				List<String> columns = row.toStringArray();
				for (Iterator j = columns.iterator(); j.hasNext();) {
					String strEnc = StringUtil.escapeHtml(Util.avoidNullNT(j.next()));
					bw.print(strEnc);
					if (j.hasNext()) {
						bw.print(StringUtil.DELIMITER);
					}
				}
				bw.println();
			}

			bw.println(TRequestBase.TOKEN_RECORD_LIST_END);
			bw.println(TRequestBase.TOKEN_RESPONSE_END);

			bw.flush();

		} catch (IOException ex) {
			throw new TRuntimeException("E00023", ex);
		} finally {
			ResourceUtil.closePrintWriter(bw);
		}
	}

	/**
	 * dispathResultDtoList�𗘗p���邱��. <br>
	 * <s>���X�g-Entity�`���̌��ʂ𑗐M����.</s>
	 * 
	 * @deprecated dispathResultDtoList�𗘗p���邱��.
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 * @param list �Ώۃ��X�g
	 */
	@SuppressWarnings("unused")
	protected final void dispatchResultListObject(HttpServletRequest req, HttpServletResponse resp, List<Object> list) {

		PrintWriter bw = null;
		try {
			// Map�`���̃��X�|���X.
			bw = resp.getWriter();

			bw.println(TRequestBase.TOKEN_RESPONSE);
			bw.println(TRequestBase.TOKEN_RECORD_LIST);

			for (Object row : list) {

				if (row == null) {
					continue;
				}

				if (row instanceof TInterfaceHasToObjectArray) {
					List<Object> columns = ((TInterfaceHasToObjectArray) row).toObjectArray();
					for (Iterator j = columns.iterator(); j.hasNext();) {
						String strEnc = StringUtil.escapeHtml(Util.avoidNullNT(j.next()));
						bw.print(strEnc);
						if (j.hasNext()) {
							bw.print(StringUtil.DELIMITER);
						}
					}
					bw.println();
				}
			}

			bw.println(TRequestBase.TOKEN_RECORD_LIST_END);
			bw.println(TRequestBase.TOKEN_RESPONSE_END);

			bw.flush();

		} catch (IOException ex) {
			throw new TRuntimeException("E00023", ex);
		} finally {
			ResourceUtil.closePrintWriter(bw);
		}
	}

	/**
	 * ���X�g-���X�g�`���̌��ʂ𑗐M����.
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 * @param list �Ώۃ��X�g
	 */
	@SuppressWarnings("unused")
	protected final void dispatchResultList(HttpServletRequest req, HttpServletResponse resp, List<List<Object>> list) {

		PrintWriter bw = null;
		try {
			// Map�`���̃��X�|���X.
			bw = resp.getWriter();

			bw.println(TRequestBase.TOKEN_RESPONSE);
			bw.println(TRequestBase.TOKEN_RECORD_LIST);

			for (List columns : list) {

				if (columns == null) {
					continue;
				}

				for (Iterator j = columns.iterator(); j.hasNext();) {
					String strEnc = StringUtil.escapeHtml(Util.avoidNullNT(j.next()));
					bw.print(strEnc);
					if (j.hasNext()) {
						bw.print(StringUtil.DELIMITER);
					}
				}
				bw.println();
			}

			bw.println(TRequestBase.TOKEN_RECORD_LIST_END);
			bw.println(TRequestBase.TOKEN_RESPONSE_END);

			bw.flush();

		} catch (IOException ex) {
			throw new TRuntimeException("E00023", ex);
		} finally {
			ResourceUtil.closePrintWriter(bw);
		}
	}

	/**
	 * ���ʂƂ���success�𑗐M����
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 */
	@SuppressWarnings("unused")
	protected final void dispatchResultSuccess(HttpServletRequest req, HttpServletResponse resp) {

		PrintWriter bw = null;
		try {
			// Map�`���̃��X�|���X.
			bw = resp.getWriter();

			bw.println(TRequestBase.TOKEN_RESPONSE);
			bw.println(TRequestBase.TOKEN_SUCCESS);
			bw.println(TRequestBase.TOKEN_RESPONSE_END);

			bw.flush();

		} catch (IOException ex) {
			throw new TRuntimeException("E00023", ex);
		} finally {
			ResourceUtil.closePrintWriter(bw);
		}
	}

	/**
	 * output stream �Ƀo�C�i���f�[�^�𑗐M����. <br>
	 * �t�@�C���𕶎��񉻂��Č��ʂƂ��đ��M
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 * @param fileKind �t�@�C�����
	 * @param fileName �t�@�C������
	 * @param bytes �Ώۃo�C�i��
	 */
	@SuppressWarnings("unused")
	private void dispatchResultBinary(HttpServletRequest req, HttpServletResponse resp, int fileKind, String fileName,
			byte[] bytes) {

		PrintWriter bw = null;

		try {
			byte[] zipBytes = ResourceUtil.zipBinary(fileName, bytes);

			// Map�`���̃��X�|���X.
			bw = resp.getWriter();

			bw.println(TRequestBase.TOKEN_RESPONSE);
			bw.println(TRequestBase.TOKEN_BINARY);

			String encStr = StringUtil.toURLEncodeString(zipBytes);

			String extension = "";

			switch (fileKind) {
			case FILE_KIND_PDF:
				extension = "pdf";
				break;
			case FILE_KIND_EXCEL:
				extension = "xls";
				break;
			case FILE_KIND_XML:
				extension = "xml";
				break;
			case FILE_KIND_CSV:
				extension = "csv";
				break;
			case FILE_KIND_TEXT:
				extension = "txt";
				break;
			default:
				throw new TRuntimeException("E00003", "file kind");
			}

			// �t�@�C�������g���q�t���Ŏw�肳��Ă���ꍇ(����)
			String[] fileNameArray = StringUtil.split(fileName, ".");
			if (fileNameArray.length != 1) {
				fileName = fileNameArray[0];
				extension = fileNameArray[1];
			}

			ServerLogger.debug("binary dispatch:" + fileName + ", " + extension + ", " + encStr.getBytes().length);

			String[] array = new String[] { StringUtil.escapeHtml(fileName), extension, encStr };
			bw.println(StringUtil.toDelimitString(array));

			bw.println(TRequestBase.TOKEN_BINARY_END);
			bw.println(TRequestBase.TOKEN_RESPONSE_END);

			bw.flush();

		} catch (IOException ex) {
			throw new TRuntimeException("E00023", ex);
		} finally {
			ResourceUtil.closePrintWriter(bw);
		}
	}

	/**
	 * PDF�t�@�C���𑗐M�i�_�E�����[�h�j����.
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 * @param fileName �t�@�C����(�g���q����)
	 * @param image �o�C�i��
	 */
	protected final void dispatchPDF(HttpServletRequest req, HttpServletResponse resp, String fileName, byte[] image) {

		this.dispatchResultBinary(req, resp, TServletBase.FILE_KIND_PDF, fileName, image);
	}

	/**
	 * �G�N�Z���t�@�C���𑗐M�i�_�E�����[�h�j����.
	 * 
	 * @param req ���N�G�X�g
	 * @param res ���X�|���X
	 * @param fileName �t�@�C����(�g���q����)
	 * @param image �o�C�i��
	 */
	protected final void dispatchExcel(HttpServletRequest req, HttpServletResponse res, String fileName, byte[] image) {

		this.dispatchResultBinary(req, res, TServletBase.FILE_KIND_EXCEL, fileName, image);
	}

	/**
	 * CSV�t�@�C���𑗐M�i�_�E�����[�h�j����.
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 * @param fileName �t�@�C����(�g���q����)
	 * @param image �o�C�i��
	 */
	protected final void dispatchCSV(HttpServletRequest req, HttpServletResponse resp, String fileName, byte[] image) {

		this.dispatchResultBinary(req, resp, TServletBase.FILE_KIND_CSV, fileName, image);
	}

	/**
	 * �e�L�X�g�t�@�C���𑗐M�i�_�E�����[�h�j����.
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 * @param fileName �g���q����(.txt�Œ�)
	 * @param text ���MTEXT
	 */
	protected final void dispatchTextPlain(HttpServletRequest req, HttpServletResponse resp, String fileName,
			String text) {

		this.dispatchTextPlain(req, resp, fileName, text, null);
	}

	/**
	 * �e�L�X�g�t�@�C���𑗐M�i�_�E�����[�h�j����.<br>
	 * �t�@�C���̓��e�̕����R�[�h���w�蕶���Z�b�g�ɕϊ�����B
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 * @param fileName �g���q����(.txt�Œ�)
	 * @param text ���MTEXT
	 * @param encode encode�����Z�b�g��
	 */
	protected final void dispatchTextPlain(HttpServletRequest req, HttpServletResponse resp, String fileName,
			String text, String encode) {

		try {
			byte[] textBytes = Util.isNullOrEmpty(encode) ? text.getBytes() : text.getBytes(encode);

			this.dispatchResultBinary(req, resp, TServletBase.FILE_KIND_TEXT, fileName, textBytes);

		} catch (UnsupportedEncodingException ex) {
			throw new TRuntimeException("E00009", ex);
		}
	}

	/**
	 * CSV�t�@�C���𑗐M�i�_�E�����[�h�j����.
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 * @param fileName �g���q����(.csv�Œ�)
	 * @param text ���MTEXT
	 */
	protected final void dispatchTextCSV(HttpServletRequest req, HttpServletResponse resp, String fileName,
			String text) {

		byte textBytes[] = text.getBytes();
		this.dispatchResultBinary(req, resp, TServletBase.FILE_KIND_CSV, fileName, textBytes);
	}

	/**
	 * �I�u�W�F�N�g���o�C�i���f�[�^�����đ��M����. <br>
	 * �I�u�W�F�N�g���� : Serializable��Object�ł��邱��
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 * @param obj �ΏۃI�u�W�F�N�g
	 */
	@SuppressWarnings("unused")
	protected final void dispatchResultObject(HttpServletRequest req, HttpServletResponse resp, Object obj) {

		PrintWriter bw = null;
		ByteArrayOutputStream out = null;
		ObjectOutputStream oos = null;
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;

		try {
			// Map�`���̃��X�|���X.
			bw = resp.getWriter();

			if (obj != null && !(obj instanceof Throwable)) {
				System.out.println("it is Throwable.");
			}

			if (obj != null && !(obj instanceof Throwable) && RESPONSE_FILE_MODE) {

				String uid = createTempFileUID();
				String fileName = getTempFileName(uid);

				File file = createTempFile(fileName);
				fos = new FileOutputStream(file, false);
				bos = new BufferedOutputStream(fos, 8192);
				oos = new ObjectOutputStream(bos);
				oos.writeObject(obj);
				oos.flush();
				bos.flush();
				fos.flush();

				oos.close();
				bos.close();
				fos.close();

				ServerLogger.debug("object ex dispatch:" + uid + ", at " + fileName);

				bw.println(TRequestBase.TOKEN_RESPONSE);
				bw.println(TRequestBase.TOKEN_OBJECT_EX);
				bw.println(uid);
				bw.println(TRequestBase.TOKEN_OBJECT_EX_END);
				bw.println(TRequestBase.TOKEN_RESPONSE_END);

				bw.flush();

			} else {

				out = new ByteArrayOutputStream();
				oos = new ObjectOutputStream(out);
				oos.writeObject(obj);
				oos.flush();

				byte[] image = out.toByteArray();

				bw.println(TRequestBase.TOKEN_RESPONSE);
				bw.println(TRequestBase.TOKEN_OBJECT);

				if (obj == null) {
					// null�̏ꍇ
					bw.println(Util.NULL_STRING1);

				} else {

					ServerLogger.debug("object dispatch:" + obj.getClass().getCanonicalName() + ", " + image.length);

					int base = 1000;

					for (int i = 0;; i++) {

						// �����Ώ۔͈�
						int from = i * base;
						int to = from + base - 1;

						// List.subList(from, to)�̒��ӎ����Ffrom:�J�n�ԍ��Ato:�I���ԍ��̎��̔ԍ�
						to = Math.min(to, image.length - 1) + 1;

						byte[] data = Arrays.copyOfRange(image, from, to);

						// �I�u�W�F�N�g���o�C�i������������
						String encStr = StringUtil.toURLEncodeString(data);

						bw.println(encStr);
						bw.flush();

						// ���[�v�I������
						if ((from + base) >= image.length) {
							break;
						}
					}
				}

				bw.println(TRequestBase.TOKEN_OBJECT_END);
				bw.println(TRequestBase.TOKEN_RESPONSE_END);

				bw.flush();
			}

		} catch (Exception ex) {
			resp.resetBuffer();
			System.out.println("----------error----------------");
			ex.printStackTrace();
			throw new TRuntimeException("E00036", ex);

		} finally {
			ResourceUtil.closeOutputStream(out);
			ResourceUtil.closeOutputStream(oos);
			ResourceUtil.closeOutputStream(bos);
			ResourceUtil.closeOutputStream(fos);

			ResourceUtil.closePrintWriter(bw);
		}
	}

	/**
	 * @return �ꎞ�t�@�C����UID�쐬
	 */
	protected String createTempFileUID() {
		UUID u1 = UUID.randomUUID();
		String fileToken = u1.toString();
		File file = createTempFile(getTempFileName(fileToken));
		if (!file.exists()) {
			return fileToken;
		} else {
			return createTempFileUID();
		}
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
	 * ���O�C�����[�U�����擾����.
	 * 
	 * @param req ���N�G�X�g
	 * @return ���O�C�����[�U���
	 */
	protected final TUserInfo refTServerUserInfo(HttpServletRequest req) {

		if (req == null) {
			throw new IllegalArgumentException("request is null.");
		}

		if (!ServerConfig.isSessionMode()) {
			// �J�����[�h��(�Z�b�V��������)�A���񃆁[�U���擾
			try {
				String userCode; // ��ЃR�[�h
				String companyCode; // ���[�U�R�[�h

				if (ServletFileUpload.isMultipartContent(req)) {
					// �A�b�v���[�h
					companyCode = getUploadParameter(req, "T_CC");
					userCode = getUploadParameter(req, "T_UC");
				} else {
					// �ʏ�
					companyCode = req.getParameter("T_CC");
					userCode = req.getParameter("T_UC");
				}

				if (Util.isNullOrEmpty(companyCode) || Util.isNullOrEmpty(userCode)) {
					// �����w�肪�����ꍇ�́A���[�h�G���[
					throw new TRuntimeException("AppletViewer MODE: Mode error or Parameter is not set");
				}

				S2Container container = SingletonS2ContainerFactory.getContainer();
				UserManager logic = (UserManager) container.getComponent("old-UserManager");
				logic.setCompanyCode(companyCode);
				logic.setUserCode(userCode);

				return logic.makeUserInfo();

			} catch (TException ex) {
				ServerErrorHandler.handledException(ex);
				return null;
			}
		}

		HttpSession session = req.getSession(false);
		if (session == null) {
			return null;
		}

		return (TUserInfo) session.getAttribute(TServerEnv.SYS_PREFIX + "userinfo");
	}

	/**
	 * �w��P��ID�A�܂��͒P����o�C���h���āA���b�Z�[�W��Ԃ�.
	 * 
	 * @param req ���N�G�X�g
	 * @param messageID ���b�Z�[�WID
	 * @param objects �P��ID�A�܂��́A�P��̃��X�g
	 * @return �ϊ����ꂽ���b�Z�[�W
	 */
	protected String getMessage(HttpServletRequest req, String messageID, Object... objects) {

		return MessageUtil.convertMessage(getLanguage(req), messageID, objects);
	}

	/**
	 * �w�肳�ꂽ�P��ID�ɕR�Â��P�ꕶ����Ԃ�.
	 * 
	 * @param req ���N�G�X�g
	 * @param wordID �P��ID
	 * @return �P�ꕶ��
	 */
	protected String getWord(HttpServletRequest req, String wordID) {

		return getWord(getLanguage(req), wordID);
	}

	/**
	 * �w�肳�ꂽ�P��ID�ɕR�Â��P�ꕶ����Ԃ�.
	 * 
	 * @param lang ����R�[�h
	 * @param wordID �P��ID
	 * @return �P�ꕶ��
	 */
	protected String getWord(String lang, String wordID) {

		return MessageUtil.getWord(lang, wordID);
	}

	/**
	 * �w�肳�ꂽ�P��ID(���X�g)�ɕR�Â��P�ꕶ�����X�g(���X�g)��Ԃ�.
	 * 
	 * @param req ���N�G�X�g
	 * @param wordIDs �P��ID���X�g
	 * @return �P�ꕶ�����X�g
	 */
	protected String[] getWordList(HttpServletRequest req, String[] wordIDs) {

		return MessageUtil.getWordList(getLanguage(req), wordIDs);
	}

	/**
	 * ����R�[�h���擾����.<br>
	 * ���O�C���O�⃆�[�U��񂪐��m�łȂ��ꍇ�A�V�X�e�������Ԃ�.
	 * 
	 * @param req ���N�G�X�g
	 * @return ����R�[�h
	 */
	protected String getLanguage(HttpServletRequest req) {

		// ���[�U���̎擾.
		TUserInfo userInfo = refTServerUserInfo(req);

		String lang = (userInfo == null) ? "" : (String) userInfo.getUserLanguage();
		if (Util.isNullOrEmpty(lang)) {
			// ���O�C����ԂłȂ��A���[�U���\�z�O ���̏ꍇ�́A�V�X�e������
			lang = ServerConfig.getSystemLanguageCode();
		}

		return lang;
	}

	/**
	 * �R���e�i���擾����
	 * 
	 * @return �R���e�i
	 */
	protected TContainer getContainer() {
		return new TContainer(SingletonS2ContainerFactory.getContainer());
	}

	// �ȉ��A�s�vHTTP method

	/**
	 * ��Ή� HTTP method.
	 */
	@Override
	protected final void doDelete(HttpServletRequest req, HttpServletResponse resp) {
		try {
			super.doDelete(req, resp);
		} catch (Exception ex) {
			ServerErrorHandler.handledException(ex);
		}
	}

	/**
	 * ��Ή� HTTP method.
	 */
	@Override
	protected final void doHead(HttpServletRequest req, HttpServletResponse resp) {
		try {
			super.doHead(req, resp);
		} catch (Exception ex) {
			ServerErrorHandler.handledException(ex);
		}
	}

	/**
	 * ��Ή� HTTP method.
	 */
	@Override
	protected final void doOptions(HttpServletRequest req, HttpServletResponse resp) {
		try {
			super.doOptions(req, resp);
		} catch (Exception ex) {
			ServerErrorHandler.handledException(ex);
		}
	}

	/**
	 * ��Ή� HTTP method.
	 */
	@Override
	protected final void doPut(HttpServletRequest req, HttpServletResponse resp) {
		try {
			super.doPut(req, resp);
		} catch (Exception ex) {
			ServerErrorHandler.handledException(ex);
		}
	}

	/**
	 * ��Ή� HTTP method.
	 */
	@Override
	protected final void doTrace(HttpServletRequest req, HttpServletResponse resp) {
		try {
			super.doTrace(req, resp);
		} catch (Exception ex) {
			ServerErrorHandler.handledException(ex);
		}
	}

	/**
	 * ��Ή� HTTP method.
	 */
	@Override
	protected final long getLastModified(HttpServletRequest req) {
		try {
			return super.getLastModified(req);
		} catch (Exception ex) {
			ServerErrorHandler.handledException(ex);
		}
		return 0L;
	}
}
