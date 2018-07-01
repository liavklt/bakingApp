package com.example.android.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/**
 * Created by lianavklt on 23/06/2018.
 */

public class Step implements Parcelable {

  private Integer id;
  private String shortDescription;
  private String description;
  private String videoUrl;
  private String thumbnailUrl;

  public static final Parcelable.Creator<Step> CREATOR =
      new Parcelable.Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel source) {
          return new Step(source);
        }

        @Override
        public Step[] newArray(int size) {
          return new Step[size];
        }
      };

  public Step(Parcel source) {
    id = source.readInt();
    shortDescription = source.readString();
    description = source.readString();
    videoUrl = source.readString();

  }

  public Step() {
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getShortDescription() {
    return shortDescription;
  }

  public void setShortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getVideoUrl() {
    return videoUrl;
  }

  public void setVideoUrl(String videoUrl) {
    this.videoUrl = videoUrl;
  }

  public String getThumbnailUrl() {
    return thumbnailUrl;
  }

  public void setThumbnailUrl(String thumbnailUrl) {
    this.thumbnailUrl = thumbnailUrl;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeInt(id);
    parcel.writeString(shortDescription);
    parcel.writeString(description);
    parcel.writeString(videoUrl);

  }
}
