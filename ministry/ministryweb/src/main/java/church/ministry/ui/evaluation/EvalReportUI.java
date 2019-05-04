package church.ministry.ui.evaluation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import church.ministry.control.commons.CommonsHandler;
import church.ministry.control.ecode.EMapper;
import church.ministry.control.log.Logger;
import church.ministry.control.minister.EvaluationHandler;
import church.ministry.control.reports.MinisterEvalReportHandler;
import church.ministry.model.file.FileAccess;
import church.ministry.ui.ministry.CommonsUI;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.themes.ValoTheme;

public class EvalReportUI implements Serializable {

	private static final long serialVersionUID = 1210083967196896872L;

	@SuppressWarnings("deprecation")
	public VerticalLayout getLayout() {
		final VerticalLayout reportLayout = new VerticalLayout();
		reportLayout.setMargin(true);
		reportLayout.setCaption("«· ﬁ«—Ì—");

		ComboBox itemComboBox = new ComboBox();
		itemComboBox.addStyleName(ValoTheme.COMBOBOX_ALIGN_RIGHT);
		itemComboBox.addStyleName(ValoTheme.COMBOBOX_SMALL);
		itemComboBox.setNewItemsAllowed(false);
		itemComboBox.setNullSelectionAllowed(false);
		itemComboBox.setTextInputAllowed(false);
		itemComboBox.setWidth("350px");
		itemComboBox.setCaption("≈Œ — «·›∆…:");

		String[] itemNames = new String[] { "«· —»Ì… «·ﬂ‰”Ì…", "ﬁÿ«⁄", "√”—…", "’›", "›’·" };

		for (String itemName : itemNames) {
			itemComboBox.addItem(itemName);
		}

		itemComboBox.setValue(itemNames[0]);
		reportLayout.addComponent(itemComboBox);
		reportLayout.setComponentAlignment(itemComboBox, Alignment.TOP_RIGHT);

		reportLayout.addComponent(CommonsUI.getSpaceLabel(null, null, null));

		ComboBox valueComboBox = new ComboBox();
		valueComboBox.addStyleName(ValoTheme.COMBOBOX_ALIGN_RIGHT);
		valueComboBox.addStyleName(ValoTheme.COMBOBOX_SMALL);
		valueComboBox.setEnabled(false);
		valueComboBox.setNewItemsAllowed(false);
		valueComboBox.setNullSelectionAllowed(false);
		valueComboBox.setWidth("350px");
		valueComboBox.setCaption("≈Œ — «·«”„:");
		reportLayout.addComponent(valueComboBox);
		reportLayout.setComponentAlignment(valueComboBox, Alignment.TOP_RIGHT);

		DateField fromDateField = new DateField();
		fromDateField.setImmediate(true);
		fromDateField.addStyleName(ValoTheme.DATEFIELD_SMALL);
		fromDateField.setDateFormat("dd-MM-yyyy");
		fromDateField.setInvalidAllowed(false);
		fromDateField.setCaption("„‰:");
		reportLayout.addComponent(fromDateField);
		reportLayout.setComponentAlignment(fromDateField, Alignment.TOP_RIGHT);

		DateField toDateField = new DateField();
		toDateField.setImmediate(true);
		toDateField.addStyleName(ValoTheme.DATEFIELD_SMALL);
		toDateField.setDateFormat("dd-MM-yyyy");
		toDateField.setInvalidAllowed(false);
		toDateField.setCaption("≈·Ï:");
		reportLayout.addComponent(toDateField);
		reportLayout.setComponentAlignment(toDateField, Alignment.TOP_RIGHT);

		reportLayout.addComponent(CommonsUI.getSpaceLabel(null, null, null));

		HorizontalLayout hLayout = new HorizontalLayout();
		reportLayout.addComponent(hLayout);
		reportLayout.setComponentAlignment(hLayout, Alignment.TOP_RIGHT);

		Button downloadButton = new Button(" ‰“Ì·  ﬁ—Ì—");
		downloadButton.setWidth("100");
		hLayout.addComponent(downloadButton);

		hLayout.addComponent(CommonsUI.getSpaceLabel(null, "10", null));

		itemComboBox.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = -1471064111812723345L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (itemComboBox.getValue().toString().equals(itemNames[0])) {
					valueComboBox.removeAllItems();
					valueComboBox.setEnabled(false);
				}

				else if (itemComboBox.getValue().toString().equals(itemNames[1])) {
					ArrayList<String> result = CommonsHandler.getAllSections();
					valueComboBox.removeAllItems();
					for (int i = 0; i < result.size(); i++) {
						valueComboBox.addItem(result.get(i));
					}
					valueComboBox.setEnabled(true);
				}

				else if (itemComboBox.getValue().toString().equals(itemNames[2])) {
					ArrayList<String> result = CommonsHandler.getAllFamilies();
					valueComboBox.removeAllItems();
					for (int i = 0; i < result.size(); i++) {
						valueComboBox.addItem(result.get(i));
					}
					valueComboBox.setEnabled(true);
				}

				else if (itemComboBox.getValue().toString().equals(itemNames[3])) {
					ArrayList<String> result = CommonsHandler.getAllYears();
					valueComboBox.removeAllItems();
					for (int i = 0; i < result.size(); i++) {
						valueComboBox.addItem(result.get(i));
					}
					valueComboBox.setEnabled(true);
				}

				else if (itemComboBox.getValue().toString().equals(itemNames[4])) {
					ArrayList<String> result = CommonsHandler.getAllClasses();
					valueComboBox.removeAllItems();
					for (int i = 0; i < result.size(); i++) {
						valueComboBox.addItem(result.get(i));
					}
					valueComboBox.setEnabled(true);
				}
			}
		});

		fromDateField.addListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1200529581865778526L;

			public void valueChange(ValueChangeEvent event) {
				if (fromDateField.getValue() != null) {
					if (!fromDateField.getValue().toString().toLowerCase().contains("fri")) {
						fromDateField.setValue(null);
					}
				} else {
					fromDateField.setValue(null);
				}
			}
		});

		toDateField.addListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1200529581865778526L;

			public void valueChange(ValueChangeEvent event) {
				if (toDateField.getValue() != null) {
					if (!toDateField.getValue().toString().toLowerCase().contains("fri")) {
						toDateField.setValue(null);
					}
				} else {
					toDateField.setValue(null);
				}
			}
		});

		downloadButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 5569230699947766441L;

			public void buttonClick(ClickEvent event) {
				final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

				ArrayList<ArrayList<String>> evaluationResult = EvaluationHandler.getEvaluationReport(
						String.valueOf(itemComboBox.getValue()), String.valueOf(valueComboBox.getValue()),
						dateFormatter.format(fromDateField.getValue()),
						dateFormatter.format(toDateField.getValue()));

				if (evaluationResult == null) {
					Notification.show(EMapper.getEDesc(EMapper.ECODE_FAILURE),
							EMapper.getNType(EMapper.ECODE_FAILURE));
					return;
				}

				String pdfFilePath = MinisterEvalReportHandler.savePDF(evaluationResult,
						dateFormatter.format(fromDateField.getValue()),
						dateFormatter.format(toDateField.getValue()),
						String.valueOf(itemComboBox.getValue()), String.valueOf(valueComboBox.getValue()));

				String csvFilePath = MinisterEvalReportHandler.saveCSV(evaluationResult,
						dateFormatter.format(fromDateField.getValue()),
						dateFormatter.format(toDateField.getValue()),
						String.valueOf(itemComboBox.getValue()), String.valueOf(valueComboBox.getValue()));

				String pdfFileName = pdfFilePath.substring(pdfFilePath.lastIndexOf("/") + 1,
						pdfFilePath.length());

				String csvFileName = csvFilePath.substring(pdfFilePath.lastIndexOf("/") + 1,
						pdfFilePath.length());

				StreamResource pdfResource = createResource(pdfFilePath, pdfFileName);
				StreamResource csvResource = createResource(csvFilePath, csvFileName);

				VerticalLayout vLayout = new VerticalLayout();
				vLayout.setMargin(true);

				final Label label = new Label();
				label.setCaption("≈÷€ÿ „Ê«›ﬁ · ‰“Ì· «· ﬁ—Ì—");
				vLayout.addComponent(label);
				vLayout.setComponentAlignment(label, Alignment.TOP_RIGHT);

				vLayout.addComponent(CommonsUI.getSpaceLabel("20", null, null));

				HorizontalLayout hLayout = new HorizontalLayout();
				vLayout.addComponent(hLayout);
				vLayout.setComponentAlignment(hLayout, Alignment.TOP_LEFT);

				Button pdfEnterButton = new Button(" ‰“Ì· PDF");
				hLayout.addComponent(pdfEnterButton);

				hLayout.addComponent(CommonsUI.getSpaceLabel(null, "10px", null));

				Button csvEnterButton = new Button(" ‰“Ì· CSV");
				hLayout.addComponent(csvEnterButton);

				FileDownloader pdfFileDownloader = new FileDownloader(pdfResource);
				pdfFileDownloader.extend(pdfEnterButton);

				FileDownloader csvFileDownloader = new FileDownloader(csvResource);
				csvFileDownloader.extend(csvEnterButton);

				hLayout.addComponent(CommonsUI.getSpaceLabel(null, "10px", null));

				Button cancelButton = new Button("≈·€«¡");
				hLayout.addComponent(cancelButton);

				Window confirmWindow = new Window();
				confirmWindow.center();
				confirmWindow.setModal(true);
				confirmWindow.setWidth("500");
				confirmWindow.setHeight("180");
				confirmWindow.setResizable(false);
				confirmWindow.setCaption("  ‰“Ì·");
				confirmWindow.setIcon(FontAwesome.DOWNLOAD);
				confirmWindow.setContent(vLayout);

				pdfEnterButton.addClickListener(new Button.ClickListener() {
					private static final long serialVersionUID = 5325311693662832508L;

					public void buttonClick(ClickEvent event) {
						pdfEnterButton.setEnabled(false);
					}
				});

				cancelButton.addClickListener(new Button.ClickListener() {
					private static final long serialVersionUID = 5325311693662832508L;

					public void buttonClick(ClickEvent event) {
						confirmWindow.close();
					}
				});

				confirmWindow.addListener(new Window.CloseListener() {
					private static final long serialVersionUID = 8546870775234950520L;

					public void windowClose(CloseEvent e) {
						FileAccess.deleteFile(pdfFilePath);
					}
				});

				UI.getCurrent().addWindow(confirmWindow);

			}
		});

		return reportLayout;
	}

	private static StreamResource createResource(String fileInputPath, String fileDownloadPath) {
		return new StreamResource(new StreamSource() {
			private static final long serialVersionUID = 5916327700706076953L;

			@Override
			public InputStream getStream() {
				try {
					InputStream inputStream = new FileInputStream(fileInputPath);
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

					int data;
					while ((data = inputStream.read()) >= 0) {
						outputStream.write(data);
					}

					inputStream.close();
					return new ByteArrayInputStream(outputStream.toByteArray());
				} catch (Exception e) {
					Logger.exception(e);
					return null;
				}
			}
		}, fileDownloadPath);
	}

}
