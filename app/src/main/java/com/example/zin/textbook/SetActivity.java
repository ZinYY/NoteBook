package com.example.zin.textbook;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SetActivity extends AppCompatActivity {
	String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set);


		final Intent it = getIntent();
		final Intent it2 = new Intent();
		password = it.getStringExtra("ps");
		FloatingActionButton fab4 = (FloatingActionButton) findViewById(R.id.fab4);
		FloatingActionButton fab5 = (FloatingActionButton) findViewById(R.id.fab5);
		final EditText ed = (EditText) findViewById(R.id.editText);
		final EditText ed1 = (EditText) findViewById(R.id.editText3);
		final EditText ed2 = (EditText) findViewById(R.id.editText4);
		RelativeLayout rl = (RelativeLayout) findViewById(R.id.activity_set);
		LinearLayout line = (LinearLayout) findViewById(R.id.lineset);
		rl.setBackgroundColor(Color.parseColor("#EEEEEE"));
		if (password.equals("")) line.removeView(ed);
		fab5.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
		fab4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (ed.getText().toString().equals(password)) {
					if (ed1.getText().toString().equals(ed2.getText().toString())) {
						Toast.makeText(SetActivity.this, "密码修改成功", Toast.LENGTH_SHORT).show();
						it2.putExtra("ps", ed1.getText().toString());
						setResult(RESULT_OK, it2);
						finish();
					} else {
						Toast.makeText(SetActivity.this, "两次密码输入的不一致", Toast.LENGTH_SHORT).show();
					}


				} else {
					Toast.makeText(SetActivity.this, "原密码不正确", Toast.LENGTH_SHORT).show();
				}

			}
		});
		fab5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(SetActivity.this, "未完成密码设置", Toast.LENGTH_SHORT).show();
				setResult(RESULT_CANCELED, it2);
				finish();
			}
		});
	}
}
