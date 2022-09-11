package com.ribo.osori.auth.presentation

import com.ribo.osori.auth.application.dto.request.SignInRequest
import com.ribo.osori.auth.application.dto.response.TokenResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class AuthController {

    @PostMapping("api/signin")
    fun signIn(@Valid @RequestBody signInRequest: SignInRequest):ResponseEntity<TokenResponse>{
        return ResponseEntity.ok().build();
    }
}