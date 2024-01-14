package sbankpaymentms.exception;

import sbankpaymentms.exception.base.InvalidStateException;

import java.io.Serial;

public class TransactionExpiredException extends InvalidStateException {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "TRANSACTION_EXPIRED";

    public TransactionExpiredException() {
        super(MESSAGE);
    }

}
