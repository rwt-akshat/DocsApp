package com.akrwt.docsapp


class DoctorModel(mName:String,
                    mInjury:String,
                    mBG:String,
                    mSex:String,
                    mPhoneNumber:String,
                    mImageUrl:String) {

    constructor():this("","","","","","")

    private var name=mName
    private var fileUrl=mImageUrl
    private var injury=mInjury
    private var sex=mSex
    private var bloodgrp=mBG
    private var phoneNumber=mPhoneNumber



    fun getName():String{
        return name
    }
    fun setName(nName:String){
        name=nName
    }
    fun getUrl():String{
        return fileUrl
    }
    fun setUrl(iUrl:String){
        fileUrl=iUrl
    }

    fun getInjury():String{
        return injury
    }
    fun setInjury(iin:String){
        injury=iin
    }

    fun getSex():String{
        return sex
    }
    fun setSex(sexx:String){
        sex=sexx
    }

    fun getBG():String{
        return bloodgrp
    }
    fun setBG(bg:String){
        bloodgrp=bg
    }

    fun getphnNumber():String{
        return phoneNumber
    }
    fun setphnNumber(p:String){
        phoneNumber=p
    }




}