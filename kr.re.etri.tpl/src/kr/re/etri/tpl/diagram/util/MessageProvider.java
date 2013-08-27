package kr.re.etri.tpl.diagram.util;

import java.util.ArrayList;

import kr.re.etri.tpl.diagram.listener.IMessageListener;
import kr.re.etri.tpl.diagram.listener.IMessageProvider;

public class MessageProvider implements IMessageProvider {
	private ArrayList<IMessageListener> listeners = new ArrayList<IMessageListener>();

	@Override
	public void addMessageListener(IMessageListener listener) {
		if(listeners.contains(listener)) {
			return;
		}

		listeners.add(listener);
	}

	@Override
	public void removeMessageListener(IMessageListener listener) {
		listeners.remove(listener);
	}

	@Override
	public void fireMessage(String newMessage, int newType) {
		int size = listeners.size();
		for(int idx = 0; idx < size; idx += 1) {
			IMessageListener listener = listeners.get(idx);
			listener.fireMessage(newMessage, newType);
		}
	}
}
