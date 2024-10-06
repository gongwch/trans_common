package jp.co.ais.trans2.define;

/**
 * “`•[‚É‘Î‚·‚éŒ ŒÀ
 * 
 * @author AIS
 */
public enum SlipRole {
	/** ‘S‚Ä */
	ALL(0),
	
	/** “ü—ÍÒ */
	SELF(1),
	
	/** Š‘®•”–å */
	DEPARTMENT(2);

	/** ’l */
	public int value;

	private SlipRole(int value) {
		this.value = value;
	}

	/**
	 * “`•[‚É‘Î‚·‚éŒ ŒÀ‚ğ•Ô‚·
	 * 
	 * @param slipRole “`•[‚É‘Î‚·‚éŒ ŒÀ
	 * @return “`•[‚É‘Î‚·‚éŒ ŒÀ
	 */
	public static SlipRole getSlipRole(int slipRole) {

		for (SlipRole em : values()) {
			if (em.value == slipRole) {
				return em;
			}
		}

		return null;
	}

	/**
	 * “`•[‚É‘Î‚·‚éŒ ŒÀ–¼Ì‚ğ•Ô‚·
	 * 
	 * @param slipRole “`•[‚É‘Î‚·‚éŒ ŒÀ
	 * @return “`•[‚É‘Î‚·‚éŒ ŒÀ–¼Ì
	 */
	public static String getSlipRoleName(SlipRole slipRole) {

		if (slipRole == null) {
			return null;
		}

		switch (slipRole) {
			case ALL:
				// ‘S‚Ä
				return "C00310";

			case SELF:
				// “ü—ÍÒ
				return "C01278";
				
			case DEPARTMENT:
				// Š‘®•”–å
				return "C00295";

			default:
				return null;

		}
	}

}
