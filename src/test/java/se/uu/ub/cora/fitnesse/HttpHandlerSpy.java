/*
 * Copyright 2016 Uppsala University Library
 *
 * This file is part of Cora.
 *
 *     Cora is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Cora is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Cora.  If not, see <http://www.gnu.org/licenses/>.
 */

package se.uu.ub.cora.fitnesse;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import se.uu.ub.cora.httphandler.HttpHandler;

public class HttpHandlerSpy implements HttpHandler {

	public HttpURLConnection httpUrlConnection;
	public String requestMetod;
	public String outputString;
	public Map<String, String> requestProperties = new HashMap<>();
	public InputStream stream;
	public int responseCode = 200;

	private HttpHandlerSpy(HttpURLConnection httpUrlConnection) {
		this.httpUrlConnection = httpUrlConnection;
	}

	public static HttpHandlerSpy usingURLConnection(HttpURLConnection httpUrlConnection) {
		return new HttpHandlerSpy(httpUrlConnection);
	}

	@Override
	public void setRequestMethod(String requestMetod) {
		this.requestMetod = requestMetod;
	}

	@Override
	public String getResponseText() {
		return "Everything ok";
	}

	@Override
	public int getResponseCode() {
		return responseCode;
	}

	@Override
	public void setOutput(String outputString) {
		this.outputString = outputString;

	}

	@Override
	public void setRequestProperty(String key, String value) {
		requestProperties.put(key, value);
	}

	@Override
	public String getErrorText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStreamOutput(InputStream stream) {
		this.stream = stream;
	}

	@Override
	public String getHeaderField(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
