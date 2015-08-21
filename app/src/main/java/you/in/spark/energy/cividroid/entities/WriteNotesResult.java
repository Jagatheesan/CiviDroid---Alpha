package you.in.spark.energy.cividroid.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WriteNotesResult {

    @SerializedName("is_error")
    @Expose
    private int isError;
    @Expose
    private int version;
    @Expose
    private int count;
    @Expose
    private int id;

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
     * The id
     */
    public int getId() {
        return this.id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(int id) {
        this.id = id;
    }

}
