package Game;

class Map{
    private int code_wall;
    private int code_dot;
    private int Size_Maze [][] = new int[11][11];
    private int code_dot_finish;
    public Player player;

    Map(){
        code_wall = 1;
        code_dot = 0;
        code_dot_finish = 2;
        int Size_Maze[][]= {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1},
                {1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
                {1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1},
                {1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 1, 2, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };
        this.Size_Maze = Size_Maze;
        player = new Player(1, 9);
    }
    public boolean isPath (int x, int y){
        return Size_Maze[y][x] == code_dot || Size_Maze[y][x] == code_dot_finish;
    }
    public boolean isWall (int x, int y){
        return Size_Maze[y][x] == code_wall;
    }
    public int getMapSize(){
        return Size_Maze.length;
    }
    public boolean isWin(){
        return Size_Maze[player.getY()][player.getX()] == code_dot_finish;
    }
    public boolean isLose(){
        return player.isOver();
    }
    public boolean HitTheWall(){
        return isWall(player.getX(), player.getY());


    }

    public void Notification(){
            player.lost();

    }

    public boolean movePlayerUp() {
        if (player.getY() > 0 && isPath(player.getX() , player.getY()-1)) {
            player.MoveUP();
            return true;
        }
        else Notification();
        return false;
    }
    public boolean movePlayerDown() {
        if (player.getY() < getMapSize() - 1 && isPath(player.getX() , player.getY()+1)) {
            player.MoveDown();
            return true;
        }
        else Notification();
        return false;

    }
    public boolean movePlayerLeft() {
        if (player.getX() > 0 && isPath(player.getX()-1, player.getY())) {
            player.MoveLeft();
            return true;
        }
        else Notification();
        return false;
    }
    public boolean movePlayerRight() {
        if (player.getX() < getMapSize() - 1 && isPath(player.getX()+1, player.getY())) {
            player.MoveRight();
            return true;
        }
        else Notification();
        return false;
    }
}