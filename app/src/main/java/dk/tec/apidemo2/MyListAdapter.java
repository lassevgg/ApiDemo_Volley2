package dk.tec.apidemo2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class MyListAdapter extends ArrayAdapter<String> {

    Activity context;
    ArrayList<String> subject;
    ArrayList<String> url;

    public MyListAdapter(Activity context, ArrayList<String> subject, ArrayList<String> url) {
        super(context, R.layout.my_list_item, subject);

        this.context = context;
        this.subject = subject;
        this.url = url;
    }

    public View getView(int pos, View v, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View row = inflater.inflate(R.layout.my_list_item, null, true);

        TextView subject = row.findViewById(R.id.subject);
        TextView url = row.findViewById(R.id.url);

        subject.setText(this.subject.get(pos));
        url.setText(this.url.get(pos));

        return row;
    }

}
