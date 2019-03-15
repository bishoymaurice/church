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
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-03-08T15:50:03.200Z")

public class AttendeeSignIn   {
  @JsonProperty("member")
  private Member member = null;

  @JsonProperty("meeting")
  private Meeting meeting = null;

  public AttendeeSignIn member(Member member) {
    this.member = member;
    return this;
  }

  /**
   * Get member
   * @return member
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Member getMember() {
    return member;
  }

  public void setMember(Member member) {
    this.member = member;
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
    return Objects.equals(this.member, attendeeSignIn.member) &&
        Objects.equals(this.meeting, attendeeSignIn.meeting);
  }

  @Override
  public int hashCode() {
    return Objects.hash(member, meeting);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AttendeeSignIn {\n");
    
    sb.append("    member: ").append(toIndentedString(member)).append("\n");
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

