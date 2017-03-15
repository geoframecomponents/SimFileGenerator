package readingWritingSim;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import oms3.io.CSTable;
import oms3.io.DataIO;

public class FromToSep {


	public String pathTOtopo;
	
	public String pathTOout;



	public void process () throws IOException{

		CSTable Reader = DataIO.table(new File(pathTOtopo));
		Double[] vectorFrom =DataIO.getColumnDoubleValues(Reader,"from");
		Double[] vectorTo =DataIO.getColumnDoubleValues(Reader,"to");

		FileWriter out =new FileWriter(pathTOout,true);
		
		for(int i=0;i<vectorFrom.length;i++){			
			for (int j=0;j<vectorTo.length;j++){
				if (vectorFrom[i]-vectorTo[j]==0) {
					vectorFrom[i]=0.0;
					vectorTo[i]=vectorTo[j];
				}
			}

			
		}
		

		

	}



}
