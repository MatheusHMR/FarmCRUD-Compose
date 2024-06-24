package pdm.compose.pdm_trabalhofazendas_2.model

import android.os.Parcel
import android.os.Parcelable


data class Farm (
    val record : String = "",
    var name: String = "",
    var price: Float = 0f,
    var latitude: Float = 0f,
    var longitude: Float = 0f
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readFloat()
    )

    fun toAttributeMap(): Map<String, String> {
        return mapOf(
            "Record" to record,
            "Name" to name,
            "Price" to price.toString(),
            "Latitude" to latitude.toString(),
            "Longitude" to longitude.toString(),
        )
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(record)
        parcel.writeString(name)
        parcel.writeFloat(price)
        parcel.writeFloat(latitude)
        parcel.writeFloat(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Farm> {
        override fun createFromParcel(parcel: Parcel): Farm {
            return Farm(parcel)
        }

        override fun newArray(size: Int): Array<Farm?> {
            return arrayOfNulls(size)
        }
    }
}