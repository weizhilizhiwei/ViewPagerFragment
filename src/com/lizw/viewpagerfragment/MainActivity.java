package com.lizw.viewpagerfragment;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends FragmentActivity {

	private List<Fragment> mFragmentList = new ArrayList<Fragment>();
	private FragmentAdapter mFragmentAdapter;

	private ViewPager mPageVp;
	/**
	 * Tab显示内容RadioButton
	 */
	private static final int TAB_CHAT_INDEX = 0, TAB_CONTACT_INDEX = 2,TAB_FRIEND_INDEX = 1;
	private RadioButton mTabChatRadio, mTabContactsRadio, mTabFriendRadio;
	private RadioGroup mTabRadioGroup;
	/**
	 * Tab的那个引导线
	 */
	private View mCursorView;
	/**
	 * Fragment
	 */
	private ChatFragment mChatFg;
	private FriendFragment mFriendFg;
	private ContactsFragment mContactsFg;
	/**
	 * ViewPager的当前选中页
	 */
	private int currentIndex;
	/**
	 * 屏幕的宽度
	 */
	private int screenWidth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findById();
		init();
		initTabLineWidth();

	}

	private void findById() {
		mTabRadioGroup = (RadioGroup) this.findViewById(R.id.id_switch_tab_group);
		mTabContactsRadio = (RadioButton) this.findViewById(R.id.id_contacts_rb);
		mTabChatRadio = (RadioButton) this.findViewById(R.id.id_chat_rb);
		mTabFriendRadio = (RadioButton) this.findViewById(R.id.id_friend_rb);
		mCursorView =  this.findViewById(R.id.id_tab_line_iv);
		mPageVp = (ViewPager) this.findViewById(R.id.id_page_vp);
	}

	private void init() {
		mFriendFg = new FriendFragment();
		mContactsFg = new ContactsFragment();
		mChatFg = new ChatFragment();
		mFragmentList.add(mChatFg);
		mFragmentList.add(mFriendFg);
		mFragmentList.add(mContactsFg);

		mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), mFragmentList);
		mPageVp.setAdapter(mFragmentAdapter);
		//********设置默认值start
		mPageVp.setCurrentItem(TAB_CHAT_INDEX);
		mTabChatRadio.setChecked(true);
		//********设置默认值end
		mPageVp.setOnPageChangeListener(new OnPageChangeListener() {

			/**
			 * state滑动中的状态 有三种状态（0，1，2） 1：正在滑动 2：滑动完毕 0：什么都没做。
			 */
			@Override
			public void onPageScrollStateChanged(int state) {

			}

			/**
			 * position :当前页面，及你点击滑动的页面
			 * offset:当前页面偏移的百分比
			 * offsetPixels:当前页面偏移的像素位置
			 */
			@Override
			public void onPageScrolled(int position, float offset, int offsetPixels) {
				LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mCursorView.getLayoutParams();
				/**
				 * 利用currentIndex(当前所在页面)和position(下一个页面)以及offset来
				 * 设置mTabLineIv的左边距 滑动场景： 记3个页面, 从左到右分别为0,1,2 0->1; 1->2; 2->1;
				 * 1->0
				 */
				if (currentIndex == TAB_CHAT_INDEX && position == 0){// 0->1
					lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex * (screenWidth / 3));
				} else if (currentIndex == TAB_CONTACT_INDEX && position == 0){ // 1->0
					lp.leftMargin = (int) (-(1 - offset) * (screenWidth * 1.0 / 3) + currentIndex * (screenWidth / 3));
				} else if (currentIndex == TAB_FRIEND_INDEX && position == 1) {// 1->2
					lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex * (screenWidth / 3));
				} else if (currentIndex == TAB_CONTACT_INDEX  && position == 1) {// 2->1
					lp.leftMargin = (int) (-(1 - offset) * (screenWidth * 1.0 / 3) + currentIndex * (screenWidth / 3));
				}
				mCursorView.setLayoutParams(lp);
			}

			@Override
			public void onPageSelected(int position) {
				currentIndex = position;
				switch (currentIndex) {
				case TAB_CHAT_INDEX:
					mTabChatRadio.setChecked(true);
					break;
				case TAB_CONTACT_INDEX:
					mTabContactsRadio.setChecked(true);		
					break;
				case TAB_FRIEND_INDEX:
					mTabFriendRadio.setChecked(true);
					break;
					
				default:
					break;
				}
			}
		});
		mTabRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.id_chat_rb:
					currentIndex = TAB_CHAT_INDEX;
					mPageVp.setCurrentItem(currentIndex);
					break;
				case R.id.id_contacts_rb:
					currentIndex = TAB_CONTACT_INDEX;
					mPageVp.setCurrentItem(currentIndex);
					break;
				case R.id.id_friend_rb:
					currentIndex = TAB_FRIEND_INDEX;
					mPageVp.setCurrentItem(currentIndex);
					break;
				default:
					break;
				}
			}
		});
	}
	
	

	/**
	 * 设置滑动条的宽度为屏幕的1/3(根据Tab的个数而定)
	 */
	private void initTabLineWidth() {
		DisplayMetrics dpMetrics = new DisplayMetrics();
		getWindow().getWindowManager().getDefaultDisplay().getMetrics(dpMetrics);
		screenWidth = dpMetrics.widthPixels;
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mCursorView.getLayoutParams();
		lp.width = screenWidth / 3;
		mCursorView.setLayoutParams(lp);
	}


}