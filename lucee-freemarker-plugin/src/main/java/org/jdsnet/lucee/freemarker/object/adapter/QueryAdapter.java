package org.jdsnet.lucee.freemarker.object.adapter;

import java.util.HashMap;
import java.util.Map;

import freemarker.template.AdapterTemplateModel;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateSequenceModel;
import freemarker.template.WrappingTemplateModel;
import lucee.runtime.exp.PageException;
import lucee.runtime.type.Collection.Key;
import lucee.runtime.type.Query;

public class QueryAdapter extends WrappingTemplateModel implements 
	 TemplateSequenceModel
	,TemplateModel
	,AdapterTemplateModel
	,TemplateHashModel
{

	private final Query query;
	
	public QueryAdapter(Query q, ObjectWrapper ow) {
		super(ow);
		this.query = q;
	}
	
	@Override
	public TemplateModel get(int row) throws TemplateModelException {
		Key[] cols = query.getColumnNames();
		Map<String, Object> rowMap = new HashMap<>();
		
		try {
			for (Key col : cols) {
				rowMap.put(col.getString(), query.getAt(col, row+1));
			}
		} catch (PageException e) {
			throw new TemplateModelException(e);
		}
		
		return wrap(rowMap);
	}

	@Override
	public int size() throws TemplateModelException {
		return query.getRecordcount();
	}

	@Override
	public Object getAdaptedObject(Class hint) {
		return query;
	}

	@Override
	public TemplateModel get(String key) throws TemplateModelException {
		try {
			return wrap(query.get(key));
		} catch (PageException e) {
			return null;
		}
	}

	@Override
	public boolean isEmpty() throws TemplateModelException {
		return query.isEmpty();
	}

}
