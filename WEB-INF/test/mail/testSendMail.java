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

		Mail mail = new Mail("新規到着VESSELのお知らせ（自動送信、返信不要）");

		mail.addFile("E:\\★仕事\\VSS履歴取得方法.xls");

		SQLCreator text = new SQLCreator();
		text.add("小池君");
		text.add("");
		text.add("お疲れ様です。叢です。");
		text.add("");
		text.add("新規到着VESSELのお知らせです。");
		text.add("VESSEL名：AIS丸");
		text.add("VOYAGE：119");
		text.add("次の港：横浜");
		text.add("");
		text.add("VSS履歴取得方法も添付します。");
		text.add("");
		text.add("以上、よろしくお願いいたします。");

		mail.setText(text.toSQL());

		String to = "mkoike@a-i-s.co.jp";
		// String cc = "mtanaka@a-i-s.co.jp";
		// String bcc = "yfcong@a-i-s.co.jp";
		String cc = null;
		String bcc = null;
		ServerMailUtil.send(to, cc, bcc, mail);

	}

}
