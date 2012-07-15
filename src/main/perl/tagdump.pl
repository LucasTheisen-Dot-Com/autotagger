#!C:/Perl/bin/perl.exe

use strict;
use warnings;

use Data::Dumper;
use MP4::Info;

sub toString {
    my $value = shift;
    my $newValue = "";
    if ( ref( $value ) eq 'ARRAY' ) {
        $newValue = '[';
        my $first = 1;
        foreach my $entry ( @{$value} ) {
            if ( $first ) {
                $first = 0;
            }
            else {
                $newValue .= ","
            }
            $newValue .= toString( $entry );
        } 
        $newValue .= ']';
    }
    elsif ( ref( $value ) eq 'HASH' ) {
        $newValue = '{';
        my $first = 1;
        foreach my $key ( sort keys( %{$value} ) ) {
            if ( $first ) {
                $first = 0;
            }
            else {
                $newValue .= ","
            }
            $newValue .= "$key=>" . toString( $value->{$key} ); 
        }
        $newValue .= '}';
    }
    else {
        $newValue = $value;
    }
    return $newValue;
}

my $file = '../../../data/AustinPowersInternationalManOfMystery.m4v';
print( "you suck\n" ) if ( ! -e $file );
my $tag = get_mp4tag($file) or die "No TAG info";
foreach my $fieldName ( sort keys( %{$tag} ) ) {
    my $fieldValue = toString( $fieldName eq 'COVR' ? "<binary>" : $tag->{$fieldName} );
    print( "$fieldName=$fieldValue\n" );
}

print( "done" );
