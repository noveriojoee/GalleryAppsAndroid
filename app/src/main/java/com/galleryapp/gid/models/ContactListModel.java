package com.galleryapp.gid.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by noverio.joe on 3/23/17.
 */

public class ContactListModel {
    public List<ContactModel> getListOfContact() {
        return ListOfContact;
    }

    public void setListOfContact(List<ContactModel> listOfContact) {
        ListOfContact = listOfContact;
    }

    private List<ContactModel> ListOfContact;


    public ContactListModel(){
        this.ListOfContact = new ArrayList<ContactModel>();
    }
}
