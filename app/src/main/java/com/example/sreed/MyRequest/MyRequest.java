package com.example.sreed.MyRequest;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyRequest {

    private Context context;
    private RequestQueue queue;

    public MyRequest(Context context, RequestQueue queue)
    {
        this.context = context;
        this.queue = queue;
    }

    public void register(final String Email, final String Mdp, final String Cmdp, final RegisterCallBack callback)
    {
        String url = "http://10.0.2.2/Essai/Inscription.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Map<String, String> errors = new HashMap<>();

                try {
                    JSONObject json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");



                    if (!error)
                    {
                        callback.onSuccess("Vous vous etes bien inscrit");
                    }
                    else
                    {
                        JSONObject messages = json.getJSONObject("message");
                        if (messages.has("Email"))
                        {
                            errors.put("Email", messages.getString("Email"));
                        }
                        if (messages.has("Mdp"))
                        {
                            errors.put("Mdp", messages.getString("Mdp"));
                        }

                        callback.inputErrors(errors);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError)
                {
                    callback.onError("Impossible de se connecter");
                }
                else if (error instanceof VolleyError)
                {
                    callback.onError("Une erreur s'est produite");
                }
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Email", Email);
                map.put("Mdp", Mdp);
                map.put("Cmdp", Cmdp);

                return map;
            }
        };

        queue.add(request);

    }


    public interface RegisterCallBack
    {
        void onSuccess(String message);
        void inputErrors(Map<String, String> errors);
        void onError(String message);
    }


    public void Connection (final String Email, final String Mdp, final LoginCallBack callback)
    {
        String url = "http://10.0.2.2/Essai/Connexion.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");

                    if (!error)
                    {
                        String Email = json.getString("Email");
                        callback.onSuccess(Email);
                    }
                    else
                    {
                        callback.onError(json.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError)
                {
                    callback.onError("Impossible de se connecter");
                }
                else if (error instanceof VolleyError)
                {
                    callback.onError("Une erreur s'est produite");
                }
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Email", Email);
                map.put("Mdp", Mdp);
                return map;
            }
        };

        queue.add(request);
    }

    public interface LoginCallBack
    {
        void onSuccess(String Email);
        void onError(String message);
    }

    public void Posts (final String Email, final String Contenu, final PostCallBack callback)
    {
        String url = "http://10.0.2.2/Essai/Posts.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");

                    if (!error)
                    {
                        callback.onSuccess("Votre post a été publié");
                    }
                    else
                    {
                        callback.onError(json.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError)
                {
                    callback.onError("Impossible de se connecter");
                }
                else if (error instanceof VolleyError)
                {
                    callback.onError("Une erreur s'est produite");
                }
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Email", Email);
                map.put("Contenu", Contenu);
                return map;
            }
        };

        queue.add(request);
    }

    public interface PostCallBack
    {
        void onSuccess(String message);
        void onError(String message);
    }

    public void PostList (final String Emaill, final PostListCallBack callback)
    {
        String url = "http://10.0.2.2/Essai/PostList.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    //JSONArray array = response.getJSONArray("test");
                    JSONObject objj = new JSONObject(response);
                    JSONArray array = objj.getJSONArray("PostList");

                    for (int i = 0; i < array.length(); i++)
                    {
                        JSONObject obj =  array.getJSONObject(i);
                        String Email = obj.getString("Email");
                        String Contenu = obj.getString("Contenu");
                        String Date = obj.getString("Date");
                        callback.onSuccess(Email, Contenu, Date);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError)
                {
                    callback.onError("Impossible de se connecter");
                }
                else if (error instanceof VolleyError)
                {
                    callback.onError("Une erreur s'est produite");
                }
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Email", Emaill);
                return map;
            }
        };

        queue.add(request);
    }

    public interface PostListCallBack
    {
        void onSuccess(String Email, String Contenu, String Date);
        void onError(String message);
    }


    public void Settings(final String Email, final String NP, final String Mdp, final boolean check, final SettingsCallBack callback)
    {
        String url = "http://10.0.2.2/Essai/Settings.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");

                    if (!error)
                    {
                        callback.onSuccess("paramètres validésé");
                    }
                    else
                    {
                        callback.onError(json.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError)
                {
                    callback.onError("Impossible de se connecter");
                }
                else if (error instanceof VolleyError)
                {
                    callback.onError("Une erreur s'est produite");
                }
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Email", Email);
                map.put("NP", NP);
                map.put("Mdp", Mdp);
                map.put("Check", String.valueOf(check));
                return map;
            }
        };

        queue.add(request);

    }
    public interface SettingsCallBack
    {
        void onSuccess(String message);
        void onError(String message);
    }


    public void ConnexionP (final String Pseudo, final String Mdp, final LoginPCallBack callback)
    {
        String url = "http://10.0.2.2/Essai/Login.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");

                    if (!error)
                    {
                        callback.onSuccess("");
                    }
                    else
                    {
                        callback.onError(json.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError)
                {
                    callback.onError("Impossible de se connecter");
                }
                else if (error instanceof VolleyError)
                {
                    callback.onError("Une erreur s'est produite");
                }
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Pseudo", Pseudo);
                map.put("Mdp", Mdp);
                return map;
            }
        };

        queue.add(request);
    }

    public interface LoginPCallBack
    {
        void onSuccess(String message);
        void onError(String message);
    }


    public void PostListP (final String Pseudo, final PostListPCallBack callback)
    {
        String url = "http://10.0.2.2/Essai/PostListP.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject objj = new JSONObject(response);
                    JSONArray array = objj.getJSONArray("PostList");

                    for (int i = 0; i < array.length(); i++)
                    {
                        JSONObject obj =  array.getJSONObject(i);
                        String Contenu = obj.getString("Contenu");
                        String Date = obj.getString("Date");
                        callback.onSuccess(Contenu, Date);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError)
                {
                    callback.onError("Impossible de se connecter");
                }
                else if (error instanceof VolleyError)
                {
                    callback.onError("Une erreur s'est produite");
                }
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Pseudo", Pseudo);
                return map;
            }
        };

        queue.add(request);
    }

    public interface PostListPCallBack
    {
        void onSuccess(String Contenu, String Date);
        void onError(String message);
    }

    public void Recuperation (final String Email, final String Code, final RecuperationCallBack callback)
    {
        String url = "http://10.0.2.2/Essai/Recuperation.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");

                    if (!error)
                    {
                        callback.onSuccess("");
                    }
                    else
                    {
                        callback.onError(json.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError)
                {
                    callback.onError("Impossible de se connecter");
                }
                else if (error instanceof VolleyError)
                {
                    callback.onError("Une erreur s'est produite");
                }
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Email", Email);
                map.put("Code", Code);
                return map;
            }
        };

        queue.add(request);
    }

    public interface RecuperationCallBack
    {
        void onSuccess(String message);
        void onError(String message);
    }

    public void NewPassword (final String Email, final String Mdp, final String Cmdp, final NewPassCallBack callback)
    {
        String url = "http://10.0.2.2/Essai/NewPassword.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");

                    if (!error)
                    {
                        callback.onSuccess("");
                    }
                    else
                    {
                        callback.onError(json.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError)
                {
                    callback.onError("Impossible de se connecter");
                }
                else if (error instanceof VolleyError)
                {
                    callback.onError("Une erreur s'est produite");
                }
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Email", Email);
                map.put("Mdp", Mdp);
                map.put("Cmdp", Cmdp);
                return map;
            }
        };

        queue.add(request);
    }

    public interface NewPassCallBack
    {
        void onSuccess(String message);
        void onError(String message);
    }

    public void Supprimer (final String Email, final String Contenu, final SupprimerCallBack callback)
    {
        String url = "http://10.0.2.2/Essai/Supprimer.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");

                    if (!error)
                    {
                        callback.onSuccess("");
                    }
                    else
                    {
                        callback.onError(json.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError)
                {
                    callback.onError("Impossible de se connecter");
                }
                else if (error instanceof VolleyError)
                {
                    callback.onError("Une erreur s'est produite");
                }
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Email", Email);
                map.put("Contenu", Contenu);
                return map;
            }
        };

        queue.add(request);
    }

    public interface SupprimerCallBack
    {
        void onSuccess(String message);
        void onError(String message);
    }

    public void Modifier (final String Email, final String Contenu, final String AncienContenu, final ModifierCallBack callback)
    {
        String url = "http://10.0.2.2/Essai/Modifier.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");

                    if (!error)
                    {
                        callback.onSuccess("");
                    }
                    else
                    {
                        callback.onError(json.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError)
                {
                    callback.onError("Impossible de se connecter");
                }
                else if (error instanceof VolleyError)
                {
                    callback.onError("Une erreur s'est produite");
                }
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Email", Email);
                map.put("Contenu", Contenu);
                map.put("AncienContenu", AncienContenu);
                return map;
            }
        };

        queue.add(request);
    }

    public interface ModifierCallBack
    {
        void onSuccess(String message);
        void onError(String message);
    }

}
