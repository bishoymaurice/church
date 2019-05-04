package church.ministry.control.commons;

import java.io.IOException;
import java.io.OutputStream;

import church.ministry.control.log.Logger;

import com.vaadin.ui.Upload.Receiver;

public class MyReceiver implements Receiver {

	private static final long serialVersionUID = -7996224823701709091L;

	private String fileName;
	private String mtype;
	private boolean sleep;
	private int total = 0;

	public OutputStream receiveUpload(String filename, String mimetype) {
		fileName = filename;
		mtype = mimetype;
		return new OutputStream() {
			@Override
			public void write(int b) throws IOException {
				total++;
				if (sleep && total % 10000 == 0) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						Logger.exception(e);
					}
				}
			}
		};
	}

	public String getFileName() {
		return fileName;
	}

	public String getMimeType() {
		return mtype;
	}

	public void setSlow(boolean value) {
		sleep = value;
	}

}
