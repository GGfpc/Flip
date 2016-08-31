package com.jtbgame;

import java.util.Arrays;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by ggarc_000 on 26/08/2016.
 */
public class Lane {

    int position;
    private Scrollable item;
    GameWorld world;
    int id;
    static int[] previous;
    int i;

    public Lane(int position, GameWorld world, int id) {
        this.position = position;
        this.world = world;
        this.id = id;
        previous = new int[2];
    }

    public boolean isEmpty(){
        if(item != null){
            return false;
        }
        return true;
    }

    public void update(){
    }

    public void setItem(Scrollable item){
        shleft(previous);
        previous[previous.length - 1] = id;
        System.out.println(Arrays.toString(previous));

        this.item = item;
        item.isOnScreen = true;
        item.position.y = position;
        world.activeObjects++;
    }

    public void removeItem(){
        System.out.println("REM");
        item = null;
        world.activeObjects--;
    }

    public Scrollable getItem(){
        return item;
    }

    private void shleft(int[] arr){
        arr[0] = arr[1];
        arr[1] = 100;
    }
}
