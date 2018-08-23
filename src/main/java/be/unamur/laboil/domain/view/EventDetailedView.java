package be.unamur.laboil.domain.view;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDetailedView {
    private String userName;
    private String comment;
    private String time;
    private String status;
    private boolean isEmployee;


}
