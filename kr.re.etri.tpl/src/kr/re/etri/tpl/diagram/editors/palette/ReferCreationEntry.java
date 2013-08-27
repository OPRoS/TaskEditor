package kr.re.etri.tpl.diagram.editors.palette;

import kr.re.etri.tpl.diagram.editors.tools.ReferCreationTool;

import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.jface.resource.ImageDescriptor;

public class ReferCreationEntry extends
		CombinedTemplateCreationEntry {

	public ReferCreationEntry(String label, String shortDesc, Object template,
			CreationFactory factory, ImageDescriptor iconSmall,
			ImageDescriptor iconLarge) {
		super(label, shortDesc, template, factory, iconSmall, iconLarge);
		setToolClass(ReferCreationTool.class);
	}
}
