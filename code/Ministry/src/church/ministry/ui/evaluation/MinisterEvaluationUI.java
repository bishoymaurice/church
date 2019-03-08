package church.ministry.ui.evaluation;

import java.io.Serializable;

import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class MinisterEvaluationUI implements Serializable {

	private static final long serialVersionUID = 8650118226365366100L;

	public VerticalLayout getLayout() {
		final VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		layout.setMargin(true);
		layout.setCaption("«· ﬁÌÌ„ «·»‰«¡");

		final TabSheet tabs = new TabSheet();
		tabs.addStyleName("right-aligned-tabs");
		tabs.addStyleName(ValoTheme.TABSHEET_FRAMED);
		tabs.setSizeFull();
		layout.addComponent(tabs);

		tabs.addComponent(new EvalReportUI().getLayout());
		tabs.addComponent(new EnterEvalUI().getLayout());
		tabs.setSelectedTab(1);

		return layout;
	}

}