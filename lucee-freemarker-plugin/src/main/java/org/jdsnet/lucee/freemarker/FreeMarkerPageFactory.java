package org.jdsnet.lucee.freemarker;

import java.io.IOException;

import freemarker.template.Configuration;
import lucee.runtime.Mapping;
import lucee.runtime.Page;
import lucee.runtime.PageSource;
import lucee.runtime.template.TemplatePageFactory;

public class FreeMarkerPageFactory implements TemplatePageFactory {
	
	private FreeMarkerTemplateEngine templateEngine;
	
	public FreeMarkerPageFactory(FreeMarkerTemplateEngine te) {
		templateEngine = te;
	}
	
	public Page getPage(PageSource ps) {
		Mapping m = ps.getMapping();
		
		boolean isArchive = m.hasArchive() && (!m.isPhysicalFirst() || !m.hasPhysical());
		
		String root = isArchive 
						? m.getArchive().getAbsolutePath() 
						: m.getPhysical().getAbsolutePath()
						;
		
		try {
			Configuration cfg = templateEngine.getConfiguration(root, isArchive);
			return new FreeMarkerPage(cfg.getTemplate(ps.getRealpath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
