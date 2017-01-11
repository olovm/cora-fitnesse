/*
 * Copyright 2017 Uppsala University Library
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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import se.uu.ub.cora.httphandler.HttpMultiPartUploader;

public class HttpMultiPartUploaderSpy implements HttpMultiPartUploader {

	public HttpURLConnection urlConnection;
	public int responseCode = 200;
	public Map<String, String> headerFields = new HashMap<>();
	public boolean doneIsCalled = false;
	public String fieldName;
	public String fileName;
	public InputStream stream;

	public HttpMultiPartUploaderSpy(HttpURLConnection urlConnection) {
		this.urlConnection = urlConnection;
	}

	public static HttpMultiPartUploaderSpy usingURLConnection(HttpURLConnection urlConnection) {
		return new HttpMultiPartUploaderSpy(urlConnection);
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
	public String getErrorText() {
		return "bad things happend";
	}

	@Override
	public void addFormField(String name, String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addFilePart(String fieldName, String fileName, InputStream stream)
			throws IOException {
		this.fieldName = fieldName;
		this.fileName = fileName;
		this.stream = stream;

	}

	@Override
	public void addHeaderField(String name, String value) {
		headerFields.put(name, value);
	}

	@Override
	public void done() throws IOException {
		// TODO Auto-generated method stub
		doneIsCalled = true;
	}

}
