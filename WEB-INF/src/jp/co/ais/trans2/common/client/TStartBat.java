package jp.co.ais.trans2.common.client;

import jp.co.ais.trans2.common.ui.*;

/**
 * �o�b�`�v���O�����N���N���X�B<br>
 * �A�v���P�[�V�����͓��Y�N���X��main���\�b�h���N�_�Ƃ���B
 * 
 * @author AIS
 */
public class TStartBat {

	/**
	 * �v���O�����G���g���|�C���g<BR>
	 * �A�v���P�[�V�������N������B
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// ���O�C���R���g���[���N��
		TLoginCtrl ctrl = new TLoginCtrl();

		// ���O�C���J�n
		ctrl.start();
	}
}
