package kr.re.etri.tpl.script.debug.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import kr.re.etri.tpl.script.debug.DebugManager;
import kr.re.etri.tpl.script.debug.ResponseMessageReader;
import kr.re.etri.tpl.script.debug.variables.Variable;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;

public class VariableMessageReader extends ResponseMessageReader {

	private DebugThreadView m_view;

	private static VariableMessageReader instance;

	public static VariableMessageReader getDefault() {
		if (instance == null)
			instance = new VariableMessageReader(null);
		return instance;
	}

	private VariableMessageReader(DebugThreadView view) {
		this.m_view = view;
	}

	private Shell getShell() {
		if (m_view != null) {
			return m_view.getSite().getShell();
		}
		return null;
	}
	
	private Display getDisplay() {
		Shell shell = getShell();
		if (shell != null) {
			return shell.getDisplay();
		}
		return null;
	}
	
	@Override
	public void update(String message) {
		StringTokenizer st = new StringTokenizer(message, "\r\n");
		DebugManager dManager = DebugManager.getDefault();
		
		Map<String, List<Variable>> map = new HashMap<String, List<Variable>>();
		boolean isVisit = false;
		
		while (st.hasMoreTokens()) {
			String msg = st.nextToken();
			
			super.update(msg);
			if (!isValid())
				continue;
			
			if (isEOB()/* && !dManager.isMonitoring()*/) {	// KJH 20110314, 조건 삭제
				break;
			}
			
			String thid = getAttribute(ATTR_THREAD);
			
			List<Variable> list = map.get(thid);
			if (list == null) {
				list = new ArrayList<Variable>();
				map.put(thid, list);
			}

			Variable var = new Variable(getAttribute(ATTR_MSGID),
					thid,
					getAttribute(ATTR_TASK),
					getAttribute(ATTR_TIME),
					getAttribute(ATTR_TARGET),
					getAttribute(ATTR_FILE),
					getAttribute(ATTR_LINE),
					getAttribute(ATTR_NAME),
					getAttribute(ATTR_TYPE),
					getAttribute(ATTR_VALUE),
					getAttribute(ATTR_DBGLINE));

			list.add(var);
			isVisit = true;
		}
		
		if (isVisit) {
			Iterator<String> it = map.keySet().iterator();
			while (it.hasNext()) {
				String thid = it.next();
				ThreadNode thread = dManager.getThread(thid);
				List<Variable> list = map.get(thid);

				if (thread != null) {
					thread.removeAllVariables();
					for (Variable var : list) {
						String vname = var.getName();
						if (vname == null || vname.equals("")) {	// KJH 20110314 s, "empty" 정보에 대한 처리(dbgline, file만 이용함)
							thread.setDbgline(var.getDbgline());
							thread.setFile(var.getFile());
						} else {
							thread.addVariable(var);	// 정상 variable은 thread에 추가
						}	// KJH 20110314 e, "empty" 정보에 대한 처리(dbgline, file만 이용함)
					}
					
					if (!dManager.isMonitoring()) {
						thread.setRunning(false);	// KJH 20110228, breakpoint 시 thread는 정지
						dManager.setSelectedThread(thread);

						updateView();
					}
					else {
						dManager.updateThread(thread, getShell());
					}
				}
			}
		}
		
		clear();
	}

	@Override
	protected boolean isValidCommand(String command) {
		String[] msgtypes = new String[] {"in", "out", "wvar", "gvar", "bvar", "svar", "lvar", "param", "empty"};	// KJH 20110314, "empty" 추가
		
		for (String msgtype : msgtypes) {
			if (msgtype.equals(command)) {
				return true;
			}
		}
		return false;
	}

	public void setView(DebugThreadView view) {
		this.m_view = view;
	}
	
	public void updateView() {
		Display display = getDisplay();
		if (display != null && !display.isDisposed()) {
			display.asyncExec(new Runnable() {
				@Override
				public void run() {
					IWorkbenchPage wp = m_view.getSite().getPage();
					m_view.update();
					DebugManager.getDefault().selectAndReveal(wp, null, true);
				}
			});
		}
	}

}
