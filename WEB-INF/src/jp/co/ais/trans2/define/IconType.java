package jp.co.ais.trans2.define;

/** Icon画像 */
public enum IconType {

	/** 複写 */
	COPY,

	/** 削除 */
	DELETE,

	/** 修正 */
	EDIT,

	/** エクセル */
	EXCEL,

	/** 新規 */
	NEW,

	/** 辞書（パターン） */
	PATTERN,

	/** プレビュー */
	PREVIEW,

	/** 検索 */
	SEARCH,

	/** 確定 */
	SETTLE,

	/** 先頭へ(左端矢印) */
	ALLOW_FIRST,

	/** 前へ(左矢印) */
	ALLOW_PREVIOUS,

	/** 次へ(右矢印) */
	ALLOW_NEXT,

	/** 最後へ(右端矢印) */
	ALLOW_LAST,

	/** 添付ファイル */
	ATTACHMENT,

	/** 添付完了 */
	ATTACHMENT_COMPLETE,

	/** 履歴 */
	HISTORY,

	/** ヘルプ */
	HELP;

	/**
	 * Icon名を返す
	 * 
	 * @return Icon名
	 */
	public String getIconName() {
		return getIconName(this);
	}

	/**
	 * Icon名を返す
	 * 
	 * @param it Icon
	 * @return Icon名
	 */
	public static String getIconName(IconType it) {

		switch (it) {
			case COPY: // 複写
				return "images/copy.png";

			case DELETE: // 削除
				return "images/delete.png";

			case EDIT: // 修正
				return "images/edit.png";

			case EXCEL: // エクセル
				return "images/excel.png";

			case NEW: // 新規
				return "images/new.png";

			case PATTERN: // 辞書（パターン）
				return "images/pattern.png";

			case PREVIEW: // プレビュー
				return "images/preview.png";

			case SEARCH: // 検索
				return "images/search.png";

			case SETTLE: // 確定
				return "images/settle.png";

			case ALLOW_FIRST: // 左端矢印
				return "images/allow_first.png";

			case ALLOW_PREVIOUS: // 左矢印
				return "images/allow_previos.png";

			case ALLOW_NEXT: // 右矢印
				return "images/allow_next.png";

			case ALLOW_LAST: // 右端矢印
				return "images/allow_last.png";

			case ATTACHMENT: // 添付
				return "images/attachment.png";

			case ATTACHMENT_COMPLETE: // 添付完了
				return "images/attachment_complete.png";
			case HISTORY: // 履歴
				return "images/history.png";

			case HELP: // ヘルプ
				return "images/help.png";
			default:
				return null;
		}
	}
}