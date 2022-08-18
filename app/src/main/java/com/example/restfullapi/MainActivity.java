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

    EditText txt_user_id, txt_title, txt_body;
    Button btn_send;

    List<String> data;
    ListView listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_user_id = (EditText) findViewById(R.id.txt_user_id);
        txt_title = (EditText) findViewById(R.id.txt_title);
        txt_body = (EditText) findViewById(R.id.txt_body);
        btn_send = (Button) findViewById(R.id.btn_send);
        listData = (ListView) findViewById(R.id.lv_1);
        listWs();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, data);

        listData.setAdapter(adapter);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                reedWs();
//                postWs();
//                postFromButtonWs(txt_title.getText().toString(), txt_body.getText().toString(), txt_user_id.getText().toString());
                updatedWs(txt_title.getText().toString(), txt_body.getText().toString(), txt_user_id.getText().toString());
            }
        });
    }

    private void reedWs() {
        String url = "https://jsonplaceholder.typicode.com/todos/5";

        StringRequest postRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//metodo se ejecuta cuando hay respuesta del web service
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    txt_user_id.setText(jsonObject.getString("userId"));
                    txt_title.setText(jsonObject.getString("title"));
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

    private void postWs() {
        String url = "https://jsonplaceholder.typicode.com/posts";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//metodo se ejecuta cuando hay respuesta del web service
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    txt_user_id.setText(jsonObject.getString("userId"));
                    txt_title.setText(jsonObject.getString("title"));
                    txt_body.setText(jsonObject.getString("body"));
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
                params.put("title", "POST TITLE UCN");
                params.put("body", "POST CONTENT UCN");
                params.put("userId", "1");
                return params;
            }
        };

        Volley.newRequestQueue(this).add(postRequest);
    }

    private void postFromButtonWs(final String title, final String body, final String userId) {
        String url = "https://jsonplaceholder.typicode.com/posts";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//metodo se ejecuta cuando hay respuesta del web service
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    txt_user_id.setText(jsonObject.getString("userId"));
                    txt_title.setText(jsonObject.getString("title"));
                    txt_body.setText(response);
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
                params.put("title", title);
                params.put("body", body);
                params.put("userId", userId);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }

    private void updatedWs(final String title, final String body, final String userId) {
        String url = "https://jsonplaceholder.typicode.com/posts/1";

        StringRequest postRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//metodo se ejecuta cuando hay respuesta del web service
                try {
                    JSONObject jsonObject = new JSONObject(response);
//                    txt_user_id.setText(jsonObject.getString("userId"));
//                    txt_title.setText(jsonObject.getString("title"));
                    txt_body.setText(response);
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
                params.put("id", "1");
                params.put("title", title);
                params.put("body", body);
                params.put("userId", userId);
                return params;
            }
        };

        Volley.newRequestQueue(this).add(postRequest);
    }

    private void deleteWs(final String title, final String body, final String userId) {
        String url = "https://jsonplaceholder.typicode.com/posts/1";

        StringRequest postRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//metodo se ejecuta cuando hay respuesta del web service
                try {
                    JSONObject jsonObject = new JSONObject(response);
//                    txt_user_id.setText(jsonObject.getString("userId"));
//                    txt_title.setText(jsonObject.getString("title"));
                    txt_body.setText(response);
//                    Toast.makeText(MainActivity.this, "Id es: " + jsonObject.getString("id"), Toast.LENGTH_SHORT).show();
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

    private void listWs() {
        String url = "https://jsonplaceholder.typicode.com/posts";
        data = new ArrayList<String>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            Group g = new Group();
                            g.setId(Integer.parseInt(obj.get("id").toString()));
                            g.setTitle(obj.get("title").toString());
                            g.setUserId(obj.get("userId").toString());
                            g.setBody(obj.get("body").toString());
                            data.add(g.getTitle());
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
                }
            }
        );
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

}