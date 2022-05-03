package com.emeet.api.models;

import java.util.Comparator;

public class MeetingSort implements Comparator<Meetings> {

    // @Override
 

    // Method of this class

    // To compare datetime objects

    public int compare(Meetings a, Meetings b)

    {
 
         if(a.getDisbaleDateandTimeSort()==null ||b.getDisbaleDateandTimeSort()==null)
        	 return 0;

        return b.getDisbaleDateandTimeSort().compareTo(a.getDisbaleDateandTimeSort());

    }
}