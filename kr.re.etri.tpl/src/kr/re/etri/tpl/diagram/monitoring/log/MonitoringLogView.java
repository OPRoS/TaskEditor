package kr.re.etri.tpl.diagram.monitoring.log;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.ViewPart;

public class MonitoringLogView extends ViewPart{
	private static Logger logger = Logger.getLogger(MonitoringLogView.class);
	public static String VIEW_ID = "kr.re.etri.tpl.diagram.monitoring.MonitoringLogView";
	
	private TableViewer viewer;	
	private List<MonitoringLog> m_logs;
	
	static final String NUM_COLUMN = "Number";
	static final String TIME_COLUMN = "Time";
	static final String TARGET_COLUMN ="Target";
	static final String DATA_COLUMN = "Data";
	static final String NAME_COLUMN = "Name";
	static final String TYPE_COLUMN = "Type";
	static final String VALUE_COLUMN = "Value";

	private String[] columnNames = new String[] {  
		NUM_COLUMN,
		TIME_COLUMN,
		TARGET_COLUMN,
		DATA_COLUMN,
		NAME_COLUMN,
		TYPE_COLUMN,
		VALUE_COLUMN
	};
	
	@Override
	public void createPartControl(Composite parent) {
		m_logs = new ArrayList<MonitoringLog>();
		createViewer(parent);
	}
	public TableViewer getTableViewer(){
		return viewer;
	}
	public void add(Object element){
		if(viewer == null){
			logger.info("Viewer was not created.");
			return;
		}
		MonitoringLog log = (MonitoringLog)element;
		
		m_logs.add(log);
		viewer.add(log);
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}
	
	private void createViewer(Composite parent) {
		Table t = createTable(parent);
		viewer = new TableViewer(t);
		
		viewer.setColumnProperties(columnNames);
		
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setLabelProvider(new MonitoringLogLabelProvider());
		viewer.setInput(m_logs);
	}
	
	private Table createTable(Composite parent){
		Table table = new Table(parent,  SWT.SINGLE | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.HIDE_SELECTION );

		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn column = new TableColumn(table, SWT.CENTER, 0);
		column.setText(MonitoringLogView.NUM_COLUMN);
		column.setWidth(70);
		
		column= new TableColumn(table,SWT.CENTER,1);
		column.setText(MonitoringLogView.TIME_COLUMN);
		column.setWidth(100);
		
		column= new TableColumn(table,SWT.LEFT,2);
		column.setText(MonitoringLogView.TARGET_COLUMN);
		column.setWidth(150);
		
		column= new TableColumn(table,SWT.CENTER,3);
		column.setText(MonitoringLogView.DATA_COLUMN);
		column.setWidth(50);		
				
		column= new TableColumn(table,SWT.LEFT,4);
		column.setText(MonitoringLogView.NAME_COLUMN);
		column.setWidth(100);
		
		column= new TableColumn(table,SWT.CENTER,5);
		column.setText(MonitoringLogView.TYPE_COLUMN);
		column.setWidth(80);		
		
		column= new TableColumn(table,SWT.CENTER,6);
		column.setText(MonitoringLogView.VALUE_COLUMN);
		column.setWidth(100);		
		
		return table;
	}
	
}
