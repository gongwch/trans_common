package jp.co.ais.trans2.common.client;

import jp.co.ais.trans2.common.ui.*;

/**
 * �v���O�����N���N���X�B<br>
 * �A�v���P�[�V�����͓��Y�N���X��main���\�b�h���N�_�Ƃ���B
 * 
 * @author AIS
 */
public class TSingleStart extends TStart {

	/**
	 * �v���O�����G���g���|�C���g<BR>
	 * �A�v���P�[�V�������N������B
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// �A�v�������ɂ���ăv���p�e�B������
		initArgumentProperties(args);

		// �V���O�����O�C���R���g���[���N��
		TSingleLoginCtrl ctrl = new TSingleLoginCtrl();

		// �V���O�����O�C���J�n
		ctrl.start();

	}

}
