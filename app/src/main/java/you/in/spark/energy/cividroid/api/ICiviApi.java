package you.in.spark.energy.cividroid.api;

import retrofit.Callback;
import retrofit.http.POST;
import retrofit.http.Query;
import you.in.spark.energy.cividroid.entities.QueryResult;


public interface ICiviApi {
        @POST("/wordpress/wp-content/plugins/civicrm/civicrm/extern/rest.php?entity=Contact&action=get")      //here is the other url part.best way is to start using /
        public void getContacts(@Query("json") String json, @Query("api_key") String apiKey, @Query("key") String siteKey,Callback<QueryResult> response);     //string user is for passing values from edittext for eg: user=basil2style,google

        @POST("/wordpress/wp-content/plugins/civicrm/civicrm/extern/rest.php?entity=Contact&action=getfields&json={\"sequential\":1,\"api_action\":\"\"}")
        public void getContactsFields(@Query("api_key") String apiKey, @Query("key") String siteKey,Callback<QueryResult> response);

        @POST("/wordpress/wp-content/plugins/civicrm/civicrm/extern/rest.php?entity=Activity&action=getfields&json={\"sequential\":1,\"api_action\":\"\"}")
        public void getActivityFields(@Query("api_key") String apiKey, @Query("key") String siteKey,Callback<QueryResult> response);

}
