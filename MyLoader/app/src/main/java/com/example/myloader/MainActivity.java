package com.example.myloader;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemClickListener {
    public static final String TAG = "ContactApp";
    ListView lvContact;
    ProgressBar progressBar;
    private ContactAdapter mAdapter;
    private final int CONTACT_REQUEST_CODE = 101;
    private final int CALL_REQUEST_CODE = 102;
    private final int CONTACT_LOAD = 110;
    private final int CONTACT_SELECT = 120;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvContact = findViewById(R.id.lv_contact);
        progressBar = findViewById(R.id.progress_bar);
        lvContact.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.GONE);
        mAdapter = new ContactAdapter(this,null,true);
        lvContact.setAdapter(mAdapter);
        lvContact.setOnItemClickListener(this);
        if(PermissionManager.isGranted(this, Manifest.permission.READ_CONTACTS)){
            getSupportLoaderManager().initLoader(CONTACT_LOAD,null,this);
        }else {
            PermissionManager.check(this,Manifest.permission.READ_CONTACTS,
                    CONTACT_REQUEST_CODE);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Cursor cursor = (Cursor) adapterView.getAdapter().getItem(i);
        long mContactId = cursor.getLong(0);
        getPhoneNumber(String.valueOf(mContactId));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CONTACT_REQUEST_CODE) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getSupportLoaderManager().initLoader(CONTACT_LOAD, null, this);
                    Toast.makeText(this, "Contact permission diterima", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Contact permission ditolak", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == CALL_REQUEST_CODE) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Call permission diterima", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Call permission ditolak", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void getPhoneNumber(String contactId){
        Bundle bundle = new Bundle();
        bundle.putString("id",contactId);
        getSupportLoaderManager().restartLoader(CONTACT_SELECT,bundle,this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        CursorLoader mCursorLoader = null;
        if(id == CONTACT_LOAD){
            progressBar.setVisibility(View.VISIBLE);
            String[] projectionFields = new String[]{
                    ContactsContract.Contacts._ID,
                    ContactsContract.Contacts.DISPLAY_NAME,
                    ContactsContract.Contacts.PHOTO_URI
            };
            mCursorLoader = new CursorLoader(MainActivity.this,
                    ContactsContract.Contacts.CONTENT_URI,
                    projectionFields,
                    ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1",
                    null,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC");
        }else if(id == CONTACT_SELECT){
            String[] projectionField = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
            mCursorLoader = new CursorLoader(MainActivity.this,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    projectionField,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID+" = ? AND " +
                            ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                    ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE+ " AND "+
                    ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER+"=1",
                    new String[]{args.getString("id")},null);
        }
        return mCursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if(loader.getId()==CONTACT_LOAD){
            if(data.getCount()>0){
                lvContact.setVisibility(View.VISIBLE);
                mAdapter.swapCursor(data);
            }
            progressBar.setVisibility(View.GONE);
        }else if(loader.getId()==CONTACT_SELECT){
            String contactNumber = null;
            if(data.moveToFirst()){
                contactNumber = data.getString(data.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            }
            if(PermissionManager.isGranted(this,Manifest.permission.CALL_PHONE)){
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+contactNumber));
                startActivity(dialIntent);
            }else{
                PermissionManager.check(this,Manifest.permission.CALL_PHONE,CALL_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        if(loader.getId()==CONTACT_LOAD){
            progressBar.setVisibility(View.GONE);
            mAdapter.swapCursor(null);
        }
    }
}
