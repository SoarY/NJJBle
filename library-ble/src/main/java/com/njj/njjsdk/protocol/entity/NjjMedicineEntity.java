package com.njj.njjsdk.protocol.entity;

/**
 * @ClassName MedicineEntity
 * @Description TODO
 * @Author LibinFan
 * @Date 2022/11/3 14:03
 * @Version 1.0
 */
public class NjjMedicineEntity {
    private int  medicineStatus;
    private int medicineWeek;
    private int medicineTime1;
    private int medicineTime2;
    private int medicineTime3;


    public int getMedicineStatus() {
        return medicineStatus;
    }

    public void setMedicineStatus(int medicineStatus) {
        this.medicineStatus = medicineStatus;
    }

    public int getMedicineWeek() {
        return medicineWeek;
    }

    public void setMedicineWeek(int medicineWeek) {
        this.medicineWeek = medicineWeek;
    }

    public int getMedicineTime1() {
        return medicineTime1;
    }

    public void setMedicineTime1(int medicineTime1) {
        this.medicineTime1 = medicineTime1;
    }

    public int getMedicineTime2() {
        return medicineTime2;
    }

    public void setMedicineTime2(int medicineTime2) {
        this.medicineTime2 = medicineTime2;
    }

    public int getMedicineTime3() {
        return medicineTime3;
    }

    public void setMedicineTime3(int medicineTime3) {
        this.medicineTime3 = medicineTime3;
    }

    @Override
    public String toString() {
        return "NjjMedicineEntity{" +
                "medicineStatus=" + medicineStatus +
                ", medicineWeek=" + medicineWeek +
                ", medicineTime1=" + medicineTime1 +
                ", medicineTime2=" + medicineTime2 +
                ", medicineTime3=" + medicineTime3 +
                '}';
    }
}
