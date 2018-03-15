package Test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Model.Component;
import Model.ComponentDatabase;

/**
 * Tests the Component Database.
 * 
 * @author Keegan Wantz - wantzkt@uw.edu
 *
 */
public class ComponentDatabaseTest {
	ComponentDatabase dbTest;
	
	@Before
	public void setUp() throws Exception {
		dbTest = new ComponentDatabase();
		dbTest.connect();
	}
	
	@After
	public void doAfter() {
		dbTest.close();
	}
	
	// We can't actually test the branches for this without removing the database file.
	@Test
	public void testConnect() {
		// Close so not connected
		dbTest.close();
		// Test connection.
		assertEquals(1, dbTest.connect());
	}

	@Test
	public void testFinalizeAndCloseNotConnected() {
		// Close so not connected
		dbTest.close();
		// Then finalize.
		dbTest.finalize();
	}
	
	@Test
	public void testFinalizeAndCloseConnected() {
		dbTest.connect();
		dbTest.finalize();
	}

	@Test
	public void testGetComponentIntNoBypass() {
		Component theComponent = dbTest.getComponent(1);
		assertNull(theComponent);
	}
	
	@Test
	public void testGetComponentIntNoBypassValid() {
		Component theComponent = dbTest.getComponent(6);
		Component testComponent = new Component(6, "Keegan Computer", new BigDecimal("1337"), new BigDecimal("15"), 3, 1, 2, 0, 25, "Aluminum and tempered glass, GTX 1070, Ryzen 7 1700X, misc other hardware.", 0, BigDecimal.ZERO, null);
		
		assertEquals(testComponent, theComponent);
	}

	@Test
	public void testGetComponentWithBypassValidComponent() {
		Component theComponent = dbTest.getComponent(1, true);
		Component testComponent = new Component(1, "TEST1", new BigDecimal("1"), new BigDecimal("2"), 3, 4, 5, 6, 7, "Some Material 1", 8, new BigDecimal("9"), null);
		assertEquals(testComponent, theComponent);
	}
	
	@Test
	public void testGetComponentWithInvalidID() {
		Component theComponent = dbTest.getComponent(-1, true);
		assertNull(theComponent);
	}

	@Test
	public void testGetComponentWithCached() {
		dbTest.getComponent(1, true);
		Component theComponent = dbTest.getComponent(1, true);
		Component testComponent = new Component(1, "TEST1", new BigDecimal("1"), new BigDecimal("2"), 3, 4, 5, 6, 7, "Some Material 1", 8, new BigDecimal("9"), null);
		assertEquals(testComponent, theComponent);
	}

	@Test
	public void testGetAllComponentsNoneCached() {
		dbTest.getAllComponents();
	}

	@Test
	public void testGetAllComponentsWithCached() {
		dbTest.getComponent(6, true);
		dbTest.getAllComponents();
	}

	@Test
	public void testAddComponent() {
		Component testAdd = new Component(0, "ADD_TEST", BigDecimal.ONE, BigDecimal.TEN, 10, 11, 12, 13, 14, "Ice Cream", 0, BigDecimal.ZERO, null);
		dbTest.addComponent(testAdd);
		List<Component> addedList = dbTest.getAllComponents();
		
		int id = addedList.get(addedList.size()-1).getMyID();
		Component testCompare = new Component(id, "ADD_TEST", BigDecimal.ONE, BigDecimal.TEN, 10, 11, 12, 13, 14, "Ice Cream", 0, BigDecimal.ZERO, null);
		
		System.out.println("Wat: " + addedList.get(addedList.size()-1).getMyID());
		
		assertEquals(testCompare, addedList.get(addedList.size()-1));
	}

	@Test
	public void testDeleteComponent() {
		Component testAdd = new Component(0, "ADD_TEST", BigDecimal.ONE, BigDecimal.TEN, 10, 11, 12, 13, 14, "Ice Cream", 0, BigDecimal.ZERO, null);
		if (dbTest.addComponent(testAdd)) {
			List<Component> addedList = dbTest.getAllComponents();
			int size1 = addedList.size();
			dbTest.deleteComponent(addedList.get(addedList.size() - 1).getMyID());
			List<Component> deletedList = dbTest.getAllComponents();
			int size2 = deletedList.size();
			assertTrue(size2 < size1);		
		} else {
			fail("Failed to add component.");
		}
	}

}
