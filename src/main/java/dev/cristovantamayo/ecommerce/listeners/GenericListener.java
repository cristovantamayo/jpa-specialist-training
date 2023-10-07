package dev.cristovantamayo.ecommerce.listeners;

import javax.persistence.PostLoad;

public class GenericListener {

    @PostLoad
    public void loadingLogging(Object obj) {
        System.out.println("-----------> Entity " + obj.getClass().getSimpleName() + " has been Loaded!");
    }
}
