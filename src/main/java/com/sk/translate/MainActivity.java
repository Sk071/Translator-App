package com.sk.translate;

import android.app.*;
import android.os.*;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;
import android.view.*;
import okhttp3.*;
import android.content.*;
import android.widget.*;
import android.text.method.LinkMovementMethod;
import android.text.Html;
import android.net.*;

/**
 **Created by Shubham Kumbhar on 11-1-2018
 */

public class MainActivity extends Activity 
{
	EditText et;
	Button b;
	TextView t,yandex;	
	String texttotranslate;
	String lang_pair="en-mr";
	Context context = this;
	private ProgressDialog dialog;
	private InterstitialAd mInterstitialAd;

	private boolean isNetworkAvailable()
	{
		ConnectivityManager connectivityManager 
			= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		if (isNetworkAvailable())
		{
			Toast.makeText(context, "Internet Is Connected", Toast.LENGTH_SHORT).show();
		}
		else
		{
			Toast.makeText(context, "Please Connect to internet First", Toast.LENGTH_SHORT).show();
		}

		et = (EditText) findViewById(R.id.e1);
		b = (Button) findViewById(R.id.b1);
		t = (TextView) findViewById(R.id.t1);
		yandex = (TextView) findViewById(R.id.yandex);
		dialog = new ProgressDialog(this);


		yandex.setClickable(true);
		yandex.setMovementMethod(LinkMovementMethod.getInstance());
		String text = "<a href='http://translate.yandex.com/'>Powered by Yandex.Translate</a>";
		yandex.setText(Html.fromHtml(text));

		b.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method	
					if (et.getText().toString().matches(""))
					{
						Toast.makeText(context, "Field Is Blank", Toast.LENGTH_SHORT).show();
					}
					else
					{
						texttotranslate = et.getText().toString();	
						Log.d("text:", "" + texttotranslate);			
						dialog.setMessage("Translating ...");
						dialog.show();

					}
                    OkhttpHandler mytask = new OkhttpHandler(new AsyncResponse(){

							@Override
							public void processFinish(String output)
							{
								// TODO: Implement this method
								if (dialog.isShowing())
								{
									dialog.dismiss();
								}
								t.setText(output);
								Log.d("Response from asynctask", (String) output);
								mInterstitialAd.show();
							}						
						});
					if (!et.getText().toString().matches(""))
						mytask.execute(texttotranslate, lang_pair);
				}
			});
	}

	@Override
	protected void onPause()
	{
		// TODO: Implement this method
		super.onPause();
		dialog.dismiss();	
	}

	@Override
	protected void onDestroy()
	{
		// TODO: Implement this method
		super.onDestroy();
		dialog.dismiss();
		finish();
	}

	@Override
	protected void onStart()
	{
		// TODO: Implement this method
		super.onStart();	
    }
}
