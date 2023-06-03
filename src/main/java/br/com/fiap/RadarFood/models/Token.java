package br.com.fiap.RadarFood.models;

public record Token(
    String token,
    String type,
    String prefix
) {}
