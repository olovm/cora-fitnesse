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
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

import org.apache.http.client.ClientProtocolException;

import se.uu.ub.cora.httphandler.HttpHandler;
import se.uu.ub.cora.httphandler.HttpHandlerImp;
import se.uu.ub.cora.httphandler.HttpMultiPartUploaderImp;

public class RecordEndpointFixture {
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
	private String baseUrl = "http://localhost:8080/therest/";

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

	// public String resetDependencyProvider() {
	// DependencyProviderForMultipleTestsWorkingTogether.spiderDependencyProvider
	// = new SystemOneDependencyProviderForFitnesse();
	// return "OK";
	// }

	public String testReadRecord() {
		String url = baseUrl + "rest/record/" + type + "/" + id;
		url += "?authToken=" + authToken;

		HttpHandler httpHandler = factorHttpHandler(url);
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

	private HttpHandler factorHttpHandler(String urlString) {
		URL url;
		try {
			url = new URL(urlString);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			return HttpHandlerImp.usingURLConnection(urlConnection);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String testReadIncomingLinks() {
		String url = baseUrl + "rest/record/" + type + "/" + id + "/incomingLinks";
		url += "?authToken=" + authToken;

		HttpHandler httpHandler = factorHttpHandler(url);
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
		String url = baseUrl + "rest/record/" + type;
		url += "?authToken=" + authToken;

		HttpHandler httpHandler = factorHttpHandler(url);
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

	public String testCreateRecord() {
		String url = baseUrl + "rest/record/" + type;
		url += "?authToken=" + authToken;

		HttpHandler httpHandler = factorHttpHandler(url);
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
		String url = baseUrl + "rest/record/" + type + "/" + id;
		url += "?authToken=" + authToken;

		HttpHandler httpHandler = factorHttpHandler(url);
		httpHandler.setRequestMethod("POST");
		httpHandler.setRequestProperty("Accept", "application/uub+record+json");
		httpHandler.setRequestProperty("Content-Type", "application/uub+record+json");
		httpHandler.setOutput(json);

		statusType = Response.Status.fromStatusCode(httpHandler.getResponseCode());

		String responseText = "";
		if (statusType.equals(Response.Status.OK)) {
			responseText = httpHandler.getResponseText();
		} else {
			responseText = httpHandler.getErrorText();
		}
		return responseText;
	}

	public String testDeleteRecord() {
		String url = baseUrl + "rest/record/" + type + "/" + id;
		url += "?authToken=" + authToken;

		HttpHandler httpHandler = factorHttpHandler(url);
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
		// UriInfo uriInfo = new TestUri();
		// setupSpiderInstanceProvider();
		// RecordEndpoint recordEndpoint = new RecordEndpoint(uriInfo);
		//
		// InputStream stream = new ByteArrayInputStream("a
		// string".getBytes(StandardCharsets.UTF_8));
		//
		// FormDataContentDispositionBuilder builder =
		// FormDataContentDisposition
		// .name("multipart;form-data");
		// builder.fileName(fileName);
		// FormDataContentDisposition formDataContentDisposition =
		// builder.build();
		//
		// Response response = recordEndpoint.uploadFile(authToken, authToken,
		// type, id, stream,
		// formDataContentDisposition);
		//
		// statusType = response.getStatusInfo();
		// if (null == response.getEntity()) {
		// return "";
		// }
		// String entity = (String) response.getEntity();
		// streamId = tryToFindStreamId(entity);
		// return entity;

		String responseText = "";
		try {
			String url = baseUrl + "rest/record/" + type + "/" + id + "/master";

			HttpMultiPartUploaderImp httpHandler = factorHttpMultiPartUploader(url);
			// httpHandler.setRequestMethod("POST");
			httpHandler.addHeaderField("Accept", "application/uub+record+json2");
			// httpHandler.addHeaderField("Content-Type",
			// "multipart/form-data");
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseText;
		// BELOW THIS LINE GOT TO SERVER
		// String url = baseUrl + "rest/record/" + type + "/" + id +
		// "/someStreamIdt";
		// url += "?authToken=" + authToken;
		// InputStream fakeStream = new ByteArrayInputStream(
		// "a string".getBytes(StandardCharsets.UTF_8));
		// HttpEntity entity =
		// MultipartEntityBuilder.create().addTextBody("number", "5555555555")
		// .addTextBody("clip",
		// "rickroll").setContentType(ContentType.MULTIPART_FORM_DATA)//
		// .addBinaryBody("upload_file",
		// // new
		// // File(filePath),
		// .addBinaryBody("file", fakeStream,
		// ContentType.create("application/png"),
		// "adele.png")
		// .addTextBody("tos", "agree").build();
		//
		// HttpPost httpPost = new HttpPost(url);
		// httpPost.setEntity(entity);
		// CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		// HttpResponse response = httpClient.execute(httpPost);
		// HttpEntity result = response.getEntity();
		// statusType =
		// Response.Status.fromStatusCode(response.getStatusLine().getStatusCode());
		// return result.getContentType().toString();
		// return response.getStatusLine().getStatusCode();
	}

	private HttpMultiPartUploaderImp factorHttpMultiPartUploader(String urlString) {
		URL url;
		try {
			url = new URL(urlString);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			return HttpMultiPartUploaderImp.usingURLConnection(urlConnection);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
		// UriInfo uriInfo = new TestUri();
		// setupSpiderInstanceProvider();
		// RecordEndpoint recordEndpoint = new RecordEndpoint(uriInfo);
		// Response response = recordEndpoint.downloadFile(authToken, authToken,
		// type, id,
		// resourceName);
		// statusType = response.getStatusInfo();
		// contentLenght = response.getHeaderString("Content-Length");
		//
		// contentDisposition = response.getHeaderString("Content-Disposition");
		// return response.toString();
		String url = baseUrl + "rest/record/" + type + "/" + id + "/" + resourceName;
		url += "?authToken=" + authToken;

		HttpHandler httpHandler = factorHttpHandler(url);
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
