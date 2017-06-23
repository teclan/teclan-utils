package teclan.utils.exec;

public class CommandExecutorException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CommandExecutorException(String s) {
        super(s);
    }

    public CommandExecutorException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
