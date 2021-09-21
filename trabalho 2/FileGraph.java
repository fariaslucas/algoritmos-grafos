import java.io.FileReader;
import java.io.BufferedReader;

public class FileGraph {
	public Digraph open_text( ) {
		String thisLine;
		Digraph dg1 = new Digraph( );
		String[] pieces;

		try {
		    FileReader file_in = new FileReader("myfiles/entrada.txt");
		    BufferedReader br1 = new BufferedReader( file_in );
		    while ( (thisLine = br1.readLine( )) != null) {
			    // retira excessos de espaços em branco
			    thisLine = thisLine.replaceAll("\\s+", " ");
			    pieces = thisLine.split(" ");
			    int v1 = Integer.parseInt( pieces[0] );
			    dg1.add_vertex( v1 );
			    for( int i = 2; i < pieces.length; i += 2 ) {
   					int v2 = Integer.parseInt( pieces[ i ] );
   					int c = Integer.parseInt( pieces[i+1] );
					dg1.add_arc( v1, v2, c );
				}
		    }       
		} catch(Exception e) {
			e.printStackTrace();
		}
		System.out.print("Arquivo lido com sucesso.");
		return dg1;
	}
}

