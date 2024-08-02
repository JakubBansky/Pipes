package sk.stuba.fei.uim.oop.board;

import lombok.Getter;
import sk.stuba.fei.uim.oop.tiles.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

public class Board extends JPanel {

    @Getter
    private Tile[][] board;

    private int[][] maze;

    public Board(int size) {
        this.maze = solveMaze(size);
        this.initializeBoard(size);

        this.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        this.setBackground(Color.GRAY);

    }

    private void initializeBoard(int size) {
        this.board = new Tile[size][size];

        this.setLayout(new GridLayout(size, size));
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (maze[i][j] == 4) {
                    this.board[i][j] = new StraightPipe();
                    this.add(this.board[i][j]);
                } else if (maze[i][j] == 3) {
                    this.board[i][j] = new CornerPipe();
                    this.add(this.board[i][j]);
                } else if (maze[i][j] == 5) {
                    this.board[i][j] = new HomePipe();
                    this.add(this.board[i][j]);
                    if (j == 0) {
                        this.board[i][j].setBaseColor(Color.GREEN);
                    } else {
                        this.board[i][j].setBaseColor(Color.RED);
                    }
                } else {
                    this.board[i][j] = new Tile();
                    this.add(this.board[i][j]);
                }
            }
        }
    }

    private int[][] genMaze(int size) {
        int cooX = (int) (Math.random() * size);
        int cooY = 0;
        int[][] maze = new int[size][size];
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze.length; j++) {
                maze[i][j] = 1;
            }
        }
        Stack<int[]> stack = new Stack<>();
        ArrayList<int[]> neighbours;
        stack.push(new int[]{cooX, cooY});
        maze[cooX][cooY] = 0;

        while (!stack.empty()) {
            neighbours = getNeighbours(maze, cooX, cooY, size);
            if (!neighbours.isEmpty()) {
                stack.push(neighbours.get((int) (Math.random() * neighbours.size())));
                neighbours.clear();
                cooX = stack.peek()[0];
                cooY = stack.peek()[1];
                maze[cooX][cooY] = 0;
            } else {
                cooX = stack.peek()[0];
                cooY = stack.peek()[1];
                stack.pop();
            }
        }
        return maze;


    }

    private ArrayList<int[]> getNeighbours(int[][] maze, int cooX, int cooY, int size) {
        ArrayList<int[]> neighbours = new ArrayList<>();

        if (cooY - 1 >= 0 && maze[cooX][cooY - 1] != 0) {
            if (neighboursCheck(maze, cooX, cooY - 1, new int[]{cooX, cooY}, size)) {
                neighbours.add(new int[]{cooX, cooY - 1});
            }
        }

        if (cooY + 1 < size && maze[cooX][cooY + 1] != 0) {
            if (neighboursCheck(maze, cooX, cooY + 1, new int[]{cooX, cooY}, size)) {
                neighbours.add(new int[]{cooX, cooY + 1});
            }
        }

        if (cooX - 1 >= 0 && maze[cooX - 1][cooY] != 0) {
            if (neighboursCheck(maze, cooX - 1, cooY, new int[]{cooX, cooY}, size)) {
                neighbours.add(new int[]{cooX - 1, cooY});
            }
        }

        if (cooX + 1 < size && maze[cooX + 1][cooY] != 0) {
            if (neighboursCheck(maze, cooX + 1, cooY, new int[]{cooX, cooY}, size)) {
                neighbours.add(new int[]{cooX + 1, cooY});
            }
        }
        return neighbours;
    }

    private boolean neighboursCheck(int[][] maze, int cooX, int cooY, int[] coord, int size) {
        if (cooX - 1 >= 0 && maze[cooX - 1][cooY] == 0 && (coord[0] != cooX - 1 || coord[1] != cooY)) {
            return false;
        } else if (cooX + 1 < size && maze[cooX + 1][cooY] == 0 && (coord[0] != cooX + 1 || coord[1] != cooY)) {
            return false;
        } else if (cooY - 1 >= 0 && maze[cooX][cooY - 1] == 0 && (coord[0] != cooX || coord[1] != cooY - 1)) {
            return false;
        } else return cooY + 1 >= size || maze[cooX][cooY + 1] != 0 || (coord[0] == cooX && coord[1] == cooY + 1);
    }

    private int[][] solveMaze(int size) {
        int[][] maze = genMaze(size);
        boolean[][] visited = new boolean[size][size];
        int[] chosenNeighbour;
        Stack<int[]> stack = new Stack<>();
        ArrayList<int[]> unvisitedNeighbours;
        int cooY = 0;
        int cooX = -1;
        while (cooX <= 0) {
            int tmp = (int) (Math.random() * size);
            if (maze[tmp][cooY] == 0) {
                cooX = tmp;
            }
        }

        stack.push(new int[]{cooX, cooY});

        visited[cooX][cooY] = true;


        while (!stack.empty()) {
            unvisitedNeighbours = getUnvisitedNeighbours(visited, cooX, cooY, size, maze);
            if (!unvisitedNeighbours.isEmpty()) {
                chosenNeighbour = unvisitedNeighbours.get((int) (Math.random() * unvisitedNeighbours.size()));
                stack.push(chosenNeighbour);
                cooX = chosenNeighbour[0];
                cooY = chosenNeighbour[1];

                visited[cooX][cooY] = true;

            } else {
                if (!stack.empty()) {
                    stack.pop();
                    cooX = stack.peek()[0];
                    cooY = stack.peek()[1];

                }
            }
            if (cooY == size - 1) {
                unvisitedNeighbours = getUnvisitedNeighbours(visited, cooX, cooY, size, maze);
                if (unvisitedNeighbours.isEmpty()) {
                    return pathFinder(stack, maze);
                }

            }
        }
        return pathFinder(stack, maze);
    }

    private ArrayList<int[]> getUnvisitedNeighbours(boolean[][] visited, int cooX, int cooY, int size, int[][] maze) {
        ArrayList<int[]> neighbours = getWay(cooX, cooY, maze, size);
        ArrayList<int[]> unvisitedNeighbours = new ArrayList<>();
        for (int[] neighbour : neighbours) {
            int x = neighbour[0];
            int y = neighbour[1];
            if (!visited[x][y]) {
                unvisitedNeighbours.add(neighbour);
            }
        }
        return unvisitedNeighbours;
    }

    private int[][] pathFinder(Stack<int[]> stack, int[][] maze) {
        int oldX = stack.peek()[0];
        int oldY = stack.peek()[1];
        stack.pop();
        int actualX = stack.peek()[0];
        int actualY = stack.peek()[1];
        int newX, newY;
        maze[oldX][oldY] = 5;
        stack.pop();
        while (!stack.empty()) {
            newX = stack.peek()[0];
            newY = stack.peek()[1];
            stack.pop();
            if (oldX == newX || oldY == newY) {
                maze[actualX][actualY] = 4;
            } else {
                maze[actualX][actualY] = 3;
            }
            oldX = actualX;
            oldY = actualY;
            actualX = newX;
            actualY = newY;
        }
        maze[actualX][actualY] = 5;
        return maze;
    }

    private ArrayList<int[]> getWay(int cooX, int cooY, int[][] maze, int size) {
        {
            ArrayList<int[]> neighbours = new ArrayList<>();
            if (cooX - 1 >= 0 && maze[cooX - 1][cooY] == 0) {
                neighbours.add(new int[]{cooX - 1, cooY});
            }
            if (cooX + 1 < size && maze[cooX + 1][cooY] == 0) {
                neighbours.add(new int[]{cooX + 1, cooY});
            }
            if (cooY - 1 >= 0 && maze[cooX][cooY - 1] == 0) {
                neighbours.add(new int[]{cooX, cooY - 1});
            }
            if (cooY + 1 < size && maze[cooX][cooY + 1] == 0) {
                neighbours.add(new int[]{cooX, cooY + 1});
            }
            return neighbours;
        }
    }


}
