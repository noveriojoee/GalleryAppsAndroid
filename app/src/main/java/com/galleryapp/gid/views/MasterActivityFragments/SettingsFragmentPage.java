package com.galleryapp.gid.views.MasterActivityFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.galleryapp.gid.Config.Config;
import com.galleryapp.gid.gidgalleryapp.R;
import com.galleryapp.gid.models.ContactListModel;
import com.galleryapp.gid.models.ContactModel;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by noverio.joe on 3/6/17.
 */

@SuppressLint("ValidFragment")
public class SettingsFragmentPage extends Fragment {

    public static final String TAG = "SettingsFragmentPage.java";
    private View rootView;
    private AppCompatActivity invokerActivity;
    private ContactListModel contactList;
    private Button btnAddContact;
    private EditText tbName;
    private EditText tbPhoneNumber;
    private EditText tbDeviceID;
    private Firebase cloudReferences;

    public SettingsFragmentPage(AppCompatActivity activity){
        this.invokerActivity = activity;
        this.contactList = new ContactListModel();
    }

    private void InitializeEventListener(){
        this.tbName = (EditText)this.rootView.findViewById(R.id.tbContactName);
        this.tbPhoneNumber = (EditText)this.rootView.findViewById(R.id.tbPhoneNumber);
        this.btnAddContact = (Button) this.rootView.findViewById(R.id.btnAddContact);
        this.tbDeviceID = (EditText)this.rootView.findViewById(R.id.tbDeviceID);

        this.InitializeValue();

        this.btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactModel dataContact = new ContactModel();
                String  uniqueID = UUID.randomUUID().toString();
                dataContact.setContactId(uniqueID);
                dataContact.setName(tbName.getText().toString());
                dataContact.setPhoneNumber(tbPhoneNumber.getText().toString());
                dataContact.setDeviceID(tbDeviceID.getText().toString());
                dataContact.setContactPhoto("");

                SaveToCloud(dataContact);
            }
        });
    }

    private void InitializeValue(){
        this.tbDeviceID.setText(Config.AndroidID);
    }

    private void SaveToCloud(final ContactModel dataContact){
        try{
            this.cloudReferences = new Firebase(Config.FIREBASE_URL);
            this.cloudReferences.child("contactList_"+dataContact.getDeviceID()).child(dataContact.getContactId()).setValue(dataContact);
            this.cloudReferences.child("contactList_"+Config.AndroidID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.child("contactList_"+Config.AndroidID).getChildren()) {
                        //Getting the data from snapshot
                        ContactModel contactData = postSnapshot.getValue(ContactModel.class);
                        //Adding it to a string
                        String strResult = "Name: "+contactData.getName()+"\nAddress: "+contactData.getPhoneNumber()+"\n\n";
                        //Displaying it on textview
                        Toast.makeText(getContext(), strResult, Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Log.d("SettingFragmentPage", firebaseError.getMessage());
                }
            });
        }
        catch(Exception e){
            Log.d("SettingFragmentPage",e.getMessage());
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_settings_master_activity, container, false);
        this.InitializeEventListener();
        return rootView;
    }

}
