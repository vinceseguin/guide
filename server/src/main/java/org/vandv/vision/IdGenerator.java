package org.vandv.vision;

/**
 * Created by vinceseguin on 23/07/14.
 */
public class IdGenerator {

    private static long idGenerator = 0;

    public long generate() {
        return ++idGenerator;
    }
}