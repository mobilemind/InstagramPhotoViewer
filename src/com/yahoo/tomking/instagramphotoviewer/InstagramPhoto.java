package com.yahoo.tomking.instagramphotoviewer;

// https://api.instagram.com/v1/media/popular?client_id=kIG_CLIENT_ID
// { "data" => [x] => "user" => "username" }
// { "data" => [x] => "caption" => "text" }
// { "data" => [x] => "images" => "standard_resolution" => "url" }
// { "data" => [x] => "images" => "standard_resolution" => "height" }
// { "data" => [x] => "likes" => "count" }

public class InstagramPhoto {
	public String username;
	public String caption;
	public String imageUrl;
	public int imageHeight;
	public int likesCount;
}
