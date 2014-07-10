package org.vandv.model.google;

import java.util.List;

/**
 * Created by vinceseguin on 10/07/14.
 */
public class MapSearchResult {
    private Location location;
    private String icon;
    private String id;
    private String name;
    private boolean isOpenNow; //TODO: is this valid
    private List<Photo> photos;
    private String placeId;
    private String scope;
    private String reference;
    private List<String> types;
    private String vicinity;
}
