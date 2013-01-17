package org.nsdcache.example;

public class CacheObject {
	
	long pushtime = 0;

	int usetimes = 0;

	Object value = null;

	CacheObject(Object value) {
		pushtime = System.currentTimeMillis();
		usetimes = 0;
		this.value = value;
	}

	/**
	 * @return Returns the pushtime.
	 */
	public final long getPushtime() {
		return pushtime;
	}

	/**
	 * @return Returns the usetimes.
	 */
	public final int getUsetimes() {
		return usetimes;
	}

	/**
	 * @param usetimes
	 *            The usetimes to set.
	 */
	public final void setUsetimes(int usetimes) {
		this.usetimes = usetimes;
	}

	/**
	 * @return Returns the value.
	 */
	public final Object getValue() {
		return value;
	}

	/**
	 * @param value
	 *            The value to set.
	 */
	public final void setValue(Object value) {
		this.value = value;
	}

	public String toString() {
		StringBuffer strBf = new StringBuffer(10);
		strBf.append("value=" + value + '\n');
		strBf.append("pushtime=" + pushtime + '\n');
		strBf.append("usetimes=" + usetimes + '\n');
		return strBf.toString();
	}
}