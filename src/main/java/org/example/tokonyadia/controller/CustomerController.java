package org.example.tokonyadia.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.example.tokonyadia.constant.APIUrl;
import org.example.tokonyadia.dto.request.CustomerRequest;
import org.example.tokonyadia.dto.response.AvatarResponse;
import org.example.tokonyadia.dto.response.CommonResponse;
import org.example.tokonyadia.dto.response.CustomerResponse;
import org.example.tokonyadia.dto.response.PageResponse;
import org.example.tokonyadia.entity.Customer;
import org.example.tokonyadia.service.CustomerService;
import org.example.tokonyadia.service.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(path = APIUrl.CUSTOMER_API)
@SecurityRequirement(name = "Authorization")
public class CustomerController {
    private CustomerService customerService;
    private final FileStorageService fileStorageService;

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest request) {
        CustomerResponse createdCustomer = customerService.saveCustomer(request);
        return ResponseEntity.ok(createdCustomer);
    }

    @PutMapping
    public ResponseEntity<CustomerResponse> updateCustomer(@RequestBody CustomerRequest request) {
        CustomerResponse createdCustomer = customerService.updateCustomer(request);
        return ResponseEntity.ok(createdCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
        // block jika terjadi exception pada line 35
        return ResponseEntity.ok("Success Delete Customer By Id");
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable String id) {
        CustomerResponse customerResponse = customerService.getCustomerById(id);
        return ResponseEntity.ok(customerResponse);
    }

    @GetMapping
    public ResponseEntity<PageResponse<CustomerResponse>> getAllCustomer(
            @RequestParam(name = "page", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "size", defaultValue = "4") Integer pageSize,
            @RequestParam(name = "sortType", defaultValue = "ASC") String sortType,
            @RequestParam(name = "property", defaultValue = "name") String property,
            @RequestParam(name = "name", required = false) String name
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortType), property);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        CustomerRequest customerRequest = CustomerRequest.builder()
                .name(name)
                .build();

        Page<CustomerResponse> customerResponse = customerService.getCustomerPerPage(pageable, customerRequest);

        if (customerResponse.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        PageResponse<CustomerResponse> pageResponse = new PageResponse<>(customerResponse);
        return ResponseEntity.ok(pageResponse);

    }

    @PostMapping("/avatar")
    public ResponseEntity<CommonResponse<AvatarResponse>> uploadAvatar(@RequestParam("avatar") MultipartFile avatar, HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        String fileName = fileStorageService.storeFile(avatar, userId);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(APIUrl.CUSTOMER_API)
                .path("/avatar")
                .path(fileName)
                .toUriString();

        AvatarResponse avatarResponse = AvatarResponse.builder()
                .url(fileDownloadUri)
                .name(fileName)
                .build();

        CommonResponse<AvatarResponse> commonResponse = CommonResponse.<AvatarResponse>builder()
                .message("File uploaded successfully")
                .statusCode(HttpStatus.CREATED.value())
                .data(Optional.of(avatarResponse))
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);

    }

    @GetMapping("/avatar/{fileName:.+}")
    public ResponseEntity<Resource> getAvatar(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        String contenType = null;
        try {
            contenType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Couldn't determine filt type");
        }

        if (contenType == null) {
            contenType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contenType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}

