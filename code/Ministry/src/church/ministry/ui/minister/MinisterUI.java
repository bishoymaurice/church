package church.ministry.ui.minister;

import java.io.Serializable;

import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class MinisterUI implements Serializable {
	private static final long serialVersionUID = 3300527321866827830L;

	public VerticalLayout getMinisterLayout() {
		final VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		layout.setMargin(true);
		layout.setCaption("«·Œœ«„");

		TabSheet tabs = new TabSheet();
		tabs.addStyleName("right-aligned-tabs");
		tabs.addStyleName(ValoTheme.TABSHEET_FRAMED);
		tabs.setSizeFull();
		layout.addComponent(tabs);

		tabs.addComponent(new SearchMinisterUI().getLayout());
		tabs.addComponent(new EditMinisterUI().getLayout());
		tabs.addComponent(new NewMinisterUI().getLayout());
		tabs.setSelectedTab(2);

		return layout;
	}

}
