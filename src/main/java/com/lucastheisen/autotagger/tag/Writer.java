package com.lucastheisen.autotagger.tag;


import java.io.File;


public interface Writer {
    public void write( File file, TagInfo tagInfo );
}
