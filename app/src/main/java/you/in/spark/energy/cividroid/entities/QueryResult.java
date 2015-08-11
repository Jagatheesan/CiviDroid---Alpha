package you.in.spark.energy.cividroid.entities;


import android.support.v4.util.Pair;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import you.in.spark.energy.cividroid.CiviContract;

public class QueryResult {

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
    private List<JsonObject> values = new ArrayList<>();

    /**x
     *
     * @return
     * The isError
     */
    public int getIsError() {
        return isError;
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
        return version;
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
        return count;
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
     * The id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The values
     */
    public List<JsonObject> getValues() {
       /* Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        //Map<String,String> stringMap = gson.fromJson(values.get("1"),type);
        //return stringMap;
        return null;*/
        return values;
    }

    public List<Pair<String,String>> getListedPairValues() {
        //since Only one element in list for fields
        List<Pair<String,String>> listedPairValues = new ArrayList<>();
        for(JsonObject jsonObject : values) {
            listedPairValues.add(new Pair<String, String>(jsonObject.get(CiviContract.TITLE_FIELD).getAsString(),jsonObject.get(CiviContract.NAME_FIELD).getAsString()));
        }
        return listedPairValues;
    }

    /**
     * @param values
     * The values
     */
    public void setValues(List<JsonObject> values) {
        this.values = values;
    }

}

