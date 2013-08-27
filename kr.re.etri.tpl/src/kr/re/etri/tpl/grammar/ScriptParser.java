package kr.re.etri.tpl.grammar;


import java.io.IOException;
import java.io.InputStream;

import kr.re.etri.tpl.diagram.listener.IErrorListener;
import kr.re.etri.tpl.diagram.util.MarkerLogger;
import kr.re.etri.tpl.diagram.util.MarkerProvider;
import kr.re.etri.tpl.diagram.util.TPLErrorLogger;
import kr.re.etri.tpl.diagram.util.TPLUtil;
import kr.re.etri.tpl.grammar.Notify.Type;
import kr.re.etri.tpl.taskmodel.IncludedElement;
import kr.re.etri.tpl.taskmodel.ModelDiagram;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;

public class ScriptParser extends MarkerProvider implements INotifyListener {
	private IFile m_sourceFile;
	private ModelDiagram model;
	
	public ScriptParser(IFile file) {
		m_sourceFile = file;
	}
	
	public IResource getResource(IResource resource, String path) {
		IContainer parent = null;
		Path resPath = new Path(path);
		if(resPath.isAbsolute()) {
			IWorkspace ws = ResourcesPlugin.getWorkspace();
			IWorkspaceRoot wsRoot = ws.getRoot();
			IResource res = wsRoot.findMember(path);
			return res;
		}
		
		if(resource instanceof IProject) {
			parent = (IContainer)resource;
		}
		else if(resource instanceof IFolder) {
			parent = (IContainer)resource;
		}
		else if(resource instanceof IFile) {
			parent = resource.getParent();
		}
		else {
			return null;
		}
		IResource res = parent.findMember(path);

		return res;
	}

	public void notifyChanged(Notify notify) {
		if(Type.ADD == notify.getType()) {
			if(((Object)TaskModelPackage.MODEL_DIAGRAM__INCLUDE_ITEMS) == notify.getData()) {
				Object newItem = notify.getNewValue();
				if((newItem instanceof IncludedElement) == false) {
					return;
				}

//				ModelDiagram modelDiagram = (ModelDiagram)notify.getNotifier();
				IncludedElement incElmt = (IncludedElement)newItem;

				// KJH 20110902, container: parent->project
				if (m_sourceFile != null) {
					IContainer container = m_sourceFile.getProject();
					String incPath = incElmt.getIncludePath();
					IResource res = getResource(container, incPath);
					if(res instanceof IFile) {
						ScriptParser parser = new ScriptParser((IFile)res);
						MarkerLogger markLogger = new MarkerLogger((IFile)res, MarkerLogger.TYPE_SCRIPT);
						markLogger.clearProblem();

						parser.addErrorListener(markLogger);

						ModelDiagram incModel = parser.parse();
						TPLUtil.mergeModel(incModel, incElmt);

						String fullincPath = ((IFile)res).getFullPath().toString();
						if (fullincPath.startsWith("/")) {	// KJH 20110905
							fullincPath = fullincPath.substring(1);
						}
						incElmt.setIncludePath(fullincPath.toString());
					}
				}
			}
			else if(((Object)TaskModelPackage.INCLUDED_ELEMENT__ITEMS) == notify.getData()) {
				Object newItem = notify.getNewValue();
				if((newItem instanceof IncludedElement) == false) {
					return;
				}

//				IncludedElement parentIncElmt = (IncludedElement)notify.getNotifier();
				IncludedElement incElmt = (IncludedElement)newItem;

				if (m_sourceFile != null) {
					IContainer container = m_sourceFile.getParent();
					String incPath = incElmt.getIncludePath();
					IResource res = getResource(container, incPath);
					if(res instanceof IFile) {
						ScriptParser parser = new ScriptParser((IFile)res);
						MarkerLogger markLogger = new MarkerLogger((IFile)res, MarkerLogger.TYPE_SCRIPT);
						markLogger.clearProblem();

						parser.addErrorListener(markLogger);

						ModelDiagram incModel = parser.parse();
						TPLUtil.mergeModel(incModel, incElmt);

						String fullincPath = ((IFile)res).getFullPath().toString();
						if (fullincPath.startsWith("/")) {
							fullincPath = fullincPath.substring(1);
						}
						incElmt.setIncludePath(fullincPath.toString());
					}
				}
			}
		}
	}
	
	/**
	 * ��ũ��Ʈ ������ �Ľ��Ͽ� IncludedElement�� �����Ѵ�.
	 * @param stream ��ũ��Ʈ ���� ����
	 * @return �Ľ̵� ��
	 */
	private ModelDiagram parseModel(InputStream stream, IErrorListener listener) throws RecognitionException, IOException {

		ANTLRInputStream antlrStream = new ANTLRInputStream(stream);
		RTMLexer lexer = new RTMLexer(antlrStream);
		lexer.setErrorListener(listener);

		CommonTokenStream tokens = new CommonTokenStream(lexer);
		RTMParser parser = new RTMParser(tokens);
		parser.setErrorListener(listener);

		// ���� Tracer�� ����Ͽ� �Ϻ� �� ������ �����ϵ��� �Ѵ�.
		RTMTokenListener rtmTracer = new RTMTokenListener(this);
		parser.addTraceListener(rtmTracer);

		RFSMParser.rfsm_return def = null;
		// Parsing�� ����
		def = parser.rfsm();

		parser.removeTraceListener(rtmTracer);

		// Parsing�� ���Ͽ� ������ ��
		model = (ModelDiagram)rtmTracer.getCurrentElement();
		
		// �ǹ� �м� �ܰ�� ���� �޽����� ����� Logger
//		ProblemLogger logListener = new ProblemLogger();
//		logListener.addLogListener(listener);

		// �Ľ� Ʈ���� ��ȸ�Ͽ� ������ ��(Connection, ����, ���๮ ��)�� �����ϵ��� �Ѵ�.
		// ModelDiagram�� ���ǹ�, ���๮���� �м��� �� Enum, Model, Action, Task, Worker�� ����
		// �ɺ� ���̺�� �̿�ȴ�.
		try {
			RTMTokenVerifier.verify((Tree)def.getTree(), tokens, model, true, this);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
//		logListener.removeLogListener(listener);
		
		return model;

	}

	public ModelDiagram parse(InputStream stream) {

		try {
			return parseModel(stream, this);
		} catch (RecognitionException e) {
			TPLErrorLogger.error(e.getMessage(), e);
			e.printStackTrace();
		} catch(IOException e) {
			TPLErrorLogger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		
		return null;
	}

	public ModelDiagram parse() {

		if(m_sourceFile == null) {
			return null;
		}
		if (m_sourceFile.exists() == false) {
			return null;
		}
		
		try {
			return parseModel(m_sourceFile.getContents(), this);
		} catch (RecognitionException e) {
			TPLErrorLogger.error(e.getMessage(), e);
			e.printStackTrace();
		} catch(IOException e) {
			TPLErrorLogger.error(e.getMessage(), e);
			e.printStackTrace();
		} catch (CoreException e) {
			TPLErrorLogger.error(e.getMessage(), e);
			e.printStackTrace();
		}

		return null;
	}
	
	public ModelDiagram getModel() {
		return model;
	}
	
}
