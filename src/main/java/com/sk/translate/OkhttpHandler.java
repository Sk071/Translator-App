package com.sk.translate;
import android.os.AsyncTask;
import okhttp3.*;
import java.io.IOException;
import android.util.Log;
import org.json.JSONObject;
import org.json.JSONException;
import android.content.Context;
import android.widget.*;

/**
 **Created by Shubham Kumbhar on 11-1-2018
 */

public class OkhttpHandler extends AsyncTask<String,Void,String> 
{	
        String res;
	String transres;
	String finalres;
	Context ctx;
	public AsyncResponse delegate;
	
	public OkhttpHandler(AsyncResponse delegate)
	{
		this.delegate = delegate;
	}
	
	@Override
	protected String doInBackground(String[] values)
	{
		// TODO: Implement this method
	    OkHttpClient client = new OkHttpClient();
		String texttotranslate = values[0];
		String lang_pair = values[1];
		String key = "API_KEY";
		String url = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + key
			+ "&text=" + texttotranslate + "&lang=" + lang_pair;
		
		

		Request request = new Request.Builder().url(url).build();


		try
		{
			Response response = client.newCall(request).execute();
		    res = response.body().string();		
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		if (res != null)
		{
			try
			{
				JSONObject jsonobj = new JSONObject(res);
				transres = jsonobj.getString("text");
				String f = transres.replace("[", "");
				String s = f.replace("]", "");
				finalres = s.replace("\"", "");
				Log.d("final result", "" + finalres);	
			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}
		}
		return finalres;
	}

	@Override
	protected void onPostExecute(String result)
	{
		// TODO: Implement this method
		super.onPostExecute(result);

		Log.d("result:", "" + result);
		delegate.processFinish(result);
	}
}
