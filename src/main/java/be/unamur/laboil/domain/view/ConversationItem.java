package be.unamur.laboil.domain.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Joachim Lebrun on 22-08-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversationItem {
    private String userName;
    private String comment;
    private String time;
    private boolean isEmployee;
}
