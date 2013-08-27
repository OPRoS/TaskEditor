package kr.re.etri.tpl.diagram.commands;

import java.util.List;

import kr.re.etri.tpl.taskmodel.ItemElement;

import org.eclipse.gef.commands.Command;

public class ReplaceListCommand extends Command {

	protected List list;
	protected ItemElement object;
	protected int newPosition;
	protected int oldPosition;
	
	public ReplaceListCommand(List list, int newPosition, ItemElement object) {
		this.list = list;
		this.newPosition = newPosition;
		this.object = object;
	}
	
	@Override
	public void execute() {
		oldPosition = list.indexOf(object);
		
		if (newPosition < 0 || newPosition > list.size() - 1) {
			return;
		}
		if (oldPosition < 0) {
			return;
		}
		
		redo();
	}

	@Override
	public void redo() {
		Object element = list.remove(oldPosition);
		list.add(newPosition, element);
	}

	@Override
	public void undo() {
		Object element = list.remove(newPosition);
		list.add(oldPosition, element);
	}

}
