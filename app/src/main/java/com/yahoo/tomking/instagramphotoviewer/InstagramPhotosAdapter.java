package com.yahoo.tomking.instagramphotoviewer;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
	public InstagramPhotosAdapter(Context context, List<InstagramPhoto> photos) {
		super(context, android.R.layout.simple_list_item_1, photos);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		InstagramPhoto photo = getItem(position);
		if (convertView == null){
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
		}
		ImageView imgPhoto = (ImageView) convertView.findViewById(R.id.imgPhoto);
		TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
		TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
		EditText etLikesCount = (EditText) convertView.findViewById(R.id.etLikeCount);
		imgPhoto.getLayoutParams().height = photo.imageHeight;
		imgPhoto.setImageResource(0);
		Picasso.with(getContext()).load(photo.imageUrl).into(imgPhoto);
		tvCaption.setText(photo.caption);
		tvUsername.setText(photo.username);
		etLikesCount.setText(Integer.toString(photo.likesCount));
		
		return convertView;
	}

}
