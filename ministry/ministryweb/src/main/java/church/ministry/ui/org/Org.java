package church.ministry.ui.org;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import church.ministry.control.commons.CommonsHandler;
import church.ministry.control.ecode.EMapper;
import church.ministry.control.org.OrgHandler;
import church.ministry.ui.ministry.CommonsUI;
import church.ministry.util.Validator;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;

public class Org implements Serializable {

	private static final long serialVersionUID = -3973643078388840874L;

	@SuppressWarnings("deprecation")
	public VerticalLayout getLayout() {

		final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		layout.setHeight(null);
		layout.setWidth(null);
		layout.setCaption("«·≈› ﬁ«œ");

		GridLayout gridLayout = new GridLayout(13, 2);
		gridLayout.setMargin(true);
		layout.addComponent(gridLayout);
		layout.setComponentAlignment(gridLayout, Alignment.TOP_RIGHT);

		SectionOrg sectionOrg = new SectionOrg();
		FamilyOrg familyOrg = new FamilyOrg();
		YearOrg yearOrg = new YearOrg();
		ClassOrg classOrg = new ClassOrg();
		MinisterOrg ministerOrg = new MinisterOrg();
		SubClassOrg subClassOrg = new SubClassOrg();
		ChildOrg childOrg = new ChildOrg();

		sectionOrg.setFamilyOrg(familyOrg);
		familyOrg.setSectionOrg(sectionOrg);
		familyOrg.setYearOrg(yearOrg);
		yearOrg.setFamilyOrg(familyOrg);
		yearOrg.setClassOrg(classOrg);
		classOrg.setYearOrg(yearOrg);
		classOrg.setGroupOrg(subClassOrg);
		ministerOrg.setClassOrg(classOrg);
		ministerOrg.setChildOrg(childOrg);
		subClassOrg.setClassOrg(classOrg);
		subClassOrg.setChildOrg(childOrg);
		subClassOrg.setMinisterOrg(ministerOrg);
		childOrg.setMinisterOrg(ministerOrg);
		childOrg.setGroupOrg(subClassOrg);

		GridLayout childLayout = childOrg.getLayout();
		gridLayout.addComponent(childLayout);
		gridLayout.setComponentAlignment(childLayout, Alignment.TOP_RIGHT);

		gridLayout.addComponent(CommonsUI.getSpaceLabel(null, "10px", null));

		// GridLayout ministerLayout = ministerOrg.getLayout();
		// we need to hide ministers
		// gridLayout.addComponent(ministerLayout);
		// gridLayout.setComponentAlignment(ministerLayout,
		// Alignment.TOP_RIGHT);

		gridLayout.addComponent(CommonsUI.getSpaceLabel(null, "10px", null));

		GridLayout groupLayout = subClassOrg.getLayout();
		gridLayout.addComponent(groupLayout);
		gridLayout.setComponentAlignment(groupLayout, Alignment.TOP_RIGHT);

		gridLayout.addComponent(CommonsUI.getSpaceLabel(null, "10px", null));

		GridLayout classLayout = classOrg.getLayout();
		gridLayout.addComponent(classLayout);
		gridLayout.setComponentAlignment(classLayout, Alignment.TOP_RIGHT);

		gridLayout.addComponent(CommonsUI.getSpaceLabel(null, "10px", null));

		GridLayout yearLayout = yearOrg.getLayout();
		gridLayout.addComponent(yearLayout);
		gridLayout.setComponentAlignment(yearLayout, Alignment.TOP_RIGHT);

		gridLayout.addComponent(CommonsUI.getSpaceLabel(null, "10px", null));

		GridLayout familyLayout = familyOrg.getLayout();
		gridLayout.addComponent(familyLayout);
		gridLayout.setComponentAlignment(familyLayout, Alignment.TOP_RIGHT);

		gridLayout.addComponent(CommonsUI.getSpaceLabel(null, "10px", null));

		GridLayout sectionLayout = sectionOrg.getLayout();
		gridLayout.addComponent(sectionLayout);
		gridLayout.setComponentAlignment(sectionLayout, Alignment.TOP_RIGHT);

		gridLayout.addComponent(CommonsUI.getSpaceLabel("30", null, null));

		layout.addComponent(CommonsUI.getSpaceLabel("30", null, null));
		// //////////////////////////////////////////////////////////////////////////////////

		HorizontalLayout goLayout = new HorizontalLayout();
		goLayout.setMargin(true);
		layout.addComponent(goLayout);
		layout.setComponentAlignment(goLayout, Alignment.TOP_RIGHT);

		Label goLabel = new Label("≈Œ — «· «—ÌŒ");
		goLayout.addComponent(goLabel);
		goLayout.addComponent(CommonsUI.getSpaceLabel(null, "30px", null));

		DateField goDateField = new DateField();
		goDateField.setImmediate(true);
		goDateField.addStyleName(ValoTheme.DATEFIELD_SMALL);
		goDateField.setDateFormat("dd-MM-yyyy");
		goDateField.setInvalidAllowed(false);
		goLayout.addComponent(goDateField);

		goDateField.addListener(new ValueChangeListener() {

			private static final long serialVersionUID = 1200529581865778526L;

			public void valueChange(ValueChangeEvent event) {
				if (goDateField.getValue() != null) {
					if (!goDateField.getValue().toString().toLowerCase().contains("fri")) {
						goDateField.setValue(null);
						ministerOrg.setGoDate(null);
						subClassOrg.setGoDate(null);
						familyOrg.setGoDate(null);
						sectionOrg.setGoDate(null);
						yearOrg.setGoDate(null);
						classOrg.setGoDate(null);
					} else {
						ministerOrg.setGoDate(dateFormatter.format(goDateField.getValue()));
						subClassOrg.setGoDate(dateFormatter.format(goDateField.getValue()));
						familyOrg.setGoDate(dateFormatter.format(goDateField.getValue()));
						sectionOrg.setGoDate(dateFormatter.format(goDateField.getValue()));
						yearOrg.setGoDate(dateFormatter.format(goDateField.getValue()));
						classOrg.setGoDate(dateFormatter.format(goDateField.getValue()));
					}
				} else {
					goDateField.setValue(null);
					ministerOrg.setGoDate(null);
					subClassOrg.setGoDate(null);
					familyOrg.setGoDate(null);
					sectionOrg.setGoDate(null);
					yearOrg.setGoDate(null);
					classOrg.setGoDate(null);
				}
			}

		});

		goLayout.addComponent(CommonsUI.getSpaceLabel(null, "30px", null));

		Button goButton = new Button();
		goButton.setWidth("40");
		goButton.setIcon(FontAwesome.ARROW_LEFT);
		goButton.addStyleName(ValoTheme.BUTTON_SMALL);
		goButton.addStyleName(ValoTheme.BUTTON_DANGER);
		goLayout.addComponent(goButton);

		// //////////////////////////////////////////////////////////////////////////////////

		VerticalLayout followupLayout = new VerticalLayout();
		followupLayout.setMargin(true);
		followupLayout.setWidth(null);
		layout.addComponent(followupLayout);
		layout.setComponentAlignment(followupLayout, Alignment.TOP_RIGHT);

		goButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 5325311693662832508L;

			public void buttonClick(ClickEvent event) {

				if (goDateField.getValue() == null) {
					Notification.show("„‰ ›÷·ﬂ «Œ — «· «—ÌŒ", null, Type.TRAY_NOTIFICATION);
					return;
				}

				followupLayout.removeAllComponents();

				ArrayList<ArrayList<Object>> namesList = new ArrayList<ArrayList<Object>>();
				ArrayList<Label> names = new ArrayList<Label>();
				ArrayList<CheckBox> ministryAttendance = new ArrayList<CheckBox>();
				ArrayList<CheckBox> massAttendance = new ArrayList<CheckBox>();
				ArrayList<CheckBox> followup = new ArrayList<CheckBox>();
				ArrayList<ComboBox> followupBy = new ArrayList<ComboBox>();
				ArrayList<ComboBox> followupComment = new ArrayList<ComboBox>();

				ArrayList<String> ministryAttendantsIdsList = new ArrayList<String>();
				ArrayList<String> massAttendantsIdsList = new ArrayList<String>();

				ArrayList<ArrayList<Object>> followupData = new ArrayList<ArrayList<Object>>();
				ArrayList<String> followupIdsList = new ArrayList<String>();

				String goDateString = dateFormatter.format(goDateField.getValue());

				if (ministerOrg.getSelectedMinister() != null) {

					if (Validator.validateString(ministerOrg.getSelectedMinister())) {
						namesList = OrgHandler.getChildrenByMinisterAndDate(
								ministerOrg.getSelectedMinister(), goDateString);

						followupLayout.addComponent(new Label("«·»Ì«‰«  «·„⁄—Ê÷… ··Œ«œ„: "
								+ ministerOrg.getSelectedMinister() + " - » «—ÌŒ: " + goDateString));
					} else {
						Notification.show("„‰ ›÷·ﬂ ﬁ„ » ⁄ÌÌ‰ Œ«œ„ «·„Ã„Ê⁄… √Ê·«", null,
								Type.TRAY_NOTIFICATION);
						return;
					}
				} else if (classOrg.getSelectedClass() != null) {

					namesList = OrgHandler.getChildrenByClassAndDate(classOrg.getSelectedClass(),
							goDateString);

					followupLayout.addComponent(new Label("«·»Ì«‰«  «·„⁄—Ê÷… ·›’·: "
							+ classOrg.getSelectedClass() + " - » «—ÌŒ: " + goDateString));

				} else if (yearOrg.getSelectedYear() != null) {

					namesList = OrgHandler.getChildrenByYearAndDate(yearOrg.getSelectedYear(), goDateString);

					followupLayout.addComponent(new Label("«·»Ì«‰«  «·„⁄—Ê÷… ·’›: "
							+ yearOrg.getSelectedYear() + " - » «—ÌŒ: " + goDateString));

				} else if (familyOrg.getSelectedFamily() != null) {

					namesList = OrgHandler.getChildrenByFamilyAndDate(familyOrg.getSelectedFamily(),
							goDateString);

					followupLayout.addComponent(new Label("«·»Ì«‰«  «·„⁄—Ê÷… ·√”—…: "
							+ familyOrg.getSelectedFamily() + " - » «—ÌŒ: " + goDateString));

				} else if (sectionOrg.getSelectedSection() != null) {

					namesList = OrgHandler.getChildrenBySectionAndDate(sectionOrg.getSelectedSection(),
							goDateString);

					followupLayout.addComponent(new Label("«·»Ì«‰«  «·„⁄—Ê÷… ·ﬁÿ«⁄: "
							+ sectionOrg.getSelectedSection() + " - » «—ÌŒ: " + goDateString));
				}

				ArrayList<String> allFollowupComments = new ArrayList<String>();

				if (namesList.size() > 0) {
					ministryAttendantsIdsList = OrgHandler.getMinistryAttendantsIdsByDate(goDateString);

					massAttendantsIdsList = OrgHandler.getMassAttendantsIdsByDate(goDateString);

					followupData = OrgHandler.getFollowupDataByDate(goDateString);

					for (int i = 0; i < followupData.size(); i++) {
						followupIdsList.add(followupData.get(i).get(0).toString());
					}

					allFollowupComments = CommonsHandler.getAllFollowupComments();
				}

				for (int i = 0; i < namesList.size(); i++) {
					final String childName = namesList.get(i).get(1).toString();
					Label nameLabel = new Label(childName);
					nameLabel.setStyleName(ValoTheme.LABEL_BOLD);
					names.add(nameLabel);

					CheckBox ministryAttendanceCheckBox = new CheckBox();
					ministryAttendanceCheckBox.addStyleName(ValoTheme.CHECKBOX_LARGE);
					ministryAttendance.add(ministryAttendanceCheckBox);

					CheckBox massAttendanceCheckBox = new CheckBox();
					massAttendanceCheckBox.addStyleName(ValoTheme.CHECKBOX_LARGE);
					massAttendance.add(massAttendanceCheckBox);

					CheckBox followupCheckBox = new CheckBox();
					followupCheckBox.addStyleName(ValoTheme.CHECKBOX_LARGE);
					followup.add(followupCheckBox);

					ComboBox followupByComboBox = new ComboBox();
					followupByComboBox.addStyleName(ValoTheme.COMBOBOX_ALIGN_CENTER);
					followupByComboBox.addStyleName(ValoTheme.COMBOBOX_SMALL);
					followupByComboBox.addItem("„ﬂ«·„…  ·Ì›Ê‰Ì…");
					followupByComboBox.addItem("“Ì«—… „‰“·Ì…");
					followupByComboBox.setNewItemsAllowed(false);
					followupByComboBox.setNullSelectionAllowed(false);
					followupByComboBox.setTextInputAllowed(false);
					followupByComboBox.setValue("„ﬂ«·„…  ·Ì›Ê‰Ì…");
					followupByComboBox.setEnabled(false);
					followupByComboBox.setWidth("130");
					followupBy.add(followupByComboBox);

					final ComboBox followupCommentComboBox = new ComboBox();
					followupCommentComboBox.addStyleName(ValoTheme.COMBOBOX_SMALL);
					followupCommentComboBox.setWidth("350");
					followupCommentComboBox.setNewItemsAllowed(true);
					followupCommentComboBox.setEnabled(false);
					followupComment.add(followupCommentComboBox);

					for (int j = 0; j < allFollowupComments.size(); j++) {
						followupCommentComboBox.addItem(allFollowupComments.get(j));
					}

					if (ministryAttendantsIdsList.contains(namesList.get(i).get(0).toString())) {
						ministryAttendanceCheckBox.setValue(true);
					}

					if (massAttendantsIdsList.contains(namesList.get(i).get(0).toString())) {
						massAttendanceCheckBox.setValue(true);
					}

					if (followupIdsList.contains(namesList.get(i).get(0).toString())) {
						int index = followupIdsList.indexOf(namesList.get(i).get(0).toString());
						followupCheckBox.setValue(true);
						followupByComboBox.setEnabled(true);
						followupByComboBox.setValue(followupData.get(index).get(1));
						followupCommentComboBox.setEnabled(true);
						followupCommentComboBox.setValue(followupData.get(index).get(2));
					}

					ministryAttendanceCheckBox.addValueChangeListener(new ValueChangeListener() {
						private static final long serialVersionUID = 459399917558110686L;

						@Override
						public void valueChange(ValueChangeEvent event) {

							boolean result = OrgHandler.addRemoveChildMinistryAttendance(childName,
									dateFormatter.format(goDateField.getValue()),
									ministryAttendanceCheckBox.getValue(), followupCheckBox.getValue());

							if (!result) {
								Notification.show(EMapper.getEDesc(EMapper.ECODE_FAILURE),
										EMapper.getNType(EMapper.ECODE_FAILURE));
							}
						}
					});

					massAttendanceCheckBox.addValueChangeListener(new ValueChangeListener() {
						private static final long serialVersionUID = 459399917558110686L;

						@Override
						public void valueChange(ValueChangeEvent event) {

							boolean result = OrgHandler.addRemoveChildMassAttendance(childName,
									dateFormatter.format(goDateField.getValue()),
									massAttendanceCheckBox.getValue());

							if (!result) {
								Notification.show(EMapper.getEDesc(EMapper.ECODE_FAILURE),
										EMapper.getNType(EMapper.ECODE_FAILURE));
							}
						}
					});

					followupCheckBox.addValueChangeListener(new ValueChangeListener() {
						private static final long serialVersionUID = 459399917558110686L;

						@Override
						public void valueChange(ValueChangeEvent event) {
							if (followupCheckBox.getValue() == true) {
								followupByComboBox.setEnabled(true);
								followupCommentComboBox.setEnabled(true);
							} else {
								followupByComboBox.setEnabled(false);
								followupByComboBox.setValue("„ﬂ«·„…  ·Ì›Ê‰Ì…");
								followupCommentComboBox.setEnabled(false);
								followupCommentComboBox.setValue(null);
							}

							boolean result = OrgHandler.addRemoveChildFollowup(childName,
									dateFormatter.format(goDateField.getValue()),
									followupCheckBox.getValue(), ministryAttendanceCheckBox.getValue());

							if (!result) {
								Notification.show(EMapper.getEDesc(EMapper.ECODE_FAILURE),
										EMapper.getNType(EMapper.ECODE_FAILURE));
								return;
							}
						}
					});

					followupByComboBox.addValueChangeListener(new ValueChangeListener() {
						private static final long serialVersionUID = 459399917558110686L;

						@Override
						public void valueChange(ValueChangeEvent event) {

							boolean result = OrgHandler.updateFollowupBy(childName, dateFormatter
									.format(goDateField.getValue()), followupByComboBox.getValue().toString());

							if (!result) {
								Notification.show(EMapper.getEDesc(EMapper.ECODE_FAILURE),
										EMapper.getNType(EMapper.ECODE_FAILURE));
							}
						}
					});

					followupCommentComboBox.addValueChangeListener(new ValueChangeListener() {
						private static final long serialVersionUID = 459399917558110686L;

						@Override
						public void valueChange(ValueChangeEvent event) {

							boolean result = OrgHandler.updateFollowupComment(childName,
									dateFormatter.format(goDateField.getValue()),
									String.valueOf(followupCommentComboBox.getValue()));

							if (!result) {
								Notification.show(EMapper.getEDesc(EMapper.ECODE_FAILURE),
										EMapper.getNType(EMapper.ECODE_FAILURE));
							}

						}
					});
				}

				// ////////////////////////////////////////////////////////////////

				if (namesList.size() > 0) {
					HorizontalLayout hLayout = new HorizontalLayout();
					followupLayout.addComponent(hLayout);

					VerticalLayout nameItemsLayout = new VerticalLayout();
					nameItemsLayout.addStyleName(ValoTheme.LAYOUT_CARD);
					nameItemsLayout.addStyleName("smallmargin-heavy");
					nameItemsLayout.setWidth(null);
					hLayout.addComponent(nameItemsLayout);

					hLayout.addComponent(CommonsUI.getSpaceLabel(null, "20", null));

					VerticalLayout ministryAttendanceItemsLayout = new VerticalLayout();
					ministryAttendanceItemsLayout.addStyleName(ValoTheme.LAYOUT_CARD);
					ministryAttendanceItemsLayout.addStyleName("smallmargin-heavy");
					hLayout.addComponent(ministryAttendanceItemsLayout);

					hLayout.addComponent(CommonsUI.getSpaceLabel(null, "20", null));

					VerticalLayout massAttendanceItemsLayout = new VerticalLayout();
					massAttendanceItemsLayout.addStyleName(ValoTheme.LAYOUT_CARD);
					massAttendanceItemsLayout.addStyleName("smallmargin-heavy");
					hLayout.addComponent(massAttendanceItemsLayout);

					hLayout.addComponent(CommonsUI.getSpaceLabel(null, "20", null));

					VerticalLayout followupItemsLayout = new VerticalLayout();
					followupItemsLayout.addStyleName(ValoTheme.LAYOUT_CARD);
					followupItemsLayout.addStyleName("smallmargin-light");
					hLayout.addComponent(followupItemsLayout);

					// ******************************************************************************
					Label nameHeader = new Label("«·«”„");
					nameHeader.setSizeUndefined();
					nameHeader.addStyleName(ValoTheme.LABEL_BOLD);
					nameHeader.addStyleName("heavyredlabel");

					nameItemsLayout.addComponent(nameHeader);
					nameItemsLayout.setComponentAlignment(nameHeader, Alignment.TOP_CENTER);

					nameItemsLayout.addComponent(CommonsUI.getSpaceLabel(null, null, ValoTheme.LABEL_SMALL));

					for (int i = 0; i < names.size(); i++) {
						nameItemsLayout.addComponent(names.get(i));
						nameItemsLayout.addComponent(CommonsUI.getSpaceLabel("31", null, null));
					}

					// ******************************************************************************
					Label ministryAttendanceHeader = new Label("Õ «·Œœ„…");
					ministryAttendanceHeader.setSizeUndefined();
					ministryAttendanceHeader.setStyleName(ValoTheme.LABEL_BOLD);
					ministryAttendanceHeader.addStyleName("heavyredlabel");

					ministryAttendanceItemsLayout.addComponent(ministryAttendanceHeader);
					ministryAttendanceItemsLayout.setComponentAlignment(ministryAttendanceHeader,
							Alignment.TOP_CENTER);

					ministryAttendanceItemsLayout.addComponent(CommonsUI.getSpaceLabel(null, null,
							ValoTheme.LABEL_SMALL));

					for (int i = 0; i < names.size(); i++) {
						ministryAttendanceItemsLayout.addComponent(ministryAttendance.get(i));
						ministryAttendanceItemsLayout.setComponentAlignment(ministryAttendance.get(i),
								Alignment.TOP_CENTER);
						ministryAttendanceItemsLayout.addComponent(CommonsUI.getSpaceLabel("31", null, null));
					}

					// ******************************************************************************
					Label massAttendanceHeader = new Label("Õ «·ﬁœ«”");
					massAttendanceHeader.setSizeUndefined();
					massAttendanceHeader.setStyleName(ValoTheme.LABEL_BOLD);
					massAttendanceHeader.addStyleName("heavyredlabel");

					massAttendanceItemsLayout.addComponent(massAttendanceHeader);
					massAttendanceItemsLayout.setComponentAlignment(massAttendanceHeader,
							Alignment.TOP_CENTER);

					massAttendanceItemsLayout.addComponent(CommonsUI.getSpaceLabel(null, null,
							ValoTheme.LABEL_SMALL));

					for (int i = 0; i < names.size(); i++) {
						massAttendanceItemsLayout.addComponent(massAttendance.get(i));
						massAttendanceItemsLayout.setComponentAlignment(massAttendance.get(i),
								Alignment.TOP_CENTER);
						massAttendanceItemsLayout.addComponent(CommonsUI.getSpaceLabel("31", null, null));
					}

					// ******************************************************************************
					Label followupHeader = new Label("«·≈› ﬁ«œ");
					followupHeader.setSizeUndefined();
					followupHeader.setStyleName(ValoTheme.LABEL_BOLD);
					followupHeader.addStyleName("heavyredlabel");

					followupItemsLayout.addComponent(followupHeader);
					followupItemsLayout.setComponentAlignment(followupHeader, Alignment.TOP_CENTER);

					followupItemsLayout.addComponent(CommonsUI.getSpaceLabel(null, null,
							ValoTheme.LABEL_SMALL));

					for (int i = 0; i < names.size(); i++) {
						HorizontalLayout followupItemsHLayout = new HorizontalLayout();

						followupItemsHLayout.addComponent(followup.get(i));

						followupItemsHLayout.addComponent(CommonsUI.getSpaceLabel(null, "20", null));

						followupItemsHLayout.addComponent(followupBy.get(i));

						followupItemsHLayout.addComponent(CommonsUI.getSpaceLabel(null, "20", null));

						followupItemsHLayout.addComponent(followupComment.get(i));

						followupItemsLayout.addComponent(followupItemsHLayout);
						followupItemsLayout.addComponent(CommonsUI.getSpaceLabel(null, null,
								ValoTheme.LABEL_SMALL));
					}
				} else {
					Notification.show(EMapper.getEDesc(EMapper.ECODE_NO_RECORD_FOUND),
							EMapper.getNType(EMapper.ECODE_NO_RECORD_FOUND));
				}
			}
		});

		return layout;
	}
}