package com.handong.cra.crawebbackend.exception.s3image;

import com.handong.cra.crawebbackend.exception.CraException;
import com.handong.cra.crawebbackend.exception.ErrorCode;

public class S3ImageTransferException extends CraException {
    public S3ImageTransferException() {
        super(ErrorCode.S3IMAGE_TRANSFER);
    }
}
