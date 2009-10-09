package scrum.client.test;

import ilarkesto.gwt.client.AAction;
import ilarkesto.gwt.client.AIntegerViewEditWidget;
import ilarkesto.gwt.client.ARichtextViewEditWidget;
import ilarkesto.gwt.client.ATextViewEditWidget;
import ilarkesto.gwt.client.AWidget;
import ilarkesto.gwt.client.ButtonWidget;
import ilarkesto.gwt.client.ImageAnchor;
import ilarkesto.gwt.client.MultiSelectionWidget;
import ilarkesto.gwt.client.NavigatorWidget;
import ilarkesto.gwt.client.ToolbarWidget;
import scrum.client.ScrumGwtApplication;
import scrum.client.common.AExtensibleBlockWidget;
import scrum.client.common.AScrumAction;
import scrum.client.common.BlockListWidget;
import scrum.client.common.BlockWidgetFactory;
import scrum.client.common.FieldsWidget;
import scrum.client.img.Img;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class WidgetsTesterWidget extends AWidget {

	private FlowPanel panel;

	@Override
	protected Widget onInitialization() {
		panel = new FlowPanel();
		panel.setStyleName("WidgetsTesterWidget");

		testTextConverter();
		testBlockList();
		testFields();
		testMultiSelection();
		testNavigator();
		testToolbar();
		testButtons();
		// testImageAnchor();

		return panel;
	}

	private void testTextConverter() {
		StringBuilder html = new StringBuilder();
		html.append(
			ScrumGwtApplication.get().richtextToHtml(
				"[Wiki] r1 aaaa t5 aaaa (r3) aaaa r3. aaaa r3: aaaa [t12] aaar7 aaaa r7x aaaa t9")).append("<hr>");
		html.append(ScrumGwtApplication.get().richtextToHtml("<b>html?</b> C&A\nnew line")).append("<hr>");
		addTest("TextConverter", new HTML(html.toString()));
	}

	private void testBlockList() {
		final BlockListWidget<String> list = new BlockListWidget<String>(TestBlock.FACTORY);
		list.setObjects("Element A", "Element B", "Element C", "Element D", "Element E");
		list.update();
		addTest("BlockList", list);
	}

	private static class TestBlock extends AExtensibleBlockWidget<String> {

		@Override
		protected void onCollapsedInitialization() {}

		@Override
		protected Widget onExtendedInitialization() {
			return null;
		}

		@Override
		protected void onUpdateHead() {
			setBlockTitle(getObject());
			setIcon(Img.bundle.project16());
			addToolbarAction(new DummyAction("Action 1"));
			addToolbarAction(new DummyAction("Action 2"));
			addMenuAction(new DummyAction("Action 3"));
			addMenuAction(new DummyAction("Action 4"));
			addMenuAction(new DummyAction("Action 5"));
		}

		@Override
		protected Widget onUpdateBody() {
			HTML content = new HTML(
					"<h3>"
							+ getObject()
							+ "</h3><p>Das ist der Content. Das ist der Content. Das ist der Content. Das ist der Content. </p>");
			return content;
		}

		public static BlockWidgetFactory<String> FACTORY = new BlockWidgetFactory<String>() {

			public TestBlock createBlock() {
				return new TestBlock();
			}
		};

	}

	private String fieldsText = "test";
	private String fieldsRichText = "test";
	private Integer fieldsInt = 5;

	private void testFields() {
		FieldsWidget fields = new FieldsWidget();
		fields.add("ATextViewEditWidget", new ATextViewEditWidget() {

			@Override
			protected void onViewerUpdate() {
				setViewerText(fieldsText);
			}

			@Override
			protected void onEditorUpdate() {
				setEditorText(fieldsText);
			}

			@Override
			protected void onEditorSubmit() {
				fieldsText = getEditorText();
			}
		});
		fields.add("ARichtextViewEditWidget", new ARichtextViewEditWidget() {

			@Override
			protected void onViewerUpdate() {
				setViewerText(fieldsRichText);
			}

			@Override
			protected void onEditorUpdate() {
				setEditorText(fieldsRichText);
			}

			@Override
			protected void onEditorSubmit() {
				fieldsRichText = getEditorText();
			}
		});
		fields.add("AIntegerViewEditWidget", new AIntegerViewEditWidget() {

			@Override
			protected void onIntegerViewerUpdate() {
				setViewerValue(fieldsInt, "times");
			}

			@Override
			protected void onEditorUpdate() {
				setEditorValue(fieldsInt);
			}

			@Override
			protected void onEditorSubmit() {
				fieldsInt = getEditorValue();
			}

			@Override
			protected void onPlusClicked() {
				fieldsInt++;
			}

			@Override
			protected void onMinusClicked() {
				fieldsInt--;
			}

		});
		fields.update();
		addTest("FieldsWidget", fields);
	}

	private void testMultiSelection() {
		MultiSelectionWidget<String> ms = new MultiSelectionWidget<String>();
		ms.add("Item 1");
		ms.add("Item 2");
		ms.add("Item 3");
		ms.update();
		addTest("MultiSelectionWidget", ms);
	}

	private void testNavigator() {
		NavigatorWidget navigator = new NavigatorWidget();
		navigator.addItem(Img.bundle.test16(), "Item 1", "1", null);
		navigator.addItem(Img.bundle.test16(), "Item 2", "2", null);
		navigator.addItem(Img.bundle.test16(), "Item 3", "3", null);
		addTest("NavigatorWidget", navigator);
	}

	private void testToolbar() {
		ToolbarWidget toolbar = new ToolbarWidget();
		toolbar.add(new ButtonWidget(createAction(Img.bundle.test16().createImage(), "icon and text")));
		toolbar.add(new ButtonWidget(createAction("text only")));
		toolbar.add(new ButtonWidget(createAction(Img.bundle.test16().createImage(), null)));
		addTest("ToolbarWidget", toolbar);
	}

	private void testButtons() {
		addTest("ButtonWidget:text-only", new ButtonWidget(createAction("text only")));
		addTest("ButtonWidget:icon-only", new ButtonWidget(createAction(Img.bundle.test16().createImage(), null)));
		addTest("ButtonWidget:icon-text", new ButtonWidget(createAction(Img.bundle.test16().createImage(),
			"icon and text")));
		addTest("ButtonWidget:nonexecutable", new ButtonWidget(createAction(Img.bundle.test16().createImage(),
			"icon and text", false)));

		FlowPanel multipleButtons = new FlowPanel();
		multipleButtons.add(new ButtonWidget(createAction("Button 1")).update());
		multipleButtons.add(new ButtonWidget(createAction("Button 2")).update());
		multipleButtons.add(new ButtonWidget(createAction("Button 3")).update());
		addTest("multiple ButtonWidgets", multipleButtons);
	}

	private void testImageAnchor() {
		ImageAnchor a = new ImageAnchor(Img.bundle.test16().createImage(), "click");
		addTest("ImageAnchor", a);
	}

	@Override
	protected void onUpdate() {

	}

	private void addTest(String title, Widget content) {
		if (content instanceof AWidget) ((AWidget) content).update();

		SimplePanel sectionContent = new SimplePanel();
		sectionContent.setStyleName("test-content");
		sectionContent.setWidget(content);

		FlowPanel section = new FlowPanel();
		section.setStyleName("test-section");
		section.add(new Label(title));
		section.add(sectionContent);
		panel.add(section);
	}

	private AAction createAction(String label) {
		return createAction(null, label);
	}

	private AAction createAction(final Image icon, final String label) {
		return createAction(icon, label, true);
	}

	private AAction createAction(final Image icon, final String label, final boolean executable) {
		return new AAction() {

			@Override
			protected void onExecute() {}

			@Override
			public String getLabel() {
				return label;
			}

			@Override
			public Image getIcon() {
				return icon;
			}

			@Override
			public boolean isExecutable() {
				return executable;
			}

			@Override
			public String getTooltip() {
				return "Tooltip for " + getLabel();
			}
		};
	}

	static class DummyAction extends AScrumAction {

		private String label;

		public DummyAction(String label) {
			this.label = label;
		}

		@Override
		public String getLabel() {
			return label;
		}

		@Override
		public boolean isExecutable() {
			return true;
		}

		@Override
		public String getTooltip() {
			return "Tooltip for " + label;
		}

		@Override
		protected void onExecute() {}

	}

}
