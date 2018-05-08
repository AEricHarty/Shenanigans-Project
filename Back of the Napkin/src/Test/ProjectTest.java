package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.math.BigDecimal;
import java.util.LinkedList;

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
	private static final BigDecimal COST = new BigDecimal("5.15");
	private static final BigDecimal CPM = new BigDecimal("7.50");
    private Project myProject;
    private final String myName = "Test Name";
    private final BigDecimal myMiscCost = new BigDecimal("8.80");
    private final double myManHrs = 4.5;
    private final BigDecimal myPowerCost = new BigDecimal("10.00");
    
    private final Component comp = new Component(2, myName, COST, CPM,
			myManHrs, myManHrs, myManHrs, myManHrs, myManHrs, myName,
			myManHrs, myMiscCost, null);
    private final ComponentListItem item = new ComponentListItem(comp, 2);
    
    private final Component comp2 = new Component(3, myName, COST, CPM,
			myManHrs, myManHrs, myManHrs, myManHrs, myManHrs, myName,
			myManHrs, myMiscCost, null);
    private final ComponentListItem item2 = new ComponentListItem(comp2, 4);
    
    private final Component subComp = new Component(3, myName, COST, CPM,
			myManHrs, myManHrs, myManHrs, myManHrs, myManHrs, myName,
			myManHrs, myMiscCost, null);
    private final ComponentListItem subItem = new ComponentListItem(subComp, 5);
    private final LinkedList<ComponentListItem> subList = new LinkedList<ComponentListItem>();

    
	/**
	 * @author Eric Harty - hartye@uw.edu
	 * 
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		myProject = new Project();
		myProject.setName(myName);
	}
	
	/**
	 * Tests the getters that do calculations in Project and Component classes
	 * @author Eric Harty - hartye@uw.edu
	 */
	@Test
	void testTotals() {
		myProject.addComponent(comp, 2);
		myProject.addComponent(comp2, 4);
		
		//subList.add(subItem);
		Component comp3 = new Component(3, myName, COST, CPM,
					myManHrs, myManHrs, myManHrs, myManHrs, myManHrs, myName,
					myManHrs, myMiscCost, null);
		
		myProject.addComponent(comp3, 1);
		
		BigDecimal upfront = new BigDecimal("5.15").multiply(BigDecimal.valueOf(7));
		BigDecimal month = new BigDecimal("7.50").multiply(BigDecimal.valueOf(7));;

		assertEquals(upfront, myProject.getTotalUpfrontCost());
		assertEquals(month, myProject.getCostPerMonth());
		assertEquals(31.5, myProject.getTotalManHrs(), 0.0000001);
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
	/*@Test
	void testSaveLoad() {
		File path = new File(myName + ".txt");	
		myProject.saveProject();
		Project copy = new Project(path);
		
		assertEquals(myProject.getName(), copy.getName());
		assertEquals(myProject.getComponents(), copy.getComponents());
		assertEquals(myProject.getTotalUpfrontCost(), copy.getTotalUpfrontCost());
		assertEquals(myProject.getCostPerMonth(), copy.getCostPerMonth());
		assertEquals(myProject.getTotalManHrs(), copy.getTotalManHrs());
	}*/

}
