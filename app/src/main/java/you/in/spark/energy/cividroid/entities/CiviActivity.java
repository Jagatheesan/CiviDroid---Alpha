package you.in.spark.energy.cividroid.entities;

import android.content.ContentValues;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import you.in.spark.energy.cividroid.CiviContract;

public class CiviActivity {

    @SerializedName("is_error")
    @Expose
    private int isError;
    @Expose
    private int version;
    @Expose
    private int count;
    @Expose
    private int id;
    @Expose
    private List<CiviActivity.Value> values = new ArrayList<CiviActivity.Value>();

    /**
     * @return The isError
     */
    public int getIsError() {
        return this.isError;
    }

    /**
     * @param isError The is_error
     */
    public void setIsError(int isError) {
        this.isError = isError;
    }

    /**
     * @return The version
     */
    public int getVersion() {
        return this.version;
    }

    /**
     * @param version The version
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * @return The count
     */
    public int getCount() {
        return this.count;
    }

    /**
     * @param count The count
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * @return The id
     */
    public int getId() {
        return this.id;
    }

    /**
     * @param id The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return The values
     */
    public List<CiviActivity.Value> getValues() {
        return this.values;
    }

    /**
     * @param values The values
     */
    public void setValues(List<CiviActivity.Value> values) {
        this.values = values;
    }


    public class Value {

        @Expose
        private String id;
        @SerializedName("activity_type_id")
        @Expose
        private String activityTypeId;
        @Expose
        private String subject;
        @SerializedName("activity_date_time")
        @Expose
        private String activityDateTime;
        @SerializedName("phone_number")
        @Expose
        private String phoneNumber;
        @Expose
        private String duration;
        @Expose
        private String location;
        @Expose
        private String details;
        @SerializedName("status_id")
        @Expose
        private String statusId;
        @SerializedName("priority_id")
        @Expose
        private String priorityId;
        @SerializedName("source_contact_id")
        @Expose
        private String sourceContactId;
        private ContentValues allValues;


        /**
         * @return The id
         */
        public String getId() {
            return this.id;
        }

        /**
         * @param id The id
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * @return The activityTypeId
         */
        public String getActivityTypeId() {
            return this.activityTypeId;
        }

        /**
         * @param activityTypeId The activity_type_id
         */
        public void setActivityTypeId(String activityTypeId) {
            this.activityTypeId = activityTypeId;
        }

        /**
         * @return The subject
         */
        public String getSubject() {
            return this.subject;
        }

        /**
         * @param subject The subject
         */
        public void setSubject(String subject) {
            this.subject = subject;
        }

        /**
         * @return The activityDateTime
         */
        public String getActivityDateTime() {
            return this.activityDateTime;
        }

        /**
         * @param activityDateTime The activity_date_time
         */
        public void setActivityDateTime(String activityDateTime) {
            this.activityDateTime = activityDateTime;
        }

        /**
         * @return The phoneNumber
         */
        public String getPhoneNumber() {
            return this.phoneNumber;
        }

        /**
         * @param phoneNumber The phone_number
         */
        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        /**
         * @return The duration
         */
        public String getDuration() {
            return this.duration;
        }

        /**
         * @param duration The duration
         */
        public void setDuration(String duration) {
            this.duration = duration;
        }

        /**
         * @return The location
         */
        public String getLocation() {
            return this.location;
        }

        /**
         * @param location The location
         */
        public void setLocation(String location) {
            this.location = location;
        }

        /**
         * @return The details
         */
        public String getDetails() {
            return this.details;
        }

        /**
         * @param details The details
         */
        public void setDetails(String details) {
            this.details = details;
        }

        /**
         * @return The statusId
         */
        public String getStatusId() {
            return this.statusId;
        }

        /**
         * @param statusId The status_id
         */
        public void setStatusId(String statusId) {
            this.statusId = statusId;
        }

        /**
         * @return The priorityId
         */
        public String getPriorityId() {
            return this.priorityId;
        }

        /**
         * @param priorityId The priority_id
         */
        public void setPriorityId(String priorityId) {
            this.priorityId = priorityId;
        }

        /**
         * @return The sourceContactId
         */
        public String getSourceContactId() {
            return this.sourceContactId;
        }

        /**
         * @param sourceContactId The source_contact_id
         */
        public void setSourceContactId(String sourceContactId) {
            this.sourceContactId = sourceContactId;
        }

        public ContentValues getAllValues() {
            this.allValues = new ContentValues();
            int i = 0;
            this.allValues.put(CiviContract.ACTIVITY_TABLE_COLUMNS[i++], this.getId());
            this.allValues.put(CiviContract.ACTIVITY_TABLE_COLUMNS[i++], this.getActivityTypeId());
            this.allValues.put(CiviContract.ACTIVITY_TABLE_COLUMNS[i++], this.getSubject());
            this.allValues.put(CiviContract.ACTIVITY_TABLE_COLUMNS[i++], this.getActivityDateTime());
            this.allValues.put(CiviContract.ACTIVITY_TABLE_COLUMNS[i++], this.getDuration());
            this.allValues.put(CiviContract.ACTIVITY_TABLE_COLUMNS[i++], this.getLocation());
            this.allValues.put(CiviContract.ACTIVITY_TABLE_COLUMNS[i++], this.getDetails());
            this.allValues.put(CiviContract.ACTIVITY_TABLE_COLUMNS[i++], this.getStatusId());
            this.allValues.put(CiviContract.ACTIVITY_TABLE_COLUMNS[i++], this.getPriorityId());
            this.allValues.put(CiviContract.ACTIVITY_TABLE_COLUMNS[i++], this.getSourceContactId());
            this.allValues.put(CiviContract.ACTIVITY_TABLE_COLUMNS[i++], this.getPhoneNumber());
            return this.allValues;
        }

        public ContentValues getAllNotesValue() {
            ContentValues allNotesValue = new ContentValues();
            allNotesValue.put(CiviContract.ACTIVITY_TABLE_COLUMNS[0], this.getId());
            allNotesValue.put(CiviContract.ACTIVITY_TABLE_COLUMNS[3], this.getActivityDateTime());
            allNotesValue.put(CiviContract.ACTIVITY_TABLE_COLUMNS[4], this.getDuration());
            allNotesValue.put(CiviContract.ACTIVITY_TABLE_COLUMNS[6], this.getDetails());
            allNotesValue.put(CiviContract.ACTIVITY_TABLE_COLUMNS[10], this.getPhoneNumber());
            return allNotesValue;
        }
    }

}
