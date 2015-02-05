package entityTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import entity.establishment.Establishment;

public class EstablishmentTest {

	private Establishment est;
	
	@Before
	public void setUp(){
		est = new Establishment("Parque do povo", "Sao Joao de Campina Grande", "Establishment photo path", Double.valueOf(111111111), Double.valueOf(222222222));
	}
	
	@Test
	public void testGetAndSetName(){
		Assert.assertEquals("Parque do povo", est.getName());
		est.setName("New name");
		Assert.assertEquals("New name", est.getName());
	}
	
	@Test
	public void testGetAndSetDescription(){
		Assert.assertEquals("Sao Joao de Campina Grande", est.getDescription());
		est.setName("New description");
		Assert.assertEquals("New description", est.getDescription());
	}

	@Test
	public void testGetAndSetPhoto(){
		Assert.assertEquals("Establishment photo path", est.getPhoto());
		est.setName("New photo");
		Assert.assertEquals("New photo", est.getPhoto());
	}

	@Test
	public void testGetAndSetLatitude(){
		
	}
	
	@Test
	public void testGetAndSetLongitude(){
		
	}
}
