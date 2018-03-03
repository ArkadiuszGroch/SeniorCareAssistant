package pl.edu.pwste.goco.senior.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import pl.edu.pwste.goco.senior.Entity.Contact;
import pl.edu.pwste.goco.senior.R;

/**
 * Created by goco on 03.03.2018.
 */

public class ContactsAdapter extends ArrayAdapter<Contact> {
    private final Context context;
    private final List<Contact> contactList;


    public ContactsAdapter(Context context, List<Contact> contactList) {
        super(context, R.layout.contact_row, contactList);
        this.contactList = contactList;
        this.context = context;
        sortContactsList();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.contact_row, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        textView.setText(contactList.get(position).toString());
        return rowView;
    }

    private void sortContactsList()
    {
        if (contactList.size() > 0) {
            Collections.sort(contactList, new Comparator<Contact>() {
                @Override
                public int compare(final Contact object1, final Contact object2) {
                    return object1.getName().toLowerCase().compareTo(object2.getName().toLowerCase());
                }
            });
        }


    }

}
