package org.jdsnet.lucee.freemarker;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jdsnet.lucee.freemarker.object.Wrapper;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
import lucee.loader.engine.CFMLEngine;
import lucee.runtime.config.Config;
import lucee.runtime.page.engine.PageEngine;
import lucee.runtime.page.engine.PageFactory;

public class FreeMarkerPageEngine extends PageEngine {
	
	public static final Version FREEMARKER_TEMPLATE_VERSION = Configuration.VERSION_2_3_23;
	
	private Map<String, Configuration> configMap;
	
	public FreeMarkerPageEngine(Config config) {
		this.configMap = new HashMap<String, Configuration>();
	}
	
	public Configuration getConfiguration(String path, boolean isArchive) throws IOException {
		if (!this.configMap.containsKey(path)) {
			Configuration cfg = new Configuration(FREEMARKER_TEMPLATE_VERSION);
			
			cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
			cfg.setObjectWrapper(new Wrapper(FREEMARKER_TEMPLATE_VERSION));
			
			if (!isArchive)
				cfg.setDirectoryForTemplateLoading(new File(path));
			else
				cfg.setTemplateLoader(new ArchiveTemplateLoader(path));
			
			configMap.put(path, cfg);
		}
		
		return this.configMap.get(path);
	}
	
	@Override
	public PageFactory getPageFactory() {
		return new FreeMarkerPageFactory(this);
	}
	
	@Override
	public int getDialect() {
		return CFMLEngine.DIALECT_BOTH;
	}

}
