package se.uu.ub.cora.fitnesse;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RecordEndpointFixtureTest {
	private RecordEndpointFixture fixture;
	private HttpHandlerFactorySpy httpHandlerFactorySpy;

	@BeforeMethod
	public void setUp() {
		SystemUrl.url = "http://localhost:8080/therest/";
		DependencyProvider
				.setHttpHandlerFactoryClassName("se.uu.ub.cora.fitnesse.HttpHandlerFactorySpy");
		httpHandlerFactorySpy = (HttpHandlerFactorySpy) DependencyProvider.getFactory();
		fixture = new RecordEndpointFixture();
	}

	@Test
	public void testReadRecordUrlIsBuilt() {
		fixture.setType("someType");
		fixture.setId("someId");
		fixture.setAuthToken("someToken");
		fixture.testReadRecord();
		assertEquals(httpHandlerFactorySpy.urlString,
				"http://localhost:8080/therest/rest/record/someType/someId?authToken=someToken");
	}

	@Test
	public void testReadRecordOk() {
		fixture.setType("someType");
		fixture.setId("someId");
		fixture.setAuthToken("someToken");

		assertEquals(fixture.testReadRecord(), "Everything ok");
		assertEquals(httpHandlerFactorySpy.urlString,
				"http://localhost:8080/therest/rest/record/someType/someId?authToken=someToken");
	}

	@Test
	public void testReadRecordNotOk() {
		httpHandlerFactorySpy.changeFactoryToFactorInvalidHttpHandlers();
		assertEquals(fixture.testReadRecord(), "bad things happend");
	}
}
