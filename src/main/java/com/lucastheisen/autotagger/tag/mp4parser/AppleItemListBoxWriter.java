package com.lucastheisen.autotagger.tag.mp4parser;


import java.util.List;


import com.coremedia.iso.boxes.MetaBox;
import com.coremedia.iso.boxes.apple.AppleGenericBox;
import com.coremedia.iso.boxes.apple.AppleItemListBox;
import com.coremedia.iso.boxes.apple.AppleTrackTitleBox;
import com.lucastheisen.autotagger.tag.TagInfo;
import com.lucastheisen.autotagger.tag.mp4parser.AppleBoxUtils.GenericMeaning;
import com.lucastheisen.autotagger.tag.mp4parser.AppleBoxUtils.GenericName;
import com.lucastheisen.autotagger.tag.mp4parser.AppleBoxUtils.PlistKey;


public class AppleItemListBoxWriter implements BoxWriter<MetaBox> {
    @Override
    public void write( MetaBox metaBox, TagInfo tagInfo ) {
        List<AppleItemListBox> appleItemListBoxes = metaBox.getBoxes( AppleItemListBox.class );
        AppleItemListBox appleItemListBox = null;
        if ( appleItemListBoxes.size() == 0 ) {
            appleItemListBox = new AppleItemListBox();
            metaBox.addBox( appleItemListBox );
        }
        else {
            appleItemListBox = appleItemListBoxes.get( 0 );
        }
        appleItemListBox.getBoxes( AppleTrackTitleBox.class );

        AppleGenericBox iTunEXTC = null;
        AppleGenericBox iTunMOVI = null;
        for ( AppleGenericBox appleGenericBox : metaBox.getBoxes( AppleGenericBox.class ) ) {
            GenericName name = GenericName.forText( AppleBoxUtils.getName( appleGenericBox ) );
            if ( name != null ) {
                switch ( name ) {
                case iTunEXTC:
                    iTunEXTC = appleGenericBox;
                    break;
                case iTunMOVI:
                    iTunMOVI = appleGenericBox;
                    break;
                }
            }
        }
        if ( tagInfo.getRating() != null ) {
            if ( iTunEXTC == null ) {
                iTunEXTC = new AppleGenericBox();
                appleItemListBox.addBox( iTunEXTC );
                AppleBoxUtils.setName( iTunEXTC, GenericName.iTunEXTC.getText() );
                AppleBoxUtils.setMeaning( iTunEXTC, GenericMeaning.iTunes.getText() );
            }
            AppleBoxUtils.setData( iTunEXTC, tagInfo.getRating().toString().getBytes( AppleBoxUtils.CHARSET ) );
        }
        if ( tagInfo.getCast() != null || tagInfo.getDirectors() != null
                || tagInfo.getProducers() != null
                || tagInfo.getScreenWriters() != null ) {
            if ( iTunMOVI == null ) {
                iTunMOVI = new AppleGenericBox();
                appleItemListBox.addBox( iTunMOVI );
                AppleBoxUtils.setName( iTunMOVI, GenericName.iTunMOVI.getText() );
                AppleBoxUtils.setMeaning( iTunMOVI, GenericMeaning.iTunes.getText() );
            }
            AppleBoxUtils.setData( iTunMOVI, buildPlist( tagInfo ).getBytes( AppleBoxUtils.CHARSET ) );
        }

        List<AppleTrackTitleBox> appleTrackTitleBoxes = appleItemListBox.getBoxes( AppleTrackTitleBox.class );
        if ( appleTrackTitleBoxes.size() == 0 ) {
            AppleTrackTitleBox appleTrackTitleBox = new AppleTrackTitleBox();
            appleTrackTitleBox.setValue( tagInfo.getTitle() );
            appleItemListBox.addBox( appleTrackTitleBox );
        }
        else {
            appleTrackTitleBoxes.get( 0 ).setValue( tagInfo.getTitle() );
        }
    }

    private String buildPlist( TagInfo tagInfo ) {
        // TODO: refactor to use a better xml build, probably XMLStreamWriter
        StringBuilder builder = new StringBuilder()
                .append( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" )
                .append( "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" )
                .append( "<plist version=\"1.0\">\n" )
                .append( "  <dict>\n" );
        if ( tagInfo.getCast() != null ) {
            appendListToDict( builder, PlistKey.cast, tagInfo.getCast() );
        }
        if ( tagInfo.getDirectors() != null ) {
            appendListToDict( builder, PlistKey.directors, tagInfo.getDirectors() );
        }
        if ( tagInfo.getProducers() != null ) {
            appendListToDict( builder, PlistKey.producers, tagInfo.getProducers() );
        }
        if ( tagInfo.getScreenWriters() != null ) {
            appendListToDict( builder, PlistKey.screenwriters, tagInfo.getScreenWriters() );
        }
        builder.append( "  </dict>\n" )
                .append( "</plist>" );
        return builder.toString();
    }

    private void appendListToDict( StringBuilder builder, PlistKey key,
            List<String> values ) {
        builder.append( "    <key>" ).append( key.toString() ).append( "</key>\n" )
                .append( "    <array>\n" );
        for ( String value : values ) {
            builder.append( "      <dict>\n" )
                    .append( "        <key>name</key>\n" )
                    .append( "        <string>" ).append( value ).append( "</string>\n" )
                    .append( "      </dict>\n" );
        }
        builder.append( "    </array>\n" );
    }
}
