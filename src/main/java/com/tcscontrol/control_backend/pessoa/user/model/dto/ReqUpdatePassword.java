package com.tcscontrol.control_backend.pessoa.user.model.dto;

public record ReqUpdatePassword(
    String currentPassword, 
    String newPassword1,
    String newPassword2
){}
