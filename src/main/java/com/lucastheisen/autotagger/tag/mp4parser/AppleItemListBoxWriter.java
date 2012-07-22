package com.lucastheisen.autotagger.tag.mp4parser;


import java.util.List;


import com.coremedia.iso.boxes.MetaBox;
import com.coremedia.iso.boxes.apple.AppleItemListBox;
import com.coremedia.iso.boxes.apple.AppleTrackTitleBox;
import com.lucastheisen.autotagger.tag.TagInfo;


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
}
