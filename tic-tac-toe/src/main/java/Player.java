import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Player {
    String name;
    SIGN sign;
}

enum SIGN {
    X, O
}
