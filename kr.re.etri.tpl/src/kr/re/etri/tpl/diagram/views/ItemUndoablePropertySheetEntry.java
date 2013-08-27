package kr.re.etri.tpl.diagram.views;

import java.text.MessageFormat;
import java.util.EventObject;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackListener;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.commands.ForwardUndoCompoundCommand;
import org.eclipse.gef.internal.GEFMessages;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySource2;

public class ItemUndoablePropertySheetEntry extends ItemPropertySheetEntry {
	private CommandStackListener commandStackListener;

	private CommandStack stack;

	private ItemUndoablePropertySheetEntry() { }

	/**
	 * Constructs the root entry using the given command stack.
	 * @param stack the command stack
	 * @since 3.1
	 */
	public ItemUndoablePropertySheetEntry(CommandStack stack) {
		setCommandStack(stack);
	}

	/**
	 * @see ItemPropertySheetEntry#createChildEntry()
	 */
	protected ItemPropertySheetEntry createChildEntry() {
		return new ItemUndoablePropertySheetEntry();
	}

	/**
	 * @see org.eclipse.ui.views.properties.IPropertySheetEntry#dispose()
	 */
	public void dispose() {
		if (stack != null)
			stack.removeCommandStackListener(commandStackListener);
		super.dispose();
	}

	CommandStack getCommandStack() {
		//only the root has, and is listening too, the command stack
		if (getParent() != null)
			return ((ItemUndoablePropertySheetEntry)getParent()).getCommandStack();
		return stack;
	}

	/**
	 * @see org.eclipse.ui.views.properties.IPropertySheetEntry#resetPropertyValue()
	 */
	public void resetPropertyValue() {
		CompoundCommand cc = new CompoundCommand();
		ResetValueCommand restoreCmd;

		if (getParent() == null)
			// root does not have a default value
			return;

		//	Use our parent's values to reset our values.
		boolean change = false;
		Object[] objects = getParent().getValues();
		for (int i = 0; i < objects.length; i++) {
			IPropertySource source = getPropertySource(objects[i]);
			if (source.isPropertySet(getDescriptor().getId())) {
				//source.resetPropertyValue(getDescriptor()getId());
				restoreCmd = new ResetValueCommand();
				restoreCmd.setTarget(source);
				restoreCmd.setPropertyId(getDescriptor().getId());
				cc.add(restoreCmd);			
				change = true;
			}
		}
		if (change) {
			getCommandStack().execute(cc);
			refreshFromRoot();
		}
	}

	void setCommandStack(CommandStack stack) {
		this.stack = stack;
		commandStackListener = new CommandStackListener() {
			public void commandStackChanged(EventObject e) {
				refreshFromRoot();
			}
		};
		stack.addCommandStackListener(commandStackListener);
	}

	/**
	 * @see ItemPropertySheetEntry#valueChanged(ItemPropertySheetEntry)
	 */
	protected void valueChanged(ItemPropertySheetEntry child) {
		valueChanged((ItemUndoablePropertySheetEntry)child,
				new ForwardUndoCompoundCommand());
	}

	void valueChanged(ItemUndoablePropertySheetEntry child, CompoundCommand command) {
		CompoundCommand cc = new CompoundCommand();
		command.add(cc);

		SetValueCommand setCommand;
		for (int i = 0; i < getValues().length; i++) {
			setCommand = new SetValueCommand(child.getDisplayName());
			setCommand.setTarget(getPropertySource(getValues()[i]));
			setCommand.setPropertyId(child.getDescriptor().getId());
			setCommand.setPropertyValue(child.getValues()[i]);
			cc.add(setCommand);
		}

		// inform our parent
		if (getParent() != null)
			((ItemUndoablePropertySheetEntry)getParent()).valueChanged(this, command);
		else {
			//I am the root entry
			stack.execute(command);
		}
	}
	
	class SetValueCommand
		extends Command {
	
		protected Object propertyValue;
		protected Object propertyName;
		protected Object undoValue;
		protected boolean resetOnUndo;
		protected IPropertySource target;
		
		public SetValueCommand() {
			super(""); //$NON-NLS-1$
		}
		
		public SetValueCommand(String propLabel) {
			super(MessageFormat.format(GEFMessages.SetPropertyValueCommand_Label,
										new Object[]{propLabel}).trim());
		}
		
		public boolean canExecute() {
			return true;
		}
		
		public void execute() {
			/*
			 * Fix for Bug# 54250
			 * IPropertySource.isPropertySet(String) returns false both when there is no default 
			 * value, and when there is a default value and the property is set to that value.
			 * To correctly determine if a reset should be done during undo, we compare the
			 * return value of isPropertySet(String) before and after setPropertyValue(...) is
			 * invoked.  If they are different (it must have been false before and true after --
			 * it cannot be the other way around), then that means we need to reset.
			 */
			boolean wasPropertySet = getTarget().isPropertySet(propertyName);
			undoValue = getTarget().getPropertyValue(propertyName);
			if (undoValue instanceof IPropertySource)
				undoValue = ((IPropertySource)undoValue).getEditableValue();
			if (propertyValue instanceof IPropertySource)
				propertyValue = ((IPropertySource)propertyValue).getEditableValue();
			getTarget().setPropertyValue(propertyName, propertyValue);
			if (getTarget() instanceof IPropertySource2)
				resetOnUndo = !wasPropertySet
						&& ((IPropertySource2)getTarget()).isPropertyResettable(propertyName);
			else
				resetOnUndo = !wasPropertySet && getTarget().isPropertySet(propertyName);
			if (resetOnUndo)
				undoValue = null;
		}
		
		public IPropertySource getTarget() {
			return target;
		}
		
		public void setTarget(IPropertySource aTarget) {
			target = aTarget;
		}
		
		public void redo() {
			execute();
		}
		
		public void setPropertyId(Object pName) {
			propertyName = pName;
		}
		
		public void setPropertyValue(Object val) {
			propertyValue = val;
		}
		
		public void undo() {
			if (resetOnUndo)
				getTarget().resetPropertyValue(propertyName);
			else
				getTarget().setPropertyValue(propertyName, undoValue);
		}

	}

	/**
	 * A Command used to restore the default value of a property.
	 * @author Pratik Shah
	 */
	class ResetValueCommand 
		extends Command 
	{

		/** the property that has to be reset */
		protected Object propertyName;
		/** the current non-default value of the property */
		protected Object undoValue;
		/** the property source whose property has to be reset */
		protected IPropertySource target;
	
		/**
		 * Default Constructor: Sets the label for the Command
		 * @since 3.1
		 */
		public ResetValueCommand() {
			super(/*GEFMessages.RestoreDefaultValueCommand_Label*/"Reset {0} Property");
		}
	
		/**
		 * Returns <code>true</code> IFF:<br>
		 * 1) the target and property have been specified<br>
		 * 2) the property has a default value<br>
		 * 3) the value set for that property is not the default
		 * @see org.eclipse.gef.commands.Command#canExecute()
		 */
		public boolean canExecute() {
			boolean answer = false;
			if (target != null && propertyName != null) {
				answer = target.isPropertySet(propertyName);
				if (target instanceof IPropertySource2)
					answer = answer 
							&& (((IPropertySource2)target).isPropertyResettable(propertyName));
			}
			return answer;
		}
	
		/**
		 * Caches the undo value and invokes redo()
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		public void execute() {
			undoValue = target.getPropertyValue(propertyName);
			if (undoValue instanceof IPropertySource)
				undoValue = ((IPropertySource)undoValue).getEditableValue();
			redo();
		}
	
		/**
		 * Sets the IPropertySource.
		 * @param propSource the IPropertySource whose property has to be reset
		 */
		public void setTarget(IPropertySource propSource) {
			target = propSource;
		}
	
		/**
		 * Resets the specified property on the specified IPropertySource
		 * @see org.eclipse.gef.commands.Command#redo()
		 */
		public void redo() {
			target.resetPropertyValue(propertyName);
		}
	
		/**
		 * Sets the property that is to be reset.
		 * @param pName the property to be reset
		 */
		public void setPropertyId(Object pName) {
			propertyName = pName;
		}
	
		/**
		 * Restores the non-default value that was reset. 
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		public void undo() {
			target.setPropertyValue(propertyName, undoValue);
		}
	}
}
