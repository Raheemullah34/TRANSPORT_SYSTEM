package com.abasynuniversity.TransportSystem.forStudents.VolleyService;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class VolleyService {

    Context mContext;

    public VolleyService(Context mContext)
    {
        this.mContext = mContext;
    }





/*START Registration  actvity*/
public void driver_Registration(String url, final String emp_id, final String full_name, final String cnic, final String contact, final String imei, final VolleyResponseListener volleyResponseListener){
    try {
        final RequestQueue queue = Volley.newRequestQueue(mContext);

            StringRequest req = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        volleyResponseListener.onSuccess(s);
                        Log.d("response",s);
                     }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
              //  volleyResponseListener.onError(volleyError);
                 //Log.v("see error responce",volleyError.toString());

                NetworkResponse response = volleyError.networkResponse;
                if (volleyError instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                      //  Toast.makeText(mContext, "array"+res, Toast.LENGTH_SHORT).show();
                        // Now you can use any deserializer to make sense of data
                        JSONObject obj = new JSONObject(res);
                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        // returned data is not JSONObject?
                        e2.printStackTrace();
                    }
                }

            }

        })


        {


            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                Log.v("see error responce",response.toString());
                return super.parseNetworkResponse(response);


            }

            @Override
            protected Map<String, String> getParams(){
                HashMap<String, String> params = new HashMap<String, String>();

//
                params.put("d_id",emp_id);
                params.put("d_name",full_name);
                params.put("d_cnic",cnic);
                params.put("d_contact",contact);
                params.put("d_imei",imei);



                return  params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
               // headers.put("Content-Type", "application/json");
                //headers.put("Authorization", "Bearer " + Utils.readSharedSetting(mContext, "access_token", ""));

                return headers;
            }
        };
        req.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(req);



    }catch (Exception e){
        // Log.v("see error responce",e.toString());

    }

}
//End of Registration activity





    //staff Services
    public void all_vehicles_dashboard(String url, final VolleyResponseListener volleyResponseListener){
        try {
            final RequestQueue queue = Volley.newRequestQueue(mContext);

            StringRequest req = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            volleyResponseListener.onSuccess(s);
                            // Log.d("password",s);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    volleyResponseListener.onError(volleyError);

                }
            })

            {


                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    Log.v("see error responce",response.toString());
                    return super.parseNetworkResponse(response);


                }

                @Override
                protected Map<String, String> getParams(){
                    HashMap<String, String> params = new HashMap<String, String>();






                    return params;
                }
            };
            req.setRetryPolicy(new DefaultRetryPolicy(
                    50000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(req);



        }catch (Exception e){
            // Log.v("see error responce",e.toString());

        }

    }
    //end of student services

    //start of Student Time Table
    public void vehical_detail_all_registerd_students(String url, final String v_id, final VolleyResponseListener volleyResponseListener){
        try {
            final RequestQueue queue = Volley.newRequestQueue(mContext);

            StringRequest req = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            volleyResponseListener.onSuccess(s);
                            // Log.d("password",s);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    volleyResponseListener.onError(volleyError);

                }
            })

            {


                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    Log.v("see error responce",response.toString());
                    return super.parseNetworkResponse(response);


                }

                @Override
                protected Map<String, String> getParams(){
                    HashMap<String, String> params = new HashMap<String, String>();

                    params.put("v_id", v_id);




                    return params;
                }
            };
            req.setRetryPolicy(new DefaultRetryPolicy(
                    50000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(req);



        }catch (Exception e){
            // Log.v("see error responce",e.toString());

        }

    }

    //End of tthe studetnt time Table
//Notification to student
    public void send_Notification_to_student(String url, final String arr,
                                             final VolleyResponseListener volleyResponseListener){
        try {
            final RequestQueue queue = Volley.newRequestQueue(mContext);

            final StringRequest req = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            volleyResponseListener.onSuccess(s);
                            // Log.d("password",s);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    volleyResponseListener.onError(volleyError);

                }
            })

            {


                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    Log.v("see error responce",response.toString());
                    return super.parseNetworkResponse(response);


                }

                @Override
                protected Map<String, String> getParams(){
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("jsonObject",arr);









                    return params;
                }
            };
            req.setRetryPolicy(new DefaultRetryPolicy(
                    50000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(req);



        }catch (Exception e){
            // Log.v("see error responce",e.toString());

        }

    }

    //end of the student notification

    // pick Student
    public void send_pick_student_info(String url, final String arr,
                                             final VolleyResponseListener volleyResponseListener){
        try {
            final RequestQueue queue = Volley.newRequestQueue(mContext);

            final StringRequest req = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            volleyResponseListener.onSuccess(s);
                            // Log.d("password",s);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    volleyResponseListener.onError(volleyError);

                }
            })

            {


                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    Log.v("see error responce",response.toString());
                    return super.parseNetworkResponse(response);


                }

                @Override
                protected Map<String, String> getParams(){
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("jsonObject",arr);









                    return params;
                }
            };
            req.setRetryPolicy(new DefaultRetryPolicy(
                    50000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(req);



        }catch (Exception e){
            // Log.v("see error responce",e.toString());

        }

    }

    // pick student


    //checking status of the employee
    public void driver_car_status_checking(String url, final String v_id, final VolleyResponseListener volleyResponseListener){
        try {
            final RequestQueue queue = Volley.newRequestQueue(mContext);

            StringRequest req = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            volleyResponseListener.onSuccess(s);
                            Log.d("response",s);
                            //Toast.makeText(mContext, ""+s, Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    //  volleyResponseListener.onError(volleyError);
                    //Log.v("see error responce",volleyError.toString());

                    NetworkResponse response = volleyError.networkResponse;
                    if (volleyError instanceof ServerError && response != null) {
                        try {
                            String res = new String(response.data,
                                    HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                            //  Toast.makeText(mContext, "array"+res, Toast.LENGTH_SHORT).show();
                            // Now you can use any deserializer to make sense of data
                            JSONObject obj = new JSONObject(res);
                        } catch (UnsupportedEncodingException e1) {
                            // Couldn't properly decode data to string
                            e1.printStackTrace();
                        } catch (JSONException e2) {
                            // returned data is not JSONObject?
                            e2.printStackTrace();
                        }
                    }

                }
            })

            {


                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    Log.v("see error responce",response.toString());
                    return super.parseNetworkResponse(response);


                }

                @Override
                protected Map<String, String> getParams(){
                    HashMap<String, String> params = new HashMap<String, String>();

//

                    params.put("v_id",v_id);





                    return  params;
                }
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    // headers.put("Content-Type", "application/json");
                    //headers.put("Authorization", "Bearer " + Utils.readSharedSetting(mContext, "access_token", ""));

                    return headers;
                }
            };
            req.setRetryPolicy(new DefaultRetryPolicy(
                    50000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(req);



        }catch (Exception e){
            // Log.v("see error responce",e.toString());

        }

    }

    // End of status of the emplyee checking







    /*------------------------------------------------- /For Headers----------------------------------------------------------*/
    public interface VolleyResponseListener {
        void onSuccess(String response);
        void onError(VolleyError error);
    }

}
