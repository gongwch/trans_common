package jp.co.ais.trans2.common.viewer;

/**
 * Viewer��Factory
 * @author AIS
 *
 */
public class TViewerFactory {

	public static TViewer getViewer() {
		return new TFrameViewer();
	}

}
