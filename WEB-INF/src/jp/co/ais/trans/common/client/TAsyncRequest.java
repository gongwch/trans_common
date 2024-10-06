package jp.co.ais.trans.common.client;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;

/**
 * �񓯊��p���N�G�X�^�[
 */
public class TAsyncRequest extends TAppletClientBase {

	/** ���R���g���[���[ */
	private TAppletClientBase ctrl;

	/** �}���`�X���b�h�u���b�N�p�̃_�~�[�C���X�^���X */
	private String dummy = "";

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param ctrl ���R���g���[���[
	 */
	public TAsyncRequest(TAppletClientBase ctrl) {
		this.ctrl = ctrl;
	}

	/**
	 * ��ʎ擾�ioverride�p�j<br>
	 * �R���g���[��������Panel or Dialog��Ԃ�.<br>
	 * ������ʂ̃R���g���[���ɍ����ւ�
	 * 
	 * @return ���R���g���[���[�̉��
	 */
	@Override
	public Container getView() {
		return ctrl.getView();
	}

	/**
	 * �ʐM���ʂ̃G���[�l�p�G���[�n���h�����O. <br>
	 * getView()��e�R���|�[�l���g�Ƃ��Ĉ���.<br>
	 * �������ύX
	 */
	@Override
	public void errorHandler() {
		super.errorHandler();
	}

	/**
	 * ���M(�񓯊�����).<br>
	 * getView()���������Ă��邱�Ƃ��O��.
	 * 
	 * @param path ���s�p�X
	 * @param listener ���N�G�X�g��̏������X�i�[
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void requestAsync(final String path, final TAsyncListener listener) {
		requestAsync(path, Collections.EMPTY_LIST, listener);
	}

	/**
	 * ���M(�񓯊�����).<br>
	 * getView()���������Ă��邱�Ƃ��O��.
	 * 
	 * @param path ���s�p�X
	 * @param files �t�@�C�����X�g
	 * @param listener ���N�G�X�g��̏������X�i�[
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void requestAsync(final String path, final List<File> files, final TAsyncListener listener) {

		try {
			synchronized (dummy) {

				ctrl.locked(true);

				new Thread() {

					public void run() {
						try {
							final boolean result = request(path, files);

							ctrl.locked(false);

							SwingUtilities.invokeLater(new Runnable() {

								public void run() {

									if (listener != null) {
										listener.after(result);
									}
								}
							});

						} catch (NullPointerException e) {
							ClientLogger.error("null error.", e);

						} catch (Exception e) {
							ctrl.locked(false);
							ctrl.errorHandler(e);

						} finally {
							ctrl.locked(false);
						}
					}
				}.start();
			}

		} catch (TRuntimeException ex) {
			ctrl.locked(false);

			throw ex;
		}
	}

	/**
	 * �t�@�C���_�E�����[�h���\��(�񓯊�����).<br>
	 * getView()���������Ă��邱�Ƃ��O��.
	 * 
	 * @param cont ���R���|�[�l���g
	 * @param servletName �Ώ�Servlet��
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void downloadAsync(final Container cont, final String servletName) {
		this.downloadAsync(cont, servletName, Collections.EMPTY_MAP);
	}

	/**
	 * �t�@�C���_�E�����[�h���\��(�񓯊�����).<br>
	 * getView()���������Ă��邱�Ƃ��O��.
	 * 
	 * @param cont ���R���|�[�l���g
	 * @param servletName �Ώ�Servlet��
	 * @param funcArgs ���n���p�����[�^
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void downloadAsync(final Container cont, final String servletName, final Map funcArgs) {

		try {
			synchronized (dummy) {

				ctrl.locked(true);

				new Thread() {

					public void run() {
						try {

							downloadNative(servletName, funcArgs);

							// // �C�x���g�f�B�X�p�b�`�X���b�h��
							SwingUtilities.invokeLater(new Runnable() {

								public void run() {

									ctrl.locked(false);

									// �t�@�C���W�J&���s
									executeResultFile(cont);
								}
							});

						} catch (NullPointerException e) {
							ClientLogger.error("null error.", e);

						} catch (TRequestException e) {
							ctrl.locked(false);
							ctrl.errorHandler(cont);

						} catch (TException ex) {
							ctrl.locked(false);
							ctrl.errorHandler(cont, ex);

						} finally {
							ctrl.locked(false);
						}
					}
				}.start();
			}

		} catch (TRuntimeException ex) {
			ctrl.locked(false);

			throw ex;
		}
	}

	/**
	 * TEXT�t�@�C���A�b�v���[�h
	 * 
	 * @param cont �p�l��(Panel�R���|�[�l���g)
	 * @param servletName ����Servlet��
	 * @param listener
	 */
	@SuppressWarnings("deprecation")
	public void uploadTXTAsync(Container cont, String servletName, TAsyncListener listener) {
		super.uploadTXTAsync(cont, servletName, listener);
	}
}
