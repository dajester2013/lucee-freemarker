package org.jdsnet.lucee.freemarker.object;

import org.jdsnet.lucee.freemarker.object.adapter.QueryAdapter;

import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.Version;
import lucee.runtime.type.Query;

public class Wrapper extends DefaultObjectWrapper {

	public Wrapper(Version incompatibleImprovements) {
		super(incompatibleImprovements);
	}
	
	@Override
	public TemplateModel wrap(Object obj) throws TemplateModelException {
		if (obj instanceof Query) {
			return new QueryAdapter((Query) obj, this);
		}
		
		return super.wrap(obj);
	}
	
}
