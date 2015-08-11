
package you.in.spark.energy.cividroid.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleValue {

    @SerializedName("contact_id")
    @Expose
    private String contactId;
    @SerializedName("contact_type")
    @Expose
    private String contactType;
    @SerializedName("contact_sub_type")
    @Expose
    private String contactSubType;
    @SerializedName("sort_name")
    @Expose
    private String sortName;
    @SerializedName("display_name")
    @Expose
    private String displayName;
    @SerializedName("do_not_email")
    @Expose
    private String doNotEmail;
    @SerializedName("do_not_phone")
    @Expose
    private String doNotPhone;
    @SerializedName("do_not_mail")
    @Expose
    private String doNotMail;
    @SerializedName("do_not_sms")
    @Expose
    private String doNotSms;
    @SerializedName("do_not_trade")
    @Expose
    private String doNotTrade;
    @SerializedName("is_opt_out")
    @Expose
    private String isOptOut;
    @SerializedName("legal_identifier")
    @Expose
    private String legalIdentifier;
    @SerializedName("external_identifier")
    @Expose
    private String externalIdentifier;
    @SerializedName("nick_name")
    @Expose
    private String nickName;
    @SerializedName("legal_name")
    @Expose
    private String legalName;
    @SerializedName("image_URL")
    @Expose
    private String imageURL;
    @SerializedName("preferred_communication_method")
    @Expose
    private String preferredCommunicationMethod;
    @SerializedName("preferred_language")
    @Expose
    private String preferredLanguage;
    @SerializedName("preferred_mail_format")
    @Expose
    private String preferredMailFormat;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("middle_name")
    @Expose
    private String middleName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("prefix_id")
    @Expose
    private String prefixId;
    @SerializedName("suffix_id")
    @Expose
    private String suffixId;
    @SerializedName("formal_title")
    @Expose
    private String formalTitle;
    @SerializedName("communication_style_id")
    @Expose
    private String communicationStyleId;
    @SerializedName("job_title")
    @Expose
    private String jobTitle;
    @SerializedName("gender_id")
    @Expose
    private String genderId;
    @SerializedName("birth_date")
    @Expose
    private String birthDate;
    @SerializedName("is_deceased")
    @Expose
    private String isDeceased;
    @SerializedName("deceased_date")
    @Expose
    private String deceasedDate;
    @SerializedName("household_name")
    @Expose
    private String householdName;
    @SerializedName("organization_name")
    @Expose
    private String organizationName;
    @SerializedName("sic_code")
    @Expose
    private String sicCode;
    @SerializedName("contact_is_deleted")
    @Expose
    private String contactIsDeleted;
    @SerializedName("current_employer")
    @Expose
    private String currentEmployer;
    @SerializedName("address_id")
    @Expose
    private String addressId;
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
    @SerializedName("geo_code_1")
    @Expose
    private String geoCode1;
    @SerializedName("geo_code_2")
    @Expose
    private String geoCode2;
    @SerializedName("state_province_id")
    @Expose
    private String stateProvinceId;
    @SerializedName("country_id")
    @Expose
    private String countryId;
    @SerializedName("phone_id")
    @Expose
    private String phoneId;
    @SerializedName("phone_type_id")
    @Expose
    private String phoneTypeId;
    @Expose
    private String phone;
    @SerializedName("email_id")
    @Expose
    private String emailId;
    @Expose
    private String email;
    @SerializedName("on_hold")
    @Expose
    private String onHold;
    @SerializedName("im_id")
    @Expose
    private String imId;
    @SerializedName("provider_id")
    @Expose
    private String providerId;
    @Expose
    private String im;
    @SerializedName("worldregion_id")
    @Expose
    private String worldregionId;
    @SerializedName("world_region")
    @Expose
    private String worldRegion;
    @SerializedName("individual_prefix")
    @Expose
    private String individualPrefix;
    @SerializedName("individual_suffix")
    @Expose
    private String individualSuffix;
    @SerializedName("communication_style")
    @Expose
    private String communicationStyle;
    @Expose
    private String gender;
    @SerializedName("state_province_name")
    @Expose
    private String stateProvinceName;
    @SerializedName("state_province")
    @Expose
    private String stateProvince;
    @Expose
    private String country;
    @Expose
    private String id;

    /**
     *
     * @return
     * The contactId
     */
    public String getContactId() {
        return contactId;
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
     * The contactType
     */
    public String getContactType() {
        return contactType;
    }

    /**
     *
     * @param contactType
     * The contact_type
     */
    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    /**
     *
     * @return
     * The contactSubType
     */
    public String getContactSubType() {
        return contactSubType;
    }

    /**
     *
     * @param contactSubType
     * The contact_sub_type
     */
    public void setContactSubType(String contactSubType) {
        this.contactSubType = contactSubType;
    }

    /**
     *
     * @return
     * The sortName
     */
    public String getSortName() {
        return sortName;
    }

    /**
     *
     * @param sortName
     * The sort_name
     */
    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    /**
     *
     * @return
     * The displayName
     */
    public String getDisplayName() {
        return displayName;
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
     * The doNotEmail
     */
    public String getDoNotEmail() {
        return doNotEmail;
    }

    /**
     *
     * @param doNotEmail
     * The do_not_email
     */
    public void setDoNotEmail(String doNotEmail) {
        this.doNotEmail = doNotEmail;
    }

    /**
     *
     * @return
     * The doNotPhone
     */
    public String getDoNotPhone() {
        return doNotPhone;
    }

    /**
     *
     * @param doNotPhone
     * The do_not_phone
     */
    public void setDoNotPhone(String doNotPhone) {
        this.doNotPhone = doNotPhone;
    }

    /**
     *
     * @return
     * The doNotMail
     */
    public String getDoNotMail() {
        return doNotMail;
    }

    /**
     *
     * @param doNotMail
     * The do_not_mail
     */
    public void setDoNotMail(String doNotMail) {
        this.doNotMail = doNotMail;
    }

    /**
     *
     * @return
     * The doNotSms
     */
    public String getDoNotSms() {
        return doNotSms;
    }

    /**
     *
     * @param doNotSms
     * The do_not_sms
     */
    public void setDoNotSms(String doNotSms) {
        this.doNotSms = doNotSms;
    }

    /**
     *
     * @return
     * The doNotTrade
     */
    public String getDoNotTrade() {
        return doNotTrade;
    }

    /**
     *
     * @param doNotTrade
     * The do_not_trade
     */
    public void setDoNotTrade(String doNotTrade) {
        this.doNotTrade = doNotTrade;
    }

    /**
     *
     * @return
     * The isOptOut
     */
    public String getIsOptOut() {
        return isOptOut;
    }

    /**
     *
     * @param isOptOut
     * The is_opt_out
     */
    public void setIsOptOut(String isOptOut) {
        this.isOptOut = isOptOut;
    }

    /**
     *
     * @return
     * The legalIdentifier
     */
    public String getLegalIdentifier() {
        return legalIdentifier;
    }

    /**
     *
     * @param legalIdentifier
     * The legal_identifier
     */
    public void setLegalIdentifier(String legalIdentifier) {
        this.legalIdentifier = legalIdentifier;
    }

    /**
     *
     * @return
     * The externalIdentifier
     */
    public String getExternalIdentifier() {
        return externalIdentifier;
    }

    /**
     *
     * @param externalIdentifier
     * The external_identifier
     */
    public void setExternalIdentifier(String externalIdentifier) {
        this.externalIdentifier = externalIdentifier;
    }

    /**
     *
     * @return
     * The nickName
     */
    public String getNickName() {
        return nickName;
    }

    /**
     *
     * @param nickName
     * The nick_name
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     *
     * @return
     * The legalName
     */
    public String getLegalName() {
        return legalName;
    }

    /**
     *
     * @param legalName
     * The legal_name
     */
    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    /**
     *
     * @return
     * The imageURL
     */
    public String getImageURL() {
        return imageURL;
    }

    /**
     *
     * @param imageURL
     * The image_URL
     */
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    /**
     *
     * @return
     * The preferredCommunicationMethod
     */
    public String getPreferredCommunicationMethod() {
        return preferredCommunicationMethod;
    }

    /**
     *
     * @param preferredCommunicationMethod
     * The preferred_communication_method
     */
    public void setPreferredCommunicationMethod(String preferredCommunicationMethod) {
        this.preferredCommunicationMethod = preferredCommunicationMethod;
    }

    /**
     *
     * @return
     * The preferredLanguage
     */
    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    /**
     *
     * @param preferredLanguage
     * The preferred_language
     */
    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    /**
     *
     * @return
     * The preferredMailFormat
     */
    public String getPreferredMailFormat() {
        return preferredMailFormat;
    }

    /**
     *
     * @param preferredMailFormat
     * The preferred_mail_format
     */
    public void setPreferredMailFormat(String preferredMailFormat) {
        this.preferredMailFormat = preferredMailFormat;
    }

    /**
     *
     * @return
     * The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName
     * The first_name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return
     * The middleName
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     *
     * @param middleName
     * The middle_name
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     *
     * @return
     * The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName
     * The last_name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return
     * The prefixId
     */
    public String getPrefixId() {
        return prefixId;
    }

    /**
     *
     * @param prefixId
     * The prefix_id
     */
    public void setPrefixId(String prefixId) {
        this.prefixId = prefixId;
    }

    /**
     *
     * @return
     * The suffixId
     */
    public String getSuffixId() {
        return suffixId;
    }

    /**
     *
     * @param suffixId
     * The suffix_id
     */
    public void setSuffixId(String suffixId) {
        this.suffixId = suffixId;
    }

    /**
     *
     * @return
     * The formalTitle
     */
    public String getFormalTitle() {
        return formalTitle;
    }

    /**
     *
     * @param formalTitle
     * The formal_title
     */
    public void setFormalTitle(String formalTitle) {
        this.formalTitle = formalTitle;
    }

    /**
     *
     * @return
     * The communicationStyleId
     */
    public String getCommunicationStyleId() {
        return communicationStyleId;
    }

    /**
     *
     * @param communicationStyleId
     * The communication_style_id
     */
    public void setCommunicationStyleId(String communicationStyleId) {
        this.communicationStyleId = communicationStyleId;
    }

    /**
     *
     * @return
     * The jobTitle
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     *
     * @param jobTitle
     * The job_title
     */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    /**
     *
     * @return
     * The genderId
     */
    public String getGenderId() {
        return genderId;
    }

    /**
     *
     * @param genderId
     * The gender_id
     */
    public void setGenderId(String genderId) {
        this.genderId = genderId;
    }

    /**
     *
     * @return
     * The birthDate
     */
    public String getBirthDate() {
        return birthDate;
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
     * The isDeceased
     */
    public String getIsDeceased() {
        return isDeceased;
    }

    /**
     *
     * @param isDeceased
     * The is_deceased
     */
    public void setIsDeceased(String isDeceased) {
        this.isDeceased = isDeceased;
    }

    /**
     *
     * @return
     * The deceasedDate
     */
    public String getDeceasedDate() {
        return deceasedDate;
    }

    /**
     *
     * @param deceasedDate
     * The deceased_date
     */
    public void setDeceasedDate(String deceasedDate) {
        this.deceasedDate = deceasedDate;
    }

    /**
     *
     * @return
     * The householdName
     */
    public String getHouseholdName() {
        return householdName;
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
        return organizationName;
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
     * The sicCode
     */
    public String getSicCode() {
        return sicCode;
    }

    /**
     *
     * @param sicCode
     * The sic_code
     */
    public void setSicCode(String sicCode) {
        this.sicCode = sicCode;
    }

    /**
     *
     * @return
     * The contactIsDeleted
     */
    public String getContactIsDeleted() {
        return contactIsDeleted;
    }

    /**
     *
     * @param contactIsDeleted
     * The contact_is_deleted
     */
    public void setContactIsDeleted(String contactIsDeleted) {
        this.contactIsDeleted = contactIsDeleted;
    }

    /**
     *
     * @return
     * The currentEmployer
     */
    public String getCurrentEmployer() {
        return currentEmployer;
    }

    /**
     *
     * @param currentEmployer
     * The current_employer
     */
    public void setCurrentEmployer(String currentEmployer) {
        this.currentEmployer = currentEmployer;
    }

    /**
     *
     * @return
     * The addressId
     */
    public String getAddressId() {
        return addressId;
    }

    /**
     *
     * @param addressId
     * The address_id
     */
    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    /**
     *
     * @return
     * The streetAddress
     */
    public String getStreetAddress() {
        return streetAddress;
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
        return supplementalAddress1;
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
        return supplementalAddress2;
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
        return city;
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
        return postalCodeSuffix;
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
        return postalCode;
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
     * The geoCode1
     */
    public String getGeoCode1() {
        return geoCode1;
    }

    /**
     *
     * @param geoCode1
     * The geo_code_1
     */
    public void setGeoCode1(String geoCode1) {
        this.geoCode1 = geoCode1;
    }

    /**
     *
     * @return
     * The geoCode2
     */
    public String getGeoCode2() {
        return geoCode2;
    }

    /**
     *
     * @param geoCode2
     * The geo_code_2
     */
    public void setGeoCode2(String geoCode2) {
        this.geoCode2 = geoCode2;
    }

    /**
     *
     * @return
     * The stateProvinceId
     */
    public String getStateProvinceId() {
        return stateProvinceId;
    }

    /**
     *
     * @param stateProvinceId
     * The state_province_id
     */
    public void setStateProvinceId(String stateProvinceId) {
        this.stateProvinceId = stateProvinceId;
    }

    /**
     *
     * @return
     * The countryId
     */
    public String getCountryId() {
        return countryId;
    }

    /**
     *
     * @param countryId
     * The country_id
     */
    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    /**
     *
     * @return
     * The phoneId
     */
    public String getPhoneId() {
        return phoneId;
    }

    /**
     *
     * @param phoneId
     * The phone_id
     */
    public void setPhoneId(String phoneId) {
        this.phoneId = phoneId;
    }

    /**
     *
     * @return
     * The phoneTypeId
     */
    public String getPhoneTypeId() {
        return phoneTypeId;
    }

    /**
     *
     * @param phoneTypeId
     * The phone_type_id
     */
    public void setPhoneTypeId(String phoneTypeId) {
        this.phoneTypeId = phoneTypeId;
    }

    /**
     *
     * @return
     * The phone
     */
    public String getPhone() {
        return phone;
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
     * The emailId
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     *
     * @param emailId
     * The email_id
     */
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
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
     * The onHold
     */
    public String getOnHold() {
        return onHold;
    }

    /**
     *
     * @param onHold
     * The on_hold
     */
    public void setOnHold(String onHold) {
        this.onHold = onHold;
    }

    /**
     *
     * @return
     * The imId
     */
    public String getImId() {
        return imId;
    }

    /**
     *
     * @param imId
     * The im_id
     */
    public void setImId(String imId) {
        this.imId = imId;
    }

    /**
     *
     * @return
     * The providerId
     */
    public String getProviderId() {
        return providerId;
    }

    /**
     *
     * @param providerId
     * The provider_id
     */
    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    /**
     *
     * @return
     * The im
     */
    public String getIm() {
        return im;
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
     * The worldregionId
     */
    public String getWorldregionId() {
        return worldregionId;
    }

    /**
     *
     * @param worldregionId
     * The worldregion_id
     */
    public void setWorldregionId(String worldregionId) {
        this.worldregionId = worldregionId;
    }

    /**
     *
     * @return
     * The worldRegion
     */
    public String getWorldRegion() {
        return worldRegion;
    }

    /**
     *
     * @param worldRegion
     * The world_region
     */
    public void setWorldRegion(String worldRegion) {
        this.worldRegion = worldRegion;
    }

    /**
     *
     * @return
     * The individualPrefix
     */
    public String getIndividualPrefix() {
        return individualPrefix;
    }

    /**
     *
     * @param individualPrefix
     * The individual_prefix
     */
    public void setIndividualPrefix(String individualPrefix) {
        this.individualPrefix = individualPrefix;
    }

    /**
     *
     * @return
     * The individualSuffix
     */
    public String getIndividualSuffix() {
        return individualSuffix;
    }

    /**
     *
     * @param individualSuffix
     * The individual_suffix
     */
    public void setIndividualSuffix(String individualSuffix) {
        this.individualSuffix = individualSuffix;
    }

    /**
     *
     * @return
     * The communicationStyle
     */
    public String getCommunicationStyle() {
        return communicationStyle;
    }

    /**
     *
     * @param communicationStyle
     * The communication_style
     */
    public void setCommunicationStyle(String communicationStyle) {
        this.communicationStyle = communicationStyle;
    }

    /**
     *
     * @return
     * The gender
     */
    public String getGender() {
        return gender;
    }

    /**
     *
     * @param gender
     * The gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     *
     * @return
     * The stateProvinceName
     */
    public String getStateProvinceName() {
        return stateProvinceName;
    }

    /**
     *
     * @param stateProvinceName
     * The state_province_name
     */
    public void setStateProvinceName(String stateProvinceName) {
        this.stateProvinceName = stateProvinceName;
    }

    /**
     *
     * @return
     * The stateProvince
     */
    public String getStateProvince() {
        return stateProvince;
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
        return country;
    }

    /**
     *
     * @param country
     * The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

}