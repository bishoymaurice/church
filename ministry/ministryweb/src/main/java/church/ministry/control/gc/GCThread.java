package church.ministry.control.gc;

import church.ministry.control.log.Logger;

public class GCThread implements Runnable {
	@Override
	public void run() {

		while (true) {

			System.gc();

			try {

				Thread.sleep(20000);

			} catch (InterruptedException e) {
				Logger.exception(e);
			}
		}
	}
}
