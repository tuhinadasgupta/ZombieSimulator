package cs2113.zombies;

import cs2113.util.Helper;
import java.awt.*;

class Zombie {
    int x;
    int y;
    private City city;
    private int d; //direction

    //this constructor used when creating first zombie
    Zombie(int x, int y, City city){
        this.x = x;
        this.y =y;
        this.city = city;
        d = Helper.nextInt(4);
    }
    //this constructor used when converting humans to zombies
    Zombie(int x, int y, City city, int d){
        this.x = x;
        this.y =y;
        this.city = city;
        this.d = d;
    }

    void update(){
        moveZombie();
        chaseHuman();
    }

    //chase humanssss
    private void chaseHuman(){
        int size1 = city.arrList.size();
        for(int i=0; i<size1; i++){
            Human currHuman = city.arrList.get(i);
            //human within 10 and human facing same way as zombie
            if (currHuman.checkHuman(x,y) && (d == currHuman.getDirection()) ){
                //move one square toward human
                int newX=x;
                int newY=y;
                 if (y == currHuman.y) {
                     if (x > currHuman.x) {
                         newX = x -1;
                         newY = y;
                     }
                     else {
                         newX = x +1;
                         newY = y;
                     }
                 }
                 if (x == currHuman.x) {
                     if (y > currHuman.y) {
                          newY = y -1;
                          newX = x;
                     }
                     else {
                         newY = y +1;
                         newX = x;
                     }
                 }
                 if (city.checkDimensions(newX,newY) ==1 && city.wallCheck(newX,newY) ==1) {
                     x = newX;
                     y = newY;
                 }
            }
        }
    }
    //direction getter
    int getDirection(){
        return d;
    }

    //check for presence of zombie within 10 squares and facing same direction
    boolean checkZombie(int dir, int a, int b){
        //a,b coords of human dir direction of human
        if((dir == d) && (Math.abs(a-x) == 10) && (Math.abs(b-y) == 10)) {
            return true;
        }
        else{
            return false;
        }
    }
    //move zombieeee
    private void moveZombie(){
        int r = Helper.nextInt(101);    //random movement percentage
        ZombieSim.dp.setPenColor(Color.RED);
        int newX = x;
        int newY = y;
        //change direction (20%)
        if (r>=0 && r<=20){
            d = Helper.nextInt(4);
            //left
            if (d == 0) {
                newX = x-1;
            }
            //up
            if(d==1){
                newY = y-1;
            }
            //right
            if (d==2){
                newX = x+1;
            }
            //down
            if (d==3){
                newY = y+1;
            }
            //valid next coordinates?
            if (city.checkDimensions(newX,newY) ==1 &&city.wallCheck(newX,newY) ==1){
                x = newX;
                y = newY;
            }
            else{
                d = Helper.nextInt(4);
            }
        }
        //don't change direction (80%)
        else{
            //left
            if (d == 0) {
                newX = x-1;
            }
            //up
            if(d==1){
                newY = y-1;
            }
            //right
            if (d==2){
                newX = x+1;
            }
            //down
            if (d==3){
                newY = y+1;
            }
            if (city.checkDimensions(newX,newY) ==1 && city.wallCheck(newX,newY) ==1){
                x = newX;
                y = newY;
            }
            else{
                d = Helper.nextInt(4);
            }

        }
    }
}


