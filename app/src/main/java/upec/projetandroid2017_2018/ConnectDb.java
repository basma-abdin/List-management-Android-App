package upec.projetandroid2017_2018;


import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

public class ConnectDb extends AsyncTask<String, String,ArrayList<String>> {
    Activity activity;
    URL url;
    HttpURLConnection connection;
    BufferedWriter bufwriter;
    OutputStream outstream;
    String result;
    public ConnectDb(Activity ap ){
        activity = ap;
    }



    @Override
    protected ArrayList<String> doInBackground(String... params) {
        ArrayList<String> list = new ArrayList<>();

        String type = params[0];
        if (type.equals("logIn")) {
            try {
                URL url = new URL("https://pw.lacl.fr/~u21502374/logIn.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                OutputStream outstream = connection.getOutputStream();
                BufferedWriter bufwriter = new BufferedWriter(new OutputStreamWriter(outstream, "UTF-8"));
                String PostData = URLEncoder.encode("user-name", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8");
                bufwriter.write(PostData);
                bufwriter.flush();
                bufwriter.close();
                outstream.close();
                InputStream instream = connection.getInputStream();
                BufferedReader bufreader = new BufferedReader(new InputStreamReader(instream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufreader.readLine()) != null) {
                    result += line;
                }
                bufreader.close();
                instream.close();
                connection.disconnect();

                list.add(0, result);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type.equals("signIn")){
            try {
                URL url = new URL("https://pw.lacl.fr/~u21502374/signIn.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                OutputStream outstream = connection.getOutputStream();
                BufferedWriter bufwriter = new BufferedWriter(new OutputStreamWriter(outstream,"UTF-8"));
                String PostData = URLEncoder.encode("user-name","UTF-8")+"="+URLEncoder.encode(params[1],"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(params[3],"UTF-8")+"&"
                        +URLEncoder.encode("mail","UTF-8")+"="+URLEncoder.encode(params[2],"UTF-8")+"&"
                        +URLEncoder.encode("password2","UTF-8")+"="+URLEncoder.encode(params[4],"UTF-8");
                bufwriter.write(PostData);
                bufwriter.flush();
                bufwriter.close();
                outstream.close();
                InputStream instream = connection.getInputStream();
                BufferedReader bufreader = new BufferedReader(new InputStreamReader(instream,"iso-8859-1"));
                String result= "";
                String line = "";
                while ((line = bufreader.readLine())!= null){
                    result +=line;
                }
                bufreader.close();instream.close();
                connection.disconnect();
                list.add(0,result);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }else if(type.equals("get")){
            try {
               bufwriter=connect("https://pw.lacl.fr/~u21502374/"+params[1]);
                String PostData="";
                if(params[1].equals("getList.php"))
                PostData = URLEncoder.encode("user-name","UTF-8")+"="+URLEncoder.encode(params[2],"UTF-8")+"&"
                        +URLEncoder.encode("visibility","UTF-8")+"="+URLEncoder.encode(params[3],"UTF-8");
                else PostData = URLEncoder.encode("Lid","UTF-8")+"="+URLEncoder.encode(params[2],"UTF-8");
                bufwriter.write(PostData);
                bufwriter.flush();
                bufwriter.close();
                outstream.close();
               list=serveResponse();
                connection.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type.equals("Add")){
            try {
                bufwriter = connect("https://pw.lacl.fr/~u21502374/"+params[1]);
                String PostData="";
                if(params[1].equals("addList.php"))
                PostData = URLEncoder.encode("user-name","UTF-8")+"="+URLEncoder.encode(params[2],"UTF-8")+"&"
                        +URLEncoder.encode("title","UTF-8")+"="+URLEncoder.encode(params[3],"UTF-8")+"&"
                        +URLEncoder.encode("state","UTF-8")+"="+URLEncoder.encode(params[4],"UTF-8");
                else {
                    PostData = URLEncoder.encode("user-name","UTF-8")+"="+URLEncoder.encode(params[2],"UTF-8")+"&"
                          + URLEncoder.encode("Lid","UTF-8")+"="+URLEncoder.encode(params[3],"UTF-8")+"&"
                            +URLEncoder.encode("title","UTF-8")+"="+URLEncoder.encode(params[4],"UTF-8");
                }
                bufwriter.write(PostData);
                bufwriter.flush();
                bufwriter.close();
                outstream.close();
                list=serveResponse();
                connection.disconnect();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(type.equals("Edit")) {
            try {
                bufwriter = connect("https://pw.lacl.fr/~u21502374/"+params[1]);
                String PostData ="";
                if(params[1].equals("editList.php"))
                        PostData = URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8") + "&"
                        + URLEncoder.encode("lid", "UTF-8") + "=" + URLEncoder.encode(params[3], "UTF-8")+ "&"
                                + URLEncoder.encode("user-name", "UTF-8") + "=" + URLEncoder.encode(params[4], "UTF-8");
                else PostData = URLEncoder.encode("user-name","UTF-8")+"="+URLEncoder.encode(params[2],"UTF-8")+"&"
                        + URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(params[3], "UTF-8") + "&"
                        + URLEncoder.encode("eid", "UTF-8") + "=" + URLEncoder.encode(params[4], "UTF-8");
                bufwriter.write(PostData);
                bufwriter.flush();
                bufwriter.close();
                outstream.close();
                list = serveResponse();
                connection.disconnect();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type.equals("delete")){
            try {
                bufwriter = connect("https://pw.lacl.fr/~u21502374/"+params[1]);
                String PostData="";
                if(params[1].equals("deleteList.php")) {
                    PostData = URLEncoder.encode("lid", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8")+ "&"
                            + URLEncoder.encode("user-name", "UTF-8") + "=" + URLEncoder.encode(params[3], "UTF-8");

                }else PostData = URLEncoder.encode("user-name","UTF-8")+"="+URLEncoder.encode(params[2],"UTF-8")+"&"
                                +URLEncoder.encode("eid", "UTF-8") + "=" + URLEncoder.encode(params[3], "UTF-8");
                bufwriter.write(PostData);
                bufwriter.flush();
                bufwriter.close();
                outstream.close();
               list = serveResponse();
                connection.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(type.equals("HistElem")){
            try {
                bufwriter = connect("https://pw.lacl.fr/~u21502374/historicElem.php");
                String PostData="";

                    PostData = URLEncoder.encode("Lid", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8");

                bufwriter.write(PostData);
                bufwriter.flush();
                bufwriter.close();
                outstream.close();
                list = serveResponse();
                connection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(type.equals("Change")){
            String PostData="";
            try {
                bufwriter = connect("https://pw.lacl.fr/~u21502374/"+params[1]);
                if(params[1].equals("changeName.php")) {
                     PostData = URLEncoder.encode("user-name", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8") + "&"
                            + URLEncoder.encode("new-name", "UTF-8") + "=" + URLEncoder.encode(params[3], "UTF-8");
                }else if (params[1].equals("changePass.php")) {
                    PostData = URLEncoder.encode("user-name", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8") + "&"
                            + URLEncoder.encode("new-pass", "UTF-8") + "=" + URLEncoder.encode(params[3], "UTF-8");
                }
                bufwriter.write(PostData);
                bufwriter.flush();
                bufwriter.close();
                outstream.close();
                list = serveResponse();
                connection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(type.equals("HistList")){
            try {
                bufwriter = connect("https://pw.lacl.fr/~u21502374/historicList.php");
                String PostData="";

                bufwriter.write(PostData);
                bufwriter.flush();
                bufwriter.close();
                outstream.close();
                list = serveResponse();
                connection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return list;
    }

    private BufferedWriter connect(String u) {
        try {
            url = new URL(u);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            outstream = connection.getOutputStream();
            bufwriter = new BufferedWriter(new OutputStreamWriter(outstream, "UTF-8"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufwriter;
    }

    private ArrayList<String> serveResponse(){
        ArrayList<String> l=new ArrayList<>();
        try {
            InputStream instream = connection.getInputStream();
            BufferedReader bufreader = new BufferedReader(new InputStreamReader(instream, "UTF-8"));
            String line = "";
            while ((line = bufreader.readLine()) != null) {
               l.add(URLDecoder.decode(line, "UTF-8"));
            }
            bufreader.close();
            instream.close();
        }  catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return l;

    }

}
