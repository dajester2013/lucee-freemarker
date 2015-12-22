package org.jdsnet.lucee.freemarker;

import java.io.IOException;

import freemarker.template.Configuration;
import lucee.runtime.Mapping;
import lucee.runtime.Page;
import lucee.runtime.PageContext;
import lucee.runtime.PageSource;
import lucee.runtime.exp.PageException;
import lucee.runtime.template.TemplatePageFactory;

public class FreeMarkerPageFactory implements TemplatePageFactory {
	
	private FreeMarkerTemplateEngine templateEngine;
	
	public FreeMarkerPageFactory(FreeMarkerTemplateEngine te) {
		templateEngine = te;
	}
	
	@Override
	public Page getPage(PageContext pc, PageSource ps, boolean forceReload, Page defaultValue) throws PageException {
		Mapping m = ps.getMapping();
		
		boolean isArchive = m.hasArchive() && (!m.isPhysicalFirst() || !m.hasPhysical());
		
		String root = isArchive 
						? m.getArchive().getAbsolutePath() 
						: m.getPhysical().getAbsolutePath()
						;
		
		try {
			Configuration cfg = templateEngine.getConfiguration(root, isArchive);
			FreeMarkerPage p = new FreeMarkerPage(cfg.getTemplate(ps.getRealpath()));
			p.setPageSource(ps);
			return p;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
