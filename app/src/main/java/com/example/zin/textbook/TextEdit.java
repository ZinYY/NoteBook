package com.example.zin.textbook;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Selection;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TextEdit extends AppCompatActivity {
	public boolean find(String text,String simble){
		int n = 0;
		for(int i=0;i<text.length()-1;i++){
			if(text.substring(i,i+1).equals(simble))	n++;
		}
		if(n>75)	return false;
		else	return true;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_text_edit);
		final Intent it = getIntent();
		final Intent it2 = new Intent();
		RelativeLayout rl = (RelativeLayout)findViewById(R.id.activity_text_edit);
		FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
		FloatingActionButton fab3 = (FloatingActionButton) findViewById(R.id.fab3);
		rl.setBackgroundColor(Color.parseColor("#EEEEEE"));
		TextView tv = (TextView)findViewById(R.id.tv1);
		//tv.setText(it.getStringExtra("Time"));
		tv.setText("");
		final EditText ed = (EditText) findViewById(R.id.editText2);
		ed.setText(it.getStringExtra("Text"));
		Boolean new0 = it.getBooleanExtra("New",false);
		final String sinitial = ed.getText().toString();
		if (find(sinitial,"\n"))	ed.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape2));
		else ed.setBackgroundColor(Color.parseColor("#FFFFFF"));
		fab2.setOnClickListener(new View.OnClickListener() {
			@RequiresApi(api = Build.VERSION_CODES.N)
			@Override
			public void onClick(View view) {
				String te = ed.getText().toString();
				if (sinitial.equals(te)) {
					Toast.makeText(TextEdit.this, "未做有效改动", Toast.LENGTH_SHORT).show();

					setResult(RESULT_CANCELED, it2);
					finish();
				} else {
					SimpleDateFormat myFmt1=new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
					Date now=new Date();
					String rq=myFmt1.format(now);

					String re[] = te.split("\n", 2);
					if (re.length == 1) {

						it2.putExtra("Title", re[0]);
						it2.putExtra("Content", "");
					} else {
						it2.putExtra("Title", re[0]);
						it2.putExtra("Content", re[1]);
					}
					it2.putExtra("Time",rq);
					Toast.makeText(TextEdit.this, "成功保存笔记", Toast.LENGTH_SHORT).show();
					setResult(RESULT_OK, it2);
					finish();
				}
			}
		});
		fab3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(TextEdit.this, "未保存笔记", Toast.LENGTH_SHORT).show();
				setResult(RESULT_CANCELED, it2);
				finish();
			}
		});
		fab3.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
		final Editable et = ed.getText();
		Selection.setSelection(et, et.length());
		if(!new0){
			rl.setFocusable(true);
			rl.setFocusableInTouchMode(true);
		}
	}
}
