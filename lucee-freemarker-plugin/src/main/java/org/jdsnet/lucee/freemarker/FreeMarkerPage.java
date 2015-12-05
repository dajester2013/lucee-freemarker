package org.jdsnet.lucee.freemarker;

import freemarker.template.Template;
import lucee.runtime.Page;
import lucee.runtime.PageContext;

public class FreeMarkerPage extends Page {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3637475040652819685L;
	
	private Template template;
	
	public FreeMarkerPage(Template tpl) {
		this.template = tpl;
	}
	
	@Override
	public Object call(PageContext pc) throws Throwable {
		this.template.process(pc.us(), pc.getOut());
		return null;
	}

}
