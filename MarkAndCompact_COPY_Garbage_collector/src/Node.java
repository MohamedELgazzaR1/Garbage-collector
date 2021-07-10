	public class Node {
	    int n;
	    String name;
	    int len;
	    boolean visited; // New attribute

	    Node(int n, String name,int len) {
	        this.n = n;
	        this.name = name;
	        this.len=len;
	        visited = false;
	    }

	    // Two new methods we'll need in our traversal algorithms
	    void visit() {
	        this.visited = true;
	    }

	    public int getN() {
			return n;
		}

		public void setN(int n) {
			this.n = n;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getLen() {
			return len;
		}

		public void setLen(int len) {
			this.len = len;
		}



		void unvisit() {
	        this.visited = false;
	    }
	    
	    boolean isVisited() {
	    	return this.visited;
	    }
	    
	}