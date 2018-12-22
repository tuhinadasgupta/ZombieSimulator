package cs2113.zombies;

import cs2113.util.Helper;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

class Human {
    int x;
    int y;
    private City city;
    private int d; //direction

    Human(int x, int y, City city){
        this.x = x;
        this.y =y;
        this.city = city;
        d = Helper.nextInt(4);
    }
    boolean update(){
        moveHuman();
        runHuman();
        return removeHuman();
    }
    private boolean checkAdjacent(int a, int b){
        //zombie x,y = a,b
        boolean bool;
        //x&y within distance of 1
        if (Math.abs((x-a)) <= 1 && Math.abs((y-b)) <= 1){
            bool = true;
        }
        else{
            bool = false;
        }
        return bool;
    }
    //marks the about to be infected humans by returning boolean true
    //in the city's update function the humans are actually removed from the human array list
    private boolean removeHuman(){
        boolean bool = false;
        int size = city.arrList2.size();
        for (int i =0; i<size; i++){
            Zombie zomb = city.arrList2.get(i);
            if (checkAdjacent(zomb.x,zomb.y)){
                bool = true;
            }
        }
       return bool;
    }
    //reaction to detection of zombie
    private void runHuman(){
        //zombie within 10 squares move 2 squares in opposite direction
        int size2 = city.arrList2.size();
        for(int i=0; i<size2; i++) {
            Zombie currZombie = city.arrList2.get(i);
            //human within 10 and human facing same way as zombie
            int newX = x;
            int newY = y;
            if (currZombie.checkZombie(d, x, y)) {
                //move two square away from zombie
                //no wall
                if (y == currZombie.y) {
                    //opposite direction
                    d = Math.abs(currZombie.getDirection() - 2);
                    if (x > currZombie.x) {
                        newX = x + 2;
                    } else {
                        newX = x - 2;
                    }
                }
                if (x == currZombie.x) {
                    //opposite direction
                    d = Math.abs(currZombie.getDirection() - 2);
                    if (y > currZombie.y) {
                        newY = x + 2;
                    } else {
                        newY = x - 2;
                    }
                }
                if (city.checkDimensions(newX,newY) ==1 && city.wallCheck(newX,newY) ==1) {
                    x = newX;
                    y = newY;
                }
            }
        }
    }
    //check if human is within 10 squares of given zombie coords
    boolean checkHuman(int a, int b){
        //a,b are zombie coords
        if (Math.abs((a-x)) <= 10 && Math.abs((b-y)) <= 10){
            return true;
        }
        else{
            return false;
        }
    }
    //regular move method
    private void moveHuman(){
        int r = Helper.nextInt(101);    //random movement percentage
        ZombieSim.dp.setPenColor(Color.WHITE);
        int newX = x;
        int newY = y;
        //change direction (10%)
        if (r>=0 && r<=10){
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
        //don't change direction (90%)
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
    //direction getter
    int getDirection(){
       return d;
    }
}

