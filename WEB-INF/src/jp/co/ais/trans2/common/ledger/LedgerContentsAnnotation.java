package jp.co.ais.trans2.common.ledger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ���[�̖ڎ��A�m�e�[�V����<br>
 * �L�[�Ɏw�肵���ԍ����ɖڎ���������
 * @author AIS
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LedgerContentsAnnotation {

	/**
	 * �L�[
	 * @return
	 */
	int key();

}
