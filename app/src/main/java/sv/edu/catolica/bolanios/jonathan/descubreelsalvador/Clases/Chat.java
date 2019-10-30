package sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases;

public class Chat {
    private String enviador;
    private String recibidor;
    private String mensaje;

    public Chat(String enviador, String recibidor, String mensaje) {
        this.enviador = enviador;
        this.recibidor = recibidor;
        this.mensaje = mensaje;
    }

    public Chat() {
    }

    public String getEnviador() {
        return enviador;
    }

    public void setEnviador(String enviador) {
        this.enviador = enviador;
    }

    public String getRecibidor() {
        return recibidor;
    }

    public void setRecibidor(String recibidor) {
        this.recibidor = recibidor;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
