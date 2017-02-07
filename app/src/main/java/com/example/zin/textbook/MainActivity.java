package com.example.zin.textbook;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity
	implements NavigationView.OnNavigationItemSelectedListener {
	int judgesecret = 0;
	int setnum = 1;
	int N = 1000;
	int Num = 0;
	int Numdelete = 0;
	boolean wdelete = true;
	String password = "";
	final String PATH = "/sdcard/Zin's Textbook";
	final String F = "/存档.txt";
	final String F2 = "/ps.txt";
	final String trim_key = "@#$%^&*sss..--///This is a seperator///--..sss!@#$%^&*";
	File path = new File(PATH);
	File file = new File(PATH + F);
	File file2 = new File(PATH + F2);

	public class MyButton extends Button {
		int turn = 0;
		Boolean secret = false;
		String title;
		String content;
		Boolean delete = false;
		String time;
		TextView tv;

		public MyButton(Context context) {
			super(context);
			tv = new TextView(context);
			super.setSingleLine();
			super.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
			super.setEllipsize(TextUtils.TruncateAt.END);
			tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape5));
			super.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape4));
			super.setTextSize(17);
		}
	}

	LinearLayout line;
	MyButton bt[] = new MyButton[N];

	public void read() {
		if (!file2.exists()) {
			password = "";
		} else {
			String resave0 = "";
			InputStreamReader isr = null;
			try {
				isr = new InputStreamReader(new FileInputStream(file2), "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			BufferedReader br = new BufferedReader(isr);
			String s = null;

			int i = 0;
			try {
				while ((s = br.readLine()) != null) {
					resave0 += s + "\n";
					i++;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (resave0.length() > 0) resave0 = resave0.substring(0, resave0.length() - 1);
			password = resave0;
		}


		String resave = "";
		if (!file.exists()) {
			Num = 0;
		} else {
			InputStreamReader isr = null;
			try {
				isr = new InputStreamReader(new FileInputStream(file), "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			BufferedReader br = new BufferedReader(isr);
			String s = null;

			int i = 0;
			try {
				while ((s = br.readLine()) != null) {
					resave += s + "\n";
					i++;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (resave.length() > 0) resave = resave.substring(0, resave.length() - 1);
		int numi = 0;
		String resave0 = resave;
		while (resave.indexOf(trim_key) != -1) {
			numi++;
			resave = resave.substring(resave.indexOf(trim_key) + trim_key.length(), resave.length());
		}
		Num = numi;
		if (Num > 0) {
			String set[] = new String[Num];
			numi = 0;
			while (resave0.indexOf(trim_key) != -1) {
				set[numi] = resave0.substring(0, resave0.indexOf(trim_key));
				resave0 = resave0.substring(resave0.indexOf(trim_key) + trim_key.length(), resave0.length());
				numi++;
			}
			if (set.length > 0) {
				Num = set.length;
				for (int j = 0; j < Num; j++) {
					String Setting[] = set[j].split("\n", 4);
					bt[j] = new MyButton(MainActivity.this);
					bt[j].secret = Boolean.valueOf(Setting[0]);
					bt[j].time = Setting[1];
					bt[j].title = Setting[2];
					bt[j].content = Setting[3];
				}
			}
		}
	}

	public void saveps() {
		if (!path.exists()) {
			path.mkdirs();
		}
		if (!file2.exists()) {
			try {
				file2.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		BufferedWriter br = null;
		try {
			br = new BufferedWriter(new FileWriter(file2));
			br.write(password);
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	public void save() {
		if (!path.exists()) {
			path.mkdirs();
		}
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		BufferedWriter br = null;
		try {
			br = new BufferedWriter(new FileWriter(file));
			for (int i = 0; i < Num; i++) {
				if (bt[i].delete == false) {
					br.write(bt[i].secret + "\n" + bt[i].time + "\n" + bt[i].title + "\n" + bt[i].content);
					br.write(trim_key);
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(getApplicationContext(), "创建新笔记", Toast.LENGTH_SHORT).show();
				Intent it = new Intent();
				it.setClass(MainActivity.this, TextEdit.class);
				it.putExtra("New", true);
				SimpleDateFormat myFmt1 = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
				Date now = new Date();
				String rq = myFmt1.format(now);
				it.putExtra("Time", rq);
				startActivityForResult(it, Num);
			}
		});
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
			if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
				requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
			} else {
				line = (LinearLayout) findViewById(R.id.line0);
				read();
				List_readd(wdelete);
			}

		} else {
			line = (LinearLayout) findViewById(R.id.line0);
			read();
			List_readd(wdelete);
		}
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
			this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
		SimpleDateFormat myFmt1 = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");
		Date now = new Date();
		String rq = myFmt1.format(now);


		LinearLayout line2 = (LinearLayout) findViewById(R.id.line2);
		line2.setBackgroundColor(Color.parseColor("#EEEEEE"));
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 123) {
			line = (LinearLayout) findViewById(R.id.line0);
			read();
			List_readd(wdelete);
		}
	}

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement


		return super.onOptionsItemSelected(item);
	}


	public void List_readd(boolean a) {

		line.removeAllViews();
		TextView tvn = new TextView(this);
		tvn.setHeight(50);
		line.addView(tvn);
		final LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT,
			LinearLayout.LayoutParams.WRAP_CONTENT
		);
		p.setMargins(0, 0, 0, 50);

		for (int i = 0; i < Num; i++) {
			final int ii = i;

			View.OnClickListener voc = new View.OnClickListener() {
				@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
				@Override
				public void onClick(View v) {
					Intent it = new Intent();
					it.setClass(MainActivity.this, TextEdit.class);
					it.putExtra("Text", bt[ii].title + "\n" + bt[ii].content);
					it.putExtra("New", false);
					it.putExtra("Time", bt[ii].time);
					startActivityForResult(it, ii);
				}
			};

			bt[i].setOnClickListener(voc);
			bt[i].tv.setOnClickListener(voc);
			final String[] choice;
			choice = new String[]{"隐藏", "删除", "取消"};
			if (bt[i].secret) {
				choice[0] = "取消隐藏";
			}
			if (bt[i].delete) {
				choice[1] = "恢复";
			}
			View.OnLongClickListener volc = new View.OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {
					new AlertDialog.Builder(MainActivity.this)
						.setItems(choice, new DialogInterface.OnClickListener() {


							@Override
							public void onClick(DialogInterface dialog, int which) {
								switch (which) {
									case 0:
										bt[ii].secret = !bt[ii].secret;
										List_readd(wdelete);
										break;

									case 1:
										bt[ii].delete = !bt[ii].delete;
										line.removeView(bt[ii]);
										line.removeView(bt[ii].tv);
										save();
//										delete(ii);
//										List_readd(wdelete);
										break;
								}

							}
						}).show();
					return false;
				}
			};
			bt[i].setOnLongClickListener(volc);
			bt[i].tv.setOnLongClickListener(volc);
			bt[i].tv.setText(bt[i].time);
			bt[i].setText("  " + bt[i].title);
			if (!bt[i].delete == a) {
				if (bt[i].secret == true) {
					if (judgesecret == 1) {
						bt[i].tv.setText(bt[i].time + "  (隐藏的)");
						line.addView(bt[i].tv);
						line.addView(bt[i], p);

					}
				} else {
					line.addView(bt[i].tv);
					line.addView(bt[i], p);

				}
			}
		}
		save();
	}

//	public void delete(int a) {
//
//		btdelete[Numdelete] = bt[a];
//		btdelete[Numdelete].setOnLongClickListener(new View.OnLongClickListener() {
//			@Override
//			public boolean onLongClick(View v) {
//
//				return false;
//			}
//		});
//		Numdelete++;
//		if (Num > 1) {
//			for (int i = a; i < Num - 1; i++) {
//				bt[i] = bt[i + 1];
//			}
//			Num--;
//		} else {
//			Num--;
//		}
//
//	}

	public void movetotop(int a) {
		if (Num > 1) {
			MyButton mb = bt[a];
			for (int i = a; i >= 1; i--) {
				bt[i] = bt[i - 1];
			}
			bt[0] = mb;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent it2) {
		if (resultCode == RESULT_OK) {
			if (requestCode == N + 1) {
				password = it2.getStringExtra("ps");
				saveps();
			} else {
				if (requestCode >= Num) {
					bt[requestCode] = new MyButton(this);
				}

				bt[requestCode].title = it2.getStringExtra("Title");
				bt[requestCode].content = it2.getStringExtra("Content");
				bt[requestCode].time = it2.getStringExtra("Time");
				if (requestCode >= Num) {
					Num++;
				}
				movetotop(requestCode);
				List_readd(wdelete);
			}
		}
	}


	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item1) {
		// Handle navigation view item clicks here.
		final MenuItem item = item1;
		int id = item.getItemId();
		if (id == R.id.nav_slideshow) {


			if (setnum == 1) {
				if (password.equals("")) {
					Toast.makeText(getApplicationContext(), "已经显示了隐藏项", Toast.LENGTH_LONG).show();
					judgesecret = 1;
					item.setTitle("不显示隐藏项");
					item.setChecked(true);
					item.setIcon(R.drawable.unlock);
					setnum = 1 - setnum;
					List_readd(wdelete);
				} else {
					final EditText et = new EditText(this);
					et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
					et.setGravity(Gravity.CENTER);
					new AlertDialog.Builder(this).setTitle("请输入密码:")
						.setView(et)
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								String input = et.getText().toString();
								if (input.equals(password)) {
									Toast.makeText(getApplicationContext(), "密码正确，已经显示了隐藏项", Toast.LENGTH_LONG).show();
									judgesecret = 1;
									item.setTitle("不显示隐藏项");
									item.setChecked(true);
									item.setIcon(R.drawable.unlock);
									setnum = 1 - setnum;
									List_readd(wdelete);
								} else {
									Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_LONG).show();
									judgesecret = 0;
									item.setChecked(false);
									item.setIcon(R.drawable.lock);
								}
							}
						})
						.setNegativeButton("取消", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								item.setChecked(false);
								item.setIcon(R.drawable.lock);
								judgesecret = 0;
							}
						})
						.show();
				}
			} else {
				item.setTitle("显示隐藏项");
				item.setChecked(false);
				item.setIcon(R.drawable.lock);
				setnum = 1 - setnum;
				judgesecret = 0;
				Toast.makeText(MainActivity.this, "已经隐去了隐藏项", Toast.LENGTH_SHORT).show();
				List_readd(wdelete);
			}
		} else if (id == R.id.nav_manage) {
			Intent it = new Intent();
			it.setClass(MainActivity.this, SetActivity.class);
			it.putExtra("ps", password);
			startActivityForResult(it, N + 1);
		} else if (id == R.id.nav_share) {
			Toast.makeText(MainActivity.this, "分享我也不会写", Toast.LENGTH_SHORT).show();
		} else if (id == R.id.nav_rubbish) {
			wdelete = item.isChecked();
			List_readd(wdelete);
			item.setChecked(!item.isChecked());
		}
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}
