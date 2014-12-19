package com.yahoo.tomking.instagramphotoviewer;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class InstagramPhotoViewer extends Activity {
	public static final String kIG_CLIENT_ID = "761087dc7aea4f0cb7a09f60f352fb8f";
	private ArrayList<InstagramPhoto> photos;
	private InstagramPhotosAdapter aPopularPhotos; 
	// https://api.instagram.com/v1/media/popular?client_id=kIG_CLIENT_ID
	// { "data" => [x] => "user" => "username" }
	// { "data" => [x] => "caption" => "text" }
	// { "data" => [x] => "images" => "standard_resolution" => "url" }
	// { "data" => [x] => "images" => "standard_resolution" => "height" }
	// { "data" => [x] => "likes" => "count" }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram_photo_viewer);
        fetchPopularPhotos();
    }


    private void fetchPopularPhotos() {
		// https://api.instagram.com/v1/media/popular?client_id=kIG_CLIENT_ID 
    	// setup endpoint
    	String popularPhotosUrl = "https://api.instagram.com/v1/media/popular?client_id=" + kIG_CLIENT_ID;
    	// init array
    	photos = new ArrayList<InstagramPhoto>();
    	// init adapter
    	aPopularPhotos = new InstagramPhotosAdapter(this, photos);
    	ListView lvPhotos = (ListView) findViewById(R.id.lvPopularPhotos);
    	lvPhotos.setAdapter(aPopularPhotos);
    	
    	// create net client & send request
    	AsyncHttpClient client = new AsyncHttpClient();
    	client.get(popularPhotosUrl, new JsonHttpResponseHandler() {
    		// define callbacks
    		public void onSuccess(int statusCode, org.apache.http.Header[] headers, org.json.JSONObject response) {
    			JSONArray popularPhotosJSON;
    			try {
    				popularPhotosJSON = response.getJSONArray("data");
    				photos.clear();
					for (int i = 0; i < popularPhotosJSON.length(); i++) {
						JSONObject photoJSON = popularPhotosJSON.getJSONObject(i);
						InstagramPhoto photo = new InstagramPhoto();
						if (photoJSON.getJSONObject("user") == null) { photo.username = ""; }
						else { photo.username = photoJSON.getJSONObject("user").getString("username"); }
						if (photoJSON.getJSONObject("caption") == null) { photo.caption = ""; }
						else { photo.caption = photoJSON.getJSONObject("caption").getString("text"); }
						photo.imageUrl = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
						photo.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
						photo.likesCount = photoJSON.getJSONObject("likes").getInt("count");
						photos.add(photo);
						Log.i("DEBUG", "processing photo " + i + "(" + photo.caption + ")");
					}
					aPopularPhotos.notifyDataSetChanged();
				} catch (JSONException e) {
					e.printStackTrace();
				}
    			
    		}

    		@Override
    		public void onFailure(int statusCode, Header[] headers,
    				Throwable throwable, JSONObject errorResponse) {
    			// TODO Auto-generated method stub
    			super.onFailure(statusCode, headers, throwable, errorResponse);
    		}
    	});
    	// send net request
    	// process response
		
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.instagram_photo_viewer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) { return true; }
        return super.onOptionsItemSelected(item);
    }
}
