package com.dcin.pyramid.model.dto;

public record SignUpRequest(String email, String password, String role, String nickname, String storeName, String address) {
}
//hacer dos diferente? o opcional la direccion nombre tienda etc, simplificar