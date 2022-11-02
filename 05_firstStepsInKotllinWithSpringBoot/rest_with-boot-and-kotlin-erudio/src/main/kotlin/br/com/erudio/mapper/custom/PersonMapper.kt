package br.com.erudio.mapper.custom

import br.com.erudio.data.vo.v2.PersonVO
import br.com.erudio.model.Person
import org.springframework.stereotype.Service
import java.util.*

@Service
class PersonMapper {

    fun mapEntityToVO(person: Person): PersonVO{
        val vo  = PersonVO()
        vo.id = person.id
        vo.firstName = person.firstName
        vo.birthDay = Date()
        vo.lastName = person.lastName
        vo.address = person.address
        vo.gender = person.gender
        return vo
    }
    fun mapVOtoEntity(person: PersonVO): Person{
        val entity  = Person()
        entity.id = person.id
        entity.firstName = person.firstName
        entity.lastName = person.lastName
        entity.address = person.address
        entity.gender = person.gender
        return entity
    }
}