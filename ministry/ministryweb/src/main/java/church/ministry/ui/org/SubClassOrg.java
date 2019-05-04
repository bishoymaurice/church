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
import church.ministry.control.conf.Constants;
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
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
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

public class SubClassOrg implements Serializable {

	private static final long serialVersionUID = -8173724753347295406L;

	String header = "„Ã„Ê⁄…";
	IndexedContainer container;
	Table table = new Table();
	ClassOrg classOrg;
	ChildOrg childOrg;
	MinisterOrg ministerOrg;
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

	public MinisterOrg getMinisterOrg() {
		return ministerOrg;
	}

	public void setMinisterOrg(MinisterOrg ministerOrg) {
		this.ministerOrg = ministerOrg;
	}

	protected String getSelectedSubClass() {
		try {
			return container.getItem(table.getValue()).toString();
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	protected void refreshContainer(String className) {
		container.removeAllItems();

		if (className != null) {
			ArrayList<String> ministers = OrgHandler.getSubClassByClass(className);

			for (int i = 0; i < ministers.size(); i++) {
				Object id = container.addItem();
				Item item = container.getItem(id);

				if (item != null) {
					item.getItemProperty(header).setValue(ministers.get(i));
				}
			}
		}

		this.childOrg.refreshContainer(null);
		this.ministerOrg.refreshContainer(null);
	}

	public GridLayout getLayout() {
		GridLayout layout = new GridLayout(1, 3);
		layout.setHeight("230px");
		layout.setWidth("200px");

		MenuBar setting = new MenuBar();
		setting.addStyleName(ValoTheme.MENUBAR_SMALL);
		setting.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
		layout.addComponent(setting);

		table.setHeight("230px");
		table.setWidth("200px");
		table.setSelectable(true);
		table.setNullSelectionAllowed(false);
		table.setSortEnabled(false);
		table.addStyleName(ValoTheme.TABLE_SMALL);
		table.setColumnAlignment(header, Align.RIGHT);
		table.addValueChangeListener(e -> ministerOrg.refreshContainer(container.getItem(table.getValue())
				.toString()));
		layout.addComponent(table);

		layout.addComponent(ministerOrg.getLayout());

		container = new IndexedContainer();
		container.addContainerProperty(header, String.class, header);
		table.setContainerDataSource(container);

		// ///////////////////////////////////////////////////////////////
		// /////////////////////////////COMMANDS//////////////////////////
		final Command newSubClassCommand = new Command() {
			private static final long serialVersionUID = -4168910857126708902L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				if (classOrg.getSelectedClass() == null) {
					Notification.show("„‰ ›÷·ﬂ «Œ — «·›’·", null, Type.TRAY_NOTIFICATION);
					return;
				}

				VerticalLayout vLayout = new VerticalLayout();
				vLayout.setMargin(true);

				TextField inputText = new TextField("«”„ «·„Ã„Ê⁄…");
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
						requestData.put("className", classOrg.getSelectedClass());

						int result = RequestHandler.handleUpdateRequest("newSubClass", requestData);

						Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));

						refreshContainer(classOrg.getSelectedClass());
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
		final Command updateSubClassCommand = new Command() {
			private static final long serialVersionUID = -4168910857126708902L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				if (getSelectedSubClass() == null) {
					Notification.show("„‰ ›÷·ﬂ «Œ — «·„Ã„Ê⁄…", null, Type.TRAY_NOTIFICATION);
					return;
				}

				VerticalLayout vLayout = new VerticalLayout();
				vLayout.setMargin(true);

				String selectedValue = container.getItem(table.getValue()).toString();

				TextField inputText = new TextField("«·«”„ «·ÃœÌœ ··„Ã„Ê⁄…");
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

						int result = RequestHandler.handleUpdateRequest("updateSubClass", requestData);

						Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));

						refreshContainer(classOrg.getSelectedClass());
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
		final Command deleteSubClassCommand = new Command() {
			private static final long serialVersionUID = -4168910857126708902L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				if (getSelectedSubClass() == null) {
					Notification.show("„‰ ›÷·ﬂ «Œ — «·Ã„Ê⁄…", null, Type.TRAY_NOTIFICATION);
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

						int result = RequestHandler.handleUpdateRequest("deleteSubClass", requestData);

						Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));

						refreshContainer(classOrg.getSelectedClass());
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

		final Command assignChildCommand = new Command() {
			private static final long serialVersionUID = -4168910857126708902L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				if (getSelectedSubClass() == null) {
					Notification.show("„‰ ›÷·ﬂ «Œ — «·„Ã„Ê⁄…", null, Type.TRAY_NOTIFICATION);
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
						requestData.put("subClassName", container.getItem(table.getValue()).toString());
						requestData.put("childName", String.valueOf(childrenTextArea.getValue()));

						int result = RequestHandler.handleUpdateRequest("assignChild", requestData);

						Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));

						childOrg.refreshContainer(getSelectedSubClass());
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

		final Command changeSubClassMinisterCommand = new Command() {
			private static final long serialVersionUID = -4168910857126708902L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				if (getSelectedSubClass() == null) {
					Notification.show("„‰ ›÷·ﬂ «Œ — «·„Ã„Ê⁄…", null, Type.TRAY_NOTIFICATION);
					return;
				}

				VerticalLayout vLayout = new VerticalLayout();
				vLayout.setMargin(true);

				Label warningLabel = new Label("<br><br><br><br><br>", ContentMode.HTML);

				final ComboBox allNamesComboBox = new ComboBox();
				allNamesComboBox.setCaption("≈Œ — «”„ «·Œ«œ„");
				allNamesComboBox.addStyleName(ValoTheme.COMBOBOX_ALIGN_RIGHT);
				allNamesComboBox.addStyleName(ValoTheme.COMBOBOX_SMALL);
				allNamesComboBox.setNewItemsAllowed(false);
				allNamesComboBox.setWidth("350px");
				allNamesComboBox
						.addValueChangeListener(e -> {
							if (e != null && e.getProperty() != null && e.getProperty().getValue() != null) {
								String newMinisterCurrentSubClassName = OrgHandler
										.getSubClassByMinister(String.valueOf(e.getProperty().getValue()));

								if (Validator.validateString(newMinisterCurrentSubClassName)) {

									StringBuilder sb = new StringBuilder();
									sb.append("„·«ÕŸ… Â«„…:");
									sb.append("<br>");
									sb.append("Â–« «·Œ«œ„ „⁄Ì‰ »«·›⁄· ›Ì „Ã„Ê⁄… √Œ—Ï Ê ÂÌ: ");
									sb.append(newMinisterCurrentSubClassName);
									sb.append("<br>");
									sb.append("≈–« ﬁ„  » √ﬂÌœ «·√„— ”Ê› Ì „ ⁄“·Â „‰ Â–Â «·„Ã„Ê⁄… Ê  ⁄ÌÌ‰Â ··„Ã„Ê⁄… «·ÃœÌœ… «·„—«œ ‰ﬁ·Â«");
									sb.append("<br>");
									sb.append("≈‰ ·„  ﬂ‰ „ √ﬂœ« ≈Œ — \"≈·€«¡\"");
									sb.append("<br>");
									warningLabel.setValue(sb.toString());
								} else {
									warningLabel.setValue("<br><br><br><br><br>");
								}
							} else {
								warningLabel.setValue("<br><br><br><br><br>");
							}
						});
				allNamesComboBox.addFocusListener(new FocusListener() {
					private static final long serialVersionUID = 8721337946386845992L;

					public void focus(FocusEvent event) {

						String currentSelectedItem = null;
						if (allNamesComboBox.getValue() != null) {
							currentSelectedItem = allNamesComboBox.getValue().toString();
						}

						allNamesComboBox.removeAllItems();
						ArrayList<String> allNames = CommonsHandler
								.getAllMinistersNamesExcludingSubClass(container.getItem(table.getValue())
										.toString());
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

				vLayout.addComponent(warningLabel);

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
				confirmWindow.setHeight("330");
				confirmWindow.setResizable(false);
				confirmWindow.setCaption(" ≈œŒ«· ««·»Ì«‰« ");
				confirmWindow.setIcon(FontAwesome.INFO);
				confirmWindow.setContent(vLayout);

				enterButton.addClickListener(new Button.ClickListener() {
					private static final long serialVersionUID = 5325311693662832508L;

					public void buttonClick(ClickEvent event) {

						confirmWindow.close();

						HashMap<String, String> requestData = new HashMap<String, String>();
						requestData.put("subClassName", container.getItem(table.getValue()).toString());
						requestData.put("ministerName", String.valueOf(allNamesComboBox.getValue()));

						int result = RequestHandler
								.handleUpdateRequest("changeSubClassMinister", requestData);

						Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));

						ministerOrg.refreshContainer(getSelectedSubClass());
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

		final Command moveSubClassToAnotherClassCommand = new Command() {
			private static final long serialVersionUID = -4168910857126708902L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				if (getSelectedSubClass() == null) {
					Notification.show("„‰ ›÷·ﬂ «Œ — «·„Ã„Ê⁄…", null, Type.TRAY_NOTIFICATION);
					return;
				}

				VerticalLayout vLayout = new VerticalLayout();
				vLayout.setMargin(true);

				final ComboBox allNamesComboBox = new ComboBox();
				allNamesComboBox.setCaption("≈Œ — «”„ «·›’·");
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
								.getAllClassesExcludingSomeClassOfSubClass(container
										.getItem(table.getValue()).toString());
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

						String className = String.valueOf(allNamesComboBox.getValue());

						if (Validator.validateString(className)) {
							VerticalLayout vLayout1 = new VerticalLayout();
							vLayout1.setMargin(true);

							StringBuilder stringBuilder = new StringBuilder();
							stringBuilder.append("”Ê› Ì „ ‰ﬁ· „Ã„Ê⁄…: ");
							stringBuilder.append(getSelectedSubClass());
							stringBuilder.append("<br>");
							stringBuilder.append("≈·Ï ›’·: ");
							stringBuilder.append(className);
							stringBuilder.append("<br>");
							stringBuilder.append("„‰ ›÷·ﬂ ≈Œ — «Õœ «·«Œ Ì«—«  «· «·Ì…");
							stringBuilder.append("<br>");
							stringBuilder
									.append("„⁄ „·«ÕŸ… ≈‰ √Ì  €ÌÌ— ”Ê› ÌƒÀ— ⁄·Ï  ﬁÌÌ„ «·Œœ«„ ›Ì «·„” ﬁ»·");
							stringBuilder.append("<br>");

							Label label = new Label(stringBuilder.toString(), ContentMode.HTML);
							vLayout1.addComponent(label);

							vLayout1.addComponent(CommonsUI.getSpaceLabel("50px", null, null));

							HorizontalLayout hLayout1 = new HorizontalLayout();
							vLayout1.addComponent(hLayout1);
							vLayout1.setComponentAlignment(hLayout1, Alignment.MIDDLE_CENTER);

							Button withSameMinisterButton = new Button("≈»ﬁ«¡ ‰›” «·Œ«œ„");
							hLayout1.addComponent(withSameMinisterButton);

							hLayout1.addComponent(CommonsUI.getSpaceLabel(null, "25px", null));

							Button withMinisterRemovedButton = new Button("Õ–› «·Œ«œ„ „‰ «·„Ã„Ê⁄…");
							hLayout1.addComponent(withMinisterRemovedButton);

							hLayout1.addComponent(CommonsUI.getSpaceLabel(null, "25px", null));

							Button withNewMinisterButton = new Button(" ⁄ÌÌ‰ Œ«œ„ ÃœÌœ ··„Ã„Ê⁄…");
							hLayout1.addComponent(withNewMinisterButton);

							hLayout1.addComponent(CommonsUI.getSpaceLabel(null, "25px", null));

							Button cancelButton = new Button("≈·€«¡ «·«„—");
							hLayout1.addComponent(cancelButton);

							Window confirmWindow1 = new Window();
							confirmWindow1.center();
							confirmWindow1.setModal(true);
							confirmWindow1.setWidth("800");
							confirmWindow1.setHeight("250");
							confirmWindow1.setResizable(false);
							confirmWindow1.setCaption("  √ﬂÌœ «·«„—");
							confirmWindow1.setIcon(FontAwesome.EXCLAMATION_CIRCLE);
							confirmWindow1.setContent(vLayout1);

							withSameMinisterButton.addClickListener(new Button.ClickListener() {
								private static final long serialVersionUID = 5325311693662832508L;

								public void buttonClick(ClickEvent event) {

									confirmWindow1.close();

									String className = String.valueOf(allNamesComboBox.getValue());

									if (Validator.validateString(className)) {

										HashMap<String, String> requestData = new HashMap<String, String>();
										requestData.put("subClassName", container.getItem(table.getValue())
												.toString());
										requestData.put("className", className);
										requestData.put(Constants.MINISTER_ASSIGNMENT_OPTION,
												Constants.MINISTER_ASSIGNMENT_OPTION_S);

										int result = RequestHandler.handleUpdateRequest("assignSubClass",
												requestData);

										Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));

										refreshContainer(classOrg.getSelectedClass());
									} else {
										Notification.show(EMapper.getEDesc(EMapper.ECODE_INVALID_INPUT_DATA),
												EMapper.getNType(EMapper.ECODE_INVALID_INPUT_DATA));
									}
								}
							});

							withMinisterRemovedButton.addClickListener(new Button.ClickListener() {
								private static final long serialVersionUID = 5325311693662832508L;

								public void buttonClick(ClickEvent event) {
									confirmWindow1.close();

									String subClassName = String.valueOf(allNamesComboBox.getValue());

									if (Validator.validateString(subClassName)) {

										HashMap<String, String> requestData = new HashMap<String, String>();
										requestData.put("subClassName", container.getItem(table.getValue())
												.toString());
										requestData.put("className", className);
										requestData.put(Constants.MINISTER_ASSIGNMENT_OPTION,
												Constants.MINISTER_ASSIGNMENT_OPTION_R);

										int result = RequestHandler.handleUpdateRequest("assignSubClass",
												requestData);

										Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));

										refreshContainer(classOrg.getSelectedClass());
									} else {
										Notification.show(EMapper.getEDesc(EMapper.ECODE_INVALID_INPUT_DATA),
												EMapper.getNType(EMapper.ECODE_INVALID_INPUT_DATA));
									}
								}
							});

							withNewMinisterButton.addClickListener(new Button.ClickListener() {
								private static final long serialVersionUID = 5325311693662832508L;

								public void buttonClick(ClickEvent event) {
									confirmWindow1.close();

									String subClassName = String.valueOf(allNamesComboBox.getValue());

									if (Validator.validateString(subClassName)) {

										// //////////////////////////////////////////

										VerticalLayout vLayout2 = new VerticalLayout();
										vLayout2.setMargin(true);

										Label warningLabel = new Label("<br><br><br><br><br>",
												ContentMode.HTML);

										final ComboBox ministersNamesComboBox2 = new ComboBox();
										ministersNamesComboBox2.setCaption("≈Œ — «”„ «·Œ«œ„");
										ministersNamesComboBox2.addStyleName(ValoTheme.COMBOBOX_ALIGN_RIGHT);
										ministersNamesComboBox2.addStyleName(ValoTheme.COMBOBOX_SMALL);
										ministersNamesComboBox2.setNewItemsAllowed(false);
										ministersNamesComboBox2.setWidth("350px");
										ministersNamesComboBox2.addValueChangeListener(e -> {
											if (e != null && e.getProperty() != null
													&& e.getProperty().getValue() != null) {
												String newMinisterCurrentSubClassName = OrgHandler
														.getSubClassByMinister(String.valueOf(e.getProperty()
																.getValue()));

												if (Validator.validateString(newMinisterCurrentSubClassName)) {

													StringBuilder sb = new StringBuilder();
													sb.append("„·«ÕŸ… Â«„…:");
													sb.append("<br>");
													sb.append("Â–« «·Œ«œ„ „⁄Ì‰ »«·›⁄· ›Ì „Ã„Ê⁄… √Œ—Ï Ê ÂÌ: ");
													sb.append(newMinisterCurrentSubClassName);
													sb.append("<br>");
													sb.append("≈–« ﬁ„  » √ﬂÌœ «·√„— ”Ê› Ì „ ⁄“·Â „‰ Â–Â «·„Ã„Ê⁄… Ê  ⁄ÌÌ‰Â ··„Ã„Ê⁄… «·ÃœÌœ… «·„—«œ ‰ﬁ·Â«");
													sb.append("<br>");
													sb.append("≈‰ ·„  ﬂ‰ „ √ﬂœ« ≈Œ — \"≈·€«¡\"");
													sb.append("<br>");
													warningLabel.setValue(sb.toString());
												} else {
													warningLabel.setValue("<br><br><br><br><br>");
												}
											} else {
												warningLabel.setValue("<br><br><br><br><br>");
											}
										});
										ministersNamesComboBox2.addFocusListener(new FocusListener() {
											private static final long serialVersionUID = 8721337946386845992L;

											public void focus(FocusEvent event) {

												String currentSelectedItem = null;
												if (ministersNamesComboBox2.getValue() != null) {
													currentSelectedItem = ministersNamesComboBox2.getValue()
															.toString();
												}

												ministersNamesComboBox2.removeAllItems();
												ArrayList<String> allNames = CommonsHandler
														.getAllMinistersNamesExcludingSubClass(subClassName);

												for (int i = 0; i < allNames.size(); i++) {
													ministersNamesComboBox2.addItem(allNames.get(i));
												}

												if (currentSelectedItem != null) {
													ministersNamesComboBox2.addItem(currentSelectedItem);
													ministersNamesComboBox2.setValue(currentSelectedItem);
												}
											}
										});
										vLayout2.addComponent(ministersNamesComboBox2);
										vLayout2.setComponentAlignment(ministersNamesComboBox2,
												Alignment.TOP_RIGHT);

										vLayout2.addComponent(CommonsUI.getSpaceLabel("25px", null, null));

										vLayout2.addComponent(warningLabel);

										vLayout2.addComponent(CommonsUI.getSpaceLabel("25px", null, null));

										HorizontalLayout hLayout2 = new HorizontalLayout();
										vLayout2.addComponent(hLayout2);
										vLayout2.setComponentAlignment(hLayout2, Alignment.BOTTOM_LEFT);

										Button enterButton2 = new Button("„Ê«›ﬁ");
										hLayout2.addComponent(enterButton2);

										hLayout2.addComponent(CommonsUI.getSpaceLabel(null, "10px", null));

										Button cancelButton2 = new Button("≈·€«¡");
										hLayout2.addComponent(cancelButton2);

										Window confirmWindow2 = new Window();
										confirmWindow2.center();
										confirmWindow2.setModal(true);
										confirmWindow2.setWidth("480");
										confirmWindow2.setHeight("330");
										confirmWindow2.setResizable(false);
										confirmWindow2.setCaption(" ≈œŒ«· ««·»Ì«‰« ");
										confirmWindow2.setIcon(FontAwesome.INFO);
										confirmWindow2.setContent(vLayout2);

										enterButton2.addClickListener(new Button.ClickListener() {
											private static final long serialVersionUID = 5325311693662832508L;

											public void buttonClick(ClickEvent event) {

												confirmWindow2.close();

												HashMap<String, String> requestData = new HashMap<String, String>();
												requestData.put("subClassName",
														container.getItem(table.getValue()).toString());
												requestData.put("className", className);
												requestData.put("newMinisterName",
														String.valueOf(ministersNamesComboBox2.getValue()));
												requestData.put(Constants.MINISTER_ASSIGNMENT_OPTION,
														Constants.MINISTER_ASSIGNMENT_OPTION_N);

												int result = RequestHandler.handleUpdateRequest(
														"assignSubClass", requestData);

												Notification.show(EMapper.getEDesc(result),
														EMapper.getNType(result));

												refreshContainer(classOrg.getSelectedClass());
											}
										});

										cancelButton2.addClickListener(new Button.ClickListener() {
											private static final long serialVersionUID = 5325311693662832508L;

											public void buttonClick(ClickEvent event) {
												confirmWindow2.close();
											}
										});

										UI.getCurrent().addWindow(confirmWindow2);
										// //////////////////////////////////////////

									} else {
										Notification.show(EMapper.getEDesc(EMapper.ECODE_INVALID_INPUT_DATA),
												EMapper.getNType(EMapper.ECODE_INVALID_INPUT_DATA));
									}
								}
							});

							cancelButton.addClickListener(new Button.ClickListener() {
								private static final long serialVersionUID = 5325311693662832508L;

								public void buttonClick(ClickEvent event) {
									confirmWindow1.close();
								}
							});

							UI.getCurrent().addWindow(confirmWindow1);
							// /////////////////////////////////////////////////////////////////////////

						} else {
							Notification.show(EMapper.getEDesc(EMapper.ECODE_INVALID_INPUT_DATA),
									EMapper.getNType(EMapper.ECODE_INVALID_INPUT_DATA));
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

		final Command downloadReportsCommand = new Command() {
			private static final long serialVersionUID = 4739618741655145334L;

			@SuppressWarnings("deprecation")
			@Override
			public void menuSelected(final MenuItem selectedItem) {
				try {
					if (getSelectedSubClass() == null) {
						Notification.show("„‰ ›÷·ﬂ «Œ — «·„Ã„Ê⁄…", null, Type.TRAY_NOTIFICATION);
						return;
					} else if (goDate == null || goDate.length() == 0) {
						Notification.show("„‰ ›÷·ﬂ «Œ — «· «—ÌŒ", null, Type.TRAY_NOTIFICATION);
						return;
					} else if (OrgHandler.getMinisterBySubClass(getSelectedSubClass()) == null) {
						Notification.show("„‰ ›÷·ﬂ ﬁ„ » ⁄ÌÌ‰ Œ«œ„ «·„Ã„Ê⁄… √Ê·«", null,
								Type.TRAY_NOTIFICATION);
						return;
					}

					String reportFilePath = OrgHandler.generateEftqadReportForSubClass(getSelectedSubClass(),
							goDate);

					if (reportFilePath == null) {
						Notification.show(EMapper.getEDesc(EMapper.ECODE_UNKNOWN_ERROR),
								EMapper.getNType(EMapper.ECODE_UNKNOWN_ERROR));
						return;
					}

					ArrayList<File> fileList = new ArrayList<File>();
					fileList.add(new File(reportFilePath));

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
							FileAccess.deleteFile(reportFilePath);
							confirmWindow.close();
						}
					});

					confirmWindow.addListener(new Window.CloseListener() {
						private static final long serialVersionUID = 8546870775234950520L;

						public void windowClose(CloseEvent e) {
							FileAccess.deleteFile(reportsZippedFileName);
							FileAccess.deleteFile(reportFilePath);
						}
					});

					UI.getCurrent().addWindow(confirmWindow);
				} catch (Exception e) {
					Logger.exception(e);
				}
			}

		};
		// ///////////////////////////////////////////////////////////////
		final Command printReportsCommand = new Command() {
			private static final long serialVersionUID = -4168910857126708902L;

			@SuppressWarnings("deprecation")
			@Override
			public void menuSelected(final MenuItem selectedItem) {
				if (getSelectedSubClass() == null) {
					Notification.show("„‰ ›÷·ﬂ «Œ — «·„Ã„Ê⁄…", null, Type.TRAY_NOTIFICATION);
					return;
				} else if (goDate == null || goDate.length() == 0) {
					Notification.show("„‰ ›÷·ﬂ «Œ — «· «—ÌŒ", null, Type.TRAY_NOTIFICATION);
					return;
				} else if (OrgHandler.getMinisterBySubClass(getSelectedSubClass()) == null) {
					Notification.show("„‰ ›÷·ﬂ ﬁ„ » ⁄ÌÌ‰ Œ«œ„ «·„Ã„Ê⁄… √Ê·«", null, Type.TRAY_NOTIFICATION);
					return;
				}

				String reportFilePath = OrgHandler.generateEftqadReportForSubClass(getSelectedSubClass(),
						goDate);

				if (reportFilePath == null) {
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
							int result = Printer.print(new String[] { reportFilePath });

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
						FileAccess.deleteFile(reportFilePath);
					}
				});

				UI.getCurrent().addWindow(confirmWindow);

			}
		};

		// /////////////////////////////COMMANDS//////////////////////////
		// ///////////////////////////////////////////////////////////////

		MenuItem settingItems = setting.addItem("", null);

		MenuItem addMenuItem = settingItems.addItem(" ÃœÌœ", newSubClassCommand);
		addMenuItem.setIcon(FontAwesome.PLUS_CIRCLE);

		MenuItem editMenuItem = settingItems.addItem("  ⁄œÌ·", updateSubClassCommand);
		editMenuItem.setIcon(FontAwesome.EDIT);

		MenuItem deleteMenuItem = settingItems.addItem(" Õ–›", deleteSubClassCommand);
		deleteMenuItem.setIcon(FontAwesome.TRASH_O);

		settingItems.addSeparator();

		MenuItem assignChildMenuItem = settingItems.addItem("  ⁄ÌÌ‰ „ŒœÊ„", assignChildCommand);
		assignChildMenuItem.setIcon(FontAwesome.LINK);

		settingItems.addSeparator();

		MenuItem changeSubClassMinister = settingItems.addItem("  €ÌÌ— Œ«œ„ «·„Ã„Ê⁄…",
				changeSubClassMinisterCommand);
		changeSubClassMinister.setIcon(FontAwesome.ARROW_CIRCLE_LEFT);

		settingItems.addSeparator();

		MenuItem moveSubClassToAnotherClass = settingItems.addItem(" ‰ﬁ· «·„Ã„Ê⁄… ≈·Ï ›’· ¬Œ—",
				moveSubClassToAnotherClassCommand);
		moveSubClassToAnotherClass.setIcon(FontAwesome.ARROW_CIRCLE_LEFT);

		settingItems.addSeparator();

		MenuItem downloadReportsMenuItem = settingItems.addItem("  ‰“Ì·  ﬁ—Ì—", downloadReportsCommand);
		downloadReportsMenuItem.setIcon(FontAwesome.DOWNLOAD);

		MenuItem printReportMenuItem = settingItems.addItem(" ÿ»«⁄…  ﬁ—Ì—", printReportsCommand);
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
