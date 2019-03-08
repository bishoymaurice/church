package church.ministry.ui.child;

import java.io.Serializable;

import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class ChildUI implements Serializable {

	private static final long serialVersionUID = 6392722757530952446L;

	public VerticalLayout getChildLayout() {
		final VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		layout.setMargin(true);
		layout.setCaption("«·„ŒœÊ„Ì‰");

		TabSheet tabs;
		tabs = new TabSheet();
		tabs.addStyleName("right-aligned-tabs");
		tabs.addStyleName(ValoTheme.TABSHEET_FRAMED);
		tabs.setSizeFull();
		layout.addComponent(tabs);

		tabs.addComponent(new SearchChildUI().getLayout());
		tabs.addComponent(new EditChildUI().getLayout());
		tabs.addComponent(new NewChildUI().getLayout());
		tabs.setSelectedTab(2);

		return layout;
	}

}
