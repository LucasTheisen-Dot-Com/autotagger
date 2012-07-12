package com.lucastheisen.autotagger.tag;


import java.util.List;


public interface Repository {
    public List<TagInfo> search( String title, int chapters );
}
