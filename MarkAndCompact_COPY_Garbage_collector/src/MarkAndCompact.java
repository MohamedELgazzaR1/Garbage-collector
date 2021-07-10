import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class MarkAndCompact {
	

	private static BufferedReader br;

	static void readCsv(LinkedList<String[]> list, LinkedList<String> list2, String path, boolean isHeap) {
		
		String line = "";
		String splitBy = ",";
		try {
			// parsing a CSV file into BufferedReader class constructor
			br = new BufferedReader(new FileReader(path, StandardCharsets.UTF_8));

			while ((line = br.readLine()) != null) {
				if (list2 == null) {
					String[] temp = line.split(splitBy);
					if (isHeap) {
						
						String[] tempHeap = new String[2];
						tempHeap[0] = temp[0];
						tempHeap[1] = Integer.toString((Integer.parseInt(temp[2]) - Integer.parseInt(temp[1])));
						list.add(tempHeap);
					} else {
						list.add(temp);
					}

				}else {
					list2.add(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(list!=null) {
		list.get(0)[0] = list.get(0)[0].substring(1);
		}
	}
	
	
	static void changeHeap(LinkedList<Node> heapNodes , LinkedList<String[]> heap) {
		int counter =0;
		for(String[] a : heap) {
			Node temp = new Node(counter,a[0],Integer.parseInt(a[1]));
			heapNodes.add(temp);
					counter++;
		}
		
	}
	
	static void changePointers(LinkedList<Node[]> pointersNodes,LinkedList<Node> heapNodes, LinkedList<String[]> pointers) {
		boolean found = false; 
		for(int i=0 ; i<pointers.size();i++) {
			String[] temp = pointers.get(i);
			found=false;
			for(int j=0;j<heapNodes.size()&!found;j++) {
				if(heapNodes.get(j).getName().compareTo(temp[0])==0) {
					Node[] temp1=new Node[2];
					temp1[0]=heapNodes.get(j);
					for(int k=0;k<heapNodes.size();k++) {
						if(heapNodes.get(k).getName().compareTo(temp[1])==0) {
							temp1[1]=heapNodes.get(k);
								
							pointersNodes.add(temp1);
							found=true;
							break;
						}					
					}	
							
				}
					
			}
		}
		
	}


     static LinkedList<String[]> createCsvDataSimple(LinkedList<Node> heapNodes) {
        int memLoc=0;
        LinkedList<String[]> list = new LinkedList<>();
        for(int i=0;i<heapNodes.size();i++) {
    		String[] temp=new String[3];
    		temp[0]=heapNodes.get(i).getName();
    		temp[1]=Integer.toString(memLoc);
    		memLoc=memLoc+heapNodes.get(i).getLen();
    		temp[2]=Integer.toString(memLoc);
    		list.add(temp);
    		memLoc++;
    	}

       

        return list;
    }
    
     
     static void writeCSV(String path,LinkedList<String[]> Data) {
    	 try(PrintWriter pw = new PrintWriter(path)){
    		 for(int i = 0 ; i < Data.size();i++) {
    			 String[] temp=Data.get(i);
    			 pw.write(temp[0]+",");
    			 pw.write(temp[1]+",");
    			 pw.write(temp[2]+","+"\n");
    		 }
    		 
    	 } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }
	public static void main(String[] args) {
		
		LinkedList<String[]> heap = new LinkedList<String[]>();
		LinkedList<Node> heapNodes = new LinkedList<Node>();
		LinkedList<String[]> pointers = new LinkedList<String[]>();
		LinkedList<Node[]> pointersNodes=new LinkedList<Node[]>() ;
		LinkedList<String> root = new LinkedList<String>();

		String heapPath = "D:\\sampleTest\\heap.csv";
		String pointersPath = "D:\\sampleTest\\pointers.csv";
		String rootPath = "D:\\sampleTest\\roots.txt";
		String newHeap ="D:\\Mark&Compact_new-heap.csv";

		readCsv(heap, null, heapPath, true);
		changeHeap(heapNodes,heap);

		readCsv(pointers, null, pointersPath, false);
		changePointers(pointersNodes, heapNodes, pointers);

		readCsv(null, root, rootPath, false);
		
		Graph heapMemory = new Graph(true);
		
		for (int i=0;i<pointersNodes.size();i++) {
			Node[] temp=pointersNodes.get(i);
			heapMemory.addEdge(temp[0], temp[1]);
		}
		
		heapMemory.removeExcess();
		
		for(int i=0 ;i<root.size();i++) {
			String temp=root.get(i);
			for(int j =0 ;j<heapNodes.size();j++) {
				if(heapNodes.get(j).getName().compareTo(temp)==0) {
					heapMemory.depthFirstSearch(heapNodes.get(j));
				}
			}
		}
		
		for(int i=0 ;i<heapNodes.size();i++) {
			if(!heapNodes.get(i).visited) {
				heapNodes.remove(i);
				i--;
			}
		}
		
		LinkedList<String[]> csvData = createCsvDataSimple(heapNodes);
		
		writeCSV(newHeap, csvData);


	}
}
