package com.lucastheisen.autotagger.console;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BlockTextTest {
    private static Logger log = LoggerFactory.getLogger( BlockTextTest.class );

    @Test
    public void testSmallBlock() {
        String original = "This is a test, this is only a test of the emergency broadcast system.  In the case of a real emergency, this message would be followed by instructions.";
        String formatted = new BlockText( original, "BBB  ", 60 ).toString();
        
        log.debug( new StringBuilder( "**** FORMATTED ****\n" ).append( formatted ).append( "\n**** END FORMATTED ****").toString() );
    }
}
