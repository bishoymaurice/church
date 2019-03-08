package church.ministry.ui.child;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import church.ministry.control.commons.CommonsHandler;
import church.ministry.control.commons.MyReceiver;
import church.ministry.control.ecode.EMapper;
import church.ministry.control.handler.RequestHandler;
import church.ministry.ui.ministry.CommonsUI;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.ProgressIndicator;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("deprecation")
class NewChildUI implements Serializable {

	private static final long serialVersionUID = 2957096979410801496L;

	protected VerticalLayout getLayout() {
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setCaption("����� ����");

		final TextField nameTextField = new TextField();
		nameTextField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		nameTextField.setMaxLength(50);
		nameTextField.setColumns(25);
		nameTextField.setCaption("�����");
		layout.addComponent(nameTextField);
		layout.setComponentAlignment(nameTextField, Alignment.TOP_RIGHT);

		layout.addComponent(CommonsUI.getSpaceLabel("10px", null, null));

		// ////////////////////////////////////////////////////////////////////////////////////////

		final HorizontalLayout addressLayout = new HorizontalLayout();
		addressLayout.setCaption("�������");
		layout.addComponent(addressLayout);
		layout.setComponentAlignment(addressLayout, Alignment.TOP_RIGHT);

		final TextField addressNumTextField = new TextField();
		addressNumTextField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		addressNumTextField.setMaxLength(10);
		addressNumTextField.setColumns(5);
		addressNumTextField.setCaption("���");
		addressLayout.addComponent(addressNumTextField);
		addressLayout.setComponentAlignment(addressNumTextField, Alignment.TOP_RIGHT);

		addressLayout.addComponent(CommonsUI.getSpaceLabel(null, "5px", null));

		final ComboBox addressStreetComboBox = new ComboBox();
		addressStreetComboBox.addStyleName(ValoTheme.COMBOBOX_SMALL);
		addressStreetComboBox.setNewItemsAllowed(true);
		addressStreetComboBox.setCaption("����");
		addressStreetComboBox.addFocusListener(new FocusListener() {
			private static final long serialVersionUID = 4617672963983221931L;

			public void focus(FocusEvent event) {
				String currentSelectedItem = null;
				if (addressStreetComboBox.getValue() != null) {
					currentSelectedItem = addressStreetComboBox.getValue().toString();
				}

				addressStreetComboBox.removeAllItems();
				ArrayList<String> allStreets = CommonsHandler.getAllStreets();
				for (int i = 0; i < allStreets.size(); i++) {
					addressStreetComboBox.addItem(allStreets.get(i));
				}

				if (currentSelectedItem != null) {
					addressStreetComboBox.addItem(currentSelectedItem);
					addressStreetComboBox.setValue(currentSelectedItem);
				}
			}
		});

		addressLayout.addComponent(addressStreetComboBox);
		addressLayout.setComponentAlignment(addressStreetComboBox, Alignment.TOP_RIGHT);

		addressLayout.addComponent(CommonsUI.getSpaceLabel(null, "5px", null));

		final ComboBox addressRegionComboBox = new ComboBox();
		addressRegionComboBox.addStyleName(ValoTheme.COMBOBOX_SMALL);
		addressRegionComboBox.setNewItemsAllowed(true);
		addressRegionComboBox.setCaption("�����");
		addressRegionComboBox.addFocusListener(new FocusListener() {
			private static final long serialVersionUID = 3779478301067878464L;

			public void focus(FocusEvent event) {
				String currentSelectedItem = null;
				if (addressRegionComboBox.getValue() != null) {
					currentSelectedItem = addressRegionComboBox.getValue().toString();
				}

				addressRegionComboBox.removeAllItems();
				ArrayList<String> allRegions = CommonsHandler.getAllRegions();
				for (int i = 0; i < allRegions.size(); i++) {
					addressRegionComboBox.addItem(allRegions.get(i));
				}

				if (currentSelectedItem != null) {
					addressRegionComboBox.addItem(currentSelectedItem);
					addressRegionComboBox.setValue(currentSelectedItem);
				}
			}
		});

		addressLayout.addComponent(addressRegionComboBox);
		addressLayout.setComponentAlignment(addressRegionComboBox, Alignment.TOP_RIGHT);

		addressLayout.addComponent(CommonsUI.getSpaceLabel(null, "5px", null));

		final ComboBox addressDistrictComboBox = new ComboBox();
		addressDistrictComboBox.addStyleName(ValoTheme.COMBOBOX_SMALL);
		addressDistrictComboBox.setNewItemsAllowed(true);
		addressDistrictComboBox.setCaption("��");
		addressDistrictComboBox.addFocusListener(new FocusListener() {
			private static final long serialVersionUID = 6845905160412594887L;

			public void focus(FocusEvent event) {
				String currentSelectedItem = null;
				if (addressDistrictComboBox.getValue() != null) {
					currentSelectedItem = addressDistrictComboBox.getValue().toString();
				}

				addressDistrictComboBox.removeAllItems();
				ArrayList<String> allDistricts = CommonsHandler.getAllDistricts();
				for (int i = 0; i < allDistricts.size(); i++) {
					addressDistrictComboBox.addItem(allDistricts.get(i));
				}

				if (currentSelectedItem != null) {
					addressDistrictComboBox.addItem(currentSelectedItem);
					addressDistrictComboBox.setValue(currentSelectedItem);
				}
			}
		});
		addressLayout.addComponent(addressDistrictComboBox);
		addressLayout.setComponentAlignment(addressDistrictComboBox, Alignment.TOP_RIGHT);

		addressLayout.addComponent(CommonsUI.getSpaceLabel(null, "5px", null));

		final TextField addressFreeTextField = new TextField();
		addressFreeTextField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		addressFreeTextField.setMaxLength(100);
		addressFreeTextField.setCaption("���� �������");
		addressLayout.addComponent(addressFreeTextField);
		addressLayout.setComponentAlignment(addressFreeTextField, Alignment.TOP_RIGHT);

		// ////////////////////////////////////////////////////////////////////////////////////////

		layout.addComponent(CommonsUI.getSpaceLabel("10px", null, null));

		final TextField phoneTextField = new TextField();
		phoneTextField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		phoneTextField.setMaxLength(50);
		phoneTextField.setCaption("������");
		layout.addComponent(phoneTextField);
		layout.setComponentAlignment(phoneTextField, Alignment.TOP_RIGHT);

		final TextField mobile1TextField = new TextField();
		mobile1TextField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		mobile1TextField.setMaxLength(50);
		mobile1TextField.setCaption("������ 1");
		layout.addComponent(mobile1TextField);
		layout.setComponentAlignment(mobile1TextField, Alignment.TOP_RIGHT);

		final TextField mobile2TextField = new TextField();
		mobile2TextField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		mobile2TextField.setMaxLength(50);
		mobile2TextField.setCaption("������ 2");
		layout.addComponent(mobile2TextField);
		layout.setComponentAlignment(mobile2TextField, Alignment.TOP_RIGHT);

		final DateField birthdayDateField = new DateField();
		birthdayDateField.setImmediate(true);
		birthdayDateField.addStyleName(ValoTheme.DATEFIELD_SMALL);
		birthdayDateField.setDateFormat("dd-MM-yyyy");
		birthdayDateField.setInvalidAllowed(false);
		birthdayDateField.setCaption("����� �������");
		layout.addComponent(birthdayDateField);
		layout.setComponentAlignment(birthdayDateField, Alignment.TOP_RIGHT);

		final TextField nationalIdTextField = new TextField();
		nationalIdTextField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		nationalIdTextField.setMaxLength(14);
		nationalIdTextField.setCaption("����� ������");
		layout.addComponent(nationalIdTextField);
		layout.setComponentAlignment(nationalIdTextField, Alignment.TOP_RIGHT);

		final TextField emailTextField = new TextField();
		emailTextField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		emailTextField.setMaxLength(50);
		emailTextField.setColumns(25);
		emailTextField.setCaption("�������");
		layout.addComponent(emailTextField);
		layout.setComponentAlignment(emailTextField, Alignment.TOP_RIGHT);

		final TextField educationTextField = new TextField();
		educationTextField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		educationTextField.setMaxLength(200);
		educationTextField.setColumns(25);
		educationTextField.setCaption("�������");
		layout.addComponent(educationTextField);
		layout.setComponentAlignment(educationTextField, Alignment.TOP_RIGHT);

		final TextField eccEducationTextField = new TextField();
		eccEducationTextField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		eccEducationTextField.setMaxLength(200);
		eccEducationTextField.setColumns(25);
		eccEducationTextField.setCaption("������� ������");
		layout.addComponent(eccEducationTextField);
		layout.setComponentAlignment(eccEducationTextField, Alignment.TOP_RIGHT);

		final TextField coursesTextField = new TextField();
		coursesTextField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		coursesTextField.setMaxLength(200);
		coursesTextField.setColumns(25);
		coursesTextField.setCaption("��������");
		layout.addComponent(coursesTextField);
		layout.setComponentAlignment(coursesTextField, Alignment.TOP_RIGHT);

		final TextField skillsTextField = new TextField();
		skillsTextField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		skillsTextField.setMaxLength(200);
		skillsTextField.setColumns(25);
		skillsTextField.setCaption("����� � �����");
		layout.addComponent(skillsTextField);
		layout.setComponentAlignment(skillsTextField, Alignment.TOP_RIGHT);

		final TextField notesTextField = new TextField();
		notesTextField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		notesTextField.setMaxLength(250);
		notesTextField.setColumns(25);
		notesTextField.setCaption("�������");
		layout.addComponent(notesTextField);
		layout.setComponentAlignment(notesTextField, Alignment.TOP_RIGHT);

		layout.addComponent(CommonsUI.getSpaceLabel("30px", null, null));

		Label status = new Label("������:");
		layout.addComponent(status);

		MyReceiver receiver = new MyReceiver();
		Upload upload = new Upload(null, receiver);
		upload.setImmediate(true);
		upload.setButtonCaption("���� ������");
		layout.addComponent(upload);
		layout.setComponentAlignment(upload, Alignment.TOP_RIGHT);

		ProgressIndicator pi = new ProgressIndicator();

		HorizontalLayout progressLayout = new HorizontalLayout();
		progressLayout.setSpacing(true);
		progressLayout.setVisible(false);
		progressLayout.addComponent(pi);
		progressLayout.setComponentAlignment(pi, Alignment.TOP_RIGHT);
		layout.addComponent(progressLayout);

		upload.addListener(new Upload.StartedListener() {
			private static final long serialVersionUID = 2744965515647487224L;

			public void uploadStarted(StartedEvent event) {
				upload.setVisible(false);
				progressLayout.setVisible(true);
				pi.setValue(0f);
				pi.setPollingInterval(500);
				status.setValue("��� ��� ������ ... \"" + event.getFilename() + "\"");
			}
		});

		upload.addListener(new Upload.ProgressListener() {
			private static final long serialVersionUID = 5961467645759914944L;

			public void updateProgress(long readBytes, long contentLength) {
				pi.setValue(new Float(readBytes / (float) contentLength));
			}
		});

		upload.addListener(new Upload.SucceededListener() {
			private static final long serialVersionUID = -5474378009196140003L;

			public void uploadSucceeded(SucceededEvent event) {
				status.setValue("�� ������ ������ �����:  \"" + event.getFilename() + "\"");
			}
		});

		upload.addListener(new Upload.FailedListener() {
			private static final long serialVersionUID = 7814180570119294526L;

			public void uploadFailed(FailedEvent event) {
				status.setValue("��� ��� ��� ������");
			}
		});

		upload.addListener(new Upload.FinishedListener() {
			private static final long serialVersionUID = 5885634757866896466L;

			public void uploadFinished(FinishedEvent event) {
				progressLayout.setVisible(false);
				upload.setVisible(true);
			}
		});

		layout.addComponent(CommonsUI.getSpaceLabel("40px", null, null));

		final HorizontalLayout buttonsLayout = new HorizontalLayout();
		layout.addComponent(buttonsLayout);
		layout.setComponentAlignment(buttonsLayout, Alignment.TOP_RIGHT);

		final Button submitButton = new Button();
		submitButton.setImmediate(true);
		submitButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		submitButton.addStyleName(ValoTheme.BUTTON_SMALL);
		submitButton.setIcon(FontAwesome.SAVE);
		submitButton.setWidth("100px");
		submitButton.setCaption(" �����");
		buttonsLayout.addComponent(submitButton);
		submitButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 5569230699947766441L;

			public void buttonClick(ClickEvent event) {
				HashMap<String, String> requestData = new HashMap<String, String>();
				final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

				requestData.put("name", nameTextField.getValue());
				requestData.put("addressNum", addressNumTextField.getValue());
				if (addressStreetComboBox.getValue() != null) {
					requestData.put("addressStreet", addressStreetComboBox.getValue().toString());
				} else {
					requestData.put("addressStreet", "null");
				}

				if (addressRegionComboBox.getValue() != null) {
					requestData.put("addressRegion", addressRegionComboBox.getValue().toString());
				} else {
					requestData.put("addressRegion", "null");
				}

				if (addressDistrictComboBox.getValue() != null) {
					requestData.put("addressDistrict", addressDistrictComboBox.getValue().toString());
				} else {
					requestData.put("addressDistrict", "null");
				}

				requestData.put("addressFree", addressFreeTextField.getValue());
				requestData.put("phone", phoneTextField.getValue());
				requestData.put("mobile1", mobile1TextField.getValue());
				requestData.put("mobile2", mobile2TextField.getValue());
				if (birthdayDateField.getValue() != null) {
					requestData.put("birthday", dateFormatter.format(birthdayDateField.getValue()));
				} else {
					requestData.put("birthday", "null");
				}
				requestData.put("email", emailTextField.getValue());
				requestData.put("nationalId", nationalIdTextField.getValue());
				requestData.put("education", educationTextField.getValue());
				requestData.put("eccEducation", eccEducationTextField.getValue());
				requestData.put("courses", coursesTextField.getValue());
				requestData.put("skills", skillsTextField.getValue());
				requestData.put("notes", notesTextField.getValue());

				int result = RequestHandler.handleUpdateRequest("newChild", requestData);

				if (result == EMapper.ECODE_SUCCESS) {
					CommonsUI.clearComponentValue(nameTextField, addressNumTextField, addressFreeTextField,
							phoneTextField, mobile1TextField, mobile2TextField, emailTextField,
							nationalIdTextField, educationTextField, eccEducationTextField, coursesTextField,
							skillsTextField, notesTextField);
					CommonsUI.clearComponentValue(addressStreetComboBox, addressRegionComboBox,
							addressDistrictComboBox);
					CommonsUI.clearComponentValue(birthdayDateField);
				}

				Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));
			}
		});

		buttonsLayout.addComponent(CommonsUI.getSpaceLabel(null, "10px", null));

		final Button cancelButton = new Button();
		cancelButton.setImmediate(true);
		cancelButton.addStyleName(ValoTheme.BUTTON_DANGER);
		cancelButton.addStyleName(ValoTheme.BUTTON_SMALL);
		cancelButton.setIcon(FontAwesome.ERASER);
		cancelButton.setWidth("100px");
		cancelButton.setCaption(" �����");
		buttonsLayout.addComponent(cancelButton);
		cancelButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 5569230699947766441L;

			public void buttonClick(ClickEvent event) {
				CommonsUI.clearComponentValue(nameTextField, addressNumTextField, addressFreeTextField,
						phoneTextField, mobile1TextField, mobile2TextField, emailTextField,
						nationalIdTextField, educationTextField, eccEducationTextField, coursesTextField,
						skillsTextField, notesTextField);
				CommonsUI.clearComponentValue(addressStreetComboBox, addressRegionComboBox,
						addressDistrictComboBox);
				CommonsUI.clearComponentValue(birthdayDateField);
			}
		});

		layout.addComponent(CommonsUI.getSpaceLabel("50px", null, null));

		return layout;
	}
}
