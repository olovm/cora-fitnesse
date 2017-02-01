package se.uu.ub.cora.fitnesse;

import static org.testng.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AppTokenEndpointFixtureTest {
	private HttpHandlerFactorySpy httpHandlerFactorySpy;
	private AppTokenEndpointFixture fixture;

	@BeforeMethod
	public void setUp() {
		SystemUrl.setAppTokenVerifierUrl("http://localhost:8080/apptokenverifier/");
		DependencyProvider
				.setHttpHandlerFactoryClassName("se.uu.ub.cora.fitnesse.HttpHandlerFactorySpy");
		httpHandlerFactorySpy = (HttpHandlerFactorySpy) DependencyProvider.getFactory();
		fixture = new AppTokenEndpointFixture();
	}

	@Test
	public void testGetAuthTokenForAppToken() {
		httpHandlerFactorySpy.setResponseCode(201);
		fixture.setUserId("someUserId");
		fixture.setAppToken("02a89fd5-c768-4209-9ecc-d80bd793b01e");
		String json = fixture.getAuthTokenForAppToken();
		HttpHandlerSpy httpHandlerSpy = httpHandlerFactorySpy.httpHandlerSpy;
		assertEquals(httpHandlerSpy.requestMetod, "POST");
		assertEquals(httpHandlerSpy.outputString, "02a89fd5-c768-4209-9ecc-d80bd793b01e");
		// assertEquals(httpHandlerSpy.requestProperties.get("Accept"),
		// "application/uub+record+json");
		// assertEquals(httpHandlerSpy.requestProperties.get("Content-Type"),
		// "application/uub+record+json");
		// assertEquals(httpHandlerSpy.requestProperties.size(), 2);
		assertEquals(httpHandlerFactorySpy.urlString,
				"http://localhost:8080/apptokenverifier/rest/apptoken/someUserId");
		assertEquals(json,
				"{\"children\":[{\"name\":\"id\",\"value\":\"a1acff95-5849-4e10-9ee9-4b192aef17fd\"}"
						+ ",{\"name\":\"validForNoSeconds\",\"value\":\"600\"}],\"name\":\"authToken\"}");
		assertEquals(fixture.getAuthToken(), "a1acff95-5849-4e10-9ee9-4b192aef17fd");
		assertEquals(fixture.getStatusType(), Response.Status.CREATED);
	}

	@Test
	public void testCreateRecordNotOk() {
		httpHandlerFactorySpy.changeFactoryToFactorInvalidHttpHandlers();
		assertEquals(fixture.getAuthTokenForAppToken(), "bad things happend");
	}
}
