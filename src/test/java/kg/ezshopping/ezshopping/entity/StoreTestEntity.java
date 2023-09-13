package kg.ezshopping.ezshopping.entity;

import liquibase.pro.packaged.L;

import static org.junit.jupiter.api.Assertions.*;

public class StoreTestEntity {
    public static final Long STORE_ID = 1L;
    public static final String STORE_NAME = "store";
    public static final String LOGIN = "store";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "store@gmail.com";
    public static final String ADDRESS = "store 2a";
    public static final String LINK  = "https://www.instagram.com/username/";

  public static Store getTestStoreEntity(){
      return new Store()
              .setId(STORE_ID)
              .setFullName(STORE_NAME)
              .setLogin(LOGIN)
              .setPassword(PASSWORD)
              .setEmail(EMAIL)
              .setLink(LINK)
              .setAddress(ADDRESS);
  }
}