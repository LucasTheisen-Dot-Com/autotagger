package com.lucastheisen.autotagger.tag;


import java.util.List;


public class TagInfo {
    private String amazonAsin;
    private List<String> cast;
    private List<String> directors;
    private String genre;
    private String imageUrl;
    private String imdbId;
    private String iTunesUrl;
    private String longDescription;
    private String netflixId;
    private List<String> producers;
    private String rating;
    private String releaseDate;
    private List<String> screenWriters;
    private String shortDescription;
    private String tagChimpId;
    private String title;
    private String totalChapters;

    public TagInfo() {
    }

    public String getAmazonAsin() {
        return amazonAsin;
    }

    public List<String> getCast() {
        return cast;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public String getGenre() {
        return genre;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }

    public String getImdbId() {
        return imdbId;
    }
    
    public String getITunesUrl() {
        return iTunesUrl;
    }

    public String getLongDescription() {
        return longDescription;
    }
    
    public String getNetflixId() {
        return netflixId;
    }

    public List<String> getProducers() {
        return producers;
    }

    public String getRating() {
        return rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public List<String> getScreenWriters() {
        return screenWriters;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getTagChimpId() {
        return tagChimpId;
    }

    public String getTitle() {
        return title;
    }

    public String getTotalChapters() {
        return totalChapters;
    }

    public void setAmazonAsin( String amazonAsin ) {
        this.amazonAsin = amazonAsin;
    }

    public void setCast( List<String> cast ) {
        this.cast = cast;
    }

    public void setDirectors( List<String> directors ) {
        this.directors = directors;
    }

    public void setGenre( String genre ) {
        this.genre = genre;
    }
    
    public void setImageUrl( String imageUrl ) {
        this.imageUrl = imageUrl;
    }

    public void setImdbId( String imdbId ) {
        this.imdbId = imdbId;
    }
    
    public void setITunesUrl( String iTunesUrl ) {
        this.iTunesUrl = iTunesUrl;
    }

    public void setLongDescription( String longDescription ) {
        this.longDescription = longDescription;
    }
    
    public void setNetflixId( String netflixId ) {
        this.netflixId = netflixId;
    }

    public void setProducers( List<String> producers ) {
        this.producers = producers;
    }

    public void setRating( String rating ) {
        this.rating = rating;
    }

    public void setReleaseDate( String releaseDate ) {
        this.releaseDate = releaseDate;
    }

    public void setScreenWriters( List<String> screenWriters ) {
        this.screenWriters = screenWriters;
    }

    public void setShortDescription( String shortDescription ) {
        this.shortDescription = shortDescription;
    }

    public void setTagChimpId( String tagChimpId ) {
        this.tagChimpId = tagChimpId;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public void setTotalChapters( String totalChapters ) {
        this.totalChapters = totalChapters;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append( "title=" )
                .append( title == null ? "<null>" : title )
                .append( ",releaseDate=" )
                .append( releaseDate == null ? "<null>" : releaseDate )
                .append( ",totalChapters=" )
                .append( totalChapters == null ? "<null>" : totalChapters )
                .append( ",cast=" )
                .append( cast == null ? "<null>" : cast )
                .append( ",imageUrl=" )
                .append( imageUrl == null ? "<null>" : imageUrl )
                .toString();
    }
}
