import java.io.*;
import java.util.List;
import java.util.Scanner;

public class AlgGrafos {
    public static void main(String[] args) {

        Scanner scan1 = new Scanner(System.in);

        String line1 = "\n\n 0 Sair \n 1 Print \n 2 Ler de arquivo \n 3 Escrever em arquivo \n 4 Adicionar vértice";
        String line2 = "\n 5 Adicionar aresta \n 6 Excluir vértice \n 7 BFS \n 8 Subjacente \n 9 Compactar";
        String line3 = "\n 10 DFS \n 11 Ordenação topológica \n 12 Reverter arcos \n 13 CFC";
        String line4 = "\n 14 Bipartido \n 15 Qtd comp. conexas \n 16 Entrada arquivo texto";
        String line5 = "\n 17 Comp. biconexas \n 18 Geodetic \n 19 Fluxo máximo \n Escolha a opção: ";
        String menu = line1 + line2 + line3 + line4 + line5;

        Digraph dg1 = new Digraph();

        while (true) {
            System.out.print(menu);
            int choice = scan1.nextInt();
            switch (choice) {
                case 0:
                    return;
                case 1:
                    dg1.print();
                    break;
                case 2:
                    dg1 = read();
                    break;
                case 3:
                    write(dg1);
                    break;
                case 4:
                    /*System.out.print("Id do vértice: ");
                    int idAdc = scan1.nextInt();
                    g1.add_vertex(idAdc);*/
                    dg1.add_vertex( 1 );
                    dg1.add_vertex( 2 );
                    dg1.add_vertex( 3 );
                    dg1.add_vertex( 4 );
                    dg1.add_vertex( 5 );
                    dg1.add_vertex( 6 );
                    dg1.add_vertex( 7 );
                    break;
                case 5:
                    /*System.out.print("Id do primeiro vértice: ");
                    int id1 = scan1.nextInt();
                    System.out.print("Id do segundo vértice: ");
                    int id2 = scan1.nextInt();
                    g1.add_edge(id1,id2);*/
                    dg1.add_edge(1,2);
                    dg1.add_edge(1,3);
                    dg1.add_edge(1,4);
                    dg1.add_edge(2,4);
                    dg1.add_edge(3,4);
                    dg1.add_edge(3,5);
                    dg1.add_edge(5,6);
                    dg1.add_edge(5,7);
                    dg1.add_edge(6,7);
                    break;
                case 6:
                    System.out.print("Vértice a excluir: ");
                    int idExc = scan1.nextInt();
                    dg1.del_vertex(idExc);
                    break;
                case 7:
                    System.out.print("Escolha uma raiz: ");
                    int raiz1 = scan1.nextInt();
                    dg1.BFS(raiz1);
                    break;
                case 8:
                    Graph g2 = dg1.subjacent();
                    g2.print();
                    break;
                case 9:
                    dg1.compact();
                    break;
                case 10:
                    dg1.DFS(null);
                    break;
                case 11:
                    List<Vertex> ts_vertex_set = dg1.topological_sorting();
                    if (!(ts_vertex_set == null)) {
                        System.out.println("\n\n Ordenação topológica");
                        for (Vertex v1 : ts_vertex_set)
                            System.out.print("\n id: " + v1.id + " f: " + v1.f);
                    }
                    break;
                case 12:
                    Digraph d2 = dg1.reverse();
                    d2.print();
                    break;
                case 13:
                    dg1.CFC();
                    break;
                case 14:
                    dg1.is_bipartite();
                    break;
                case 15:
                    System.out.printf("\n Quantidade de componentes conexas: %d", dg1.count_components());
                    break;
                case 16:
                    FileGraph fg1 = new FileGraph();
                    dg1 = fg1.open_text();
                    break;
                case 17:
                    g2 = dg1.subjacent();
                    g2.bicon_comp();
                    break;
                case 18:
                    g2 = dg1.subjacent();
                    g2.is_geodetic();
                    break;
                case 19:
                    System.out.print(" Escolha uma fonte: ");
                    int source = scan1.nextInt();
                    System.out.print(" Escolha um sumidouro: ");
                    int sink = scan1.nextInt();
                    dg1.FordFulkerson(source, sink);
            }
        }
    }
    private static void write( Digraph g1 ) {
        try {
            FileOutputStream arquivoGrav = new FileOutputStream("grafo.dat");
            ObjectOutputStream objGravar = new ObjectOutputStream(arquivoGrav);
            objGravar.writeObject( g1 );
            objGravar.flush();
            objGravar.close();
            arquivoGrav.flush();
            arquivoGrav.close();
            System.out.println("Objeto gravado com sucesso!");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static Graph read( ) {
        Graph g1 = null;
        try {
            FileInputStream arquivoLeitura = new FileInputStream("myfiles/saida.dat");
            ObjectInputStream objLeitura = new ObjectInputStream(arquivoLeitura);
            g1 = (Graph) objLeitura.readObject();
            objLeitura.close();
            arquivoLeitura.close();
            System.out.println("Objeto recuperado: ");
        } catch(Exception e) {
            e.printStackTrace();
        }
        return g1;
    }
}