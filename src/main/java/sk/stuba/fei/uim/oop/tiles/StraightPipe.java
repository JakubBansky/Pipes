package sk.stuba.fei.uim.oop.tiles;

import sk.stuba.fei.uim.oop.board.Position;


import java.awt.*;

public class StraightPipe extends Tile {
    @Override
    protected void paintTile(Graphics g) {
        if (degrees == 90 || degrees == 270) {
            this.positions.clear();
            this.positions.add(Position.BOTTOM);
            this.positions.add(Position.TOP);

        } else {
            this.positions.clear();
            this.positions.add(Position.LEFT);
            this.positions.add(Position.RIGHT);
        }
        g.fillRect(0, (int) (getHeight() * 0.3), getWidth(), (int) (getHeight() * 0.4));
    }
}
