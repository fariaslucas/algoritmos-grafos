import java.util.*;

public class Graph extends Digraph {

	private Stack<Vertex> st1;
	List<Vertex> path1;
	List<Vertex> path2;

    public Graph() {
		st1 = new Stack<>();
    }

	@Override
    public void add_arc( Integer id1, Integer id2) {
		System.out.println("Operação não permitida: Adição de arco.");
     }

	@Override
	public boolean is_acyclic( ) {
		// fazer
		return true;
	}

	@Override
	public boolean is_bipartite( ) {
		for( Vertex v1 : vertex_set.values( ) ) {
			if( v1.ind_set == 0 ) {
				v1.ind_set = 1;
				if(bipartite_visit(v1))
					return false;
			}
		}
		System.out.print( "\n É bipartido " );
		return true;
	}
	private boolean bipartite_visit( Vertex v1 ) {
		for( Vertex v2 : v1.nbhood.values( ) ) {
			if( v2.ind_set == 0 ) {
				v2.ind_set = - v1.ind_set;
				if(bipartite_visit(v2))
					return true;
			}
			if( v2.ind_set == v1.ind_set ) {
				// encontrar um ciclo ímpar
				System.out.println( "\n Não é bipartido " );
				return true;
			}
		}
		return false;
	}
/*
	public void find_articulation() {
    	DFS(null);
    	System.out.print("\n Articulações: ");
		for (Vertex v1 : vertex_set.values()) {
			if (v1.parent == null) {
				if (v1.children > 1)
					System.out.printf("%d ", v1.id);
			}
			else  {
				for (Vertex v2 : v1.nbhood.values()) {
					if (v1.equals(v2.parent) && v2.low >= v1.d)
						System.out.printf("%d ", v1.id);
				}
			}
		}
	}
*/
	public void bicon_comp( ) {
		time = 0;
		for( Vertex v1 : vertex_set.values( ) )
			if( v1.d == null )
				bicon_comp_visit( v1 );
	}

	private void bicon_comp_visit( Vertex v1 ) {
		v1.d = ++time;
		v1.low = v1.d;
		for( Vertex neig : v1.nbhood.values( ) ) {
			if( neig.d == null ) {
				st1.push( v1 );
				st1.push( neig );
				v1.children++;
				neig.parent = v1;
				bicon_comp_visit( neig );
				if (v1.parent == null && v1.children > 1)
					this.desempilha( v1, neig );
				if( neig.low >= v1.d)
					this.desempilha( v1, neig );
				if( neig.low < v1.low )
					v1.low = neig.low;
			}
			else if( neig != v1.parent ) {
				if( neig.d < v1.d ) {
					st1.push( v1 );
					st1.push( neig );
				}
			}
		}
	}

	private void desempilha( Vertex cut_vertex, Vertex aux ) {
		if( st1.empty( ) )
			return;
		System.out.println( "\n Bloco: " );
		Vertex v1 = this.st1.pop( );
		Vertex v2 = this.st1.pop( );
		System.out.print( v1.id );
		System.out.println( v2.id );
		while( v1 != cut_vertex || v2 != aux ) {
			if( st1.empty( ) )
				return;
			v1 = this.st1.pop( );
			v2 = this.st1.pop( );
			System.out.print( v1.id );
			System.out.println( v2.id );
		}
	}



	/* Função que retorna o caminho mínimo entre dois vértices.
	*  Utiliza a ideia de que na árvore resultante da BFS o caminho entre a raiz
	*  e os outros vértices é o mínimo. */
	public List<Vertex> shortest_path(Vertex raiz, Vertex dest) {
		List<Vertex> path = new ArrayList<>();
		for (Vertex v : vertex_set.values()) {
			v.dist = null;
			v.parent = null;
		}
		raiz.dist = 0;
		Queue<Vertex> lista = new LinkedList<>();
		lista.add(raiz);
		Vertex atual;
		while ((atual = lista.poll()) != null) {
			for( Vertex viz : atual.nbhood.values() ) {
				if (viz.stamp)
					continue; // pula o vértice que estiver marcado
				if( viz.dist == null ) {
					viz.discover(atual);
					lista.add(viz);
				}
			}
		}
		/* Até aqui foi a aplicação de uma BFS com o
		vértice inicial do caminho que queremos encontrar
		como raiz */

		/* Esse for começa no último vértice do caminho e vai
		percorrendo de pai em pai até chegar na raiz */
		for (Vertex v1 = dest; v1 != null; v1 = v1.parent) {
			path.add(v1);
		}
		Collections.reverse(path);
		return path;
	}

	/* Função que verifica se o caminho mínimo entre dois vértices é único.
	*  Ela consiste em ir marcando um vértice do caminho a cada iteração para que
	*  esse vértice não seja levado em consideração ao encontrar um caminho mínimo e
	*  assim retornar um segundo caminho entre os vértices.
	*  Caso os caminhos encontrados tenham o mesmo tamanho retorna false. */
	public boolean unique_path(Vertex source, Vertex dest) {
		path1 = shortest_path(source, dest);
		for (Vertex v1 : path1) {
			if (source.equals(v1) || dest.equals(v1))
				continue; // pula o primeiro e o último vértice para evitar a verificação de caminhos entre o mesmo vértice
			v1.stamp = true; // vai marcando os vértices do caminho para que não sejam utilizados na próxima procura
			path2 = shortest_path(source, dest);
			v1.stamp = false;
			if (path1.size() == path2.size())
				return false;
		}
		return true;
	}

	// Função que verifica se os caminhos mínimos são únicos para cada par de vértices do grafo
	public void is_geodetic() {
		List<Vertex> vertex_set2 = new ArrayList<>(vertex_set.values());
		for (Vertex v1 : vertex_set.values()) {
			for (Vertex v2 : vertex_set2) {
				if (v1 == v2)
					continue;
				if (!unique_path(v1, v2)) {
					System.out.println("\n O grafo não pertence à classe geodetic!!");
					System.out.printf(" Foram encontrados os seguintes caminhos mínimos entre os vértices %d e %d:\n", v1.id, v2.id);
					for (Vertex v : path1)
						System.out.printf(" %d", v.id);
					System.out.println();
					for (Vertex v : path2)
						System.out.printf(" %d", v.id);
					return;
				}
			}
			vertex_set2.remove(v1);
		}
		System.out.println("\n O grafo pertence à classe geodetic!!");
	}
}
