package org.vandv.vision;

/**
 * Generates ids for the client's request
 *
 * Created by vinceseguin on 23/07/14.
 */
public class IdGenerator {

    private static long idGenerator = 0;

    /**
     * Generate a new id
     * @return the id
     */
    public long generate() {
        return ++idGenerator;
    }
}