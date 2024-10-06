package jp.co.ais.trans2.common.ledger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 帳票の目次アノテーション<br>
 * キーに指定した番号順に目次生成する
 * @author AIS
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LedgerContentsAnnotation {

	/**
	 * キー
	 * @return
	 */
	int key();

}
