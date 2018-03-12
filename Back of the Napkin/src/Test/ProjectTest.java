package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Model.Component;
import Model.ComponentListItem;
import Model.Project;

/**
 * Tests the project class
 * 
 * @author Eric Harty - hartye@uw.edu
 */
class ProjectTest {
	
	private static final BigDecimal COST = new BigDecimal(5);
	private static final BigDecimal CPM = new BigDecimal(7);
    private Project myProject;
    private final String myName = "Test Name";
    private final BigDecimal myMiscCost = new BigDecimal(8);
    private final double myManHrs = 4.5;
    private final BigDecimal myPowerCost = new BigDecimal(10);
    
    private final Component comp = new Component(2, myName + "2", COST, CPM,
			myManHrs, myManHrs, myManHrs, myManHrs, myManHrs, myName,
			myManHrs, myMiscCost, null);
    private final ComponentListItem item = new ComponentListItem(comp, 2);
    //Cost 10.00, cpm 14.00/m, mhrs 9
    
    private final Component comp2 = new Component(3, myName + "3", COST, CPM,
			myManHrs, myManHrs, myManHrs, myManHrs, myManHrs, myName,
			myManHrs, myMiscCost, null);
    private final ComponentListItem item2 = new ComponentListItem(comp2, 4);
    //Cost 20.00, cpm 28.00/m, mhrs 18
    
    
	/**
	 * @author Eric Harty - hartye@uw.edu
	 * 
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		myProject = new Project();
		myProject.setName(myName);
		myProject.setMiscCost(myMiscCost);
		myProject.setPowerCost(myPowerCost);
		myProject.setManHrs(myManHrs);
	}
	
	/**@author Eric Harty - hartye@uw.edu*/
	@Test
	void testTotals() {
		myProject.addComponent(comp, 2);
		myProject.addComponent(comp2, 4);
		
		BigDecimal upfront = new BigDecimal(38);
		BigDecimal month = new BigDecimal(42);

		assertEquals(myProject.getTotalUpfrontCost(), upfront);
		assertEquals(myProject.getCostPerMonth(), month);
		assertEquals(myProject.getTotalManHrs(), 31.5);
	}

	/**@author Eric Harty - hartye@uw.edu*/
	@Test
	void testAddComp() {
		myProject.addComponent(comp, 2);
		myProject.addComponent(comp2, 4);
		assertNotEquals(myProject.getComponents(), null);
		assertEquals(myProject.getComponents().size(), 2);
		assertEquals(myProject.getComponents().getFirst(), item);
		assertEquals(myProject.getComponents().getLast(), item2);
	}
	
	/**@author Eric Harty - hartye@uw.edu*/
	@Test
	void testRemoveComp() {
		myProject.addComponent(comp, 2);
		myProject.addComponent(comp2, 4);
		myProject.removeComponent(3);
		assertNotEquals(myProject.getComponents().getLast(), item2);
		assertEquals(myProject.getComponents().getLast(), item);
	}
	
	/**@author Eric Harty - hartye@uw.edu*/
	@Test
	void testSaveLoad() {
		File path = new File(myName + ".txt");
		
		myProject.saveProject();

		Project copy = new Project(path);
		
		assertEquals(myProject.getName(), copy.getName());
		assertEquals(myProject.getMiscCost(), copy.getMiscCost());
		assertEquals(myProject.getManHrs(), copy.getManHrs());
		assertEquals(myProject.getComponents(), copy.getComponents());
		assertEquals(myProject.getTotalUpfrontCost(), copy.getTotalUpfrontCost());
		assertEquals(myProject.getCostPerMonth(), copy.getCostPerMonth());
		assertEquals(myProject.getTotalManHrs(), copy.getTotalManHrs());
	}

}
