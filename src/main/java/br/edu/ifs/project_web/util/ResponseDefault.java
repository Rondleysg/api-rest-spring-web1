package br.edu.ifs.project_web.util;
import lombok.*;

@Getter
@Setter
public class ResponseDefault {
    private String mensagem;
    private int codigo;
    private Object value;
}