package church.ministry.ui.org;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import church.ministry.control.commons.CommonsHandler;
import church.ministry.control.ecode.EMapper;
import church.ministry.control.handler.RequestHandler;
import church.ministry.control.log.Logger;
import church.ministry.control.org.OrgHandler;
import church.ministry.control.printing.Printer;
import church.ministry.model.file.FileAccess;
import church.ministry.ui.ministry.CommonsUI;
import church.ministry.util.Validator;

import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
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
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.themes.ValoTheme;

public class MinisterOrg implements Serializable {

	private static final long serialVersionUID = -8173724753347295406L;

	String header = "«·Œ«œ„";
	IndexedContainer container;
	Table table = new Table();
	ClassOrg classOrg;
	ChildOrg childOrg;
	String goDate;

	public void setGoDate(String goDate) {
		this.goDate = goDate;
	}

	public void setClassOrg(ClassOrg classOrg) {
		this.classOrg = classOrg;
	}

	public ClassOrg getClassOrg() {
		return this.classOrg;
	}

	public void setChildOrg(ChildOrg childOrg) {
		this.childOrg = childOrg;
	}

	public ChildOrg getChildOrg() {
		return this.childOrg;
	}

	protected String getSelectedMinister() {
		try {
			return container.getItem(table.getValue()).toString();
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	protected void refreshContainer(String subClassName) {
		container.removeAllItems();

		if (subClassName != null) {

			String ministerName = OrgHandler.getMinisterBySubClass(subClassName);

			Object id = container.addItem();
			Item item = container.getItem(id);

			if (item != null) {
				item.getItemProperty(header).setValue(ministerName);
			}

			table.select(id);

			this.childOrg.refreshContainer(subClassName);
		}
	}

	public GridLayout getLayout() {
		GridLayout layout = new GridLayout(1, 2);
		layout.setHeight("70px");
		layout.setWidth("200px");

		MenuBar setting = new MenuBar();
		setting.addStyleName(ValoTheme.MENUBAR_SMALL);
		setting.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
		// layout.addComponent(setting);

		table.setHeight("70px");
		table.setWidth("200px");
		table.setSelectable(false);
		table.setSortEnabled(false);
		table.addStyleName(ValoTheme.TABLE_BORDERLESS);
		table.addStyleName(ValoTheme.TABLE_SMALL);
		table.setColumnAlignment(header, Align.RIGHT);
		table.addValueChangeListener(e -> childOrg.refreshContainer(container.getItem(table.getValue())
				.toString()));
		layout.addComponent(table);

		container = new IndexedContainer();
		container.addContainerProperty(header, String.class, header);
		table.setContainerDataSource(container);

		// ///////////////////////////////////////////////////////////////
		// /////////////////////////////COMMANDS//////////////////////////
		final Command assignChildCommand = new Command() {
			private static final long serialVersionUID = -4168910857126708902L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				if (getSelectedMinister() == null) {
					Notification.show("„‰ ›÷·ﬂ «Œ — «·Œ«œ„", null, Type.TRAY_NOTIFICATION);
					return;
				}

				VerticalLayout vLayout = new VerticalLayout();
				vLayout.setMargin(true);

				final ComboBox allNamesComboBox = new ComboBox();
				allNamesComboBox.setCaption("≈Œ — «”„ «·„ŒœÊ„");
				allNamesComboBox.addStyleName(ValoTheme.COMBOBOX_ALIGN_RIGHT);
				allNamesComboBox.addStyleName(ValoTheme.COMBOBOX_SMALL);
				allNamesComboBox.setNewItemsAllowed(false);
				allNamesComboBox.setWidth("350px");
				vLayout.addComponent(allNamesComboBox);
				vLayout.setComponentAlignment(allNamesComboBox, Alignment.TOP_RIGHT);
				allNamesComboBox.addFocusListener(new FocusListener() {
					private static final long serialVersionUID = 8721337946386845992L;

					public void focus(FocusEvent event) {

						String currentSelectedItem = null;
						if (allNamesComboBox.getValue() != null) {
							currentSelectedItem = allNamesComboBox.getValue().toString();
						}

						allNamesComboBox.removeAllItems();
						ArrayList<String> allNames = CommonsHandler.getNamesByType("child");
						for (int i = 0; i < allNames.size(); i++) {
							allNamesComboBox.addItem(allNames.get(i));
						}

						if (currentSelectedItem != null) {
							allNamesComboBox.addItem(currentSelectedItem);
							allNamesComboBox.setValue(currentSelectedItem);
						}
					}
				});

				vLayout.addComponent(CommonsUI.getSpaceLabel("20", null, null));

				TextArea childrenTextArea = new TextArea();
				childrenTextArea.setSizeFull();
				childrenTextArea.setCaption("√”„«¡ «·√ÿ›«· «·„—«œ  ⁄Ì‰Â„:");
				vLayout.addComponent(childrenTextArea);

				allNamesComboBox.addValueChangeListener(new ValueChangeListener() {
					private static final long serialVersionUID = 8574536515233780208L;

					public void valueChange(ValueChangeEvent event) {

						if (allNamesComboBox.getValue() != null) {

							String newValue;

							if (childrenTextArea.getValue().length() != 0) {
								newValue = childrenTextArea.getValue() + "\n" + allNamesComboBox.getValue();
							} else {
								newValue = allNamesComboBox.getValue().toString();
							}

							newValue = newValue.substring(0, newValue.length());

							childrenTextArea.setValue(newValue);
						}
					}
				});

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
				confirmWindow.setHeight("400");
				confirmWindow.setResizable(false);
				confirmWindow.setCaption(" ≈œŒ«· ««·»Ì«‰« ");
				confirmWindow.setIcon(FontAwesome.INFO);
				confirmWindow.setContent(vLayout);

				enterButton.addClickListener(new Button.ClickListener() {
					private static final long serialVersionUID = 5325311693662832508L;

					public void buttonClick(ClickEvent event) {

						confirmWindow.close();

						HashMap<String, String> requestData = new HashMap<String, String>();
						requestData.put("ministerName", container.getItem(table.getValue()).toString());
						requestData.put("childName", String.valueOf(childrenTextArea.getValue()));

						int result = RequestHandler.handleUpdateRequest("assignChild", requestData);

						Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));

						childOrg.refreshContainer(getSelectedMinister());
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

		// ///////////////////////////////////////////////////////////////

		final Command downloadReportCommand = new Command() {
			private static final long serialVersionUID = -4168910857126708902L;

			@SuppressWarnings({ "deprecation" })
			@Override
			public void menuSelected(final MenuItem selectedItem) {
				if (getSelectedMinister() == null) {
					Notification.show("„‰ ›÷·ﬂ «Œ — «·Œ«œ„", null, Type.TRAY_NOTIFICATION);
					return;
				} else if (goDate == null || goDate.length() == 0) {
					Notification.show("„‰ ›÷·ﬂ «Œ — «· «—ÌŒ", null, Type.TRAY_NOTIFICATION);
					return;
				}

				if (!Validator.validateString(goDate)) {
					Notification.show(EMapper.getEDesc(EMapper.ECODE_INVALID_INPUT_DATA),
							EMapper.getNType(EMapper.ECODE_INVALID_INPUT_DATA));
					return;
				}

				String reportFileName = OrgHandler.generateEftqadReportPDF(getSelectedMinister(), goDate);

				if (!Validator.validateString(reportFileName)) {
					Notification.show(EMapper.getEDesc(EMapper.ECODE_UNKNOWN_ERROR),
							EMapper.getNType(EMapper.ECODE_UNKNOWN_ERROR));
					return;
				}

				String downloadFileName = null;
				if (reportFileName.contains("/")) {
					downloadFileName = reportFileName.substring(reportFileName.lastIndexOf("/") + 1,
							reportFileName.length());
				} else {
					downloadFileName = reportFileName.substring(reportFileName.lastIndexOf("\\") + 1,
							reportFileName.length());
				}

				StreamResource myResource = createResource(reportFileName, downloadFileName);
				FileDownloader fileDownloader = new FileDownloader(myResource);

				VerticalLayout vLayout = new VerticalLayout();
				vLayout.setMargin(true);

				final Label label = new Label();
				label.setCaption("≈÷€ÿ „Ê«›ﬁ · ‰“Ì· «· ﬁ—Ì—");
				vLayout.addComponent(label);
				vLayout.setComponentAlignment(label, Alignment.TOP_RIGHT);

				vLayout.addComponent(CommonsUI.getSpaceLabel("20", null, null));

				HorizontalLayout hLayout = new HorizontalLayout();
				vLayout.addComponent(hLayout);
				vLayout.setComponentAlignment(hLayout, Alignment.TOP_LEFT);

				Button enterButton = new Button("„Ê«›ﬁ");
				fileDownloader.extend(enterButton);
				hLayout.addComponent(enterButton);

				hLayout.addComponent(CommonsUI.getSpaceLabel(null, "10px", null));

				Button cancelButton = new Button("≈·€«¡");
				hLayout.addComponent(cancelButton);

				Window confirmWindow = new Window();
				confirmWindow.center();
				confirmWindow.setModal(true);
				confirmWindow.setWidth("500");
				confirmWindow.setHeight("180");
				confirmWindow.setResizable(false);
				confirmWindow.setCaption("  ‰“»·");
				confirmWindow.setIcon(FontAwesome.DOWNLOAD);
				confirmWindow.setContent(vLayout);

				enterButton.addClickListener(new Button.ClickListener() {
					private static final long serialVersionUID = 5325311693662832508L;

					public void buttonClick(ClickEvent event) {
						enterButton.setEnabled(false);
					}
				});

				cancelButton.addClickListener(new Button.ClickListener() {
					private static final long serialVersionUID = 5325311693662832508L;

					public void buttonClick(ClickEvent event) {
						confirmWindow.close();
					}
				});

				confirmWindow.addListener(new Window.CloseListener() {
					private static final long serialVersionUID = -631639912944994222L;

					public void windowClose(CloseEvent e) {
						FileAccess.deleteFile(reportFileName);
					}
				});

				UI.getCurrent().addWindow(confirmWindow);

			}
		};

		// ///////////////////////////////////////////////////////////////

		final Command printReportCommand = new Command() {
			private static final long serialVersionUID = -4168910857126708902L;

			@SuppressWarnings("deprecation")
			@Override
			public void menuSelected(final MenuItem selectedItem) {
				if (getSelectedMinister() == null) {
					Notification.show("„‰ ›÷·ﬂ «Œ — «·Œ«œ„", null, Type.TRAY_NOTIFICATION);
					return;
				} else if (goDate == null || goDate.length() == 0) {
					Notification.show("„‰ ›÷·ﬂ «Œ — «· «—ÌŒ", null, Type.TRAY_NOTIFICATION);
					return;
				}

				String reportFileName = OrgHandler.generateEftqadReportPDF(getSelectedMinister(), goDate);

				if (!Validator.validateString(reportFileName)) {
					Notification.show(EMapper.getEDesc(EMapper.ECODE_UNKNOWN_ERROR),
							EMapper.getNType(EMapper.ECODE_UNKNOWN_ERROR));
					return;
				}

				VerticalLayout vLayout = new VerticalLayout();
				vLayout.setMargin(true);

				final Label label = new Label();
				label.setCaption("≈÷€ÿ „Ê«›ﬁ ·ÿ»«⁄… «· ﬁ—Ì—");
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
				confirmWindow.setHeight("180");
				confirmWindow.setResizable(false);
				confirmWindow.setCaption(" ÿ»«⁄…");
				confirmWindow.setIcon(FontAwesome.PRINT);
				confirmWindow.setContent(vLayout);

				enterButton.addClickListener(new Button.ClickListener() {
					private static final long serialVersionUID = 5569230699947766441L;

					public void buttonClick(ClickEvent event) {
						try {
							int result = Printer.print(new String[] { reportFileName });

							if (result != EMapper.ECODE_SUCCESS) {
								Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));
							}

							enterButton.setEnabled(false);
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

				confirmWindow.addListener(new Window.CloseListener() {
					private static final long serialVersionUID = 8546870775234950520L;

					public void windowClose(CloseEvent e) {
						FileAccess.deleteFile(reportFileName);
					}
				});

				UI.getCurrent().addWindow(confirmWindow);

			}
		};

		// ///////////////////////////////////////////////////////////////

		final Command unassignMinisterCommand = new Command() {
			private static final long serialVersionUID = -4168910857126708902L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				if (getSelectedMinister() == null) {
					Notification.show("„‰ ›÷·ﬂ «Œ — «·Œ«œ„", null, Type.TRAY_NOTIFICATION);
					return;
				}

				VerticalLayout vLayout = new VerticalLayout();
				vLayout.setMargin(true);

				final Label label = new Label();
				label.setCaption("„‰ ›÷·ﬂ ﬁ„ » √ﬂÌœ «·√„—. Â·  —Ìœ ≈·€«¡  ⁄ÌÌ‰ ("
						+ container.getItem(table.getValue()).toString() + ")ø");
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
				confirmWindow.setHeight("180");
				confirmWindow.setResizable(false);
				confirmWindow.setCaption("  √ﬂÌœ «·√„—");
				confirmWindow.setIcon(FontAwesome.EXCLAMATION_CIRCLE);
				confirmWindow.setContent(vLayout);

				enterButton.addClickListener(new Button.ClickListener() {
					private static final long serialVersionUID = 5569230699947766441L;

					public void buttonClick(ClickEvent event) {
						try {
							HashMap<String, String> requestData = new HashMap<String, String>();
							requestData.put("ministerName", container.getItem(table.getValue()).toString());

							int result = RequestHandler.handleUpdateRequest("unassignMinister", requestData);

							if (result != EMapper.ECODE_SUCCESS) {
								Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));
							} else {
								refreshContainer(classOrg.getSelectedClass());
							}

							confirmWindow.close();

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

		// ///////////////////////////////////////////////////////////////

		final Command exchangeMinisterChildrenCommand = new Command() {
			private static final long serialVersionUID = -4168910857126708902L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				if (getSelectedMinister() == null) {
					Notification.show("„‰ ›÷·ﬂ «Œ — «·Œ«œ„", null, Type.TRAY_NOTIFICATION);
					return;
				}

				VerticalLayout vLayout = new VerticalLayout();
				vLayout.setMargin(true);

				final ComboBox allNamesComboBox = new ComboBox();
				allNamesComboBox.setCaption("≈Œ — «”„ «·Œ«œ„ «·√Œ—");
				allNamesComboBox.addStyleName(ValoTheme.COMBOBOX_ALIGN_RIGHT);
				allNamesComboBox.addStyleName(ValoTheme.COMBOBOX_SMALL);
				allNamesComboBox.setNewItemsAllowed(false);
				allNamesComboBox.setWidth("350px");
				vLayout.addComponent(allNamesComboBox);
				vLayout.setComponentAlignment(allNamesComboBox, Alignment.TOP_RIGHT);
				allNamesComboBox.addFocusListener(new FocusListener() {
					private static final long serialVersionUID = 8721337946386845992L;

					public void focus(FocusEvent event) {

						String currentSelectedItem = null;
						if (allNamesComboBox.getValue() != null) {
							currentSelectedItem = allNamesComboBox.getValue().toString();
						}

						allNamesComboBox.removeAllItems();
						ArrayList<String> allNames = CommonsHandler.getNamesByType("minister");
						for (int i = 0; i < allNames.size(); i++) {
							allNamesComboBox.addItem(allNames.get(i));
						}

						if (currentSelectedItem != null) {
							allNamesComboBox.addItem(currentSelectedItem);
							allNamesComboBox.setValue(currentSelectedItem);
						}
					}
				});

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

						HashMap<String, String> requestData = new HashMap<String, String>();
						requestData.put("ministerAName", container.getItem(table.getValue()).toString());
						requestData.put("ministerBName", String.valueOf(allNamesComboBox.getValue()));

						int result = RequestHandler.handleUpdateRequest("exchangeMinisterChildren",
								requestData);

						Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));

						childOrg.refreshContainer(getSelectedMinister());

						confirmWindow.close();
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

		// ///////////////////////////////////////////////////////////////

		final Command assignChildrenToAnotherMinisterCommand = new Command() {
			private static final long serialVersionUID = -4168910857126708902L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				if (getSelectedMinister() == null) {
					Notification.show("„‰ ›÷·ﬂ «Œ — «·Œ«œ„", null, Type.TRAY_NOTIFICATION);
					return;
				}

				VerticalLayout vLayout = new VerticalLayout();
				vLayout.setMargin(true);

				final ComboBox allNamesComboBox = new ComboBox();
				allNamesComboBox.setCaption("≈Œ — «”„ «·Œ«œ„ «·√Œ—");
				allNamesComboBox.addStyleName(ValoTheme.COMBOBOX_ALIGN_RIGHT);
				allNamesComboBox.addStyleName(ValoTheme.COMBOBOX_SMALL);
				allNamesComboBox.setNewItemsAllowed(false);
				allNamesComboBox.setWidth("350px");
				vLayout.addComponent(allNamesComboBox);
				vLayout.setComponentAlignment(allNamesComboBox, Alignment.TOP_RIGHT);
				allNamesComboBox.addFocusListener(new FocusListener() {
					private static final long serialVersionUID = 8721337946386845992L;

					public void focus(FocusEvent event) {

						String currentSelectedItem = null;
						if (allNamesComboBox.getValue() != null) {
							currentSelectedItem = allNamesComboBox.getValue().toString();
						}

						allNamesComboBox.removeAllItems();
						ArrayList<String> allNames = CommonsHandler.getNamesByType("minister");
						for (int i = 0; i < allNames.size(); i++) {
							allNamesComboBox.addItem(allNames.get(i));
						}

						if (currentSelectedItem != null) {
							allNamesComboBox.addItem(currentSelectedItem);
							allNamesComboBox.setValue(currentSelectedItem);
						}
					}
				});

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

						HashMap<String, String> requestData = new HashMap<String, String>();
						requestData.put("ministerAName", container.getItem(table.getValue()).toString());
						requestData.put("ministerBName", String.valueOf(allNamesComboBox.getValue()));

						int result = RequestHandler.handleUpdateRequest(
								"assignChildrenToAnotherMinisterCommand", requestData);

						Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));

						childOrg.refreshContainer(getSelectedMinister());

						confirmWindow.close();
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

		// /////////////////////////////COMMANDS//////////////////////////
		// ///////////////////////////////////////////////////////////////

		MenuItem settingItems = setting.addItem("", null);

		MenuItem assignChildMenuItem = settingItems.addItem("  ⁄ÌÌ‰ „ŒœÊ„", assignChildCommand);
		assignChildMenuItem.setIcon(FontAwesome.LINK);

		settingItems.addSeparator();

		MenuItem unassignMinisterMenuItem = settingItems.addItem(" ≈«€«¡  ⁄ÌÌ‰ «·Œ«œ„",
				unassignMinisterCommand);
		unassignMinisterMenuItem.setIcon(FontAwesome.UNLINK);

		settingItems.addSeparator();

		MenuItem assignChildrenToAnotherMinisterMenuItem = settingItems.addItem(
				"  ⁄ÌÌ‰ «·„ŒœÊ„Ì‰ ≈·Ï Œ«œ„ √Œ—", assignChildrenToAnotherMinisterCommand);
		assignChildrenToAnotherMinisterMenuItem.setIcon(FontAwesome.ARROW_CIRCLE_LEFT);

		MenuItem exchangeMinisterChildrenMenuItem = settingItems.addItem("  »œÌ· «·„ŒœÊ„Ì‰ „⁄ Œ«œ„ √Œ—",
				exchangeMinisterChildrenCommand);
		exchangeMinisterChildrenMenuItem.setIcon(FontAwesome.EXCHANGE);

		settingItems.addSeparator();

		MenuItem downloadReportMenuItem = settingItems.addItem("  ‰“Ì·  ﬁ—Ì—", downloadReportCommand);
		downloadReportMenuItem.setIcon(FontAwesome.DOWNLOAD);

		MenuItem printReportMenuItem = settingItems.addItem(" ÿ»«⁄…  ﬁ—Ì—", printReportCommand);
		printReportMenuItem.setIcon(FontAwesome.PRINT);

		return layout;
	}

	/*************************************************************/

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
