package com.cibertec.monkey.service;

public interface EmailService {
    void enviarATodos(String asunto, String contenidoHtml);
    void enviarAUno(String destinatario, String asunto, String contenidoHtml);
}