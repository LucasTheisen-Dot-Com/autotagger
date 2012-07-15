package com.lucastheisen.autotagger.tag.mp4parser;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.coremedia.iso.boxes.AbstractContainerBox;
import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.apple.AppleDataBox;
import com.coremedia.iso.boxes.apple.AppleEncoderBox;
import com.coremedia.iso.boxes.apple.AppleGenericBox;
import com.coremedia.iso.boxes.apple.AppleItemListBox;
import com.coremedia.iso.boxes.apple.AppleMeanBox;
import com.coremedia.iso.boxes.apple.AppleNameBox;
import com.lucastheisen.autotagger.tag.TagInfo;
import com.lucastheisen.autotagger.tag.TagInfo.Rating;


public class AppleItemListBoxParser implements Parser<AppleItemListBox> {
    private static Logger log = LoggerFactory.getLogger( AppleItemListBoxParser.class );

    @Override
    public void parse( AppleItemListBox appleItemListBox, TagInfo tagInfo ) {
        log.trace( "parsing AppleItemListBox" );
        for ( Box box : appleItemListBox.getBoxes() ) {
            if ( box instanceof AppleEncoderBox ) {
                // do nothing
            }
            else if ( box instanceof AppleGenericBox ) {
                String meaning = getMeaning( (AppleGenericBox) box );
                String name = getName( (AppleGenericBox) box );
                byte[] data = getData( (AppleGenericBox) box );
                if ( data != null && "com.apple.iTunes".equals( meaning ) ) {
                    if ( "iTunEXTC".equals( name ) ) {
                        String[] ratingTokens = new String( data ).split( "\\|" );
                        if ( ratingTokens.length > 1 ) {
                            tagInfo.setRating( Rating.fromStandardAndRating( ratingTokens[0], ratingTokens[1] ) );
                        }
                        else {
                            tagInfo.setRating( Rating.fromRating( ratingTokens[0] ) );
                        }
                    }
                    else if ( "iTunMOVI".equals( name ) ) {
                        
                    }
                }
            }
        }
    }

    private byte[] getData( AbstractContainerBox box ) {
        return box.getBoxes( AppleDataBox.class ).get( 0 ).getData();
    }

    private String getMeaning( AppleGenericBox box ) {
        return box.getBoxes( AppleMeanBox.class ).get( 0 ).getMeaning();
    }

    private String getName( AppleGenericBox box ) {
        return box.getBoxes( AppleNameBox.class ).get( 0 ).getName();
    }
}
