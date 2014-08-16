package id.aditya.andropulsa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class AboutActivity extends Activity {
	
	private Button btnHelp;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_about);
        
        btnHelp = (Button)findViewById(R.id.button_help);
        btnHelp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(AboutActivity.this, HelpActivity.class);
				startActivity(i);
				
			}
		});
	}
	
}
