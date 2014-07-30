package org.vandv.navigation;

/**
 * Created by vinceseguin on 23/07/14.
 */
public class NavigationManager {

    private static NavigationManager instance;
    private final IdGenerator idGenerator;

    private NavigationManager(){
        idGenerator = new IdGenerator();
    }

    public static synchronized NavigationManager getInstance() {
        if (instance == null) {
            instance = new NavigationManager();
        }

        return instance;
    }

    public long startNavigation(String address) {


        return idGenerator.generate();
    }
}
