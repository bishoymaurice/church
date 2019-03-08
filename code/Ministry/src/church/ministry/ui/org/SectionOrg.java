package church.ministry.ui.org;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
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
import church.ministry.util.Zipper;

import com.vaadin.data.Item;
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
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.themes.ValoTheme;

public class SectionOrg implements Serializable {

	private static final long serialVersionUID = 1987558337785639078L;

	String header = "ﬁÿ«⁄";
	IndexedContainer container;
	Table table = new Table();
	FamilyOrg familyOrg;
	String goDate;

	public void setGoDate(String goDate) {
		this.goDate = goDate;
	}

	public void setFamilyOrg(FamilyOrg familyOrg) {
		this.familyOrg = familyOrg;
	}

	public FamilyOrg getFamilyOrg() {
		return this.familyOrg;
	}

	protected String getSelectedSection() {
		try {
			return container.getItem(table.getValue()).toString();
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private void refreshContainer() {
		container.removeAllItems();

		ArrayList<String> sections = OrgHandler.getAllSections();

		for (int i = 0; i < sections.size(); i++) {
			Object id = container.addItem();
			Item item = container.getItem(id);

			if (item != null) {
				item.getItemProperty(header).setValue(sections.get(i));
			}
		}

		this.familyOrg.refreshContainer(null);
	}

	public GridLayout getLayout() {
		GridLayout layout = new GridLayout(1, 2);
		layout.setHeight("300px");
		layout.setWidth("200px");

		MenuBar setting = new MenuBar();
		setting.addStyleName(ValoTheme.MENUBAR_SMALL);
		setting.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
		layout.addComponent(setting);

		table.setHeight("300px");
		table.setWidth("200px");
		table.setSelectable(true);
		table.setNullSelectionAllowed(false);
		table.setSortEnabled(false);
		table.addStyleName(ValoTheme.TABLE_SMALL);
		table.setColumnAlignment(header, Align.RIGHT);
		table.addValueChangeListener(e -> familyOrg.refreshContainer(container.getItem(table.getValue())
				.toString()));

		layout.addComponent(table);

		container = new IndexedContainer();
		container.addContainerProperty(header, String.class, header);
		table.setContainerDataSource(container);

		refreshContainer();

		// ///////////////////////////////////////////////////////////////
		// /////////////////////////////COMMANDS//////////////////////////
		final Command newSectionCommand = new Command() {
			private static final long serialVersionUID = -4168910857126708902L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				VerticalLayout vLayout = new VerticalLayout();
				vLayout.setMargin(true);

				TextField inputText = new TextField("«”„ «·ﬁÿ«⁄");
				inputText.setMaxLength(50);
				vLayout.addComponent(inputText);
				vLayout.setComponentAlignment(inputText, Alignment.TOP_RIGHT);

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
				confirmWindow.setCaption(" ≈œŒ«· ««·»Ì«‰« ");
				confirmWindow.setIcon(FontAwesome.INFO);
				confirmWindow.setContent(vLayout);

				enterButton.addClickListener(new Button.ClickListener() {
					private static final long serialVersionUID = 5325311693662832508L;

					public void buttonClick(ClickEvent event) {

						confirmWindow.close();

						HashMap<String, String> requestData = new HashMap<String, String>();
						requestData.put("name", inputText.getValue());
						int result = RequestHandler.handleUpdateRequest("newSection", requestData);
						Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));

						refreshContainer();
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
		final Command updateSectionCommand = new Command() {
			private static final long serialVersionUID = -4168910857126708902L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				if (getSelectedSection() == null) {
					Notification.show("„‰ ›÷·ﬂ «Œ — «·ﬁÿ«⁄", null, Type.TRAY_NOTIFICATION);
					return;
				}

				VerticalLayout vLayout = new VerticalLayout();
				vLayout.setMargin(true);

				String selectedValue = container.getItem(table.getValue()).toString();

				TextField inputText = new TextField("«·«”„ «·ÃœÌœ ··ﬁÿ«⁄");
				inputText.setMaxLength(50);
				inputText.setValue(selectedValue);
				vLayout.addComponent(inputText);
				vLayout.setComponentAlignment(inputText, Alignment.TOP_RIGHT);

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
				confirmWindow.setCaption(" ≈œŒ«· ««·»Ì«‰« ");
				confirmWindow.setIcon(FontAwesome.INFO);
				confirmWindow.setContent(vLayout);

				enterButton.addClickListener(new Button.ClickListener() {
					private static final long serialVersionUID = 5325311693662832508L;

					public void buttonClick(ClickEvent event) {

						confirmWindow.close();

						HashMap<String, String> requestData = new HashMap<String, String>();
						requestData.put("oldName", selectedValue);
						requestData.put("newName", inputText.getValue());

						int result = RequestHandler.handleUpdateRequest("updateSection", requestData);

						Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));

						refreshContainer();
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
		final Command deleteSectionCommand = new Command() {
			private static final long serialVersionUID = -4168910857126708902L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				if (getSelectedSection() == null) {
					Notification.show("„‰ ›÷·ﬂ «Œ — «·ﬁÿ«⁄", null, Type.TRAY_NOTIFICATION);
					return;
				}

				VerticalLayout vLayout = new VerticalLayout();
				vLayout.setMargin(true);

				Label label = new Label(
						"”Ê› Ì „ Õ–› «·»Ì«‰«   „«„« Ê ·‰ Ì” ÿ⁄ «·»—‰«„Ã ≈⁄«œ Â« „—… √Œ—Ï. „‰ ›÷·ﬂ ﬁ„ » √ﬂÌœ «·√„—.");
				vLayout.addComponent(label);

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
				confirmWindow.setCaption("  Õ–Ì—");
				confirmWindow.setIcon(FontAwesome.WARNING);
				confirmWindow.setContent(vLayout);

				enterButton.addClickListener(new Button.ClickListener() {
					private static final long serialVersionUID = 5325311693662832508L;

					public void buttonClick(ClickEvent event) {

						confirmWindow.close();

						HashMap<String, String> requestData = new HashMap<String, String>();
						requestData.put("name", container.getItem(table.getValue()).toString());

						int result = RequestHandler.handleUpdateRequest("deleteSection", requestData);

						Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));

						refreshContainer();
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
		final Command assignFamilyCommand = new Command() {
			private static final long serialVersionUID = -4168910857126708902L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				if (getSelectedSection() == null) {
					Notification.show("„‰ ›÷·ﬂ «Œ — «·ﬁÿ«⁄", null, Type.TRAY_NOTIFICATION);
					return;
				}

				VerticalLayout vLayout = new VerticalLayout();
				vLayout.setMargin(true);

				final ComboBox allNamesComboBox = new ComboBox();
				allNamesComboBox.setCaption("≈Œ — «”„ «·√”—…");
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
						ArrayList<String> allNames = CommonsHandler.getAllFamilies();
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

						HashMap<String, String> requestData = new HashMap<String, String>();
						requestData.put("sectionName", container.getItem(table.getValue()).toString());
						requestData.put("familyName", String.valueOf(allNamesComboBox.getValue()));

						int result = RequestHandler.handleUpdateRequest("assignFamily", requestData);

						Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));

						familyOrg.refreshContainer(getSelectedSection());
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
		}; // ///////////////////////////////////////////////////////////////
		final Command downloadReportsCommand = new Command() {
			private static final long serialVersionUID = 4739618741655145334L;

			@SuppressWarnings("deprecation")
			@Override
			public void menuSelected(final MenuItem selectedItem) {
				try {
					if (getSelectedSection() == null) {
						Notification.show("„‰ ›÷·ﬂ «Œ — «·›’·", null, Type.TRAY_NOTIFICATION);
						return;
					} else if (goDate == null || goDate.length() == 0) {
						Notification.show("„‰ ›÷·ﬂ «Œ — «· «—ÌŒ", null, Type.TRAY_NOTIFICATION);
						return;
					}

					String[] reportFilesPaths = OrgHandler.generateEftqadReportForSection(
							getSelectedSection(), goDate);

					if (reportFilesPaths == null) {
						Notification.show(EMapper.getEDesc(EMapper.ECODE_UNKNOWN_ERROR),
								EMapper.getNType(EMapper.ECODE_UNKNOWN_ERROR));
						return;
					}

					ArrayList<File> fileList = new ArrayList<File>();
					for (String filePath : reportFilesPaths) {
						fileList.add(new File(filePath));
					}

					String reportsZippedFileName = Zipper.zip(fileList, true);

					if (!Validator.validateString(reportsZippedFileName)) {
						Notification.show(EMapper.getEDesc(EMapper.ECODE_UNKNOWN_ERROR),
								EMapper.getNType(EMapper.ECODE_UNKNOWN_ERROR));
						return;
					}

					String downloadFileName = null;
					if (reportsZippedFileName.contains("/")) {
						downloadFileName = reportsZippedFileName.substring(
								reportsZippedFileName.lastIndexOf("/") + 1, reportsZippedFileName.length());
					} else {
						downloadFileName = reportsZippedFileName.substring(
								reportsZippedFileName.lastIndexOf("\\") + 1, reportsZippedFileName.length());
					}

					StreamResource myResource = createResource(reportsZippedFileName, downloadFileName);
					FileDownloader fileDownloader = new FileDownloader(myResource);

					VerticalLayout vLayout = new VerticalLayout();
					vLayout.setMargin(true);

					final Label label = new Label();
					label.setCaption("≈÷€ÿ „Ê«›ﬁ · ‰“Ì· «· ﬁ«—Ì—");
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
					confirmWindow.setHeight("200");
					confirmWindow.setResizable(false);
					confirmWindow.setCaption("  ‰“Ì·");
					confirmWindow.setIcon(FontAwesome.PRINT);
					confirmWindow.setContent(vLayout);

					enterButton.addClickListener(new Button.ClickListener() {
						private static final long serialVersionUID = 5569230699947766441L;

						public void buttonClick(ClickEvent event) {
							enterButton.setEnabled(false);
						}
					});

					cancelButton.addClickListener(new Button.ClickListener() {
						private static final long serialVersionUID = 5325311693662832508L;

						public void buttonClick(ClickEvent event) {
							FileAccess.deleteFile(reportsZippedFileName);
							for (String filePath : reportFilesPaths) {
								FileAccess.deleteFile(filePath);
							}
							confirmWindow.close();
						}
					});

					confirmWindow.addListener(new Window.CloseListener() {
						private static final long serialVersionUID = 8546870775234950520L;

						public void windowClose(CloseEvent e) {
							FileAccess.deleteFile(reportsZippedFileName);
							for (String filePath : reportFilesPaths) {
								FileAccess.deleteFile(filePath);
							}
						}
					});

					UI.getCurrent().addWindow(confirmWindow);
				} catch (Exception e) {
					Logger.exception(e);
				}
			}

		};
		// ///////////////////////////////////////////////////////////////
		final Command printReportCommand = new Command() {
			private static final long serialVersionUID = -4168910857126708902L;

			@SuppressWarnings("deprecation")
			@Override
			public void menuSelected(final MenuItem selectedItem) {
				if (getSelectedSection() == null) {
					Notification.show("„‰ ›÷·ﬂ «Œ — «·ﬁÿ«⁄", null, Type.TRAY_NOTIFICATION);
					return;
				} else if (goDate == null || goDate.length() == 0) {
					Notification.show("„‰ ›÷·ﬂ «Œ — «· «—ÌŒ", null, Type.TRAY_NOTIFICATION);
					return;
				}

				String[] reportFilesPaths = OrgHandler.generateEftqadReportForSection(getSelectedSection(),
						goDate);

				if (reportFilesPaths == null) {
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
				confirmWindow.setHeight("200");
				confirmWindow.setResizable(false);
				confirmWindow.setCaption(" ÿ»«⁄…");
				confirmWindow.setIcon(FontAwesome.PRINT);
				confirmWindow.setContent(vLayout);

				enterButton.addClickListener(new Button.ClickListener() {
					private static final long serialVersionUID = 5569230699947766441L;

					public void buttonClick(ClickEvent event) {
						try {
							int result = Printer.print(reportFilesPaths);

							if (result != EMapper.ECODE_SUCCESS) {
								Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));
								return;
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
						for (int i = 0; i < reportFilesPaths.length; i++) {
							FileAccess.deleteFile(reportFilesPaths[i]);
						}
					}
				});

				UI.getCurrent().addWindow(confirmWindow);

			}
		};
		// /////////////////////////////COMMANDS//////////////////////////
		// ///////////////////////////////////////////////////////////////

		MenuItem settingItems = setting.addItem("", null);

		MenuItem addMenuItem = settingItems.addItem(" ÃœÌœ", newSectionCommand);
		addMenuItem.setIcon(FontAwesome.PLUS_CIRCLE);

		MenuItem editMenuItem = settingItems.addItem("  ⁄œÌ·", updateSectionCommand);
		editMenuItem.setIcon(FontAwesome.EDIT);

		MenuItem deleteMenuItem = settingItems.addItem(" Õ–›", deleteSectionCommand);
		deleteMenuItem.setIcon(FontAwesome.TRASH_O);

		settingItems.addSeparator();

		MenuItem assignFamilyMenuItem = settingItems.addItem("  ⁄ÌÌ‰ √”—…", assignFamilyCommand);
		assignFamilyMenuItem.setIcon(FontAwesome.LINK);

		settingItems.addSeparator();

		MenuItem downloadReportsMenuItem = settingItems.addItem("  ‰“Ì·  ﬁ«—Ì—", downloadReportsCommand);
		downloadReportsMenuItem.setIcon(FontAwesome.DOWNLOAD);

		MenuItem printReportMenuItem = settingItems.addItem(" ÿ»«⁄…  ﬁ«—Ì—", printReportCommand);
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
