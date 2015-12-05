package org.jdsnet.lucee.freemarker;

import java.net.MalformedURLException;
import java.net.URL;

import freemarker.cache.URLTemplateLoader;

public class ArchiveTemplateLoader extends URLTemplateLoader {

	private String archivePath;
	
	public ArchiveTemplateLoader(String archivePath) {
		this.archivePath = archivePath;
	}
	
	@Override
	protected URL getURL(String template) {
		System.err.println("trying to load flt " + template);
		URL url = null;
		try {
			url = new URL("jar:file:///" + archivePath + "!/" + "index.dummy");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}

}
