package church.ministry.ui.org;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import church.ministry.control.commons.CommonsHandler;
import church.ministry.control.ecode.EMapper;
import church.ministry.control.handler.RequestHandler;
import church.ministry.control.log.Logger;
import church.ministry.control.org.OrgHandler;
import church.ministry.ui.ministry.CommonsUI;
import church.ministry.util.Converter;

import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;

public class ChildOrg implements Serializable {

	private static final long serialVersionUID = 2670397606141225809L;

	String header = "  „ŒœÊ„  ";
	ListSelect childrenList = new ListSelect();
	MinisterOrg ministerOrg;
	SubClassOrg subClassOrg;
	ArrayList<String> selectedChildren = new ArrayList<String>();

	public void setMinisterOrg(MinisterOrg ministerOrg) {
		this.ministerOrg = ministerOrg;
	}

	public MinisterOrg getMinisterOrg() {
		return this.ministerOrg;
	}

	public SubClassOrg getGroupOrg() {
		return subClassOrg;
	}

	public void setGroupOrg(SubClassOrg subClassOrg) {
		this.subClassOrg = subClassOrg;
	}

	protected String[] getSelectedChildren() {
		try {
			String[] arr = new String[selectedChildren.size()];

			for (int i = 0; i < selectedChildren.size(); i++) {
				arr[i] = selectedChildren.get(i);
			}

			return arr;
		} catch (Exception e) {
			return null;
		}
	}

	protected int setSelectedChildren(String str) {
		try {

			String[] arr = Converter.convertStringIntoArray(str);

			while (selectedChildren.size() > 0) {
				selectedChildren.remove(0);
			}

			for (String s : arr) {
				selectedChildren.add(s);
			}

			return EMapper.ECODE_SUCCESS;
		} catch (Exception e) {
			return EMapper.ECODE_FAILURE;
		}
	}

	protected void refreshContainer(String subClassName) {
		childrenList.removeAllItems();
		if (subClassName != null) {

			ArrayList<String> children = OrgHandler.getChildrenBySubClass(subClassName);

			for (int i = 0; i < children.size(); i++) {
				childrenList.addItem(children.get(i));
			}
		}
	}

	public GridLayout getLayout() {
		GridLayout layout = new GridLayout(1, 2);
		layout.setHeight("300px");
		layout.setWidth("200px");

		MenuBar setting = new MenuBar();
		setting.addStyleName(ValoTheme.MENUBAR_SMALL);
		setting.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
		layout.addComponent(setting);

		Label caption = new Label(header, ContentMode.HTML);
		caption.addStyleName("border");
		layout.addComponent(caption);

		childrenList.setHeight("370px");
		childrenList.setWidth("200px");
		childrenList.setNullSelectionAllowed(false);
		childrenList.setImmediate(true);
		childrenList.setMultiSelect(true);
		childrenList.addValueChangeListener(event -> {
			int result = setSelectedChildren(event.getProperty().getValue().toString());
			if (result != EMapper.ECODE_SUCCESS) {
				Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));
			}
		});
		layout.addComponent(childrenList);

		final Command unassignChildCommand = new Command() {
			private static final long serialVersionUID = -4168910857126708902L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {

				String[] selectedChildren = getSelectedChildren();

				if (selectedChildren == null || selectedChildren.length == 0) {
					Notification.show("„‰ ›÷·ﬂ «Œ — «·„ŒœÊ„Ì‰", null, Type.TRAY_NOTIFICATION);
					return;
				}

				VerticalLayout vLayout = new VerticalLayout();
				vLayout.setMargin(true);

				StringBuilder sb = new StringBuilder();
				sb.append("„‰ ›÷·ﬂ ﬁ„ » √ﬂÌœ «·√„—. Â·  —Ìœ ≈·€«¡  ⁄ÌÌ‰ «·„ŒœÊ„Ì‰ «·„–ﬂÊ—Ì‰ »«·«”›·ø");
				sb.append("<br>");
				sb.append("<br>");
				for (String s : selectedChildren) {
					sb.append(s);
					sb.append("<br>");
				}

				final Label label = new Label(sb.toString(), ContentMode.HTML);
				vLayout.addComponent(label);
				vLayout.setComponentAlignment(label, Alignment.TOP_RIGHT);

				vLayout.addComponent(CommonsUI.getSpaceLabel("20", null, null));

				HorizontalLayout hLayout = new HorizontalLayout();
				vLayout.addComponent(hLayout);
				vLayout.setComponentAlignment(hLayout, Alignment.TOP_LEFT);

				Button enterButton = new Button("„Ê«›ﬁ");
				hLayout.addComponent(enterButton);

				hLayout.addComponent(CommonsUI.getSpaceLabel(null, "10px", null));

				Button cancelButton = new Button("≈·€«¡");
				hLayout.addComponent(cancelButton);

				Window confirmWindow = new Window();
				confirmWindow.center();
				confirmWindow.setModal(true);
				confirmWindow.setWidth("500");
				confirmWindow.setHeight("200");
				confirmWindow.setResizable(false);
				confirmWindow.setCaption("  √ﬂÌœ «·√„—");
				confirmWindow.setIcon(FontAwesome.EXCLAMATION_CIRCLE);
				confirmWindow.setContent(vLayout);

				enterButton.addClickListener(new Button.ClickListener() {
					private static final long serialVersionUID = 5569230699947766441L;

					public void buttonClick(ClickEvent event) {
						try {

							confirmWindow.close();

							int result = EMapper.ECODE_FAILURE;

							for (String childName : selectedChildren) {
								HashMap<String, String> requestData = new HashMap<String, String>();
								requestData.put("childName", childName);
								requestData.put("ministerName", ministerOrg.getSelectedMinister());
								requestData.put("subClassName", subClassOrg.getSelectedSubClass());

								result = RequestHandler.handleUpdateRequest("unassignChild", requestData);

								if (result != EMapper.ECODE_SUCCESS) {
									break;
								}
							}

							Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));

							refreshContainer(ministerOrg.getSelectedMinister());
							refreshContainer(subClassOrg.getSelectedSubClass());

						} catch (Exception e) {
							Logger.exception(e);
						}
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
		};

		final Command moveChildrenToAnotherSubClassCommand = new Command() {
			private static final long serialVersionUID = -4168910857126708902L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {

				String[] selectedChildren = getSelectedChildren();

				if (selectedChildren == null || selectedChildren.length == 0) {
					Notification.show("„‰ ›÷·ﬂ «Œ — «·„ŒœÊ„Ì‰", null, Type.TRAY_NOTIFICATION);
					return;
				}

				VerticalLayout vLayout = new VerticalLayout();
				vLayout.setMargin(true);

				final ComboBox allNamesComboBox = new ComboBox();
				allNamesComboBox.setCaption("≈Œ — «”„ «·„Ã„Ê⁄…");
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
						ArrayList<String> allNames = CommonsHandler
								.getAllSubClassesExcludingSomeSubclass(subClassOrg.getSelectedSubClass());
						for (int i = 0; i < allNames.size(); i++) {
							allNamesComboBox.addItem(allNames.get(i));
						}

						if (currentSelectedItem != null) {
							allNamesComboBox.addItem(currentSelectedItem);
							allNamesComboBox.setValue(currentSelectedItem);
						}
					}
				});
				vLayout.addComponent(allNamesComboBox);
				vLayout.setComponentAlignment(allNamesComboBox, Alignment.TOP_RIGHT);

				vLayout.addComponent(CommonsUI.getSpaceLabel("20", null, null));

				HorizontalLayout hLayout = new HorizontalLayout();
				vLayout.addComponent(hLayout);
				vLayout.setComponentAlignment(hLayout, Alignment.TOP_LEFT);

				Button enterButton = new Button("„Ê«›ﬁ");
				hLayout.addComponent(enterButton);

				hLayout.addComponent(CommonsUI.getSpaceLabel(null, "10px", null));

				Button cancelButton = new Button("≈·€«¡");
				hLayout.addComponent(cancelButton);

				Window confirmWindow = new Window();
				confirmWindow.center();
				confirmWindow.setModal(true);
				confirmWindow.setWidth("500");
				confirmWindow.setHeight("200");
				confirmWindow.setResizable(false);
				confirmWindow.setCaption(" ≈œŒ«· ««·»Ì«‰« ");
				confirmWindow.setIcon(FontAwesome.INFO);
				confirmWindow.setContent(vLayout);

				enterButton.addClickListener(new Button.ClickListener() {
					private static final long serialVersionUID = 5325311693662832508L;

					public void buttonClick(ClickEvent event) {

						confirmWindow.close();

						StringBuilder sb = new StringBuilder();

						for (String s : selectedChildren) {
							sb.append(s);
							sb.append("\n");
						}

						HashMap<String, String> requestData = new HashMap<String, String>();
						requestData.put("subClassName", allNamesComboBox.getValue().toString());
						requestData.put("childName", String.valueOf(sb.toString()));

						int result = RequestHandler.handleUpdateRequest("assignChild", requestData);

						Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));

						refreshContainer(subClassOrg.getSelectedSubClass());
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
		};

		MenuItem settingItems = setting.addItem("", null);

		MenuItem assignChildMenuItem = settingItems.addItem(" ≈·€«¡  ⁄ÌÌ‰ «·„ŒœÊ„", unassignChildCommand);
		assignChildMenuItem.setIcon(FontAwesome.UNLINK);

		settingItems.addSeparator();

		MenuItem moveChildrenToAnotherSubClassMenuItem = settingItems.addItem(" ‰ﬁ· ≈·Ï „Ã„Ê⁄… √Œ—Ï",
				moveChildrenToAnotherSubClassCommand);
		moveChildrenToAnotherSubClassMenuItem.setIcon(FontAwesome.ARROW_CIRCLE_LEFT);

		return layout;
	}
}
