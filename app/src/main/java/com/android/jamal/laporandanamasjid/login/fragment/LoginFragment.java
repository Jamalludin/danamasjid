package com.android.jamal.laporandanamasjid.login.fragment;


import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.jamal.laporandanamasjid.MainActivity;
import com.android.jamal.laporandanamasjid.R;
import com.android.jamal.laporandanamasjid.jsonReq.JsonReq;
import com.android.jamal.laporandanamasjid.login.LoginBiasa;
import com.android.jamal.laporandanamasjid.login.Session;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    protected EditText username, password;
    protected Button masuk;
    public ProgressDialog progressDialog;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        progressDialog = new ProgressDialog(getActivity());

        username = (EditText)view.findViewById(R.id.input_nim);
        password =(EditText)view.findViewById(R.id.input_password);

        masuk = (Button)view.findViewById(R.id.btn_signup);
        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (user.equals("") && pass.equals("")){
                    Toast.makeText(getActivity(), "Please Insert Username and Password", Toast.LENGTH_SHORT).show();

                }

                else if(user.equals("") || pass.equals("")){

                    Toast.makeText(getActivity(), "Please Insert Username or Password", Toast.LENGTH_SHORT).show();
                }

                else {

                    progressDialog.setMessage("Masuk Aplikasi...");
                    progressDialog.show();

                    new LoginApp(user,pass).execute();

                }
            }
        });

        if (new Session(getContext()).cekLogin()==true){
            if (new Session(getContext()).getRole().equals("admin")){
                startActivity(new Intent(getActivity(), MainActivity.class));
            }else {
                startActivity(new Intent(getActivity(), LoginBiasa.class));
            }

            getActivity().finish();
        }

        return view;
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

            progressDialog.dismiss();

            try {

                JSONObject obj = new JSONObject(s);
                String hasil = obj.getString("hasil");
                if (hasil.equals("sukses")){
                    JSONObject array = obj.getJSONObject("data");
                    new Session(getContext()).userLogin(this.pengguna, this.passw,
                            array.getString("nama"),
                            array.getString("jk"),
                            array.getString("alamat"),
                            array.getString("telp"),
                            array.getString("role"));

                    if (new Session(getContext()).getRole().equals("admin")){

                        Intent i = new Intent(getActivity(), MainActivity.class);
                        startActivity(i);
                        getActivity().finish();
                    }else {

                        startActivity(new Intent(getActivity(), LoginBiasa.class));
                        getActivity().finish();
                    }

                    Toast.makeText(getActivity(), new Session(getContext()).getRole().toString(), Toast.LENGTH_SHORT).show();
                }else {

                    Toast.makeText(getActivity(), "Please Check Your Username or Password", Toast.LENGTH_SHORT).show();
                }

            }catch (JSONException e){

                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
