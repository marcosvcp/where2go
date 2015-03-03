package entityTest;


import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.parse.ParseObject;

import entity.establishment.Establishment;
import entity.event.Event;
import entity.event.Invitation;
import entity.user.User;

public class EstablishmentTest extends TestCase{
	
	private Establishment est1;
	private Establishment est2;
	private Event event2;
	@Before
	public void setUp() {
        ParseObject.registerSubclass(Event.class);
        ParseObject.registerSubclass(User.class);
        ParseObject.registerSubclass(Invitation.class);
        ParseObject.registerSubclass(Establishment.class);
		est1 = new Establishment("Estabelecimento Nome teste 1", "Estabelecimento Descricao teste 1", "Estabelecimento Foto Teste 1", 42.42, 42.42);
		est2 = new Establishment("Estabelecimento Nome teste 2", "Estabelecimento Descricao teste 2", "Estabelecimento Foto Teste 2", 13.13, 13.13);
		event2 = new Event();
		new User("User1 name");
	}
	
	@Test
	public void testEstablishmenConstructor() {
		est1 = null;
		est2 = null;
		est1 = new Establishment("Estabelecimento Nome teste 1", "Estabelecimento Descricao teste 1", "Estabelecimento Foto Teste 1", 42.42, 42.42);
		est2 = new Establishment("Estabelecimento Nome teste 2", "Estabelecimento Descricao teste 2", "Estabelecimento Foto Teste 2", 13.13, 13.13);
		Assert.assertNotNull("Establishment constructor not working", est1);
		Assert.assertNotNull("Establishment constructor not working", est2);
	}
	
	@Test
	public void testGetName(){
		Assert.assertNotNull("Establishment getName not working", est1.getName());
		Assert.assertEquals("Establishment getName not working", "Estabelecimento Nome teste 1", est1.getName());
	}
	
	@Test
	public void testSetName(){
		Assert.assertEquals("Estabelecimento Nome teste 1", est1.getName());
		est1.setName("Estabelecimento Novo Nome");
		Assert.assertEquals("Establishment setName not working", "Estabelecimento Novo Nome", est1.getName());
	}
	
	@Test
	public void testGetDescription(){
		Assert.assertNotNull("Establishment getDescription not working", est1.getDescription());
		Assert.assertEquals("Establishment getDescription not working", "Estabelecimento Descricao teste 1", est1.getDescription());
	}
	
	@Test
	public void testSetDescription(){
		Assert.assertEquals("Estabelecimento Descricao teste 1", est1.getDescription());
		est1.setDescription("Estabelecimento Nova Descricao");
		Assert.assertEquals("Establishment setDescription not working", "Estabelecimento Nova Descricao", est1.getDescription());
	}
	
	@Test
	public void testGetPhoto(){
		Assert.assertNotNull("Establishment getPhoto not working", est1.getPhoto());
		Assert.assertEquals("Establishment getPhoto not working","Estabelecimento Foto Teste 1", est1.getPhoto());
	}
	
	@Test
	public void testSetPhoto(){
		Assert.assertEquals("Estabelecimento Foto Teste 1", est1.getPhoto());
		est1.setPhoto("Estabelecimento Nova Foto");
		Assert.assertEquals("Establishment setPhoto not working", "Estabelecimento Nova Foto", est1.getPhoto());
	}
	
	@Test
	public void getLocation() {
		//Assert.assertNotNull("Establishment getLocation not working", est1.getLocation());
		//Assert.assertEquals("Establishment getLocation not working", est1.getLocation());
		System.out.println("est1 = " + est1.getLocation());
		System.out.println("est2 = " + est2.getLocation());
	}

//	@Test
//	public void testAddEvents(){
//		Assert.assertNotNull("Establishment getEvents not working", est1.getEvents());
//		Assert.assertEquals("Establishment getEvents not working", 0, est1.getEvents().size());
//		Assert.assertNotNull("Establishment addEvents not working", est1.addEvent(event1));
//		Assert.assertNotNull("Establishment addEvents not working", est1.addEvent(event2));
//		Assert.assertEquals("Establishment addEvents not working", 2, est1.getEvents().size());
//		Assert.assertEquals("Establishment addEvents not working", "Event 1 Name", est1.getEvents().get(0).getName());
//	}
	
//	@Test
//	public void testGetEvents(){
//		Assert.assertNotNull("Establishment getEvents not working", est1.getEvents());
//		Assert.assertEquals("Establishment getEvents not working", 0, est1.getEvents().size());
//		Assert.assertNotNull("Establishment addEvents not working", est1.addEvent(event1));
//		Assert.assertNotNull("Establishment getEvents not working", est1.getEvents());
//		Assert.assertEquals("Establishment addEvents not working", 1, est1.getEvents().size());
//	}
	
//	@Test
//	public void testRemoveEvents(){
//		Assert.assertNotNull("Establishment getEvents not working", est1.getEvents());
//		Assert.assertEquals("Establishment getEvents not working", 0, est1.getEvents().size());
//		Assert.assertNotNull("Establishment addEvents not working", est1.addEvent(event1));
//		Assert.assertEquals("Establishment addEvents not working", 1, est1.getEvents().size());
//		est1.removeEvent(event1);
//		Assert.assertEquals("Establishment removeEvents not working", 0, est1.getEvents().size());
//	}
	
	@Test
	public void testHashCode(){
		Assert.assertNotNull("Establishment hashCode not working", est1.hashCode());
	}
	
	@Test
	public void testEquals(){
		Assert.assertEquals("Establishment equals not working", false, est1.equals(null));
		Assert.assertEquals("Establishment equals not working", false, est1.equals(event2));
		Assert.assertEquals("Establishment equals not working", true, est1.equals(est1));
		Assert.assertEquals("Establishment equals not working", false, est1.equals(est2));
	}
}
