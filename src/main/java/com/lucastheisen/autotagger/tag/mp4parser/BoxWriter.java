package com.lucastheisen.autotagger.tag.mp4parser;

import com.coremedia.iso.boxes.Box;
import com.lucastheisen.autotagger.tag.TagInfo;

public interface BoxWriter<T extends Box> {
    public void write( T parentBox, TagInfo tagInfo );
}
