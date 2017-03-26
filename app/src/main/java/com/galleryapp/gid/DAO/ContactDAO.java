package com.galleryapp.gid.DAO;

import com.galleryapp.gid.models.ContactModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by noverio.joe on 3/23/17.
 */

public class ContactDAO implements IMobileDAO{

    private List<ContactModel> listDataContact;


    @Override
    public boolean InsertData(Object Data) {
        try{
            if(this.listDataContact == null){
                this.listDataContact = new ArrayList<ContactModel>();
            }
            ContactModel insertedData = (ContactModel)Data;
            this.listDataContact.add(insertedData);

            return true;
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean UpdateData(Object InsertedData, Object DeletedData) {
        if(this.listDataContact != null){
            this.listDataContact.remove((ContactModel)DeletedData);
            this.listDataContact.add((ContactModel) InsertedData);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean DeleteData(Object DeletedData) {
        if(this.listDataContact != null){
            this.listDataContact.remove((ContactModel)DeletedData);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<Objects> GetListData(Objects Data) {
        return null;
    }

    public List<ContactModel> GetDataContacts(){
        return this.listDataContact;
    }
}
