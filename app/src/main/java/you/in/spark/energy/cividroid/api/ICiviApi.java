package you.in.spark.energy.cividroid.api;


import java.util.Map;

import retrofit.Callback;
import retrofit.http.POST;
import retrofit.http.QueryMap;
import you.in.spark.energy.cividroid.entities.CiviActivity;
import you.in.spark.energy.cividroid.entities.Contact;
import you.in.spark.energy.cividroid.entities.ContactType;
import you.in.spark.energy.cividroid.entities.WriteNotesResult;


public interface ICiviApi {

    @POST("/rest.php?entity=ContactType&action=get")
    void getContactSubtypes(@QueryMap Map<String, String> fields, Callback<ContactType> response);

    @POST("/rest.php?entity=Contact&action=get")
    Contact getContacts(@QueryMap Map<String, String> fields);

    @POST("/rest.php?entity=Activity&action=get")
    CiviActivity getActivity(@QueryMap Map<String, String> fields);

    @POST("/rest.php?entity=Activity&action=create")
    WriteNotesResult writeNotes(@QueryMap Map<String, String> fields);

}
