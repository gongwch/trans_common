package mail;

import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.model.mail.*;

/**
 * 
 */
public class testSendMail {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Mail mail = new Mail("�V�K����VESSEL�̂��m�点�i�������M�A�ԐM�s�v�j");

		mail.addFile("E:\\���d��\\VSS�����擾���@.xls");

		SQLCreator text = new SQLCreator();
		text.add("���r�N");
		text.add("");
		text.add("�����l�ł��B�p�ł��B");
		text.add("");
		text.add("�V�K����VESSEL�̂��m�点�ł��B");
		text.add("VESSEL���FAIS��");
		text.add("VOYAGE�F119");
		text.add("���̍`�F���l");
		text.add("");
		text.add("VSS�����擾���@���Y�t���܂��B");
		text.add("");
		text.add("�ȏ�A��낵�����肢�������܂��B");

		mail.setText(text.toSQL());

		String to = "mkoike@a-i-s.co.jp";
		// String cc = "mtanaka@a-i-s.co.jp";
		// String bcc = "yfcong@a-i-s.co.jp";
		String cc = null;
		String bcc = null;
		ServerMailUtil.send(to, cc, bcc, mail);

	}

}
