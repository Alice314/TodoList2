package com.wusui.todolist;

/**
 * Created by fg on 2016/2/26.
 */
public class Function {
    private String name;
    private int imageId;

    public Function(){}
    public Function(String name,int imageId){
        this.name = name;
        this.imageId = imageId;
    }
    public void setName(){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public void setImageId(){
        this.imageId = imageId;
    }
    public int getImageId(){
        return imageId;
    }
}
