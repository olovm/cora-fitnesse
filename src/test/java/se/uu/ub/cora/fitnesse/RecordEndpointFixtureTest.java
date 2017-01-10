package se.uu.ub.cora.fitnesse;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RecordEndpointFixtureTest {
	private RecordEndpointFixture fixture;

	@BeforeMethod
	public void setUp() {
		SystemUrl.url = "http://localhost:8080/therest/";
		fixture = new RecordEndpointFixture();
	}

	@Test
	public void testSetGetType() {
		fixture.setType("someType");
		// assertEquals(fixture.gettype, expected);
	}
}
