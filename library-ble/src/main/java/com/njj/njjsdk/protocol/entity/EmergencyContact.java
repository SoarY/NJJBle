package com.njj.njjsdk.protocol.entity;

/**
 * 紧急联系人
 *
 * @ClassName EmergencyContact
 * @Description TODO
 * @Author Darcy
 * @Date 2021/8/12 19:58
 * @Version 1.0
 */
public class EmergencyContact {

    private int contactId;//联系人id  0-7

    private String contactName;//联系人姓名

    private String phoneNumber;//手机号码


    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
