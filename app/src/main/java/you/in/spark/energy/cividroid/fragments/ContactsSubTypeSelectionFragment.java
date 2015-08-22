package you.in.spark.energy.cividroid.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RestAdapter.Builder;
import retrofit.RestAdapter.LogLevel;
import retrofit.RetrofitError;
import retrofit.client.Response;
import you.in.spark.energy.cividroid.FieldSelectionActivity;
import you.in.spark.energy.cividroid.R.id;
import you.in.spark.energy.cividroid.R.layout;
import you.in.spark.energy.cividroid.R.string;
import you.in.spark.energy.cividroid.adapters.ContactSubtypeAdapter;
import you.in.spark.energy.cividroid.api.ICiviApi;
import you.in.spark.energy.cividroid.entities.ContactType;


public class ContactsSubTypeSelectionFragment extends Fragment implements OnCheckedChangeListener {

    private int position;
    public String contactTypeName;
    public int selectedChoice;
    private int count;
    public ContactSubtypeAdapter contactSubtypeAdapter;
    private RadioButton rbAllContacts, rbNoContacts, rbOnlySubtypes;


    public ContactsSubTypeSelectionFragment() {
    }

    public static ContactsSubTypeSelectionFragment newInstance(int position) {
        ContactsSubTypeSelectionFragment myFragment = new ContactsSubTypeSelectionFragment();
        myFragment.count = 0;
        myFragment.selectedChoice = 0;
        myFragment.position = position;
        return myFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(layout.contacts_subtype_selection_layout, container, false);
        RadioGroup rgContactTypes = (RadioGroup) v.findViewById(id.rgContactTypes);
        rgContactTypes.setOnCheckedChangeListener(this);

        this.rbAllContacts = (RadioButton) v.findViewById(id.rbAllContacts);
        this.rbNoContacts = (RadioButton) v.findViewById(id.rbNoContacts);
        this.rbOnlySubtypes = (RadioButton) v.findViewById(id.rbOnlySubtypes);

        switch (this.position) {
            case 0:
                this.rbAllContacts.setText(this.getString(string.all_individuals));
                this.rbNoContacts.setText(this.getString(string.no_individuals));
                break;
            case 1:
                this.rbAllContacts.setText(this.getString(string.all_households));
                this.rbNoContacts.setText(this.getString(string.no_households));

                break;
            case 2:
                this.rbAllContacts.setText(this.getString(string.all_organizations));
                this.rbNoContacts.setText(this.getString(string.no_organizations));
                break;
        }


        RestAdapter adapter = new Builder().setLogLevel(LogLevel.FULL).
                setEndpoint(FieldSelectionActivity.websiteUrl).build();

        ICiviApi iCiviApi = adapter.create(ICiviApi.class);

        Map<String, String> fields = new HashMap<>();
        fields.put("key", FieldSelectionActivity.siteKey);
        fields.put("api_key", FieldSelectionActivity.apiKey);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sequential", "1");
        jsonObject.addProperty("parent_id", this.position + 1);
        fields.put("json", jsonObject.toString());

        final Map<Integer, Pair<String, String>> subtypeNames = new HashMap<>();

        RecyclerView rvContactSubtypes = (RecyclerView) v.findViewById(id.rvContactSubTypes);
        rvContactSubtypes.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        this.contactSubtypeAdapter = new ContactSubtypeAdapter(subtypeNames);
        rvContactSubtypes.setAdapter(this.contactSubtypeAdapter);


        do {
            fields.put("options[offset]", "" + this.count);
            iCiviApi.getContactSubtypes(fields, new Callback<ContactType>() {
                @Override
                public void success(ContactType contactType, Response response) {
                    if (contactType.getIsError() != 1) {
                        ContactsSubTypeSelectionFragment.this.count = contactType.getCount();
                        if (ContactsSubTypeSelectionFragment.this.count > 0) {
                            for (int i = 0; i < ContactsSubTypeSelectionFragment.this.count; i++) {
                                subtypeNames.put(i, new Pair<>(contactType.getValues().get(i).getLabel(), contactType.getValues().get(i).getName()));
                            }
                            if (ContactsSubTypeSelectionFragment.this.count < 25 && !subtypeNames.isEmpty()) {
                                ContactsSubTypeSelectionFragment.this.contactSubtypeAdapter.setRealData(subtypeNames);
                                ContactsSubTypeSelectionFragment.this.rbOnlySubtypes.setEnabled(true);

                                FieldSelectionActivity.fragmentStatus++;
                                if (FieldSelectionActivity.fragmentStatus == 3 && FieldSelectionActivity.civiRotate != null) {
                                    FieldSelectionActivity.civiRotate.setAnimation(null);
                                    FieldSelectionActivity.waitScreen.setVisibility(View.GONE);
                                }

                            }
                        } else {

                            FieldSelectionActivity.fragmentStatus++;
                            if (FieldSelectionActivity.fragmentStatus == 3 && FieldSelectionActivity.civiRotate != null) {
                                FieldSelectionActivity.civiRotate.setAnimation(null);
                                FieldSelectionActivity.waitScreen.setVisibility(View.GONE);
                            }

                        }
                    } else {
                        //FieldSelectionActivity.progressDialog.dismiss();
                        Toast.makeText(ContactsSubTypeSelectionFragment.this.getActivity(), "Authentication failed! Patience is the key, get the details right! :)", Toast.LENGTH_LONG).show();
                        ContactsSubTypeSelectionFragment.this.getActivity().onBackPressed();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    if (getActivity() != null) {
                        Toast.makeText(ContactsSubTypeSelectionFragment.this.getActivity(), "Authentication failed! Patience is the key, get the details right! :)", Toast.LENGTH_LONG).show();
                        ContactsSubTypeSelectionFragment.this.getActivity().onBackPressed();
                    }
                }
            });
        } while (this.count == 25);


        return v;
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case id.rbAllContacts:
                this.selectedChoice = 0;
                if (this.rbOnlySubtypes.isEnabled() && this.contactSubtypeAdapter.isEnabled()) {
                    this.contactSubtypeAdapter.setEnabled(false);
                }
                break;
            case id.rbNoContacts:
                this.selectedChoice = 1;
                if (this.rbOnlySubtypes.isEnabled() && this.contactSubtypeAdapter.isEnabled()) {
                    this.contactSubtypeAdapter.setEnabled(false);
                }
                break;
            case id.rbOnlySubtypes:
                this.selectedChoice = 2;
                this.contactSubtypeAdapter.setEnabled(true);
                break;
        }
    }
}
