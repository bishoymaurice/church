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

public class ClassOrg implements Serializable {

	private static final long serialVersionUID = -2749158390093453983L;

	String header = "›’·";
	IndexedContainer container;
	Table table = new Table();
	YearOrg yearOrg;
	SubClassOrg subClassOrg;
	String goDate;

	public void setGoDate(String goDate) {
		this.goDate = goDate;
	}

	public void setYearOrg(YearOrg yearOrg) {
		this.yearOrg = yearOrg;
	}

	public YearOrg getYearOrg() {
		return this.yearOrg;
	}

	public SubClassOrg getGroupOrg() {
		return subClassOrg;
	}

	public void setGroupOrg(SubClassOrg subClassOrg) {
		this.subClassOrg = subClassOrg;
	}

	protected String getSelectedClass() {
		try {
			return container.getItem(table.getValue()).toString();
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	protected void refreshContainer(String yearName) {
		container.removeAllItems();

		if (yearName != null) {
			ArrayList<String> classes = OrgHandler.getClassesByYear(yearName);

			for (int i = 0; i < classes.size(); i++) {
				Object id = container.addItem();
				Item item = container.getItem(id);

				if (item != null) {
					item.getItemProperty(header).setValue(classes.get(i));
				}
			}
		}

		this.subClassOrg.refreshContainer(null);
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
		table.addValueChangeListener(e -> this.subClassOrg.refreshContainer(container.getItem(
				table.getValue()).toString()));
		layout.addComponent(table);

		container = new IndexedContainer();
		container.addContainerProperty(header, String.class, header);
		table.setContainerDataSource(container);

		// ///////////////////////////////////////////////////////////////
		// /////////////////////////////COMMANDS//////////////////////////
		final Command newClassCommand = new Command() {
			private static final long serialVersionUID = -4168910857126708902L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				if (yearOrg.getSelectedYear() == null) {
					Notification.show("„‰ ›÷·ﬂ «Œ — «·’›", null, Type.TRAY_NOTIFICATION);
					return;
				}

				VerticalLayout vLayout = new VerticalLayout();
				vLayout.setMargin(true);

				TextField inputText = new TextField("«”„ «·›’·");
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
						requestData.put("yearName", yearOrg.getSelectedYear());

						int result = RequestHandler.handleUpdateRequest("newClass", requestData);

						Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));

						refreshContainer(yearOrg.getSelectedYear());
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
		final Command updateClassCommand = new Command() {
			private static final long serialVersionUID = -4168910857126708902L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				if (getSelectedClass() == null) {
					Notification.show("„‰ ›÷·ﬂ «Œ — «·›’·", null, Type.TRAY_NOTIFICATION);
					return;
				}

				VerticalLayout vLayout = new VerticalLayout();
				vLayout.setMargin(true);

				String selectedValue = container.getItem(table.getValue()).toString();

				TextField inputText = new TextField("«·«”„ «·ÃœÌœ ··›’·");
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

						int result = RequestHandler.handleUpdateRequest("updateClass", requestData);

						Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));

						refreshContainer(yearOrg.getSelectedYear());
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
		final Command deleteClassCommand = new Command() {
			private static final long serialVersionUID = -4168910857126708902L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				if (getSelectedClass() == null) {
					Notification.show("„‰ ›÷·ﬂ «Œ — «·›’·", null, Type.TRAY_NOTIFICATION);
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

						int result = RequestHandler.handleUpdateRequest("deleteClass", requestData);

						Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));

						refreshContainer(yearOrg.getSelectedYear());
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
		final Command assignSubClassCommand = new Command() {
			private static final long serialVersionUID = -4168910857126708902L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				if (getSelectedClass() == null) {
					Notification.show("„‰ ›÷·ﬂ «Œ — «·›’·", null, Type.TRAY_NOTIFICATION);
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
								.getAllSubClassesExcludingSomeClass(container.getItem(table.getValue())
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

						String subClassName = String.valueOf(allNamesComboBox.getValue());

						if (Validator.validateString(subClassName)) {

							String subClassMinister = OrgHandler.getSubClassMinister(subClassName);

							if (Validator.validateString(subClassMinister)) {

								// /////////////////////////////////////////////////////////////////////////
								VerticalLayout vLayout1 = new VerticalLayout();
								vLayout1.setMargin(true);

								StringBuilder stringBuilder = new StringBuilder();
								stringBuilder.append("Â–Â «·„Ã„Ê⁄…: ");
								stringBuilder.append(subClassName);
								stringBuilder.append("<br>");
								stringBuilder.append("„”ƒ·… «·«‰ „‰ «·Œ«œ„: ");
								stringBuilder.append(subClassMinister);
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

										String subClassName = String.valueOf(allNamesComboBox.getValue());

										if (Validator.validateString(subClassName)) {

											HashMap<String, String> requestData = new HashMap<String, String>();
											requestData.put("className", container.getItem(table.getValue())
													.toString());
											requestData.put("subClassName", subClassName);
											requestData.put(Constants.MINISTER_ASSIGNMENT_OPTION,
													Constants.MINISTER_ASSIGNMENT_OPTION_S);

											int result = RequestHandler.handleUpdateRequest("assignSubClass",
													requestData);

											Notification.show(EMapper.getEDesc(result),
													EMapper.getNType(result));

											subClassOrg.refreshContainer(getSelectedClass());
										} else {
											Notification.show(
													EMapper.getEDesc(EMapper.ECODE_INVALID_INPUT_DATA),
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
											requestData.put("className", container.getItem(table.getValue())
													.toString());
											requestData.put("subClassName", subClassName);
											requestData.put(Constants.MINISTER_ASSIGNMENT_OPTION,
													Constants.MINISTER_ASSIGNMENT_OPTION_R);

											int result = RequestHandler.handleUpdateRequest("assignSubClass",
													requestData);

											Notification.show(EMapper.getEDesc(result),
													EMapper.getNType(result));

											subClassOrg.refreshContainer(getSelectedClass());
										} else {
											Notification.show(
													EMapper.getEDesc(EMapper.ECODE_INVALID_INPUT_DATA),
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
											ministersNamesComboBox2
													.addStyleName(ValoTheme.COMBOBOX_ALIGN_RIGHT);
											ministersNamesComboBox2.addStyleName(ValoTheme.COMBOBOX_SMALL);
											ministersNamesComboBox2.setNewItemsAllowed(false);
											ministersNamesComboBox2.setWidth("350px");
											ministersNamesComboBox2.addValueChangeListener(e -> {
												if (e != null && e.getProperty() != null
														&& e.getProperty().getValue() != null) {
													String newMinisterCurrentSubClassName = OrgHandler
															.getSubClassByMinister(String.valueOf(e
																	.getProperty().getValue()));

													if (Validator
															.validateString(newMinisterCurrentSubClassName)) {

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
														currentSelectedItem = ministersNamesComboBox2
																.getValue().toString();
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
													requestData.put("className",
															container.getItem(table.getValue()).toString());
													requestData.put("subClassName", subClassName);
													requestData.put("newMinisterName", String
															.valueOf(ministersNamesComboBox2.getValue()));
													requestData.put(Constants.MINISTER_ASSIGNMENT_OPTION,
															Constants.MINISTER_ASSIGNMENT_OPTION_N);

													int result = RequestHandler.handleUpdateRequest(
															"assignSubClass", requestData);

													Notification.show(EMapper.getEDesc(result),
															EMapper.getNType(result));

													subClassOrg.refreshContainer(getSelectedClass());
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
											Notification.show(
													EMapper.getEDesc(EMapper.ECODE_INVALID_INPUT_DATA),
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
								confirmWindow.close();

								subClassName = String.valueOf(allNamesComboBox.getValue());

								if (Validator.validateString(subClassName)) {

									HashMap<String, String> requestData = new HashMap<String, String>();
									requestData.put("className", container.getItem(table.getValue())
											.toString());
									requestData.put("subClassName", subClassName);
									requestData.put(Constants.MINISTER_ASSIGNMENT_OPTION,
											Constants.MINISTER_ASSIGNMENT_OPTION_S);

									int result = RequestHandler.handleUpdateRequest("assignSubClass",
											requestData);

									Notification.show(EMapper.getEDesc(result), EMapper.getNType(result));

									subClassOrg.refreshContainer(getSelectedClass());
								} else {
									Notification.show(EMapper.getEDesc(EMapper.ECODE_INVALID_INPUT_DATA),
											EMapper.getNType(EMapper.ECODE_INVALID_INPUT_DATA));
								}

							}
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
		// ///////////////////////////////////////////////////////////////
		final Command downloadReportsCommand = new Command() {
			private static final long serialVersionUID = 4739618741655145334L;

			@SuppressWarnings("deprecation")
			@Override
			public void menuSelected(final MenuItem selectedItem) {
				try {
					if (getSelectedClass() == null) {
						Notification.show("„‰ ›÷·ﬂ «Œ — «·›’·", null, Type.TRAY_NOTIFICATION);
						return;
					} else if (goDate == null || goDate.length() == 0) {
						Notification.show("„‰ ›÷·ﬂ «Œ — «· «—ÌŒ", null, Type.TRAY_NOTIFICATION);
						return;
					}

					String[] reportFilesPaths = OrgHandler.generateEftqadReportForClass(getSelectedClass(),
							goDate);

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
		final Command printReportsCommand = new Command() {
			private static final long serialVersionUID = -4168910857126708902L;

			@SuppressWarnings("deprecation")
			@Override
			public void menuSelected(final MenuItem selectedItem) {
				if (getSelectedClass() == null) {
					Notification.show("„‰ ›÷·ﬂ «Œ — «·›’·", null, Type.TRAY_NOTIFICATION);
					return;
				} else if (goDate == null || goDate.length() == 0) {
					Notification.show("„‰ ›÷·ﬂ «Œ — «· «—ÌŒ", null, Type.TRAY_NOTIFICATION);
					return;
				}

				String[] reportFilesPaths = OrgHandler.generateEftqadReportForClass(getSelectedClass(),
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

		MenuItem addMenuItem = settingItems.addItem(" ÃœÌœ", newClassCommand);
		addMenuItem.setIcon(FontAwesome.PLUS_CIRCLE);

		MenuItem editMenuItem = settingItems.addItem("  ⁄œÌ·", updateClassCommand);
		editMenuItem.setIcon(FontAwesome.EDIT);

		MenuItem deleteMenuItem = settingItems.addItem(" Õ–›", deleteClassCommand);
		deleteMenuItem.setIcon(FontAwesome.TRASH_O);

		settingItems.addSeparator();

		MenuItem assignYearMenuItem = settingItems.addItem("  ⁄ÌÌ‰ „Ã„Ê⁄…", assignSubClassCommand);
		assignYearMenuItem.setIcon(FontAwesome.LINK);

		settingItems.addSeparator();

		MenuItem downloadReportsMenuItem = settingItems.addItem("  ‰“Ì·  ﬁ«—Ì—", downloadReportsCommand);
		downloadReportsMenuItem.setIcon(FontAwesome.DOWNLOAD);

		MenuItem printReportMenuItem = settingItems.addItem(" ÿ»«⁄…  ﬁ«—Ì—", printReportsCommand);
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
