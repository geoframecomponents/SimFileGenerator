package test;
import org.junit.Test;

import readingWritingSim.FromToSep;
import readingWritingSim.ReadingWritingSim;


public class testSep {
	
	@Test
	public void componentTest() throws Exception {
		
		int []ID={2,3,4};
		
		FromToSep test=new FromToSep();
		
		//test.ID=1;
		//test.IDarray=ID;
		
		//test.pathInputSim="resources/input/1.sim";
		//test.pathOutputDir="resources/output";
		test.pathTOtopo="resources/input/topo.csv";
		
		test.process();
		
	}

}
