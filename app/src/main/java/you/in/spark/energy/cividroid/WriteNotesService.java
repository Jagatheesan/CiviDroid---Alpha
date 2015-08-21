package you.in.spark.energy.cividroid;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import you.in.spark.energy.cividroid.R.drawable;
import you.in.spark.energy.cividroid.R.id;
import you.in.spark.energy.cividroid.R.layout;

public class WriteNotesService extends Service implements OnClickListener {

    private WindowManager wm;
    private RelativeLayout parent;
    private EditText notes;
    private String date, contactID, duration;
    private SimpleDateFormat simpleDateFormat;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        if (this.wm != null) {
            this.wm.removeView(this.parent);
            this.wm = null;
        }
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        String name = null;




            name = intent.getStringExtra(CiviContract.NAME_CALL_LOG_COLUMN);
        if(name!=null) {
            this.date = intent.getStringExtra(CiviContract.DATE_CALL_LOG_COLUMN);
            this.contactID = intent.getStringExtra(CiviContract.CONTACT_ID_FIELD);
            this.duration = intent.getStringExtra(CiviContract.DURATION_CALL_LOG_COLUMN);

            this.simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


            this.wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);


            LayoutParams params = new LayoutParams(
                    LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                    LayoutParams.TYPE_PHONE,
                    LayoutParams.FLAG_LAYOUT_INSET_DECOR,
                    PixelFormat.TRANSLUCENT);
            params.gravity = Gravity.TOP;

            this.parent = new RelativeLayout(this);
            if(Build.VERSION.SDK_INT >= 21) {
                this.parent.setBackground(this.getDrawable(drawable.notes_gradient));
            } else {
                this.parent.setBackground(this.getResources().getDrawable(drawable.notes_gradient));

            }

            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(layout.write_note_layout, this.parent, true);


            TextView writeNotesTitle = (TextView) this.parent.findViewById(id.writeNotesTitle);
            this.notes = (EditText) this.parent.findViewById(id.writeNotes);

            writeNotesTitle.setText("Notes on last call with " + name);
            Button cancel, save;
            cancel = (Button) this.parent.findViewById(id.writeNotesCancel);
            save = (Button) this.parent.findViewById(id.writeNotesDone);

            cancel.setOnClickListener(this);
            save.setOnClickListener(this);

            this.wm.addView(this.parent, params);

        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case id.writeNotesCancel:
                this.finish();
                return;
            case id.writeNotesDone:
                ContentValues cv = new ContentValues();

                Date rawDate = new Date(Long.valueOf(this.date));
                cv.put(CiviContract.ACTIVITY_TABLE_COLUMNS[3], this.simpleDateFormat.format(rawDate));
                cv.put(CiviContract.ACTIVITY_TABLE_COLUMNS[4], this.duration);
                cv.put(CiviContract.ACTIVITY_TABLE_COLUMNS[6], this.notes.getText().toString());
                cv.put(CiviContract.ACTIVITY_TABLE_COLUMNS[10], this.contactID);
                cv.put(CiviContract.ACTIVITY_TABLE_COLUMNS[11], "1");
                this.getContentResolver().insert(Uri.parse(CiviContract.CONTENT_URI + "/" + CiviContract.ACTIVITY_TABLE), cv);
                this.finish();
                return;
        }

    }

    private void finish() {
        if (this.wm != null) {
            this.wm.removeView(this.parent);
            this.wm = null;
        }
        this.onDestroy();
    }
}
