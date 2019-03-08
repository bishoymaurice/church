package church.ministry.ui.ministry;

import java.io.Serializable;

import javax.servlet.annotation.WebServlet;

import church.ministry.control.ecode.EMapper;
import church.ministry.control.gc.GCThread;
import church.ministry.control.log.Logger;
import church.ministry.model.oracle.DatabaseConnectionManager;
import church.ministry.ui.child.ChildUI;
import church.ministry.ui.evaluation.MinisterEvaluationUI;
import church.ministry.ui.father.FatherUI;
import church.ministry.ui.minister.MinisterUI;
import church.ministry.ui.org.Org;
import church.ministry.util.Init;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@Theme("ministry")
public class MinistryUI extends UI implements Serializable {

	private final CssLayout root = new CssLayout();

	@WebServlet(value = "/*", asyncSupported = true)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {

		Logger.init();
		Init.initiate();

		GCThread gcThread = new GCThread();
		Thread thread1 = new Thread(gcThread);
		thread1.start();

		if (!DatabaseConnectionManager.connect()) {
			int result = EMapper.ECODE_DATABASE_CONN_FAILURE;
			Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));
			return;
		}

		root.removeAllComponents();
		root.setSizeFull();
		setContent(root);

		final TabSheet tabs = new TabSheet();
		tabs.addStyleName("right-aligned-tabs");
		tabs.addStyleName("right-aligned");
		tabs.addStyleName(ValoTheme.TABSHEET_EQUAL_WIDTH_TABS);
		tabs.setSizeFull();
		root.addComponent(tabs);

		tabs.addComponent(new MinisterEvaluationUI().getLayout());
		tabs.addComponent(new Org().getLayout());
		tabs.addComponent(new ChildUI().getChildLayout());
		tabs.addComponent(new MinisterUI().getMinisterLayout());
		tabs.addComponent(new FatherUI().getFatherLayout());

		tabs.setSelectedTab(4);
	}

}