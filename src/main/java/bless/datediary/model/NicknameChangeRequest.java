package bless.datediary.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NicknameChangeRequest {
    String user_email;
    String edited_nickname;

}
