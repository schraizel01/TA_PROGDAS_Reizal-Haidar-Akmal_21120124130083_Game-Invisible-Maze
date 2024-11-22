package Game;

import javax.swing.*;
import java.awt.*;

class MazePanel extends JPanel {
    private MazeGameGUI mazeGameGUI;
    private Map map;


    
    public MazePanel(Map map, MazeGameGUI mazeGameGUI) {
        this.map = map;
        this.mazeGameGUI = mazeGameGUI;

        setPreferredSize(new Dimension(600,600));
    }

    public void setMap(Map map) {
        this.map = map;
        repaint(); // Repaint panel untuk memperbarui tampilan
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int tileSize = 50; // Ukuran tiap sel (px)
        int dotSize = 10;  // Ukuran titik untuk path


        for (int y = 0; y < map.getMapSize(); y++) {
            for (int x = 0; x < map.getMapSize(); x++) {
                boolean isEdge = (x == 0 || y == 0 || x == map.getMapSize() - 1 || y == map.getMapSize() - 1);

                if (x == 9 && y == 9) {
                    g.setColor(Color.RED); // Warna merah untuk finish point
                    int centerX = x * tileSize + tileSize / 2 - dotSize / 2;
                    int centerY = y * tileSize + tileSize / 2 - dotSize / 2;
                    g.fillOval(centerX, centerY, dotSize, dotSize);
                    continue; // Lanjutkan ke sel berikutnya setelah menggambar finish point
                }

                if (map.isWall(x, y)) {
                    if (mazeGameGUI.isMazeVisible()|| isEdge){
                        g.setColor(new Color(0, 255, 0)); // Dinding
                        g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                    }
                    if (!(mazeGameGUI.isMazeVisible()||isEdge)){
                        g.setColor(Color.WHITE); // Jalur kosong
                        g.setColor(Color.LIGHT_GRAY);
                        int centerX = x * tileSize + tileSize / 2 - dotSize / 2;
                        int centerY = y * tileSize + tileSize / 2 - dotSize / 2;
                        g.fillOval(centerX, centerY, dotSize, dotSize);
                    }


                } else if (map.isPath(x, y)) {
                    g.setColor(Color.WHITE); // Jalur kosong
                    g.setColor(Color.LIGHT_GRAY);
                    int centerX = x * tileSize + tileSize / 2 - dotSize / 2;
                    int centerY = y * tileSize + tileSize / 2 - dotSize / 2;
                    g.fillOval(centerX, centerY, dotSize, dotSize);


                }
                g.setColor(new Color(255, 165, 0));
                g.drawRect(x * tileSize, y * tileSize, tileSize, tileSize);
            }
        }
        // Menggambar pemain
        g.setColor(Color.blue);
        g.fillOval(map.player.getX() * tileSize + 10, map.player.getY() * tileSize + 10, tileSize - 20, tileSize - 20);


    }
}
