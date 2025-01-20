package com.handong.cra.crawebbackend.exception.gallery;

import com.handong.cra.crawebbackend.exception.CraException;
import com.handong.cra.crawebbackend.exception.ErrorCode;

public class GalleryPageSizeLimitExceededException extends CraException {
    public GalleryPageSizeLimitExceededException() {
        super(ErrorCode.GALLERY_PAGE_SIZE_LIMIT_EXCEEDED);
    }
}
