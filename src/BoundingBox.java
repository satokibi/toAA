/**
 * Created by yuya on 2016/10/10.
 */

import java.awt.*;
import javax.swing.*;

class BoundBox {
    private int x, y, vx, vy;
    private int ir,ig,ib;

    public BoundBox() {
        x = BoundingBox.getRand(50,350);
        y = BoundingBox.getRand(50,350);
        vx = BoundingBox.getRand(-5,5);
        vy = BoundingBox.getRand(-5,5);
        ir = BoundingBox.getRand(5,255);
        ig = BoundingBox.getRand(5,255);
        ib = BoundingBox.getRand(5,255);
    }

    public void move() {
        x += vx;
        y += vy;

        if (x < 0 || x > 360)
            vx = -vx;
        if (y < 20 || y > 360)
            vy = -vy;
    }

    public void draw(Graphics g) {
        g.setColor(new Color(ir,ig,ib));
        g.fillRect(x, y, 40, 40);
    }

}


public class BoundingBox extends JFrame implements Runnable {

    private static BoundBox[] box;

    public static int getRand(int min, int max) {
        return (int)(Math.random() * (max-min) + min);
    }

    public static void main(String[] args) {
        new BoundingBox();
    }

    public BoundingBox() {
        box = new BoundBox[32];
        for(int i=0;i<box.length;i++) {
            box[i] = new BoundBox();
        }
        setAlwaysOnTop(true);
        this.setLocation(400, 200);
        this.setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        new Thread(this).start();

    }

    public void paint(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, 400, 400);

        for(int i=0;i<box.length;i++) {
            box[i].move();
            box[i].draw(g);
        }
    }


    public void run() {
        while (true) {
            try {
                Thread.sleep(20);
            } catch (Exception e) {
            }

            repaint();
        }
    }
}
