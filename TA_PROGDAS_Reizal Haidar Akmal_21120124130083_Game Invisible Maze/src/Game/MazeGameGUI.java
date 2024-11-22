package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MazeGameGUI extends JFrame {
    private Map map;
    private boolean isVisible = true;  // Untuk visibilitas maze
    private boolean canMove = false;   // Untuk mengontrol pergerakan pemain
    private Timer timer;
    private int Chance = 3;
    private JLabel ChanceLabel;
    private JLabel Message;
    private JButton Play;
    private JButton Restart;
    private MazePanel mazePanel;
    private JPanel homePanel;  // Panel untuk Home Page
    private JPanel gamePanel;  // Panel untuk permainan (maze)
    private CardLayout cardLayout;
    private boolean gameStarted = false; // Indikator apakah game telah dimulai

    public boolean isMazeVisible() {
        return isVisible;
    }

    private class MazeKeyListener implements KeyListener {
        private MazePanel mazePanel;

        public MazeKeyListener(MazePanel mazePanel) {
            this.mazePanel = mazePanel;
        }

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            if (!canMove) return; // Abaikan input jika pergerakan belum diizinkan

            int key = e.getKeyCode();
            boolean validMove = true;

            if (key == KeyEvent.VK_UP) {
                validMove = map.movePlayerUp(); // Gerak ke atas
            } else if (key == KeyEvent.VK_DOWN) {
                validMove = map.movePlayerDown(); // Gerak ke bawah
            } else if (key == KeyEvent.VK_LEFT) {
                validMove = map.movePlayerLeft(); // Gerak ke kiri
            } else if (key == KeyEvent.VK_RIGHT) {
                validMove = map.movePlayerRight(); // Gerak ke kanan
            }

            if (!validMove) {
                Chance--;
                ChanceLabel.setText("Chance: " + Chance);
            }
            if (Chance == 0) {
                Message.setText("GAME OVER");
                gameStarted = false;// Hentikan game jika kalah
                Restart.setVisible(true);
                canMove = false;

            }
            if (map.isWin()){
                Message.setText("FINISH");
                gameStarted = false;// Hentikan game jika kalah
                Restart.setVisible(true);
                canMove = false;
            }

            mazePanel.repaint(); // Perbarui tampilan maze
        }

        @Override
        public void keyReleased(KeyEvent e) {}
    }

    private void startGame() {
        isVisible = true; // Reset visibilitas maze
        Chance = 3; // Reset jumlah kesempatan
        ChanceLabel.setText("Chance: " + Chance);
        Message.setText(""); // Hapus pesan
        map = new Map(); // Reset peta
        mazePanel.setMap(map); // Perbarui panel dengan peta baru
        mazePanel.repaint(); // Repaint panel maze
        gameStarted = true; // Tandai game telah dimulai
        canMove = false; // Tidak bisa bergerak sampai timer selesai
        Restart.setVisible(false);
        timer.start(); // Mulai timer
    }

    public MazeGameGUI() {
        setTitle("Invisible Maze");
        setSize(800, 800);
        getContentPane().setBackground(Color.BLACK);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        map = new Map();

        // Inisialisasi CardLayout
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        // Gambar maze
        ImageIcon originalIcon = new ImageIcon("src/Game/MAZE.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);

        JPanel MazeIMG = new JPanel(new GridBagLayout());
        MazeIMG.setPreferredSize(new Dimension(300, 300));
        GridBagConstraints letak2 = new GridBagConstraints();
        ImageIcon MazeIcon = new ImageIcon("src/Game/MAZE.png");

        JLabel MazeLabel = new JLabel(resizedIcon);
        MazeLabel.setPreferredSize(new Dimension(300, 300));
        MazeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        letak2.gridx = 0;
        letak2.gridy = 0;
        letak2.anchor = GridBagConstraints.CENTER;
        MazeIMG.add(MazeLabel, letak2);

        // Panel Home Page
        homePanel = new JPanel(new GridBagLayout());
        GridBagConstraints letak1 = new GridBagConstraints();
        homePanel.setBackground(Color.BLACK);

        JLabel homeLabel = new JLabel("INVISIBLE MAZE", SwingConstants.CENTER);
        homeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        homeLabel.setForeground(Color.WHITE);
        letak1.anchor = GridBagConstraints.NORTH;
        homePanel.add(homeLabel, letak1);

        letak1.gridy = 1;
        letak1.insets = new Insets(10, 0, 10, 0); // Margin atas-bawah
        homePanel.add(MazeLabel, letak1);


        Play = new JButton("Play");
        Play.setFont(new Font("Arial", Font.BOLD, 18));
        Play.setForeground(Color.WHITE);
        Play.setBackground(Color.GREEN);
        Play.setOpaque(true);
        Play.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230), 2));
        Play.setPreferredSize(new Dimension(200, 50));
        Play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(); // Mulai permainan
                cardLayout.show(getContentPane(), "Game"); // Pindah ke game panel
                mazePanel.requestFocusInWindow();
            }
        });
        letak1.gridy = 2;
        letak1.anchor = GridBagConstraints.CENTER;
        homePanel.add(Play, letak1);

        // Panel Game
        gamePanel = new JPanel(new GridBagLayout());
        GridBagConstraints letak = new GridBagConstraints();
        gamePanel.setBackground(Color.BLACK);

        // Panel Maze
        mazePanel = new MazePanel(map, this);
        mazePanel.setPreferredSize(new Dimension(600, 600));
        mazePanel.setBackground(Color.BLACK);
        mazePanel.setFocusable(true);
        mazePanel.addKeyListener(new MazeKeyListener(mazePanel));
        letak.gridx = 0;
        letak.gridy = 1;
        letak.gridwidth = 1;
        letak.gridheight = 1;
        letak.anchor = GridBagConstraints.CENTER;
        gamePanel.add(mazePanel, letak);

        // Label untuk menampilkan chance
        ChanceLabel = new JLabel("Chance : " + Chance, SwingConstants.CENTER);
        ChanceLabel.setFont(new Font("Arial", Font.BOLD, 24));
        ChanceLabel.setForeground(Color.WHITE);
        letak.gridx = 0;
        letak.gridy = 0; // Tempatkan ChanceLabel di baris pertama
        letak.gridwidth = 1;
        letak.gridheight = 1;
        gamePanel.add(ChanceLabel, letak);

        // Message Label
        Message = new JLabel();
        Message.setFont(new Font("Arial", Font.BOLD, 36));
        Message.setForeground(Color.RED);
        letak.gridx = 0;
        letak.gridy = 2;
        letak.gridwidth = 1;
        letak.gridheight = 1;
        letak.anchor = GridBagConstraints.CENTER;
        gamePanel.add(Message, letak);

        //Tombol Restart
        Restart = new JButton("Restart");
        Restart.setFont(new Font("Arial", Font.BOLD, 18));
        Restart.setForeground(Color.WHITE);
        Restart.setBackground(Color.GREEN);
        Restart.setOpaque(true);
        Restart.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230), 2));
        Restart.setPreferredSize(new Dimension(200, 50));
        letak.gridx = 0;
        letak.gridy = 3;
        letak.gridwidth = 1;
        letak.gridheight = 1;
        letak.anchor = GridBagConstraints.CENTER;
        Restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
                mazePanel.requestFocusInWindow();
            }
        });
        gamePanel.add(Restart, letak);


        // Menambahkan panel ke dalam CardLayout
        add(homePanel, "Home");
        add(gamePanel, "Game");

        // Menampilkan halaman home pertama kali
        cardLayout.show(getContentPane(), "Home");

        // Timer untuk mengatur visibilitas maze
        timer = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isVisible = false; // Setelah 10 detik, maze jadi tak terlihat
                mazePanel.repaint(); // Perbarui tampilan maze

                canMove = true; // Setelah timer selesai, izinkan pergerakan
            }
        });

        timer.setRepeats(false); // Timer hanya berjalan sekali
        setVisible(true); // Tampilkan frame
    }

    public static void main(String[] args) {
        new MazeGameGUI();  // Jalankan game
    }
}

















