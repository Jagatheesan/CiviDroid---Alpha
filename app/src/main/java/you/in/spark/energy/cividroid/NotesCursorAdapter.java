package you.in.spark.energy.cividroid;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class NotesCursorAdapter extends CursorAdapter {

    private LayoutInflater inflater;

    public NotesCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(R.layout.note_layout,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView noteText, noteDate, noteDuration;
        noteText = (TextView) view.findViewById(R.id.noteText);
        noteDate = (TextView) view.findViewById(R.id.noteDate);
        noteDuration = (TextView) view.findViewById(R.id.noteDuration);

        noteDate.setText(cursor.getString(1));
        noteDuration.setText(cursor.getString(2));
        noteText.setText(cursor.getString(3));

    }
}
