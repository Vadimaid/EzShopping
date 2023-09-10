package kg.ezshopping.ezshopping.dto;

public class JwtResponseDto {
    private String jwtValue;

    public JwtResponseDto() {
    }

    public String getJwtValue() {
        return jwtValue;
    }

    public JwtResponseDto setJwtValue(String jwtValue) {
        this.jwtValue = jwtValue;
        return this;
    }
}
