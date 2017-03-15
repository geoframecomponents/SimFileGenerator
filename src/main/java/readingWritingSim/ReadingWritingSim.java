/*
 * GNU GPL v3 License
 *
 * Copyright 2016 Marialaura Bancheri
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package readingWritingSim;

import oms3.annotations.*;
import oms3.io.CSTable;
import oms3.io.DataIO;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


@Description("The component reads exsitting sim files and generates n sim file, according to the topo.csv file")
@Author(name = "Marialaura Bancheri")
@Keywords("Sim file, Hydrology")
@Name("ReadingWritingSim")
@Status(Status.CERTIFIED)
@License("General Public License Version 3 (GPLv3)")
@SuppressWarnings("nls")

public class ReadingWritingSim {

	@Description("Path to sim file for children hrus")
	@In
	public String pathInputSimFrom;
	
	@Description("Path to sim file for parent hrus")
	@In
	public String pathInputSimTo;
	
	@Description("ID of the sim file of example for the children hrus")
	@In
	public int IDFrom;
	
	@Description("ID of the sim file of example for the parent hrus")
	@In
	public int IDto;
	

	@Description("Path to file topo.csv")
	@In
	public String pathTOtopo;

	@Description("Path to the output directory (sim)")
	@In
	public String pathOutputDir;

	int [] IDarray;




	@Execute
	public void process () throws IOException{
		
		
		//reading of the topo.csv file in which are specified the connection between the HRUs
		// the vetorFrom and vetorTo contain the ID of the connections between the nodes
		// From node "1" to node "2"
		CSTable Reader = DataIO.table(new File(pathTOtopo));
		Double[] vectorFrom =DataIO.getColumnDoubleValues(Reader,"from");
		Double[] vectorTo =DataIO.getColumnDoubleValues(Reader,"to");

		
		// detection of the the children nodes, which are the external nodes
		for(int i=0;i<vectorFrom.length;i++){			
			for (int j=0;j<vectorTo.length;j++){
				if (vectorFrom[i]-vectorTo[j]==0) {
					vectorFrom[i]=0.0;
					
				}
			}			
		}
		
		// detection of the the parent nodes, which are the internal nodes
		for(int i=0;i<=vectorFrom.length;i++){
			for (int j=i+1;j<vectorTo.length;j++){
			vectorTo[i]=(vectorTo[i]-vectorTo[j]==0)?0:vectorTo[i];
			}
		}
		
		
		// for each kind of node there is a different sim file to generate
		writeSim(vectorFrom,pathInputSimFrom, IDFrom);
		writeSim(vectorTo,pathInputSimTo, IDto);
		

	}


	
	
	public void writeSim(Double [] IDarray, String path, int ID) throws IOException{
		
		// for each node (both parent and child)
		for(int i=0;i<IDarray.length;i++){
		
			if(IDarray[i]!=0){
				
			// reading of the example sim file 
			FileReader file =new FileReader(path);

			// writing of the output files 
			FileWriter out =new FileWriter(pathOutputDir+"/"+IDarray[i].intValue()+".sim",false);

			Scanner in = new Scanner(file);

			// the files are exactly the same except for some lines
			while(in.hasNextLine()){
				String line = in.nextLine();

				//System.out.println("OMS3.sim (name: \""+ID+"\") {");

				// the lines containing the ID must be updated
				if(line.equals("def ID="+ID)){
					out.write("def ID="+IDarray[i].intValue()+"\n");


				}else if(line.equals("OMS3.sim (name: \""+ID+"\") {")){

					out.write("OMS3.sim (name: \""+IDarray[i].intValue()+"\") {"+"\n");

				}else{

					out.write(line+"\n");
				}

			}
			file.close();
			out.close();
		}	

	}
	}


}
