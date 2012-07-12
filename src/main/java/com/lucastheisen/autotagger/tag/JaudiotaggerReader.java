package com.lucastheisen.autotagger.tag;


import java.io.File;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JaudiotaggerReader implements Reader {
    private static Logger log = LoggerFactory.getLogger( JaudiotaggerReader.class );

    @Override
    public TagInfo read( File file ) {
        log.debug( "Reading tag info from {}", file.getAbsolutePath() );
        TagInfo tagInfo = new TagInfo();
//        Mp4Tag mp4tag = (Mp4Tag) f.getTag();
//        mp4tag.getFirst( Mp4FieldKey.ARTIST );
//        mp4tag.getFirst( Mp4FieldKey.COMPOSER );
//        mp4tag.getFirst( Mp4FieldKey.LYRICS );
        return tagInfo;
    }
}
