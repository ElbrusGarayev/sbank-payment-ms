package sbankpaymentms.exception;

import sbankpaymentms.exception.base.InvalidStateException;

import java.io.Serial;

public class TransactionIsNotRefundableException extends InvalidStateException {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "TRANSACTION_IS_NOT_REFUNDABLE";

    public TransactionIsNotRefundableException() {
        super(MESSAGE);
    }

}
