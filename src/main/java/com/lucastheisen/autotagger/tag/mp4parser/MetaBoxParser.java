package com.lucastheisen.autotagger.tag.mp4parser;


import com.coremedia.iso.boxes.MetaBox;
import com.lucastheisen.autotagger.tag.TagInfo;


public interface MetaBoxParser {
    public TagInfo parse( MetaBox metaBox );
}
