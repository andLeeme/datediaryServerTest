package bless.datediary.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatResponseBody {
    String couple_index;
    String email;
    String sender;
    String message;
    String timestamp;
}
