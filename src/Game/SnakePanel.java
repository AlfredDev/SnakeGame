/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author losga
 */
public class SnakePanel extends JPanel implements ActionListener {

    static final int WIDTH = 600;
    static final int HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (WIDTH * HEIGHT) / UNIT_SIZE;
    static final int DELAY = 85;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int partes = 5;
    int apple;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    public SnakePanel() {
        random = new Random();
        setBackground(Color.BLACK);
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addKeyListener(new MyKeyAdapter());
        IniciarJuego();
    }

    private void IniciarJuego() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        draw(g);
    }

    private void draw(Graphics g) {
        // Pintaremos  el panel con cuadriculas
        if (running) {

            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            // Dibujo de la serpiente
            for (int i = 0; i < partes; i++) {
                if (i == 0) {
                    g.setColor(Color.GREEN);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(Color.YELLOW);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + apple, (WIDTH - metrics.stringWidth("Score: " + apple)) / 2, g.getFont().getSize());

        } else {
            gameOver(g);
        }

    }

    private void newApple() {
        // Cordenanas para la nueva manzana
        appleX = random.nextInt((int) (WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int) (HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    private void mover() {
        for (int i = partes; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    public void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            partes++;
            apple++;
            newApple();
        }

    }

    void checkCollisions() {
        // Si el gusano colisiona con su cuerpo
        for (int i = partes; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
            }
        }

        // Si la cabeza choca el borde izquiedo
        if (x[0] < 0) {
            running = false;
        }
        // Si la cabeza choca el borde derecho
        if (x[0] > WIDTH) {
            running = false;
        }
        // Si la cabeza choca el borde inferior
        if (y[0] > HEIGHT) {
            running = false;
        }
        // Si la cabeza choca el borde superios
        if (y[0] < 0) {
            running = false;
        }

        if (!running) {
            timer.stop();
        }
    }

    private void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(getFont());
        int swidth = metrics.stringWidth("Game Over");
        g.drawString("Game Over", (WIDTH - swidth) / 2 - 140, HEIGHT / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Evento constantes
        if (running) {
            mover();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }

    }

}
