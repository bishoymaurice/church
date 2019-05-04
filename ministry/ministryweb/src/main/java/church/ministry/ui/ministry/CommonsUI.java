package church.ministry.ui.ministry;

import java.io.Serializable;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

public class CommonsUI implements Serializable {
	private static final long serialVersionUID = 8958159835817145222L;

	public static Label getSpaceLabel(String height, String width, String styleName) {
		Label label = new Label();
		label.setHeight(height);
		label.setWidth(width);
		if (styleName != null) {
			label.addStyleName(styleName);
		}
		return label;
	}

	public static Label getHorizontalSeparator() {
		@SuppressWarnings("deprecation")
		Label label = new Label("<hr />", Label.CONTENT_XHTML);
		label.setWidth(null);
		return label;
	}

	public static void clearComponentValue(Label... labels) {
		for (int i = 0; i < labels.length; i++) {
			labels[i].setCaption("");
		}
	}

	public static void clearComponentValue(TextField... textFields) {
		for (int i = 0; i < textFields.length; i++) {
			textFields[i].setValue("");
		}
	}

	public static void clearComponentValue(DateField... dateFields) {
		for (int i = 0; i < dateFields.length; i++) {
			dateFields[i].setValue(null);
		}
	}

	public static void clearComponentValue(ComboBox... ComboBoxes) {
		for (int i = 0; i < ComboBoxes.length; i++) {
			ComboBoxes[i].setValue(null);
		}
	}

	public static void disableComponent(TextField... textFields) {
		for (int i = 0; i < textFields.length; i++) {
			textFields[i].setEnabled(false);
		}
	}

	public static void disableComponent(DateField... dateFields) {
		for (int i = 0; i < dateFields.length; i++) {
			dateFields[i].setEnabled(false);
		}
	}

	public static void disableComponent(ComboBox... ComboBoxes) {
		for (int i = 0; i < ComboBoxes.length; i++) {
			ComboBoxes[i].setEnabled(false);
		}
	}

	public static void disableComponent(Button... buttons) {
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setEnabled(false);
		}
	}

	public static void enableComponent(TextField... textFields) {
		for (int i = 0; i < textFields.length; i++) {
			textFields[i].setEnabled(true);
		}
	}

	public static void enableComponent(DateField... dateFields) {
		for (int i = 0; i < dateFields.length; i++) {
			dateFields[i].setEnabled(true);
		}
	}

	public static void enableComponent(ComboBox... ComboBoxes) {
		for (int i = 0; i < ComboBoxes.length; i++) {
			ComboBoxes[i].setEnabled(true);
		}
	}

	public static void enableComponent(Button... buttons) {
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setEnabled(true);
		}
	}

	public static void toggleCheckIcon(Button... button) {
		for (int i = 0; i < button.length; i++) {
			if (button[i].getIcon() == null) {
				button[i].setIcon(FontAwesome.CHECK_CIRCLE_O);
			} else {
				button[i].setIcon(null);
			}
		}
	}

	public static void removeIcon(Button... button) {
		for (int i = 0; i < button.length; i++) {
			button[i].setIcon(null);
		}
	}

	public static void setCheckIcon(Button... button) {
		for (int i = 0; i < button.length; i++) {
			button[i].setIcon(FontAwesome.CHECK_CIRCLE_O);
		}
	}
}
