package util;

public class LogService {

    private String log = "";

    public void registrar(String mensagem) {
        log += mensagem + "\n";
    }

    public String obterLog() {

        if (log.isEmpty()) {
            return "Nenhuma operação registrada.";
        }

        return log;
    }
}