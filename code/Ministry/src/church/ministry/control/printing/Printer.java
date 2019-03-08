package church.ministry.control.printing;

import java.awt.HeadlessException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import church.ministry.control.ecode.EMapper;
import church.ministry.control.log.Logger;

/**
 *
 * @author JBWB
 */
public class Printer implements Serializable {

	private static final long serialVersionUID = -4526231372416783363L;

	public static int print(String[] docs) {
		try {

			ArrayList<PDDocument> documents = new ArrayList<>();

			PrinterJob job = PrinterJob.getPrinterJob();

			if (job != null) {

				for (String doc : docs) {
					PDDocument document = PDDocument.load(new File(doc));
					documents.add(document);
				}

				for (PDDocument doc : documents) {
					job.setPageable(new PDFPageable(doc));
					job.print();
				}

				for (PDDocument doc : documents) {
					doc.close();
				}

			} else {
				Logger.error("Could not find printing service");
				return EMapper.ECODE_PRINTING_FAILURE;
			}

			return EMapper.ECODE_SUCCESS;

		} catch (Exception e) {
			Logger.exception(e);
			return EMapper.ECODE_PRINTING_FAILURE;
		}
	}

	public static int print_old(String[] docs) throws FileNotFoundException, PrintException {
		try {
			PrintRequestAttributeSet printSet = new HashPrintRequestAttributeSet();

			DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;

			PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();

			printSet.add(OrientationRequested.LANDSCAPE);

			if (defaultService != null) {
				for (String doc : docs) {

					DocPrintJob job = defaultService.createPrintJob();

					FileInputStream fis = new FileInputStream(doc);

					DocAttributeSet das = new HashDocAttributeSet();

					Doc document = new SimpleDoc(fis, flavor, das);

					PrintJobWatcher pjDone = new PrintJobWatcher(job);

					job.print(document, printSet);

					pjDone.waitForDone();

					fis.close();

					Logger.info(doc + " had been sent to printer successfully");
				}
			} else {
				Logger.error("Default printing service could not be found!");
			}
			return EMapper.ECODE_SUCCESS;
		} catch (HeadlessException | FileNotFoundException | PrintException e) {
			Logger.exception(e);
			return EMapper.ECODE_PRINTING_FAILURE;
		} catch (IOException e) {
			Logger.exception(e);
			return EMapper.ECODE_PRINTING_FAILURE;
		} catch (Exception e) {
			Logger.exception(e);
			return EMapper.ECODE_PRINTING_FAILURE;
		}
	}
}

class PrintJobWatcher {
	boolean done = false;

	PrintJobWatcher(DocPrintJob job) {
		job.addPrintJobListener(new PrintJobAdapter() {
			public void printJobCanceled(PrintJobEvent pje) {
				synchronized (PrintJobWatcher.this) {
					done = true;
					PrintJobWatcher.this.notify();
				}
			}

			public void printJobCompleted(PrintJobEvent pje) {
				synchronized (PrintJobWatcher.this) {
					done = true;
					PrintJobWatcher.this.notify();
				}
			}

			public void printJobFailed(PrintJobEvent pje) {
				synchronized (PrintJobWatcher.this) {
					done = true;
					PrintJobWatcher.this.notify();
				}
			}

			public void printJobNoMoreEvents(PrintJobEvent pje) {
				synchronized (PrintJobWatcher.this) {
					done = true;
					PrintJobWatcher.this.notify();
				}
			}
		});
	}

	public synchronized void waitForDone() {
		try {
			while (!done) {
				wait();
			}
		} catch (InterruptedException e) {
		}
	}
}