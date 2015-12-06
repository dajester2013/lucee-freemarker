package org.jdsnet.lucee.freemarker;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import lucee.runtime.PageContext;
import lucee.runtime.exp.PageException;
import lucee.runtime.type.scope.Scope;

public class FreeMarkerScope implements Map<String, Object> {
	
	PageContext pc;
	
	private enum scopeNames {
		local,request,url,form,arguments,cgi,client,cluster,cookie,server,session,variables,application;
	}
	
	public FreeMarkerScope(PageContext pc) {
		this.pc = pc;
	}
	
	public int size() {
		return pc.us().size();
	}

	public boolean isEmpty() {
		return pc.us().isEmpty();
	}

	@SuppressWarnings("deprecation")
	public boolean containsKey(String key) {
		boolean found = false;
		
		switch(scopeNames.valueOf(key.toLowerCase())) {
			case local:
			case request:
			case url:
			case form:
			case arguments:
			case cgi:
			case client:
			case cluster:
			case cookie:
			case server:
			case session:
			case variables:

			case application:
				found = true;
			
			default:
				found = pc.us().containsKey(key);
		}
		
		return found;
	}

	public boolean containsValue(Object value) {
		boolean found = false;
		
		found = 	pc.us().containsValue(value)
				||	pc.localScope().containsValue(value)
				||	pc.requestScope().containsValue(value)
				||	pc.urlScope().containsValue(value)
				||	pc.formScope().containsValue(value)
				||	pc.argumentsScope().containsValue(value)
				||	pc.cgiScope().containsValue(value)
				||	pc.cookieScope().containsValue(value)
				||	pc.variablesScope().containsValue(value)
				;
		
		if (found)
			return true;

		try {
			if (pc.clientScope().containsValue(value))
				return true;
		} catch(Exception e) {}

		try {
			if (pc.clusterScope().containsValue(value))
				return true;
		} catch(Exception e) {}

		try {
			if (pc.serverScope().containsValue(value))
				return true;
		} catch(Exception e) {}

		try {
			if (pc.sessionScope().containsValue(value))
				return true;
		} catch(Exception e) {}

		try {
			if (pc.applicationScope().containsValue(value))
				return true;
		} catch(Exception e) {}
		
		return false;
	}

	@SuppressWarnings("deprecation")
	public Object get(String key) {
		Object value = null;
		
		try {
			switch(key.toLowerCase()) {
				case "local":		value = pc.localScope();		break;
				case "request":		value = pc.requestScope();		break;
				case "url":			value = pc.urlScope();			break;
				case "form":		value = pc.formScope();			break;
				
				case "arguments":	value = pc.argumentsScope();	break;
				case "cgi":			value = pc.cgiScope();			break;
				case "client":		value = pc.clientScope();		break;
				case "cluster":		value = pc.clusterScope();		break;
				case "cookie":		value = pc.cookieScope();		break;
				case "server":		value = pc.serverScope();		break;
				case "session":		value = pc.sessionScope();		break;
				case "variables":	value = pc.variablesScope();	break;

				case "application":	value = pc.applicationScope();	break;
				
				default:			value = pc.us().get(key);		break;
			}
		} catch (PageException e) {
			value = null;
		}
		
		return value;
	}

	public Object put(String key, Object value) {
		return pc.us().put(key, value);
	}

	public Object remove(Object key) {
		return pc.us().remove(key);
	}

	public void putAll(Map<? extends String, ? extends Object> m) {
		pc.us().putAll(m);
	}

	public void clear() {
		pc.us().clear();
	}

	public Set<String> keySet() {
		return pc.us().keySet();
	}

	public Collection<Object> values() {
		return pc.us().values();
	}

	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		return pc.us().entrySet();
	}

	@Override
	public boolean containsKey(Object key) {
		return this.containsKey((String)key);
	}

	@Override
	public Object get(Object key) {
		// could get a single char key as a Character instance... this is not going to be a scope,
		// so look it up in the undefined scope.
		// otherwise, cast to string and look it up as above.
		if (key instanceof String)
			return this.get((String) key);
		return pc.us().get(key);
	}
	
}
