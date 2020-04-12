package com.akrwt.docsapp


class AccidentModel(
    minjDetail: String,
    mName: String,
    mSex: String,
    mAge: String,
    mContact: String,
    mBG: String,
    mImageUrl: String
) {

    constructor() : this("","", "", "", "", "", "")

    private var inj = minjDetail
    private var name = mName
    private var fileUrl = mImageUrl

    private var age = mAge
    private var sex = mSex
    private var bloodgrp = mBG
    private var phoneNumber = mContact


    fun getInj(): String {
        return inj
    }

    fun setInj(i: String) {
        inj = i
    }

    fun getName(): String {
        return name
    }

    fun setName(nName: String) {
        name = nName
    }

    fun getUrl(): String {
        return fileUrl
    }

    fun setUrl(iUrl: String) {
        fileUrl = iUrl
    }

    fun getAge(): String {
        return age
    }

    fun setInjury(ag: String) {
        age = ag
    }

    fun getSex(): String {
        return sex
    }

    fun setSex(sexx: String) {
        sex = sexx
    }

    fun getBG(): String {
        return bloodgrp
    }

    fun setBG(bg: String) {
        bloodgrp = bg
    }

    fun getphnNumber(): String {
        return phoneNumber
    }

    fun setphnNumber(p: String) {
        phoneNumber = p
    }


}