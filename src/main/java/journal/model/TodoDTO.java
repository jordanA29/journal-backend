package journal.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TodoDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String title;

    @Size(max = 255)
    private String comment;

    @NotNull
    private Boolean done;

}
