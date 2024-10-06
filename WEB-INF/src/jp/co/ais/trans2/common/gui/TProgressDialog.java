package jp.co.ais.trans2.common.gui;

import java.awt.*;

import javax.swing.*;

/**
 * �v���O���X�o�[��\������_�C�A���O
 */
public class TProgressDialog extends JDialog implements Runnable {

	/** �v���O���X�o�[ */
	public JProgressBar progressBar;

	/** �L�����Z�� */
	public volatile boolean canceled = false;

	/**  */
	public JPanel pnlProgressBar;

	/** �\�����b�Z�[�W */
	public String message = null;

	/** �ُ�������� */
	public Throwable exception = null;

	/**
	 * �R���X�g���N�^�[.
	 * 
	 * @param owner �I�[�i�[
	 * @param modal ���[�_���L��
	 */
	public TProgressDialog(Frame owner, boolean modal) {
		super(owner, modal);
		setUndecorated(true);
	}

	/**
	 * �v���O���X�o�[����.
	 * 
	 * @return �v���O���X�o�[
	 */
	protected JProgressBar createProgressBar() {
		return new JProgressBar();
	}

	/**
	 * �v���O���X�o�[�擾.
	 * 
	 * @return �v���O���X�o�[
	 */
	public JProgressBar getProgressBar() {
		return progressBar;
	}

	/**
	 * �v���O���X�o�[���ݒl�ݒ�.
	 * 
	 * @param n �i���o�[�̌��݂̒l
	 */
	public void setProgressValue(int n) {
		progressBar.setValue(n);

		StringBuilder sb = new StringBuilder();
		sb.append(n);
		sb.append(" / ");
		sb.append(getMaxValue());

		progressBar.setString(sb.toString());
	}

	/**
	 * �v���O���X�o�[���ݒl�{�P�A�\�������ݒ�.
	 * 
	 * @param str �i���o�[�̌��ݕ\������
	 */
	public void setProgressText(String str) {
		progressBar.setValue(progressBar.getValue() + 1);
		progressBar.setString(str);
	}

	/**
	 * �v���O���X�o�[���ݒl�擾.
	 * 
	 * @return �i���o�[�̌��݂̒l
	 */
	public int getProgressValue() {
		return progressBar.getValue();
	}

	@Override
	public void dispose() {
		canceled = true;
		super.dispose();
	}

	/**
	 * �I���L���擾.
	 * <p>
	 * ��ɃL�����Z���{�^���ɂ���ăL�����Z�����ꂽ���ǂ����̔���Ɏg�p����B<br>
	 * �_�C�A���O���̂��I�������ۂ�true�ƂȂ�B
	 * </p>
	 * 
	 * @return true�F�i�����I�����Ă���
	 */
	public boolean canceled() {
		return canceled;
	}

	/**
	 * �i���I���ݒ�.
	 * <p>
	 * �X���b�h�������I�������ۂɓ����\�b�h���Ăяo���Ȃ���΂Ȃ�Ȃ��B
	 * </p>
	 */
	public synchronized void progressEnd() {
		setVisible(false);
	}

	/**
	 * �_�C�A���O������.
	 * 
	 * @param maxValue �ő�l
	 */
	public void init(int maxValue) {
		canceled = false;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		setSize(450, 70);

		Container c = getContentPane();
		c.setLayout(new GridBagLayout());

		initProgressPane(c);
		setUndecorated(true);

		setLocationRelativeTo(null);
		setMaxValue(maxValue);
	}

	/**
	 * ������
	 * 
	 * @param c
	 */
	protected void initProgressPane(Container c) {
		progressBar = createProgressBar();
		progressBar.setStringPainted(true);

		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.NORTHWEST;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		c.add(progressBar, gc);
	}

	/**
	 * �_�C�A���O�\���ݒ�.
	 * <p>
	 * true�ɂ���ƁA�X���b�h�i{@link #run()}�j�����s�J�n����B<br>
	 * �܂��A���[�_���_�C�A���O�̏ꍇ�A�X���b�h�̏I�����҂B
	 * </p>
	 * 
	 * @param b true�F�_�C�A���O��\������
	 */
	@Override
	public void setVisible(boolean b) {
		if (b) {
			startSilence(false);
		} else {
			super.setVisible(false);
		}
	}

	/**
	 * @param silence true:�\���Ȃ�
	 */
	public void startSilence(boolean silence) {
		Thread thread = new Thread(this);
		thread.start();
		if (!silence) {
			super.setVisible(true);
		}
		if (isModal()) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				// �G���[�Ȃ�
				e.printStackTrace();
			}
		}
	}

	/**
	 * �X���b�h����.
	 * <p>
	 * �����\�b�h���I�[�o�[���C�h���āA���ۂ̏������s���B
	 * </p>
	 * 
	 * <pre>
	 * // ��
	 * &#064;Override
	 * public void run() {
	 *  for (int i = 0; i &lt; 100; i++) {
	 *   if ({@link #canceled()}) {
	 *    return;
	 *   }
	 *   {@link #setProgressValue}(i + 1);
	 *  }
	 *  {@link #progressEnd()};
	 * }
	 * </pre>
	 */
	@Override
	public void run() {
		progressEnd();
	}

	/**
	 * �\�����b�Z�[�W�̎擾
	 * 
	 * @return message �\�����b�Z�[�W
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * �\�����b�Z�[�W�̐ݒ�
	 * 
	 * @param message �\�����b�Z�[�W
	 */
	public void setMessage(String message) {
		this.message = message;

		this.setTitle(message);
	}

	/**
	 * �ő�l�̎擾
	 * 
	 * @return maxValue �ő�l
	 */
	public int getMaxValue() {
		return progressBar.getMaximum();
	}

	/**
	 * �ő�l�̐ݒ�
	 * 
	 * @param maxValue �ő�l
	 */
	public void setMaxValue(int maxValue) {
		progressBar.setMaximum(maxValue);
	}

	/**
	 * �ُ���̎擾
	 * 
	 * @return exception �ُ���
	 */
	public Throwable getException() {
		return exception;
	}

	/**
	 * �ُ���̐ݒ�
	 * 
	 * @param exception �ُ���
	 */
	public void setException(Throwable exception) {
		this.exception = exception;
	}

}
