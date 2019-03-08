package church.ministry.ui.minister;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import church.ministry.control.commons.CommonsHandler;
import church.ministry.control.ecode.EMapper;
import church.ministry.control.handler.RequestHandler;
import church.ministry.control.log.Logger;
import church.ministry.control.printing.Printer;
import church.ministry.control.reports.SearchReportHandler;
import church.ministry.model.file.FileAccess;
import church.ministry.ui.ministry.CommonsUI;
import church.ministry.util.Validator;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.themes.ValoTheme;

class SearchMinisterUI implements Serializable {

	private static final long serialVersionUID = 9209270300558377069L;

	protected VerticalLayout getLayout() {
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setCaption("»ÕÀ");

		final TextField idTextField = new TextField();
		idTextField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		idTextField.setMaxLength(50);
		idTextField.setCaption("«·—ﬁ„");
		layout.addComponent(idTextField);
		layout.setComponentAlignment(idTextField, Alignment.TOP_RIGHT);

		final TextField nameTextField = new TextField();
		nameTextField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		nameTextField.setMaxLength(50);
		nameTextField.setColumns(25);
		nameTextField.setCaption("«·√”„");
		layout.addComponent(nameTextField);
		layout.setComponentAlignment(nameTextField, Alignment.TOP_RIGHT);

		layout.addComponent(CommonsUI.getSpaceLabel("10px", null, null));

		// ////////////////////////////////////////////////////////////////////////////////////////

		final HorizontalLayout addressLayout = new HorizontalLayout();
		addressLayout.setCaption("«·⁄‰Ê«‰");
		layout.addComponent(addressLayout);
		layout.setComponentAlignment(addressLayout, Alignment.TOP_RIGHT);

		final TextField addressNumTextField = new TextField();
		addressNumTextField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		addressNumTextField.setMaxLength(10);
		addressNumTextField.setColumns(5);
		addressNumTextField.setCaption("—ﬁ„");
		addressLayout.addComponent(addressNumTextField);
		addressLayout.setComponentAlignment(addressNumTextField, Alignment.TOP_RIGHT);

		addressLayout.addComponent(CommonsUI.getSpaceLabel(null, "5px", null));

		final ComboBox addressStreetComboBox = new ComboBox();
		addressStreetComboBox.addStyleName(ValoTheme.COMBOBOX_SMALL);
		addressStreetComboBox.setNewItemsAllowed(false);
		addressStreetComboBox.setCaption("‘«—⁄");
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
		addressRegionComboBox.setNewItemsAllowed(false);
		addressRegionComboBox.setCaption("„‰ÿﬁ…");
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
		addressDistrictComboBox.setNewItemsAllowed(false);
		addressDistrictComboBox.setCaption("ÕÌ");
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
		addressFreeTextField.setCaption("»«ﬁÌ «·⁄‰Ê«‰");
		addressLayout.addComponent(addressFreeTextField);
		addressLayout.setComponentAlignment(addressFreeTextField, Alignment.TOP_RIGHT);

		// ////////////////////////////////////////////////////////////////////////////////////////

		layout.addComponent(CommonsUI.getSpaceLabel("10px", null, null));

		final TextField phoneTextField = new TextField();
		phoneTextField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		phoneTextField.setMaxLength(50);
		phoneTextField.setCaption(" ·Ì›Ê‰");
		layout.addComponent(phoneTextField);
		layout.setComponentAlignment(phoneTextField, Alignment.TOP_RIGHT);

		final TextField mobile1TextField = new TextField();
		mobile1TextField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		mobile1TextField.setMaxLength(50);
		mobile1TextField.setCaption("„Ê»«Ì· 1");
		layout.addComponent(mobile1TextField);
		layout.setComponentAlignment(mobile1TextField, Alignment.TOP_RIGHT);

		final TextField mobile2TextField = new TextField();
		mobile2TextField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		mobile2TextField.setMaxLength(50);
		mobile2TextField.setCaption("„Ê»«Ì· 2");
		layout.addComponent(mobile2TextField);
		layout.setComponentAlignment(mobile2TextField, Alignment.TOP_RIGHT);

		final DateField birthdayDateField = new DateField();
		birthdayDateField.setImmediate(true);
		birthdayDateField.addStyleName(ValoTheme.DATEFIELD_SMALL);
		birthdayDateField.setDateFormat("dd-MM-yyyy");
		birthdayDateField.setInvalidAllowed(false);
		birthdayDateField.setCaption(" «—ÌŒ «·„Ì·«œ");
		layout.addComponent(birthdayDateField);
		layout.setComponentAlignment(birthdayDateField, Alignment.TOP_RIGHT);

		layout.addComponent(CommonsUI.getSpaceLabel("5px", null, null));

		final HorizontalLayout birthdayLayout = new HorizontalLayout();
		layout.addComponent(birthdayLayout);
		layout.setComponentAlignment(birthdayLayout, Alignment.TOP_RIGHT);

		layout.addComponent(CommonsUI.getSpaceLabel("5px", null, null));

		final ComboBox birthdayDayComboBox = new ComboBox();
		birthdayDayComboBox.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		birthdayDayComboBox.setNewItemsAllowed(false);
		birthdayDayComboBox.setCaption("ÌÊ„");
		birthdayDayComboBox.setWidth("80px");
		for (int i = 1; i < 32; i++) {
			birthdayDayComboBox.addItem(CommonsHandler.getIntegerInDecimalFormat(i, "00"));
		}
		birthdayLayout.addComponent(birthdayDayComboBox);
		birthdayLayout.setComponentAlignment(birthdayDayComboBox, Alignment.TOP_RIGHT);

		birthdayLayout.addComponent(CommonsUI.getSpaceLabel(null, "5px", null));

		final ComboBox birthdayMonthComboBox = new ComboBox();
		birthdayMonthComboBox.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		birthdayMonthComboBox.setNewItemsAllowed(false);
		birthdayMonthComboBox.setCaption("‘Â—");
		birthdayMonthComboBox.setWidth("80px");
		for (int i = 1; i < 13; i++) {
			birthdayMonthComboBox.addItem(CommonsHandler.getIntegerInDecimalFormat(i, "00"));
		}
		birthdayLayout.addComponent(birthdayMonthComboBox);
		birthdayLayout.setComponentAlignment(birthdayMonthComboBox, Alignment.TOP_RIGHT);

		birthdayLayout.addComponent(CommonsUI.getSpaceLabel(null, "5px", null));

		final TextField birthdayYearTextField = new TextField();
		birthdayYearTextField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		birthdayYearTextField.setCaption("”‰…");
		birthdayYearTextField.setWidth("80px");
		birthdayLayout.addComponent(birthdayYearTextField);
		birthdayLayout.setComponentAlignment(birthdayYearTextField, Alignment.TOP_RIGHT);

		// ****************************************************************************************

		layout.addComponent(CommonsUI.getSpaceLabel(null, null, null));

		final VerticalLayout sectionsLayout = new VerticalLayout();
		sectionsLayout.setMargin(true);
		sectionsLayout.setSizeUndefined();
		sectionsLayout.addStyleName(ValoTheme.LAYOUT_CARD);
		sectionsLayout.setCaption("«·„—Õ·…:");
		layout.addComponent(sectionsLayout);
		layout.setComponentAlignment(sectionsLayout, Alignment.TOP_RIGHT);

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
		sectionsLayout.addComponent(itemComboBox);

		sectionsLayout.addComponent(CommonsUI.getSpaceLabel(null, null, null));

		ComboBox valueComboBox = new ComboBox();
		valueComboBox.addStyleName(ValoTheme.COMBOBOX_ALIGN_RIGHT);
		valueComboBox.addStyleName(ValoTheme.COMBOBOX_SMALL);
		valueComboBox.setEnabled(false);
		valueComboBox.setNewItemsAllowed(false);
		valueComboBox.setNullSelectionAllowed(false);
		valueComboBox.setWidth("350px");
		valueComboBox.setCaption("≈Œ — «·«”„:");
		sectionsLayout.addComponent(valueComboBox);

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

		// ****************************************************************************************

		final TextField emailTextField = new TextField();
		emailTextField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		emailTextField.setMaxLength(50);
		emailTextField.setColumns(25);
		emailTextField.setCaption("«·≈Ì„Ì·");
		layout.addComponent(emailTextField);
		layout.setComponentAlignment(emailTextField, Alignment.TOP_RIGHT);

		final TextField jobTextField = new TextField();
		jobTextField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		jobTextField.setMaxLength(50);
		jobTextField.setColumns(25);
		jobTextField.setCaption("«·ÊŸÌ›…");
		layout.addComponent(jobTextField);
		layout.setComponentAlignment(jobTextField, Alignment.TOP_RIGHT);

		final TextField educationTextField = new TextField();
		educationTextField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		educationTextField.setMaxLength(200);
		educationTextField.setColumns(25);
		educationTextField.setCaption("«· ⁄·Ì„");
		layout.addComponent(educationTextField);
		layout.setComponentAlignment(educationTextField, Alignment.TOP_RIGHT);

		final TextField eccEducationTextField = new TextField();
		eccEducationTextField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		eccEducationTextField.setMaxLength(200);
		eccEducationTextField.setColumns(25);
		eccEducationTextField.setCaption("«· ⁄·Ì„ «·ﬂ‰”Ì");
		layout.addComponent(eccEducationTextField);
		layout.setComponentAlignment(eccEducationTextField, Alignment.TOP_RIGHT);

		final TextField coursesTextField = new TextField();
		coursesTextField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		coursesTextField.setMaxLength(200);
		coursesTextField.setColumns(25);
		coursesTextField.setCaption("«·ﬂÊ—”« ");
		layout.addComponent(coursesTextField);
		layout.setComponentAlignment(coursesTextField, Alignment.TOP_RIGHT);

		final TextField skillsTextField = new TextField();
		skillsTextField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		skillsTextField.setMaxLength(200);
		skillsTextField.setColumns(25);
		skillsTextField.setCaption("ﬁœ—«  Ê „Ê«Â»");
		layout.addComponent(skillsTextField);
		layout.setComponentAlignment(skillsTextField, Alignment.TOP_RIGHT);

		final TextField notesTextField = new TextField();
		notesTextField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		notesTextField.setMaxLength(250);
		notesTextField.setColumns(25);
		notesTextField.setCaption("„·«ÕŸ« ");
		layout.addComponent(notesTextField);
		layout.setComponentAlignment(notesTextField, Alignment.TOP_RIGHT);

		layout.addComponent(CommonsUI.getSpaceLabel("20px", null, null));

		GridLayout searchItemsLayout = new GridLayout(5, 6);
		searchItemsLayout.setMargin(true);
		searchItemsLayout.setCaption("≈Œ — ‰ «∆Ã «·»ÕÀ:");
		layout.addComponent(searchItemsLayout);
		layout.setComponentAlignment(searchItemsLayout, Alignment.TOP_RIGHT);

		Button idToggleButton = new Button("«·—ﬁ„");
		idToggleButton.setWidth("150px");
		idToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		idToggleButton.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
		idToggleButton.addStyleName("button-right-aligned");
		idToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
		searchItemsLayout.addComponent(idToggleButton, 4, 0);

		Button nameToggleButton = new Button("«·«”„");
		nameToggleButton.setWidth("150px");
		nameToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		nameToggleButton.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
		nameToggleButton.addStyleName("button-right-aligned");
		nameToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
		searchItemsLayout.addComponent(nameToggleButton, 3, 0);

		Button addressNumToggleButton = new Button("«·⁄‰Ê«‰ - —ﬁ„");
		addressNumToggleButton.setWidth("150px");
		addressNumToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		addressNumToggleButton.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
		addressNumToggleButton.addStyleName("button-right-aligned");
		addressNumToggleButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 5569230699947766441L;

			public void buttonClick(ClickEvent event) {
				if (addressNumToggleButton.getIcon() == null) {
					addressNumToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
				} else {
					addressNumToggleButton.setIcon(null);
				}
			}
		});
		searchItemsLayout.addComponent(addressNumToggleButton, 4, 1);

		Button addressStreetToggleButton = new Button("«·⁄‰Ê«‰ - ‘«—⁄");
		addressStreetToggleButton.setWidth("150px");
		addressStreetToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		addressStreetToggleButton.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
		addressStreetToggleButton.addStyleName("button-right-aligned");
		addressStreetToggleButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 5569230699947766441L;

			public void buttonClick(ClickEvent event) {
				if (addressStreetToggleButton.getIcon() == null) {
					addressStreetToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
				} else {
					addressStreetToggleButton.setIcon(null);
				}
			}
		});
		searchItemsLayout.addComponent(addressStreetToggleButton, 3, 1);

		Button addressRegionToggleButton = new Button("«·⁄‰Ê«‰ - „‰ÿﬁ…");
		addressRegionToggleButton.setWidth("150px");
		addressRegionToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		addressRegionToggleButton.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
		addressRegionToggleButton.addStyleName("button-right-aligned");
		addressRegionToggleButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 5569230699947766441L;

			public void buttonClick(ClickEvent event) {
				if (addressRegionToggleButton.getIcon() == null) {
					addressRegionToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
				} else {
					addressRegionToggleButton.setIcon(null);
				}
			}
		});
		searchItemsLayout.addComponent(addressRegionToggleButton, 2, 1);

		Button addressDistrictToggleButton = new Button("«·⁄‰Ê«‰ - ÕÌ");
		addressDistrictToggleButton.setWidth("150px");
		addressDistrictToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		addressDistrictToggleButton.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
		addressDistrictToggleButton.addStyleName("button-right-aligned");
		addressDistrictToggleButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 5569230699947766441L;

			public void buttonClick(ClickEvent event) {
				if (addressDistrictToggleButton.getIcon() == null) {
					addressDistrictToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
				} else {
					addressDistrictToggleButton.setIcon(null);
				}
			}
		});
		searchItemsLayout.addComponent(addressDistrictToggleButton, 1, 1);

		Button addressFreeToggleButton = new Button("»«ﬁÌ «·⁄‰Ê«‰");
		addressFreeToggleButton.setWidth("150px");
		addressFreeToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		addressFreeToggleButton.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
		addressFreeToggleButton.addStyleName("button-right-aligned");
		addressFreeToggleButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 5569230699947766441L;

			public void buttonClick(ClickEvent event) {
				if (addressFreeToggleButton.getIcon() == null) {
					addressFreeToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
				} else {
					addressFreeToggleButton.setIcon(null);
				}
			}
		});
		searchItemsLayout.addComponent(addressFreeToggleButton, 0, 1);

		Button phoneToggleButton = new Button("«· ·Ì›Ê‰");
		phoneToggleButton.setWidth("150px");
		phoneToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		phoneToggleButton.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
		phoneToggleButton.addStyleName("button-right-aligned");
		phoneToggleButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 5569230699947766441L;

			public void buttonClick(ClickEvent event) {
				if (phoneToggleButton.getIcon() == null) {
					phoneToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
				} else {
					phoneToggleButton.setIcon(null);
				}
			}
		});
		searchItemsLayout.addComponent(phoneToggleButton, 4, 2);

		Button mobile1ToggleButton = new Button("„Ê»«Ì· 1");
		mobile1ToggleButton.setWidth("150px");
		mobile1ToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		mobile1ToggleButton.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
		mobile1ToggleButton.addStyleName("button-right-aligned");
		mobile1ToggleButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 5569230699947766441L;

			public void buttonClick(ClickEvent event) {
				if (mobile1ToggleButton.getIcon() == null) {
					mobile1ToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
				} else {
					mobile1ToggleButton.setIcon(null);
				}
			}
		});
		searchItemsLayout.addComponent(mobile1ToggleButton, 3, 2);

		Button mobile2ToggleButton = new Button("„Ê»«Ì· 2");
		mobile2ToggleButton.setWidth("150px");
		mobile2ToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		mobile2ToggleButton.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
		mobile2ToggleButton.addStyleName("button-right-aligned");
		mobile2ToggleButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 5569230699947766441L;

			public void buttonClick(ClickEvent event) {
				if (mobile2ToggleButton.getIcon() == null) {
					mobile2ToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
				} else {
					mobile2ToggleButton.setIcon(null);
				}
			}
		});
		searchItemsLayout.addComponent(mobile2ToggleButton, 2, 2);

		Button emailToggleButton = new Button("«·≈Ì„Ì·");
		emailToggleButton.setWidth("150px");
		emailToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		emailToggleButton.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
		emailToggleButton.addStyleName("button-right-aligned");
		emailToggleButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 5569230699947766441L;

			public void buttonClick(ClickEvent event) {
				if (emailToggleButton.getIcon() == null) {
					emailToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
				} else {
					emailToggleButton.setIcon(null);
				}
			}
		});
		searchItemsLayout.addComponent(emailToggleButton, 1, 2);

		Button jobToggleButton = new Button("«·ÊŸÌ›…");
		jobToggleButton.setWidth("150px");
		jobToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		jobToggleButton.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
		jobToggleButton.addStyleName("button-right-aligned");
		jobToggleButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 5569230699947766441L;

			public void buttonClick(ClickEvent event) {
				if (jobToggleButton.getIcon() == null) {
					jobToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
				} else {
					jobToggleButton.setIcon(null);
				}
			}
		});
		searchItemsLayout.addComponent(jobToggleButton, 1, 3);

		Button birthdayToggleButton = new Button(" «—ÌŒ «·„Ì·«œ");
		birthdayToggleButton.setWidth("150px");
		birthdayToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		birthdayToggleButton.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
		birthdayToggleButton.addStyleName("button-right-aligned");
		birthdayToggleButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 5569230699947766441L;

			public void buttonClick(ClickEvent event) {
				if (birthdayToggleButton.getIcon() == null) {
					birthdayToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
				} else {
					birthdayToggleButton.setIcon(null);
				}
			}
		});
		searchItemsLayout.addComponent(birthdayToggleButton, 4, 3);

		Button educationToggleButton = new Button("«· ⁄·Ì„");
		educationToggleButton.setWidth("150px");
		educationToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		educationToggleButton.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
		educationToggleButton.addStyleName("button-right-aligned");
		educationToggleButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 5569230699947766441L;

			public void buttonClick(ClickEvent event) {
				if (educationToggleButton.getIcon() == null) {
					educationToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
				} else {
					educationToggleButton.setIcon(null);
				}
			}
		});
		searchItemsLayout.addComponent(educationToggleButton, 3, 3);

		Button eccEducationToggleButton = new Button("«· ⁄·Ì„ «·ﬂ‰”Ì");
		eccEducationToggleButton.setWidth("150px");
		eccEducationToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		eccEducationToggleButton.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
		eccEducationToggleButton.addStyleName("button-right-aligned");
		eccEducationToggleButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 5569230699947766441L;

			public void buttonClick(ClickEvent event) {
				if (eccEducationToggleButton.getIcon() == null) {
					eccEducationToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
				} else {
					eccEducationToggleButton.setIcon(null);
				}
			}
		});
		searchItemsLayout.addComponent(eccEducationToggleButton, 2, 3);

		Button coursesToggleButton = new Button("ﬂÊ—”« ");
		coursesToggleButton.setWidth("150px");
		coursesToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		coursesToggleButton.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
		coursesToggleButton.addStyleName("button-right-aligned");
		coursesToggleButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 5569230699947766441L;

			public void buttonClick(ClickEvent event) {
				if (coursesToggleButton.getIcon() == null) {
					coursesToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
				} else {
					coursesToggleButton.setIcon(null);
				}
			}
		});
		searchItemsLayout.addComponent(coursesToggleButton, 4, 4);

		Button skillsToggleButton = new Button("„Â«—« ");
		skillsToggleButton.setWidth("150px");
		skillsToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		skillsToggleButton.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
		skillsToggleButton.addStyleName("button-right-aligned");
		skillsToggleButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 5569230699947766441L;

			public void buttonClick(ClickEvent event) {
				if (skillsToggleButton.getIcon() == null) {
					skillsToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
				} else {
					skillsToggleButton.setIcon(null);
				}
			}
		});
		searchItemsLayout.addComponent(skillsToggleButton, 3, 4);

		Button notesToggleButton = new Button("„·«ÕŸ« ");
		notesToggleButton.setWidth("150px");
		notesToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		notesToggleButton.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
		notesToggleButton.addStyleName("button-right-aligned");
		notesToggleButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 5569230699947766441L;

			public void buttonClick(ClickEvent event) {
				if (notesToggleButton.getIcon() == null) {
					notesToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
				} else {
					notesToggleButton.setIcon(null);
				}
			}
		});
		searchItemsLayout.addComponent(notesToggleButton, 2, 4);

		Button activeDateToggleButton = new Button(" «—ÌŒ «· ›⁄Ì·");
		activeDateToggleButton.setWidth("150px");
		activeDateToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		activeDateToggleButton.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
		activeDateToggleButton.addStyleName("button-right-aligned");
		activeDateToggleButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 5569230699947766441L;

			public void buttonClick(ClickEvent event) {
				if (activeDateToggleButton.getIcon() == null) {
					activeDateToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
				} else {
					activeDateToggleButton.setIcon(null);
				}
			}
		});
		searchItemsLayout.addComponent(activeDateToggleButton, 2, 0);

		// ////////////////////////////////////////////////////////////////////////////////////////////////

		Button sectionToggleButton = new Button("«·ﬁÿ«⁄");
		sectionToggleButton.setWidth("150px");
		sectionToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		sectionToggleButton.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
		sectionToggleButton.addStyleName("button-right-aligned");
		sectionToggleButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 5569230699947766441L;

			public void buttonClick(ClickEvent event) {
				if (sectionToggleButton.getIcon() == null) {
					sectionToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
				} else {
					sectionToggleButton.setIcon(null);
				}
			}
		});
		searchItemsLayout.addComponent(sectionToggleButton, 4, 5);

		Button familyToggleButton = new Button("«·√”—…");
		familyToggleButton.setWidth("150px");
		familyToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		familyToggleButton.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
		familyToggleButton.addStyleName("button-right-aligned");
		familyToggleButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 5569230699947766441L;

			public void buttonClick(ClickEvent event) {
				if (familyToggleButton.getIcon() == null) {
					familyToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
				} else {
					familyToggleButton.setIcon(null);
				}
			}
		});
		searchItemsLayout.addComponent(familyToggleButton, 3, 5);

		Button yearToggleButton = new Button("«·’›");
		yearToggleButton.setWidth("150px");
		yearToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		yearToggleButton.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
		yearToggleButton.addStyleName("button-right-aligned");
		yearToggleButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 5569230699947766441L;

			public void buttonClick(ClickEvent event) {
				if (yearToggleButton.getIcon() == null) {
					yearToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
				} else {
					yearToggleButton.setIcon(null);
				}
			}
		});
		searchItemsLayout.addComponent(yearToggleButton, 2, 5);

		Button classToggleButton = new Button("«·›’·");
		classToggleButton.setWidth("150px");
		classToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		classToggleButton.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
		classToggleButton.addStyleName("button-right-aligned");
		classToggleButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 5569230699947766441L;

			public void buttonClick(ClickEvent event) {
				if (classToggleButton.getIcon() == null) {
					classToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
				} else {
					classToggleButton.setIcon(null);
				}
			}
		});
		searchItemsLayout.addComponent(classToggleButton, 1, 5);

		Button selectAllToggleButton = new Button(" ÕœÌœ «·ﬂ·");
		selectAllToggleButton.setWidth("150px");
		selectAllToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		selectAllToggleButton.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
		selectAllToggleButton.addStyleName("button-right-aligned");
		selectAllToggleButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 5569230699947766441L;

			public void buttonClick(ClickEvent event) {
				if (selectAllToggleButton.getIcon() == null) {
					selectAllToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
					addressNumToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
					addressStreetToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
					addressRegionToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
					addressDistrictToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
					addressFreeToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
					phoneToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
					mobile1ToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
					mobile2ToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
					birthdayToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
					emailToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
					jobToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
					educationToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
					eccEducationToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
					coursesToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
					skillsToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
					notesToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
					activeDateToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
					sectionToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
					familyToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
					yearToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
					classToggleButton.setIcon(FontAwesome.SEARCH_PLUS);
				} else {
					selectAllToggleButton.setIcon(null);
					addressNumToggleButton.setIcon(null);
					addressStreetToggleButton.setIcon(null);
					addressRegionToggleButton.setIcon(null);
					addressDistrictToggleButton.setIcon(null);
					addressFreeToggleButton.setIcon(null);
					phoneToggleButton.setIcon(null);
					mobile1ToggleButton.setIcon(null);
					mobile2ToggleButton.setIcon(null);
					birthdayToggleButton.setIcon(null);
					emailToggleButton.setIcon(null);
					jobToggleButton.setIcon(null);
					educationToggleButton.setIcon(null);
					eccEducationToggleButton.setIcon(null);
					coursesToggleButton.setIcon(null);
					skillsToggleButton.setIcon(null);
					notesToggleButton.setIcon(null);
					activeDateToggleButton.setIcon(null);
					sectionToggleButton.setIcon(null);
					familyToggleButton.setIcon(null);
					yearToggleButton.setIcon(null);
					classToggleButton.setIcon(null);
				}
			}
		});
		layout.addComponent(selectAllToggleButton);
		layout.setComponentAlignment(selectAllToggleButton, Alignment.TOP_RIGHT);

		layout.addComponent(CommonsUI.getSpaceLabel("20px", null, null));

		final HorizontalLayout buttonsLayout = new HorizontalLayout();
		layout.addComponent(buttonsLayout);
		layout.setComponentAlignment(buttonsLayout, Alignment.TOP_RIGHT);

		final Button submitButton = new Button();
		submitButton.setImmediate(true);
		submitButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		submitButton.addStyleName(ValoTheme.BUTTON_SMALL);
		submitButton.setIcon(FontAwesome.SEARCH);
		submitButton.setWidth("100px");
		submitButton.setCaption(" »ÕÀ");
		buttonsLayout.addComponent(submitButton);
		submitButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 5569230699947766441L;

			@SuppressWarnings("deprecation")
			public void buttonClick(ClickEvent event) {

				if (!Validator.isNumeric(idTextField.getValue())) {
					Notification.show("«·—ﬁ„ «·„œŒ· €Ì— ’ÕÌÕ",
							EMapper.getEDesc(EMapper.ECODE_INVALID_INPUT_DATA),
							EMapper.getNType(EMapper.ECODE_INVALID_INPUT_DATA));
					return;
				}

				HashMap<String, String> requestData = new HashMap<String, String>();
				HashMap<String, String> selectData = new HashMap<String, String>();
				final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

				requestData.put("id", idTextField.getValue());
				requestData.put("name", nameTextField.getValue());
				requestData.put("addressNum", addressNumTextField.getValue());
				if (addressStreetComboBox.getValue() != null) {
					requestData.put("addressStreet", addressStreetComboBox.getValue().toString());
				} else {
					requestData.put("addressStreet", null);
				}

				if (addressRegionComboBox.getValue() != null) {
					requestData.put("addressRegion", addressRegionComboBox.getValue().toString());
				} else {
					requestData.put("addressRegion", null);
				}

				if (addressDistrictComboBox.getValue() != null) {
					requestData.put("addressDistrict", addressDistrictComboBox.getValue().toString());
				} else {
					requestData.put("addressDistrict", null);
				}

				requestData.put("addressFree", addressFreeTextField.getValue());
				requestData.put("phone", phoneTextField.getValue());
				requestData.put("mobile1", mobile1TextField.getValue());
				requestData.put("mobile2", mobile2TextField.getValue());
				if (birthdayDateField.getValue() != null) {
					requestData.put("birthday", dateFormatter.format(birthdayDateField.getValue()));
				} else {
					requestData.put("birthday", null);
				}
				requestData.put("email", emailTextField.getValue());
				requestData.put("education", educationTextField.getValue());
				requestData.put("eccEducation", eccEducationTextField.getValue());
				requestData.put("courses", coursesTextField.getValue());
				requestData.put("skills", skillsTextField.getValue());
				requestData.put("notes", notesTextField.getValue());

				if (birthdayDayComboBox.getValue() != null) {
					requestData.put("birthdayDay", birthdayDayComboBox.getValue().toString());
				} else {
					requestData.put("birthdayDay", null);
				}
				if (birthdayMonthComboBox.getValue() != null) {
					requestData.put("birthdayMonth", birthdayMonthComboBox.getValue().toString());
				} else {
					requestData.put("birthdayMonth", null);
				}
				requestData.put("birthdayYear", birthdayYearTextField.getValue());

				if (idToggleButton.getIcon() == null) {
					selectData.put("id", "0");
				} else {
					selectData.put("id", "1");
				}
				if (nameToggleButton.getIcon() == null) {
					selectData.put("name", "0");
				} else {
					selectData.put("name", "1");
				}
				if (addressNumToggleButton.getIcon() == null) {
					selectData.put("addressNum", "0");
				} else {
					selectData.put("addressNum", "1");
				}
				if (addressStreetToggleButton.getIcon() == null) {
					selectData.put("addressStreet", "0");
				} else {
					selectData.put("addressStreet", "1");
				}
				if (addressRegionToggleButton.getIcon() == null) {
					selectData.put("addressRegion", "0");
				} else {
					selectData.put("addressRegion", "1");
				}
				if (addressDistrictToggleButton.getIcon() == null) {
					selectData.put("addressDistrict", "0");
				} else {
					selectData.put("addressDistrict", "1");
				}
				if (addressFreeToggleButton.getIcon() == null) {
					selectData.put("addressFree", "0");
				} else {
					selectData.put("addressFree", "1");
				}
				if (phoneToggleButton.getIcon() == null) {
					selectData.put("phone", "0");
				} else {
					selectData.put("phone", "1");
				}
				if (mobile1ToggleButton.getIcon() == null) {
					selectData.put("mobile1", "0");
				} else {
					selectData.put("mobile1", "1");
				}
				if (mobile2ToggleButton.getIcon() == null) {
					selectData.put("mobile2", "0");
				} else {
					selectData.put("mobile2", "1");
				}
				if (birthdayToggleButton.getIcon() == null) {
					selectData.put("birthday", "0");
				} else {
					selectData.put("birthday", "1");
				}
				if (emailToggleButton.getIcon() == null) {
					selectData.put("email", "0");
				} else {
					selectData.put("email", "1");
				}
				if (jobToggleButton.getIcon() == null) {
					selectData.put("job", "0");
				} else {
					selectData.put("job", "1");
				}
				if (educationToggleButton.getIcon() == null) {
					selectData.put("education", "0");
				} else {
					selectData.put("education", "1");
				}
				if (eccEducationToggleButton.getIcon() == null) {
					selectData.put("eccEducation", "0");
				} else {
					selectData.put("eccEducation", "1");
				}
				if (coursesToggleButton.getIcon() == null) {
					selectData.put("courses", "0");
				} else {
					selectData.put("courses", "1");
				}
				if (skillsToggleButton.getIcon() == null) {
					selectData.put("skills", "0");
				} else {
					selectData.put("skills", "1");
				}
				if (notesToggleButton.getIcon() == null) {
					selectData.put("notes", "0");
				} else {
					selectData.put("notes", "1");
				}
				if (activeDateToggleButton.getIcon() == null) {
					selectData.put("activeDate", "0");
				} else {
					selectData.put("activeDate", "1");
				}
				if (itemComboBox.getValue().toString() != itemNames[0] && valueComboBox.getValue() != null) {
					selectData.put("level", "1");
					requestData.put("levelItem", itemComboBox.getValue().toString());
					requestData.put("levelValue", valueComboBox.getValue().toString());
				} else {
					selectData.put("level", "0");
				}

				if (sectionToggleButton.getIcon() == null) {
					selectData.put("section", "0");
				} else {
					selectData.put("section", "1");
				}
				if (familyToggleButton.getIcon() == null) {
					selectData.put("family", "0");
				} else {
					selectData.put("family", "1");
				}
				if (yearToggleButton.getIcon() == null) {
					selectData.put("year", "0");
				} else {
					selectData.put("year", "1");
				}
				if (classToggleButton.getIcon() == null) {
					selectData.put("class", "0");
				} else {
					selectData.put("class", "1");
				}

				ArrayList<ArrayList<Object>> searchResult = RequestHandler.handle2DQueryRequest(
						"searchMinister", requestData, selectData);

				if (searchResult.size() < 2) {
					Notification.show(EMapper.getEDesc(EMapper.ECODE_NO_RECORD_FOUND),
							EMapper.getNType(EMapper.ECODE_NO_RECORD_FOUND));
				} else {
					String pdfDownloadFilePath = SearchReportHandler.savePDF(searchResult);
					String pdfDownloadFileName = pdfDownloadFilePath.substring(
							pdfDownloadFilePath.lastIndexOf("/") + 1, pdfDownloadFilePath.length());

					String csvDownloadFilePath = SearchReportHandler.saveCSV(searchResult);
					String csvDownloadFileName = csvDownloadFilePath.substring(
							csvDownloadFilePath.lastIndexOf("/") + 1, csvDownloadFilePath.length());

					Window searchWindow = new Window();
					searchWindow.setImmediate(true);
					searchWindow.center();
					searchWindow.setSizeFull();
					searchWindow.setModal(true);
					searchWindow.setResizable(false);
					searchWindow.setStyleName(ValoTheme.WINDOW_TOP_TOOLBAR);
					searchWindow.setIcon(FontAwesome.SEARCH);
					searchWindow.setCaption("‰ «∆Ã «·»ÕÀ");
					searchWindow.setContent(getSearchResultTabSheet(searchResult, pdfDownloadFilePath,
							pdfDownloadFileName, csvDownloadFilePath, csvDownloadFileName));
					searchWindow.addListener(new Window.CloseListener() {
						private static final long serialVersionUID = 5104127567125802774L;

						public void windowClose(CloseEvent e) {
							FileAccess.deleteFile(pdfDownloadFilePath);
						}
					});
					UI.getCurrent().addWindow(searchWindow);
				}
			}
		});

		buttonsLayout.addComponent(CommonsUI.getSpaceLabel(null, "10px", null));

		final Button cancelButton = new Button();
		cancelButton.setImmediate(true);
		cancelButton.addStyleName(ValoTheme.BUTTON_DANGER);
		cancelButton.addStyleName(ValoTheme.BUTTON_SMALL);
		cancelButton.setIcon(FontAwesome.ERASER);
		cancelButton.setWidth("100px");
		cancelButton.setCaption(" ≈·€«¡");
		buttonsLayout.addComponent(cancelButton);
		cancelButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 5569230699947766441L;

			public void buttonClick(ClickEvent event) {
				CommonsUI.clearComponentValue(idTextField, nameTextField, addressNumTextField,
						addressFreeTextField, phoneTextField, mobile1TextField, mobile2TextField,
						emailTextField, educationTextField, eccEducationTextField, coursesTextField,
						skillsTextField, notesTextField, birthdayYearTextField);
				CommonsUI.clearComponentValue(addressStreetComboBox, addressRegionComboBox,
						addressDistrictComboBox, birthdayDayComboBox, birthdayMonthComboBox);
				CommonsUI.clearComponentValue(birthdayDateField);
			}
		});

		layout.addComponent(CommonsUI.getSpaceLabel("50px", null, null));

		return layout;
	}

	@SuppressWarnings("unchecked")
	private static VerticalLayout getSearchResultTabSheet(ArrayList<ArrayList<Object>> searchResult,
			String pdfDownloadFilePath, String pdfDownloadFileName, String csvDownloadFilePath,
			String csvDownloadFileName) {

		StreamResource pdfResource = createResource(pdfDownloadFilePath, pdfDownloadFileName);
		StreamResource csvResource = createResource(csvDownloadFilePath, csvDownloadFileName);

		FileDownloader pdfFileDownloader = new FileDownloader(pdfResource);
		FileDownloader csvFileDownloader = new FileDownloader(csvResource);

		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setImmediate(true);

		HorizontalLayout buttonsLayout = new HorizontalLayout();
		buttonsLayout.setHeight("20%");
		layout.addComponent(buttonsLayout);

		Button printButton = new Button();
		printButton.setImmediate(true);
		printButton.setIcon(FontAwesome.PRINT);
		printButton.addStyleName(ValoTheme.BUTTON_SMALL);
		printButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 5569230699947766441L;

			public void buttonClick(ClickEvent event) {
				try {
					int result = Printer.print(new String[] { pdfDownloadFilePath });

					if (result != EMapper.ECODE_SUCCESS) {
						Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));
					}
				} catch (Exception e) {
					Logger.exception(e);
				}
			}
		});
		buttonsLayout.addComponent(printButton);

		buttonsLayout.addComponent(CommonsUI.getSpaceLabel(null, "10px", null));

		Button savePdfButton = new Button();
		savePdfButton.setImmediate(true);
		savePdfButton.setIcon(FontAwesome.DOWNLOAD);
		savePdfButton.addStyleName(ValoTheme.BUTTON_SMALL);
		savePdfButton.setCaption(" PDF");
		pdfFileDownloader.extend(savePdfButton);
		buttonsLayout.addComponent(savePdfButton);

		buttonsLayout.addComponent(CommonsUI.getSpaceLabel(null, "10px", null));

		Button saveCsvButton = new Button();
		saveCsvButton.setImmediate(true);
		saveCsvButton.setIcon(FontAwesome.DOWNLOAD);
		saveCsvButton.addStyleName(ValoTheme.BUTTON_SMALL);
		saveCsvButton.setCaption(" CSV");
		csvFileDownloader.extend(saveCsvButton);
		buttonsLayout.addComponent(saveCsvButton);

		layout.addComponent(CommonsUI.getSpaceLabel("1px", null, null));

		ArrayList<Object> tableProperties = new ArrayList<Object>();
		for (int i = 0; i < searchResult.get(0).size(); i++) {
			Object object = searchResult.get(0).get(i);
			tableProperties.add(object);
		}

		IndexedContainer resultContainer = new IndexedContainer();
		for (int i = 0; i < tableProperties.size(); i++) {
			resultContainer.addContainerProperty(tableProperties.get(i), String.class, null);
		}

		for (int i = 1; i < searchResult.size(); i++) {
			Item item = resultContainer.addItem(i - 1);
			for (int j = 0; j < tableProperties.size(); j++) {
				item.getItemProperty(tableProperties.get(j)).setValue(searchResult.get(i).get(j).toString());
			}
		}

		Table table = new Table();
		table.setSelectable(true);
		table.setSizeFull();
		table.addStyleName(ValoTheme.TABLE_COMPACT);
		table.setContainerDataSource(resultContainer);

		for (int i = 0; i < tableProperties.size(); i++) {
			table.setColumnAlignment(tableProperties.get(i), Align.RIGHT);
		}
		layout.addComponent(table);

		return layout;
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
