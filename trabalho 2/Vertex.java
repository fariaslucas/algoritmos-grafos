import java.io.Serializable;
import java.lang.Comparable;
import java.util.HashMap;

public class Vertex implements Serializable, Comparable<Vertex> {
    protected Integer id;
    protected HashMap<Integer,Vertex> nbhood;
    protected HashMap<Integer, Integer> arc_capacity;
    protected Vertex parent, root;
    protected Integer dist, d, f, low, children;
    // independent set if bipartite: 0, -1, or 1
    protected int ind_set;
    protected boolean stamp;

    public Vertex ( int id ) {
		// exigir id >= 1
        this.id = id;
        nbhood = new HashMap<>();
        arc_capacity = new HashMap<>();
        children = 0;
    }
    
	@Override public int compareTo( Vertex otherVertex ) {
		if( otherVertex.f > this.f)
			return 1;
		else
			return -1;
	}
    
    public void add_neighbor( Vertex viz ) {
        nbhood.put(viz.id, viz);
    }

    public void add_capacity(Integer id_nb, Integer capacity) {
        arc_capacity.put(id_nb, capacity);
    }

    public int degree() {
        // grau de saída se direcionado
        return nbhood.size();
    }
    
    public void discover( Vertex parent ) {
        this.parent = parent;
        this.dist = parent.dist + 1;
    }
    
    public void print() {
        System.out.print("\nId do vértice " + id + ", Vizinhança: " );
        for( Vertex v : nbhood.values())
            System.out.print(" " + v.id + " " + this.arc_capacity.get(v.id));
        if( d != null )
			System.out.print(". d " + d + ", f " + f);
        if( parent != null)
            System.out.print(". Pai " + parent.id + ", distância " + dist );
        else if ( dist == null )
            System.out.print(". Não alcançável pela raiz.");
        else
            System.out.print(". Raiz.");
    }
    
    protected Vertex get_root( ) {
		if( parent == null )
			root = this;
		else
			root = parent.get_root( );
		return root;
	}
}
