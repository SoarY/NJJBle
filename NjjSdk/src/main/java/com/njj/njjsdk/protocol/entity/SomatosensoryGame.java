package com.njj.njjsdk.protocol.entity;

/**
 * @ClassName SomatosensoryGame
 * @Description TODO
 * @Author LibinFan
 * @Date 2023/2/2 16:49
 * @Version 1.0
 */
public class SomatosensoryGame {
    public  short  	x;					//2byte
    public  short 	y;					//2byte
    public  short 	Speed;				//2byte
    public  short 	X_Throw;			//2byte
    public  short 	Y_Throw;			//2byte
    public  short 	Speed_Throw;		//2byte
    public  short   Count_Throw;		//1byte
    public  short	test;              //预留1byte
    public  short 	X_Move_Distance;	//2byte
    public  short 	Y_Move_Distance;	//2byte
    public  short 	X_gravity;			//2byte
    public  short 	Y_gravity;			//2byte
    public  short 	Z_gravity;			//2byte

    @Override
    public String toString() {
        return "SomatosensoryGame{" +
                "x=" + x +
                ", y=" + y +
                ", Speed=" + Speed +
                ", X_Throw=" + X_Throw +
                ", Y_Throw=" + Y_Throw +
                ", Speed_Throw=" + Speed_Throw +
                ", Count_Throw=" + Count_Throw +
                ", test=" + test +
                ", X_Move_Distance=" + X_Move_Distance +
                ", Y_Move_Distance=" + Y_Move_Distance +
                ", X_gravity=" + X_gravity +
                ", Y_gravity=" + Y_gravity +
                ", Z_gravity=" + Z_gravity +
                '}';
    }
}
