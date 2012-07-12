package com.lucastheisen.autotagger.tag;

import java.io.File;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JaudiotaggerWriter implements Writer {
    private static Logger log = LoggerFactory.getLogger( JaudiotaggerWriter.class );
    
    @Override
    public void write( File file, TagInfo tagInfo ) {
        log.info( "Writing tag data to {}", file.getAbsolutePath() );
    }

}
