package com.tcscontrol.control_backend.auth.dto;

public record AuthRequest(
    String login,
    String password
) {}
