import java.util.ArrayList;
import java.util.Scanner;

// Classe Livro
class Livro {
    private String titulo;
    private String autor;
    private String isbn;
    private boolean disponivel;

    public Livro(String titulo, String autor, String isbn) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.disponivel = true;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void emprestar() {
        this.disponivel = false;
    }

    public void devolver() {
        this.disponivel = true;
    }

    public void exibirDetalhes() {
        System.out.println("Título: " + titulo + ", Autor: " + autor + ", ISBN: " + isbn + ", Disponível: " + disponivel);
    }
}

// Classe Usuario
class Usuario {
    private String nome;
    private int id;
    private ArrayList<Livro> livrosEmprestados;

    public Usuario(String nome, int id) {
        this.nome = nome;
        this.id = id;
        this.livrosEmprestados = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public boolean podeEmprestar() {
        return livrosEmprestados.size() < 3;
    }

    public void adicionarLivro(Livro livro) {
        livrosEmprestados.add(livro);
    }

    public void removerLivro(Livro livro) {
        livrosEmprestados.remove(livro);
    }

    public void exibirDetalhes() {
        System.out.println("Nome: " + nome + ", ID: " + id + ", Livros emprestados: " + livrosEmprestados.size());
    }
}

// Classe Biblioteca
class Biblioteca {
    private ArrayList<Livro> livros;
    private ArrayList<Usuario> usuarios;

    public Biblioteca() {
        this.livros = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }

    public void cadastrarLivro(Livro livro) {
        livros.add(livro);
        System.out.println("Livro cadastrado com sucesso!");
    }

    public void cadastrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
        System.out.println("Usuário cadastrado com sucesso!");
    }

    public void realizarEmprestimo(String isbn, int idUsuario) {
        Livro livro = buscarLivroPorIsbn(isbn);
        Usuario usuario = buscarUsuarioPorId(idUsuario);

        if (livro == null || usuario == null) {
            System.out.println("Livro ou usuário não encontrado.");
            return;
        }

        if (!livro.isDisponivel()) {
            System.out.println("Livro não está disponível.");
            return;
        }

        if (!usuario.podeEmprestar()) {
            System.out.println("Usuário já atingiu o limite de empréstimos.");
            return;
        }

        livro.emprestar();
        usuario.adicionarLivro(livro);
        System.out.println("Empréstimo realizado com sucesso!");
    }

    public void realizarDevolucao(String isbn, int idUsuario) {
        Livro livro = buscarLivroPorIsbn(isbn);
        Usuario usuario = buscarUsuarioPorId(idUsuario);

        if (livro == null || usuario == null) {
            System.out.println("Livro ou usuário não encontrado.");
            return;
        }

        livro.devolver();
        usuario.removerLivro(livro);
        System.out.println("Devolução realizada com sucesso!");
    }

    public void exibirLivrosDisponiveis() {
        System.out.println("Livros disponíveis:");
        for (Livro livro : livros) {
            if (livro.isDisponivel()) {
                livro.exibirDetalhes();
            }
        }
    }

    private Livro buscarLivroPorIsbn(String isbn) {
        for (Livro livro : livros) {
            if (livro.getIsbn().equals(isbn)) {
                return livro;
            }
        }
        return null;
    }

    private Usuario buscarUsuarioPorId(int id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                return usuario;
            }
        }
        return null;
    }
}

// Classe principal
public class Livrinhos {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nBem-vindo ao Sistema de Gerenciamento de Biblioteca!");
            System.out.println("1. Cadastrar livro");
            System.out.println("2. Cadastrar usuário");
            System.out.println("3. Realizar empréstimo");
            System.out.println("4. Realizar devolução");
            System.out.println("5. Exibir livros disponíveis");
            System.out.println("6. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    System.out.print("Digite o título do livro: ");
                    String titulo = scanner.nextLine();
                    System.out.print("Digite o autor do livro: ");
                    String autor = scanner.nextLine();
                    System.out.print("Digite o ISBN do livro: ");
                    String isbn = scanner.nextLine();
                    biblioteca.cadastrarLivro(new Livro(titulo, autor, isbn));
                    break;
                case 2:
                    System.out.print("Digite o nome do usuário: ");
                    String nome = scanner.nextLine();
                    System.out.print("Digite o ID do usuário: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consumir a quebra de linha
                    biblioteca.cadastrarUsuario(new Usuario(nome, id));
                    break;
                case 3:
                    System.out.print("Digite o ISBN do livro: ");
                    String isbnEmprestimo = scanner.nextLine(); // Usando nextLine() para capturar corretamente o ISBN
                    System.out.print("Digite o ID do usuário: ");
                    int idUsuarioEmprestimo = scanner.nextInt();
                    scanner.nextLine(); // Consumir a quebra de linha
                    biblioteca.realizarEmprestimo(isbnEmprestimo, idUsuarioEmprestimo);
                    break;
                case 4:
                    System.out.print("Digite o ISBN do livro: ");
                    String isbnDevolucao = scanner.nextLine(); // Usando nextLine() para capturar corretamente o ISBN
                    System.out.print("Digite o ID do usuário: ");
                    int idUsuarioDevolucao = scanner.nextInt();
                    scanner.nextLine(); // Consumir a quebra de linha
                    biblioteca.realizarDevolucao(isbnDevolucao, idUsuarioDevolucao);
                    break;
                case 5:
                    biblioteca.exibirLivrosDisponiveis();
                    break;
                case 6:
                    System.out.println("Saindo do sistema...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
