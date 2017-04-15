import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { MerchantConfiguration } from './merchant-configuration.model';
import { MerchantConfigurationService } from './merchant-configuration.service';
@Injectable()
export class MerchantConfigurationPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private merchantConfigurationService: MerchantConfigurationService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.merchantConfigurationService.find(id).subscribe((merchantConfiguration) => {
                this.merchantConfigurationModalRef(component, merchantConfiguration);
            });
        } else {
            return this.merchantConfigurationModalRef(component, new MerchantConfiguration());
        }
    }

    merchantConfigurationModalRef(component: Component, merchantConfiguration: MerchantConfiguration): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.merchantConfiguration = merchantConfiguration;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
