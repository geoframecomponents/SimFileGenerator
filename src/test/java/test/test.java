package test;
import org.junit.Test;
import readingWritingSim.ReadingWritingSim;


public class test {
	
	@Test
	public void componentTest() throws Exception {
		
		//int []ID={2,3,4};
		
		ReadingWritingSim test=new ReadingWritingSim();
		
		test.IDto=1;
		test.IDFrom=35;
		//test.IDarray=ID;
		
		test.pathInputSimFrom="resources/input/35.sim";
		test.pathInputSimTo="resources/input/1.sim";
		test.pathOutputDir="resources/output";
		test.pathTOtopo="resources/input/topo.csv";
		
		test.process();
		
	}

}
