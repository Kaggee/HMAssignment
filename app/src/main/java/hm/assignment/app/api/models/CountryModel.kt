package hm.assignment.app.api.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import hm.assignment.app.util.Constants
import java.io.Serializable

/**
 * Created on 2022-03-04.
 * CopyrightⒸ Kagge
 */

@Entity(tableName = Constants.CountriesTable)
data class CountryModel(
    @PrimaryKey val name: String,
    @ColumnInfo val capital: String,
    @ColumnInfo val region: String,
    @ColumnInfo val alpha2Code: String,
    @ColumnInfo var favourite: Boolean = false
): Serializable