package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.Statement;


public class addcourse extends AppCompatActivity {
    ProgressDialog progressDialog;
    ConnectionClass connectionClass;

     EditText courseid , coursename , duration , fees;
     public Button btncoursereg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcourse);

        courseid = (EditText)findViewById(R.id.courseid);
        coursename = (EditText)findViewById(R.id.coursename);
        duration=(EditText)findViewById(R.id.courseduration);
        fees=(EditText)findViewById(R.id.coursefees);
        btncoursereg=(Button)findViewById(R.id.btncoursereg);

        connectionClass = new ConnectionClass();
        progressDialog = new ProgressDialog(this);
        btncoursereg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Coursereg coursereg = new Coursereg();
                coursereg.execute("");
            }
        });


    }
    public class Coursereg extends AsyncTask<String,String,String>

    {
        String putcourseid=courseid.getText().toString();
        String putcoursename=coursename.getText().toString();
        int putduration = Integer.parseInt( duration.getText().toString() );
        int putfees = Integer.parseInt( fees.getText().toString() );
        String z="";
        boolean isSuccess=false;

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            if(putcourseid.trim().equals("")|| putcoursename.trim().equals(""))
                z = "Please enter all fields....";
            else
            {
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Please check your internet connection";
                    } else {

                        String query="insert into course values('"+putcourseid+"','"+putcoursename+"','"+putduration+"','"+putfees+"')";

                        Statement stmt = con.createStatement();
                        stmt.executeUpdate(query);

                        z = "Register successfull";
                        isSuccess=true;


                    }
                }
                catch (Exception ex)
                {
                    isSuccess = false;
                    z = "Exceptions"+ex;
                }
            }
            return z;
        }

        @Override
        protected void onPostExecute(String s) {

            Toast.makeText(getBaseContext(),""+z, Toast.LENGTH_LONG).show();


            if(isSuccess) {
                startActivity(new Intent(getApplicationContext(),admin.class));

            }


            progressDialog.hide();
        }
    }
}
