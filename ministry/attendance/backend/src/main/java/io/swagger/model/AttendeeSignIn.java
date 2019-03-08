package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.Meeting;
import io.swagger.model.Member;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AttendeeSignIn
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-03-08T12:34:07.357Z")

public class AttendeeSignIn   {
  @JsonProperty("user")
  private Member user = null;

  @JsonProperty("meeting")
  private Meeting meeting = null;

  public AttendeeSignIn user(Member user) {
    this.user = user;
    return this;
  }

  /**
   * Get user
   * @return user
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Member getUser() {
    return user;
  }

  public void setUser(Member user) {
    this.user = user;
  }

  public AttendeeSignIn meeting(Meeting meeting) {
    this.meeting = meeting;
    return this;
  }

  /**
   * Get meeting
   * @return meeting
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Meeting getMeeting() {
    return meeting;
  }

  public void setMeeting(Meeting meeting) {
    this.meeting = meeting;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AttendeeSignIn attendeeSignIn = (AttendeeSignIn) o;
    return Objects.equals(this.user, attendeeSignIn.user) &&
        Objects.equals(this.meeting, attendeeSignIn.meeting);
  }

  @Override
  public int hashCode() {
    return Objects.hash(user, meeting);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AttendeeSignIn {\n");
    
    sb.append("    user: ").append(toIndentedString(user)).append("\n");
    sb.append("    meeting: ").append(toIndentedString(meeting)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

