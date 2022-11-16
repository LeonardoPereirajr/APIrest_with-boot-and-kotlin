package br.com.erudio.services

import br.com.erudio.controller.PersonController
import br.com.erudio.data.vo.v1.PersonVO
import br.com.erudio.exceptions.RequiredObjectIsNullException
import br.com.erudio.data.vo.v2.PersonVO as PersonVOV2
import br.com.erudio.exceptions.ResourceNotFoundException
import br.com.erudio.mapper.DozerMapper
import br.com.erudio.mapper.custom.PersonMapper
import br.com.erudio.model.Person
import br.com.erudio.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import java.util.logging.Logger


@Service
class PersonService {

    @Autowired
    private lateinit var repository: PersonRepository

    @Autowired
    private lateinit var mapper: PersonMapper

    private val logger = Logger.getLogger(PersonService::class.java.name)

    fun findAll(): List<PersonVO> {
        logger.info("Finding All peoples!")
        val persons = repository.findAll()
        val vos = DozerMapper.parseListObjects(persons,PersonVO::class.java)
        for(person in vos){
            val withSelfRel = linkTo(PersonController::class.java).slash(person.key).withSelfRel()
            person.add(withSelfRel)
        }
        return vos
    }

    fun findById(id: Long): PersonVO {
        logger.info("Finding one person with $id!")
        val person =  repository.findById(id).orElseThrow({ ResourceNotFoundException("No records found for this ID") })
        val personVO: PersonVO = DozerMapper.parseObject(person,PersonVO::class.java )
        val withSelfRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()
        personVO.add(withSelfRel)
        return personVO
    }

    fun create(person: PersonVO?): PersonVO {//passamos o VO que chega para entity, vai até o BD,salva, devolve entity
                                            //e parse da entity para VO
        if(person==null)throw RequiredObjectIsNullException()
        logger.info("Create one person name ${person.firstName}!")
        val entity : Person = DozerMapper.parseObject(person,Person::class.java )
        val personVO: PersonVO = DozerMapper.parseObject(repository.save(entity),PersonVO::class.java)
        val withSelfRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()
        personVO.add(withSelfRel)
        return personVO
    }

    fun update(person: PersonVO?): PersonVO {
        if(person==null)throw RequiredObjectIsNullException()
        logger.info("Update one person name ${person.key}!")
        val entity = repository.findById(person.key)
            .orElseThrow({ ResourceNotFoundException("No records found for this ID") })
        entity.firstName = person.firstName
        entity.lastName = person.lastName
        entity.address = person.address
        entity.gender = person.gender
        val personVO: PersonVO = DozerMapper.parseObject(repository.save(entity),PersonVO::class.java)
        val withSelfRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()
        personVO.add(withSelfRel)
        return personVO
    }

    fun delete(id: Long) {
        logger.info("Delete a person Id $id!")
        val entity = repository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for delete this ID") }
        repository.delete(entity)
    }

    fun createV2(person: PersonVOV2): PersonVOV2 {
        logger.info("Create one person name ${person.firstName}!")
        val entity : Person = mapper.mapVOtoEntity(person)
        return mapper.mapEntityToVO(repository.save(entity))
    }
}