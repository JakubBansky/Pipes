package sk.stuba.fei.uim.oop.tiles;

import sk.stuba.fei.uim.oop.board.Position;

import java.awt.*;

public class HomePipe extends Tile {

    @Override
    protected void paintTile(Graphics g) {
        if (degrees == 0) {
            this.positions.clear();
            this.positions.add(Position.RIGHT);
        } else if (degrees == 270) {
            this.positions.clear();
            this.positions.add(Position.TOP);
        } else if (degrees == 180) {
            this.positions.clear();
            this.positions.add(Position.LEFT);
        } else {
            this.positions.clear();
            this.positions.add(Position.BOTTOM);
        }


        g.fillRect((int) (getWidth() * 0.5), (int) (getHeight() * 0.3), (int) (getWidth() * 0.5), (int) (getHeight() * 0.4));
        g.fillRect((int) (getWidth() * 0.1), (int) (getHeight() * 0.1), (int) (getWidth() * 0.5), (int) (getHeight() * 0.8));
    }
}
