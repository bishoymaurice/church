package church.ministry.ui.evaluation;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import church.ministry.control.commons.CommonsHandler;
import church.ministry.control.ecode.EMapper;
import church.ministry.control.minister.EvaluationHandler;
import church.ministry.ui.ministry.CommonsUI;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

public class EnterEvalUI implements Serializable {

	private static final long serialVersionUID = -4753836060401323779L;

	@SuppressWarnings("deprecation")
	public VerticalLayout getLayout() {
		final VerticalLayout enterInputsLayout = new VerticalLayout();
		enterInputsLayout.setMargin(true);
		enterInputsLayout.setCaption("≈œŒ«·  ﬁÌÌ„ «·Œ«œ„");

		final ComboBox allNamesComboBox = new ComboBox();
		allNamesComboBox.setCaption("≈Œ — «”„ «·Œ«œ„");
		allNamesComboBox.addStyleName(ValoTheme.COMBOBOX_ALIGN_RIGHT);
		allNamesComboBox.addStyleName(ValoTheme.COMBOBOX_SMALL);
		allNamesComboBox.setNewItemsAllowed(false);
		allNamesComboBox.setWidth("350px");
		enterInputsLayout.addComponent(allNamesComboBox);
		enterInputsLayout.setComponentAlignment(allNamesComboBox, Alignment.TOP_RIGHT);

		ArrayList<String> allNames = CommonsHandler.getNamesByType("minister");
		for (int i = 0; i < allNames.size(); i++) {
			allNamesComboBox.addItem(allNames.get(i));
		}

		DateField goDateField = new DateField();
		goDateField.setImmediate(true);
		goDateField.addStyleName(ValoTheme.DATEFIELD_SMALL);
		goDateField.setDateFormat("dd-MM-yyyy");
		goDateField.setInvalidAllowed(false);
		goDateField.setCaption("≈Œ — «· «—ÌŒ:");
		enterInputsLayout.addComponent(goDateField);
		enterInputsLayout.setComponentAlignment(goDateField, Alignment.TOP_RIGHT);

		enterInputsLayout.addComponent(CommonsUI.getSpaceLabel("50", null, null));

		Button ministersMeetingButton = new Button();
		ministersMeetingButton.addStyleName(ValoTheme.BUTTON_QUIET);
		ministersMeetingButton.addStyleName(ValoTheme.BUTTON_LINK);
		ministersMeetingButton.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
		ministersMeetingButton.addStyleName("button-right-aligned");
		ministersMeetingButton.setWidth("150");
		ministersMeetingButton.setCaption("≈Ã „«⁄ «·Œœ«„");
		enterInputsLayout.addComponent(ministersMeetingButton);
		enterInputsLayout.setComponentAlignment(ministersMeetingButton, Alignment.TOP_RIGHT);

		Button massButton = new Button();
		massButton.addStyleName(ValoTheme.BUTTON_QUIET);
		massButton.addStyleName(ValoTheme.BUTTON_LINK);
		massButton.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
		massButton.addStyleName("button-right-aligned");
		massButton.setWidth("150");
		massButton.setCaption("Õ÷Ê— «·ﬁœ«”");
		enterInputsLayout.addComponent(massButton);
		enterInputsLayout.setComponentAlignment(massButton, Alignment.TOP_RIGHT);

		Button ministryButton = new Button();
		ministryButton.addStyleName(ValoTheme.BUTTON_QUIET);
		ministryButton.addStyleName(ValoTheme.BUTTON_LINK);
		ministryButton.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
		ministryButton.addStyleName("button-right-aligned");
		ministryButton.setWidth("150");
		ministryButton.setCaption("Õ÷Ê— «·Œœ„…");
		enterInputsLayout.addComponent(ministryButton);
		enterInputsLayout.setComponentAlignment(ministryButton, Alignment.TOP_RIGHT);

		Button lessonPreparationButton = new Button();
		lessonPreparationButton.addStyleName(ValoTheme.BUTTON_QUIET);
		lessonPreparationButton.addStyleName(ValoTheme.BUTTON_LINK);
		lessonPreparationButton.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
		lessonPreparationButton.addStyleName("button-right-aligned");
		lessonPreparationButton.setWidth("150");
		lessonPreparationButton.setCaption(" Õ÷Ì— «·œ—”");
		enterInputsLayout.addComponent(lessonPreparationButton);
		enterInputsLayout.setComponentAlignment(lessonPreparationButton, Alignment.TOP_RIGHT);

		Button illustrationToolButton = new Button();
		illustrationToolButton.addStyleName(ValoTheme.BUTTON_QUIET);
		illustrationToolButton.addStyleName(ValoTheme.BUTTON_LINK);
		illustrationToolButton.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
		illustrationToolButton.addStyleName("button-right-aligned");
		illustrationToolButton.setWidth("150");
		illustrationToolButton.setCaption("Ê”Ì·… «·≈Ì÷«Õ");
		enterInputsLayout.addComponent(illustrationToolButton);
		enterInputsLayout.setComponentAlignment(illustrationToolButton, Alignment.TOP_RIGHT);

		Button familyMeetingButton = new Button();
		familyMeetingButton.addStyleName(ValoTheme.BUTTON_QUIET);
		familyMeetingButton.addStyleName(ValoTheme.BUTTON_LINK);
		familyMeetingButton.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
		familyMeetingButton.addStyleName("button-right-aligned");
		familyMeetingButton.setWidth("150");
		familyMeetingButton.setCaption("≈Ã „«⁄ «·√”—…");
		enterInputsLayout.addComponent(familyMeetingButton);
		enterInputsLayout.setComponentAlignment(familyMeetingButton, Alignment.TOP_RIGHT);

		CommonsUI.disableComponent(ministersMeetingButton, massButton, ministryButton,
				lessonPreparationButton, illustrationToolButton, familyMeetingButton);

		// ************************************************************//

		ministersMeetingButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
				String value = null;
				int result;

				if (ministersMeetingButton.getIcon() == null) {
					value = "1";
					CommonsUI.toggleCheckIcon(ministersMeetingButton);
				} else {
					value = "0";
					CommonsUI.toggleCheckIcon(ministersMeetingButton);
				}

				result = EvaluationHandler.setME_ministersMeeting(
						String.valueOf(allNamesComboBox.getValue()),
						dateFormatter.format(goDateField.getValue()), value);

				if (result != EMapper.ECODE_SUCCESS) {
					Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));
				}
			}
		});

		massButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
				String value = null;
				int result;

				if (massButton.getIcon() == null) {
					value = "1";
					CommonsUI.toggleCheckIcon(massButton);
				} else {
					value = "0";
					CommonsUI.toggleCheckIcon(massButton);
				}

				result = EvaluationHandler.setME_mass(String.valueOf(allNamesComboBox.getValue()),
						dateFormatter.format(goDateField.getValue()), value);

				if (result != EMapper.ECODE_SUCCESS) {
					Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));
				}
			}
		});

		ministryButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
				String value = null;
				int result;

				if (ministryButton.getIcon() == null) {
					value = "1";
					CommonsUI.toggleCheckIcon(ministryButton);
				} else {
					value = "0";
					CommonsUI.toggleCheckIcon(ministryButton);
				}

				result = EvaluationHandler.setME_ministry(String.valueOf(allNamesComboBox.getValue()),
						dateFormatter.format(goDateField.getValue()), value);

				if (result != EMapper.ECODE_SUCCESS) {
					Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));
				}
			}
		});

		lessonPreparationButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
				String value = null;
				int result;

				if (lessonPreparationButton.getIcon() == null) {
					value = "1";
					CommonsUI.toggleCheckIcon(lessonPreparationButton);
				} else {
					value = "0";
					CommonsUI.toggleCheckIcon(lessonPreparationButton);
				}

				result = EvaluationHandler.setME_lessonPreparation(
						String.valueOf(allNamesComboBox.getValue()),
						dateFormatter.format(goDateField.getValue()), value);

				if (result != EMapper.ECODE_SUCCESS) {
					Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));
				}
			}
		});

		illustrationToolButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
				String value = null;
				int result;

				if (illustrationToolButton.getIcon() == null) {
					value = "1";
					CommonsUI.toggleCheckIcon(illustrationToolButton);
				} else {
					value = "0";
					CommonsUI.toggleCheckIcon(illustrationToolButton);
				}

				result = EvaluationHandler.setME_illustrationTool(
						String.valueOf(allNamesComboBox.getValue()),
						dateFormatter.format(goDateField.getValue()), value);

				if (result != EMapper.ECODE_SUCCESS) {
					Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));
				}
			}
		});

		familyMeetingButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
				String value = null;
				int result;

				if (familyMeetingButton.getIcon() == null) {
					value = "1";
					CommonsUI.toggleCheckIcon(familyMeetingButton);
				} else {
					value = "0";
					CommonsUI.toggleCheckIcon(familyMeetingButton);
				}

				result = EvaluationHandler.setME_familyMeeting(String.valueOf(allNamesComboBox.getValue()),
						dateFormatter.format(goDateField.getValue()), value);

				if (result != EMapper.ECODE_SUCCESS) {
					Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));
				}
			}
		});

		// ************************************************************//

		allNamesComboBox.addValueChangeListener(event -> enableDisableInputButtons(
				allNamesComboBox.getValue(), goDateField.getValue(), ministersMeetingButton, massButton,
				ministryButton, lessonPreparationButton, illustrationToolButton, familyMeetingButton));

		goDateField.addListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1200529581865778526L;

			public void valueChange(ValueChangeEvent event) {
				if (goDateField.getValue() != null) {
					if (!goDateField.getValue().toString().toLowerCase().contains("fri")) {
						goDateField.setValue(null);
					} else {
						enableDisableInputButtons(allNamesComboBox.getValue(), goDateField.getValue(),
								ministersMeetingButton, massButton, ministryButton, lessonPreparationButton,
								illustrationToolButton, familyMeetingButton);
					}
				} else {
					goDateField.setValue(null);
				}
			}
		});
		return enterInputsLayout;
	}

	private void enableDisableInputButtons(Object selectedValue, Date date, Button ministersMeetingButton,
			Button massButton, Button ministryButton, Button lessonPreparationButton,
			Button illustrationToolButton, Button familyMeetingButton) {

		if (selectedValue == null || String.valueOf(selectedValue).length() == 0 || date == null) {

			CommonsUI.disableComponent(new Button[] { ministersMeetingButton, massButton, ministryButton,
					lessonPreparationButton, illustrationToolButton, familyMeetingButton });

			CommonsUI.removeIcon(new Button[] { ministersMeetingButton, massButton, ministryButton,
					lessonPreparationButton, illustrationToolButton, familyMeetingButton });
		} else {

			final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

			HashMap<String, String> evaluation = EvaluationHandler.getMinisterEvaluation(
					String.valueOf(selectedValue), dateFormatter.format(date));

			if (evaluation == null) {
				Notification.show(EMapper.getEDesc(EMapper.ECODE_FAILURE),
						EMapper.getNType(EMapper.ECODE_FAILURE));
				return;
			}

			CommonsUI.removeIcon(new Button[] { ministersMeetingButton, massButton, ministryButton,
					lessonPreparationButton, illustrationToolButton, familyMeetingButton });

			CommonsUI.enableComponent(new Button[] { ministersMeetingButton, massButton, ministryButton,
					lessonPreparationButton, illustrationToolButton, familyMeetingButton });

			if (evaluation.get("mass") != null && evaluation.get("mass").equals("1")) {
				CommonsUI.setCheckIcon(massButton);
			}

			if (evaluation.get("ministry") != null && evaluation.get("ministry").equals("1")) {
				CommonsUI.setCheckIcon(ministryButton);
			}

			if (evaluation.get("ministersMeeting") != null && evaluation.get("ministersMeeting").equals("1")) {
				CommonsUI.setCheckIcon(ministersMeetingButton);
			}

			if (evaluation.get("familyMeeting") != null && evaluation.get("familyMeeting").equals("1")) {
				CommonsUI.setCheckIcon(familyMeetingButton);
			}

			if (evaluation.get("lessonPreparation") != null
					&& evaluation.get("lessonPreparation").equals("1")) {
				CommonsUI.setCheckIcon(lessonPreparationButton);
			}

			if (evaluation.get("illustrationTool") != null && evaluation.get("illustrationTool").equals("1")) {
				CommonsUI.setCheckIcon(illustrationToolButton);
			}

		}
	}
}
