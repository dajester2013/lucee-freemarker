package org.jdsnet.lucee.freemarker;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
import lucee.runtime.config.Config;
import lucee.runtime.template.TemplateEngine;
import lucee.runtime.template.TemplatePageFactory;

public class FreeMarkerTemplateEngine extends TemplateEngine {
	
	public static final Version FREEMARKER_TEMPLATE_VERSION = Configuration.VERSION_2_3_23;
	
	private Map<String, Configuration> configMap;
	
	public FreeMarkerTemplateEngine(Config config) {
		this.configMap = new HashMap<String, Configuration>();
	}
	
	public Configuration getConfiguration(String path, boolean isArchive) throws IOException {
		if (!this.configMap.containsKey(path)) {
			Configuration cfg = new Configuration(FREEMARKER_TEMPLATE_VERSION);
			
			cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
			
			System.out.println("create config for path " + path);
			
			if (!isArchive)
				cfg.setDirectoryForTemplateLoading(new File(path));
			else
				cfg.setTemplateLoader(new ArchiveTemplateLoader(path));
			// TODO: actually configure the thing
			
			configMap.put(path, cfg);
		}
		
		return this.configMap.get(path);
	}
	
	@Override
	public TemplatePageFactory getPageFactory() {
		return new FreeMarkerPageFactory(this);
	}

}
