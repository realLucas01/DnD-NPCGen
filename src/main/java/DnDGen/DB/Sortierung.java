package DnDGen.DB;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Sortierung {
	@Retention(RetentionPolicy.RUNTIME)
	@interface Order {
	    int value();
	}
}
