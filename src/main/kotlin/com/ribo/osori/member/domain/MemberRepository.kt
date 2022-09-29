package com.ribo.osori.member.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface MemberRepository : JpaRepository<Member, Long>{

    fun existsByEmailValue(email: String?): Boolean

    @Query("SELECT m FROM Member m  WHERE m.email.value = :email")
    fun findMemberByEmail(@Param("email") email: String): Member


}