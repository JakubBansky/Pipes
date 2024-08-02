package sk.stuba.fei.uim.oop.controls;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.board.Position;
import sk.stuba.fei.uim.oop.game.Game;
import sk.stuba.fei.uim.oop.tiles.HomePipe;
import sk.stuba.fei.uim.oop.tiles.Tile;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class GameLogic extends UniversalAdapter {
    public static final int INITIAL_BOARD_SIZE = 8;
    private JFrame mainGame;
    private Board currentBoard;
    @Getter
    private JLabel levelLabel;
    @Getter
    private JLabel boardSizeLabel;
    private int currentBoardSize;
    @Setter
    private int level;

    public GameLogic(JFrame frame) {
        this.level = 1;
        this.mainGame = frame;
        this.currentBoardSize = INITIAL_BOARD_SIZE;
        this.initializeBoard(this.currentBoardSize);

        this.mainGame.add(this.currentBoard);
        this.currentBoard.addMouseMotionListener(this);
        this.currentBoard.addMouseListener(this);
        this.levelLabel = new JLabel();
        this.boardSizeLabel = new JLabel();
        this.updateSizeInfo();
        this.updateLevelInfo();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(Game.RESTART)) {
            this.restart();
        } else if (e.getActionCommand().equals(Game.CHECK)) {
            this.checkPipes();
        }
        this.mainGame.setFocusable(true);
        this.mainGame.requestFocus();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_R:
                this.restart();
                break;
            case KeyEvent.VK_ESCAPE:
                this.mainGame.dispose();
                break;
            case KeyEvent.VK_ENTER:
                this.checkPipes();
                break;
        }
        this.mainGame.setFocusable(true);
        this.mainGame.requestFocus();
    }


    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting()) {
            this.currentBoardSize = ((JSlider) e.getSource()).getValue();
            this.updateSizeInfo();
            this.restart();
            this.mainGame.setFocusable(true);
            this.mainGame.requestFocus();
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        setTurnedGood(false);
        this.mainGame.repaint();
    }


    @Override
    public void mouseMoved(MouseEvent e) {
        Component current = e.getComponent();
        while (current != null && !(current instanceof Tile)) {
            current = current.getParent();
        }
        if (current == null) {
            return;
        }
        ((Tile) current).setHighlight(true);
        mainGame.repaint();
    }


    private void initializeBoard(int size) {
        this.currentBoard = new Board(size);
        this.currentBoard.addMouseMotionListener(this);
        this.currentBoard.addMouseListener(this);
        Tile[][] b = currentBoard.getBoard();
        for (int row = 0; row < currentBoardSize; row++) {
            for (int col = 0; col < currentBoardSize; col++) {

                b[row][col].addMouseListener(this);
                b[row][col].addMouseMotionListener(this);
            }
        }
    }

    private void updateSizeInfo() {
        this.levelLabel.setText("Board size is " + this.currentBoardSize);
        this.mainGame.revalidate();
        this.mainGame.repaint();

    }

    private void updateLevelInfo() {
        this.boardSizeLabel.setText("Current level is " + this.level);
        this.mainGame.revalidate();
        this.mainGame.repaint();

    }

    private void restart() {
        this.mainGame.remove(this.currentBoard);
        this.initializeBoard(this.currentBoardSize);
        this.mainGame.add(this.currentBoard);
        this.setLevel(1);
        this.updateLevelInfo();
        this.updateSizeInfo();
        this.mainGame.setFocusable(true);
        this.mainGame.requestFocus();

    }

    private void checkPipes() {
        boolean flag = false;
        Tile[][] board = currentBoard.getBoard();

        setTurnedGood(false);
        int startX = 0;
        int startY = 0;
        int nextX = startX;
        int nextY = startY;
        ArrayList<Position> positions;
        for (int i = 0; i < board.length; i++) {
            if (board[i][0] instanceof HomePipe) {
                startX = i;
                break;
            }
        }
        while (true) {
            positions = board[startX][startY].getPositions();
            board[startX][startY].setTurnedGood(true);
            for (Position position : positions) {
                if (position.equals(Position.LEFT) && startY - 1 >= 0) {
                    nextY = startY - 1;
                    for (Position positionNeighbour : board[startX][startY - 1].getPositions()) {
                        if (positionNeighbour.equals(Position.RIGHT)) {
                            flag = true;
                            board[startX][startY - 1].delPosition(Position.RIGHT);
                            break;
                        }
                    }

                } else if (position.equals(Position.RIGHT) && startY + 1 < currentBoardSize) {
                    nextX = startX;
                    nextY = startY + 1;
                    for (Position positionNeighbour : board[startX][startY + 1].getPositions()) {
                        if (positionNeighbour.equals(Position.LEFT)) {
                            flag = true;
                            board[startX][startY + 1].delPosition(Position.LEFT);
                            break;
                        }
                    }

                } else if (position.equals(Position.TOP) && startX - 1 >= 0) {
                    nextX = startX - 1;
                    for (Position positionNeighbour : board[startX - 1][startY].getPositions()) {
                        if (positionNeighbour.equals(Position.BOTTOM)) {

                            flag = true;
                            board[startX - 1][startY].delPosition(Position.BOTTOM);
                            break;
                        }
                    }

                } else if (position.equals(Position.BOTTOM) && startX + 1 < currentBoardSize) {
                    nextX = startX + 1;
                    for (Position positionNeighbour : board[startX + 1][startY].getPositions()) {
                        if (positionNeighbour.equals(Position.TOP)) {

                            flag = true;
                            board[startX + 1][startY].delPosition(Position.TOP);
                            break;
                        }
                    }
                }
                if (nextY != startY || nextX != startX) {
                    startX = nextX;
                    startY = nextY;
                }
            }
            if (!flag) {

                break;
            } else {
                flag = false;
            }
        }

        mainGame.repaint();


        if (board[nextX][nextY].getPositions().isEmpty() && nextY == currentBoardSize - 1) {
            this.mainGame.remove(this.currentBoard);
            this.initializeBoard(this.currentBoardSize);
            this.mainGame.add(this.currentBoard);
            this.setLevel(++this.level);
            this.updateLevelInfo();
            this.updateSizeInfo();
            this.mainGame.setFocusable(true);
            this.mainGame.requestFocus();
        }
    }

    private void setTurnedGood(boolean bool) {
        for (Tile[] tiles : this.currentBoard.getBoard()) {
            for (Tile tile : tiles) {
                tile.setTurnedGood(bool);
            }
        }
    }



}
