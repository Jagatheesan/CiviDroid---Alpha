package you.in.spark.energy.cividroid.entities;

import android.content.ContentValues;
import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import you.in.spark.energy.cividroid.CiviContract;

public class Contact {

    @SerializedName("is_error")
    @Expose
    private int isError;
    @Expose
    private int version;
    @Expose
    private int count;
    @Expose
    private List<Contact.Value> values = new ArrayList<Contact.Value>();

    /**
     *
     * @return
     * The isError
     */
    public int getIsError() {
        return this.isError;
    }

    /**
     *
     * @param isError
     * The is_error
     */
    public void setIsError(int isError) {
        this.isError = isError;
    }

    /**
     *
     * @return
     * The version
     */
    public int getVersion() {
        return this.version;
    }

    /**
     *
     * @param version
     * The version
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     *
     * @return
     * The count
     */
    public int getCount() {
        return this.count;
    }

    /**
     *
     * @param count
     * The count
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     *
     * @return
     * The values
     */
    public List<Contact.Value> getValues() {
        return this.values;
    }

    /**
     *
     * @param values
     * The values
     */
    public void setValues(List<Contact.Value> values) {
        this.values = values;
    }


public class Value{
    @SerializedName("contact_id")
    @Expose
    private String contactId;
    @SerializedName("display_name")
    @Expose
    private String displayName;
    @SerializedName("birth_date")
    @Expose
    private String birthDate;
    @SerializedName("household_name")
    @Expose
    private String householdName;
    @SerializedName("organization_name")
    @Expose
    private String organizationName;
    @SerializedName("street_address")
    @Expose
    private String streetAddress;
    @SerializedName("supplemental_address_1")
    @Expose
    private String supplementalAddress1;
    @SerializedName("supplemental_address_2")
    @Expose
    private String supplementalAddress2;
    @Expose
    private String city;
    @SerializedName("postal_code_suffix")
    @Expose
    private String postalCodeSuffix;
    @SerializedName("postal_code")
    @Expose
    private String postalCode;
    @Expose
    private String phone;
    @Expose
    private String email;
    @Expose
    private String im;
    @SerializedName("state_province")
    @Expose
    private String stateProvince;
    @Expose
    private String country;

    /**
     *
     * @return
     * The contactId
     */
    public String getContactId() {
        return this.contactId;
    }

    /**
     *
     * @param contactId
     * The contact_id
     */
    public void setContactId(String contactId) {
        this.contactId = contactId;
    }


    /**
     *
     * @return
     * The displayName
     */
    public String getDisplayName() {
        return this.displayName;
    }

    /**
     *
     * @param displayName
     * The display_name
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     *
     * @return
     * The birthDate
     */
    public String getBirthDate() {
        return this.birthDate;
    }

    /**
     *
     * @param birthDate
     * The birth_date
     */
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    /**
     *
     * @return
     * The householdName
     */
    public String getHouseholdName() {
        return this.householdName;
    }

    /**
     *
     * @param householdName
     * The household_name
     */
    public void setHouseholdName(String householdName) {
        this.householdName = householdName;
    }

    /**
     *
     * @return
     * The organizationName
     */
    public String getOrganizationName() {
        return this.organizationName;
    }

    /**
     *
     * @param organizationName
     * The organization_name
     */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    /**
     *
     * @return
     * The streetAddress
     */
    public String getStreetAddress() {
        return this.streetAddress;
    }

    /**
     *
     * @param streetAddress
     * The street_address
     */
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    /**
     *
     * @return
     * The supplementalAddress1
     */
    public String getSupplementalAddress1() {
        return this.supplementalAddress1;
    }

    /**
     *
     * @param supplementalAddress1
     * The supplemental_address_1
     */
    public void setSupplementalAddress1(String supplementalAddress1) {
        this.supplementalAddress1 = supplementalAddress1;
    }

    /**
     *
     * @return
     * The supplementalAddress2
     */
    public String getSupplementalAddress2() {
        return this.supplementalAddress2;
    }

    /**
     *
     * @param supplementalAddress2
     * The supplemental_address_2
     */
    public void setSupplementalAddress2(String supplementalAddress2) {
        this.supplementalAddress2 = supplementalAddress2;
    }

    /**
     *
     * @return
     * The city
     */
    public String getCity() {
        return this.city;
    }

    /**
     *
     * @param city
     * The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * @return
     * The postalCodeSuffix
     */
    public String getPostalCodeSuffix() {
        return this.postalCodeSuffix;
    }

    /**
     *
     * @param postalCodeSuffix
     * The postal_code_suffix
     */
    public void setPostalCodeSuffix(String postalCodeSuffix) {
        this.postalCodeSuffix = postalCodeSuffix;
    }

    /**
     *
     * @return
     * The postalCode
     */
    public String getPostalCode() {
        return this.postalCode;
    }

    /**
     *
     * @param postalCode
     * The postal_code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }


    /**
     *
     * @return
     * The phone
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     *
     * @param phone
     * The phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The im
     */
    public String getIm() {
        return this.im;
    }

    /**
     *
     * @param im
     * The im
     */
    public void setIm(String im) {
        this.im = im;
    }

    /**
     *
     * @return
     * The stateProvince
     */
    public String getStateProvince() {
        return this.stateProvince;
    }

    /**
     *
     * @param stateProvince
     * The state_province
     */
    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    /**
     *
     * @return
     * The country
     */
    public String getCountry() {
        return this.country;
    }

    /**
     *
     * @param country
     * The country
     */
    public void setCountry(String country) {
        this.country = country;
    }


        public ContentValues getAllValues(String contactType, String contactSubtype) {

            ContentValues vals = new ContentValues();
            int i = 0;
            vals.put(CiviContract.CONTACT_TABLE_COLUMNS[i++], this.getContactId());
            vals.put(CiviContract.CONTACT_TABLE_COLUMNS[i++],contactType);
            vals.put(CiviContract.CONTACT_TABLE_COLUMNS[i++],contactSubtype);
            vals.put(CiviContract.CONTACT_TABLE_COLUMNS[i++], this.getDisplayName());
            vals.put(CiviContract.CONTACT_TABLE_COLUMNS[i++], this.getBirthDate());
            vals.put(CiviContract.CONTACT_TABLE_COLUMNS[i++], this.getHouseholdName());
            vals.put(CiviContract.CONTACT_TABLE_COLUMNS[i++], this.getOrganizationName());
            vals.put(CiviContract.CONTACT_TABLE_COLUMNS[i++], this.getStreetAddress());
            vals.put(CiviContract.CONTACT_TABLE_COLUMNS[i++], this.getSupplementalAddress1());
            vals.put(CiviContract.CONTACT_TABLE_COLUMNS[i++], this.getSupplementalAddress2());
            vals.put(CiviContract.CONTACT_TABLE_COLUMNS[i++], this.getCity());
            vals.put(CiviContract.CONTACT_TABLE_COLUMNS[i++], this.getPostalCodeSuffix());
            vals.put(CiviContract.CONTACT_TABLE_COLUMNS[i++], this.getPostalCode());
            vals.put(CiviContract.CONTACT_TABLE_COLUMNS[i++], this.getPhone());
            vals.put(CiviContract.CONTACT_TABLE_COLUMNS[i++], this.getEmail());
            vals.put(CiviContract.CONTACT_TABLE_COLUMNS[i++], this.getIm());
            vals.put(CiviContract.CONTACT_TABLE_COLUMNS[i++], this.getStateProvince());
            vals.put(CiviContract.CONTACT_TABLE_COLUMNS[i++], this.getCountry());

            for(int k = 0 ; k<vals.size();k++) {
                Log.v("COL NAME",""+vals.getAsString(CiviContract.CONTACT_TABLE_COLUMNS[k]));
            }

            return vals;
        }
    }

}