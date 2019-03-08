package church.ministry.ui.father;

import java.io.Serializable;

import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class FatherUI implements Serializable {

	private static final long serialVersionUID = -343135695534153348L;

	public VerticalLayout getFatherLayout() {
		final VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		layout.setMargin(true);
		layout.setCaption("«·√»«¡");

		TabSheet tabs = new TabSheet();
		tabs.addStyleName("right-aligned-tabs");
		tabs.addStyleName(ValoTheme.TABSHEET_FRAMED);
		tabs.setSizeFull();
		layout.addComponent(tabs);

		tabs.addComponent(new SearchFatherUI().getLayout());
		tabs.addComponent(new EditFatherUI().getLayout());
		tabs.addComponent(new NewFatherUI().getLayout());
		tabs.setSelectedTab(2);

		return layout;
	}
}
