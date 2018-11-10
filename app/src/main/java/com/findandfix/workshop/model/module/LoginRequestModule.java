package com.findandfix.workshop.model.module;

import com.findandfix.workshop.model.request.LoginRequest;
import com.findandfix.workshop.ui.callback.scopes.AppScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by DELL on 14/05/2018.
 */
@Module
public class LoginRequestModule {

    private String email;
    private String phone;
    private String password;
    private String token;

    public LoginRequestModule(String email, String phone, String password, String token) {
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.token = token;
    }

    @Provides
    @AppScope
    public LoginRequest getLoginRequest(){
            return new LoginRequest(email,phone,password,token);
    }
}
