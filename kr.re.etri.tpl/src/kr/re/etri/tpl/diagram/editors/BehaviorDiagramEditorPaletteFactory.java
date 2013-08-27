package kr.re.etri.tpl.diagram.editors;

import kr.re.etri.tpl.IconStrings;
import kr.re.etri.tpl.TaskModelPlugin;
import kr.re.etri.tpl.diagram.ModelManager;
import kr.re.etri.tpl.diagram.editors.tools.ReferCreationTool;
import kr.re.etri.tpl.diagram.tools.RTMConnectionDragCreationTool;
import kr.re.etri.tpl.taskmodel.BehaviorElement;
import kr.re.etri.tpl.taskmodel.ConnectionElement;
import kr.re.etri.tpl.taskmodel.ConnectorElement;
import kr.re.etri.tpl.taskmodel.ConnectorType;
import kr.re.etri.tpl.taskmodel.ExpandTransElement;
import kr.re.etri.tpl.taskmodel.LineEndPoint;
import kr.re.etri.tpl.taskmodel.LineStyle;
import kr.re.etri.tpl.taskmodel.ReferAttribute;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.RelationShip;
import kr.re.etri.tpl.taskmodel.StateAction;
import kr.re.etri.tpl.taskmodel.StateActionType;
import kr.re.etri.tpl.taskmodel.StateAttribute;
import kr.re.etri.tpl.taskmodel.StateElement;
import kr.re.etri.tpl.taskmodel.StructBlockElement;
import kr.re.etri.tpl.taskmodel.StructType;
import kr.re.etri.tpl.taskmodel.TaskElement;
import kr.re.etri.tpl.taskmodel.WithElement;

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
public final class BehaviorDiagramEditorPaletteFactory {

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
		// KJH 20110412 s, task
		component = new CombinedTemplateCreationEntry(
				"Task",
				"Create a Task",
				TaskElement.class,
				new CreationFactory() {
					public Object getNewObject() {
						TaskElement task = ModelManager.getFactory().createWorkerElement();
						
						StructBlockElement strBlock = ModelManager.getFactory().createStructBlockElement();
						strBlock.setStructType(StructType.CONSTRUCT);
						strBlock.setName("initializer");
						strBlock.setParent(task);
						task.setInitialize(strBlock);
						
//						strBlock = ModelManager.getFactory().createStructBlockElement();
//						strBlock.setStructType(StructType.RUN);
//						strBlock.setName("run");
//						strBlock.setParent(task);
//						task.setRun(strBlock);
						
						strBlock = ModelManager.getFactory().createStructBlockElement();
						strBlock.setStructType(StructType.DESTRUCT);
						strBlock.setName("finalizer");
						strBlock.setParent(task);
						task.setFinalize(strBlock);
						
						return task;
					}
					public Object getObjectType() { return null; }
					
				},
				TaskModelPlugin.getImageDescriptor(IconStrings.TASK_16),
				TaskModelPlugin.getImageDescriptor(IconStrings.TASK_32));
		componentsDrawer.add(component);
		// KJH 20110412 e, task
		
		component = new CombinedTemplateCreationEntry(
				"Behavior", 
				"Create a Behavior", 
				BehaviorElement.class,
				new CreationFactory() {
					// KJH 201000803, behavior attribute
					public Object getNewObject() {
						BehaviorElement behavior = ModelManager.getFactory().createTaskElement();
						
						StructBlockElement strBlock = ModelManager.getFactory().createStructBlockElement();
						strBlock.setStructType(StructType.CONSTRUCT);
						strBlock.setName("construct");
						strBlock.setParent(behavior);
						behavior.setConstruct(strBlock);
						
						strBlock = ModelManager.getFactory().createStructBlockElement();
						strBlock.setStructType(StructType.DESTRUCT);
						strBlock.setName("destruct");
						strBlock.setParent(behavior);
						behavior.setDestruct(strBlock);
						
						return behavior;
					}
					public Object getObjectType() { return null; }
				},
				TaskModelPlugin.getImageDescriptor(IconStrings.BEHAVIOR_16),
				TaskModelPlugin.getImageDescriptor(IconStrings.BEHAVIOR_32));
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
						entryAction.setParent(state);
						state.setEntry(entryAction);
						
						StateAction stayAction = ModelManager.getFactory().createStateAction();
						stayAction.setName("stay");
						stayAction.setStateActionType(StateActionType.STAY);
						stayAction.setParent(state);
						state.setStay(stayAction);
						
						StateAction exitAction = ModelManager.getFactory().createStateAction();
						exitAction.setName("exit");
						exitAction.setStateActionType(StateActionType.EXIT);
						exitAction.setParent(state);
						state.setExit(exitAction);
						return state;
					}
					public Object getObjectType() { return null; }
				},
				TaskModelPlugin.getImageDescriptor(IconStrings.INITSTATE_16),
				TaskModelPlugin.getImageDescriptor(IconStrings.INITSTATE_32));
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
						entryAction.setParent(state);
						state.setEntry(entryAction);
						
						StateAction stayAction = ModelManager.getFactory().createStateAction();
						stayAction.setName("stay");
						stayAction.setStateActionType(StateActionType.STAY);
						stayAction.setParent(state);
						state.setStay(stayAction);
						
						StateAction exitAction = ModelManager.getFactory().createStateAction();
						exitAction.setName("exit");
						exitAction.setStateActionType(StateActionType.EXIT);
						exitAction.setParent(state);
						state.setExit(exitAction);
						return state;
					}
					public Object getObjectType() { return null; }
				},
				TaskModelPlugin.getImageDescriptor(IconStrings.STATE_16),
				TaskModelPlugin.getImageDescriptor(IconStrings.STATE_32));
		componentsDrawer.add(component);

		// KJH 20110418 s, goal state
		component = new CombinedTemplateCreationEntry(
				"GoalState",
				"Create a GoalState", 
				StateElement.class,
				new CreationFactory() {
					public Object getNewObject() {
						StateElement state = ModelManager.getFactory().createStateElement();
						state.setAttribute(StateAttribute.TARGET);
						StateAction entryAction = ModelManager.getFactory().createStateAction();
						entryAction.setName("entry");
						entryAction.setStateActionType(StateActionType.ENTRY);
						entryAction.setParent(state);
						state.setEntry(entryAction);
						
						StateAction stayAction = ModelManager.getFactory().createStateAction();
						stayAction.setName("stay");
						stayAction.setStateActionType(StateActionType.STAY);
						stayAction.setParent(state);
						state.setStay(stayAction);
						
						StateAction exitAction = ModelManager.getFactory().createStateAction();
						exitAction.setName("exit");
						exitAction.setStateActionType(StateActionType.EXIT);
						exitAction.setParent(state);
						state.setExit(exitAction);
						return state;
					}
					public Object getObjectType() { return null; }
				},
				TaskModelPlugin.getImageDescriptor(IconStrings.GOALSTATE_16),
				TaskModelPlugin.getImageDescriptor(IconStrings.GOALSTATE_32));
		componentsDrawer.add(component);
		// KJH 20110418 s, goal state
		
		component = new CombinedTemplateCreationEntry(
				"Reference Behavior",
				"Create a Reference Behavior", 
				BehaviorElement.class,
				new CreationFactory() {
					public Object getNewObject() {
						ReferElement refer = ModelManager.getFactory().createReferElement();
						refer.setAttribute(ReferAttribute.EXTERNAL);
						return refer;
					}
					public Object getObjectType() { return null; }
				},
				TaskModelPlugin.getImageDescriptor(IconStrings.REFBEHAVIOR_16),
				TaskModelPlugin.getImageDescriptor(IconStrings.REFBEHAVIOR_32));
		// KJH 20100910
		component.setToolClass(ReferCreationTool.class);
		componentsDrawer.add(component);
		
		// KJH 20101124 s, connector - conexer
		component = new CombinedTemplateCreationEntry(
				"Conexer Connector",
				"Create a Connector",
				ConnectorElement.class,
				new CreationFactory() {
					@Override
					public Object getNewObject() {
						ConnectorElement connector = ModelManager.getFactory().createConnectorElement();
						
						connector.setConType(ConnectorType.CONEXER);
						
						StructBlockElement strBlock = ModelManager.getFactory().createStructBlockElement();
						strBlock.setStructType(StructType.CONSTRUCT);
						strBlock.setName("construct");
						strBlock.setParent(connector);
						connector.setConstruct(strBlock);
						
						strBlock = ModelManager.getFactory().createStructBlockElement();
						strBlock.setStructType(StructType.DESTRUCT);
						strBlock.setName("destruct");
						strBlock.setParent(connector);
						connector.setDestruct(strBlock);

						return connector;
					}
					@Override
					public Object getObjectType() { return null; }
				},
				TaskModelPlugin.getImageDescriptor(IconStrings.CONEXER_16),
				TaskModelPlugin.getImageDescriptor(IconStrings.CONEXER_32));
		componentsDrawer.add(component);
		// KJH 20101124 e, connector - conexer
		
		// KJH 20110418 s, connector - seqexer
		component = new CombinedTemplateCreationEntry(
				"Seqexer Connector",
				"Create a Connector",
				ConnectorElement.class,
				new CreationFactory() {
					@Override
					public Object getNewObject() {
						ConnectorElement connector = ModelManager.getFactory().createConnectorElement();
						
						connector.setConType(ConnectorType.SEQEXER);
						
						StructBlockElement strBlock = ModelManager.getFactory().createStructBlockElement();
						strBlock.setStructType(StructType.CONSTRUCT);
						strBlock.setName("construct");
						strBlock.setParent(connector);
						connector.setConstruct(strBlock);
						
						strBlock = ModelManager.getFactory().createStructBlockElement();
						strBlock.setStructType(StructType.DESTRUCT);
						strBlock.setName("destruct");
						strBlock.setParent(connector);
						connector.setDestruct(strBlock);

						return connector;
					}
					@Override
					public Object getObjectType() { return null; }
				},
				TaskModelPlugin.getImageDescriptor(IconStrings.SEQEXER_16),
				TaskModelPlugin.getImageDescriptor(IconStrings.SEQEXER_32));
		componentsDrawer.add(component);
		// KJH 20110418 e, connector - seqexer
		
		// KJH 20110429 s, with
		component = new CombinedTemplateCreationEntry(
				"Run",	// KJH 20110728, With->Run
				"Create a Run",
				WithElement.class,
				new CreationFactory() {
					@Override
					public Object getNewObject() {
						WithElement w = ModelManager.getFactory().createWithElement();
						return w;
					}
					@Override
					public Object getObjectType() { return null; }
				},
				TaskModelPlugin.getImageDescriptor(IconStrings.WITH_16),
				TaskModelPlugin.getImageDescriptor(IconStrings.WITH_32));
		componentsDrawer.add(component);
		// KJH 20110429 e, with
		
		// KJH 20110429 s, expand&trans
		component = new CombinedTemplateCreationEntry(
				"Expand&Trans",
				"Create a Expand&Trans",
				ExpandTransElement.class,
				new CreationFactory() {
					@Override
					public Object getNewObject() {
						ExpandTransElement et = ModelManager.getFactory().createExpandTransElement();
						return et;
					}
					@Override
					public Object getObjectType() { return null; }
				},
				TaskModelPlugin.getImageDescriptor(IconStrings.PORT_16),
				TaskModelPlugin.getImageDescriptor(IconStrings.PORT_32));
		componentsDrawer.add(component);
		// KJH 20110429 e, expand&trans
		
		ToolEntry tool = new ConnectionCreationToolEntry(
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
				TaskModelPlugin.getImageDescriptor(IconStrings.TRANSITION_16),
				TaskModelPlugin.getImageDescriptor(IconStrings.TRANSITION_32));
		tool.setToolClass(RTMConnectionDragCreationTool.class);
		componentsDrawer.add(tool);

		// Add (solid-line) connection tool 
		tool = new ConnectionCreationToolEntry(
				"Expand",	// KJH 20110211, Call Behavior -> Expand
				"Expand",
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
				TaskModelPlugin.getImageDescriptor(IconStrings.EXPAND_16),
				TaskModelPlugin.getImageDescriptor(IconStrings.EXPAND_32));
		tool.setToolClass(RTMConnectionDragCreationTool.class);

		componentsDrawer.add(tool);

		// Add (dashed-line) connection tool
//		tool = new ConnectionCreationToolEntry(
//				"Call action",
//				"Call action",
//				new CreationFactory() {
//					public Object getNewObject() { return null; }
//					// see ShapeEditPart#createEditPolicies()
//					// this is abused to transmit the desired line style
//					
//					public Object getObjectType() {
//						ConnectionElement conn = ModelManager.getFactory().createConnectionElement();
//						conn.setLineStyle(LineStyle.LINE_DASH);
//						conn.setRelationship(RelationShip.ACTION_CALL);
//						conn.setSourceEndPoint(LineEndPoint.NONE);
//						conn.setTargetEndPoint(LineEndPoint.OPENED_ARROW);
//						return conn;
//					}
//				},
//				TaskModelPlugin.getImageDescriptor("icons/dotopenarrow16.gif"),
//				TaskModelPlugin.getImageDescriptor("icons/dotopenarrow24.gif"));
//		tool.setToolClass(RTMConnectionDragCreationTool.class);
//		componentsDrawer.add(tool);
		
//		component = new CombinedTemplateCreationEntry(
//				"goto stay", 
//				"Create a goto stay", 
//				new CreationFactory() {
//					public Object getNewObject() { return null; }
//					
//					public Object getObjectType() {
//						ConnectionElement conn = ModelManager.getFactory().createConnectionElement();
//						conn.setLineStyle(LineStyle.LINE_SOLID);
//						conn.setRelationship(RelationShip.TRANSITION);
//						conn.setSourceEndPoint(LineEndPoint.NONE);
//						conn.setTargetEndPoint(LineEndPoint.OPENED_ARROW);
//						return conn;
//					}
//				},
//				TaskModelPlugin.getImageDescriptor("icons/club/transition.png"),
//				TaskModelPlugin.getImageDescriptor("icons/solidopenarrow24.gif"));
//		tool.setToolClass(RTMConnectionDragCreationTool.class);
//		componentsDrawer.add(component);
		componentsDrawer.add(new PaletteSeparator());

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
	private BehaviorDiagramEditorPaletteFactory() {
		// Utility class
	}

}