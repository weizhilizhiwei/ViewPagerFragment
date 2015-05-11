package com.lizw.viewpagerfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ChatFragment extends Fragment {
	private View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		if (rootView == null) {
			rootView = inflater.inflate(R.layout.fragment_tab_chat, container,false);
		}
		ViewGroup parentView = (ViewGroup) rootView.getParent();
		if (parentView != null) {
			parentView.removeView(rootView);
		}

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
}
