import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { MerchantStore } from './merchant-store.model';
import { MerchantStoreService } from './merchant-store.service';
@Injectable()
export class MerchantStorePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private merchantStoreService: MerchantStoreService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.merchantStoreService.find(id).subscribe((merchantStore) => {
                if (merchantStore.inBusinessSince) {
                    merchantStore.inBusinessSince = {
                        year: merchantStore.inBusinessSince.getFullYear(),
                        month: merchantStore.inBusinessSince.getMonth() + 1,
                        day: merchantStore.inBusinessSince.getDate()
                    };
                }
                if (merchantStore.created_date) {
                    merchantStore.created_date = {
                        year: merchantStore.created_date.getFullYear(),
                        month: merchantStore.created_date.getMonth() + 1,
                        day: merchantStore.created_date.getDate()
                    };
                }
                if (merchantStore.last_modified_date) {
                    merchantStore.last_modified_date = {
                        year: merchantStore.last_modified_date.getFullYear(),
                        month: merchantStore.last_modified_date.getMonth() + 1,
                        day: merchantStore.last_modified_date.getDate()
                    };
                }
                this.merchantStoreModalRef(component, merchantStore);
            });
        } else {
            return this.merchantStoreModalRef(component, new MerchantStore());
        }
    }

    merchantStoreModalRef(component: Component, merchantStore: MerchantStore): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.merchantStore = merchantStore;
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
