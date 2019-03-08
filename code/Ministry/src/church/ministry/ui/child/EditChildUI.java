package church.ministry.ui.child;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import church.ministry.control.child.ChildHandler;
import church.ministry.control.commons.CommonsHandler;
import church.ministry.control.ecode.EMapper;
import church.ministry.control.handler.RequestHandler;
import church.ministry.control.log.Logger;
import church.ministry.ui.ministry.CommonsUI;

import com.vaadin.data.Property.ReadOnlyException;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.converter.Converter.ConversionException;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

class EditChildUI implements Serializable {

	private static final long serialVersionUID = 1349965210829530773L;

	protected VerticalLayout getLayout() {
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setCaption("�����");

		final ComboBox allNamesComboBox = new ComboBox();
		allNamesComboBox.setCaption("���� �����");
		allNamesComboBox.addStyleName(ValoTheme.COMBOBOX_ALIGN_RIGHT);
		allNamesComboBox.addStyleName(ValoTheme.COMBOBOX_SMALL);
		allNamesComboBox.setNewItemsAllowed(false);
		allNamesComboBox.setWidth("350px");
		allNamesComboBox.addFocusListener(new FocusListener() {
			private static final long serialVersionUID = 8721337946386845992L;

			public void focus(FocusEvent event) {

				String currentSelectedItem = null;
				if (allNamesComboBox.getValue() != null) {
					currentSelectedItem = allNamesComboBox.getValue().toString();
				}

				allNamesComboBox.removeAllItems();
				ArrayList<String> allNames = CommonsHandler.getAllNamesByType("child");
				for (int i = 0; i < allNames.size(); i++) {
					allNamesComboBox.addItem(allNames.get(i));
				}

				if (currentSelectedItem != null) {
					allNamesComboBox.addItem(currentSelectedItem);
					allNamesComboBox.setValue(currentSelectedItem);
				}
			}
		});
		layout.addComponent(allNamesComboBox);
		layout.setComponentAlignment(allNamesComboBox, Alignment.TOP_RIGHT);

		layout.addComponent(CommonsUI.getSpaceLabel("10px", null, null));

		final TextField idTextField = new TextField();
		idTextField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		idTextField.setEnabled(false);
		idTextField.setMaxLength(50);
		idTextField.setCaption("�����");
		layout.addComponent(idTextField);
		layout.setComponentAlignment(idTextField, Alignment.TOP_RIGHT);

		final TextField statusTextField = new TextField();
		statusTextField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		statusTextField.setEnabled(false);
		statusTextField.setMaxLength(50);
		statusTextField.setCaption("������");
		layout.addComponent(statusTextField);
		layout.setComponentAlignment(statusTextField, Alignment.TOP_RIGHT);

		final TextField nameTextField = new TextField();
		nameTextField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		nameTextField.setMaxLength(50);
		nameTextField.setColumns(25);
		nameTextField.setCaption("�����");
		layout.addComponent(nameTextField);
		layout.setComponentAlignment(nameTextField, Alignment.TOP_RIGHT);

		layout.addComponent(CommonsUI.getSpaceLabel("13px", null, null));

		final HorizontalLayout childPositionInMinistryHoriLayout = new HorizontalLayout();
		childPositionInMinistryHoriLayout.setCaption("���� ������� �� ������:");
		layout.addComponent(childPositionInMinistryHoriLayout);
		layout.setComponentAlignment(childPositionInMinistryHoriLayout, Alignment.TOP_RIGHT);

		Label childPositionInMinistryLabel = new Label("", ContentMode.HTML);
		childPositionInMinistryHoriLayout.addComponent(childPositionInMinistryLabel);

		childPositionInMinistryHoriLayout.addComponent(CommonsUI.getSpaceLabel(null, "13px", null));

		Button changeSubclassButton = new Button("����� ��������");
		changeSubclassButton.addStyleName(ValoTheme.BUTTON_SMALL);
		changeSubclassButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
		changeSubclassButton.setImmediate(true);
		changeSubclassButton.addClickListener(event -> {
			VerticalLayout vLayout = new VerticalLayout();
			vLayout.setMargin(true);

			final ComboBox allSubclassesComboBox = new ComboBox();
			allSubclassesComboBox.setCaption("���� ��� ��������");
			allSubclassesComboBox.addStyleName(ValoTheme.COMBOBOX_ALIGN_RIGHT);
			allSubclassesComboBox.addStyleName(ValoTheme.COMBOBOX_SMALL);
			allSubclassesComboBox.setNewItemsAllowed(false);
			allSubclassesComboBox.setWidth("350px");
			allSubclassesComboBox.addFocusListener(new FocusListener() {
				private static final long serialVersionUID = 8721337946386845992L;

				public void focus(FocusEvent event) {

					String currentSelectedItem = null;
					if (allSubclassesComboBox.getValue() != null) {
						currentSelectedItem = allSubclassesComboBox.getValue().toString();
					}

					allSubclassesComboBox.removeAllItems();

					String subclassName = CommonsHandler.getChildSubclass(nameTextField.getValue());

					ArrayList<String> allNames = CommonsHandler
							.getAllSubClassesExcludingSomeSubclass(subclassName);

					for (int i = 0; i < allNames.size(); i++) {
						allSubclassesComboBox.addItem(allNames.get(i));
					}

					if (currentSelectedItem != null) {
						allSubclassesComboBox.addItem(currentSelectedItem);
						allSubclassesComboBox.setValue(currentSelectedItem);
					}
				}
			});
			vLayout.addComponent(allSubclassesComboBox);
			vLayout.setComponentAlignment(allSubclassesComboBox, Alignment.TOP_RIGHT);

			vLayout.addComponent(CommonsUI.getSpaceLabel("20", null, null));

			HorizontalLayout hLayout = new HorizontalLayout();
			vLayout.addComponent(hLayout);
			vLayout.setComponentAlignment(hLayout, Alignment.TOP_LEFT);

			Button enterButton = new Button("�����");
			hLayout.addComponent(enterButton);

			hLayout.addComponent(CommonsUI.getSpaceLabel(null, "10px", null));

			Button cancelButton = new Button("�����");
			hLayout.addComponent(cancelButton);

			Window confirmWindow = new Window();
			confirmWindow.center();
			confirmWindow.setModal(true);
			confirmWindow.setWidth("500");
			confirmWindow.setHeight("200");
			confirmWindow.setResizable(false);
			confirmWindow.setCaption(" ����� ���������");
			confirmWindow.setIcon(FontAwesome.INFO);
			confirmWindow.setContent(vLayout);

			enterButton.addClickListener(new Button.ClickListener() {
				private static final long serialVersionUID = 5325311693662832508L;

				public void buttonClick(ClickEvent event) {

					confirmWindow.close();

					HashMap<String, String> requestData = new HashMap<String, String>();
					requestData.put("subClassName", allSubclassesComboBox.getValue().toString());
					requestData.put("childName", nameTextField.getValue());

					int result = RequestHandler.handleUpdateRequest("assignChild", requestData);

					String newPosiotion = ChildHandler.getChildPositionInMinistry(nameTextField.getValue());

					childPositionInMinistryLabel.setCaption(newPosiotion);

					Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));
				}
			});

			cancelButton.addClickListener(new Button.ClickListener() {
				private static final long serialVersionUID = 5325311693662832508L;

				public void buttonClick(ClickEvent event) {
					confirmWindow.close();
				}
			});

			UI.getCurrent().addWindow(confirmWindow);
		});
		childPositionInMinistryHoriLayout.addComponent(changeSubclassButton);

		layout.addComponent(CommonsUI.getSpaceLabel("13px", null, null));

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
		ArrayList<String> allStreets = CommonsHandler.getAllStreets();
		for (int i = 0; i < allStreets.size(); i++) {
			addressStreetComboBox.addItem(allStreets.get(i));
		}
		addressStreetComboBox.addFocusListener(new FocusListener() {
			private static final long serialVersionUID = -7969593057526583093L;

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
		ArrayList<String> allRegions = CommonsHandler.getAllRegions();
		for (int i = 0; i < allRegions.size(); i++) {
			addressRegionComboBox.addItem(allRegions.get(i));
		}
		addressRegionComboBox.addFocusListener(new FocusListener() {
			private static final long serialVersionUID = -3191948816313650108L;

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
		ArrayList<String> allDistricts = CommonsHandler.getAllDistricts();
		for (int i = 0; i < allDistricts.size(); i++) {
			addressDistrictComboBox.addItem(allDistricts.get(i));
		}
		addressDistrictComboBox.addFocusListener(new FocusListener() {
			private static final long serialVersionUID = -2475430140548475983L;

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

		layout.addComponent(CommonsUI.getSpaceLabel("20px", null, null));

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

		buttonsLayout.addComponent(CommonsUI.getSpaceLabel(null, "10px", null));

		final Button cancelButton = new Button();
		cancelButton.setImmediate(true);
		cancelButton.addStyleName(ValoTheme.BUTTON_DANGER);
		cancelButton.addStyleName(ValoTheme.BUTTON_SMALL);
		cancelButton.setIcon(FontAwesome.ERASER);
		cancelButton.setWidth("100px");
		cancelButton.setCaption(" �����");
		buttonsLayout.addComponent(cancelButton);

		buttonsLayout.addComponent(CommonsUI.getSpaceLabel(null, "10px", null));

		final Button deleteButton = new Button();
		deleteButton.setImmediate(true);
		deleteButton.addStyleName(ValoTheme.BUTTON_DANGER);
		deleteButton.addStyleName(ValoTheme.BUTTON_SMALL);
		deleteButton.setIcon(FontAwesome.TRASH_O);
		deleteButton.setWidth("100px");
		deleteButton.setCaption(" ���");
		buttonsLayout.addComponent(deleteButton);

		buttonsLayout.addComponent(CommonsUI.getSpaceLabel(null, "10px", null));

		final Button reactivateButton = new Button();
		reactivateButton.setImmediate(true);
		reactivateButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		reactivateButton.addStyleName(ValoTheme.BUTTON_SMALL);
		reactivateButton.setIcon(FontAwesome.RECYCLE);
		reactivateButton.setWidth("100px");
		reactivateButton.setCaption(" ����� �����");
		buttonsLayout.addComponent(reactivateButton);

		layout.addComponent(CommonsUI.getSpaceLabel("50px", null, null));

		// ///////////////////////
		CommonsUI.disableComponent(nameTextField, addressNumTextField, addressFreeTextField, phoneTextField,
				mobile1TextField, mobile2TextField, emailTextField, nationalIdTextField, educationTextField,
				eccEducationTextField, coursesTextField, skillsTextField, notesTextField);
		CommonsUI.clearComponentValue(childPositionInMinistryLabel);
		CommonsUI.disableComponent(addressStreetComboBox, addressRegionComboBox, addressDistrictComboBox);
		CommonsUI.disableComponent(birthdayDateField);
		CommonsUI.disableComponent(submitButton, cancelButton, deleteButton, reactivateButton,
				changeSubclassButton);
		// ///////////////////////

		submitButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 5569230699947766441L;

			public void buttonClick(ClickEvent event) {
				HashMap<String, String> requestData = new HashMap<String, String>();
				final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

				requestData.put("id", idTextField.getValue());
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

				int result = RequestHandler.handleUpdateRequest("updateChild", requestData);

				if (result == EMapper.ECODE_SUCCESS) {
					CommonsUI.clearComponentValue(idTextField, nameTextField, addressNumTextField,
							addressFreeTextField, phoneTextField, mobile1TextField, mobile2TextField,
							emailTextField, nationalIdTextField, educationTextField, eccEducationTextField,
							coursesTextField, skillsTextField, notesTextField, statusTextField);
					CommonsUI.clearComponentValue(childPositionInMinistryLabel);
					CommonsUI.clearComponentValue(addressStreetComboBox, addressRegionComboBox,
							addressDistrictComboBox, allNamesComboBox);
					CommonsUI.clearComponentValue(birthdayDateField);

					CommonsUI.disableComponent(nameTextField, addressNumTextField, addressFreeTextField,
							phoneTextField, mobile1TextField, mobile2TextField, emailTextField,
							nationalIdTextField, educationTextField, eccEducationTextField, coursesTextField,
							skillsTextField, notesTextField);
					CommonsUI.disableComponent(addressStreetComboBox, addressRegionComboBox,
							addressDistrictComboBox);
					CommonsUI.disableComponent(birthdayDateField);
					CommonsUI.disableComponent(submitButton, cancelButton, deleteButton, reactivateButton,
							changeSubclassButton);
				}

				Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));
			}
		});

		cancelButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 5569230699947766441L;

			public void buttonClick(ClickEvent event) {
				CommonsUI.clearComponentValue(idTextField, nameTextField, addressNumTextField,
						addressFreeTextField, phoneTextField, mobile1TextField, mobile2TextField,
						emailTextField, nationalIdTextField, educationTextField, eccEducationTextField,
						coursesTextField, skillsTextField, notesTextField, statusTextField);
				CommonsUI.clearComponentValue(addressStreetComboBox, addressRegionComboBox,
						addressDistrictComboBox, allNamesComboBox);
				CommonsUI.clearComponentValue(childPositionInMinistryLabel);
				CommonsUI.clearComponentValue(birthdayDateField);

				CommonsUI.disableComponent(nameTextField, addressNumTextField, addressFreeTextField,
						phoneTextField, mobile1TextField, mobile2TextField, emailTextField,
						nationalIdTextField, educationTextField, eccEducationTextField, coursesTextField,
						skillsTextField, notesTextField);
				CommonsUI.disableComponent(addressStreetComboBox, addressRegionComboBox,
						addressDistrictComboBox);
				CommonsUI.disableComponent(birthdayDateField);
				CommonsUI.disableComponent(submitButton, cancelButton, deleteButton, reactivateButton,
						changeSubclassButton);
			}
		});

		deleteButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 5569230699947766441L;

			public void buttonClick(ClickEvent event) {

				VerticalLayout confirmVerticalLayout = new VerticalLayout();
				confirmVerticalLayout.setMargin(true);

				Label confirmLabel = new Label(
						"��� ��� ��� �������� ������ � �� ����� �������� ������� ��� ����. �� ���� �� ������ �����.");
				confirmVerticalLayout.addComponent(confirmLabel);

				HorizontalLayout subHoriaontalLayout = new HorizontalLayout();
				confirmVerticalLayout.addComponent(subHoriaontalLayout);
				confirmVerticalLayout.setComponentAlignment(subHoriaontalLayout, Alignment.TOP_LEFT);

				Button confirmButton = new Button("�����");
				subHoriaontalLayout.addComponent(confirmButton);

				subHoriaontalLayout.addComponent(CommonsUI.getSpaceLabel(null, "10px", null));

				Button cancelButton = new Button("�����");
				subHoriaontalLayout.addComponent(cancelButton);

				Window confirmWindow = new Window();
				confirmWindow.center();
				confirmWindow.setModal(true);
				confirmWindow.setWidth("500");
				confirmWindow.setHeight("160");
				confirmWindow.setResizable(false);
				confirmWindow.setCaption(" �����");
				confirmWindow.setIcon(FontAwesome.WARNING);
				confirmWindow.setContent(confirmVerticalLayout);

				confirmButton.addClickListener(new Button.ClickListener() {
					private static final long serialVersionUID = 5325311693662832508L;

					public void buttonClick(ClickEvent event) {

						confirmWindow.close();

						HashMap<String, String> requestData = new HashMap<String, String>();
						requestData.put("id", idTextField.getValue());
						requestData.put("childName", nameTextField.getValue());

						int result = RequestHandler.handleUpdateRequest("deleteChild", requestData);

						if (result == EMapper.ECODE_SUCCESS) {
							CommonsUI.clearComponentValue(idTextField, nameTextField, addressNumTextField,
									addressFreeTextField, phoneTextField, mobile1TextField, mobile2TextField,
									emailTextField, nationalIdTextField, educationTextField,
									eccEducationTextField, coursesTextField, skillsTextField, notesTextField,
									statusTextField);
							CommonsUI.clearComponentValue(childPositionInMinistryLabel);
							CommonsUI.clearComponentValue(addressStreetComboBox, addressRegionComboBox,
									addressDistrictComboBox, allNamesComboBox);
							CommonsUI.clearComponentValue(birthdayDateField);

							CommonsUI.disableComponent(nameTextField, addressNumTextField,
									addressFreeTextField, phoneTextField, mobile1TextField, mobile2TextField,
									emailTextField, nationalIdTextField, educationTextField,
									eccEducationTextField, coursesTextField, skillsTextField, notesTextField);
							CommonsUI.disableComponent(addressStreetComboBox, addressRegionComboBox,
									addressDistrictComboBox);
							CommonsUI.disableComponent(birthdayDateField);
							CommonsUI.disableComponent(submitButton, cancelButton, deleteButton,
									reactivateButton, changeSubclassButton);
						}

						Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));
					}
				});

				cancelButton.addClickListener(new Button.ClickListener() {
					private static final long serialVersionUID = 5325311693662832508L;

					public void buttonClick(ClickEvent event) {
						confirmWindow.close();
					}
				});

				UI.getCurrent().addWindow(confirmWindow);
			}
		});

		reactivateButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 5569230699947766441L;

			public void buttonClick(ClickEvent event) {

				HashMap<String, String> requestData = new HashMap<String, String>();
				requestData.put("id", idTextField.getValue());

				int result = RequestHandler.handleUpdateRequest("reactivateMember", requestData);

				if (result == EMapper.ECODE_SUCCESS) {
					CommonsUI.clearComponentValue(idTextField, nameTextField, addressNumTextField,
							addressFreeTextField, phoneTextField, mobile1TextField, mobile2TextField,
							emailTextField, nationalIdTextField, educationTextField, eccEducationTextField,
							coursesTextField, skillsTextField, notesTextField, statusTextField);
					CommonsUI.clearComponentValue(addressStreetComboBox, addressRegionComboBox,
							addressDistrictComboBox, allNamesComboBox);
					CommonsUI.clearComponentValue(birthdayDateField);

					CommonsUI.disableComponent(nameTextField, addressNumTextField, addressFreeTextField,
							phoneTextField, mobile1TextField, mobile2TextField, emailTextField,
							nationalIdTextField, educationTextField, eccEducationTextField, coursesTextField,
							skillsTextField, notesTextField);
					CommonsUI.clearComponentValue(childPositionInMinistryLabel);
					CommonsUI.disableComponent(addressStreetComboBox, addressRegionComboBox,
							addressDistrictComboBox);
					CommonsUI.disableComponent(birthdayDateField);
					CommonsUI.disableComponent(submitButton, cancelButton, deleteButton, reactivateButton,
							changeSubclassButton);
				}

				Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));
			}

		});

		allNamesComboBox.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = -2449218224292943056L;

			@Override
			public void valueChange(ValueChangeEvent event) {

				if (event.getProperty().getValue() != null) {

					// ******************************//
					addressStreetComboBox.removeAllItems();
					addressRegionComboBox.removeAllItems();
					addressDistrictComboBox.removeAllItems();

					ArrayList<String> allStreets = CommonsHandler.getAllStreets();
					ArrayList<String> allRegions = CommonsHandler.getAllRegions();
					ArrayList<String> allDistricts = CommonsHandler.getAllDistricts();

					for (int i = 0; i < allStreets.size(); i++) {
						addressStreetComboBox.addItem(allStreets.get(i));
					}

					for (int i = 0; i < allRegions.size(); i++) {
						addressRegionComboBox.addItem(allRegions.get(i));
					}

					for (int i = 0; i < allDistricts.size(); i++) {
						addressDistrictComboBox.addItem(allDistricts.get(i));
					}

					// ******************************//

					final String childName = String.valueOf(event.getProperty().getValue());
					final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

					HashMap<String, String> requestData = new HashMap<String, String>();
					requestData.put("childName", childName);

					HashMap<String, String> result = RequestHandler.handleQueryRequest("getChildData",
							requestData);

					idTextField.setValue(result.get("id"));
					nameTextField.setValue(result.get("name"));
					addressNumTextField.setValue(result.get("addressNum"));
					if (result.get("addressStreet").length() != 0) {
						addressStreetComboBox.setValue(result.get("addressStreet"));
					} else {
						addressStreetComboBox.setValue(null);
					}
					if (result.get("addressRegion").length() != 0) {
						addressRegionComboBox.setValue(result.get("addressRegion"));
					} else {
						addressRegionComboBox.setValue(null);
					}
					if (result.get("addressDistrict").length() != 0) {
						addressDistrictComboBox.setValue(result.get("addressDistrict"));
					} else {
						addressDistrictComboBox.setValue(null);
					}
					addressFreeTextField.setValue(result.get("addressFree"));
					phoneTextField.setValue(result.get("phone"));
					mobile1TextField.setValue(result.get("mobile1"));
					mobile2TextField.setValue(result.get("mobile2"));
					if (result.get("birthday").length() != 0) {
						try {
							birthdayDateField.setValue(dateFormatter.parse(result.get("birthday")));
						} catch (ReadOnlyException | ConversionException | ParseException e) {
							Logger.exception(e);
						}
					} else {
						birthdayDateField.setValue(null);
					}
					emailTextField.setValue(result.get("email"));
					nationalIdTextField.setValue(result.get("nationalId"));
					educationTextField.setValue(result.get("education"));
					eccEducationTextField.setValue(result.get("eccEducation"));
					coursesTextField.setValue(result.get("courses"));
					skillsTextField.setValue(result.get("skills"));
					notesTextField.setValue(result.get("notes"));
					childPositionInMinistryLabel.setCaption((result.get("childPositionInMinistry") != null) ? result
							.get("childPositionInMinistry") : "");

					if (result.get("status").equals("1")) {
						statusTextField.setValue("����");
						CommonsUI.enableComponent(deleteButton);
						CommonsUI.disableComponent(reactivateButton);
					} else {
						statusTextField.setValue("��� ����");
						CommonsUI.disableComponent(deleteButton);
						CommonsUI.enableComponent(reactivateButton);
					}

					CommonsUI.enableComponent(nameTextField, addressNumTextField, addressFreeTextField,
							phoneTextField, mobile1TextField, mobile2TextField, emailTextField,
							nationalIdTextField, educationTextField, eccEducationTextField, coursesTextField,
							skillsTextField, notesTextField);
					CommonsUI.enableComponent(addressStreetComboBox, addressRegionComboBox,
							addressDistrictComboBox);
					CommonsUI.enableComponent(birthdayDateField);
					CommonsUI.enableComponent(submitButton, cancelButton, changeSubclassButton);

				} else {
					CommonsUI.clearComponentValue(idTextField, nameTextField, addressNumTextField,
							addressFreeTextField, phoneTextField, mobile1TextField, mobile2TextField,
							emailTextField, nationalIdTextField, educationTextField, eccEducationTextField,
							coursesTextField, skillsTextField, notesTextField, statusTextField);
					CommonsUI.clearComponentValue(addressStreetComboBox, addressRegionComboBox,
							addressDistrictComboBox, allNamesComboBox);
					CommonsUI.clearComponentValue(childPositionInMinistryLabel);
					CommonsUI.clearComponentValue(birthdayDateField);

					CommonsUI.disableComponent(nameTextField, addressNumTextField, addressFreeTextField,
							phoneTextField, mobile1TextField, mobile2TextField, emailTextField,
							nationalIdTextField, educationTextField, eccEducationTextField, coursesTextField,
							skillsTextField, notesTextField);
					CommonsUI.disableComponent(addressStreetComboBox, addressRegionComboBox,
							addressDistrictComboBox);
					CommonsUI.disableComponent(birthdayDateField);
					CommonsUI.disableComponent(submitButton, cancelButton, deleteButton, reactivateButton,
							changeSubclassButton);
				}
			}
		});
		// /////////////////////

		return layout;
	}
}
