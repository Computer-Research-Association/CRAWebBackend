package com.handong.cra.crawebbackend.exception.gallery;

import com.handong.cra.crawebbackend.exception.CraException;
import com.handong.cra.crawebbackend.exception.ErrorCode;

public class GalleryNotFoundException extends CraException {
    public GalleryNotFoundException() {
        super(ErrorCode.GALLERY_PAGE_SIZE_LIMIT_EXCEEDED);
    }

}
