public class Goblin extends Creature {

    int health;
    int power;
    public Goblin(int posX, int posY){
        //complete constructor
        this.posX = posX;
        this.posY = posY;
        health = 80;
        power = 15;
    }

    @Override
    public boolean isInRange(int targetPosX, int targetPosY) {
        //complete this method
        // equation of circle
        // if radius less than distance of object, then out of range
        // if less, then in range.
        int distanceX = posX - targetPosX;
        int distanceY = posY - targetPosY;
        double distance = Math.sqrt((distanceY*distanceY) + (distanceX * distanceX));
        if (distance <= 3)
                return true;
        return false;
    }
}
