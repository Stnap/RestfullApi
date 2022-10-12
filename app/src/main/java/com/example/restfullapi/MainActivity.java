package com.example.restfullapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText txt_code, txt_name;
    Button btn_save, btn_update, btn_delete, btn_search;
    List<String> data;
    ListView listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        listData.setAdapter(adapter);
        listWs(adapter);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s_code = txt_code.getText().toString();
                String s_name = txt_name.getText().toString();
                postFromButtonWs(s_code, s_name);
                cleanForm();
                chargeListView();
            }
        });
    }

    private void initUI() {
        txt_code = (EditText) findViewById(R.id.txt_code);
        txt_name = (EditText) findViewById(R.id.txt_name);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_update = (Button) findViewById(R.id.btn_update);
        btn_search = (Button) findViewById(R.id.btn_search);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        listData = (ListView) findViewById(R.id.lv_1);

    }

    private void chargeListView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, data);
        listData.setAdapter(adapter);
        listWs(adapter);
    }

    private void cleanForm() {
        txt_code.setText("");
        txt_name.setText("");
    }

    private void reedWs() {
        String url = "https://jsonplaceholder.typicode.com/todos/" + txt_code.getText().toString();

        cleanForm();
        StringRequest postRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//metodo se ejecuta cuando hay respuesta del web service
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    txt_code.setText(jsonObject.getString("userId"));
                    txt_name.setText(jsonObject.getString("title"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error de consulta el API REST", error.getMessage());
            }
        });

        Volley.newRequestQueue(this).add(postRequest);
    }

    private void postFromButtonWs(final String code, final String name) {
        String url = "http://192.168.1.189:8000/api/projects";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//metodo se ejecuta cuando hay respuesta del web service
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(MainActivity.this, "Proyecto: " + name + " creado exitosamente", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error de consulta el API REST", error.getMessage());
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("code", code);
                params.put("name", name);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }

    private void updatedWs(final String code, final String name) {
        String url = "https://jsonplaceholder.typicode.com/posts/1";
        StringRequest postRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//metodo se ejecuta cuando hay respuesta del web service
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(MainActivity.this, "Id es: " + jsonObject.getString("id"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error de consulta el API REST", error.getMessage());
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }

    private void deleteWs(final String code) {
        String url = "https://jsonplaceholder.typicode.com/posts/1";

        StringRequest postRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//metodo se ejecuta cuando hay respuesta del web service
                try {
                    JSONObject jsonObject = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error de consulta el API REST", error.getMessage());

            }
        });
        Volley.newRequestQueue(this).add(postRequest);
    }

    private void listWs(ArrayAdapter adapter) {
        String url = "http://192.168.1.189:8000/api/projects";
        data = new ArrayList<String>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            Group g = new Group();
                            g.setTitle(obj.get("name").toString());
                            adapter.add(g.getTitle());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        Log.e("error de consulta el API REST", error.getMessage());
                    }
                }
        );
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

}