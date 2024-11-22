package Game;
class Player {
    private int Chance;
    private int Position_X;
    private int Position_Y;

    Player(int StartX, int StartY){
        Chance = 3;
        Position_X = StartX;
        Position_Y = StartY;
    }
    public void lost(){
        Chance--;
    }
    public boolean isOver(){
        return Chance<=0;
    }
    public int getX(){
        return Position_X;
    }
    public int getY(){
        return Position_Y;
    }
    public void MoveUP(){
        Position_Y--;
    }
    public void MoveDown(){
        Position_Y++;
    }
    public void MoveRight(){
        Position_X++;
    }
    public void MoveLeft(){
        Position_X--;
    }
}




