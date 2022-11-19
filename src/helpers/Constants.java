package helpers;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class Constants {
    public static final int MAX_ROW_INDEX = 4;
    public static final int HERO_MAX_HEALTH = 30;
    public static final  int PLAYER_1_BACK_ROW = 3;
    public static final  int PLAYER_1_FRONT_ROW = 2;
    public static final  int PLAYER_2_BACK_ROW = 0;
    public static final  int PLAYER_2_FRONT_ROW = 1;
    public static final  int MAX_MANA_PER_TURN = 10;
    public static final  int MAX_MINIONS_PER_ROW = 5;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * returns mapper to be used in JSON conversion
     * @return generic ObjectMapper
     */
    public static ObjectMapper getMapper() {
        return OBJECT_MAPPER;
    }

    private Constants() { }
}
