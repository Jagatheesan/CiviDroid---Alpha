package you.in.spark.energy.cividroid;


public class CiviContract {

    public static final String SITE_KEY = "sitekey";
    public static final String API_KEY = "apikey";
    public static final String AUTHORITY = "com.android.contacts";
    public static final String PROVIDER_AUTHORITY = "you.in.spark.energy.cividroid.provider";
    public static final String CONTENT_URI = "content://"+ CiviContract.PROVIDER_AUTHORITY;
    public static final String ACCOUNT_TYPE = "you.in.spark.cividroid.account";
    public static final String ACCOUNT = "CiviDroid";
    public static final String CALL_FROM_ACTIVITY = "callFromActivity";
    public static final String CONTACT_ID_FIELD = "contact_id_on_device";

    public static final String ID_COLUMN = "_id";
    public static final String CONTACTS_FIELD_TABLE = "contacts_field_table";

    public static final String LAST_CONV_DATE_PREF = "lastcallconvpref";
    public static final String CONTACT_CONV_TABLE = "conconvtable";

    public static final String NUMBER_CALL_LOG_COLUMN = "number";
    public static final String DATE_CALL_LOG_COLUMN = "date";
    public static final String DURATION_CALL_LOG_COLUMN = "duration";
    public static final String NAME_CALL_LOG_COLUMN = "name";
    public static final String TYPE_CALL_LOG_COLUMN = "type";

    public static final String[] CALL_LOG_COLUMNS = {CiviContract.NUMBER_CALL_LOG_COLUMN, CiviContract.DATE_CALL_LOG_COLUMN, CiviContract.DURATION_CALL_LOG_COLUMN, CiviContract.NAME_CALL_LOG_COLUMN, CiviContract.TYPE_CALL_LOG_COLUMN};

    //offset by one
    public static final String[] CONTACT_FIELD_NAMES = {"Contact ID", "Contact Type","Contact Subtype", "Display Name", "Birthdate", "Household Name", "Organization Name", "Street Address", "Supplemental Address 1", "Supplemental Address 2", "City", "Post Code Suffix", "Postal Code", "Phone", "Email", "IM", "State Province", "Country"};
    public static final String[] CONTACT_TABLE_COLUMNS = {"contact_id", "contact_type", "contact_sub_type", "display_name", "birth_date", "household_name", "organization_name", "street_address", "supplemental_address_1", "supplemental_address_2", "city", "postal_code_suffix", "postal_code", "phone", "email", "im", "state_province", "country" };
    public static final String ACTIVITY_TABLE = "activitytable";
    public static final String TARGET_CONTACT_ID_COLUMN = "target_contact_id";

    public static final String LAST_ACTIVITY_SYNC_ID = "lasacsyncid";
    public static final String LAST_NOTES_SYNC_ID = "lastnotessyncid";

    public static final String[] ACTIVITY_FIELD_NAMES = {"Activity ID", "Activity Type", "Subject", "Reminder", "Duration", "Location", "Details", "Status", "Priority", "Source Contact", "Phone Number", "Dirty"};
    public static final String[] ACTIVITY_TABLE_COLUMNS = {"id", "activity_type_id", "subject", "activity_date_time", "duration", "location", "details", "status_id", "priority_id", "source_contact_id", "phone_number", "dirty"};


    public static final String NOTES_TABLE ="notestable";
    public static final String NOTES_COLUMN = "notescolumn";
    public static final String NOTES_DATE_COLUMN = "notesdatecolumn";
    public static final String NOTES_DURATION_COLUMN = "notesdurationcolumn";
    public static final String PHOTO_URI = "photouri";

    public static final String WEBSITE_URL = "websiteurl";
    public static final String SOURCE_CONTACT_ID = "sourcecontactid";
}
