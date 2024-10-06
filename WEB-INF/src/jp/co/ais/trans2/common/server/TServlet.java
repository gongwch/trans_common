package jp.co.ais.trans2.common.server;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;

import javax.servlet.http.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.server.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.common.exception.*;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.common.fw.*;
import jp.co.ais.trans2.common.fw.TContainer;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.user.*;

/**
 * �T�[�u���b�g���
 * 
 * @author AIS
 */
public class TServlet extends TServletBase {

	/** �t�@�C���T�C�YMAX�ݒ�l */
	protected static int autosaveSize = 0;

	/** �T�[�o�ۑ��p�X */
	protected static String autosaveOutputDir;

	/** true:ZIP���[�h */
	protected static boolean isZipMode = false;

	static {
		try {
			// (�P�ʕϊ�:MB��B)
			autosaveSize = Util.avoidNullAsInt(ServerConfig.getProperty("trans.auto.save.size")) * 1024 * 1024;
			autosaveOutputDir = ServerConfig.getProperty("trans.auto.save.output.dir");
		} catch (MissingResourceException e) {
			// �����Ȃ�
		}
		try {
			// (true:ZIP���[�h)
			isZipMode = ServerConfig.isFlagOn("trans.response.zip.mode");
		} catch (MissingResourceException e) {
			// �����Ȃ�
		}
	}

	/**
	 * �t�@�C���t��
	 * 
	 * @see jp.co.ais.trans.common.server.TServletBase#doMethodPostMultipart(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doMethodPostMultipart(HttpServletRequest req, HttpServletResponse resp) {
		this.doMethodPost(req, resp);
	}

	/**
	 * Web�A�v���P�[�V�����̏ꍇ
	 * 
	 * @see TServletBase#doMethodPost(HttpServletRequest, HttpServletResponse)
	 */
	@Override
	protected void doMethodPost(HttpServletRequest req, HttpServletResponse resp) {

		// �C�x���g�n���h�����O
		String className = null;
		String methodName = null;
		List<Object> objectList;

		try {

			String reqVersion = Util.avoidNull(req.getParameter("systemVersion"));

			ServerLogger.debug("systemVersion:" + version);
			ServerLogger.debug("reqVersion:" + reqVersion);

			// E00041">�����p�[���̃V�X�e���o�[�W�����ƃT�[�o�̃V�X�e���o�[�W�������قȂ�܂��B\n�N�����̃A�v���P�[�V������S�ĕ�����A�ēx���O�C�����s���Ă��������B
			if (!Util.isNullOrEmpty(reqVersion)) {
				if (!"local".equals(version) && !"local".equals(reqVersion)) {
					if (!version.equals(reqVersion)) {
						super.dispatchResultObject(req, resp, new TException("E00041"));
						return;
					}
				}
			}

			// �t�@�C��������ꍇ�A�����̉ӏ��ɓ���Ȃ���
			List<File> fileList = getUploadFiles(req);

			if (fileList == null) {
				// �ʏ�
				className = Util.avoidNull(req.getParameter("className"));
				methodName = Util.avoidNull(req.getParameter("methodName"));
				objectList = (List<Object>) getObjectParameter(req, "methodArg");

			} else {
				// �t�@�C������(multipart���M)
				className = Util.avoidNull(getUploadParameter(req, "className"));
				methodName = Util.avoidNull(getUploadParameter(req, "methodName"));
				objectList = (List<Object>) getUploadObject(req, "methodArg");

				// List<Integer> fileIndexs = (List<Integer>) getUploadObject(req, "fileIndex");

				// �t�@�C�����T�[�o�T�C�g�ɃR���o�[�g
				FileTransferUtil.dispatchWebObjectList(objectList, fileList);
			}

			Class clazz = Class.forName(className);

			// ����
			Object[] arg = objectList.toArray(new Object[objectList.size()]);

			TContainer container = TContainerFactory.getContainer();
			Object instance = container.getComponent(clazz);

			if (instance instanceof TModel) {
				TModel model = (TModel) instance;

				Company company; // ���
				User user; // ���[�U
				String prg; // �v���O�����R�[�h

				if (fileList == null) {
					// �ʏ�
					company = (Company) getObjectParameter(req, "trans.system.company");
					user = (User) getObjectParameter(req, "trans.system.user");
					prg = Util.avoidNull(req.getParameter("programCode"));

				} else {
					// �t�@�C������(multipart���M)
					company = (Company) getUploadObject(req, "trans.system.company");
					user = (User) getUploadObject(req, "trans.system.user");
					prg = Util.avoidNull(getUploadParameter(req, "programCode"));
				}

				model.setCompany(company);
				model.setUser(user);
				model.setProgramCode(prg);
				model.setNow(Util.getCurrentDate());

				String url = req.getRequestURL().toString();

				url = url.replaceAll("/", "_");
				url = url.replaceAll(":", "_");
				url = url.replaceAll("\\\\", "");

				if (Util.isNullOrEmpty(url)) {
					url = ServerConfig.getSchemaName();
				}

				model.setServerInstanceName(url);
			}

			// �߂�
			Object ret = null;

			if (arg == null || arg.length == 0) {
				// �����Ȃ����\�b�h
				Method method = clazz.getMethod(methodName);
				ret = method.invoke(instance);

			} else {
				// �������胁�\�b�h
				Method method = getMethod(clazz, methodName, arg);

				if (method == null) {
					throw new TException(getMessage(req, "E00038") + clazz.getName() + "#" + methodName + "()");// �Ăяo���惁�\�b�h��������܂���:
				}

				ret = method.invoke(instance, arg);
			}

			if (ret instanceof byte[] || ret instanceof Byte[]) {
				if (instance instanceof TModel) {
					TModel model = (TModel) instance;

					// �t�@�C���T�C�Y�`�F�b�N
					checkFileSize((byte[]) ret, model, methodName);

				}
				// �o�C�i��(�t�@�C��)�̏ꍇ�͈��k
				if (isZipMode) {
					byte[] zipBytes = ResourceUtil.zipBinary("bytes", (byte[]) ret);
					super.dispatchResultObject(req, resp, zipBytes);
				} else {
					// ��ZIP���[�h
					super.dispatchResultObject(req, resp, ret);
				}

				return;
			}

			super.dispatchResultObject(req, resp, ret);

		} catch (TFileSizeOverException e) {

			super.dispatchResultObject(req, resp, e);

			String msg = getServletContext().getRealPath(Util.avoidNull(e.getMessageArgs()[0]));
			ServerLogger.error(getMessage(req, e.getMessageID(), msg));

		} catch (InvocationTargetException e) {
			if (e.getTargetException() instanceof TException || e.getTargetException() instanceof TRuntimeException) {
				super.dispatchResultObject(req, resp, e.getTargetException());
				return;
			}

			if (e.getTargetException() instanceof OutOfMemoryError) {
				throw (OutOfMemoryError) e.getTargetException();
			}

			super.dispatchResultObject(req, resp, e);
			ServerLogger.error("className=" + className);
			ServerLogger.error("methodName=" + methodName);
			ServerErrorHandler.handledException(e);

		} catch (Throwable e) {
			if (e instanceof OutOfMemoryError) {
				throw (OutOfMemoryError) e;
			}

			super.dispatchResultObject(req, resp, e);
			ServerLogger.error("className=" + className);
			ServerLogger.error("methodName=" + methodName);
			ServerErrorHandler.handledException(e);

		} finally {
			System.gc();
		}
	}

	/**
	 * �Ăяo�����\�b�h�̓���
	 * 
	 * @param clazz �N���X
	 * @param name ���\�b�h��
	 * @param args ����
	 * @return ���\�b�h
	 */
	protected Method getMethod(Class clazz, String name, Object[] args) {

		// �P���Ăяo��
		try {
			Class classes[] = new Class[args.length];
			for (int i = 0; i < args.length; i++) {
				classes[i] = args[i].getClass();
			}
			return clazz.getMethod(name, classes);

		} catch (Exception ex) {
			// ����
		}

		// �N���X�ɂ��郁�\�b�h�ϓ_����̊���o��
		Method[] methods = clazz.getMethods();

		Map<Method, Class[]> map = new HashMap<Method, Class[]>();

		// ���O�A�����̐�����v���郁�\�b�h�̂݃s�b�N�A�b�v
		for (Method method : methods) {
			if (name.equals(method.getName()) && method.getParameterTypes().length == args.length) {
				map.put(method, method.getParameterTypes());
			}
		}

		if (map.isEmpty()) {
			// �Y���Ȃ�
			return null;

		} else if (map.size() == 1) {
			// �Y��1���Ȃ̂ŕԂ��Ă��܂�
			return map.keySet().toArray(new Method[1])[0];
		}

		// ����Class�̌��ꗗ�����ɓZ�߂�
		List<List<Class>> argClassList = new ArrayList<List<Class>>(args.length);

		for (Object arg : args) {
			List<Class> classList = new LinkedList<Class>();

			Class aclass = arg.getClass();
			classList.add(aclass); // ��
			classList.add(aclass.getSuperclass()); // �e

			// interface
			for (Class iclass : aclass.getInterfaces()) {
				classList.add(iclass);
			}

			argClassList.add(classList);
		}

		// �}�b�`����
		m1: for (Map.Entry<Method, Class[]> entry : map.entrySet()) {
			Class[] values = entry.getValue();

			// �����擪����}�b�`���邩�m�F
			m2: for (int i = 0; i < values.length; i++) {
				Class value = values[i];

				// ��⃊�X�g
				List<Class> kList = argClassList.get(i);

				for (Class kc : kList) {
					if (value.equals(kc)) {
						// ���ɂ���Ύ��̈�����
						continue m2;
					}
				}

				// ���ɂȂ���΁A���̃��\�b�h��
				continue m1;
			}

			// �I�[���N���A��Method�Ԃ�
			return entry.getKey();
		}

		// ���ǌ�����Ȃ�
		return null;
	}

	/**
	 * �t�@�C���T�C�Y�`�F�b�N
	 * 
	 * @param data
	 * @param model ���W���[��
	 * @param method �֐���
	 * @throws Exception
	 */
	protected void checkFileSize(byte[] data, TModel model, String method) throws Exception {
		if (autosaveSize > 0 && data.length > autosaveSize) {
			saveFile(data, model, method);
		}
	}

	/**
	 * �t�@�C���T�[�o�ۑ�
	 * 
	 * @param data
	 * @param model ���W���[��
	 * @param method �֐���
	 * @throws TException
	 */
	protected void saveFile(byte[] data, TModel model, String method) throws TException {
		try {
			// ���݃`�F�b�N�A���݂��Ȃ��ꍇ�A�쐬
			File dir = new File(autosaveOutputDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			// �t�@�C���T�C�Y�`�F�b�N
			String filename = getSaveFileName(model, method);
			ResourceUtil.writeBinary(autosaveOutputDir + File.separator + filename, data);

			// �t�@�C���T�C�Y���傫�����߃T�[�o�ɕۑ����܂����B<���s>�i�ۑ���:{0}�j
			throw new TFileSizeOverException("W01144", autosaveOutputDir + File.separator + filename);
		} catch (IOException e) {
			// �t�@�C���̕ۑ��Ɏ��s���܂����B
			throw new TException("E00032", e);
		}
	}

	/**
	 * �T�[�o�ۑ��p�X���擾
	 * 
	 * @param model ���W���[��
	 * @param method �֐���
	 * @return �T�[�o�ۑ��p�X
	 */
	protected String getSaveFileName(TModel model, String method) {

		String fileName = (model.getCompany() == null ? "" : model.getCompany().getCode()); // ��ЃR�[�h
		fileName += "_" + (model.getUser() == null ? "" : model.getUser().getCode()); // ���O�C�����[�U�R�[�h
		fileName += "_" + model.getProgramCode();
		fileName += "_" + DateUtil.toYMDHMSSSSPlainString(Util.getCurrentDate()).replaceAll("\\.", ""); // �^�C���X�^���v

		String methodName = Util.avoidNull(method).toLowerCase();
		if (methodName.contains("excel") || methodName.contains("xls")) {
			if (TExcel.isExcelXml()) {
				fileName += ".xlsx"; // Excel 2007
			} else {
				fileName += ".xls"; // Excel 2003
			}
		} else {
			fileName += ".pdf"; // PDF
		}

		return fileName;
	}
}
