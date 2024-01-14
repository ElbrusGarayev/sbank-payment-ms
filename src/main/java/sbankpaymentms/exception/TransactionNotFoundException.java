package sbankpaymentms.exception;

import sbankpaymentms.exception.base.NotFoundException;

import java.io.Serial;

public class TransactionNotFoundException extends NotFoundException {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "TRANSACTION_NOT_FOUND";

    public TransactionNotFoundException() {
        super(MESSAGE);
    }

}
