package com.ribo.osori.member.application

import com.ribo.osori.common.exception.ErrorCode
import com.ribo.osori.member.application.dto.request.SignupRequest
import com.ribo.osori.member.domain.Member
import com.ribo.osori.member.domain.MemberRepository
import com.ribo.osori.member.domain.vo.Email
import com.ribo.osori.member.domain.vo.NickName
import com.ribo.osori.member.domain.vo.Password
import com.ribo.osori.member.exception.DuplicateEmailException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
) {

    @Transactional
    fun createMember(signupRequest: SignupRequest) {
        val email: String = signupRequest.email
        validateDuplicateEmail(email)

        val member: Member = Member(
            id = null,
            email = Email(email),
            password = Password().encryptPassword(passwordEncoder, signupRequest.password),
            nickName = NickName(signupRequest.nickName),
            imageUrl = null,
        )

        memberRepository.save(member)
    }

    private fun validateDuplicateEmail(email: String) {
        if (memberRepository.existsByEmailValue(email)) {
            throw DuplicateEmailException(email, ErrorCode.DUPLICATE_EMAIL)
        }
    }
}