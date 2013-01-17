package org.nsdcache.example;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
/**
 * <url>http://code.google.com/p/testcq/wiki/LRU</url>
 */
@SuppressWarnings("rawtypes")
public class LRU {
	
	protected HashMap lruCache = new HashMap(2);

	protected int MAX_INTEGER_NUMBER = 2147483647;

	protected int max_object_num = 1000;

	public LRU() {
	}

	public LRU(int maxObjectNum) {
		max_object_num = maxObjectNum;
	}

	/**
	 * 增加对象到缓存中
	 * 
	 * @param key
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	public Object put(Object key, Object value) {
		CacheObject newValue = new CacheObject(value);
		if (lruCache.size() >= max_object_num) {
			removeLease();
		}
		return lruCache.put(key, newValue);
	}

	/**
	 * 使用key来获取对象
	 * 
	 * @param key
	 * @return
	 */
	public Object get(Object key) {
		CacheObject object = (CacheObject) lruCache.get(key);
		if (object == null) {
			return null;
		}

		// 根据LRU算法原则，将命中的对象计算器0，将其他对象的计算值加1
		Set set = lruCache.keySet();
		Iterator iter = set.iterator();
		Object keyObject = null;
		CacheObject cacheObject = null;
		while (iter.hasNext()) {
			keyObject = iter.next();
			cacheObject = (CacheObject) lruCache.get(keyObject);
			cacheObject.setUsetimes(cacheObject.getUsetimes() + 1);
		}
		object.setUsetimes(0);

		return object != null ? object.getValue() : null;
	}

	public boolean containsKey(Object key) {
		return lruCache.containsKey(key);
	}

	public void clear() {
		lruCache.clear();
	}

	public int size() {
		return lruCache.size();
	}

	public boolean isEmpty() {
		return lruCache.isEmpty();
	}

	public boolean containsValue(Object value) {
		return lruCache.containsKey(value);
	}

	/**
	 * 移除使用最少的对象
	 */
	public void removeLease() {
		Object leaseUseObjectKey = null;
		int usetimes = 0;

		Set set = lruCache.keySet();
		Iterator iter = set.iterator();
		while (iter.hasNext()) {
			Object keyObject = iter.next();
			CacheObject object = (CacheObject) lruCache.get(keyObject);
			if (object.getUsetimes() > usetimes) {
				usetimes = object.getUsetimes();
				leaseUseObjectKey = keyObject;
			}
		}
		lruCache.remove(leaseUseObjectKey);
	}

	public Set keySet() {
		return lruCache.keySet();
	}

	/**
	 * 移除使用最频繁的对象
	 */
	public void removeMost() {
		Object leaseUseObjectKey = null;
		int usetimes = MAX_INTEGER_NUMBER;

		Set set = lruCache.keySet();
		Iterator iter = set.iterator();
		while (iter.hasNext()) {
			Object keyObject = iter.next();
			CacheObject object = (CacheObject) lruCache.get(keyObject);
			if (object.getUsetimes() < usetimes) {
				usetimes = object.getUsetimes();
				leaseUseObjectKey = keyObject;
			}
		}
		lruCache.remove(leaseUseObjectKey);
	}

	/**
	 * 移除最早置入缓存的对象
	 */
	public void removeEarly() {
		Object leaseUseObjectKey = null;
		long time = System.currentTimeMillis() + 365 * 24 * 60 * 60 * 1000;

		Set set = lruCache.keySet();
		Iterator iter = set.iterator();
		while (iter.hasNext()) {
			Object keyObject = iter.next();
			CacheObject object = (CacheObject) lruCache.get(keyObject);
			if (object.getPushtime() < time) {
				time = object.getPushtime();
				leaseUseObjectKey = keyObject;
			}
		}
		lruCache.remove(leaseUseObjectKey);
	}

	/**
	 * 移除最迟放入的对象
	 */
	public void removeLater() {
		Object leaseUseObjectKey = null;
		long time = -1;

		Set set = lruCache.keySet();
		Iterator iter = set.iterator();
		while (iter.hasNext()) {
			Object keyObject = iter.next();
			CacheObject object = (CacheObject) lruCache.get(keyObject);
			if (object.getPushtime() > time) {
				time = object.getPushtime();
				leaseUseObjectKey = keyObject;
			}
		}
		lruCache.remove(leaseUseObjectKey);
	}

	/**
	 * 删除某个键值及对应对象
	 * 
	 * @param key
	 */
	public void remove(Object key) {
		lruCache.remove(key);
	}

	public static void main(String[] args) {
		LRU lru = new LRU(4);
		lru.put("a", "The A String");
		lru.put("b", "The B String");
		lru.put("d", "The D String");
		lru.put("c", "The C String");

		System.out.println(lru.toString());

		lru.get("a");
		lru.get("b");
		lru.get("d");
		lru.get("a");
		lru.get("b");
		lru.get("d");
		lru.put("e", "The E String");
		lru.get("e");
		lru.get("e");
		lru.get("e");
		lru.get("e");
		System.out.println(lru.toString());
	}

	public String toString() {
		StringBuffer strBf = new StringBuffer(10);
		Set set1 = lruCache.keySet();
		Iterator iter1 = set1.iterator();
		while (iter1.hasNext()) {
			Object key = iter1.next();
			strBf.append(key + "=");
			strBf.append(lruCache.get(key));
			strBf.append("\n");
		}
		return strBf.toString();
	}

}
