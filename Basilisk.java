public class Basilisk extends Creature {

    public Basilisk (int posX, int posY){
        //complete constructor
        this.posX = posX;
        this.posY = posY;
        health = 200;
        power = 30;
    }

    @Override
    public boolean isInRange(int targetPosX, int targetPosY) {
        //complete this method
        if (targetPosX <= posX+2 && targetPosX >= posX-2)
            if (targetPosY== posY+2)
                return true;
        return false;
    }

}
