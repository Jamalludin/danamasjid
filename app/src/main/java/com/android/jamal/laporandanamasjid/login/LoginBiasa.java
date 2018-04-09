package com.android.jamal.laporandanamasjid.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.jamal.laporandanamasjid.MainActivity;
import com.android.jamal.laporandanamasjid.R;
import com.android.jamal.laporandanamasjid.jsonReq.JsonReq;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginBiasa extends AppCompatActivity {

    protected EditText username, password;
    protected Button masuk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_biasa);

        username = (EditText)findViewById(R.id.input_nim2);
        password =(EditText)findViewById(R.id.input_password2);

        username.setText("admin");
        password.setText("admin");

        masuk = (Button)findViewById(R.id.btn_signup2);
        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nim = username.getText().toString();
                String passw = password.getText().toString();

                Toast.makeText(LoginBiasa.this, username.getText().toString(), Toast.LENGTH_SHORT).show();

                new LoginApp(nim,passw).execute();
            }
        });
    }

    class LoginApp extends AsyncTask<Void,Void,String>{

        public String pengguna, passw;

        public LoginApp(String pengguna, String passw) {
            this.pengguna = pengguna;
            this.passw = passw;
        }

        @Override
        protected String doInBackground(Void... voids) {

            JsonReq jsonReq = new JsonReq();
            String hasil = jsonReq.login(this.pengguna, this.passw);
            return hasil;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                JSONObject obj = new JSONObject(s);
                String hasil = obj.getString("hasil");
                if (hasil.equals("sukses")){
                    JSONObject array = obj.getJSONObject("data");
                    new Session(getApplicationContext()).userLogin(this.pengguna, this.passw,
                            array.getString("nama"),
                            array.getString("jk"),
                            array.getString("alamat"),
                            array.getString("telp"),
                            array.getString("role"));

                    if (new Session(getApplicationContext()).getRole().equals("admin")){
                        Intent i = new Intent(LoginBiasa.this, MainActivity.class);
                        startActivity(i);
                    }
                }

            }catch (JSONException e){

                /*Toast.makeText(LoginBiasa.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();*/
            }

            Toast.makeText(LoginBiasa.this, s.toString(), Toast.LENGTH_SHORT).show();
            Log.e("data", s.toString());

        }
    }
}

