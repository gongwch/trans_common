package jp.co.ais.trans.master.logic;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * プログラムマスタビジネスロジック
 * 
 * @author 細谷
 */
public interface ProgramLogic {

	/**
	 * 会社コードでプログラムリストを習得
	 * 
	 * @param kaiCode 会社コード
	 * @return List<PRG_MST> プログラムlist
	 */
	public List<PRG_MST> searchProgramList(String kaiCode);

}
