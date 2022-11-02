package br.com.erudio.data.vo.v1

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder("id","address","first_Name","last_Name","gender")
data class PersonVO(

    var id: Long = 0,

    @field:JsonProperty("first_name")
    var firstName: String = "",

    @field:JsonProperty("last_name")
    var lastName: String = "",

    var address: String = "",
    var gender: String = "",
)
