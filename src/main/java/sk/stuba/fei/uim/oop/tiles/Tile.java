package sk.stuba.fei.uim.oop.tiles;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.board.Position;
import sk.stuba.fei.uim.oop.controls.TileLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;


public class Tile extends JPanel {


    @Setter
    private Color baseColor;

    @Setter
    protected boolean highlight;

    @Setter
    @Getter
    protected int degrees;

    protected TileLogic logic;

    @Setter
    protected boolean turnedGood;

    @Getter
    protected ArrayList<Position> positions;

    public void delPosition(Position pos) {
        positions.remove(pos);
    }

    public Tile() {
        this.baseColor = Color.BLACK;
        this.logic = new TileLogic(this);
        this.positions = new ArrayList<>();
        this.turnedGood = false;
        this.degrees = (int) (Math.random() * 4) * 90;

        this.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
        this.setBackground(Color.YELLOW);
        this.addMouseListener(logic);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        AffineTransform transform = AffineTransform.getRotateInstance(Math.toRadians(degrees), getWidth() / 2.0, getHeight() / 2.0);
        g2d.transform(transform);
        if (highlight) {
            g.setColor(Color.RED);
            ((Graphics2D) g).setStroke(new BasicStroke(5));
            g.drawRect((int) (getWidth() * 0.02), (int) (getHeight() * 0.02), (int) (getWidth() * 0.98), (int) (getHeight() * 0.98));
            highlight = false;
            g.setColor(baseColor);
        }
        if (turnedGood) {
            g2d.setColor(Color.BLUE);
        } else {
            g2d.setColor(baseColor);
        }

        paintTile(g2d);

        g2d.dispose();

    }

    protected void paintTile(Graphics g) {

    }

}
