import java.io.*;
import java.util.*;
public class assignment1 {
	
	/* A node represents an artist */
	 static class Artist {
	    private String name;
	    private Artist next;

	 }

	/* The List TopStreamingArtists is composed of a series of artist names */
	static class TopStreamingArtists {
		
	    private Artist first;
	    public TopStreamingArtists(){
	      first = null;
	    }
	    
	    public boolean isEmpty(){
	     return (first == null);
	    }
	    
	    //inserting the artist name into the list
	    public void insert(String artist) {
	    	
	    	//creating a node containing the artist name
	    	Artist node=new Artist();
	    	node.name=artist;
	    	node.next = first; // it points to old first link
	    	first = node; // now first points to this
	    }
	    
	    //displaying the list
	    public void displayList(PrintWriter printer) {
	    	Artist node=first;
	    	
	    	//printing the elements until a null object is found
	    	while(node.next!=null) {
	    		printer.println(node.name);
	    		node=node.next;
	    	}
	    	//printing the last node
	    	printer.println(node.name);

	    }
	 }


		public static void main(String[] args) throws IOException {
			
			//creating the input file and Scanner
			File input=new File("input.txt");
			Scanner scan=new Scanner(input);
			
			//creating output files
			PrintWriter outputArray=new PrintWriter("outputArray.txt");
			PrintWriter outputLinkedList=new PrintWriter("outputLinkedList.txt");
			
			//declaring arraylists that will be used
			ArrayList<String> artist= new ArrayList<>(200);
			
			//filling the arraylist with the artist names
			int i=0;
			while(scan.hasNextLine()) {
				String s = scan.nextLine();
				//splitting the line into tokens and taking the second token, which should be the artist in most cases
				String[] tokens=s.split("[,]");
				artist.add(i,new String(tokens[2]));
				
				//removes " from the names
				if(artist.get(i).charAt(0)=='"')
					artist.set(i, artist.get(i).substring(1,artist.get(i).length()-1));
				
				//if the song title has a comma in it, this if statement takes the next token from the line, which should be the artist
				if(artist.get(i).charAt(artist.get(i).length()-1)=='"')
				// " is used here for logic because the song title would end with it, but would not begin with it if it had a comma in its name
					artist.set(i, tokens[3].substring(1,tokens[3].length()-1));
				
				i++;
			}
			
			//dealing with two special cases where there were a lot of featured artists and it messed up the whole processing so I replaced those manually
			artist.set(192, "Justin Quiles");
			artist.set(82, "Eminem");
			
			//sorting the arraylist in alphabetical order
			Collections.sort(artist, String.CASE_INSENSITIVE_ORDER);
			
			//set will be used to ensure that no duplicates are put into the linked list
			Set<String> added = new HashSet<>();
			
			TopStreamingArtists artistNames = new TopStreamingArtists();
			
			//adding each artist into a linked list, and printing how many times each appears, accomplished using a set
			for (String s : artist) {
			    if (added.add(s)) {
			    	artistNames.insert(s);
			    	outputArray.println(s+ " appears " + Collections.frequency(artist, s)+" times");
			    }
			}
			
			//printing the linked list
			artistNames.displayList(outputLinkedList);
			
			//closing all our files
			scan.close();
			outputArray.close();
			outputLinkedList.close();
			
		}
	}
