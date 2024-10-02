package com.app.tests;

import com.intuit.karate.junit5.Karate;
import com.intuit.karate.RuntimeHook;

class UsersRunner {
    
    @Karate.Test
    Karate testUsers() {

        return Karate.run("users").relativeTo(getClass());
    }    

}
