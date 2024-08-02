package sk.stuba.fei.uim.oop.tiles;

import sk.stuba.fei.uim.oop.board.Position;

import java.awt.*;



public class CornerPipe extends Tile {

    @Override
    protected void paintTile(Graphics g) {
        if (degrees == 0) {
            this.positions.clear();
            this.positions.add(Position.BOTTOM);
            this.positions.add(Position.RIGHT);

        } else if (degrees == 270) {
            this.positions.clear();
            this.positions.add(Position.RIGHT);
            this.positions.add(Position.TOP);
        } else if (degrees == 180) {
            this.positions.clear();
            this.positions.add(Position.LEFT);
            this.positions.add(Position.TOP);
        } else {
            this.positions.clear();
            this.positions.add(Position.LEFT);
            this.positions.add(Position.BOTTOM);
        }

        g.fillRect((int) (getWidth() * 0.30), (int) (getHeight() * 0.30), (int) (getWidth() * 0.4), (int) (getHeight() * 0.70));
        g.fillRect((int) (getWidth() * 0.70), (int) (getHeight() * 0.30), (int) (getWidth() * 0.30), (int) (getHeight() * 0.40));

    }

}
