package jp.co.ais.trans2.common.client;

import java.awt.*;
import java.io.*;
import java.lang.reflect.*;
import java.net.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.exception.*;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.common.fw.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.program.*;
import jp.co.ais.trans2.model.security.*;
import jp.co.ais.trans2.model.user.*;

/**
 * �R���g���[�����
 * 
 * @author AIS
 */
public abstract class TController extends TPanelCtrlBase {

	/** true:ZIP���[�h */
	protected static boolean isZipMode = false;

	/** �V�X�e���o�[�W���� */
	public static String jarUpdateDate = null;

	/** �V�X�e���o�[�W���� */
	public static String jarVersion = "";

	static {
		try {
			// (true:ZIP���[�h)
			isZipMode = ClientConfig.isFlagOn("trans.response.zip.mode");
		} catch (MissingResourceException e) {
			// �����Ȃ�
		}
	}

	/**
	 * �v���O�������J�n����(���C����ʂ�Orverride�p)
	 */
	public void start() {
		//
	}

	/**
	 * �o�[�W����������
	 */
	public static void initClientVersion() {

		try {
			Class clazz = TController.class;

			try {
				// �ʎw��(�J�X�^�}�C�Y���[�U�[�Ή��̂���)
				String clazzName = ClientConfig.getProperty("trans.version.check.class");
				clazz = Class.forName(clazzName);

			} catch (Exception e) {
				// �����Ȃ�
			}

			String[] info = Util.getSystemVersion(clazz);
			jarUpdateDate = info[0];
			jarVersion = info[1];

		} catch (Exception e) {
			// �G���[�\��
			ClientLogger.error("version initial error.");
		}
	}

	/**
	 * �p�l���擾 (���C����ʂ�Orverride�p)
	 * 
	 * @return �p�l��
	 */
	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * Server�Ƃ̒ʐM
	 * 
	 * @param cls �N���X
	 * @param methodName ���\�b�h
	 * @param arg ����
	 * @return ����
	 * @throws TException
	 */
	public Object request(Class cls, String methodName, Object... arg) throws TException {

		try {
			String programCode = getProgramCode();
			String realUID = getRealUID();
			String realInfo = getRealInfo();

			// Web�A�v���P�[�V�����̏ꍇ
			if (isWeb()) {
				addSendValues("programCode", programCode);
				addSendValues("realUID", realUID);
				addSendValues("realInfo", realInfo);
				addSendValues("className", cls != null ? cls.getName() : "");
				addSendValues("methodName", methodName);
				addSendValues("systemVersion", jarVersion); // �N���C�A���g���Ŏ����Ă���o�[�W����

				// �t�@�C����؂蕪��
				List<Object> objectList = new ArrayList<Object>();
				List<File> fileList = new ArrayList<File>();
				List<Integer> fileIndexs = new ArrayList<Integer>();

				// WEB���M�Ώۃ��X�g�𐮔�
				FileTransferUtil.reflectWebObjectList(objectList, fileList, fileIndexs, arg);

				addSendObject("methodArg", objectList);
				// addSendObject("fileIndex", fileIndexs);

				addSendObject("trans.system.company", getCompany());
				addSendObject("trans.system.user", getUser());

				send(getServletName(), fileList);

				// �G���[�n���h�����O
				if (getRespMode() == RESP_ERROR || getRespMode() == RESP_ERROR_SYNTAX) {

					if (Util.isNullOrEmpty(getErrorMessage())) {
						throw new TException(getErrorCode());

					} else {
						throw new TException(getErrorMessage());
					}
				}

				Object ret = getResultObject();

				if (ret instanceof Throwable) {
					throw (Exception) ret;
				}

				// �o�C�i���͈��k����
				if (ret instanceof byte[] && isZipMode) {
					ret = ResourceUtil.toBinaryInZip((byte[]) ret);
				}

				return ret;
			}

			// ��Web�A�v���P�[�V�����̏ꍇ
			canTransfer(arg); // �J�����ʐM�`�F�b�N

			Object ret = null;

			Class clazz = Class.forName(cls.getName());

			TContainer container = TContainerFactory.getContainer();
			Object instance = container.getComponent(clazz);

			if (instance instanceof TModel) {
				TModel model = (TModel) instance;
				model.setCompany(TLoginInfo.getCompany());
				model.setUser(TLoginInfo.getUser());
				model.setProgramCode(programCode);
				model.setNow(Util.getCurrentDate());

				String url = TLoginCtrl.getClientSaveKey();
				model.setServerInstanceName(url);

				// ��ʎ��ʎq�y�у��O�p���
				model.setRealUID(realUID);
				model.setRealInfo(realInfo);
			}

			if (arg == null || arg.length == 0) {
				Method method = clazz.getMethod(methodName);
				ret = method.invoke(instance);

			} else {
				Method method = getMethod(clazz, methodName, arg);

				if (method == null) {
					throw new TException(getMessage("E00038") + clazz.getName() + "#" + methodName + "()");// �Ăяo���惁�\�b�h��������܂���:
				}

				ret = method.invoke(instance, arg);
			}

			canTransfer(new Object[] { ret }); // �J�����ʐM�`�F�b�N

			return ret;

		} catch (InvocationTargetException e) {
			if (e.getTargetException() instanceof TException) {
				throw (TException) e.getTargetException();

			} else if (e.getTargetException() instanceof TRuntimeException) {
				throw (TRuntimeException) e.getTargetException();

			} else if (e.getTargetException() instanceof RuntimeException) {
				throw (RuntimeException) e.getTargetException();
			}

			throw new RuntimeException(e);

		} catch (Throwable e) {
			if (e instanceof TException) {
				throw (TException) e;

			} else if (e instanceof TRuntimeException || e instanceof RuntimeException) {
				throw (RuntimeException) e;
			}

			Throwable cause = e.getCause();
			if (cause != null) {
				if (cause instanceof TException) {
					throw (TException) cause;

				} else if (cause instanceof TRuntimeException) {
					throw (TRuntimeException) cause;
				}
			}

			if (e instanceof RuntimeException) {
				throw (RuntimeException) e;
			}

			throw new RuntimeException(e);
		}
	}

	/**
	 * Web���[�h����
	 * 
	 * @return true:Web���[�h
	 */
	protected boolean isWeb() {
		return ClientConfig.isWeb();
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
			List<Class> classList = new ArrayList<Class>();

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
	 * �ʐM��Servlet��
	 * 
	 * @return Servlet��
	 */
	protected String getServletName() {
		return "TServlet";
	}

	/**
	 * �ʐM�\���ǂ����`�F�b�N.NG�̓G���[(��ʐM���[�J���J���p)
	 * 
	 * @param args �Ώ�
	 */
	protected void canTransfer(Object[] args) {

		boolean not = false;

		// Selealizable�`�F�b�N
		Object notObject = null;
		for (Object arg : args) {
			if (arg == null) {
				continue;
			}

			if (arg instanceof List) {
				List list = (List) arg;
				if (list.isEmpty()) {
					continue;
				}

				if (!(list.get(0) instanceof Serializable)) {
					notObject = list.get(0);
					not = true;
					break;
				}

			} else if (arg instanceof Map) {
				Map map = ((Map) arg);
				if (map.isEmpty()) {
					continue;
				}

				Map.Entry[] arry = (Map.Entry[]) map.entrySet().toArray(new Map.Entry[0]);

				if (!(arry[0].getKey() instanceof Serializable)) {
					notObject = arry[0].getKey();
					not = true;
					break;
				}

				if (!(arry[0].getValue() instanceof Serializable)) {
					notObject = arry[0].getValue();
					not = true;
					break;
				}

			} else if (arg instanceof Set) {
				Set set = ((Set) arg);
				if (set.isEmpty()) {
					continue;
				}

				Object[] arry = set.toArray(new Object[0]);
				if (!(arry[0] instanceof Serializable) || !(arry[0] instanceof Serializable)) {
					notObject = arry[0];
					not = true;
					break;
				}

			} else {
				if (!(arg instanceof Serializable)) {
					notObject = arg;
					not = true;
					break;
				}
			}
		}

		if (not) {
			String className = notObject == null ? "" : notObject.getClass().getName();
			throw new TRuntimeException("E00039", className);// �ʐM�I�u�W�F�N�g��Serializable�ɂ���K�v������܂��B{0}
		}
	}

	/**
	 * ���O�C����Џ���߂��܂��B
	 * 
	 * @return ��Џ��
	 */
	public Company getCompany() {
		return TLoginInfo.getCompany();
	}

	/**
	 * ���O�C����ЃR�[�h��߂��܂��B
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return getCompany().getCode();
	}

	/**
	 * ���O�C�����[�U�[����߂��܂��B
	 * 
	 * @return ���[�U�[���
	 */
	public User getUser() {
		return TLoginInfo.getUser();
	}

	/**
	 * ���O�C�����[�U�[�R�[�h��߂��܂��B
	 * 
	 * @return ���[�U�[�R�[�h
	 */
	public String getUserCode() {
		return getUser().getCode();
	}

	/**
	 * �v���O�������̎擾
	 * 
	 * @return �v���O�������
	 */
	@Override
	public TClientProgramInfo getProgramInfo() {

		return this.prgInfo;
	}

	/**
	 * �v���O����ID�擾
	 * 
	 * @return �v���O����ID
	 */
	@Override
	public String getProgramCode() {

		if (prgInfo == null) {
			return "";
		}

		return this.getProgramInfo().getProgramCode();
	}

	/**
	 * �v���O�������̎擾
	 * 
	 * @return �v���O��������
	 */
	@Override
	public String getProgramName() {

		if (prgInfo == null) {
			return "";
		}

		return this.getProgramInfo().getProgramName();
	}

	/**
	 * �G���[�n���h�����O<br>
	 * TException�̏ꍇ�́A�ێ����Ă��郁�b�Z�[�W��\��.<br>
	 * ����ȊO�́u����ɏ�������܂���ł����v�̃��b�Z�[�W��\������.
	 * 
	 * @param parent �e�R���|�[�l���g
	 * @param ex ����Exception
	 */
	@Override
	public void errorHandler(Component parent, Throwable ex) {

		if (ex instanceof TMessageListException) {
			TMessageListException tex = (TMessageListException) ex;
			showMessageList(parent, tex.getMessage(), tex.getList());

		} else {
			super.errorHandler(parent, ex);
		}
	}

	/**
	 * �w�肳�ꂽ���b�Z�[�W���ꗗ�ŕ\������
	 * 
	 * @param message ���x�����b�Z�[�W
	 * @param list ���b�Z�[�W���X�g
	 */
	public void showList(String message, List<String> list) {
		List<Message> mlist = new ArrayList<Message>(list.size());

		for (String str : list) {
			mlist.add(new Message(str));
		}

		showMessageList(getView(), message, mlist);
	}

	/**
	 * �w�肳�ꂽ���b�Z�[�W���ꗗ�ŕ\������
	 * 
	 * @param list ���b�Z�[�W���X�g
	 */
	public void showMessageList(List<Message> list) {
		showMessageList(getView(), list);
	}

	/**
	 * �w�肳�ꂽ���b�Z�[�W���ꗗ�ŕ\������
	 * 
	 * @param comp �Ăяo�����R���|�[�l���g
	 * @param list ���b�Z�[�W���X�g
	 */
	public void showMessageList(Component comp, List<Message> list) {
		showMessageList(comp, "", list);
	}

	/**
	 * �w�肳�ꂽ���b�Z�[�W���ꗗ�ŕ\������
	 * 
	 * @param message ���x�����b�Z�[�W
	 * @param list ���b�Z�[�W���X�g
	 */
	public void showMessageList(String message, List<Message> list) {
		showMessageList(getView(), message, list);
	}

	/**
	 * �w�肳�ꂽ���b�Z�[�W���ꗗ�ŕ\������
	 * 
	 * @param comp �Ăяo�����R���|�[�l���g
	 * @param message ���x�����b�Z�[�W
	 * @param list ���b�Z�[�W���X�g
	 */
	public void showMessageList(Component comp, String message, List<Message> list) {

		MessageListDialog dialog;
		if (comp instanceof TPanel) {
			dialog = createMessageDialog(((TPanel) comp).getParentFrame());

		} else {
			Component parent = TGuiUtil.getParentFrameOrDialog2(comp);

			if (parent instanceof Frame) {
				dialog = createMessageDialog((Frame) parent);

			} else if (parent instanceof Dialog) {
				dialog = createMessageDialog((Dialog) parent);

			} else {
				throw new IllegalArgumentException("Wrong parent window");
			}
		}

		// ���b�Z�[�W�ϊ�
		List<String[]> msgList = new ArrayList<String[]>();
		LinkedHashSet<String> msgs = new LinkedHashSet<String>();

		for (Message bean : list) {
			if (bean.hasMessage()) {
				String[] args = new String[2];
				args[0] = getMessage(bean.getSubMessageID());
				args[1] = getMessage(bean.getMessageID(), bean.getBindIds());

				// ���ꃁ�b�Z�[�W�͏��O
				String msg = args[0] + "<>" + args[1];
				if (msgs.contains(msg)) {
					continue;
				}
				msgs.add(msg);

				msgList.add(args);
			}
		}

		dialog.setMessagesList(msgList);

		// ���b�Z�[�W�ꗗ�\���_�C�A���O��\������
		dialog.show(getMessage(message));
	}

	/**
	 * �w�肳�ꂽ���b�Z�[�W���ꗗ�ŕ\������
	 * 
	 * @param message ���x�����b�Z�[�W
	 * @param list ���b�Z�[�W���X�g
	 * @return �I��l
	 */
	public int showConfermList(String message, List<String> list) {
		return showConfermList(getView(), message, list);
	}

	/**
	 * �w�肳�ꂽ���b�Z�[�W���ꗗ�ŕ\������
	 * 
	 * @param comp �Ăяo�����R���|�[�l���g
	 * @param message ���x�����b�Z�[�W
	 * @param list ���b�Z�[�W���X�g
	 * @return �I��l
	 */
	public int showConfermList(Component comp, String message, List<String> list) {
		List<Message> mlist = new ArrayList<Message>(list.size());

		for (String str : list) {
			mlist.add(new Message(str));
		}

		return showConfermMessageList(comp, message, mlist);
	}

	/**
	 * �w�肳�ꂽ���b�Z�[�W���ꗗ�ŕ\������
	 * 
	 * @param message ���x�����b�Z�[�W
	 * @param list ���b�Z�[�W���X�g
	 * @return �I��l
	 */
	public int showConfermMessageList(String message, List<Message> list) {
		return showConfermMessageList(getView(), message, list);
	}

	/**
	 * �w�肳�ꂽ���b�Z�[�W���ꗗ�ŕ\������
	 * 
	 * @param comp �Ăяo�����R���|�[�l���g
	 * @param message ���x�����b�Z�[�W
	 * @param list ���b�Z�[�W���X�g
	 * @return �I��l
	 */
	public int showConfermMessageList(Component comp, String message, List<Message> list) {

		ConfermMessageListDialog dialog;
		if (comp instanceof TPanel) {
			dialog = createConfermDialog(((TPanel) comp).getParentFrame());

		} else {
			Component parent = TGuiUtil.getParentFrameOrDialog2(comp);

			if (parent instanceof Frame) {
				dialog = createConfermDialog((Frame) parent);

			} else if (parent instanceof Dialog) {
				dialog = createConfermDialog((Dialog) parent);

			} else {
				throw new IllegalArgumentException("Wrong parent window");
			}
		}

		List<String[]> msgList = new ArrayList<String[]>();
		LinkedHashSet<String> msgs = new LinkedHashSet<String>();

		for (Message bean : list) {
			if (bean.hasMessage()) {
				String[] args = new String[2];
				args[0] = getMessage(bean.getSubMessageID());
				args[1] = getMessage(bean.getMessageID(), bean.getBindIds());

				// ���ꃁ�b�Z�[�W�͏��O
				String msg = args[0] + "<>" + args[1];
				if (msgs.contains(msg)) {
					continue;
				}
				msgs.add(msg);

				msgList.add(args);
			}
		}

		dialog.setMessageList(msgList);

		// ���b�Z�[�W�ꗗ�\���_�C�A���O��\������
		return dialog.show(getMessage(message));
	}

	/**
	 * @param comp
	 * @return ���b�Z�[�W�_�C�A���O
	 */
	protected MessageListDialog createMessageDialog(Frame comp) {
		return new MessageListDialog(comp);
	}

	/**
	 * @param comp
	 * @return ���b�Z�[�W�_�C�A���O
	 */
	protected MessageListDialog createMessageDialog(Dialog comp) {
		return new MessageListDialog(comp);
	}

	/**
	 * @param comp
	 * @return �m�F�_�C�A���O
	 */
	protected ConfermMessageListDialog createConfermDialog(Frame comp) {
		return new ConfermMessageListDialog(comp);
	}

	/**
	 * @param comp
	 * @return �m�F�_�C�A���O
	 */
	protected ConfermMessageListDialog createConfermDialog(Dialog comp) {
		return new ConfermMessageListDialog(comp);
	}

	/**
	 * ���O�����
	 * 
	 * @param company
	 * @param user
	 * @param programCode
	 * @param message
	 */
	public void log(Company company, User user, String programCode, String message) {
		log(company, user, programCode, message, "");
	}

	/**
	 * ���O�����
	 * 
	 * @param company
	 * @param user
	 * @param programCode
	 * @param message
	 * @param info
	 */
	public void log(Company company, User user, String programCode, String message, String info) {

		try {

			// ���O����
			logE(company, user, programCode, message, info);

		} catch (TException e) {
			errorHandler(e);
		}
	}

	/**
	 * ���O�����
	 * 
	 * @param company
	 * @param user
	 * @param programCode
	 * @param message
	 * @throws TException
	 */
	public void logE(Company company, User user, String programCode, String message) throws TException {
		logE(company, user, programCode, message, "");
	}

	/**
	 * ���O�����
	 * 
	 * @param company
	 * @param user
	 * @param programCode
	 * @param message
	 * @param info
	 * @throws TException
	 */
	public void logE(Company company, User user, String programCode, String message, String info) throws TException {

		// ���O����
		Log log = new Log();

		log.setDate(Util.getCurrentDate());
		log.setCompany(company);
		log.setUser(user);
		Program program = new Program();
		program.setCode(programCode);
		log.setProgram(program);
		try {
			log.setIp(InetAddress.getLocalHost().getHostAddress());
		} catch (Exception e) {
			log.setIp("Unknown");
		}
		log.setMessage(message);
		log.setInfo(info);

		request(LogManager.class, "log", log);
	}

	/**
	 * �G�N�Z�����o�͂���
	 * 
	 * @param data
	 * @param fileName �g���q�i.xls�j�s�v
	 * @throws TException
	 */
	protected void printOutExcel(byte[] data, String fileName) throws TException {
		printOut(data, fileName + ".xls");
	}

	/**
	 * PDF���o�͂���
	 * 
	 * @param data
	 * @param fileName �g���q�i.pdf�j�s�v
	 * @throws TException
	 */
	protected void printOutPDF(byte[] data, String fileName) throws TException {
		printOut(data, fileName + ".pdf");
	}

	/**
	 * �t�@�C�������w�肵�ďo�͂���B
	 * 
	 * @param data
	 * @param fileName (�g���q���K�v)
	 * @throws TException
	 */
	protected void printOut(byte[] data, String fileName) throws TException {
		if (data == null || data.length == 0) {
			showMessage("I00022");
			return;
		}

		// �v���r���[
		TPrinter printer = new TPrinter();
		printer.preview(data, fileName);
	}

	/**
	 * �o�b�N�A�b�v�t�H���_�Ƀt�@�C�����ړ�����.
	 * 
	 * @param file �Ώۃt�@�C��
	 */
	protected void moveToBackup(TFile file) {
		try {
			String oldPath = file.getFile().getPath();
			String newPath = file.getFile().getParent() + File.separator + "backup" + File.separator
					+ file.getFileName();

			if (new File(newPath).exists()) {
				// �o�b�N�A�b�v��ɓ����t�@�C�������݂��܂��B�㏑�����܂����H
				if (!showConfirmMessage("Q00065")) {
					return;
				}
			}

			FileUtil.moveFile(oldPath, newPath);

		} catch (Exception ex) {
			// ��ʏ�ɂ͓��ɒʒm���Ȃ�
			ClientErrorHandler.handledException(ex);
		}

	}

}
