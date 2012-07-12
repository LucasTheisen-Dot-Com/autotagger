package com.lucastheisen.autotagger.tag;


import java.io.File;


public interface Reader {
    public TagInfo read( File file );
}
