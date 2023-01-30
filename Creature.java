public abstract class Creature {
    int posX;
    int posY;
    int health;
    int power;

    public void attack(Creature c){
        //complete this method
        if (isInRange(posX, posY))
            c.health = c.health - c.power;
    }

    public abstract boolean isInRange(int targetPosX, int targetPosY);

    public void move (int x, int y){
        //complete this method
        this.posX = posX + x;
        this.posY = posY + y;
    }

    public boolean isDefeated (){
        //complete this method
        if (health <= 0)
            return true;
        else
            return false;
    }
}
