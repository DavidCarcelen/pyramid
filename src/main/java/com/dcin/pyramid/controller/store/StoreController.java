package com.dcin.pyramid.controller.store;

import com.dcin.pyramid.model.dto.GeneralResponse;
import com.dcin.pyramid.model.dto.auth.StoreSignUpRequest;
import com.dcin.pyramid.model.dto.user.StoreDTO;
import com.dcin.pyramid.model.dto.user.UpdateStoreRequest;
import com.dcin.pyramid.model.entity.Store;
import com.dcin.pyramid.service.StoreService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequiredArgsConstructor
@RequestMapping("/pyramid/store")
@PreAuthorize("hasRole('STORE')")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Store", description = "Store-related operations")
public class StoreController {
    private final StoreService storeService;

    @PutMapping("/update")
    public ResponseEntity<GeneralResponse> updateStore(@AuthenticationPrincipal Store store,
                                                        @Valid @RequestBody UpdateStoreRequest request) {
        return ResponseEntity.ok(storeService.updateStore(store, request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<GeneralResponse> deleteStore(@AuthenticationPrincipal Store store) {

        return ResponseEntity.ok(storeService.deleteStore(store));
    }

    @GetMapping("/my-profile")
    public ResponseEntity<StoreDTO> storeProfile (@AuthenticationPrincipal Store store){

        return ResponseEntity.ok(storeService.getStoreDTO(store));
    }

    @PatchMapping("/profile-picture")
    public ResponseEntity<GeneralResponse> uploadProfilePicture(@AuthenticationPrincipal Store store,
                                                                @RequestParam("file") MultipartFile file){
        return ResponseEntity.ok(storeService.uploadProfilePicture(store, file));
    }

}
