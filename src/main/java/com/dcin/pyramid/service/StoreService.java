package com.dcin.pyramid.service;

import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.auth.PlayerSignUpRequest;
import com.dcin.pyramid.model.dto.auth.StoreSignUpRequest;
import com.dcin.pyramid.model.dto.user.PlayerDTO;
import com.dcin.pyramid.model.dto.user.StoreDTO;
import com.dcin.pyramid.model.dto.user.UpdateStoreRequest;
import com.dcin.pyramid.model.entity.Player;
import com.dcin.pyramid.model.entity.Store;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface StoreService {
    GeneralResponse updateStore(Store store, UpdateStoreRequest request);
    GeneralResponse deleteStore(Store store);
    Store getStoreById(UUID storeId);
    StoreDTO getStoreDTO(Store store);
    GeneralResponse uploadProfilePicture(Store store, MultipartFile file);
}
