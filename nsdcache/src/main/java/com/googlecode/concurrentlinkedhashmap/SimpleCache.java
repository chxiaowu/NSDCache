package com.googlecode.concurrentlinkedhashmap;
import java.util.HashMap;
import java.util.Map;

public class SimpleCache {

	@SuppressWarnings("rawtypes")
	private final Map cache = new HashMap();

	public Object load(String objectName) {
		return objectName;
	}

	public void clearCache() {
		synchronized (cache) {
			cache.clear();
		}
	}

	@SuppressWarnings("unchecked")
	public Object getObject(String objectName) {
		Object o;
		synchronized (cache) {
			o = cache.get(objectName);
			if (o == null) {
				o = load(objectName);
				cache.put(objectName, o);
			}
		}
		return o;
	}
}
