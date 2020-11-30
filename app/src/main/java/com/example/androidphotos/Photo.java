package com.example.androidphotos;

import android.media.Image;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class that defines a Photo
 * @author Advith Chegu
 * @author Banty Patel
 */
public class Photo implements Comparable<Photo>{
    private String caption;
    private ArrayList<String> tags;
    private String photoPath;

    /**
     * Creates a new photo object
     * @param caption caption of the photo
     * @param photoPath filepath of the photo on user's computer
     * @param tags tags of the photo in tag name: tag value format
     */
    public Photo(String caption, ArrayList<String> tags, String photoPath){
        this.caption = caption;
        this.tags = tags;
        this.photoPath = photoPath;
    }

    /**
     * method sets the caption of the photo
     * @param caption - caption of photo
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }

    /**
     * method to add a tag to a photo
     * @param tag - tag to be added
     */
    public void addTag(String tag){
        tags.add(tag);
    }
    /**
     * method to remove a tag from a photo
     * @param tag - tag to be removed
     */
    public void removeTag(String tag) {
        for(String s: tags) {
            if(s.equals(tag)) {
                tags.remove(s);
                break;
            }
        }
    }

    /**
     * method to get the caption of a photo
     * @return - the caption of the photo
     */
    public String getCaption() {
        return caption;
    }

    /**
     * method to get the list of tags for a photo
     * @return - arraylist of tags
     */
    public ArrayList<String> getTags() {
        return tags;
    }

    /**
     * method to get the photo path of a photo
     * @return - path of photo
     */
    public String getPhotoPath() {
        return photoPath;
    }

    /**
     * method used to display the caption of a photo
     * @return - caption string
     */
    public String toString2(){
        return "Caption: " + this.caption;
    }

    /**
     * method used to serialize the photo data
     * @return - string to be serialized
     */
    @Override
    public String toString() {
        return this.caption + "|" + this.tags + "|" + this.photoPath;
    }

    /**
     * method to produce a hashcode of a caption
     * @return - hashcode
     */
    @Override
    public int hashCode() {
        return this.caption.hashCode();
    }

    /**
     * method two photos
     * @return - int used for comparison
     */
    @Override
    public int compareTo(Photo toCompare) {

        //sort tag arrays before comparing
        ArrayList<String> myTags = new ArrayList<>(this.tags);
        ArrayList<String> compareTags = new ArrayList<>(toCompare.getTags());
        Collections.sort(myTags);
        Collections.sort(compareTags);

        //first compare the date time
        if (!this.photoPath.equals(toCompare.getPhotoPath())){
            return this.photoPath.compareTo(toCompare.getPhotoPath());
        } else if(!this.caption.equals(toCompare.getCaption())){
            return this.caption.compareTo(toCompare.getCaption());
        } else if(this.tags.size() != toCompare.getTags().size()){
            return toCompare.getTags().size() - this.getTags().size();
        } else{
            for (int i = 0; i < this.tags.size(); i++){
                if(!myTags.get(i).equals(compareTags.get(i))){
                    return myTags.get(i).compareTo(compareTags.get(i));
                }
            }
        }
        return 0;
    }

    /**
     * method to check equality of two photos
     * @return - bool used for comparison
     */
    @Override
    public boolean equals(Object o) {

        //convert to a photo object
        if(o == null){
            return false;
        } else if(!(o instanceof Photo)){
            return false;
        }
        Photo toCompare = (Photo)o;

        //sort tag arrays before comparing
        ArrayList<String> myTags = new ArrayList<>(this.tags);
        ArrayList<String> compareTags = new ArrayList<>(toCompare.getTags());
        Collections.sort(myTags);
        Collections.sort(compareTags);

        //first compare the date time
        if (!this.photoPath.equals(toCompare.getPhotoPath())){
            return false;
        } else if(!this.caption.equals(toCompare.getCaption())){
            return false;
        } else if(this.tags.size() != toCompare.getTags().size()){
            return false;
        } else{
            for (int i = 0; i < this.tags.size(); i++){
                if(!myTags.get(i).equals(compareTags.get(i))){
                    return false;
                }
            }
        }
        return true;
    }
}
