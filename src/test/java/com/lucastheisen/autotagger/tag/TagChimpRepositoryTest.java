package com.lucastheisen.autotagger.tag;


import static org.junit.Assert.assertTrue;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TagChimpRepositoryTest {
    private static Logger log = LoggerFactory.getLogger( TagChimpRepositoryTest.class );
    
    @Test
    public void canIEvenConnect() {
        URI uri = null;
        try {
            uri = new URI( "http", null, "www.tagchimp.com", -1, "/ape/search.php", "token=10664939374FF64C1FA0EFE&type=search&title=Tomorrow Never Dies&totalChapters=1", null );
        }
        catch ( URISyntaxException urise ) {
            assertTrue( urise.getMessage(), false );    
        }
        
        try ( BufferedReader reader = new BufferedReader( new InputStreamReader( uri.toURL().openStream() ) )) {
            String line;
            while ( (line = reader.readLine()) != null ) {
                log.debug( line );
            }
        }
        catch ( IOException e ) {
            assertTrue( e.getMessage(), false );    
        }
    }

    @Test
    public void testTomorrowNeverDies() {
        // https://www.tagchimp.com/ape/search.php?token=10664939374FF64C1FA0EFE&type=search&title=Tomorrow%20Never%20Dies&totalChapters=1
        TagChimpRepository repository = new TagChimpRepository( "http", "www.tagchimp.com", "/ape/search.php", "10664939374FF64C1FA0EFE" );
        log.debug( "**** Results ****" );
        for ( TagInfo tagInfo : repository.search( "Tomorrow Never Dies", 1 ) ) {
            log.debug( tagInfo.toString() );
        }
        log.debug( "**** End Results ****" );
    }
}
