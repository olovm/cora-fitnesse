/*
 * Copyright 2015, 2016 Uppsala University Library
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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

import org.apache.http.client.ClientProtocolException;

import se.uu.ub.cora.httphandler.HttpHandler;
import se.uu.ub.cora.httphandler.HttpMultiPartUploader;

public class RecordEndpointFixture {
	private static final String AUTH_TOKEN = "?authToken=";
	private String id;
	private String type;
	private String json;
	private StatusType statusType;
	private String createdId;
	private String fileName;
	private String streamId;
	private String resourceName;
	private String contentLenght;
	private String contentDisposition;
	private String authToken = "fitnesseAdminToken";
	private String baseUrl = SystemUrl.url + "rest/record/";
	private HttpHandlerFactory factory;

	public RecordEndpointFixture() {
		factory = DependencyProvider.getFactory();
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public void setId(String id) {
		this.id = id;
	}

	public StatusType getStatusType() {
		return statusType;
	}

	public String getCreatedId() {
		return createdId;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getStreamId() {
		return streamId;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}

	public String getContentLength() {
		return contentLenght;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String testReadRecord() {
		String url = baseUrl + type + "/" + id;
		url += AUTH_TOKEN + authToken;

		HttpHandler httpHandler = factory.factorHttpHandler(url);
		httpHandler.setRequestMethod("GET");

		statusType = Response.Status.fromStatusCode(httpHandler.getResponseCode());
		if (statusType.equals(Response.Status.OK)) {
			return httpHandler.getResponseText();
		}
		return httpHandler.getErrorText();
	}

	public String testReadIncomingLinks() {
		String url = baseUrl + type + "/" + id + "/incomingLinks";
		url += AUTH_TOKEN + authToken;

		HttpHandler httpHandler = factory.factorHttpHandler(url);
		httpHandler.setRequestMethod("GET");

		statusType = Response.Status.fromStatusCode(httpHandler.getResponseCode());
		String responseText = "";
		if (statusType.equals(Response.Status.OK)) {
			responseText = httpHandler.getResponseText();
		} else {
			responseText = httpHandler.getErrorText();
		}
		return responseText;
	}

	public String testReadRecordList() {
		String url = baseUrl + type;
		url += AUTH_TOKEN + authToken;

		HttpHandler httpHandler = factory.factorHttpHandler(url);
		httpHandler.setRequestMethod("GET");

		statusType = Response.Status.fromStatusCode(httpHandler.getResponseCode());
		if (statusType.equals(Response.Status.OK)) {
			return httpHandler.getResponseText();
		}
		return httpHandler.getErrorText();
	}

	public String testCreateRecord() {
		String url = baseUrl + type;
		url += AUTH_TOKEN + authToken;

		HttpHandler httpHandler = factory.factorHttpHandler(url);
		httpHandler.setRequestMethod("POST");
		httpHandler.setRequestProperty("Accept", "application/uub+record+json");
		httpHandler.setRequestProperty("Content-Type", "application/uub+record+json");
		httpHandler.setOutput(json);

		statusType = Response.Status.fromStatusCode(httpHandler.getResponseCode());

		String responseText = "";
		if (statusType.equals(Response.Status.CREATED)) {
			responseText = httpHandler.getResponseText();
			createdId = tryToFindCreatedId(responseText);
		} else {
			responseText = httpHandler.getErrorText();
		}
		return responseText;
	}

	private String tryToFindCreatedId(String entity) {
		try {
			return findCreatedId(entity);
		} catch (Exception e) {
			return "";
		}
	}

	private String findCreatedId(String entity) {
		return entity.substring(entity.lastIndexOf("/") + 1, entity.lastIndexOf("\""));
	}

	public String testUpdateRecord() {
		String url = baseUrl + type + "/" + id;
		url += AUTH_TOKEN + authToken;

		HttpHandler httpHandler = factory.factorHttpHandler(url);
		httpHandler.setRequestMethod("POST");
		httpHandler.setRequestProperty("Accept", "application/uub+record+json");
		httpHandler.setRequestProperty("Content-Type", "application/uub+record+json");
		httpHandler.setOutput(json);

		statusType = Response.Status.fromStatusCode(httpHandler.getResponseCode());

		if (statusType.equals(Response.Status.OK)) {
			return httpHandler.getResponseText();
		}
		return httpHandler.getErrorText();
	}

	public String testDeleteRecord() {
		String url = baseUrl + type + "/" + id;
		url += AUTH_TOKEN + authToken;

		HttpHandler httpHandler = factory.factorHttpHandler(url);
		httpHandler.setRequestMethod("DELETE");

		statusType = Response.Status.fromStatusCode(httpHandler.getResponseCode());
		String responseText = "";
		if (statusType.equals(Response.Status.OK)) {
			responseText = httpHandler.getResponseText();
		} else {
			responseText = httpHandler.getErrorText();
		}
		return responseText;
	}

	public String testUpload() throws ClientProtocolException, IOException {
		String responseText = "";
		try {
			String url = baseUrl + type + "/" + id + "/master";

			HttpMultiPartUploader httpHandler = factory.factorHttpMultiPartUploader(url);
			httpHandler.addHeaderField("Accept", "application/uub+record+json2");
			InputStream fakeStream = new ByteArrayInputStream(
					"a string".getBytes(StandardCharsets.UTF_8));
			httpHandler.addFilePart("file", fileName, fakeStream);
			httpHandler.done();
			statusType = Response.Status.fromStatusCode(httpHandler.getResponseCode());

			if (statusType.equals(Response.Status.OK)) {
				responseText = httpHandler.getResponseText();
				streamId = tryToFindStreamId(responseText);
			} else {
				responseText = httpHandler.getErrorText();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return responseText;
	}

	private String tryToFindStreamId(String entity) {
		try {
			return findStreamId(entity);
		} catch (Exception e) {
			return "";
		}
	}

	private String findStreamId(String entity) {
		int streamIdIndex = entity.lastIndexOf("streamId") + 19;
		return entity.substring(streamIdIndex, entity.indexOf("\"", streamIdIndex));
	}

	public String testDownload() {
		String url = baseUrl + type + "/" + id + "/" + resourceName;
		url += AUTH_TOKEN + authToken;

		HttpHandler httpHandler = factory.factorHttpHandler(url);
		httpHandler.setRequestMethod("GET");

		statusType = Response.Status.fromStatusCode(httpHandler.getResponseCode());
		String responseText = "";
		if (statusType.equals(Response.Status.OK)) {
			responseText = httpHandler.getResponseText();
			contentLenght = httpHandler.getHeaderField("Content-Length");
			contentDisposition = httpHandler.getHeaderField("Content-Disposition");
		} else {
			responseText = httpHandler.getErrorText();
		}
		return responseText;
	}

}
