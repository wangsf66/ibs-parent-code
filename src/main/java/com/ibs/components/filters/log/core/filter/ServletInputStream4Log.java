package com.ibs.components.filters.log.core.filter;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

/**
 * 
 * @author DougLei
 */
public class ServletInputStream4Log extends ServletInputStream {
	private final ByteArrayInputStream bais; 
	public ServletInputStream4Log(byte[] body) {
		this.bais = new ByteArrayInputStream(body);
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public boolean isReady() {
		return false;
	}

	@Override
	public void setReadListener(ReadListener listener) {
	}

	@Override
	public int read() throws IOException {
		return bais.read();
	}
}
