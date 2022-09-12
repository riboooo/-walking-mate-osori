package com.ribo.osori.member.domain

import com.ribo.osori.common.exception.ErrorCode
import com.ribo.osori.common.model.BaseEntity
import com.ribo.osori.member.domain.vo.Email
import com.ribo.osori.member.domain.vo.NickName
import com.ribo.osori.member.domain.vo.Password
import com.ribo.osori.member.exception.LoginFailedException
import org.springframework.security.crypto.password.PasswordEncoder
import javax.persistence.*


class Member(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long,

    @Embedded
    private val email: Email,

    @Embedded
    private val password: Password,

    @Embedded
    private var nickName: NickName,

    @Column
    private var imageUrl: String?,

    ) : BaseEntity() {

    fun checkPassword(passwordEncoder: PasswordEncoder?, inputPassword: String?) {
        if (passwordEncoder?.let { password.isNotSamePassword(it, inputPassword) } == true) {
            throw LoginFailedException(ErrorCode.LOGIN_FAILED)
        }
    }
    fun changeNickName(nickName: String?) {
        this.nickName = NickName(nickName)
    }

    fun changeProfileImage(imageUrl: String?) {
        this.imageUrl = imageUrl!!
    }

    fun isSame(id: Long?): Boolean {
        return this.id == id
    }
}