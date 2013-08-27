package kr.re.etri.tpl.diagram.editors;

import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.ModelManager;
import kr.re.etri.tpl.diagram.tools.RTMConnectionDragCreationTool;
import kr.re.etri.tpl.taskmodel.ActionElement;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.LineEndPoint;
import kr.re.etri.tpl.taskmodel.LineStyle;
import kr.re.etri.tpl.taskmodel.RelationShip;
import kr.re.etri.tpl.taskmodel.StateAction;
import kr.re.etri.tpl.taskmodel.StateActionType;
import kr.re.etri.tpl.taskmodel.StateAttribute;
import kr.re.etri.tpl.taskmodel.StateElement;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.TaskElement;

import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteSeparator;
import org.eclipse.gef.palette.PanningSelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Utility class that can create a GEF Palette.
 * @see #createPalette() 
 * @author Elias Volanakis
 * @author Chris Aniszczyk
 */
public final class TaskDiagramEditorPaletteFactory {

	/** Preference ID used to persist the palette location. */
	private static final String PALETTE_DOCK_LOCATION = "ShapeModelEditorPaletteFactory.Location";
	/** Preference ID used to persist the palette size. */
	private static final String PALETTE_SIZE = "ShapeModelEditorPaletteFactory.Size";
	/** Preference ID used to persist the flyout palette's state. */
	private static final String PALETTE_STATE = "ShapeModelEditorPaletteFactory.StateElement";

	/**
	 * Creates the PaletteRoot and adds all palette elements.
	 * Use this factory method to create a new palette for your graphical editor.
	 * @return a new PaletteRoot
	 */
	public static PaletteRoot createPalette() {
		PaletteRoot palette = new PaletteRoot();
		palette.add(createToolsGroup(palette));
		palette.add(createShapesDrawer(palette));
		return palette;
	}
	
	/** Create the "Shapes" drawer. */
	private static PaletteContainer createShapesDrawer(PaletteRoot palette) {
		PaletteDrawer componentsDrawer = new PaletteDrawer("Model Items");
		
		// TODO FIX, don't use simple factory :D
		CombinedTemplateCreationEntry component;
		component = new CombinedTemplateCreationEntry(
				"Behavior", 
				"Create a Behavior", 
				BehaviorElement.class,
				new CreationFactory() {
					public Object getNewObject() {
						return ModelManager.getFactory().createTaskElement();
					}
					public Object getObjectType() { return null; }
				},
				TaskModelPlugin.getImageDescriptor("icons/club/task.png"),
				TaskModelPlugin.getImageDescriptor("icons/task24.gif"));
		componentsDrawer.add(component);
	
		component = new CombinedTemplateCreationEntry(
				"State",
				"Create a State", 
				StateElement.class,
				new CreationFactory() {
					public Object getNewObject() {
						StateElement state = ModelManager.getFactory().createStateElement();
						state.setAttribute(StateAttribute.NORMAL);
						StateAction entryAction = ModelManager.getFactory().createStateAction();
						entryAction.setName("entry");
						entryAction.setStateActionType(StateActionType.ENTRY);
						state.setEntry(entryAction);
						
						StateAction stayAction = ModelManager.getFactory().createStateAction();
						stayAction.setName("stay");
						stayAction.setStateActionType(StateActionType.STAY);
						state.setStay(stayAction);
						
						StateAction exitAction = ModelManager.getFactory().createStateAction();
						exitAction.setName("exit");
						exitAction.setStateActionType(StateActionType.EXIT);
						state.setExit(exitAction);
						return state;
					}
					public Object getObjectType() { return null; }
				},
				TaskModelPlugin.getImageDescriptor("icons/state16.gif"),
				TaskModelPlugin.getImageDescriptor("icons/state24.gif"));
		componentsDrawer.add(component);
		
		component = new CombinedTemplateCreationEntry(
				"InitialState",
				"Create a InitialState", 
				StateElement.class,
				new CreationFactory() {
					public Object getNewObject() {
						StateElement state = ModelManager.getFactory().createStateElement();
						state.setAttribute(StateAttribute.INITIAL);
						StateAction entryAction = ModelManager.getFactory().createStateAction();
						entryAction.setName("entry");
						entryAction.setStateActionType(StateActionType.ENTRY);
						state.setEntry(entryAction);
						
						StateAction stayAction = ModelManager.getFactory().createStateAction();
						stayAction.setName("stay");
						stayAction.setStateActionType(StateActionType.STAY);
						state.setStay(stayAction);
						
						StateAction exitAction = ModelManager.getFactory().createStateAction();
						exitAction.setName("exit");
						exitAction.setStateActionType(StateActionType.EXIT);
						state.setExit(exitAction);
						return state;
					}
					public Object getObjectType() { return null; }
				},
				TaskModelPlugin.getImageDescriptor("icons/initialstate16.gif"),
				TaskModelPlugin.getImageDescriptor("icons/initialstate24.gif"));
		componentsDrawer.add(component);
		
		component = new CombinedTemplateCreationEntry(
				"TargetState",
				"Create a TargetState", 
				StateElement.class,
				new CreationFactory() {
					public Object getNewObject() {
						StateElement state = ModelManager.getFactory().createStateElement();
						state.setAttribute(StateAttribute.TARGET);
						StateAction entryAction = ModelManager.getFactory().createStateAction();
						entryAction.setName("entry");
						entryAction.setStateActionType(StateActionType.ENTRY);
						state.setEntry(entryAction);
						
						StateAction stayAction = ModelManager.getFactory().createStateAction();
						stayAction.setName("stay");
						stayAction.setStateActionType(StateActionType.STAY);
						state.setStay(stayAction);
						
						StateAction exitAction = ModelManager.getFactory().createStateAction();
						exitAction.setName("exit");
						exitAction.setStateActionType(StateActionType.EXIT);
						state.setExit(exitAction);
						return state;
					}
					public Object getObjectType() { return null; }
				},
				TaskModelPlugin.getImageDescriptor("icons/targetstate16.gif"),
				TaskModelPlugin.getImageDescriptor("icons/targetstate24.gif"));
		componentsDrawer.add(component);

		component = new CombinedTemplateCreationEntry(
				"Action", 
				"Create a action", 
				ActionElement.class,
				new CreationFactory() {
					public Object getNewObject() {
						return ModelManager.getFactory().createActionElement();
						}
					public Object getObjectType() { return null; }
				},
				TaskModelPlugin.getImageDescriptor("icons/action16.gif"),
				TaskModelPlugin.getImageDescriptor("icons/action24.gif"));
		componentsDrawer.add(component);
	

		// Add a (unnamed) separator to the group
		componentsDrawer.add(new PaletteSeparator());

		ToolEntry tool;
		// Add (solid-line) connection tool 
		tool = new ConnectionCreationToolEntry(
				"Call Behavior",
				"Call Behavior",
				new CreationFactory() {
					public Object getNewObject() { return null; }
					// see ShapeEditPart#createEditPolicies() 
					// this is abused to transmit the desired line style 
					public Object getObjectType() {
						ConnectionElement conn = ModelManager.getFactory().createConnectionElement();
						conn.setLineStyle(LineStyle.LINE_SOLID);
						conn.setRelationship(RelationShip.TASK_CALL);
						conn.setSourceEndPoint(LineEndPoint.NONE);
						conn.setTargetEndPoint(LineEndPoint.CLOSED_TRIANGLE);
						return conn;
					}
				},
				TaskModelPlugin.getImageDescriptor("icons/solidclosedarrow16.gif"),
				TaskModelPlugin.getImageDescriptor("icons/solidclosedarrow24.gif"));
		tool.setToolClass(RTMConnectionDragCreationTool.class);

		componentsDrawer.add(tool);

		// Add (dashed-line) connection tool
		tool = new ConnectionCreationToolEntry(
				"Transition",
				"Transition",
				new CreationFactory() {
					public Object getNewObject() { return null; }
					// see ShapeEditPart#createEditPolicies()
					// this is abused to transmit the desired line style
					
					public Object getObjectType() {
						ConnectionElement conn = ModelManager.getFactory().createConnectionElement();
						conn.setLineStyle(LineStyle.LINE_SOLID);
						conn.setRelationship(RelationShip.TRANSITION);
						conn.setSourceEndPoint(LineEndPoint.NONE);
						conn.setTargetEndPoint(LineEndPoint.OPENED_ARROW);
						return conn;
					}
				},
				TaskModelPlugin.getImageDescriptor("icons/solidopenarrow16.gif"),
				TaskModelPlugin.getImageDescriptor("icons/solidopenarrow24.gif"));
		tool.setToolClass(RTMConnectionDragCreationTool.class);
		componentsDrawer.add(tool);

		// Add (dashed-line) connection tool
		tool = new ConnectionCreationToolEntry(
				"Call action",
				"Call action",
				new CreationFactory() {
					public Object getNewObject() { return null; }
					// see ShapeEditPart#createEditPolicies()
					// this is abused to transmit the desired line style
					
					public Object getObjectType() {
						ConnectionElement conn = ModelManager.getFactory().createConnectionElement();
						conn.setLineStyle(LineStyle.LINE_DASH);
						conn.setRelationship(RelationShip.ACTION_CALL);
						conn.setSourceEndPoint(LineEndPoint.NONE);
						conn.setTargetEndPoint(LineEndPoint.OPENED_ARROW);
						return conn;
					}
				},
				TaskModelPlugin.getImageDescriptor("icons/dotopenarrow16.gif"),
				TaskModelPlugin.getImageDescriptor("icons/dotopenarrow24.gif"));
		tool.setToolClass(RTMConnectionDragCreationTool.class);
		componentsDrawer.add(tool);

		return componentsDrawer;
	}
	
	/**
	 * Return a FlyoutPreferences instance used to save/load the preferences of a flyout palette.
	 */
	public static FlyoutPreferences createPalettePreferences() {
		return new FlyoutPreferences() {
			private IPreferenceStore getPreferenceStore() {
				return TaskModelPlugin.getDefault().getPreferenceStore();
			}
			public int getDockLocation() {
				return getPreferenceStore().getInt(PALETTE_DOCK_LOCATION);
			}
			public int getPaletteState() {
				return getPreferenceStore().getInt(PALETTE_STATE);
			}
			public int getPaletteWidth() {
				return getPreferenceStore().getInt(PALETTE_SIZE);
			}
			public void setDockLocation(int location) {
				getPreferenceStore().setValue(PALETTE_DOCK_LOCATION, location);
			}
			public void setPaletteState(int state) {
				getPreferenceStore().setValue(PALETTE_STATE, state);
			}
			public void setPaletteWidth(int width) {
				getPreferenceStore().setValue(PALETTE_SIZE, width);
			}
		};
	}
	
	/** Create the "Tools" group. */
	private static PaletteContainer createToolsGroup(PaletteRoot palette) {
		PaletteGroup toolGroup = new PaletteGroup("Tools");
	
		// Add a selection tool to the group
		ToolEntry tool = new PanningSelectionToolEntry();
		toolGroup.add(tool);
		palette.setDefaultEntry(tool);
		
		// Add a marquee tool to the group
		toolGroup.add(new MarqueeToolEntry());

		return toolGroup;
	}
	
	/** Utility class. */
	private TaskDiagramEditorPaletteFactory() {
		// Utility class
	}

}