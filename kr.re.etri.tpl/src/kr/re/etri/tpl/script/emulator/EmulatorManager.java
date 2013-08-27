package kr.re.etri.tpl.script.emulator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kr.re.etri.tpl.script.debug.DebugManager;
import kr.re.etri.tpl.script.debug.DebugMessageClient;
import kr.re.etri.tpl.script.debug.IConnectionListener;
import kr.re.etri.tpl.script.debug.IPostStateChangedListender;
import kr.re.etri.tpl.script.emulator.dialogs.EmulatorDialog2;
import kr.re.etri.tpl.script.emulator.dialogs.EmulatorTreeItem;
import kr.re.etri.tpl.script.grammar.tree2.IFuncNode;
import kr.re.etri.tpl.script.grammar.tree2.IModelNode;
import kr.re.etri.tpl.script.grammar.tree2.IScriptNode;
import kr.re.etri.tpl.script.grammar.tree2.IVarNode;
import kr.re.etri.tpl.script.grammar.tree2.ScriptManager;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IResource;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class EmulatorManager implements IConnectionListener, IPostStateChangedListender {
	private static Logger logger = Logger.getLogger(EmulatorManager.class);

	private static EmulatorManager manager;
	private DebugMessageClient client;
	private EmulatorDialog2 dialog;
	
	private EmulatorTreeItem root;
	
	private IResource oldResource;
	
	private EmulatorManager() {
		DebugManager.getDefault().addPostStateChangedListener(this);
	}
	
	public static EmulatorManager getDefault() {
		if (manager == null) {
			manager = new EmulatorManager();
		}
		return manager;
	}
	
	public boolean isConnected() {
		if (client == null || client.isConnected() == false) {
			return false;
		}
		return true;
	}
	
	private boolean send(String msg) {
		if (client != null && client.isConnected()) {
			try {
				client.sendMessage(msg);
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public boolean emul(EmulatorTreeItem[] onItems, EmulatorTreeItem[] offItems) {
		boolean result = false;
		StringBuffer sb = new StringBuffer();
		int msgCount = DebugManager.getDefault().getMsgCount();
		
		sb.append("REQ-start;emul;");
		sb.append("@msgid[").append(msgCount++).append("]");
		sb.append("@on[");
		for (int i = 0; i < onItems.length; i++) {
			if (i > 0) {
				sb.append(",");
			}
			sb.append(onItems[i].getFullName());
		}
		sb.append("]");
		sb.append("@off[");
		for (int i = 0; i < offItems.length; i++) {
			if (i > 0) {
				sb.append(",");
			}
			sb.append(offItems[i].getFullName());
		}
		sb.append("]");
		sb.append(";REQ-end\r\n");
		
		result = send(sb.toString());
		if (result) {	// KJH 20111124, modify
			DebugManager.getDefault().setMsgCount(msgCount);
		}
		
		return result;
	}
	
	public boolean set(EmulatorTreeItem item) {
		boolean result = false;
		
		if (item.validate()) {
			StringBuffer sb = new StringBuffer();
			int msgCount = DebugManager.getDefault().getMsgCount();

			sb.append("REQ-start;set;");
			sb.append("@msgid[").append(msgCount++).append("]");
			sb.append(item.toString());
			sb.append(";REQ-end\r\n");

			logger.debug(sb.toString());
			result = send(sb.toString());
			
			if (result) {	// KJH 20111124, modify
				DebugManager.getDefault().setMsgCount(msgCount);
			}
		}
		return result;
	}
	
	public void setDialog(final EmulatorDialog2 dialog) {
		this.dialog = dialog;
	}
	
	public EmulatorTreeItem getInput(IResource resource) {
		if (resource == null) {
			resource = DebugManager.getDefault().getCurProject();
		}
		
		if (root == null) {
			root = new EmulatorTreeItem(resource != null ? resource.getName() : "");
			root.setItemType(EmulatorTreeItem.NONE);
		}
		else {
			if (resource != null && !resource.getName().equals(root.getName())) {
				root = new EmulatorTreeItem(resource.getName());
				root.setItemType(EmulatorTreeItem.NONE);
			}
		}
		
		if (resource != null) {
			oldResource = resource;
			
			List<IScriptNode> list = ScriptManager.getInstance().getModels(resource);
			List<EmulatorTreeItem> remList = new ArrayList<EmulatorTreeItem>();
			
			for (int i = 0; i < root.getChildrenCount(); i++) {
				remList.add(root.getChild(i));
			}
			
			for (int i = 0; i < list.size(); i++) {
				EmulatorTreeItem child = createEmulatorTreeItem(root, list.get(i));
				if (child != null && !root.contains(child)) {
					root.addChild(child);
				}
				child.setParent(root);
				remList.remove(child);
			}
			
			for (int i = 0; i < remList.size(); i++) {
				root.removeChild(remList.get(i));
			}
		}

		return root;
	}
	
	private EmulatorTreeItem createEmulatorTreeItem(EmulatorTreeItem parent, Object object) {
		EmulatorTreeItem item = null;
		
		if (object instanceof IModelNode) {
			IModelNode model = (IModelNode)object;

			EmulatorTreeItem[] children = parent.getChildren();
			for (int i = 0; i < children.length; i++) {
				EmulatorTreeItem child = children[i];
				if (child.getItemType() == EmulatorTreeItem.MODEL &&
						child.getName().equals(model.getName())) {
					item = child;
					break;
				}
			}
			
			if (item == null) {
				item = new EmulatorTreeItem(model.getName(), EmulatorTreeItem.MODEL);
			}
			
			List<EmulatorTreeItem> remList = new ArrayList<EmulatorTreeItem>();
			
			for (int i = 0; i < item.getChildrenCount(); i++) {
				remList.add(item.getChild(i));
			}
			
			for (int i = 0; i < model.getChildrenCount(); i++) {
				EmulatorTreeItem child = createEmulatorTreeItem(item, model.getChild(i));
				if (child != null && !item.contains(child)) {
					item.addChild(child);
				}
				child.setParent(item);
				remList.remove(child);
			}
			
			for (int i = 0; i < remList.size(); i++) {
				item.removeChild(remList.get(i));
			}
		}
		else if (object instanceof IFuncNode) {
			IFuncNode func = (IFuncNode)object;

			EmulatorTreeItem[] children = parent.getChildren();
			for (int i = 0; i < children.length; i++) {
				EmulatorTreeItem child = children[i];
				if (child.getItemType() == EmulatorTreeItem.FUNCTION &&
						child.getName().equals(func.getName()) &&
						child.getType().equals(func.getRtn())) {
					item = child;
				}
			}
			
			if (item == null) {
				item = new EmulatorTreeItem(func.getName(), EmulatorTreeItem.FUNCTION);
				item.setType(func.getRtn());
			}
		}
		else if (object instanceof IVarNode) {
			IVarNode var = (IVarNode)object;

			EmulatorTreeItem[] children = parent.getChildren();
			for (int i = 0; i < children.length; i++) {
				EmulatorTreeItem child = children[i];
				if (child.getItemType() == EmulatorTreeItem.SYMBOL &&
						child.getName().equals(var.getName()) &&
						child.getType().equals(var.getVType())) {
					item = child;
				}
			}
			
			if (item == null) {
				item = new EmulatorTreeItem(var.getName(), EmulatorTreeItem.SYMBOL);
				item.setType(var.getVType());
			}
		}
		
		return item;
	}
	
	@Override
	public void changedConnection(DebugMessageClient listener) {
		client = listener;
		if (client == null || client.isConnected() == false) {
			if (dialog != null) {
				dialog.close();
			}
		}
	}

	private Shell getShell() {
		if (dialog != null) {
			return dialog.getShell();
		}
		return null;
	}
	
	private Display getDisplay() {
		Shell shell = getShell();
		if (shell != null && !shell.isDisposed()) {
			return shell.getDisplay();
		}
		return null;
	}
	

	@Override
	public void stateChanged() {
		// KJH 20111125, del	
//		Display display = getDisplay();
//		if (display != null && !display.isDisposed()) {
//			display.asyncExec(new Runnable() {
//				@Override
//				public void run() {
//					dialog.update();
//				}
//			});
//		}
	}

}
