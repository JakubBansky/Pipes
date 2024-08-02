package sk.stuba.fei.uim.oop.controls;

import sk.stuba.fei.uim.oop.tiles.Tile;

import java.awt.event.MouseEvent;

public class TileLogic extends UniversalAdapter{
    private Tile tile;
    public TileLogic(Tile tile) {
        this.tile = tile;
    }
    @Override
    public void mousePressed(MouseEvent e) {
        tile.setDegrees((tile.getDegrees()+90)%360);
        tile.setHighlight(true);
        tile.repaint();
    }
}
