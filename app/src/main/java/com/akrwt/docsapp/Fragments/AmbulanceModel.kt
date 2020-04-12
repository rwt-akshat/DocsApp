package com.akrwt.docsapp.Fragments


class AmbulanceModel(
    mName: String,
    mAmbulance: String,
    mContact: String
) {

    constructor() : this("", "", "")

    private var name = mName
    private var ambulance = mAmbulance
    private var contact = mContact


    fun getName(): String {
        return name
    }

    fun setName(nName: String) {
        name = nName
    }

    fun getAmb(): String {
        return ambulance
    }

    fun setAmb(am: String) {
        ambulance = am
    }

    fun getContact(): String {
        return contact
    }

    fun setContact(c: String) {
        contact = c
    }


}